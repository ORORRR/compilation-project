// Generated from fr/femtost/disc/glcomp/m1comp6/analyser/mjj/MiniJaja.g4 by ANTLR 4.7.1
package fr.femtost.disc.glcomp.m1comp6.analyser.mjj;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class MiniJajaParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, ID=33, NUMBER=34, INLINE_COMMENTS=35, BLOCK_COMMENTS=36, WS=37;
	public static final int
		RULE_classe = 0, RULE_ident = 1, RULE_decls = 2, RULE_decl = 3, RULE_vars = 4, 
		RULE_var = 5, RULE_vexp = 6, RULE_methode = 7, RULE_methmain = 8, RULE_entetes = 9, 
		RULE_entete = 10, RULE_instrs = 11, RULE_instr = 12, RULE_listexp = 13, 
		RULE_exp = 14, RULE_exp1 = 15, RULE_exp2 = 16, RULE_terme = 17, RULE_fact = 18, 
		RULE_ident1 = 19, RULE_typemeth = 20, RULE_type = 21;
	public static final String[] ruleNames = {
		"classe", "ident", "decls", "decl", "vars", "var", "vexp", "methode", 
		"methmain", "entetes", "entete", "instrs", "instr", "listexp", "exp", 
		"exp1", "exp2", "terme", "fact", "ident1", "typemeth", "type"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'class'", "'{'", "'}'", "';'", "'['", "']'", "'final'", "'='", 
		"'('", "')'", "'main'", "','", "'+='", "'++'", "'return'", "'if'", "'else'", 
		"'while'", "'!'", "'&&'", "'||'", "'=='", "'>'", "'+'", "'-'", "'*'", 
		"'/'", "'true'", "'false'", "'void'", "'int'", "'boolean'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, "ID", "NUMBER", 
		"INLINE_COMMENTS", "BLOCK_COMMENTS", "WS"
	};
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "MiniJaja.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public MiniJajaParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ClasseContext extends ParserRuleContext {
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public DeclsContext decls() {
			return getRuleContext(DeclsContext.class,0);
		}
		public MethmainContext methmain() {
			return getRuleContext(MethmainContext.class,0);
		}
		public TerminalNode EOF() { return getToken(MiniJajaParser.EOF, 0); }
		public ClasseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classe; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJajaVisitor ) return ((MiniJajaVisitor<? extends T>)visitor).visitClasse(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClasseContext classe() throws RecognitionException {
		ClasseContext _localctx = new ClasseContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_classe);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(44);
			match(T__0);
			setState(45);
			ident();
			setState(46);
			match(T__1);
			setState(47);
			decls();
			setState(48);
			methmain();
			setState(49);
			match(T__2);
			setState(50);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdentContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(MiniJajaParser.ID, 0); }
		public IdentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ident; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJajaVisitor ) return ((MiniJajaVisitor<? extends T>)visitor).visitIdent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdentContext ident() throws RecognitionException {
		IdentContext _localctx = new IdentContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_ident);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(52);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclsContext extends ParserRuleContext {
		public DeclContext decl() {
			return getRuleContext(DeclContext.class,0);
		}
		public DeclsContext decls() {
			return getRuleContext(DeclsContext.class,0);
		}
		public DeclsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decls; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJajaVisitor ) return ((MiniJajaVisitor<? extends T>)visitor).visitDecls(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclsContext decls() throws RecognitionException {
		DeclsContext _localctx = new DeclsContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_decls);
		try {
			setState(59);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__6:
			case T__29:
			case T__30:
			case T__31:
				enterOuterAlt(_localctx, 1);
				{
				setState(54);
				decl();
				setState(55);
				match(T__3);
				setState(56);
				decls();
				}
				break;
			case T__10:
				enterOuterAlt(_localctx, 2);
				{
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclContext extends ParserRuleContext {
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public MethodeContext methode() {
			return getRuleContext(MethodeContext.class,0);
		}
		public DeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decl; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJajaVisitor ) return ((MiniJajaVisitor<? extends T>)visitor).visitDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclContext decl() throws RecognitionException {
		DeclContext _localctx = new DeclContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_decl);
		try {
			setState(63);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(61);
				var();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(62);
				methode();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarsContext extends ParserRuleContext {
		public VarContext var() {
			return getRuleContext(VarContext.class,0);
		}
		public VarsContext vars() {
			return getRuleContext(VarsContext.class,0);
		}
		public VarsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vars; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJajaVisitor ) return ((MiniJajaVisitor<? extends T>)visitor).visitVars(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarsContext vars() throws RecognitionException {
		VarsContext _localctx = new VarsContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_vars);
		try {
			setState(70);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__6:
			case T__29:
			case T__30:
			case T__31:
				enterOuterAlt(_localctx, 1);
				{
				setState(65);
				var();
				setState(66);
				match(T__3);
				setState(67);
				vars();
				}
				break;
			case T__2:
			case T__14:
			case T__15:
			case T__17:
			case ID:
				enterOuterAlt(_localctx, 2);
				{
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarContext extends ParserRuleContext {
		public TypemethContext typemeth() {
			return getRuleContext(TypemethContext.class,0);
		}
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public VexpContext vexp() {
			return getRuleContext(VexpContext.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public VarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJajaVisitor ) return ((MiniJajaVisitor<? extends T>)visitor).visitVar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarContext var() throws RecognitionException {
		VarContext _localctx = new VarContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_var);
		try {
			setState(87);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(72);
				typemeth();
				setState(73);
				ident();
				setState(74);
				vexp();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(76);
				typemeth();
				setState(77);
				ident();
				setState(78);
				match(T__4);
				setState(79);
				exp(0);
				setState(80);
				match(T__5);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(82);
				match(T__6);
				setState(83);
				type();
				setState(84);
				ident();
				setState(85);
				vexp();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VexpContext extends ParserRuleContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public VexpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vexp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJajaVisitor ) return ((MiniJajaVisitor<? extends T>)visitor).visitVexp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VexpContext vexp() throws RecognitionException {
		VexpContext _localctx = new VexpContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_vexp);
		try {
			setState(92);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__7:
				enterOuterAlt(_localctx, 1);
				{
				setState(89);
				match(T__7);
				setState(90);
				exp(0);
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 2);
				{
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethodeContext extends ParserRuleContext {
		public TypemethContext typemeth() {
			return getRuleContext(TypemethContext.class,0);
		}
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public EntetesContext entetes() {
			return getRuleContext(EntetesContext.class,0);
		}
		public VarsContext vars() {
			return getRuleContext(VarsContext.class,0);
		}
		public InstrsContext instrs() {
			return getRuleContext(InstrsContext.class,0);
		}
		public MethodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methode; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJajaVisitor ) return ((MiniJajaVisitor<? extends T>)visitor).visitMethode(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethodeContext methode() throws RecognitionException {
		MethodeContext _localctx = new MethodeContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_methode);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(94);
			typemeth();
			setState(95);
			ident();
			setState(96);
			match(T__8);
			setState(97);
			entetes();
			setState(98);
			match(T__9);
			setState(99);
			match(T__1);
			setState(100);
			vars();
			setState(101);
			instrs();
			setState(102);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MethmainContext extends ParserRuleContext {
		public VarsContext vars() {
			return getRuleContext(VarsContext.class,0);
		}
		public InstrsContext instrs() {
			return getRuleContext(InstrsContext.class,0);
		}
		public MethmainContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_methmain; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJajaVisitor ) return ((MiniJajaVisitor<? extends T>)visitor).visitMethmain(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MethmainContext methmain() throws RecognitionException {
		MethmainContext _localctx = new MethmainContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_methmain);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(104);
			match(T__10);
			setState(105);
			match(T__1);
			setState(106);
			vars();
			setState(107);
			instrs();
			setState(108);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EntetesContext extends ParserRuleContext {
		public EnteteContext entete() {
			return getRuleContext(EnteteContext.class,0);
		}
		public EntetesContext entetes() {
			return getRuleContext(EntetesContext.class,0);
		}
		public EntetesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_entetes; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJajaVisitor ) return ((MiniJajaVisitor<? extends T>)visitor).visitEntetes(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EntetesContext entetes() throws RecognitionException {
		EntetesContext _localctx = new EntetesContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_entetes);
		try {
			setState(116);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(110);
				entete();
				setState(111);
				match(T__11);
				setState(112);
				entetes();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(114);
				entete();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EnteteContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public EnteteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_entete; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJajaVisitor ) return ((MiniJajaVisitor<? extends T>)visitor).visitEntete(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EnteteContext entete() throws RecognitionException {
		EnteteContext _localctx = new EnteteContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_entete);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(118);
			type();
			setState(119);
			ident();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InstrsContext extends ParserRuleContext {
		public InstrContext instr() {
			return getRuleContext(InstrContext.class,0);
		}
		public InstrsContext instrs() {
			return getRuleContext(InstrsContext.class,0);
		}
		public InstrsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_instrs; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJajaVisitor ) return ((MiniJajaVisitor<? extends T>)visitor).visitInstrs(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InstrsContext instrs() throws RecognitionException {
		InstrsContext _localctx = new InstrsContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_instrs);
		try {
			setState(126);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__14:
			case T__15:
			case T__17:
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(121);
				instr();
				setState(122);
				match(T__3);
				setState(123);
				instrs();
				}
				break;
			case T__2:
				enterOuterAlt(_localctx, 2);
				{
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InstrContext extends ParserRuleContext {
		public Ident1Context ident1() {
			return getRuleContext(Ident1Context.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public ListexpContext listexp() {
			return getRuleContext(ListexpContext.class,0);
		}
		public List<InstrsContext> instrs() {
			return getRuleContexts(InstrsContext.class);
		}
		public InstrsContext instrs(int i) {
			return getRuleContext(InstrsContext.class,i);
		}
		public InstrContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_instr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJajaVisitor ) return ((MiniJajaVisitor<? extends T>)visitor).visitInstr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InstrContext instr() throws RecognitionException {
		InstrContext _localctx = new InstrContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_instr);
		int _la;
		try {
			setState(166);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(128);
				ident1();
				setState(129);
				match(T__7);
				setState(130);
				exp(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(132);
				ident1();
				setState(133);
				match(T__12);
				setState(134);
				exp(0);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(136);
				ident1();
				setState(137);
				match(T__13);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(139);
				ident();
				setState(140);
				match(T__8);
				setState(141);
				listexp();
				setState(142);
				match(T__9);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(144);
				match(T__14);
				setState(145);
				exp(0);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(146);
				match(T__15);
				setState(147);
				exp(0);
				setState(148);
				match(T__1);
				setState(149);
				instrs();
				setState(150);
				match(T__2);
				setState(156);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__16) {
					{
					setState(151);
					match(T__16);
					setState(152);
					match(T__1);
					setState(153);
					instrs();
					setState(154);
					match(T__2);
					}
				}

				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(158);
				match(T__17);
				setState(159);
				match(T__8);
				setState(160);
				exp(0);
				setState(161);
				match(T__9);
				setState(162);
				match(T__1);
				setState(163);
				instrs();
				setState(164);
				match(T__2);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ListexpContext extends ParserRuleContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public ListexpContext listexp() {
			return getRuleContext(ListexpContext.class,0);
		}
		public ListexpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_listexp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJajaVisitor ) return ((MiniJajaVisitor<? extends T>)visitor).visitListexp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ListexpContext listexp() throws RecognitionException {
		ListexpContext _localctx = new ListexpContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_listexp);
		try {
			setState(174);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(168);
				exp(0);
				setState(169);
				match(T__11);
				setState(170);
				listexp();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(172);
				exp(0);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpContext extends ParserRuleContext {
		public Exp1Context exp1() {
			return getRuleContext(Exp1Context.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public ExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJajaVisitor ) return ((MiniJajaVisitor<? extends T>)visitor).visitExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpContext exp() throws RecognitionException {
		return exp(0);
	}

	private ExpContext exp(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpContext _localctx = new ExpContext(_ctx, _parentState);
		ExpContext _prevctx = _localctx;
		int _startState = 28;
		enterRecursionRule(_localctx, 28, RULE_exp, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(180);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__18:
				{
				setState(177);
				match(T__18);
				setState(178);
				exp1(0);
				}
				break;
			case T__8:
			case T__24:
			case T__27:
			case T__28:
			case ID:
			case NUMBER:
				{
				setState(179);
				exp1(0);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(190);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(188);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
					case 1:
						{
						_localctx = new ExpContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(182);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(183);
						match(T__19);
						setState(184);
						exp1(0);
						}
						break;
					case 2:
						{
						_localctx = new ExpContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_exp);
						setState(185);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(186);
						match(T__20);
						setState(187);
						exp1(0);
						}
						break;
					}
					} 
				}
				setState(192);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Exp1Context extends ParserRuleContext {
		public Exp2Context exp2() {
			return getRuleContext(Exp2Context.class,0);
		}
		public Exp1Context exp1() {
			return getRuleContext(Exp1Context.class,0);
		}
		public Exp1Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exp1; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJajaVisitor ) return ((MiniJajaVisitor<? extends T>)visitor).visitExp1(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Exp1Context exp1() throws RecognitionException {
		return exp1(0);
	}

	private Exp1Context exp1(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Exp1Context _localctx = new Exp1Context(_ctx, _parentState);
		Exp1Context _prevctx = _localctx;
		int _startState = 30;
		enterRecursionRule(_localctx, 30, RULE_exp1, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(194);
			exp2(0);
			}
			_ctx.stop = _input.LT(-1);
			setState(204);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(202);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
					case 1:
						{
						_localctx = new Exp1Context(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_exp1);
						setState(196);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(197);
						match(T__21);
						setState(198);
						exp2(0);
						}
						break;
					case 2:
						{
						_localctx = new Exp1Context(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_exp1);
						setState(199);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(200);
						match(T__22);
						setState(201);
						exp2(0);
						}
						break;
					}
					} 
				}
				setState(206);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class Exp2Context extends ParserRuleContext {
		public TermeContext terme() {
			return getRuleContext(TermeContext.class,0);
		}
		public Exp2Context exp2() {
			return getRuleContext(Exp2Context.class,0);
		}
		public Exp2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exp2; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJajaVisitor ) return ((MiniJajaVisitor<? extends T>)visitor).visitExp2(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Exp2Context exp2() throws RecognitionException {
		return exp2(0);
	}

	private Exp2Context exp2(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Exp2Context _localctx = new Exp2Context(_ctx, _parentState);
		Exp2Context _prevctx = _localctx;
		int _startState = 32;
		enterRecursionRule(_localctx, 32, RULE_exp2, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(211);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__24:
				{
				setState(208);
				match(T__24);
				setState(209);
				terme(0);
				}
				break;
			case T__8:
			case T__27:
			case T__28:
			case ID:
			case NUMBER:
				{
				setState(210);
				terme(0);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(221);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(219);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
					case 1:
						{
						_localctx = new Exp2Context(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_exp2);
						setState(213);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(214);
						match(T__23);
						setState(215);
						terme(0);
						}
						break;
					case 2:
						{
						_localctx = new Exp2Context(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_exp2);
						setState(216);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(217);
						match(T__24);
						setState(218);
						terme(0);
						}
						break;
					}
					} 
				}
				setState(223);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class TermeContext extends ParserRuleContext {
		public FactContext fact() {
			return getRuleContext(FactContext.class,0);
		}
		public TermeContext terme() {
			return getRuleContext(TermeContext.class,0);
		}
		public TermeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_terme; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJajaVisitor ) return ((MiniJajaVisitor<? extends T>)visitor).visitTerme(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TermeContext terme() throws RecognitionException {
		return terme(0);
	}

	private TermeContext terme(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		TermeContext _localctx = new TermeContext(_ctx, _parentState);
		TermeContext _prevctx = _localctx;
		int _startState = 34;
		enterRecursionRule(_localctx, 34, RULE_terme, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(225);
			fact();
			}
			_ctx.stop = _input.LT(-1);
			setState(235);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(233);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
					case 1:
						{
						_localctx = new TermeContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_terme);
						setState(227);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(228);
						match(T__25);
						setState(229);
						fact();
						}
						break;
					case 2:
						{
						_localctx = new TermeContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_terme);
						setState(230);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(231);
						match(T__26);
						setState(232);
						fact();
						}
						break;
					}
					} 
				}
				setState(237);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class FactContext extends ParserRuleContext {
		public Ident1Context ident1() {
			return getRuleContext(Ident1Context.class,0);
		}
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public ListexpContext listexp() {
			return getRuleContext(ListexpContext.class,0);
		}
		public TerminalNode NUMBER() { return getToken(MiniJajaParser.NUMBER, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public FactContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fact; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJajaVisitor ) return ((MiniJajaVisitor<? extends T>)visitor).visitFact(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FactContext fact() throws RecognitionException {
		FactContext _localctx = new FactContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_fact);
		try {
			setState(251);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(238);
				ident1();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(239);
				ident();
				setState(240);
				match(T__8);
				setState(241);
				listexp();
				setState(242);
				match(T__9);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(244);
				match(T__27);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(245);
				match(T__28);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(246);
				match(NUMBER);
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(247);
				match(T__8);
				setState(248);
				exp(0);
				setState(249);
				match(T__9);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Ident1Context extends ParserRuleContext {
		public IdentContext ident() {
			return getRuleContext(IdentContext.class,0);
		}
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public Ident1Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ident1; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJajaVisitor ) return ((MiniJajaVisitor<? extends T>)visitor).visitIdent1(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Ident1Context ident1() throws RecognitionException {
		Ident1Context _localctx = new Ident1Context(_ctx, getState());
		enterRule(_localctx, 38, RULE_ident1);
		try {
			setState(259);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(253);
				ident();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(254);
				ident();
				setState(255);
				match(T__4);
				setState(256);
				exp(0);
				setState(257);
				match(T__5);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypemethContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TypemethContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typemeth; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJajaVisitor ) return ((MiniJajaVisitor<? extends T>)visitor).visitTypemeth(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypemethContext typemeth() throws RecognitionException {
		TypemethContext _localctx = new TypemethContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_typemeth);
		try {
			setState(263);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__29:
				enterOuterAlt(_localctx, 1);
				{
				setState(261);
				match(T__29);
				}
				break;
			case T__30:
			case T__31:
				enterOuterAlt(_localctx, 2);
				{
				setState(262);
				type();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MiniJajaVisitor ) return ((MiniJajaVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(265);
			_la = _input.LA(1);
			if ( !(_la==T__30 || _la==T__31) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 14:
			return exp_sempred((ExpContext)_localctx, predIndex);
		case 15:
			return exp1_sempred((Exp1Context)_localctx, predIndex);
		case 16:
			return exp2_sempred((Exp2Context)_localctx, predIndex);
		case 17:
			return terme_sempred((TermeContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean exp_sempred(ExpContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 3);
		case 1:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean exp1_sempred(Exp1Context _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return precpred(_ctx, 3);
		case 3:
			return precpred(_ctx, 2);
		}
		return true;
	}
	private boolean exp2_sempred(Exp2Context _localctx, int predIndex) {
		switch (predIndex) {
		case 4:
			return precpred(_ctx, 4);
		case 5:
			return precpred(_ctx, 3);
		}
		return true;
	}
	private boolean terme_sempred(TermeContext _localctx, int predIndex) {
		switch (predIndex) {
		case 6:
			return precpred(_ctx, 3);
		case 7:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\'\u010e\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\3\2\3\2\3\2\3\2\3\2"+
		"\3\2\3\2\3\2\3\3\3\3\3\4\3\4\3\4\3\4\3\4\5\4>\n\4\3\5\3\5\5\5B\n\5\3\6"+
		"\3\6\3\6\3\6\3\6\5\6I\n\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\5\7Z\n\7\3\b\3\b\3\b\5\b_\n\b\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13"+
		"\5\13w\n\13\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\5\r\u0081\n\r\3\16\3\16\3"+
		"\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3"+
		"\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\5\16\u009f"+
		"\n\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\5\16\u00a9\n\16\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\5\17\u00b1\n\17\3\20\3\20\3\20\3\20\5\20\u00b7\n"+
		"\20\3\20\3\20\3\20\3\20\3\20\3\20\7\20\u00bf\n\20\f\20\16\20\u00c2\13"+
		"\20\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\7\21\u00cd\n\21\f\21"+
		"\16\21\u00d0\13\21\3\22\3\22\3\22\3\22\5\22\u00d6\n\22\3\22\3\22\3\22"+
		"\3\22\3\22\3\22\7\22\u00de\n\22\f\22\16\22\u00e1\13\22\3\23\3\23\3\23"+
		"\3\23\3\23\3\23\3\23\3\23\3\23\7\23\u00ec\n\23\f\23\16\23\u00ef\13\23"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\5\24"+
		"\u00fe\n\24\3\25\3\25\3\25\3\25\3\25\3\25\5\25\u0106\n\25\3\26\3\26\5"+
		"\26\u010a\n\26\3\27\3\27\3\27\2\6\36 \"$\30\2\4\6\b\n\f\16\20\22\24\26"+
		"\30\32\34\36 \"$&(*,\2\3\3\2!\"\2\u011a\2.\3\2\2\2\4\66\3\2\2\2\6=\3\2"+
		"\2\2\bA\3\2\2\2\nH\3\2\2\2\fY\3\2\2\2\16^\3\2\2\2\20`\3\2\2\2\22j\3\2"+
		"\2\2\24v\3\2\2\2\26x\3\2\2\2\30\u0080\3\2\2\2\32\u00a8\3\2\2\2\34\u00b0"+
		"\3\2\2\2\36\u00b6\3\2\2\2 \u00c3\3\2\2\2\"\u00d5\3\2\2\2$\u00e2\3\2\2"+
		"\2&\u00fd\3\2\2\2(\u0105\3\2\2\2*\u0109\3\2\2\2,\u010b\3\2\2\2./\7\3\2"+
		"\2/\60\5\4\3\2\60\61\7\4\2\2\61\62\5\6\4\2\62\63\5\22\n\2\63\64\7\5\2"+
		"\2\64\65\7\2\2\3\65\3\3\2\2\2\66\67\7#\2\2\67\5\3\2\2\289\5\b\5\29:\7"+
		"\6\2\2:;\5\6\4\2;>\3\2\2\2<>\3\2\2\2=8\3\2\2\2=<\3\2\2\2>\7\3\2\2\2?B"+
		"\5\f\7\2@B\5\20\t\2A?\3\2\2\2A@\3\2\2\2B\t\3\2\2\2CD\5\f\7\2DE\7\6\2\2"+
		"EF\5\n\6\2FI\3\2\2\2GI\3\2\2\2HC\3\2\2\2HG\3\2\2\2I\13\3\2\2\2JK\5*\26"+
		"\2KL\5\4\3\2LM\5\16\b\2MZ\3\2\2\2NO\5*\26\2OP\5\4\3\2PQ\7\7\2\2QR\5\36"+
		"\20\2RS\7\b\2\2SZ\3\2\2\2TU\7\t\2\2UV\5,\27\2VW\5\4\3\2WX\5\16\b\2XZ\3"+
		"\2\2\2YJ\3\2\2\2YN\3\2\2\2YT\3\2\2\2Z\r\3\2\2\2[\\\7\n\2\2\\_\5\36\20"+
		"\2]_\3\2\2\2^[\3\2\2\2^]\3\2\2\2_\17\3\2\2\2`a\5*\26\2ab\5\4\3\2bc\7\13"+
		"\2\2cd\5\24\13\2de\7\f\2\2ef\7\4\2\2fg\5\n\6\2gh\5\30\r\2hi\7\5\2\2i\21"+
		"\3\2\2\2jk\7\r\2\2kl\7\4\2\2lm\5\n\6\2mn\5\30\r\2no\7\5\2\2o\23\3\2\2"+
		"\2pq\5\26\f\2qr\7\16\2\2rs\5\24\13\2sw\3\2\2\2tw\5\26\f\2uw\3\2\2\2vp"+
		"\3\2\2\2vt\3\2\2\2vu\3\2\2\2w\25\3\2\2\2xy\5,\27\2yz\5\4\3\2z\27\3\2\2"+
		"\2{|\5\32\16\2|}\7\6\2\2}~\5\30\r\2~\u0081\3\2\2\2\177\u0081\3\2\2\2\u0080"+
		"{\3\2\2\2\u0080\177\3\2\2\2\u0081\31\3\2\2\2\u0082\u0083\5(\25\2\u0083"+
		"\u0084\7\n\2\2\u0084\u0085\5\36\20\2\u0085\u00a9\3\2\2\2\u0086\u0087\5"+
		"(\25\2\u0087\u0088\7\17\2\2\u0088\u0089\5\36\20\2\u0089\u00a9\3\2\2\2"+
		"\u008a\u008b\5(\25\2\u008b\u008c\7\20\2\2\u008c\u00a9\3\2\2\2\u008d\u008e"+
		"\5\4\3\2\u008e\u008f\7\13\2\2\u008f\u0090\5\34\17\2\u0090\u0091\7\f\2"+
		"\2\u0091\u00a9\3\2\2\2\u0092\u0093\7\21\2\2\u0093\u00a9\5\36\20\2\u0094"+
		"\u0095\7\22\2\2\u0095\u0096\5\36\20\2\u0096\u0097\7\4\2\2\u0097\u0098"+
		"\5\30\r\2\u0098\u009e\7\5\2\2\u0099\u009a\7\23\2\2\u009a\u009b\7\4\2\2"+
		"\u009b\u009c\5\30\r\2\u009c\u009d\7\5\2\2\u009d\u009f\3\2\2\2\u009e\u0099"+
		"\3\2\2\2\u009e\u009f\3\2\2\2\u009f\u00a9\3\2\2\2\u00a0\u00a1\7\24\2\2"+
		"\u00a1\u00a2\7\13\2\2\u00a2\u00a3\5\36\20\2\u00a3\u00a4\7\f\2\2\u00a4"+
		"\u00a5\7\4\2\2\u00a5\u00a6\5\30\r\2\u00a6\u00a7\7\5\2\2\u00a7\u00a9\3"+
		"\2\2\2\u00a8\u0082\3\2\2\2\u00a8\u0086\3\2\2\2\u00a8\u008a\3\2\2\2\u00a8"+
		"\u008d\3\2\2\2\u00a8\u0092\3\2\2\2\u00a8\u0094\3\2\2\2\u00a8\u00a0\3\2"+
		"\2\2\u00a9\33\3\2\2\2\u00aa\u00ab\5\36\20\2\u00ab\u00ac\7\16\2\2\u00ac"+
		"\u00ad\5\34\17\2\u00ad\u00b1\3\2\2\2\u00ae\u00b1\5\36\20\2\u00af\u00b1"+
		"\3\2\2\2\u00b0\u00aa\3\2\2\2\u00b0\u00ae\3\2\2\2\u00b0\u00af\3\2\2\2\u00b1"+
		"\35\3\2\2\2\u00b2\u00b3\b\20\1\2\u00b3\u00b4\7\25\2\2\u00b4\u00b7\5 \21"+
		"\2\u00b5\u00b7\5 \21\2\u00b6\u00b2\3\2\2\2\u00b6\u00b5\3\2\2\2\u00b7\u00c0"+
		"\3\2\2\2\u00b8\u00b9\f\5\2\2\u00b9\u00ba\7\26\2\2\u00ba\u00bf\5 \21\2"+
		"\u00bb\u00bc\f\4\2\2\u00bc\u00bd\7\27\2\2\u00bd\u00bf\5 \21\2\u00be\u00b8"+
		"\3\2\2\2\u00be\u00bb\3\2\2\2\u00bf\u00c2\3\2\2\2\u00c0\u00be\3\2\2\2\u00c0"+
		"\u00c1\3\2\2\2\u00c1\37\3\2\2\2\u00c2\u00c0\3\2\2\2\u00c3\u00c4\b\21\1"+
		"\2\u00c4\u00c5\5\"\22\2\u00c5\u00ce\3\2\2\2\u00c6\u00c7\f\5\2\2\u00c7"+
		"\u00c8\7\30\2\2\u00c8\u00cd\5\"\22\2\u00c9\u00ca\f\4\2\2\u00ca\u00cb\7"+
		"\31\2\2\u00cb\u00cd\5\"\22\2\u00cc\u00c6\3\2\2\2\u00cc\u00c9\3\2\2\2\u00cd"+
		"\u00d0\3\2\2\2\u00ce\u00cc\3\2\2\2\u00ce\u00cf\3\2\2\2\u00cf!\3\2\2\2"+
		"\u00d0\u00ce\3\2\2\2\u00d1\u00d2\b\22\1\2\u00d2\u00d3\7\33\2\2\u00d3\u00d6"+
		"\5$\23\2\u00d4\u00d6\5$\23\2\u00d5\u00d1\3\2\2\2\u00d5\u00d4\3\2\2\2\u00d6"+
		"\u00df\3\2\2\2\u00d7\u00d8\f\6\2\2\u00d8\u00d9\7\32\2\2\u00d9\u00de\5"+
		"$\23\2\u00da\u00db\f\5\2\2\u00db\u00dc\7\33\2\2\u00dc\u00de\5$\23\2\u00dd"+
		"\u00d7\3\2\2\2\u00dd\u00da\3\2\2\2\u00de\u00e1\3\2\2\2\u00df\u00dd\3\2"+
		"\2\2\u00df\u00e0\3\2\2\2\u00e0#\3\2\2\2\u00e1\u00df\3\2\2\2\u00e2\u00e3"+
		"\b\23\1\2\u00e3\u00e4\5&\24\2\u00e4\u00ed\3\2\2\2\u00e5\u00e6\f\5\2\2"+
		"\u00e6\u00e7\7\34\2\2\u00e7\u00ec\5&\24\2\u00e8\u00e9\f\4\2\2\u00e9\u00ea"+
		"\7\35\2\2\u00ea\u00ec\5&\24\2\u00eb\u00e5\3\2\2\2\u00eb\u00e8\3\2\2\2"+
		"\u00ec\u00ef\3\2\2\2\u00ed\u00eb\3\2\2\2\u00ed\u00ee\3\2\2\2\u00ee%\3"+
		"\2\2\2\u00ef\u00ed\3\2\2\2\u00f0\u00fe\5(\25\2\u00f1\u00f2\5\4\3\2\u00f2"+
		"\u00f3\7\13\2\2\u00f3\u00f4\5\34\17\2\u00f4\u00f5\7\f\2\2\u00f5\u00fe"+
		"\3\2\2\2\u00f6\u00fe\7\36\2\2\u00f7\u00fe\7\37\2\2\u00f8\u00fe\7$\2\2"+
		"\u00f9\u00fa\7\13\2\2\u00fa\u00fb\5\36\20\2\u00fb\u00fc\7\f\2\2\u00fc"+
		"\u00fe\3\2\2\2\u00fd\u00f0\3\2\2\2\u00fd\u00f1\3\2\2\2\u00fd\u00f6\3\2"+
		"\2\2\u00fd\u00f7\3\2\2\2\u00fd\u00f8\3\2\2\2\u00fd\u00f9\3\2\2\2\u00fe"+
		"\'\3\2\2\2\u00ff\u0106\5\4\3\2\u0100\u0101\5\4\3\2\u0101\u0102\7\7\2\2"+
		"\u0102\u0103\5\36\20\2\u0103\u0104\7\b\2\2\u0104\u0106\3\2\2\2\u0105\u00ff"+
		"\3\2\2\2\u0105\u0100\3\2\2\2\u0106)\3\2\2\2\u0107\u010a\7 \2\2\u0108\u010a"+
		"\5,\27\2\u0109\u0107\3\2\2\2\u0109\u0108\3\2\2\2\u010a+\3\2\2\2\u010b"+
		"\u010c\t\2\2\2\u010c-\3\2\2\2\31=AHY^v\u0080\u009e\u00a8\u00b0\u00b6\u00be"+
		"\u00c0\u00cc\u00ce\u00d5\u00dd\u00df\u00eb\u00ed\u00fd\u0105\u0109";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}