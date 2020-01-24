package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.CodeTSTO;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Environment.DoubleDefException;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.Register;

/**
 * 
 * @author gl28
 * @date 20/01/2020
 */
public class DeclField extends AbstractDeclField {
    private static final Logger LOG = Logger.getLogger(DeclField.class);
    
    final private Visibility visibility;
    final private AbstractIdentifier type;
    final private AbstractIdentifier fieldName;
    final private AbstractInitialization initialization;

    
    public DeclField(Visibility visibility, AbstractIdentifier type,
    		AbstractIdentifier fieldName, AbstractInitialization initialization) {
    	Validate.notNull(type);
    	Validate.notNull(fieldName);
    	Validate.notNull(initialization);
    	this.visibility = visibility;
    	this.type = type;
    	this.fieldName = fieldName;
    	this.initialization = initialization;
    }

	@Override
	protected void verifyDeclField(DecacCompiler compiler, 
			Symbol currentClass, Symbol superClass)
			throws ContextualError {
        LOG.debug("verifyField start");
		Type typeVerified = type.verifyType(compiler);
		if (typeVerified.isVoid()) {
			throw new ContextualError("Field type cannot be void (2.5)",
				type.getLocation());
		}
		fieldName.setType(typeVerified);
		ClassDefinition currDef = (ClassDefinition)compiler.getEnvTypes().get(currentClass);
		ClassDefinition superDef = (ClassDefinition)compiler.getEnvTypes().get(superClass);

		fieldName.setDefinition(new FieldDefinition(typeVerified, fieldName.getLocation(),
				visibility, currDef, 0));
		// TODO handle field already defined in superclass (2.5)
		try {
			currDef.getMembers().declare(fieldName.getName(), fieldName.getDefinition());
		} catch (DoubleDefException e) {
			throw new ContextualError("Field " + fieldName.getName() + " already defined",
				 fieldName.getLocation());
		}
		initialization.verifyInitialization(compiler, typeVerified, currDef.getMembers(),
				currDef);
        for (Symbol s: currDef.getMembers().getDefinitionMap().keySet()) {
            System.out.println("symbol: " + s);
        }
        LOG.debug("verifyField end");
		
	}

	@Override
	protected void verifyClassBodyField(DecacCompiler compiler,
            EnvironmentExp localEnv, Symbol currentClass) throws ContextualError {
		this.initialization.verifyInitialization(compiler, type.getType(), localEnv, (ClassDefinition)compiler.getEnvTypes().get(currentClass));
	   
     }
   
	@Override
    String prettyPrintNode() {
		return "[visibility=" + visibility + "] " 
				+  this.getClass().getSimpleName();
	}

	@Override
	public void decompile(IndentPrintStream s) {
		type.decompile(s);
		s.print(" ");
		fieldName.decompile(s);
		initialization.decompile(s);
		s.print(";");
	}

	@Override
	protected void prettyPrintChildren(PrintStream s, String prefix) {
		type.prettyPrint(s, prefix, false);
		fieldName.prettyPrint(s, prefix, false);
		initialization.prettyPrint(s, prefix, true);
	}

	@Override
	protected void iterChildren(TreeFunction f) {
		type.iter(f);
		fieldName.iter(f);
		initialization.iter(f);
	}
	
	 @Override
	    protected void codeGenDeclField(DecacCompiler compiler) {
	 }

}
