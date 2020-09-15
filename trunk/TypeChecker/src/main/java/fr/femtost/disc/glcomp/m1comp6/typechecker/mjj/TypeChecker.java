package fr.femtost.disc.glcomp.m1comp6.typechecker.mjj;

import fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja;
import fr.femtost.disc.glcomp.m1comp6.memory.SymbolTable;

public class TypeChecker {
    public static final int FIRST_PASS = 0;
    public static final int SECOND_PASS = 1;

    public static final String SCOPE_GLOBAL = "global";
    public static final String SCOPE_MAIN = "main";

    private final NodeMiniJaja node;

    private final TypeCheckerVisitor typeCheckerVisitor;

    public TypeChecker(NodeMiniJaja node) {
        this.node = node;

        typeCheckerVisitor = new TypeCheckerVisitor();
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        typeCheckerVisitor.setSymbolTable(symbolTable);
    }

    public void typeCheck() throws TypeCheckerException {
        // First pass: add decls to symbol table
        node.accept(typeCheckerVisitor, SCOPE_GLOBAL, FIRST_PASS, TypeCheckerMode.ANY);

        // Second pass: type check
        node.accept(typeCheckerVisitor, SCOPE_GLOBAL, SECOND_PASS, TypeCheckerMode.ANY);
    }
}
