package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

public class New extends AbstractExpr {
	private AbstractIdentifier newName;
	
	public New(AbstractIdentifier newName) {
		this.newName = newName;
	}
	@Override
	public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
			throws ContextualError {
		ClassType newType = compiler.getEnvTypes().get(newName.getName()).getType().asClassType("not a class type", newName.getLocation());
		this.setType(newType);
		newName.setDefinition(compiler.getEnvTypes().get(newName.getName()));
		return newType;
	}

	@Override
	public void decompile(IndentPrintStream s) {
		s.print("new ");
		newName.decompile(s);
	}

	@Override
	protected void prettyPrintChildren(PrintStream s, String prefix) {
		newName.prettyPrint(s, prefix, true);
	}

	@Override
	protected void iterChildren(TreeFunction f) {
		newName.iterChildren(f);
	}

}
