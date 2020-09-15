package fr.femtost.disc.glcomp.m1comp6.compiler;

import fr.femtost.disc.glcomp.m1comp6.ast.common.VisitorException;

public class CompilerPrinterException extends VisitorException {
    public CompilerPrinterException(String message) {
        super(message);
    }
}
