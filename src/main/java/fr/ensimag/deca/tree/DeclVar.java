package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.TypeDefinition;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.ImmediateFloat;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.CodeTSTO;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

/**
 * @author gl28
 * @date 01/01/2020
 */
public class DeclVar extends AbstractDeclVar {
    private static final Logger LOG = Logger.getLogger(DeclVar.class);

    
    final private AbstractIdentifier type;
    final private AbstractIdentifier varName;
    final private AbstractInitialization initialization;

    public DeclVar(AbstractIdentifier type, AbstractIdentifier varName, AbstractInitialization initialization) {
        Validate.notNull(type);
        Validate.notNull(varName);
        Validate.notNull(initialization);
        this.type = type;
        this.varName = varName;
        this.initialization = initialization;
    }

    
    @Override
    protected void verifyDeclVar(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
    	// rule (3.17)
    	Type typeVerified = type.verifyType(compiler);
       	varName.setType(typeVerified);
    	varName.setDefinition(new VariableDefinition(typeVerified, varName.getLocation()));
    	try {
    		// ajout de la variable dans l'environnement;
			localEnv.declare(varName.getName(), varName.getExpDefinition());
		} catch (fr.ensimag.deca.context.Environment.DoubleDefException e1) {
			throw new ContextualError("Variable " + varName.getName() + " is already declared at " +
					localEnv.get(varName.getName()).getLocation() + " (3.17)",
					varName.getLocation());
		}
    	initialization.verifyInitialization(compiler, typeVerified, localEnv, currentClass);
    }

    
    @Override
    public void decompile(IndentPrintStream s) {
        s.print(type.decompile());
        s.print(" ");
        s.print(varName.decompile());
        s.print(initialization.decompile());
        s.print(";");
    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
        type.iter(f);
        varName.iter(f);
        initialization.iter(f);
    }
    
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        varName.prettyPrint(s, prefix, false);
        initialization.prettyPrint(s, prefix, true);
    }
    
    @Override
    protected void codeGenDeclVar(DecacCompiler compiler) {
    	DAddr addr = Register.getNewAddr();
    	varName.getVariableDefinition().setOperand(addr);
    	CodeTSTO.incLocalVariables();
    	if (type.getType().isFloat()) {
    		compiler.addInstruction(new LOAD(new ImmediateFloat(0.0f), Register.getDefaultRegister()));
    		
		} else if (type.getType().isClassOrNull()) {
			compiler.addInstruction(new LOAD(new NullOperand(), Register.getDefaultRegister()));
		} else {
			compiler.addInstruction(new LOAD(new ImmediateInteger(0), Register.getDefaultRegister()));
		}
    	initialization.codeExpr(compiler, Register.defaultRegisterIndex);
    	compiler.addInstruction(new STORE(Register.getDefaultRegister(), addr));
    }
    
}
