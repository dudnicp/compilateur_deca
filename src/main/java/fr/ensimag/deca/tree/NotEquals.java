package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.instructions.SEQ;
import fr.ensimag.ima.pseudocode.instructions.SNE;

/**
 *
 * @author gl28
 * @date 01/01/2020
 */
public class NotEquals extends AbstractOpExactCmp {

    public NotEquals(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "!=";
    }


    @Override
	protected void mnemo(DecacCompiler compiler, DVal op,
			GPRegister register) {
		super.mnemo(compiler, op, register);
		compiler.addInstruction(new SNE(register));
	}

}
