package fr.femtost.disc.glcomp.m1comp6.ast.jjc;

import fr.femtost.disc.glcomp.m1comp6.ast.common.JajaCodeASTVisitor;

public class NodeGoto extends NodeJajaCode {
    @Override
    public <T, E extends Throwable> T accept(JajaCodeASTVisitor<T, E> visitor, Object... args) throws E {
        return visitor.visit(this, args);
    }

    public int getAddress(){
        return ((NodeNumber) this.getChildren().get(0)).getValue();
    }
}
