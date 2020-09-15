package fr.femtost.disc.glcomp.m1comp6.compiler;

public class Compiler {
    private final fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja node;

    private final CompilerVisitor compilerVisitor;
    private final CompilerPrinterVisitor compilerPrinterVisitor;

    public Compiler(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja node) {
        this.node = node;

        compilerVisitor = new CompilerVisitor();
        compilerPrinterVisitor = new CompilerPrinterVisitor();
    }

    public void setInstrs(fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeInstrs instrs) {
        compilerVisitor.setInstrs(instrs);
    }

    public void compile() throws CompilerException {
        node.accept(compilerVisitor, 1, CompilerMode.DEFAULT);

        compilerVisitor.setLineAndColumnNumbers();
    }

    public String print(fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeInstrs instrs) throws CompilerPrinterException {
        StringBuilder buffer = new StringBuilder();

        compilerPrinterVisitor.setBuffer(buffer);

        instrs.accept(compilerPrinterVisitor);

        return buffer.toString();
    }
}
