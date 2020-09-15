package fr.femtost.disc.glcomp.m1comp6.typechecker.mjj;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import fr.femtost.disc.glcomp.m1comp6.memory.*;

import fr.femtost.disc.glcomp.m1comp6.ast.common.*;
import fr.femtost.disc.glcomp.m1comp6.ast.mjj.*;

import static org.mockito.Mockito.*;

public class TypeCheckerVisitorTest {

    private TypeCheckerVisitor typeCheckerVisitor;
    private SymbolTable symbolTable = mock(SymbolTable.class);

    @Before
    public void initialize () {
        typeCheckerVisitor = new TypeCheckerVisitor();
        typeCheckerVisitor.setSymbolTable(symbolTable);
    }

    @Test
    public void NodeTrueVisitorTest () throws TypeCheckerException {
        NodeTrue nodeTrue = new NodeTrue();

        Type res  = typeCheckerVisitor.visit(nodeTrue, "global");
        Assert.assertEquals(Type.BOOLEAN, res);
    }

    @Test
    public void NodeFalseVisitorTest () throws TypeCheckerException {
        NodeFalse nodeFalse = new NodeFalse();

        Type res  = typeCheckerVisitor.visit(nodeFalse, "global");
        Assert.assertEquals(Type.BOOLEAN, res);
    }

    @Test
    public void NodeNumberVisitorTest () throws TypeCheckerException {
        NodeNumber nodeNumber = new NodeNumber(4);

        Type res  = typeCheckerVisitor.visit(nodeNumber, "global");
        Assert.assertEquals(Type.INTEGER, res);
    }

    @Test
    public void NodeOmegaVisitorTest () throws TypeCheckerException {
        NodeOmega nodeOmega = new NodeOmega();

        Type res  = typeCheckerVisitor.visit(nodeOmega, "global");
        Assert.assertNull(res);
    }

    @Test
    public void NodeVnilVisitorTest () throws TypeCheckerException {
        NodeVnil nodeVnil = new NodeVnil();

        Type res  = typeCheckerVisitor.visit(nodeVnil, "global");
        Assert.assertNull(res);
    }

    @Test
    public void NodeInilVisitorTest () throws TypeCheckerException {
        NodeInil nodeInil = new NodeInil();

        Type res  = typeCheckerVisitor.visit(nodeInil, "global");
        Assert.assertNull(res);
    }

    /*
    @Test
    public void NodeBooleanVisitorTest () throws TypeCheckerException {
        NodeBoolean nodeBoolean = new NodeBoolean();

        Type res  = typeCheckerVisitor.visit(nodeBoolean, "global");
        Assert.assertNull(res);
    }

    @Test
    public void NodeIntegerVisitorTest () throws TypeCheckerException {
        NodeInteger nodeInteger = new NodeInteger();

        Type res  = typeCheckerVisitor.visit(nodeInteger, "global");
        Assert.assertNull(res);
    }

    @Test
    public void NodeVoidVisitorTest () throws TypeCheckerException {
        NodeVoid nodeVoid = new NodeVoid();

        Type res  = typeCheckerVisitor.visit(nodeVoid, "global");
        Assert.assertNull(res);
    }
    */

//    @Test
//    public void NodeVisitorTest () throws TypeCheckerException {
//        Node node = new Node();
//
//        Type res  = typeCheckerVisitor.visit(node, "global");
//        Assert.assertNull(res);
//    }


    @Test
    public void NodeIdentVisitorTes1 () throws TypeCheckerException {
        when(this.symbolTable.get("x","main" )).thenReturn(new SymbolNode("x", "main", Kind.VARIABLE, Type.BOOLEAN));

        //-----------------

        NodeIdent nodeIdent = new NodeIdent("x");

        Type res  = typeCheckerVisitor.visit(nodeIdent, "main");
        Assert.assertEquals(Type.BOOLEAN, res);
    }

    @Test
    public void NodeIdentVisitorTest2 () throws TypeCheckerException {
        when(symbolTable.get("x","main" )).thenReturn(null);
        when(symbolTable.get("x","global" )).thenReturn(new SymbolNode("x", "global", Kind.VARIABLE, Type.BOOLEAN));
        //-----------------

        NodeIdent nodeIdent = new NodeIdent("x");

        Type res  = typeCheckerVisitor.visit(nodeIdent, "main");
        Assert.assertEquals(Type.BOOLEAN, res);
    }

    @Test (expected = TypeCheckerException.class)
    public void NodeIdentVisitorTest3 () throws TypeCheckerException {
        when(symbolTable.get("x","main" )).thenReturn(null);
        when(symbolTable.get("x","global" )).thenReturn(null);
        //-----------------

        NodeIdent nodeIdent = new NodeIdent("x");

        typeCheckerVisitor.visit(nodeIdent, "main");
    }


}
