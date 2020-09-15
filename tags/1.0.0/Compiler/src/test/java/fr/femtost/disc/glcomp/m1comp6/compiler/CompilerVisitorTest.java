package fr.femtost.disc.glcomp.m1comp6.compiler;

import fr.femtost.disc.glcomp.m1comp6.ast.common.Kind;
import fr.femtost.disc.glcomp.m1comp6.ast.common.Type;
import org.junit.*;
import fr.femtost.disc.glcomp.m1comp6.memory.*;
import fr.femtost.disc.glcomp.m1comp6.ast.mjj.*;
import fr.femtost.disc.glcomp.m1comp6.ast.jjc.*;
import static org.mockito.Mockito.*;


public class CompilerVisitorTest {

    private CompilerVisitor compilerVisitor;
    private fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeInstrs instrs;
    private fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeInstrs expectedInstrs;
    private SymbolTable symbolTable = mock(SymbolTable.class);


    @Before
    public void initialize () {
        compilerVisitor = new CompilerVisitor();
        instrs = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeInstrs();
        expectedInstrs = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeInstrs();

        compilerVisitor.setSymbolTable(symbolTable);
        compilerVisitor.setInstrs(instrs);
    }


    @Test
    public void NodeTrueVisitorTest () throws CompilerException{
        //construction of mjj ast
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeTrue node = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeTrue();

        //compilation
        int res  = compilerVisitor.visit(node, 0,CompilerMode.DEFAULT, "global");

        //check return value
        Assert.assertEquals(1, res);

        //construction of expectedInstrs
        NodePush nodePush = new NodePush();
        nodePush.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeTrue());
        expectedInstrs.addChild(nodePush);

        //check that instrs from compilation and expectedInstrs are equivalent
        Assert.assertTrue(expectedInstrs.isEquivalentTo(instrs));
    }

    @Test
    public void NodeFalseVisitorTest () throws CompilerException{
        //construction of mjj ast
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeFalse node = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeFalse();

        //compilation
        int res  = compilerVisitor.visit(node, 0,CompilerMode.DEFAULT, "global");

        //check return value
        Assert.assertEquals(1, res);

        //construction of expectedInstrs
        NodePush nodePush = new NodePush();
        nodePush.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeFalse());
        expectedInstrs.addChild(nodePush);

        //check that instrs from compilation and expectedInstrs are equivalent
        Assert.assertTrue(expectedInstrs.isEquivalentTo(instrs));
    }

    @Test
    public void NodeOmegaVisitorTest () throws CompilerException{
        //construction of mjj ast
        NodeOmega node = new NodeOmega();

        //compilation
        int res  = compilerVisitor.visit(node, 0,CompilerMode.DEFAULT, "global");

        //check return value
        Assert.assertEquals(1, res);

        //construction of expectedInstrs
        NodePush nodePush = new NodePush();
        nodePush.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeJcnil());
        expectedInstrs.addChild(nodePush);

        //check that instrs from compilation and expectedInstrs are equivalent
        Assert.assertTrue(expectedInstrs.isEquivalentTo(instrs));
    }

    @Test
    public void NodeNumberVisitorTest () throws CompilerException{
        //construction of mjj ast
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber node = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(4);

        //compilation
        int res  = compilerVisitor.visit(node, 0,CompilerMode.DEFAULT, "global");

        //check return value
        Assert.assertEquals(1, res);

        //construction of expectedInstrs
        NodePush nodePush = new NodePush();
        nodePush.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(4));
        expectedInstrs.addChild(nodePush);

        //check that instrs from compilation and expectedInstrs are equivalent
        Assert.assertTrue(expectedInstrs.isEquivalentTo(instrs));
    }


    @Test
    public void NodeIdentVisitorTestSymbolInScope ()  throws CompilerException{

        when(this.symbolTable.get("x@main","global" )).thenReturn(new SymbolNode("x", "main", Kind.VARIABLE, Type.INTEGER));

        //----------------------------

        //construction of mjj ast
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent node = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("x");

        //compilation
        int res  = compilerVisitor.visit(node, 0,CompilerMode.DEFAULT, "main");

        //check return value
        Assert.assertEquals(1, res);

        //construction of expectedInstrs
        NodeLoad nodeLoad = new NodeLoad();
        nodeLoad.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("x@main"));
        expectedInstrs.addChild(nodeLoad);

        //check that instrs from compilation and expectedInstrs are equivalent
        Assert.assertTrue(expectedInstrs.isEquivalentTo(instrs));
    }

    @Test
    public void NodeIdentVisitorTestSymbolGlobal ()  throws CompilerException{

        when(symbolTable.get("x@main","global" )).thenReturn(null);
        when(symbolTable.get("x@global","global" )).thenReturn(new SymbolNode("x", "global", Kind.VARIABLE, Type.INTEGER));

        //---------------------

        //construction of mjj ast
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent node = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("x");

        //compilation
        int res  = compilerVisitor.visit(node, 0,CompilerMode.DEFAULT, "main");

        //check return value
        Assert.assertEquals(1, res);

        //construction of expectedInstrs
        NodeLoad nodeLoad = new NodeLoad();
        nodeLoad.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("x@global"));
        expectedInstrs.addChild(nodeLoad);

        //check that instrs from compilation and expectedInstrs are equivalent
        Assert.assertTrue(expectedInstrs.isEquivalentTo(instrs));
    }

    @Test (expected = CompilerException.class)
    public void NodeIdentVisitorTestFail () throws CompilerException{

        when(symbolTable.get("x@main","global" )).thenReturn(null);
        when(symbolTable.get("x@global","global" )).thenReturn(null);

        //---------------------------------

        //construction of mjj ast
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent node = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("x");

        //compilation
        compilerVisitor.visit(node, 0,CompilerMode.DEFAULT, "main");
    }


    @Test
    public void NodeInilVisitorTest () throws CompilerException{
        //construction of mjj ast
        NodeInil node = new NodeInil();

        //compilation
        int res = compilerVisitor.visit(node, 0,CompilerMode.DEFAULT, "main");

        //check return value
        Assert.assertEquals(0, res);

        //expectedInstrs is empty

        //check that instrs from compilation and expectedInstrs are equivalent
        Assert.assertTrue(expectedInstrs.isEquivalentTo(instrs));
    }

    @Test
    public void NodeVnilVisitorTest () throws CompilerException{
        //construction of mjj ast
        NodeVnil node = new NodeVnil();

        //compilation
        int res = compilerVisitor.visit(node, 0,CompilerMode.DEFAULT, "main");

        //check return value
        Assert.assertEquals(0, res);

        //expectedInstrs is empty

        //check that instrs from compilation and expectedInstrs are equivalent
        Assert.assertTrue(expectedInstrs.isEquivalentTo(instrs));
    }

    @Test
    public void NodeVarVisitorTest () throws CompilerException {
        //construction of mjj ast
        NodeVar nodeVar = new NodeVar();
        nodeVar.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeBoolean());
        nodeVar.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("y"));
        nodeVar.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeFalse());

        //compilation
        int res1 = compilerVisitor.visit(nodeVar, 0,CompilerMode.DEFAULT, "main");
        int res2 = compilerVisitor.visit(nodeVar, res1,CompilerMode.CANCEL, "main");

        //check return values
        Assert.assertEquals(2, res1);
        Assert.assertEquals(2, res2);

        //construction of expectedInstrs
        NodePush nodePush = new NodePush();
        nodePush.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeFalse());
        expectedInstrs.addChild(nodePush);
        NodeNew nodeNew = new NodeNew();
        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("y@main"));
        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeType("BOOLEAN"));
        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeKind("VARIABLE"));
        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));
        expectedInstrs.addChild(nodeNew);
        expectedInstrs.addChild(new NodeSwap());
        expectedInstrs.addChild(new NodePop());

        //check that instrs from compilation and expectedInstrs are equivalent
        Assert.assertTrue(expectedInstrs.isEquivalentTo(instrs));
    }

    @Test (expected = CompilerException.class)
    public void NodeVarVisitorTestFailure () throws CompilerException, ExistingSymbolException {

        when(symbolTable.put(any(SymbolNode.class))).thenThrow(ExistingSymbolException.class);

        //---------------------------------

        //creation of mjj ast
        NodeVar nodeVar = new NodeVar();

        nodeVar.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeBoolean());
        nodeVar.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("y"));
        nodeVar.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeFalse());


        //compilation
        compilerVisitor.visit(nodeVar, 0,CompilerMode.DEFAULT, "main");
    }

    @Test
    public void NodeVarsVisitorTest () throws CompilerException {
        //creation of mjj ast
        NodeVars nodeVars1 = new NodeVars();
        NodeVars nodeVars2 = new NodeVars();

        NodeVar nodeVar1 = new NodeVar();
        nodeVar1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeBoolean());
        nodeVar1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("y"));
        nodeVar1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeFalse());

        NodeVar nodeVar2 = new NodeVar();
        nodeVar2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeBoolean());
        nodeVar2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("x"));
        nodeVar2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeTrue());

        nodeVars1.addChild(nodeVar1);
        nodeVars1.addChild(nodeVars2);

        nodeVars2.addChild(nodeVar2);
        nodeVars2.addChild(new NodeVnil());


        //compilation
        int res1 = compilerVisitor.visit(nodeVars1, 0,CompilerMode.DEFAULT, "main");
        int res2 = compilerVisitor.visit(nodeVars1, res1,CompilerMode.CANCEL, "main");

        //check return values
        Assert.assertEquals(4, res1);
        Assert.assertEquals(4, res2);

        //construction of expectedInstrs
        NodePush nodePush = new NodePush();
        nodePush.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeFalse());
        expectedInstrs.addChild(nodePush);
        NodeNew nodeNew = new NodeNew();
        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("y@main"));
        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeType("BOOLEAN"));
        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeKind("VARIABLE"));
        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));
        expectedInstrs.addChild(nodeNew);

        NodePush nodePush2 = new NodePush();
        nodePush2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeTrue());
        expectedInstrs.addChild(nodePush2);
        NodeNew nodeNew2 = new NodeNew();
        nodeNew2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("x@main"));
        nodeNew2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeType("BOOLEAN"));
        nodeNew2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeKind("VARIABLE"));
        nodeNew2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));
        expectedInstrs.addChild(nodeNew2);

        expectedInstrs.addChild(new NodeSwap());
        expectedInstrs.addChild(new NodePop());
        expectedInstrs.addChild(new NodeSwap());
        expectedInstrs.addChild(new NodePop());

        //check that instrs from compilation and expectedInstrs are equivalent
        Assert.assertTrue(expectedInstrs.isEquivalentTo(instrs));
    }

    @Test
    public void NodeMainVisitorTest () throws CompilerException {
        //creation of mjj ast
        NodeVar nodeVar2 = new NodeVar();
        nodeVar2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeBoolean());
        nodeVar2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("x"));
        nodeVar2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeTrue());

        NodeVars nodeVars2 = new NodeVars();
        nodeVars2.addChild(nodeVar2);
        nodeVars2.addChild(new NodeVnil());

        NodeVar nodeVar1 = new NodeVar();
        nodeVar1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeBoolean());
        nodeVar1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("y"));
        nodeVar1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeFalse());

        NodeVars nodeVars1 = new NodeVars();
        nodeVars1.addChild(nodeVar1);
        nodeVars1.addChild(nodeVars2);

        NodeMain nodeMain= new NodeMain();
        nodeMain.addChild(nodeVars1);
        nodeMain.addChild(new NodeInil());


        //compilation
        int res = compilerVisitor.visit(nodeMain, 0,CompilerMode.DEFAULT, "global");

        //check return
        Assert.assertEquals(9, res);

        //construction of expectedInstrs
        NodePush nodePush = new NodePush();
        nodePush.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeFalse());
        expectedInstrs.addChild(nodePush);
        NodeNew nodeNew = new NodeNew();
        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("y@main"));
        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeType("BOOLEAN"));
        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeKind("VARIABLE"));
        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));
        expectedInstrs.addChild(nodeNew);

        NodePush nodePush2 = new NodePush();
        nodePush2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeTrue());
        expectedInstrs.addChild(nodePush2);
        NodeNew nodeNew2 = new NodeNew();
        nodeNew2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("x@main"));
        nodeNew2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeType("BOOLEAN"));
        nodeNew2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeKind("VARIABLE"));
        nodeNew2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));
        expectedInstrs.addChild(nodeNew2);

        NodePush nodePush3 = new NodePush();
        nodePush3.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));
        expectedInstrs.addChild(nodePush3);

        expectedInstrs.addChild(new NodeSwap());
        expectedInstrs.addChild(new NodePop());
        expectedInstrs.addChild(new NodeSwap());
        expectedInstrs.addChild(new NodePop());

        //check that instrs from compilation and expectedInstrs are equivalent
        Assert.assertTrue(expectedInstrs.isEquivalentTo(instrs));
    }

    @Test
    public void NodeMainVisitorTest2 () throws CompilerException {
        //creation of mjj ast
        NodeMain nodeMain= new NodeMain();
        nodeMain.addChild(new NodeVnil());
        nodeMain.addChild(new NodeInil());

        //compilation
        int res = compilerVisitor.visit(nodeMain, 0,CompilerMode.DEFAULT, "global");

        //check return value
        Assert.assertEquals(1, res);

        //construction of expectedInstrs
        NodePush nodePush = new NodePush();
        nodePush.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));
        expectedInstrs.addChild(nodePush);

        //check that instrs from compilation and expectedInstrs are equivalent
        Assert.assertTrue(expectedInstrs.isEquivalentTo(instrs));
    }

    @Test
    public void NodeDeclsVisitorTest () throws CompilerException {
        //creation of mjj ast
        NodeDecls nodeDecls1 = new NodeDecls();
        NodeDecls nodeDecls2 = new NodeDecls();

        NodeVar nodeVar1 = new NodeVar();
        nodeVar1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeBoolean());
        nodeVar1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("a"));
        nodeVar1.addChild(new NodeOmega());

        NodeVar nodeVar2 = new NodeVar();
        nodeVar2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeInteger());
        nodeVar2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("y"));
        nodeVar2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(4));

        nodeDecls1.addChild(nodeVar1);
        nodeDecls1.addChild(nodeDecls2);

        nodeDecls2.addChild(nodeVar2);
        nodeDecls2.addChild(new NodeVnil());


        //compilation
        int res1 = compilerVisitor.visit(nodeDecls1, 0,CompilerMode.DEFAULT, "global");
        int res2 = compilerVisitor.visit(nodeDecls1, res1,CompilerMode.CANCEL, "global");

        //check return values
        Assert.assertEquals(4, res1);
        Assert.assertEquals(4, res2);

        //construction of expectedInstrs
        NodePush nodePush = new NodePush();
        nodePush.addChild(new NodeJcnil());
        expectedInstrs.addChild(nodePush);
        NodeNew nodeNew = new NodeNew();
        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("a@global"));
        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeType("BOOLEAN"));
        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeKind("VARIABLE"));
        nodeNew.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));
        expectedInstrs.addChild(nodeNew);

        NodePush nodePush2 = new NodePush();
        nodePush2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(4));
        expectedInstrs.addChild(nodePush2);
        NodeNew nodeNew2 = new NodeNew();
        nodeNew2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("y@global"));
        nodeNew2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeType("INTEGER"));
        nodeNew2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeKind("VARIABLE"));
        nodeNew2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));
        expectedInstrs.addChild(nodeNew2);

        expectedInstrs.addChild(new NodeSwap());
        expectedInstrs.addChild(new NodePop());
        expectedInstrs.addChild(new NodeSwap());
        expectedInstrs.addChild(new NodePop());

        //check that instrs from compilation and expectedInstrs are equivalent
        Assert.assertTrue(expectedInstrs.isEquivalentTo(instrs));
    }

    @Test
    public void NodeClassVisitorTest1 () throws CompilerException {
        //creation of mjj ast
        NodeMain nodeMain= new NodeMain();
        nodeMain.addChild(new NodeVnil());
        nodeMain.addChild(new NodeInil());

        NodeClass nodeClass = new NodeClass();
        nodeClass.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("C"));
        nodeClass.addChild(new NodeVnil());
        nodeClass.addChild(nodeMain);

        //compilation
        int res = compilerVisitor.visit(nodeClass, 0,CompilerMode.DEFAULT, null);

        //check return value
        Assert.assertEquals(4, res);

        //construction of expectedInstrs
        expectedInstrs.addChild(new NodeInit());
        NodePush nodePush = new NodePush();
        nodePush.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));
        expectedInstrs.addChild(nodePush);
        expectedInstrs.addChild(new NodePop());
        expectedInstrs.addChild(new NodeJcstop());

        //check that instrs from compilation and expectedInstrs are equivalent
        Assert.assertTrue(expectedInstrs.isEquivalentTo(instrs));
    }

    @Test
    public void NodeClassVisitorTest2 () throws CompilerException {

        //creation of mjj ast
        NodeDecls nodeDecls1 = new NodeDecls();
        NodeDecls nodeDecls2 = new NodeDecls();

        NodeVar nodeVar1 = new NodeVar();
        nodeVar1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeBoolean());
        nodeVar1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("a"));
        nodeVar1.addChild(new NodeOmega());

        NodeVar nodeVar2 = new NodeVar();
        nodeVar2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeInteger());
        nodeVar2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("y"));
        nodeVar2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(4));

        nodeDecls1.addChild(nodeVar1);
        nodeDecls1.addChild(nodeDecls2);

        nodeDecls2.addChild(nodeVar2);
        nodeDecls2.addChild(new NodeVnil());

        NodeVar nodeVar4 = new NodeVar();
        nodeVar4.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeBoolean());
        nodeVar4.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("x"));
        nodeVar4.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeTrue());

        NodeVars nodeVars2 = new NodeVars();
        nodeVars2.addChild(nodeVar4);
        nodeVars2.addChild(new NodeVnil());

        NodeVar nodeVar3 = new NodeVar();
        nodeVar3.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeBoolean());
        nodeVar3.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("y"));
        nodeVar3.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeFalse());

        NodeVars nodeVars1 = new NodeVars();
        nodeVars1.addChild(nodeVar3);
        nodeVars1.addChild(nodeVars2);

        NodeMain nodeMain= new NodeMain();
        nodeMain.addChild(nodeVars1);
        nodeMain.addChild(new NodeInil());

        NodeClass nodeClass = new NodeClass();
        nodeClass.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("C"));
        nodeClass.addChild(nodeDecls1);
        nodeClass.addChild(nodeMain);


        //compilation
        int res = compilerVisitor.visit(nodeClass, 0,CompilerMode.DEFAULT, null);

        //check return value
        Assert.assertEquals(20, res);


        //construction of expectedInstrs
        expectedInstrs.addChild(new NodeInit());

        NodePush nodePush1 = new NodePush();
        nodePush1.addChild(new NodeJcnil());
        expectedInstrs.addChild(nodePush1);
        NodeNew nodeNew1 = new NodeNew();
        nodeNew1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("a@global"));
        nodeNew1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeType("BOOLEAN"));
        nodeNew1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeKind("VARIABLE"));
        nodeNew1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));
        expectedInstrs.addChild(nodeNew1);

        NodePush nodePush2 = new NodePush();
        nodePush2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(4));
        expectedInstrs.addChild(nodePush2);
        NodeNew nodeNew2 = new NodeNew();
        nodeNew2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("y@global"));
        nodeNew2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeType("INTEGER"));
        nodeNew2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeKind("VARIABLE"));
        nodeNew2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));
        expectedInstrs.addChild(nodeNew2);

        NodePush nodePush3 = new NodePush();
        nodePush3.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeFalse());
        expectedInstrs.addChild(nodePush3);
        NodeNew nodeNew3 = new NodeNew();
        nodeNew3.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("y@main"));
        nodeNew3.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeType("BOOLEAN"));
        nodeNew3.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeKind("VARIABLE"));
        nodeNew3.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));
        expectedInstrs.addChild(nodeNew3);

        NodePush nodePush4 = new NodePush();
        nodePush4.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeTrue());
        expectedInstrs.addChild(nodePush4);
        NodeNew nodeNew4 = new NodeNew();
        nodeNew4.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("x@main"));
        nodeNew4.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeType("BOOLEAN"));
        nodeNew4.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeKind("VARIABLE"));
        nodeNew4.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));
        expectedInstrs.addChild(nodeNew4);

        NodePush nodePush5 = new NodePush();
        nodePush5.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));
        expectedInstrs.addChild(nodePush5);

        expectedInstrs.addChild(new NodeSwap());
        expectedInstrs.addChild(new NodePop());
        expectedInstrs.addChild(new NodeSwap());
        expectedInstrs.addChild(new NodePop());
        expectedInstrs.addChild(new NodeSwap());
        expectedInstrs.addChild(new NodePop());
        expectedInstrs.addChild(new NodeSwap());
        expectedInstrs.addChild(new NodePop());

        expectedInstrs.addChild(new NodePop());
        expectedInstrs.addChild(new NodeJcstop());



        //check that instrs from compilation and expectedInstrs are equivalent
        Assert.assertTrue(expectedInstrs.isEquivalentTo(instrs));
    }

}
