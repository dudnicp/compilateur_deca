package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.FLOAT;
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
    	System.out.println("convfloat start");
    	this.setType(compiler.getEnvTypes().getDefinitionFromName("float").getType());
    	System.out.println("convfloat end");
    	return this.getOperand().getType();

    	}


    @Override
    protected String getOperatorName() {
        return "/* conv float */";
    }
    
    
    @Override
	protected void codeExpr(DecacCompiler compiler, int n) {
    	compiler.addInstruction(new FLOAT(getOperand().dval(), Register.getR(n)));
	}

}
