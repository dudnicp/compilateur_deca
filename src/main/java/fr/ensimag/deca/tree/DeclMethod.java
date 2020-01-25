package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.tree.ListDeclParam;

public class DeclMethod extends Tree {
	private AbstractIdentifier type;
	private AbstractIdentifier methodName;
	private ListDeclParam listDeclParam;
	private AbstractMethodBody methodBody;
	
	public DeclMethod(AbstractIdentifier type, AbstractIdentifier methodName,
			ListDeclParam listDeclParam, AbstractMethodBody methodBody) {
		this.type = type;
		this.methodName = methodName;
		this.listDeclParam = listDeclParam;
		this.methodBody = methodBody;
	}
	
	public void verifyDeclMethod(DecacCompiler compiler,
			ClassDefinition currentClass) throws ContextualError {
    	// return type decoration 
		EnvironmentExp envTypes = compiler.getEnvTypes();
    	Definition def = envTypes.getDefinitionFromName(type.getName().toString()); // predefined types
    	Type verifiedType;
    	if (def == null) def = envTypes.get(type.getName()); // classes type
    	if (def == null) {
    		throw new ContextualError("Type " + type.getName() + " is not defined (0.2)",
    				this.getLocation());
    	} else {
        	verifiedType = def.getType();
        	type.setDefinition(def);
    	}
    	// determine whether field is already defined in this class - in one of its parents - or not at all
		Signature sig = listDeclParam.verifyListDeclParam(compiler);
		MethodDefinition incMethodDef;
		
		if (currentClass.getMembers().get(methodName.getName()) != null) {
			throw new ContextualError("Field or method " + methodName.getName() + " is already defined in class " + currentClass.toString(),
					methodName.getLocation());
		} else if (currentClass.getMembers().getAny(methodName.getName()) != null) {
			Definition previousDef = currentClass.getSuperClass().getMembers().getAny(methodName.getName());
			MethodDefinition previousMethodDef = previousDef.asMethodDefinition("Method " + methodName.getName()
			+ " should redefine another method", methodName.getLocation());
			if (!sig.equals(previousMethodDef.getSignature())) {
                throw new ContextualError("Wrong signature for redifinition of method " + methodName.getName(),
                        methodName.getLocation());
			} else {
				incMethodDef = new MethodDefinition(verifiedType,
		        		this.getLocation(), sig, previousMethodDef.getIndex());
			}
		} else {
			incMethodDef = new MethodDefinition(verifiedType, methodName.getLocation(),
					sig, currentClass.getNumberOfFields());
		}
		// decorate method
		methodName.setDefinition(incMethodDef);
		try {
			currentClass.getMembers().declare(methodName.getName(), methodName.getDefinition());
		} catch (DoubleDefException e) {
			// this never happens since we verified previously
			e.printStackTrace();
		}
    	
    	
    }
	
	public void verifyClassBodyMethod(DecacCompiler compiler, 
			EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
		EnvironmentExp envExpParam = new EnvironmentExp(localEnv);
		listDeclParam.verifyClassBodyListDeclParam(compiler,
				envExpParam);
		methodBody.verifyClassMethodBody(compiler, envExpParam, currentClass, type.getDefinition().getType());
	}
	
	public AbstractIdentifier getMethodName() {
		return methodName;
	}
	
	
	@Override
	public void decompile(IndentPrintStream s) {
		type.decompile(s);
		s.print(" ");
		methodName.decompile(s);
		s.print("(TODO decompile param) {}");
	}

	@Override
	protected void prettyPrintChildren(PrintStream s, String prefix) {
		type.prettyPrint(s, prefix, false);
		methodName.prettyPrint(s, prefix, false);
		listDeclParam.prettyPrint(s, prefix, false);
		methodBody.prettyPrint(s, prefix, true);
	}

	@Override
	protected void iterChildren(TreeFunction f) {
		type.iter(f);
		methodName.iter(f);
		listDeclParam.iterChildren(f);
	}

}
