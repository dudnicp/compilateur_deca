package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.instructions.ADD;

/**
 * @author gl28
 * @date 01/01/2020
 */
public class Plus extends AbstractOpArith {
    public Plus(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }
 

    @Override
    protected String getOperatorName() {
        return "+";
    }
    
    @Override
	protected void mnemo(DecacCompiler compiler, DVal op,
			GPRegister register) {
    	compiler.addInstruction(new ADD(op, register));
	}
    
}
