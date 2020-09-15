package fr.femtost.disc.glcomp.m1comp6.analyser.mjj;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.misc.ParseCancellationException;

public class AnalyserErrorListener extends BaseErrorListener {
    public static final AnalyserErrorListener INSTANCE = new AnalyserErrorListener();

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) throws ParseCancellationException {
        throw new ParseCancellationException("Analyser error: " + msg + " at line " + line + ":" + charPositionInLine + ".");
    }
}
