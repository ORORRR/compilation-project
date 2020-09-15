package fr.femtost.disc.glcomp.m1comp6.compiler;

import fr.femtost.disc.glcomp.m1comp6.ast.common.VisitorException;

public class CompilerException extends VisitorException {
    public CompilerException(String message) {
        super(message);
    }
}
