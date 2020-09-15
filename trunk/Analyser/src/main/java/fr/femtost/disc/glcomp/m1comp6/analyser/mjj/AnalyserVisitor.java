package fr.femtost.disc.glcomp.m1comp6.analyser.mjj;

import fr.femtost.disc.glcomp.m1comp6.ast.mjj.*;

public class AnalyserVisitor extends MiniJajaBaseVisitor<NodeMiniJaja> {
    @Override
    public NodeMiniJaja visitClasse(MiniJajaParser.ClasseContext ctx) {
        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();

        NodeMiniJaja nodeClass = new NodeClass();

        nodeClass.setLine(line);
        nodeClass.setColumn(column);

        nodeClass.addChild(ctx.ident().accept(this));
        nodeClass.addChild(ctx.decls().accept(this));
        nodeClass.addChild(ctx.methmain().accept(this));

        return nodeClass;
    }

    @Override
    public NodeMiniJaja visitIdent(MiniJajaParser.IdentContext ctx) {
        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();

        String text = ctx.getChild(0).getText();

        NodeMiniJaja nodeIdent = new NodeIdent(text);

        nodeIdent.setLine(line);
        nodeIdent.setColumn(column);

        return nodeIdent;
    }

    @Override
    public NodeMiniJaja visitDecls(MiniJajaParser.DeclsContext ctx) {
        if (ctx.getChildCount() == 0) {
            return new NodeVnil();
        }

        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();

        NodeMiniJaja nodeDecls = new NodeDecls();

        nodeDecls.setLine(line);
        nodeDecls.setColumn(column);

        nodeDecls.addChild(ctx.decl().accept(this));
        nodeDecls.addChild(ctx.decls().accept(this));

        return nodeDecls;
    }

    @Override
    public NodeMiniJaja visitDecl(MiniJajaParser.DeclContext ctx) {
        if (ctx.var() != null) {
            return ctx.var().accept(this);
        }

        return ctx.methode().accept(this);
    }

    @Override
    public NodeMiniJaja visitVars(MiniJajaParser.VarsContext ctx) {
        if (ctx.getChildCount() == 0) {
            return new NodeVnil();
        }

        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();

        NodeMiniJaja nodeVars = new NodeVars();

        nodeVars.setLine(line);
        nodeVars.setColumn(column);

        nodeVars.addChild(ctx.var().accept(this));
        nodeVars.addChild(ctx.vars().accept(this));

        return nodeVars;
    }

    @Override
    public NodeMiniJaja visitVar(MiniJajaParser.VarContext ctx) {
        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();

        String text = ctx.getChild(0).getText();

        if ("final".equals(text)) {
            NodeMiniJaja nodeCst = new NodeCst();

            nodeCst.setLine(line);
            nodeCst.setColumn(column);

            nodeCst.addChild(ctx.type().accept(this));
            nodeCst.addChild(ctx.ident().accept(this));
            nodeCst.addChild(ctx.vexp().accept(this));

            return nodeCst;
        }

        if (ctx.vexp() != null) {
            NodeMiniJaja nodeVar = new NodeVar();

            nodeVar.setLine(line);
            nodeVar.setColumn(column);

            nodeVar.addChild(ctx.typemeth().accept(this));
            nodeVar.addChild(ctx.ident().accept(this));
            nodeVar.addChild(ctx.vexp().accept(this));

            return nodeVar;
        }

        NodeMiniJaja nodeArray = new NodeArray();

        nodeArray.setLine(line);
        nodeArray.setColumn(column);

        nodeArray.addChild(ctx.typemeth().accept(this));
        nodeArray.addChild(ctx.ident().accept(this));
        nodeArray.addChild(ctx.exp().accept(this));

        return nodeArray;
    }

    @Override
    public NodeMiniJaja visitVexp(MiniJajaParser.VexpContext ctx) {
        if (ctx.getChildCount() == 0) {
            return new NodeOmega();
        }

        return ctx.exp().accept(this);
    }

    @Override
    public NodeMiniJaja visitMethode(MiniJajaParser.MethodeContext ctx) {
        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();

        NodeMiniJaja nodeMethod = new NodeMethod();

        nodeMethod.setLine(line);
        nodeMethod.setColumn(column);

        nodeMethod.addChild(ctx.typemeth().accept(this));
        nodeMethod.addChild(ctx.ident().accept(this));
        nodeMethod.addChild(ctx.entetes().accept(this));
        nodeMethod.addChild(ctx.vars().accept(this));
        nodeMethod.addChild(ctx.instrs().accept(this));

        return nodeMethod;
    }

    @Override
    public NodeMiniJaja visitMethmain(MiniJajaParser.MethmainContext ctx) {
        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();

        NodeMiniJaja nodeMain = new NodeMain();

        nodeMain.setLine(line);
        nodeMain.setColumn(column);

        nodeMain.addChild(ctx.vars().accept(this));
        nodeMain.addChild(ctx.instrs().accept(this));

        return nodeMain;
    }

    @Override
    public NodeMiniJaja visitEntetes(MiniJajaParser.EntetesContext ctx) {
        if (ctx.getChildCount() == 0) {
            return new NodeEnil();
        }

        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();

        NodeMiniJaja nodeHeaders = new NodeHeaders();

        nodeHeaders.setLine(line);
        nodeHeaders.setColumn(column);

        nodeHeaders.addChild(ctx.entete().accept(this));

        if (ctx.entetes() != null) {
            nodeHeaders.addChild(ctx.entetes().accept(this));
        } else {
            nodeHeaders.addChild(new NodeEnil());
        }

        return nodeHeaders;
    }

    @Override
    public NodeMiniJaja visitEntete(MiniJajaParser.EnteteContext ctx) {
        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();

        NodeMiniJaja nodeHeader = new NodeHeader();

        nodeHeader.setLine(line);
        nodeHeader.setColumn(column);

        nodeHeader.addChild(ctx.type().accept(this));
        nodeHeader.addChild(ctx.ident().accept(this));

        return nodeHeader;
    }

    @Override
    public NodeMiniJaja visitInstrs(MiniJajaParser.InstrsContext ctx) {
        if (ctx.getChildCount() == 0) {
            return new NodeInil();
        }

        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();

        NodeMiniJaja nodeInstrs = new NodeInstrs();

        nodeInstrs.setLine(line);
        nodeInstrs.setColumn(column);

        nodeInstrs.addChild(ctx.instr().accept(this));
        nodeInstrs.addChild(ctx.instrs().accept(this));

        return nodeInstrs;
    }

    @Override
    public NodeMiniJaja visitInstr(MiniJajaParser.InstrContext ctx) {
        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();

        String text1 = ctx.getChild(0).getText();
        String text2 = ctx.getChild(1).getText();

        if ("return".equals(text1)) {
            NodeMiniJaja nodeReturn = new NodeReturn();

            nodeReturn.setLine(line);
            nodeReturn.setColumn(column);

            nodeReturn.addChild(ctx.exp().accept(this));

            return nodeReturn;
        }

        if ("if".equals(text1)) {
            NodeMiniJaja nodeIf = new NodeIf();

            nodeIf.setLine(line);
            nodeIf.setColumn(column);

            nodeIf.addChild(ctx.exp().accept(this));
            nodeIf.addChild(ctx.instrs(0).accept(this));

            if (ctx.instrs(1) != null) {
                nodeIf.addChild(ctx.instrs(1).accept(this));
            } else {
                nodeIf.addChild(new NodeInil());
            }

            return nodeIf;
        }

        if ("while".equals(text1)) {
            NodeMiniJaja nodeWhile = new NodeWhile();

            nodeWhile.setLine(line);
            nodeWhile.setColumn(column);

            nodeWhile.addChild(ctx.exp().accept(this));
            nodeWhile.addChild(ctx.instrs(0).accept(this));

            return nodeWhile;
        }

        if ("=".equals(text2)) {
            NodeMiniJaja nodeAssignment = new NodeAssignment();

            nodeAssignment.setLine(line);
            nodeAssignment.setColumn(column);

            nodeAssignment.addChild(ctx.ident1().accept(this));
            nodeAssignment.addChild(ctx.exp().accept(this));

            return nodeAssignment;
        }

        if ("+=".equals(text2)) {
            NodeMiniJaja nodeSum = new NodeSum();

            nodeSum.setLine(line);
            nodeSum.setColumn(column);

            nodeSum.addChild(ctx.ident1().accept(this));
            nodeSum.addChild(ctx.exp().accept(this));

            return nodeSum;
        }

        if ("++".equals(text2)) {
            NodeMiniJaja nodeIncrement = new NodeIncrement();

            nodeIncrement.setLine(line);
            nodeIncrement.setColumn(column);

            nodeIncrement.addChild(ctx.ident1().accept(this));

            return nodeIncrement;
        }

        NodeMiniJaja nodeCallI = new NodeCallI();

        nodeCallI.setLine(line);
        nodeCallI.setColumn(column);

        nodeCallI.addChild(ctx.ident().accept(this));
        nodeCallI.addChild(ctx.listexp().accept(this));

        return nodeCallI;
    }


    @Override
    public NodeMiniJaja visitListexp(MiniJajaParser.ListexpContext ctx) {
        if (ctx.getChildCount() == 0) {
            return new NodeExnil();
        }

        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();

        NodeMiniJaja nodeExpList = new NodeExpList();

        nodeExpList.setLine(line);
        nodeExpList.setColumn(column);

        nodeExpList.addChild(ctx.exp().accept(this));

        if (ctx.listexp() != null) {
            nodeExpList.addChild(ctx.listexp().accept(this));
        } else {
            nodeExpList.addChild(new NodeExnil());
        }

        return nodeExpList;
    }

    @Override
    public NodeMiniJaja visitExp(MiniJajaParser.ExpContext ctx) {
        if (ctx.getChildCount() == 1) {
            return ctx.exp1().accept(this);
        }

        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();

        String text1 = ctx.getChild(0).getText();
        String text2 = ctx.getChild(1).getText();

        if ("!".equals(text1)) {
            NodeMiniJaja nodeNot = new NodeNot();

            nodeNot.setLine(line);
            nodeNot.setColumn(column);

            nodeNot.addChild(ctx.exp1().accept(this));

            return nodeNot;
        }

        if ("&&".equals(text2)) {
            NodeMiniJaja nodeAnd = new NodeAnd();

            nodeAnd.setLine(line);
            nodeAnd.setColumn(column);

            nodeAnd.addChild(ctx.exp().accept(this));
            nodeAnd.addChild(ctx.exp1().accept(this));

            return nodeAnd;
        }

        NodeMiniJaja nodeOr = new NodeOr();

        nodeOr.setLine(line);
        nodeOr.setColumn(column);

        nodeOr.addChild(ctx.exp().accept(this));
        nodeOr.addChild(ctx.exp1().accept(this));

        return nodeOr;
    }

    @Override
    public NodeMiniJaja visitExp1(MiniJajaParser.Exp1Context ctx) {
        if (ctx.getChildCount() == 1) {
            return ctx.exp2().accept(this);
        }

        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();

        String text2 = ctx.getChild(1).getText();

        if ("==".equals(text2)) {
            NodeMiniJaja nodeCmp = new NodeCmp();

            nodeCmp.setLine(line);
            nodeCmp.setColumn(column);

            nodeCmp.addChild(ctx.exp1().accept(this));
            nodeCmp.addChild(ctx.exp2().accept(this));

            return nodeCmp;
        }

        NodeMiniJaja nodeSup = new NodeSup();

        nodeSup.setLine(line);
        nodeSup.setColumn(column);

        nodeSup.addChild(ctx.exp1().accept(this));
        nodeSup.addChild(ctx.exp2().accept(this));

        return nodeSup;
    }

    @Override
    public NodeMiniJaja visitExp2(MiniJajaParser.Exp2Context ctx) {
        if (ctx.getChildCount() == 1) {
            return ctx.terme().accept(this);
        }

        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();

        String text1 = ctx.getChild(0).getText();
        String text2 = ctx.getChild(1).getText();

        if ("-".equals(text1)) {
            NodeMiniJaja nodeNeg = new NodeNeg();

            nodeNeg.setLine(line);
            nodeNeg.setColumn(column);

            nodeNeg.addChild(ctx.terme().accept(this));

            return nodeNeg;
        }

        if ("+".equals(text2)) {
            NodeMiniJaja nodeAdd = new NodeAdd();

            nodeAdd.setLine(line);
            nodeAdd.setColumn(column);

            nodeAdd.addChild(ctx.exp2().accept(this));
            nodeAdd.addChild(ctx.terme().accept(this));

            return nodeAdd;
        }

        NodeMiniJaja nodeSub = new NodeSub();

        nodeSub.setLine(line);
        nodeSub.setColumn(column);

        nodeSub.addChild(ctx.exp2().accept(this));
        nodeSub.addChild(ctx.terme().accept(this));

        return nodeSub;
    }

    @Override
    public NodeMiniJaja visitTerme(MiniJajaParser.TermeContext ctx) {
        if (ctx.getChildCount() == 1) {
            return ctx.fact().accept(this);
        }

        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();

        String text2 = ctx.getChild(1).getText();

        if ("*".equals(text2)) {
            NodeMiniJaja nodeMul = new NodeMul();

            nodeMul.setLine(line);
            nodeMul.setColumn(column);

            nodeMul.addChild(ctx.terme().accept(this));
            nodeMul.addChild(ctx.fact().accept(this));

            return nodeMul;
        }

        NodeMiniJaja nodeDiv = new NodeDiv();

        nodeDiv.setLine(line);
        nodeDiv.setColumn(column);

        nodeDiv.addChild(ctx.terme().accept(this));
        nodeDiv.addChild(ctx.fact().accept(this));

        return nodeDiv;
    }

    @Override
    public NodeMiniJaja visitFact(MiniJajaParser.FactContext ctx) {
        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();

        String text1 = ctx.getChild(0).getText();

        if (ctx.ident1() != null) {
            return ctx.ident1().accept(this);
        }

        if (ctx.listexp() != null) {
            NodeMiniJaja nodeCallE = new NodeCallE();

            nodeCallE.setLine(line);
            nodeCallE.setColumn(column);

            nodeCallE.addChild(ctx.ident().accept(this));
            nodeCallE.addChild(ctx.listexp().accept(this));

            return nodeCallE;
        }

        if ("true".equals(text1)) {
            NodeMiniJaja nodeTrue = new NodeTrue();

            nodeTrue.setLine(line);
            nodeTrue.setColumn(column);

            return nodeTrue;
        }

        if ("false".equals(text1)) {
            NodeMiniJaja nodeFalse = new NodeFalse();

            nodeFalse.setLine(line);
            nodeFalse.setColumn(column);

            return nodeFalse;
        }

        if (ctx.NUMBER() != null) {
            int value;

            try {
                value = Integer.parseInt(text1);
            } catch (Exception exception) {
                throw new AnalyserException("Not supported integer.", line, column);
            }

            NodeMiniJaja nodeNumber = new NodeNumber(value);

            nodeNumber.setLine(line);
            nodeNumber.setColumn(column);

            return nodeNumber;
        }

        return ctx.exp().accept(this);
    }

    @Override
    public NodeMiniJaja visitIdent1(MiniJajaParser.Ident1Context ctx) {
        if (ctx.exp() == null) {
            return ctx.ident().accept(this);
        }

        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();

        NodeMiniJaja nodeArrayGet = new NodeArrayGet();

        nodeArrayGet.setLine(line);
        nodeArrayGet.setColumn(column);

        nodeArrayGet.addChild(ctx.ident().accept(this));
        nodeArrayGet.addChild(ctx.exp().accept(this));

        return nodeArrayGet;
    }

    @Override
    public NodeMiniJaja visitTypemeth(MiniJajaParser.TypemethContext ctx) {
        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();

        String text1 = ctx.getChild(0).getText();

        if ("void".equals(text1)) {
            NodeMiniJaja nodeVoid = new NodeVoid();

            nodeVoid.setLine(line);
            nodeVoid.setColumn(column);

            return nodeVoid;
        }

        return ctx.type().accept(this);
    }

    @Override
    public NodeMiniJaja visitType(MiniJajaParser.TypeContext ctx) {
        int line = ctx.getStart().getLine();
        int column = ctx.getStart().getCharPositionInLine();

        String text1 = ctx.getChild(0).getText();

        if ("int".equals(text1)) {
            NodeMiniJaja nodeInteger = new NodeInteger();

            nodeInteger.setLine(line);
            nodeInteger.setColumn(column);

            return nodeInteger;
        }

        NodeMiniJaja nodeBoolean = new NodeBoolean();

        nodeBoolean.setLine(line);
        nodeBoolean.setColumn(column);

        return nodeBoolean;
    }
}
