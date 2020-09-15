package fr.femtost.disc.glcomp.m1comp6.typechecker.mjj;

import fr.femtost.disc.glcomp.m1comp6.ast.common.VisitorException;

public class TypeCheckerException extends VisitorException {
    public TypeCheckerException(String message) {
        super(message);
    }
}
