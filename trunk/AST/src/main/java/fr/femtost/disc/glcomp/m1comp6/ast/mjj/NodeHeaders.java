package fr.femtost.disc.glcomp.m1comp6.ast.mjj;

import fr.femtost.disc.glcomp.m1comp6.ast.common.MiniJajaASTVisitor;

public class NodeHeaders extends NodeMiniJaja {
    @Override
    public <T, E extends Throwable> T accept(MiniJajaASTVisitor<T, E> visitor, Object... args) throws E {
        return visitor.visit(this, args);
    }

    public NodeMiniJaja getHeader() {
        return (NodeMiniJaja) getChildren().get(0);
    }

    public NodeMiniJaja getHeaders() {
        return (NodeMiniJaja) getChildren().get(1);
    }
}
