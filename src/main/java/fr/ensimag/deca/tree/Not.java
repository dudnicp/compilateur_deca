package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 *
 * @author gl28
 * @date 01/01/2020
 */
public class Not extends AbstractUnaryExpr {

    public Not(AbstractExpr operand) {
        super(operand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
    	this.getOperand().verifyExpr(compiler, localEnv, currentClass);
    	if (!this.getOperand().getType().isBoolean()) {
    		throw new ContextualError("Invalid type for operand: " 
    				+ this.getOperand().getType() + " instead of boolean",
    				this.getOperand().getLocation());
    	}
    	this.setType(this.getOperand().getType());
    	return this.getType();
    }


    @Override
    protected String getOperatorName() {
        return "!";
    }
}
