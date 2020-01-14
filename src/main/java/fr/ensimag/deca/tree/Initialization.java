package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.EnvironmentType;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

/**
 * @author gl28
 * @date 01/01/2020
 */
public class Initialization extends AbstractInitialization {
    private static final Logger LOG = Logger.getLogger(Initialization.class);

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
    	// verify that variable and initialization types match
    	LOG.debug("verifyInitialization start");
    	EnvironmentType evnTypes = compiler.getEnvTypes();
    	expression.setType(t);
    	// assign expression to type t
    	// A FAIRE : handle compatible types conversion
    	try {
    		IntLiteral expr = (IntLiteral)this.expression;
    		LOG.debug(expr.getType());
        	LOG.debug(expr.getValue());

    	} catch (ClassCastException e) {}
    	try {
    		StringLiteral expr = (StringLiteral)this.expression;
    		LOG.debug(expr.getType());
        	LOG.debug(expr.getValue());
        	LOG.debug(expr.getClass());
    		//expression.setType(evnTypes.get(evnTypes.getSymbol(expr.name.toString())).getType());

    	} catch (ClassCastException e) {}
    	try {
    		FloatLiteral expr = (FloatLiteral)this.expression;
        	LOG.debug(expr.getValue());

    	} catch (ClassCastException e) {}
    	try {
    		BooleanLiteral expr = (BooleanLiteral)this.expression;
        	LOG.debug(expr.getValue());

    	} catch (ClassCastException e) {}
    }


    @Override
    public void decompile(IndentPrintStream s) {
        throw new UnsupportedOperationException("Not yet implemented");
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
}
