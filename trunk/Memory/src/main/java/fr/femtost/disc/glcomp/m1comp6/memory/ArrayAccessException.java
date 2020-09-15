package fr.femtost.disc.glcomp.m1comp6.memory;

import fr.femtost.disc.glcomp.m1comp6.ast.common.Kind;

public class ArrayAccessException extends MemoryException {
    public ArrayAccessException(Kind actual) {
        super("Tried to do an array operation on a symbol of kind " + actual);
    }
}
