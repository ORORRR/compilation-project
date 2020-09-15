// Generated from fr/femtost/disc/glcomp/m1comp6/analyser/mjj/MiniJaja.g4 by ANTLR 4.7.1
package fr.femtost.disc.glcomp.m1comp6.analyser.mjj;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MiniJajaParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MiniJajaVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MiniJajaParser#classe}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClasse(MiniJajaParser.ClasseContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJajaParser#ident}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdent(MiniJajaParser.IdentContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJajaParser#decls}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecls(MiniJajaParser.DeclsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJajaParser#decl}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecl(MiniJajaParser.DeclContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJajaParser#vars}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVars(MiniJajaParser.VarsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJajaParser#var}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVar(MiniJajaParser.VarContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJajaParser#vexp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVexp(MiniJajaParser.VexpContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJajaParser#methode}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethode(MiniJajaParser.MethodeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJajaParser#methmain}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethmain(MiniJajaParser.MethmainContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJajaParser#entetes}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEntetes(MiniJajaParser.EntetesContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJajaParser#entete}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEntete(MiniJajaParser.EnteteContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJajaParser#instrs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstrs(MiniJajaParser.InstrsContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJajaParser#instr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstr(MiniJajaParser.InstrContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJajaParser#listexp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListexp(MiniJajaParser.ListexpContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJajaParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp(MiniJajaParser.ExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJajaParser#exp1}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp1(MiniJajaParser.Exp1Context ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJajaParser#exp2}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp2(MiniJajaParser.Exp2Context ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJajaParser#terme}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerme(MiniJajaParser.TermeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJajaParser#fact}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFact(MiniJajaParser.FactContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJajaParser#ident1}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdent1(MiniJajaParser.Ident1Context ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJajaParser#typemeth}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypemeth(MiniJajaParser.TypemethContext ctx);
	/**
	 * Visit a parse tree produced by {@link MiniJajaParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(MiniJajaParser.TypeContext ctx);
}