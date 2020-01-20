package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Environment.DoubleDefException;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

/**
 * 
 * @author pierrero
 * @date 20/01/2020
 */
public class DeclField extends AbstractDeclField {
    private static final Logger LOG = Logger.getLogger(DeclVar.class);
    
    final private Visibility visibility;
    final private AbstractIdentifier type;
    final private AbstractIdentifier fieldName;
    final private AbstractInitialization initialization;

    
    public DeclField(AbstractIdentifier type, AbstractIdentifier fieldName,
    		Visibility visibility, AbstractInitialization initialization) {
    	Validate.notNull(type);
    	Validate.notNull(fieldName);
    	Validate.notNull(initialization);
    	this.visibility = visibility;
    	this.type = type;
    	this.fieldName = fieldName;
    	this.initialization = initialization;
    }

	@Override
	protected void verifyDeclField(DecacCompiler compiler, ClassDefinition classDef,
			ClassDefinition superDef) throws ContextualError {
		Type typeVerified = type.verifyType(compiler);
		fieldName.setType(typeVerified);
		fieldName.setDefinition(new FieldDefinition(typeVerified, fieldName.getLocation(),
				visibility, classDef, 0));
		try {
			classDef.getMembers().declare(fieldName.getName(), fieldName.getDefinition());
		} catch (DoubleDefException e) {
			throw new ContextualError("Field " + fieldName.getName() + " already defined",
				 fieldName.getLocation());
		}
		initialization.verifyInitialization(compiler, typeVerified, classDef.getMembers(), classDef);
		
	}

	@Override
	protected void codeGenDeclField(DecacCompiler compiler) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

	}

	@Override
	protected void iterChildren(TreeFunction f) {
		// TODO Auto-generated method stub

	}

}
