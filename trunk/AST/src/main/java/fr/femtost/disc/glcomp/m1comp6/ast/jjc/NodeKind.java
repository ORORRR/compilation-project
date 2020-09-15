package fr.femtost.disc.glcomp.m1comp6.ast.jjc;

import fr.femtost.disc.glcomp.m1comp6.ast.common.JajaCodeASTVisitor;
import fr.femtost.disc.glcomp.m1comp6.ast.common.Kind;
import fr.femtost.disc.glcomp.m1comp6.ast.common.Node;

public class NodeKind extends NodeJajaCode {
    private Kind value;

    public NodeKind(Kind value) {
        this.value = value;
    }

    public Kind getValue() {
        return value;
    }

    @Override
    public <T, E extends Throwable> T accept(JajaCodeASTVisitor<T, E> visitor, Object... args) throws E {
        return visitor.visit(this, args);
    }

    @Override
    public boolean hasSameValueThan(Node node) {
        return getValue() == ((NodeKind) node).getValue();
    }

    @Override
    protected String toString(int indent) {
        StringBuilder str = new StringBuilder(super.toString(indent));

        str.append(" (").append(value).append(")");

        return str.toString();
    }
}
