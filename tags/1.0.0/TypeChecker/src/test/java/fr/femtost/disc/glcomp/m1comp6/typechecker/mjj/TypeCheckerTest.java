package fr.femtost.disc.glcomp.m1comp6.typechecker.mjj;

import org.junit.Test;

import fr.femtost.disc.glcomp.m1comp6.memory.SymbolTable;

import fr.femtost.disc.glcomp.m1comp6.ast.mjj.*;

public class TypeCheckerTest {

    @Test
    public void testTypeChecker() throws TypeCheckerException {
        /*
         * class C { int x = 0; main { int y = x; }
         */

        NodeClass nodeClass = new NodeClass();
        NodeIdent nodeIdent = new NodeIdent("C");
        NodeDecls nodeDecls = new NodeDecls();
        NodeMain nodeMain = new NodeMain();

        nodeClass.addChild(nodeIdent);
        nodeClass.addChild(nodeDecls);
        nodeClass.addChild(nodeMain);

        NodeVar nodeVar = new NodeVar();
        NodeVnil nodeVnil = new NodeVnil();

        nodeDecls.addChild(nodeVar);
        nodeDecls.addChild(nodeVnil);

        NodeInteger nodeInteger = new NodeInteger();
        NodeIdent nodeIdent1 = new NodeIdent("x");
        NodeNumber nodeNumber = new NodeNumber(0);

        nodeVar.addChild(nodeInteger);
        nodeVar.addChild(nodeIdent1);
        nodeVar.addChild(nodeNumber);

        NodeVars nodeVars = new NodeVars();
        NodeInil nodeInil = new NodeInil();

        nodeMain.addChild(nodeVars);
        nodeMain.addChild(nodeInil);

        NodeVar nodeVar1 = new NodeVar();

        nodeVars.addChild(nodeVar1);
        nodeVars.addChild(new NodeVnil());

        NodeInteger nodeInteger1 = new NodeInteger();
        NodeIdent nodeIdent2 = new NodeIdent("y");
        NodeIdent nodeIdent3 = new NodeIdent("x");

        nodeVar1.addChild(nodeInteger1);
        nodeVar1.addChild(nodeIdent2);
        nodeVar1.addChild(nodeIdent3);

        /*NodeVar nodeVar1 = new NodeVar();
        NodeVars nodeVars1 = new NodeVars();

        nodeVars.addChild(nodeVar1);
        nodeVars.addChild(nodeVars1);

        NodeBoolean nodeBoolean = new NodeBoolean();
        NodeIdent nodeIdent2 = new NodeIdent("x");
        NodeTrue nodeTrue = new NodeTrue();

        nodeVar1.addChild(nodeBoolean);
        nodeVar1.addChild(nodeIdent2);
        nodeVar1.addChild(nodeTrue);

        NodeVar nodeVar2 = new NodeVar();
        NodeVnil nodeVnil1 = new NodeVnil();

        nodeVars1.addChild(nodeVar2);
        nodeVars1.addChild(nodeVnil1);

        NodeBoolean nodeBoolean1 = new NodeBoolean();
        NodeIdent nodeIdent3 = new NodeIdent("y");
        NodeFalse nodeFalse = new NodeFalse();

        nodeVar2.addChild(nodeBoolean1);
        nodeVar2.addChild(nodeIdent3);
        nodeVar2.addChild(nodeFalse);*/

        TypeChecker typeChecker = new TypeChecker(nodeClass);
        SymbolTable symbolTable = new SymbolTable();

        typeChecker.typeCheck(symbolTable);

//        System.out.println(nodeClass);
//        System.out.println(symbolTable);
    }
}
