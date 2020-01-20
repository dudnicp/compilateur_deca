package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.context.Signature;
import fr.ensimag.deca.tools.IndentPrintStream;

public class DeclMethod extends Tree {
	private Signature signature;
	private AbstractIdentifier type;
	private AbstractIdentifier methodName;
	
	public DeclMethod(AbstractIdentifier type, AbstractIdentifier methodName,
			Signature signature) {
		this.type = type;
		this.methodName = methodName;
		this.signature = signature;
	}
	
	
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

}
