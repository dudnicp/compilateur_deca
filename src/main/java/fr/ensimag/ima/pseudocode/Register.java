package fr.ensimag.ima.pseudocode;

import java.util.Stack;

/**
 * Register operand (including special registers like SP).
 * 
 * @author Ensimag
 * @date 01/01/2020
 */
public class Register extends DVal {
	
	private static final GPRegister[] R = initRegisters();
	
	public static final int baseRegisterIndex = 2;
	private static int currentRegisterIndex = baseRegisterIndex;
	private static GPRegister lastExprRegister = null;
	
	public static GPRegister getAviableRegister() {
		int temp = currentRegisterIndex;
		currentRegisterIndex++;
		return R[temp];
	}
	
	public static void resetAviableRegisterIndex() {
		currentRegisterIndex = baseRegisterIndex;
	}
	
	public static GPRegister getLastExprRegister() {
		return lastExprRegister;
	}
	
	public static void setLastExprRegister(GPRegister register) {
		lastExprRegister = register;
	}
	
    private String name;
    protected Register(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Global Base register
     */
    public static final Register GB = new Register("GB");
    /**
     * Local Base register
     */
    public static final Register LB = new Register("LB");
    /**
     * Stack Pointer
     */
    public static final Register SP = new Register("SP");
    /**
     * General Purpose Registers. Array is private because Java arrays cannot be
     * made immutable, use getR(i) to access it.
     */
    /**
     * General Purpose Registers
     */
    public static GPRegister getR(int i) {
        return R[i];
    }
    /**
     * Convenience shortcut for R[0]
     */
    public static final GPRegister R0 = R[0];
    /**
     * Convenience shortcut for R[1]
     */
    public static final GPRegister R1 = R[1];
    
    public static final GPRegister R2 = R[2];
    
    static private GPRegister[] initRegisters() {
        GPRegister [] res = new GPRegister[16];
        for (int i = 0; i <= 15; i++) {
            res[i] = new GPRegister("R" + i, i);
        }
        return res;
    }
}
