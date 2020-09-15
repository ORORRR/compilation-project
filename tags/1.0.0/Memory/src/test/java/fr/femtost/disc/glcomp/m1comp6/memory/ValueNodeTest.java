package fr.femtost.disc.glcomp.m1comp6.memory;

import fr.femtost.disc.glcomp.m1comp6.ast.common.Kind;
import fr.femtost.disc.glcomp.m1comp6.ast.common.Type;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class ValueNodeTest {

    @Test
    public void constructTest() throws InvalidTypeException {
        Integer value = 0;
        SymbolNode parent = Mockito.mock(SymbolNode.class);

        Mockito.when(parent.getType()).thenReturn(Type.INTEGER);

        ValueNode<Integer> valueNode1 = new ValueNode<Integer>(value, parent);
        ValueNode<Integer> valueNode2 = new ValueNode<Integer>(value);
        ValueNode<Integer> valueNode3 = new ValueNode<Integer>();
    }

    @Test(expected = InvalidTypeException.class)
    public void constructInvalidTypeTest() throws InvalidTypeException {
        Integer value = 0;
        SymbolNode parent = Mockito.mock(SymbolNode.class);

        Mockito.when(parent.getType()).thenReturn(Type.BOOLEAN);

        ValueNode<Integer> valueNode = new ValueNode<Integer>(value, parent);
    }

    @Test
    public void hasIdentTest() throws InvalidTypeException {
        Integer value = 0;
        SymbolNode parent = Mockito.mock(SymbolNode.class);

        Mockito.when(parent.getType()).thenReturn(Type.INTEGER);
        Mockito.when(parent.hasIdent("x", "global")).thenReturn(true);

        ValueNode<Integer> valueNode1 = new ValueNode<Integer>(value, parent);
        ValueNode<Integer> valueNode2 = new ValueNode<Integer>(value);

        Assert.assertTrue(valueNode1.hasIdent("x", "global"));
        Assert.assertFalse(valueNode1.hasIdent("y", "global"));
        Assert.assertFalse(valueNode2.hasIdent("x", "global"));
    }

    @Test
    public void getValueTest() throws InvalidTypeException {
        Integer value = 0;
        SymbolNode parent = Mockito.mock(SymbolNode.class);

        Mockito.when(parent.getType()).thenReturn(Type.INTEGER);

        ValueNode<Integer> valueNode = new ValueNode<Integer>(value, parent);

        Assert.assertEquals(valueNode.getValue(), value);
    }

    @Test
    public void setValueTest() throws InvalidTypeException {
        Integer value1 = 0;
        Integer value2 = 1;
        SymbolNode parent = Mockito.mock(SymbolNode.class);

        Mockito.when(parent.getType()).thenReturn(Type.INTEGER);

        ValueNode<Integer> valueNode = new ValueNode<Integer>(value1, parent);

        Assert.assertEquals(valueNode.getValue(), value1);

        valueNode.setValue(value2);

        Assert.assertEquals(valueNode.getValue(), value2);
    }

    @Test(expected = InvalidTypeException.class)
    public void setValueInvalidTypeTest() throws InvalidTypeException {
        Integer value1 = 0;
        Boolean value2 = true;
        SymbolNode parent = Mockito.mock(SymbolNode.class);

        Mockito.when(parent.getType()).thenReturn(Type.INTEGER);

        ValueNode valueNode = new ValueNode(value1, parent);

        Assert.assertEquals(valueNode.getValue(), value1);

        valueNode.setValue(value2);
    }

    @Test
    public void getParentTest() throws InvalidTypeException {
        Integer value = 0;
        SymbolNode parent = Mockito.mock(SymbolNode.class);

        Mockito.when(parent.getType()).thenReturn(Type.INTEGER);

        ValueNode<Integer> valueNode = new ValueNode<Integer>(value, parent);

        Assert.assertEquals(valueNode.getParent(), parent);
    }

    @Test
    public void setParentTest() throws InvalidTypeException {
        Integer value = 0;
        SymbolNode parent1 = Mockito.mock(SymbolNode.class);
        SymbolNode parent2 = Mockito.mock(SymbolNode.class);

        Mockito.when(parent1.getType()).thenReturn(Type.INTEGER);
        Mockito.when(parent2.getType()).thenReturn(Type.INTEGER);

        ValueNode<Integer> valueNode = new ValueNode<Integer>(value, parent1);

        Assert.assertEquals(valueNode.getParent(), parent1);

        valueNode.setParent(parent2);

        Assert.assertEquals(valueNode.getParent(), parent2);
    }

    @Test(expected = InvalidTypeException.class)
    public void setParentInvalidTypeTest() throws InvalidTypeException {
        Integer value = 0;
        SymbolNode parent1 = Mockito.mock(SymbolNode.class);
        SymbolNode parent2 = Mockito.mock(SymbolNode.class);

        Mockito.when(parent1.getType()).thenReturn(Type.INTEGER);
        Mockito.when(parent2.getType()).thenReturn(Type.BOOLEAN);

        ValueNode<Integer> valueNode = new ValueNode<Integer>(value, parent1);

        Assert.assertEquals(valueNode.getParent(), parent1);

        valueNode.setParent(parent2);
    }

    @Test
    public void getIdentTest() throws InvalidTypeException {
        Integer value = 0;
        String ident = "x";
        SymbolNode parent = Mockito.mock(SymbolNode.class);

        Mockito.when(parent.getIdent()).thenReturn(ident);
        Mockito.when(parent.getScope()).thenReturn("global");
        Mockito.when(parent.getKind()).thenReturn(Kind.VARIABLE);
        Mockito.when(parent.getType()).thenReturn(Type.INTEGER);

        ValueNode<Integer> valueNode1 = new ValueNode<Integer>(value, parent);
        ValueNode<Integer> valueNode2 = new ValueNode<Integer>(value);

        Assert.assertEquals(valueNode1.getIdent(), ident);
        Assert.assertNull(valueNode2.getIdent());
    }

    @Test
    public void getScopeTest() throws InvalidTypeException {
        Integer value = 0;
        String scope = "global";
        SymbolNode parent = Mockito.mock(SymbolNode.class);

        Mockito.when(parent.getIdent()).thenReturn("x");
        Mockito.when(parent.getScope()).thenReturn(scope);
        Mockito.when(parent.getKind()).thenReturn(Kind.VARIABLE);
        Mockito.when(parent.getType()).thenReturn(Type.INTEGER);

        ValueNode<Integer> valueNode1 = new ValueNode<Integer>(value, parent);
        ValueNode<Integer> valueNode2 = new ValueNode<Integer>(value);

        Assert.assertEquals(valueNode1.getScope(), scope);
        Assert.assertNull(valueNode2.getScope());
    }

    @Test
    public void getKindTest() throws InvalidTypeException {
        Integer value = 0;
        Kind kind = Kind.VARIABLE;
        SymbolNode parent = Mockito.mock(SymbolNode.class);

        Mockito.when(parent.getIdent()).thenReturn("x");
        Mockito.when(parent.getScope()).thenReturn("global");
        Mockito.when(parent.getKind()).thenReturn(kind);
        Mockito.when(parent.getType()).thenReturn(Type.INTEGER);

        ValueNode<Integer> valueNode1 = new ValueNode<Integer>(value, parent);
        ValueNode<Integer> valueNode2 = new ValueNode<Integer>(value);

        Assert.assertEquals(valueNode1.getKind(), kind);
        Assert.assertEquals(valueNode2.getKind(), Kind.CONSTANT);
    }

    @Test
    public void getTypeTest() throws InvalidTypeException {
        Integer value = 0;
        Type type = Type.INTEGER;
        SymbolNode parent = Mockito.mock(SymbolNode.class);

        Mockito.when(parent.getIdent()).thenReturn("x");
        Mockito.when(parent.getScope()).thenReturn("global");
        Mockito.when(parent.getKind()).thenReturn(Kind.VARIABLE);
        Mockito.when(parent.getType()).thenReturn(type);

        ValueNode<Integer> valueNode1 = new ValueNode<Integer>(value, parent);
        ValueNode<Integer> valueNode2 = new ValueNode<Integer>(value);

        Assert.assertEquals(valueNode1.getType(), type);
        Assert.assertNull(valueNode2.getType());
    }
}
