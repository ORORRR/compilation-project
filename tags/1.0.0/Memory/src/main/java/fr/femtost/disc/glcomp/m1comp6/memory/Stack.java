package fr.femtost.disc.glcomp.m1comp6.memory;

import java.util.ArrayList;
import java.util.List;

public class Stack {
    // Index of the last element
    private int top;

    private final List<ValueNode> values;

    public Stack() {
        top = -1;
        values = new ArrayList<ValueNode>();
    }

    /**
     * Add an element on the top of the stack.
     * @param   valueNode   The element to add on the stack.
     * @return  The value that has been pushed.
     */
    public <T> ValueNode<T> push(ValueNode<T> valueNode) {
        values.add(valueNode);
        top += 1;

        return valueNode;
    }

    /**
     * Remove the element on the top of the stack.
     * @return  The value that has been removed, null if the stack is empty.
     */
    public <T> ValueNode<T> pop() throws StackException {
        if (isEmpty()) {
            throw new StackException("pop on empty stack");
        }

        ValueNode<T> valueNode = values.remove(top);
        top -= 1;

        return valueNode;
    }

    /**
     * Get the element on the top of the stack.
     * @return  The element on the top, null if the stack is empty.
     */
    public <T> ValueNode<T> peek() throws StackException{
        if (isEmpty()) {
            throw new StackException("peek on empty stack");
        }

        return values.get(top);
    }

    /**
     * Check if the stack is empty.
     * @return True if the stack is empty, false otherwise.
     */
    public boolean isEmpty() {
        return top < 0;
    }

    /**
     * Swap first and second elements on the top of the stack.
     */
    public void swap() throws StackException{
        if (top < 1) {
            throw new StackException("swap on stack with less than 2 elements");
        }

        ValueNode last = values.get(top);
        ValueNode before = values.get(top - 1);

        values.set(top, before);
        values.set(top - 1, last);
    }

    /**
     * Get the value at the given position from the top.
     * @param   s   The position from the top of the value to get.
     * @return  The value at the given position, null if the position is invalid.
     */
    public <T> ValueNode<T> getValueAt(int s) {
        if (s < 0 || s > top) {
            return null;
        }

        return values.get(top - s);
    }

    /**
     * Get the first value matching the given ident in the given scope.
     * @param   ident   The ident of the value we want to get.
     * @param   scope   The scope of the value we want to get.
     * @return  The found value if it exists, null otherwise.
     */
    public <T> ValueNode<T> getFirstValueOf(String ident, String scope) {
        for (int i = 0; i <= top; ++i) {
            ValueNode<T> valueNode = getValueAt(i);

            if (valueNode.hasIdent(ident, scope)) {
                return valueNode;
            }
        }

        return null;
    }

    /**
     * Remove the first value matching the given ident in the given scope.
     * @param   ident   The ident of the value we want to remove.
     * @param   scope   The scope of the value we want to remove.
     * @return  True if the value has been removed, false otherwise.
     */
    public <T> ValueNode<T> removeFirstValueOf(String ident, String scope) {
        for (int i = 0; i <= top; ++i) {
            ValueNode<T> valueNode = getValueAt(i);

            if (valueNode.hasIdent(ident, scope)) {
                values.remove(top - i);
                top -= 1;

                return valueNode;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder("Stack:\n");

        for (int i = 0; i <= top; ++i) {
            ValueNode valueNode = getValueAt(i);

            buffer.append("\t<");
            buffer.append(valueNode.getIdent() == null ? "-" : valueNode.getIdent());
            buffer.append(", ");
            buffer.append(valueNode.getValue() == null ? "-" : valueNode.getValue());
            buffer.append(", ");
            buffer.append(valueNode.getKind() == null ? "-" : valueNode.getKind());
            buffer.append(", ");
            buffer.append(valueNode.getType() == null ? "-" : valueNode.getType());
            buffer.append(">\n");
        }

        return buffer.toString();
    }
}
