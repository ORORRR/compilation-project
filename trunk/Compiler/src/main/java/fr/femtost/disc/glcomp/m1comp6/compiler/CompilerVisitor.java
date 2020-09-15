package fr.femtost.disc.glcomp.m1comp6.compiler;

import fr.femtost.disc.glcomp.m1comp6.ast.common.Kind;
import fr.femtost.disc.glcomp.m1comp6.ast.common.MiniJajaASTVisitor;
import fr.femtost.disc.glcomp.m1comp6.ast.common.Node;
import fr.femtost.disc.glcomp.m1comp6.ast.common.Type;
import fr.femtost.disc.glcomp.m1comp6.ast.jjc.*;
import fr.femtost.disc.glcomp.m1comp6.ast.mjj.*;
import fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeReturn;

import java.util.List;

public class CompilerVisitor implements MiniJajaASTVisitor<Integer, CompilerException> {
    private fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeInstrs instrs;

    public CompilerVisitor() {
        // Nothing to do
    }

    public void setInstrs(fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeInstrs instrs) {
        this.instrs = instrs;
    }

    /**
     * Set lines and columns of instrs.
     */
    public void setLineAndColumnNumbers() {
        List<Node> children = instrs.getChildren();

        for (int i = 0; i < children.size(); ++i) {
            children.get(i).setLine(i + 1);
            children.get(i).setColumn(0);
        }
    }

    @Override
    public Integer visit(NodeClass node, Object... args) throws CompilerException {
        NodeMiniJaja nodeDecls = node.getDecls();
        NodeMiniJaja nodeMain = node.getMain();

        Integer n = (Integer) args[0];
        CompilerMode mode = (CompilerMode) args[1];

        switch (mode) {
            case DEFAULT:
                NodeInit nodeInit = new NodeInit();
                NodePop nodePop = new NodePop();
                NodeJcstop nodeJcstop = new NodeJcstop();

                instrs.addChild(nodeInit);

                int ndss = nodeDecls.accept(this, n + 1, CompilerMode.DEFAULT);
                int nmma = nodeMain.accept(this, n + ndss + 1, CompilerMode.DEFAULT);
                int nrdss = nodeDecls.accept(this, n + ndss + nmma + 1, CompilerMode.CANCEL);

                instrs.addChild(nodePop);
                instrs.addChild(nodeJcstop);

                return ndss + nmma + nrdss + 3;

            default:
                return 0;
        }
    }

    @Override
    public Integer visit(NodeDecls node, Object... args) throws CompilerException {
        NodeMiniJaja nodeDecl = node.getDecl();
        NodeMiniJaja nodeDecls = node.getDecls();

        Integer n = (Integer) args[0];
        CompilerMode mode = (CompilerMode) args[1];

        switch (mode) {
            case DEFAULT:
                int nds = nodeDecl.accept(this, n, CompilerMode.DEFAULT);
                int ndss = nodeDecls.accept(this, n + nds, CompilerMode.DEFAULT);

                return nds + ndss;

            case CANCEL:
                int nrdss = nodeDecls.accept(this, n, CompilerMode.CANCEL);
                int nrds = nodeDecl.accept(this, n + nrdss, CompilerMode.CANCEL);

                return nrdss + nrds;

            default:
                return 0;
        }
    }

    @Override
    public Integer visit(NodeVars node, Object... args) throws CompilerException {
        NodeMiniJaja nodeVar = node.getVar();
        NodeMiniJaja nodeVars = node.getVars();

        Integer n = (Integer) args[0];
        CompilerMode mode = (CompilerMode) args[1];

        switch (mode) {
            case DEFAULT:
                int ndv = nodeVar.accept(this, n, CompilerMode.DEFAULT);
                int ndvs = nodeVars.accept(this, n + ndv, CompilerMode.DEFAULT);

                return ndv + ndvs;

            case CANCEL:
                int nrdvs = nodeVars.accept(this, n, CompilerMode.CANCEL);
                int nrdv = nodeVar.accept(this, n + nrdvs, CompilerMode.CANCEL);

                return nrdvs + nrdv;

            default:
                return 0;
        }
    }

    @Override
    public Integer visit(NodeVar node, Object... args) throws CompilerException {
        NodeMiniJaja nodeExp = node.getExp();

        Integer n = (Integer) args[0];
        CompilerMode mode = (CompilerMode) args[1];

        switch (mode) {
            case DEFAULT:
                NodeNew nodeNew = new NodeNew();

                nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent(node.generateIdent()));
                nodeNew.addChild(new NodeType(Type.fromNode(node.getType())));
                nodeNew.addChild(new NodeKind(Kind.VARIABLE));
                nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));

                int ne = nodeExp.accept(this, n, CompilerMode.DEFAULT);

                instrs.addChild(nodeNew);

                return ne + 1;

            case CANCEL:
                NodeSwap nodeSwap = new NodeSwap();
                NodePop nodePop = new NodePop();

                instrs.addChild(nodeSwap);
                instrs.addChild(nodePop);

                return 2;

            default:
                return 0;
        }
    }

    @Override
    public Integer visit(NodeMain node, Object... args) throws CompilerException {
        NodeMiniJaja nodeVars = node.getVars();
        NodeMiniJaja nodeInstrs = node.getInstrs();

        Integer n = (Integer) args[0];
        CompilerMode mode = (CompilerMode) args[1];

        switch (mode) {
            case DEFAULT:
                NodePush nodePush = new NodePush();

                nodePush.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));

                int ndvs = nodeVars.accept(this, n, CompilerMode.DEFAULT);
                int niss = nodeInstrs.accept(this, n + ndvs, CompilerMode.DEFAULT);

                instrs.addChild(nodePush);

                int nrdvs = nodeVars.accept(this, n + ndvs + niss + 1, CompilerMode.CANCEL);

                return ndvs + niss + nrdvs + 1;

            default:
                return 0;
        }
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeInstrs node, Object... args) throws CompilerException {
        NodeMiniJaja nodeInstr = node.getInstr();
        NodeMiniJaja nodeInstrs = node.getInstrs();

        Integer n = (Integer) args[0];
        CompilerMode mode = (CompilerMode) args[1];

        switch (mode) {
            case DEFAULT:
                int nis = nodeInstr.accept(this, n, CompilerMode.DEFAULT);
                if (nodeInstr instanceof NodeReturn) {
                    return nis;
                }

                int niss = nodeInstrs.accept(this, n + nis, CompilerMode.DEFAULT);

                return nis + niss;

            default:
                return 0;
        }
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent node, Object... args) throws CompilerException {
        NodeLoad nodeLoad = new NodeLoad();

        nodeLoad.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent(node.generateNormalIdent()));

        instrs.addChild(nodeLoad);

        return 1;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber node, Object... args) throws CompilerException {
        NodePush nodePush = new NodePush();

        nodePush.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(node.getValue()));

        instrs.addChild(nodePush);

        return 1;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeTrue node, Object... args) throws CompilerException {
        NodePush nodePush = new NodePush();

        nodePush.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeTrue());

        instrs.addChild(nodePush);

        return 1;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeFalse node, Object... args) throws CompilerException {
        NodePush nodePush = new NodePush();

        nodePush.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeFalse());

        instrs.addChild(nodePush);

        return 1;
    }

    @Override
    public Integer visit(NodeOmega node, Object... args) throws CompilerException {
        NodePush nodePush = new NodePush();

        nodePush.addChild(new NodeJcnil());

        instrs.addChild(nodePush);

        return 1;
    }

    @Override
    public Integer visit(NodeAssignment node, Object... args) throws CompilerException {
        NodeMiniJaja nodeIdent = node.getIdent();
        NodeMiniJaja nodeExp = node.getExp();

        Integer n = (Integer) args[0];
        CompilerMode mode = (CompilerMode) args[1];

        if (nodeIdent instanceof NodeArrayGet) {
            NodeMiniJaja nodeIndex = ((NodeArrayGet) nodeIdent).getExp();

            switch (mode) {
                case DEFAULT:
                    NodeAstore nodeAstore = new NodeAstore();

                    nodeAstore.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent(((NodeArrayGet) nodeIdent).generateIdent()));

                    int ne = nodeIndex.accept(this, n, CompilerMode.DEFAULT);
                    int ne1 = nodeExp.accept(this, n + ne, CompilerMode.DEFAULT);

                    instrs.addChild(nodeAstore);

                    return ne + ne1 + 1;

                default:
                    return 0;
            }
        }

        switch (mode) {
            case DEFAULT:
                NodeStore nodeStore = new NodeStore();

                nodeStore.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent(((fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent) nodeIdent).generateNormalIdent()));

                int ne = nodeExp.accept(this, n, CompilerMode.DEFAULT);

                instrs.addChild(nodeStore);

                return ne + 1;

            default:
                return 0;
        }
    }

    @Override
    public Integer visit(NodeCst node, Object... args) throws CompilerException {
        NodeMiniJaja nodeExp = node.getExp();

        Integer n = (Integer) args[0];
        CompilerMode mode = (CompilerMode) args[1];

        switch (mode) {
            case DEFAULT:
                NodeNew nodeNew = new NodeNew();

                nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent(node.generateIdent()));
                nodeNew.addChild(new NodeType(Type.fromNode(node.getType())));
                nodeNew.addChild(new NodeKind(Kind.CONSTANT));
                nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));

                int ne = nodeExp.accept(this, n, CompilerMode.DEFAULT);

                instrs.addChild(nodeNew);

                return ne + 1;

            case CANCEL:
                NodeSwap nodeSwap = new NodeSwap();
                NodePop nodePop = new NodePop();

                instrs.addChild(nodeSwap);
                instrs.addChild(nodePop);

                return 2;

            default:
                return 0;
        }
    }

    @Override
    public Integer visit(NodeIncrement node, Object... args) throws CompilerException {
        NodeMiniJaja nodeIdent = node.getIdent();

        Integer n = (Integer) args[0];
        CompilerMode mode = (CompilerMode) args[1];

        if (nodeIdent instanceof NodeArrayGet) {
            NodeMiniJaja nodeIndex = ((NodeArrayGet) nodeIdent).getExp();

            switch (mode) {
                case DEFAULT:
                    NodeAinc nodeAinc = new NodeAinc();
                    NodePush nodePush = new NodePush();

                    nodeAinc.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent(((NodeArrayGet) nodeIdent).generateIdent()));
                    nodePush.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(1));

                    int ne = nodeIndex.accept(this, n, CompilerMode.DEFAULT);

                    instrs.addChild(nodePush);
                    instrs.addChild(nodeAinc);

                    return ne + 2;

                default:
                    return 0;
            }
        }

        switch (mode) {
            case DEFAULT:
                NodePush nodePush = new NodePush();
                NodeInc nodeInc = new NodeInc();

                nodePush.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(1));
                nodeInc.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent(((fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent) nodeIdent).generateNormalIdent()));

                instrs.addChild(nodePush);
                instrs.addChild(nodeInc);

                return 2;

            default:
                return 0;
        }
    }

    @Override
    public Integer visit(NodeSum node, Object... args) throws CompilerException {
        NodeMiniJaja nodeIdent = node.getIdent();
        NodeMiniJaja nodeExp = node.getExp();

        Integer n = (Integer) args[0];
        CompilerMode mode = (CompilerMode) args[1];

        if (nodeIdent instanceof NodeArrayGet) {
            NodeMiniJaja nodeIndex = ((NodeArrayGet) nodeIdent).getExp();

            switch (mode) {
                case DEFAULT:
                    NodeAinc nodeAinc = new NodeAinc();

                    nodeAinc.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent(((NodeArrayGet) nodeIdent).generateIdent()));

                    int ne = nodeIndex.accept(this, n, CompilerMode.DEFAULT);
                    int ne1 = nodeExp.accept(this, n + ne, CompilerMode.DEFAULT);

                    instrs.addChild(nodeAinc);

                    return ne + ne1 + 1;

                default:
                    return 0;
            }
        }

        switch (mode) {
            case DEFAULT:
                NodeInc nodeInc = new NodeInc();

                nodeInc.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent(((fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent) nodeIdent).generateNormalIdent()));

                int ne = nodeExp.accept(this, n, CompilerMode.DEFAULT);

                instrs.addChild(nodeInc);

                return ne + 1;

            default:
                return 0;
        }
    }

    @Override
    public Integer visit(NodeInil node, Object... args) throws CompilerException {
        return 0;
    }

    @Override
    public Integer visit(NodeVnil node, Object... args) throws CompilerException {
        return 0;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNot node, Object... args) throws CompilerException {
        NodeMiniJaja nodeExp = node.getExp();

        Integer n = (Integer) args[0];
        CompilerMode mode = (CompilerMode) args[1];

        switch (mode) {
            case DEFAULT:
                fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNot nodeNot = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNot();

                int ne = nodeExp.accept(this, n, CompilerMode.DEFAULT);

                instrs.addChild(nodeNot);

                return ne + 1;

            default:
                return 0;
        }
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNeg node, Object... args) throws CompilerException {
        NodeMiniJaja nodeExp = node.getExp();

        Integer n = (Integer) args[0];
        CompilerMode mode = (CompilerMode) args[1];

        switch (mode) {
            case DEFAULT:
                fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNeg nodeNeg = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNeg();

                int ne = nodeExp.accept(this, n, CompilerMode.DEFAULT);

                instrs.addChild(nodeNeg);

                return ne + 1;

            default:
                return 0;
        }
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeOr node, Object... args) throws CompilerException {
        NodeMiniJaja nodeExp1 = node.getExpLeft();
        NodeMiniJaja nodeExp2 = node.getExpRight();

        Integer n = (Integer) args[0];
        CompilerMode mode = (CompilerMode) args[1];

        switch (mode) {
            case DEFAULT:
                fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIf nodeIf = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIf();
                NodeGoto nodeGoto = new NodeGoto();
                NodePush nodePush = new NodePush();


                int ne1 = nodeExp1.accept(this, n, CompilerMode.DEFAULT);
                instrs.addChild(nodeIf);
                int ne2 = nodeExp2.accept(this, n + ne1 + 1, CompilerMode.DEFAULT);
                instrs.addChild(nodeGoto);
                instrs.addChild(nodePush);

                nodeIf.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(n + ne1 + ne2 + 2));
                nodeGoto.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(n + ne1 + ne2 + 3));
                nodePush.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeTrue());

                return ne1 + ne2 + 3;

            default:
                return 0;
        }
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeSup node, Object... args) throws CompilerException {
        NodeMiniJaja nodeExp1 = node.getExpLeft();
        NodeMiniJaja nodeExp2 = node.getExpRight();

        Integer n = (Integer) args[0];
        CompilerMode mode = (CompilerMode) args[1];

        switch (mode) {
            case DEFAULT:
                fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeSup nodeSup = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeSup();

                int ne1 = nodeExp1.accept(this, n, CompilerMode.DEFAULT);
                int ne2 = nodeExp2.accept(this, n + ne1, CompilerMode.DEFAULT);

                instrs.addChild(nodeSup);

                return ne1 + ne2 + 1;

            default:
                return 0;
        }
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeAnd node, Object... args) throws CompilerException {
        NodeMiniJaja nodeExp1 = node.getExpLeft();
        NodeMiniJaja nodeExp2 = node.getExpRight();

        Integer n = (Integer) args[0];
        CompilerMode mode = (CompilerMode) args[1];

        switch (mode) {
            case DEFAULT:
                fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIf nodeIf = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIf();
                NodeGoto nodeGoto = new NodeGoto();
                NodePush nodePush = new NodePush();


                int ne1 = nodeExp1.accept(this, n, CompilerMode.DEFAULT);
                instrs.addChild(nodeIf);
                instrs.addChild(nodePush);
                instrs.addChild(nodeGoto);
                int ne2 = nodeExp2.accept(this, n + ne1 + 3, CompilerMode.DEFAULT);

                nodeIf.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(n + ne1 + 3));
                nodeGoto.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(n + ne1 + ne2 + 3));
                nodePush.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeFalse());

                return ne1 + ne2 + 3;

            default:
                return 0;
        }
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeCmp node, Object... args) throws CompilerException {
        NodeMiniJaja nodeExp1 = node.getExpLeft();
        NodeMiniJaja nodeExp2 = node.getExpRight();

        Integer n = (Integer) args[0];
        CompilerMode mode = (CompilerMode) args[1];

        switch (mode) {
            case DEFAULT:
                fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeCmp nodeCmp = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeCmp();

                int ne1 = nodeExp1.accept(this, n, CompilerMode.DEFAULT);
                int ne2 = nodeExp2.accept(this, n + ne1, CompilerMode.DEFAULT);

                instrs.addChild(nodeCmp);

                return ne1 + ne2 + 1;

            default:
                return 0;
        }
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeAdd node, Object... args) throws CompilerException {
        NodeMiniJaja nodeExp1 = node.getExpLeft();
        NodeMiniJaja nodeExp2 = node.getExpRight();

        Integer n = (Integer) args[0];
        CompilerMode mode = (CompilerMode) args[1];

        switch (mode) {
            case DEFAULT:
                fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeAdd nodeAdd = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeAdd();

                int ne1 = nodeExp1.accept(this, n, CompilerMode.DEFAULT);
                int ne2 = nodeExp2.accept(this, n + ne1, CompilerMode.DEFAULT);

                instrs.addChild(nodeAdd);

                return ne1 + ne2 + 1;

            default:
                return 0;
        }
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeSub node, Object... args) throws CompilerException {
        NodeMiniJaja nodeExp1 = node.getExpLeft();
        NodeMiniJaja nodeExp2 = node.getExpRight();

        Integer n = (Integer) args[0];
        CompilerMode mode = (CompilerMode) args[1];

        switch (mode) {
            case DEFAULT:
                fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeSub nodeSub = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeSub();

                int ne1 = nodeExp1.accept(this, n, CompilerMode.DEFAULT);
                int ne2 = nodeExp2.accept(this, n + ne1, CompilerMode.DEFAULT);

                instrs.addChild(nodeSub);

                return ne1 + ne2 + 1;

            default:
                return 0;
        }
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeDiv node, Object... args) throws CompilerException {
        NodeMiniJaja nodeExp1 = node.getExpLeft();
        NodeMiniJaja nodeExp2 = node.getExpRight();

        Integer n = (Integer) args[0];
        CompilerMode mode = (CompilerMode) args[1];

        switch (mode) {
            case DEFAULT:
                fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeDiv nodeDiv = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeDiv();

                int ne1 = nodeExp1.accept(this, n, CompilerMode.DEFAULT);
                int ne2 = nodeExp2.accept(this, n + ne1, CompilerMode.DEFAULT);

                instrs.addChild(nodeDiv);

                return ne1 + ne2 + 1;

            default:
                return 0;
        }
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMul node, Object... args) throws CompilerException {
        NodeMiniJaja nodeExp1 = node.getExpLeft();
        NodeMiniJaja nodeExp2 = node.getExpRight();

        Integer n = (Integer) args[0];
        CompilerMode mode = (CompilerMode) args[1];

        switch (mode) {
            case DEFAULT:
                fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeMul nodeMul = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeMul();

                int ne1 = nodeExp1.accept(this, n, CompilerMode.DEFAULT);
                int ne2 = nodeExp2.accept(this, n + ne1, CompilerMode.DEFAULT);

                instrs.addChild(nodeMul);

                return ne1 + ne2 + 1;

            default:
                return 0;
        }
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIf node, Object... args) throws CompilerException {
        NodeMiniJaja nodeExp = node.getExp();
        NodeMiniJaja nodeInstrs1 = node.getInstrs();
        NodeMiniJaja nodeInstrs2 = node.getInstrsElse();

        Integer n = (Integer) args[0];
        CompilerMode mode = (CompilerMode) args[1];

        switch (mode) {
            case DEFAULT:
                fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIf nodeIf = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIf();
                NodeGoto nodeGoto = new NodeGoto();

                int ne = nodeExp.accept(this, n, CompilerMode.DEFAULT);

                instrs.addChild(nodeIf);

                int niss2 = nodeInstrs2.accept(this, n + ne + 1, CompilerMode.DEFAULT);

                nodeIf.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(n + ne + niss2 + 2));

                instrs.addChild(nodeGoto);

                int niss1 = nodeInstrs1.accept(this, n + ne + niss2 + 2, CompilerMode.DEFAULT);

                nodeGoto.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(n + ne + niss2 + niss1 + 2));

                return ne + niss1 + niss2 + 2;

            default:
                return 0;
        }
    }

    @Override
    public Integer visit(NodeWhile node, Object... args) throws CompilerException {
        NodeMiniJaja nodeExp = node.getExp();
        NodeMiniJaja nodeInstrs = node.getInstrs();

        Integer n = (Integer) args[0];
        CompilerMode mode = (CompilerMode) args[1];

        switch (mode) {
            case DEFAULT:
                fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNot nodeNot = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNot();
                fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIf nodeIf = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIf();
                NodeGoto nodeGoto = new NodeGoto();

                int ne = nodeExp.accept(this, n, CompilerMode.DEFAULT);

                instrs.addChild(nodeNot);
                instrs.addChild(nodeIf);

                int niss = nodeInstrs.accept(this, n + ne + 2, CompilerMode.DEFAULT);

                nodeIf.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(n + ne + niss + 3));
                nodeGoto.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(n));

                instrs.addChild(nodeGoto);

                return ne + niss + 3;

            default:
                return 0;
        }
    }

    @Override
    public Integer visit(NodeArray node, Object... args) throws CompilerException {
        NodeMiniJaja nodeExp = node.getExp();

        Integer n = (Integer) args[0];
        CompilerMode mode = (CompilerMode) args[1];

        switch (mode) {
            case DEFAULT:
                NodeNewArray nodeNewArray = new NodeNewArray();

                nodeNewArray.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent(node.generateIdent()));
                nodeNewArray.addChild(new NodeType(Type.fromNode(node.getType())));

                int ne = nodeExp.accept(this, n, CompilerMode.DEFAULT);

                instrs.addChild(nodeNewArray);

                return ne + 1;

            case CANCEL:
                NodeSwap nodeSwap = new NodeSwap();
                NodePop nodePop = new NodePop();

                instrs.addChild(nodeSwap);
                instrs.addChild(nodePop);

                return 2;

            default:
                return 0;
        }
    }

    @Override
    public Integer visit(NodeArrayGet node, Object... args) throws CompilerException {
        NodeMiniJaja nodeExp = node.getExp();

        Integer n = (Integer) args[0];
        CompilerMode mode = (CompilerMode) args[1];

        switch (mode) {
            case DEFAULT:
                NodeAload nodeAload = new NodeAload();

                nodeAload.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent(node.generateIdent()));

                int ne = nodeExp.accept(this, n, CompilerMode.DEFAULT);

                instrs.addChild(nodeAload);

                return ne + 1;

            default:
                return 0;
        }
    }

    @Override
    public Integer visit(NodeVoid node, Object... args) throws CompilerException {
        return 0;
    }

    @Override
    public Integer visit(NodeInteger node, Object... args) throws CompilerException {
        return 0;
    }

    @Override
    public Integer visit(NodeBoolean node, Object... args) throws CompilerException {
        return 0;
    }

    @Override
    public Integer visit(NodeCallE node, Object... args) throws CompilerException {
        NodeMiniJaja nodeExpList = node.getExpList();

        Integer n = (Integer) args[0];
        CompilerMode mode = (CompilerMode) args[1];

        switch (mode) {
            case DEFAULT:
                NodeInvoke nodeInvoke = new NodeInvoke();

                nodeInvoke.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent(node.generateIdent()));

                int nlexp = nodeExpList.accept(this, n, CompilerMode.DEFAULT);

                instrs.addChild(nodeInvoke);

                int nrlexp = nodeExpList.accept(this, n, CompilerMode.CANCEL);

                return nlexp + nrlexp + 1;

            default:
                return 0;
        }
    }

    @Override
    public Integer visit(NodeCallI node, Object... args) throws CompilerException {
        NodeMiniJaja nodeExpList = node.getExpList();

        Integer n = (Integer) args[0];
        CompilerMode mode = (CompilerMode) args[1];

        switch (mode) {
            case DEFAULT:
                NodeInvoke nodeInvoke = new NodeInvoke();

                nodeInvoke.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent(node.generateIdent()));

                NodePop nodePop = new NodePop();

                int nlexp = nodeExpList.accept(this, n, CompilerMode.DEFAULT);

                instrs.addChild(nodeInvoke);

                int nrlexp = nodeExpList.accept(this, n, CompilerMode.CANCEL);

                instrs.addChild(nodePop);

                return nlexp + nrlexp + 2;

            default:
                return 0;
        }
    }

    @Override
    public Integer visit(NodeMethod node, Object... args) throws CompilerException {
        NodeMiniJaja nodeType = node.getType();
        NodeMiniJaja nodeHeaders = node.getHeaders();
        NodeMiniJaja nodeVars = node.getVars();
        NodeMiniJaja nodeInstrs = node.getInstrs();

        Integer n = (Integer) args[0];
        CompilerMode mode = (CompilerMode) args[1];

        boolean isVoid = nodeType instanceof NodeVoid;
        int nr = isVoid ? 6 : 5;
        int nh = getHeadersNumber(nodeHeaders);

        switch (mode) {
            case DEFAULT:
                NodePush nodePush = new NodePush();
                NodeNew nodeNew = new NodeNew();
                NodeGoto nodeGoto = new NodeGoto();
                NodeSwap nodeSwap = new NodeSwap();
                fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeReturn nodeReturn = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeReturn();

                nodePush.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(n + 3));

                instrs.addChild(nodePush);

                nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent(node.generateIdent()));
                nodeNew.addChild(new NodeType(Type.fromNode(nodeType)));
                nodeNew.addChild(new NodeKind(Kind.METHOD));
                nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));

                instrs.addChild(nodeNew);
                instrs.addChild(nodeGoto);

                int nens = nodeHeaders.accept(this, n + 3, CompilerMode.DEFAULT, nh);
                int ndvs = nodeVars.accept(this, n + nens + 3, CompilerMode.DEFAULT);

                int instrsStartAddress = instrs.getChildren().size();
                int niss = nodeInstrs.accept(this, n + nens + ndvs + 3, CompilerMode.DEFAULT);

                if (isVoid) {
                    NodePush nodePush0 = new NodePush();

                    nodePush0.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));

                    instrs.addChild(nodePush0);
                } else {
                    //fill goto that represents returns
                    for (int i = instrsStartAddress; i < instrsStartAddress + niss; i++) {
                        Node instr = instrs.getChildren().get(i);

                        if (instr instanceof NodeGoto && instr.getChildren().size() == 0) {
                            instr.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(instrsStartAddress + niss + 1));
                        }
                    }
                }

                int nrdvs = nodeVars.accept(this, n + nens + ndvs + 3, CompilerMode.CANCEL);

                nodeGoto.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(n + nens + ndvs + niss + nrdvs + nr));

                instrs.addChild(nodeSwap);
                instrs.addChild(nodeReturn);

                return nens + ndvs + niss + nrdvs + nr;

            case CANCEL:
                NodeSwap nodeSwapR = new NodeSwap();
                NodePop nodePopR = new NodePop();

                instrs.addChild(nodeSwapR);
                instrs.addChild(nodePopR);

                return 2;

            default:
                return 0;
        }
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeReturn node, Object... args) throws CompilerException {
        int ne = node.getExp().accept(this, args);
        instrs.addChild(new NodeGoto());

        return ne + 1;
    }

    @Override
    public Integer visit(NodeEnil node, Object... args) throws CompilerException {
        return 0;
    }

    @Override
    public Integer visit(NodeExnil node, Object... args) throws CompilerException {
        return 0;
    }

    @Override
    public Integer visit(NodeExpList node, Object... args) throws CompilerException {
        NodeMiniJaja nodeExp = node.getExp();
        NodeMiniJaja nodeExpList = node.getExpList();

        Integer n = (Integer) args[0];
        CompilerMode mode = (CompilerMode) args[1];

        switch (mode) {
            case DEFAULT:
                int nexp = nodeExp.accept(this, n, CompilerMode.DEFAULT);
                int nlexp = nodeExpList.accept(this, n + nexp, CompilerMode.DEFAULT);

                return nexp + nlexp;

            case CANCEL:
                NodeSwap nodeSwap = new NodeSwap();
                NodePop nodePop = new NodePop();

                instrs.addChild(nodeSwap);
                instrs.addChild(nodePop);

                int nrlexp = nodeExpList.accept(this, n, CompilerMode.CANCEL);

                return nrlexp + 2;

            default:
                return 0;
        }

    }

    @Override
    public Integer visit(NodeHeader node, Object... args) throws CompilerException {
        Integer n = (Integer) args[0];
        CompilerMode mode = (CompilerMode) args[1];
        Integer s = (Integer) args[2];

        switch (mode) {
            case DEFAULT:
                NodeNew nodeNew = new NodeNew();

                nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent(node.generateIdent()));
                nodeNew.addChild(new NodeType(Type.fromNode(node.getType())));
                nodeNew.addChild(new NodeKind(Kind.VARIABLE));
                nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(s));

                instrs.addChild(nodeNew);

                return 1;

            default:
                return 0;
        }
    }

    @Override
    public Integer visit(NodeHeaders node, Object... args) throws CompilerException {
        NodeMiniJaja nodeHeader = node.getHeader();
        NodeMiniJaja nodeHeaders = node.getHeaders();

        Integer n = (Integer) args[0];
        CompilerMode mode = (CompilerMode) args[1];
        Integer nh = (Integer) args[2];

        switch (mode) {
            case DEFAULT:
                int en = nodeHeader.accept(this, n, CompilerMode.DEFAULT, nh);
                int ens = nodeHeaders.accept(this, n + en, CompilerMode.DEFAULT, nh - 1);

                return en + ens;

            default:
                return 0;
        }

    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja node, Object... args) throws CompilerException {
        return null;
    }

    /**
     * Compute the number of headers.
     *
     * @param nodeHeaders The headers node.
     * @return The total number of headers.
     */
    private static int getHeadersNumber(NodeMiniJaja nodeHeaders) {
        int value = 0;

        while (nodeHeaders instanceof NodeHeaders) {
            value += 1;

            nodeHeaders = ((NodeHeaders) nodeHeaders).getHeaders();
        }

        return value;
    }
}
