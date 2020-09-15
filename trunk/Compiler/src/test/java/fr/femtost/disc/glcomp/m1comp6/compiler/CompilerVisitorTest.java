package fr.femtost.disc.glcomp.m1comp6.compiler;

import fr.femtost.disc.glcomp.m1comp6.ast.common.Kind;
import fr.femtost.disc.glcomp.m1comp6.ast.common.Type;
import fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber;
import fr.femtost.disc.glcomp.m1comp6.ast.jjc.*;
import fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeTrue;
import fr.femtost.disc.glcomp.m1comp6.ast.mjj.*;
import fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeReturn;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CompilerVisitorTest {
    private CompilerVisitor compilerVisitor;
    private fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeInstrs instrs;

    private fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeInstrs expected;

    @Before
    public void initialize() {
        compilerVisitor = new CompilerVisitor();
        instrs = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeInstrs();

        expected = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeInstrs();

        compilerVisitor.setInstrs(instrs);
    }

    //--------------------------------------------------------------------

    @Test
    public void nodeTrueVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeTrue node = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeTrue();

        // Compilation
        int n = compilerVisitor.visit(node, 1, CompilerMode.DEFAULT);

        Assert.assertEquals(1, n);

        // Construction of expected instrs
        NodePush nodePush = new NodePush();

        expected.addChild(nodePush);
        nodePush.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeTrue());

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    //--------------------------------------------------------------------

    @Test
    public void nodeFalseVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeFalse node = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeFalse();

        // Compilation
        int n = compilerVisitor.visit(node, 1, CompilerMode.DEFAULT);

        Assert.assertEquals(1, n);

        // Construction of expected instrs
        NodePush nodePush = new NodePush();

        expected.addChild(nodePush);
        nodePush.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeFalse());

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    //--------------------------------------------------------------------

    @Test
    public void nodeOmegaVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        NodeOmega node = new NodeOmega();

        // Compilation
        int n = compilerVisitor.visit(node, 1, CompilerMode.DEFAULT);

        Assert.assertEquals(1, n);

        // Construction of expected instrs
        NodePush nodePush = new NodePush();

        expected.addChild(nodePush);
        nodePush.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeJcnil());

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    //--------------------------------------------------------------------

    @Test
    public void nodeNumberVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber node = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(0);

        // Compilation
        int n = compilerVisitor.visit(node, 1, CompilerMode.DEFAULT);

        Assert.assertEquals(1, n);

        // Construction of expected instrs
        NodePush nodePush = new NodePush();

        expected.addChild(nodePush);
        nodePush.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    //--------------------------------------------------------------------

    @Test
    public void nodeIdentVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent node = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("x", "main");

        // Compilation
        int n = compilerVisitor.visit(node, 1, CompilerMode.DEFAULT);

        Assert.assertEquals(1, n);

        // Construction of expected instrs
        NodeLoad nodeLoad = new NodeLoad();

        expected.addChild(nodeLoad);
        nodeLoad.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("x@main"));

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    //--------------------------------------------------------------------

    @Test //default mode
    public void nodeInilVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        NodeInil node = new NodeInil();

        // Compilation
        int n = compilerVisitor.visit(node, 1, CompilerMode.DEFAULT);

        Assert.assertEquals(0, n);

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    @Test //cancel mode
    public void nodeInilVisitorTestCancel() throws CompilerException {
        // Construction of MJJ AST
        NodeInil node = new NodeInil();

        // Compilation
        int n = compilerVisitor.visit(node, 1, CompilerMode.CANCEL);

        Assert.assertEquals(0, n);

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    //--------------------------------------------------------------------

    @Test //default mode
    public void nodeVnilVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        NodeVnil node = new NodeVnil();

        // Compilation
        int n = compilerVisitor.visit(node, 1, CompilerMode.DEFAULT);

        Assert.assertEquals(0, n);

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    @Test //cancel mode
    public void nodeVnilVisitorTestCancel() throws CompilerException {
        // Construction of MJJ AST
        NodeVnil node = new NodeVnil();

        // Compilation
        int n = compilerVisitor.visit(node, 1, CompilerMode.DEFAULT);

        Assert.assertEquals(0, n);

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    //--------------------------------------------------------------------

    @Test //default mode
    public void nodeEnilVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        NodeEnil node = new NodeEnil();

        // Compilation
        int n = compilerVisitor.visit(node, 1, CompilerMode.DEFAULT);

        Assert.assertEquals(0, n);

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    @Test //cancel mode
    public void nodeEnilVisitorTestCancel() throws CompilerException {
        // Construction of MJJ AST
        NodeEnil node = new NodeEnil();

        // Compilation
        int n = compilerVisitor.visit(node, 1, CompilerMode.CANCEL);

        Assert.assertEquals(0, n);

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    //--------------------------------------------------------------------

    @Test //default mode
    public void nodeExnilVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        NodeExnil node = new NodeExnil();

        // Compilation
        int n = compilerVisitor.visit(node, 1, CompilerMode.DEFAULT);

        Assert.assertEquals(0, n);

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    @Test //cancel mode
    public void nodeExnilVisitorTestCancel() throws CompilerException {
        // Construction of MJJ AST
        NodeExnil node = new NodeExnil();

        // Compilation
        int n = compilerVisitor.visit(node, 1, CompilerMode.CANCEL);

        Assert.assertEquals(0, n);

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    //--------------------------------------------------------------------

    @Test //default mode
    public void nodeVarVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        NodeVar nodeVar = new NodeVar();

        nodeVar.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeBoolean());
        nodeVar.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("x", "main"));
        nodeVar.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeFalse());

        // Compilation
        int n = compilerVisitor.visit(nodeVar, 1, CompilerMode.DEFAULT);

        Assert.assertEquals(2, n);

        // Construction of expected instrs
        NodePush nodePush = new NodePush();

        expected.addChild(nodePush);
        nodePush.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeFalse());

        NodeNew nodeNew = new NodeNew();

        expected.addChild(nodeNew);

        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("x@main"));
        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeType(Type.BOOLEAN));
        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeKind(Kind.VARIABLE));
        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    @Test //cancel mode
    public void nodeVarVisitorTestCancel() throws CompilerException {
        // Construction of MJJ AST
        NodeVar nodeVar = new NodeVar();

        nodeVar.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeBoolean());
        nodeVar.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("x", "main"));
        nodeVar.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeFalse());

        // Compilation
        int n = compilerVisitor.visit(nodeVar, 1, CompilerMode.CANCEL);

        Assert.assertEquals(2, n);

        // Construction of expected instrs
        expected.addChild(new NodeSwap());
        expected.addChild(new NodePop());

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    //--------------------------------------------------------------------

    @Test
    public void nodeVarsVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        NodeVars nodeVars1 = new NodeVars();
        NodeVars nodeVars2 = new NodeVars();

        NodeVar nodeVar1 = new NodeVar();

        nodeVars1.addChild(nodeVar1);
        nodeVars1.addChild(nodeVars2);

        nodeVar1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeBoolean());
        nodeVar1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("y", "main"));
        nodeVar1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeFalse());

        NodeVar nodeVar2 = new NodeVar();

        nodeVars2.addChild(nodeVar2);
        nodeVars2.addChild(new NodeVnil());

        nodeVar2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeBoolean());
        nodeVar2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("x", "main"));
        nodeVar2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeTrue());

        // Compilation
        int n1 = compilerVisitor.visit(nodeVars1, 1, CompilerMode.DEFAULT);
        int n2 = compilerVisitor.visit(nodeVars1, n1, CompilerMode.CANCEL);

        Assert.assertEquals(4, n1);
        Assert.assertEquals(4, n2);

        // Construction of expected instrs
        NodePush nodePush1 = new NodePush();

        expected.addChild(nodePush1);
        nodePush1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeFalse());

        NodeNew nodeNew = new NodeNew();

        expected.addChild(nodeNew);

        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("y@main"));
        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeType(Type.BOOLEAN));
        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeKind(Kind.VARIABLE));
        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));

        NodePush nodePush2 = new NodePush();

        expected.addChild(nodePush2);
        nodePush2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeTrue());

        NodeNew nodeNew2 = new NodeNew();

        expected.addChild(nodeNew2);

        nodeNew2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("x@main"));
        nodeNew2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeType(Type.BOOLEAN));
        nodeNew2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeKind(Kind.VARIABLE));
        nodeNew2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));

        expected.addChild(new NodeSwap());
        expected.addChild(new NodePop());
        expected.addChild(new NodeSwap());
        expected.addChild(new NodePop());

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    //--------------------------------------------------------------------

    @Test
    public void nodeMainVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        NodeVars nodeVars1 = new NodeVars();
        NodeVars nodeVars2 = new NodeVars();

        NodeVar nodeVar1 = new NodeVar();

        nodeVars1.addChild(nodeVar1);
        nodeVars1.addChild(nodeVars2);

        nodeVar1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeBoolean());
        nodeVar1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("y", "main"));
        nodeVar1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeFalse());

        NodeVar nodeVar2 = new NodeVar();

        nodeVars2.addChild(nodeVar2);
        nodeVars2.addChild(new NodeVnil());

        nodeVar2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeBoolean());
        nodeVar2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("x", "main"));
        nodeVar2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeTrue());

        NodeMain nodeMain = new NodeMain();

        nodeMain.addChild(nodeVars1);
        nodeMain.addChild(new NodeInil());

        // Compilation
        int n = compilerVisitor.visit(nodeMain, 1, CompilerMode.DEFAULT);

        Assert.assertEquals(9, n);

        // Construction of expected instrs
        NodePush nodePush = new NodePush();

        expected.addChild(nodePush);
        nodePush.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeFalse());

        NodeNew nodeNew = new NodeNew();

        expected.addChild(nodeNew);

        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("y@main"));
        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeType(Type.BOOLEAN));
        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeKind(Kind.VARIABLE));
        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));

        NodePush nodePush2 = new NodePush();

        expected.addChild(nodePush2);
        nodePush2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeTrue());

        NodeNew nodeNew2 = new NodeNew();

        expected.addChild(nodeNew2);

        nodeNew2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("x@main"));
        nodeNew2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeType(Type.BOOLEAN));
        nodeNew2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeKind(Kind.VARIABLE));
        nodeNew2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));

        NodePush nodePush3 = new NodePush();

        expected.addChild(nodePush3);
        nodePush3.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));

        expected.addChild(new NodeSwap());
        expected.addChild(new NodePop());
        expected.addChild(new NodeSwap());
        expected.addChild(new NodePop());

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    @Test
    public void nodeMainEmptyVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        NodeMain nodeMain = new NodeMain();

        nodeMain.addChild(new NodeVnil());
        nodeMain.addChild(new NodeInil());

        // Compilation
        int n = compilerVisitor.visit(nodeMain, 1, CompilerMode.DEFAULT);

        Assert.assertEquals(1, n);

        // Construction of expected instrs
        NodePush nodePush = new NodePush();

        expected.addChild(nodePush);
        nodePush.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    //--------------------------------------------------------------------

    @Test
    public void nodeDeclsVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        NodeDecls nodeDecls1 = new NodeDecls();
        NodeDecls nodeDecls2 = new NodeDecls();

        NodeVar nodeVar1 = new NodeVar();

        nodeDecls1.addChild(nodeVar1);
        nodeDecls1.addChild(nodeDecls2);

        nodeVar1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeBoolean());
        nodeVar1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("a", "global"));
        nodeVar1.addChild(new NodeOmega());

        NodeVar nodeVar2 = new NodeVar();

        nodeDecls2.addChild(nodeVar2);
        nodeDecls2.addChild(new NodeVnil());

        nodeVar2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeInteger());
        nodeVar2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("y", "global"));
        nodeVar2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(4));

        // Compilation
        int n1 = compilerVisitor.visit(nodeDecls1, 1, CompilerMode.DEFAULT);
        int n2 = compilerVisitor.visit(nodeDecls1, n1, CompilerMode.CANCEL);

        Assert.assertEquals(4, n1);
        Assert.assertEquals(4, n2);

        // Construction of expected instrs
        NodePush nodePush = new NodePush();

        expected.addChild(nodePush);
        nodePush.addChild(new NodeJcnil());

        NodeNew nodeNew = new NodeNew();

        expected.addChild(nodeNew);

        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("a@global"));
        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeType(Type.BOOLEAN));
        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeKind(Kind.VARIABLE));
        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));

        NodePush nodePush2 = new NodePush();

        expected.addChild(nodePush2);
        nodePush2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(4));

        NodeNew nodeNew2 = new NodeNew();

        expected.addChild(nodeNew2);

        nodeNew2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("y@global"));
        nodeNew2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeType(Type.INTEGER));
        nodeNew2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeKind(Kind.VARIABLE));
        nodeNew2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));

        expected.addChild(new NodeSwap());
        expected.addChild(new NodePop());
        expected.addChild(new NodeSwap());
        expected.addChild(new NodePop());

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    //--------------------------------------------------------------------

    @Test
    public void nodeClassEmptyVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        NodeMain nodeMain = new NodeMain();

        nodeMain.addChild(new NodeVnil());
        nodeMain.addChild(new NodeInil());

        NodeClass nodeClass = new NodeClass();

        nodeClass.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("C", "global"));
        nodeClass.addChild(new NodeVnil());
        nodeClass.addChild(nodeMain);

        // Compilation
        int n = compilerVisitor.visit(nodeClass, 1, CompilerMode.DEFAULT, null);

        Assert.assertEquals(4, n);

        // Construction of expected instrs
        expected.addChild(new NodeInit());

        NodePush nodePush = new NodePush();

        expected.addChild(nodePush);
        nodePush.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));

        expected.addChild(new NodePop());
        expected.addChild(new NodeJcstop());

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    @Test
    public void nodeClassVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        NodeDecls nodeDecls1 = new NodeDecls();
        NodeDecls nodeDecls2 = new NodeDecls();

        NodeVar nodeVar1 = new NodeVar();

        nodeDecls1.addChild(nodeVar1);
        nodeDecls1.addChild(nodeDecls2);

        nodeVar1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeBoolean());
        nodeVar1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("a", "global"));
        nodeVar1.addChild(new NodeOmega());

        NodeVar nodeVar2 = new NodeVar();

        nodeDecls2.addChild(nodeVar2);
        nodeDecls2.addChild(new NodeVnil());

        nodeVar2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeInteger());
        nodeVar2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("y", "global"));
        nodeVar2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(4));

        NodeVars nodeVars1 = new NodeVars();
        NodeVars nodeVars2 = new NodeVars();

        NodeVar nodeVar3 = new NodeVar();

        nodeVars1.addChild(nodeVar3);
        nodeVars1.addChild(nodeVars2);

        nodeVar3.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeBoolean());
        nodeVar3.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("y", "main"));
        nodeVar3.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeFalse());

        NodeVar nodeVar4 = new NodeVar();

        nodeVars2.addChild(nodeVar4);
        nodeVars2.addChild(new NodeVnil());

        nodeVar4.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeBoolean());
        nodeVar4.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("x", "main"));
        nodeVar4.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeTrue());

        NodeMain nodeMain = new NodeMain();

        nodeMain.addChild(nodeVars1);
        nodeMain.addChild(new NodeInil());

        NodeClass nodeClass = new NodeClass();

        nodeClass.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("C", "global"));
        nodeClass.addChild(nodeDecls1);
        nodeClass.addChild(nodeMain);

        // Compilation
        int n = compilerVisitor.visit(nodeClass, 1, CompilerMode.DEFAULT, null);

        Assert.assertEquals(20, n);

        // Construction of expected instrs
        expected.addChild(new NodeInit());

        NodePush nodePush1 = new NodePush();

        expected.addChild(nodePush1);
        nodePush1.addChild(new NodeJcnil());

        NodeNew nodeNew1 = new NodeNew();

        expected.addChild(nodeNew1);

        nodeNew1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("a@global"));
        nodeNew1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeType(Type.BOOLEAN));
        nodeNew1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeKind(Kind.VARIABLE));
        nodeNew1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));

        NodePush nodePush2 = new NodePush();

        expected.addChild(nodePush2);
        nodePush2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(4));

        NodeNew nodeNew2 = new NodeNew();

        expected.addChild(nodeNew2);

        nodeNew2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("y@global"));
        nodeNew2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeType(Type.INTEGER));
        nodeNew2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeKind(Kind.VARIABLE));
        nodeNew2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));

        NodePush nodePush3 = new NodePush();

        expected.addChild(nodePush3);
        nodePush3.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeFalse());

        NodeNew nodeNew3 = new NodeNew();

        expected.addChild(nodeNew3);

        nodeNew3.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("y@main"));
        nodeNew3.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeType(Type.BOOLEAN));
        nodeNew3.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeKind(Kind.VARIABLE));
        nodeNew3.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));

        NodePush nodePush4 = new NodePush();

        expected.addChild(nodePush4);
        nodePush4.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeTrue());

        NodeNew nodeNew4 = new NodeNew();

        expected.addChild(nodeNew4);

        nodeNew4.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("x@main"));
        nodeNew4.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeType(Type.BOOLEAN));
        nodeNew4.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeKind(Kind.VARIABLE));
        nodeNew4.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));

        NodePush nodePush5 = new NodePush();

        expected.addChild(nodePush5);
        nodePush5.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));

        expected.addChild(new NodeSwap());
        expected.addChild(new NodePop());
        expected.addChild(new NodeSwap());
        expected.addChild(new NodePop());
        expected.addChild(new NodeSwap());
        expected.addChild(new NodePop());
        expected.addChild(new NodeSwap());
        expected.addChild(new NodePop());
        expected.addChild(new NodePop());
        expected.addChild(new NodeJcstop());

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    //--------------------------------------------------------------------

    @Test
    public void nodeAssignmentVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        NodeAssignment node = new NodeAssignment();

        node.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("x", "global"));
        node.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(0));

        // Compilation
        int n = compilerVisitor.visit(node, 1, CompilerMode.DEFAULT);

        Assert.assertEquals(2, n);

        // Construction of expected instrs
        NodePush nodePush = new NodePush();

        expected.addChild(nodePush);
        nodePush.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));

        NodeStore nodeStore = new NodeStore();

        expected.addChild(nodeStore);
        nodeStore.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("x@global"));

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    @Test
    public void nodeAssignmentArrayVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        NodeAssignment node = new NodeAssignment();

        NodeArrayGet nodeArrayGet = new NodeArrayGet();
        nodeArrayGet.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("tab", "global"));
        nodeArrayGet.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(3));

        node.addChild(nodeArrayGet);
        node.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(0));

        // Compilation
        int n = compilerVisitor.visit(node, 1, CompilerMode.DEFAULT);

        Assert.assertEquals(3, n);

        // Construction of expected instrs
        NodePush nodePush1 = new NodePush();
        nodePush1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(3));
        expected.addChild(nodePush1);

        NodePush nodePush2 = new NodePush();
        nodePush2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));
        expected.addChild(nodePush2);

        NodeAstore nodeAstore = new NodeAstore();

        expected.addChild(nodeAstore);
        nodeAstore.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("tab@global"));

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    //--------------------------------------------------------------------

    @Test
    public void nodeCstVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        NodeCst nodeCst = new NodeCst();

        nodeCst.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeBoolean());
        nodeCst.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("x", "main"));
        nodeCst.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeFalse());

        // Compilation
        int n1 = compilerVisitor.visit(nodeCst, 1, CompilerMode.DEFAULT);
        int n2 = compilerVisitor.visit(nodeCst, n1, CompilerMode.CANCEL);

        Assert.assertEquals(2, n1);
        Assert.assertEquals(2, n2);

        // Construction of expected instrs
        NodePush nodePush = new NodePush();

        expected.addChild(nodePush);
        nodePush.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeFalse());

        NodeNew nodeNew = new NodeNew();

        expected.addChild(nodeNew);

        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("x@main"));
        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeType(Type.BOOLEAN));
        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeKind(Kind.CONSTANT));
        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));

        expected.addChild(new NodeSwap());
        expected.addChild(new NodePop());

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    //--------------------------------------------------------------------

    @Test
    public void nodeIncrementVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        NodeIncrement node = new NodeIncrement();

        node.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("x", "global"));

        // Compilation
        int n = compilerVisitor.visit(node, 1, CompilerMode.DEFAULT);

        Assert.assertEquals(2, n);

        // Construction of expected instrs
        NodePush nodePush = new NodePush();

        expected.addChild(nodePush);
        nodePush.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(1));

        NodeInc nodeInc = new NodeInc();

        expected.addChild(nodeInc);
        nodeInc.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("x@global"));

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    @Test
    public void nodeIncrementArrayVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        NodeIncrement node = new NodeIncrement();

        NodeArrayGet nodeArrayGet = new NodeArrayGet();
        nodeArrayGet.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("tab", "global"));
        nodeArrayGet.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(3));

        node.addChild(nodeArrayGet);

        // Compilation
        int n = compilerVisitor.visit(node, 1, CompilerMode.DEFAULT);

        Assert.assertEquals(3, n);

        // Construction of expected instrs
        NodePush nodePush1 = new NodePush();
        nodePush1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(3));
        expected.addChild(nodePush1);

        NodePush nodePush2 = new NodePush();
        nodePush2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(1));
        expected.addChild(nodePush2);

        NodeAinc nodeAinc = new NodeAinc();
        nodeAinc.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("tab@global"));

        expected.addChild(nodeAinc);

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    //--------------------------------------------------------------------

    //sum for a variable
    @Test
    public void nodeSumVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        NodeSum node = new NodeSum();

        node.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("x", "global"));
        node.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(2));

        // Compilation
        int n = compilerVisitor.visit(node, 1, CompilerMode.DEFAULT);

        Assert.assertEquals(2, n);

        // Construction of expected instrs
        NodePush nodePush = new NodePush();

        expected.addChild(nodePush);
        nodePush.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(2));

        NodeInc nodeInc = new NodeInc();

        expected.addChild(nodeInc);
        nodeInc.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("x@global"));

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    //sum for an array
    @Test
    public void nodeSumArrayVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        NodeSum node = new NodeSum();

        NodeArrayGet nodeArrayGet = new NodeArrayGet();
        nodeArrayGet.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("tab", "global"));
        nodeArrayGet.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(1));

        node.addChild(nodeArrayGet);
        node.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(4));

        // Compilation
        int n = compilerVisitor.visit(node, 1, CompilerMode.DEFAULT);

        Assert.assertEquals(3, n);

        // Construction of expected instrs
        NodePush nodePush1 = new NodePush();
        nodePush1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(1));
        expected.addChild(nodePush1);

        NodePush nodePush2 = new NodePush();
        nodePush2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(4));
        expected.addChild(nodePush2);

        NodeAinc nodeAinc = new NodeAinc();
        nodeAinc.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("tab@global"));

        expected.addChild(nodeAinc);

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    //--------------------------------------------------------------------

    @Test
    public void nodeNotVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNot node = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNot();

        node.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeTrue());

        // Compilation
        int n = compilerVisitor.visit(node, 1, CompilerMode.DEFAULT);

        Assert.assertEquals(2, n);

        // Construction of expected instrs
        NodePush nodePush = new NodePush();

        expected.addChild(nodePush);
        nodePush.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeTrue());

        fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNot nodeNot = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNot();

        expected.addChild(nodeNot);

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    //--------------------------------------------------------------------

    @Test
    public void nodeNegVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNeg node = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNeg();

        node.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(1));

        // Compilation
        int n = compilerVisitor.visit(node, 1, CompilerMode.DEFAULT);

        Assert.assertEquals(2, n);

        // Construction of expected instrs
        NodePush nodePush = new NodePush();

        expected.addChild(nodePush);
        nodePush.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(1));

        fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNeg nodeNeg = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNeg();

        expected.addChild(nodeNeg);

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    //--------------------------------------------------------------------

    @Test
    public void nodeOrVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeOr node = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeOr();

        node.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeTrue());
        node.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeFalse());

        // Compilation
        int n = compilerVisitor.visit(node, 1, CompilerMode.DEFAULT);

        Assert.assertEquals(5, n);

        // Construction of expected instrs
        NodePush nodePush1 = new NodePush();
        nodePush1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeTrue());
        expected.addChild(nodePush1);

        fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIf nodeIf = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIf();
        nodeIf.addChild(new NodeNumber(5));
        expected.addChild(nodeIf);

        NodePush nodePush2 = new NodePush();
        nodePush2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeFalse());
        expected.addChild(nodePush2);

        NodeGoto nodeGoto = new NodeGoto();
        nodeGoto.addChild(new NodeNumber(6));
        expected.addChild(nodeGoto);

        NodePush nodePush3 = new NodePush();
        nodePush3.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeTrue());
        expected.addChild(nodePush3);

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    //--------------------------------------------------------------------

    @Test
    public void nodeSupVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeSup node = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeSup();

        node.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(1));
        node.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(1));

        // Compilation
        int n = compilerVisitor.visit(node, 1, CompilerMode.DEFAULT);

        Assert.assertEquals(3, n);

        // Construction of expected instrs
        NodePush nodePush1 = new NodePush();

        expected.addChild(nodePush1);
        nodePush1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(1));

        NodePush nodePush2 = new NodePush();

        expected.addChild(nodePush2);
        nodePush2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(1));

        fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeSup nodeSup = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeSup();

        expected.addChild(nodeSup);

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    //--------------------------------------------------------------------

    @Test
    public void nodeAndVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeAnd node = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeAnd();

        node.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeTrue());
        node.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeFalse());

        // Compilation
        int n = compilerVisitor.visit(node, 1, CompilerMode.DEFAULT);

        Assert.assertEquals(5, n);

        // Construction of expected instrs
        NodePush nodePush1 = new NodePush();
        nodePush1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeTrue());
        expected.addChild(nodePush1);

        fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIf nodeIf = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIf();
        nodeIf.addChild(new NodeNumber(5));
        expected.addChild(nodeIf);

        NodePush nodePush2 = new NodePush();
        nodePush2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeFalse());
        expected.addChild(nodePush2);

        NodeGoto nodeGoto = new NodeGoto();
        nodeGoto.addChild(new NodeNumber(6));
        expected.addChild(nodeGoto);

        NodePush nodePush3 = new NodePush();
        nodePush3.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeFalse());
        expected.addChild(nodePush3);

        Assert.assertTrue(instrs.isEquivalentTo(expected));

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    //--------------------------------------------------------------------

    @Test
    public void nodeCmpVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeCmp node = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeCmp();

        node.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(1));
        node.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(1));

        // Compilation
        int n = compilerVisitor.visit(node, 1, CompilerMode.DEFAULT);

        Assert.assertEquals(3, n);

        // Construction of expected instrs
        NodePush nodePush1 = new NodePush();

        expected.addChild(nodePush1);
        nodePush1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(1));

        NodePush nodePush2 = new NodePush();

        expected.addChild(nodePush2);
        nodePush2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(1));

        fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeCmp nodeCmp = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeCmp();

        expected.addChild(nodeCmp);

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    //--------------------------------------------------------------------

    @Test
    public void nodeAddVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeAdd node = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeAdd();

        node.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(1));
        node.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(1));

        // Compilation
        int n = compilerVisitor.visit(node, 1, CompilerMode.DEFAULT);

        Assert.assertEquals(3, n);

        // Construction of expected instrs
        NodePush nodePush1 = new NodePush();

        expected.addChild(nodePush1);
        nodePush1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(1));

        NodePush nodePush2 = new NodePush();

        expected.addChild(nodePush2);
        nodePush2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(1));

        fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeAdd nodeAdd = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeAdd();

        expected.addChild(nodeAdd);

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    //--------------------------------------------------------------------

    @Test
    public void nodeSubVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeSub node = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeSub();

        node.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(1));
        node.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(1));

        // Compilation
        int n = compilerVisitor.visit(node, 1, CompilerMode.DEFAULT);

        Assert.assertEquals(3, n);

        // Construction of expected instrs
        NodePush nodePush1 = new NodePush();

        expected.addChild(nodePush1);
        nodePush1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(1));

        NodePush nodePush2 = new NodePush();

        expected.addChild(nodePush2);
        nodePush2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(1));

        fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeSub nodeSub = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeSub();

        expected.addChild(nodeSub);

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    //--------------------------------------------------------------------

    @Test
    public void nodeMulVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMul node = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMul();

        node.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(1));
        node.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(1));

        // Compilation
        int n = compilerVisitor.visit(node, 1, CompilerMode.DEFAULT);

        Assert.assertEquals(3, n);

        // Construction of expected instrs
        NodePush nodePush1 = new NodePush();

        expected.addChild(nodePush1);
        nodePush1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(1));

        NodePush nodePush2 = new NodePush();

        expected.addChild(nodePush2);
        nodePush2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(1));

        fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeMul nodeMul = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeMul();

        expected.addChild(nodeMul);

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    //--------------------------------------------------------------------

    @Test
    public void nodeDivVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeDiv node = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeDiv();

        node.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(1));
        node.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(1));

        // Compilation
        int n = compilerVisitor.visit(node, 1, CompilerMode.DEFAULT);

        Assert.assertEquals(3, n);

        // Construction of expected instrs
        NodePush nodePush1 = new NodePush();

        expected.addChild(nodePush1);
        nodePush1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(1));

        NodePush nodePush2 = new NodePush();

        expected.addChild(nodePush2);
        nodePush2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(1));

        fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeDiv nodeDiv = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeDiv();

        expected.addChild(nodeDiv);

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    //--------------------------------------------------------------------

    @Test
    public void nodeInstrs() throws CompilerException {
        //construction of mjj ast
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeInstrs nodeInstrs1 = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeInstrs();
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeInstrs nodeInstrs2 = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeInstrs();

        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeReturn nodeReturn = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeReturn();
        nodeReturn.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeTrue());

        NodeAssignment nodeAssignment1 = new NodeAssignment();
        nodeAssignment1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("x", "global"));
        nodeAssignment1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(1));

        nodeInstrs1.addChild(nodeReturn);
        nodeInstrs1.addChild(nodeInstrs2);

        nodeInstrs2.addChild(nodeAssignment1);
        nodeInstrs2.addChild(new NodeInil());

        // Compilation
        int n = compilerVisitor.visit(nodeInstrs1, 1, CompilerMode.DEFAULT);

        Assert.assertEquals(2, n);

        //Construction of expected Instrs
        NodePush nodePush1 = new NodePush();
        nodePush1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeTrue());
        expected.addChild(nodePush1);

        NodeGoto nodeGoto = new NodeGoto();
        expected.addChild(nodeGoto);

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    //--------------------------------------------------------------------

    @Test
    public void nodeIfVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIf node = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIf();
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeInstrs nodeInstrs1 = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeInstrs();
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeInstrs nodeInstrs2 = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeInstrs();
        NodeAssignment nodeAssignment1 = new NodeAssignment();
        NodeAssignment nodeAssignment2 = new NodeAssignment();

        node.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeTrue());
        node.addChild(nodeInstrs1);
        node.addChild(nodeInstrs2);

        nodeInstrs1.addChild(nodeAssignment1);
        nodeInstrs1.addChild(new NodeInil());

        nodeInstrs2.addChild(nodeAssignment2);
        nodeInstrs2.addChild(new NodeInil());

        nodeAssignment1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("x", "global"));
        nodeAssignment1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(1));

        nodeAssignment2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("y", "global"));
        nodeAssignment2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(1));

        // Compilation
        int n = compilerVisitor.visit(node, 1, CompilerMode.DEFAULT);

        Assert.assertEquals(7, n);

        // Construction of expected instrs
        NodePush nodePush1 = new NodePush();

        expected.addChild(nodePush1);
        nodePush1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeTrue());

        fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIf nodeIf = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIf();

        expected.addChild(nodeIf);
        nodeIf.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(6));

        NodePush nodePush2 = new NodePush();

        expected.addChild(nodePush2);
        nodePush2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(1));

        NodeStore nodeStore1 = new NodeStore();

        expected.addChild(nodeStore1);
        nodeStore1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("y@global"));

        NodeGoto nodeGoto = new NodeGoto();

        expected.addChild(nodeGoto);
        nodeGoto.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(8));

        NodePush nodePush3 = new NodePush();

        expected.addChild(nodePush3);
        nodePush3.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(1));

        NodeStore nodeStore2 = new NodeStore();

        expected.addChild(nodeStore2);
        nodeStore2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("x@global"));

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    //--------------------------------------------------------------------

    @Test
    public void nodeWhileVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeWhile node = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeWhile();
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeInstrs nodeInstrs = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeInstrs();
        NodeAssignment nodeAssignment = new NodeAssignment();

        node.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeTrue());
        node.addChild(nodeInstrs);

        nodeInstrs.addChild(nodeAssignment);
        nodeInstrs.addChild(new NodeInil());

        nodeAssignment.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("x", "global"));
        nodeAssignment.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(1));

        // Compilation
        int n = compilerVisitor.visit(node, 1, CompilerMode.DEFAULT);

        Assert.assertEquals(6, n);

        // Construction of expected instrs
        NodePush nodePush1 = new NodePush();

        expected.addChild(nodePush1);
        nodePush1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeTrue());

        fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNot nodeNot = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNot();

        expected.addChild(nodeNot);

        fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIf nodeIf = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIf();

        expected.addChild(nodeIf);
        nodeIf.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(7));

        NodePush nodePush2 = new NodePush();

        expected.addChild(nodePush2);
        nodePush2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(1));

        NodeStore nodeStore = new NodeStore();

        expected.addChild(nodeStore);
        nodeStore.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("x@global"));

        NodeGoto nodeGoto = new NodeGoto();

        expected.addChild(nodeGoto);
        nodeGoto.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(1));

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    //--------------------------------------------------------------------

    @Test
    public void nodeArrayVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        NodeArray node = new NodeArray();

        node.addChild(new NodeInteger());
        node.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("tab", "global"));
        node.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(4));

        // Compilation
        int n = compilerVisitor.visit(node, 1, CompilerMode.DEFAULT);

        Assert.assertEquals(2, n);

        // Construction of expected instrs
        NodePush nodePush1 = new NodePush();
        nodePush1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(4));

        expected.addChild(nodePush1);

        NodeNewArray nodeNewArray = new NodeNewArray();
        nodeNewArray.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("tab@global"));
        nodeNewArray.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeType(Type.INTEGER));

        expected.addChild(nodeNewArray);

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    @Test //cancel mode
    public void nodeArrayVisitorTestCancel() throws CompilerException {
        // Construction of MJJ AST
        NodeArray node = new NodeArray();

        node.addChild(new NodeInteger());
        node.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("tab", "global"));
        node.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(4));

        // Compilation
        int n = compilerVisitor.visit(node, 1, CompilerMode.CANCEL, "main");

        Assert.assertEquals(2, n);

        // Construction of expected instrs

        expected.addChild(new NodeSwap());
        expected.addChild(new NodePop());

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    //--------------------------------------------------------------------

    @Test
    public void nodeArrayGetVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        NodeArrayGet node = new NodeArrayGet();

        node.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("tab", "global"));
        node.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(1));

        // Compilation
        int n = compilerVisitor.visit(node, 1, CompilerMode.DEFAULT);

        Assert.assertEquals(2, n);

        // Construction of expected instrs
        NodePush nodePush1 = new NodePush();
        nodePush1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(1));

        expected.addChild(nodePush1);

        NodeAload nodeAload = new NodeAload();
        nodeAload.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("tab@global"));

        expected.addChild(nodeAload);

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    //--------------------------------------------------------------------

    @Test
    public void nodeHeaderVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        NodeHeader node = new NodeHeader();

        node.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeInteger());
        node.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("arg", "fct:int_boolean"));

        // Compilation
        int n = compilerVisitor.visit(node, 1, CompilerMode.DEFAULT, 3);

        Assert.assertEquals(1, n);

        // Construction of expected instrs
        NodeNew nodeNew = new NodeNew();
        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("arg@fct:int_boolean"));
        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeType(Type.INTEGER));
        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeKind(Kind.VARIABLE));
        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(3));

        expected.addChild(nodeNew);

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    //--------------------------------------------------------------------

    @Test
    public void nodeHeadersVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        NodeHeaders nodeHeaders1 = new NodeHeaders();
        NodeHeaders nodeHeaders2 = new NodeHeaders();

        NodeHeader nodeHeader1 = new NodeHeader();
        nodeHeader1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeInteger());
        nodeHeader1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("arg1", "fct:int_boolean"));

        NodeHeader nodeHeader2 = new NodeHeader();
        nodeHeader2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeBoolean());
        nodeHeader2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("arg2", "fct:int_boolean"));

        nodeHeaders1.addChild(nodeHeader1);
        nodeHeaders1.addChild(nodeHeaders2);
        nodeHeaders2.addChild(nodeHeader2);
        nodeHeaders2.addChild(new NodeEnil());

        // Compilation
        int n = compilerVisitor.visit(nodeHeaders1, 1, CompilerMode.DEFAULT, 2);

        Assert.assertEquals(2, n);

        // Construction of expected instrs
        NodeNew nodeNew1 = new NodeNew();
        nodeNew1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("arg1@fct:int_boolean"));
        nodeNew1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeType(Type.INTEGER));
        nodeNew1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeKind(Kind.VARIABLE));
        nodeNew1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(2));

        expected.addChild(nodeNew1);

        NodeNew nodeNew2 = new NodeNew();
        nodeNew2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("arg2@fct:int_boolean"));
        nodeNew2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeType(Type.BOOLEAN));
        nodeNew2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeKind(Kind.VARIABLE));
        nodeNew2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(1));

        expected.addChild(nodeNew2);

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    //--------------------------------------------------------------------

    @Test
    public void nodeMethodVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        //------------------- headers ------------------
        NodeHeaders nodeHeaders1 = new NodeHeaders();
        NodeHeaders nodeHeaders2 = new NodeHeaders();

        NodeHeader nodeHeader1 = new NodeHeader();
        nodeHeader1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeInteger());
        nodeHeader1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("arg1", "fct:int_boolean"));

        NodeHeader nodeHeader2 = new NodeHeader();
        nodeHeader2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeBoolean());
        nodeHeader2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("arg2", "fct:int_boolean"));

        nodeHeaders1.addChild(nodeHeader1);
        nodeHeaders1.addChild(nodeHeaders2);
        nodeHeaders2.addChild(nodeHeader2);
        nodeHeaders2.addChild(new NodeEnil());

        //------------------- vars ------------------
        NodeVars nodeVars1 = new NodeVars();
        NodeVar nodeVar = new NodeVar();
        nodeVars1.addChild(nodeVar);
        nodeVars1.addChild(new NodeVnil());

        nodeVar.addChild(new NodeInteger());
        nodeVar.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("localVar", "fct:int_boolean"));
        nodeVar.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(100));

        //------------------- instrs ------------------
        NodeAssignment nodeAssignment = new NodeAssignment();
        nodeAssignment.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("localVar", "fct:int_boolean"));
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMul nodeMul = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMul();
        nodeMul.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("localVar", "fct:int_boolean"));
        nodeMul.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("arg1", "fct:int_boolean"));
        nodeAssignment.addChild(nodeMul);

        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeReturn nodeReturn = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeReturn();
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeAdd nodeAdd = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeAdd();
        nodeAdd.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("localVar", "fct:int_boolean"));
        nodeAdd.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("arg2", "fct:int_boolean"));
        nodeReturn.addChild(nodeAdd);

        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeInstrs nodeInstrs1 = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeInstrs();
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeInstrs nodeInstrs2 = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeInstrs();
        nodeInstrs1.addChild(nodeAssignment);
        nodeInstrs1.addChild(nodeInstrs2);
        nodeInstrs2.addChild(nodeReturn);
        nodeInstrs2.addChild(new NodeInil());

        //------------------ method------------------
        NodeMethod node = new NodeMethod();

        node.addChild(new NodeInteger());
        node.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("fct", "global", "int_boolean"));
        node.addChild(nodeHeaders1);
        node.addChild(nodeVars1);
        node.addChild(nodeInstrs1);

        // Compilation
        int n = compilerVisitor.visit(node, 1, CompilerMode.DEFAULT);

        Assert.assertEquals(19, n);

        // Construction of expected instrs
        NodePush nodePush1 = new NodePush();
        nodePush1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(4));
        expected.addChild(nodePush1);

        NodeNew nodeNewFct = new NodeNew();
        nodeNewFct.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("fct:int_boolean"));
        nodeNewFct.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeType(Type.INTEGER));
        nodeNewFct.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeKind(Kind.METHOD));
        nodeNewFct.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));
        expected.addChild(nodeNewFct);

        NodeGoto nodeGoto = new NodeGoto();
        nodeGoto.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(20));
        expected.addChild(nodeGoto);

        //headers
        NodeNew nodeNewArg1 = new NodeNew();
        nodeNewArg1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("arg1@fct:int_boolean"));
        nodeNewArg1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeType(Type.INTEGER));
        nodeNewArg1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeKind(Kind.VARIABLE));
        nodeNewArg1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(2));
        expected.addChild(nodeNewArg1);

        NodeNew nodeNewArg2 = new NodeNew();
        nodeNewArg2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("arg2@fct:int_boolean"));
        nodeNewArg2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeType(Type.BOOLEAN));
        nodeNewArg2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeKind(Kind.VARIABLE));
        nodeNewArg2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(1));
        expected.addChild(nodeNewArg2);

        //declarations
        NodePush nodePushLocalVar = new NodePush();
        nodePushLocalVar.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(100));
        expected.addChild(nodePushLocalVar);

        NodeNew nodeNewLocalVar = new NodeNew();
        nodeNewLocalVar.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("localVar@fct:int_boolean"));
        nodeNewLocalVar.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeType(Type.INTEGER));
        nodeNewLocalVar.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeKind(Kind.VARIABLE));
        nodeNewLocalVar.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));
        expected.addChild(nodeNewLocalVar);

        //instructions
        NodeLoad loadLocalVar = new NodeLoad();
        loadLocalVar.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("localVar@fct:int_boolean"));
        NodeLoad loadArg1 = new NodeLoad();
        loadArg1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("arg1@fct:int_boolean"));
        NodeLoad loadArg2 = new NodeLoad();
        loadArg2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("arg2@fct:int_boolean"));

        expected.addChild(loadLocalVar);
        expected.addChild(loadArg1);
        expected.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeMul());
        NodeStore nodeStore = new NodeStore();
        nodeStore.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("localVar@fct:int_boolean"));
        expected.addChild(nodeStore);

        expected.addChild(loadLocalVar);
        expected.addChild(loadArg2);
        expected.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeAdd());

        NodeGoto nodeGoto2 = new NodeGoto();
        nodeGoto2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(16));
        expected.addChild(nodeGoto2);

        //cancel declarations
        expected.addChild(new NodeSwap());
        expected.addChild(new NodePop());

        expected.addChild(new NodeSwap());
        expected.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeReturn());

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    @Test
    public void nodeVoidMethodVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        NodeMethod nodeMethod = new NodeMethod();
        NodeEnil nodeHeaders = new NodeEnil();
        NodeVnil nodeVars = new NodeVnil();
        NodeInil nodeInstrs = new NodeInil();

        nodeMethod.addChild(new NodeVoid());
        nodeMethod.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("fct", "global", ""));
        nodeMethod.addChild(nodeHeaders);
        nodeMethod.addChild(nodeVars);
        nodeMethod.addChild(nodeInstrs);

        // Compilation
        int n = compilerVisitor.visit(nodeMethod, 1, CompilerMode.DEFAULT);

        Assert.assertEquals(6, n);

        // Construction of expected instrs
        NodePush nodePush1 = new NodePush();
        nodePush1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(4));
        expected.addChild(nodePush1);

        NodeNew nodeNew = new NodeNew();
        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("fct:"));
        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeType(Type.VOID));
        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeKind(Kind.METHOD));
        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));
        expected.addChild(nodeNew);

        NodeGoto nodeGoto = new NodeGoto();
        nodeGoto.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(7));
        expected.addChild(nodeGoto);

        NodePush nodePush0 = new NodePush();
        nodePush0.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));
        expected.addChild(nodePush0);

        expected.addChild(new NodeSwap());
        expected.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeReturn());

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    @Test
    public void nodeVoidMethodVisitorTestCancel() throws CompilerException {
        // Construction of MJJ AST
        NodeMethod nodeMethod = new NodeMethod();
        NodeEnil nodeHeaders = new NodeEnil();
        NodeVnil nodeVars = new NodeVnil();
        NodeInil nodeInstrs = new NodeInil();

        nodeMethod.addChild(new NodeVoid());
        nodeMethod.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("fct", "global", ""));
        nodeMethod.addChild(nodeHeaders);
        nodeMethod.addChild(nodeVars);
        nodeMethod.addChild(nodeInstrs);

        // Compilation
        int n = compilerVisitor.visit(nodeMethod, 1, CompilerMode.CANCEL);

        Assert.assertEquals(2, n);

        // Construction of expected instrs
        expected.addChild(new NodeSwap());
        expected.addChild(new NodePop());

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    //--------------------------------------------------------------------

    @Test //default mode on 2 element expList
    public void nodeExpListVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        NodeExpList nodeExpList1 = new NodeExpList();
        NodeExpList nodeExpList2 = new NodeExpList();
        NodeEnil nodeEnil = new NodeEnil();

        nodeExpList1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(4));
        nodeExpList1.addChild(nodeExpList2);
        nodeExpList2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeFalse());
        nodeExpList2.addChild(nodeEnil);

        // Compilation
        int n = compilerVisitor.visit(nodeExpList1, 1, CompilerMode.DEFAULT);

        Assert.assertEquals(2, n);

        // Construction of expected instrs
        NodePush nodePush1 = new NodePush();
        nodePush1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(4));

        expected.addChild(nodePush1);

        NodePush nodePush2 = new NodePush();
        nodePush2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeFalse());

        expected.addChild(nodePush2);

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    @Test //cancel mode on 2 element expList
    public void nodeExpListVisitorTestCancel() throws CompilerException {
        // Construction of MJJ AST
        NodeExpList nodeExpList1 = new NodeExpList();
        NodeExpList nodeExpList2 = new NodeExpList();
        NodeEnil nodeEnil = new NodeEnil();

        nodeExpList1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(4));
        nodeExpList1.addChild(nodeExpList2);
        nodeExpList2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeFalse());
        nodeExpList2.addChild(nodeEnil);

        // Compilation
        int n = compilerVisitor.visit(nodeExpList1, 1, CompilerMode.CANCEL);

        Assert.assertEquals(4, n);

        // Construction of expected instrs
        expected.addChild(new NodeSwap());
        expected.addChild(new NodePop());
        expected.addChild(new NodeSwap());
        expected.addChild(new NodePop());

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    //--------------------------------------------------------------------

    @Test
    public void nodeCallIVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        NodeCallI nodeCallI = new NodeCallI();

        NodeExpList nodeExpList1 = new NodeExpList();
        NodeExpList nodeExpList2 = new NodeExpList();
        NodeEnil nodeEnil = new NodeEnil();

        nodeExpList1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(4));
        nodeExpList1.addChild(nodeExpList2);
        nodeExpList2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeFalse());
        nodeExpList2.addChild(nodeEnil);

        nodeCallI.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("fct", "global", "int_boolean"));
        nodeCallI.addChild(nodeExpList1);

        // Compilation
        int n = compilerVisitor.visit(nodeCallI, 1, CompilerMode.DEFAULT);

        Assert.assertEquals(8, n);

        // Construction of expected instrs
        NodePush nodePush1 = new NodePush();
        nodePush1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(4));
        expected.addChild(nodePush1);

        NodePush nodePush2 = new NodePush();
        nodePush2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeFalse());
        expected.addChild(nodePush2);

        NodeInvoke nodeInvoke = new NodeInvoke();
        nodeInvoke.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("fct:int_boolean"));
        expected.addChild(nodeInvoke);

        expected.addChild(new NodeSwap());
        expected.addChild(new NodePop());
        expected.addChild(new NodeSwap());
        expected.addChild(new NodePop());

        expected.addChild(new NodePop());

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    //--------------------------------------------------------------------

    @Test
    public void nodeCallEVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        NodeCallE nodeCallE = new NodeCallE();

        NodeExpList nodeExpList1 = new NodeExpList();
        NodeExpList nodeExpList2 = new NodeExpList();
        NodeEnil nodeEnil = new NodeEnil();

        nodeExpList1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(4));
        nodeExpList1.addChild(nodeExpList2);
        nodeExpList2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeFalse());
        nodeExpList2.addChild(nodeEnil);

        nodeCallE.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("fct", "global", "int_boolean"));
        nodeCallE.addChild(nodeExpList1);

        // Compilation
        int n = compilerVisitor.visit(nodeCallE, 1, CompilerMode.DEFAULT);

        Assert.assertEquals(7, n);

        // Construction of expected instrs
        NodePush nodePush1 = new NodePush();
        nodePush1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(4));
        expected.addChild(nodePush1);

        NodePush nodePush2 = new NodePush();
        nodePush2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeFalse());
        expected.addChild(nodePush2);

        NodeInvoke nodeInvoke = new NodeInvoke();
        nodeInvoke.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("fct:int_boolean"));
        expected.addChild(nodeInvoke);

        expected.addChild(new NodeSwap());
        expected.addChild(new NodePop());
        expected.addChild(new NodeSwap());
        expected.addChild(new NodePop());

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    //--------------------------------------------------------------------

    @Test
    public void nodeReturnVisitorTest() throws CompilerException {
        // Construction of MJJ AST
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeReturn nodeReturn = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeReturn();
        nodeReturn.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(4));

        // Compilation
        int n = compilerVisitor.visit(nodeReturn, 1, CompilerMode.DEFAULT);

        Assert.assertEquals(2, n);

        // Construction of expected instrs
        NodePush nodePush = new NodePush();
        nodePush.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(4));
        expected.addChild(nodePush);

        NodeGoto nodeGoto = new NodeGoto();
        expected.addChild(nodeGoto);

        Assert.assertTrue(instrs.isEquivalentTo(expected));
    }

    //----------------------------------------------------------------------

    @Test
    public void setLinesAndColumsNumberTest() throws CompilerException{

        // Construction of MJJ AST
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIf node = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIf();
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeInstrs nodeInstrs1 = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeInstrs();
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeInstrs nodeInstrs2 = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeInstrs();
        NodeAssignment nodeAssignment1 = new NodeAssignment();
        NodeAssignment nodeAssignment2 = new NodeAssignment();

        node.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeTrue());
        node.addChild(nodeInstrs1);
        node.addChild(nodeInstrs2);

        nodeInstrs1.addChild(nodeAssignment1);
        nodeInstrs1.addChild(new NodeInil());

        nodeInstrs2.addChild(nodeAssignment2);
        nodeInstrs2.addChild(new NodeInil());

        nodeAssignment1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("x@global"));
        nodeAssignment1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(1));

        nodeAssignment2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("y@global"));
        nodeAssignment2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(1));


        int n=compilerVisitor.visit(node,1,CompilerMode.DEFAULT);

        compilerVisitor.setLineAndColumnNumbers();

        Assert.assertEquals(7, n);

        Assert.assertEquals(instrs.getChildren().get(0).getLine(),1);
        Assert.assertEquals(instrs.getChildren().get(1).getLine(),2);
        Assert.assertEquals(instrs.getChildren().get(2).getLine(),3);
        Assert.assertEquals(instrs.getChildren().get(3).getLine(),4);
        Assert.assertEquals(instrs.getChildren().get(4).getLine(),5);
        Assert.assertEquals(instrs.getChildren().get(5).getLine(),6);
        Assert.assertEquals(instrs.getChildren().get(6).getLine(),7);

    }
}

