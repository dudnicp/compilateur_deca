package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.IMAProgram;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.SUB;

/**
 * @author gl28
 * @date 01/01/2020
 */
public class Minus extends AbstractOpArith {
    public Minus(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "-";
    }
    
    @Override
	protected void mnemo(IMAProgram program, DVal op,
			GPRegister register) {
    	program.addInstruction(new SUB(op, register));
    	if (getLeftOperand().getType().isFloat() || getRightOperand().getType().isFloat()) {
    		program.addInstruction(new BOV(Label.OVERFLOW));
		}
    }

    
}
