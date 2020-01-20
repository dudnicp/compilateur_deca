package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BGT;
import fr.ensimag.ima.pseudocode.instructions.BLE;
import fr.ensimag.ima.pseudocode.instructions.SGT;

/**
 *
 * @author gl28
 * @date 01/01/2020
 */
public class Greater extends AbstractOpIneq {

    public Greater(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return ">";
    }


	@Override
	protected void mnemo(DecacCompiler compiler, DVal op,
			GPRegister register) {
		super.mnemo(compiler, op, register);
	}
	
	@Override
	protected void codeBranch(DecacCompiler compiler, boolean b, Label label) {
		if (b) {
			compiler.addInstruction(new BGT(label));
		}
		else {
			compiler.addInstruction(new BLE(label));
		}
	}
	
	@Override
	protected void codeAssign(DecacCompiler compiler, int n) {
		codeExpr(compiler, n);
		compiler.addInstruction(new SGT(Register.getR(n)));
	}
}
