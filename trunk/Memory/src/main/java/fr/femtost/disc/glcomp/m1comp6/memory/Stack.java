package fr.femtost.disc.glcomp.m1comp6.memory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Stack implements Iterable<ValueNode> {
    // Index of the last element
    private int top;

    private final List<ValueNode> values;

    public Stack() {
        top = -1;
        values = new ArrayList<>();
    }

    @Override
    public Iterator<ValueNode> iterator() {
        return values.iterator();
    }

    public List<ValueNode> getValues() {
        return values;
    }

    /**
     * Add an element on the top of the stack.
     *
     * @param valueNode The element to add on the stack.
     * @return The value that has been pushed.
     */
    public <T> ValueNode<T> push(ValueNode<T> valueNode) {
        values.add(valueNode);
        top += 1;

        return valueNode;
    }

    /**
     * Remove the element on the top of the stack.
     *
     * @return The value that has been removed, null if the stack is empty.
     */
    public <T> ValueNode<T> pop() throws StackException {
        if (isEmpty()) {
            throw new StackException("Trying to pop on empty stack.");
        }

        ValueNode<T> valueNode = values.remove(top);
        top -= 1;

        return valueNode;
    }

    /**
     * Get the element on the top of the stack.
     *
     * @return The element on the top, null if the stack is empty.
     */
    public <T> ValueNode<T> peek() throws StackException {
        if (isEmpty()) {
            throw new StackException("Trying to peek on empty stack.");
        }

        return values.get(top);
    }

    /**
     * Check if the stack is empty.
     *
     * @return True if the stack is empty, false otherwise.
     */
    public boolean isEmpty() {
        return top < 0;
    }

    /**
     * Swap first and second elements on the top of the stack.
     */
    public void swap() throws StackException {
        if (top < 1) {
            throw new StackException("Trying to swap on stack with less than 2 elements.");
        }

        ValueNode last = values.get(top);
        ValueNode before = values.get(top - 1);

        values.set(top, before);
        values.set(top - 1, last);
    }

    /**
     * Get the value at the given position from the top.
     *
     * @param s The position from the top of the value to get.
     * @return The value at given position.
     * @throws NotFoundValueException No value at given position in the stack.
     */
    public <T> ValueNode<T> getValueAt(int s) throws NotFoundValueException {
        try {
            return values.get(top - s);
        } catch (IndexOutOfBoundsException exception) {
            throw new NotFoundValueException(s);
        }
    }

    /**
     * Get the value at bottom of the stack.
     *
     * @return The value at bottom of the stack.
     */
    public <T> ValueNode<T> getBottomValue() throws NotFoundValueException {
        return getValueAt(top);
    }

    /**
     * Get the first value of the given symbol in the stack.
     *
     * @param symbol The parent of the value we want to get.
     * @return The found value.
     * @throws NotFoundValueException No value matching the symbol in the stack.
     */
    public <T> ValueNode<T> getFirstValueOf(SymbolNode symbol) throws NotFoundValueException {
        for (int i = 0; i <= top; ++i) {
            ValueNode<T> valueNode = getValueAt(i);
            SymbolNode parent = valueNode.getParent();

            if (parent != null && parent.equals(symbol)) {
                return valueNode;
            }
        }

        throw new NotFoundValueException(symbol);
    }

    /**
     * Remove the first value of the given symbol in the stack.
     *
     * @param symbol The parent of the value we want to remove.
     * @return The removed value.
     * @throws NotFoundValueException No value matching the symbol in the stack.
     */
    public <T> ValueNode<T> removeFirstValueOf(SymbolNode symbol) throws NotFoundValueException {
        for (int i = 0; i <= top; ++i) {
            ValueNode<T> valueNode = getValueAt(i);
            SymbolNode parent = valueNode.getParent();

            if (parent != null && parent.equals(symbol)) {
                values.remove(top - i);
                top -= 1;

                return valueNode;
            }
        }

        throw new NotFoundValueException(symbol);
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder("Stack:\n");

        for (int i = 0; i <= top; ++i) {
            ValueNode valueNode = values.get(top - i);

            buffer.append("\t<");
            buffer.append(valueNode.getIdent() == null ? "-" : valueNode.getIdent());
            buffer.append(", ");
            buffer.append(valueNode.getValue() == null ? "-" : valueNode.toString());
            buffer.append(", ");
            buffer.append(valueNode.getKind() == null ? "-" : valueNode.getKind());
            buffer.append(", ");
            buffer.append(valueNode.getType() == null ? "-" : valueNode.getType());
            buffer.append(">\n");
        }

        return buffer.toString();
    }
}
