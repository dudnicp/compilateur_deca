package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;

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
    	ClassType objectType = treeExpr.verifyExpr(compiler, localEnv, currentClass).asClassType("not a classtype", treeExpr.getLocation());
    	EnvironmentExp e = objectType.getDefinition().getMembers();
    	Definition def = objectType.getDefinition().getMembers().get(methodName.getName());
    	if (def == null) {
    		throw new ContextualError("Method " + methodName.getName() + " is not defined in class " + objectType.getName(),
    				methodName.getLocation());
    	}
    	MethodDefinition methodDef = def.asMethodDefinition("not a method defintion", this.getLocation());
    	// verify that the object's class is the same as the method's class
    	Signature sig = methodDef.getSignature();
    	Signature sig2 = new Signature();
    	arguments.verifySignature(compiler, localEnv, currentClass, sig2);
    	// verify that signatures match
    	if (!sig.equals(sig2)) {
    		throw new ContextualError("Signature of called method " + methodName.getName() + " does not match its definition (3.71)",
    				methodName.getLocation());
    	}
    	// returnType of the method called
    	this.setType(methodDef.getType());
    	methodName.setDefinition(methodDef);
    	return methodDef.getType();
    }


    @Override
    public void decompile(IndentPrintStream s){
        // A FAIRE : gérer plus proprement ça, dans les autres classes (de base) c'est pas comme ça
    	treeExpr.decompile(s);
    	methodName.decompile(s);
    	arguments.decompile(s);
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
        treeExpr.prettyPrint(s, prefix, false);
        methodName.prettyPrint(s, prefix, false);
        arguments.prettyPrint(s, prefix, true);
    }

}
