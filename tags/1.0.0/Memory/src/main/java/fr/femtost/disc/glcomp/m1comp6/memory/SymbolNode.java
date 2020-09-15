package fr.femtost.disc.glcomp.m1comp6.memory;

import fr.femtost.disc.glcomp.m1comp6.ast.common.Kind;
import fr.femtost.disc.glcomp.m1comp6.ast.common.Type;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SymbolNode {
    private final String ident;
    private final String scope;
    private final Kind kind;
    private final Type type;

    private final List<ValueNode> values;

    public SymbolNode(String ident, String scope, Kind kind, Type type) {
        this.ident = ident;
        this.scope = scope;
        this.kind = kind;
        this.type = type;

        values = new ArrayList<ValueNode>();
    }

    public String getIdent() {
        return ident;
    }

    public String getScope() {
        return scope;
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
     * @param   ident   The ident to check.
     * @param   scope   The scope to check.
     * @return  True if the symbol match the ident in the scope, false otherwise.
     */
    public boolean hasIdent(String ident, String scope) {
        return this.ident.equals(ident) && this.scope.equals(scope);
    }

    /**
     * Check if a symbol has value(s).
     * @return  True if the symbol has value(s), false otherwise.
     */
    public boolean hasValues() {
        return !values.isEmpty();
    }

    /**
     * Add a value to the list of values of the symbol.
     * @param   value   The value to add to the list of values.
     * @return  The value that has been created.
     */
    public <T> ValueNode<T> addValue(T value) throws InvalidTypeException {
        ValueNode<T> valueNode = new ValueNode<T>(value, this);

        values.add(valueNode);

        return valueNode;
    }

    /**
     * Add a value to the list of values of the symbol.
     * @param   valueNode   The ValueNode to add to the list of values.
     * @return  The value that has been added.
     * @throws  WrongSymbolException The parent of the value does not match the current symbol.
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
     * @param   valueNode   The value to remove from the list.
     * @return  The value that has been removed.
     */
    public <T> ValueNode<T> removeValue(ValueNode<T> valueNode) {
        values.remove(valueNode);

        return valueNode;
    }

    /**
     * Check if two SymbolNode are equals, i.e. two symbols represent the exact same symbol.
     * @param   obj The SymbolNode to compare with.
     * @return  True if the symbols are equals, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SymbolNode)) {
            return false;
        }

        SymbolNode symbolNode = (SymbolNode) obj;

        String ident = symbolNode.getIdent();
        String scope = symbolNode.getScope();

        // For now (as we are only dealing with variables), we consider that two
        // symbols represent the exact same symbol if they have the same ident and scope.
        return this.ident.equals(ident) && this.scope.equals(scope);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("{");

        str.append(ident);
        str.append(", ");
        str.append(scope);
        str.append(", ");
        str.append(kind);
        str.append(", ");
        str.append(type);

        str.append("} -> [");

        Iterator<ValueNode> iterator = values.iterator();

        while (iterator.hasNext()){
            str.append(iterator.next());

            if (iterator.hasNext()) {
                str.append(", ");
            }
        }

        str.append("]");

        return str.toString();
    }
}
