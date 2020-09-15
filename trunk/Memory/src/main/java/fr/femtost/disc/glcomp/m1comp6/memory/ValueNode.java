package fr.femtost.disc.glcomp.m1comp6.memory;

import fr.femtost.disc.glcomp.m1comp6.ast.common.Kind;
import fr.femtost.disc.glcomp.m1comp6.ast.common.Type;
import fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMethod;

public class ValueNode<T> {
    private T value;
    private SymbolNode parent;

    public ValueNode() throws InvalidTypeException {
        this(null);
    }

    public ValueNode(T value) throws InvalidTypeException {
        this(value, null);
    }

    /**
     * Constructor for ValueNode with value and parent symbol.
     *
     * @param value  The value to set the ValueNode with.
     * @param parent The parent SymbolNode of the ValueNode.
     * @throws InvalidTypeException Value and parent types do not match.
     */
    public ValueNode(T value, SymbolNode parent) throws InvalidTypeException {
        if (isInvalidType(value, parent)) {
            throw new InvalidTypeException(value, parent);
        }

        this.value = value;
        this.parent = parent;
    }

    /**
     * Check the correspondence of types between a value and its parent symbol.
     *
     * @param value  The value to check.
     * @param parent The parent symbol of the value.
     * @return True if the types do not match, false otherwise.
     */
    public static <T> boolean isInvalidType(T value, SymbolNode parent) {
        if (value == null || parent == null) {
            return false;
        }

        Type type = parent.getType();
        Kind kind = parent.getKind();

        if (kind == Kind.ARRAY) {
            // Assignment of array (the value of an array is always an integer because it's a heap key)
            return !(value instanceof Integer);
        }

        if (kind == Kind.METHOD) {
            // Assignment of method
            return !(value instanceof Integer || value instanceof NodeMethod);
        }

        if (kind == Kind.VARIABLE && type == null) {
            // Assignment of class variable
            return !(value instanceof Integer || value instanceof Boolean);
        }

        return !type.getTypeClass().isInstance(value);
    }

    /**
     * Check the correspondence of types between a value and an array symbol
     *
     * @param value  The value to check.
     * @param arraySymbol The array symbol of the value.
     * @return True if the types do not match, false otherwise.
     */
    public static <T> boolean isInvalidTypeArray(T value, SymbolNode arraySymbol) {

        Type type = arraySymbol.getType();

        return !type.getTypeClass().isInstance(value);
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) throws InvalidTypeException {
        if (isInvalidType(value, parent)) {
            throw new InvalidTypeException(value, parent);
        }

        this.value = value;
    }

    public SymbolNode getParent() {
        return parent;
    }

    public void setParent(SymbolNode parent) throws InvalidTypeException {
        if (isInvalidType(value, parent)) {
            throw new InvalidTypeException(value, parent);
        }

        this.parent = parent;
    }

    /**
     * Get the ident of the parent of the ValueNode.
     *
     * @return The parent ident.
     */
    public String getIdent() {
        if (parent == null) {
            return null;
        }

        return parent.getIdent();
    }

    /**
     * Get the kind of the parent of the ValueNode.
     *
     * @return The parent kind.
     */
    public Kind getKind() {
        if (parent == null) {
            return Kind.CONSTANT;
        }

        return parent.getKind();
    }

    /**
     * Get the type of the parent of the ValueNode.
     *
     * @return The parent type.
     */
    public Type getType() {
        if (parent == null) {
            return null;
        }

        return parent.getType();
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();

        if (value instanceof NodeMethod) {
            buffer.append("NodeMethod(");
            buffer.append(((NodeMethod) value).getIdentValue());
            buffer.append(")");
        } else {
            buffer.append(value);
        }

        return buffer.toString();
    }
}
