package fr.femtost.disc.glcomp.m1comp6.interpreters.mjj;

import fr.femtost.disc.glcomp.m1comp6.memory.Memory;

import fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja;

public class Interpreter {
    private final NodeMiniJaja node;

    private final InterpreterVisitor interpreterVisitor;

    public Interpreter(NodeMiniJaja node) {
        this.node = node;

        interpreterVisitor = new InterpreterVisitor();
    }

    public void eval(Memory memory) throws InterpreterException {
        interpreterVisitor.setMemory(memory);

        node.accept(interpreterVisitor, InterpreterMode.DEFAULT, "global");
    }
}
