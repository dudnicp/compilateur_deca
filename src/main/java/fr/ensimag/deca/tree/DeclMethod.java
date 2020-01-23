package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.Environment.DoubleDefException;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;

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
			Symbol currentClass) throws ContextualError {
        // TODO: dont throw exception when type is void
		Type verifiedType = type.verifyType(compiler);
		Signature sig = listDeclParam.verifyListDeclParam(compiler);
		// TODO verifier la redifinition d'une methode
		// ie meme returnType et signature
		ClassDefinition classDef = (ClassDefinition)compiler.getEnvTypes().get(currentClass);
		// TODO increment number of methods
        Definition methodDef = new MethodDefinition(verifiedType,
        		this.getLocation(), sig, 0);
		try {
            classDef.getMembers().declare(methodName.getName(), methodDef);
		} catch (DoubleDefException e) {
			e.printStackTrace();
		}
        this.methodName.setDefinition(methodDef);
	}
	
	public void verifyClassBodyMethod(DecacCompiler compiler, 
			EnvironmentExp parent, Symbol currentClass) throws ContextualError {
		EnvironmentExp envExpParam = new EnvironmentExp(parent);
		listDeclParam.verifyClassBodyListDeclParam(compiler,
				envExpParam);
		methodBody.verifyClassMethodBody(compiler, envExpParam, currentClass, type.getType());
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
