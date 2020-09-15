package fr.femtost.disc.glcomp.m1comp6.typechecker.mjj;

import fr.femtost.disc.glcomp.m1comp6.ast.common.LocatableException;

public class TypeCheckerException extends LocatableException {
    public TypeCheckerException(String message) {
        super(message);
    }

    public TypeCheckerException(String message, int line, int column) {
        super(message, line, column);
    }
}
