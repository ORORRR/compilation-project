package fr.femtost.disc.glcomp.m1comp6.memory;

public class UnknownSymbolException extends MemoryException {
    public UnknownSymbolException(String ident) {
        super("Unknown symbol: " + ident);
    }
}
