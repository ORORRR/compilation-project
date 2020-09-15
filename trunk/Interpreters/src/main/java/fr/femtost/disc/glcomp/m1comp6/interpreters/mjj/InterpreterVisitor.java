package fr.femtost.disc.glcomp.m1comp6.interpreters.mjj;

import fr.femtost.disc.glcomp.m1comp6.ast.common.CallStackElement;
import fr.femtost.disc.glcomp.m1comp6.ast.common.MiniJajaASTVisitor;
import fr.femtost.disc.glcomp.m1comp6.ast.common.VM;
import fr.femtost.disc.glcomp.m1comp6.ast.mjj.*;
import fr.femtost.disc.glcomp.m1comp6.debuggers.Debugger;
import fr.femtost.disc.glcomp.m1comp6.memory.Memory;

import java.util.Stack;

public class InterpreterVisitor implements MiniJajaASTVisitor<Object, InterpreterException> {
    private Debugger debugger;
    private Memory memory;

    private Stack<CallStackElement> callStack;

    private boolean debuggerActive;

    private boolean hasMethodReturned;

    public InterpreterVisitor() {
        callStack = new Stack<>();
        this.hasMethodReturned = false;
    }

    public void setDebugger(Debugger debugger) {
        this.debugger = debugger;
    }

    public void setMemory(Memory memory) {
        this.memory = memory;
    }

    public void setDebuggerActive(boolean debuggerActive) {
        this.debuggerActive = debuggerActive;
    }

    public void onVisit(NodeMiniJaja node) {
        if (debugger != null && debuggerActive) {
            try {
                debugger.onVisit(node);
            } catch (Exception exception) {
                debuggerActive = false;
            }
        }
    }

    @Override
    public Object visit(NodeClass node, Object... args) throws InterpreterException {
        NodeMiniJaja nodeDecls = node.getDecls();
        NodeMiniJaja nodeMain = node.getMain();

        InterpreterMode mode = (InterpreterMode) args[0];

        switch (mode) {
            case DEFAULT:
                onVisit(node);

                try {
                    memory.declVar(node.generateIdent(), null);
                } catch (Exception exception) {
                    throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
                }

                nodeDecls.accept(this, InterpreterMode.DEFAULT);
                nodeMain.accept(this, InterpreterMode.DEFAULT);
                nodeDecls.accept(this, InterpreterMode.CANCEL);

                onVisit(node);

                try {
                    memory.removeDecl(node.generateIdent());
                } catch (Exception exception) {
                    throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
                }

                return null;

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeMain node, Object... args) throws InterpreterException {
        NodeMiniJaja nodeVars = node.getVars();
        NodeMiniJaja nodeInstrs = node.getInstrs();

        InterpreterMode mode = (InterpreterMode) args[0];

        switch (mode) {
            case DEFAULT:
                onVisit(node);

                callStack.push(new CallStackElement("main", node.getLine(), node.getColumn()));

                nodeVars.accept(this, InterpreterMode.DEFAULT);
                nodeInstrs.accept(this, InterpreterMode.DEFAULT);
                nodeVars.accept(this, InterpreterMode.CANCEL);

                callStack.pop();

                onVisit(node);

                return null;

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeVar node, Object... args) throws InterpreterException {
        NodeMiniJaja nodeExp = node.getExp();

        InterpreterMode mode = (InterpreterMode) args[0];

        switch (mode) {
            case DEFAULT:
                onVisit(node);

                Object value = nodeExp.accept(this, InterpreterMode.EVAL);

                try {
                    memory.declVar(node.generateIdent(), value);
                } catch (Exception exception) {
                    throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
                }

                return null;

            case CANCEL:
                onVisit(node);

                try {
                    memory.removeDecl(node.generateIdent());
                } catch (Exception exception) {
                    throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
                }

                return null;

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeDecls node, Object... args) throws InterpreterException {
        NodeMiniJaja nodeDecl = node.getDecl();
        NodeMiniJaja nodeDecls = node.getDecls();

        InterpreterMode mode = (InterpreterMode) args[0];

        switch (mode) {
            case DEFAULT:
                nodeDecl.accept(this, InterpreterMode.DEFAULT);
                nodeDecls.accept(this, InterpreterMode.DEFAULT);

                return null;

            case CANCEL:
                nodeDecls.accept(this, InterpreterMode.CANCEL);
                nodeDecl.accept(this, InterpreterMode.CANCEL);

                return null;

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeVars node, Object... args) throws InterpreterException {
        NodeMiniJaja nodeVar = node.getVar();
        NodeMiniJaja nodeVars = node.getVars();

        InterpreterMode mode = (InterpreterMode) args[0];

        switch (mode) {
            case DEFAULT:
                nodeVar.accept(this, InterpreterMode.DEFAULT);
                nodeVars.accept(this, InterpreterMode.DEFAULT);

                return null;

            case CANCEL:
                nodeVars.accept(this, InterpreterMode.CANCEL);
                nodeVar.accept(this, InterpreterMode.CANCEL);

                return null;

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeInstrs node, Object... args) throws InterpreterException {
        NodeMiniJaja nodeInstr = node.getInstr();
        NodeMiniJaja nodeInstrs = node.getInstrs();

        InterpreterMode mode = (InterpreterMode) args[0];

        switch (mode) {
            case DEFAULT:
                nodeInstr.accept(this, InterpreterMode.DEFAULT);

                //evaluate instrs only if the method hasn't returned yet
                if (!hasMethodReturned) {
                    nodeInstrs.accept(this, InterpreterMode.DEFAULT);
                }

                return null;

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeIdent node, Object... args) throws InterpreterException {
        InterpreterMode mode = (InterpreterMode) args[0];

        switch (mode) {
            case EVAL:
                Object value;

                try {
                    value = memory.getValue(node.generateNormalIdent());
                } catch (Exception exception) {
                    throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
                }

                if (value == null) {
                    throw new InterpreterException("Variable \"" + node.getValue() + "\" might not have been initialized.", node.getLine(), node.getColumn(), callStack);
                }

                return value;

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeNumber node, Object... args) throws InterpreterException {
        InterpreterMode mode = (InterpreterMode) args[0];

        switch (mode) {
            case EVAL:
                return node.getValue();

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeTrue node, Object... args) throws InterpreterException {
        InterpreterMode mode = (InterpreterMode) args[0];

        switch (mode) {
            case EVAL:
                return true;

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeFalse node, Object... args) throws InterpreterException {
        InterpreterMode mode = (InterpreterMode) args[0];

        switch (mode) {
            case EVAL:
                return false;

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeAdd node, Object... args) throws InterpreterException {
        NodeMiniJaja nodeExp1 = node.getExpLeft();
        NodeMiniJaja nodeExp2 = node.getExpRight();

        InterpreterMode mode = (InterpreterMode) args[0];

        switch (mode) {
            case EVAL:
                int e1 = (int) nodeExp1.accept(this, InterpreterMode.EVAL);
                int e2 = (int) nodeExp2.accept(this, InterpreterMode.EVAL);

                return e1 + e2;

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeAnd node, Object... args) throws InterpreterException {
        NodeMiniJaja nodeExp1 = node.getExpLeft();
        NodeMiniJaja nodeExp2 = node.getExpRight();

        InterpreterMode mode = (InterpreterMode) args[0];

        switch (mode) {
            case EVAL:
                // Short circuit: if exp1 is false then do not evaluate exp2
                boolean cond = (boolean) nodeExp1.accept(this, InterpreterMode.EVAL);

                if (cond) {
                    cond = (boolean) nodeExp2.accept(this, InterpreterMode.EVAL);
                }

                return cond;

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeOr node, Object... args) throws InterpreterException {
        NodeMiniJaja nodeExp1 = node.getExpLeft();
        NodeMiniJaja nodeExp2 = node.getExpRight();

        InterpreterMode mode = (InterpreterMode) args[0];

        switch (mode) {
            case EVAL:
                // Short circuit: if exp1 is true then do not evaluate exp2
                boolean cond = (boolean) nodeExp1.accept(this, InterpreterMode.EVAL);

                if (!cond) {
                    cond = (boolean) nodeExp2.accept(this, InterpreterMode.EVAL);
                }

                return cond;

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeNeg node, Object... args) throws InterpreterException {
        NodeMiniJaja nodeExp = node.getExp();

        InterpreterMode mode = (InterpreterMode) args[0];

        switch (mode) {
            case EVAL:
                int e = (int) nodeExp.accept(this, InterpreterMode.EVAL);

                return -e;

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeMul node, Object... args) throws InterpreterException {
        NodeMiniJaja nodeExp1 = node.getExpLeft();
        NodeMiniJaja nodeExp2 = node.getExpRight();

        InterpreterMode mode = (InterpreterMode) args[0];

        switch (mode) {
            case EVAL:
                int e1 = (int) nodeExp1.accept(this, InterpreterMode.EVAL);
                int e2 = (int) nodeExp2.accept(this, InterpreterMode.EVAL);

                return e1 * e2;

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeNot node, Object... args) throws InterpreterException {
        NodeMiniJaja nodeExp = node.getExp();

        InterpreterMode mode = (InterpreterMode) args[0];

        switch (mode) {
            case EVAL:
                boolean e = (boolean) nodeExp.accept(this, InterpreterMode.EVAL);

                return !e;

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeSub node, Object... args) throws InterpreterException {
        NodeMiniJaja nodeExp1 = node.getExpLeft();
        NodeMiniJaja nodeExp2 = node.getExpRight();

        InterpreterMode mode = (InterpreterMode) args[0];

        switch (mode) {
            case EVAL:
                int e1 = (int) nodeExp1.accept(this, InterpreterMode.EVAL);
                int e2 = (int) nodeExp2.accept(this, InterpreterMode.EVAL);

                return e1 - e2;

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeSup node, Object... args) throws InterpreterException {
        NodeMiniJaja nodeExp1 = node.getExpLeft();
        NodeMiniJaja nodeExp2 = node.getExpRight();

        InterpreterMode mode = (InterpreterMode) args[0];

        switch (mode) {
            case EVAL:
                int e1 = (int) nodeExp1.accept(this, InterpreterMode.EVAL);
                int e2 = (int) nodeExp2.accept(this, InterpreterMode.EVAL);

                return e1 > e2;

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeCmp node, Object... args) throws InterpreterException {
        NodeMiniJaja nodeExp1 = node.getExpLeft();
        NodeMiniJaja nodeExp2 = node.getExpRight();

        InterpreterMode mode = (InterpreterMode) args[0];

        switch (mode) {
            case EVAL:
                Object e1 = nodeExp1.accept(this, InterpreterMode.EVAL);
                Object e2 = nodeExp2.accept(this, InterpreterMode.EVAL);

                return e1.equals(e2);

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeDiv node, Object... args) throws InterpreterException {
        NodeMiniJaja nodeExp1 = node.getExpLeft();
        NodeMiniJaja nodeExp2 = node.getExpRight();

        InterpreterMode mode = (InterpreterMode) args[0];

        switch (mode) {
            case EVAL:
                int e1 = (int) nodeExp1.accept(this, InterpreterMode.EVAL);
                int e2 = (int) nodeExp2.accept(this, InterpreterMode.EVAL);

                try {
                    return e1 / e2;
                } catch (ArithmeticException exception) {
                    throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
                }

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeCst node, Object... args) throws InterpreterException {
        NodeMiniJaja nodeExp = node.getExp();

        InterpreterMode mode = (InterpreterMode) args[0];

        switch (mode) {
            case DEFAULT:
                onVisit(node);

                Object value = nodeExp.accept(this, InterpreterMode.EVAL);

                try {
                    memory.declCst(node.generateIdent(), value);
                } catch (Exception exception) {
                    throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
                }

                return null;

            case CANCEL:
                onVisit(node);

                try {
                    memory.removeDecl(node.generateIdent());
                } catch (Exception exception) {
                    throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
                }

                return null;

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeReturn node, Object... args) throws InterpreterException {
        NodeMiniJaja nodeExp = node.getExp();

        InterpreterMode mode = (InterpreterMode) args[0];

        switch (mode) {
            case DEFAULT:
                onVisit(node);

                Object value = nodeExp.accept(this, InterpreterMode.EVAL);

                try {
                    memory.assignVal(memory.getClassName(), value);
                } catch (Exception exception) {
                    throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
                }

                //set hasMethodReturned to true
                this.hasMethodReturned = true;

                return null;

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeSum node, Object... args) throws InterpreterException {
        NodeMiniJaja nodeIdent = node.getIdent();
        NodeMiniJaja nodeExp = node.getExp();

        InterpreterMode mode = (InterpreterMode) args[0];

        switch (mode) {
            case DEFAULT:
                onVisit(node);

                int current = (int) nodeIdent.accept(this, InterpreterMode.EVAL);
                int value = (int) nodeExp.accept(this, InterpreterMode.EVAL);

                if (nodeIdent instanceof NodeArrayGet) {
                    NodeMiniJaja nodeIndex = ((NodeArrayGet) nodeIdent).getExp();

                    int index = (int) nodeIndex.accept(this, InterpreterMode.EVAL);

                    try {
                        memory.assignValArray(((NodeArrayGet) nodeIdent).generateIdent(), current + value, index);
                    } catch (Exception exception) {
                        throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
                    }
                } else {
                    try {
                        memory.assignVal(((NodeIdent) nodeIdent).generateNormalIdent(), current + value);
                    } catch (Exception exception) {
                        throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
                    }
                }

                return null;

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeMiniJaja node, Object... args) throws InterpreterException {
        return null;
    }

    @Override
    public Object visit(NodeAssignment node, Object... args) throws InterpreterException {
        NodeMiniJaja nodeIdent = node.getIdent();
        NodeMiniJaja nodeExp = node.getExp();

        InterpreterMode mode = (InterpreterMode) args[0];

        switch (mode) {
            case DEFAULT:
                onVisit(node);

                Object value = nodeExp.accept(this, InterpreterMode.EVAL);

                if (nodeIdent instanceof NodeArrayGet) {
                    NodeMiniJaja nodeIndex = ((NodeArrayGet) nodeIdent).getExp();

                    int index = (int) nodeIndex.accept(this, InterpreterMode.EVAL);

                    try {
                        memory.assignValArray(((NodeArrayGet) nodeIdent).generateIdent(), value, index);
                    } catch (Exception exception) {
                        throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
                    }
                } else {
                    try {
                        memory.assignVal(((NodeIdent) nodeIdent).generateNormalIdent(), value);
                    } catch (Exception exception) {
                        throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
                    }
                }

                return null;

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeArray node, Object... args) throws InterpreterException {
        NodeMiniJaja nodeExp = node.getExp();

        InterpreterMode mode = (InterpreterMode) args[0];

        switch (mode) {
            case DEFAULT:
                onVisit(node);

                int size = (int) nodeExp.accept(this, InterpreterMode.EVAL);

                try {
                    memory.declArray(node.generateIdent(), size);
                } catch (Exception exception) {
                    throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
                }

                return null;

            case CANCEL:
                onVisit(node);

                try {
                    memory.removeDecl(node.generateIdent());
                } catch (Exception exception) {
                    throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
                }

            default:
                return null;
        }

    }

    @Override
    public Object visit(NodeArrayGet node, Object... args) throws InterpreterException {
        NodeMiniJaja nodeExp = node.getExp();

        InterpreterMode mode = (InterpreterMode) args[0];

        switch (mode) {
            case EVAL:
                int index = (int) nodeExp.accept(this, InterpreterMode.EVAL);

                try {
                    return memory.getValueArray(node.generateIdent(), index);
                } catch (Exception exception) {
                    throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
                }

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeBoolean node, Object... args) throws InterpreterException {
        return null;
    }

    @Override
    public Object visit(NodeCallE node, Object... args) throws InterpreterException {
        NodeMiniJaja nodeIdent = node.getIdent();
        NodeMiniJaja nodeExpList = node.getExpList();

        InterpreterMode mode = (InterpreterMode) args[0];

        switch (mode) {
            case EVAL:
                NodeMiniJaja nodeCallI = new NodeCallI();

                nodeCallI.addChild(nodeIdent);
                nodeCallI.addChild(nodeExpList);

                nodeCallI.accept(this, InterpreterMode.DEFAULT, true);

                try {
                    return memory.getValue(memory.getClassName());
                } catch (Exception exception) {
                    throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
                }

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeCallI node, Object... args) throws InterpreterException {
        NodeMiniJaja nodeExpList = node.getExpList();

        InterpreterMode mode = (InterpreterMode) args[0];

        switch (mode) {
            case DEFAULT:
                if (args.length == 1) {
                    onVisit(node);
                }

                NodeMethod nodeMethod;

                try {
                    nodeMethod = memory.getValue(node.generateIdent());
                } catch (Exception exception) {
                    throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
                }

                NodeMiniJaja nodeHeaders = nodeMethod.getHeaders();
                NodeMiniJaja nodeVars = nodeMethod.getVars();
                NodeMiniJaja nodeInstrs = nodeMethod.getInstrs();

                onVisit(nodeMethod);

                callStack.push(new CallStackElement(node.getIdentValue(), nodeMethod.getLine(), nodeMethod.getColumn()));

                // Declare method params
                NodeMiniJaja expList = nodeExpList;
                NodeMiniJaja headers = nodeHeaders;

                while (expList instanceof NodeExpList && headers instanceof NodeHeaders) {
                    NodeMiniJaja nodeExp = ((NodeExpList) expList).getExp();
                    NodeMiniJaja nodeHeader = ((NodeHeaders) headers).getHeader();

                    Object value = nodeExp.accept(this, InterpreterMode.EVAL);

                    try {
                        memory.declVar(((NodeHeader) nodeHeader).generateIdent(), value);
                    } catch (Exception exception) {
                        throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
                    }

                    expList = ((NodeExpList) expList).getExpList();
                    headers = ((NodeHeaders) headers).getHeaders();
                }

                // Declare method vars
                nodeVars.accept(this, InterpreterMode.DEFAULT);

                // Execute method instrs
                try {
                    nodeInstrs.accept(this, InterpreterMode.DEFAULT);
                } catch (StackOverflowError error) {
                    throw new InterpreterException("Maximum call stack size exceeded.", node.getLine(), node.getColumn(), callStack);
                }

                //reset hasMethodReturned
                this.hasMethodReturned = false;

                // Remove method vars
                nodeVars.accept(this, InterpreterMode.CANCEL);

                // Remove method params
                nodeHeaders.accept(this, InterpreterMode.CANCEL);

                callStack.pop();

                return null;

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeEnil node, Object... args) throws InterpreterException {
        return null;
    }

    @Override
    public Object visit(NodeExnil node, Object... args) throws InterpreterException {
        return null;
    }

    @Override
    public Object visit(NodeExpList node, Object... args) throws InterpreterException {
        return null;
    }

    @Override
    public Object visit(NodeHeader node, Object... args) throws InterpreterException {
        InterpreterMode mode = (InterpreterMode) args[0];

        switch (mode) {
            case CANCEL:
                try {
                    memory.removeDecl(node.generateIdent());
                } catch (Exception exception) {
                    throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
                }

                return null;

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeHeaders node, Object... args) throws InterpreterException {
        NodeMiniJaja nodeHeader = node.getHeader();
        NodeMiniJaja nodHeaders = node.getHeaders();

        InterpreterMode mode = (InterpreterMode) args[0];

        switch (mode) {
            case CANCEL:
                nodHeaders.accept(this, InterpreterMode.CANCEL);
                nodeHeader.accept(this, InterpreterMode.CANCEL);

                return null;

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeIf node, Object... args) throws InterpreterException {
        NodeMiniJaja nodeExp = node.getExp();
        NodeMiniJaja nodeInstrs1 = node.getInstrs();
        NodeMiniJaja nodeInstrs2 = node.getInstrsElse();

        InterpreterMode mode = (InterpreterMode) args[0];

        switch (mode) {
            case DEFAULT:
                onVisit(node);

                boolean exp = (boolean) nodeExp.accept(this, InterpreterMode.EVAL);

                if (exp) {
                    nodeInstrs1.accept(this, InterpreterMode.DEFAULT);
                } else {
                    nodeInstrs2.accept(this, InterpreterMode.DEFAULT);
                }

                return null;

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeIncrement node, Object... args) throws InterpreterException {
        NodeMiniJaja nodeIdent = node.getIdent();

        InterpreterMode mode = (InterpreterMode) args[0];

        switch (mode) {
            case DEFAULT:
                onVisit(node);

                int current = (int) nodeIdent.accept(this, InterpreterMode.EVAL);

                if (nodeIdent instanceof NodeArrayGet) {
                    NodeMiniJaja nodeIndex = ((NodeArrayGet) nodeIdent).getExp();

                    int index = (int) nodeIndex.accept(this, InterpreterMode.EVAL);

                    try {
                        memory.assignValArray(((NodeArrayGet) nodeIdent).generateIdent(), current + 1, index);
                    } catch (Exception exception) {
                        throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
                    }
                } else {
                    try {
                        memory.assignVal(((NodeIdent) nodeIdent).generateNormalIdent(), current + 1);
                    } catch (Exception exception) {
                        throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
                    }
                }

                return null;

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeInil node, Object... args) throws InterpreterException {
        return null;
    }

    @Override
    public Object visit(NodeInteger node, Object... args) throws InterpreterException {
        return null;
    }

    @Override
    public Object visit(NodeMethod node, Object... args) throws InterpreterException {
        InterpreterMode mode = (InterpreterMode) args[0];

        switch (mode) {
            case DEFAULT:
                onVisit(node);

                try {
                    memory.declMethod(node.generateIdent(), node);
                } catch (Exception exception) {
                    throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
                }

                return null;

            case CANCEL:
                onVisit(node);

                try {
                    memory.removeDecl(node.generateIdent());
                } catch (Exception exception) {
                    throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
                }

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeVnil node, Object... args) throws InterpreterException {
        return null;
    }

    @Override
    public Object visit(NodeVoid node, Object... args) throws InterpreterException {
        return null;
    }

    @Override
    public Object visit(NodeWhile node, Object... args) throws InterpreterException {
        NodeMiniJaja nodeExp = node.getExp();
        NodeMiniJaja nodeInstrs = node.getInstrs();

        InterpreterMode mode = (InterpreterMode) args[0];

        switch (mode) {
            case DEFAULT:
                onVisit(node);

                int n = 0;

                while ((boolean) nodeExp.accept(this, InterpreterMode.EVAL)) {
                    if (n++ > VM.MAX_LOOP) {
                        throw new InterpreterException("Infinite loop detected.", node.getLine(), node.getColumn(), callStack);
                    }

                    nodeInstrs.accept(this, InterpreterMode.DEFAULT);

                    onVisit(node);
                }

                return null;

            default:
                return null;
        }
    }

    @Override
    public Object visit(NodeOmega node, Object... args) throws InterpreterException {
        return null;
    }
}
