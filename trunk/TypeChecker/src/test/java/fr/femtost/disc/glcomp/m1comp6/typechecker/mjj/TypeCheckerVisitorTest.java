package fr.femtost.disc.glcomp.m1comp6.typechecker.mjj;

import fr.femtost.disc.glcomp.m1comp6.ast.common.Kind;
import fr.femtost.disc.glcomp.m1comp6.ast.common.Type;
import fr.femtost.disc.glcomp.m1comp6.ast.mjj.*;
import fr.femtost.disc.glcomp.m1comp6.memory.SymbolNode;
import fr.femtost.disc.glcomp.m1comp6.memory.SymbolTable;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TypeCheckerVisitorTest {
    private TypeCheckerVisitor typeCheckerVisitor;
    private SymbolTable symbolTable;

    @Before
    public void initialize() {
        typeCheckerVisitor = new TypeCheckerVisitor();
        symbolTable = new SymbolTable();

        typeCheckerVisitor.setSymbolTable(symbolTable);
    }

    @Test
    public void nodeTrueVisitorTest() throws Exception {
        // Construction of MJJ AST
        NodeTrue nodeTrue = new NodeTrue();

        // Type check
        Type type = typeCheckerVisitor.visit(nodeTrue, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);

        Assert.assertEquals(type, Type.BOOLEAN);
    }

    @Test
    public void nodeFalseVisitorTest() throws Exception {
        // Construction of MJJ AST
        NodeFalse nodeFalse = new NodeFalse();

        // Type check
        Type type = typeCheckerVisitor.visit(nodeFalse, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);

        Assert.assertEquals(type, Type.BOOLEAN);
    }

    @Test
    public void nodeNumberVisitorTest() throws Exception {
        // Construction of MJJ AST
        NodeNumber nodeNumber = new NodeNumber(0);

        // Type check
        Type type = typeCheckerVisitor.visit(nodeNumber, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);

        Assert.assertEquals(type, Type.INTEGER);
    }

    @Test
    public void nodeOmegaVisitorTest() throws Exception {
        // Construction of MJJ AST
        NodeOmega nodeOmega = new NodeOmega();

        // Type check
        Type type = typeCheckerVisitor.visit(nodeOmega, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);

        Assert.assertNull(type);
    }

    @Test
    public void nodeVnilVisitorTest() throws Exception {
        // Construction of MJJ AST
        NodeVnil nodeVnil = new NodeVnil();

        // Type check
        Type type = typeCheckerVisitor.visit(nodeVnil, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);

        Assert.assertNull(type);
    }

    @Test
    public void nodeInilVisitorTest() throws Exception {
        // Construction of MJJ AST
        NodeInil nodeInil = new NodeInil();

        // Type check
        Type type = typeCheckerVisitor.visit(nodeInil, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);

        Assert.assertNull(type);
    }

    @Test
    public void nodeEnilVisitorTest() throws Exception {
        // Construction of MJJ AST
        NodeEnil nodeEnil = new NodeEnil();

        // Type check
        Type type = typeCheckerVisitor.visit(nodeEnil, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);

        Assert.assertNull(type);
    }

    @Test
    public void nodeExnilVisitorTest() throws Exception {
        // Construction of MJJ AST
        NodeExnil nodeExnil = new NodeExnil();

        // Type check
        Type type = typeCheckerVisitor.visit(nodeExnil, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);

        Assert.assertNull(type);
    }

    @Test
    public void nodeExpListVisitorTest() throws Exception {
        // Construction of MJJ AST
        NodeExpList nodeExpList = new NodeExpList();

        // Type check
        Type type = typeCheckerVisitor.visit(nodeExpList, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);

        Assert.assertNull(type);
    }

    @Test
    public void nodeIdentVisitorTest() throws Exception {
        symbolTable.put(new SymbolNode("x@main", Kind.VARIABLE, Type.BOOLEAN));

        // Construction of MJJ AST
        NodeIdent nodeIdent = new NodeIdent("x");

        // Type check
        Type type = typeCheckerVisitor.visit(nodeIdent, TypeChecker.SCOPE_MAIN, null, TypeCheckerMode.VALUE);

        Assert.assertEquals(type, Type.BOOLEAN);
    }

    @Test
    public void nodeIdentVisitorScopeTest() throws Exception {
        symbolTable.put(new SymbolNode("x@global", Kind.VARIABLE, Type.BOOLEAN));

        // Construction of MJJ AST
        NodeIdent nodeIdent = new NodeIdent("x");

        // Type check
        Type type = typeCheckerVisitor.visit(nodeIdent, TypeChecker.SCOPE_MAIN, null, TypeCheckerMode.VALUE);

        Assert.assertEquals(type, Type.BOOLEAN);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeIdentVisitorFailureDeclaredTest() throws Exception {
        // Construction of MJJ AST
        NodeIdent nodeIdent = new NodeIdent("x");

        // Type check
        typeCheckerVisitor.visit(nodeIdent, TypeChecker.SCOPE_MAIN, null, TypeCheckerMode.VALUE);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeIdentVisitorFailTest1() throws Exception {
        symbolTable.put(new SymbolNode("y@main", Kind.VARIABLE, Type.INTEGER));

        // Construction of MJJ AST
        NodeIdent nodeIdent = new NodeIdent("y");

        // Type check
        typeCheckerVisitor.visit(nodeIdent, TypeChecker.SCOPE_MAIN, null, TypeCheckerMode.CONSTANT);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeIdentVisitorFailTest2() throws Exception {
        symbolTable.put(new SymbolNode("y@main", Kind.VARIABLE, Type.INTEGER));

        // Construction of MJJ AST
        NodeIdent nodeIdent = new NodeIdent("y");

        // Type check
        typeCheckerVisitor.visit(nodeIdent, TypeChecker.SCOPE_MAIN, null, TypeCheckerMode.VCONSTANT);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeIdentVisitorFailTest3() throws Exception {
        symbolTable.put(new SymbolNode("y@main", Kind.VARIABLE, Type.INTEGER));

        // Construction of MJJ AST
        NodeIdent nodeIdent = new NodeIdent("y");

        // Type check
        typeCheckerVisitor.visit(nodeIdent, TypeChecker.SCOPE_MAIN, null, TypeCheckerMode.ARRAY);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeIdentVisitorFailTest4() throws Exception {
        symbolTable.put(new SymbolNode("y@main", Kind.VARIABLE, Type.INTEGER));

        // Construction of MJJ AST
        NodeIdent nodeIdent = new NodeIdent("y");

        // Type check
        typeCheckerVisitor.visit(nodeIdent, TypeChecker.SCOPE_MAIN, null, TypeCheckerMode.METHOD);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeIdentVisitorFailTest5() throws Exception {
        symbolTable.put(new SymbolNode("y@main", Kind.ARRAY, Type.INTEGER));

        // Construction of MJJ AST
        NodeIdent nodeIdent = new NodeIdent("y");

        // Type check
        typeCheckerVisitor.visit(nodeIdent, TypeChecker.SCOPE_MAIN, null, TypeCheckerMode.VALUE);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeIdentVisitorFailTest6() throws Exception {
        symbolTable.put(new SymbolNode("y@main", Kind.ARRAY, Type.INTEGER));

        // Construction of MJJ AST
        NodeIdent nodeIdent = new NodeIdent("y");

        // Type check
        typeCheckerVisitor.visit(nodeIdent, TypeChecker.SCOPE_MAIN, null, TypeCheckerMode.VARIABLE);
    }

    @Test
    public void nodeVarVisitorTest() throws Exception {
        // Construction of MJJ AST
        NodeVar nodeVar = new NodeVar();

        nodeVar.addChild(new NodeBoolean());
        nodeVar.addChild(new NodeIdent("y"));
        nodeVar.addChild(new NodeFalse());

        // Type check
        Type type = typeCheckerVisitor.visit(nodeVar, TypeChecker.SCOPE_MAIN, TypeChecker.FIRST_PASS, TypeCheckerMode.ANY);
        SymbolNode symbolNode = symbolTable.get("y@main");

        Assert.assertNull(type);
        Assert.assertNotNull(symbolNode);
        Assert.assertEquals(symbolNode.getKind(), Kind.VARIABLE);
        Assert.assertEquals(symbolNode.getType(), Type.BOOLEAN);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeVarVisitorFailureVoidTest() throws Exception {
        // Construction of MJJ AST
        NodeVar nodeVar = new NodeVar();

        nodeVar.addChild(new NodeVoid());
        nodeVar.addChild(new NodeIdent("y"));
        nodeVar.addChild(new NodeOmega());

        // Type check
        typeCheckerVisitor.visit(nodeVar, TypeChecker.SCOPE_MAIN, TypeChecker.FIRST_PASS, TypeCheckerMode.ANY);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeVarVisitorFailureTypeTest() throws Exception {
        // Construction of MJJ AST
        NodeVar nodeVar = new NodeVar();

        nodeVar.addChild(new NodeInteger());
        nodeVar.addChild(new NodeIdent("y"));
        nodeVar.addChild(new NodeFalse());

        // Type check
        typeCheckerVisitor.visit(nodeVar, TypeChecker.SCOPE_MAIN, TypeChecker.FIRST_PASS, TypeCheckerMode.ANY);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeVarVisitorFailureDeclaredTest() throws Exception {
        symbolTable.put(new SymbolNode("y@main", Kind.VARIABLE, Type.INTEGER));

        // Construction of MJJ AST
        NodeVar nodeVar = new NodeVar();

        nodeVar.addChild(new NodeBoolean());
        nodeVar.addChild(new NodeIdent("y"));
        nodeVar.addChild(new NodeFalse());

        // Type check
        typeCheckerVisitor.visit(nodeVar, TypeChecker.SCOPE_MAIN, TypeChecker.FIRST_PASS, TypeCheckerMode.ANY);
    }

    @Test
    public void nodeCstVisitorTest() throws Exception {
        // Construction of MJJ AST
        NodeCst nodeCst = new NodeCst();

        nodeCst.addChild(new NodeBoolean());
        nodeCst.addChild(new NodeIdent("y"));
        nodeCst.addChild(new NodeFalse());

        // Type check
        Type type = typeCheckerVisitor.visit(nodeCst, TypeChecker.SCOPE_MAIN, TypeChecker.FIRST_PASS, TypeCheckerMode.ANY);
        SymbolNode symbolNode = symbolTable.get("y@main");

        Assert.assertNull(type);
        Assert.assertNotNull(symbolNode);
        Assert.assertEquals(symbolNode.getKind(), Kind.CONSTANT);
        Assert.assertEquals(symbolNode.getType(), Type.BOOLEAN);
    }

    @Test
    public void nodeCstVisitorNotInitTest() throws Exception {
        // Construction of MJJ AST
        NodeCst nodeCst = new NodeCst();

        nodeCst.addChild(new NodeBoolean());
        nodeCst.addChild(new NodeIdent("y"));
        nodeCst.addChild(new NodeOmega());

        // Type check
        Type type = typeCheckerVisitor.visit(nodeCst, TypeChecker.SCOPE_MAIN, TypeChecker.FIRST_PASS, TypeCheckerMode.ANY);
        SymbolNode symbolNode = symbolTable.get("y@main");

        Assert.assertNull(type);
        Assert.assertNotNull(symbolNode);
        Assert.assertEquals(symbolNode.getKind(), Kind.VCONSTANT);
        Assert.assertEquals(symbolNode.getType(), Type.BOOLEAN);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeCstVisitorFailureVoidTest() throws Exception {
        // Construction of MJJ AST
        NodeCst nodeCst = new NodeCst();

        nodeCst.addChild(new NodeVoid());
        nodeCst.addChild(new NodeIdent("y"));
        nodeCst.addChild(new NodeOmega());

        // Type check
        typeCheckerVisitor.visit(nodeCst, TypeChecker.SCOPE_MAIN, TypeChecker.FIRST_PASS, TypeCheckerMode.ANY);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeCstVisitorFailureTypeTest() throws Exception {
        // Construction of MJJ AST
        NodeCst nodeCst = new NodeCst();

        nodeCst.addChild(new NodeInteger());
        nodeCst.addChild(new NodeIdent("y"));
        nodeCst.addChild(new NodeFalse());

        // Type check
        typeCheckerVisitor.visit(nodeCst, TypeChecker.SCOPE_MAIN, TypeChecker.FIRST_PASS, TypeCheckerMode.ANY);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeCstVisitorFailureDeclaredTest() throws Exception {
        symbolTable.put(new SymbolNode("y@main", Kind.VARIABLE, Type.INTEGER));

        // Construction of MJJ AST
        NodeCst nodeCst = new NodeCst();

        nodeCst.addChild(new NodeBoolean());
        nodeCst.addChild(new NodeIdent("y"));
        nodeCst.addChild(new NodeFalse());

        // Type check
        typeCheckerVisitor.visit(nodeCst, TypeChecker.SCOPE_MAIN, TypeChecker.FIRST_PASS, TypeCheckerMode.ANY);
    }

    @Test
    public void nodeInstrsVisitorTest() throws Exception {
        symbolTable.put(new SymbolNode("x@global", Kind.VARIABLE, Type.INTEGER));

        // Construction of MJJ AST
        NodeInstrs nodeInstrs1 = new NodeInstrs();
        NodeInstrs nodeInstrs2 = new NodeInstrs();

        NodeIncrement nodeIncrement = new NodeIncrement();

        nodeIncrement.addChild(new NodeIdent("x"));

        nodeInstrs1.addChild(nodeIncrement);
        nodeInstrs1.addChild(nodeInstrs2);

        NodeAssignment nodeAssignment = new NodeAssignment();

        nodeAssignment.addChild(new NodeIdent("x"));
        nodeAssignment.addChild(new NodeNumber(0));

        nodeInstrs2.addChild(nodeAssignment);
        nodeInstrs2.addChild(new NodeInil());

        // Type check
        Type type = typeCheckerVisitor.visit(nodeInstrs1, TypeChecker.SCOPE_MAIN, TypeChecker.SECOND_PASS, TypeCheckerMode.ANY);

        Assert.assertNull(type);
    }

    @Test
    public void nodeVarsVisitorTest() throws Exception {
        // Construction of MJJ AST
        NodeVars nodeVars1 = new NodeVars();
        NodeVars nodeVars2 = new NodeVars();

        NodeVar nodeVar1 = new NodeVar();

        nodeVar1.addChild(new NodeBoolean());
        nodeVar1.addChild(new NodeIdent("y"));
        nodeVar1.addChild(new NodeFalse());

        NodeVar nodeVar2 = new NodeVar();

        nodeVar2.addChild(new NodeBoolean());
        nodeVar2.addChild(new NodeIdent("x"));
        nodeVar2.addChild(new NodeTrue());

        nodeVars1.addChild(nodeVar1);
        nodeVars1.addChild(nodeVars2);

        nodeVars2.addChild(nodeVar2);
        nodeVars2.addChild(new NodeVnil());

        // Type check
        Type type = typeCheckerVisitor.visit(nodeVars1, TypeChecker.SCOPE_MAIN, TypeChecker.FIRST_PASS, TypeCheckerMode.ANY);

        Assert.assertNull(type);
        Assert.assertNotNull(symbolTable.get("x@main"));
        Assert.assertNotNull(symbolTable.get("y@main"));
    }

    @Test
    public void nodeDeclsVisitorTest() throws Exception {
        // Construction of MJJ AST
        NodeDecls nodeDecls1 = new NodeDecls();
        NodeDecls nodeDecls2 = new NodeDecls();

        NodeVar nodeVar1 = new NodeVar();

        nodeVar1.addChild(new NodeBoolean());
        nodeVar1.addChild(new NodeIdent("x"));
        nodeVar1.addChild(new NodeOmega());

        NodeVar nodeVar2 = new NodeVar();

        nodeVar2.addChild(new NodeInteger());
        nodeVar2.addChild(new NodeIdent("y"));
        nodeVar2.addChild(new NodeNumber(0));

        nodeDecls1.addChild(nodeVar1);
        nodeDecls1.addChild(nodeDecls2);

        nodeDecls2.addChild(nodeVar2);
        nodeDecls2.addChild(new NodeVnil());

        // Type check
        Type type = typeCheckerVisitor.visit(nodeDecls1, TypeChecker.SCOPE_GLOBAL, TypeChecker.FIRST_PASS, TypeCheckerMode.ANY);

        Assert.assertNull(type);
        Assert.assertNotNull(symbolTable.get("x@global"));
        Assert.assertNotNull(symbolTable.get("y@global"));
    }

    @Test
    public void nodeMainVisitorTest() throws Exception {
        // Construction of MJJ AST
        NodeVars nodeVars1 = new NodeVars();
        NodeVars nodeVars2 = new NodeVars();

        NodeVar nodeVar1 = new NodeVar();

        nodeVars1.addChild(nodeVar1);
        nodeVars1.addChild(nodeVars2);

        nodeVar1.addChild(new NodeBoolean());
        nodeVar1.addChild(new NodeIdent("y"));
        nodeVar1.addChild(new NodeFalse());

        NodeVar nodeVar2 = new NodeVar();

        nodeVars2.addChild(nodeVar2);
        nodeVars2.addChild(new NodeVnil());

        nodeVar2.addChild(new NodeBoolean());
        nodeVar2.addChild(new NodeIdent("x"));
        nodeVar2.addChild(new NodeTrue());

        NodeMain nodeMain = new NodeMain();

        nodeMain.addChild(nodeVars1);
        nodeMain.addChild(new NodeInil());

        // Type check
        Type type = typeCheckerVisitor.visit(nodeMain, TypeChecker.SCOPE_GLOBAL, TypeChecker.FIRST_PASS, TypeCheckerMode.ANY);

        Assert.assertNull(type);
        Assert.assertNotNull(symbolTable.get("x@main"));
        Assert.assertNotNull(symbolTable.get("y@main"));
    }

    @Test
    public void nodeClassVisitorTest() throws Exception {
        // Construction of MJJ AST
        NodeMain nodeMain = new NodeMain();

        nodeMain.addChild(new NodeVnil());
        nodeMain.addChild(new NodeInil());

        NodeClass nodeClass = new NodeClass();

        nodeClass.addChild(new NodeIdent("C"));
        nodeClass.addChild(new NodeVnil());
        nodeClass.addChild(nodeMain);

        // Type check
        Type type = typeCheckerVisitor.visit(nodeClass, TypeChecker.SCOPE_GLOBAL, TypeChecker.FIRST_PASS, TypeCheckerMode.ANY);
        SymbolNode symbolNode = symbolTable.get("C@global");

        Assert.assertNull(type);
        Assert.assertNotNull(symbolNode);
        Assert.assertEquals(symbolNode.getKind(), Kind.VARIABLE);
        Assert.assertNull(symbolNode.getType());
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeClassVisitorFailureDeclaredTest() throws Exception {
        symbolTable.put(new SymbolNode("C@global", Kind.VARIABLE, null));

        // Construction of MJJ AST
        NodeMain nodeMain = new NodeMain();

        nodeMain.addChild(new NodeVnil());
        nodeMain.addChild(new NodeInil());

        NodeClass nodeClass = new NodeClass();

        nodeClass.addChild(new NodeIdent("C"));
        nodeClass.addChild(new NodeVnil());
        nodeClass.addChild(nodeMain);

        // Type check
        typeCheckerVisitor.visit(nodeClass, TypeChecker.SCOPE_GLOBAL, TypeChecker.FIRST_PASS, TypeCheckerMode.ANY);
    }

    @Test
    public void nodeVoidVisitorTest() throws Exception {
        // Construction of MJJ AST
        NodeVoid nodeVoid = new NodeVoid();

        // Type check
        Type type = typeCheckerVisitor.visit(nodeVoid, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);

        Assert.assertEquals(type, Type.VOID);
    }

    @Test
    public void nodeIntegerVisitorTest() throws Exception {
        // Construction of MJJ AST
        NodeInteger nodeInteger = new NodeInteger();

        // Type check
        Type type = typeCheckerVisitor.visit(nodeInteger, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);

        Assert.assertEquals(type, Type.INTEGER);
    }

    @Test
    public void nodeBooleanVisitorTest() throws Exception {
        // Construction of MJJ AST
        NodeBoolean nodeBoolean = new NodeBoolean();

        // Type check
        Type type = typeCheckerVisitor.visit(nodeBoolean, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);

        Assert.assertEquals(type, Type.BOOLEAN);
    }

    @Test
    public void nodeAddVisitorTest() throws Exception {
        // Construction of MJJ AST
        NodeAdd nodeAdd = new NodeAdd();

        nodeAdd.addChild(new NodeNumber(0));
        nodeAdd.addChild(new NodeNumber(1));

        // Type check
        Type type = typeCheckerVisitor.visit(nodeAdd, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);

        Assert.assertEquals(type, Type.INTEGER);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeAddVisitorFailureLeftTest() throws Exception {
        // Construction of MJJ AST
        NodeAdd nodeAdd = new NodeAdd();

        nodeAdd.addChild(new NodeTrue());
        nodeAdd.addChild(new NodeNumber(0));

        // Type check
        typeCheckerVisitor.visit(nodeAdd, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeAddVisitorFailureRightTest() throws Exception {
        // Construction of MJJ AST
        NodeAdd nodeAdd = new NodeAdd();

        nodeAdd.addChild(new NodeNumber(0));
        nodeAdd.addChild(new NodeTrue());

        // Type check
        typeCheckerVisitor.visit(nodeAdd, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);
    }

    @Test
    public void nodeSubVisitorTest() throws Exception {
        // Construction of MJJ AST
        NodeSub nodeSub = new NodeSub();

        nodeSub.addChild(new NodeNumber(0));
        nodeSub.addChild(new NodeNumber(1));

        // Type check
        Type type = typeCheckerVisitor.visit(nodeSub, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);

        Assert.assertEquals(type, Type.INTEGER);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeSubVisitorFailureLeftTest() throws Exception {
        // Construction of MJJ AST
        NodeSub nodeSub = new NodeSub();

        nodeSub.addChild(new NodeTrue());
        nodeSub.addChild(new NodeNumber(0));

        // Type check
        typeCheckerVisitor.visit(nodeSub, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeSubVisitorFailureRightTest() throws Exception {
        // Construction of MJJ AST
        NodeSub nodeSub = new NodeSub();

        nodeSub.addChild(new NodeNumber(0));
        nodeSub.addChild(new NodeTrue());

        // Type check
        typeCheckerVisitor.visit(nodeSub, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);
    }

    @Test
    public void nodeMulVisitorTest() throws Exception {
        // Construction of MJJ AST
        NodeMul nodeMul = new NodeMul();

        nodeMul.addChild(new NodeNumber(0));
        nodeMul.addChild(new NodeNumber(1));

        // Type check
        Type type = typeCheckerVisitor.visit(nodeMul, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);

        Assert.assertEquals(type, Type.INTEGER);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeMulVisitorFailureLeftTest() throws Exception {
        // Construction of MJJ AST
        NodeMul nodeMul = new NodeMul();

        nodeMul.addChild(new NodeTrue());
        nodeMul.addChild(new NodeNumber(0));

        // Type check
        typeCheckerVisitor.visit(nodeMul, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeMulVisitorFailureRightTest() throws Exception {
        // Construction of MJJ AST
        NodeMul nodeMul = new NodeMul();

        nodeMul.addChild(new NodeNumber(0));
        nodeMul.addChild(new NodeTrue());

        // Type check
        typeCheckerVisitor.visit(nodeMul, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);
    }

    @Test
    public void nodeDivVisitorTest() throws Exception {
        // Construction of MJJ AST
        NodeDiv nodeDiv = new NodeDiv();

        nodeDiv.addChild(new NodeNumber(0));
        nodeDiv.addChild(new NodeNumber(1));

        // Type check
        Type type = typeCheckerVisitor.visit(nodeDiv, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);

        Assert.assertEquals(type, Type.INTEGER);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeDivVisitorFailureLeftTest() throws Exception {
        // Construction of MJJ AST
        NodeDiv nodeDiv = new NodeDiv();

        nodeDiv.addChild(new NodeTrue());
        nodeDiv.addChild(new NodeNumber(0));

        // Type check
        typeCheckerVisitor.visit(nodeDiv, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeDivVisitorFailureRightTest() throws Exception {
        // Construction of MJJ AST
        NodeDiv nodeDiv = new NodeDiv();

        nodeDiv.addChild(new NodeNumber(0));
        nodeDiv.addChild(new NodeTrue());

        // Type check
        typeCheckerVisitor.visit(nodeDiv, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);
    }

    @Test
    public void nodeNegVisitorTest() throws Exception {
        // Construction of MJJ AST
        NodeNeg nodeNeg = new NodeNeg();

        nodeNeg.addChild(new NodeNumber(1));

        // Type check
        Type type = typeCheckerVisitor.visit(nodeNeg, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);

        Assert.assertEquals(type, Type.INTEGER);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeNegVisitorFailureTest() throws Exception {
        // Construction of MJJ AST
        NodeNeg nodeNeg = new NodeNeg();

        nodeNeg.addChild(new NodeTrue());

        // Type check
        typeCheckerVisitor.visit(nodeNeg, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);
    }

    @Test
    public void nodeSupVisitorTest() throws Exception {
        // Construction of MJJ AST
        NodeSup nodeSup = new NodeSup();

        nodeSup.addChild(new NodeNumber(0));
        nodeSup.addChild(new NodeNumber(1));

        // Type check
        Type type = typeCheckerVisitor.visit(nodeSup, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);

        Assert.assertEquals(type, Type.BOOLEAN);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeSupVisitorFailureLeftTest() throws Exception {
        // Construction of MJJ AST
        NodeSup nodeSup = new NodeSup();

        nodeSup.addChild(new NodeTrue());
        nodeSup.addChild(new NodeNumber(0));

        // Type check
        typeCheckerVisitor.visit(nodeSup, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeSupVisitorFailureRightTest() throws Exception {
        // Construction of MJJ AST
        NodeSup nodeSup = new NodeSup();

        nodeSup.addChild(new NodeNumber(0));
        nodeSup.addChild(new NodeTrue());

        // Type check
        typeCheckerVisitor.visit(nodeSup, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);
    }

    @Test
    public void nodeOrVisitorTest() throws Exception {
        // Construction of MJJ AST
        NodeOr nodeOr = new NodeOr();

        nodeOr.addChild(new NodeTrue());
        nodeOr.addChild(new NodeFalse());

        // Type check
        Type type = typeCheckerVisitor.visit(nodeOr, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);

        Assert.assertEquals(type, Type.BOOLEAN);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeOrVisitorFailureLeftTest() throws Exception {
        // Construction of MJJ AST
        NodeOr nodeOr = new NodeOr();

        nodeOr.addChild(new NodeNumber(0));
        nodeOr.addChild(new NodeTrue());

        // Type check
        typeCheckerVisitor.visit(nodeOr, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeOrVisitorFailureRightTest() throws Exception {
        // Construction of MJJ AST
        NodeOr nodeOr = new NodeOr();

        nodeOr.addChild(new NodeTrue());
        nodeOr.addChild(new NodeNumber(0));

        // Type check
        typeCheckerVisitor.visit(nodeOr, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);
    }

    @Test
    public void nodeAndVisitorTest() throws Exception {
        // Construction of MJJ AST
        NodeAnd nodeAnd = new NodeAnd();

        nodeAnd.addChild(new NodeTrue());
        nodeAnd.addChild(new NodeFalse());

        // Type check
        Type type = typeCheckerVisitor.visit(nodeAnd, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);

        Assert.assertEquals(type, Type.BOOLEAN);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeAndVisitorFailureLeftTest() throws Exception {
        // Construction of MJJ AST
        NodeAnd nodeAnd = new NodeAnd();

        nodeAnd.addChild(new NodeNumber(0));
        nodeAnd.addChild(new NodeTrue());

        // Type check
        typeCheckerVisitor.visit(nodeAnd, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeAndVisitorFailureRightTest() throws Exception {
        // Construction of MJJ AST
        NodeAnd nodeAnd = new NodeAnd();

        nodeAnd.addChild(new NodeTrue());
        nodeAnd.addChild(new NodeNumber(0));

        // Type check
        typeCheckerVisitor.visit(nodeAnd, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);
    }

    @Test
    public void nodeNotVisitorTest() throws Exception {
        // Construction of MJJ AST
        NodeNot nodeNot = new NodeNot();

        nodeNot.addChild(new NodeTrue());

        // Type check
        Type type = typeCheckerVisitor.visit(nodeNot, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);

        Assert.assertEquals(type, Type.BOOLEAN);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeNotVisitorFailureTest() throws Exception {
        // Construction of MJJ AST
        NodeNot nodeNot = new NodeNot();

        nodeNot.addChild(new NodeNumber(0));

        // Type check
        typeCheckerVisitor.visit(nodeNot, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);
    }

    @Test
    public void nodeCmpVisitorTest() throws Exception {
        // Construction of MJJ AST
        NodeCmp nodeCmp = new NodeCmp();

        nodeCmp.addChild(new NodeTrue());
        nodeCmp.addChild(new NodeFalse());

        // Type check
        Type type = typeCheckerVisitor.visit(nodeCmp, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);

        Assert.assertEquals(type, Type.BOOLEAN);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeCmpVisitorFailureTest() throws Exception {
        // Construction of MJJ AST
        NodeCmp nodeCmp = new NodeCmp();

        nodeCmp.addChild(new NodeNumber(0));
        nodeCmp.addChild(new NodeFalse());

        // Type check
        typeCheckerVisitor.visit(nodeCmp, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);
    }

    @Test
    public void nodeSumVisitorTest() throws Exception {
        symbolTable.put(new SymbolNode("x@global", Kind.VARIABLE, Type.INTEGER));

        // Construction of MJJ AST
        NodeSum nodeSum = new NodeSum();

        nodeSum.addChild(new NodeIdent("x"));
        nodeSum.addChild(new NodeNumber(1));

        // Type check
        Type type = typeCheckerVisitor.visit(nodeSum, TypeChecker.SCOPE_MAIN, null, TypeCheckerMode.ANY);

        Assert.assertNull(type);
    }

    @Test
    public void nodeSumVisitorArrayTest() throws Exception {
        symbolTable.put(new SymbolNode("x@global", Kind.ARRAY, Type.INTEGER));

        // Construction of MJJ AST
        NodeSum nodeSum = new NodeSum();
        NodeArrayGet nodeArrayGet = new NodeArrayGet();

        nodeArrayGet.addChild(new NodeIdent("x"));
        nodeArrayGet.addChild(new NodeNumber(0));

        nodeSum.addChild(nodeArrayGet);
        nodeSum.addChild(new NodeNumber(1));

        // Type check
        Type type = typeCheckerVisitor.visit(nodeSum, TypeChecker.SCOPE_MAIN, null, TypeCheckerMode.ANY);

        Assert.assertNull(type);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeSumVisitorFailureDeclaredTest() throws Exception {
        // Construction of MJJ AST
        NodeSum nodeSum = new NodeSum();

        nodeSum.addChild(new NodeIdent("x"));
        nodeSum.addChild(new NodeNumber(1));

        // Type check
        typeCheckerVisitor.visit(nodeSum, TypeChecker.SCOPE_MAIN, null, TypeCheckerMode.ANY);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeSumVisitorFailureConstantTest() throws Exception {
        symbolTable.put(new SymbolNode("x@global", Kind.CONSTANT, Type.INTEGER));

        // Construction of MJJ AST
        NodeSum nodeSum = new NodeSum();

        nodeSum.addChild(new NodeIdent("x"));
        nodeSum.addChild(new NodeNumber(1));

        // Type check
        typeCheckerVisitor.visit(nodeSum, TypeChecker.SCOPE_MAIN, null, TypeCheckerMode.ANY);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeSumVisitorFailureVConstantTest() throws Exception {
        symbolTable.put(new SymbolNode("x@global", Kind.VCONSTANT, Type.INTEGER));

        // Construction of MJJ AST
        NodeSum nodeSum = new NodeSum();

        nodeSum.addChild(new NodeIdent("x"));
        nodeSum.addChild(new NodeNumber(1));

        // Type check
        typeCheckerVisitor.visit(nodeSum, TypeChecker.SCOPE_MAIN, null, TypeCheckerMode.ANY);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeSumVisitorFailureTypeTest() throws Exception {
        symbolTable.put(new SymbolNode("x@global", Kind.VARIABLE, Type.BOOLEAN));

        // Construction of MJJ AST
        NodeSum nodeSum = new NodeSum();

        nodeSum.addChild(new NodeIdent("x"));
        nodeSum.addChild(new NodeNumber(1));

        // Type check
        typeCheckerVisitor.visit(nodeSum, TypeChecker.SCOPE_MAIN, null, TypeCheckerMode.ANY);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeSumVisitorFailureExpTypeTest() throws Exception {
        symbolTable.put(new SymbolNode("x@global", Kind.VARIABLE, Type.INTEGER));

        // Construction of MJJ AST
        NodeSum nodeSum = new NodeSum();

        nodeSum.addChild(new NodeIdent("x"));
        nodeSum.addChild(new NodeTrue());

        // Type check
        typeCheckerVisitor.visit(nodeSum, TypeChecker.SCOPE_MAIN, null, TypeCheckerMode.ANY);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeSumVisitorArrayFailureTypeTest() throws Exception {
        symbolTable.put(new SymbolNode("x@global", Kind.ARRAY, Type.BOOLEAN));

        // Construction of MJJ AST
        NodeSum nodeSum = new NodeSum();
        NodeArrayGet nodeArrayGet = new NodeArrayGet();

        nodeArrayGet.addChild(new NodeIdent("x"));
        nodeArrayGet.addChild(new NodeNumber(0));

        nodeSum.addChild(nodeArrayGet);
        nodeSum.addChild(new NodeNumber(1));

        // Type check
        typeCheckerVisitor.visit(nodeSum, TypeChecker.SCOPE_MAIN, null, TypeCheckerMode.ANY);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeSumVisitorArrayFailureExpTypeTest() throws Exception {
        symbolTable.put(new SymbolNode("x@global", Kind.ARRAY, Type.INTEGER));

        // Construction of MJJ AST
        NodeSum nodeSum = new NodeSum();
        NodeArrayGet nodeArrayGet = new NodeArrayGet();

        nodeArrayGet.addChild(new NodeIdent("x"));
        nodeArrayGet.addChild(new NodeNumber(0));

        nodeSum.addChild(nodeArrayGet);
        nodeSum.addChild(new NodeTrue());

        // Type check
        typeCheckerVisitor.visit(nodeSum, TypeChecker.SCOPE_MAIN, null, TypeCheckerMode.ANY);
    }

    @Test
    public void nodeIncrementVisitorTest() throws Exception {
        symbolTable.put(new SymbolNode("x@global", Kind.VARIABLE, Type.INTEGER));

        // Construction of MJJ AST
        NodeIncrement nodeIncrement = new NodeIncrement();

        nodeIncrement.addChild(new NodeIdent("x"));

        // Type check
        Type type = typeCheckerVisitor.visit(nodeIncrement, TypeChecker.SCOPE_MAIN, null, TypeCheckerMode.ANY);

        Assert.assertNull(type);
    }

    @Test
    public void nodeIncrementVisitorArrayTest() throws Exception {
        symbolTable.put(new SymbolNode("x@global", Kind.ARRAY, Type.INTEGER));

        // Construction of MJJ AST
        NodeIncrement nodeIncrement = new NodeIncrement();
        NodeArrayGet nodeArrayGet = new NodeArrayGet();

        nodeArrayGet.addChild(new NodeIdent("x"));
        nodeArrayGet.addChild(new NodeNumber(0));

        nodeIncrement.addChild(nodeArrayGet);

        // Type check
        Type type = typeCheckerVisitor.visit(nodeIncrement, TypeChecker.SCOPE_MAIN, null, TypeCheckerMode.ANY);

        Assert.assertNull(type);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeIncrementVisitorFailureDeclaredTest() throws Exception {
        // Construction of MJJ AST
        NodeIncrement nodeIncrement = new NodeIncrement();

        nodeIncrement.addChild(new NodeIdent("x"));

        // Type check
        Type type = typeCheckerVisitor.visit(nodeIncrement, TypeChecker.SCOPE_MAIN, null, TypeCheckerMode.ANY);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeIncrementVisitorFailureConstantTest() throws Exception {
        symbolTable.put(new SymbolNode("x@global", Kind.CONSTANT, Type.INTEGER));

        // Construction of MJJ AST
        NodeIncrement nodeIncrement = new NodeIncrement();

        nodeIncrement.addChild(new NodeIdent("x"));

        // Type check
        Type type = typeCheckerVisitor.visit(nodeIncrement, TypeChecker.SCOPE_MAIN, null, TypeCheckerMode.ANY);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeIncrementVisitorFailureVConstantTest() throws Exception {
        symbolTable.put(new SymbolNode("x@global", Kind.VCONSTANT, Type.INTEGER));

        // Construction of MJJ AST
        NodeIncrement nodeIncrement = new NodeIncrement();

        nodeIncrement.addChild(new NodeIdent("x"));

        // Type check
        Type type = typeCheckerVisitor.visit(nodeIncrement, TypeChecker.SCOPE_MAIN, null, TypeCheckerMode.ANY);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeIncrementVisitorFailureTypeTest() throws Exception {
        symbolTable.put(new SymbolNode("x@global", Kind.VARIABLE, Type.BOOLEAN));

        // Construction of MJJ AST
        NodeIncrement nodeIncrement = new NodeIncrement();

        nodeIncrement.addChild(new NodeIdent("x"));

        // Type check
        Type type = typeCheckerVisitor.visit(nodeIncrement, TypeChecker.SCOPE_MAIN, null, TypeCheckerMode.ANY);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeIncrementVisitorArrayFailureTypeTest() throws Exception {
        symbolTable.put(new SymbolNode("x@global", Kind.ARRAY, Type.BOOLEAN));

        // Construction of MJJ AST
        NodeIncrement nodeIncrement = new NodeIncrement();
        NodeArrayGet nodeArrayGet = new NodeArrayGet();

        nodeArrayGet.addChild(new NodeIdent("x"));
        nodeArrayGet.addChild(new NodeNumber(0));

        nodeIncrement.addChild(nodeArrayGet);

        // Type check
        Type type = typeCheckerVisitor.visit(nodeIncrement, TypeChecker.SCOPE_MAIN, null, TypeCheckerMode.ANY);
    }

    @Test
    public void nodeAssignmentVisitorTest() throws Exception {
        symbolTable.put(new SymbolNode("x@global", Kind.VARIABLE, Type.INTEGER));

        // Construction of MJJ AST
        NodeAssignment nodeAssignment = new NodeAssignment();

        nodeAssignment.addChild(new NodeIdent("x"));
        nodeAssignment.addChild(new NodeNumber(0));

        // Type check
        Type type = typeCheckerVisitor.visit(nodeAssignment, TypeChecker.SCOPE_MAIN, null, TypeCheckerMode.ANY);

        Assert.assertNull(type);
    }

    @Test
    public void nodeAssignmentVisitorVConstantTest() throws Exception {
        symbolTable.put(new SymbolNode("x@global", Kind.VCONSTANT, Type.INTEGER));

        // Construction of MJJ AST
        NodeAssignment nodeAssignment = new NodeAssignment();

        nodeAssignment.addChild(new NodeIdent("x"));
        nodeAssignment.addChild(new NodeNumber(0));

        // Type check
        Type type = typeCheckerVisitor.visit(nodeAssignment, TypeChecker.SCOPE_MAIN, null, TypeCheckerMode.ANY);
        SymbolNode symbolNode = symbolTable.get("x@global");

        Assert.assertNull(type);
        Assert.assertNotNull(symbolNode);
        Assert.assertEquals(symbolNode.getKind(), Kind.CONSTANT);
    }

    @Test
    public void nodeAssignmentVisitorArrayTest() throws Exception {
        symbolTable.put(new SymbolNode("x@global", Kind.ARRAY, Type.INTEGER));

        // Construction of MJJ AST
        NodeAssignment nodeAssignment = new NodeAssignment();
        NodeArrayGet nodeArrayGet = new NodeArrayGet();

        nodeArrayGet.addChild(new NodeIdent("x"));
        nodeArrayGet.addChild(new NodeNumber(0));

        nodeAssignment.addChild(nodeArrayGet);
        nodeAssignment.addChild(new NodeNumber(0));

        // Type check
        Type type = typeCheckerVisitor.visit(nodeAssignment, TypeChecker.SCOPE_MAIN, null, TypeCheckerMode.ANY);

        Assert.assertNull(type);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeAssignmentVisitorFailureDeclaredTest() throws Exception {
        // Construction of MJJ AST
        NodeAssignment nodeAssignment = new NodeAssignment();

        nodeAssignment.addChild(new NodeIdent("x"));
        nodeAssignment.addChild(new NodeNumber(0));

        // Type check
        Type type = typeCheckerVisitor.visit(nodeAssignment, TypeChecker.SCOPE_MAIN, null, TypeCheckerMode.ANY);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeAssignmentVisitorFailureConstantTest() throws Exception {
        symbolTable.put(new SymbolNode("x@global", Kind.CONSTANT, Type.INTEGER));

        // Construction of MJJ AST
        NodeAssignment nodeAssignment = new NodeAssignment();

        nodeAssignment.addChild(new NodeIdent("x"));
        nodeAssignment.addChild(new NodeNumber(0));

        // Type check
        Type type = typeCheckerVisitor.visit(nodeAssignment, TypeChecker.SCOPE_MAIN, null, TypeCheckerMode.ANY);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeAssignmentVisitorFailureVConstantTest() throws Exception {
        symbolTable.put(new SymbolNode("x@global", Kind.CONSTANT, Type.INTEGER));

        // Construction of MJJ AST
        NodeAssignment nodeAssignment = new NodeAssignment();

        nodeAssignment.addChild(new NodeIdent("x"));
        nodeAssignment.addChild(new NodeNumber(0));

        // Type check
        Type type = typeCheckerVisitor.visit(nodeAssignment, "f:", null, TypeCheckerMode.ANY);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeAssignmentVisitorFailureMethodTest() throws Exception {
        symbolTable.put(new SymbolNode("x@global", Kind.METHOD, Type.INTEGER));

        // Construction of MJJ AST
        NodeAssignment nodeAssignment = new NodeAssignment();

        nodeAssignment.addChild(new NodeIdent("x"));
        nodeAssignment.addChild(new NodeNumber(0));

        // Type check
        Type type = typeCheckerVisitor.visit(nodeAssignment, TypeChecker.SCOPE_MAIN, null, TypeCheckerMode.ANY);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeAssignmentVisitorFailureWrongTypeArrayTest() throws Exception {
        symbolTable.put(new SymbolNode("x@global", Kind.ARRAY, Type.INTEGER));
        symbolTable.put(new SymbolNode("y@global", Kind.ARRAY, Type.BOOLEAN));

        // Construction of MJJ AST
        NodeAssignment nodeAssignment = new NodeAssignment();

        nodeAssignment.addChild(new NodeIdent("x"));
        nodeAssignment.addChild(new NodeIdent("y"));

        // Type check
        Type type = typeCheckerVisitor.visit(nodeAssignment, TypeChecker.SCOPE_MAIN, null, TypeCheckerMode.ANY);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeAssignmentVisitorFailureExpTypeTest() throws Exception {
        symbolTable.put(new SymbolNode("x@global", Kind.VARIABLE, Type.BOOLEAN));

        // Construction of MJJ AST
        NodeAssignment nodeAssignment = new NodeAssignment();

        nodeAssignment.addChild(new NodeIdent("x"));
        nodeAssignment.addChild(new NodeNumber(0));

        // Type check
        Type type = typeCheckerVisitor.visit(nodeAssignment, TypeChecker.SCOPE_MAIN, null, TypeCheckerMode.ANY);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeAssignmentVisitorArrayFailureExpTypeTest() throws Exception {
        symbolTable.put(new SymbolNode("x@global", Kind.ARRAY, Type.BOOLEAN));

        // Construction of MJJ AST
        NodeAssignment nodeAssignment = new NodeAssignment();
        NodeArrayGet nodeArrayGet = new NodeArrayGet();

        nodeArrayGet.addChild(new NodeIdent("x"));
        nodeArrayGet.addChild(new NodeNumber(0));

        nodeAssignment.addChild(nodeArrayGet);
        nodeAssignment.addChild(new NodeNumber(0));

        // Type check
        Type type = typeCheckerVisitor.visit(nodeAssignment, TypeChecker.SCOPE_MAIN, null, TypeCheckerMode.ANY);
    }

    @Test
    public void nodeIfVisitorTest() throws Exception {
        // Construction of MJJ AST
        NodeIf nodeIf = new NodeIf();

        nodeIf.addChild(new NodeTrue());
        nodeIf.addChild(new NodeInil());
        nodeIf.addChild(new NodeInil());

        // Type check
        Type type = typeCheckerVisitor.visit(nodeIf, TypeChecker.SCOPE_MAIN, null, TypeCheckerMode.ANY);

        Assert.assertNull(type);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeIfVisitorFailureTest() throws Exception {
        // Construction of MJJ AST
        NodeIf nodeIf = new NodeIf();

        nodeIf.addChild(new NodeNumber(0));
        nodeIf.addChild(new NodeInil());
        nodeIf.addChild(new NodeInil());

        // Type check
        typeCheckerVisitor.visit(nodeIf, TypeChecker.SCOPE_MAIN, null, TypeCheckerMode.ANY);
    }

    @Test
    public void nodeWhileVisitorTest() throws Exception {
        // Construction of MJJ AST
        NodeWhile nodeWhile = new NodeWhile();

        nodeWhile.addChild(new NodeTrue());
        nodeWhile.addChild(new NodeInil());

        // Type check
        Type type = typeCheckerVisitor.visit(nodeWhile, TypeChecker.SCOPE_MAIN, null, TypeCheckerMode.ANY);

        Assert.assertNull(type);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeWhileVisitorFailureTest() throws Exception {
        // Construction of MJJ AST
        NodeWhile nodeWhile = new NodeWhile();

        nodeWhile.addChild(new NodeNumber(0));
        nodeWhile.addChild(new NodeInil());

        // Type check
        typeCheckerVisitor.visit(nodeWhile, TypeChecker.SCOPE_MAIN, null, TypeCheckerMode.ANY);
    }

    @Test
    public void nodeHeaderVisitorTest() throws Exception {
        // Construction of MJJ AST
        NodeHeader nodeHeader = new NodeHeader();

        nodeHeader.addChild(new NodeInteger());
        nodeHeader.addChild(new NodeIdent("x"));

        // Type check
        Type type = typeCheckerVisitor.visit(nodeHeader, "f:", TypeChecker.FIRST_PASS, TypeCheckerMode.ANY);
        SymbolNode symbolNode = symbolTable.get("x@f:");

        Assert.assertNull(type);
        Assert.assertNotNull(symbolNode);
        Assert.assertEquals(symbolNode.getKind(), Kind.VARIABLE);
        Assert.assertEquals(symbolNode.getType(), Type.INTEGER);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeHeaderVisitorFailureVoidTest() throws Exception {
        // Construction of MJJ AST
        NodeHeader nodeHeader = new NodeHeader();

        nodeHeader.addChild(new NodeVoid());
        nodeHeader.addChild(new NodeIdent("x"));

        // Type check
        Type type = typeCheckerVisitor.visit(nodeHeader, "f:", TypeChecker.FIRST_PASS, TypeCheckerMode.ANY);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeHeaderVisitorFailureDeclaredTest() throws Exception {
        symbolTable.put(new SymbolNode("x@f:", Kind.VARIABLE, Type.INTEGER));

        // Construction of MJJ AST
        NodeHeader nodeHeader = new NodeHeader();

        nodeHeader.addChild(new NodeInteger());
        nodeHeader.addChild(new NodeIdent("x"));

        // Type check
        Type type = typeCheckerVisitor.visit(nodeHeader, "f:", TypeChecker.FIRST_PASS, TypeCheckerMode.ANY);
    }

    @Test
    public void nodeHeadersVisitorTest() throws Exception {
        // Construction of MJJ AST
        NodeHeaders nodeHeaders1 = new NodeHeaders();
        NodeHeaders nodeHeaders2 = new NodeHeaders();

        NodeHeader nodeHeader1 = new NodeHeader();

        nodeHeader1.addChild(new NodeInteger());
        nodeHeader1.addChild(new NodeIdent("x"));

        NodeHeader nodeHeader2 = new NodeHeader();

        nodeHeader2.addChild(new NodeBoolean());
        nodeHeader2.addChild(new NodeIdent("y"));

        nodeHeaders1.addChild(nodeHeader1);
        nodeHeaders1.addChild(nodeHeaders2);

        nodeHeaders2.addChild(nodeHeader2);
        nodeHeaders2.addChild(new NodeEnil());

        // Type check
        Type type = typeCheckerVisitor.visit(nodeHeaders1, "f:int_boolean", TypeChecker.FIRST_PASS, TypeCheckerMode.ANY);
        SymbolNode symbolNode1 = symbolTable.get("x@f:int_boolean");
        SymbolNode symbolNode2 = symbolTable.get("y@f:int_boolean");

        Assert.assertNull(type);
        Assert.assertNotNull(symbolNode1);
        Assert.assertNotNull(symbolNode2);
        Assert.assertEquals(symbolNode1.getKind(), Kind.VARIABLE);
        Assert.assertEquals(symbolNode1.getType(), Type.INTEGER);
        Assert.assertEquals(symbolNode2.getKind(), Kind.VARIABLE);
        Assert.assertEquals(symbolNode2.getType(), Type.BOOLEAN);
    }

    @Test
    public void nodeReturnVisitorTest() throws Exception {
        symbolTable.put(new SymbolNode("f:", Kind.METHOD, Type.INTEGER));

        // Construction of MJJ AST
        NodeReturn nodeReturn = new NodeReturn();

        nodeReturn.addChild(new NodeNumber(0));

        // Type check
        Type type = typeCheckerVisitor.visit(nodeReturn, "f:", null, TypeCheckerMode.ANY);

        Assert.assertNull(type);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeReturnVisitorFailureOutsideTest() throws Exception {
        // Construction of MJJ AST
        NodeReturn nodeReturn = new NodeReturn();

        nodeReturn.addChild(new NodeNumber(0));

        // Type check
        Type type = typeCheckerVisitor.visit(nodeReturn, TypeChecker.SCOPE_MAIN, null, TypeCheckerMode.ANY);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeReturnVisitorFailureDeclaredTest() throws Exception {
        // Construction of MJJ AST
        NodeReturn nodeReturn = new NodeReturn();

        nodeReturn.addChild(new NodeNumber(0));

        // Type check
        Type type = typeCheckerVisitor.visit(nodeReturn, "f:", null, TypeCheckerMode.ANY);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeReturnVisitorFailureVoidTest() throws Exception {
        symbolTable.put(new SymbolNode("f:", Kind.METHOD, Type.VOID));

        // Construction of MJJ AST
        NodeReturn nodeReturn = new NodeReturn();

        nodeReturn.addChild(new NodeNumber(0));

        // Type check
        Type type = typeCheckerVisitor.visit(nodeReturn, "f:", null, TypeCheckerMode.ANY);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeReturnVisitorFailureTypeTest() throws Exception {
        symbolTable.put(new SymbolNode("f:", Kind.METHOD, Type.BOOLEAN));

        // Construction of MJJ AST
        NodeReturn nodeReturn = new NodeReturn();

        nodeReturn.addChild(new NodeNumber(0));

        // Type check
        Type type = typeCheckerVisitor.visit(nodeReturn, "f:", null, TypeCheckerMode.ANY);
    }

    @Test
    public void nodeMethodVisitorTest() throws Exception {
        // Construction of MJJ AST
        NodeMethod nodeMethod = new NodeMethod();
        NodeHeaders nodeHeaders1 = new NodeHeaders();
        NodeHeaders nodeHeaders2 = new NodeHeaders();

        nodeMethod.addChild(new NodeVoid());
        nodeMethod.addChild(new NodeIdent("f"));
        nodeMethod.addChild(nodeHeaders1);
        nodeMethod.addChild(new NodeVnil());
        nodeMethod.addChild(new NodeInil());

        NodeHeader nodeHeader1 = new NodeHeader();
        NodeHeader nodeHeader2 = new NodeHeader();

        nodeHeader1.addChild(new NodeInteger());
        nodeHeader1.addChild(new NodeIdent("x"));

        nodeHeader2.addChild(new NodeBoolean());
        nodeHeader2.addChild(new NodeIdent("y"));

        nodeHeaders1.addChild(nodeHeader1);
        nodeHeaders1.addChild(nodeHeaders2);

        nodeHeaders2.addChild(nodeHeader2);
        nodeHeaders2.addChild(new NodeEnil());

        // Type check
        Type type = typeCheckerVisitor.visit(nodeMethod, TypeChecker.SCOPE_GLOBAL, TypeChecker.FIRST_PASS, TypeCheckerMode.ANY);
        SymbolNode symbolNode = symbolTable.get("f:int_boolean");

        Assert.assertNull(type);
        Assert.assertNotNull(symbolNode);
        Assert.assertEquals(symbolNode.getKind(), Kind.METHOD);
        Assert.assertEquals(symbolNode.getType(), Type.VOID);
    }

    @Test
    public void nodeMethodVisitorDeepReturnTest() throws Exception {
        // Construction of MJJ AST
        NodeMethod nodeMethod = new NodeMethod();
        NodeInstrs nodeInstrs1 = new NodeInstrs();
        NodeIf nodeIf = new NodeIf();

        nodeInstrs1.addChild(nodeIf);
        nodeInstrs1.addChild(new NodeInil());

        NodeInstrs nodeInstrs2 = new NodeInstrs();
        NodeInstrs nodeInstrs3 = new NodeInstrs();

        nodeIf.addChild(new NodeTrue());
        nodeIf.addChild(nodeInstrs2);
        nodeIf.addChild(nodeInstrs3);

        NodeReturn nodeReturn1 = new NodeReturn();
        NodeReturn nodeReturn2 = new NodeReturn();

        nodeInstrs2.addChild(nodeReturn1);
        nodeInstrs2.addChild(new NodeInil());

        nodeInstrs3.addChild(nodeReturn2);
        nodeInstrs3.addChild(new NodeInil());

        nodeReturn1.addChild(new NodeNumber(0));
        nodeReturn2.addChild(new NodeNumber(1));

        nodeMethod.addChild(new NodeInteger());
        nodeMethod.addChild(new NodeIdent("f"));
        nodeMethod.addChild(new NodeEnil());
        nodeMethod.addChild(new NodeVnil());
        nodeMethod.addChild(nodeInstrs1);

        // Type check
        Type type = typeCheckerVisitor.visit(nodeMethod, TypeChecker.SCOPE_GLOBAL, TypeChecker.FIRST_PASS, TypeCheckerMode.ANY);
        SymbolNode symbolNode = symbolTable.get("f:");

        Assert.assertNull(type);
        Assert.assertNotNull(symbolNode);
        Assert.assertEquals(symbolNode.getKind(), Kind.METHOD);
        Assert.assertEquals(symbolNode.getType(), Type.INTEGER);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeMethodVisitorFailureDeclaredTest() throws Exception {
        symbolTable.put(new SymbolNode("f:", Kind.METHOD, Type.VOID));

        // Construction of MJJ AST
        NodeMethod nodeMethod = new NodeMethod();

        nodeMethod.addChild(new NodeVoid());
        nodeMethod.addChild(new NodeIdent("f"));
        nodeMethod.addChild(new NodeEnil());
        nodeMethod.addChild(new NodeVnil());
        nodeMethod.addChild(new NodeInil());

        // Type check
        Type type = typeCheckerVisitor.visit(nodeMethod, TypeChecker.SCOPE_GLOBAL, TypeChecker.FIRST_PASS, TypeCheckerMode.ANY);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeMethodVisitorFailureReturnTest() throws Exception {
        // Construction of MJJ AST
        NodeMethod nodeMethod = new NodeMethod();

        nodeMethod.addChild(new NodeInteger());
        nodeMethod.addChild(new NodeIdent("f"));
        nodeMethod.addChild(new NodeEnil());
        nodeMethod.addChild(new NodeVnil());
        nodeMethod.addChild(new NodeInil());

        // Type check
        Type type = typeCheckerVisitor.visit(nodeMethod, TypeChecker.SCOPE_GLOBAL, TypeChecker.FIRST_PASS, TypeCheckerMode.ANY);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeMethodVisitorFailureDeepReturnTest() throws Exception {
        // Construction of MJJ AST
        NodeMethod nodeMethod = new NodeMethod();
        NodeInstrs nodeInstrs1 = new NodeInstrs();
        NodeIf nodeIf = new NodeIf();

        nodeInstrs1.addChild(nodeIf);
        nodeInstrs1.addChild(new NodeInil());

        NodeInstrs nodeInstrs2 = new NodeInstrs();

        nodeIf.addChild(new NodeTrue());
        nodeIf.addChild(nodeInstrs2);
        nodeIf.addChild(new NodeInil());

        NodeReturn nodeReturn = new NodeReturn();

        nodeInstrs2.addChild(nodeReturn);
        nodeInstrs2.addChild(new NodeInil());

        nodeReturn.addChild(new NodeNumber(0));

        nodeMethod.addChild(new NodeInteger());
        nodeMethod.addChild(new NodeIdent("f"));
        nodeMethod.addChild(new NodeEnil());
        nodeMethod.addChild(new NodeVnil());
        nodeMethod.addChild(nodeInstrs1);

        // Type check
        Type type = typeCheckerVisitor.visit(nodeMethod, TypeChecker.SCOPE_GLOBAL, TypeChecker.FIRST_PASS, TypeCheckerMode.ANY);
    }

    @Test
    public void nodeCallEVisitorTest() throws Exception {
        symbolTable.put(new SymbolNode("f:int_boolean", Kind.METHOD, Type.INTEGER));

        // Construction of MJJ AST
        NodeCallE nodeCallE = new NodeCallE();
        NodeExpList nodeExpList1 = new NodeExpList();
        NodeExpList nodeExpList2 = new NodeExpList();

        nodeCallE.addChild(new NodeIdent("f"));
        nodeCallE.addChild(nodeExpList1);

        nodeExpList1.addChild(new NodeNumber(0));
        nodeExpList1.addChild(nodeExpList2);

        nodeExpList2.addChild(new NodeTrue());
        nodeExpList2.addChild(new NodeExnil());

        // Type check
        Type type = typeCheckerVisitor.visit(nodeCallE, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);

        Assert.assertEquals(type, Type.INTEGER);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeCallEVisitorFailureDeclaredTest() throws Exception {
        // Construction of MJJ AST
        NodeCallE nodeCallE = new NodeCallE();

        nodeCallE.addChild(new NodeIdent("f"));
        nodeCallE.addChild(new NodeExnil());

        // Type check
        Type type = typeCheckerVisitor.visit(nodeCallE, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);
    }

    @Test
    public void nodeCallIVisitorTest() throws Exception {
        symbolTable.put(new SymbolNode("f:", Kind.METHOD, Type.INTEGER));

        // Construction of MJJ AST
        NodeCallI nodeCallI = new NodeCallI();

        nodeCallI.addChild(new NodeIdent("f"));
        nodeCallI.addChild(new NodeExnil());

        // Type check
        Type type = typeCheckerVisitor.visit(nodeCallI, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);

        Assert.assertNull(type);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeCallIVisitorFailureDeclaredTest() throws Exception {
        // Construction of MJJ AST
        NodeCallI nodeCallI = new NodeCallI();

        nodeCallI.addChild(new NodeIdent("f"));
        nodeCallI.addChild(new NodeExnil());

        // Type check
        Type type = typeCheckerVisitor.visit(nodeCallI, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);
    }

    @Test
    public void nodeArrayGetVisitorTest() throws Exception {
        symbolTable.put(new SymbolNode("x@global", Kind.ARRAY, Type.INTEGER));

        // Construction of MJJ AST
        NodeArrayGet nodeArrayGet = new NodeArrayGet();

        nodeArrayGet.addChild(new NodeIdent("x"));
        nodeArrayGet.addChild(new NodeNumber(0));

        // Type check
        Type type = typeCheckerVisitor.visit(nodeArrayGet, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);

        Assert.assertEquals(type, Type.INTEGER);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeArrayGetVisitorFailureExpTypeTest() throws Exception {
        // Construction of MJJ AST
        NodeArrayGet nodeArrayGet = new NodeArrayGet();

        nodeArrayGet.addChild(new NodeIdent("x"));
        nodeArrayGet.addChild(new NodeTrue());

        // Type check
        Type type = typeCheckerVisitor.visit(nodeArrayGet, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeArrayGetVisitorFailureDeclaredTest() throws Exception {
        // Construction of MJJ AST
        NodeArrayGet nodeArrayGet = new NodeArrayGet();

        nodeArrayGet.addChild(new NodeIdent("x"));
        nodeArrayGet.addChild(new NodeNumber(0));

        // Type check
        Type type = typeCheckerVisitor.visit(nodeArrayGet, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeArrayGetVisitorFailureNotArrayTest() throws Exception {
        symbolTable.put(new SymbolNode("x@global", Kind.VARIABLE, Type.INTEGER));

        // Construction of MJJ AST
        NodeArrayGet nodeArrayGet = new NodeArrayGet();

        nodeArrayGet.addChild(new NodeIdent("x"));
        nodeArrayGet.addChild(new NodeNumber(0));

        // Type check
        Type type = typeCheckerVisitor.visit(nodeArrayGet, TypeChecker.SCOPE_GLOBAL, null, TypeCheckerMode.ANY);
    }

    @Test
    public void nodeArrayVisitorTest() throws Exception {
        // Construction of MJJ AST
        NodeArray nodeArray = new NodeArray();

        nodeArray.addChild(new NodeInteger());
        nodeArray.addChild(new NodeIdent("x"));
        nodeArray.addChild(new NodeNumber(1));

        // Type check
        Type type = typeCheckerVisitor.visit(nodeArray, TypeChecker.SCOPE_GLOBAL, TypeChecker.FIRST_PASS, TypeCheckerMode.ANY);
        SymbolNode symbolNode = symbolTable.get("x@global");

        Assert.assertNull(type);
        Assert.assertNotNull(symbolNode);
        Assert.assertEquals(symbolNode.getKind(), Kind.ARRAY);
        Assert.assertEquals(symbolNode.getType(), Type.INTEGER);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeArrayVisitorFailureVoidTest() throws Exception {
        // Construction of MJJ AST
        NodeArray nodeArray = new NodeArray();

        nodeArray.addChild(new NodeVoid());
        nodeArray.addChild(new NodeIdent("x"));
        nodeArray.addChild(new NodeNumber(1));

        // Type check
        Type type = typeCheckerVisitor.visit(nodeArray, TypeChecker.SCOPE_GLOBAL, TypeChecker.FIRST_PASS, TypeCheckerMode.ANY);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeArrayVisitorFailureExpTypeTest() throws Exception {
        // Construction of MJJ AST
        NodeArray nodeArray = new NodeArray();

        nodeArray.addChild(new NodeInteger());
        nodeArray.addChild(new NodeIdent("x"));
        nodeArray.addChild(new NodeTrue());

        // Type check
        Type type = typeCheckerVisitor.visit(nodeArray, TypeChecker.SCOPE_GLOBAL, TypeChecker.FIRST_PASS, TypeCheckerMode.ANY);
    }

    @Test(expected = TypeCheckerException.class)
    public void nodeArrayVisitorFailureDeclaredTest() throws Exception {
        symbolTable.put(new SymbolNode("x@global", Kind.VARIABLE, Type.BOOLEAN));

        // Construction of MJJ AST
        NodeArray nodeArray = new NodeArray();

        nodeArray.addChild(new NodeInteger());
        nodeArray.addChild(new NodeIdent("x"));
        nodeArray.addChild(new NodeNumber(1));

        // Type check
        Type type = typeCheckerVisitor.visit(nodeArray, TypeChecker.SCOPE_GLOBAL, TypeChecker.FIRST_PASS, TypeCheckerMode.ANY);
    }
}
