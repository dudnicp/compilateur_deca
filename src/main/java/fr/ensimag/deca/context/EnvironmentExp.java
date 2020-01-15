package fr.ensimag.deca.context;

import fr.ensimag.deca.tools.SymbolTable.Symbol;
import java.util.HashMap;
import java.util.Map;

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
	private Map<Symbol, ExpDefinition> map;
    EnvironmentExp parentEnvironment;
    
    public EnvironmentExp(EnvironmentExp parentEnvironment) {
        this.parentEnvironment = parentEnvironment;
        // new environment inherits all of the parent's associations
        // this.map = new HashMap<Symbol, ExpDefinition>(parentEnvironment.map);
    }
    public static class DoubleDefException extends Exception {
        private static final long serialVersionUID = -2733379901827316441L;
    }

    public Map<Symbol, ExpDefinition> getMap() {
    	return this.map;
    }
    
    /**
     * Return the definition of the symbol in the environment, or null if the
     * symbol is undefined.
     */
    public ExpDefinition get(Symbol key) {
    	return map.get(key);
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
    public void declare(Symbol name, ExpDefinition def) throws DoubleDefException {
    	assert(parentEnvironment != null); // programmation defensive
    	if (map.get(name) != null) {
    		throw new DoubleDefException(); // exists in current dictionary
    	} else if (parentEnvironment.getMap().get(name) != null) {
    		map.put(name, def); // hides previous declaration 
    	} else {
    		map.put(name, def); // adds new definition - symbol association
    	}
    }

}
