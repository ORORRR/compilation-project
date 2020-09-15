package fr.femtost.disc.glcomp.m1comp6.debuggers;

import fr.femtost.disc.glcomp.m1comp6.ast.common.Node;

public interface DebuggerHandler {
    void handle(boolean isFinished, Node node);
}
