package fr.femtost.disc.glcomp.m1comp6.ast.mjj;

import fr.femtost.disc.glcomp.m1comp6.ast.common.MiniJajaASTVisitor;
import fr.femtost.disc.glcomp.m1comp6.ast.common.Node;

public class NodeNumber extends NodeMiniJaja {
    private int value;

    public NodeNumber(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public <T, E extends Throwable> T accept(MiniJajaASTVisitor<T, E> visitor, Object... args) throws E {
        return visitor.visit(this, args);
    }

    @Override
    public boolean hasSameValueThan(Node node) {
        int nodeValue = ((NodeNumber) node).getValue();

        return getValue() == nodeValue;
    }

    @Override
    protected String toString(int indent) {
        StringBuilder str = new StringBuilder(super.toString(indent));

        str.append(" <").append(value).append(">");

        return str.toString();
    }
}
