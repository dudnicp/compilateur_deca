package fr.ensimag.deca;

import java.io.File;
import org.apache.log4j.Logger;

import fr.ensimag.ima.pseudocode.Register;

/**
 * Main class for the command-line Deca compiler.
 *
 * @author gl28
 * @date 01/01/2020
 */
public class DecacMain {
    private static Logger LOG = Logger.getLogger(DecacMain.class);
    
    public static void main(String[] args) {
        // example log4j message.
        LOG.info("Decac compiler started");
        boolean error = false;
        final CompilerOptions options = new CompilerOptions();
        try {
            options.parseArgs(args);
        } catch (CLIException e) {
            System.err.println("Error during option parsing:\n"
                    + e.getMessage());
            options.displayUsage();
            System.exit(1);
        }
        if (options.getPrintBanner()) {
        	// -b
            System.out.println("GL28");
        }
        if (options.getParse()) {
        	
        }
        if (options.getVerification()) {
        	// -v
        	throw new UnsupportedOperationException("decac -v not yet implemented");
//        	if (options.getParse()) {
//        		throw new UnsupportedOperationException("-p and -v incompatible");
//        	}
        }
        if (options.getNocheck()) {
        	// -n
        	throw new UnsupportedOperationException("decac -n not yet implemented");
        }
        if (options.getRegisters() != -1) {
        	// -r
        	Register.setRMAX(options.getRegisters());
        }
        if (options.getDebug() != 0) {
        	// -d
        	throw new UnsupportedOperationException("decac -d not yet implemented");
        }
        if (options.getSourceFiles().isEmpty()) {
            throw new UnsupportedOperationException("decac without argument not yet implemented");
        }
        if (options.getParallel()) {
        	// -P
            // A FAIRE : instancier DecacCompiler pour chaque fichier à
            // compiler, et lancer l'exécution des méthodes compile() de chaque
            // instance en parallèle. Il est conseillé d'utiliser
            // java.util.concurrent de la bibliothèque standard Java.
            throw new UnsupportedOperationException("Parallel build not yet implemented");
        } else {
            for (File source : options.getSourceFiles()) {
                DecacCompiler compiler = new DecacCompiler(options, source);
                if (compiler.compile()) {
                    error = true;
                }
            }
        }
        System.exit(error ? 1 : 0);
    }
}
