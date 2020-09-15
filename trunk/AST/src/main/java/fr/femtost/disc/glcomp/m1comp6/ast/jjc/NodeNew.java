package fr.femtost.disc.glcomp.m1comp6.ast.jjc;

import fr.femtost.disc.glcomp.m1comp6.ast.common.JajaCodeASTVisitor;
import fr.femtost.disc.glcomp.m1comp6.ast.common.Kind;
import fr.femtost.disc.glcomp.m1comp6.ast.common.Type;

public class NodeNew extends NodeJajaCode {
    @Override
    public <T, E extends Throwable> T accept(JajaCodeASTVisitor<T, E> visitor, Object... args) throws E {
        return visitor.visit(this, args);
    }

    public String getIdent() {
        return ((NodeIdent) this.getChildren().get(0)).getValue();
    }

    public Type getType() {
        return ((NodeType) this.getChildren().get(1)).getValue();
    }

    public Kind getKind() {
        return ((NodeKind) this.getChildren().get(2)).getValue();
    }

    public int getValuePosition() {
        return ((NodeNumber) this.getChildren().get(3)).getValue();
    }
}
