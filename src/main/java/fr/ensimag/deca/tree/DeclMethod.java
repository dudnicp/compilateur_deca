package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.Environment.DoubleDefException;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.EnvironmentType;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.tree.ListDeclParam;
import fr.ensimag.ima.pseudocode.IMAProgram;
import fr.ensimag.ima.pseudocode.ImmediateString;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.ADDSP;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.ERROR;
import fr.ensimag.ima.pseudocode.instructions.RTS;
import fr.ensimag.ima.pseudocode.instructions.WNL;
import fr.ensimag.ima.pseudocode.instructions.WSTR;

public class DeclMethod extends Tree {
	private AbstractIdentifier type;
	private AbstractIdentifier methodName;
	private ListDeclParam listDeclParam;
	private AbstractMethodBody methodBody;
	
	public DeclMethod(AbstractIdentifier type, AbstractIdentifier methodName,
			ListDeclParam listDeclParam, AbstractMethodBody methodBody) {
		this.type = type;
		this.methodName = methodName;
		this.listDeclParam = listDeclParam;
		this.methodBody = methodBody;
	}
	
	public void verifyDeclMethod(DecacCompiler compiler,
			Symbol currentClass) throws ContextualError {
    	EnvironmentType envTypes = compiler.getEnvTypes();
    	Definition def = envTypes.getDefinitionFromName(type.getName().toString()); // predefined types
    	Type verifiedType;
    	if (def == null) def = envTypes.get(type.getName()); // classes type
    	if (def == null) {
    		throw new ContextualError("Type " + type.getName() + " is not defined (0.2)",
    				this.getLocation());
    	} else {
        	verifiedType = def.getType();
        	type.setDefinition(def);
    	}
		Signature sig = listDeclParam.verifyListDeclParam(compiler);
		ClassDefinition classDef = (ClassDefinition)compiler.getEnvTypes().get(currentClass);
        MethodDefinition methodDef = new MethodDefinition(verifiedType,
        		this.getLocation(), sig, classDef.getNumberOfMethods());
		try {
            classDef.getMembers().declare(methodName.getName(), methodDef);
            classDef.incNumberOfMethods();
            this.methodName.setDefinition(methodDef);
		} catch (DoubleDefException e) {
			Definition mDef = classDef.getMembers().getParent().get(methodName.getName());
            if (mDef == null) {
				throw new ContextualError("Method or field " + methodName.getName() + " is already defined in this scope",
						methodName.getLocation());
            }
            MethodDefinition methodDefinition = mDef.asMethodDefinition("Not a method definition", mDef.getLocation());
            if (!methodDef.getSignature().equals(methodDefinition.getSignature())) {
                throw new ContextualError("Wrong signature for redifinition of method " + methodName.getName(),
                        methodDefinition.getLocation());
			} else {
				MethodDefinition methodDefRedefinition = new MethodDefinition(verifiedType,
		        		this.getLocation(), sig, methodDefinition.getIndex());
				classDef.getMembers().getDefinitionMap().put(methodName.getName(), methodDefRedefinition);
				this.methodName.setDefinition(methodDefRedefinition);
			}
		}
	}
	
	public void verifyClassBodyMethod(DecacCompiler compiler, 
			EnvironmentExp localEnv, Symbol currentClass) throws ContextualError {
		EnvironmentExp envExpParam = new EnvironmentExp(localEnv);
		listDeclParam.verifyClassBodyListDeclParam(compiler,
				envExpParam);
		methodBody.verifyClassMethodBody(compiler, envExpParam, currentClass, type.getDefinition().getType());
	}
	
	public AbstractIdentifier getMethodName() {
		return methodName;
	}
	
	
	@Override
	public void decompile(IndentPrintStream s) {
		type.decompile(s);
		s.print(" ");
		methodName.decompile(s);
		s.print("(TODO decompile param) {}");
	}

	@Override
	protected void prettyPrintChildren(PrintStream s, String prefix) {
		type.prettyPrint(s, prefix, false);
		methodName.prettyPrint(s, prefix, false);
		listDeclParam.prettyPrint(s, prefix, false);
		methodBody.prettyPrint(s, prefix, true);
	}

	@Override
	protected void iterChildren(TreeFunction f) {
		type.iter(f);
		methodName.iter(f);
		listDeclParam.iterChildren(f);
	}
	
	public void codeGen(IMAProgram program, String className) {
//		System.out.println(Label.getMethodStartLabel(className, methodName.getName().getName()).toString());
		
		
		RegisterManager registerManager = new RegisterManager(Register.LB);
		
		int argIndex = 3;
		for (DeclParam arg : listDeclParam.getList()) {
			arg.getParamName().getExpDefinition().setOperand(new RegisterOffset(-argIndex, Register.LB));
		}
		
		IMAProgram startLabelCode = new IMAProgram();
		IMAProgram tstoCode = new IMAProgram();
		IMAProgram saveRegisterCode = new IMAProgram();
		IMAProgram bodyCode = new IMAProgram(); 
		IMAProgram returnErrorCode = new IMAProgram();
		IMAProgram endLabelCode = new IMAProgram();
		IMAProgram restoreRegistersCode = new IMAProgram();
		
		startLabelCode.addLabel(Label.getMethodStartLabel(className, methodName.getName().getName()));
		endLabelCode.addLabel(Label.getMethodEndLabel(className, methodName.getName().getName()));
		
		methodBody.codeGen(bodyCode, registerManager, className, methodName.getName().getName());
		
		if (!type.getDefinition().getType().isVoid()) {
			returnErrorCode.addInstruction(new WSTR(new ImmediateString("Error : exiting function " + 
							className + "." + methodName.getName().getName() + " without return instruction")));
			returnErrorCode.addInstruction(new WNL());
			returnErrorCode.addInstruction(new ERROR());
		}
		
		registerManager.saveGPRegisters(saveRegisterCode);
		registerManager.restoreGPRegisters(restoreRegistersCode);
		
		registerManager.codeTSTO(tstoCode);
		tstoCode.addInstruction(new BOV(Label.STACKOVERFLOW));
		if (registerManager.getNLocalVariables() != 0) {
			tstoCode.addInstruction(new ADDSP(registerManager.getNLocalVariables()));
		}
		
		
		program.append(startLabelCode);
		program.append(tstoCode);
		program.append(saveRegisterCode);
		program.append(bodyCode);
		program.append(returnErrorCode);
		program.append(endLabelCode);
		program.append(restoreRegistersCode);
		program.addInstruction(new RTS());
	}
}
