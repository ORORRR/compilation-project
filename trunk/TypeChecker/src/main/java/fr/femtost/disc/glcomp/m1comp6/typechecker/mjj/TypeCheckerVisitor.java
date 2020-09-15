package fr.femtost.disc.glcomp.m1comp6.typechecker.mjj;

import fr.femtost.disc.glcomp.m1comp6.ast.common.Kind;
import fr.femtost.disc.glcomp.m1comp6.ast.common.MiniJajaASTVisitor;
import fr.femtost.disc.glcomp.m1comp6.ast.common.Type;
import fr.femtost.disc.glcomp.m1comp6.ast.mjj.*;
import fr.femtost.disc.glcomp.m1comp6.memory.ExistingSymbolException;
import fr.femtost.disc.glcomp.m1comp6.memory.SymbolNode;
import fr.femtost.disc.glcomp.m1comp6.memory.SymbolTable;
import fr.femtost.disc.glcomp.m1comp6.memory.UnknownSymbolException;

public class TypeCheckerVisitor implements MiniJajaASTVisitor<Type, TypeCheckerException> {
    private SymbolTable symbolTable;

    public TypeCheckerVisitor() {
        // Nothing to do
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    @Override
    public Type visit(NodeClass node, Object... args) throws TypeCheckerException {
        NodeMiniJaja nodeDecls = node.getDecls();
        NodeMiniJaja nodeMain = node.getMain();

        String scope = TypeChecker.SCOPE_GLOBAL;
        Integer n = (Integer) args[1];
        TypeCheckerMode mode = (TypeCheckerMode) args[2];

        // Update scope
        node.setIdentScope(scope);

        if (n == TypeChecker.FIRST_PASS) {
            SymbolNode symbolNode = new SymbolNode(node.generateIdent(), Kind.VARIABLE, null);

            try {
                symbolTable.put(symbolNode);
            } catch (ExistingSymbolException exception) {
                throw new TypeCheckerException("The symbol \"" + node.getIdentValue() + "\" has already been declared.", node.getLine(), node.getColumn());
            }
        }

        nodeDecls.accept(this, scope, n, mode);
        nodeMain.accept(this, scope, n, mode);

        return null;
    }

    @Override
    public Type visit(NodeDecls node, Object... args) throws TypeCheckerException {
        NodeMiniJaja nodeDecl = node.getDecl();
        NodeMiniJaja nodeDecls = node.getDecls();

        String scope = (String) args[0];
        Integer n = (Integer) args[1];
        TypeCheckerMode mode = (TypeCheckerMode) args[2];

        nodeDecl.accept(this, scope, n, mode);
        nodeDecls.accept(this, scope, n, mode);

        return null;
    }

    @Override
    public Type visit(NodeVars node, Object... args) throws TypeCheckerException {
        NodeMiniJaja nodeVar = node.getVar();
        NodeMiniJaja nodeVars = node.getVars();

        String scope = (String) args[0];
        Integer n = (Integer) args[1];
        TypeCheckerMode mode = (TypeCheckerMode) args[2];

        nodeVar.accept(this, scope, n, mode);
        nodeVars.accept(this, scope, n, mode);

        return null;
    }

    @Override
    public Type visit(NodeVar node, Object... args) throws TypeCheckerException {
        NodeMiniJaja nodeType = node.getType();
        NodeMiniJaja nodeExp = node.getExp();

        String scope = (String) args[0];
        Integer n = (Integer) args[1];
        TypeCheckerMode mode = (TypeCheckerMode) args[2];

        // Update scope
        node.setIdentScope(scope);

        if (n == TypeChecker.FIRST_PASS) {
            Type type = nodeType.accept(this, scope, n, mode);
            Type expType = nodeExp.accept(this, scope, n, TypeCheckerMode.VALUE);

            if (type == Type.VOID) {
                throw new TypeCheckerException("Can not declare a variable of type " + type + ".", node.getLine(), node.getColumn());
            }

            if (expType != null && expType != type) {
                throw new TypeCheckerException("A value of type " + expType + " is not compatible with the symbol \"" + node.getIdentValue() + "\" of type " + type + ".", node.getLine(), node.getColumn());
            }

            SymbolNode symbolNode = new SymbolNode(node.generateIdent(), Kind.VARIABLE, type);

            try {
                symbolTable.put(symbolNode);
            } catch (ExistingSymbolException exception) {
                throw new TypeCheckerException("The symbol \"" + node.getIdentValue() + "\" has already been declared.", node.getLine(), node.getColumn());
            }
        }

        return null;
    }

    @Override
    public Type visit(NodeMain node, Object... args) throws TypeCheckerException {
        NodeMiniJaja nodeVars = node.getVars();
        NodeMiniJaja nodeInstrs = node.getInstrs();

        String scope = TypeChecker.SCOPE_MAIN;
        Integer n = (Integer) args[1];
        TypeCheckerMode mode = (TypeCheckerMode) args[2];

        nodeVars.accept(this, scope, n, mode);
        nodeInstrs.accept(this, scope, n, mode);

        return null;
    }

    @Override
    public Type visit(NodeInstrs node, Object... args) throws TypeCheckerException {
        NodeMiniJaja nodeInstr = node.getInstr();
        NodeMiniJaja nodeInstrs = node.getInstrs();

        String scope = (String) args[0];
        Integer n = (Integer) args[1];
        TypeCheckerMode mode = (TypeCheckerMode) args[2];

        if (n == TypeChecker.SECOND_PASS) {
            nodeInstr.accept(this, scope, n, mode);
            nodeInstrs.accept(this, scope, n, mode);
        }

        return null;
    }

    @Override
    public Type visit(NodeIdent node, Object... args) throws TypeCheckerException {
        String scope = (String) args[0];
        Integer n = (Integer) args[1];
        TypeCheckerMode mode = (TypeCheckerMode) args[2];

        // Update scope
        node.setScope(scope);

        SymbolNode symbolNode;

        try {
            symbolNode = symbolTable.get(node.generateNormalIdent());
        } catch (UnknownSymbolException exception1) {
            scope = TypeChecker.SCOPE_GLOBAL;

            // Update scope
            node.setScope(scope);

            try {
                symbolNode = symbolTable.get(node.generateNormalIdent());
            } catch (UnknownSymbolException exception2) {
                throw new TypeCheckerException("The symbol \"" + node.getValue() + "\" has not been declared.", node.getLine(), node.getColumn());
            }
        }

        switch (mode) {
            case VALUE:
                if (symbolNode.getKind() != Kind.VARIABLE && symbolNode.getKind() != Kind.CONSTANT) {
                    throw new TypeCheckerException("The symbol \"" + node.getValue() + "\" is not a value.", node.getLine(), node.getColumn());
                }

                break;

            case VARIABLE:
                if (symbolNode.getKind() != Kind.VARIABLE) {
                    throw new TypeCheckerException("The symbol \"" + node.getValue() + "\" is not a variable.", node.getLine(), node.getColumn());
                }

                break;

            case CONSTANT:
                if (symbolNode.getKind() != Kind.CONSTANT) {
                    throw new TypeCheckerException("The symbol \"" + node.getValue() + "\" is not a constant.", node.getLine(), node.getColumn());
                }

                break;

            case VCONSTANT:
                if (symbolNode.getKind() != Kind.VCONSTANT) {
                    throw new TypeCheckerException("The symbol \"" + node.getValue() + "\" is not a not-initialized constant.", node.getLine(), node.getColumn());
                }

                break;

            case ARRAY:
                if (symbolNode.getKind() != Kind.ARRAY) {
                    throw new TypeCheckerException("The symbol \"" + node.getValue() + "\" is not an array.", node.getLine(), node.getColumn());
                }

                break;

            case METHOD:
                if (symbolNode.getKind() != Kind.METHOD) {
                    throw new TypeCheckerException("The symbol \"" + node.getValue() + "\" is not a method.", node.getLine(), node.getColumn());
                }

                break;

            case ANY:
            default:
                break;
        }

        return symbolNode.getType();
    }

    @Override
    public Type visit(NodeNumber node, Object... args) throws TypeCheckerException {
        return Type.INTEGER;
    }

    @Override
    public Type visit(NodeTrue node, Object... args) throws TypeCheckerException {
        return Type.BOOLEAN;
    }

    @Override
    public Type visit(NodeFalse node, Object... args) throws TypeCheckerException {
        return Type.BOOLEAN;
    }

    @Override
    public Type visit(NodeVoid node, Object... args) throws TypeCheckerException {
        return Type.VOID;
    }

    @Override
    public Type visit(NodeBoolean node, Object... args) throws TypeCheckerException {
        return Type.BOOLEAN;
    }

    @Override
    public Type visit(NodeInteger node, Object... args) throws TypeCheckerException {
        return Type.INTEGER;
    }

    @Override
    public Type visit(NodeAdd node, Object... args) throws TypeCheckerException {
        NodeMiniJaja nodeExp1 = node.getExpLeft();
        NodeMiniJaja nodeExp2 = node.getExpRight();

        String scope = (String) args[0];
        Integer n = (Integer) args[1];
        TypeCheckerMode mode = (TypeCheckerMode) args[2];

        Type expType1 = nodeExp1.accept(this, scope, n, TypeCheckerMode.VALUE);
        Type expType2 = nodeExp2.accept(this, scope, n, TypeCheckerMode.VALUE);

        if (expType1 != Type.INTEGER) {
            throw new TypeCheckerException("A value of type " + expType1 + " is not compatible with the ADD (+) operator.", node.getLine(), node.getColumn());
        }

        if (expType2 != Type.INTEGER) {
            throw new TypeCheckerException("A value of type " + expType2 + " is not compatible with the ADD (+) operator.", node.getLine(), node.getColumn());
        }

        return Type.INTEGER;
    }

    @Override
    public Type visit(NodeSub node, Object... args) throws TypeCheckerException {
        NodeMiniJaja nodeExp1 = node.getExpLeft();
        NodeMiniJaja nodeExp2 = node.getExpRight();

        String scope = (String) args[0];
        Integer n = (Integer) args[1];
        TypeCheckerMode mode = (TypeCheckerMode) args[2];

        Type expType1 = nodeExp1.accept(this, scope, n, TypeCheckerMode.VALUE);
        Type expType2 = nodeExp2.accept(this, scope, n, TypeCheckerMode.VALUE);

        if (expType1 != Type.INTEGER) {
            throw new TypeCheckerException("A value of type " + expType1 + " is not compatible with the SUB (-) operator.", node.getLine(), node.getColumn());
        }

        if (expType2 != Type.INTEGER) {
            throw new TypeCheckerException("A value of type " + expType2 + " is not compatible with the SUB (-) operator.", node.getLine(), node.getColumn());
        }

        return Type.INTEGER;
    }

    @Override
    public Type visit(NodeDiv node, Object... args) throws TypeCheckerException {
        NodeMiniJaja nodeExp1 = node.getExpLeft();
        NodeMiniJaja nodeExp2 = node.getExpRight();

        String scope = (String) args[0];
        Integer n = (Integer) args[1];
        TypeCheckerMode mode = (TypeCheckerMode) args[2];

        Type expType1 = nodeExp1.accept(this, scope, n, TypeCheckerMode.VALUE);
        Type expType2 = nodeExp2.accept(this, scope, n, TypeCheckerMode.VALUE);

        if (expType1 != Type.INTEGER) {
            throw new TypeCheckerException("A value of type " + expType1 + " is not compatible with the DIV (/) operator.", node.getLine(), node.getColumn());
        }

        if (expType2 != Type.INTEGER) {
            throw new TypeCheckerException("A value of type " + expType2 + " is not compatible with the DIV (/) operator.", node.getLine(), node.getColumn());
        }

        return Type.INTEGER;
    }

    @Override
    public Type visit(NodeMul node, Object... args) throws TypeCheckerException {
        NodeMiniJaja nodeExp1 = node.getExpLeft();
        NodeMiniJaja nodeExp2 = node.getExpRight();

        String scope = (String) args[0];
        Integer n = (Integer) args[1];
        TypeCheckerMode mode = (TypeCheckerMode) args[2];

        Type expType1 = nodeExp1.accept(this, scope, n, TypeCheckerMode.VALUE);
        Type expType2 = nodeExp2.accept(this, scope, n, TypeCheckerMode.VALUE);

        if (expType1 != Type.INTEGER) {
            throw new TypeCheckerException("A value of type " + expType1 + " is not compatible with the MUL (*) operator.", node.getLine(), node.getColumn());
        }

        if (expType2 != Type.INTEGER) {
            throw new TypeCheckerException("A value of type " + expType2 + " is not compatible with the MUL (*) operator.", node.getLine(), node.getColumn());
        }

        return Type.INTEGER;
    }

    @Override
    public Type visit(NodeNot node, Object... args) throws TypeCheckerException {
        NodeMiniJaja nodeExp = node.getExp();

        String scope = (String) args[0];
        Integer n = (Integer) args[1];
        TypeCheckerMode mode = (TypeCheckerMode) args[2];

        Type expType = nodeExp.accept(this, scope, n, TypeCheckerMode.VALUE);

        if (expType != Type.BOOLEAN) {
            throw new TypeCheckerException("A value of type " + expType + " is not compatible with the NOT (!) operator.", node.getLine(), node.getColumn());
        }

        return Type.BOOLEAN;
    }

    @Override
    public Type visit(NodeOr node, Object... args) throws TypeCheckerException {
        NodeMiniJaja nodeExp1 = node.getExpLeft();
        NodeMiniJaja nodeExp2 = node.getExpRight();

        String scope = (String) args[0];
        Integer n = (Integer) args[1];
        TypeCheckerMode mode = (TypeCheckerMode) args[2];

        Type expType1 = nodeExp1.accept(this, scope, n, TypeCheckerMode.VALUE);
        Type expType2 = nodeExp2.accept(this, scope, n, TypeCheckerMode.VALUE);

        if (expType1 != Type.BOOLEAN) {
            throw new TypeCheckerException("A value of type " + expType1 + " is not compatible with the OR (||) operator.", node.getLine(), node.getColumn());
        }

        if (expType2 != Type.BOOLEAN) {
            throw new TypeCheckerException("A value of type " + expType2 + " is not compatible with the OR (||) operator.", node.getLine(), node.getColumn());
        }

        return Type.BOOLEAN;
    }

    @Override
    public Type visit(NodeAnd node, Object... args) throws TypeCheckerException {
        NodeMiniJaja nodeExp1 = node.getExpLeft();
        NodeMiniJaja nodeExp2 = node.getExpRight();

        String scope = (String) args[0];
        Integer n = (Integer) args[1];
        TypeCheckerMode mode = (TypeCheckerMode) args[2];

        Type expType1 = nodeExp1.accept(this, scope, n, TypeCheckerMode.VALUE);
        Type expType2 = nodeExp2.accept(this, scope, n, TypeCheckerMode.VALUE);

        if (expType1 != Type.BOOLEAN) {
            throw new TypeCheckerException("A value of type " + expType1 + " is not compatible with the AND (&&) operator.", node.getLine(), node.getColumn());
        }

        if (expType2 != Type.BOOLEAN) {
            throw new TypeCheckerException("A value of type " + expType2 + " is not compatible with the AND (&&) operator.", node.getLine(), node.getColumn());
        }

        return Type.BOOLEAN;
    }

    @Override
    public Type visit(NodeSum node, Object... args) throws TypeCheckerException {
        NodeMiniJaja nodeIdent = node.getIdent();
        NodeMiniJaja nodeExp = node.getExp();

        String scope = (String) args[0];
        Integer n = (Integer) args[1];
        TypeCheckerMode mode = (TypeCheckerMode) args[2];

        if (nodeIdent instanceof NodeArrayGet) {
            Type type = nodeIdent.accept(this, scope, n, TypeCheckerMode.VARIABLE);
            Type expType = nodeExp.accept(this, scope, n, TypeCheckerMode.VALUE);

            if (type != Type.INTEGER) {
                throw new TypeCheckerException("Can not add something to an element of an array of type " + type + ".", node.getLine(), node.getColumn());
            }

            if (expType != Type.INTEGER) {
                throw new TypeCheckerException("Can not add a value of type " + expType + " to an element of an array.", node.getLine(), node.getColumn());
            }
        } else {
            Type type = nodeIdent.accept(this, scope, n, TypeCheckerMode.VARIABLE);
            Type expType = nodeExp.accept(this, scope, n, TypeCheckerMode.VALUE);

            if (type != Type.INTEGER) {
                throw new TypeCheckerException("Can not add something to a variable of type " + type + ".", node.getLine(), node.getColumn());
            }

            if (expType != Type.INTEGER) {
                throw new TypeCheckerException("Can not add a value of type " + expType + " to a variable.", node.getLine(), node.getColumn());
            }
        }

        return null;
    }

    @Override
    public Type visit(NodeIncrement node, Object... args) throws TypeCheckerException {
        NodeMiniJaja nodeIdent = node.getIdent();

        String scope = (String) args[0];
        Integer n = (Integer) args[1];
        TypeCheckerMode mode = (TypeCheckerMode) args[2];

        if (nodeIdent instanceof NodeArrayGet) {
            Type type = nodeIdent.accept(this, scope, n, TypeCheckerMode.VARIABLE);

            if (type != Type.INTEGER) {
                throw new TypeCheckerException("Can not increment an element of an array of type " + type + ".", node.getLine(), node.getColumn());
            }
        } else {
            Type type = nodeIdent.accept(this, scope, n, TypeCheckerMode.VARIABLE);

            if (type != Type.INTEGER) {
                throw new TypeCheckerException("Can not increment a variable of type " + type + ".", node.getLine(), node.getColumn());
            }
        }

        return null;
    }

    @Override
    public Type visit(NodeSup node, Object... args) throws TypeCheckerException {
        NodeMiniJaja nodeExp1 = node.getExpLeft();
        NodeMiniJaja nodeExp2 = node.getExpRight();

        String scope = (String) args[0];
        Integer n = (Integer) args[1];
        TypeCheckerMode mode = (TypeCheckerMode) args[2];

        Type expType1 = nodeExp1.accept(this, scope, n, TypeCheckerMode.VALUE);
        Type expType2 = nodeExp2.accept(this, scope, n, TypeCheckerMode.VALUE);

        if (expType1 != Type.INTEGER) {
            throw new TypeCheckerException("A value of type " + expType1 + " is not compatible with the SUP (>) operator.", node.getLine(), node.getColumn());
        }

        if (expType2 != Type.INTEGER) {
            throw new TypeCheckerException("A value of type " + expType2 + " is not compatible with the SUP (>) operator.", node.getLine(), node.getColumn());
        }

        return Type.BOOLEAN;
    }

    @Override
    public Type visit(NodeAssignment node, Object... args) throws TypeCheckerException {
        NodeMiniJaja nodeIdent = node.getIdent();
        NodeMiniJaja nodeExp = node.getExp();

        String scope = (String) args[0];
        Integer n = (Integer) args[1];
        TypeCheckerMode mode = (TypeCheckerMode) args[2];

        if (nodeIdent instanceof NodeArrayGet) {
            Type type = nodeIdent.accept(this, scope, n, TypeCheckerMode.VARIABLE);
            Type expType = nodeExp.accept(this, scope, n, TypeCheckerMode.VALUE);

            if (type != expType) {
                throw new TypeCheckerException("A value of type " + expType + " is not compatible with an array of type " + type + ".", node.getLine(), node.getColumn());
            }
        } else {
            Type type = nodeIdent.accept(this, scope, n, mode);

            SymbolNode symbolNode;

            try {
                symbolNode = symbolTable.get(((NodeIdent) nodeIdent).generateNormalIdent());
            } catch (UnknownSymbolException exception) {
                throw new TypeCheckerException("The symbol \"" + ((NodeIdent) nodeIdent).getValue() + "\" has not been declared.", node.getLine(), node.getColumn());
            }

            if (symbolNode.getKind() == Kind.METHOD) {
                throw new TypeCheckerException("Can not assign something to a method.", node.getLine(), node.getColumn());
            }

            if (symbolNode.getKind() == Kind.ARRAY) {
                // An array is stored in another array
                Type expType = nodeExp.accept(this, scope, n, TypeCheckerMode.ARRAY);

                if (type != expType) {
                    throw new TypeCheckerException("An array of type " + expType + " is not compatible with an array of type " + type + ".", node.getLine(), node.getColumn());
                }

                if (!(nodeExp instanceof NodeIdent)) {
                    throw new TypeCheckerException("Can not assign an expression to an array.", node.getLine(), node.getColumn());
                }
            } else {
                // A new value is assigned to an assignable symbol
                Type expType = nodeExp.accept(this, scope, n, TypeCheckerMode.VALUE);

                if (type != expType) {
                    throw new TypeCheckerException("A value of type " + expType + " is not compatible with a variable of type " + type + ".", node.getLine(), node.getColumn());
                }

                if (symbolNode.getKind() == Kind.CONSTANT) {
                    throw new TypeCheckerException("Can not re-assign a constant.", node.getLine(), node.getColumn());
                }

                if (symbolNode.getKind() == Kind.VCONSTANT) {
                    if (!scope.equals(TypeChecker.SCOPE_MAIN)) {
                        throw new TypeCheckerException("Can not re-assign a constant.", node.getLine(), node.getColumn());
                    }

                    try {
                        symbolTable.remove(((NodeIdent) nodeIdent).generateNormalIdent());
                    } catch (UnknownSymbolException exception) {
                        throw new TypeCheckerException("The symbol \"" + ((NodeIdent) nodeIdent).getValue() + "\" has not been declared.", node.getLine(), node.getColumn());
                    }

                    symbolNode = new SymbolNode(((NodeIdent) nodeIdent).generateNormalIdent(), Kind.CONSTANT, type);

                    try {
                        symbolTable.put(symbolNode);
                    } catch (ExistingSymbolException exception) {
                        throw new TypeCheckerException("The symbol \"" + ((NodeIdent) nodeIdent).getValue() + "\" has already been declared.", node.getLine(), node.getColumn());
                    }
                }
            }
        }

        return null;
    }

    @Override
    public Type visit(NodeOmega node, Object... args) throws TypeCheckerException {
        return null;
    }

    @Override
    public Type visit(NodeMiniJaja node, Object... args) throws TypeCheckerException {
        return null;
    }

    @Override
    public Type visit(NodeArray node, Object... args) throws TypeCheckerException {
        NodeMiniJaja nodeType = node.getType();
        NodeMiniJaja nodeExp = node.getExp();

        String scope = (String) args[0];
        Integer n = (Integer) args[1];
        TypeCheckerMode mode = (TypeCheckerMode) args[2];

        // Update scope
        node.setIdentScope(scope);

        if (n == TypeChecker.FIRST_PASS) {
            Type type = nodeType.accept(this, scope, n, mode);
            Type expType = nodeExp.accept(this, scope, n, TypeCheckerMode.VALUE);

            if (type == Type.VOID) {
                throw new TypeCheckerException("Can not declare an array of type " + type + ".", node.getLine(), node.getColumn());
            }

            if (expType != Type.INTEGER) {
                throw new TypeCheckerException("The size of an array has to be an integer expression.", node.getLine(), node.getColumn());
            }

            SymbolNode symbolNode = new SymbolNode(node.generateIdent(), Kind.ARRAY, type);

            try {
                symbolTable.put(symbolNode);
            } catch (ExistingSymbolException exception) {
                throw new TypeCheckerException("The array \"" + node.getIdentValue() + "\" has already been declared.", node.getLine(), node.getColumn());
            }
        }

        return null;
    }

    @Override
    public Type visit(NodeArrayGet node, Object... args) throws TypeCheckerException {
        NodeMiniJaja nodeExp = node.getExp();

        String scope = (String) args[0];
        Integer n = (Integer) args[1];
        TypeCheckerMode mode = (TypeCheckerMode) args[2];

        // Update scope
        node.setIdentScope(scope);

        Type expType = nodeExp.accept(this, scope, n, TypeCheckerMode.VALUE);

        if (expType != Type.INTEGER) {
            throw new TypeCheckerException("The expression to get an element from an array has to be an integer expression.", node.getLine(), node.getColumn());
        }

        SymbolNode symbolNode;

        try {
            symbolNode = symbolTable.get(node.generateIdent());
        } catch (UnknownSymbolException e) {
            scope = TypeChecker.SCOPE_GLOBAL;

            node.setIdentScope(scope);

            try {
                symbolNode = symbolTable.get(node.generateIdent());
            } catch (UnknownSymbolException exception) {
                throw new TypeCheckerException("The symbol \"" + node.getIdentValue() + "\" has not been declared.", node.getLine(), node.getColumn());
            }
        }

        if (symbolNode.getKind() != Kind.ARRAY) {
            throw new TypeCheckerException("The symbol \"" + node.getIdentValue() + "\" is not an array.", node.getLine(), node.getColumn());
        }

        return symbolNode.getType();
    }

    @Override
    public Type visit(NodeCallE node, Object... args) throws TypeCheckerException {
        NodeMiniJaja nodeExpList = node.getExpList();

        String scope = (String) args[0];
        Integer n = (Integer) args[1];
        TypeCheckerMode mode = (TypeCheckerMode) args[2];

        // Update signature
        node.setIdentSignature(generateCallSignature(nodeExpList, scope, n, TypeCheckerMode.VALUE));

        SymbolNode symbolNode;

        try {
            symbolNode = symbolTable.get(node.generateIdent());
        } catch (UnknownSymbolException exception) {
            throw new TypeCheckerException("The symbol \"" + node.getIdentValue() + "\" has not been declared.", node.getLine(), node.getColumn());
        }

        if (symbolNode.getKind() != Kind.METHOD) {
            throw new TypeCheckerException("The symbol \"" + node.getIdentValue() + "\" is not a method.", node.getLine(), node.getColumn());
        }

        return symbolNode.getType();
    }

    @Override
    public Type visit(NodeCallI node, Object... args) throws TypeCheckerException {
        NodeMiniJaja nodeExpList = node.getExpList();

        String scope = (String) args[0];
        Integer n = (Integer) args[1];
        TypeCheckerMode mode = (TypeCheckerMode) args[2];

        // Update signature
        node.setIdentSignature(generateCallSignature(nodeExpList, scope, n, TypeCheckerMode.VALUE));

        SymbolNode symbolNode;

        try {
            symbolNode = symbolTable.get(node.generateIdent());
        } catch (UnknownSymbolException exception) {
            throw new TypeCheckerException("The symbol \"" + node.getIdentValue() + "\" has not been declared.", node.getLine(), node.getColumn());
        }

        if (symbolNode.getKind() != Kind.METHOD) {
            throw new TypeCheckerException("The symbol \"" + node.getIdentValue() + "\" is not a method.", node.getLine(), node.getColumn());
        }

        return null;
    }

    @Override
    public Type visit(NodeCmp node, Object... args) throws TypeCheckerException {
        NodeMiniJaja nodeExp1 = node.getExpLeft();
        NodeMiniJaja nodeExp2 = node.getExpRight();

        String scope = (String) args[0];
        Integer n = (Integer) args[1];
        TypeCheckerMode mode = (TypeCheckerMode) args[2];

        Type expType1 = nodeExp1.accept(this, scope, n, TypeCheckerMode.VALUE);
        Type expType2 = nodeExp2.accept(this, scope, n, TypeCheckerMode.VALUE);

        if (expType1 != expType2) {
            throw new TypeCheckerException("A value of type " + expType1 + " can not be compared to a value of type " + expType2 + ".", node.getLine(), node.getColumn());
        }

        return Type.BOOLEAN;
    }

    @Override
    public Type visit(NodeCst node, Object... args) throws TypeCheckerException {
        NodeMiniJaja nodeType = node.getType();
        NodeMiniJaja nodeExp = node.getExp();

        String scope = (String) args[0];
        Integer n = (Integer) args[1];
        TypeCheckerMode mode = (TypeCheckerMode) args[2];

        // Update scope
        node.setIdentScope(scope);

        if (n == TypeChecker.FIRST_PASS) {
            Type type = nodeType.accept(this, scope, n, mode);
            Type expType = nodeExp.accept(this, scope, n, TypeCheckerMode.VALUE);

            if (type == Type.VOID) {
                throw new TypeCheckerException("Can not declare a constant of type " + type + ".", node.getLine(), node.getColumn());
            }

            if (expType != null && expType != type) {
                throw new TypeCheckerException("A value of type " + expType + " is not compatible with the symbol \"" + node.getIdentValue() + "\" of type " + type + ".", node.getLine(), node.getColumn());
            }

            SymbolNode symbolNode = new SymbolNode(node.generateIdent(), expType == null ? Kind.VCONSTANT : Kind.CONSTANT, type);

            try {
                symbolTable.put(symbolNode);
            } catch (ExistingSymbolException exception) {
                throw new TypeCheckerException("The symbol \"" + node.getIdentValue() + "\" has already been declared.", node.getLine(), node.getColumn());
            }
        }

        return null;
    }

    @Override
    public Type visit(NodeEnil node, Object... args) throws TypeCheckerException {
        return null;
    }

    @Override
    public Type visit(NodeExnil node, Object... args) throws TypeCheckerException {
        return null;
    }

    @Override
    public Type visit(NodeExpList node, Object... args) throws TypeCheckerException {
        return null;
    }

    @Override
    public Type visit(NodeHeader node, Object... args) throws TypeCheckerException {
        NodeMiniJaja nodeType = node.getType();

        String scope = (String) args[0];
        Integer n = (Integer) args[1];
        TypeCheckerMode mode = (TypeCheckerMode) args[2];

        // Update scope
        node.setIdentScope(scope);

        if (n == TypeChecker.FIRST_PASS) {
            Type type = nodeType.accept(this, scope, n, mode);

            if (type == Type.VOID) {
                throw new TypeCheckerException("Can not declare a parameter of type " + type + ".", node.getLine(), node.getColumn());
            }

            SymbolNode symbolNode = new SymbolNode(node.generateIdent(), Kind.VARIABLE, type);

            try {
                symbolTable.put(symbolNode);
            } catch (ExistingSymbolException exception) {
                throw new TypeCheckerException("The symbol \"" + node.getIdentValue() + "\" has already been declared.", node.getLine(), node.getColumn());
            }
        }

        return null;
    }

    @Override
    public Type visit(NodeHeaders node, Object... args) throws TypeCheckerException {
        NodeMiniJaja nodeHeader = node.getHeader();
        NodeMiniJaja nodeHeaders = node.getHeaders();

        String scope = (String) args[0];
        Integer n = (Integer) args[1];
        TypeCheckerMode mode = (TypeCheckerMode) args[2];

        nodeHeader.accept(this, scope, n, mode);
        nodeHeaders.accept(this, scope, n, mode);

        return null;
    }

    @Override
    public Type visit(NodeIf node, Object... args) throws TypeCheckerException {
        NodeMiniJaja nodeExp = node.getExp();
        NodeMiniJaja nodeInstrs1 = node.getInstrs();
        NodeMiniJaja nodeInstrs2 = node.getInstrsElse();

        String scope = (String) args[0];
        Integer n = (Integer) args[1];
        TypeCheckerMode mode = (TypeCheckerMode) args[2];

        Type expType = nodeExp.accept(this, scope, n, TypeCheckerMode.VALUE);

        if (expType != Type.BOOLEAN) {
            throw new TypeCheckerException("Can not evaluate an expression of type " + expType + " as a conditional expression.", node.getLine(), node.getColumn());
        }

        nodeInstrs1.accept(this, scope, n, mode);
        nodeInstrs2.accept(this, scope, n, mode);

        return null;
    }

    @Override
    public Type visit(NodeWhile node, Object... args) throws TypeCheckerException {
        NodeMiniJaja nodeExp = node.getExp();
        NodeMiniJaja nodeInstrs = node.getInstrs();

        String scope = (String) args[0];
        Integer n = (Integer) args[1];
        TypeCheckerMode mode = (TypeCheckerMode) args[2];

        Type expType = nodeExp.accept(this, scope, n, TypeCheckerMode.VALUE);

        if (expType != Type.BOOLEAN) {
            throw new TypeCheckerException("Can not evaluate an expression of type " + expType + " as a conditional expression.", node.getLine(), node.getColumn());
        }

        nodeInstrs.accept(this, scope, n, mode);

        return null;
    }

    @Override
    public Type visit(NodeInil node, Object... args) throws TypeCheckerException {
        return null;
    }

    @Override
    public Type visit(NodeMethod node, Object... args) throws TypeCheckerException {
        NodeMiniJaja nodeType = node.getType();
        NodeMiniJaja nodeHeaders = node.getHeaders();
        NodeMiniJaja nodeVars = node.getVars();
        NodeMiniJaja nodeInstrs = node.getInstrs();

        // Update signature
        node.setIdentSignature(generateMethodSignature(nodeHeaders));

        String scope = node.generateIdent();
        Integer n = (Integer) args[1];
        TypeCheckerMode mode = (TypeCheckerMode) args[2];

        if (n == TypeChecker.FIRST_PASS) {
            Type type = nodeType.accept(this, scope, n, mode);

            SymbolNode symbolNode = new SymbolNode(node.generateIdent(), Kind.METHOD, type);

            try {
                symbolTable.put(symbolNode);
            } catch (ExistingSymbolException exception) {
                throw new TypeCheckerException("The method \"" + node.getIdentValue() + "\" has already been declared.", node.getLine(), node.getColumn());
            }
        }

        if (!(nodeType instanceof NodeVoid) && !existingReturnStatement(nodeInstrs)) {
            throw new TypeCheckerException("Missing return statement.", node.getLine(), node.getColumn());
        }

        nodeHeaders.accept(this, scope, n, mode);
        nodeVars.accept(this, scope, n, mode);
        nodeInstrs.accept(this, scope, n, mode);

        return null;
    }

    @Override
    public Type visit(NodeNeg node, Object... args) throws TypeCheckerException {
        NodeMiniJaja nodeExp = node.getExp();

        String scope = (String) args[0];
        Integer n = (Integer) args[1];
        TypeCheckerMode mode = (TypeCheckerMode) args[2];

        Type expType = nodeExp.accept(this, scope, n, TypeCheckerMode.VALUE);

        if (expType != Type.INTEGER) {
            throw new TypeCheckerException("A value of type " + expType + " is not compatible with the NEG (-) operator.", node.getLine(), node.getColumn());
        }

        return Type.INTEGER;
    }

    @Override
    public Type visit(NodeReturn node, Object... args) throws TypeCheckerException {
        NodeMiniJaja nodeExp = node.getExp();

        String scope = (String) args[0];
        Integer n = (Integer) args[1];
        TypeCheckerMode mode = (TypeCheckerMode) args[2];

        if (scope.equals(TypeChecker.SCOPE_GLOBAL) || scope.equals(TypeChecker.SCOPE_MAIN)) {
            throw new TypeCheckerException("Can not return something outside of a method.", node.getLine(), node.getColumn());
        }

        Type expType = nodeExp.accept(this, scope, n, TypeCheckerMode.VALUE);

        SymbolNode symbolNode;

        try {
            symbolNode = symbolTable.get(scope);
        } catch (UnknownSymbolException exception) {
            throw new TypeCheckerException("Can not return something outside of a declared method.", node.getLine(), node.getColumn());
        }

        if (symbolNode.getType() == Type.VOID) {
            throw new TypeCheckerException("A method of type " + symbolNode.getType() + " can not return something.", node.getLine(), node.getColumn());
        }

        if (symbolNode.getType() != expType) {
            throw new TypeCheckerException("A method of type " + symbolNode.getType() + " can not return a value of type " + expType + ".", node.getLine(), node.getColumn());
        }

        return null;
    }

    @Override
    public Type visit(NodeVnil node, Object... args) throws TypeCheckerException {
        return null;
    }

    private static boolean existingReturnStatement(NodeMiniJaja nodeInstrs) {
        boolean existing = false;

        while (nodeInstrs instanceof NodeInstrs) {
            NodeMiniJaja nodeInstr = ((NodeInstrs) nodeInstrs).getInstr();

            if (nodeInstr instanceof NodeIf) {
                existing = existingReturnStatement(((NodeIf) nodeInstr).getInstrs()) && existingReturnStatement(((NodeIf) nodeInstr).getInstrsElse());
            }

            if (nodeInstr instanceof NodeReturn) {
                return true;
            }

            nodeInstrs = ((NodeInstrs) nodeInstrs).getInstrs();
        }

        return existing;
    }

    private String generateCallSignature(NodeMiniJaja nodeExpList, Object... args) throws TypeCheckerException {
        StringBuilder buffer = new StringBuilder();

        while (nodeExpList instanceof NodeExpList) {
            NodeMiniJaja nodeExp = ((NodeExpList) nodeExpList).getExp();

            Type type = nodeExp.accept(this, args);

            if (type == Type.INTEGER) {
                buffer.append("int");
            } else if (type == Type.BOOLEAN) {
                buffer.append("boolean");
            }

            nodeExpList = ((NodeExpList) nodeExpList).getExpList();

            if (nodeExpList instanceof NodeExpList) {
                buffer.append(NodeIdent.HEADER_DIVIDER);
            }
        }

        return buffer.toString();
    }

    private static String generateMethodSignature(NodeMiniJaja nodeHeaders) {
        StringBuilder buffer = new StringBuilder();

        while (nodeHeaders instanceof NodeHeaders) {
            NodeMiniJaja nodeHeader = ((NodeHeaders) nodeHeaders).getHeader();
            NodeMiniJaja nodeType = ((NodeHeader) nodeHeader).getType();

            if (nodeType instanceof NodeInteger) {
                buffer.append("int");
            } else if (nodeType instanceof NodeBoolean) {
                buffer.append("boolean");
            }

            nodeHeaders = ((NodeHeaders) nodeHeaders).getHeaders();

            if (nodeHeaders instanceof NodeHeaders) {
                buffer.append(NodeIdent.HEADER_DIVIDER);
            }
        }

        return buffer.toString();
    }
}
