package fr.femtost.disc.glcomp.m1comp6.interpreters.mjj;

import fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja;
import fr.femtost.disc.glcomp.m1comp6.debuggers.DebuggableInterpreter;
import fr.femtost.disc.glcomp.m1comp6.debuggers.Debugger;
import fr.femtost.disc.glcomp.m1comp6.memory.Memory;

public class Interpreter implements DebuggableInterpreter {
    private final NodeMiniJaja node;

    private final InterpreterVisitor interpreterVisitor;

    private Debugger debugger;

    public Interpreter(NodeMiniJaja node) {
        this.node = node;

        interpreterVisitor = new InterpreterVisitor();
    }

    @Override
    public void setDebugger(Debugger debugger) {
        this.debugger = debugger;

        interpreterVisitor.setDebugger(debugger);
    }

    public void setMemory(Memory memory) {
        interpreterVisitor.setMemory(memory);
    }

    /**
     * Evaluate the current AST.
     *
     * @throws InterpreterException Errors occured during interpretation.
     */
    public void eval() throws InterpreterException {
        node.accept(interpreterVisitor, InterpreterMode.DEFAULT);
    }

    @Override
    public void run() {
        // Activate debugger
        interpreterVisitor.setDebuggerActive(true);

        // Run MiniJaja
        eval();

        // Notify the end
        debugger.handle(true, null);
    }
}
