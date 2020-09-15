package fr.femtost.disc.glcomp.m1comp6.memory;

public class UnknownSymbolException extends Exception {
    public UnknownSymbolException(String ident, String scope) {
        super("The symbol \"" + ident + "\" does not exist in the scope \"" + scope + "\".");
    }
}
