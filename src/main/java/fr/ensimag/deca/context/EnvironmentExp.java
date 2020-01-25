package fr.ensimag.deca.context;

import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import java.util.HashMap;
import java.util.Map;


public class EnvironmentExp extends Environment{
	
    public EnvironmentExp(EnvironmentExp parentEnvironment) {
    	super(parentEnvironment);
    }
    
}
