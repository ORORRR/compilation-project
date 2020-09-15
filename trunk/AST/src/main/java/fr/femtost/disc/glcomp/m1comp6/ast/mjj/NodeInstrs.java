package fr.femtost.disc.glcomp.m1comp6.ast.mjj;

import fr.femtost.disc.glcomp.m1comp6.ast.common.MiniJajaASTVisitor;

public class NodeInstrs extends NodeMiniJaja {
    @Override
    public <T, E extends Throwable> T accept(MiniJajaASTVisitor<T, E> visitor, Object... args) throws E {
        return visitor.visit(this, args);
    }

    public NodeMiniJaja getInstr() {
        return (NodeMiniJaja) getChildren().get(0);
    }

    public NodeMiniJaja getInstrs() {
        return (NodeMiniJaja) getChildren().get(1);
    }
}
