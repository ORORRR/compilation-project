package fr.femtost.disc.glcomp.m1comp6.ast.common;

import fr.femtost.disc.glcomp.m1comp6.ast.jjc.*;

public interface JajaCodeASTVisitor<T, E extends Throwable> extends Visitor {
    T visit(NodeAdd node, Object... args) throws E;
    T visit(NodeAinc node, Object... args) throws E;
    T visit(NodeAload node, Object... args) throws E;
    T visit(NodeAnd node, Object... args) throws E;
    T visit(NodeAstore node, Object... args) throws E;
    T visit(NodeCmp node, Object... args) throws E;
    T visit(NodeDiv node, Object... args) throws E;
    T visit(NodeFalse node, Object... args) throws E;
    T visit(NodeGoto node, Object... args) throws E;
    T visit(NodeIdent node, Object... args) throws E;
    T visit(NodeIf node, Object... args) throws E;
    T visit(NodeInc node, Object... args) throws E;
    T visit(NodeInit node, Object... args) throws E;
    T visit(NodeInstrs node, Object... args) throws E;
    T visit(NodeInvoke node, Object... args) throws E;
    T visit(NodeJajaCode node, Object... args) throws E;
    T visit(NodeJcnil node, Object... args) throws E;
    T visit(NodeJcstop node, Object... args) throws E;
    T visit(NodeKind node, Object... args) throws E;
    T visit(NodeLoad node, Object... args) throws E;
    T visit(NodeMul node, Object... args) throws E;
    T visit(NodeNeg node, Object... args) throws E;
    T visit(NodeNew node, Object... args) throws E;
    T visit(NodeNewArray node, Object... args) throws E;
    T visit(NodeNop node, Object... args) throws E;
    T visit(NodeNot node, Object... args) throws E;
    T visit(NodeNumber node, Object... args) throws E;
    T visit(NodeOr node, Object... args) throws E;
    T visit(NodePop node, Object... args) throws E;
    T visit(NodePush node, Object... args) throws E;
    T visit(NodeReturn node, Object... args) throws E;
    T visit(NodeStore node, Object... args) throws E;
    T visit(NodeSub node, Object... args) throws E;
    T visit(NodeSup node, Object... args) throws E;
    T visit(NodeSwap node, Object... args) throws E;
    T visit(NodeTrue node, Object... args) throws E;
    T visit(NodeType node, Object... args) throws E;
}