package fr.femtost.disc.glcomp.m1comp6.interpreters.jjc;

import fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeJajaCode;
import fr.femtost.disc.glcomp.m1comp6.debuggers.DebuggableInterpreter;
import fr.femtost.disc.glcomp.m1comp6.debuggers.Debugger;
import fr.femtost.disc.glcomp.m1comp6.memory.Memory;

public class Interpreter implements DebuggableInterpreter {
    private NodeJajaCode node;

    private InterpreterVisitor interpreterVisitor;

    private Debugger debugger;

    public Interpreter(NodeJajaCode node) {
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
        node.accept(interpreterVisitor);
    }

    @Override
    public void run() {
        // Activate debugger
        interpreterVisitor.setDebuggerActive(true);

        // Run JajaCode
        eval();

        // Notify the end
        debugger.handle(true, null);
    }
}
