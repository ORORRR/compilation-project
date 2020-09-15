package fr.femtost.disc.glcomp.m1comp6.compiler;

import fr.femtost.disc.glcomp.m1comp6.ast.common.Kind;
import fr.femtost.disc.glcomp.m1comp6.ast.common.Type;
import fr.femtost.disc.glcomp.m1comp6.ast.jjc.*;
import fr.femtost.disc.glcomp.m1comp6.ast.mjj.*;
import org.junit.Assert;
import org.junit.Test;

public class CompilerTest {

    @Test
    public void compilerTest() throws CompilerException {
        /*
         * class C {
         *   int x;
         *
         *   main {
         *     int x = 0;
         *     boolean y;
         *
         *     y = true;
         *
         *     if (y) {
         *       x += 1;
         *     };
         *   }
         * }
         *
         * 1  init
         * 2  push(nil)
         * 3  new(x@global, INTEGER, VARIABLE, 0)
         * 4  push(0)
         * 5  new(x@main, INTEGER, VARIABLE, 0)
         * 6  push(nil)
         * 7  new(y@main, BOOLEAN, VARIABLE, 0)
         * 8  push(true)
         * 9  store(y@main)
         * 10 load(y@main)
         * 11 if(13)
         * 12 goto(15)
         * 13 push(1)
         * 14 inc(x@main)
         * 15 push(0)
         * 16 swap
         * 17 pop
         * 18 swap
         * 19 pop
         * 20 swap
         * 21 pop
         * 22 pop
         * 23 jcstop
         */

        // Construction of MJJ AST
        NodeClass nodeClass = new NodeClass();

        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent nodeIdent1 = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("C", "global");
        NodeDecls nodeDecls = new NodeDecls();
        NodeMain nodeMain = new NodeMain();

        nodeClass.addChild(nodeIdent1);
        nodeClass.addChild(nodeDecls);
        nodeClass.addChild(nodeMain);

        NodeVar nodeVar1 = new NodeVar();

        nodeDecls.addChild(nodeVar1);
        nodeDecls.addChild(new NodeVnil());

        nodeVar1.addChild(new NodeInteger());
        nodeVar1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("x", "global"));
        nodeVar1.addChild(new NodeOmega());

        NodeVars nodeVars1 = new NodeVars();
        NodeVars nodeVars2 = new NodeVars();

        NodeVar nodeVar2 = new NodeVar();
        NodeVar nodeVar3 = new NodeVar();

        nodeVars1.addChild(nodeVar2);
        nodeVars1.addChild(nodeVars2);

        nodeVars2.addChild(nodeVar3);
        nodeVars2.addChild(new NodeVnil());

        nodeVar2.addChild(new NodeInteger());
        nodeVar2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("x", "main"));
        nodeVar2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(0));

        nodeVar3.addChild(new NodeBoolean());
        nodeVar3.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("y", "main"));
        nodeVar3.addChild(new NodeOmega());

        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeInstrs nodeInstrs1 = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeInstrs();
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeInstrs nodeInstrs2 = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeInstrs();
        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeInstrs nodeInstrs3 = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeInstrs();

        nodeMain.addChild(nodeVars1);
        nodeMain.addChild(nodeInstrs1);

        NodeAssignment nodeAssignment = new NodeAssignment();

        nodeInstrs1.addChild(nodeAssignment);
        nodeInstrs1.addChild(nodeInstrs2);

        nodeAssignment.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("y", "main"));
        nodeAssignment.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeTrue());

        fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIf nodeIf1 = new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIf();

        nodeInstrs2.addChild(nodeIf1);
        nodeInstrs2.addChild(new NodeInil());

        nodeIf1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("y", "main"));
        nodeIf1.addChild(nodeInstrs3);
        nodeIf1.addChild(new NodeInil());

        NodeSum nodeSum = new NodeSum();

        nodeInstrs3.addChild(nodeSum);
        nodeInstrs3.addChild(new NodeInil());

        nodeSum.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeIdent("x", "main"));
        nodeSum.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeNumber(1));

        // Compilation
        Compiler compiler = new Compiler(nodeClass);
        fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeInstrs instrs = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeInstrs();

        compiler.setInstrs(instrs);

        compiler.compile();

        // Construction of expected instrs
        fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeInstrs expected = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeInstrs();

        expected.addChild(new NodeInit());

        NodePush nodePush1 = new NodePush();

        expected.addChild(nodePush1);
        nodePush1.addChild(new NodeJcnil());

        NodeNew nodeNew1 = new NodeNew();

        expected.addChild(nodeNew1);

        nodeNew1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("x@global"));
        nodeNew1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeType(Type.INTEGER));
        nodeNew1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeKind(Kind.VARIABLE));
        nodeNew1.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));

        NodePush nodePush2 = new NodePush();

        expected.addChild(nodePush2);
        nodePush2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));

        NodeNew nodeNew2 = new NodeNew();

        expected.addChild(nodeNew2);

        nodeNew2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("x@main"));
        nodeNew2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeType(Type.INTEGER));
        nodeNew2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeKind(Kind.VARIABLE));
        nodeNew2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));

        NodePush nodePush3 = new NodePush();

        expected.addChild(nodePush3);
        nodePush3.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeJcnil());

        NodeNew nodeNew3 = new NodeNew();

        expected.addChild(nodeNew3);

        nodeNew3.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("y@main"));
        nodeNew3.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeType(Type.BOOLEAN));
        nodeNew3.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeKind(Kind.VARIABLE));
        nodeNew3.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));

        NodePush nodePush4 = new NodePush();

        expected.addChild(nodePush4);
        nodePush4.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeTrue());

        NodeStore nodeStore = new NodeStore();

        expected.addChild(nodeStore);
        nodeStore.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("y@main"));

        NodeLoad nodeLoad = new NodeLoad();

        expected.addChild(nodeLoad);
        nodeLoad.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("y@main"));

        fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIf nodeIf2 = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIf();

        expected.addChild(nodeIf2);
        nodeIf2.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(13));

        NodeGoto nodeGoto = new NodeGoto();

        expected.addChild(nodeGoto);
        nodeGoto.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(15));

        NodePush nodePush5 = new NodePush();

        expected.addChild(nodePush5);
        nodePush5.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(1));

        NodeInc nodeInc = new NodeInc();

        expected.addChild(nodeInc);
        nodeInc.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeIdent("x@main"));

        NodePush nodePush6 = new NodePush();

        expected.addChild(nodePush6);
        nodePush6.addChild(new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(0));

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
}
