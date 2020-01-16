package fr.ensimag.deca.context;

import fr.ensimag.deca.tools.SymbolTable;
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
public class EnvironmentExp extends Environment{
	
    public EnvironmentExp(EnvironmentExp parentEnvironment) {
        // new environment inherits all of the parent's associations
    	super(parentEnvironment);
    }
    
    @Override
    public void declare(Symbol name, Definition def) throws DoubleDefException {
    	if (this.get(name) != null) { // already exists in this
    		throw new DoubleDefException(); // environment
    	} else { // else we add it to the map
    		this.getDefinitionMap().put(name, def);
    	}
    }

}
