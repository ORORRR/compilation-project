package fr.femtost.disc.glcomp.m1comp6.memory;

import fr.femtost.disc.glcomp.m1comp6.ast.common.Kind;

public class WrongKindException extends Exception {
    public WrongKindException(Kind expected, Kind actual) {
        super("Tried to declare a value of kind " + actual + " in a symbol of kind " + expected + ".");
    }
}
