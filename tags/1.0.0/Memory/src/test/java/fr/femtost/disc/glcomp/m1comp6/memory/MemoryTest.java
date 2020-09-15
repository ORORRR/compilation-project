package fr.femtost.disc.glcomp.m1comp6.memory;

import fr.femtost.disc.glcomp.m1comp6.ast.common.Kind;
import fr.femtost.disc.glcomp.m1comp6.ast.common.Type;
import org.junit.Assert;
import org.junit.Test;

public class MemoryTest {

    private SymbolTable buildSymbolTable() throws ExistingSymbolException {
        /*
         * class C {
         *   int x;
         *
         *   main {
         *     int x;
         *     boolean y;
         *     int z;
         *   }
         * }
         */

        SymbolNode symbolNode1 = new SymbolNode("C", "global", Kind.VARIABLE, null);
        SymbolNode symbolNode2 = new SymbolNode("x", "global", Kind.VARIABLE, Type.INTEGER);
        SymbolNode symbolNode5 = new SymbolNode("x", "main", Kind.VARIABLE, Type.INTEGER);
        SymbolNode symbolNode3 = new SymbolNode("y", "main", Kind.VARIABLE, Type.BOOLEAN);
        SymbolNode symbolNode4 = new SymbolNode("z", "main", Kind.VARIABLE, Type.INTEGER);

        SymbolTable symbolTable = new SymbolTable();

        symbolTable.put(symbolNode1);
        symbolTable.put(symbolNode2);
        symbolTable.put(symbolNode3);
        symbolTable.put(symbolNode4);
        symbolTable.put(symbolNode5);

        return symbolTable;
    }

    @Test
    public void declVarTest()
        throws ExistingSymbolException,
            UnknownSymbolException,
            WrongKindException,
            InvalidTypeException {
        SymbolTable symbolTable = buildSymbolTable();

        Memory memory = new Memory(symbolTable);

        ValueNode<Integer> valueNode = memory.declVar("x", "global", 1);

        Assert.assertEquals(valueNode.getIdent(), "x");
        Assert.assertEquals(valueNode.getScope(), "global");
        Assert.assertEquals(valueNode.getKind(), Kind.VARIABLE);
        Assert.assertEquals(valueNode.getType(), Type.INTEGER);
    }

    @Test(expected = UnknownSymbolException.class)
    public void declVarUnknownSymbolTest()
        throws ExistingSymbolException,
            UnknownSymbolException,
            WrongKindException,
            InvalidTypeException {
        SymbolTable symbolTable = buildSymbolTable();

        Memory memory = new Memory(symbolTable);

        memory.declVar("w", "main", 1);
    }

    @Test(expected = InvalidTypeException.class)
    public void declVarInvalidTypeTest()
        throws ExistingSymbolException,
            UnknownSymbolException,
            WrongKindException,
            InvalidTypeException {
        SymbolTable symbolTable = buildSymbolTable();

        Memory memory = new Memory(symbolTable);

        memory.declVar("x", "main", true);
    }

    @Test
    public void removeDeclTest()
        throws ExistingSymbolException,
            WrongKindException,
            InvalidTypeException,
            UnknownSymbolException,
            NotFoundValueException {
        SymbolTable symbolTable = buildSymbolTable();

        Memory memory = new Memory(symbolTable);

        ValueNode<Integer> valueNode = memory.declVar("x", "global", 1);

        Assert.assertEquals(memory.removeDecl("x", "global"), valueNode);
    }

    @Test(expected = UnknownSymbolException.class)
    public void removeDeclUnknownSymbolTest()
        throws ExistingSymbolException,
            UnknownSymbolException,
            NotFoundValueException {
        SymbolTable symbolTable = buildSymbolTable();

        Memory memory = new Memory(symbolTable);

        memory.removeDecl("w", "main");
    }

    @Test(expected = NotFoundValueException.class)
    public void removeDeclNotFoundValueTest()
        throws ExistingSymbolException,
            UnknownSymbolException,
            NotFoundValueException {
        SymbolTable symbolTable = buildSymbolTable();

        Memory memory = new Memory(symbolTable);

        memory.removeDecl("x", "global");
    }

    @Test
    public void identValTest()
        throws ExistingSymbolException,
            NotFoundValueException,
            UnknownSymbolException,
            InvalidTypeException,
            WrongSymbolException {
        SymbolTable symbolTable = buildSymbolTable();

        Memory memory = new Memory(symbolTable);

        ValueNode<Integer> valueNode = memory.push(1);

        Assert.assertEquals(memory.identVal("x", "main", 0), valueNode);
    }

    @Test(expected = NotFoundValueException.class)
    public void identValNotFoundValueTest()
        throws ExistingSymbolException,
            NotFoundValueException,
            UnknownSymbolException,
            InvalidTypeException,
            WrongSymbolException {
        SymbolTable symbolTable = buildSymbolTable();

        Memory memory = new Memory(symbolTable);

        ValueNode<Integer> valueNode = memory.push(1);

        memory.identVal("x", "main", 1);
    }

    @Test(expected = UnknownSymbolException.class)
    public void identValUnknownSymbolTest()
        throws ExistingSymbolException,
            NotFoundValueException,
            UnknownSymbolException,
            InvalidTypeException,
            WrongSymbolException {
        SymbolTable symbolTable = buildSymbolTable();

        Memory memory = new Memory(symbolTable);

        ValueNode<Integer> valueNode = memory.push(1);

        memory.identVal("w", "main", 0);
    }

    @Test(expected = InvalidTypeException.class)
    public void identValInvalidTypeTest()
        throws ExistingSymbolException,
            NotFoundValueException,
            UnknownSymbolException,
            InvalidTypeException,
            WrongSymbolException {
        SymbolTable symbolTable = buildSymbolTable();

        Memory memory = new Memory(symbolTable);

        ValueNode<Integer> valueNode = memory.push(1);

        memory.identVal("y", "main", 0);
    }

    @Test
    public void assignValTest()
        throws ExistingSymbolException,
            WrongKindException,
            InvalidTypeException,
            UnknownSymbolException,
            NotFoundValueException,
            InvalidConstantAssignmentException{
        SymbolTable symbolTable = buildSymbolTable();

        Memory memory = new Memory(symbolTable);

        ValueNode<Integer> valueNode = memory.declVar("x", "global", 1);

        Assert.assertEquals((int) valueNode.getValue(), 1);
        Assert.assertEquals(memory.assignVal("x", "global", 2), valueNode);
        Assert.assertEquals((int) valueNode.getValue(), 2);
    }

    @Test(expected = InvalidTypeException.class)
    public void assignValInvalidTypeTest()
        throws ExistingSymbolException,
            WrongKindException,
            InvalidTypeException,
            UnknownSymbolException,
            NotFoundValueException ,
            InvalidConstantAssignmentException{
        SymbolTable symbolTable = buildSymbolTable();

        Memory memory = new Memory(symbolTable);

        memory.declVar("x", "global", 1);
        memory.assignVal("x", "global", true);
    }

    @Test(expected = UnknownSymbolException.class)
    public void assignValUnknownSymbolTest()
        throws ExistingSymbolException,
            InvalidTypeException,
            UnknownSymbolException,
            NotFoundValueException,
            InvalidConstantAssignmentException{
        SymbolTable symbolTable = buildSymbolTable();

        Memory memory = new Memory(symbolTable);

        memory.assignVal("w", "main", 1);
    }

    @Test(expected = NotFoundValueException.class)
    public void assignValNotFoundValueTest()
        throws ExistingSymbolException,
            InvalidTypeException,
            UnknownSymbolException,
            NotFoundValueException ,
            InvalidConstantAssignmentException{
        SymbolTable symbolTable = buildSymbolTable();

        Memory memory = new Memory(symbolTable);

        memory.assignVal("x", "main", 1);
    }

    @Test
    public void getValueTest()
        throws ExistingSymbolException,
            WrongKindException,
            InvalidTypeException,
            UnknownSymbolException,
            NotFoundValueException {
        SymbolTable symbolTable = buildSymbolTable();

        Integer value = 1;
        Memory memory = new Memory(symbolTable);

        memory.declVar("x", "global", value);

        Assert.assertEquals(memory.getValue("x", "global"), value);
    }

    @Test(expected = UnknownSymbolException.class)
    public void getValueUnknownSymbolTest()
        throws ExistingSymbolException,
            UnknownSymbolException,
            NotFoundValueException {
        SymbolTable symbolTable = buildSymbolTable();

        Memory memory = new Memory(symbolTable);

        memory.getValue("w", "main");
    }

    @Test(expected = NotFoundValueException.class)
    public void getValueNotFoundValueTest()
        throws ExistingSymbolException,
            UnknownSymbolException,
            NotFoundValueException {
        SymbolTable symbolTable = buildSymbolTable();

        Memory memory = new Memory(symbolTable);

        memory.getValue("x", "main");
    }

    @Test
    public void getKindTest()
        throws ExistingSymbolException,
            WrongKindException,
            InvalidTypeException,
            UnknownSymbolException,
            NotFoundValueException {
        SymbolTable symbolTable = buildSymbolTable();

        Memory memory = new Memory(symbolTable);

        memory.declVar("x", "global", 1);

        Assert.assertEquals(memory.getKind("x", "global"), Kind.VARIABLE);
    }

    @Test(expected = UnknownSymbolException.class)
    public void getKindUnknownSymbolTest()
        throws ExistingSymbolException,
            UnknownSymbolException,
            NotFoundValueException {
        SymbolTable symbolTable = buildSymbolTable();

        Memory memory = new Memory(symbolTable);

        memory.getKind("w", "main");
    }

    @Test(expected = NotFoundValueException.class)
    public void getKindNotFoundValueTest()
        throws ExistingSymbolException,
            UnknownSymbolException,
            NotFoundValueException {
        SymbolTable symbolTable = buildSymbolTable();

        Memory memory = new Memory(symbolTable);

        memory.getKind("x", "main");
    }

    @Test
    public void getTypeTest()
        throws ExistingSymbolException,
            WrongKindException,
            InvalidTypeException,
            UnknownSymbolException,
            NotFoundValueException {
        SymbolTable symbolTable = buildSymbolTable();

        Memory memory = new Memory(symbolTable);

        memory.declVar("x", "global", 1);

        Assert.assertEquals(memory.getType("x", "global"), Type.INTEGER);
    }

    @Test(expected = UnknownSymbolException.class)
    public void getTypeUnknownSymbolTest()
        throws ExistingSymbolException,
            UnknownSymbolException,
            NotFoundValueException {
        SymbolTable symbolTable = buildSymbolTable();

        Memory memory = new Memory(symbolTable);

        memory.getType("w", "main");
    }

    @Test(expected = NotFoundValueException.class)
    public void getTypeNotFoundValueTest()
        throws ExistingSymbolException,
            UnknownSymbolException,
            NotFoundValueException {
        SymbolTable symbolTable = buildSymbolTable();

        Memory memory = new Memory(symbolTable);

        memory.getType("x", "main");
    }
}
