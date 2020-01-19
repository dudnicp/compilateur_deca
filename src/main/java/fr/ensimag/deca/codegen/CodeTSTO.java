package fr.ensimag.deca.codegen;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.PUSH;

public class CodeTSTO {
	private static int maxStackSize = 0;
	private static int currentStackSize = 0;
	private static int nLocalVariables = 0;
	
	public static void incLocalVariables() {
		nLocalVariables ++;
		incCurrentStackSize();
	}
	
	public static void incCurrentStackSize() {
		currentStackSize ++;
		if (currentStackSize > maxStackSize) {
			maxStackSize = currentStackSize;
		}
	}
	
	public static void decCurrentStackSize() {
		currentStackSize --;
	}
	
	public static int getCurrentStackSize() {
		return currentStackSize;
	}
	
	public static int getMaxStackSize() {
		return maxStackSize;
	}
	
	public static int getNLocalVariables() {
		return nLocalVariables;
	}
}
