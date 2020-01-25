package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.RegisterManager;
import fr.ensimag.deca.context.BooleanType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Environment.DoubleDefException;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.NullType;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.deca.context.VoidType;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.ima.pseudocode.IMAProgram;
import fr.ensimag.ima.pseudocode.ImmediateString;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.ADDSP;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.ERROR;
import fr.ensimag.ima.pseudocode.instructions.HALT;
import fr.ensimag.ima.pseudocode.instructions.TSTO;
import fr.ensimag.ima.pseudocode.instructions.WSTR;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

/**
 * @author gl28
 * @date 01/01/2020
 */
public class Main extends AbstractMain {
    private static final Logger LOG = Logger.getLogger(Main.class);
    
    private ListDeclVar declVariables;
    private ListInst insts;
    public Main(ListDeclVar declVariables,
            ListInst insts) {
        Validate.notNull(declVariables);
        Validate.notNull(insts);
        this.declVariables = declVariables;
        this.insts = insts;
    }

    @Override
    protected void verifyMain(DecacCompiler compiler) throws ContextualError {
    	// envTypes is herited through **compiler**
        // environment of superclass (for **bloc** only cf rule (3.18))
        EnvironmentExp envExpSup = new EnvironmentExp(null);
        // environment (of parameters for **bloc**) 
        EnvironmentExp envExp = new EnvironmentExp(envExpSup);
        //Class 0
        Type voidType = compiler.getEnvTypes().getDefinitionFromName("void").getType();
        
        declVariables.verifyListDeclVariable(compiler, envExp , null);
        
        // we need to assign a returnType to the list of instructions
        insts.verifyListInst(compiler, envExp, null, voidType);
    }

    @Override
    protected void codeGenMain(DecacCompiler compiler) {
    	
    	IMAProgram mainProgram = new IMAProgram();
    	mainProgram.addComment("Beginning of main instructions:");
        declVariables.codeGenDecl(mainProgram, RegisterManager.GLOBAL_REGISTER_MANAGER);
        insts.codeGenListInst(mainProgram, RegisterManager.GLOBAL_REGISTER_MANAGER);
        mainProgram.addInstruction(new HALT());
        
        // checking stack size for stack overflow
        mainProgram.addFirst(new ADDSP(RegisterManager.GLOBAL_REGISTER_MANAGER.getNLocalVariables()));
        mainProgram.addFirst(new BOV(Label.STACKOVERFLOW));
        RegisterManager.GLOBAL_REGISTER_MANAGER.codeTSTO(mainProgram);
        
        // coding errors
        mainProgram.addLabel(Label.STACKOVERFLOW);
        mainProgram.addInstruction(new WSTR(new ImmediateString("Error: Stack overflow")));
        mainProgram.addInstruction(new ERROR());
        mainProgram.addLabel(Label.DIVBYZERO);
        mainProgram.addInstruction(new WSTR(new ImmediateString("Error: Division by zero")));
        mainProgram.addInstruction(new ERROR());
        mainProgram.addLabel(Label.OVERFLOW);
        mainProgram.addInstruction(new WSTR(new ImmediateString("Error: Overflow during arithmetic operation")));
        mainProgram.addInstruction(new ERROR());
        mainProgram.addLabel(Label.INVALIDINPUT);
        mainProgram.addInstruction(new WSTR(new ImmediateString("Error: Invalid input")));
        mainProgram.addInstruction(new ERROR());
        mainProgram.addLabel(Label.NULLOBJECT);
        mainProgram.addInstruction(new WSTR(new ImmediateString("Error: Cannot acces null object")));
        mainProgram.addInstruction(new ERROR());
        mainProgram.addLabel(Label.IMPOSSIBLECONVFLOAT);
        mainProgram.addInstruction(new WSTR(new ImmediateString("Error: Impossible converion to float")));
        mainProgram.addInstruction(new ERROR());
    }
    
    @Override
    public void decompile(IndentPrintStream s) {
        s.println("{");
        s.indent();
        declVariables.decompile(s);
        insts.decompile(s);
        s.unindent();
        s.print("}");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        declVariables.iter(f);
        insts.iter(f);
    }
 
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        declVariables.prettyPrint(s, prefix, false);
        insts.prettyPrint(s, prefix, true);
    }
}
