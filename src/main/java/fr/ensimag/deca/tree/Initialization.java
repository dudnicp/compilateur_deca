package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.EnvironmentType;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.STORE;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

/**
 * @author gl28
 * @date 01/01/2020
 */
public class Initialization extends AbstractInitialization {

    public AbstractExpr getExpression() {
        return expression;
    }

    private AbstractExpr expression;

    public void setExpression(AbstractExpr expression) {
        Validate.notNull(expression);
        this.expression = expression;
    }

    public Initialization(AbstractExpr expression) {
        Validate.notNull(expression);
        this.expression = expression;
    }

    @Override
    protected void verifyInitialization(DecacCompiler compiler, Type t,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
    	expression = expression.verifyRValue(compiler, localEnv, currentClass, t);
    	if (expression.getType().isClassOrNull() && t.isClass()) {}
    	else if (!expression.getType().sameType(t) ||
    			(expression.getType().isClass() && t.isClass() && !expression.getType().getName().equals(t.getName()))) {
    		throw new ContextualError("Initialization of type " + expression.getType()
    			+ " to variable of type " + t, expression.getLocation());
    	}
    	System.out.println(expression.getType().getName() + " --- " + t.getName());
    	this.setLocation(expression.getLocation());
    }


    @Override
    public void decompile(IndentPrintStream s) {
        s.print(" = ");
        s.print(getExpression().decompile());
    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
        expression.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        expression.prettyPrint(s, prefix, true);
    }
    
    @Override
	public void codeExpr(DecacCompiler compiler, int n, DAddr addr) {
    	expression.codeAssign(compiler, n);
    	compiler.addInstruction(new STORE(Register.getR(n), addr));
    }
}
