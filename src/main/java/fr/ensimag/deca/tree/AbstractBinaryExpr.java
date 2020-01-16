package fr.ensimag.deca.tree;

import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;

/**
 * Binary expressions.
 *
 * @author gl28
 * @date 01/01/2020
 */
public abstract class AbstractBinaryExpr extends AbstractExpr {

    public AbstractExpr getLeftOperand() {
        return leftOperand;
    }

    public AbstractExpr getRightOperand() {
        return rightOperand;
    }

    protected void setLeftOperand(AbstractExpr leftOperand) {
        Validate.notNull(leftOperand);
        this.leftOperand = leftOperand;
    }

    protected void setRightOperand(AbstractExpr rightOperand) {
        Validate.notNull(rightOperand);
        this.rightOperand = rightOperand;
    }

    private AbstractExpr leftOperand;
    private AbstractExpr rightOperand;

    public AbstractBinaryExpr(AbstractExpr leftOperand,
            AbstractExpr rightOperand) {
        Validate.notNull(leftOperand, "left operand cannot be null");
        Validate.notNull(rightOperand, "right operand cannot be null");
        Validate.isTrue(leftOperand != rightOperand, "Sharing subtrees is forbidden");
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
    }


    @Override
    public void decompile(IndentPrintStream s) {
        s.print("(");
        getLeftOperand().decompile(s);
        s.print(" " + getOperatorName() + " ");
        getRightOperand().decompile(s);
        s.print(")");
    }

    abstract protected String getOperatorName();

    @Override
    protected void iterChildren(TreeFunction f) {
        leftOperand.iter(f);
        rightOperand.iter(f);
    }

<<<<<<< Updated upstream
=======
    
    protected void codeGenInst(DecacCompiler compiler, DVal op, 
    		GPRegister register) {
    	throw new UnsupportedOperationException("not yet implemented");
    }
    
    @Override
	protected void codeExpr(DecacCompiler compiler, int n) {
    	leftOperand.codeExpr(compiler, n);
    	DVal rightDVal = rightOperand.dval();
    	if (rightDVal != null) {
    		codeGenInst(compiler, rightOperand.dval(), Register.getR(n));
		}
    	else {
			if (n < Register.getRMAX()) {
				rightOperand.codeExpr(compiler, n + 1);
				codeGenInst(compiler, Register.getR(n + 1), Register.getR(n));
			}
			else {
				compiler.addInstruction(new PUSH(Register.getR(n)));
				rightOperand.codeExpr(compiler, n);
				compiler.addInstruction(new LOAD(Register.getR(n), Register.R0));
				compiler.addInstruction(new POP(Register.getR(n)));;
				codeGenInst(compiler, Register.R0, Register.getR(n));
			}
		}
	}
    	
    @Override
	protected void codeGenPrintInstruction(DecacCompiler compiler) {
		leftOperand.codeGenPrintInstruction(compiler);
	}
    
    
>>>>>>> Stashed changes
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        leftOperand.prettyPrint(s, prefix, false);
        rightOperand.prettyPrint(s, prefix, true);
    }

}
