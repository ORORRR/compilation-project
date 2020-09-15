package fr.femtost.disc.glcomp.m1comp6.memory;

public class NotFoundValueException extends StackException {
    public NotFoundValueException(int position) {
        super("No value at position " + position + " in the stack.");
    }

    public NotFoundValueException(SymbolNode symbol) {
        super("No value for symbol \"" + symbol.getIdent() + "\" found in the stack.");
    }
}
