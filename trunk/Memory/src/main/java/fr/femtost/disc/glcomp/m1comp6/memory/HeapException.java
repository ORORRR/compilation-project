package fr.femtost.disc.glcomp.m1comp6.memory;

public class HeapException extends MemoryException {
    public HeapException(String text) {
        super("Invalid Heap operation: " + text);
    }
}
