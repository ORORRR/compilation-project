package fr.femtost.disc.glcomp.m1comp6.interpreters.jjc;

import fr.femtost.disc.glcomp.m1comp6.ast.common.JajaCodeASTVisitor;
import fr.femtost.disc.glcomp.m1comp6.ast.common.Node;
import fr.femtost.disc.glcomp.m1comp6.ast.jjc.*;
import fr.femtost.disc.glcomp.m1comp6.memory.*;

import java.util.List;

public class InterpreterVisitor implements JajaCodeASTVisitor<Object, InterpreterException> {
    private Memory memory;
    private Integer adr;


    public InterpreterVisitor() {}

    public void setMemory (Memory memory) {
        this.memory = memory ;
    }


    @Override
    public Object visit(NodeInit node, Object... args) throws InterpreterException {
        adr += 1 ;
        return null;
    }

    @Override
    public Object visit(NodePop node, Object...args) throws InterpreterException {
        try {
            this.memory.pop();
        } catch (Exception exception){
            throw new InterpreterException(exception.toString());
        }

        this.adr += 1 ;
        return null;
    }

    @Override
    public Object visit(NodePush node, Object...args) throws InterpreterException {
        List<Node> children  = node.getChildren();
        NodeJajaCode nodeValue = (NodeJajaCode) children.get(0);

        Object value = nodeValue.accept(this);

        try {
            memory.push(value);
        } catch (Exception exception){
            throw new InterpreterException(exception.toString());
        }

        adr += 1 ;

        return null ;
    }

    @Override
    public Object visit(NodeSwap node, Object...args) throws InterpreterException {

        try {
           this. memory.swap();
        } catch (Exception exception){
            throw new InterpreterException(exception.toString());
        }

        adr += 1 ;
        return null ;
    }


    @Override
    public Object visit(NodeIdent node, Object...args) throws InterpreterException {
        return node.getValue();
    }

    @Override
    public Integer visit(NodeType node, Object...args) throws InterpreterException {
        return null ;
    }

    @Override
    public Integer visit(NodeAdd node, Object...args) throws InterpreterException {
        return null ;
    }

    @Override
    public Integer visit(NodeKind node, Object...args) throws InterpreterException {
        return null ;
    }

    @Override
    public Integer visit(NodeNumber node, Object...args) throws InterpreterException {
        return node.getValue() ;
    }

    @Override
    public Boolean visit(NodeFalse node, Object...args) throws InterpreterException {
        return false ;
    }

    @Override
    public Boolean visit(NodeTrue node, Object...args) throws InterpreterException {
        return true ;
    }

    @Override
    public Object visit(NodeJcnil node, Object...args) throws InterpreterException {
        return null ;
    }

    @Override
    public Object visit(NodeJcstop node, Object...args) throws InterpreterException {
        this.adr = null;
        return null;
    }


    @Override
    public Object visit(NodeNew node, Object...args) throws InterpreterException {
        List<Node> children = node.getChildren();

        NodeIdent nodeIdent = (NodeIdent) children.get(0);
        NodeKind nodeKind = (NodeKind) children.get(2);
        NodeNumber nodeS = (NodeNumber) children.get(3);

        String ident = (String) nodeIdent.accept(this);


        int s = (int) nodeS.accept(this);


        try {
            //TODO : faire un condition sur le kind

            memory.identVal(ident, "global", s); //si le kind est Variable


        }  catch (Exception exception){
            throw new InterpreterException(exception.toString());
        }

        adr += 1;

        return null ;
    }

    @Override
    public Object visit(NodeInstrs node, Object...args) throws InterpreterException {
        List<Node> children = node.getChildren();

        this.adr = 1;

        while(adr != null){
            NodeJajaCode next_instr = ((NodeJajaCode)children.get(adr-1));
            next_instr.accept(this);
        }
        return null ;
    }

    @Override
    public Object visit(NodeAinc node, Object...args) throws InterpreterException {
        return null ;
    }

    @Override
    public Object visit(NodeAload node, Object...args) throws InterpreterException {
        return null ;
    }

    @Override
    public Object visit(NodeAnd node, Object...args) throws InterpreterException {
        return null ;
    }

    @Override
    public Object visit(NodeAstore node, Object...args) throws InterpreterException {
        return null ;
    }

    @Override
    public Object visit(NodeCmp node, Object...args) throws InterpreterException {
        return null ;
    }

    @Override
    public Object visit(NodeDiv node, Object...args) throws InterpreterException {
        return null ;
    }

    @Override
    public Object visit(NodeGoto node, Object...args) throws InterpreterException {
        return null ;
    }

    @Override
    public Object visit(NodeIf node, Object...args) throws InterpreterException {
        return null ;
    }

    @Override
    public Object visit(NodeInc node, Object...args) throws InterpreterException {
        return null ;
    }

    @Override
    public Object visit(NodeInvoke node, Object...args) throws InterpreterException {
        return null ;
    }

    @Override
    public Object visit(NodeJajaCode node, Object...args) throws InterpreterException {
        return null ;
    }

    @Override
    public Object visit(NodeLoad node, Object...args) throws InterpreterException {
        return null ;
    }

    @Override
    public Object visit(NodeMul node, Object...args) throws InterpreterException {
        return null ;
    }

    @Override
    public Object visit(NodeNeg node, Object...args) throws InterpreterException {
        return null ;
    }

    @Override
    public Object visit(NodeNewArray node, Object...args) throws InterpreterException {
        return null ;
    }

    @Override
    public Object visit(NodeNop node, Object...args) throws InterpreterException {
        return null ;
    }

    @Override
    public Object visit(NodeNot node, Object...args) throws InterpreterException {
        return null ;
    }

    @Override
    public Object visit(NodeOr node, Object...args) throws InterpreterException {
        return null ;
    }

    @Override
    public Object visit(NodeReturn node, Object...args) throws InterpreterException {
        return null ;
    }

    @Override
    public Object visit(NodeStore node, Object...args) throws InterpreterException {
        return null ;
    }

    @Override
    public Object visit(NodeSub node, Object...args) throws InterpreterException {
        return null ;
    }

    @Override
    public Object visit(NodeSup node, Object...args) throws InterpreterException {
        return null ;
    }
}
