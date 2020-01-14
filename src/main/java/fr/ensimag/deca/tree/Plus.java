package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;

/**
 * @author gl28
 * @date 01/01/2020
 */
public class Plus extends AbstractOpArith {
    public Plus(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }
 

    @Override
    protected String getOperatorName() {
        return "+";
    }
    
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        // A FAIRE TODO
    }
}
