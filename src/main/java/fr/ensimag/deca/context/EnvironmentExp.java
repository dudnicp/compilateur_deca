package fr.ensimag.deca.context;

import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.tree.Location;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * Dictionary associating identifier's ExpDefinition to their names.
 * 
 * This is actually a linked list of dictionaries: each EnvironmentExp has a
 * pointer to a parentEnvironment, corresponding to superblock (eg superclass).
 * 
 * The dictionary at the head of this list thus corresponds to the "current" 
 * block (eg class).
 * 
 * Searching a definition (through method get) is done in the "current" 
 * dictionary and in the parentEnvironment if it fails. 
 * 
 * Insertion (through method declare) is always done in the "current" dictionary.
 * 
 * @author gl28
 * @date 01/01/2020
 */
public class EnvironmentExp {
	private SymbolTable symbolMap;
	private EnvironmentExp parent;
	protected Map<Symbol, Definition> definitionMap;


	public EnvironmentExp getParent() {
		return parent;
	}
	
	
	public EnvironmentExp() {
		this(null);
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

	public EnvironmentExp(EnvironmentExp parent) {
		this.parent = parent;
		this.symbolMap = new SymbolTable();
		this.definitionMap = new HashMap<Symbol, Definition>();
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
	
	
	/**
     * Create or reuse a symbol.
     *
     * If a symbol already exists with the same name in this table, then return
     * this Symbol. Otherwise, create a new Symbol and add it to the table.
     */
	public Symbol createSymbol(String name) {
		return symbolMap.create(name);
	}
	
	/**
     * Get method for map field DefinitionMap
     *
     * @param key
     * 			Name of the symbol to search for
     * @return 
     * 			Return the definition of the symbol or null if the symbol is undefined.
     * 			If not in this environment , return the definition of the symbol in its parent environment. This goes on until 
     * 			a null parent is reached - in this case it returns null - or a definition is found and returned.
     */
	public Definition get(Symbol key) {
		return definitionMap.get(key);
	}
	
	/**
	 * Get method for linked dictionaries (from current dictionary to the tail)
	 * 
	 * @param key
	 * @return
	 * 			Return first occurrence of symbol key, null otherwise
	 */
	public Definition getAny(Symbol key) {
		if (this.get(key) == null) {
			if (parent == null) return null;
			else return parent.getAny(key);
		} else return this.get(key);
	}	

	
	public static class DoubleDefException extends Exception {
        private static final long serialVersionUID = -2733379901827316441L;
    }
	
	/**
     * Add the definition def associated to the symbol name in the environment.
     * 
     * Adding a symbol which is already defined in the environment,
     * - throws DoubleDefException if the symbol is in the "current" dictionary 
     * - or, hides the previous declaration otherwise.
     * 
     * @param name
     *            Name of the symbol to define
     * @param def
     *            Definition of the symbol
     * @throws DoubleDefException
     *             if the symbol is already defined at the "current" dictionary
     *
     */
    public void declare(Symbol name, Definition def) throws DoubleDefException {
    	if (this.get(name) == null) {
			this.definitionMap.put(name, def);
		} else {
			throw new DoubleDefException();
		}
    }
    

    
    @Override
    public String toString() {
    	String str = "";
    	for (Symbol s: definitionMap.keySet()) {
    		str = str + " ||| symbol: " + s + " --> " + definitionMap.get(s); 
    	}
    	if (parent != null) {
    		return str + parent.toString();
    	} else return str;
    }
    
}
