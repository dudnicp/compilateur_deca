package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.CodeTSTO;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BRA;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.PUSH;

/**
 *
 * @author gl28
 * @date 01/01/2020
 */
public class And extends AbstractOpBool {

    public And(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return "&&";
    }

    @Override
	protected void codeCondExpr(DecacCompiler compiler, boolean b, Label label, int n) {
    	if (b) {
    		Label endLabel = Label.newEndAndLabel();
			getLeftOperand().codeCondExpr(compiler, false, endLabel, n);
			getRightOperand().codeCondExpr(compiler, true, label, n);
			compiler.addLabel(endLabel);
		} else {
			getLeftOperand().codeCondExpr(compiler, false, label, n);
			getRightOperand().codeCondExpr(compiler, false, label, n);
		}
	}
    
    @Override
	protected void codeExpr(DecacCompiler compiler, int n) {
    	Label endLabelAux = Label.newEndAndLabel();
    	Label endLabel = Label.newEndAndLabel();
    	compiler.addInstruction(new LOAD(new ImmediateInteger(0), Register.getR(n)));
    	if (n < Register.getRMAX()) {
    		getLeftOperand().codeCondExpr(compiler, false, endLabel, n+1);			
		}
    	else {
    		compiler.addInstruction(new PUSH(Register.getR(n)));
    		getLeftOperand().codeCondExpr(compiler, false, endLabelAux, n);
		}
    	getRightOperand().codeExpr(compiler, n);
    	compiler.addInstruction(new BRA(endLabel));
    	if (n >= Register.getRMAX()) {
    		compiler.addLabel(endLabelAux);
        	compiler.addInstruction(new POP(Register.getR(n)));	
        	CodeTSTO.decCurrentStackSize();
		}
    	compiler.addLabel(endLabel);
	}

}
