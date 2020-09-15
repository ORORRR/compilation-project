package fr.femtost.disc.glcomp.m1comp6.typechecker.mjj;


import fr.femtost.disc.glcomp.m1comp6.ast.mjj.*;
import fr.femtost.disc.glcomp.m1comp6.memory.SymbolTable;
import org.junit.Test;

public class TypeCheckerTest {
    @Test
    public void typeCheckTest() throws TypeCheckerException {
        // Construction of the AST
        NodeClass nodeClass = new NodeClass();
        NodeDecls nodeDecls1 = new NodeDecls();
        NodeDecls nodeDecls2 = new NodeDecls();
        NodeDecls nodeDecls3 = new NodeDecls();
        NodeMain nodeMain = new NodeMain();

        nodeClass.addChild(new NodeIdent("C"));
        nodeClass.addChild(nodeDecls1);
        nodeClass.addChild(nodeMain);

        NodeCst nodeCst = new NodeCst();

        nodeDecls1.addChild(nodeCst);
        nodeDecls1.addChild(nodeDecls2);

        nodeCst.addChild(new NodeInteger());
        nodeCst.addChild(new NodeIdent("x"));
        nodeCst.addChild(new NodeOmega());

        NodeArray nodeArray = new NodeArray();

        nodeDecls2.addChild(nodeArray);
        nodeDecls2.addChild(nodeDecls3);

        nodeArray.addChild(new NodeInteger());
        nodeArray.addChild(new NodeIdent("t"));
        nodeArray.addChild(new NodeNumber(3));

        NodeMethod nodeMethod = new NodeMethod();

        nodeDecls3.addChild(nodeMethod);
        nodeDecls3.addChild(new NodeVnil());

        NodeHeaders nodeHeaders = new NodeHeaders();

        NodeHeader nodeHeader = new NodeHeader();

        nodeHeaders.addChild(nodeHeader);
        nodeHeaders.addChild(new NodeEnil());

        nodeHeader.addChild(new NodeBoolean());
        nodeHeader.addChild(new NodeIdent("x"));

        NodeInstrs nodeInstrs1 = new NodeInstrs();
        NodeInstrs nodeInstrs2 = new NodeInstrs();

        NodeAssignment nodeAssignment1 = new NodeAssignment();

        nodeInstrs1.addChild(nodeAssignment1);
        nodeInstrs1.addChild(nodeInstrs2);

        NodeArrayGet nodeArrayGet1 = new NodeArrayGet();

        nodeAssignment1.addChild(nodeArrayGet1);
        nodeAssignment1.addChild(new NodeNumber(0));

        nodeArrayGet1.addChild(new NodeIdent("t"));
        nodeArrayGet1.addChild(new NodeNumber(0));

        NodeReturn nodeReturn = new NodeReturn();

        nodeInstrs2.addChild(nodeReturn);
        nodeInstrs2.addChild(new NodeInil());

        NodeArrayGet nodeArrayGet2 = new NodeArrayGet();

        nodeReturn.addChild(nodeArrayGet2);

        nodeArrayGet2.addChild(new NodeIdent("t"));
        nodeArrayGet2.addChild(new NodeNumber(1));

        nodeMethod.addChild(new NodeInteger());
        nodeMethod.addChild(new NodeIdent("f"));
        nodeMethod.addChild(nodeHeaders);
        nodeMethod.addChild(new NodeVnil());
        nodeMethod.addChild(nodeInstrs1);

        NodeVars nodeVars1 = new NodeVars();
        NodeVars nodeVars2 = new NodeVars();
        NodeInstrs nodeInstrs3 = new NodeInstrs();
        NodeInstrs nodeInstrs4 = new NodeInstrs();
        NodeInstrs nodeInstrs5 = new NodeInstrs();
        NodeInstrs nodeInstrs6 = new NodeInstrs();

        nodeMain.addChild(nodeVars1);
        nodeMain.addChild(nodeInstrs3);

        NodeVar nodeVar1 = new NodeVar();
        NodeVar nodeVar2 = new NodeVar();

        nodeVars1.addChild(nodeVar1);
        nodeVars1.addChild(nodeVars2);

        nodeVars2.addChild(nodeVar2);
        nodeVars2.addChild(new NodeVnil());

        NodeDiv nodeDiv = new NodeDiv();
        NodeMul nodeMul = new NodeMul();
        NodeSub nodeSub = new NodeSub();
        NodeAdd nodeAdd = new NodeAdd();

        nodeAdd.addChild(new NodeNumber(1));
        nodeAdd.addChild(new NodeNumber(2));

        nodeSub.addChild(new NodeNumber(4));
        nodeSub.addChild(new NodeNumber(5));

        nodeMul.addChild(nodeAdd);
        nodeMul.addChild(new NodeNumber(3));

        nodeDiv.addChild(nodeMul);
        nodeDiv.addChild(nodeSub);

        nodeVar1.addChild(new NodeInteger());
        nodeVar1.addChild(new NodeIdent("x"));
        nodeVar1.addChild(nodeDiv);

        nodeVar2.addChild(new NodeBoolean());
        nodeVar2.addChild(new NodeIdent("y"));
        nodeVar2.addChild(new NodeOmega());

        NodeCallI nodeCallI = new NodeCallI();

        nodeInstrs3.addChild(nodeCallI);
        nodeInstrs3.addChild(nodeInstrs4);

        NodeExpList nodeExpList1 = new NodeExpList();

        NodeAnd nodeAnd = new NodeAnd();
        NodeOr nodeOr = new NodeOr();

        nodeAnd.addChild(new NodeTrue());
        nodeAnd.addChild(nodeOr);

        nodeOr.addChild(new NodeTrue());
        nodeOr.addChild(new NodeFalse());

        nodeExpList1.addChild(nodeAnd);
        nodeExpList1.addChild(new NodeExnil());

        nodeCallI.addChild(new NodeIdent("f"));
        nodeCallI.addChild(nodeExpList1);

        NodeAssignment nodeAssignment2 = new NodeAssignment();

        nodeInstrs4.addChild(nodeAssignment2);
        nodeInstrs4.addChild(nodeInstrs5);

        NodeNot nodeNot = new NodeNot();
        NodeSup nodeSup = new NodeSup();

        nodeNot.addChild(nodeSup);

        nodeSup.addChild(new NodeNumber(1));
        nodeSup.addChild(new NodeNumber(0));

        nodeAssignment2.addChild(new NodeIdent("y"));
        nodeAssignment2.addChild(nodeNot);

        NodeIf nodeIf = new NodeIf();

        nodeInstrs5.addChild(nodeIf);
        nodeInstrs5.addChild(nodeInstrs6);

        NodeCmp nodeCmp = new NodeCmp();

        nodeCmp.addChild(new NodeIdent("y"));
        nodeCmp.addChild(new NodeFalse());

        NodeInstrs nodeInstrs7 = new NodeInstrs();
        NodeInstrs nodeInstrs8 = new NodeInstrs();

        nodeIf.addChild(nodeCmp);
        nodeIf.addChild(nodeInstrs7);
        nodeIf.addChild(new NodeInil());

        NodeSum nodeSum = new NodeSum();

        nodeInstrs7.addChild(nodeSum);
        nodeInstrs7.addChild(nodeInstrs8);

        NodeCallE nodeCallE = new NodeCallE();

        nodeSum.addChild(new NodeIdent("x"));
        nodeSum.addChild(nodeCallE);

        NodeExpList nodeExpList2 = new NodeExpList();

        nodeExpList2.addChild(new NodeTrue());
        nodeExpList2.addChild(new NodeExnil());

        nodeCallE.addChild(new NodeIdent("f"));
        nodeCallE.addChild(nodeExpList2);

        NodeIncrement nodeIncrement = new NodeIncrement();

        nodeInstrs8.addChild(nodeIncrement);
        nodeInstrs8.addChild(new NodeInil());

        nodeIncrement.addChild(new NodeIdent("x"));

        NodeWhile nodeWhile = new NodeWhile();

        nodeInstrs6.addChild(nodeWhile);
        nodeInstrs6.addChild(new NodeInil());

        NodeSup nodeSup2 = new NodeSup();

        NodeNeg nodeNeg = new NodeNeg();

        nodeSup2.addChild(new NodeIdent("x"));
        nodeSup2.addChild(nodeNeg);

        nodeNeg.addChild(new NodeNumber(1));

        NodeInstrs nodeInstrs9 = new NodeInstrs();

        nodeWhile.addChild(nodeSup2);
        nodeWhile.addChild(nodeInstrs9);

        NodeAssignment nodeAssignment3 = new NodeAssignment();

        nodeInstrs9.addChild(nodeAssignment3);
        nodeInstrs9.addChild(new NodeInil());

        NodeSub nodeSub2 = new NodeSub();

        nodeSub2.addChild(new NodeIdent("x"));
        nodeSub2.addChild(new NodeNumber(1));

        nodeAssignment3.addChild(new NodeIdent("x"));
        nodeAssignment3.addChild(nodeSub2);

        System.out.println(nodeClass);

        // Type check
        TypeChecker typeChecker = new TypeChecker(nodeClass);
        SymbolTable symbolTable = new SymbolTable();

        typeChecker.setSymbolTable(symbolTable);

        typeChecker.typeCheck();

//        System.out.println(symbolTable);
    }
}
