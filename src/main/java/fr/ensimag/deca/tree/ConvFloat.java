package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.ImmediateFloat;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.FLOAT;
import fr.ensimag.ima.pseudocode.instructions.WFLOAT;
import fr.ensimag.ima.pseudocode.instructions.WFLOATX;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * Conversion of an int into a float. Used for implicit conversions.
 * 
 * @author gl28
 * @date 01/01/2020
 */
public class ConvFloat extends AbstractUnaryExpr {
    public ConvFloat(AbstractExpr operand) {
        super(operand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) {
    	// set type := float decoration on **operand**
    	this.setType(compiler.getEnvTypes().getDefinitionFromName("float").getType());
    	return this.getType();

    	}


    @Override
    protected String getOperatorName() {
        return "/* conv float */";
    }
    
    
    @Override
	protected void codeExpr(DecacCompiler compiler, int n) {
    	getOperand().codeExpr(compiler, n);
    	compiler.addInstruction(new FLOAT(Register.getR(n), Register.getR(n)));
	}
    
    @Override
    protected void codeGenPrintInstruction(DecacCompiler compiler) {
    	compiler.addInstruction(new WFLOAT());
    }
    
     @Override
    protected void codeCMP(DecacCompiler compiler, int n) {
    	 compiler.addInstruction(new CMP(new ImmediateFloat(0.0f), Register.getR(n)));
    }
}
