package fr.femtost.disc.glcomp.m1comp6.ast.mjj;

import fr.femtost.disc.glcomp.m1comp6.ast.common.MiniJajaASTVisitor;
import fr.femtost.disc.glcomp.m1comp6.ast.common.Node;

public class NodeIdent extends NodeMiniJaja {
    public static final String SCOPE_DIVIDER = "@";
    public static final String SIGNATURE_DIVIDER = ":";
    public static final String HEADER_DIVIDER = "_";

    private String value;
    private String scope;
    private String signature;

    public NodeIdent(String value) {
        this.value = value;
    }

    public NodeIdent(String value, String scope) {
        this.value = value;
        this.scope = scope;
    }

    public NodeIdent(String value, String scope, String signature) {
        this.value = value;
        this.scope = scope;
        this.signature = signature;
    }

    public String getValue() {
        return value;
    }

    public String getScope() {
        return scope;
    }

    public String getSignature() {
        return signature;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String generateNormalIdent() {
        return value + SCOPE_DIVIDER + scope;
    }

    public String generateMethodIdent() {
        return value + SIGNATURE_DIVIDER + signature;
    }

    @Override
    public <T, E extends Throwable> T accept(MiniJajaASTVisitor<T, E> visitor, Object... args) throws E {
        return visitor.visit(this, args);
    }

    @Override
    public boolean hasSameValueThan(Node node) {
        String nodeValue = ((NodeIdent) node).getValue();

        return value.equals(nodeValue);
    }

    @Override
    protected String toString(int indent) {
        StringBuilder buffer = new StringBuilder(super.toString(indent));

        buffer.append(" <").append(value).append(">");

        return buffer.toString();
    }
}
