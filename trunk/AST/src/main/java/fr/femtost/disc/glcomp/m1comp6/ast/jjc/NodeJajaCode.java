package fr.femtost.disc.glcomp.m1comp6.ast.jjc;

import fr.femtost.disc.glcomp.m1comp6.ast.common.*;

public abstract class NodeJajaCode extends Node implements JajaCodeASTVisitable {
    @Override
    public <T, E extends Throwable> T accept(JajaCodeASTVisitor<T, E> visitor, Object... args) throws E {
        return visitor.visit(this, args);
    }
}
