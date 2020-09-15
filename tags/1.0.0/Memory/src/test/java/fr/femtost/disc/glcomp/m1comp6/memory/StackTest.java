package fr.femtost.disc.glcomp.m1comp6.memory;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class StackTest {

    @Test
    public void pushTest() throws StackException{
        Stack stack = new Stack();

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
        Stack stack = new Stack();

        ValueNode value = Mockito.mock(ValueNode.class);

        stack.push(value);

        ValueNode pop = stack.pop();

        Assert.assertTrue(stack.isEmpty());
        Assert.assertEquals(pop, value);
    }

    @Test (expected = StackException.class)
    public void peekEmptyTest()  throws StackException{
        Stack stack = new Stack();

        Assert.assertTrue(stack.isEmpty());
        stack.peek();
    }

    @Test (expected = StackException.class)
    public void popEmptyTest() throws StackException {
        Stack stack = new Stack();

        Assert.assertTrue(stack.isEmpty());
        stack.pop();
    }

    @Test
    public void isEmptyTest() {
        Stack stack = new Stack();

        Assert.assertTrue(stack.isEmpty());
    }

    @Test
    public void swapTest()  throws StackException {
        Stack stack  = new Stack();

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

    @Test (expected = StackException.class)
    public void swapOneElementTest()  throws StackException {
        Stack stack  = new Stack();

        ValueNode value = Mockito.mock(ValueNode.class);

        stack.push(value);

        Assert.assertFalse(stack.isEmpty());

        stack.swap();

    }

    @Test (expected = StackException.class)
    public void swapEmptyTest()  throws StackException {
        Stack stack = new Stack();

        Assert.assertTrue(stack.isEmpty());

        stack.swap();
    }

    @Test
    public void getValueAtTest() {
        Stack stack = new Stack();

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

        Assert.assertNull(stack.getValueAt(-1));
        Assert.assertNull(stack.getValueAt(3));
    }

    @Test
    public void getValueAtEmptyTest() {
        Stack stack = new Stack();

        Assert.assertTrue(stack.isEmpty());

        Assert.assertNull(stack.getValueAt(0));
    }

    @Test
    public void getFirstValueOfTest() {
        Stack stack = new Stack();

        ValueNode value1 = Mockito.mock(ValueNode.class);
        ValueNode value2 = Mockito.mock(ValueNode.class);
        ValueNode value3 = Mockito.mock(ValueNode.class);

        Mockito.when(value1.hasIdent("x", "global")).thenReturn(true);
        Mockito.when(value2.hasIdent("x", "global")).thenReturn(true);
        Mockito.when(value3.hasIdent("x", "global")).thenReturn(false);

        Mockito.when(value1.hasIdent("y", "global")).thenReturn(false);
        Mockito.when(value2.hasIdent("y", "global")).thenReturn(false);
        Mockito.when(value3.hasIdent("y", "global")).thenReturn(false);

        stack.push(value1);
        stack.push(value2);
        stack.push(value3);

        Assert.assertFalse(stack.isEmpty());

        Assert.assertEquals(stack.getFirstValueOf("x", "global"), value2);
        Assert.assertNull(stack.getFirstValueOf("y", "global"));
    }

    @Test
    public void removeFirstValueOfTest() {
        Stack stack = new Stack();

        ValueNode value1 = Mockito.mock(ValueNode.class);
        ValueNode value2 = Mockito.mock(ValueNode.class);
        ValueNode value3 = Mockito.mock(ValueNode.class);

        Mockito.when(value1.hasIdent("x", "global")).thenReturn(true);
        Mockito.when(value2.hasIdent("x", "global")).thenReturn(true);
        Mockito.when(value3.hasIdent("x", "global")).thenReturn(false);

        Mockito.when(value1.hasIdent("y", "global")).thenReturn(false);
        Mockito.when(value2.hasIdent("y", "global")).thenReturn(false);
        Mockito.when(value3.hasIdent("y", "global")).thenReturn(false);

        stack.push(value1);
        stack.push(value2);
        stack.push(value3);

        Assert.assertFalse(stack.isEmpty());

        Assert.assertEquals(stack.removeFirstValueOf("x", "global"), value2);
        Assert.assertEquals(stack.removeFirstValueOf("x", "global"), value1);
        Assert.assertNull(stack.removeFirstValueOf("y", "global"));
    }
}
