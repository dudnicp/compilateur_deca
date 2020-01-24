package fr.ensimag.deca.context;

import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;


public abstract class Environment {
	private SymbolTable symbolMap;
	private Environment parent;
	private Map<Symbol, Definition> definitionMap;

	public SymbolTable getSymbolMap() {
		return symbolMap;
	}

	public Environment getParent() {
		return parent;
	}
	
	public Map<Symbol, Definition> getDefinitionMap() {
		return definitionMap;
	}

	public Environment(Environment parent) {
		this.parent = parent;
		this.symbolMap = new SymbolTable();
		this.definitionMap = new HashMap<Symbol, Definition>();
	}
	
	public Symbol getSymbolFromMap(String name) {
		return symbolMap.get(name);
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
     * Return the definition of the symbol in the environment, or null if the
     * symbol is undefined.
     */
	public Definition get(Symbol key) {
		return definitionMap.get(key);
	}
	
	public Definition getDefinitionFromName(String name) {
		return this.get(this.createSymbol(name));
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
    public abstract void declare(Symbol name, Definition def) throws DoubleDefException;
    
    /**
     * Look for a symbol in this environment then call in all of its parents
     * meaning it goes up in the linked environment scheme
     * 
     * @param name
     * @throws DoubleDefException
     * 			if the symbol is already defined in any environment linked to this one
     */
    public void isDefined(Symbol name) throws DoubleDefException{
    	if (this.get(name) == null) {
    		if (parent != null) {
    			parent.isDefined(name); // look up parent environment
    		} else {
    			return; // stop here - no previous definition of symbol "name"
    		}
    	} else {
    		throw new DoubleDefException();
    	}
    	
    }
    
}
