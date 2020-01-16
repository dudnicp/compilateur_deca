package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;

import org.apache.log4j.Logger;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 *
 * @author gl28
 * @date 01/01/2020
 */
public abstract class AbstractOpBool extends AbstractBinaryExpr {
    private static final Logger LOG = Logger.getLogger(AbstractOpBool.class);

    public AbstractOpBool(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
    	this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
    	this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);
    	if (!this.getLeftOperand().getType().isBoolean()) {
    		throw new ContextualError("Invalid type for left operand: " 
    				+ this.getLeftOperand().getType() + " instead of boolean",
    				this.getLeftOperand().getLocation());
    	} else if (!this.getRightOperand().getType().isBoolean()) {
    		throw new ContextualError("Invalid type for right operand: " 
    				+ this.getRightOperand().getType() + " instead of boolean",
    				this.getRightOperand().getLocation());
    	}
    	this.setType(this.getLeftOperand().getType());
    	return this.getType();
    }

}
