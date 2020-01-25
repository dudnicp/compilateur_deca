package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.IMAProgram;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

public class Selection extends AbstractLValue {
	private AbstractExpr objectName;
	private AbstractIdentifier fieldName;
	
	public Selection(AbstractExpr objectName, AbstractIdentifier fieldName) {
		Validate.notNull(objectName);
		Validate.notNull(fieldName);
		this.objectName = objectName;
		this.fieldName = fieldName; 
	}

	@Override
	public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
			throws ContextualError {
		Type cType = objectName.verifyExpr(compiler, localEnv, currentClass);
		ClassType classType = cType.asClassType("not a class type", objectName.getLocation());
		if (classType.getDefinition().getMembers().get(fieldName.getName()) == null) {
			throw new ContextualError("Field " + fieldName.getName() + " is not defined in class " + classType.getName(),
					fieldName.getLocation());
		}
		Definition field = classType.getDefinition().getMembers().get(fieldName.getName());
		FieldDefinition fieldDef = field.asFieldDefinition("not a field definition", fieldName.getLocation());
		if (fieldDef.getVisibility() == Visibility.PROTECTED && (currentClass == null ||
				!currentClass.equals(classType.getDefinition()))) {
			throw new ContextualError("Cannot access protected field " + fieldName.getName(),
					fieldName.getLocation());
		}
		fieldName.setDefinition(fieldDef);
		this.setType(fieldDef.getType());
		return fieldDef.getType();
	}

	@Override
	public void decompile(IndentPrintStream s) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void prettyPrintChildren(PrintStream s, String prefix) {
		objectName.prettyPrint(s, prefix, false);
        fieldName.prettyPrint(s, prefix, true);

	}

	@Override
	protected void iterChildren(TreeFunction f) {
		// TODO Auto-generated method stub

	}
	
	@Override
	protected DAddr daddr() {
		return fieldName.getFieldDefinition().getOperand();
	}
	
	@Override
	protected void codeAssign(IMAProgram program, int n, RegisterManager registerManager) {
		objectName.codeExpr(program, n, registerManager);
		fieldName.getFieldDefinition().setOperand(new RegisterOffset(fieldName.getFieldDefinition().getIndex(), Register.getR(n)));
	}
	
	@Override
	protected void codeExpr(IMAProgram program, int n, RegisterManager registerManager) {
		codeAssign(program, n, registerManager);
		program.addInstruction(new LOAD(daddr(), Register.getR(n)));
	}

}
