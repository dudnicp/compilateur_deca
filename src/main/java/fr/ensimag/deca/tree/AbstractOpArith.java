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
    	Type lType = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
    	Type rType = this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);
    	
    	// handle conversion to float
    	if (this.getLeftOperand().getType().isFloat() && this.getRightOperand().getType().isInt()) {
        	this.setRightOperand(this.getRightOperand().verifyRValue(compiler, localEnv, currentClass, lType));
    	} else if (this.getLeftOperand().getType().isInt() && this.getRightOperand().getType().isFloat()) {
        	this.setLeftOperand(this.getLeftOperand().verifyRValue(compiler, localEnv, currentClass, rType));
        
        // else if types are not compatible
    	} else if (!this.getLeftOperand().getType().sameType(this.getRightOperand().getType())) {
    		throw new ContextualError("Arithmetic operation \"" + this.getOperatorName() +  "\" is invalid between type "
    				+ this.getLeftOperand().getType() + " and type " + this.getRightOperand().getType(),
    				this.getLocation());
    	}
    	this.setType(this.getLeftOperand().getType());
    	LOG.debug("verify OpArith end");
    	return this.getType();
    }
}
