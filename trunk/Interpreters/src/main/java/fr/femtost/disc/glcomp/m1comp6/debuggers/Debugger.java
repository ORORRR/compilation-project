package fr.femtost.disc.glcomp.m1comp6.debuggers;

import fr.femtost.disc.glcomp.m1comp6.ast.common.Node;

import java.util.ArrayList;
import java.util.List;

public abstract class Debugger {
    protected DebuggableInterpreter interpreter;
    protected List<Node> breakpoints;

    protected DebuggerHandler debuggerHandler;
    protected Thread.UncaughtExceptionHandler exceptionHandler;

    protected Thread thread;

    // This flag should only be used as a condition for the waiting loop in onVisit
    protected boolean isPaused = false;

    public Debugger() {
        breakpoints = new ArrayList<>();
    }

    public void setInterpreter(DebuggableInterpreter interpreter) {
        this.interpreter = interpreter;

        // Bind the debugger to the interpreter
        interpreter.setDebugger(this);

        // Reset breakpoints
        breakpoints.clear();
    }

    /**
     * Add a breakpoint to the debugger.
     *
     * @param breakpoint The AST node that represents the breakpoint.
     */
    public void addBreakpoint(Node breakpoint) {
        breakpoints.add(breakpoint);
    }

    /**
     * Remove a breakpoint from the debugger.
     *
     * @param breakpoint The AST node that represents the breakpoint.
     */
    public void removeBreakpoint(Node breakpoint) {
        breakpoints.remove(breakpoint);
    }

    /**
     * Bind an event handler to the debugger. This debuggerHandler will be called according to the current debugger implementation.
     *
     * @param debuggerHandler The debuggerHandler to bind.
     */
    public void setDebuggerHandler(DebuggerHandler debuggerHandler) {
        this.debuggerHandler = debuggerHandler;
    }

    /**
     * Bind an exception handler to the debugger.
     *
     * @param exceptionHandler The exceptionHandler to bind.
     */
    public void setExceptionHandler(Thread.UncaughtExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    /**
     * Trigger the debugger handler.
     */
    public void handle(boolean isFinished, Node node) {
        if (debuggerHandler != null) {
            debuggerHandler.handle(isFinished, node);
        }
    }

    /**
     * Embed the interpreter in a thread and start it.
     */
    public void start() {
        thread = new Thread(interpreter);

        if (exceptionHandler != null) {
            thread.setUncaughtExceptionHandler(exceptionHandler);
        }

        thread.start();
    }

    /**
     * Stop the interpreter by interrupting the related thread.
     */
    public void stop() {
        if (thread != null) {
            thread.interrupt();
        }
    }

    /**
     * Go to the next breakpoint by notifying the interpreter thread.
     */
    public synchronized void next() {
        isPaused = false;

        notify();
    }

    /**
     * Hook that will be called by the interpreter when a node is visited. The used strategy is up to the concrete implementation.
     *
     * @param node The currently visited node.
     */
    public synchronized void onVisit(Node node) throws InterruptedException {
        // Activate the flag
        isPaused = true;
    }
}
