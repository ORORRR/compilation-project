package fr.femtost.disc.glcomp.m1comp6.ast.common;

import java.util.ArrayList;
import java.util.List;

public abstract class Node {
    private int line;
    private int column;

    private List<Node> children;

    public Node() {
        children = new ArrayList<>();
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public List<Node> getChildren() {
        return children;
    }

    public Node getChild(int index) {
        return children.get(index);
    }

    /**
     * Add a child to the node.
     *
     * @param node The node to add as a child.
     */
    public void addChild(Node node) {
        children.add(node);
    }

    /**
     * Add a child at the given index to the node.
     *
     * @param index The index to add the child.
     * @param node  The node to add as a child.
     */
    public void addChild(int index, Node node) {
        children.add(index, node);
    }

    public boolean hasSameValueThan(Node node) {
        return true;
    }

    /**
     * Check if two AST are equivalent.
     *
     * @param node The node to compare with.
     * @return True if the trees are equivalent, false otherwise.
     */
    public boolean isEquivalentTo(Node node) {
        // Compare node classes
        if (getClass() != node.getClass()) {
            return false;
        }

        // Compare node values (if they have one)
        if (!hasSameValueThan(node)) {
            return false;
        }

        // Compare node children
        List<Node> nodeChildren = node.getChildren();

        if (children.size() != nodeChildren.size()) {
            return false;
        }

        for (int i = 0, sz = children.size(); i < sz; ++i) {
            Node child = children.get(i);
            Node nodeChild = nodeChildren.get(i);

            if (!child.isEquivalentTo(nodeChild)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return toString(0);
    }

    protected String toString(int indent) {
        StringBuilder buffer = new StringBuilder();

        for (int i = 0; i < indent; ++i) {
            buffer.append("    ");
        }

        buffer.append("|- ")
                .append(getClass().getSimpleName())
                .append(" (")
                .append(line)
                .append(",")
                .append(column)
                .append(")");

        for (Node child : children) {
            buffer.append("\n").append(child.toString(indent + 1));
        }

        return buffer.toString();
    }
}
