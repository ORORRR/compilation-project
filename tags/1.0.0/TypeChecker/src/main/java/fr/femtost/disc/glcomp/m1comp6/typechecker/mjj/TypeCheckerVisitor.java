package fr.femtost.disc.glcomp.m1comp6.typechecker.mjj;

import java.util.List;

import fr.femtost.disc.glcomp.m1comp6.memory.*;

import fr.femtost.disc.glcomp.m1comp6.ast.common.*;
import fr.femtost.disc.glcomp.m1comp6.ast.mjj.*;

public class TypeCheckerVisitor implements MiniJajaASTVisitor<Type, TypeCheckerException> {
    private SymbolTable symbolTable;

    public TypeCheckerVisitor() {}

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    @Override
    public Type visit(NodeClass node, Object... args) throws TypeCheckerException {
        List<Node> children = node.getChildren();

        NodeMiniJaja nodeIdent = (NodeMiniJaja) children.get(0);
        NodeMiniJaja nodeDecls = (NodeMiniJaja) children.get(1);
        NodeMiniJaja nodeMain = (NodeMiniJaja) children.get(2);

        String ident = ((NodeIdent) nodeIdent).getValue();
        String scope = "global";
        Kind kind = Kind.VARIABLE;

        SymbolNode symbolNode = new SymbolNode(ident, scope, kind, null);

        try {
            symbolTable.put(symbolNode);
        } catch (ExistingSymbolException exception) {
            throw new TypeCheckerException("The symbol \"" + ident + "\" has already been declared.");
        }

        nodeDecls.accept(this, scope);
        nodeMain.accept(this, scope);

        return null;
    }

    @Override
    public Type visit(NodeDecls node, Object... args) throws TypeCheckerException {
        List<Node> children = node.getChildren();

        NodeMiniJaja nodeDecl = (NodeMiniJaja) children.get(0);
        NodeMiniJaja nodeDecls = (NodeMiniJaja) children.get(1);

        String scope = (String) args[0];

        nodeDecl.accept(this, scope);
        nodeDecls.accept(this, scope);

        return null;
    }

    @Override
    public Type visit(NodeVars node, Object... args) throws TypeCheckerException {
        List<Node> children = node.getChildren();

        NodeMiniJaja nodeVar = (NodeMiniJaja) children.get(0);
        NodeMiniJaja nodeVars = (NodeMiniJaja) children.get(1);

        String scope = (String) args[0];

        nodeVar.accept(this, scope);
        nodeVars.accept(this, scope);

        return null;
    }

    @Override
    public Type visit(NodeVar node, Object... args) throws TypeCheckerException {
        List<Node> children = node.getChildren();

        NodeMiniJaja nodeType = (NodeMiniJaja) children.get(0);
        NodeMiniJaja nodeIdent = (NodeMiniJaja) children.get(1);
        NodeMiniJaja nodeExp = (NodeMiniJaja) children.get(2);

        String ident = ((NodeIdent) nodeIdent).getValue();
        String scope = (String) args[0];
        Kind kind = Kind.VARIABLE;
        Type type = Type.fromNode(nodeType);

        Type expType = nodeExp.accept(this, scope);

        if (expType != null && expType != type) {
            throw new TypeCheckerException("A value of type " + expType + " is not compatible with the symbol \"" + ident + "\" of type " + type + ".");
        }

        SymbolNode symbolNode = new SymbolNode(ident, scope, kind, type);

        try {
            symbolTable.put(symbolNode);
        } catch (ExistingSymbolException exception) {
            throw new TypeCheckerException("The symbol \"" + ident + "\" has already been declared.");
        }

        return null;
    }

    @Override
    public Type visit(NodeMain node, Object... args) throws TypeCheckerException {
        List<Node> children = node.getChildren();

        NodeMiniJaja nodeVars = (NodeMiniJaja) children.get(0);
        NodeMiniJaja nodeInstrs = (NodeMiniJaja) children.get(1);

        String scope = "main";

        nodeVars.accept(this, scope);
        nodeInstrs.accept(this, scope);

        return null;
    }

    @Override
    public Type visit(NodeInstrs node, Object... args) throws TypeCheckerException {
        List<Node> children = node.getChildren();

        NodeMiniJaja nodeInstr = (NodeMiniJaja) children.get(0);
        NodeMiniJaja nodeInstrs = (NodeMiniJaja) children.get(1);

        String scope = (String) args[0];

        nodeInstr.accept(this, scope);
        nodeInstrs.accept(this, scope);

        return null;
    }

    @Override
    public Type visit(NodeIdent node, Object... args) throws TypeCheckerException {
        String ident = node.getValue();
        String scope = (String) args[0];

        SymbolNode symbolNode = symbolTable.get(ident, scope);

        if (symbolNode == null) {
            scope = "global";
            symbolNode = symbolTable.get(ident, scope);
        }

        if (symbolNode == null) {
            throw new TypeCheckerException("The symbol \"" + ident + "\" has not been declared.");
        }

        return symbolNode.getType();
    }

    @Override
    public Type visit(NodeNumber node, Object... args) throws TypeCheckerException {
        return Type.INTEGER;
    }

    @Override
    public Type visit(NodeTrue node, Object... args) throws TypeCheckerException {
        return Type.BOOLEAN;
    }

    @Override
    public Type visit(NodeFalse node, Object... args) throws TypeCheckerException {
        return Type.BOOLEAN;
    }

    @Override
    public Type visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeOmega node, Object... args) throws TypeCheckerException {
        return null;
    }

    @Override
    public Type visit(NodeMiniJaja visitable, Object... args) throws TypeCheckerException {
        return null;
    }

    @Override
    public Type visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeAdd node, Object... args) throws TypeCheckerException {
        return null;
    }

    @Override
    public Type visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeAnd node, Object... args) throws TypeCheckerException {
        return null;
    }

    @Override
    public Type visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeAssignment node, Object... args) throws TypeCheckerException {
        return null;
    }

    @Override
    public Type visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeArray node, Object... args) throws TypeCheckerException {
        return null;
    }

    @Override
    public Type visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeArrayGet node, Object... args) throws TypeCheckerException {
        return null;
    }

    @Override
    public Type visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeBoolean node, Object... args) throws TypeCheckerException {
        return null;
    }

    @Override
    public Type visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeCallE node, Object... args) throws TypeCheckerException {
        return null;
    }

    @Override
    public Type visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeCallI node, Object... args) throws TypeCheckerException {
        return null;
    }

    @Override
    public Type visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeCmp node, Object... args) throws TypeCheckerException {
        return null;
    }

    @Override
    public Type visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeCst node, Object... args) throws TypeCheckerException {
        return null;
    }

    @Override
    public Type visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeDiv node, Object... args) throws TypeCheckerException {
        return null;
    }

    @Override
    public Type visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeEnil node, Object... args) throws TypeCheckerException {
        return null;
    }

    @Override
    public Type visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeExnil node, Object... args) throws TypeCheckerException {
        return null;
    }

    @Override
    public Type visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeExpList node, Object... args) throws TypeCheckerException {
        return null;
    }

    @Override
    public Type visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeHeader node, Object... args) throws TypeCheckerException {
        return null;
    }

    @Override
    public Type visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeHeaders node, Object... args) throws TypeCheckerException {
        return null;
    }

    @Override
    public Type visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIf node, Object... args) throws TypeCheckerException {
        return null;
    }

    @Override
    public Type visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIncrement node, Object... args) throws TypeCheckerException {
        return null;
    }

    @Override
    public Type visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeInil node, Object... args) throws TypeCheckerException {
        return null;
    }

    @Override
    public Type visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeInteger node, Object... args) throws TypeCheckerException {
        return null;
    }

    @Override
    public Type visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMethod node, Object... args) throws TypeCheckerException {
        return null;
    }

    @Override
    public Type visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMinus node, Object... args) throws TypeCheckerException {
        return null;
    }

    @Override
    public Type visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMul node, Object... args) throws TypeCheckerException {
        return null;
    }

    @Override
    public Type visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNot node, Object... args) throws TypeCheckerException {
        return null;
    }

    @Override
    public Type visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeOr node, Object... args) throws TypeCheckerException {
        return null;
    }

    @Override
    public Type visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeReturn node, Object... args) throws TypeCheckerException {
        return null;
    }

    @Override
    public Type visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeSub node, Object... args) throws TypeCheckerException {
        return null;
    }

    @Override
    public Type visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeSum node, Object... args) throws TypeCheckerException {
        return null;
    }

    @Override
    public Type visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeSup node, Object... args) throws TypeCheckerException {
        return null;
    }

    @Override
    public Type visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeVnil node, Object... args) throws TypeCheckerException {
        return null;
    }

    @Override
    public Type visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeVoid node, Object... args) throws TypeCheckerException {
        return null;
    }

    @Override
    public Type visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeWhile node, Object... args) throws TypeCheckerException {
        return null;
    }
}
