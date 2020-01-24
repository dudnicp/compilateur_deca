package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;

public abstract class AbstractDeclField extends Tree {

	/**
     * Implements non-terminal "decl_field" of [SyntaxeContextuelle] in pass 2
     * @param compiler contains "env_types" attribute
     * @param localEnv 
     *   its "parentEnvironment" corresponds to the "env_exp_sup" attribute
     *   in precondition, its "current" dictionary corresponds to 
     *      the "env_exp" attribute
     *   in postcondition, its "current" dictionary corresponds to 
     *      the synthetized attribute
     * @param currentClass 
     *          corresponds to the "class" attribute (null in the main bloc).
     */    
    protected void verifyDeclField(DecacCompiler compiler, Symbol currentClass,
    		Symbol superClass)
            throws ContextualError {
	}
    
    protected abstract void codeGenDeclField(DecacCompiler compiler);
    
    protected abstract void verifyClassBodyField(DecacCompiler compiler,
            EnvironmentExp localEnv, Symbol currentClass) throws ContextualError;
    
}
