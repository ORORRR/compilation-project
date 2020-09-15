package fr.femtost.disc.glcomp.m1comp6.memory;

import fr.femtost.disc.glcomp.m1comp6.ast.common.Kind;
import fr.femtost.disc.glcomp.m1comp6.ast.common.Type;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class SymbolNode {
    private final String ident;
    private final Kind kind;
    private final Type type;

    private final List<ValueNode> values; //TODO: no usage of values -> maybe remove it

    public SymbolNode(String ident, Kind kind, Type type) {
        this.ident = ident;
        this.kind = kind;
        this.type = type;

        values = new ArrayList<>();
    }

    public String getIdent() {
        return ident;
    }

    public Kind getKind() {
        return kind;
    }

    public Type getType() {
        return type;
    }

    public List<ValueNode> getValues() {
        return values;
    }

    /**
     * Check if a symbol has the given ident in the given scope.
     *
     * @param ident The ident to check.
     * @return True if the symbol match the ident in the scope, false otherwise.
     */
    public boolean hasIdent(String ident) {
        return this.ident.equals(ident);
    }

    /**
     * Check if a symbol has value(s).
     *
     * @return True if the symbol has value(s), false otherwise.
     */
    public boolean hasValues() {
        return !values.isEmpty();
    }

    /**
     * Add a value to the list of values of the symbol.
     *
     * @param value The value to add to the list of values.
     * @return The value that has been created.
     */
    public <T> ValueNode<T> addValue(T value) throws InvalidTypeException {
        ValueNode<T> valueNode = new ValueNode<T>(value, this);

        values.add(valueNode);

        return valueNode;
    }

    /**
     * Add a value to the list of values of the symbol.
     *
     * @param valueNode The ValueNode to add to the list of values.
     * @return The value that has been added.
     * @throws WrongSymbolException The parent of the value does not match the current symbol.
     */
    public <T> ValueNode<T> addValue(ValueNode<T> valueNode) throws WrongSymbolException {
        if (valueNode.getParent() != this) {
            throw new WrongSymbolException(valueNode, this);
        }

        values.add(valueNode);

        return valueNode;
    }

    /**
     * Remove a value from the list of values of the symbol.
     *
     * @param valueNode The value to remove from the list.
     * @return The value that has been removed.
     */
    public <T> ValueNode<T> removeValue(ValueNode<T> valueNode) {
        values.remove(valueNode);

        return valueNode;
    }

    /**
     * Check if two SymbolNode are equals, i.e. two symbols represent the exact same symbol.
     *
     * @param obj The SymbolNode to compare with.
     * @return True if the symbols are equals, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SymbolNode)) {
            return false;
        }

        SymbolNode symbolNode = (SymbolNode) obj;

        // Symbols are the same if their idents are equal
        return hasIdent(symbolNode.getIdent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(ident);
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder("{");

        buffer.append(ident);
        buffer.append(", ");
        buffer.append(kind);
        buffer.append(", ");
        buffer.append(type);

        buffer.append("} -> [");

        Iterator<ValueNode> iterator = values.iterator();

        while (iterator.hasNext()) {
            buffer.append(iterator.next());

            if (iterator.hasNext()) {
                buffer.append(", ");
            }
        }

        buffer.append("]");

        return buffer.toString();
    }
}
