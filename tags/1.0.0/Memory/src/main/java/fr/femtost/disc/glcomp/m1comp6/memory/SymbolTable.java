package fr.femtost.disc.glcomp.m1comp6.memory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SymbolTable {
    public static final int SIZE = 4;

    private final List<List<SymbolNode>> entries;

    public SymbolTable() {
        entries = new ArrayList<List<SymbolNode>>(SIZE);

        for (int i = 0 ; i < SIZE; ++i) {
            entries.add(new ArrayList<SymbolNode>());
        }
    }

    /**
     *  Put a new symbol in the symbol table.
     *  @param  symbolNode  The symbol to add to the table.
     *  @return True if the symbol has been added to the table, false otherwise.
     */
    public SymbolNode put(SymbolNode symbolNode) throws ExistingSymbolException {
        int hash = getHash(symbolNode.getIdent());

        List<SymbolNode> entry = entries.get(hash);

        if (entry.contains(symbolNode)) {
            throw new ExistingSymbolException(symbolNode);
        }

        entry.add(symbolNode);

        return symbolNode;
    }

    /**
     * Get the symbol matching the specified attributes.
     * @param   ident   The ident of the symbol to look for.
     * @param   scope   The scope of the symbol to look for.
     * @return  The symbol with the specified ident and scope, null if the table does not contain such symbol.
     */
    public SymbolNode get(String ident, String scope) {
        int hash = getHash(ident);

        List<SymbolNode> entry = entries.get(hash);

        // Loop through the list looking for the one that matches given ident and scope
        for (SymbolNode symbolNode : entry) {
            if (symbolNode.hasIdent(ident, scope)) {
                return symbolNode;
            }
        }

        // Not found symbol
        return null;
    }

    /**
     * Remove the symbol owning the specified attributes.
     * @param   ident   The ident of the symbol to look for.
     * @return  True if the symbol has been removed from the table, false otherwise.
     */
    public SymbolNode remove(String ident, String scope) {
        int hash = getHash(ident);

        List<SymbolNode> entry = entries.get(hash);

        // Loop through the list looking for the one that match given ident and scope
        Iterator<SymbolNode> it = entry.iterator();

        while (it.hasNext()) {
            SymbolNode symbolNode = it.next();

            if (symbolNode.hasIdent(ident, scope)) {
                it.remove();

                return symbolNode;
            }
        }

        // Not found symbol
        return null;
    }

    /**
     * Generate a hash code value for the symbol.
     * @param   ident   The ident to compute the hash code from.
     * @return  The hash code value.
     */
    public int getHash(String ident) {
        int hashed = 0;

        for (int i = 0; i < ident.length(); ++i) {
            hashed += (int) ident.charAt(i) * (i + 1);
        }

        return hashed % SIZE;
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder("Symbol table:\n");

        for (int i = 0; i < SIZE; ++i) {
            List<SymbolNode> symbolNodes = entries.get(i);

            buffer.append("[");
            buffer.append(i);
            buffer.append("]:\n");

            for (SymbolNode symbolNode : symbolNodes) {
                buffer.append("\t");
                buffer.append(symbolNode);
                buffer.append("\n");
            }
        }

        return buffer.toString();
    }
}
