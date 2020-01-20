package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;

/**
 * Declaration of a class (<code>class name extends superClass {members}<code>).
 * 
 * @author gl28
 * @date 01/01/2020
 */
public class DeclClass extends AbstractDeclClass {

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("class { ... A FAIRE ... }");
    }

    @Override
    protected void verifyClass(DecacCompiler compiler) throws ContextualError {
    	/*
    	EnvironmentType envTypes = compiler.getEnvTypes();
    	try {
    		Symbol classSymbol = envTypes.createSymbol(name);
    		if (envTypes.getDefinitionFromName(superclass) == null) {
    			throw new ContextualError("Superclass " + superclass + " is not defined", this.getLocation());
    		}
    		ClassType classType = new ClassType(classSymbol, name.getLocation(), superclass);
    		envTypes.declare(classSymbol, classType.getDefinition());
    	} catch (DoubleDefException e) {
    		throw new ContextualError("Class " + name + " is already defined", this.getLocation());
    	}
    	*/
    }

    @Override
    protected void verifyClassMembers(DecacCompiler compiler)
            throws ContextualError {
    	/*
    	for (AbstractDeclVar field: getFields()) {
    		field.verifyDeclField(compiler, name, superClass);
    	}
    	*/
    }
    
    @Override
    protected void verifyClassBody(DecacCompiler compiler) throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");
    }


    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        throw new UnsupportedOperationException("Not yet supported");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        throw new UnsupportedOperationException("Not yet supported");
    }

}
