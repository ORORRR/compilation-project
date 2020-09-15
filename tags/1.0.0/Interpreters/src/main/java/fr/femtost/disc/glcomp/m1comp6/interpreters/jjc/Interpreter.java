package fr.femtost.disc.glcomp.m1comp6.interpreters.jjc;

import fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeJajaCode;
import fr.femtost.disc.glcomp.m1comp6.memory.Memory;

public class Interpreter {
    private NodeJajaCode node ;
    private InterpreterVisitor interpreterVisitor;


    public Interpreter(NodeJajaCode node) {
        this.node = node ;
        this.interpreterVisitor = new InterpreterVisitor();
    }

    public void eval(Memory memory) throws InterpreterException {
        interpreterVisitor.setMemory(memory);
        node.accept(interpreterVisitor);
    }
}
