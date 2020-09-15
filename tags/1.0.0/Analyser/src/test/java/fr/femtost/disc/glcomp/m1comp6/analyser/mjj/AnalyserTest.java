package fr.femtost.disc.glcomp.m1comp6.analyser.mjj;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import fr.femtost.disc.glcomp.m1comp6.ast.common.Node;
import fr.femtost.disc.glcomp.m1comp6.ast.mjj.*;

public class AnalyserTest {

    @Test
    public void parseStringTest() throws AnalyserException {
        Analyser analyser = new Analyser();

        NodeMiniJaja root = analyser.parseString("class MyClass { int x = 0; main { boolean y = true; } }");

        Assert.assertTrue(root instanceof NodeClass);

        List<Node> rootChildren = root.getChildren();

        Assert.assertTrue(rootChildren.get(0) instanceof NodeIdent);
        Assert.assertTrue(rootChildren.get(1) instanceof NodeDecls);
        Assert.assertTrue(rootChildren.get(2) instanceof NodeMain);

        List<Node> declsChildren = rootChildren.get(1).getChildren();
        List<Node> mainChildren = rootChildren.get(2).getChildren();

        Assert.assertTrue(declsChildren.get(0) instanceof NodeVar);
        Assert.assertTrue(declsChildren.get(1) instanceof NodeVnil);

        Assert.assertTrue(mainChildren.get(0) instanceof NodeVars);
        Assert.assertTrue(mainChildren.get(1) instanceof NodeInil);

        List<Node> varChildren = declsChildren.get(0).getChildren();

        Assert.assertTrue(varChildren.get(0) instanceof NodeInteger);
        Assert.assertTrue(varChildren.get(1) instanceof NodeIdent);
        Assert.assertTrue(varChildren.get(2) instanceof NodeNumber);

        List<Node> varsChildren = mainChildren.get(0).getChildren();

        Assert.assertTrue(varsChildren.get(0) instanceof NodeVar);
        Assert.assertTrue(varsChildren.get(1) instanceof NodeVnil);

        List<Node> varChildren1 = varsChildren.get(0).getChildren();

        Assert.assertTrue(varChildren1.get(0) instanceof NodeBoolean);
        Assert.assertTrue(varChildren1.get(1) instanceof NodeIdent);
        Assert.assertTrue(varChildren1.get(2) instanceof NodeTrue);
    }
}
