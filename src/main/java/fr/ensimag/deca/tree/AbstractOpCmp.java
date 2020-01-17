package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.instructions.CMP;

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
    	this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
    	this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);
    	if (!this.getLeftOperand().getType().sameType(this.getRightOperand().getType())) {
    		throw new ContextualError("Invalid comparaison operation between type "
    				+ this.getLeftOperand().getType() + " and type " + this.getRightOperand().getType(),
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
