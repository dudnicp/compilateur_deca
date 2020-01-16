package fr.ensimag.deca.tree;

import java.io.PrintStream;

import javax.security.auth.x500.X500Principal;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.EnvironmentType;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;

/**
 * Deca Identifier
 *
 * @author gl28
 * @date 01/01/2020
 */
public class Identifier extends AbstractIdentifier {
    private static final Logger LOG = Logger.getLogger(Identifier.class);

    @Override
    protected void checkDecoration() {
        if (getDefinition() == null) {
            throw new DecacInternalError("Identifier " + this.getName() + " has no attached Definition");
        }
    }

    @Override
    public Definition getDefinition() {
        return definition;
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * ClassDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a class definition.
     */
    @Override
    public ClassDefinition getClassDefinition() {
        try {
            return (ClassDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a class identifier, you can't call getClassDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * MethodDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a method definition.
     */
    @Override
    public MethodDefinition getMethodDefinition() {
        try {
            return (MethodDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a method identifier, you can't call getMethodDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * FieldDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a field definition.
     */
    @Override
    public FieldDefinition getFieldDefinition() {
        try {
            return (FieldDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a field identifier, you can't call getFieldDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * VariableDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a field definition.
     */
    @Override
    public VariableDefinition getVariableDefinition() {
        try {
            return (VariableDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a variable identifier, you can't call getVariableDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a ExpDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a field definition.
     */
    @Override
    public ExpDefinition getExpDefinition() {
        try {
            return (ExpDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a Exp identifier, you can't call getExpDefinition on it");
        }
    }

    @Override
    public void setDefinition(Definition definition) {
        this.definition = definition;
    }

    @Override
    public Symbol getName() {
        return name;
    }

    private Symbol name;

    public Identifier(Symbol name) {
        Validate.notNull(name);
        this.name = name;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
    	if (localEnv.get(this.getName()) == null) {
    		throw new ContextualError("Undefined identifier " + this.getName(),
    				this.getLocation());
    	} else {
    		Type definedType = localEnv.get(this.getName()).getType();
    		this.setDefinition(localEnv.get(this.getName()));
    		this.setType(definedType);
    		return definedType;
    	}
    }

    /**
     * Implements non-terminal "type" of [SyntaxeContextuelle] in the 3 passes
     * @param compiler contains "env_types" attribute
     */
    @Override
    public Type verifyType(DecacCompiler compiler) throws ContextualError {
    	// Rule (3.17)
    	EnvironmentType envTypes = compiler.getEnvTypes();
    	Definition def = envTypes.getDefinitionFromName(this.getName().toString());
    	if (def == null) {
    		throw new ContextualError("Type " + this.getName() + " is not defined",
    				this.getLocation());
    	} else {
        	Type type = def.getType();
    		this.setType(type);
    	}
    	if (this.getType().isVoid()) {
    		throw new ContextualError("Variable cannot be of type void", this.getLocation());
    	} else {
    		return this.getType();
    	}
    }
    
    
    private Definition definition;


    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print(name.toString());
    }

    @Override
    String prettyPrintNode() {
        return "Identifier (" + getName() + ")";
    }

    @Override
    protected void prettyPrintType(PrintStream s, String prefix) {
        Definition d = getDefinition();
        if (d != null) {
            s.print(prefix);
            s.print("definition: ");
            s.print(d);
            s.println();
        }
    }
    
    @Override
    protected DVal dval() {
    	return daddr();
    }
    
    @Override
    public DAddr daddr() {
    	return Register.getAddr(name.toString());
    }

}
