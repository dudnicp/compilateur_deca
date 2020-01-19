package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
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
        
        EnvironmentExp envExpObject = new EnvironmentExp(null);
        Symbol trueBoolean = envExpObject.createSymbol("true");
        Symbol falseBoolean = envExpObject.createSymbol("false");

        try {
            envExpObject.declare(trueBoolean, new VariableDefinition(
                    new BooleanType(compiler.getEnvTypes().getSymbolFromMap("boolean")),
                    Location.BUILTIN));
            envExpObject.declare(falseBoolean, new VariableDefinition(
                    new BooleanType(compiler.getEnvTypes().getSymbolFromMap("boolean")),
                    Location.BUILTIN));
        } catch (DoubleDefException e) {
            // this won't happen since we initialize the env_expr
            e.printStackTrace();
        }
        // we inherit the true and false "variables"
        // TODO: make it constant
        EnvironmentExp localEnv = new EnvironmentExp(envExpObject);
        
        declVariables.verifyListDeclVariable(compiler, localEnv , null);
        
        // we need to assign a returnType to the list of instructions
        Type defaultType = compiler.getEnvTypes().getDefinitionFromName("void").getType();
        insts.verifyListInst(compiler, localEnv, null, defaultType);
    }

    @Override
    protected void codeGenMain(DecacCompiler compiler) {
        // A FAIRE: traiter les déclarations de variables.
        compiler.addComment("Beginning of main instructions:");
        declVariables.codeGenDecl(compiler);
        insts.codeGenListInst(compiler);
        compiler.addInstruction(new HALT());
        
        // checking stack size for stack overflow
        compiler.addFirst(new ADDSP(CodeTSTO.getNLocalVariables()));
        compiler.addFirst(new BOV(Label.STACKOVERFLOW));
        compiler.addFirst(new TSTO(CodeTSTO.getMaxStackSize()));
        
        // coding errors
        compiler.addLabel(Label.STACKOVERFLOW);
        compiler.addInstruction(new WSTR(new ImmediateString("Error: Stack overflow, exiting program")));
        compiler.addInstruction(new ERROR());
        compiler.addLabel(Label.DIVBYZERO);
        compiler.addInstruction(new WSTR(new ImmediateString("Error: Division by zero, exiting program")));
        compiler.addInstruction(new ERROR());
        compiler.addLabel(Label.INVALIDINPUT);
        compiler.addInstruction(new WSTR(new ImmediateString("Error: Invalid input, exiting program")));
        compiler.addInstruction(new ERROR());
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
