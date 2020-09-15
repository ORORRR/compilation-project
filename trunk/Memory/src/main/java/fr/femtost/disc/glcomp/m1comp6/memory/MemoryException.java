package fr.femtost.disc.glcomp.m1comp6.memory;

public class MemoryException extends Exception {
    public MemoryException(String text) {
        super("Memory error: " + text + ".");
    }
}
