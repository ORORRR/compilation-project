package fr.femtost.disc.glcomp.m1comp6.memory;

import fr.femtost.disc.glcomp.m1comp6.ast.common.Kind;
import fr.femtost.disc.glcomp.m1comp6.ast.common.Type;
import org.junit.Assert;
import org.junit.Test;

public class SymbolTableTest {

    @Test
    public void constructTest() {
        SymbolTable symbolTable = new SymbolTable();
    }

    @Test
    public void putTest() throws ExistingSymbolException {
        SymbolTable symbolTable = new SymbolTable();

        SymbolNode symbolNode1 = new SymbolNode("x", "global", Kind.VARIABLE, Type.INTEGER);
        SymbolNode symbolNode2 = new SymbolNode("y", "global", Kind.VARIABLE, Type.BOOLEAN);

        Assert.assertEquals(symbolTable.put(symbolNode1), symbolNode1);
        Assert.assertEquals(symbolTable.put(symbolNode2), symbolNode2);
    }

    @Test(expected = ExistingSymbolException.class)
    public void putExistingSymbolTest() throws ExistingSymbolException {
        SymbolTable symbolTable = new SymbolTable();

        SymbolNode symbolNode1 = new SymbolNode("x", "global", Kind.VARIABLE, Type.INTEGER);
        SymbolNode symbolNode2 = new SymbolNode("x", "global", Kind.VARIABLE, Type.INTEGER);

        symbolTable.put(symbolNode1);
        symbolTable.put(symbolNode2);
    }

    @Test
    public void getTest() throws ExistingSymbolException {
        SymbolTable symbolTable = new SymbolTable();

        SymbolNode symbolNode1 = new SymbolNode("x", "global", Kind.VARIABLE, Type.INTEGER);
        SymbolNode symbolNode2 = new SymbolNode("y", "global", Kind.VARIABLE, Type.INTEGER);
        SymbolNode symbolNode3 = new SymbolNode("z", "global", Kind.VARIABLE, Type.BOOLEAN);

        symbolTable.put(symbolNode1);
        symbolTable.put(symbolNode2);
        symbolTable.put(symbolNode3);

        Assert.assertEquals(symbolTable.get("x", "global"), symbolNode1);
        Assert.assertEquals(symbolTable.get("y", "global"), symbolNode2);
        Assert.assertEquals(symbolTable.get("z", "global"), symbolNode3);

        Assert.assertNull(symbolTable.get("w", "global"));
    }

    @Test
    public void removeTest() throws ExistingSymbolException {
        SymbolTable symbolTable = new SymbolTable();

        SymbolNode symbolNode1 = new SymbolNode("x", "global", Kind.VARIABLE, Type.INTEGER);
        SymbolNode symbolNode2 = new SymbolNode("y", "global", Kind.VARIABLE, Type.INTEGER);
        SymbolNode symbolNode3 = new SymbolNode("z", "global", Kind.VARIABLE, Type.BOOLEAN);

        symbolTable.put(symbolNode1);
        symbolTable.put(symbolNode2);
        symbolTable.put(symbolNode3);

        Assert.assertEquals(symbolTable.remove("x", "global"), symbolNode1);
        Assert.assertNull(symbolTable.get("x", "global"));
        Assert.assertNull(symbolTable.remove("w", "global"));
    }
}
