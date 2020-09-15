package fr.femtost.disc.glcomp.m1comp6.gui;

import fr.femtost.disc.glcomp.m1comp6.ast.jjc.NodeJajaCode;
import fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja;
import fr.femtost.disc.glcomp.m1comp6.debuggers.Debugger;
import fr.femtost.disc.glcomp.m1comp6.memory.SymbolTable;

import java.util.ArrayList;
import java.util.List;

public class VMModel {
    private static VMModel instance;

    private NodeMiniJaja mjjAST;
    private NodeJajaCode jjcAST;

    private SymbolTable symbolTable;

    private List<Debugger> debuggers;

    private VMModel() {
        debuggers = new ArrayList<>();
    }

    public static VMModel getInstance() {
        if (instance == null) {
            instance = new VMModel();
        }

        return instance;
    }

    public NodeMiniJaja getMjjAST() {
        return mjjAST;
    }

    public void setMjjAST(NodeMiniJaja mjjAST) {
        this.mjjAST = mjjAST;
    }

    public NodeJajaCode getJjcAST() {
        return jjcAST;
    }

    public void setJjcAST(NodeJajaCode jjcAST) {
        this.jjcAST = jjcAST;
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable symbolTable) {
        this.symbolTable = symbolTable;
    }

    public List<Debugger> getDebuggers() {
        return debuggers;
    }

    public void addDebugger(Debugger debugger) {
        debuggers.add(debugger);
    }

    public void removeDebugger(Debugger debugger) {
        debuggers.remove(debugger);
    }

    public void reset() {
        mjjAST = null;
        jjcAST = null;
        symbolTable = null;

        debuggers.clear();
    }
}
