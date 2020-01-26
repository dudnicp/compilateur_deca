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

public abstract class AbstractMethodBody extends Tree {

	public AbstractMethodBody() {
	}
	
	public abstract void verifyClassMethodBody(DecacCompiler compiler,
			EnvironmentExp envExpParam, ClassDefinition currentClass,
			Type returnType) throws ContextualError;

	@Override
	public void decompile(IndentPrintStream s) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void prettyPrintChildren(PrintStream s, String prefix) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void iterChildren(TreeFunction f) {
		// TODO Auto-generated method stub

	}
	
	protected abstract void verifyMethodBody(DecacCompiler compiler,
			Symbol currentClass);

	public abstract void codeGen(IMAProgram program, RegisterManager registerManager, String className, String methodName);
}
