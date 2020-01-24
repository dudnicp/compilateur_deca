package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.IMAProgram;

public class ListDeclField extends TreeList<AbstractDeclField> {

	public ListDeclField() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void decompile(IndentPrintStream s) {
		for (AbstractDeclField f: this.getList()) {
			f.decompile(s);
			s.println();
    	}
	}
	
	 public void verifyListDeclField(DecacCompiler compiler, Symbol currentClass,
	            Symbol superClass) throws ContextualError {
	    	for (AbstractDeclField f: this.getList()) {
	    		f.verifyDeclField(compiler, currentClass, superClass);
	    	}
	    }
	 
	 public void codeGenDefaultInit(IMAProgram program, GPRegister register, RegisterManager registerManager) {
		for (AbstractDeclField field : getList()) {
			field.codeGenDefaultInit(program, register, registerManager);
		}
	 }
	 
	 public void codeGenProperInit(IMAProgram program, GPRegister register, RegisterManager registerManager) {
		for (AbstractDeclField field : getList()) {
				field.codeGenProperInit(program, register, registerManager);
		}
	}
	 
    public void verifyClassBodyListField(DecacCompiler compiler,
            EnvironmentExp localEnv, Symbol currentClass) throws ContextualError {
 		for (AbstractDeclField f: this.getList()) {
             f.verifyClassBodyField(compiler, localEnv, currentClass);
         }
     }
	 
}
