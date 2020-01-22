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
    
    public EnvironmentType(Environment parentEnvironment) {
    	super(parentEnvironment);
    	if (parentEnvironment == null) {
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
    		} catch (DoubleDefException e) { // this should never happens
    			e.printStackTrace(); // since we inherits all of this
    		} // unless parentEnvironment == null
    	}
    }

	@Override
	public void declare(Symbol name, Definition def) throws DoubleDefException {
		if (this.get(name) == null) {
			this.getDefinitionMap().put(name, def);
		} else {
			throw new DoubleDefException();
		}
	}
    
    

}