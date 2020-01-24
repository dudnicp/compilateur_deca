package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.MethodTable;
import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.LabelOperand;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.*;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

/**
 * Deca complete program (class definition plus main block)
 *
 * @author gl28
 * @date 01/01/2020
 */
public class Program extends AbstractProgram {
    private static final Logger LOG = Logger.getLogger(Program.class);
    
    public Program(ListDeclClass classes, AbstractMain main) {
        Validate.notNull(classes);
        Validate.notNull(main);
        this.classes = classes;
        this.main = main;
    }
    public ListDeclClass getClasses() {
        return classes;
    }
    public AbstractMain getMain() {
        return main;
    }
    private ListDeclClass classes;
    private AbstractMain main;

    @Override
    public void verifyProgram(DecacCompiler compiler) throws ContextualError {
    	// rule (3.1)
    	// the EnvironmentType attribute of compiler is updated
    	// after the first pass (2.1)
        classes.verifyListClass(compiler);
        // second pass
        //classes.verifyListClassMembers(compiler);
        // third pass
        //classes.verifyListClassBody(compiler);
        main.verifyMain(compiler);
    }

    @Override
    public void codeGenProgram(DecacCompiler compiler) {
    	
    	// creating method table for the super-class Object
    	MethodTable.addClass("Object", 1);
    	Label objectEqualsLabel = Label.getMethodLabel("Object", "equals");
    	MethodTable.putMethod("Object", objectEqualsLabel, 0);
    	
    	// generating code for the method table of the super class Object
    	compiler.addInstruction(new LOAD(new NullOperand(), Register.R0));
    	compiler.addInstruction(new STORE(Register.R0, RegisterManager.GLOBAL_REGISTER_MANAGER.getNewAddress()));
    	compiler.addInstruction(new LOAD(new LabelOperand(objectEqualsLabel), Register.R0));
    	compiler.addInstruction(new STORE(Register.R0, RegisterManager.GLOBAL_REGISTER_MANAGER.getNewAddress()));
    	
    	for (AbstractDeclClass c : classes.getList()) {
    		c.codeGenMethodTable(compiler);
		}
    	
    	
    	
        compiler.addComment("Main program");
        main.codeGenMain(compiler);
    }

    @Override
    public void decompile(IndentPrintStream s) {
        getClasses().decompile(s);
        getMain().decompile(s);
    }
    
    @Override
    protected void iterChildren(TreeFunction f) {
        classes.iter(f);
        main.iter(f);
    }
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        classes.prettyPrint(s, prefix, false);
        main.prettyPrint(s, prefix, true);
    }
}
