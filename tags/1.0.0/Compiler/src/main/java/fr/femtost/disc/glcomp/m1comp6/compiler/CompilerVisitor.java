package fr.femtost.disc.glcomp.m1comp6.compiler;

import java.util.List;

import fr.femtost.disc.glcomp.m1comp6.memory.*;

import fr.femtost.disc.glcomp.m1comp6.ast.common.*;

public class CompilerVisitor implements MiniJajaASTVisitor<Integer, CompilerException> {
    private SymbolTable symbolTable;
    private fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeInstrs instrs;

    public CompilerVisitor() {}

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    public void setInstrs(fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeInstrs instrs) {
        this.instrs = instrs;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeClass node, Object... args) throws CompilerException {
        List<Node> children = node.getChildren();

        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja dss = (fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja) children.get(1);
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja mma = (fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja) children.get(2);

        Integer n = (Integer) args[0];
        CompilerMode rule = (CompilerMode) args[1];
        String scope = "global";

        switch (rule) {
            case DEFAULT:
                fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeInit nodeInit = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeInit();
                fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodePop nodePop = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodePop();
                fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeJcstop nodeJcstop = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeJcstop();

                instrs.addChild(nodeInit);

                int ndss = dss.accept(this, n + 1, CompilerMode.DEFAULT, scope);
                int nmma = mma.accept(this, n + ndss + 1, CompilerMode.DEFAULT, scope);
                int nrdss = dss.accept(this, n + ndss + nmma + 1, CompilerMode.CANCEL, scope);

                instrs.addChild(nodePop);
                instrs.addChild(nodeJcstop);

                return ndss + nmma + nrdss + 3;

            default:
                return 0;
        }
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeDecls node, Object... args) throws CompilerException {
        List<Node> children = node.getChildren();

        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja ds = (fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja) children.get(0);
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja dss = (fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja) children.get(1);

        Integer n = (Integer) args[0];
        CompilerMode rule = (CompilerMode) args[1];
        String scope = (String) args[2];

        switch (rule) {
            case DEFAULT:
                int nds = ds.accept(this, n, CompilerMode.DEFAULT, scope);
                int ndss = dss.accept(this, n + nds, CompilerMode.DEFAULT, scope);

                return nds + ndss;

            case CANCEL:
                int nrdss = dss.accept(this, n, CompilerMode.CANCEL, scope);
                int nrds = ds.accept(this, n + nrdss, CompilerMode.CANCEL, scope);

                return nrdss + nrds;

            default:
                return 0;
        }
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeVars node, Object... args) throws CompilerException {
        List<Node> children = node.getChildren();

        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja dv = (fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja) children.get(0);
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja dvs = (fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja) children.get(1);

        Integer n = (Integer) args[0];
        CompilerMode rule = (CompilerMode) args[1];
        String scope = (String) args[2];

        switch (rule) {
            case DEFAULT:
                int ndv = dv.accept(this, n, CompilerMode.DEFAULT, scope);
                int ndvs = dvs.accept(this, n + ndv, CompilerMode.DEFAULT, scope);

                return ndv + ndvs;

            case CANCEL:
                int nrdvs = dvs.accept(this, n, CompilerMode.CANCEL, scope);
                int nrdv = dv.accept(this, n + nrdvs, CompilerMode.CANCEL, scope);

                return nrdvs + nrdv;

            default:
                return 0;
        }
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeVar node, Object... args) throws CompilerException {
        List<Node> children = node.getChildren();

        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja t = (fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja) children.get(0);
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja i = (fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja) children.get(1);
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja e = (fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja) children.get(2);

        Integer n = (Integer) args[0];
        CompilerMode rule = (CompilerMode) args[1];
        String scope = (String) args[2];

        String ident = ((fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent) i).getValue() + "@" + scope;
        Type type = Type.fromNode(t);
        Kind kind = Kind.VARIABLE;

        switch (rule) {
            case DEFAULT:
                fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNew nodeNew = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNew();

                nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent(ident));
                nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeType(String.valueOf(type)));
                nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeKind(String.valueOf(kind)));
                nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));

                int ne = e.accept(this, n, CompilerMode.DEFAULT, scope);

                instrs.addChild(nodeNew);

                SymbolNode symbolNode = new SymbolNode(ident, "global", Kind.VARIABLE, type);

                try {
                    symbolTable.put(symbolNode);
                } catch (ExistingSymbolException exception) {
                    throw new CompilerException("Error while updating symbol table.");
                }

                return ne + 1;

            case CANCEL:
                fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeSwap nodeSwap = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeSwap();
                fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodePop nodePop = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodePop();

                instrs.addChild(nodeSwap);
                instrs.addChild(nodePop);

                return 2;

            default:
                return 0;
        }
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMain node, Object... args) throws CompilerException {
        List<Node> children = node.getChildren();

        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja dvs = (fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja) children.get(0);
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja iss = (fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja) children.get(1);

        Integer n = (Integer) args[0];
        CompilerMode rule = (CompilerMode) args[1];
        String scope = "main";

        switch (rule) {
            case DEFAULT:
                fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodePush nodePush = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodePush();
                fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber nodeNumber = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0);

                nodePush.addChild(nodeNumber);

                int ndvs = dvs.accept(this, n, CompilerMode.DEFAULT, scope);
                int niss = iss.accept(this, n + ndvs, CompilerMode.DEFAULT, scope);

                instrs.addChild(nodePush);

                int nrdvs = dvs.accept(this, n + ndvs + niss + 1, CompilerMode.CANCEL, scope);

                return ndvs + niss + nrdvs + 1;

            default:
                return 0;
        }
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeInstrs node, Object... args) throws CompilerException {
        List<Node> children = node.getChildren();

        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja is = (fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja) children.get(0);
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja iss = (fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja) children.get(1);

        Integer n = (Integer) args[0];
        CompilerMode rule = (CompilerMode) args[1];
        String scope = (String) args[2];

        switch (rule) {
            case DEFAULT:
                int nis = is.accept(this, n, CompilerMode.DEFAULT, scope);
                int niss = iss.accept(this, n + nis, CompilerMode.DEFAULT, scope);

                return nis + niss;

            default:
                return 0;
        }
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent node, Object... args) throws CompilerException {
        String scope = (String) args[2];
        String ident = node.getValue() + "@" + scope;

        if (symbolTable.get(ident, "global") == null) {
            ident = node.getValue() + "@global";
        }

        if (symbolTable.get(ident, "global") == null) {
            throw new CompilerException("Error while updating symbol table.");
        }

        fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeLoad nodeLoad = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeLoad();
        fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent nodeIdent = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent(ident);

        nodeLoad.addChild(nodeIdent);

        instrs.addChild(nodeLoad);

        return 1;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber node, Object... args) throws CompilerException {
        fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodePush nodePush = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodePush();
        fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber nodeNumber = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(node.getValue());

        nodePush.addChild(nodeNumber);

        instrs.addChild(nodePush);

        return 1;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeTrue node, Object... args) throws CompilerException {
        fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodePush nodePush = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodePush();
        fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeTrue nodeTrue = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeTrue();

        nodePush.addChild(nodeTrue);

        instrs.addChild(nodePush);

        return 1;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeFalse node, Object... args) throws CompilerException {
        fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodePush nodePush = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodePush();
        fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeFalse nodeFalse = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeFalse();

        nodePush.addChild(nodeFalse);

        instrs.addChild(nodePush);

        return 1;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeOmega node, Object... args) throws CompilerException {
        fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodePush nodePush = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodePush();
        fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeJcnil nodeJcnil = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeJcnil();

        nodePush.addChild(nodeJcnil);

        instrs.addChild(nodePush);

        return 1;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja node, Object... args) throws CompilerException {
        return null;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeAdd node, Object... args) throws CompilerException {
        return 0;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeAnd node, Object... args) throws CompilerException {
        return 0;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeAssignment node, Object... args) throws CompilerException {
        return 0;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeArray node, Object... args) throws CompilerException {
        return 0;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeArrayGet node, Object... args) throws CompilerException {
        return 0;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeBoolean node, Object... args) throws CompilerException {
        return 0;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeCallE node, Object... args) throws CompilerException {
        return 0;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeCallI node, Object... args) throws CompilerException {
        return 0;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeCmp node, Object... args) throws CompilerException {
        return 0;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeCst node, Object... args) throws CompilerException {
        return 0;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeDiv node, Object... args) throws CompilerException {
        return 0;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeEnil node, Object... args) throws CompilerException {
        return 0;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeExnil node, Object... args) throws CompilerException {
        return 0;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeExpList node, Object... args) throws CompilerException {
        return 0;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeHeader node, Object... args) throws CompilerException {
        return 0;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeHeaders node, Object... args) throws CompilerException {
        return 0;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIf node, Object... args) throws CompilerException {
        return 0;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIncrement node, Object... args) throws CompilerException {
        return 0;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeInil node, Object... args) throws CompilerException {
        return 0;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeInteger node, Object... args) throws CompilerException {
        return 0;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMethod node, Object... args) throws CompilerException {
        return 0;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMinus node, Object... args) throws CompilerException {
        return 0;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMul node, Object... args) throws CompilerException {
        return 0;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNot node, Object... args) throws CompilerException {
        return 0;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeOr node, Object... args) throws CompilerException {
        return 0;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeReturn node, Object... args) throws CompilerException {
        return 0;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeSub node, Object... args) throws CompilerException {
        return 0;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeSum node, Object... args) throws CompilerException {
        return 0;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeSup node, Object... args) throws CompilerException {
        return 0;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeVnil node, Object... args) throws CompilerException {
        return 0;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeVoid node, Object... args) throws CompilerException {
        return 0;
    }

    @Override
    public Integer visit(fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeWhile node, Object... args) throws CompilerException {
        return 0;
    }
}
