package fr.ensimag.deca.codegen;

import fr.ensimag.deca.tree.AbstractIdentifier;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;

public class RegisterHandler {
	
	public static final RegisterHandler MAINREGISTERHANDLER = new RegisterHandler(Register.GB);
	
	private int offset;
	private Register register;
	
	public RegisterHandler(Register register) {
		this.register = register;
		this.offset = 0;
	}
	
	public DAddr getNewAdress() {
		offset ++;
		return new RegisterOffset(offset, register);
	}
}
