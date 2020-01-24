package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.ima.pseudocode.IMAProgram;

public class MethodBody extends AbstractMethodBody {
	private ListDeclVar listDeclVar;
	private ListInst listInst;
	
	public MethodBody(ListDeclVar listDeclVar, ListInst listDeclInst) {
		this.listDeclVar = listDeclVar;
		this.listInst = listDeclInst;
	}
	
	public void verifyClassMethodBody(DecacCompiler compiler,
			EnvironmentExp envExpParam, Symbol currentClass,
			Type returnType) throws ContextualError {
		ClassDefinition classDef = (ClassDefinition)compiler.getEnvTypes().get(currentClass);
		listDeclVar.verifyListDeclVariable(compiler, envExpParam,
				classDef);
		listInst.verifyListInst(compiler, envExpParam, classDef, returnType);

	}
	
	
	@Override
	public void decompile(IndentPrintStream s) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void prettyPrintChildren(PrintStream s, String prefix) {
		listDeclVar.prettyPrint(s, prefix, false);
		listInst.prettyPrint(s, prefix, true);
	}

	@Override
	protected void iterChildren(TreeFunction f) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void verifyMethodBody(DecacCompiler compiler, Symbol currentClass) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void codeGen(IMAProgram program, RegisterManager registerManager, String className, String methodName) {
		listDeclVar.codeGenDecl(program, registerManager);
		listInst.codeGenListInstInMethod(program, registerManager, className, methodName);
	}
}
