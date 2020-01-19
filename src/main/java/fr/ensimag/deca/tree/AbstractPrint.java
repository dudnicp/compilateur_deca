package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * Print statement (print, println, ...).
 *
 * @author gl28
 * @date 01/01/2020
 */
public abstract class AbstractPrint extends AbstractInst {

    private boolean printHex;
    private ListExpr arguments = new ListExpr();
    
    abstract String getSuffix();

    public AbstractPrint(boolean printHex, ListExpr arguments) {
        Validate.notNull(arguments);
        this.arguments = arguments;
        this.printHex = printHex;
    }

    public ListExpr getArguments() {
        return arguments;
    }

    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {
        // rule (3.31)
        for (AbstractExpr a : getArguments().getList()) {
        	// rule (3.30)
            Type type = a.verifyExpr(compiler, localEnv, currentClass);
            if (this.getPrintHex()) {
            	if (!(type.isFloat() || type.isInt())) {
            		throw new ContextualError("Arguments type of 'printx' must be int or float",
            				this.getLocation());
            	}
            } else {
            	if (!(type.isFloat() || type.isString() || type.isInt())) {
            		throw new ContextualError("Arguments type must be String, int or float (3.31)", a.getLocation());
            	}
        	}
        }
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
    	if (printHex) {
            for (AbstractExpr a : getArguments().getList()) {
                a.codeGenPrintHex(compiler);
            }
		}
    	else {
    		for (AbstractExpr a : getArguments().getList()) {
                a.codeGenPrint(compiler);
            }
		}
    }

    private boolean getPrintHex() {
        return printHex;
    }

    @Override
    public void decompile(IndentPrintStream s) {
    	s.print("print");
    	s.print(this.getSuffix());
    	s.print("(");
    	arguments.decompile(s);
    	s.print(");");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        arguments.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        arguments.prettyPrint(s, prefix, true);
    }

}
