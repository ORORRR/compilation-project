package fr.femtost.disc.glcomp.m1comp6.memory;

public class InvalidConstantAssignmentException extends Exception {
    public InvalidConstantAssignmentException(SymbolNode symbolNode) {
        super("Invalid assignment to constant : "+symbolNode.getIdent()+"("+symbolNode.getScope()+") is already set");
    }
}
