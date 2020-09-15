package fr.femtost.disc.glcomp.m1comp6.interpreters.jjc;

import fr.femtost.disc.glcomp.m1comp6.ast.common.Kind;
import fr.femtost.disc.glcomp.m1comp6.ast.common.Type;
import fr.femtost.disc.glcomp.m1comp6.ast.common.VisitorException;
import fr.femtost.disc.glcomp.m1comp6.ast.jjc.*;
import fr.femtost.disc.glcomp.m1comp6.memory.*;
import org.junit.Test;


public class InterpreterTest {
    @Test
    public void testInterpreter() throws ExistingSymbolException, VisitorException {
       NodeInstrs insts = new NodeInstrs();

        insts.addChild(new NodeInit());

        NodePush nodePush1 = new NodePush();
        nodePush1.addChild(new NodeNumber(4));
        insts.addChild(nodePush1);

        NodeNew nodeNew1 = new NodeNew();
        nodeNew1.addChild(new NodeIdent("x@global"));
        nodeNew1.addChild(new NodeKind(String.valueOf(Kind.VARIABLE)));
        nodeNew1.addChild(new NodeKind(String.valueOf(Type.INTEGER)));
        nodeNew1.addChild(new NodeNumber(0));
        insts.addChild(nodeNew1);

        NodePush nodePush2 = new NodePush();
        nodePush2.addChild(new NodeNumber(0));
        insts.addChild(nodePush2);

        insts.addChild(new NodeSwap());
        insts.addChild(new NodePop());
        insts.addChild(new NodePop());
        insts.addChild(new NodeJcstop());

        Interpreter interpreter = new Interpreter(insts);

        SymbolTable symbolTable = new SymbolTable();
        symbolTable.put(new SymbolNode("x@global", "global", Kind.VARIABLE, Type.INTEGER));
        Memory memory = new Memory(symbolTable);

        System.out.println(memory);
        interpreter.eval(memory);
        System.out.println(memory);
    }

}
