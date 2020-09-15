package fr.femtost.disc.glcomp.m1comp6.ast.common;

public abstract class LocatableException extends Exception implements Locatable {
    private final int line;
    private final int column;

    public LocatableException(String message) {
        this(message, 0, 0);
    }

    public LocatableException(String message, int line, int column) {
        super(message);

        this.line = line;
        this.column = column;
    }

    @Override
    public int getLine() {
        return line;
    }

    @Override
    public int getColumn() {
        return column;
    }
}
