package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;

public class ListDeclField extends TreeList<AbstractDeclField> {

	public ListDeclField() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void decompile(IndentPrintStream s) {
		// TODO Auto-generated method stub

	}
	
	 void verifyListDeclField(DecacCompiler compiler, Symbol currentClass,
	            Symbol superClass) throws ContextualError {
	    	for (AbstractDeclField f: this.getList()) {
	    		f.verifyDeclField(compiler, currentClass, superClass);
	    	}
	    }

}
