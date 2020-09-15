package fr.femtost.disc.glcomp.m1comp6.compiler;

import fr.femtost.disc.glcomp.m1comp6.ast.common.LocatableException;

public class CompilerPrinterException extends LocatableException {
    public CompilerPrinterException(String message) {
        super(message);
    }

    public CompilerPrinterException(String message, int line, int column) {
        super(message, line, column);
    }
}
