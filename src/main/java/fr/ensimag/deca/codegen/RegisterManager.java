package fr.ensimag.deca.codegen;

import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.IMAProgram;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.ADDSP;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.BSR;
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.PUSH;
import fr.ensimag.ima.pseudocode.instructions.SUBSP;
import fr.ensimag.ima.pseudocode.instructions.TSTO;

public class RegisterManager {
	
	public static final RegisterManager GLOBAL_REGISTER_MANAGER = new RegisterManager(Register.GB);
	
	private int nUsedRegisters = 0;
	private int GPRegisterUsedMaxIndex = Register.defaultRegisterIndex;
	
	private int nLocalVariables = 0;
	
	private int currentNumberOfTemps = 0;
	private int maxNumberOfTemps = 0;
	
	private int currentNumberOfMethodParams = 0;
	private int maxNumberOfMethodParams = 0;
	
	private Register register;
	
	
	/* Constructor */
	public RegisterManager(Register register) {
		this.register = register;
	}
	
	
	
	/* Used registers */
	public void saveGPRegisters(IMAProgram program) {
		for (int i = GPRegisterUsedMaxIndex; i >= Register.defaultRegisterIndex; i--) {
			program.addInstruction(new PUSH(Register.getR(i)));
			nUsedRegisters ++;
		}
	}
	
	public void restoreGPRegisters(IMAProgram program) {
		for (int i = Register.defaultRegisterIndex; i < GPRegisterUsedMaxIndex; i++) {
			program.addInstruction(new POP(Register.getR(i)));
		}
	}
	
	public void tryMaxRegisterIndex(int n) {
		if (n > GPRegisterUsedMaxIndex) {
			GPRegisterUsedMaxIndex = n;
		}
	}
	
	
	
	/* Local Variables */
	public DAddr getNewAddress() {
		nLocalVariables ++;
		return new RegisterOffset(nLocalVariables, register);
	}
	
	public int getNLocalVariables() {
		return nLocalVariables;
	}
	
	
	/* Number of temporary registers */
	public void incCurrentNumberOfTemps() {
		currentNumberOfTemps ++;
		if (currentNumberOfTemps > maxNumberOfTemps) {
			maxNumberOfTemps = currentNumberOfTemps;
		}
	}
	
	public void decCurrentNumberOfTemps() {
		currentNumberOfTemps --;
	}
	
	
	
	/* Number of method parameters */
	public void incCurrentNumberOfMethodParams(int n) {
		currentNumberOfMethodParams += n;
		if (currentNumberOfMethodParams > maxNumberOfMethodParams) {
			currentNumberOfMethodParams = maxNumberOfMethodParams;
		}
	}
	
	public void decCurrentNumberOfMethodParams(int n) {
		currentNumberOfMethodParams -= n;
	}
	
	
	
	/* Coding TSTO */
	public void codeTSTO(IMAProgram program) {
		program.addInstruction(new TSTO(nUsedRegisters + nLocalVariables + maxNumberOfTemps + maxNumberOfMethodParams));
	}
}
