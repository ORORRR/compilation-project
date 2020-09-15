package fr.femtost.disc.glcomp.m1comp6.ast.mjj;

import fr.femtost.disc.glcomp.m1comp6.ast.common.MiniJajaASTVisitor;

public class NodeHeader extends NodeMiniJaja {
    @Override
    public <T, E extends Throwable> T accept(MiniJajaASTVisitor<T, E> visitor, Object... args) throws E {
        return visitor.visit(this, args);
    }

    public NodeMiniJaja getType() {
        return (NodeMiniJaja) getChildren().get(0);
    }

    public NodeMiniJaja getIdent() {
        return (NodeMiniJaja) getChildren().get(1);
    }

    public String getIdentValue() {
        return ((NodeIdent) getIdent()).getValue();
    }

    public String getIdentScope() {
        return ((NodeIdent) getIdent()).getScope();
    }

    public void setIdentValue(String value) {
        ((NodeIdent) getIdent()).setValue(value);
    }

    public void setIdentScope(String scope) {
        ((NodeIdent) getIdent()).setScope(scope);
    }

    public String generateIdent() {
        return ((NodeIdent) getIdent()).generateNormalIdent();
    }
}
