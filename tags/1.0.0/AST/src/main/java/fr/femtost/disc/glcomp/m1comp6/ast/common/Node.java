package fr.femtost.disc.glcomp.m1comp6.ast.common;

import java.util.ArrayList;
import java.util.List;

public abstract class Node {
    private List<Node> children;

    public Node() {
        children = new ArrayList<Node>();
    }

    public List<Node> getChildren() {
        return children;
    }

    /**
     * Add a child to the node.
     * @param node The node to add as a child.
     */
    public void addChild(Node node) {
        children.add(node);
    }

    @Override
    public String toString() {
        return toString(0);
    }

    protected String toString(int indent) {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < indent; ++i) {
            str.append("    ");
        }

        str.append("|- ").append(getClass().getSimpleName());

        for (Node child : children) {
            str.append("\n").append(child.toString(indent + 1));
        }

        return str.toString();
    }

    /**
     * Tells if a node is equivalent to this
     * @param   that    the node to compare with
     * @return          true if the nodes are equivalent, false otherwise
     */
    public boolean isEquivalentTo (Node that){
        //compare the nodes classes
        if(that.getClass() != this.getClass()){
            return false;
        }

        //compare the values of the nodes (if they have one)
        if(! this.haveSameValueThan(that)){
            return false;
        }

        //compare the nodes children
        List<Node> thisChildren = that.getChildren();
        List<Node> thatChildren = this.getChildren();

        if( thisChildren.size() != thatChildren.size()){
            return false;
        }

        for (int i=0; i<thatChildren.size() ; i++){
            if( ! thisChildren.get(i).isEquivalentTo(thatChildren.get(i))){
                return false;
            }
        }

        return true;
    }

    /**
     * Tells if a node has the same value as this
     * @param   that    the node we want to compare value with (has to be of the same class as this)
     * @return          true if the values are equals, false otherwise
     */
    private boolean haveSameValueThan (Node that) {
        boolean sameValue;
        if(that instanceof fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent){
            sameValue = ((fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent) that).getValue().equals( ((fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent) this).getValue());
        }
        else if(that instanceof fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber){
            sameValue = ((fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber) that).getValue() == (((fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber) this).getValue());
        }
        else if(that instanceof fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeKind){
            sameValue = ((fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeKind) that).getValue().equals( ((fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeKind) this).getValue());
        }
        else if(that instanceof fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeType){
            sameValue = ((fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeType) that).getValue().equals( ((fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeType) this).getValue());
        }
        else if(that instanceof fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent){
            sameValue = ((fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent) that).getValue().equals( ((fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent) this).getValue());
        }
        else if(that instanceof fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber){
            sameValue = ((fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber) that).getValue() == (((fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber) this).getValue());
        }
        else{ //the nodes have no value
            return true;
        }

        return sameValue;
    }
}
