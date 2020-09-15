package fr.femtost.disc.glcomp.m1comp6.ast.common;

import fr.femtost.disc.glcomp.m1comp6.ast.mjj.*;

public enum Type {
    BOOLEAN(Boolean.class),
    INTEGER(Integer.class),
    VOID(Void.class);

    private Class typeClass;

    Type(Class typeClass) {
        this.typeClass = typeClass;
    }

    public Class getTypeClass() {
        return typeClass;
    }

    public static Type fromString(String string) {
        if ("INTEGER".equals(string)) {
            return Type.INTEGER;
        }

        if ("BOOLEAN".equals(string)) {
            return Type.BOOLEAN;
        }

        if ("VOID".equals(string)) {
            return Type.VOID;
        }

        return null;
    }

    public static Type fromNode(NodeMiniJaja node) {
        if (node instanceof NodeInteger) {
            return Type.INTEGER;
        }

        if (node instanceof NodeBoolean) {
            return Type.BOOLEAN;
        }

        if (node instanceof NodeVoid) {
            return Type.VOID;
        }

        return null;
    }
}
