package fr.femtost.disc.glcomp.m1comp6.compiler;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.femtost.disc.glcomp.m1comp6.ast.common.Kind;
import fr.femtost.disc.glcomp.m1comp6.ast.common.Type;
import fr.femtost.disc.glcomp.m1comp6.ast.jjc.*;

public class CompilerPrinterVisitorTest {
    CompilerPrinterVisitor compilerPrinterVisitor;
    StringBuilder buffer;

    @Before
    public void initialize() {
        compilerPrinterVisitor = new CompilerPrinterVisitor();
        buffer = new StringBuilder();

        compilerPrinterVisitor.setBuffer(buffer);
    }

    @Test
    public void visitInitTest() throws CompilerPrinterException {
        NodeInit node = new NodeInit();

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(), "init\n");
    }

    @Test
    public void visitIdentTest() throws CompilerPrinterException {
        NodeIdent node = new NodeIdent("x@global");

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(), node.getValue());
    }

    @Test
    public void visitAddTest() throws CompilerPrinterException {
        NodeAdd node = new NodeAdd();

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(), "add\n");
    }

    @Test
    public void visitAndTest() throws CompilerPrinterException {
        NodeAnd node = new NodeAnd();

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(), "and\n");
    }

    @Test
    public void visitCmpTest() throws CompilerPrinterException {
        NodeCmp node = new NodeCmp();

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(), "cmp\n");
    }

    @Test
    public void visitDivTest() throws CompilerPrinterException {
        NodeDiv node = new NodeDiv();

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(), "div\n");
    }

    @Test
    public void visitFalseTest() throws CompilerPrinterException {
        NodeFalse node = new NodeFalse();

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(), "false");
    }

    @Test
    public void visitTrueTest() throws CompilerPrinterException {
        NodeTrue node = new NodeTrue();

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(), "true");
    }

    @Test
    public void visitJcstopTest() throws CompilerPrinterException {
        NodeJcstop node = new NodeJcstop();

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(), "jcstop\n");
    }

    @Test
    public void visitKindTest() throws CompilerPrinterException {
        NodeKind node = new NodeKind(Kind.METHOD);

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(), node.getValue().toString());
    }

    @Test
    public void visitMulTest() throws CompilerPrinterException {
        NodeMul node = new NodeMul();

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(), "mul\n");
    }

    @Test
    public void visitNegTest() throws CompilerPrinterException {
        NodeNeg node = new NodeNeg();

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(), "neg\n");
    }

    @Test
    public void visitNotTest() throws CompilerPrinterException {
        NodeNot node = new NodeNot();

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(), "not\n");
    }

    @Test
    public void visitNopTest() throws CompilerPrinterException {
        NodeNop node = new NodeNop();

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(), "nop\n");
    }

    @Test
    public void visitNumberTest() throws CompilerPrinterException {
        NodeNumber node = new NodeNumber(0);

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(), Integer.toString(node.getValue()));
    }

    @Test
    public void visitOrTest() throws CompilerPrinterException {
        NodeOr node = new NodeOr();

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(), "or\n");
    }

    @Test
    public void visitPopTest() throws CompilerPrinterException {
        NodePop node = new NodePop();

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(), "pop\n");
    }

    @Test
    public void visitReturnTest() throws CompilerPrinterException {
        NodeReturn node = new NodeReturn();

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(), "return\n");
    }

    @Test
    public void visitSubTest() throws CompilerPrinterException {
        NodeSub node = new NodeSub();

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(), "sub\n");
    }

    @Test
    public void visitSupTest () throws CompilerPrinterException {
        NodeSup node = new NodeSup();

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(), "sup\n");
    }

    @Test
    public void visitSwapTest() throws CompilerPrinterException {
        NodeSwap node = new NodeSwap();

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(), "swap\n");
    }

    @Test
    public void visitTypeTest() throws CompilerPrinterException {
        NodeType node = new NodeType(Type.INTEGER);

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(), node.getValue().toString());
    }

    @Test
    public void visitJcnilTest() throws CompilerPrinterException {
        NodeJcnil node = new NodeJcnil();

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(), "nil");
    }

    @Test
    public void visitPushNumberTest() throws CompilerPrinterException {
        NodePush node = new NodePush();

        node.addChild(new NodeNumber(0));

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(), "push(0)\n");
    }

    @Test
    public void visitPushTrueTest() throws CompilerPrinterException {
        NodePush node = new NodePush();

        node.addChild(new NodeTrue());

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(), "push(true)\n");
    }

    @Test
    public void visitPushFalseTest() throws CompilerPrinterException {
        NodePush node = new NodePush();

        node.addChild(new NodeFalse());

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(), "push(false)\n");
    }

    @Test
    public void visitPushJcnilTest() throws CompilerPrinterException {
        NodePush node = new NodePush();

        node.addChild(new NodeJcnil());

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(), "push(nil)\n");
    }

    @Test
    public void visitAincTest() throws CompilerPrinterException {
        NodeAinc node = new NodeAinc();

        node.addChild(new NodeIdent("x@global"));

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(), "ainc(x@global)\n");
    }

    @Test
    public void visitAloadTest() throws CompilerPrinterException {
        NodeAload node = new NodeAload();

        node.addChild(new NodeIdent("x@global"));

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(), "aload(x@global)\n");
    }

    @Test
    public void visitAstoreTest() throws CompilerPrinterException {
        NodeAstore node = new NodeAstore();

        node.addChild(new NodeIdent("x@global"));

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(), "astore(x@global)\n");
    }

    @Test
    public void visitStoreTest() throws CompilerPrinterException {
        NodeStore node = new NodeStore();

        node.addChild(new NodeIdent("x@global"));

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(), "store(x@global)\n");
    }

    @Test
    public void visitNewArrayTest() throws  CompilerPrinterException {
        NodeNewArray node = new NodeNewArray();

        node.addChild(new NodeIdent("x@global"));
        node.addChild(new NodeType(Type.INTEGER));

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(), "newarray(x@global, INTEGER)\n");
    }

    @Test
    public void visitNewTest() throws CompilerPrinterException {
        NodeNew node = new NodeNew();

        node.addChild(new NodeIdent("x@global"));
        node.addChild(new NodeType(Type.INTEGER));
        node.addChild(new NodeKind(Kind.VARIABLE));
        node.addChild(new NodeNumber(0));

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(), "new(x@global, INTEGER, VARIABLE, 0)\n");
    }

    @Test
    public void visitInvokeTest() throws CompilerPrinterException {
        NodeInvoke node = new NodeInvoke();

        node.addChild(new NodeIdent("f:int"));

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(), "invoke(f:int)\n");
    }

    @Test
    public void visitIncTest() throws CompilerPrinterException {
        NodeInc node = new NodeInc();

        node.addChild(new NodeIdent("x@global"));

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(), "inc(x@global)\n");
    }

    @Test
    public void visitIfTest() throws CompilerPrinterException {
        NodeIf node = new NodeIf();

        node.addChild(new NodeNumber(0));

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(), "if(0)\n");
    }

    @Test
    public void visitGotoTest() throws CompilerPrinterException {
        NodeGoto node = new NodeGoto();

        node.addChild(new NodeNumber(0));

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(), "goto(0)\n");
    }

    @Test
    public void visitLoadTest () throws CompilerPrinterException {
        NodeLoad node = new NodeLoad();

        node.addChild(new NodeIdent("x@global"));

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(), "load(x@global)\n");
    }

    @Test
    public void visitInstrsIncTest () throws CompilerPrinterException {
        NodeInstrs node = new NodeInstrs();
        NodeInc nodeChild = new NodeInc();

        node.addChild(nodeChild);
        nodeChild.addChild(new NodeIdent("x@global"));

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(), "inc(x@global)\n");
    }

    @Test
    public void visitInstrsAincTest () throws CompilerPrinterException {
        NodeInstrs node = new NodeInstrs();
        NodeAinc nodeChild = new NodeAinc();

        nodeChild.addChild(new NodeIdent("x@global"));
        node.addChild(nodeChild);

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(), "ainc(x@global)\n");
    }
}
