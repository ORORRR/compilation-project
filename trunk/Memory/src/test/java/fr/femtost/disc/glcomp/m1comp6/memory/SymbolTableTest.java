package fr.femtost.disc.glcomp.m1comp6.memory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.femtost.disc.glcomp.m1comp6.ast.common.Kind;
import fr.femtost.disc.glcomp.m1comp6.ast.common.Type;

public class SymbolTableTest {
    private SymbolTable symbolTable;

    @Before
    public void initialize() {
        symbolTable = new SymbolTable();
    }

    @Test
    public void putTest() throws ExistingSymbolException {
        SymbolNode symbolNode1 = new SymbolNode("x@global", Kind.VARIABLE, Type.INTEGER);
        SymbolNode symbolNode2 = new SymbolNode("y@global", Kind.VARIABLE, Type.BOOLEAN);

        Assert.assertEquals(symbolTable.put(symbolNode1), symbolNode1);
        Assert.assertEquals(symbolTable.put(symbolNode2), symbolNode2);
    }

    @Test(expected = ExistingSymbolException.class)
    public void putExistingSymbolTest() throws ExistingSymbolException {
        SymbolNode symbolNode1 = new SymbolNode("x@global", Kind.VARIABLE, Type.INTEGER);
        SymbolNode symbolNode2 = new SymbolNode("x@global", Kind.VARIABLE, Type.INTEGER);

        symbolTable.put(symbolNode1);
        symbolTable.put(symbolNode2);
    }

    @Test
    public void getTest() throws ExistingSymbolException, UnknownSymbolException {
        SymbolNode symbolNode1 = new SymbolNode("x@global", Kind.VARIABLE, Type.INTEGER);
        SymbolNode symbolNode2 = new SymbolNode("y@global", Kind.VARIABLE, Type.INTEGER);
        SymbolNode symbolNode3 = new SymbolNode("z@global", Kind.VARIABLE, Type.BOOLEAN);

        symbolTable.put(symbolNode1);
        symbolTable.put(symbolNode2);
        symbolTable.put(symbolNode3);

        Assert.assertEquals(symbolTable.get("x@global"), symbolNode1);
        Assert.assertEquals(symbolTable.get("y@global"), symbolNode2);
        Assert.assertEquals(symbolTable.get("z@global"), symbolNode3);
    }

    @Test(expected = UnknownSymbolException.class)
    public void getUnknownSymbolTest() throws UnknownSymbolException {
        Assert.assertNull(symbolTable.get("w@global"));
    }

    @Test
    public void removeTest() throws ExistingSymbolException, UnknownSymbolException {
        SymbolNode symbolNode1 = new SymbolNode("x@global", Kind.VARIABLE, Type.INTEGER);
        SymbolNode symbolNode2 = new SymbolNode("y@global", Kind.VARIABLE, Type.INTEGER);
        SymbolNode symbolNode3 = new SymbolNode("z@global", Kind.VARIABLE, Type.BOOLEAN);

        symbolTable.put(symbolNode1);
        symbolTable.put(symbolNode2);
        symbolTable.put(symbolNode3);

        Assert.assertEquals(symbolTable.remove("x@global"), symbolNode1);
        try {
            symbolTable.get("x@global");
        } catch (UnknownSymbolException e) {
            return;
        }
        Assert.fail();
    }

    @Test(expected = UnknownSymbolException.class)
    public void removeUnknownSymbolTest() throws UnknownSymbolException {
        Assert.assertNull(symbolTable.remove("w@global"));
    }
}
