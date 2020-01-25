package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;

public class MethodAsmBody extends AbstractMethodBody {
	private StringLiteral asm;
	
	public MethodAsmBody(StringLiteral asm) {
		this.asm = asm;
	}
	
	@Override
	public void decompile(IndentPrintStream s) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void prettyPrintChildren(PrintStream s, String prefix) {
		asm.prettyPrint(s, prefix, true);

	}

	@Override
	protected void iterChildren(TreeFunction f) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void verifyMethodBody(DecacCompiler compiler, Symbol currentClass) {
		// TODO verify asm
		
	}

	@Override
	public void verifyClassMethodBody(DecacCompiler compiler, EnvironmentExp envExpParam, Symbol currentClass,
			Type returnType) throws ContextualError {
		// TODO Auto-generated method stub
		
	}

}
