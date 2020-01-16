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
	SymbolTable typeTable;
    EnvironmentType parentEnvironment;
    
    public EnvironmentType(EnvironmentType parentEnvironment) {
        this.parentEnvironment = parentEnvironment;
        // new environment inherits all of the parent's associations
        this.map = new HashMap<Symbol, TypeDefinition>(parentEnvironment.getMap());
    }
    
    public EnvironmentType() {
    	// build the predefined type environment
        this.map = new HashMap<Symbol, TypeDefinition>();
        typeTable = new SymbolTable();
        Symbol symbol;
        
        symbol = typeTable.create("void");
        map.put(symbol,  new TypeDefinition(new VoidType(symbol), Location.BUILTIN));
        
        symbol = typeTable.create("boolean");
        map.put(symbol,  new TypeDefinition(new BooleanType(symbol), Location.BUILTIN));

        symbol = typeTable.create("float");
        map.put(symbol,  new TypeDefinition(new FloatType(symbol), Location.BUILTIN));

        symbol = typeTable.create("String");
        map.put(symbol,  new TypeDefinition(new StringType(symbol), Location.BUILTIN));


        symbol = typeTable.create("int");
        map.put(symbol,  new TypeDefinition(new IntType(symbol), Location.BUILTIN));

        symbol = typeTable.create("Object");
        map.put(symbol,  new ClassDefinition(new ClassType(symbol), Location.BUILTIN, null));

    }
    
    public Map<Symbol, TypeDefinition> getMap() {
    	return this.map;
    }
    
    public SymbolTable getSymbolTable() {
    	return typeTable;
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
    
    public boolean typeDefined(String name) {
    	return map.containsKey(typeTable.contains(name));
    }

    public Symbol getSymbol(String name) {
    	return typeTable.contains(name);
    }
   
    public void declare(Symbol name, TypeDefinition def) throws DoubleDefException {
    	if (map.get(name) == null) {
    		map.put(name,  def);
    	} else throw new DoubleDefException();
    }
    	
}
