package fr.femtost.disc.glcomp.m1comp6.analyser.mjj;

import fr.femtost.disc.glcomp.m1comp6.ast.mjj.NodeMiniJaja;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class Analyser {
    private final AnalyserVisitor analyserVisitor;

    public Analyser() {
        analyserVisitor = new AnalyserVisitor();
    }

    /**
     * Build an AST from a string.
     *
     * @param str The string to parse.
     * @return The root node of the generated MiniJaja AST.
     */
    public NodeMiniJaja parseString(String str) {
        CharStream stream = CharStreams.fromString(str);

        return parse(stream);
    }

    private NodeMiniJaja parse(CharStream stream) {
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
