package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DAddr;

public class Selection extends AbstractLValue {
	private AbstractExpr objectName;
	private AbstractIdentifier fieldName;
	
	public Selection(AbstractExpr objetName, AbstractIdentifier fieldName) {
		this.objectName = objectName;
		this.fieldName = fieldName; 
	}
	
	@Override
	protected DAddr daddr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
			throws ContextualError {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void decompile(IndentPrintStream s) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void prettyPrintChildren(PrintStream s, String prefix) {
		objetName.prettyPrint(s, prefix, false);
        fieldName.prettyPrint(s, prefix, true);

	}

	@Override
	protected void iterChildren(TreeFunction f) {
		// TODO Auto-generated method stub

	}

}
