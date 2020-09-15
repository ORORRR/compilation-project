package fr.femtost.disc.glcomp.m1comp6.memory;

import fr.femtost.disc.glcomp.m1comp6.ast.common.Kind;
import fr.femtost.disc.glcomp.m1comp6.ast.common.Type;

public class Memory {
    private final SymbolTable symbolTable;
    private final Heap heap;
    private final Stack stack;

    public Memory(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;

        heap = new Heap();
        stack = new Stack();
    }

    /**
     * Push a value as a constant with no parent.
     * @param   value   The value to add in the stack.
     */
    public <T> ValueNode<T> push(T value) throws InvalidTypeException {
        ValueNode<T> valueNode = new ValueNode<T>(value);

        stack.push(valueNode);

        return valueNode;
    }

    /**
     * Pop the value on top of the stack and remove it from the symbol table if it was in it.
     */
    public <T> ValueNode<T> pop() throws StackException {
        ValueNode<T> valueNode = stack.pop();

        if (valueNode != null) {
            // Remove the value from the symbol table
            SymbolNode parent = valueNode.getParent();

            if (parent != null) {
                parent.removeValue(valueNode);
            }
        }

        return valueNode;
    }

    /**
     * Return the value on top of the stack
     * @return  the value on top of the stack
     * @throws StackException stack is empty
     */
    public <T> T peekValue () throws StackException{
        ValueNode<T> valueNode = stack.peek();

        return valueNode.getValue();
    }

    /**
     * Swap first and second elements on the top of the stack.
     */
    public void swap()throws StackException {
        stack.swap();
    }

    /**
     * Declare a new value.
     * @param   ident   The ident of the value.
     * @param   scope   The scope of the value.
     * @param   value   The value.
     * @return  The created ValueNode.
     * @throws  UnknownSymbolException There is no symbol that match the given ident.
     * @throws  WrongKindException     The symbol matching the ident is not a variable.
     * @throws  InvalidTypeException   The types of the value and the matching symbol do not match.
     */
    public <T> ValueNode<T> declVar(String ident, String scope, T value)
        throws UnknownSymbolException,
            WrongKindException,
            InvalidTypeException {
        SymbolNode symbolNode = symbolTable.get(ident, scope);

        // Check that there is a symbol matching the ident and scope
        if (symbolNode == null) {
            scope = "global";
            symbolNode = symbolTable.get(ident, scope);

            if (symbolNode == null) {
                throw new UnknownSymbolException(ident, scope);
            }
        }

        // Check that the symbol is a variable
        if (symbolNode.getKind() != Kind.VARIABLE) {
            throw new WrongKindException(symbolNode.getKind(), Kind.VARIABLE);
        }

        ValueNode<T> valueNode = symbolNode.addValue(value);

        // Push the value on top of the stack
        stack.push(valueNode);

        return valueNode;
    }

    /**
     * Declare a new constant.
     * @param   ident   The ident of the value.
     * @param   scope   The scope of the value.
     * @param   value   The value.
     * @return  The created ValueNode.
     * @throws  UnknownSymbolException There is no symbol that match the given ident.
     * @throws  WrongKindException     The symbol matching the ident is not a constant.
     * @throws  InvalidTypeException   The types of the value and the matching symbol do not match.
     */
    public <T> ValueNode<T> declCst(String ident, String scope, T value)
            throws UnknownSymbolException,
            WrongKindException,
            InvalidTypeException {
        SymbolNode symbolNode = symbolTable.get(ident, scope);

        // Check that there is a symbol matching the ident and scope
        if (symbolNode == null) {
            scope = "global";
            symbolNode = symbolTable.get(ident, scope);

            if (symbolNode == null) {
                throw new UnknownSymbolException(ident, scope);
            }
        }

        // Check that the symbol is a variable
        if (symbolNode.getKind() != Kind.CONSTANT) {
            throw new WrongKindException(symbolNode.getKind(), Kind.CONSTANT);
        }


        //add the value to the symbol table
        ValueNode<T> valueNode = symbolNode.addValue(value);

        // Push the value on top of the stack
        stack.push(valueNode);

        return valueNode;
    }

    /**
     * Remove the first declaration matching the ident in the stack.
     * @param   ident   The ident of the value to remove.
     * @param   scope   The scope of the value to remove.
     * @return  The removed ValueNode.
     * @throws  UnknownSymbolException  There is no symbol that match the given ident.
     * @throws  NotFoundValueException  There is no value that match the given ident in the stack.
     */
    public <T> ValueNode<T> removeDecl(String ident, String scope)
        throws UnknownSymbolException,
            NotFoundValueException {
        SymbolNode symbolNode = symbolTable.get(ident, scope);

        // Check that there is a symbol matching the ident and scope
        if (symbolNode == null) {
            scope = "global";
            symbolNode = symbolTable.get(ident, scope);

            if (symbolNode == null) {
                throw new UnknownSymbolException(ident, scope);
            }
        }

        ValueNode<T> valueNode = stack.removeFirstValueOf(ident, scope);

        if (valueNode == null) {
            throw new NotFoundValueException(ident, scope);
        }

        symbolNode.removeValue(valueNode);

        return valueNode;
    }

    /**
     * Set the parent symbol of the value at the given position in the stack.
     * @param   ident       The ident of the symbol to set as parent.
     * @param   scope       The scope of the symbol to set as parent.
     * @param   position    The position of the value in the stack.
     * @return  The updated ValueNode.
     * @throws  NotFoundValueException  Not found value at given position in the stack.
     * @throws  UnknownSymbolException  There is no symbol that match the given ident.
     * @throws  InvalidTypeException    The types of the value and the matching symbol do not match.
     * @throws  WrongSymbolException    The parent of the value does not match the correct symbol.
     */
    public <T> ValueNode<T> identVal(String ident, String scope, int position)
        throws NotFoundValueException,
            UnknownSymbolException,
            InvalidTypeException,
            WrongSymbolException {
        ValueNode<T> valueNode = stack.getValueAt(position);

        if (valueNode == null) {
            throw new NotFoundValueException(position);
        }

        SymbolNode symbolNode = symbolTable.get(ident, scope);

        // Check that there is a symbol matching the ident and scope
        if (symbolNode == null) {
            scope = "global";
            symbolNode = symbolTable.get(ident, scope);

            if (symbolNode == null) {
                throw new UnknownSymbolException(ident, scope);
            }
        }

        valueNode.setParent(symbolNode);
        symbolNode.addValue(valueNode);

        return valueNode;
    }

    /**
     * Assign a new value to a ValueNode in the stack.
     * @param   ident   The ident of the value we want to update the value of.
     * @param   scope   The scope of the value we want to update the value of.
     * @param   value   The new value.
     * @throws  InvalidTypeException                The types of the value and the matching symbol do not match.
     * @throws  UnknownSymbolException              There is no symbol matching the given ident.
     * @throws  NotFoundValueException              There is no value matching the given ident in the stack.
     * @throws  InvalidConstantAssignmentException  Assignment to a constant that is already set
     */
    public <T> ValueNode<T> assignVal(String ident, String scope, T value)
        throws InvalidTypeException,
            UnknownSymbolException,
            NotFoundValueException,
            InvalidConstantAssignmentException{
        SymbolNode symbolNode = symbolTable.get(ident, scope);

        // Check that there is a symbol matching the ident and scope
        if (symbolNode == null) {
            scope = "global";
            symbolNode = symbolTable.get(ident, scope);

            if (symbolNode == null) {
                throw new UnknownSymbolException(ident, scope);
            }
        }

        ValueNode<T> valueNode = stack.getFirstValueOf(ident, scope);

        if (valueNode == null) {
            throw new NotFoundValueException(ident, scope);
        }

        switch (valueNode.getKind()) {
            case METHOD:
                break;

            case VARIABLE:
                valueNode.setValue(value);
                break;

            case ARRAY:
                break;

            case CONSTANT:
                if(valueNode.getValue() == null){
                    valueNode.setValue(value);
                }
                else{
                    throw new InvalidConstantAssignmentException(symbolNode);
                }
                break;

            default:
                valueNode.setValue(value);
                break;
        }

        return valueNode;
    }

    /**
     * Get the value of the first ValueNode matching the given ident in the stack.
     * @param   ident   The ident of the ValueNode we want to get the value of.
     * @param   scope   The scope of the ValueNode we want to get the value of.
     * @return  The value of the ValueNode matching the given ident.
     * @throws  UnknownSymbolException  There is no symbol that match the given ident.
     * @throws  NotFoundValueException  There is no value that match the given ident in the stack.
     */
    public <T> T getValue(String ident, String scope)
        throws UnknownSymbolException,
            NotFoundValueException {
        SymbolNode symbolNode = symbolTable.get(ident, scope);

        // Check that there is a symbol matching the ident and scope
        if (symbolNode == null) {
            scope = "global";
            symbolNode = symbolTable.get(ident, scope);

            if (symbolNode == null) {
                throw new UnknownSymbolException(ident, scope);
            }
        }

        ValueNode<T> valueNode = stack.getFirstValueOf(ident, scope);

        if (valueNode == null) {
            throw new NotFoundValueException(ident, scope);
        }

        return valueNode.getValue();
    }

    /**
     * Get the kind of the first ValueNode matching the given ident in the stack.
     * @param   ident   The ident of the ValueNode we want to get the kind of.
     * @param   scope   The scope of the ValueNode we want to get the kind of.
     * @return  The kind of the ValueNode matching the given ident.
     * @throws  UnknownSymbolException  There is no symbol that match the given ident.
     * @throws  NotFoundValueException  There is no value that match the given ident in the stack.
     */
    public <T> Kind getKind(String ident, String scope)
        throws UnknownSymbolException,
            NotFoundValueException {
        SymbolNode symbolNode = symbolTable.get(ident, scope);

        // Check that there is a symbol matching the ident and scope
        if (symbolNode == null) {
            scope = "global";
            symbolNode = symbolTable.get(ident, scope);

            if (symbolNode == null) {
                throw new UnknownSymbolException(ident, scope);
            }
        }

        ValueNode<T> valueNode = stack.getFirstValueOf(ident, scope);

        if (valueNode == null) {
            throw new NotFoundValueException(ident, scope);
        }

        return valueNode.getKind();
    }

    /**
     * Get the type of the first ValueNode matching the given ident in the stack.
     * @param   ident   The ident of the ValueNode we want to get the type of.
     * @param   scope   The scope of the ValueNode we want to get the type of.
     * @return  The type of the ValueNode matching the given ident.
     * @throws  UnknownSymbolException  There is no symbol that match the given ident.
     * @throws  NotFoundValueException  There is no value that match the given ident in the stack.
     */
    public <T> Type getType(String ident, String scope)
        throws UnknownSymbolException,
            NotFoundValueException {
        SymbolNode symbolNode = symbolTable.get(ident, scope);

        // Check that there is a symbol matching the ident and scope
        if (symbolNode == null) {
            scope = "global";
            symbolNode = symbolTable.get(ident, scope);

            if (symbolNode == null) {
                throw new UnknownSymbolException(ident, scope);
            }
        }

        ValueNode<T> valueNode = stack.getFirstValueOf(ident, scope);

        if (valueNode == null) {
            throw new NotFoundValueException(ident, scope);
        }

        return valueNode.getType();
    }

    @Override
    public String toString() {
        return symbolTable + "\n\n" + stack + "\n\n" + heap;
    }
}
