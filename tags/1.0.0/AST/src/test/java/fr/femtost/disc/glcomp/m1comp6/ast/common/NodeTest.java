package fr.femtost.disc.glcomp.m1comp6.ast.common;

import org.junit.Assert;
import org.junit.Test;

import fr.femtost.disc.glcomp.m1comp6.ast.mjj.*;

public class NodeTest {
    @Test
    public void testAST() {
        NodeClass nodeClass = new NodeClass();
        NodeIdent nodeIdent = new NodeIdent("MyClass");
        NodeDecls nodeDecls = new NodeDecls();
        NodeMain nodeMain = new NodeMain();

        nodeClass.addChild(nodeIdent);
        nodeClass.addChild(nodeDecls);
        nodeClass.addChild(nodeMain);

        NodeVar nodeVar = new NodeVar();
        NodeDecls nodeDecls1 = new NodeDecls();

        nodeDecls.addChild(nodeVar);
        nodeDecls.addChild(nodeDecls1);

        NodeVar nodeVar1 = new NodeVar();

        nodeDecls1.addChild(nodeVar1);
        nodeDecls1.addChild(new NodeVnil());

        System.out.println(nodeClass);
    }

    @Test
    public void isEquivalentToTest () {

        fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeFalse nodeFalse = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeFalse();
        fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeTrue nodeTrue = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeTrue();
        fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber nodeNumber1 = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(2);
        fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber nodeNumber2 = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeNumber(5);

        fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodePush nodePush1 = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodePush();
        fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodePush nodePush2 = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodePush();
        fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodePush nodePush3 = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodePush();
        fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodePush nodePush4 = new fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodePush();

        nodePush1.addChild(nodeNumber1);
        nodePush2.addChild(nodeNumber2);
        nodePush3.addChild(nodeFalse);
        nodePush4.addChild(nodeTrue);

        Assert.assertTrue(nodeFalse.isEquivalentTo(nodeFalse));
        Assert.assertFalse(nodeTrue.isEquivalentTo(nodeFalse));
        Assert.assertTrue(nodePush3.isEquivalentTo(nodePush3));
        Assert.assertFalse(nodePush4.isEquivalentTo(nodePush3));
        Assert.assertFalse(nodePush2.isEquivalentTo(nodePush1));
        Assert.assertTrue(nodePush2.isEquivalentTo(nodePush2));

    }
}
