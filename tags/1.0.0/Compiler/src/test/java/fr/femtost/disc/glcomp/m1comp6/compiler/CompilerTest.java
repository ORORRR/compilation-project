package fr.femtost.disc.glcomp.m1comp6.compiler;


import fr.femtost.disc.glcomp.m1comp6.memory.SymbolTable;
import fr.femtost.disc.glcomp.m1comp6.ast.common.*;
import fr.femtost.disc.glcomp.m1comp6.ast.mjj.*;
import fr.femtost.disc.glcomp.m1comp6.ast.jjc.*;

import org.junit.Assert;
import org.junit.Test;

public class CompilerTest {

    @Test
    public void compilerTest() throws VisitorException {
        /*
         * class C {
         *   boolean a ;
         *   int y = 4;
         *
         *   main {
         *     boolean x = true;
         *     boolean y = false;
         *   }
         * }
         */

        //CREATION OF MJJ AST
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


        //COMPILE
        Compiler compiler = new Compiler(nodeClass);
        SymbolTable symbolTable = new SymbolTable();
        fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeInstrs instrs = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeInstrs();

        compiler.compile(symbolTable, instrs);


        //CHECK RESULT

//        System.out.println(nodeClass);
//        System.out.println(symbolTable);
//        System.out.println(instrs);
//        System.out.println(compiler.print(instrs));

        //CONSTRUCTION OF EXPECTED JJC AST
        fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeInstrs expectedInstrs = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeInstrs();

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



        //CHECK THAT THE RESULT OF THE COMPILATION IS CORRECT
        Assert.assertTrue(expectedInstrs.isEquivalentTo(instrs));


    }

}
