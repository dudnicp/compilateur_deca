package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

public class ListDeclParam extends TreeList<DeclParam> {

	@Override
	public void decompile(IndentPrintStream s) {
		// TODO Auto-generated method stub

	}
	
	public Signature verifyListDeclParam(DecacCompiler compiler) throws ContextualError {
        System.out.println("verify listdeclparam start");
		Signature sig = new Signature();
		Type paramType;
		for (DeclParam p: this.getList()) {
			paramType = p.verifyDeclParam(compiler);
			sig.add(paramType);
		}
        System.out.println("verify listdeclparam end");
		return sig;
	}

}
