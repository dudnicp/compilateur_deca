package fr.ensimag.deca.codegen;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.PUSH;

public class CodeTSTO {
	private static int maxStackSize = 0;
	private static int currentStackSize = 0;
	
	public static void incCurrentStackSize() {
		currentStackSize ++;
		if (currentStackSize > maxStackSize) {
			maxStackSize = currentStackSize;
		}
	}
	
	public static void decCurrentStackSize() {
		currentStackSize --;
	}
}
