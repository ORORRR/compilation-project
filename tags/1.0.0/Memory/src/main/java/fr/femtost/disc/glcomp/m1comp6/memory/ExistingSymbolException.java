package fr.femtost.disc.glcomp.m1comp6.memory;

public class ExistingSymbolException extends Exception {
    public ExistingSymbolException(SymbolNode symbolNode) {
        super("The symbol {" + symbolNode.getIdent() + ", " + symbolNode.getScope() + ", " + symbolNode.getKind() + ", " + symbolNode.getType() + "} already exists in the symbol table.");
    }
}
