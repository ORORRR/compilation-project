package fr.femtost.disc.glcomp.m1comp6.debuggers;

import fr.femtost.disc.glcomp.m1comp6.ast.common.Node;

/**
 * This debugger will stop the interpreter at each encountered node (follows method calls and recursions).
 */
public class DebuggerStepInto extends Debugger {
    @Override
    public synchronized void onVisit(Node node) throws InterruptedException {
        super.onVisit(node);

        // Trigger the debuggerHandler when a node is visited
        handle(false, node);

        // Use a loop to avoid spurious wakeups (see https://docs.oracle.com/javase/7/docs/api/java/lang/Object.html#wait())
        while (isPaused) {
            // Make the calling thread waiting
            wait();
        }
    }
}
