package fr.femtost.disc.glcomp.m1comp6.ast.mjj;

import fr.femtost.disc.glcomp.m1comp6.ast.common.MiniJajaASTVisitor;

public class NodeMethod extends NodeMiniJaja {
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

    public NodeMiniJaja getHeaders() {
        return (NodeMiniJaja) getChildren().get(2);
    }

    public NodeMiniJaja getVars() {
        return (NodeMiniJaja) getChildren().get(3);
    }

    public NodeMiniJaja getInstrs() {
        return (NodeMiniJaja) getChildren().get(4);
    }

    public String getIdentValue() {
        return ((NodeIdent) getIdent()).getValue();
    }

    public String getIdentSignature() {
        return ((NodeIdent) getIdent()).getSignature();
    }

    public void setIdentValue(String value) {
        ((NodeIdent) getIdent()).setValue(value);
    }

    public void setIdentSignature(String scope) {
        ((NodeIdent) getIdent()).setSignature(scope);
    }

    public String generateIdent() {
        return ((NodeIdent) getIdent()).generateMethodIdent();
    }
}
