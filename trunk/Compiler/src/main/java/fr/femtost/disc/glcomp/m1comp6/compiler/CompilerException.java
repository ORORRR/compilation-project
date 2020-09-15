package fr.femtost.disc.glcomp.m1comp6.compiler;

import fr.femtost.disc.glcomp.m1comp6.ast.common.LocatableException;

public class CompilerException extends LocatableException {
    public CompilerException(String message) {
        super(message);
    }

    public CompilerException(String message, int line, int column) {
        super(message, line, column);
    }
}
