package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.MUL;

/**
 * @author gl28
 * @date 01/01/2020
 */
public class Multiply extends AbstractOpArith {
    public Multiply(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "*";
    }
    
    @Override
	protected void mnemo(DecacCompiler compiler, DVal op,
			GPRegister register) {
    	compiler.addInstruction(new MUL(op, register));
    	if (getLeftOperand().getType().isFloat() || getRightOperand().getType().isFloat()) {
			compiler.addInstruction(new BOV(Label.OVERFLOW));
		}
    }

    
}
