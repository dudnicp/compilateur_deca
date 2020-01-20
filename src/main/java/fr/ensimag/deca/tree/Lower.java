package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BGE;
import fr.ensimag.ima.pseudocode.instructions.BLT;
import fr.ensimag.ima.pseudocode.instructions.SLT;

/**
 *
 * @author gl28
 * @date 01/01/2020
 */
public class Lower extends AbstractOpIneq {

    public Lower(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "<";
    }

	
	@Override
	protected void codeBranch(DecacCompiler compiler, boolean b, Label label) {
		if (b) {
			compiler.addInstruction(new BLT(label));
		} else {
			compiler.addInstruction(new BGE(label));
		}
	}
	
	@Override
	protected void codeAssign(DecacCompiler compiler, int n) {
		codeExpr(compiler, n);
		compiler.addInstruction(new SLT(Register.getR(n)));		
	}
	
}
