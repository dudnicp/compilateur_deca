package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;

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
    	Identifier lValue = (Identifier)this.getLeftOperand();
    	Type lValueType = lValue.verifyExpr(compiler, localEnv, currentClass);
    	this.getRightOperand().verifyRValue(compiler, localEnv, currentClass, lValueType);
    	LOG.debug(this.getRightOperand());
    	return this.getLeftOperand().getType();
    }


    @Override
    protected String getOperatorName() {
        return "=";
    }

	@Override
	protected void codeGenInst(DecacCompiler compiler, DVal op,
			GPRegister register) {
		// TODO Auto-generated method stub
		
	}

}
