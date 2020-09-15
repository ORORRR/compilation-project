package fr.femtost.disc.glcomp.m1comp6.memory;

public class NotFoundValueException extends Exception {
    public NotFoundValueException(int position) {
        super("No value at position " + position + " in the stack.");
    }

    public NotFoundValueException(String ident, String scope) {
        super("No value for symbol \"" + ident + "\" with scope \"" + scope + "\" found in the stack.");
    }
}
