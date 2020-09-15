package fr.femtost.disc.glcomp.m1comp6.interpreters.mjj;

import fr.femtost.disc.glcomp.m1comp6.ast.common.Kind;
import fr.femtost.disc.glcomp.m1comp6.ast.common.Node;
import fr.femtost.disc.glcomp.m1comp6.ast.common.Type;
import fr.femtost.disc.glcomp.m1comp6.ast.mjj.*;
import fr.femtost.disc.glcomp.m1comp6.memory.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InterpreterVisitorTest {

    private InterpreterVisitor interpreterVisitor;
    private Memory memory;
    private SymbolTable symbolTable;

    @Before
    public void initialize() {
        interpreterVisitor = new InterpreterVisitor();
        symbolTable = new SymbolTable();
        memory = new Memory(symbolTable);

        interpreterVisitor.setMemory(memory);
    }

    @Test
    public void nodeFalseTest() throws InterpreterException {

        NodeFalse nodeFalse = new NodeFalse();

        Object res = nodeFalse.accept(interpreterVisitor, InterpreterMode.EVAL);

        Assert.assertEquals(false, res);
    }

    @Test
    public void nodeFalseTest2() throws InterpreterException {

        NodeFalse nodeFalse = new NodeFalse();

        Object res = nodeFalse.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        Assert.assertNull(res);

    }

    @Test
    public void nodeTrueTest() throws InterpreterException {

        NodeTrue nodeTrue = new NodeTrue();

        Object res = nodeTrue.accept(interpreterVisitor, InterpreterMode.EVAL);

        Assert.assertEquals(true, res);
    }

    @Test
    public void nodeTrueTest2() throws InterpreterException {

        NodeTrue nodeTrue = new NodeTrue();

        Object res = nodeTrue.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        Assert.assertNull(res);
    }

    @Test
    public void nodeNumberTest() throws InterpreterException {

        NodeNumber nodeNumber = new NodeNumber(4);

        Object res = nodeNumber.accept(interpreterVisitor, InterpreterMode.EVAL);

        Assert.assertEquals(4, res);
    }

    @Test
    public void nodeNumberTest2() throws InterpreterException {

        NodeNumber nodeNumber = new NodeNumber(4);

        Object res = nodeNumber.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        Assert.assertNull(res);

    }

    @Test
    public void nodeIdentTest() throws InterpreterException {
        //Creation of mjj AST
        NodeIdent nodeIdent = new NodeIdent("test", "global");

        //interpretation
        Object res = nodeIdent.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        //Check Return
        Assert.assertNull(res);

    }


    @Test
    public void nodeIdentTest2() throws InterpreterException, InvalidTypeException, ExistingSymbolException, UnknownSymbolException, WrongKindException {
        //Creation of mjj AST

        symbolTable.put(new SymbolNode("test@global", Kind.VARIABLE, Type.INTEGER));
        memory.declVar("test@global", 4);

        NodeIdent nodeIdent = new NodeIdent("test", "global");

        //interpretation
        Object res = nodeIdent.accept(interpreterVisitor, InterpreterMode.EVAL);

        //Check Return
        Assert.assertEquals(4, res);

    }

    @Test(expected = InterpreterException.class)
    public void nodeIdentTest3() throws InterpreterException {

        NodeIdent nodeIdent = new NodeIdent("test", "global");

        //interpretation
        Object res = nodeIdent.accept(interpreterVisitor, InterpreterMode.EVAL);

        //Check Return
        Assert.assertEquals(4, res);
    }


    @Test
    public void nodeIdentTest4() throws InterpreterException {
        NodeIdent nodeIdent = new NodeIdent("test", "global");

        //interpretation
        Object res = nodeIdent.accept(interpreterVisitor, InterpreterMode.CANCEL);

        //Check Return
        Assert.assertNull(res);
    }

    @Test
    public void nodeInstrsTest() throws InterpreterException {
        NodeInstrs nodeInstrs = new NodeInstrs();
        nodeInstrs.addChild(new NodeInil());
        nodeInstrs.addChild(new NodeInil());

        Object res = nodeInstrs.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        Assert.assertNull(res);
    }

    @Test
    public void nodeInstrsTest2() throws InterpreterException {
        NodeInstrs nodeInstrs = new NodeInstrs();
        nodeInstrs.addChild(new NodeInil());
        nodeInstrs.addChild(new NodeInil());

        Object res = nodeInstrs.accept(interpreterVisitor, InterpreterMode.EVAL);

        Assert.assertNull(res);
    }

    @Test
    public void nodeInstrsTest3() throws InterpreterException {
        NodeInstrs nodeInstrs = new NodeInstrs();
        nodeInstrs.addChild(new NodeInil());
        nodeInstrs.addChild(new NodeInil());

        Object res = nodeInstrs.accept(interpreterVisitor, InterpreterMode.CANCEL);

        Assert.assertNull(res);
    }

    @Test
    public void nodeVarsTest() throws InterpreterException {
        NodeVars nodeVars = new NodeVars();
        nodeVars.addChild(new NodeVnil());
        nodeVars.addChild(new NodeVnil());

        Object res = nodeVars.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        Assert.assertNull(res);
    }

    @Test
    public void nodeVarsTest2() throws InterpreterException {
        NodeVars nodeVars = new NodeVars();
        nodeVars.addChild(new NodeVnil());
        nodeVars.addChild(new NodeVnil());

        Object res = nodeVars.accept(interpreterVisitor, InterpreterMode.EVAL);

        Assert.assertNull(res);
    }

    @Test
    public void nodeVarsTest3() throws InterpreterException {
        NodeVars nodeVars = new NodeVars();
        nodeVars.addChild(new NodeVnil());
        nodeVars.addChild(new NodeVnil());

        Object res = nodeVars.accept(interpreterVisitor, InterpreterMode.CANCEL);

        Assert.assertNull(res);
    }

    @Test
    public void nodeDeclsTest() throws InterpreterException {
        NodeDecls nodeDecls = new NodeDecls();
        nodeDecls.addChild(new NodeVnil());
        nodeDecls.addChild(new NodeVnil());

        Object res = nodeDecls.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        Assert.assertNull(res);
    }

    @Test
    public void nodeDeclsTest2() throws InterpreterException {
        NodeDecls nodeDecls = new NodeDecls();
        nodeDecls.addChild(new NodeVnil());
        nodeDecls.addChild(new NodeVnil());

        Object res = nodeDecls.accept(interpreterVisitor, InterpreterMode.EVAL);

        Assert.assertNull(res);
    }

    @Test
    public void nodeDeclsTest3() throws InterpreterException {
        NodeDecls nodeDecls = new NodeDecls();
        nodeDecls.addChild(new NodeVnil());
        nodeDecls.addChild(new NodeVnil());

        Object res = nodeDecls.accept(interpreterVisitor, InterpreterMode.CANCEL);

        Assert.assertNull(res);
    }

    @Test
    public void nodeVarTest() throws InterpreterException, StackException, ExistingSymbolException {
        symbolTable.put(new SymbolNode("test@global", Kind.VARIABLE, Type.INTEGER));
        NodeVar nodeVar = new NodeVar();
        nodeVar.addChild(new NodeInteger());
        nodeVar.addChild(new NodeIdent("test", "global"));
        nodeVar.addChild(new NodeNumber(4));

        Object res = nodeVar.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        Assert.assertNull(res);
        Assert.assertEquals(4, (int) memory.peekValue());
    }

    @Test
    public void nodeVarTest2() throws InterpreterException, StackException, ExistingSymbolException, InvalidTypeException {
        symbolTable.put(new SymbolNode("test@global", Kind.VARIABLE, Type.INTEGER));
        NodeVar nodeVar = new NodeVar();
        nodeVar.addChild(new NodeInteger());
        nodeVar.addChild(new NodeIdent("test", "global"));
        nodeVar.addChild(new NodeNumber(4));

        memory.push(false);

        nodeVar.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        Object res = nodeVar.accept(interpreterVisitor, InterpreterMode.CANCEL);

        Assert.assertNull(res);
        Assert.assertEquals(false, memory.peekValue());
    }

    @Test
    public void nodeVarTest3() throws InterpreterException, ExistingSymbolException {
        symbolTable.put(new SymbolNode("test@global", Kind.VARIABLE, Type.INTEGER));
        NodeVar nodeVar = new NodeVar();
        nodeVar.addChild(new NodeInteger());
        nodeVar.addChild(new NodeIdent("test", "global"));
        nodeVar.addChild(new NodeNumber(4));

        Object res = nodeVar.accept(interpreterVisitor, InterpreterMode.EVAL);

        Assert.assertNull(res);
    }

    @Test(expected = InterpreterException.class)
    public void nodeVarTest4() throws InterpreterException {
        NodeVar nodeVar = new NodeVar();
        nodeVar.addChild(new NodeInteger());
        nodeVar.addChild(new NodeIdent("test", "global"));
        nodeVar.addChild(new NodeNumber(4));

        Object res = nodeVar.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        Assert.assertNull(res);
    }

    @Test(expected = InterpreterException.class)
    public void nodeVarTest5() throws InterpreterException, ExistingSymbolException {
        symbolTable.put(new SymbolNode("test@global", Kind.VARIABLE, Type.INTEGER));

        NodeVar nodeVar = new NodeVar();
        nodeVar.addChild(new NodeInteger());
        nodeVar.addChild(new NodeIdent("test", "global"));
        nodeVar.addChild(new NodeNumber(4));

        Object res = nodeVar.accept(interpreterVisitor, InterpreterMode.CANCEL);

        Assert.assertNull(res);
    }


    @Test
    public void nodeMainTest() throws InterpreterException {
        NodeMain nodeMain = new NodeMain();
        nodeMain.addChild(new NodeVnil());
        nodeMain.addChild(new NodeInil());

        Object res = nodeMain.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        Assert.assertNull(res);
    }

    @Test
    public void nodeMainTest2() throws InterpreterException {
        NodeMain nodeMain = new NodeMain();
        nodeMain.addChild(new NodeVnil());
        nodeMain.addChild(new NodeInil());

        Object res = nodeMain.accept(interpreterVisitor, InterpreterMode.EVAL);

        Assert.assertNull(res);
    }

    @Test
    public void nodeClassTest() throws InterpreterException, ExistingSymbolException, InvalidTypeException, StackException {
        symbolTable.put(new SymbolNode("test@global", Kind.VARIABLE, null));

        memory.push(false);

        NodeClass nodeClass = new NodeClass();
        nodeClass.addChild(new NodeIdent("test", "global"));
        nodeClass.addChild(new NodeVnil());

        NodeMain nodeMain = new NodeMain();
        nodeMain.addChild(new NodeVnil());
        nodeMain.addChild(new NodeInil());

        nodeClass.addChild(nodeMain);

        Object res = nodeClass.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        Assert.assertNull(res);
        Assert.assertEquals(false, memory.peekValue());

    }

    @Test
    public void nodeClassTest2() throws InterpreterException, ExistingSymbolException {
        symbolTable.put(new SymbolNode("test@global", Kind.VARIABLE, null));

        NodeClass nodeClass = new NodeClass();
        nodeClass.addChild(new NodeIdent("test", "global"));
        nodeClass.addChild(new NodeVnil());

        NodeMain nodeMain = new NodeMain();
        nodeMain.addChild(new NodeVnil());
        nodeMain.addChild(new NodeInil());

        nodeClass.addChild(nodeMain);

        Object res = nodeClass.accept(interpreterVisitor, InterpreterMode.EVAL);

        Assert.assertNull(res);

    }

    @Test
    public void nodeClassTest3() throws InterpreterException, ExistingSymbolException {
        symbolTable.put(new SymbolNode("test@global", Kind.VARIABLE, null));

        NodeClass nodeClass = new NodeClass();
        nodeClass.addChild(new NodeIdent("test", "global"));
        nodeClass.addChild(new NodeVnil());

        NodeMain nodeMain = new NodeMain();
        nodeMain.addChild(new NodeVnil());
        nodeMain.addChild(new NodeInil());

        nodeClass.addChild(nodeMain);

        Object res = nodeClass.accept(interpreterVisitor, InterpreterMode.CANCEL);

        Assert.assertNull(res);

    }

    @Test
    public void nodeClassTest4() throws InterpreterException, ExistingSymbolException {
        symbolTable.put(new SymbolNode("test@global", Kind.VARIABLE, null));

        NodeClass nodeClass = new NodeClass();
        nodeClass.addChild(new NodeIdent("test", "global"));
        nodeClass.addChild(new NodeVnil());

        NodeMain nodeMain = new NodeMain();
        nodeMain.addChild(new NodeVnil());
        nodeMain.addChild(new NodeInil());

        nodeClass.addChild(nodeMain);

        Object res = nodeClass.accept(interpreterVisitor, InterpreterMode.CANCEL);

        Assert.assertNull(res);
    }

    @Test(expected = InterpreterException.class)
    public void nodeClassTest5() throws InterpreterException {

        NodeClass nodeClass = new NodeClass();
        nodeClass.addChild(new NodeIdent("test", "global"));
        nodeClass.addChild(new NodeVnil());

        NodeMain nodeMain = new NodeMain();
        nodeMain.addChild(new NodeVnil());
        nodeMain.addChild(new NodeInil());

        nodeClass.addChild(nodeMain);

        Object res = nodeClass.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        Assert.assertNull(res);
    }

    @Test
    public void nodeAddTest() throws InterpreterException {
        NodeAdd nodeAdd = new NodeAdd();
        nodeAdd.addChild(new NodeNumber(4));
        nodeAdd.addChild(new NodeNumber(6));

        Object res = nodeAdd.accept(interpreterVisitor, InterpreterMode.EVAL);

        Assert.assertEquals(10, (int) res);
    }

    @Test
    public void nodeAddTest2() throws InterpreterException {
        NodeAdd nodeAdd = new NodeAdd();
        nodeAdd.addChild(new NodeNumber(4));
        nodeAdd.addChild(new NodeNumber(6));

        Object res = nodeAdd.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        Assert.assertNull(res);
    }

    @Test
    public void nodeAndTest() throws InterpreterException {
        NodeAnd nodeAnd = new NodeAnd();
        nodeAnd.addChild(new NodeTrue());
        nodeAnd.addChild(new NodeFalse());

        Object res = nodeAnd.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        Assert.assertNull(res);
    }

    @Test
    public void nodeAndTest2() throws InterpreterException {
        NodeAnd nodeAnd = new NodeAnd();
        nodeAnd.addChild(new NodeTrue());
        nodeAnd.addChild(new NodeFalse());

        Object res = nodeAnd.accept(interpreterVisitor, InterpreterMode.EVAL);

        Assert.assertEquals(false, res);
    }

    //test short-circuit
    @Test
    public void nodeAndTest3 () throws InterpreterException {
        NodeDiv nodeDiv = new NodeDiv();
        nodeDiv.addChild(new NodeNumber(24));
        nodeDiv.addChild(new NodeNumber(0));

        NodeAnd nodeAnd = new NodeAnd();
        nodeAnd.addChild(new NodeFalse());
        nodeAnd.addChild(nodeDiv);

        Object res = nodeAnd.accept(interpreterVisitor, InterpreterMode.EVAL);

        Assert.assertEquals(false, res);
    }

    @Test
    public void nodeOrTest() throws InterpreterException {
        NodeOr nodeOr = new NodeOr();
        nodeOr.addChild(new NodeTrue());
        nodeOr.addChild(new NodeFalse());

        Object res = nodeOr.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        Assert.assertNull(res);
    }

    @Test
    public void nodeOrTest2() throws InterpreterException {
        NodeOr nodeOr = new NodeOr();
        nodeOr.addChild(new NodeFalse());
        nodeOr.addChild(new NodeTrue());

        Object res = nodeOr.accept(interpreterVisitor, InterpreterMode.EVAL);

        Assert.assertEquals(true, res);
    }

    //test short-circuit
    @Test
    public void nodeOrTest3 () throws InterpreterException {
        NodeDiv nodeDiv = new NodeDiv();
        nodeDiv.addChild(new NodeNumber(24));
        nodeDiv.addChild(new NodeNumber(0));

        NodeOr nodeOr = new NodeOr();
        nodeOr.addChild(new NodeTrue());
        nodeOr.addChild(nodeDiv);

        Object res = nodeOr.accept(interpreterVisitor, InterpreterMode.EVAL);

        Assert.assertEquals(true, res);
    }

    @Test
    public void nodeNegTest() throws InterpreterException {
        NodeNeg nodeNeg = new NodeNeg();
        nodeNeg.addChild(new NodeNumber(4));

        Object res = nodeNeg.accept(interpreterVisitor, InterpreterMode.EVAL);

        Assert.assertEquals(-4, (int) res);
    }

    @Test
    public void nodeNegTest2() throws InterpreterException {
        NodeNeg nodeNeg = new NodeNeg();
        nodeNeg.addChild(new NodeNumber(4));

        Object res = nodeNeg.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        Assert.assertNull(res);
    }

    @Test
    public void nodeMulTest() throws InterpreterException {
        NodeMul nodeMul = new NodeMul();
        nodeMul.addChild(new NodeNumber(4));
        nodeMul.addChild(new NodeNumber(6));

        Object res = nodeMul.accept(interpreterVisitor, InterpreterMode.EVAL);

        Assert.assertEquals(24, (int) res);
    }


    @Test
    public void nodeMulTest2() throws InterpreterException {
        NodeMul nodeMul = new NodeMul();
        nodeMul.addChild(new NodeNumber(4));
        nodeMul.addChild(new NodeNumber(6));

        Object res = nodeMul.accept(interpreterVisitor, InterpreterMode.DEFAULT);
        Assert.assertNull(res);
    }

    @Test
    public void nodeNotTest() throws InterpreterException {
        NodeNot nodeNot = new NodeNot();
        nodeNot.addChild(new NodeFalse());

        Object res = nodeNot.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        Assert.assertNull(res);
    }

    @Test
    public void nodeNotTest2() throws InterpreterException {
        NodeNot nodeNot = new NodeNot();
        nodeNot.addChild(new NodeFalse());

        Object res = nodeNot.accept(interpreterVisitor, InterpreterMode.EVAL);

        Assert.assertEquals(true, res);
    }

    @Test
    public void nodeSubTest() throws InterpreterException {
        NodeSub nodeSub = new NodeSub();
        nodeSub.addChild(new NodeNumber(4));
        nodeSub.addChild(new NodeNumber(6));

        Object res = nodeSub.accept(interpreterVisitor, InterpreterMode.EVAL);

        Assert.assertEquals(-2, (int) res);
    }


    @Test
    public void nodeSubTest2() throws InterpreterException {
        NodeSub nodeSub = new NodeSub();
        nodeSub.addChild(new NodeNumber(4));
        nodeSub.addChild(new NodeNumber(6));

        Object res = nodeSub.accept(interpreterVisitor, InterpreterMode.DEFAULT);
        Assert.assertNull(res);
    }

    @Test
    public void nodeSubTest3() throws InterpreterException {
        NodeSub nodeSub = new NodeSub();
        nodeSub.addChild(new NodeNumber(4));
        nodeSub.addChild(new NodeNumber(-6));

        Object res = nodeSub.accept(interpreterVisitor, InterpreterMode.EVAL);

        Assert.assertEquals(10, (int) res);
    }

    @Test
    public void nodeSupTest() throws InterpreterException {
        NodeSup nodeSup = new NodeSup();

        nodeSup.addChild(new NodeNumber(4));
        nodeSup.addChild(new NodeNumber(-6));
        Object res = nodeSup.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        Assert.assertNull(res);
    }

    @Test
    public void nodeSupTest2() throws InterpreterException {
        NodeSup nodeSup = new NodeSup();
        nodeSup.addChild(new NodeNumber(4));
        nodeSup.addChild(new NodeNumber(-6));

        Object res = nodeSup.accept(interpreterVisitor, InterpreterMode.EVAL);

        Assert.assertEquals(true, res);
    }

    @Test
    public void nodeSupTest3() throws InterpreterException {
        NodeSup nodeSup = new NodeSup();
        nodeSup.addChild(new NodeNumber(-6));
        nodeSup.addChild(new NodeNumber(4));

        Object res = nodeSup.accept(interpreterVisitor, InterpreterMode.EVAL);

        Assert.assertEquals(false, res);
    }

    @Test
    public void nodeCmpTest() throws InterpreterException {
        NodeCmp nodeCmp = new NodeCmp();

        nodeCmp.addChild(new NodeNumber(4));
        nodeCmp.addChild(new NodeNumber(-6));
        Object res = nodeCmp.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        Assert.assertNull(res);
    }

    @Test
    public void nodeCmpTest2() throws InterpreterException {
        NodeCmp nodeCmp = new NodeCmp();
        nodeCmp.addChild(new NodeNumber(4));
        nodeCmp.addChild(new NodeNumber(-6));

        Object res = nodeCmp.accept(interpreterVisitor, InterpreterMode.EVAL);

        Assert.assertEquals(false, res);
    }

    @Test
    public void nodeCmpTest3() throws InterpreterException {
        NodeCmp nodeCmp = new NodeCmp();
        nodeCmp.addChild(new NodeNumber(4));
        nodeCmp.addChild(new NodeNumber(4));

        Object res = nodeCmp.accept(interpreterVisitor, InterpreterMode.EVAL);

        Assert.assertEquals(true, res);
    }

    @Test
    public void nodeDivTest() throws InterpreterException {
        NodeDiv nodeDiv = new NodeDiv();
        nodeDiv.addChild(new NodeNumber(4));
        nodeDiv.addChild(new NodeNumber(6));

        Object res = nodeDiv.accept(interpreterVisitor, InterpreterMode.EVAL);

        Assert.assertEquals(0, (int) res);
    }


    @Test
    public void nodeDivTest2() throws InterpreterException {
        NodeDiv nodeDiv = new NodeDiv();
        nodeDiv.addChild(new NodeNumber(24));
        nodeDiv.addChild(new NodeNumber(6));

        Object res = nodeDiv.accept(interpreterVisitor, InterpreterMode.DEFAULT);
        Assert.assertNull(res);
    }

    @Test
    public void nodeDivTest3() throws InterpreterException {
        NodeDiv nodeDiv = new NodeDiv();
        nodeDiv.addChild(new NodeNumber(24));
        nodeDiv.addChild(new NodeNumber(6));

        Object res = nodeDiv.accept(interpreterVisitor, InterpreterMode.EVAL);

        Assert.assertEquals(4, (int) res);
    }

    @Test(expected = InterpreterException.class)
    public void nodeDivTest4() throws InterpreterException {
        NodeDiv nodeDiv = new NodeDiv();
        nodeDiv.addChild(new NodeNumber(24));
        nodeDiv.addChild(new NodeNumber(0));

        nodeDiv.accept(interpreterVisitor, InterpreterMode.EVAL);

    }

    @Test
    public void nodeCstTest() throws InterpreterException, StackException, ExistingSymbolException {
        symbolTable.put(new SymbolNode("test@global", Kind.CONSTANT, Type.INTEGER));
        NodeCst nodeCst = new NodeCst();
        nodeCst.addChild(new NodeInteger());
        nodeCst.addChild(new NodeIdent("test", "global"));
        nodeCst.addChild(new NodeNumber(4));

        Object res = nodeCst.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        Assert.assertNull(res);
        Assert.assertEquals(4, (int) memory.peekValue());
    }

    @Test
    public void nodeCstTest2() throws InterpreterException, StackException, ExistingSymbolException, InvalidTypeException {
        symbolTable.put(new SymbolNode("test@global", Kind.CONSTANT, Type.INTEGER));
        NodeCst nodeCst = new NodeCst();
        nodeCst.addChild(new NodeInteger());
        nodeCst.addChild(new NodeIdent("test", "global"));
        nodeCst.addChild(new NodeNumber(4));

        memory.push(false);

        nodeCst.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        Object res = nodeCst.accept(interpreterVisitor, InterpreterMode.CANCEL);

        Assert.assertNull(res);
        Assert.assertEquals(false, memory.peekValue());
    }

    @Test
    public void nodeReturnTest() throws InterpreterException {
        NodeReturn nodeReturn = new NodeReturn();

        NodeExpList nodeExpList = new NodeExpList();
        nodeExpList.addChild(new NodeExnil());

        nodeReturn.addChild(nodeExpList);

        Object res = nodeReturn.accept(interpreterVisitor, InterpreterMode.CANCEL);

        Assert.assertNull(res);
    }

    @Test(expected = InterpreterException.class)
    public void nodeReturnTest2() throws InterpreterException {
        NodeReturn nodeReturn = new NodeReturn();

        nodeReturn.addChild(new NodeIdent("notdefined", "global"));
        Object res = nodeReturn.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        Assert.assertNull(res);
    }

    @Test
    public void nodeCstTest3() throws InterpreterException, ExistingSymbolException {
        symbolTable.put(new SymbolNode("test@global", Kind.CONSTANT, Type.INTEGER));
        NodeCst nodeCst = new NodeCst();
        nodeCst.addChild(new NodeInteger());
        nodeCst.addChild(new NodeIdent("test", "global"));
        nodeCst.addChild(new NodeNumber(4));

        Object res = nodeCst.accept(interpreterVisitor, InterpreterMode.EVAL);

        Assert.assertNull(res);
    }

    @Test(expected = InterpreterException.class)
    public void nodeCstTest4() throws InterpreterException {
        NodeCst nodeCst = new NodeCst();
        nodeCst.addChild(new NodeInteger());
        nodeCst.addChild(new NodeIdent("test", "global"));
        nodeCst.addChild(new NodeNumber(4));

        Object res = nodeCst.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        Assert.assertNull(res);
    }

    @Test(expected = InterpreterException.class)
    public void nodeCstTest5() throws InterpreterException, ExistingSymbolException {
        symbolTable.put(new SymbolNode("test@global", Kind.VARIABLE, Type.INTEGER));

        NodeCst nodeCst = new NodeCst();
        nodeCst.addChild(new NodeInteger());
        nodeCst.addChild(new NodeIdent("test", "global"));
        nodeCst.addChild(new NodeNumber(4));

        Object res = nodeCst.accept(interpreterVisitor, InterpreterMode.CANCEL);

        Assert.assertNull(res);
    }

    @Test
    public void nodeSumTest() throws InterpreterException, ExistingSymbolException, UnknownSymbolException, NotFoundValueException {
        symbolTable.put(new SymbolNode("test@global", Kind.VARIABLE, Type.INTEGER));
        NodeVar nodeVar = new NodeVar();
        nodeVar.addChild(new NodeInteger());
        nodeVar.addChild(new NodeIdent("test", "global"));
        nodeVar.addChild(new NodeNumber(4));

        nodeVar.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        NodeSum nodeSum = new NodeSum();
        nodeSum.addChild(new NodeIdent("test", "global"));
        nodeSum.addChild(new NodeNumber(4));

        Object res = nodeSum.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        int valSum = memory.getValue("test@global");

        //Check new value of ident
        Assert.assertEquals(8, valSum);

        Assert.assertNull(res);
    }

    /*
        Sum value that does not exist
     */
    @Test(expected = InterpreterException.class)
    public void nodeSumTest2() throws InterpreterException, ExistingSymbolException {
        symbolTable.put(new SymbolNode("test@global", Kind.VARIABLE, Type.INTEGER));

        NodeSum nodeSum = new NodeSum();
        nodeSum.addChild(new NodeIdent("test", "global"));
        nodeSum.addChild(new NodeNumber(4));

        nodeSum.accept(interpreterVisitor, InterpreterMode.DEFAULT);
    }

    @Test
    public void nodeSumTest3() throws InterpreterException, ExistingSymbolException {
        symbolTable.put(new SymbolNode("test@global", Kind.VARIABLE, Type.INTEGER));

        NodeSum nodeSum = new NodeSum();
        nodeSum.addChild(new NodeIdent("test", "global"));
        nodeSum.addChild(new NodeNumber(4));

        nodeSum.accept(interpreterVisitor, InterpreterMode.EVAL);
    }

    //sum on array
    @Test
    public void nodeSumTest4() throws InterpreterException, ExistingSymbolException, UnknownSymbolException, NotFoundValueException, HeapException, ArrayAccessException {

        symbolTable.put(new SymbolNode("tab@global", Kind.ARRAY, Type.INTEGER));

        // int tab[4]
        NodeArray nodeArray = new NodeArray();
        nodeArray.addChild(new NodeInteger());
        nodeArray.addChild(new NodeIdent("tab", "global"));
        nodeArray.addChild(new NodeNumber(4));

        nodeArray.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        NodeArrayGet nodeArrayGet = new NodeArrayGet();
        nodeArrayGet.addChild(new NodeIdent("tab", "global"));
        nodeArrayGet.addChild(new NodeNumber(0));

        NodeAssignment nodeAssignment = new NodeAssignment();
        nodeAssignment.addChild(nodeArrayGet);
        nodeAssignment.addChild(new NodeNumber(4));

        nodeAssignment.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        int valInd = memory.getValueArray("tab@global", 0);

        Assert.assertEquals(4, valInd);

        NodeSum nodeSum = new NodeSum();
        nodeSum.addChild(nodeArrayGet);
        nodeSum.addChild(new NodeNumber(4));

        Object res = nodeSum.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        int valSum = memory.getValueArray("tab@global", 0);

        //Check new value of ident
        Assert.assertEquals(8, valSum);

        Assert.assertNull(res);
    }

    @Test
    public void nodeAssignmentTest() throws InterpreterException, ExistingSymbolException, UnknownSymbolException, NotFoundValueException {
        symbolTable.put(new SymbolNode("test@global", Kind.VARIABLE, Type.INTEGER));

        NodeVar nodeVar = new NodeVar();
        nodeVar.addChild(new NodeInteger());
        nodeVar.addChild(new NodeIdent("test", "global"));
        nodeVar.addChild(new NodeVnil());
        nodeVar.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        NodeAssignment nodeAssignment = new NodeAssignment();
        nodeAssignment.addChild(new NodeIdent("test", "global"));
        nodeAssignment.addChild(new NodeNumber(4));

        Object res = nodeAssignment.accept(interpreterVisitor, InterpreterMode.DEFAULT);
        int value = memory.getValue("test@global");

        Assert.assertEquals(4, value);
        Assert.assertNull(res);
    }

    @Test(expected = InterpreterException.class)
    public void nodeAssignmentTest2() throws InterpreterException, ExistingSymbolException {
        symbolTable.put(new SymbolNode("test@global", Kind.VARIABLE, Type.INTEGER));

        NodeAssignment nodeAssignment = new NodeAssignment();
        nodeAssignment.addChild(new NodeIdent("test", "global"));
        nodeAssignment.addChild(new NodeNumber(4));

        nodeAssignment.accept(interpreterVisitor, InterpreterMode.DEFAULT);
    }

    @Test
    public void nodeAssignmentTest3() throws InterpreterException, ExistingSymbolException {
        symbolTable.put(new SymbolNode("test@global", Kind.VARIABLE, Type.INTEGER));

        NodeAssignment nodeAssignment = new NodeAssignment();
        nodeAssignment.addChild(new NodeIdent("test", "global"));
        nodeAssignment.addChild(new NodeNumber(4));

        nodeAssignment.accept(interpreterVisitor, InterpreterMode.EVAL);
    }

    @Test
    public void nodeAssignmentTest4() throws InterpreterException, ExistingSymbolException, UnknownSymbolException, NotFoundValueException, HeapException, ArrayAccessException {
        //integer tab test
        //tab[0] = 3;

        symbolTable.put(new SymbolNode("test@global", Kind.ARRAY, Type.INTEGER));

        // int test[4]
        NodeArray nodeArray = new NodeArray();
        nodeArray.addChild(new NodeInteger());
        nodeArray.addChild(new NodeIdent("test", "global"));
        nodeArray.addChild(new NodeNumber(4));

        nodeArray.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        NodeArrayGet nodeArrayGet = new NodeArrayGet();
        nodeArrayGet.addChild(new NodeIdent("test", "global"));
        nodeArrayGet.addChild(new NodeNumber(0));

        NodeAssignment nodeAssignment = new NodeAssignment();
        nodeAssignment.addChild(nodeArrayGet);
        nodeAssignment.addChild(new NodeNumber(3));

        Object res = nodeAssignment.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        Assert.assertNull(res);

        int valInd = memory.getValueArray("test@global", 0);

        Assert.assertEquals(3, valInd);
    }

    @Test
    public void nodeAssignmentTest5() throws InterpreterException, ExistingSymbolException, UnknownSymbolException, NotFoundValueException, HeapException, ArrayAccessException {
        //boolean tab test
        //tab[0] = true;

        symbolTable.put(new SymbolNode("test@global", Kind.ARRAY, Type.BOOLEAN));

        // boolean test[4]
        NodeArray nodeArray = new NodeArray();
        nodeArray.addChild(new NodeBoolean());
        nodeArray.addChild(new NodeIdent("test", "global"));
        nodeArray.addChild(new NodeNumber(4));

        nodeArray.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        NodeArrayGet nodeArrayGet = new NodeArrayGet();
        nodeArrayGet.addChild(new NodeIdent("test", "global"));
        nodeArrayGet.addChild(new NodeNumber(0));

        NodeAssignment nodeAssignment = new NodeAssignment();
        nodeAssignment.addChild(nodeArrayGet);
        nodeAssignment.addChild(new NodeTrue());

        //interpretation
        Object res = nodeAssignment.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        Assert.assertNull(res);

        //check heap
        Assert.assertTrue(memory.getValueArray("test@global", 0));
    }

    @Test
    public void nodeIfTest() throws InterpreterException {
        NodeIf nodeIf = new NodeIf();
        nodeIf.addChild(new NodeFalse());
        nodeIf.addChild(new NodeInil());
        nodeIf.addChild(new NodeInil());

        Object res = nodeIf.accept(interpreterVisitor, InterpreterMode.DEFAULT);
        Assert.assertNull(res);

    }

    @Test
    public void nodeIfTest2() throws InterpreterException {
        NodeIf nodeIf = new NodeIf();
        nodeIf.addChild(new NodeTrue());
        nodeIf.addChild(new NodeInil());
        nodeIf.addChild(new NodeInil());

        Object res = nodeIf.accept(interpreterVisitor, InterpreterMode.DEFAULT);
        Assert.assertNull(res);

    }

    @Test
    public void nodeIfTest3() throws InterpreterException {
        NodeIf nodeIf = new NodeIf();
        nodeIf.addChild(new NodeTrue());
        nodeIf.addChild(new NodeInil());
        nodeIf.addChild(new NodeInil());

        Object res = nodeIf.accept(interpreterVisitor, InterpreterMode.EVAL);
        Assert.assertNull(res);

    }

    @Test
    public void nodeIncrementTest() throws InterpreterException, ExistingSymbolException, UnknownSymbolException, NotFoundValueException {
        symbolTable.put(new SymbolNode("test@global", Kind.VARIABLE, Type.INTEGER));
        NodeVar nodeVar = new NodeVar();
        nodeVar.addChild(new NodeInteger());
        nodeVar.addChild(new NodeIdent("test", "global"));
        nodeVar.addChild(new NodeNumber(4));

        nodeVar.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        NodeIncrement nodeIncrement = new NodeIncrement();
        nodeIncrement.addChild(new NodeIdent("test", "global"));

        Object res = nodeIncrement.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        int valInc = memory.getValue("test@global");

        //Check new value of ident
        Assert.assertEquals(5, valInc);

        Assert.assertNull(res);
    }

    @Test(expected = InterpreterException.class)
    public void nodeIncrementTest2() throws InterpreterException, ExistingSymbolException {
        symbolTable.put(new SymbolNode("test@global", Kind.VARIABLE, Type.INTEGER));

        NodeIncrement nodeIncrement = new NodeIncrement();
        nodeIncrement.addChild(new NodeIdent("test", "global"));

        nodeIncrement.accept(interpreterVisitor, InterpreterMode.DEFAULT);

    }

    @Test
    public void nodeIncrementTest3() throws InterpreterException, ExistingSymbolException {
        symbolTable.put(new SymbolNode("test@global", Kind.VARIABLE, Type.INTEGER));

        NodeIncrement nodeIncrement = new NodeIncrement();
        nodeIncrement.addChild(new NodeIdent("test", "global"));

        nodeIncrement.accept(interpreterVisitor, InterpreterMode.EVAL);
    }

    @Test
    public void nodeIncrementTest4() throws InterpreterException, ExistingSymbolException, UnknownSymbolException, NotFoundValueException, HeapException, ArrayAccessException {

        symbolTable.put(new SymbolNode("tab@global", Kind.ARRAY, Type.INTEGER));

        // int tab[4]
        NodeArray nodeArray = new NodeArray();
        nodeArray.addChild(new NodeInteger());
        nodeArray.addChild(new NodeIdent("tab", "global"));
        nodeArray.addChild(new NodeNumber(4));

        nodeArray.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        NodeArrayGet nodeArrayGet = new NodeArrayGet();
        nodeArrayGet.addChild(new NodeIdent("tab", "global"));
        nodeArrayGet.addChild(new NodeNumber(0));

        NodeAssignment nodeAssignment = new NodeAssignment();
        nodeAssignment.addChild(nodeArrayGet);
        nodeAssignment.addChild(new NodeNumber(4));

        nodeAssignment.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        int valInd = memory.getValueArray("tab@global", 0);

        Assert.assertEquals(4, valInd);

        NodeIncrement nodeIncrement = new NodeIncrement();
        nodeIncrement.addChild(nodeArrayGet);

        Object res = nodeIncrement.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        int valSum = memory.getValueArray("tab@global", 0);

        //Check new value of ident
        Assert.assertEquals(5, valSum);

        Assert.assertNull(res);
    }


    @Test
    public void nodeWhileTest() throws InterpreterException, ExistingSymbolException {
        // test = 4
        symbolTable.put(new SymbolNode("test@global", Kind.VARIABLE, Type.INTEGER));
        NodeVar nodeVar = new NodeVar();
        nodeVar.addChild(new NodeInteger());
        nodeVar.addChild(new NodeIdent("test", "global"));
        nodeVar.addChild(new NodeNumber(4));

        Object var = nodeVar.accept(interpreterVisitor, InterpreterMode.DEFAULT);
        Assert.assertNull(var);

        // sup (6 > test)
        NodeSup nodeSup = new NodeSup();
        nodeSup.addChild(new NodeNumber(6));
        nodeSup.addChild(new NodeIdent("test", "global"));

        //test++
        NodeIncrement nodeIncrement = new NodeIncrement();
        nodeIncrement.addChild(new NodeIdent("test", "global"));

        //instructions
        NodeInstrs nodeInstrs = new NodeInstrs();
        nodeInstrs.addChild(nodeIncrement);
        nodeInstrs.addChild(new NodeInil());

        //While
        NodeWhile nodeWhile = new NodeWhile();
        nodeWhile.addChild(nodeSup);
        nodeWhile.addChild(nodeInstrs);

        Object res = nodeWhile.accept(interpreterVisitor, InterpreterMode.DEFAULT);
        Assert.assertNull(res);

    }

    @Test
    public void nodeWhileTest2() throws InterpreterException {

        NodeWhile nodeWhile = new NodeWhile();
        nodeWhile.addChild(new NodeFalse());
        nodeWhile.addChild(new NodeInil());

        Object res = nodeWhile.accept(interpreterVisitor, InterpreterMode.DEFAULT);
        Assert.assertNull(res);

    }

    @Test
    public void nodeWhileTest3() throws InterpreterException {

        NodeWhile nodeWhile = new NodeWhile();
        nodeWhile.addChild(new NodeFalse());
        nodeWhile.addChild(new NodeInil());

        Object res = nodeWhile.accept(interpreterVisitor, InterpreterMode.EVAL);
        Assert.assertNull(res);

    }

    //infinite loop
    @Test (expected = InterpreterException.class)
    public void nodeWhileTest4() throws InterpreterException {

        NodeWhile nodeWhile = new NodeWhile();
        nodeWhile.addChild(new NodeTrue());
        nodeWhile.addChild(new NodeInil());

        Object res = nodeWhile.accept(interpreterVisitor, InterpreterMode.DEFAULT);
        Assert.assertNull(res);

    }

    @Test
    public void nodeArrayTest() throws InterpreterException, StackException, ExistingSymbolException, HeapException {
        symbolTable.put(new SymbolNode("test@global", Kind.ARRAY, Type.INTEGER));

        // int test[4]
        NodeArray nodeArray = new NodeArray();
        nodeArray.addChild(new NodeInteger());
        nodeArray.addChild(new NodeIdent("test", "global"));
        nodeArray.addChild(new NodeNumber(4));

        Object res = nodeArray.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        Assert.assertNull(res);

        int address = memory.peekValue();
        HeapEntry heapEntry = memory.getHeapEntry(address);

        Assert.assertEquals(4, heapEntry.getSize());
        Assert.assertEquals(1, heapEntry.getNbRef());
    }

    @Test
    public void nodeArrayTest2() throws InterpreterException, StackException, ExistingSymbolException, InvalidTypeException {
        symbolTable.put(new SymbolNode("test@global", Kind.ARRAY, Type.INTEGER));
        NodeArray nodeArray = new NodeArray();
        nodeArray.addChild(new NodeInteger());
        nodeArray.addChild(new NodeIdent("test", "global"));
        nodeArray.addChild(new NodeNumber(4));

        memory.push(false);

        nodeArray.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        Object res = nodeArray.accept(interpreterVisitor, InterpreterMode.CANCEL);

        Assert.assertNull(res);
        Assert.assertEquals(false, memory.peekValue());
    }

    @Test
    public void nodeArrayTest3() throws InterpreterException, ExistingSymbolException {
        symbolTable.put(new SymbolNode("test@global", Kind.ARRAY, Type.INTEGER));
        NodeArray nodeArray = new NodeArray();
        nodeArray.addChild(new NodeInteger());
        nodeArray.addChild(new NodeIdent("test", "global"));
        nodeArray.addChild(new NodeNumber(4));

        Object res = nodeArray.accept(interpreterVisitor, InterpreterMode.EVAL);

        Assert.assertNull(res);
    }

    @Test(expected = InterpreterException.class)
    public void nodeArrayTest4() throws InterpreterException {
        NodeArray nodeArray = new NodeArray();
        nodeArray.addChild(new NodeInteger());
        nodeArray.addChild(new NodeIdent("test", "global"));
        nodeArray.addChild(new NodeNumber(4));

        Object res = nodeArray.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        Assert.assertNull(res);
    }

    @Test(expected = InterpreterException.class)
    public void nodeArrayTest5() throws InterpreterException, ExistingSymbolException {
        symbolTable.put(new SymbolNode("test@global", Kind.ARRAY, Type.INTEGER));

        NodeArray nodeArray = new NodeArray();
        nodeArray.addChild(new NodeInteger());
        nodeArray.addChild(new NodeIdent("test", "global"));
        nodeArray.addChild(new NodeNumber(4));

        Object res = nodeArray.accept(interpreterVisitor, InterpreterMode.CANCEL);

        Assert.assertNull(res);
    }

    @Test
    public void nodeArrayGetTest() throws InterpreterException, ExistingSymbolException, NotFoundValueException, ArrayAccessException, HeapException, UnknownSymbolException {
        //tab[0] = 4;
        symbolTable.put(new SymbolNode("test@global", Kind.ARRAY, Type.INTEGER));

        // int test[4]
        NodeArray nodeArray = new NodeArray();
        nodeArray.addChild(new NodeInteger());
        nodeArray.addChild(new NodeIdent("test", "global"));
        nodeArray.addChild(new NodeNumber(4));

        nodeArray.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        NodeArrayGet nodeArrayGet = new NodeArrayGet();
        nodeArrayGet.addChild(new NodeIdent("test", "global"));
        nodeArrayGet.addChild(new NodeNumber(0));

        NodeAssignment nodeAssignment = new NodeAssignment();
        nodeAssignment.addChild(nodeArrayGet);
        nodeAssignment.addChild(new NodeNumber(4));

        nodeAssignment.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        int valInd = memory.getValueArray("test@global", 0);

        Assert.assertEquals(4, valInd);
    }

    @Test(expected = InterpreterException.class)
    public void nodeArrayGetTest2() throws InterpreterException, ExistingSymbolException {
        //tab[0] = 1;
        symbolTable.put(new SymbolNode("test@global", Kind.ARRAY, Type.INTEGER));

        // int test[4]
        NodeArray nodeArray = new NodeArray();
        nodeArray.addChild(new NodeInteger());
        nodeArray.addChild(new NodeIdent("test", "global"));
        nodeArray.addChild(new NodeNumber(4));

        nodeArray.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        NodeArrayGet nodeArrayGet = new NodeArrayGet();
        nodeArrayGet.addChild(new NodeIdent("test2", "global"));
        nodeArrayGet.addChild(new NodeNumber(0));

        NodeAssignment nodeAssignment = new NodeAssignment();
        nodeAssignment.addChild(nodeArrayGet);
        nodeAssignment.addChild(new NodeNumber(4));

        nodeAssignment.accept(interpreterVisitor, InterpreterMode.DEFAULT);

    }

    @Test
    public void nodeArrayGetTest3() throws InterpreterException {
        NodeArrayGet nodeArrayGet = new NodeArrayGet();
        nodeArrayGet.addChild(new NodeIdent("test2", "global"));
        nodeArrayGet.addChild(new NodeNumber(0));

        Object res = nodeArrayGet.accept(interpreterVisitor, InterpreterMode.DEFAULT);
        Assert.assertNull(res);
    }

    @Test
    public void nodeCallETest() throws InterpreterException, MemoryException {

        symbolTable.put(new SymbolNode("ClassTest@global", Kind.VARIABLE, null));
        symbolTable.put(new SymbolNode("value@global", Kind.VARIABLE, Type.INTEGER));
        symbolTable.put(new SymbolNode("inc:int", Kind.METHOD, Type.INTEGER));
        symbolTable.put(new SymbolNode("arg@inc:int", Kind.VARIABLE, Type.INTEGER));

        memory.declVar("ClassTest@global", null);


        //------------int inc(int arg){ arg++; return arg;};--------------------------------------
        //Headers
        NodeHeader nodeHeader = new NodeHeader();
        nodeHeader.addChild(new NodeInteger());
        nodeHeader.addChild(new NodeIdent("arg", "inc:int"));

        NodeHeaders nodeHeaders = new NodeHeaders();
        nodeHeaders.addChild(nodeHeader);
        nodeHeaders.addChild(new NodeEnil());

        //Vars
        NodeEnil nodeVars = new NodeEnil();

        //Instrs
        NodeIncrement nodeIncrement = new NodeIncrement();
        nodeIncrement.addChild(new NodeIdent("arg", "inc:int"));

        NodeReturn nodeReturn = new NodeReturn();
        nodeReturn.addChild(new NodeIdent("arg", "inc:int"));

        NodeInstrs nodeInstrs = new NodeInstrs();
        NodeInstrs nodeInstrs2 = new NodeInstrs();
        nodeInstrs.addChild(nodeIncrement);
        nodeInstrs.addChild(nodeInstrs2);
        nodeInstrs2.addChild(nodeReturn);
        nodeInstrs2.addChild(new NodeInil());


        NodeMethod nodeMethod = new NodeMethod();

        nodeMethod.addChild(new NodeInteger());
        nodeMethod.addChild(new NodeIdent("inc", "global", "int"));
        nodeMethod.addChild(nodeHeaders);
        nodeMethod.addChild(nodeVars);
        nodeMethod.addChild(nodeInstrs);

        nodeMethod.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        //------------------ int value = 4 ;---------------------------------
        NodeVar nodeVar = new NodeVar();
        nodeVar.addChild(new NodeInteger());
        nodeVar.addChild(new NodeIdent("value", "global"));
        nodeVar.addChild(new NodeNumber(4));

        nodeVar.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        //---------- value = inc(value);-----------------

        //Node list exp
        NodeExpList nodeExpList = new NodeExpList();
        nodeExpList.addChild(new NodeIdent("value", "global"));
        nodeExpList.addChild(new NodeExnil());

        //NodeCallE
        NodeCallE nodeCallE = new NodeCallE();
        nodeCallE.addChild(new NodeIdent("inc", "global", "int"));
        nodeCallE.addChild(nodeExpList);

        NodeAssignment nodeAssignment = new NodeAssignment();
        nodeAssignment.addChild(new NodeIdent("value", "global"));
        nodeAssignment.addChild(nodeCallE);

        nodeAssignment.accept(interpreterVisitor, InterpreterMode.DEFAULT);


        int value = memory.getValue("value@global");
        Assert.assertEquals(5, value);
    }

    @Test(expected = InterpreterException.class)
    public void nodeCallETest2() throws InterpreterException, ExistingSymbolException, WrongKindException, InvalidTypeException, UnknownSymbolException {

        symbolTable.put(new SymbolNode("ClassTest@global", Kind.VARIABLE, null));
        symbolTable.put(new SymbolNode("value@global", Kind.VARIABLE, Type.INTEGER));
        symbolTable.put(new SymbolNode("inc:int", Kind.METHOD, Type.INTEGER));
        symbolTable.put(new SymbolNode("arg@inc:int", Kind.VARIABLE, Type.INTEGER));

        memory.declVar("ClassTest@global", null);


        //------------int inc(int arg){ arg++; return arg;};--------------------------------------
        //Headers
        NodeHeader nodeHeader = new NodeHeader();
        nodeHeader.addChild(new NodeInteger());
        nodeHeader.addChild(new NodeIdent("arg", "inc:int"));

        NodeHeaders nodeHeaders = new NodeHeaders();
        nodeHeaders.addChild(nodeHeader);
        nodeHeaders.addChild(new NodeEnil());

        //Vars
        NodeEnil nodeVars = new NodeEnil();

        //Instrs
        NodeIncrement nodeIncrement = new NodeIncrement();
        nodeIncrement.addChild(new NodeIdent("arg", "inc:int"));

        NodeReturn nodeReturn = new NodeReturn();
        nodeReturn.addChild(new NodeIdent("arg", "inc:int"));

        NodeInstrs nodeInstrs = new NodeInstrs();
        NodeInstrs nodeInstrs2 = new NodeInstrs();
        nodeInstrs.addChild(nodeIncrement);
        nodeInstrs.addChild(nodeInstrs2);
        nodeInstrs2.addChild(nodeReturn);
        nodeInstrs2.addChild(new NodeInil());


        NodeMethod nodeMethod = new NodeMethod();

        nodeMethod.addChild(new NodeInteger());
        nodeMethod.addChild(new NodeIdent("inc", "global", "int"));
        nodeMethod.addChild(nodeHeaders);
        nodeMethod.addChild(nodeVars);
        nodeMethod.addChild(nodeInstrs);

        nodeMethod.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        //------------------ int value = 4 ;---------------------------------
        NodeVar nodeVar = new NodeVar();
        nodeVar.addChild(new NodeInteger());
        nodeVar.addChild(new NodeIdent("value", "global"));
        nodeVar.addChild(new NodeNumber(4));

        nodeVar.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        //---------- value = inc(value);-----------------

        //Node list exp
        NodeExpList nodeExpList = new NodeExpList();
        nodeExpList.addChild(new NodeIdent("value", "global"));
        nodeExpList.addChild(new NodeExnil());

        //NodeCallE
        NodeCallE nodeCallE = new NodeCallE();
        nodeCallE.addChild(new NodeIdent("inc", "global", "int"));
        nodeCallE.addChild(nodeExpList);

        NodeAssignment nodeAssignment = new NodeAssignment();
        nodeAssignment.addChild(new NodeIdent("value2", "global"));
        nodeAssignment.addChild(nodeCallE);

        nodeAssignment.accept(interpreterVisitor, InterpreterMode.DEFAULT);

    }

    @Test
    public void nodeCallETest3() throws InterpreterException {

        //Node list exp
        NodeExpList nodeExpList = new NodeExpList();
        nodeExpList.addChild(new NodeVnil());
        nodeExpList.addChild(new NodeExnil());

        //NodeCallE
        NodeCallE nodeCallE = new NodeCallE();
        nodeCallE.addChild(new NodeIdent("inc", "global", "int"));
        nodeCallE.addChild(nodeExpList);

        nodeCallE.accept(interpreterVisitor, InterpreterMode.DEFAULT);
    }

    @Test
    public void nodeCallETest4() throws InterpreterException, MemoryException {

        symbolTable.put(new SymbolNode("ClassTest@global", Kind.VARIABLE, null));
        symbolTable.put(new SymbolNode("value@global", Kind.VARIABLE, Type.INTEGER));
        symbolTable.put(new SymbolNode("value2@global", Kind.VARIABLE, Type.INTEGER));
        symbolTable.put(new SymbolNode("value3@global", Kind.VARIABLE, Type.INTEGER));
        symbolTable.put(new SymbolNode("inc:int_int_int", Kind.METHOD, Type.INTEGER));
        symbolTable.put(new SymbolNode("arg@inc:int_int_int", Kind.VARIABLE, Type.INTEGER));
        symbolTable.put(new SymbolNode("arg2@inc:int_int_int", Kind.VARIABLE, Type.INTEGER));
        symbolTable.put(new SymbolNode("arg3@inc:int_int_int", Kind.VARIABLE, Type.INTEGER));

        memory.declVar("ClassTest@global", null);

        //------------int inc(int arg, int arg2, int arg3){ arg +=arg2; arg += arg3; return arg;};--------------------------------------
        //Headers
        NodeHeader nodeHeader = new NodeHeader();
        NodeHeader nodeHeader2 = new NodeHeader();
        NodeHeader nodeHeader3 = new NodeHeader();

        nodeHeader.addChild(new NodeInteger());
        nodeHeader.addChild(new NodeIdent("arg", "inc:int_int_int"));

        nodeHeader2.addChild(new NodeInteger());
        nodeHeader2.addChild(new NodeIdent("arg2", "inc:int_int_int"));

        nodeHeader3.addChild(new NodeInteger());
        nodeHeader3.addChild(new NodeIdent("arg3", "inc:int_int_int"));

        NodeHeaders nodeHeaders = new NodeHeaders();
        NodeHeaders nodeHeaders2 = new NodeHeaders();
        NodeHeaders nodeHeaders3 = new NodeHeaders();

        nodeHeaders.addChild(nodeHeader);
        nodeHeaders.addChild(nodeHeaders2);

        nodeHeaders2.addChild(nodeHeader2);
        nodeHeaders2.addChild(nodeHeaders3);

        nodeHeaders3.addChild(nodeHeader3);
        nodeHeaders3.addChild(new NodeEnil());

        //Vars
        NodeEnil nodeVars = new NodeEnil();

        //Instrs
        NodeSum nodeSum = new NodeSum();
        nodeSum.addChild(new NodeIdent("arg", "inc:int_int_int"));
        nodeSum.addChild(new NodeIdent("arg2", "inc:int_int_int"));

        NodeSum nodeSum2 = new NodeSum();
        nodeSum2.addChild(new NodeIdent("arg", "inc:int_int_int"));
        nodeSum2.addChild(new NodeIdent("arg3", "inc:int_int_int"));

        NodeReturn nodeReturn = new NodeReturn();
        nodeReturn.addChild(new NodeIdent("arg", "inc:int_int_int"));

        NodeInstrs nodeInstrs = new NodeInstrs();
        NodeInstrs nodeInstrs2 = new NodeInstrs();
        NodeInstrs nodeInstrs3 = new NodeInstrs();

        nodeInstrs.addChild(nodeSum);
        nodeInstrs.addChild(nodeInstrs2);

        nodeInstrs2.addChild(nodeSum2);
        nodeInstrs2.addChild(nodeInstrs3);

        nodeInstrs3.addChild(nodeReturn);
        nodeInstrs3.addChild(new NodeInil());


        NodeMethod nodeMethod = new NodeMethod();

        nodeMethod.addChild(new NodeInteger());
        nodeMethod.addChild(new NodeIdent("inc", "global", "int_int_int"));
        nodeMethod.addChild(nodeHeaders);
        nodeMethod.addChild(nodeVars);
        nodeMethod.addChild(nodeInstrs);

        nodeMethod.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        //------------------ int value = 4 ;---------------------------------
        NodeVar nodeVar = new NodeVar();
        nodeVar.addChild(new NodeInteger());
        nodeVar.addChild(new NodeIdent("value", "global"));
        nodeVar.addChild(new NodeNumber(4));

        nodeVar.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        //------------------ int value2 = 4 ;---------------------------------
        NodeVar nodeVar2 = new NodeVar();
        nodeVar2.addChild(new NodeInteger());
        nodeVar2.addChild(new NodeIdent("value2", "global"));
        nodeVar2.addChild(new NodeNumber(4));

        nodeVar2.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        //------------------ int value3 = 4 ;---------------------------------
        NodeVar nodeVar3 = new NodeVar();
        nodeVar3.addChild(new NodeInteger());
        nodeVar3.addChild(new NodeIdent("value3", "global"));
        nodeVar3.addChild(new NodeNumber(4));

        nodeVar3.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        //---------- value = inc(value);-----------------

        //Node list exp
        NodeExpList nodeExpList = new NodeExpList();
        NodeExpList nodeExpList2 = new NodeExpList();
        NodeExpList nodeExpList3 = new NodeExpList();

        nodeExpList.addChild(new NodeIdent("value", "global"));
        nodeExpList.addChild(nodeExpList2);

        nodeExpList2.addChild(new NodeIdent("value2", "global"));
        nodeExpList2.addChild(nodeExpList3);

        nodeExpList3.addChild(new NodeIdent("value3", "global"));
        nodeExpList3.addChild(new NodeExnil());

        //NodeCallE
        NodeCallE nodeCallE = new NodeCallE();
        nodeCallE.addChild(new NodeIdent("inc", "global", "int_int_int"));
        nodeCallE.addChild(nodeExpList);

        NodeAssignment nodeAssignment = new NodeAssignment();
        nodeAssignment.addChild(new NodeIdent("value", "global"));
        nodeAssignment.addChild(nodeCallE);

        nodeAssignment.accept(interpreterVisitor, InterpreterMode.DEFAULT);


        int value = memory.getValue("value@global");
        Assert.assertEquals(12, value);
    }


    @Test
    public void nodeCallITest() throws InterpreterException, ExistingSymbolException, UnknownSymbolException, NotFoundValueException, InvalidTypeException, WrongKindException {

        symbolTable.put(new SymbolNode("ClassTest@global", Kind.VARIABLE, null));
        symbolTable.put(new SymbolNode("value@global", Kind.VARIABLE, Type.INTEGER));
        symbolTable.put(new SymbolNode("inc:", Kind.METHOD, Type.INTEGER));

        memory.declVar("ClassTest@global", null);


        //------------int inc(){ value++; return arg;};--------------------------------------

        //Vars
        NodeEnil nodeVars = new NodeEnil();

        //Instrs
        NodeIncrement nodeIncrement = new NodeIncrement();
        nodeIncrement.addChild(new NodeIdent("value", "global"));

        NodeInstrs nodeInstrs = new NodeInstrs();
        nodeInstrs.addChild(nodeIncrement);
        nodeInstrs.addChild(new NodeInil());


        NodeMethod nodeMethod = new NodeMethod();

        nodeMethod.addChild(new NodeInteger());
        nodeMethod.addChild(new NodeIdent("inc", "global", ""));
        nodeMethod.addChild(new NodeEnil());
        nodeMethod.addChild(nodeVars);
        nodeMethod.addChild(nodeInstrs);

        nodeMethod.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        //------------------ int value = 4 ;---------------------------------
        NodeVar nodeVar = new NodeVar();
        nodeVar.addChild(new NodeInteger());
        nodeVar.addChild(new NodeIdent("value", "global"));
        nodeVar.addChild(new NodeNumber(4));

        nodeVar.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        //---------- inc();-----------------

        //NodeCallI
        NodeCallI nodeCallI = new NodeCallI();
        nodeCallI.addChild(new NodeIdent("inc", "global", ""));
        nodeCallI.addChild(new NodeExnil());

        nodeCallI.accept(interpreterVisitor, InterpreterMode.DEFAULT);


        int value = memory.getValue("value@global");
        Assert.assertEquals(5, value);

    }

    //no symbol matching method ident
    @Test(expected = InterpreterException.class)
    public void nodeCallITest2() throws InterpreterException, ExistingSymbolException, UnknownSymbolException, InvalidTypeException, WrongKindException {

        symbolTable.put(new SymbolNode("ClassTest@global", Kind.VARIABLE, null));
        symbolTable.put(new SymbolNode("value@global", Kind.VARIABLE, Type.INTEGER));
        symbolTable.put(new SymbolNode("inc:", Kind.METHOD, Type.INTEGER));

        memory.declVar("ClassTest@global", null);


        //------------int inc(){ value++; return arg;};--------------------------------------

        //Vars
        NodeEnil nodeVars = new NodeEnil();

        //Instrs
        NodeIncrement nodeIncrement = new NodeIncrement();
        nodeIncrement.addChild(new NodeIdent("value", "global"));

        NodeInstrs nodeInstrs = new NodeInstrs();
        nodeInstrs.addChild(nodeIncrement);
        nodeInstrs.addChild(new NodeInil());


        NodeMethod nodeMethod = new NodeMethod();

        nodeMethod.addChild(new NodeInteger());
        nodeMethod.addChild(new NodeIdent("inc", "global", ""));
        nodeMethod.addChild(new NodeEnil());
        nodeMethod.addChild(nodeVars);
        nodeMethod.addChild(nodeInstrs);

        nodeMethod.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        //------------------ int value = 4 ;---------------------------------
        NodeVar nodeVar = new NodeVar();
        nodeVar.addChild(new NodeInteger());
        nodeVar.addChild(new NodeIdent("value", "global"));
        nodeVar.addChild(new NodeNumber(4));

        nodeVar.accept(interpreterVisitor, InterpreterMode.DEFAULT);

        //---------- inc();-----------------

        //NodeCallI
        NodeCallI nodeCallI = new NodeCallI();
        nodeCallI.addChild(new NodeIdent("inc2", "global", ""));
        nodeCallI.addChild(new NodeExnil());

        nodeCallI.accept(interpreterVisitor, InterpreterMode.DEFAULT);

    }

    //mode eval (expected behavior : nothing happens)
    @Test
    public void nodeCallITest3() throws InterpreterException {

        //Node list exp
        NodeExpList nodeExpList = new NodeExpList();
        nodeExpList.addChild(new NodeVnil());
        nodeExpList.addChild(new NodeExnil());

        //NodeCallI
        NodeCallI nodeCallI = new NodeCallI();
        nodeCallI.addChild(new NodeIdent("inc", "global", ""));
        nodeCallI.addChild(nodeExpList);

        nodeCallI.accept(interpreterVisitor, InterpreterMode.EVAL);

    }


    @Test
    public void nodeMethodTest() throws InterpreterException, ExistingSymbolException {
        symbolTable.put(new SymbolNode("testMethod:int", Kind.METHOD, Type.INTEGER));

        //Entetes
        NodeHeader nodeHeader = new NodeHeader();
        nodeHeader.addChild(new NodeInteger());
        nodeHeader.addChild(new NodeIdent("arg", "testMethod:int"));

        NodeHeaders nodeHeaders = new NodeHeaders();
        nodeHeaders.addChild(nodeHeader);
        nodeHeaders.addChild(new NodeEnil());

        //Vars
        NodeVars nodeVars = new NodeVars();
        nodeVars.addChild(new NodeEnil());

        //Instrs
        NodeInstrs nodeInstrs = new NodeInstrs();
        nodeInstrs.addChild(new NodeInil());

        NodeMethod nodeMethod = new NodeMethod();

        nodeMethod.addChild(new NodeInteger());
        nodeMethod.addChild(new NodeIdent("testMethod", "global", "int"));
        nodeMethod.addChild(nodeHeaders);
        nodeMethod.addChild(nodeVars);
        nodeMethod.addChild(nodeInstrs);

        Object res = nodeMethod.accept(interpreterVisitor, InterpreterMode.DEFAULT);
        Assert.assertNull(res);

    }

    //no symbol matching method ident
    @Test(expected = InterpreterException.class)
    public void nodeMethodTest2() throws InterpreterException, ExistingSymbolException {
        symbolTable.put(new SymbolNode("testMethod:boolean", Kind.METHOD, Type.INTEGER));

        //Entetes
        NodeHeader nodeHeader = new NodeHeader();
        nodeHeader.addChild(new NodeInteger());
        nodeHeader.addChild(new NodeIdent("arg", "testMethod:boolean"));

        NodeHeaders nodeHeaders = new NodeHeaders();
        nodeHeaders.addChild(nodeHeader);
        nodeHeaders.addChild(new NodeEnil());

        //Vars
        NodeVars nodeVars = new NodeVars();
        nodeVars.addChild(new NodeEnil());

        //Instrs
        NodeInstrs nodeInstrs = new NodeInstrs();
        nodeInstrs.addChild(new NodeInil());

        NodeMethod nodeMethod = new NodeMethod();

        nodeMethod.addChild(new NodeInteger());
        nodeMethod.addChild(new NodeIdent("testMethod", "global", "int"));
        nodeMethod.addChild(nodeHeaders);
        nodeMethod.addChild(nodeVars);
        nodeMethod.addChild(nodeInstrs);

        nodeMethod.accept(interpreterVisitor, InterpreterMode.DEFAULT);
    }

    //mode eval test (expected behavior : nothing happens)
    @Test
    public void nodeMethodTest3() throws InterpreterException, ExistingSymbolException {
        symbolTable.put(new SymbolNode("testMethod:int", Kind.METHOD, Type.INTEGER));

        //Entetes
        NodeHeader nodeHeader = new NodeHeader();
        nodeHeader.addChild(new NodeInteger());
        nodeHeader.addChild(new NodeIdent("arg", "testMethod:int"));

        NodeHeaders nodeHeaders = new NodeHeaders();
        nodeHeaders.addChild(nodeHeader);
        nodeHeaders.addChild(new NodeEnil());

        //Vars
        NodeVars nodeVars = new NodeVars();
        nodeVars.addChild(new NodeEnil());

        //Instrs
        NodeInstrs nodeInstrs = new NodeInstrs();
        nodeInstrs.addChild(new NodeInil());

        NodeMethod nodeMethod = new NodeMethod();

        nodeMethod.addChild(new NodeInteger());
        nodeMethod.addChild(new NodeIdent("testMethod", "global", "int"));
        nodeMethod.addChild(nodeHeaders);
        nodeMethod.addChild(nodeVars);
        nodeMethod.addChild(nodeInstrs);

        nodeMethod.accept(interpreterVisitor, InterpreterMode.EVAL);

    }

    @Test
    public void nodeExpListTest() throws InterpreterException {
        NodeExpList nodeExpList = new NodeExpList();
        nodeExpList.addChild(new NodeNumber(4));
        nodeExpList.addChild(new NodeExnil());

        Object res = nodeExpList.accept(interpreterVisitor, InterpreterMode.CANCEL);
        Assert.assertNull(res);
    }

    @Test
    public void nodeHeaderTest() throws InterpreterException {

        //Entetes
        NodeHeader nodeHeader = new NodeHeader();
        nodeHeader.addChild(new NodeInteger());
        nodeHeader.addChild(new NodeIdent("arg", "method:"));

        Object res = nodeHeader.accept(interpreterVisitor, InterpreterMode.DEFAULT);
        Assert.assertNull(res);
    }

    @Test
    public void nodeHeadersTest() throws InterpreterException, ExistingSymbolException {
        symbolTable.put(new SymbolNode("test@method:", Kind.VARIABLE, Type.INTEGER));

        NodeHeaders nodeHeaders = new NodeHeaders();

        NodeHeader nodeHeader = new NodeHeader();
        nodeHeader.addChild(new NodeInteger());
        nodeHeader.addChild(new NodeIdent("test", "method:"));

        nodeHeaders.addChild(nodeHeader);
        nodeHeaders.addChild(new NodeInil());

        Object res = nodeHeaders.accept(interpreterVisitor, InterpreterMode.DEFAULT);
        Assert.assertNull(res);
    }

    @Test
    public void nodeBooleanTest() throws InterpreterException {
        NodeBoolean nodeBoolean = new NodeBoolean();

        Object res = nodeBoolean.accept(interpreterVisitor, InterpreterMode.DEFAULT);
        Assert.assertNull(res);
    }

    @Test
    public void nodeIntegerTest() throws InterpreterException {
        NodeInteger nodeInteger = new NodeInteger();

        Object res = nodeInteger.accept(interpreterVisitor, InterpreterMode.DEFAULT);
        Assert.assertNull(res);
    }

    @Test
    public void nodeVoidTest() throws InterpreterException {
        NodeVoid nodeVoid = new NodeVoid();

        Object res = nodeVoid.accept(interpreterVisitor, InterpreterMode.DEFAULT);
        Assert.assertNull(res);
    }

    @Test
    public void nodeOmegaTest() throws InterpreterException {
        NodeOmega nodeOmega = new NodeOmega();

        Object res = nodeOmega.accept(interpreterVisitor, InterpreterMode.DEFAULT);
        Assert.assertNull(res);
    }

    @Test
    public void nodeEnilTest() throws InterpreterException {
        NodeEnil nodeEnil = new NodeEnil();

        Object res = nodeEnil.accept(interpreterVisitor, InterpreterMode.DEFAULT);
        Assert.assertNull(res);
    }

    @Test
    public void nodeExnilTest() throws InterpreterException {
        NodeExnil nodeExnil = new NodeExnil();

        Object res = nodeExnil.accept(interpreterVisitor, InterpreterMode.DEFAULT);
        Assert.assertNull(res);
    }

}
