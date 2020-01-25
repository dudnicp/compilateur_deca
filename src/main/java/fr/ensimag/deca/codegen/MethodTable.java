package fr.ensimag.deca.codegen;

import java.util.HashMap;
import org.apache.commons.lang.Validate;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.Label;

public abstract class MethodTable {
	private static HashMap<String, Label[]> methodTable = new HashMap<String, Label[]>();
	private static HashMap<String, DAddr> tablesAdress = new HashMap<String, DAddr>();
	
	public static void addClass(String name, int nMethods) {
		Validate.isTrue(nMethods >= 0);
		methodTable.put(name, new Label[nMethods]);
		tablesAdress.put(name, RegisterManager.GLOBAL_REGISTER_MANAGER.getNewAddress());
	}
	
	public static DAddr getClassAddr(String classeName) {
		return tablesAdress.get(classeName);
	}
	
	public static void putMethod(String className, Label methodLabel, int index) {
		Validate.isTrue(methodTable.containsKey(className) && getMethods(className).length > index && 0 <= index);
		getMethods(className)[index] = methodLabel;
	}
	
	public static Label getMethodLabel(String className, int methodIndex) {
		return methodTable.get(className)[methodIndex];
	}
	
	public static Label[] getMethods(String className) {
		Validate.isTrue(methodTable.containsKey(className));
		return methodTable.get(className);
	}
	
	public static void addClass(String name, String superName, int nMethods) {
		Validate.isTrue(methodTable.containsKey(name) 
				&& methodTable.containsKey(superName) 
				&& nMethods >= getMethods(superName).length);
		for (int i = 0; i < getMethods(superName).length; i++) {
			putMethod(name, getMethodLabel(name, i), i);
		}
	}
	
	
	public static void codeGen(DecacCompiler compiler) {
		
	}
}