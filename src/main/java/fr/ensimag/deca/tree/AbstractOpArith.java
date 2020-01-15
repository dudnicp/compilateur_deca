package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;

import org.apache.log4j.Logger;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * Arithmetic binary operations (+, -, /, ...)
 * 
 * @author gl28
 * @date 01/01/2020
 */
public abstract class AbstractOpArith extends AbstractBinaryExpr {
    private static final Logger LOG = Logger.getLogger(AbstractOpArith.class);

    public AbstractOpArith(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
    	//Identifier lValue = (Identifier)this.getLeftOperand();
    	//Type lValueType = localEnv.get(lValue.getName()).getType();
    	//this.getRightOperand().verifyRValue(compiler, localEnv, currentClass, lValueType);
    	LOG.debug("verify OpArith start");
    	this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
    	this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);
    	if (!this.getLeftOperand().getType().sameType(this.getRightOperand().getType())) {
    		throw new ContextualError("Invalid arithmetic operation between type "
    				+ this.getLeftOperand().getType() + " and type " + this.getRightOperand().getType(),
    				this.getLocation());
    	}
    	this.setType(this.getLeftOperand().getType());
    	LOG.debug("verify OpArith end");
    	return this.getType();
    }
}
