package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

public class DeclParam extends Tree {

	private AbstractIdentifier paramType;
	
	public DeclParam(AbstractIdentifier paramType) {
		Validate.notNull(paramType);
		this.paramType = paramType;
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
	
	public Type verifyDeclParam(DecacCompiler compiler) {
		return null;
		// synthetise la signature
	}

}
