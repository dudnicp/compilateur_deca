package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.DAddr;

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
	protected DAddr daddr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
			throws ContextualError {
		Type cType = objectName.verifyExpr(compiler, localEnv, currentClass);
		ClassType classType = cType.asClassType("not a class type", objectName.getLocation());
		if (classType.getDefinition().getMembers().getAny(fieldName.getName()) == null) {
			throw new ContextualError("Field " + fieldName.getName() + " is not defined in class " + classType.getName(),
					fieldName.getLocation());
		}
		Definition field = classType.getDefinition().getMembers().getAny(fieldName.getName());
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

}
