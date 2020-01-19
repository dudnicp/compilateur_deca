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
    	Type type1 = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
    	Type type2 = this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);
    	if (type1.isInt() && type2.isFloat()) {
    		ConvFloat leftConv = new ConvFloat(this.getLeftOperand());
    		leftConv.verifyExpr(compiler, localEnv, currentClass);
    		this.setLeftOperand(leftConv);
    		this.setType(type2);
    	} else if (type1.isFloat() && type2.isInt()) {
    		ConvFloat rightConv = new ConvFloat(this.getRightOperand());
    		rightConv.verifyExpr(compiler, localEnv, currentClass);
    		this.setRightOperand(rightConv);
    		this.setType(type1);
    	} else if ((type1.isInt() && type2.isInt()) ||
    			(type1.isFloat() && type2.isFloat())){
    		this.setType(type1);
    		// no conversion needed
    	} else {
    		throw new ContextualError("Arithmetic operation \"" + this.getOperatorName() +  "\" is not supported for types "
    				+ this.getLeftOperand().getType() + " and " + this.getRightOperand().getType() + " (3.33)",
    				this.getLocation());
    	}
    	return this.getType();
    	/*
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
    	return this.getType();
    	*/
    }
}
