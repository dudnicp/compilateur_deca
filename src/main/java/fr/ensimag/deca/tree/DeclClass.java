package fr.ensimag.deca.tree;

import java.io.PrintStream;
import org.apache.log4j.Logger;
import java.util.HashMap;
import java.util.Map;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Environment.DoubleDefException;
import fr.ensimag.deca.context.EnvironmentType;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.tree.ListDeclMethod;

/**
 * Declaration of a class (<code>class name extends superClass {members}<code>).
 * 
 * @author gl28
 * @date 01/01/2020
 */
public class DeclClass extends AbstractDeclClass {
    private static final Logger LOG = Logger.getLogger(DeclClass.class);

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
    	EnvironmentType envTypes = compiler.getEnvTypes();
        ClassDefinition superClassDef;
 	    if (superClassName.getName().toString().equals("Object")){
            superClassDef = (ClassDefinition)envTypes.getDefinitionFromName("Object");
            superClassName.setLocation(superClassDef.getLocation());
        } else if (envTypes.get(superClassName.getName()) == null) {
            throw new ContextualError("Superclass " + superClassName.getName() + " is not defined (1.3)",
    				superClassName.getLocation());
    	} else {
            superClassDef = (ClassDefinition)envTypes.get(superClassName.getName());
        }
        superClassName.setType(superClassDef.getType());
        superClassName.setDefinition(superClassDef);
        ClassType classType;
    	try {
    		classType = new ClassType(className.getName(), className.getLocation(), superClassDef);
    		envTypes.declare(className.getName(), classType.getDefinition());
    	} catch (DoubleDefException e) {
    		throw new ContextualError("Class " + className.getName() + " is already defined (1.3)", this.getLocation());
    	}
        className.setType(classType);
        className.setDefinition(classType.getDefinition());
    }



    @Override
    protected void verifyClassMembers(DecacCompiler compiler)
            throws ContextualError {
    	fields.verifyListDeclField(compiler, this.className.getName(), this.superClassName.getName());
    	methods.verifyListDeclMethod(compiler, this.className.getName());
    }

    @Override
    protected void verifyClassBody(DecacCompiler compiler) throws ContextualError {
    	ClassDefinition classDef = (ClassDefinition)compiler.getEnvTypes().get(
    			this.className.getName());
    	EnvironmentExp localEnv = new EnvironmentExp(classDef.getMembers());
    	this.fields.verifyClassBodyListField(compiler, localEnv, className.getName());
    	this.methods.verifyClassBodyListMethod(compiler, localEnv, className.getName());
    }


    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        //this.className.prettyPrintNode();
    	this.className.prettyPrint(s, prefix, false);
        //this.className.prettyPrintType(s, prefix);
    	this.superClassName.prettyPrint(s, prefix, false);
        //this.superClassName.prettyPrintType(s, prefix);
    	this.fields.prettyPrint(s, prefix, false);
    	this.methods.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
    	className.iterChildren(f);
    	if (superClassName != null) superClassName.iterChildren(f);
    	fields.iterChildren(f);
    	methods.iterChildren(f);
    }

}
