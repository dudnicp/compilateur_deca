package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

public class DeclParam extends AbstractDeclParam {

	private AbstractIdentifier paramType;
	private AbstractIdentifier paramName;
	
	public DeclParam(AbstractIdentifier paramType, AbstractIdentifier paramName) {
		Validate.notNull(paramType);
		Validate.notNull(paramName);
		this.paramType = paramType;
		this.paramName = paramName;
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
	
	public Type verifyDeclParam(DecacCompiler compiler) throws ContextualError {
		Type verifiedType = paramType.verifyType(compiler);
		return verifiedType;
	}

}
