package fr.femtost.disc.glcomp.m1comp6.analyser.mjj;

import fr.femtost.disc.glcomp.m1comp6.ast.mjj.*;

public class AnalyserVisitor extends MiniJajaBaseVisitor<NodeMiniJaja> {
    @Override
    public NodeMiniJaja visitClasse(MiniJajaParser.ClasseContext ctx) {
        NodeMiniJaja nodeClass = new NodeClass();

        nodeClass.addChild(visitIdent(ctx.ident()));
        nodeClass.addChild(visitDecls(ctx.decls()));
        nodeClass.addChild(visitMethmain(ctx.methmain()));

        return nodeClass;
    }

    @Override
    public NodeMiniJaja visitIdent(MiniJajaParser.IdentContext ctx) {
        String text = ctx.getText();

        return new NodeIdent(text);
    }

    @Override
    public NodeMiniJaja visitDecls(MiniJajaParser.DeclsContext ctx) {
        if (ctx.getChildCount() == 0) {
            return new NodeVnil();
        }

        NodeMiniJaja nodeDecls = new NodeDecls();

        nodeDecls.addChild(visitDecl(ctx.decl()));
        nodeDecls.addChild(visitDecls(ctx.decls()));

        return nodeDecls;
    }

    @Override
    public NodeMiniJaja visitDecl(MiniJajaParser.DeclContext ctx) {
        return visitVar(ctx.var());
    }

    @Override
    public NodeMiniJaja visitVars(MiniJajaParser.VarsContext ctx) {
        if (ctx.getChildCount() == 0) {
            return new NodeVnil();
        }

        NodeMiniJaja nodeVars = new NodeVars();

        nodeVars.addChild(visitVar(ctx.var()));
        nodeVars.addChild(visitVars(ctx.vars()));

        return nodeVars;
    }

    @Override
    public NodeMiniJaja visitVar(MiniJajaParser.VarContext ctx) {
        NodeMiniJaja nodeVar = new NodeVar();

        nodeVar.addChild(visitTypemeth(ctx.typemeth()));
        nodeVar.addChild(visitIdent(ctx.ident()));
        nodeVar.addChild(visitVexp(ctx.vexp()));

        return nodeVar;
    }

    @Override
    public NodeMiniJaja visitVexp(MiniJajaParser.VexpContext ctx) {
        if (ctx.getChildCount() == 0) {
            return new NodeOmega();
        }

        return visitExp(ctx.exp());
    }

    @Override
    public NodeMiniJaja visitMethmain(MiniJajaParser.MethmainContext ctx) {
        NodeMiniJaja nodeMain = new NodeMain();

        nodeMain.addChild(visitVars(ctx.vars()));
        nodeMain.addChild(visitInstrs(ctx.instrs()));

        return nodeMain;
    }

    @Override
    public NodeMiniJaja visitInstrs(MiniJajaParser.InstrsContext ctx) {
        if (ctx.getChildCount() == 0) {
            return new NodeInil();
        }

        NodeMiniJaja nodeInstrs = new NodeInstrs();

        nodeInstrs.addChild(visitInstr(ctx.instr()));
        nodeInstrs.addChild(visitInstrs(ctx.instrs()));

        return nodeInstrs;
    }

    @Override
    public NodeMiniJaja visitInstr(MiniJajaParser.InstrContext ctx) {
        return null;
    }

    @Override
    public NodeMiniJaja visitExp(MiniJajaParser.ExpContext ctx) {
        return visitExp1(ctx.exp1());
    }

    @Override
    public NodeMiniJaja visitExp1(MiniJajaParser.Exp1Context ctx) {
        return visitExp2(ctx.exp2());
    }

    @Override
    public NodeMiniJaja visitExp2(MiniJajaParser.Exp2Context ctx) {
        return visitTerme(ctx.terme());
    }

    @Override
    public NodeMiniJaja visitTerme(MiniJajaParser.TermeContext ctx) {
        return visitFact(ctx.fact());
    }

    @Override
    public NodeMiniJaja visitFact(MiniJajaParser.FactContext ctx) {
        String text = ctx.getText();

        if ("true".equals(text)) {
            return new NodeTrue();
        }

        if ("false".equals(text)) {
            return new NodeFalse();
        }

        return new NodeNumber(Integer.parseInt(text));
    }

    @Override
    public NodeMiniJaja visitTypemeth(MiniJajaParser.TypemethContext ctx) {
        String text = ctx.getText();

        if ("void".equals(text)) {
            return new NodeVoid();
        }

        return visitType(ctx.type());
    }

    @Override
    public NodeMiniJaja visitType(MiniJajaParser.TypeContext ctx) {
        String text = ctx.getText();

        if ("int".equals(text)) {
            return new NodeInteger();
        }

        return new NodeBoolean();
    }
}
