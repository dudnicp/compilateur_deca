package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BGT;
import fr.ensimag.ima.pseudocode.instructions.BLE;
import fr.ensimag.ima.pseudocode.instructions.SLE;

/**
 *
 * @author gl28
 * @date 01/01/2020
 */
public class LowerOrEqual extends AbstractOpIneq {
    public LowerOrEqual(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "<=";
    }


	@Override
	protected void mnemo(DecacCompiler compiler, DVal op,
			GPRegister register) {
		super.mnemo(compiler, op, register);
		compiler.addInstruction(new SLE(register));
	}
	
	@Override
	protected void codeCondExpr(DecacCompiler compiler, boolean b, Label label, int n) {
		super.codeCondExpr(compiler, b, label, n);
		if (b) {
			compiler.addInstruction(new BLE(label));
		} else {
			compiler.addInstruction(new BGT(label));
		}
	}

}
