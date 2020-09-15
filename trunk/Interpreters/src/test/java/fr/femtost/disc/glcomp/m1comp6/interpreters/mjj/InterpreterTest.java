package fr.femtost.disc.glcomp.m1comp6.interpreters.mjj;

import org.junit.Test;

import fr.femtost.disc.glcomp.m1comp6.memory.*;
import fr.femtost.disc.glcomp.m1comp6.ast.common.Kind;
import fr.femtost.disc.glcomp.m1comp6.ast.common.Type;
import fr.femtost.disc.glcomp.m1comp6.ast.mjj.*;

public class InterpreterTest {
    @Test
    public void testInterpreter() throws ExistingSymbolException {
        NodeClass nodeClass = new NodeClass();

        NodeIdent nodeIdent = new NodeIdent("C", "global");
        NodeDecls nodeDecls = new NodeDecls();
        NodeMain nodeMain = new NodeMain();

        nodeClass.addChild(nodeIdent);
        nodeClass.addChild(nodeDecls);
        nodeClass.addChild(nodeMain);

        NodeVar nodeVar = new NodeVar();

        nodeDecls.addChild(nodeVar);

        NodeInteger nodeInteger = new NodeInteger();
        NodeIdent nodeIdent1 = new NodeIdent("x", "global");
        NodeNumber nodeNumber = new NodeNumber(0);

        nodeVar.addChild(nodeInteger);
        nodeVar.addChild(nodeIdent1);
        nodeVar.addChild(nodeNumber);

        nodeDecls.addChild(new NodeVnil());

        NodeVars nodeVars = new NodeVars();

        nodeMain.addChild(nodeVars);

        NodeVar nodeVar1 = new NodeVar();

        nodeVars.addChild(nodeVar1);

        NodeInteger nodeInteger1 = new NodeInteger();
        NodeIdent nodeIdent2 = new NodeIdent("x", "main");
        NodeNumber nodeNumber1 = new NodeNumber(1);

        nodeVar1.addChild(nodeInteger1);
        nodeVar1.addChild(nodeIdent2);
        nodeVar1.addChild(nodeNumber1);

        nodeVars.addChild(new NodeVnil());

        nodeMain.addChild(new NodeInil());

        Interpreter mjjInterpreter = new Interpreter(nodeClass);
        SymbolTable symbolTable = new SymbolTable();
        symbolTable.put(new SymbolNode("C@global", Kind.VARIABLE, null));
        symbolTable.put(new SymbolNode("x@global", Kind.VARIABLE, Type.INTEGER));
        symbolTable.put(new SymbolNode("x@main", Kind.VARIABLE, Type.INTEGER));
        Memory memory = new Memory(symbolTable);
        mjjInterpreter.setMemory(memory);

        System.out.println(memory);
        System.out.println(memory);
    }
}

