package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;

public class ListDeclMethod extends TreeList<DeclMethod>{

	@Override
	public void decompile(IndentPrintStream s) {
		for (DeclMethod m: this.getList()) {
			m.decompile(s);
			s.println();
		}
	}
	
	public void verifyListDeclMethod(DecacCompiler compiler,
			Symbol currentClass) throws ContextualError{
		for (DeclMethod m: this.getList()) {
            System.out.println("list declmethod");
			m.verifyDeclMethod(compiler, currentClass);
		}
	}

    public void verifyClassBodyListMethod(DecacCompiler compiler,
           EnvironmentExp parent, Symbol currentClass) throws ContextualError {
		for (DeclMethod m: this.getList()) {
            m.verifyClassBodyMethod(compiler, parent, currentClass);
        }
    }

}
