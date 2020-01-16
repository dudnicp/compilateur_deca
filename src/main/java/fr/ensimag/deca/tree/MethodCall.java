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
 * Method call
 *
 * @author gl28
 * @date 11/01/2020
 */
public class MethodCall extends AbstractExpr {

    public AbstractExpr getTreeExpr() {
        return treeExpr;
    }

    public AbstractIdentifier getMethodName() {
        return methodName;
    }

    public ListExpr getArguments() {
        return arguments;
    }

    private AbstractExpr treeExpr;
    private AbstractIdentifier methodName;
    private ListExpr arguments;

    public MethodCall(AbstractExpr treeExpr, AbstractIdentifier methodName, ListExpr arguments) {
        Validate.notNull(methodName);
        Validate.notNull(arguments);
        this.treeExpr = treeExpr;
        this.methodName = methodName;
        this.arguments = arguments;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");        
    }


    @Override
    public void decompile(IndentPrintStream s){
        // A FAIRE : gérer plus proprement ça, dans les autres classes (de base) c'est pas comme ça
        throw new UnsupportedOperationException("not yet implemented"); 
    }

    @Override
    String prettyPrintNode() {
        return "MethodCall";
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }

}
