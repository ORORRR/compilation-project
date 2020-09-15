package fr.femtost.disc.glcomp.m1comp6.memory;

import fr.femtost.disc.glcomp.m1comp6.ast.common.Type;

public class InvalidTypeException extends MemoryException {
    public <T> InvalidTypeException(T value, SymbolNode parent) {
        super("The provided value \"" + value + "\" is not compatible with " + parent.getType() + " type");
    }

    public <T> InvalidTypeException(T value, Type type) {
        super("The provided value \"" + value + "\" is not compatible with " + type + " type");
    }
}
