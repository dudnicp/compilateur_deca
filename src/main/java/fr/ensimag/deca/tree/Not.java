package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Operand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.ADD;
import fr.ensimag.ima.pseudocode.instructions.OPP;
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
    				+ this.getOperand().getType() + " instead of boolean (3.37)",
    				this.getOperand().getLocation());
    	}
    	this.setType(this.getOperand().getType());
    	return this.getType();
    }


    @Override
    protected String getOperatorName() {
        return "!";
    }
    
    @Override
	protected void codeCond(DecacCompiler compiler, boolean b, Label label) {
    	getOperand().codeCond(compiler, !b, label);
	}
    
    @Override
	protected void codeExpr(DecacCompiler compiler, int n) {
    	getOperand().codeExpr(compiler, n);
    	compiler.addInstruction(new OPP(Register.getR(n), Register.getR(n)));
    	compiler.addInstruction(new ADD(new ImmediateInteger(1), Register.getR(n)));
    	
	}
    
}
