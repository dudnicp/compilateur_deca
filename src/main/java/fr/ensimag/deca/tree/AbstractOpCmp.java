package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;

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
    	LOG.debug("verify OpCmpstart");
    	this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
    	this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);
    	if (!this.getLeftOperand().getType().sameType(this.getRightOperand().getType())) {
    		throw new ContextualError("Invalid comparaison operation between type "
    				+ this.getLeftOperand().getType() + " and type " + this.getRightOperand().getType(),
    				this.getLocation());	
    	}
    	this.setType(new BooleanType(compiler.getEnvTypes().getSymbolFromMap("boolean")));
    	LOG.debug("verify OpCmp end");
    	return this.getType();
    }


}
