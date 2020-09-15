package fr.femtost.disc.glcomp.m1comp6.interpreters.jjc;

import fr.femtost.disc.glcomp.m1comp6.ast.common.CallStackElement;
import fr.femtost.disc.glcomp.m1comp6.ast.common.LocatableRuntimeException;

import java.util.Stack;

public class InterpreterException extends LocatableRuntimeException {
    public InterpreterException(String message) {
        super(message);
    }

    public InterpreterException(String message, int line, int column) {
        super(message, line, column);
    }

    public InterpreterException(String message, int line, int column, Stack<CallStackElement> callStack) {
        super(message, line, column, callStack);
    }
}
