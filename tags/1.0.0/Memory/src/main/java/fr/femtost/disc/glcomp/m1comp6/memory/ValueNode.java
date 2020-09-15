package fr.femtost.disc.glcomp.m1comp6.memory;

import fr.femtost.disc.glcomp.m1comp6.ast.common.Kind;
import fr.femtost.disc.glcomp.m1comp6.ast.common.Type;

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
     * @param   value   The value to set the ValueNode with.
     * @param   parent  The parent SymbolNode of the ValueNode.
     * @throws  InvalidTypeException Value and parent types do not match.
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
     * @param   value   The value to check.
     * @param   parent  The parent symbol of the value.
     * @return  True if the types do not match, false otherwise.
     */
    private boolean isInvalidType(T value, SymbolNode parent) {
        if (value == null || parent == null) {
            return false;
        }

        Type type = parent.getType();
        Class typeClass = type.getTypeClass();

        return !typeClass.isInstance(value);
    }

    /**
     * Check if a value has the given ident in the given scope.
     * @param   ident   The ident to check.
     * @param   scope   The scope to check.
     * @return  True if the value match the ident in the scope, false otherwise.
     */
    public boolean hasIdent(String ident, String scope) {
        if (parent == null) {
            return false;
        }

        return parent.hasIdent(ident, scope);
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
     * @return  The parent ident.
     */
    public String getIdent(){
        if (parent == null) {
            return null;
        }

        return parent.getIdent();
    }

    /**
     * Get the scope of the parent of the ValueNode.
     * @return  The parent scope.
     */
    public String getScope() {
        if (parent == null) {
            return null;
        }

        return parent.getScope();
    }

    /**
     * Get the kind of the parent of the ValueNode.
     * @return  The parent kind.
     */
    public Kind getKind() {
        if (parent == null) {
            return Kind.CONSTANT;
        }

        return parent.getKind();
    }

    /**
     * Get the type of the parent of the ValueNode.
     * @return  The parent type.
     */
    public Type getType(){
        if (parent == null) {
            return null;
        }

        return parent.getType();
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
