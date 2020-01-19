package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.DIV;
import fr.ensimag.ima.pseudocode.instructions.QUO;

/**
 *
 * @author gl28
 * @date 01/01/2020
 */
public class Divide extends AbstractOpArith {
    public Divide(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "/";
    }

    @Override
	protected void mnemo(DecacCompiler compiler, DVal op,
			GPRegister register) {
    	if (getLeftOperand().getType().isInt()) {
			compiler.addInstruction(new QUO(op, register));
		}
    	else if (getLeftOperand().getType().isFloat()) {
    		compiler.addInstruction(new DIV(op, register));
		}
    	compiler.addInstruction(new BOV(Label.DIVBYZERO));
    }
    
}
