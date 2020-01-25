package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Environment.DoubleDefException;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.IMAProgram;
import fr.ensimag.ima.pseudocode.ImmediateFloat;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;

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
		// decorate type
		Type typeVerified = type.verifyType(compiler);
		if (typeVerified.isVoid()) {
			throw new ContextualError("Field type cannot be void (2.5)",
				type.getLocation());
		}
		
		// determine whether field is already defined in this class - in one of its parents - or not at all
		fieldName.setType(typeVerified); // TODO: optional
		ClassDefinition currDef = (ClassDefinition)compiler.getEnvTypes().get(currentClass);
		FieldDefinition incFieldDef;
		
		if (currDef.getMembers().get(fieldName.getName()) != null) {
			throw new ContextualError("Field or method " + fieldName.getName() + " is already defined in class " + currentClass.getName(),
					fieldName.getLocation());
		} else if (currDef.getMembers().getAny(fieldName.getName()) != null) {
			Definition previousDef = currDef.getSuperClass().getMembers().getAny(fieldName.getName());
			FieldDefinition previousFieldDef = previousDef.asFieldDefinition("Field " + fieldName.getName()
			+ " should redefine another field", fieldName.getLocation());
			incFieldDef = new FieldDefinition(typeVerified, fieldName.getLocation(),
					visibility, currDef, previousFieldDef.getIndex());
		} else {
			incFieldDef = new FieldDefinition(typeVerified, fieldName.getLocation(),
					visibility, currDef, currDef.getNumberOfFields());
		}
		// decorate field
		fieldName.setDefinition(incFieldDef);
		try {
			currDef.getMembers().declare(fieldName.getName(), fieldName.getDefinition());
		} catch (DoubleDefException e) {
			// this never happens since we verified previously
			e.printStackTrace();
		}
		
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
	protected void codeGenProperInit(IMAProgram program, GPRegister register, RegisterManager registerManager) {
    	initialization.codeExpr(program, Register.defaultRegisterIndex, registerManager);
    	int index = fieldName.getFieldDefinition().getIndex();
    	program.addInstruction(new STORE(Register.getDefaultRegister(), new RegisterOffset(index, register)));
	}
	
	@Override
	protected void codeGenDefaultInit(IMAProgram program, GPRegister register, RegisterManager registerManager) {
    	if (type.getType().isFloat()) {
    		program.addInstruction(new LOAD(new ImmediateFloat(0.0f), Register.getDefaultRegister()));
    		
		} else if (type.getType().isClassOrNull()) {
			program.addInstruction(new LOAD(new NullOperand(), Register.getDefaultRegister()));
		} else {
			program.addInstruction(new LOAD(new ImmediateInteger(0), Register.getDefaultRegister()));
		}
	}
}
