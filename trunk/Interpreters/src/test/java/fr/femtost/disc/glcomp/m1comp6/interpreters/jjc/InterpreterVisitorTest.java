package fr.femtost.disc.glcomp.m1comp6.interpreters.jjc;

import fr.femtost.disc.glcomp.m1comp6.ast.common.Kind;
import fr.femtost.disc.glcomp.m1comp6.ast.common.Type;
import fr.femtost.disc.glcomp.m1comp6.ast.jjc.*;
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
        interpreterVisitor.setAdr(1);
    }

    @Test
    public void nodeInitTest() throws InterpreterException {
        //creation of jjc AST
        NodeInit nodeInit = new NodeInit();

        //interpretation
        Object res = nodeInit.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());
    }


    @Test
    public void nodePopTest2() throws InterpreterException, InvalidTypeException, StackException {

        memory.push(4);
        memory.push(5);

        //---------------------------

        //creation of jjc AST
        NodePop nodePop = new NodePop();

        //interpretation
        Object res = nodePop.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());

        //check stack
        Assert.assertEquals(4, (int) memory.peekValue());
    }

    //Pop no existing value
    @Test(expected = InterpreterException.class)
    public void nodePopTest3() throws InterpreterException {

        //creation of jjc AST
        NodePop nodePop = new NodePop();

        //interpretation
        nodePop.accept(interpreterVisitor);

    }

    //push number
    @Test
    public void nodePushNumberTest() throws InterpreterException, StackException {

        //creation of jjc AST
        NodePush nodePush = new NodePush();
        nodePush.addChild(new NodeNumber(4));

        //interpretation
        Object res = nodePush.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());

        //check stack
        Assert.assertEquals(4, (int) memory.peekValue());
    }

    //push true
    @Test
    public void nodePushTrueTest() throws InterpreterException, StackException {

        //creation of jjc AST
        NodePush nodePush = new NodePush();
        nodePush.addChild(new NodeTrue());

        //interpretation
        Object res = nodePush.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());

        //check stack
        Assert.assertEquals(true, memory.peekValue());
    }

    //push false
    @Test
    public void nodePushFalseTest() throws InterpreterException, StackException {

        //creation of jjc AST
        NodePush nodePush = new NodePush();
        nodePush.addChild(new NodeFalse());

        //interpretation
        Object res = nodePush.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());

        //check stack
        Assert.assertEquals(false,memory.peekValue());
    }

     //swap on empty stack
    @Test(expected = InterpreterException.class)
    public void nodeSwapTest1() throws InterpreterException {

        //creation of jjc AST
        NodeSwap nodeSwap = new NodeSwap();

        //interpretation
        nodeSwap.accept(interpreterVisitor);
    }

    @Test
    public void nodeSwapTest2() throws InterpreterException, StackException, InvalidTypeException, HeapException {

        memory.push(4);
        memory.push(true);

        //---------------------

        //creation of jjc AST
        NodeSwap nodeSwap = new NodeSwap();

        //interpretation
        Object res = nodeSwap.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());

        //check stack
        Assert.assertEquals(4, (int) memory.peekValue());
        memory.pop();
        Assert.assertTrue(memory.peekValue());
    }

    @Test
    public void nodeIdentTest() throws InterpreterException {
        //creation of jjc AST
        NodeIdent nodeIdent = new NodeIdent("test");

        //interpretation
        Object res = nodeIdent.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(1, interpreterVisitor.getAdr());
    }

    @Test
    public void nodeTypeTest() throws InterpreterException {
        //creation of jjc AST
        NodeType nodeType = new NodeType(Type.INTEGER);

        //interpretation
        Object res = nodeType.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(1, interpreterVisitor.getAdr());
    }


    @Test
    public void nodeKindTest() throws InterpreterException {
        //creation of jjc AST
        NodeKind nodeKind = new NodeKind(Kind.VARIABLE);

        //interpretation
        Object res = nodeKind.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(1, interpreterVisitor.getAdr());
    }


    @Test
    public void nodeNumberTest() throws InterpreterException {
        //creation of jjc AST
        NodeNumber nodeNumber = new NodeNumber(4);

        //interpretation
        Object res = nodeNumber.accept(interpreterVisitor);

        //check return
        Assert.assertEquals(4, res);

        //check adr
        Assert.assertEquals(1, interpreterVisitor.getAdr());
    }

    @Test
    public void nodeFalseTest() throws InterpreterException {
        //creation of jjc AST
        NodeFalse nodeFalse = new NodeFalse();

        //interpretation
        Object res = nodeFalse.accept(interpreterVisitor);

        //check return
        Assert.assertEquals(false, res);

        //check adr
        Assert.assertEquals(1, interpreterVisitor.getAdr());
    }

    @Test
    public void nodeTrueTest() throws InterpreterException {
        //creation of jjc AST
        NodeTrue nodeTrue = new NodeTrue();

        //interpretation
        Object res = nodeTrue.accept(interpreterVisitor);

        //check return
        Assert.assertEquals(true, res);

        //check adr
        Assert.assertEquals(1, interpreterVisitor.getAdr());
    }

    @Test
    public void nodeJcnilTest() throws InterpreterException {
        //creation of jjc AST
        NodeJcnil nodeJcnil = new NodeJcnil();

        //interpretation
        Object res = nodeJcnil.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(1, interpreterVisitor.getAdr());
    }

    @Test
    public void nodeJcstopTest() throws InterpreterException {
        //creation of jjc AST
        NodeJcstop nodeJcstop = new NodeJcstop();

        //interpretation
        Object res = nodeJcstop.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(-1, interpreterVisitor.getAdr());
    }

    //incorrect s  argument : no value in stack at position 0 from top (because stack is empty)
    @Test(expected = InterpreterException.class)
    public void nodeNewTestVar1() throws InterpreterException {

        //creation of jjc AST
        NodeNew nodeNew = new NodeNew();
        nodeNew.addChild(new NodeIdent("x@main"));
        nodeNew.addChild(new NodeType(Type.INTEGER));
        nodeNew.addChild(new NodeKind(Kind.VARIABLE));
        nodeNew.addChild(new NodeNumber(0));

        //interpretation
        nodeNew.accept(interpreterVisitor);
    }

    //no corresponding symbol in symbolTable
    @Test(expected = InterpreterException.class)
    public void nodeNewTestVar2() throws InterpreterException, InvalidTypeException {

        memory.push(4);

        //---------------------

        //creation of jjc AST
        NodeNew nodeNew = new NodeNew();
        nodeNew.addChild(new NodeIdent("x@main"));
        nodeNew.addChild(new NodeType(Type.INTEGER));
        nodeNew.addChild(new NodeKind(Kind.VARIABLE));
        nodeNew.addChild(new NodeNumber(0));

        //interpretation
        nodeNew.accept(interpreterVisitor);
    }

    //type don't match with the symbol's type
    @Test(expected = InterpreterException.class)
    public void nodeNewTestVar3() throws InterpreterException, ExistingSymbolException, InvalidTypeException {

        symbolTable.put(new SymbolNode("x@main", Kind.VARIABLE, Type.BOOLEAN));
        memory.push(4);

        //---------------------

        //creation of jjc AST
        NodeNew nodeNew = new NodeNew();
        nodeNew.addChild(new NodeIdent("x@main"));
        nodeNew.addChild(new NodeType(Type.INTEGER));
        nodeNew.addChild(new NodeKind(Kind.VARIABLE));
        nodeNew.addChild(new NodeNumber(0));

        //interpretation
        nodeNew.accept(interpreterVisitor);
    }


    @Test
    public void nodeNewTestVar4() throws InterpreterException, StackException, ExistingSymbolException, InvalidTypeException {

        symbolTable.put(new SymbolNode("x@main", Kind.VARIABLE, Type.INTEGER));
        memory.push(4);

        //---------------------

        //creation of jjc AST
        NodeNew nodeNew = new NodeNew();
        nodeNew.addChild(new NodeIdent("x@main"));
        nodeNew.addChild(new NodeType(Type.INTEGER));
        nodeNew.addChild(new NodeKind(Kind.VARIABLE));
        nodeNew.addChild(new NodeNumber(0));

        //interpretation
        Object res = nodeNew.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());

        //check stack
        Assert.assertEquals(4, (int) memory.peekValue());
        //TODO : check more in detail the value node that was added ontop of the stack (problem : no way to get the value node added)
    }

    //new unassigned var
    @Test
    public void nodeNewTestVar5() throws InterpreterException, StackException, ExistingSymbolException, InvalidTypeException {

        symbolTable.put(new SymbolNode("x@main", Kind.VARIABLE, Type.INTEGER));
        memory.push(null);

        //---------------------

        //creation of jjc AST
        NodeNew nodeNew = new NodeNew();
        nodeNew.addChild(new NodeIdent("x@main"));
        nodeNew.addChild(new NodeType(Type.INTEGER));
        nodeNew.addChild(new NodeKind(Kind.VARIABLE));
        nodeNew.addChild(new NodeNumber(0));

        //interpretation
        Object res = nodeNew.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());

        //check stack
        Assert.assertNull(memory.peekValue());
    }

    //incorrect s  argument : no value in stack at position 0 from top (because stack is empty)
    @Test(expected = InterpreterException.class)
    public void nodeNewTestCst1() throws InterpreterException {

        //creation of jjc AST
        NodeNew nodeNew = new NodeNew();
        nodeNew.addChild(new NodeIdent("x@main"));
        nodeNew.addChild(new NodeType(Type.INTEGER));
        nodeNew.addChild(new NodeKind(Kind.CONSTANT));
        nodeNew.addChild(new NodeNumber(0));

        //interpretation
        nodeNew.accept(interpreterVisitor);
    }

    //no corresponding symbol in symbolTable
    @Test(expected = InterpreterException.class)
    public void nodeNewTestCst2() throws InterpreterException, InvalidTypeException {

        memory.push(4);

        //---------------------

        //creation of jjc AST
        NodeNew nodeNew = new NodeNew();
        nodeNew.addChild(new NodeIdent("x@main"));
        nodeNew.addChild(new NodeType(Type.INTEGER));
        nodeNew.addChild(new NodeKind(Kind.CONSTANT));
        nodeNew.addChild(new NodeNumber(0));

        //interpretation
        nodeNew.accept(interpreterVisitor);
    }

    //type don't match with the symbol's type
    @Test(expected = InterpreterException.class)
    public void nodeNewTestCst3() throws InterpreterException, ExistingSymbolException, InvalidTypeException {

        symbolTable.put(new SymbolNode("x@main", Kind.CONSTANT, Type.BOOLEAN));
        memory.push(4);

        //---------------------

        //creation of jjc AST
        NodeNew nodeNew = new NodeNew();
        nodeNew.addChild(new NodeIdent("x@main"));
        nodeNew.addChild(new NodeType(Type.INTEGER));
        nodeNew.addChild(new NodeKind(Kind.CONSTANT));
        nodeNew.addChild(new NodeNumber(0));

        //interpretation
        nodeNew.accept(interpreterVisitor);
    }


    @Test
    public void nodeNewTestCst4() throws InterpreterException, StackException, ExistingSymbolException, InvalidTypeException {

        symbolTable.put(new SymbolNode("x@main", Kind.CONSTANT, Type.INTEGER));
        memory.push(4);

        //---------------------

        //creation of jjc AST
        NodeNew nodeNew = new NodeNew();
        nodeNew.addChild(new NodeIdent("x@main"));
        nodeNew.addChild(new NodeType(Type.INTEGER));
        nodeNew.addChild(new NodeKind(Kind.CONSTANT));
        nodeNew.addChild(new NodeNumber(0));

        //interpretation
        Object res = nodeNew.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());

        //check stack
        Assert.assertEquals(4, (int) memory.peekValue());
        //zTODO : check more in detail the value node that was added ontop of the stack (problem : no way to get the value node added)
    }

    //new unassigned constant
    @Test
    public void nodeNewTestCst5() throws InterpreterException, StackException, ExistingSymbolException, InvalidTypeException {

        symbolTable.put(new SymbolNode("x@main", Kind.CONSTANT, Type.INTEGER));
        memory.push(null);

        //---------------------

        //creation of jjc AST
        NodeNew nodeNew = new NodeNew();
        nodeNew.addChild(new NodeIdent("x@main"));
        nodeNew.addChild(new NodeType(Type.INTEGER));
        nodeNew.addChild(new NodeKind(Kind.CONSTANT));
        nodeNew.addChild(new NodeNumber(0));

        //interpretation
        Object res = nodeNew.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());

        //check stack
        Assert.assertNull(memory.peekValue());
    }

    @Test
    public void nodeInstrTest() throws InterpreterException, StackException, HeapException {

        //creation of jjc AST
        NodePush nodePush1 = new NodePush();
        NodePush nodePush2 = new NodePush();
        NodePush nodePush3 = new NodePush();

        nodePush1.addChild(new NodeNumber(4));
        nodePush2.addChild(new NodeFalse());
        nodePush3.addChild(new NodeNumber(0));

        NodeInstrs nodeInstrs = new NodeInstrs();
        nodeInstrs.addChild(nodePush1);
        nodeInstrs.addChild(nodePush2);
        nodeInstrs.addChild(nodePush3);
        nodeInstrs.addChild(new NodeJcstop());

        //interpretation
        Object res = nodeInstrs.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(-1, interpreterVisitor.getAdr());

        //check stack
        Assert.assertEquals(0, (int) memory.peekValue());
        memory.pop();
        Assert.assertEquals(false, memory.peekValue());
        memory.pop();
        Assert.assertEquals(4, (int) memory.peekValue());
    }

    @Test
    public void nodeAddTest() throws InterpreterException, StackException, InvalidTypeException, HeapException {

        memory.push(false);
        memory.push(3);
        memory.push(4);

        //-----------------

        //creation of jjc AST
        NodeAdd nodeAdd = new NodeAdd();

        //interpretation
        Object res = nodeAdd.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());

        //check stack
        Assert.assertEquals(7, (int) memory.peekValue());
        memory.pop();
        Assert.assertEquals(false, memory.peekValue());
    }


    //Miss value in stack
    @Test(expected = InterpreterException.class)
    public void nodeAddExceptionTest() throws InterpreterException, StackException, InvalidTypeException, HeapException {

        memory.push(false);
        memory.push(4);

        //-----------------

        //creation of jjc AST
        NodeAdd nodeAdd = new NodeAdd();

        //interpretation
        Object res = nodeAdd.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());

        //check stack
        Assert.assertEquals(7, (int) memory.peekValue());
        memory.pop();
        Assert.assertEquals(false, memory.peekValue());
    }




    //division result is an integer
    @Test
    public void nodeDivTest1() throws InterpreterException, StackException, InvalidTypeException, HeapException {

        memory.push(false);
        memory.push(6);
        memory.push(2);

        //-----------------

        //creation of jjc AST
        NodeDiv nodeDiv = new NodeDiv();

        //interpretation
        Object res = nodeDiv.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());

        //check stack
        Assert.assertEquals(3, (int) memory.peekValue());
        memory.pop();
        Assert.assertEquals(false, memory.peekValue());
    }

    //division result is not an integer
    @Test
    public void nodeDivTest2() throws InterpreterException, StackException, InvalidTypeException, HeapException {

        memory.push(false);
        memory.push(5);
        memory.push(2);

        //-----------------

        //creation of jjc AST
        NodeDiv nodeDiv = new NodeDiv();

        //interpretation
        Object res = nodeDiv.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());

        //check stack
        Assert.assertEquals(2, (int) memory.peekValue());
        memory.pop();
        Assert.assertEquals(false, memory.peekValue());
    }

    //division by 0
    @Test(expected = InterpreterException.class)
    public void nodeDivTest3() throws InterpreterException, InvalidTypeException {

        memory.push(false);
        memory.push(5);
        memory.push(0);

        //-----------------

        //creation of jjc AST
        NodeDiv nodeDiv = new NodeDiv();

        //interpretation
        nodeDiv.accept(interpreterVisitor);
    }

    @Test
    public void nodeMulTest() throws InterpreterException, StackException, InvalidTypeException, HeapException {

        memory.push(false);
        memory.push(6);
        memory.push(2);

        //-----------------

        //creation of jjc AST
        NodeMul nodeMul = new NodeMul();

        //interpretation
        Object res = nodeMul.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());

        //check stack
        Assert.assertEquals(12, (int) memory.peekValue());
        memory.pop();
        Assert.assertEquals(false, memory.peekValue());
    }

    //Missing a value in stack
    @Test(expected = InterpreterException.class)
    public void nodeMulExceptionTest() throws InterpreterException,InvalidTypeException{

        memory.push(2);

        //creation of jjc AST
        NodeMul nodeMul = new NodeMul();

        //interpretation
        nodeMul.accept(interpreterVisitor);

    }

    //second operand is positive
    @Test
    public void nodeSubTest1() throws InterpreterException, StackException, InvalidTypeException, HeapException {

        memory.push(false);
        memory.push(6);
        memory.push(2);

        //-----------------

        //creation of jjc AST
        NodeSub nodeSub = new NodeSub();

        //interpretation
        Object res = nodeSub.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());

        //check stack
        Assert.assertEquals(4, (int) memory.peekValue());
        memory.pop();
        Assert.assertEquals(false, memory.peekValue());
    }

    //second operand is negative
    @Test
    public void nodeSubTest2() throws InterpreterException, StackException, InvalidTypeException, HeapException {

        memory.push(false);
        memory.push(6);
        memory.push(-2);

        //-----------------

        //creation of jjc AST
        NodeSub nodeSub = new NodeSub();

        //interpretation
        Object res = nodeSub.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());

        //check stack
        Assert.assertEquals(8, (int) memory.peekValue());
        memory.pop();
        Assert.assertEquals(false, memory.peekValue());
    }

    //missing value on stack
    @Test(expected = InterpreterException.class)
    public void nodeSubExceptionTest() throws InterpreterException,InvalidTypeException{


        memory.push(6);


        //creation of jjc AST
        NodeSub nodeSub = new NodeSub();

        //interpretation
        nodeSub.accept(interpreterVisitor);
    }

    //on positive integer
    @Test
    public void nodeNegTest1() throws InterpreterException, StackException, InvalidTypeException, HeapException {

        memory.push(false);
        memory.push(6);

        //-----------------

        //creation of jjc AST
        NodeNeg nodeNeg = new NodeNeg();

        //interpretation
        Object res = nodeNeg.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());

        //check stack
        Assert.assertEquals(-6, (int) memory.peekValue());
        memory.pop();
        Assert.assertEquals(false, memory.peekValue());
    }

    //on negative integer
    @Test
    public void nodeNegTest2() throws InterpreterException, StackException, InvalidTypeException, HeapException {

        memory.push(false);
        memory.push(-6);

        //-----------------

        //creation of jjc AST
        NodeNeg nodeNeg = new NodeNeg();

        //interpretation
        Object res = nodeNeg.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());

        //check stack
        Assert.assertEquals(6, (int) memory.peekValue());
        memory.pop();
        Assert.assertEquals(false, memory.peekValue());
    }

    //Empty stack
    @Test(expected = InterpreterException.class)
    public void nodeNegExceptionTest() throws InterpreterException{

        //creation of jjc AST
        NodeNeg nodeNeg = new NodeNeg();

        //interpretation
        nodeNeg.accept(interpreterVisitor);

    }

    //variable x has not been declared before
    @Test(expected = InterpreterException.class)
    public void nodeIncTest1() throws InterpreterException, InvalidTypeException, ExistingSymbolException {

        symbolTable.put(new SymbolNode("x@main", Kind.VARIABLE, Type.INTEGER));

        memory.push(false);
        memory.push(3);

        //-----------------

        //creation of jjc AST
        NodeInc nodeInc = new NodeInc();
        nodeInc.addChild(new NodeIdent("x@main"));

        //interpretation
        nodeInc.accept(interpreterVisitor);
    }

    //variable x has not been initialized
    @Test(expected = InterpreterException.class)
    public void nodeIncExceptioncTest() throws InterpreterException, InvalidTypeException, ExistingSymbolException,UnknownSymbolException, WrongSymbolException, NotFoundValueException {

        symbolTable.put(new SymbolNode("x@main", Kind.VARIABLE, Type.INTEGER));
        memory.push(null);
        memory.identVal("x@main", 0);


        memory.push(2);

        //creation of jjc AST
        NodeInc nodeInc = new NodeInc();
        nodeInc.addChild(new NodeIdent("x@main"));

        //interpretation
        nodeInc.accept(interpreterVisitor);
    }

    @Test
    public void nodeIncTest2() throws InterpreterException, StackException, InvalidTypeException, ExistingSymbolException, UnknownSymbolException, WrongSymbolException, HeapException {

        symbolTable.put(new SymbolNode("x@main", Kind.VARIABLE, Type.INTEGER));
        memory.push(2);
        memory.identVal("x@main", 0);

        memory.push(false);
        memory.push(3);

        //-----------------

        //creation of jjc AST
        NodeInc nodeInc = new NodeInc();
        nodeInc.addChild(new NodeIdent("x@main"));

        //interpretation
        Object res = nodeInc.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());

        //check stack
        Assert.assertEquals(false, memory.peekValue());
        memory.pop();
        Assert.assertEquals(5, (int) memory.peekValue());
    }

     //variable x has not been declared before
    @Test(expected = InterpreterException.class)
    public void nodeLoadTest1() throws InterpreterException, InvalidTypeException, ExistingSymbolException {

        symbolTable.put(new SymbolNode("x@main", Kind.VARIABLE, Type.INTEGER));

        memory.push(false);
        memory.push(3);
        //-----------------

        //creation of jjc AST
        NodeLoad nodeLoad = new NodeLoad();
        nodeLoad.addChild(new NodeIdent("x@main"));

        //interpretation
        nodeLoad.accept(interpreterVisitor);
    }

    //variable x has not been initialized before
    @Test(expected = InterpreterException.class)
    public void nodeLoadExceptionTest() throws InterpreterException, InvalidTypeException, ExistingSymbolException, NotFoundValueException, UnknownSymbolException, WrongSymbolException {

        symbolTable.put(new SymbolNode("x@main", Kind.VARIABLE, Type.INTEGER));

        memory.push(null);
        memory.identVal("x@main", 0);

        //creation of jjc AST
        NodeLoad nodeLoad = new NodeLoad();
        nodeLoad.addChild(new NodeIdent("x@main"));

        //interpretation
        nodeLoad.accept(interpreterVisitor);
    }

    @Test
    public void nodeLoadTest2() throws InterpreterException, StackException, InvalidTypeException, ExistingSymbolException, UnknownSymbolException, WrongSymbolException, HeapException {

        symbolTable.put(new SymbolNode("x@main", Kind.VARIABLE, Type.INTEGER));
        memory.push(2);
        memory.identVal("x@main", 0);

        memory.push(false);

        //-----------------

        //creation of jjc AST
        NodeLoad nodeLoad = new NodeLoad();
        nodeLoad.addChild(new NodeIdent("x@main"));

        //interpretation
        Object res = nodeLoad.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());

        //check stack
        Assert.assertEquals(2, (int) memory.peekValue());
        memory.pop();
        Assert.assertEquals(false, memory.peekValue());
    }

    //x@main has not been declared
    @Test(expected = InterpreterException.class)
    public void nodeStoreTest1() throws InterpreterException, InvalidTypeException, ExistingSymbolException {

        symbolTable.put(new SymbolNode("x@main", Kind.VARIABLE, Type.INTEGER));

        memory.push(false);

        memory.push(47);

        //-----------------

        //creation of jjc AST
        NodeStore nodeStore = new NodeStore();
        nodeStore.addChild(new NodeIdent("x@main"));

        //interpretation
        nodeStore.accept(interpreterVisitor);
    }

    //store boolean in integer
    @Test(expected = InterpreterException.class)
    public void nodeStoreTest2() throws InterpreterException, InvalidTypeException, ExistingSymbolException, UnknownSymbolException, WrongSymbolException, NotFoundValueException {

        symbolTable.put(new SymbolNode("x@main", Kind.VARIABLE, Type.INTEGER));
        memory.push(2);
        memory.identVal("x@main", 0);

        memory.push(false);

        memory.push(true);

        //-----------------

        //creation of jjc AST
        NodeStore nodeStore = new NodeStore();
        nodeStore.addChild(new NodeIdent("x@main"));

        //interpretation
        nodeStore.accept(interpreterVisitor);
    }

    @Test
    public void nodeStoreTest3() throws InterpreterException, StackException, InvalidTypeException, ExistingSymbolException, UnknownSymbolException, WrongSymbolException, HeapException {

        symbolTable.put(new SymbolNode("x@main", Kind.VARIABLE, Type.INTEGER));
        memory.push(2);
        memory.identVal("x@main", 0);

        memory.push(false);

        memory.push(47);

        //-----------------

        //creation of jjc AST
        NodeStore nodeStore = new NodeStore();
        nodeStore.addChild(new NodeIdent("x@main"));

        //interpretation
        Object res = nodeStore.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());

        //check stack
        Assert.assertEquals(false, memory.peekValue());
        memory.pop();
        Assert.assertEquals(47, (int) memory.peekValue());
        //TODO : check more in detail the stack top is x@main with value 47
    }

    //store in unassigned constant
    @Test
    public void nodeStoreTest4() throws InterpreterException, StackException, InvalidTypeException, ExistingSymbolException, UnknownSymbolException, WrongSymbolException, HeapException {

        symbolTable.put(new SymbolNode("x@main", Kind.CONSTANT, Type.INTEGER));
        memory.push(null);
        memory.identVal("x@main", 0);

        memory.push(false);

        memory.push(47);

        //-----------------

        //creation of jjc AST
        NodeStore nodeStore = new NodeStore();
        nodeStore.addChild(new NodeIdent("x@main"));

        //interpretation
        Object res = nodeStore.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());

        //check stack
        Assert.assertEquals(false, memory.peekValue());
        memory.pop();
        Assert.assertEquals(47, (int) memory.peekValue());
        //TODO : check more in detail the stack top is x@main with value 47 of kind constant
    }

    //store in assigned constant
    @Test(expected = InterpreterException.class)
    public void nodeStoreTest5() throws InterpreterException, StackException, InvalidTypeException, ExistingSymbolException, UnknownSymbolException, WrongSymbolException {

        symbolTable.put(new SymbolNode("x@main", Kind.CONSTANT, Type.INTEGER));
        memory.push(2);
        memory.identVal("x@main", 0);

        memory.push(false);

        memory.push(47);

        //-----------------

        //creation of jjc AST
        NodeStore nodeStore = new NodeStore();
        nodeStore.addChild(new NodeIdent("x@main"));

        //interpretation
        nodeStore.accept(interpreterVisitor);
    }

    // false and true
    @Test
    public void nodeAndTest1() throws InterpreterException, StackException, InvalidTypeException, HeapException {

        memory.push(3);
        memory.push(false);
        memory.push(true);

        //-----------------

        //creation of jjc AST
        NodeAnd nodeAnd = new NodeAnd();

        //interpretation
        Object res = nodeAnd.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());

        //check stack
        Assert.assertEquals(false, memory.peekValue());
        memory.pop();
        Assert.assertEquals(3, (int) memory.peekValue());
    }

    //true and true
    @Test
    public void nodeAndTest2() throws InterpreterException, StackException, InvalidTypeException, HeapException {

        memory.push(3);
        memory.push(true);
        memory.push(true);

        //-----------------

        //creation of jjc AST
        NodeAnd nodeAnd = new NodeAnd();

        //interpretation
        Object res = nodeAnd.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());

        //check stack
        Assert.assertEquals(true, memory.peekValue());
        memory.pop();
        Assert.assertEquals(3, (int) memory.peekValue());
    }

    @Test(expected = InterpreterException.class)
    public void nodeAndExceptionTest() throws InterpreterException, InvalidTypeException {

        memory.push(3);
        memory.push(true);

        //-----------------

        //creation of jjc AST
        NodeAnd nodeAnd = new NodeAnd();

        //interpretation
        nodeAnd.accept(interpreterVisitor);

    }

    //false or true
    @Test
    public void nodeOrTest1() throws InterpreterException, StackException, InvalidTypeException, HeapException {

        memory.push(3);
        memory.push(false);
        memory.push(true);

        //-----------------

        //creation of jjc AST
        NodeOr nodeOr = new NodeOr();

        //interpretation
        Object res = nodeOr.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());

        //check stack
        Assert.assertEquals(true, memory.peekValue());
        memory.pop();
        Assert.assertEquals(3, (int) memory.peekValue());
    }

    //false or false
    @Test
    public void nodeOrTest2() throws InterpreterException, StackException, InvalidTypeException, HeapException {

        memory.push(3);
        memory.push(false);
        memory.push(false);

        //-----------------

        //creation of jjc AST
        NodeOr nodeOr = new NodeOr();

        //interpretation
        Object res = nodeOr.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());

        //check stack
        Assert.assertEquals(false, memory.peekValue());
        memory.pop();
        Assert.assertEquals(3, (int) memory.peekValue());
    }

    //Missing boolean value on stack
    @Test(expected = InterpreterException.class)
    public void nodeOrExceptionTest() throws InterpreterException,InvalidTypeException{

        memory.push(3);
        memory.push(false);

        //-----------------

        //creation of jjc AST
        NodeOr nodeOr = new NodeOr();

        //interpretation
        nodeOr.accept(interpreterVisitor);

    }

    //not false
    @Test
    public void nodeNotTest1() throws InterpreterException, StackException, InvalidTypeException, HeapException {

        memory.push(3);
        memory.push(false);

        //-----------------

        //creation of jjc AST
        NodeNot nodeNot = new NodeNot();

        //interpretation
        Object res = nodeNot.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());

        //check stack
        Assert.assertEquals(true, memory.peekValue());
        memory.pop();
        Assert.assertEquals(3, (int) memory.peekValue());
    }

    //no true
    @Test
    public void nodeNotTest2() throws InterpreterException, StackException, InvalidTypeException, HeapException {

        memory.push(3);
        memory.push(true);

        //-----------------

        //creation of jjc AST
        NodeNot nodeNot = new NodeNot();

        //interpretation
        Object res = nodeNot.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());

        //check stack
        Assert.assertEquals(false, memory.peekValue());
        memory.pop();
        Assert.assertEquals(3, (int) memory.peekValue());
    }

    //Missing value in stack
    @Test(expected = InterpreterException.class)
    public void nodeNotExceptionTest() throws InterpreterException{

        //creation of jjc AST
        NodeNot nodeNot = new NodeNot();

        //interpretation
        nodeNot.accept(interpreterVisitor);
    }

    @Test
    public void nodeCmpTestNumber() throws InterpreterException, StackException, InvalidTypeException, HeapException {

        memory.push(0);
        memory.push(4);
        memory.push(4);

        //-----------------

        //creation of jjc AST
        NodeCmp nodeCmp = new NodeCmp();

        //interpretation
        Object res = nodeCmp.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());

        //check stack
        Assert.assertEquals(true, memory.peekValue());
        memory.pop();
        Assert.assertEquals(0, (int) memory.peekValue());
    }

    @Test
    public void nodeCmpTestBoolean() throws InterpreterException, StackException, InvalidTypeException, HeapException {

        memory.push(3);
        memory.push(true);
        memory.push(false);

        //-----------------

        //creation of jjc AST
        NodeCmp nodeCmp = new NodeCmp();

        //interpretation
        Object res = nodeCmp.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());

        //check stack
        Assert.assertEquals(false, memory.peekValue());
        memory.pop();
        Assert.assertEquals(3, (int) memory.peekValue());
    }

    @Test(expected = InterpreterException.class)
    public void nodeCmpExceptionTest() throws InterpreterException, InvalidTypeException {

        memory.push(0);


        //-----------------

        //creation of jjc AST
        NodeCmp nodeCmp = new NodeCmp();

        //interpretation
        nodeCmp.accept(interpreterVisitor);

    }

    //first operand < second operand
    @Test
    public void nodeSupTest1() throws InterpreterException, StackException, InvalidTypeException, HeapException {

        memory.push(0);
        memory.push(7);
        memory.push(8);

        //-----------------

        //creation of jjc AST
        NodeSup nodeSup = new NodeSup();

        //interpretation
        Object res = nodeSup.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());

        //check stack
        Assert.assertEquals(false, memory.peekValue());
        memory.pop();
        Assert.assertEquals(0, (int) memory.peekValue());
    }

    //first operand == second operand
    @Test
    public void nodeSupTest2() throws InterpreterException, StackException, InvalidTypeException, HeapException {

        memory.push(0);
        memory.push(7);
        memory.push(7);

        //-----------------

        //creation of jjc AST
        NodeSup nodeSup = new NodeSup();

        //interpretation
        Object res = nodeSup.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());

        //check stack
        Assert.assertEquals(false, memory.peekValue());
        memory.pop();
        Assert.assertEquals(0, (int) memory.peekValue());
    }

    //first operand > second operand
    @Test
    public void nodeSupTest3() throws InterpreterException, StackException, InvalidTypeException, HeapException {

        memory.push(0);
        memory.push(8);
        memory.push(7);

        //-----------------

        //creation of jjc AST
        NodeSup nodeSup = new NodeSup();

        //interpretation
        Object res = nodeSup.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());

        //check stack
        Assert.assertEquals(true, memory.peekValue());
        memory.pop();
        Assert.assertEquals(0, (int) memory.peekValue());
    }

    //Missing value on stack
    @Test(expected = InterpreterException.class)
    public void nodeSupExceptionTest() throws InterpreterException,InvalidTypeException{

        memory.push(7);

        //creation of jjc AST
        NodeSup nodeSup = new NodeSup();

        //interpretation
         nodeSup.accept(interpreterVisitor);

    }


    @Test
    public void nodeGotoTest() throws InterpreterException {

        //creation of jjc AST
        NodeGoto nodeGoto = new NodeGoto();
        nodeGoto.addChild(new NodeNumber(23));

        //interpretation
        Object res = nodeGoto.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(23, interpreterVisitor.getAdr());

    }

    @Test
    public void nodeIfTestTrue() throws InterpreterException, InvalidTypeException {

        memory.push(true);

        //-----------------

        //creation of jjc AST
        NodeIf nodeIf = new NodeIf();
        nodeIf.addChild(new NodeNumber(23));

        //interpretation
        Object res = nodeIf.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(23, interpreterVisitor.getAdr());
    }

    @Test
    public void nodeIfTestFalse() throws InterpreterException, InvalidTypeException {

        memory.push(false);

        //-----------------

        //creation of jjc AST
        NodeIf nodeIf = new NodeIf();
        nodeIf.addChild(new NodeNumber(23));

        //interpretation
        Object res = nodeIf.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());
    }

    @Test(expected = InterpreterException.class)
    public void nodeIfExceptionTest() throws InterpreterException{

        //-----------------

        //creation of jjc AST
        NodeIf nodeIf = new NodeIf();
        nodeIf.addChild(new NodeNumber(23));

        //interpretation
        nodeIf.accept(interpreterVisitor);

    }

    @Test
    public void nodeAincTest() throws InterpreterException, InvalidTypeException, ExistingSymbolException, HeapException, WrongKindException, UnknownSymbolException, StackException, ArrayAccessException {


        symbolTable.put(new SymbolNode("tab@main", Kind.ARRAY, Type.INTEGER));

        memory.declArray("tab@main", 3);

        memory.push(false);
        memory.push(2);
        memory.push(1);


        //creation of jjc AST
        NodeAinc nodeAinc = new NodeAinc();
        NodeIdent nodeIdent = new NodeIdent("tab@main");

        nodeAinc.addChild(nodeIdent);

        //interpretation
        Object res = nodeAinc.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());

        //Check peek
        Assert.assertEquals(false, memory.peekValue());

        int value = memory.getValueArray("tab@main", 2);

        //Check heap
        Assert.assertEquals(1, value);

    }

    @Test(expected = InterpreterException.class)
    public void nodeAincExceptionTest() throws InterpreterException, InvalidTypeException, ExistingSymbolException, HeapException, WrongKindException, UnknownSymbolException {


        symbolTable.put(new SymbolNode("tab@main", Kind.ARRAY, Type.INTEGER));

        memory.declArray("tab@main", 3);

        memory.push(false);
        memory.push(2);


        //creation of jjc AST
        NodeAinc nodeAinc = new NodeAinc();
        NodeIdent nodeIdent = new NodeIdent("tab@main");

        nodeAinc.addChild(nodeIdent);

        //interpretation
        nodeAinc.accept(interpreterVisitor);


    }

    @Test
    public void nodeNewArrayTest() throws ExistingSymbolException, HeapException, InvalidTypeException, InterpreterException, StackException, ArrayAccessException, UnknownSymbolException {

        int value;

        symbolTable.put(new SymbolNode("tab@main", Kind.ARRAY, Type.INTEGER));

        memory.push(false);
        memory.push(2);

        //Creation of jjc AST
        NodeNewArray nodeNewArray = new NodeNewArray();
        NodeIdent nodeIdent = new NodeIdent("tab@main");
        NodeType nodeInteger = new NodeType(Type.INTEGER);

        nodeNewArray.addChild(nodeIdent);
        nodeNewArray.addChild(nodeInteger);

        Object res = nodeNewArray.accept(interpreterVisitor);


        Assert.assertNull(res);


        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());

        value = memory.getValueArray("tab@main", 0);

        Assert.assertEquals(0, value);

        value = memory.getValueArray("tab@main", 1);

        Assert.assertEquals(0, value);


        //Check peek
        Assert.assertEquals(0, (int) memory.peekValue());

    }

    @Test(expected = InterpreterException.class)
    public void nodeNewArrayExceptionTest() throws ExistingSymbolException, InterpreterException{


        symbolTable.put(new SymbolNode("tab@main", Kind.ARRAY, Type.INTEGER));

        //Creation of jjc AST
        NodeNewArray nodeNewArray = new NodeNewArray();
        NodeIdent nodeIdent = new NodeIdent("tab@main");
        NodeType nodeInteger = new NodeType(Type.INTEGER);

        nodeNewArray.addChild(nodeIdent);
        nodeNewArray.addChild(nodeInteger);

        nodeNewArray.accept(interpreterVisitor);

    }

    @Test
    public void nodeNewMTest() throws InterpreterException, InvalidTypeException, ExistingSymbolException, UnknownSymbolException, StackException {
        int value;

        symbolTable.put(new SymbolNode("inc@int", Kind.METHOD, Type.INTEGER));

        memory.push(false);
        memory.push(2);

        //Creation of jjc AST
        NodeNew nodeNewMethode = new NodeNew();

        nodeNewMethode.addChild(new NodeIdent("inc@int"));
        nodeNewMethode.addChild(new NodeType(Type.INTEGER));
        nodeNewMethode.addChild(new NodeKind(Kind.METHOD));
        nodeNewMethode.addChild(new NodeNumber(0));


        Object res = nodeNewMethode.accept(interpreterVisitor);


        Assert.assertNull(res);


        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());

        value = memory.getValue("inc@int");


        Assert.assertEquals(2, value);


        //Check peek
        Assert.assertEquals(2, (int) memory.peekValue());

    }

    @Test(expected = InterpreterException.class)
    public void nodeNewMExceptionTest() throws InterpreterException, InvalidTypeException, ExistingSymbolException {

        symbolTable.put(new SymbolNode("inc@int", Kind.METHOD, Type.INTEGER));

        memory.push(false);

        //Creation of jjc AST
        NodeNew nodeNewMethode = new NodeNew();

        nodeNewMethode.addChild(new NodeIdent("inc@int"));
        nodeNewMethode.addChild(new NodeType(Type.INTEGER));
        nodeNewMethode.addChild(new NodeKind(Kind.METHOD));
        nodeNewMethode.addChild(new NodeNumber(0));


        nodeNewMethode.accept(interpreterVisitor);
    }

    @Test
    public void nodeInvokeTest() throws InvalidTypeException, StackException, ExistingSymbolException, UnknownSymbolException, WrongKindException {

        symbolTable.put(new SymbolNode("test@int", Kind.METHOD, Type.INTEGER));

        memory.declMethod("test@int", 1);


        //Creation of jjc AST
        NodeInvoke nodeInvoke = new NodeInvoke();
        NodeIdent nodeIdent = new NodeIdent("test@int");

        nodeInvoke.addChild(nodeIdent);

        Object res = nodeInvoke.accept(interpreterVisitor);

        Assert.assertNull(res);

        //Check Peek Value
        Assert.assertEquals(2, (int) memory.peekValue());

        //Check adress
        Assert.assertEquals(1, interpreterVisitor.getAdr());

    }

    @Test(expected = InterpreterException.class)
    public void nodeInvokeExceptionTest() throws ExistingSymbolException{

        symbolTable.put(new SymbolNode("test@int", Kind.METHOD, Type.INTEGER));


        //Creation of jjc AST
        NodeInvoke nodeInvoke = new NodeInvoke();
        NodeIdent nodeIdent = new NodeIdent("test@int");

        nodeInvoke.addChild(nodeIdent);

        nodeInvoke.accept(interpreterVisitor);

    }

    @Test
    public void nodeReturnTest() throws InvalidTypeException {

        memory.push(4);

        NodeReturn nodeReturn = new NodeReturn();

        Object res = nodeReturn.accept(interpreterVisitor);

        //Check return
        Assert.assertNull(res);

        //Check peek
        Assert.assertEquals(4, interpreterVisitor.getAdr());

    }

    @Test(expected = InterpreterException.class)
    public void nodeReturnExceptionTest() {

        NodeReturn nodeReturn = new NodeReturn();

        nodeReturn.accept(interpreterVisitor);
    }

    @Test
    public void NodeAloadTest() throws ExistingSymbolException, UnknownSymbolException, WrongKindException, InvalidTypeException, HeapException, StackException, ArrayAccessException {
        symbolTable.put(new SymbolNode("tab@main", Kind.ARRAY, Type.INTEGER));

        memory.declArray("tab@main", 3);

        memory.push(false);
        memory.push(2);


        //creation of jjc AST
        NodeAload nodeAload = new NodeAload();
        NodeIdent nodeIdent = new NodeIdent("tab@main");

        nodeAload.addChild(nodeIdent);

        //interpretation
        Object res = nodeAload.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());

        //Check peek
        Assert.assertEquals(0, (int) memory.peekValue());

        int value = memory.getValueArray("tab@main", 2);

        //Check heap
        Assert.assertEquals(0, value);

    }

    @Test(expected = InterpreterException.class)
    public void NodeAloadExceptionTest() throws ExistingSymbolException, UnknownSymbolException, WrongKindException, InvalidTypeException, HeapException {
        symbolTable.put(new SymbolNode("tab@main", Kind.ARRAY, Type.INTEGER));

        memory.declArray("tab@main", 3);

        memory.push(false);


        //creation of jjc AST
        NodeAload nodeAload = new NodeAload();
        NodeIdent nodeIdent = new NodeIdent("tab@main");

        nodeAload.addChild(nodeIdent);

        //interpretation
        nodeAload.accept(interpreterVisitor);

    }

    @Test
    public void NodeAstoreTest() throws ExistingSymbolException, UnknownSymbolException, WrongKindException, InvalidTypeException, HeapException, StackException, ArrayAccessException {
        //integer tab

        symbolTable.put(new SymbolNode("tab@main", Kind.ARRAY, Type.INTEGER));

        memory.declArray("tab@main", 3);

        memory.push(false);
        memory.push(2);
        memory.push(1);


        //creation of jjc AST
        NodeAstore nodeAstore = new NodeAstore();
        NodeIdent nodeIdent = new NodeIdent("tab@main");

        nodeAstore.addChild(nodeIdent);


        //interpretation
        Object res = nodeAstore.accept(interpreterVisitor);


        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());

        //Check peek
        Assert.assertEquals(false, memory.peekValue());

        int value = memory.getValueArray("tab@main", 2);

        //Check heap
        Assert.assertEquals(1, value);
    }

    @Test
    public void NodeAstoreTest2() throws ExistingSymbolException, UnknownSymbolException, WrongKindException, InvalidTypeException, HeapException, StackException, ArrayAccessException {
        //boolean tab

        symbolTable.put(new SymbolNode("tab@main", Kind.ARRAY, Type.BOOLEAN));
        memory.declArray("tab@main", 3);

        memory.push(false);
        memory.push(2);
        memory.push(true);

        //creation of jjc AST
        NodeAstore nodeAstore = new NodeAstore();
        NodeIdent nodeIdent = new NodeIdent("tab@main");

        nodeAstore.addChild(nodeIdent);

        //interpretation
        Object res = nodeAstore.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);

        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());

        //Check peek
        Assert.assertEquals(false, memory.peekValue());

        //Check heap
        Assert.assertTrue(memory.getValueArray("tab@main", 2));
    }

    @Test(expected = InterpreterException.class)
    public void NodeAstoreExceptionTest() throws ExistingSymbolException, UnknownSymbolException, WrongKindException, InvalidTypeException, HeapException {
        //integer tab

        symbolTable.put(new SymbolNode("tab@main", Kind.ARRAY, Type.INTEGER));

        memory.declArray("tab@main", 3);

        memory.push(false);
        memory.push(1);


        //creation of jjc AST
        NodeAstore nodeAstore = new NodeAstore();
        NodeIdent nodeIdent = new NodeIdent("tab@main");

        nodeAstore.addChild(nodeIdent);


        //interpretation
        nodeAstore.accept(interpreterVisitor);

    }

    @Test
    public void NodeNopTest() {

        NodeNop nodeNop = new NodeNop();

        Object res = nodeNop.accept(interpreterVisitor);

        //check return
        Assert.assertNull(res);


        //check adr
        Assert.assertEquals(2, interpreterVisitor.getAdr());


    }




}
