package fr.femtost.disc.glcomp.m1comp6.memory;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import fr.femtost.disc.glcomp.m1comp6.ast.common.Kind;
import fr.femtost.disc.glcomp.m1comp6.ast.common.Type;

public class SymbolNodeTest {

    @Test
    public void getIdentTest() {
        String ident = "x";

        SymbolNode symbolNode = new SymbolNode(ident, Kind.VARIABLE, Type.INTEGER);

        Assert.assertEquals(symbolNode.getIdent(), ident);
    }

    @Test
    public void getKindTest() {
        Kind kind = Kind.VARIABLE;

        SymbolNode symbolNode = new SymbolNode("x@global", kind, Type.INTEGER);

        Assert.assertEquals(symbolNode.getKind(), kind);
    }

    @Test
    public void getTypeTest() {
        Type type = Type.INTEGER;

        SymbolNode symbolNode = new SymbolNode("x@global", Kind.VARIABLE, type);

        Assert.assertEquals(symbolNode.getType(), type);
    }

    @Test
    public void getValuesTest() throws InvalidTypeException {
        SymbolNode symbolNode = new SymbolNode("x@global", Kind.VARIABLE, Type.INTEGER);

        ValueNode<Integer> valueNode1 = symbolNode.addValue(0);
        ValueNode<Integer> valueNode2 = symbolNode.addValue(1);
        ValueNode<Integer> valueNode3 = symbolNode.addValue(2);

        List<ValueNode> values = symbolNode.getValues();

        Assert.assertEquals(values.get(0), valueNode1);
        Assert.assertEquals(values.get(1), valueNode2);
        Assert.assertEquals(values.get(2), valueNode3);
    }

    @Test
    public void hasIdentTest() {
        SymbolNode symbolNode = new SymbolNode("x@global", Kind.VARIABLE, Type.INTEGER);

        Assert.assertTrue(symbolNode.hasIdent("x@global"));
        Assert.assertFalse(symbolNode.hasIdent("y@global"));
        Assert.assertFalse(symbolNode.hasIdent("x@main"));
    }

    @Test
    public void hasValuesTest() throws InvalidTypeException {
        SymbolNode symbolNode = new SymbolNode("x@global", Kind.VARIABLE, Type.INTEGER);

        Assert.assertFalse(symbolNode.hasValues());

        symbolNode.addValue(0);

        Assert.assertTrue(symbolNode.hasValues());
    }

    @Test
    public void addValueTest() throws InvalidTypeException, WrongSymbolException {
        Integer value = 0;

        SymbolNode symbolNode = new SymbolNode("x@global", Kind.VARIABLE, Type.INTEGER);

        ValueNode<Integer> valueNode1 = symbolNode.addValue(value);

        Assert.assertEquals(symbolNode.getValues().get(0), valueNode1);
        Assert.assertEquals(valueNode1.getValue(), value);
        Assert.assertEquals(valueNode1.getParent(), symbolNode);

        ValueNode<Integer> valueNode2 = new ValueNode<>(value, symbolNode);

        symbolNode.addValue(valueNode2);

        Assert.assertEquals(symbolNode.getValues().get(1), valueNode2);
        Assert.assertEquals(valueNode2.getValue(), value);
        Assert.assertEquals(valueNode2.getParent(), symbolNode);
    }

    @Test(expected = InvalidTypeException.class)
    public void addValueInvalidTypeTest() throws InvalidTypeException {
        SymbolNode symbolNode = new SymbolNode("x@global", Kind.VARIABLE, Type.INTEGER);

        symbolNode.addValue(true);
    }

    @Test(expected = WrongSymbolException.class)
    public void addValueWrongSymbolTest() throws InvalidTypeException, WrongSymbolException {
        SymbolNode symbolNode = new SymbolNode("x@global", Kind.VARIABLE, Type.INTEGER);

        ValueNode<Integer> valueNode = new ValueNode<>(0);

        symbolNode.addValue(valueNode);
    }

    @Test
    public void removeValueTest() throws InvalidTypeException {
        SymbolNode symbolNode = new SymbolNode("x@global", Kind.VARIABLE, Type.INTEGER);

        ValueNode<Integer> valueNode1 = symbolNode.addValue(0);
        ValueNode<Integer> valueNode2 = symbolNode.addValue(1);

        Assert.assertEquals(symbolNode.getValues().get(0), valueNode1);
        Assert.assertEquals(symbolNode.removeValue(valueNode1), valueNode1);
        Assert.assertEquals(symbolNode.getValues().get(0), valueNode2);
    }

    @Test
    public void equalsTest() {
        SymbolNode symbolNode1 = new SymbolNode("x@global", Kind.VARIABLE, Type.INTEGER);
        SymbolNode symbolNode2 = new SymbolNode("x@global", Kind.VARIABLE, Type.INTEGER);
        SymbolNode symbolNode3 = new SymbolNode("y@global", Kind.VARIABLE, Type.INTEGER);

        Assert.assertNotEquals(symbolNode1, 0);
        Assert.assertNotEquals(symbolNode1, symbolNode3);
        Assert.assertEquals(symbolNode1, symbolNode2);
    }
}
