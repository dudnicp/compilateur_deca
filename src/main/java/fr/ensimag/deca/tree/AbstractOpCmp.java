package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.PUSH;

import org.apache.log4j.Logger;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.BooleanType;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 *
 * @author gl28
 * @date 01/01/2020
 */
public abstract class AbstractOpCmp extends AbstractBinaryExpr {
    private static final Logger LOG = Logger.getLogger(AbstractOpCmp.class);

    public AbstractOpCmp(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
    	Type type1 = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
    	Type type2 = this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);
    	if (type1.isInt() && type2.isFloat()) {
    		ConvFloat leftConv = new ConvFloat(this.getLeftOperand());
    		this.setLeftOperand(leftConv);
    		this.setType(type2);
    	} else if (type1.isFloat() && type2.isInt()) {
    		ConvFloat rightConv = new ConvFloat(this.getRightOperand());
    		this.setRightOperand(rightConv);
    		this.setType(type1);
    	} else {
    		throw new ContextualError("Comparison operation \"" + this.getOperatorName() +  "\" is not supported for types "
    				+ this.getLeftOperand().getType() + " and " + this.getRightOperand().getType() + " (3.33)",
    				this.getLocation());
    	}
    	this.setType(compiler.getEnvTypes().getDefinitionFromName("boolean").getType());
    	return this.getType();
    }
    
    @Override
    protected void mnemo(DecacCompiler compiler, DVal op, GPRegister register) {
    	compiler.addInstruction(new CMP(op, register));
    }
    
}
