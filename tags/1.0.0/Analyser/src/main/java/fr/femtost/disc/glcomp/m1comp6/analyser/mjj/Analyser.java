package fr.femtost.disc.glcomp.m1comp6.analyser.mjj;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.*;

import fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja;

public class Analyser {
    private final AnalyserVisitor analyserVisitor;

    public Analyser() {
        analyserVisitor = new AnalyserVisitor();
    }

    /**
     * Build an AST from a string.
     * @param   str     The string to parse.
     * @return  The root node of the generated MiniJaja AST.
     */
    public NodeMiniJaja parseString(String str) throws AnalyserException {
        CharStream stream = CharStreams.fromString(str);

        try {
            return parse(stream);
        } catch (ParseCancellationException exception) {
            throw new AnalyserException(exception.toString());
        }
    }

    private NodeMiniJaja parse(CharStream stream) throws ParseCancellationException {
        // Lexical analysis
        MiniJajaLexer lexer = new MiniJajaLexer(stream);

        lexer.removeErrorListeners();
        lexer.addErrorListener(AnalyserErrorListener.INSTANCE);

        CommonTokenStream tokens = new CommonTokenStream(lexer);

        // Syntactic analysis
        MiniJajaParser parser = new MiniJajaParser(tokens);

        parser.removeErrorListeners();
        parser.addErrorListener(AnalyserErrorListener.INSTANCE);

        ParseTree tree = parser.classe();

        return analyserVisitor.visit(tree);
    }
}
