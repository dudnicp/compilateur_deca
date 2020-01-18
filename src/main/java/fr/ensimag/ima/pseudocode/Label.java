package fr.ensimag.ima.pseudocode;

import org.apache.commons.lang.Validate;

/**
 * Representation of a label in IMA code. The same structure is used for label
 * declaration (e.g. foo: instruction) or use (e.g. BRA foo).
 *
 * @author Ensimag
 * @date 01/01/2020
 */
public class Label extends Operand {
	
	private static int endAndLabelCounter = 0;
	private static int endOrLabelCounter = 0;
	private static int endIfLabelCounter = 0;
	private static int elseIfLabelCounter = 0;
	private static int beginWhileLabelCounter = 0;
	private static int whileCondLabelCounter = 0;
	
	public static Label newEndAndLabel() {
		int temp = endAndLabelCounter;
		endAndLabelCounter ++;
		return new Label("End_And_" + temp);
	}
	
	public static Label newEndOrLabel() {
		int temp = endOrLabelCounter;
		endOrLabelCounter ++;
		return new Label("End_Or_" + temp);
	}
	
	public static Label newEndIfLabel() {
		int temp = endIfLabelCounter;
		endIfLabelCounter ++;
		return new Label("End_If_" + temp);
	}
	
	public static Label newElseIfLabel() {
		int temp = elseIfLabelCounter;
		elseIfLabelCounter ++;
		return new Label("Else_" + temp);
	}
	
	public static Label newBeginWhileLabel() {
		int temp = beginWhileLabelCounter;
		beginWhileLabelCounter ++;
		return new Label("Begin_While_" + temp);
	}
	
	public static Label newWhileCondLabel() {
		int temp = whileCondLabelCounter;
		whileCondLabelCounter ++;
		return new Label("While_Cond_" + temp);
	}

    @Override
    public String toString() {
        return name;
    }

    public Label(String name) {
        super();
        Validate.isTrue(name.length() <= 1024, "Label name too long, not supported by IMA");
        Validate.isTrue(name.matches("^[a-zA-Z][a-zA-Z0-9_.]*$"), "Invalid label name " + name);
        this.name = name;
    }
    private String name;
}
