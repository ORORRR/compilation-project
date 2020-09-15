package fr.femtost.disc.glcomp.m1comp6.analyser.mjj;

import fr.femtost.disc.glcomp.m1comp6.ast.common.LocatableRuntimeException;

public class AnalyserException extends LocatableRuntimeException {
    public AnalyserException(String message) {
        super(message);
    }

    public AnalyserException(String message, int line, int column) {
        super(message, line, column);
    }
}
