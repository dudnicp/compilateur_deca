package fr.ensimag.deca.context;

import fr.ensimag.deca.tree.Location;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import java.util.HashMap;
import java.util.Map;


import org.apache.commons.lang.Validate;

/**
 * @author gl28
 * @date 01/01/2020
 */
public class EnvironmentType extends Environment{
    
	/**
	 * Initialize the predefined types
	 */
    public EnvironmentType() {
    	super(null);
		Symbol s;
		try {
			s = this.createSymbol("void");
    		this.declare(s, new TypeDefinition(new VoidType(s), Location.BUILTIN));
    		
    		s = this.createSymbol("boolean");
    		this.declare(s, new TypeDefinition(new BooleanType(s), Location.BUILTIN));

    		s = this.createSymbol("float");
    		this.declare(s, new TypeDefinition(new FloatType(s), Location.BUILTIN));
    		
    		s = this.createSymbol("int");
    		this.declare(s, new TypeDefinition(new IntType(s), Location.BUILTIN));
    		
    		s = this.createSymbol("String");
    		this.declare(s, new TypeDefinition(new StringType(s), Location.BUILTIN));
    		
    		s = this.createSymbol("null");
    		this.declare(s, new TypeDefinition(new NullType(s), Location.BUILTIN));
    		
    		s = this.createSymbol("Object");
    		ClassType objectClassType = new ClassType(s, Location.BUILTIN, null);
    		this.declare(s, objectClassType.getDefinition());
		} catch (DoubleDefException e) { // this never happens
			e.printStackTrace(); // since the map is empty at this point
		}
    }

	
	/**
	 * get method for predefined types
	 * 
	 * @param name The name of the type
	 * @return Definition of the type, or null if undefined
	 */
	public Definition getDefinitionFromName(String name) {
		return this.get(this.createSymbol(name));
	}
    
}