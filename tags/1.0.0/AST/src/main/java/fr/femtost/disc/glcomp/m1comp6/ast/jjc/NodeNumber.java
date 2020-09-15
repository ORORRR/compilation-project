package fr.femtost.disc.glcomp.m1comp6.ast.jjc;

import fr.femtost.disc.glcomp.m1comp6.ast.common.JajaCodeASTVisitor;

public class NodeNumber extends NodeJajaCode{
    private int value;

    public NodeNumber(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public <T, E extends Throwable> T accept(JajaCodeASTVisitor<T, E> visitor, Object... args) throws E {
        return visitor.visit(this, args);
    }

    @Override
    protected String toString(int indent) {
        StringBuilder str = new StringBuilder(super.toString(indent));

        str.append(" (").append(value).append(")");

        return str.toString();
    }
}
