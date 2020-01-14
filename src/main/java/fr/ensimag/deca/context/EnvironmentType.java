package fr.ensimag.deca.context;

import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.tree.Location;
import fr.ensimag.deca.tools.SymbolTable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;

/**
 * @author gl28
 * @date 01/01/2020
 */
public class EnvironmentType {
	private Map<Symbol, TypeDefinition> map;
    EnvironmentType parentEnvironment;
    
    public EnvironmentType(EnvironmentType parentEnvironment) {
        this.parentEnvironment = parentEnvironment;
        // new environment inherits all of the parent's associations
        this.map = new HashMap<Symbol, TypeDefinition>(parentEnvironment.getMap());
    }
    
    public EnvironmentType() {
    	// build the predefined type environment
        this.map = new HashMap<Symbol, TypeDefinition>();
        SymbolTable typeTable = new SymbolTable();
        Symbol symbol;
        Location defaultLocation = new Location(0, 0, "Predefined type");
        
        symbol = typeTable.create("void");
        map.put(symbol,  new TypeDefinition(new VoidType(symbol), defaultLocation));
        
        symbol = typeTable.create("boolean");
        map.put(symbol,  new TypeDefinition(new BooleanType(symbol), defaultLocation));

        symbol = typeTable.create("float");
        map.put(symbol,  new TypeDefinition(new FloatType(symbol), defaultLocation));

        symbol = typeTable.create("int");
        map.put(symbol,  new TypeDefinition(new IntType(symbol), defaultLocation));

        symbol = typeTable.create("Object");
        map.put(symbol,  new ClassDefinition(new ClassType(symbol), defaultLocation, null));

    }
    
    public Map<Symbol, TypeDefinition> getMap() {
    	return this.map;
    }

    public static class DoubleDefException extends Exception {
        private static final long serialVersionUID = -2733379901827316441L;
    }

    /**
     * Return the definition of the symbol in the environment, or null if the
     * symbol is undefined.
     */
    public TypeDefinition get(Symbol key) {
    	return map.get(key);
    }

   
    public void declare(Symbol name, TypeDefinition def) throws DoubleDefException {
    	if (map.get(name) == null) {
    		map.put(name,  def);
    	} else throw new DoubleDefException();
    }
    	
}
