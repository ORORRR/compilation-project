package fr.femtost.disc.glcomp.m1comp6.memory;

public class WrongSymbolException extends MemoryException {
    public WrongSymbolException(ValueNode valueNode, SymbolNode symbolNode) {
        super("The symbol \"" + symbolNode.getIdent() + "\" is not set as the parent of the value \"" + valueNode.getValue() + "\"");
    }
}
