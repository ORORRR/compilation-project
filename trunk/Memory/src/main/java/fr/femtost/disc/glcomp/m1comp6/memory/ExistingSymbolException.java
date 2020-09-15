package fr.femtost.disc.glcomp.m1comp6.memory;

public class ExistingSymbolException extends MemoryException {
    public ExistingSymbolException(SymbolNode symbolNode) {
        super("The symbol {" + symbolNode.getIdent() + ", " + symbolNode.getKind() + ", " + symbolNode.getType() + "} already exists in the symbol table");
    }
}
