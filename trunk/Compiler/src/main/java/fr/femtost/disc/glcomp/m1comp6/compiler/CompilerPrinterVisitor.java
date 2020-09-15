package fr.femtost.disc.glcomp.m1comp6.compiler;

import java.util.List;

import fr.femtost.disc.glcomp.m1comp6.ast.common.*;
import fr.femtost.disc.glcomp.m1comp6.ast.jjc.*;

public class CompilerPrinterVisitor implements JajaCodeASTVisitor<Void, CompilerPrinterException> {
    private StringBuilder buffer;

    public CompilerPrinterVisitor() {
        // Nothing to do
    }

    public void setBuffer(StringBuilder buffer) {
        this.buffer = buffer;
    }

    @Override
    public Void visit(NodeInstrs node, Object... args) throws CompilerPrinterException {
        List<Node> children = node.getChildren();

        for (Node child : children) {
            ((NodeJajaCode) child).accept(this);
        }

        return null;
    }

    @Override
    public Void visit(NodeInit node, Object... args) throws CompilerPrinterException {
        buffer.append("init\n");

        return null;
    }

    @Override
    public Void visit(NodePush node, Object... args) throws CompilerPrinterException {
        List<Node> children = node.getChildren();

        NodeJajaCode value = (NodeJajaCode) children.get(0);

        buffer.append("push(");
        value.accept(this);
        buffer.append(")\n");

        return null;
    }

    @Override
    public Void visit(NodeIdent node, Object... args) throws CompilerPrinterException {
        buffer.append(node.getValue());

        return null;
    }

    @Override
    public Void visit(NodeAdd node, Object... args) throws CompilerPrinterException {
        buffer.append("add\n");

        return null;
    }

    @Override
    public Void visit(NodeAinc node, Object... args) throws CompilerPrinterException {
        List<Node> children = node.getChildren();

        NodeJajaCode ident = (NodeJajaCode) children.get(0);

        buffer.append("ainc(");
        ident.accept(this);
        buffer.append(")\n");

        return null;
    }

    @Override
    public Void visit(NodeAload node, Object... args) throws CompilerPrinterException {
        List<Node> children = node.getChildren();

        NodeJajaCode ident = (NodeJajaCode) children.get(0);

        buffer.append("aload(");
        ident.accept(this);
        buffer.append(")\n");

        return null;
    }

    @Override
    public Void visit(NodeAnd node, Object... args) throws CompilerPrinterException {
        buffer.append("and\n");

        return null;
    }

    @Override
    public Void visit(NodeAstore node, Object... args) throws CompilerPrinterException {
        List<Node> children = node.getChildren();

        NodeJajaCode ident = (NodeJajaCode) children.get(0);

        buffer.append("astore(");
        ident.accept(this);
        buffer.append(")\n");

        return null;
    }

    @Override
    public Void visit(NodeCmp node, Object... args) throws CompilerPrinterException {
        buffer.append("cmp\n");

        return null;
    }

    @Override
    public Void visit(NodeDiv node, Object... args) throws CompilerPrinterException {
        buffer.append("div\n");

        return null;
    }

    @Override
    public Void visit(NodeFalse node, Object... args) throws CompilerPrinterException {
        buffer.append("false");

        return null;
    }

    @Override
    public Void visit(NodeGoto node, Object... args) throws CompilerPrinterException {
        List<Node> children = node.getChildren();

        NodeJajaCode adr = (NodeJajaCode) children.get(0);

        buffer.append("goto(");
        adr.accept(this);
        buffer.append(")\n");

        return null;
    }

    @Override
    public Void visit(NodeIf node, Object... args) throws CompilerPrinterException {
        List<Node> children = node.getChildren();

        NodeJajaCode adr = (NodeJajaCode) children.get(0);

        buffer.append("if(");
        adr.accept(this);
        buffer.append(")\n");

        return null;
    }

    @Override
    public Void visit(NodeInc node, Object... args) throws CompilerPrinterException {
        List<Node> children = node.getChildren();

        NodeJajaCode ident = (NodeJajaCode) children.get(0);

        buffer.append("inc(");
        ident.accept(this);
        buffer.append(")\n");

        return null;
    }

    @Override
    public Void visit(NodeInvoke node, Object... args) throws CompilerPrinterException {
        List<Node> children = node.getChildren();

        NodeJajaCode ident = (NodeJajaCode) children.get(0);

        buffer.append("invoke(");
        ident.accept(this);
        buffer.append(")\n");

        return null;
    }

    @Override
    public Void visit(NodeJcstop node, Object... args) throws CompilerPrinterException {
        buffer.append("jcstop\n");

        return null;
    }

    @Override
    public Void visit(NodeKind node, Object... args) throws CompilerPrinterException {
        buffer.append(node.getValue());

        return null;
    }

    @Override
    public Void visit(NodeLoad node, Object... args) throws CompilerPrinterException {
        List<Node> children = node.getChildren();

        NodeJajaCode ident = (NodeJajaCode) children.get(0);

        buffer.append("load(");
        ident.accept(this);
        buffer.append(")\n");

        return null;
    }

    @Override
    public Void visit(NodeMul node, Object... args) throws CompilerPrinterException {
        buffer.append("mul\n");

        return null;
    }

    @Override
    public Void visit(NodeNeg node, Object... args) throws CompilerPrinterException {
        buffer.append("neg\n");

        return null;
    }

    @Override
    public Void visit(NodeNew node, Object... args) throws CompilerPrinterException {
        List<Node> children = node.getChildren();

        NodeJajaCode ident = (NodeJajaCode) children.get(0);
        NodeJajaCode type = (NodeJajaCode) children.get(1);
        NodeJajaCode kind = (NodeJajaCode) children.get(2);
        NodeJajaCode adr = (NodeJajaCode) children.get(3);

        buffer.append("new(");
        ident.accept(this);
        buffer.append(", ");
        type.accept(this);
        buffer.append(", ");
        kind.accept(this);
        buffer.append(", ");
        adr.accept(this);
        buffer.append(")\n");

        return null;
    }

    @Override
    public Void visit(NodeNewArray node, Object... args) throws CompilerPrinterException {
        List<Node> children = node.getChildren();

        NodeJajaCode ident = (NodeJajaCode) children.get(0);
        NodeJajaCode type = (NodeJajaCode) children.get(1);

        buffer.append("newarray(");
        ident.accept(this);
        buffer.append(", ");
        type.accept(this);
        buffer.append(")\n");

        return null;
    }

    @Override
    public Void visit(NodeNop node, Object... args) throws CompilerPrinterException {
        buffer.append("nop\n");

        return null;
    }

    @Override
    public Void visit(NodeNot node, Object... args) throws CompilerPrinterException {
        buffer.append("not\n");

        return null;
    }

    @Override
    public Void visit(NodeNumber node, Object... args) throws CompilerPrinterException {
        buffer.append(node.getValue());

        return null;
    }

    @Override
    public Void visit(NodeOr node, Object... args) throws CompilerPrinterException {
        buffer.append("or\n");

        return null;
    }

    @Override
    public Void visit(NodePop node, Object... args) throws CompilerPrinterException {
        buffer.append("pop\n");

        return null;
    }

    @Override
    public Void visit(NodeReturn node, Object... args) throws CompilerPrinterException {
        buffer.append("return\n");

        return null;
    }

    @Override
    public Void visit(NodeStore node, Object... args) throws CompilerPrinterException {
        List<Node> children = node.getChildren();

        NodeJajaCode ident = (NodeJajaCode) children.get(0);

        buffer.append("store(");
        ident.accept(this);
        buffer.append(")\n");

        return null;
    }

    @Override
    public Void visit(NodeSub node, Object... args) throws CompilerPrinterException {
        buffer.append("sub\n");

        return null;
    }

    @Override
    public Void visit(NodeSup node, Object... args) throws CompilerPrinterException {
        buffer.append("sup\n");

        return null;
    }

    @Override
    public Void visit(NodeSwap node, Object... args) throws CompilerPrinterException {
        buffer.append("swap\n");

        return null;
    }

    @Override
    public Void visit(NodeTrue node, Object... args) throws CompilerPrinterException {
        buffer.append("true");

        return null;
    }

    @Override
    public Void visit(NodeType node, Object... args) throws CompilerPrinterException {
        buffer.append(node.getValue());

        return null;
    }

    @Override
    public Void visit(NodeJajaCode node, Object... args) throws CompilerPrinterException {
        return null;
    }

    @Override
    public Void visit(NodeJcnil node, Object... args) throws CompilerPrinterException {
        buffer.append("nil");

        return null;
    }
}
