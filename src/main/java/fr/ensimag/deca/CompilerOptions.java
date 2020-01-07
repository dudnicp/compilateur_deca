package fr.ensimag.deca;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * User-specified options influencing the compilation.
 *
 * @author gl28
 * @date 01/01/2020
 */
public class CompilerOptions {
    public static final int QUIET = 0;
    public static final int INFO  = 1;
    public static final int DEBUG = 2;
    public static final int TRACE = 3;
    public int getDebug() {
        return debug;
    }

    public boolean getParallel() {
        return parallel;
    }

    public boolean getPrintBanner() {
        return printBanner;
    }
    
    public boolean getParse() {
    	return parse;
    }
    
    public boolean getVerification() {
    	return verification;
    }
    
    public List<File> getSourceFiles() {
        return Collections.unmodifiableList(sourceFiles);
    }

    private int debug = 0;
    private boolean parallel = false;
    private boolean printBanner = false;
    private boolean parse = false;
    private boolean verification = false;
    private List<File> sourceFiles = new ArrayList<File>();

    
    public void parseArgs(String[] args) throws CLIException {
        // A FAIRE : parcourir args pour positionner les options correctement.
    	
    	for (String argument : args) {
    		if (argument == "-b")
    			printBanner = true;
    		
    		if (argument == "-p") {
    			if (getVerification() == true)
    				throw new UnsupportedOperationException("Les options -p et -v sont incompatibles");
    			// A FAIRE : gestion de l'argument parse (cf page 101)
    		}
    		
    		if (argument == "-v") {
    			if (getParse() == true)
    				throw new UnsupportedOperationException("Les options -p et -v sont incompatibles");
    			// A FAIRE : gestion de l'argument verification (cf page 101 polycopie)
    		}
    		
    		// A FAIRE : gestion des autres options ... (cf page 101 poly)
    		System.out.println(argument);
    		
    		// A FAIRE : gestion pas du tout bien du fichier source...
    		sourceFiles.add(new File(argument));
    	}
    	
        Logger logger = Logger.getRootLogger();
        // map command-line debug option to log4j's level.
        switch (getDebug()) {
        case QUIET: break; // keep default
        case INFO:
            logger.setLevel(Level.INFO); break;
        case DEBUG:
            logger.setLevel(Level.DEBUG); break;
        case TRACE:
            logger.setLevel(Level.TRACE); break;
        default:
            logger.setLevel(Level.ALL); break;
        }
        logger.info("Application-wide trace level set to " + logger.getLevel());

        boolean assertsEnabled = false;
        assert assertsEnabled = true; // Intentional side effect!!!
        if (assertsEnabled) {
            logger.info("Java assertions enabled");
        } else {
            logger.info("Java assertions disabled");
        }

    }

    protected void displayUsage() {
        throw new UnsupportedOperationException("not yet implemented");
    }
}
