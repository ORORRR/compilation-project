package fr.femtost.disc.glcomp.m1comp6.compiler;

import fr.femtost.disc.glcomp.m1comp6.ast.jjc.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class CompilerPrinterVisitorTest {


    CompilerPrinterVisitor compilerPrinterVisitor;
    StringBuilder buffer;

    @Before
    public void initialize(){
        compilerPrinterVisitor=new CompilerPrinterVisitor();
        buffer=new StringBuilder();
        compilerPrinterVisitor.setBuffer(buffer);
    }

    @Test
    public void visitInitTest() throws CompilerPrinterException{
        NodeInit node=new NodeInit();

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(),"init\n");

    }

    @Test
    public void visitIdentTest() throws CompilerPrinterException{
        NodeIdent node=new NodeIdent("C");

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(),node.getValue());
    }

    @Test
    public void visitAddTest() throws CompilerPrinterException{
        NodeAdd node=new NodeAdd();

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(),"add\n");
    }

    @Test
    public void visitAndTest() throws CompilerPrinterException{
        NodeAnd node=new NodeAnd();

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(),"and\n");
    }

    @Test
    public void visitCmpTest() throws CompilerPrinterException{
        NodeCmp node=new NodeCmp();

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(),"cmp\n");
    }

    @Test
    public void visitDivTest() throws CompilerPrinterException{
        NodeDiv node=new NodeDiv();

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(),"div\n");
    }

    @Test
    public void visitFalseTest() throws CompilerPrinterException{
        NodeFalse node=new NodeFalse();

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(),"false");
    }

    @Test
    public void visitTrueTest() throws  CompilerPrinterException{
        NodeTrue node=new NodeTrue();

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(),"true");
    }

    @Test
    public void visitJcstopTest() throws CompilerPrinterException{
        NodeJcstop node=new NodeJcstop();

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(),"jcstop\n");
    }

    @Test
    public void visitKindTest() throws CompilerPrinterException{
        NodeKind node=new NodeKind("method");

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(),node.getValue());
    }

    @Test
    public void visitMulTest() throws CompilerPrinterException{
        NodeMul node=new NodeMul();

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(),"mul\n");
    }

    @Test
    public void visitNegTest() throws CompilerPrinterException{
        NodeNeg node=new NodeNeg();

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(),"neg\n");
    }

    @Test
    public void visitNotTest() throws CompilerPrinterException{
        NodeNot node=new NodeNot();

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(),"not\n");
    }

    @Test
    public void visitNopTest() throws CompilerPrinterException{
        NodeNop node=new NodeNop();

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(),"nop\n");
    }

    @Test
    public void visitNumberTest() throws CompilerPrinterException{
        NodeNumber node=new NodeNumber(3);

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(), Integer.toString(node.getValue()));
    }

    @Test
    public void visitOrTest() throws CompilerPrinterException{
        NodeOr node=new NodeOr();

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(),"or\n");
    }

    @Test
    public void visitPopTest() throws CompilerPrinterException{
        NodePop node=new NodePop();

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(),"pop\n");
    }

    @Test
    public void visitReturnTest() throws CompilerPrinterException{
        NodeReturn node=new NodeReturn();

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(),"return\n");
    }

    @Test
    public void visitSubTest() throws CompilerPrinterException{
        NodeSub node=new NodeSub();

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(),"sub\n");
    }

    @Test
    public void visitSwapTest() throws CompilerPrinterException{
        NodeSwap node=new NodeSwap();

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(),"swap\n");
    }

    @Test
    public void visiteTypeTest() throws CompilerPrinterException{
        NodeType node=new NodeType("integer");

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(),node.getValue());
    }

    @Test
    public void visitJcnilTest() throws CompilerPrinterException{
        NodeJcnil node=new NodeJcnil();

        compilerPrinterVisitor.visit(node);
        Assert.assertEquals(buffer.toString(),"");

    }

    @Test
    public void visitPushNumberTest() throws CompilerPrinterException{
        NodePush node=new NodePush();
        NodeNumber nodeChildren=new NodeNumber(2);
        node.addChild(nodeChildren);

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(),"push(2)\n");
    }

    @Test
    public void visitPushTrueTest() throws CompilerPrinterException{
        NodePush node=new NodePush();
        NodeTrue nodeChildren=new NodeTrue();
        node.addChild(nodeChildren);

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(),"push(true)\n");
    }

    @Test
    public void visitPushFalseTest() throws CompilerPrinterException{
        NodePush node=new NodePush();
        NodeFalse nodeChildren=new NodeFalse();
        node.addChild(nodeChildren);

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(),"push(false)\n");
    }

    @Test
    public void visitPushJcnilTest() throws CompilerPrinterException{
        NodePush node=new NodePush();
        NodeJcnil nodeChildren=new NodeJcnil();
        node.addChild(nodeChildren);

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(),"push()\n");
    }


    @Test
    public void visitAincTest() throws CompilerPrinterException{
        NodeAinc node=new NodeAinc();
        NodeIdent nodeChildren=new NodeIdent("C");
        node.addChild(nodeChildren);

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(),"ainc(C)\n");

    }

    @Test
    public void visitAloadTest() throws CompilerPrinterException{
        NodeAload node=new NodeAload();
        NodeIdent nodeChildren=new NodeIdent("A");
        node.addChild(nodeChildren);

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(),"aload(A)\n");
    }

    @Test
    public void visitAstoreTest() throws CompilerPrinterException{
        NodeAstore node=new NodeAstore();
        NodeIdent nodeChildren=new NodeIdent("tab");
        node.addChild(nodeChildren);

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(),"astore(tab)\n");
    }

    @Test
    public void visitStoreTest() throws CompilerPrinterException{
        NodeStore node=new NodeStore();
        NodeIdent nodeChildren=new NodeIdent("tab");
        node.addChild(nodeChildren);

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(),"store(tab)\n");
    }

    @Test
    public void visitNewArrayTest() throws  CompilerPrinterException{
        NodeNewArray node=new NodeNewArray();
        NodeIdent nodeChildren=new NodeIdent("tab");
        NodeType nodeSecondChildren=new NodeType("integer");

        node.addChild(nodeChildren);
        node.addChild(nodeSecondChildren);

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(),"newarray(tab,integer)\n");
    }

    @Test
    public void visitNewTest() throws CompilerPrinterException{
        NodeNew node=new NodeNew();
        NodeIdent nodeFirstChild=new NodeIdent("C");
        NodeType nodeSecondChild=new NodeType("integer");
        NodeKind nodeThirdChild=new NodeKind("variable");
        NodeNumber nodeFourthChild=new NodeNumber(40600);

        node.addChild(nodeFirstChild);
        node.addChild(nodeSecondChild);
        node.addChild(nodeThirdChild);
        node.addChild(nodeFourthChild);

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(),"new(C, integer, variable, 40600)\n");


    }

    @Test
    public void visitInvokeTest() throws CompilerPrinterException{
        NodeInvoke node=new NodeInvoke();
        NodeIdent nodeChildren=new NodeIdent("C");

        node.addChild(nodeChildren);

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(),"invoke(C)\n");

    }

    @Test
    public void visitIncTest() throws CompilerPrinterException{
        NodeInc node=new NodeInc();
        NodeIdent nodeChildren=new NodeIdent("i");

        node.addChild(nodeChildren);

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(),"inc(i)\n");
    }

    @Test
    public void visitIfTest() throws CompilerPrinterException{
        NodeIf node=new NodeIf();
        NodeNumber nodeChildren=new NodeNumber(28907);

        node.addChild(nodeChildren);

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(),"if(28907)\n");
    }

    @Test
    public void visitGotoTest() throws CompilerPrinterException{
        NodeGoto node=new NodeGoto();
        NodeNumber nodeChildren=new NodeNumber(67890);

        node.addChild(nodeChildren);

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(),"goto(67890)\n");
    }

    @Test
    public void visitLoadTest () throws CompilerPrinterException{
        NodeLoad node=new NodeLoad();
        NodeIdent nodeChildren=new NodeIdent("x");

        node.addChild(nodeChildren);

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(),"load(x)\n");
    }

    @Test
    public void visitInstrsIncTest () throws CompilerPrinterException{
        NodeInstrs node=new NodeInstrs();
        NodeInc nodeChildren=new NodeInc();
        NodeIdent nodeSecondChildren=new NodeIdent("i");

        nodeChildren.addChild(nodeSecondChildren);
        node.addChild(nodeChildren);

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(),"inc(i)\n");

    }

    @Test
    public void visitInstrsAincTest () throws CompilerPrinterException{
        NodeInstrs node=new NodeInstrs();
        NodeAinc nodeChildren=new NodeAinc();
        NodeIdent nodeSecondChildren=new NodeIdent("i");

        nodeChildren.addChild(nodeSecondChildren);
        node.addChild(nodeChildren);

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(),"ainc(i)\n");

    }

    @Test
    public void visitSupTest () throws CompilerPrinterException{
        NodeSup node=new NodeSup();

        compilerPrinterVisitor.visit(node);

        Assert.assertEquals(buffer.toString(),"sup\n");
    }


}
