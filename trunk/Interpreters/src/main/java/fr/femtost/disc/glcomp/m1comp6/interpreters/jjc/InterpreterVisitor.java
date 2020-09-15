package fr.femtost.disc.glcomp.m1comp6.interpreters.jjc;

import fr.femtost.disc.glcomp.m1comp6.ast.common.CallStackElement;
import fr.femtost.disc.glcomp.m1comp6.ast.common.JajaCodeASTVisitor;
import fr.femtost.disc.glcomp.m1comp6.ast.common.Kind;
import fr.femtost.disc.glcomp.m1comp6.ast.common.VM;
import fr.femtost.disc.glcomp.m1comp6.ast.jjc.*;
import fr.femtost.disc.glcomp.m1comp6.debuggers.Debugger;
import fr.femtost.disc.glcomp.m1comp6.memory.Memory;

import java.util.Stack;

public class InterpreterVisitor implements JajaCodeASTVisitor<Object, InterpreterException> {
    private Debugger debugger;
    private Memory memory;

    private Stack<CallStackElement> callStack;

    private boolean debuggerActive;

    private int adr;

    private int n;
    private NodeGoto lastGoto;

    public InterpreterVisitor() {
        callStack = new Stack<>();
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

    public void setAdr(int adr) {
        this.adr = adr;
    }

    public int getAdr() {
        return adr;
    }

    public void onVisit(NodeJajaCode node) {
        if (debugger != null && debuggerActive) {
            try {
                debugger.onVisit(node);
            } catch (Exception exception) {
                debuggerActive = false;
            }
        }
    }

    @Override
    public Object visit(NodeInit node, Object... args) throws InterpreterException {
        onVisit(node);

        adr += 1;

        return null;
    }

    @Override
    public Object visit(NodePop node, Object... args) throws InterpreterException {
        onVisit(node);

        try {
            memory.pop();
        } catch (Exception exception) {
            throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
        }

        adr += 1;

        return null;
    }

    @Override
    public Object visit(NodePush node, Object... args) throws InterpreterException {
        onVisit(node);

        NodeJajaCode nodeValue = node.getValue();

        Object value = nodeValue.accept(this);

        try {
            memory.push(value);
        } catch (Exception exception) {
            throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
        }

        adr += 1;

        return null;
    }

    @Override
    public Object visit(NodeSwap node, Object... args) throws InterpreterException {
        onVisit(node);

        try {
            memory.swap();
        } catch (Exception exception) {
            throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
        }

        adr += 1;

        return null;
    }

    @Override
    public Object visit(NodeIdent node, Object... args) throws InterpreterException {
        return null;
    }

    @Override
    public Object visit(NodeType node, Object... args) throws InterpreterException {
        return null;
    }

    @Override
    public Object visit(NodeAdd node, Object... args) throws InterpreterException {
        onVisit(node);

        try {
            // Get value on top of stack
            int value2 = memory.popValue();

            // Get second value on top of stack
            int value1 = memory.popValue();

            // Push result into memory
            memory.push(value1 + value2);
        } catch (Exception exception) {
            throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
        }

        adr += 1;

        return null;
    }

    @Override
    public Object visit(NodeKind node, Object... args) throws InterpreterException {
        return null;
    }

    @Override
    public Integer visit(NodeNumber node, Object... args) throws InterpreterException {
        return node.getValue();
    }

    @Override
    public Boolean visit(NodeFalse node, Object... args) throws InterpreterException {
        return false;
    }

    @Override
    public Boolean visit(NodeTrue node, Object... args) throws InterpreterException {
        return true;
    }

    @Override
    public Object visit(NodeJcnil node, Object... args) throws InterpreterException {
        return null;
    }

    @Override
    public Object visit(NodeJcstop node, Object... args) throws InterpreterException {
        onVisit(node);

        adr = -1;

        return null;
    }

    @Override
    public Object visit(NodeNew node, Object... args) throws InterpreterException {
        onVisit(node);

        String ident = node.getIdent();
        Kind kind = node.getKind();

        int valuePosition = node.getValuePosition();

        switch (kind) {
            case VARIABLE:
                try {
                    memory.identVal(ident, valuePosition);
                } catch (Exception exception) {
                    throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
                }

                break;

            case CONSTANT:
                try {
                    // Get the value on top of the stack
                    Object value = memory.popValue();

                    memory.declCst(ident, value);
                } catch (Exception exception) {
                    throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
                }

                break;

            case METHOD:
                try {
                    // Get the value on top of the stack
                    Object value = memory.popValue();

                    memory.declMethod(ident, value);
                } catch (Exception exception) {
                    throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
                }

                break;

            default:
                throw new InterpreterException("Invalid declaration.", node.getLine(), node.getColumn(), callStack);
        }

        adr += 1;

        return null;
    }

    @Override
    public Object visit(NodeInstrs node, Object... args) throws InterpreterException {
        adr = 1;

        while (adr != -1) {
            NodeJajaCode nodeInstr = (NodeJajaCode) node.getChild(adr - 1);

            nodeInstr.accept(this);
        }

        return null;
    }

    @Override
    public Object visit(NodeAinc node, Object... args) throws InterpreterException {
        onVisit(node);

        String ident = node.getIdent();

        try {
            // Get value on top of stack
            int value = memory.popValue();

            // Get second value on top of stack
            int index = memory.popValue();

            int current = memory.getValueArray(ident, index);

            memory.assignValArray(ident, current + value, index);
        } catch (Exception exception) {
            throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
        }

        adr += 1;

        return null;
    }

    @Override
    public Object visit(NodeAload node, Object... args) throws InterpreterException {
        onVisit(node);

        String ident = node.getIdent();

        try {
            // Get value on top of stack
            int index = memory.popValue();

            // Get value associated with ident
            Object value = memory.getValueArray(ident, index);

            memory.push(value);
        } catch (Exception exception) {
            throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
        }

        adr += 1;

        return null;
    }

    @Override
    public Object visit(NodeAnd node, Object... args) throws InterpreterException {
        onVisit(node);

        try {
            // Get value on top of stack
            boolean value2 = memory.popValue();

            // Get second value on top of stack
            boolean value1 = memory.popValue();

            // Push result into memory
            memory.push(value1 && value2);
        } catch (Exception exception) {
            throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
        }

        adr += 1;

        return null;
    }

    @Override
    public Object visit(NodeAstore node, Object... args) throws InterpreterException {
        onVisit(node);

        String ident = node.getIdent();

        try {
            // Get value on top of stack
            Object value = memory.popValue();

            // Get second value on top of stack
            int index = memory.popValue();

            memory.assignValArray(ident, value, index);
        } catch (Exception exception) {
            throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
        }

        adr += 1;

        return null;
    }

    @Override
    public Object visit(NodeCmp node, Object... args) throws InterpreterException {
        onVisit(node);

        try {
            // Get value on top of stack
            Object value2 = memory.popValue();

            // Get second value on top of stack
            Object value1 = memory.popValue();

            // Push result into memory
            memory.push(value1.equals(value2));
        } catch (Exception exception) {
            throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
        }

        adr += 1;

        return null;
    }

    @Override
    public Object visit(NodeDiv node, Object... args) throws InterpreterException {
        onVisit(node);

        try {
            // Get value on top of stack
            int value2 = memory.popValue();

            // Get second value on top of stack
            int value1 = memory.popValue();

            // Push result into memory
            memory.push(value1 / value2);
        } catch (Exception exception) {
            throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
        }

        adr += 1;

        return null;
    }

    @Override
    public Object visit(NodeGoto node, Object... args) throws InterpreterException {
        onVisit(node);

        if (lastGoto == node) {
            if (n++ > VM.MAX_LOOP) {
                throw new InterpreterException("Infinite loop detected.", node.getLine(), node.getColumn(), callStack);
            }
        } else {
            n = 0;
            lastGoto = node;
        }

        adr = node.getAddress();

        return null;
    }

    @Override
    public Object visit(NodeIf node, Object... args) throws InterpreterException {
        onVisit(node);

        int address = node.getAddress();

        try {
            // Get value on top of stack
            boolean cond = memory.popValue();

            if (cond) {
                adr = address;
            } else {
                adr += 1;
            }
        } catch (Exception exception) {
            throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
        }

        return null;
    }

    @Override
    public Object visit(NodeInc node, Object... args) throws InterpreterException {
        onVisit(node);

        String ident = node.getIdent();

        Integer value;
        Integer current;

        try {
            // Get value on top of stack
            value = memory.popValue();
            current = memory.getValue(ident);
        } catch (Exception exception) {
            throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
        }

        if (current == null) {
            // Check that the variable has been initialized
            throw new InterpreterException("Variable \"" + ident + "\" might not have been initialized.", node.getLine(), node.getColumn(), callStack);
        }

        try {
            memory.assignVal(ident, current + value);
        } catch (Exception exception) {
            throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
        }

        adr += 1;

        return null;
    }

    @Override
    public Object visit(NodeInvoke node, Object... args) throws InterpreterException {
        onVisit(node);

        if (callStack.size() > VM.MAX_STACK_SIZE) {
            throw new InterpreterException("Maximum call stack size exceeded.", node.getLine(), node.getColumn(), callStack);
        }

        String ident = node.getIdent();

        callStack.push(new CallStackElement(ident, node.getLine(), node.getColumn()));

        try {
            // Get method adr
            int value = memory.getValue(ident);

            // Save current adr
            memory.push(adr + 1);

            // Go to method
            adr = value;
        } catch (Exception exception) {
            throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
        }

        return null;
    }

    @Override
    public Object visit(NodeJajaCode node, Object... args) throws InterpreterException {
        return null;
    }

    @Override
    public Object visit(NodeLoad node, Object... args) throws InterpreterException {
        onVisit(node);

        String ident = node.getIdent();

        Object value;

        try {
            value = memory.getValue(ident);
        } catch (Exception exception) {
            throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
        }

        if (value == null) {
            throw new InterpreterException("Variable \"" + ident + "\" might not have been initialized.", node.getLine(), node.getColumn(), callStack);
        }

        try {
            memory.push(value);
        } catch (Exception exception) {
            throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
        }

        adr += 1;

        return null;
    }

    @Override
    public Object visit(NodeMul node, Object... args) throws InterpreterException {
        onVisit(node);

        try {
            // Get value on top of stack
            int value2 = memory.popValue();

            // Get second value on top of stack
            int value1 = memory.popValue();

            // Push result into memory
            memory.push(value1 * value2);
        } catch (Exception exception) {
            throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
        }

        adr += 1;

        return null;
    }

    @Override
    public Object visit(NodeNeg node, Object... args) throws InterpreterException {
        onVisit(node);

        try {
            // Get value on top of stack
            int value = memory.popValue();

            memory.push(-value);
        } catch (Exception exception) {
            throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
        }

        adr += 1;

        return null;
    }

    @Override
    public Object visit(NodeNewArray node, Object... args) throws InterpreterException {
        onVisit(node);

        String ident = node.getIdent();

        try {
            // Get value on top of stack
            int size = memory.popValue();

            memory.declArray(ident, size);
        } catch (Exception exception) {
            throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
        }

        adr += 1;

        return null;
    }

    @Override
    public Object visit(NodeNop node, Object... args) throws InterpreterException {
        onVisit(node);

        adr += 1;

        return null;
    }

    @Override
    public Object visit(NodeNot node, Object... args) throws InterpreterException {
        onVisit(node);

        try {
            // Get value on top of stack
            boolean value = memory.popValue();

            memory.push(!value);
        } catch (Exception exception) {
            throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
        }

        adr += 1;

        return null;
    }

    @Override
    public Object visit(NodeOr node, Object... args) throws InterpreterException {
        onVisit(node);

        try {
            // Get value on top of stack
            boolean value2 = memory.popValue();

            // Get second value on top of stack
            boolean value1 = memory.popValue();

            // Push result into memory
            memory.push(value1 || value2);
        } catch (Exception exception) {
            throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
        }

        adr += 1;

        return null;
    }

    @Override
    public Object visit(NodeReturn node, Object... args) throws InterpreterException {
        onVisit(node);

        try {
            callStack.pop();
        } catch (Exception exception) {
            // Nothing to do
        }

        try {
            adr = memory.popValue();
        } catch (Exception exception) {
            throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
        }

        return null;
    }

    @Override
    public Object visit(NodeStore node, Object... args) throws InterpreterException {
        onVisit(node);

        String ident = node.getIdent();

        try {
            // Get value on top of stack
            Object value = memory.popValue();

            memory.assignVal(ident, value);
        } catch (Exception exception) {
            throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
        }

        adr += 1;

        return null;
    }

    @Override
    public Object visit(NodeSub node, Object... args) throws InterpreterException {
        onVisit(node);

        try {
            // Get value on top of stack
            int value2 = memory.popValue();

            // Get second value on top of stack
            int value1 = memory.popValue();

            // Push result into memory
            memory.push(value1 - value2);
        } catch (Exception exception) {
            throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
        }

        adr += 1;

        return null;
    }

    @Override
    public Object visit(NodeSup node, Object... args) throws InterpreterException {
        onVisit(node);

        try {
            // Get value on top of stack
            int value2 = memory.popValue();

            // Get second value on top of stack
            int value1 = memory.popValue();

            // Push result into memory
            memory.push(value1 > value2);
        } catch (Exception exception) {
            throw new InterpreterException(exception.getMessage(), node.getLine(), node.getColumn(), callStack);
        }

        adr += 1;

        return null;
    }
}
