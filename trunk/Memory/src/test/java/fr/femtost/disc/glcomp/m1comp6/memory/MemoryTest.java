package fr.femtost.disc.glcomp.m1comp6.memory;

import fr.femtost.disc.glcomp.m1comp6.ast.common.Kind;
import fr.femtost.disc.glcomp.m1comp6.ast.common.Type;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MemoryTest {
    private SymbolTable symbolTable;
    private Memory memory;

    @Before
    public void initialize() throws Exception {
        symbolTable = new SymbolTable();
        memory = new Memory(symbolTable);

        SymbolNode symbolNode1 = new SymbolNode("C@global", Kind.VARIABLE, null);
        SymbolNode symbolNode2 = new SymbolNode("x@global", Kind.VARIABLE, Type.INTEGER);
        SymbolNode symbolNode3 = new SymbolNode("y@global", Kind.CONSTANT, Type.INTEGER);
        SymbolNode symbolNode4 = new SymbolNode("x@main", Kind.VARIABLE, Type.INTEGER);
        SymbolNode symbolNode5 = new SymbolNode("y@main", Kind.VARIABLE, Type.BOOLEAN);
        SymbolNode symbolNode6 = new SymbolNode("z@main", Kind.VARIABLE, Type.INTEGER);
        SymbolNode symbolNode7 = new SymbolNode("t@main", Kind.ARRAY, Type.INTEGER);
        SymbolNode symbolNode8 = new SymbolNode("u@main", Kind.ARRAY, Type.INTEGER);
        SymbolNode symbolNode9 = new SymbolNode("f:int", Kind.METHOD, Type.INTEGER);

        symbolTable.put(symbolNode1);
        symbolTable.put(symbolNode2);
        symbolTable.put(symbolNode3);
        symbolTable.put(symbolNode4);
        symbolTable.put(symbolNode5);
        symbolTable.put(symbolNode6);
        symbolTable.put(symbolNode7);
        symbolTable.put(symbolNode8);
        symbolTable.put(symbolNode9);
    }

    @Test
    public void pushTest() throws Exception {
        ValueNode<Integer> valueNode = memory.push(1);

        Assert.assertNull(valueNode.getIdent());
        Assert.assertEquals(valueNode.getKind(), Kind.CONSTANT);
        Assert.assertNull(valueNode.getType());
    }

    @Test
    public void popTest() throws Exception {
        ValueNode<Integer> valueNode1 = memory.push(1);
        ValueNode<Integer> valueNode2 = memory.declVar("x@global", 1);

        Assert.assertEquals(memory.pop(), valueNode2);
        Assert.assertEquals(memory.pop(), valueNode1);
    }

    @Test
    public void popArrayTest() throws Exception {
        ValueNode<Integer> valueNode1 = memory.push(1);
        ValueNode<Integer> valueNode2 = memory.declArray("t@main", 2);

        Assert.assertEquals(memory.pop(), valueNode2);
        Assert.assertEquals(memory.pop(), valueNode1);
    }

    @Test
    public void popValueTest() throws Exception {
        ValueNode<Integer> valueNode = memory.push(1);

        Assert.assertEquals(memory.popValue(), valueNode.getValue());
    }

    @Test
    public void peekTest() throws Exception {
        ValueNode<Integer> valueNode = memory.push(1);

        Assert.assertEquals(memory.peek(), valueNode);
    }

    @Test
    public void peekValueTest() throws Exception {
        ValueNode<Integer> valueNode = memory.push(1);

        Assert.assertEquals(memory.peekValue(), valueNode.getValue());
    }

    @Test
    public void swapTest() throws Exception {
        ValueNode<Integer> valueNode1 = memory.push(1);
        ValueNode<Integer> valueNode2 = memory.push(2);

        Assert.assertEquals(memory.peek(), valueNode2);

        memory.swap();

        Assert.assertEquals(memory.peek(), valueNode1);
    }

    @Test
    public void declVarTest() throws Exception {
        ValueNode<Integer> valueNode = memory.declVar("x@global", 1);

        Assert.assertEquals(valueNode.getIdent(), "x@global");
        Assert.assertEquals(valueNode.getKind(), Kind.VARIABLE);
        Assert.assertEquals(valueNode.getType(), Type.INTEGER);
    }

    @Test(expected = UnknownSymbolException.class)
    public void declVarUnknownSymbolTest() throws Exception {
        memory.declVar("w@main", 1);
    }

    @Test(expected = WrongKindException.class)
    public void declVarWrongKindTest() throws Exception {
        memory.declVar("y@global", 1);
    }

    @Test(expected = InvalidTypeException.class)
    public void declVarInvalidTypeTest() throws Exception {
        memory.declVar("x@main", true);
    }

    @Test
    public void declCstTest() throws Exception {
        ValueNode<Integer> valueNode = memory.declCst("y@global", 1);

        Assert.assertEquals(valueNode.getIdent(), "y@global");
        Assert.assertEquals(valueNode.getKind(), Kind.CONSTANT);
        Assert.assertEquals(valueNode.getType(), Type.INTEGER);
    }

    @Test(expected = UnknownSymbolException.class)
    public void declCstUnknownSymbolTest() throws Exception {
        memory.declCst("w@main", 1);
    }

    @Test(expected = WrongKindException.class)
    public void declCstWrongKindTest() throws Exception {
        memory.declCst("x@global", 1);
    }

    @Test(expected = InvalidTypeException.class)
    public void declCstInvalidTypeTest() throws Exception {
        memory.declCst("y@global", true);
    }

    @Test
    public void declArrayTest() throws Exception {
        ValueNode<Integer> valueNode = memory.declArray("t@main", 5);

        Assert.assertEquals(valueNode.getIdent(), "t@main");
        Assert.assertEquals(valueNode.getKind(), Kind.ARRAY);
        Assert.assertEquals(valueNode.getType(), Type.INTEGER);
    }

    @Test(expected = UnknownSymbolException.class)
    public void declArrayUnknownSymbolTest() throws Exception {
        memory.declArray("w@main", 5);
    }

    @Test(expected = WrongKindException.class)
    public void declArrayWrongKindTest() throws Exception {
        memory.declArray("x@global", 5);
    }

    @Test
    public void declMethodTest() throws Exception {
        ValueNode<Integer> valueNode = memory.declMethod("f:int", 1);

        Assert.assertEquals(valueNode.getIdent(), "f:int");
        Assert.assertEquals(valueNode.getKind(), Kind.METHOD);
        Assert.assertEquals(valueNode.getType(), Type.INTEGER);
    }

    @Test(expected = UnknownSymbolException.class)
    public void declMethodUnknownSymbolTest() throws Exception {
        memory.declMethod("g:boolean", 1);
    }

    @Test(expected = WrongKindException.class)
    public void declMethodWrongKindTest() throws Exception {
        memory.declMethod("x@global", 1);
    }

    @Test
    public void removeDeclTest() throws Exception {
        ValueNode<Integer> valueNode1 = memory.declVar("x@global", 1);
        ValueNode<Integer> valueNode2 = memory.declArray("t@main", 5);

        Assert.assertEquals(memory.removeDecl("x@global"), valueNode1);
        Assert.assertEquals(memory.removeDecl("t@main"), valueNode2);
    }

    @Test(expected = UnknownSymbolException.class)
    public void removeDeclUnknownSymbolTest() throws Exception {
        memory.removeDecl("w@main");
    }

    @Test(expected = NotFoundValueException.class)
    public void removeDeclNotFoundValueTest() throws Exception {
        memory.removeDecl("x@global");
    }

    @Test
    public void identValTest() throws Exception {
        ValueNode<Integer> valueNode = memory.push(1);

        Assert.assertEquals(memory.identVal("x@main", 0), valueNode);
    }

    @Test(expected = NotFoundValueException.class)
    public void identValNotFoundValueTest() throws Exception {
        ValueNode<Integer> valueNode = memory.push(1);

        memory.identVal("x@main", 1);
    }

    @Test(expected = UnknownSymbolException.class)
    public void identValUnknownSymbolTest() throws Exception {
        ValueNode<Integer> valueNode = memory.push(1);

        memory.identVal("w@main", 0);
    }

    @Test(expected = InvalidTypeException.class)
    public void identValInvalidTypeTest() throws Exception {
        ValueNode<Integer> valueNode = memory.push(1);

        memory.identVal("y@main", 0);
    }

    @Test
    public void assignValTest() throws Exception {
        ValueNode<Integer> valueNode1 = memory.declVar("x@global", 1);
        ValueNode<Integer> valueNode2 = memory.declCst("y@global", null);
        ValueNode<Integer> valueNode3 = memory.declArray("t@main", 5);
        ValueNode<Integer> valueNode4 = memory.declArray("u@main", 5);

        Assert.assertEquals((int) valueNode1.getValue(), 1);
        Assert.assertEquals(memory.assignVal("x@global", 2), valueNode1);
        Assert.assertEquals((int) valueNode1.getValue(), 2);

        Assert.assertNull(valueNode2.getValue());
        Assert.assertEquals(memory.assignVal("y@global", 1), valueNode2);
        Assert.assertEquals((int) valueNode2.getValue(), 1);

        Assert.assertEquals(memory.assignVal("t@main", valueNode4.getValue()), valueNode3);
        Assert.assertEquals(valueNode3.getValue(), valueNode4.getValue());
    }

    @Test(expected = InvalidTypeException.class)
    public void assignValInvalidTypeTest() throws Exception {
        memory.declVar("x@global", 1);
        memory.assignVal("x@global", true);
    }

    @Test(expected = UnknownSymbolException.class)
    public void assignValUnknownSymbolTest() throws Exception {
        memory.assignVal("w@main", 1);
    }

    @Test(expected = NotFoundValueException.class)
    public void assignValNotFoundValueTest() throws Exception {
        memory.assignVal("x@main", 1);
    }

    @Test(expected = InvalidConstantAssignmentException.class)
    public void assignValInvalidConstantAssignmentTest() throws Exception {
        memory.declCst("y@global", 1);
        memory.assignVal("y@global", 2);
    }

    @Test
    public void assignValArrayTest() throws Exception {
        ValueNode<Integer> valueNode = memory.declArray("t@main", 5);

        Assert.assertEquals(memory.assignValArray("t@main", 0, 0), valueNode);
    }

    @Test(expected = ArrayAccessException.class)
    public void assignValArrayAccessTest() throws Exception {
        ValueNode<Integer> valueNode = memory.declVar("x@global", 1);

        memory.assignValArray("x@global", 0, 0);
    }

    @Test(expected = InvalidTypeException.class)
    public void assignValArrayInvalidTypeTest() throws Exception {
        ValueNode<Integer> valueNode = memory.declArray("t@main", 5);

        memory.assignValArray("t@main", true, 0);
    }

    @Test
    public void getValueTest() throws Exception {
        Integer value = 1;

        memory.declVar("x@global", value);

        Assert.assertEquals(memory.getValue("x@global"), value);
    }

    @Test(expected = UnknownSymbolException.class)
    public void getValueUnknownSymbolTest() throws Exception {
        memory.getValue("w@main");
    }

    @Test(expected = NotFoundValueException.class)
    public void getValueNotFoundValueTest() throws Exception {
        memory.getValue("x@main");
    }

    @Test
    public void getValueArrayTest() throws Exception {
        Integer value = 1;
        Integer index = 0;

        memory.declArray("t@main", 5);
        memory.assignValArray("t@main", value, index);

        Assert.assertEquals(memory.getValueArray("t@main", index), value);
    }

    @Test(expected = ArrayAccessException.class)
    public void getValueArrayAccessTest() throws Exception {
        memory.getValueArray("x@global", 0);
    }

    @Test
    public void getClassNameTest() throws Exception {
        memory.declVar("C@global", 5);

        Assert.assertEquals(memory.getClassName(), "C@global");
    }

    @Test(expected = MemoryException.class)
    public void getClassNameStackEmptyTest() throws Exception {
        memory.getClassName();
    }
}
