package fr.femtost.disc.glcomp.m1comp6.ast.common;

import java.util.Stack;

public abstract class LocatableRuntimeException extends RuntimeException implements Locatable {
    private final int line;
    private final int column;

    private Stack<CallStackElement> callStack;

    public LocatableRuntimeException(String message) {
        this(message, 0, 0, null);
    }

    public LocatableRuntimeException(String message, int line, int column) {
        this(message, line, column, null);
    }

    public LocatableRuntimeException(String message, int line, int column, Stack<CallStackElement> callStack) {
        super(message);

        this.line = line;
        this.column = column;
        this.callStack = callStack;
    }

    @Override
    public int getLine() {
        return line;
    }

    @Override
    public int getColumn() {
        return column;
    }

    public Stack<CallStackElement> getCallStack() {
        return callStack;
    }
}
