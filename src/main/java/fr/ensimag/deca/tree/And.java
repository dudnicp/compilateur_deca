package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.ima.pseudocode.IMAProgram;
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
	protected void codeCondExpr(IMAProgram program, boolean b, Label label, int n, RegisterManager registerManager) {
    	if (b) {
    		Label endLabel = Label.newEndAndLabel();
			getLeftOperand().codeCondExpr(program, false, endLabel, n, registerManager);
			getRightOperand().codeCondExpr(program, true, label, n, registerManager);
			program.addLabel(endLabel);
		} else {
			getLeftOperand().codeCondExpr(program, false, label, n, registerManager);
			getRightOperand().codeCondExpr(program, false, label, n, registerManager);
		}
	}
    
    @Override
	protected void codeExpr(IMAProgram program, int n, RegisterManager registerManager) {
    	Label endLabelAux = Label.newEndAndLabel();
    	Label endLabel = Label.newEndAndLabel();
    	program.addInstruction(new LOAD(new ImmediateInteger(0), Register.getR(n)));
    	if (n < Register.getRMAX()) {
    		getLeftOperand().codeCondExpr(program, false, endLabel, n+1, registerManager);			
		}
    	else {
    		program.addInstruction(new PUSH(Register.getR(n)));
    		registerManager.incCurrentNumberOfTemps();
    		getLeftOperand().codeCondExpr(program, false, endLabelAux, n, registerManager);
		}
    	getRightOperand().codeExpr(program, n, registerManager);
    	program.addInstruction(new BRA(endLabel));
    	if (n >= Register.getRMAX()) {
    		program.addLabel(endLabelAux);
    		program.addInstruction(new POP(Register.getR(n)));	
        	registerManager.decCurrentNumberOfTemps();
		}
    	program.addLabel(endLabel);
	}

}
