package fr.femtost.disc.glcomp.m1comp6.compiler;

import fr.femtost.disc.glcomp.m1comp6.memory.SymbolTable;

public class Compiler {
    private final fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja node;

    private final CompilerVisitor compilerVisitor;
    private final CompilerPrinterVisitor compilerPrinterVisitor;

    public Compiler(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja node) {
        this.node = node;

        compilerVisitor = new CompilerVisitor();
        compilerPrinterVisitor = new CompilerPrinterVisitor();
    }

    public void compile(SymbolTable symbolTable, fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeInstrs instrs) throws CompilerException {
        compilerVisitor.setSymbolTable(symbolTable);
        compilerVisitor.setInstrs(instrs);

        node.accept(compilerVisitor, 0, CompilerMode.DEFAULT, null);
    }

    public String print(fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeInstrs instrs) throws CompilerPrinterException {
        StringBuilder buffer = new StringBuilder();

        compilerPrinterVisitor.setBuffer(buffer);

        instrs.accept(compilerPrinterVisitor);

        return buffer.toString();
    }
}
