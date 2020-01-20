package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;

import org.apache.log4j.Logger;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * Assignment, i.e. lvalue = expr.
 *
 * @author gl28
 * @date 01/01/2020
 */
public class Assign extends AbstractBinaryExpr {
    private static final Logger LOG = Logger.getLogger(Assign.class);

    @Override
    public AbstractLValue getLeftOperand() {
        // The cast succeeds by construction, as the leftOperand has been set
        // as an AbstractLValue by the constructor.
        return (AbstractLValue)super.getLeftOperand();
    }

    public Assign(AbstractLValue leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
    	// rule (3.32)
    	Identifier lValue = (Identifier)this.getLeftOperand();
    	Type lValueType = lValue.verifyExpr(compiler, localEnv, currentClass);
    	// verifyRValue returns **this** or **convfloat(this)** if needed
    	this.setRightOperand(this.getRightOperand().verifyRValue(compiler, localEnv, currentClass, lValueType));
    	this.setType(lValueType);
    	return this.getType();
    }


    @Override
    protected String getOperatorName() {
        return "=";
    }
     
    @Override
    protected void codeExpr(DecacCompiler compiler, int n) {
    	getRightOperand().codeExpr(compiler, n);
    	compiler.addInstruction(new STORE(Register.getR(n), getLeftOperand().daddr()));
    }
    
}
