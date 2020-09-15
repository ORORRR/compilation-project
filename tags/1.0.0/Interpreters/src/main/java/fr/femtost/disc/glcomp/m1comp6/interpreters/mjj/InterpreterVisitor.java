package fr.femtost.disc.glcomp.m1comp6.interpreters.mjj;

import java.util.List;

import fr.femtost.disc.glcomp.m1comp6.memory.*;

import fr.femtost.disc.glcomp.m1comp6.ast.common.*;
import fr.femtost.disc.glcomp.m1comp6.ast.mjj.*;

public class InterpreterVisitor implements MiniJajaASTVisitor<Object, InterpreterException> {
    private Memory memory;

    public InterpreterVisitor() {}

    public void setMemory(Memory memory) {
        this.memory = memory;
    }

    @Override
    public Object visit(NodeClass node, Object... args) throws InterpreterException {
        List<Node> children = node.getChildren();

        NodeMiniJaja nodeIdent = (NodeMiniJaja) children.get(0);
        NodeMiniJaja nodeDecls = (NodeMiniJaja) children.get(1);
        NodeMiniJaja nodeMain = (NodeMiniJaja) children.get(2);

        InterpreterMode mode = (InterpreterMode) args[0];
        String scope = "global";

        String ident = ((NodeIdent) nodeIdent).getValue();

        switch (mode) {
            case DEFAULT:
                try {
                    memory.declVar(ident, scope, null);
                } catch (Exception exception){
                    throw new InterpreterException(exception.toString());
                }

                nodeDecls.accept(this, InterpreterMode.DEFAULT, scope);
                nodeMain.accept(this , InterpreterMode.DEFAULT, scope);
                nodeDecls.accept(this, InterpreterMode.CANCEL, scope);

                try {
                    memory.removeDecl(ident, scope);
                } catch (Exception exception){
                    throw new InterpreterException(exception.toString());
                }

                return null;

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeMain node, Object... args) throws InterpreterException {
        List<Node> children = node.getChildren();

        NodeMiniJaja nodeVars = (NodeMiniJaja) children.get(0);
        NodeMiniJaja nodeInstrs = (NodeMiniJaja) children.get(1);

        InterpreterMode mode = (InterpreterMode) args[0];
        String scope = "main";

        switch (mode) {
            case DEFAULT:
                nodeVars.accept(this, InterpreterMode.DEFAULT, scope);
                nodeInstrs.accept(this , InterpreterMode.DEFAULT, scope);
                nodeVars.accept(this, InterpreterMode.CANCEL, scope);

                return null;

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeVar node, Object... args) throws InterpreterException {
        List<Node> children = node.getChildren();

        NodeMiniJaja nodeIdent = (NodeMiniJaja) children.get(1);
        NodeMiniJaja nodeExp = (NodeMiniJaja) children.get(2);

        InterpreterMode mode = (InterpreterMode) args[0];
        String scope = (String) args[1];

        String ident = ((NodeIdent) nodeIdent).getValue();

        switch (mode) {
            case DEFAULT:
                Object value = nodeExp.accept(this, InterpreterMode.EVAL, scope);

                try {
                    memory.declVar(ident, scope, value);
                } catch (Exception e){
                    throw new InterpreterException(e.toString());
                }

                return null;

            case CANCEL:
                try {
                    memory.removeDecl(ident, scope);
                } catch (Exception e){
                    throw new InterpreterException(e.toString());
                }

                return null;

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeDecls node, Object... args) throws InterpreterException {
        List<Node> children = node.getChildren();

        NodeMiniJaja nodeDecl = (NodeMiniJaja) children.get(0);
        NodeMiniJaja nodeDecls = (NodeMiniJaja) children.get(1);

        InterpreterMode mode = (InterpreterMode) args[0];
        String scope = (String) args[1];

        switch (mode) {
            case DEFAULT:
                nodeDecl.accept(this, InterpreterMode.DEFAULT, scope);
                nodeDecls.accept(this, InterpreterMode.DEFAULT, scope);

                return null;

            case CANCEL:
                nodeDecls.accept(this, InterpreterMode.CANCEL, scope);
                nodeDecl.accept(this, InterpreterMode.CANCEL, scope);

                return null;

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeVars node, Object... args) throws InterpreterException {
        List<Node> children = node.getChildren();

        NodeMiniJaja nodeVar = (NodeMiniJaja) children.get(0);
        NodeMiniJaja nodeVars = (NodeMiniJaja) children.get(1);

        InterpreterMode mode = (InterpreterMode) args[0];
        String scope = (String) args[1];

        switch (mode) {
            case DEFAULT:
                nodeVar.accept(this, InterpreterMode.DEFAULT, scope);
                nodeVars.accept(this, InterpreterMode.DEFAULT, scope);

                return null;

            case CANCEL:
                nodeVars.accept(this, InterpreterMode.CANCEL, scope);
                nodeVar.accept(this, InterpreterMode.CANCEL, scope);

                return null;

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeInstrs node, Object... args) throws InterpreterException {
        List<Node> children = node.getChildren();

        NodeMiniJaja nodeInstr = (NodeMiniJaja) children.get(0);
        NodeMiniJaja nodeInstrs = (NodeMiniJaja) children.get(1);

        InterpreterMode mode = (InterpreterMode) args[0];
        String scope = (String) args[1];

        switch (mode) {
            case DEFAULT:
                nodeInstr.accept(this, InterpreterMode.DEFAULT, scope);
                nodeInstrs.accept(this, InterpreterMode.DEFAULT, scope);

                return null;

            default:
               return null;
        }
    }

    @Override
    public Object visit(NodeIdent node, Object... args) throws InterpreterException {
        InterpreterMode mode = (InterpreterMode) args[0];
        String scope = (String) args[1];

        String ident = node.getValue();

        switch (mode) {
            case EVAL:
                try {
                    return memory.getValue(ident, scope);
                } catch (Exception e) {
                    throw new InterpreterException(e.toString());
                }

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeNumber node, Object... args) throws InterpreterException {
        InterpreterMode mode = (InterpreterMode) args[0];

        switch (mode) {
            case EVAL:
                return node.getValue();

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeTrue node, Object... args) throws InterpreterException {
        InterpreterMode mode = (InterpreterMode) args[0];

        switch (mode) {
            case EVAL:
                return true;

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeFalse node, Object... args) throws InterpreterException {
        InterpreterMode mode = (InterpreterMode) args[0];

        switch (mode) {
            case EVAL:
                return false;

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeAdd node, Object... args) throws InterpreterException {
        /*List<Node> children = node.getChildren();
        String scope = (String) args[1];

        NodeMiniJaja v = (NodeMiniJaja) children.get(0);
        NodeMiniJaja v1 = (NodeMiniJaja) children.get(1);

        if (!children.isEmpty() && children.size() == 2)
            return (Integer)v.accept(this, InterpreterMode.DEFAULT, scope) + (Integer)v1.accept(this, InterpreterMode.DEFAULT, scope);*/

        return null;
    }

    @Override
    public Object visit(NodeAnd node, Object... args) throws InterpreterException {
        /*List<Node> children = node.getChildren();
        String scope = (String) args[1];

        NodeMiniJaja leftNode = (NodeMiniJaja) children.get(0);
        NodeMiniJaja rightNode = (NodeMiniJaja) children.get(1);

        if (!children.isEmpty() && children.size() == 2)
            return (Boolean) leftNode.accept(this, InterpreterMode.DEFAULT, scope) && (Boolean)rightNode.accept(this, InterpreterMode.DEFAULT, scope);*/

        return null;
    }

    @Override
    public Object visit(NodeOr node, Object... args) throws InterpreterException {
        /*List<Node> children = node.getChildren();
        String scope = (String) args[1];

        NodeMiniJaja leftNode = (NodeMiniJaja) children.get(0);
        NodeMiniJaja rightNode = (NodeMiniJaja) children.get(1);

        if (!children.isEmpty() && children.size() == 2)
            return (Boolean) leftNode.accept(this, InterpreterMode.DEFAULT, scope) || (Boolean)rightNode.accept(this, InterpreterMode.DEFAULT, scope);*/

        return null;
    }

    @Override
    public Object visit(NodeMinus node, Object... args) throws InterpreterException {
        /*String scope = (String) args[1];

        NodeMiniJaja firstChild = (NodeMiniJaja) node.getChildren().get(0);
        return -(Integer)firstChild.accept(this, InterpreterMode.DEFAULT, scope);*/

        return null;
    }

    @Override
    public Object visit(NodeMul node, Object... args) throws InterpreterException {
        /*List<Node> children = node.getChildren();
        String scope = (String) args[1];

        NodeMiniJaja leftNode = (NodeMiniJaja) children.get(0);
        NodeMiniJaja rightNode = (NodeMiniJaja) children.get(1);

        if (!children.isEmpty() && children.size() == 2)
            return (Integer)leftNode.accept(this, InterpreterMode.DEFAULT, scope) * (Integer)rightNode.accept(this, InterpreterMode.DEFAULT, scope);*/

        return null;
    }

    @Override
    public Object visit(NodeNot node, Object... args) throws InterpreterException {
        /*List<Node> children = node.getChildren();
        String scope = (String) args[1];

        NodeMiniJaja firstChild = (NodeMiniJaja) children.get(0);

        return !(Boolean)firstChild.accept(this, InterpreterMode.DEFAULT, scope);*/

        return null;
    }

    @Override
    public Object visit(NodeSub node, Object... args) throws InterpreterException {
        /*List<Node> children = node.getChildren();
        String scope = (String) args[1];

        NodeMiniJaja leftNode = (NodeMiniJaja) children.get(0);
        NodeMiniJaja rightNode = (NodeMiniJaja) children.get(1);

        return (Integer)leftNode.accept(this, InterpreterMode.DEFAULT, scope) - (Integer)rightNode.accept(this, InterpreterMode.DEFAULT, scope);*/

        return null;
    }

    @Override
    public Object visit(NodeSup node, Object... args) throws InterpreterException {
        /*List<Node> children = node.getChildren();
        String scope = (String) args[1];

        NodeMiniJaja leftNode = (NodeMiniJaja) children.get(0);
        NodeMiniJaja rightNode = (NodeMiniJaja) children.get(1);

        return (Integer)leftNode.accept(this, InterpreterMode.DEFAULT, scope) > (Integer)rightNode.accept(this, InterpreterMode.DEFAULT, scope);*/

        return null;
    }

    @Override
    public Object visit(NodeCmp node, Object... args) throws InterpreterException {
        /*List<Node> children = node.getChildren();
        String scope = (String) args[1];

        NodeMiniJaja leftNode = (NodeMiniJaja) children.get(0);
        NodeMiniJaja rightNode = (NodeMiniJaja) children.get(1);

        if (!children.isEmpty() && children.size() == 2)
            return leftNode.accept(this, InterpreterMode.DEFAULT, scope) == rightNode.accept(this, InterpreterMode.DEFAULT, scope);*/

        return null;
    }

    @Override
    public Object visit(NodeDiv node, Object... args) throws InterpreterException {
        /*List<Node> children = node.getChildren();
        String scope = (String) args[1];

        NodeMiniJaja leftNode = (NodeMiniJaja) children.get(0);
        NodeMiniJaja rightNode = (NodeMiniJaja) children.get(1);

        return (Integer)leftNode.accept(this, InterpreterMode.DEFAULT, scope) / (Integer)rightNode.accept(this, InterpreterMode.DEFAULT, scope);*/

        return null;
    }

    @Override
    public Object visit(NodeCst node, Object... args) throws InterpreterException {
        return null;
    }

    @Override
    public Object visit(NodeReturn node, Object... args) throws InterpreterException {
        return null;
    }

    @Override
    public Object visit(NodeSum node, Object... args) throws InterpreterException {
        return null;
    }

    @Override
    public Object visit(NodeMiniJaja node, Object... args) throws InterpreterException {
        return null;
    }

    @Override
    public Object visit(NodeAssignment node, Object... args) throws InterpreterException {
        return null;
    }

    @Override
    public Object visit(NodeArray node, Object... args) throws InterpreterException {
        return null;
    }

    @Override
    public Object visit(NodeArrayGet node, Object... args) throws InterpreterException {
        return null;
    }

    @Override
    public Object visit(NodeBoolean node, Object... args) throws InterpreterException {
        return null;
    }

    @Override
    public Object visit(NodeCallE node, Object... args) throws InterpreterException {
        return null;
    }

    @Override
    public Object visit(NodeCallI node, Object... args) throws InterpreterException {
        return null;
    }

    @Override
    public Object visit(NodeEnil node, Object... args) throws InterpreterException {
        return null;
    }

    @Override
    public Object visit(NodeExnil node, Object... args) throws InterpreterException {
        return null;
    }

    @Override
    public Object visit(NodeExpList node, Object... args) throws InterpreterException {
        return null;
    }

    @Override
    public Object visit(NodeHeader node, Object... args) throws InterpreterException {
        return null;
    }

    @Override
    public Object visit(NodeHeaders node, Object... args) throws InterpreterException {
        return null;
    }

    @Override
    public Object visit(NodeIf node, Object... args) throws InterpreterException {
        return null;
    }

    @Override
    public Object visit(NodeIncrement node, Object... args) throws InterpreterException {
        return null;
    }

    @Override
    public Object visit(NodeInil node, Object... args) throws InterpreterException {
        return null;
    }

    @Override
    public Object visit(NodeInteger node, Object... args) throws InterpreterException {
        return null;
    }

    @Override
    public Object visit(NodeMethod node, Object... args) throws InterpreterException {
        return null;
    }

    @Override
    public Object visit(NodeVnil node, Object... args) throws InterpreterException {
        return null;
    }

    @Override
    public Object visit(NodeVoid node, Object... args) throws InterpreterException {
        return null;
    }

    @Override
    public Object visit(NodeWhile node, Object... args) throws InterpreterException {
        return null;
    }

    @Override
    public Object visit(NodeOmega node, Object... args) throws InterpreterException {
        return null;
    }
}
