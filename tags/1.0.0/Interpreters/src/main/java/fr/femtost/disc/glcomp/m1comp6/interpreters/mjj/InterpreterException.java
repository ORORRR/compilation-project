package fr.femtost.disc.glcomp.m1comp6.interpreters.mjj;

import fr.femtost.disc.glcomp.m1comp6.ast.common.VisitorException;

public class InterpreterException extends VisitorException {
    public InterpreterException(String message) {
        super(message);
    }
}
