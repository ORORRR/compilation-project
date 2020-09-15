package fr.femtost.disc.glcomp.m1comp6.memory;

public class InvalidConstantAssignmentException extends MemoryException {
    public InvalidConstantAssignmentException(SymbolNode symbolNode) {
        super("Invalid constant assignment: " + symbolNode.getIdent() + " is already set");
    }
}
