package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

public class Return extends AbstractInst {
	
	AbstractExpr rvalue;
	
	public AbstractExpr getRvalue() {
		return this.rvalue;
	}
	
	public Return(AbstractExpr rvalue) {
		Validate.notNull(rvalue);
		this.rvalue = rvalue;
	}

	@Override
	protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass,
			Type returnType) throws ContextualError {
		if (returnType.isVoid()) {
			throw new ContextualError("Return value cannot be of type void (3.24)",
					this.getLocation());
		}
	}

	@Override
	protected void codeGenInst(DecacCompiler compiler) {
		// TODO Auto-generated method stub

	}

	@Override
	public void decompile(IndentPrintStream s) {
        s.print("return ");
        getRvalue().decompile(s);
        s.print(";");
	}

	@Override
	protected void prettyPrintChildren(PrintStream s, String prefix) {
		rvalue.prettyPrint(s, prefix, true);
	}

	@Override
	protected void iterChildren(TreeFunction f) {
		rvalue.iter(f);
	}

}
