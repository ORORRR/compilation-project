package fr.femtost.disc.glcomp.m1comp6.memory;

public class StackException extends MemoryException {
    public StackException(String message) {
        super("Invalid stack operation: " + message);
    }
}
