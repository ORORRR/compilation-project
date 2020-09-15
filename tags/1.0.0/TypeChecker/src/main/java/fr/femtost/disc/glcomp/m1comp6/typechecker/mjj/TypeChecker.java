package fr.femtost.disc.glcomp.m1comp6.typechecker.mjj;

import fr.femtost.disc.glcomp.m1comp6.memory.SymbolTable;

import fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja;

public class TypeChecker {
    private final NodeMiniJaja node;

    private final TypeCheckerVisitor visitor;

    public TypeChecker(NodeMiniJaja node) {
        this.node = node;

        visitor = new TypeCheckerVisitor();
    }

    public void typeCheck(SymbolTable symbolTable) throws TypeCheckerException {
        visitor.setSymbolTable(symbolTable);

        node.accept(visitor);
    }
}
