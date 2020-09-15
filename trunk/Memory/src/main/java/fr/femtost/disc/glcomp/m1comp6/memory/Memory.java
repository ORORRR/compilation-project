package fr.femtost.disc.glcomp.m1comp6.memory;

import fr.femtost.disc.glcomp.m1comp6.ast.common.Kind;
import fr.femtost.disc.glcomp.m1comp6.ast.common.Type;

public class Memory {
    private final SymbolTable symbolTable;
    private final Stack stack;
    private final Heap heap;

    public Memory(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;

        stack = new Stack();
        heap = new Heap();
    }

    public Stack getStack() {
        return stack;
    }

    public Heap getHeap() {
        return heap;
    }

    /**
     * Push a value as a constant with no parent.
     *
     * @param value The value to add in the stack.
     * @return The added ValueNode.
     * @throws InvalidTypeException The provided value type is invalid.
     */
    public <T> ValueNode<T> push(T value) throws InvalidTypeException {
        ValueNode<T> valueNode = new ValueNode<>(value);

        stack.push(valueNode);

        return valueNode;
    }

    /**
     * Pop the ValueNode on top of the stack and remove it from the symbol table if it was in it.
     *
     * @return The ValueNode on top of the stack.
     * @throws StackException The stack is empty.
     */
    public <T> ValueNode<T> pop() throws StackException, HeapException {
        ValueNode<T> valueNode = stack.pop();

        // Remove the value from the symbol table
        SymbolNode parent = valueNode.getParent();

        // Remove the value from the symbol
        if (parent != null) {
            parent.removeValue(valueNode);

            // Remove ref of the array
            if (parent.getKind() == Kind.ARRAY) {
                heap.removeRef((Integer) valueNode.getValue());
            }
        }

        return valueNode;
    }

    /**
     * Pop the value on top of the stack.
     *
     * @return The value on top of the stack.
     * @throws StackException The stack is empty.
     */
    public <T> T popValue() throws StackException, HeapException {
        ValueNode<T> valueNode = pop();

        return valueNode.getValue();
    }

    /**
     * Get the ValueNode on top of the stack.
     *
     * @return The ValueNode on top of the stack.
     * @throws StackException The stack is empty.
     */
    public <T> ValueNode<T> peek() throws StackException {
        return stack.peek();
    }

    /**
     * Get the value on top of the stack.
     *
     * @return The value on top of the stack.
     * @throws StackException The stack is empty.
     */
    public <T> T peekValue() throws StackException {
        ValueNode<T> valueNode = peek();

        return valueNode.getValue();
    }

    /**
     * Swap first and second elements on the top of the stack.
     *
     * @throws StackException The stack has less than 2 elements.
     */
    public void swap() throws StackException {
        stack.swap();
    }

    /**
     * Get the entry matching the given id.
     *
     * @param id The id of the entry.
     * @return The entry matching the id.
     * @throws HeapException No entry matching the id.
     */
    public HeapEntry getHeapEntry(int id) throws HeapException {
        return heap.getHeapEntry(id);
    }

    /**
     * Declare a new variable.
     *
     * @param ident The ident of the value.
     * @param value The value.
     * @return The created ValueNode.
     * @throws UnknownSymbolException There is no symbol matching the given ident.
     * @throws WrongKindException     The symbol matching the ident is not a variable.
     * @throws InvalidTypeException   The types of the value and the matching symbol do not match.
     */
    public <T> ValueNode<T> declVar(String ident, T value)
            throws UnknownSymbolException,
            WrongKindException,
            InvalidTypeException {
        SymbolNode symbolNode = symbolTable.get(ident);

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
     *
     * @param ident The ident of the value.
     * @param value The value.
     * @return The created ValueNode.
     * @throws UnknownSymbolException There is no symbol matching the given ident.
     * @throws WrongKindException     The symbol matching the ident is not a constant.
     * @throws InvalidTypeException   The types of the value and the matching symbol do not match.
     */
    public <T> ValueNode<T> declCst(String ident, T value)
            throws UnknownSymbolException,
            WrongKindException,
            InvalidTypeException {
        SymbolNode symbolNode = symbolTable.get(ident);

        // Check that the symbol is a constant
        if (symbolNode.getKind() != Kind.CONSTANT && symbolNode.getKind() != Kind.VCONSTANT) {
            throw new WrongKindException(symbolNode.getKind(), Kind.CONSTANT);
        }

        ValueNode<T> valueNode = symbolNode.addValue(value);

        // Push the value on top of the stack
        stack.push(valueNode);

        return valueNode;
    }

    /**
     * Declare a new array.
     *
     * @param ident The ident of the value.
     * @param size  The size of the array.
     * @return The created ValueNode.
     * @throws UnknownSymbolException There is no symbol matching the given ident.
     * @throws WrongKindException     The symbol matching the ident is not a variable.
     * @throws InvalidTypeException   The types of the value and the matching symbol do not match.
     * @throws HeapException          Tried to declare an array of negative or null size
     */
    public ValueNode<Integer> declArray(String ident, int size)
            throws UnknownSymbolException,
            WrongKindException,
            InvalidTypeException,
            HeapException {
        SymbolNode symbolNode = symbolTable.get(ident);

        // Check that the symbol is an array
        if (symbolNode.getKind() != Kind.ARRAY) {
            throw new WrongKindException(symbolNode.getKind(), Kind.ARRAY);
        }

        int id = heap.insert(size, symbolNode.getType());

        ValueNode<Integer> valueNode = symbolNode.addValue(id);

        // Push the value on top of the stack
        stack.push(valueNode);

        return valueNode;
    }

    /**
     * Declare a new method.
     *
     * @param value The value.
     * @return The created ValueNode.
     * @throws UnknownSymbolException There is no symbol matching the given ident.
     * @throws InvalidTypeException   The types of the value and the matching symbol do not match.
     */
    public <T> ValueNode<T> declMethod(String ident, T value)
            throws UnknownSymbolException,
            WrongKindException,
            InvalidTypeException {
        SymbolNode symbolNode = symbolTable.get(ident);

        // Check that the symbol is an method
        if (symbolNode.getKind() != Kind.METHOD) {
            throw new WrongKindException(symbolNode.getKind(), Kind.METHOD);
        }

        ValueNode<T> valueNode = symbolNode.addValue(value);

        // Push the value on top of the stack
        stack.push(valueNode);

        return valueNode;
    }

    /**
     * Remove the first declaration matching the ident in the stack.
     *
     * @param ident The ident of the value to remove.
     * @return The removed ValueNode.
     * @throws UnknownSymbolException There is no symbol matching the given ident.
     * @throws NotFoundValueException There is no value matching the given ident in the stack.
     * @throws HeapException          Wrong access to the heap
     */
    public <T> ValueNode<T> removeDecl(String ident)
            throws UnknownSymbolException,
            NotFoundValueException,
            HeapException {
        SymbolNode symbolNode = symbolTable.get(ident);

        // Remove the value in the stack
        ValueNode<T> valueNode = stack.removeFirstValueOf(symbolNode);

        // Remove the value from the symbol
        symbolNode.removeValue(valueNode);

        // Remove ref of the array
        if (symbolNode.getKind() == Kind.ARRAY) {
            heap.removeRef((Integer) valueNode.getValue());
        }

        return valueNode;
    }

    /**
     * Set the parent symbol of the value at the given position in the stack.
     *
     * @param ident    The ident of the symbol to set as parent.
     * @param position The position of the value in the stack.
     * @return The updated ValueNode.
     * @throws NotFoundValueException Not found valueNode at given position in the stack.
     * @throws UnknownSymbolException There is no symbol matching the given ident.
     * @throws InvalidTypeException   The types of the value and the matching symbol do not match.
     * @throws WrongSymbolException   The parent of the value does not match the correct symbol.
     */
    public <T> ValueNode<T> identVal(String ident, int position)
            throws NotFoundValueException,
            UnknownSymbolException,
            InvalidTypeException,
            WrongSymbolException {
        ValueNode<T> valueNode = stack.getValueAt(position);

        SymbolNode symbolNode = symbolTable.get(ident);

        valueNode.setParent(symbolNode);
        symbolNode.addValue(valueNode);

        return valueNode;
    }

    /**
     * Assign a new value to a ValueNode in the stack.
     *
     * @param ident The ident of the value we want to update the value of.
     * @param value The new value.
     * @throws InvalidTypeException               The types of the value and the matching symbol do not match.
     * @throws UnknownSymbolException             There is no symbol matching the given ident.
     * @throws NotFoundValueException             There is no value matching the given ident in the stack.
     * @throws InvalidConstantAssignmentException Assignment to a constant that is already set
     * @throws HeapException                      Wrong access to the Heap
     */
    public <T> ValueNode<T> assignVal(String ident, T value)
            throws InvalidTypeException,
            UnknownSymbolException,
            NotFoundValueException,
            InvalidConstantAssignmentException,
            HeapException {
        SymbolNode symbolNode = symbolTable.get(ident);

        ValueNode<T> valueNode = stack.getFirstValueOf(symbolNode);

        switch (valueNode.getKind()) {
            case ARRAY:
                // Remove a ref for the former array
                heap.removeRef((Integer) valueNode.getValue());

                if (!(value instanceof Integer)) {
                    throw new InvalidTypeException(value, Type.INTEGER);
                }

                valueNode.setValue(value);

                // Add a ref to the new array
                heap.addRef((Integer) value);

                break;

            case CONSTANT:
                if (valueNode.getValue() != null) {
                    throw new InvalidConstantAssignmentException(symbolNode);
                }

                valueNode.setValue(value);

                break;

            case VARIABLE:
            case METHOD:
            default:
                valueNode.setValue(value);

                break;
        }

        return valueNode;
    }

    /**
     * Assign a new value to an array cell.
     *
     * @param ident The ident of the array we want to update the value of.
     * @param value The new value.
     * @param index The index in the array.
     * @throws InvalidTypeException   The types of the value and the matching symbol do not match.
     * @throws UnknownSymbolException There is no symbol matching the given ident.
     * @throws NotFoundValueException There is no value matching the given ident in the stack.
     * @throws ArrayAccessException   The symbol is not an array.
     * @throws HeapException          Wrong access to heap.
     */
    public <T> ValueNode<T> assignValArray(String ident, T value, int index)
            throws InvalidTypeException,
            UnknownSymbolException,
            NotFoundValueException,
            ArrayAccessException,
            HeapException {
        SymbolNode symbolNode = symbolTable.get(ident);

        ValueNode<T> valueNode = stack.getFirstValueOf(symbolNode);

        // Check that the symbol is an array
        if (symbolNode.getKind() != Kind.ARRAY) {
            throw new ArrayAccessException(symbolNode.getKind());
        }

        // Check that the new value type matches the array type
        if (ValueNode.isInvalidTypeArray(value, symbolNode)) {
            throw new InvalidTypeException(value, symbolNode);
        }

        heap.setEntryValueAt((Integer) valueNode.getValue(), index, value);

        return valueNode;
    }

    /**
     * Get the value of the first ValueNode matching the given ident in the stack.
     *
     * @param ident The ident of the ValueNode we want to get the value of.
     * @return The value of the ValueNode matching the given ident.
     * @throws UnknownSymbolException There is no symbol matching the given ident.
     * @throws NotFoundValueException There is no value matching the given ident in the stack.
     */
    public <T> T getValue(String ident)
            throws UnknownSymbolException,
            NotFoundValueException {
        SymbolNode symbolNode = symbolTable.get(ident);

        ValueNode<T> valueNode = stack.getFirstValueOf(symbolNode);

        return valueNode.getValue();
    }

    /**
     * Get the value of the first ValueNode matching the given ident in the stack.
     *
     * @param ident The ident of the ValueNode we want to get the value fom.
     * @param index The index in the array.
     * @return The value of the ValueNode matching the given ident.
     * @throws UnknownSymbolException There is no symbol matching the given ident.
     * @throws NotFoundValueException There is no value matching the given ident in the stack.
     * @throws ArrayAccessException   The symbol is not an array.
     * @throws HeapException          Wrong access to the heap.
     */
    public <T> T getValueArray(String ident, int index)
            throws UnknownSymbolException,
            NotFoundValueException,
            ArrayAccessException,
            HeapException {
        SymbolNode symbolNode = symbolTable.get(ident);

        // Check that the symbol is an array
        if (symbolNode.getKind() != Kind.ARRAY) {
            throw new ArrayAccessException(symbolNode.getKind());
        }

        // Get the value node of the array
        ValueNode<Integer> valueNode = stack.getFirstValueOf(symbolNode);

        return (T) heap.getEntryValueAt(valueNode.getValue(), index);
    }

    /**
     * Get the ident of the class
     *
     * @return the ident of the class
     */
    public String getClassName() throws MemoryException {
        try {
            ValueNode valueNode = stack.getBottomValue();

            return valueNode.getIdent();
        } catch (NotFoundValueException exception) {
            throw new MemoryException("Tried to access class variable when stack is empty");
        }
    }

    @Override
    public String toString() {
        return stack + "\n\n" + symbolTable + "\n\n" + heap;
    }
}
