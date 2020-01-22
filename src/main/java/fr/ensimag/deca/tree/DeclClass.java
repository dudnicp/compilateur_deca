package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;

/**
 * Declaration of a class (<code>class name extends superClass {members}<code>).
 * 
 * @author gl28
 * @date 01/01/2020
 */
public class DeclClass extends AbstractDeclClass {
	private AbstractIdentifier className;
	private AbstractIdentifier superClassName;
	private ListDeclField fields;
	private ListDeclMethod methods;
	private Symbol classSymbol;
	
	public DeclClass(AbstractIdentifier className, AbstractIdentifier superClassName,
			ListDeclField fields, ListDeclMethod methods) {
		this.className = className;
		this.superClassName = superClassName;			
		this.fields = fields;
		this.methods = methods;
	}
    @Override
    public void decompile(IndentPrintStream s) {
        s.print("class ");
        this.className.decompile(s);
        if (this.superClassName != null) {
        	s.print(" extends ");
        	this.superClassName.decompile(s);
        }
        s.print(" {");
        s.println();
        s.indent();
        fields.decompile(s);
        s.println();
        methods.decompile(s);
        s.println();
        s.unindent();
        s.print("}");
    }

    @Override
    protected void verifyClass(DecacCompiler compiler) throws ContextualError {
    	/*
    	EnvironmentType envTypes = compiler.getEnvTypes();
    	if (supperClassName == null) {
    	System.out.println("DeclClass.java : superCLass null");
    	} else if (envTypes.getDefinitionFromName(this.superClasseName.getName().toString()) == null) {
    		throw new ContextualError("Superclass " + superClassName.getName() + " is not defined (1.3)",
    			superClassName.getLocation())
    	}
    	try {
    		this.classSymbol = envTypes.createSymbol(className);
    		ClassType classType = new ClassType(this.classSymbol, name.getLocation(), superclass);
    		envTypes.declare(this.classSymbol, classType.getDefinition());
    	} catch (DoubleDefException e) {
    		throw new ContextualError("Class " + className.getName() + " is already defined (1.3)", this.getLocation());
    	}
    	*/
    }

    @Override
    protected void verifyClassMembers(DecacCompiler compiler)
            throws ContextualError {
    	/*
    	fields.verifyListDeclField(compiler, this.classSymbol, this.superClassSymbol);
    	methods.verifyListDeclMethod(compiler, this.classSymbol);
    	*/
    }
    
    @Override
    protected void verifyClassBody(DecacCompiler compiler) throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");
    }


    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
    	this.className.prettyPrint(s, prefix, false);
    	this.superClassName.prettyPrint(s, prefix, false);
    	this.fields.prettyPrintChildren(s, prefix);
    	this.methods.prettyPrintChildren(s, prefix);
    	
    }

    @Override
    protected void iterChildren(TreeFunction f) {
    	className.iterChildren(f);
    	if (superClassName != null) superClassName.iterChildren(f);
    	fields.iterChildren(f);
    	methods.iterChildren(f);
    }

}
