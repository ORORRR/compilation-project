package fr.femtost.disc.glcomp.m1comp6.memory;

public class IdentValInvalidArgumentException extends Exception {
    public IdentValInvalidArgumentException(String text) {
        super("Invalid argument for IdentVal: " + text + ".");
    }
}
