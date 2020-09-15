package fr.femtost.disc.glcomp.m1comp6.memory;

import fr.femtost.disc.glcomp.m1comp6.ast.common.Kind;
import fr.femtost.disc.glcomp.m1comp6.ast.common.Type;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class StackTest {
    private Stack stack;

    @Before
    public void initialize() {
        stack = new Stack();
    }

    @Test
    public void pushTest() throws StackException {
        ValueNode value1 = Mockito.mock(ValueNode.class);
        ValueNode value2 = Mockito.mock(ValueNode.class);

        stack.push(value1);

        Assert.assertFalse(stack.isEmpty());
        Assert.assertEquals(stack.peek(), value1);

        stack.push(value2);

        Assert.assertEquals(stack.peek(), value2);
    }

    @Test
    public void popTest() throws StackException {
        ValueNode value = Mockito.mock(ValueNode.class);

        stack.push(value);

        ValueNode pop = stack.pop();

        Assert.assertTrue(stack.isEmpty());
        Assert.assertEquals(pop, value);
    }

    @Test(expected = StackException.class)
    public void popEmptyTest() throws StackException {
        Assert.assertTrue(stack.isEmpty());

        stack.pop();
    }

    @Test
    public void peekTest() throws StackException {
        ValueNode value = Mockito.mock(ValueNode.class);

        stack.push(value);

        ValueNode peek = stack.peek();

        Assert.assertFalse(stack.isEmpty());
        Assert.assertEquals(peek, value);
    }

    @Test(expected = StackException.class)
    public void peekEmptyTest() throws StackException {
        Assert.assertTrue(stack.isEmpty());

        stack.peek();
    }

    @Test
    public void isEmptyTest() {
        Assert.assertTrue(stack.isEmpty());
    }

    @Test
    public void swapTest() throws StackException {
        ValueNode value1 = Mockito.mock(ValueNode.class);
        ValueNode value2 = Mockito.mock(ValueNode.class);
        ValueNode value3 = Mockito.mock(ValueNode.class);

        stack.push(value1);
        stack.push(value2);
        stack.push(value3);

        Assert.assertFalse(stack.isEmpty());
        Assert.assertEquals(stack.peek(), value3);

        stack.swap();

        Assert.assertEquals(stack.peek(), value2);

        Assert.assertEquals(stack.pop(), value2);
        Assert.assertEquals(stack.pop(), value3);
        Assert.assertEquals(stack.pop(), value1);

        Assert.assertTrue(stack.isEmpty());
    }

    @Test(expected = StackException.class)
    public void swapOneElementTest() throws StackException {
        ValueNode value = Mockito.mock(ValueNode.class);

        stack.push(value);

        Assert.assertFalse(stack.isEmpty());

        stack.swap();

    }

    @Test(expected = StackException.class)
    public void swapEmptyTest() throws StackException {
        Assert.assertTrue(stack.isEmpty());

        stack.swap();
    }

    @Test
    public void getValueAtTest() throws NotFoundValueException {
        ValueNode value1 = Mockito.mock(ValueNode.class);
        ValueNode value2 = Mockito.mock(ValueNode.class);
        ValueNode value3 = Mockito.mock(ValueNode.class);

        stack.push(value1);
        stack.push(value2);
        stack.push(value3);

        Assert.assertFalse(stack.isEmpty());

        Assert.assertEquals(stack.getValueAt(0), value3);
        Assert.assertEquals(stack.getValueAt(1), value2);
        Assert.assertEquals(stack.getValueAt(2), value1);
    }

    @Test(expected = NotFoundValueException.class)
    public void getValueAtEmptyTest() throws NotFoundValueException {
        Assert.assertTrue(stack.isEmpty());

        stack.getValueAt(0);
    }

    @Test(expected = NotFoundValueException.class)
    public void getValueAtInvlidTest1() throws NotFoundValueException {
        ValueNode value1 = Mockito.mock(ValueNode.class);
        ValueNode value2 = Mockito.mock(ValueNode.class);
        ValueNode value3 = Mockito.mock(ValueNode.class);

        stack.push(value1);
        stack.push(value2);
        stack.push(value3);

        Assert.assertFalse(stack.isEmpty());

        stack.getValueAt(-1);
    }

    @Test(expected = NotFoundValueException.class)
    public void getValueAtInvlidTest2() throws NotFoundValueException {
        ValueNode value1 = Mockito.mock(ValueNode.class);
        ValueNode value2 = Mockito.mock(ValueNode.class);
        ValueNode value3 = Mockito.mock(ValueNode.class);

        stack.push(value1);
        stack.push(value2);
        stack.push(value3);

        Assert.assertFalse(stack.isEmpty());

        stack.getValueAt(3);
    }

    @Test
    public void getFirstValueOfTest() throws InvalidTypeException, NotFoundValueException {
        SymbolNode symbolNode1 = new SymbolNode("x@global", Kind.VARIABLE, Type.INTEGER);
        SymbolNode symbolNode2 = new SymbolNode("y@global", Kind.VARIABLE, Type.INTEGER);

        ValueNode value1 = new ValueNode(4, symbolNode1);
        ValueNode value2 = new ValueNode(5, symbolNode2);
        ValueNode value3 = new ValueNode(6, symbolNode1);

        stack.push(value1);
        stack.push(value2);
        stack.push(value3);

        Assert.assertFalse(stack.isEmpty());

        Assert.assertEquals(stack.getFirstValueOf(symbolNode1), value3);
    }

    @Test(expected = NotFoundValueException.class)
    public void getFirstValueOfNotFoundTest() throws InvalidTypeException, NotFoundValueException {
        SymbolNode symbolNode1 = new SymbolNode("x@global", Kind.VARIABLE, Type.INTEGER);
        SymbolNode symbolNode2 = new SymbolNode("y@global", Kind.VARIABLE, Type.INTEGER);

        ValueNode value1 = new ValueNode(4, symbolNode1);
        ValueNode value2 = new ValueNode(6, symbolNode1);

        stack.push(value1);
        stack.push(value2);

        Assert.assertFalse(stack.isEmpty());

        stack.getFirstValueOf(symbolNode2);
    }

    @Test
    public void removeFirstValueOfTest() throws InvalidTypeException, NotFoundValueException {
        SymbolNode symbolNode1 = new SymbolNode("x@global", Kind.VARIABLE, Type.INTEGER);
        SymbolNode symbolNode2 = new SymbolNode("y@global", Kind.VARIABLE, Type.INTEGER);

        ValueNode value1 = new ValueNode(4, symbolNode1);
        ValueNode value2 = new ValueNode(5, symbolNode2);
        ValueNode value3 = new ValueNode(6, symbolNode1);

        stack.push(value1);
        stack.push(value2);
        stack.push(value3);

        Assert.assertFalse(stack.isEmpty());

        Assert.assertEquals(stack.removeFirstValueOf(symbolNode1), value3);
        Assert.assertEquals(stack.removeFirstValueOf(symbolNode1), value1);
    }

    @Test(expected = NotFoundValueException.class)
    public void removeFirstValueOfNotFoundTest() throws InvalidTypeException, NotFoundValueException {
        SymbolNode symbolNode1 = new SymbolNode("x@global", Kind.VARIABLE, Type.INTEGER);
        SymbolNode symbolNode2 = new SymbolNode("y@global", Kind.VARIABLE, Type.INTEGER);

        ValueNode value1 = new ValueNode(4, symbolNode1);
        ValueNode value2 = new ValueNode(6, symbolNode1);

        stack.push(value1);
        stack.push(value2);

        Assert.assertFalse(stack.isEmpty());

        stack.removeFirstValueOf(symbolNode2);
    }

    @Test
    public void getBottomValueTest() throws InvalidTypeException, NotFoundValueException {
        SymbolNode symbolNode1 = new SymbolNode("x@global", Kind.VARIABLE, Type.INTEGER);
        SymbolNode symbolNode2 = new SymbolNode("y@global", Kind.VARIABLE, Type.INTEGER);

        ValueNode value1 = new ValueNode(4, symbolNode1);
        ValueNode value2 = new ValueNode(5, symbolNode2);
        ValueNode value3 = new ValueNode(6, symbolNode1);

        stack.push(value1);
        stack.push(value2);
        stack.push(value3);

        Assert.assertFalse(stack.isEmpty());

        Assert.assertEquals(stack.getBottomValue(), value1);
    }

    @Test(expected = NotFoundValueException.class)
    public void getBottomValueEmptyTest() throws InvalidTypeException, NotFoundValueException {
        Assert.assertTrue(stack.isEmpty());

        stack.getBottomValue();
    }
}
