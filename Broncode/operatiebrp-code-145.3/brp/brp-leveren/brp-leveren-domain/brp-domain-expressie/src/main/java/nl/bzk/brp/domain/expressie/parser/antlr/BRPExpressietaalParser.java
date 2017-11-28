/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

// Generated from BRPExpressietaal.g4 by ANTLR 4.7

package nl.bzk.brp.domain.expressie.parser.antlr;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class BRPExpressietaalParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, STRING=29, INTEGER=30, WS=31, 
		LP=32, RP=33, LL=34, RL=35, COMMA=36, DOT=37, UNKNOWN_VALUE=38, EOP_EQUAL=39, 
		AOP_EQUAL=40, OP_EQUAL=41, EOP_NOT_EQUAL=42, AOP_NOT_EQUAL=43, OP_NOT_EQUAL=44, 
		EOP_LIKE=45, AOP_LIKE=46, OP_LIKE=47, EOP_LESS=48, AOP_LESS=49, OP_LESS=50, 
		EOP_GREATER=51, AOP_GREATER=52, OP_GREATER=53, EOP_LESS_EQUAL=54, AOP_LESS_EQUAL=55, 
		OP_LESS_EQUAL=56, EOP_GREATER_EQUAL=57, AOP_GREATER_EQUAL=58, OP_GREATER_EQUAL=59, 
		OP_AIN_WILDCARD=60, OP_AIN=61, OP_EIN_WILDCARD=62, OP_EIN=63, OP_PLUS=64, 
		OP_MINUS=65, OP_OR=66, OP_AND=67, OP_NOT=68, OP_REF=69, OP_WAARBIJ=70, 
		TRUE_CONSTANT=71, FALSE_CONSTANT=72, NULL_CONSTANT=73, MAAND_JAN=74, MAAND_FEB=75, 
		MAAND_MRT=76, MAAND_APR=77, MAAND_MEI=78, MAAND_JUN=79, MAAND_JUL=80, 
		MAAND_AUG=81, MAAND_SEP=82, MAAND_OKT=83, MAAND_NOV=84, MAAND_DEC=85, 
		IDENTIFIER=86;
	public static final int
		RULE_brp_expressie = 0, RULE_exp = 1, RULE_closure = 2, RULE_assignments = 3, 
		RULE_assignment = 4, RULE_booleanExp = 5, RULE_booleanTerm = 6, RULE_equalityExpression = 7, 
		RULE_equalityOp = 8, RULE_relationalExpression = 9, RULE_relationalOp = 10, 
		RULE_collectionEOp = 11, RULE_collectionAOp = 12, RULE_ordinalExpression = 13, 
		RULE_ordinalOp = 14, RULE_negatableExpression = 15, RULE_negationOperator = 16, 
		RULE_unaryExpression = 17, RULE_bracketedExp = 18, RULE_expressionList = 19, 
		RULE_emptyList = 20, RULE_nonEmptyList = 21, RULE_element = 22, RULE_variable = 23, 
		RULE_function = 24, RULE_functionName = 25, RULE_existFunction = 26, RULE_existFunctionName = 27, 
		RULE_literal = 28, RULE_stringLiteral = 29, RULE_booleanLiteral = 30, 
		RULE_numericLiteral = 31, RULE_dateTimeLiteral = 32, RULE_dateLiteral = 33, 
		RULE_year = 34, RULE_month = 35, RULE_monthName = 36, RULE_day = 37, RULE_hour = 38, 
		RULE_minute = 39, RULE_second = 40, RULE_periodLiteral = 41, RULE_relativeYear = 42, 
		RULE_relativeMonth = 43, RULE_relativeDay = 44, RULE_periodPart = 45, 
		RULE_elementCodeLiteral = 46, RULE_nullLiteral = 47, RULE_element_path = 48;
	public static final String[] ruleNames = {
		"brp_expressie", "exp", "closure", "assignments", "assignment", "booleanExp", 
		"booleanTerm", "equalityExpression", "equalityOp", "relationalExpression", 
		"relationalOp", "collectionEOp", "collectionAOp", "ordinalExpression", 
		"ordinalOp", "negatableExpression", "negationOperator", "unaryExpression", 
		"bracketedExp", "expressionList", "emptyList", "nonEmptyList", "element", 
		"variable", "function", "functionName", "existFunction", "existFunctionName", 
		"literal", "stringLiteral", "booleanLiteral", "numericLiteral", "dateTimeLiteral", 
		"dateLiteral", "year", "month", "monthName", "day", "hour", "minute", 
		"second", "periodLiteral", "relativeYear", "relativeMonth", "relativeDay", 
		"periodPart", "elementCodeLiteral", "nullLiteral", "element_path"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'IS_NULL'", "'DATUM'", "'VANDAAG'", "'DAG'", "'MAAND'", "'JAAR'", 
		"'AANTAL_DAGEN'", "'LAATSTE_DAG'", "'AANTAL'", "'ALS'", "'HISM'", "'HISM_LAATSTE'", 
		"'HISF'", "'GEWIJZIGD'", "'KV'", "'KNV'", "'ACTIE'", "'AH'", "'SELECTIE_DATUM'", 
		"'SELECTIE_LIJST'", "'ER_IS'", "'ALLE'", "'FILTER'", "'MAP'", "'/'", "'^'", 
		"'['", "']'", null, null, null, "'('", "')'", "'{'", "'}'", "','", "'.'", 
		"'?'", "'E='", "'A='", "'='", "'E<>'", "'A<>'", "'<>'", "'E=%'", "'A=%'", 
		"'=%'", "'E<'", "'A<'", "'<'", "'E>'", "'A>'", "'>'", "'E<='", "'A<='", 
		"'<='", "'E>='", "'A>='", "'>='", "'AIN%'", "'AIN'", "'EIN%'", "'EIN'", 
		"'+'", "'-'", "'OF'", "'EN'", "'NIET'", "'$'", "'WAARBIJ'", null, null, 
		"'NULL'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, "STRING", "INTEGER", "WS", "LP", "RP", "LL", 
		"RL", "COMMA", "DOT", "UNKNOWN_VALUE", "EOP_EQUAL", "AOP_EQUAL", "OP_EQUAL", 
		"EOP_NOT_EQUAL", "AOP_NOT_EQUAL", "OP_NOT_EQUAL", "EOP_LIKE", "AOP_LIKE", 
		"OP_LIKE", "EOP_LESS", "AOP_LESS", "OP_LESS", "EOP_GREATER", "AOP_GREATER", 
		"OP_GREATER", "EOP_LESS_EQUAL", "AOP_LESS_EQUAL", "OP_LESS_EQUAL", "EOP_GREATER_EQUAL", 
		"AOP_GREATER_EQUAL", "OP_GREATER_EQUAL", "OP_AIN_WILDCARD", "OP_AIN", 
		"OP_EIN_WILDCARD", "OP_EIN", "OP_PLUS", "OP_MINUS", "OP_OR", "OP_AND", 
		"OP_NOT", "OP_REF", "OP_WAARBIJ", "TRUE_CONSTANT", "FALSE_CONSTANT", "NULL_CONSTANT", 
		"MAAND_JAN", "MAAND_FEB", "MAAND_MRT", "MAAND_APR", "MAAND_MEI", "MAAND_JUN", 
		"MAAND_JUL", "MAAND_AUG", "MAAND_SEP", "MAAND_OKT", "MAAND_NOV", "MAAND_DEC", 
		"IDENTIFIER"
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
	public String getGrammarFileName() { return "BRPExpressietaal.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public BRPExpressietaalParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class Brp_expressieContext extends ParserRuleContext {
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public Brp_expressieContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_brp_expressie; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitBrp_expressie(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Brp_expressieContext brp_expressie() throws RecognitionException {
		Brp_expressieContext _localctx = new Brp_expressieContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_brp_expressie);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98);
			exp();
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
		public ClosureContext closure() {
			return getRuleContext(ClosureContext.class,0);
		}
		public ExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpContext exp() throws RecognitionException {
		ExpContext _localctx = new ExpContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_exp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(100);
			closure();
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

	public static class ClosureContext extends ParserRuleContext {
		public BooleanExpContext booleanExp() {
			return getRuleContext(BooleanExpContext.class,0);
		}
		public AssignmentsContext assignments() {
			return getRuleContext(AssignmentsContext.class,0);
		}
		public ClosureContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_closure; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitClosure(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClosureContext closure() throws RecognitionException {
		ClosureContext _localctx = new ClosureContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_closure);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(102);
			booleanExp();
			setState(104);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==OP_WAARBIJ) {
				{
				setState(103);
				assignments();
				}
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

	public static class AssignmentsContext extends ParserRuleContext {
		public TerminalNode OP_WAARBIJ() { return getToken(BRPExpressietaalParser.OP_WAARBIJ, 0); }
		public List<AssignmentContext> assignment() {
			return getRuleContexts(AssignmentContext.class);
		}
		public AssignmentContext assignment(int i) {
			return getRuleContext(AssignmentContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(BRPExpressietaalParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(BRPExpressietaalParser.COMMA, i);
		}
		public AssignmentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignments; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitAssignments(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentsContext assignments() throws RecognitionException {
		AssignmentsContext _localctx = new AssignmentsContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_assignments);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(106);
			match(OP_WAARBIJ);
			setState(107);
			assignment();
			setState(112);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(108);
					match(COMMA);
					setState(109);
					assignment();
					}
					} 
				}
				setState(114);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
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

	public static class AssignmentContext extends ParserRuleContext {
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public TerminalNode OP_EQUAL() { return getToken(BRPExpressietaalParser.OP_EQUAL, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public AssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_assignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(115);
			variable();
			setState(116);
			match(OP_EQUAL);
			setState(117);
			exp();
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

	public static class BooleanExpContext extends ParserRuleContext {
		public BooleanTermContext booleanTerm() {
			return getRuleContext(BooleanTermContext.class,0);
		}
		public TerminalNode OP_OR() { return getToken(BRPExpressietaalParser.OP_OR, 0); }
		public BooleanExpContext booleanExp() {
			return getRuleContext(BooleanExpContext.class,0);
		}
		public BooleanExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanExp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitBooleanExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BooleanExpContext booleanExp() throws RecognitionException {
		BooleanExpContext _localctx = new BooleanExpContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_booleanExp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(119);
			booleanTerm();
			setState(122);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==OP_OR) {
				{
				setState(120);
				match(OP_OR);
				setState(121);
				booleanExp();
				}
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

	public static class BooleanTermContext extends ParserRuleContext {
		public EqualityExpressionContext equalityExpression() {
			return getRuleContext(EqualityExpressionContext.class,0);
		}
		public TerminalNode OP_AND() { return getToken(BRPExpressietaalParser.OP_AND, 0); }
		public BooleanTermContext booleanTerm() {
			return getRuleContext(BooleanTermContext.class,0);
		}
		public BooleanTermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanTerm; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitBooleanTerm(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BooleanTermContext booleanTerm() throws RecognitionException {
		BooleanTermContext _localctx = new BooleanTermContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_booleanTerm);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(124);
			equalityExpression();
			setState(127);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==OP_AND) {
				{
				setState(125);
				match(OP_AND);
				setState(126);
				booleanTerm();
				}
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

	public static class EqualityExpressionContext extends ParserRuleContext {
		public List<RelationalExpressionContext> relationalExpression() {
			return getRuleContexts(RelationalExpressionContext.class);
		}
		public RelationalExpressionContext relationalExpression(int i) {
			return getRuleContext(RelationalExpressionContext.class,i);
		}
		public EqualityOpContext equalityOp() {
			return getRuleContext(EqualityOpContext.class,0);
		}
		public EqualityExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_equalityExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitEqualityExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EqualityExpressionContext equalityExpression() throws RecognitionException {
		EqualityExpressionContext _localctx = new EqualityExpressionContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_equalityExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(129);
			relationalExpression();
			setState(133);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OP_EQUAL) | (1L << OP_NOT_EQUAL) | (1L << OP_LIKE))) != 0)) {
				{
				setState(130);
				equalityOp();
				setState(131);
				relationalExpression();
				}
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

	public static class EqualityOpContext extends ParserRuleContext {
		public TerminalNode OP_EQUAL() { return getToken(BRPExpressietaalParser.OP_EQUAL, 0); }
		public TerminalNode OP_NOT_EQUAL() { return getToken(BRPExpressietaalParser.OP_NOT_EQUAL, 0); }
		public TerminalNode OP_LIKE() { return getToken(BRPExpressietaalParser.OP_LIKE, 0); }
		public EqualityOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_equalityOp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitEqualityOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EqualityOpContext equalityOp() throws RecognitionException {
		EqualityOpContext _localctx = new EqualityOpContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_equalityOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(135);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OP_EQUAL) | (1L << OP_NOT_EQUAL) | (1L << OP_LIKE))) != 0)) ) {
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

	public static class RelationalExpressionContext extends ParserRuleContext {
		public List<OrdinalExpressionContext> ordinalExpression() {
			return getRuleContexts(OrdinalExpressionContext.class);
		}
		public OrdinalExpressionContext ordinalExpression(int i) {
			return getRuleContext(OrdinalExpressionContext.class,i);
		}
		public RelationalOpContext relationalOp() {
			return getRuleContext(RelationalOpContext.class,0);
		}
		public RelationalExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relationalExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitRelationalExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RelationalExpressionContext relationalExpression() throws RecognitionException {
		RelationalExpressionContext _localctx = new RelationalExpressionContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_relationalExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(137);
			ordinalExpression();
			setState(141);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EOP_EQUAL) | (1L << AOP_EQUAL) | (1L << EOP_NOT_EQUAL) | (1L << AOP_NOT_EQUAL) | (1L << EOP_LIKE) | (1L << AOP_LIKE) | (1L << EOP_LESS) | (1L << AOP_LESS) | (1L << OP_LESS) | (1L << EOP_GREATER) | (1L << AOP_GREATER) | (1L << OP_GREATER) | (1L << EOP_LESS_EQUAL) | (1L << AOP_LESS_EQUAL) | (1L << OP_LESS_EQUAL) | (1L << EOP_GREATER_EQUAL) | (1L << AOP_GREATER_EQUAL) | (1L << OP_GREATER_EQUAL) | (1L << OP_AIN_WILDCARD) | (1L << OP_AIN) | (1L << OP_EIN_WILDCARD) | (1L << OP_EIN))) != 0)) {
				{
				setState(138);
				relationalOp();
				setState(139);
				ordinalExpression();
				}
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

	public static class RelationalOpContext extends ParserRuleContext {
		public TerminalNode OP_LESS() { return getToken(BRPExpressietaalParser.OP_LESS, 0); }
		public TerminalNode OP_GREATER() { return getToken(BRPExpressietaalParser.OP_GREATER, 0); }
		public TerminalNode OP_LESS_EQUAL() { return getToken(BRPExpressietaalParser.OP_LESS_EQUAL, 0); }
		public TerminalNode OP_GREATER_EQUAL() { return getToken(BRPExpressietaalParser.OP_GREATER_EQUAL, 0); }
		public TerminalNode OP_AIN() { return getToken(BRPExpressietaalParser.OP_AIN, 0); }
		public TerminalNode OP_AIN_WILDCARD() { return getToken(BRPExpressietaalParser.OP_AIN_WILDCARD, 0); }
		public TerminalNode OP_EIN() { return getToken(BRPExpressietaalParser.OP_EIN, 0); }
		public TerminalNode OP_EIN_WILDCARD() { return getToken(BRPExpressietaalParser.OP_EIN_WILDCARD, 0); }
		public CollectionEOpContext collectionEOp() {
			return getRuleContext(CollectionEOpContext.class,0);
		}
		public CollectionAOpContext collectionAOp() {
			return getRuleContext(CollectionAOpContext.class,0);
		}
		public RelationalOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relationalOp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitRelationalOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RelationalOpContext relationalOp() throws RecognitionException {
		RelationalOpContext _localctx = new RelationalOpContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_relationalOp);
		try {
			setState(153);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OP_LESS:
				enterOuterAlt(_localctx, 1);
				{
				setState(143);
				match(OP_LESS);
				}
				break;
			case OP_GREATER:
				enterOuterAlt(_localctx, 2);
				{
				setState(144);
				match(OP_GREATER);
				}
				break;
			case OP_LESS_EQUAL:
				enterOuterAlt(_localctx, 3);
				{
				setState(145);
				match(OP_LESS_EQUAL);
				}
				break;
			case OP_GREATER_EQUAL:
				enterOuterAlt(_localctx, 4);
				{
				setState(146);
				match(OP_GREATER_EQUAL);
				}
				break;
			case OP_AIN:
				enterOuterAlt(_localctx, 5);
				{
				setState(147);
				match(OP_AIN);
				}
				break;
			case OP_AIN_WILDCARD:
				enterOuterAlt(_localctx, 6);
				{
				setState(148);
				match(OP_AIN_WILDCARD);
				}
				break;
			case OP_EIN:
				enterOuterAlt(_localctx, 7);
				{
				setState(149);
				match(OP_EIN);
				}
				break;
			case OP_EIN_WILDCARD:
				enterOuterAlt(_localctx, 8);
				{
				setState(150);
				match(OP_EIN_WILDCARD);
				}
				break;
			case EOP_EQUAL:
			case EOP_NOT_EQUAL:
			case EOP_LIKE:
			case EOP_LESS:
			case EOP_GREATER:
			case EOP_LESS_EQUAL:
			case EOP_GREATER_EQUAL:
				enterOuterAlt(_localctx, 9);
				{
				setState(151);
				collectionEOp();
				}
				break;
			case AOP_EQUAL:
			case AOP_NOT_EQUAL:
			case AOP_LIKE:
			case AOP_LESS:
			case AOP_GREATER:
			case AOP_LESS_EQUAL:
			case AOP_GREATER_EQUAL:
				enterOuterAlt(_localctx, 10);
				{
				setState(152);
				collectionAOp();
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

	public static class CollectionEOpContext extends ParserRuleContext {
		public TerminalNode EOP_EQUAL() { return getToken(BRPExpressietaalParser.EOP_EQUAL, 0); }
		public TerminalNode EOP_NOT_EQUAL() { return getToken(BRPExpressietaalParser.EOP_NOT_EQUAL, 0); }
		public TerminalNode EOP_LESS() { return getToken(BRPExpressietaalParser.EOP_LESS, 0); }
		public TerminalNode EOP_GREATER() { return getToken(BRPExpressietaalParser.EOP_GREATER, 0); }
		public TerminalNode EOP_LESS_EQUAL() { return getToken(BRPExpressietaalParser.EOP_LESS_EQUAL, 0); }
		public TerminalNode EOP_GREATER_EQUAL() { return getToken(BRPExpressietaalParser.EOP_GREATER_EQUAL, 0); }
		public TerminalNode EOP_LIKE() { return getToken(BRPExpressietaalParser.EOP_LIKE, 0); }
		public CollectionEOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_collectionEOp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitCollectionEOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CollectionEOpContext collectionEOp() throws RecognitionException {
		CollectionEOpContext _localctx = new CollectionEOpContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_collectionEOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(155);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EOP_EQUAL) | (1L << EOP_NOT_EQUAL) | (1L << EOP_LIKE) | (1L << EOP_LESS) | (1L << EOP_GREATER) | (1L << EOP_LESS_EQUAL) | (1L << EOP_GREATER_EQUAL))) != 0)) ) {
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

	public static class CollectionAOpContext extends ParserRuleContext {
		public TerminalNode AOP_EQUAL() { return getToken(BRPExpressietaalParser.AOP_EQUAL, 0); }
		public TerminalNode AOP_NOT_EQUAL() { return getToken(BRPExpressietaalParser.AOP_NOT_EQUAL, 0); }
		public TerminalNode AOP_LESS() { return getToken(BRPExpressietaalParser.AOP_LESS, 0); }
		public TerminalNode AOP_GREATER() { return getToken(BRPExpressietaalParser.AOP_GREATER, 0); }
		public TerminalNode AOP_LESS_EQUAL() { return getToken(BRPExpressietaalParser.AOP_LESS_EQUAL, 0); }
		public TerminalNode AOP_GREATER_EQUAL() { return getToken(BRPExpressietaalParser.AOP_GREATER_EQUAL, 0); }
		public TerminalNode AOP_LIKE() { return getToken(BRPExpressietaalParser.AOP_LIKE, 0); }
		public CollectionAOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_collectionAOp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitCollectionAOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CollectionAOpContext collectionAOp() throws RecognitionException {
		CollectionAOpContext _localctx = new CollectionAOpContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_collectionAOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(157);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << AOP_EQUAL) | (1L << AOP_NOT_EQUAL) | (1L << AOP_LIKE) | (1L << AOP_LESS) | (1L << AOP_GREATER) | (1L << AOP_LESS_EQUAL) | (1L << AOP_GREATER_EQUAL))) != 0)) ) {
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

	public static class OrdinalExpressionContext extends ParserRuleContext {
		public NegatableExpressionContext negatableExpression() {
			return getRuleContext(NegatableExpressionContext.class,0);
		}
		public OrdinalOpContext ordinalOp() {
			return getRuleContext(OrdinalOpContext.class,0);
		}
		public OrdinalExpressionContext ordinalExpression() {
			return getRuleContext(OrdinalExpressionContext.class,0);
		}
		public OrdinalExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ordinalExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitOrdinalExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OrdinalExpressionContext ordinalExpression() throws RecognitionException {
		OrdinalExpressionContext _localctx = new OrdinalExpressionContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_ordinalExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(159);
			negatableExpression();
			setState(163);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==OP_PLUS || _la==OP_MINUS) {
				{
				setState(160);
				ordinalOp();
				setState(161);
				ordinalExpression();
				}
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

	public static class OrdinalOpContext extends ParserRuleContext {
		public TerminalNode OP_PLUS() { return getToken(BRPExpressietaalParser.OP_PLUS, 0); }
		public TerminalNode OP_MINUS() { return getToken(BRPExpressietaalParser.OP_MINUS, 0); }
		public OrdinalOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ordinalOp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitOrdinalOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OrdinalOpContext ordinalOp() throws RecognitionException {
		OrdinalOpContext _localctx = new OrdinalOpContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_ordinalOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(165);
			_la = _input.LA(1);
			if ( !(_la==OP_PLUS || _la==OP_MINUS) ) {
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

	public static class NegatableExpressionContext extends ParserRuleContext {
		public UnaryExpressionContext unaryExpression() {
			return getRuleContext(UnaryExpressionContext.class,0);
		}
		public NegationOperatorContext negationOperator() {
			return getRuleContext(NegationOperatorContext.class,0);
		}
		public NegatableExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_negatableExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitNegatableExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NegatableExpressionContext negatableExpression() throws RecognitionException {
		NegatableExpressionContext _localctx = new NegatableExpressionContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_negatableExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(168);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				{
				setState(167);
				negationOperator();
				}
				break;
			}
			setState(170);
			unaryExpression();
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

	public static class NegationOperatorContext extends ParserRuleContext {
		public TerminalNode OP_MINUS() { return getToken(BRPExpressietaalParser.OP_MINUS, 0); }
		public TerminalNode OP_NOT() { return getToken(BRPExpressietaalParser.OP_NOT, 0); }
		public NegationOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_negationOperator; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitNegationOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NegationOperatorContext negationOperator() throws RecognitionException {
		NegationOperatorContext _localctx = new NegationOperatorContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_negationOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(172);
			_la = _input.LA(1);
			if ( !(_la==OP_MINUS || _la==OP_NOT) ) {
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

	public static class UnaryExpressionContext extends ParserRuleContext {
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public FunctionContext function() {
			return getRuleContext(FunctionContext.class,0);
		}
		public ExistFunctionContext existFunction() {
			return getRuleContext(ExistFunctionContext.class,0);
		}
		public ElementContext element() {
			return getRuleContext(ElementContext.class,0);
		}
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public BracketedExpContext bracketedExp() {
			return getRuleContext(BracketedExpContext.class,0);
		}
		public UnaryExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unaryExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitUnaryExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnaryExpressionContext unaryExpression() throws RecognitionException {
		UnaryExpressionContext _localctx = new UnaryExpressionContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_unaryExpression);
		try {
			setState(181);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(174);
				expressionList();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(175);
				function();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(176);
				existFunction();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(177);
				element();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(178);
				literal();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(179);
				variable();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(180);
				bracketedExp();
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

	public static class BracketedExpContext extends ParserRuleContext {
		public TerminalNode LP() { return getToken(BRPExpressietaalParser.LP, 0); }
		public ExpContext exp() {
			return getRuleContext(ExpContext.class,0);
		}
		public TerminalNode RP() { return getToken(BRPExpressietaalParser.RP, 0); }
		public BracketedExpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bracketedExp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitBracketedExp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BracketedExpContext bracketedExp() throws RecognitionException {
		BracketedExpContext _localctx = new BracketedExpContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_bracketedExp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(183);
			match(LP);
			setState(184);
			exp();
			setState(185);
			match(RP);
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

	public static class ExpressionListContext extends ParserRuleContext {
		public EmptyListContext emptyList() {
			return getRuleContext(EmptyListContext.class,0);
		}
		public NonEmptyListContext nonEmptyList() {
			return getRuleContext(NonEmptyListContext.class,0);
		}
		public ExpressionListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitExpressionList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionListContext expressionList() throws RecognitionException {
		ExpressionListContext _localctx = new ExpressionListContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_expressionList);
		try {
			setState(189);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(187);
				emptyList();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(188);
				nonEmptyList();
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

	public static class EmptyListContext extends ParserRuleContext {
		public TerminalNode LL() { return getToken(BRPExpressietaalParser.LL, 0); }
		public TerminalNode RL() { return getToken(BRPExpressietaalParser.RL, 0); }
		public EmptyListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_emptyList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitEmptyList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EmptyListContext emptyList() throws RecognitionException {
		EmptyListContext _localctx = new EmptyListContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_emptyList);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(191);
			match(LL);
			setState(192);
			match(RL);
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

	public static class NonEmptyListContext extends ParserRuleContext {
		public TerminalNode LL() { return getToken(BRPExpressietaalParser.LL, 0); }
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public TerminalNode RL() { return getToken(BRPExpressietaalParser.RL, 0); }
		public List<TerminalNode> COMMA() { return getTokens(BRPExpressietaalParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(BRPExpressietaalParser.COMMA, i);
		}
		public NonEmptyListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nonEmptyList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitNonEmptyList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NonEmptyListContext nonEmptyList() throws RecognitionException {
		NonEmptyListContext _localctx = new NonEmptyListContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_nonEmptyList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(194);
			match(LL);
			setState(195);
			exp();
			setState(200);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(196);
				match(COMMA);
				setState(197);
				exp();
				}
				}
				setState(202);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(203);
			match(RL);
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

	public static class ElementContext extends ParserRuleContext {
		public Element_pathContext element_path() {
			return getRuleContext(Element_pathContext.class,0);
		}
		public ElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_element; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementContext element() throws RecognitionException {
		ElementContext _localctx = new ElementContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_element);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(205);
			element_path();
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

	public static class VariableContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(BRPExpressietaalParser.IDENTIFIER, 0); }
		public VariableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitVariable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableContext variable() throws RecognitionException {
		VariableContext _localctx = new VariableContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_variable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(207);
			match(IDENTIFIER);
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

	public static class FunctionContext extends ParserRuleContext {
		public FunctionNameContext functionName() {
			return getRuleContext(FunctionNameContext.class,0);
		}
		public TerminalNode LP() { return getToken(BRPExpressietaalParser.LP, 0); }
		public TerminalNode RP() { return getToken(BRPExpressietaalParser.RP, 0); }
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(BRPExpressietaalParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(BRPExpressietaalParser.COMMA, i);
		}
		public FunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitFunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionContext function() throws RecognitionException {
		FunctionContext _localctx = new FunctionContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_function);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(209);
			functionName();
			setState(210);
			match(LP);
			setState(219);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__2) | (1L << T__3) | (1L << T__4) | (1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23) | (1L << T__25) | (1L << T__26) | (1L << STRING) | (1L << INTEGER) | (1L << LP) | (1L << LL) | (1L << UNKNOWN_VALUE))) != 0) || ((((_la - 65)) & ~0x3f) == 0 && ((1L << (_la - 65)) & ((1L << (OP_MINUS - 65)) | (1L << (OP_NOT - 65)) | (1L << (TRUE_CONSTANT - 65)) | (1L << (FALSE_CONSTANT - 65)) | (1L << (NULL_CONSTANT - 65)) | (1L << (IDENTIFIER - 65)))) != 0)) {
				{
				setState(211);
				exp();
				setState(216);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(212);
					match(COMMA);
					setState(213);
					exp();
					}
					}
					setState(218);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(221);
			match(RP);
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

	public static class FunctionNameContext extends ParserRuleContext {
		public FunctionNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionName; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitFunctionName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionNameContext functionName() throws RecognitionException {
		FunctionNameContext _localctx = new FunctionNameContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_functionName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(223);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__1) | (1L << T__2) | (1L << T__3) | (1L << T__4) | (1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12) | (1L << T__13) | (1L << T__14) | (1L << T__15) | (1L << T__16) | (1L << T__17) | (1L << T__18) | (1L << T__19))) != 0)) ) {
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

	public static class ExistFunctionContext extends ParserRuleContext {
		public ExistFunctionNameContext existFunctionName() {
			return getRuleContext(ExistFunctionNameContext.class,0);
		}
		public TerminalNode LP() { return getToken(BRPExpressietaalParser.LP, 0); }
		public List<ExpContext> exp() {
			return getRuleContexts(ExpContext.class);
		}
		public ExpContext exp(int i) {
			return getRuleContext(ExpContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(BRPExpressietaalParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(BRPExpressietaalParser.COMMA, i);
		}
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public TerminalNode RP() { return getToken(BRPExpressietaalParser.RP, 0); }
		public ExistFunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_existFunction; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitExistFunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExistFunctionContext existFunction() throws RecognitionException {
		ExistFunctionContext _localctx = new ExistFunctionContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_existFunction);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(225);
			existFunctionName();
			setState(226);
			match(LP);
			setState(227);
			exp();
			setState(228);
			match(COMMA);
			setState(229);
			variable();
			setState(230);
			match(COMMA);
			setState(231);
			exp();
			setState(232);
			match(RP);
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

	public static class ExistFunctionNameContext extends ParserRuleContext {
		public ExistFunctionNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_existFunctionName; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitExistFunctionName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExistFunctionNameContext existFunctionName() throws RecognitionException {
		ExistFunctionNameContext _localctx = new ExistFunctionNameContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_existFunctionName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(234);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__20) | (1L << T__21) | (1L << T__22) | (1L << T__23))) != 0)) ) {
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

	public static class LiteralContext extends ParserRuleContext {
		public StringLiteralContext stringLiteral() {
			return getRuleContext(StringLiteralContext.class,0);
		}
		public BooleanLiteralContext booleanLiteral() {
			return getRuleContext(BooleanLiteralContext.class,0);
		}
		public NumericLiteralContext numericLiteral() {
			return getRuleContext(NumericLiteralContext.class,0);
		}
		public DateLiteralContext dateLiteral() {
			return getRuleContext(DateLiteralContext.class,0);
		}
		public DateTimeLiteralContext dateTimeLiteral() {
			return getRuleContext(DateTimeLiteralContext.class,0);
		}
		public PeriodLiteralContext periodLiteral() {
			return getRuleContext(PeriodLiteralContext.class,0);
		}
		public ElementContext element() {
			return getRuleContext(ElementContext.class,0);
		}
		public ElementCodeLiteralContext elementCodeLiteral() {
			return getRuleContext(ElementCodeLiteralContext.class,0);
		}
		public NullLiteralContext nullLiteral() {
			return getRuleContext(NullLiteralContext.class,0);
		}
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_literal);
		try {
			setState(245);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(236);
				stringLiteral();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(237);
				booleanLiteral();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(238);
				numericLiteral();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(239);
				dateLiteral();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(240);
				dateTimeLiteral();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(241);
				periodLiteral();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(242);
				element();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(243);
				elementCodeLiteral();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(244);
				nullLiteral();
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

	public static class StringLiteralContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(BRPExpressietaalParser.STRING, 0); }
		public StringLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stringLiteral; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitStringLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StringLiteralContext stringLiteral() throws RecognitionException {
		StringLiteralContext _localctx = new StringLiteralContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_stringLiteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(247);
			match(STRING);
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

	public static class BooleanLiteralContext extends ParserRuleContext {
		public TerminalNode TRUE_CONSTANT() { return getToken(BRPExpressietaalParser.TRUE_CONSTANT, 0); }
		public TerminalNode FALSE_CONSTANT() { return getToken(BRPExpressietaalParser.FALSE_CONSTANT, 0); }
		public BooleanLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanLiteral; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitBooleanLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BooleanLiteralContext booleanLiteral() throws RecognitionException {
		BooleanLiteralContext _localctx = new BooleanLiteralContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_booleanLiteral);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(249);
			_la = _input.LA(1);
			if ( !(_la==TRUE_CONSTANT || _la==FALSE_CONSTANT) ) {
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

	public static class NumericLiteralContext extends ParserRuleContext {
		public TerminalNode INTEGER() { return getToken(BRPExpressietaalParser.INTEGER, 0); }
		public NumericLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numericLiteral; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitNumericLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumericLiteralContext numericLiteral() throws RecognitionException {
		NumericLiteralContext _localctx = new NumericLiteralContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_numericLiteral);
		try {
			setState(254);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INTEGER:
				enterOuterAlt(_localctx, 1);
				{
				setState(251);
				match(INTEGER);
				}
				break;
			case OP_MINUS:
				enterOuterAlt(_localctx, 2);
				{
				setState(252);
				match(OP_MINUS);
				setState(253);
				match(INTEGER);
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

	public static class DateTimeLiteralContext extends ParserRuleContext {
		public YearContext year() {
			return getRuleContext(YearContext.class,0);
		}
		public MonthContext month() {
			return getRuleContext(MonthContext.class,0);
		}
		public DayContext day() {
			return getRuleContext(DayContext.class,0);
		}
		public HourContext hour() {
			return getRuleContext(HourContext.class,0);
		}
		public MinuteContext minute() {
			return getRuleContext(MinuteContext.class,0);
		}
		public SecondContext second() {
			return getRuleContext(SecondContext.class,0);
		}
		public DateTimeLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dateTimeLiteral; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitDateTimeLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DateTimeLiteralContext dateTimeLiteral() throws RecognitionException {
		DateTimeLiteralContext _localctx = new DateTimeLiteralContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_dateTimeLiteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(256);
			year();
			setState(257);
			match(T__24);
			setState(258);
			month();
			setState(259);
			match(T__24);
			setState(260);
			day();
			setState(261);
			match(T__24);
			setState(262);
			hour();
			setState(263);
			match(T__24);
			setState(264);
			minute();
			setState(265);
			match(T__24);
			setState(266);
			second();
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

	public static class DateLiteralContext extends ParserRuleContext {
		public YearContext year() {
			return getRuleContext(YearContext.class,0);
		}
		public MonthContext month() {
			return getRuleContext(MonthContext.class,0);
		}
		public DayContext day() {
			return getRuleContext(DayContext.class,0);
		}
		public DateLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_dateLiteral; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitDateLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DateLiteralContext dateLiteral() throws RecognitionException {
		DateLiteralContext _localctx = new DateLiteralContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_dateLiteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(268);
			year();
			setState(269);
			match(T__24);
			setState(270);
			month();
			setState(271);
			match(T__24);
			setState(272);
			day();
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

	public static class YearContext extends ParserRuleContext {
		public NumericLiteralContext numericLiteral() {
			return getRuleContext(NumericLiteralContext.class,0);
		}
		public TerminalNode UNKNOWN_VALUE() { return getToken(BRPExpressietaalParser.UNKNOWN_VALUE, 0); }
		public YearContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_year; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitYear(this);
			else return visitor.visitChildren(this);
		}
	}

	public final YearContext year() throws RecognitionException {
		YearContext _localctx = new YearContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_year);
		try {
			setState(276);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INTEGER:
			case OP_MINUS:
				enterOuterAlt(_localctx, 1);
				{
				setState(274);
				numericLiteral();
				}
				break;
			case UNKNOWN_VALUE:
				enterOuterAlt(_localctx, 2);
				{
				setState(275);
				match(UNKNOWN_VALUE);
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

	public static class MonthContext extends ParserRuleContext {
		public NumericLiteralContext numericLiteral() {
			return getRuleContext(NumericLiteralContext.class,0);
		}
		public TerminalNode UNKNOWN_VALUE() { return getToken(BRPExpressietaalParser.UNKNOWN_VALUE, 0); }
		public MonthNameContext monthName() {
			return getRuleContext(MonthNameContext.class,0);
		}
		public MonthContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_month; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitMonth(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MonthContext month() throws RecognitionException {
		MonthContext _localctx = new MonthContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_month);
		try {
			setState(281);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INTEGER:
			case OP_MINUS:
				enterOuterAlt(_localctx, 1);
				{
				setState(278);
				numericLiteral();
				}
				break;
			case UNKNOWN_VALUE:
				enterOuterAlt(_localctx, 2);
				{
				setState(279);
				match(UNKNOWN_VALUE);
				}
				break;
			case MAAND_JAN:
			case MAAND_FEB:
			case MAAND_MRT:
			case MAAND_APR:
			case MAAND_MEI:
			case MAAND_JUN:
			case MAAND_JUL:
			case MAAND_AUG:
			case MAAND_SEP:
			case MAAND_OKT:
			case MAAND_NOV:
			case MAAND_DEC:
				enterOuterAlt(_localctx, 3);
				{
				setState(280);
				monthName();
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

	public static class MonthNameContext extends ParserRuleContext {
		public TerminalNode MAAND_JAN() { return getToken(BRPExpressietaalParser.MAAND_JAN, 0); }
		public TerminalNode MAAND_FEB() { return getToken(BRPExpressietaalParser.MAAND_FEB, 0); }
		public TerminalNode MAAND_MRT() { return getToken(BRPExpressietaalParser.MAAND_MRT, 0); }
		public TerminalNode MAAND_APR() { return getToken(BRPExpressietaalParser.MAAND_APR, 0); }
		public TerminalNode MAAND_MEI() { return getToken(BRPExpressietaalParser.MAAND_MEI, 0); }
		public TerminalNode MAAND_JUN() { return getToken(BRPExpressietaalParser.MAAND_JUN, 0); }
		public TerminalNode MAAND_JUL() { return getToken(BRPExpressietaalParser.MAAND_JUL, 0); }
		public TerminalNode MAAND_AUG() { return getToken(BRPExpressietaalParser.MAAND_AUG, 0); }
		public TerminalNode MAAND_SEP() { return getToken(BRPExpressietaalParser.MAAND_SEP, 0); }
		public TerminalNode MAAND_OKT() { return getToken(BRPExpressietaalParser.MAAND_OKT, 0); }
		public TerminalNode MAAND_NOV() { return getToken(BRPExpressietaalParser.MAAND_NOV, 0); }
		public TerminalNode MAAND_DEC() { return getToken(BRPExpressietaalParser.MAAND_DEC, 0); }
		public MonthNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_monthName; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitMonthName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MonthNameContext monthName() throws RecognitionException {
		MonthNameContext _localctx = new MonthNameContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_monthName);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(283);
			_la = _input.LA(1);
			if ( !(((((_la - 74)) & ~0x3f) == 0 && ((1L << (_la - 74)) & ((1L << (MAAND_JAN - 74)) | (1L << (MAAND_FEB - 74)) | (1L << (MAAND_MRT - 74)) | (1L << (MAAND_APR - 74)) | (1L << (MAAND_MEI - 74)) | (1L << (MAAND_JUN - 74)) | (1L << (MAAND_JUL - 74)) | (1L << (MAAND_AUG - 74)) | (1L << (MAAND_SEP - 74)) | (1L << (MAAND_OKT - 74)) | (1L << (MAAND_NOV - 74)) | (1L << (MAAND_DEC - 74)))) != 0)) ) {
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

	public static class DayContext extends ParserRuleContext {
		public NumericLiteralContext numericLiteral() {
			return getRuleContext(NumericLiteralContext.class,0);
		}
		public TerminalNode UNKNOWN_VALUE() { return getToken(BRPExpressietaalParser.UNKNOWN_VALUE, 0); }
		public DayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_day; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitDay(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DayContext day() throws RecognitionException {
		DayContext _localctx = new DayContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_day);
		try {
			setState(287);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INTEGER:
			case OP_MINUS:
				enterOuterAlt(_localctx, 1);
				{
				setState(285);
				numericLiteral();
				}
				break;
			case UNKNOWN_VALUE:
				enterOuterAlt(_localctx, 2);
				{
				setState(286);
				match(UNKNOWN_VALUE);
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

	public static class HourContext extends ParserRuleContext {
		public NumericLiteralContext numericLiteral() {
			return getRuleContext(NumericLiteralContext.class,0);
		}
		public HourContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_hour; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitHour(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HourContext hour() throws RecognitionException {
		HourContext _localctx = new HourContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_hour);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(289);
			numericLiteral();
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

	public static class MinuteContext extends ParserRuleContext {
		public NumericLiteralContext numericLiteral() {
			return getRuleContext(NumericLiteralContext.class,0);
		}
		public MinuteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_minute; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitMinute(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MinuteContext minute() throws RecognitionException {
		MinuteContext _localctx = new MinuteContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_minute);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(291);
			numericLiteral();
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

	public static class SecondContext extends ParserRuleContext {
		public NumericLiteralContext numericLiteral() {
			return getRuleContext(NumericLiteralContext.class,0);
		}
		public SecondContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_second; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitSecond(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SecondContext second() throws RecognitionException {
		SecondContext _localctx = new SecondContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_second);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(293);
			numericLiteral();
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

	public static class PeriodLiteralContext extends ParserRuleContext {
		public RelativeYearContext relativeYear() {
			return getRuleContext(RelativeYearContext.class,0);
		}
		public RelativeMonthContext relativeMonth() {
			return getRuleContext(RelativeMonthContext.class,0);
		}
		public RelativeDayContext relativeDay() {
			return getRuleContext(RelativeDayContext.class,0);
		}
		public PeriodLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_periodLiteral; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitPeriodLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PeriodLiteralContext periodLiteral() throws RecognitionException {
		PeriodLiteralContext _localctx = new PeriodLiteralContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_periodLiteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(295);
			match(T__25);
			setState(296);
			relativeYear();
			setState(297);
			match(T__24);
			setState(298);
			relativeMonth();
			setState(299);
			match(T__24);
			setState(300);
			relativeDay();
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

	public static class RelativeYearContext extends ParserRuleContext {
		public PeriodPartContext periodPart() {
			return getRuleContext(PeriodPartContext.class,0);
		}
		public TerminalNode UNKNOWN_VALUE() { return getToken(BRPExpressietaalParser.UNKNOWN_VALUE, 0); }
		public RelativeYearContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relativeYear; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitRelativeYear(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RelativeYearContext relativeYear() throws RecognitionException {
		RelativeYearContext _localctx = new RelativeYearContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_relativeYear);
		try {
			setState(304);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INTEGER:
			case OP_MINUS:
				enterOuterAlt(_localctx, 1);
				{
				setState(302);
				periodPart();
				}
				break;
			case UNKNOWN_VALUE:
				enterOuterAlt(_localctx, 2);
				{
				setState(303);
				match(UNKNOWN_VALUE);
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

	public static class RelativeMonthContext extends ParserRuleContext {
		public PeriodPartContext periodPart() {
			return getRuleContext(PeriodPartContext.class,0);
		}
		public TerminalNode UNKNOWN_VALUE() { return getToken(BRPExpressietaalParser.UNKNOWN_VALUE, 0); }
		public RelativeMonthContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relativeMonth; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitRelativeMonth(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RelativeMonthContext relativeMonth() throws RecognitionException {
		RelativeMonthContext _localctx = new RelativeMonthContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_relativeMonth);
		try {
			setState(308);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INTEGER:
			case OP_MINUS:
				enterOuterAlt(_localctx, 1);
				{
				setState(306);
				periodPart();
				}
				break;
			case UNKNOWN_VALUE:
				enterOuterAlt(_localctx, 2);
				{
				setState(307);
				match(UNKNOWN_VALUE);
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

	public static class RelativeDayContext extends ParserRuleContext {
		public PeriodPartContext periodPart() {
			return getRuleContext(PeriodPartContext.class,0);
		}
		public TerminalNode UNKNOWN_VALUE() { return getToken(BRPExpressietaalParser.UNKNOWN_VALUE, 0); }
		public RelativeDayContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relativeDay; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitRelativeDay(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RelativeDayContext relativeDay() throws RecognitionException {
		RelativeDayContext _localctx = new RelativeDayContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_relativeDay);
		try {
			setState(312);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INTEGER:
			case OP_MINUS:
				enterOuterAlt(_localctx, 1);
				{
				setState(310);
				periodPart();
				}
				break;
			case UNKNOWN_VALUE:
				enterOuterAlt(_localctx, 2);
				{
				setState(311);
				match(UNKNOWN_VALUE);
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

	public static class PeriodPartContext extends ParserRuleContext {
		public NumericLiteralContext numericLiteral() {
			return getRuleContext(NumericLiteralContext.class,0);
		}
		public PeriodPartContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_periodPart; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitPeriodPart(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PeriodPartContext periodPart() throws RecognitionException {
		PeriodPartContext _localctx = new PeriodPartContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_periodPart);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(316);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				{
				setState(314);
				match(OP_MINUS);
				}
				break;
			case 2:
				{
				}
				break;
			}
			setState(318);
			numericLiteral();
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

	public static class ElementCodeLiteralContext extends ParserRuleContext {
		public Element_pathContext element_path() {
			return getRuleContext(Element_pathContext.class,0);
		}
		public ElementCodeLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elementCodeLiteral; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitElementCodeLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementCodeLiteralContext elementCodeLiteral() throws RecognitionException {
		ElementCodeLiteralContext _localctx = new ElementCodeLiteralContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_elementCodeLiteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(320);
			match(T__26);
			setState(321);
			element_path();
			setState(322);
			match(T__27);
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

	public static class NullLiteralContext extends ParserRuleContext {
		public TerminalNode NULL_CONSTANT() { return getToken(BRPExpressietaalParser.NULL_CONSTANT, 0); }
		public NullLiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nullLiteral; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitNullLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NullLiteralContext nullLiteral() throws RecognitionException {
		NullLiteralContext _localctx = new NullLiteralContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_nullLiteral);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(324);
			match(NULL_CONSTANT);
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

	public static class Element_pathContext extends ParserRuleContext {
		public List<TerminalNode> IDENTIFIER() { return getTokens(BRPExpressietaalParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(BRPExpressietaalParser.IDENTIFIER, i);
		}
		public List<TerminalNode> DOT() { return getTokens(BRPExpressietaalParser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(BRPExpressietaalParser.DOT, i);
		}
		public Element_pathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_element_path; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof BRPExpressietaalVisitor ) return ((BRPExpressietaalVisitor<? extends T>)visitor).visitElement_path(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Element_pathContext element_path() throws RecognitionException {
		Element_pathContext _localctx = new Element_pathContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_element_path);
		int _la;
		try {
			setState(334);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(326);
				match(IDENTIFIER);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(327);
				match(IDENTIFIER);
				setState(330); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(328);
					match(DOT);
					setState(329);
					match(IDENTIFIER);
					}
					}
					setState(332); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==DOT );
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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3X\u0153\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\3\2\3\2\3\3\3\3\3"+
		"\4\3\4\5\4k\n\4\3\5\3\5\3\5\3\5\7\5q\n\5\f\5\16\5t\13\5\3\6\3\6\3\6\3"+
		"\6\3\7\3\7\3\7\5\7}\n\7\3\b\3\b\3\b\5\b\u0082\n\b\3\t\3\t\3\t\3\t\5\t"+
		"\u0088\n\t\3\n\3\n\3\13\3\13\3\13\3\13\5\13\u0090\n\13\3\f\3\f\3\f\3\f"+
		"\3\f\3\f\3\f\3\f\3\f\3\f\5\f\u009c\n\f\3\r\3\r\3\16\3\16\3\17\3\17\3\17"+
		"\3\17\5\17\u00a6\n\17\3\20\3\20\3\21\5\21\u00ab\n\21\3\21\3\21\3\22\3"+
		"\22\3\23\3\23\3\23\3\23\3\23\3\23\3\23\5\23\u00b8\n\23\3\24\3\24\3\24"+
		"\3\24\3\25\3\25\5\25\u00c0\n\25\3\26\3\26\3\26\3\27\3\27\3\27\3\27\7\27"+
		"\u00c9\n\27\f\27\16\27\u00cc\13\27\3\27\3\27\3\30\3\30\3\31\3\31\3\32"+
		"\3\32\3\32\3\32\3\32\7\32\u00d9\n\32\f\32\16\32\u00dc\13\32\5\32\u00de"+
		"\n\32\3\32\3\32\3\33\3\33\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34"+
		"\3\35\3\35\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\36\5\36\u00f8\n\36"+
		"\3\37\3\37\3 \3 \3!\3!\3!\5!\u0101\n!\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\""+
		"\3\"\3\"\3\"\3\"\3#\3#\3#\3#\3#\3#\3$\3$\5$\u0117\n$\3%\3%\3%\5%\u011c"+
		"\n%\3&\3&\3\'\3\'\5\'\u0122\n\'\3(\3(\3)\3)\3*\3*\3+\3+\3+\3+\3+\3+\3"+
		"+\3,\3,\5,\u0133\n,\3-\3-\5-\u0137\n-\3.\3.\5.\u013b\n.\3/\3/\5/\u013f"+
		"\n/\3/\3/\3\60\3\60\3\60\3\60\3\61\3\61\3\62\3\62\3\62\3\62\6\62\u014d"+
		"\n\62\r\62\16\62\u014e\5\62\u0151\n\62\3\62\2\2\63\2\4\6\b\n\f\16\20\22"+
		"\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJLNPRTVXZ\\^`b\2\13\5"+
		"\2++..\61\61\t\2)),,//\62\62\65\6588;;\t\2**--\60\60\63\63\66\6699<<\3"+
		"\2BC\4\2CCFF\3\2\3\26\3\2\27\32\3\2IJ\3\2LW\2\u014f\2d\3\2\2\2\4f\3\2"+
		"\2\2\6h\3\2\2\2\bl\3\2\2\2\nu\3\2\2\2\fy\3\2\2\2\16~\3\2\2\2\20\u0083"+
		"\3\2\2\2\22\u0089\3\2\2\2\24\u008b\3\2\2\2\26\u009b\3\2\2\2\30\u009d\3"+
		"\2\2\2\32\u009f\3\2\2\2\34\u00a1\3\2\2\2\36\u00a7\3\2\2\2 \u00aa\3\2\2"+
		"\2\"\u00ae\3\2\2\2$\u00b7\3\2\2\2&\u00b9\3\2\2\2(\u00bf\3\2\2\2*\u00c1"+
		"\3\2\2\2,\u00c4\3\2\2\2.\u00cf\3\2\2\2\60\u00d1\3\2\2\2\62\u00d3\3\2\2"+
		"\2\64\u00e1\3\2\2\2\66\u00e3\3\2\2\28\u00ec\3\2\2\2:\u00f7\3\2\2\2<\u00f9"+
		"\3\2\2\2>\u00fb\3\2\2\2@\u0100\3\2\2\2B\u0102\3\2\2\2D\u010e\3\2\2\2F"+
		"\u0116\3\2\2\2H\u011b\3\2\2\2J\u011d\3\2\2\2L\u0121\3\2\2\2N\u0123\3\2"+
		"\2\2P\u0125\3\2\2\2R\u0127\3\2\2\2T\u0129\3\2\2\2V\u0132\3\2\2\2X\u0136"+
		"\3\2\2\2Z\u013a\3\2\2\2\\\u013e\3\2\2\2^\u0142\3\2\2\2`\u0146\3\2\2\2"+
		"b\u0150\3\2\2\2de\5\4\3\2e\3\3\2\2\2fg\5\6\4\2g\5\3\2\2\2hj\5\f\7\2ik"+
		"\5\b\5\2ji\3\2\2\2jk\3\2\2\2k\7\3\2\2\2lm\7H\2\2mr\5\n\6\2no\7&\2\2oq"+
		"\5\n\6\2pn\3\2\2\2qt\3\2\2\2rp\3\2\2\2rs\3\2\2\2s\t\3\2\2\2tr\3\2\2\2"+
		"uv\5\60\31\2vw\7+\2\2wx\5\4\3\2x\13\3\2\2\2y|\5\16\b\2z{\7D\2\2{}\5\f"+
		"\7\2|z\3\2\2\2|}\3\2\2\2}\r\3\2\2\2~\u0081\5\20\t\2\177\u0080\7E\2\2\u0080"+
		"\u0082\5\16\b\2\u0081\177\3\2\2\2\u0081\u0082\3\2\2\2\u0082\17\3\2\2\2"+
		"\u0083\u0087\5\24\13\2\u0084\u0085\5\22\n\2\u0085\u0086\5\24\13\2\u0086"+
		"\u0088\3\2\2\2\u0087\u0084\3\2\2\2\u0087\u0088\3\2\2\2\u0088\21\3\2\2"+
		"\2\u0089\u008a\t\2\2\2\u008a\23\3\2\2\2\u008b\u008f\5\34\17\2\u008c\u008d"+
		"\5\26\f\2\u008d\u008e\5\34\17\2\u008e\u0090\3\2\2\2\u008f\u008c\3\2\2"+
		"\2\u008f\u0090\3\2\2\2\u0090\25\3\2\2\2\u0091\u009c\7\64\2\2\u0092\u009c"+
		"\7\67\2\2\u0093\u009c\7:\2\2\u0094\u009c\7=\2\2\u0095\u009c\7?\2\2\u0096"+
		"\u009c\7>\2\2\u0097\u009c\7A\2\2\u0098\u009c\7@\2\2\u0099\u009c\5\30\r"+
		"\2\u009a\u009c\5\32\16\2\u009b\u0091\3\2\2\2\u009b\u0092\3\2\2\2\u009b"+
		"\u0093\3\2\2\2\u009b\u0094\3\2\2\2\u009b\u0095\3\2\2\2\u009b\u0096\3\2"+
		"\2\2\u009b\u0097\3\2\2\2\u009b\u0098\3\2\2\2\u009b\u0099\3\2\2\2\u009b"+
		"\u009a\3\2\2\2\u009c\27\3\2\2\2\u009d\u009e\t\3\2\2\u009e\31\3\2\2\2\u009f"+
		"\u00a0\t\4\2\2\u00a0\33\3\2\2\2\u00a1\u00a5\5 \21\2\u00a2\u00a3\5\36\20"+
		"\2\u00a3\u00a4\5\34\17\2\u00a4\u00a6\3\2\2\2\u00a5\u00a2\3\2\2\2\u00a5"+
		"\u00a6\3\2\2\2\u00a6\35\3\2\2\2\u00a7\u00a8\t\5\2\2\u00a8\37\3\2\2\2\u00a9"+
		"\u00ab\5\"\22\2\u00aa\u00a9\3\2\2\2\u00aa\u00ab\3\2\2\2\u00ab\u00ac\3"+
		"\2\2\2\u00ac\u00ad\5$\23\2\u00ad!\3\2\2\2\u00ae\u00af\t\6\2\2\u00af#\3"+
		"\2\2\2\u00b0\u00b8\5(\25\2\u00b1\u00b8\5\62\32\2\u00b2\u00b8\5\66\34\2"+
		"\u00b3\u00b8\5.\30\2\u00b4\u00b8\5:\36\2\u00b5\u00b8\5\60\31\2\u00b6\u00b8"+
		"\5&\24\2\u00b7\u00b0\3\2\2\2\u00b7\u00b1\3\2\2\2\u00b7\u00b2\3\2\2\2\u00b7"+
		"\u00b3\3\2\2\2\u00b7\u00b4\3\2\2\2\u00b7\u00b5\3\2\2\2\u00b7\u00b6\3\2"+
		"\2\2\u00b8%\3\2\2\2\u00b9\u00ba\7\"\2\2\u00ba\u00bb\5\4\3\2\u00bb\u00bc"+
		"\7#\2\2\u00bc\'\3\2\2\2\u00bd\u00c0\5*\26\2\u00be\u00c0\5,\27\2\u00bf"+
		"\u00bd\3\2\2\2\u00bf\u00be\3\2\2\2\u00c0)\3\2\2\2\u00c1\u00c2\7$\2\2\u00c2"+
		"\u00c3\7%\2\2\u00c3+\3\2\2\2\u00c4\u00c5\7$\2\2\u00c5\u00ca\5\4\3\2\u00c6"+
		"\u00c7\7&\2\2\u00c7\u00c9\5\4\3\2\u00c8\u00c6\3\2\2\2\u00c9\u00cc\3\2"+
		"\2\2\u00ca\u00c8\3\2\2\2\u00ca\u00cb\3\2\2\2\u00cb\u00cd\3\2\2\2\u00cc"+
		"\u00ca\3\2\2\2\u00cd\u00ce\7%\2\2\u00ce-\3\2\2\2\u00cf\u00d0\5b\62\2\u00d0"+
		"/\3\2\2\2\u00d1\u00d2\7X\2\2\u00d2\61\3\2\2\2\u00d3\u00d4\5\64\33\2\u00d4"+
		"\u00dd\7\"\2\2\u00d5\u00da\5\4\3\2\u00d6\u00d7\7&\2\2\u00d7\u00d9\5\4"+
		"\3\2\u00d8\u00d6\3\2\2\2\u00d9\u00dc\3\2\2\2\u00da\u00d8\3\2\2\2\u00da"+
		"\u00db\3\2\2\2\u00db\u00de\3\2\2\2\u00dc\u00da\3\2\2\2\u00dd\u00d5\3\2"+
		"\2\2\u00dd\u00de\3\2\2\2\u00de\u00df\3\2\2\2\u00df\u00e0\7#\2\2\u00e0"+
		"\63\3\2\2\2\u00e1\u00e2\t\7\2\2\u00e2\65\3\2\2\2\u00e3\u00e4\58\35\2\u00e4"+
		"\u00e5\7\"\2\2\u00e5\u00e6\5\4\3\2\u00e6\u00e7\7&\2\2\u00e7\u00e8\5\60"+
		"\31\2\u00e8\u00e9\7&\2\2\u00e9\u00ea\5\4\3\2\u00ea\u00eb\7#\2\2\u00eb"+
		"\67\3\2\2\2\u00ec\u00ed\t\b\2\2\u00ed9\3\2\2\2\u00ee\u00f8\5<\37\2\u00ef"+
		"\u00f8\5> \2\u00f0\u00f8\5@!\2\u00f1\u00f8\5D#\2\u00f2\u00f8\5B\"\2\u00f3"+
		"\u00f8\5T+\2\u00f4\u00f8\5.\30\2\u00f5\u00f8\5^\60\2\u00f6\u00f8\5`\61"+
		"\2\u00f7\u00ee\3\2\2\2\u00f7\u00ef\3\2\2\2\u00f7\u00f0\3\2\2\2\u00f7\u00f1"+
		"\3\2\2\2\u00f7\u00f2\3\2\2\2\u00f7\u00f3\3\2\2\2\u00f7\u00f4\3\2\2\2\u00f7"+
		"\u00f5\3\2\2\2\u00f7\u00f6\3\2\2\2\u00f8;\3\2\2\2\u00f9\u00fa\7\37\2\2"+
		"\u00fa=\3\2\2\2\u00fb\u00fc\t\t\2\2\u00fc?\3\2\2\2\u00fd\u0101\7 \2\2"+
		"\u00fe\u00ff\7C\2\2\u00ff\u0101\7 \2\2\u0100\u00fd\3\2\2\2\u0100\u00fe"+
		"\3\2\2\2\u0101A\3\2\2\2\u0102\u0103\5F$\2\u0103\u0104\7\33\2\2\u0104\u0105"+
		"\5H%\2\u0105\u0106\7\33\2\2\u0106\u0107\5L\'\2\u0107\u0108\7\33\2\2\u0108"+
		"\u0109\5N(\2\u0109\u010a\7\33\2\2\u010a\u010b\5P)\2\u010b\u010c\7\33\2"+
		"\2\u010c\u010d\5R*\2\u010dC\3\2\2\2\u010e\u010f\5F$\2\u010f\u0110\7\33"+
		"\2\2\u0110\u0111\5H%\2\u0111\u0112\7\33\2\2\u0112\u0113\5L\'\2\u0113E"+
		"\3\2\2\2\u0114\u0117\5@!\2\u0115\u0117\7(\2\2\u0116\u0114\3\2\2\2\u0116"+
		"\u0115\3\2\2\2\u0117G\3\2\2\2\u0118\u011c\5@!\2\u0119\u011c\7(\2\2\u011a"+
		"\u011c\5J&\2\u011b\u0118\3\2\2\2\u011b\u0119\3\2\2\2\u011b\u011a\3\2\2"+
		"\2\u011cI\3\2\2\2\u011d\u011e\t\n\2\2\u011eK\3\2\2\2\u011f\u0122\5@!\2"+
		"\u0120\u0122\7(\2\2\u0121\u011f\3\2\2\2\u0121\u0120\3\2\2\2\u0122M\3\2"+
		"\2\2\u0123\u0124\5@!\2\u0124O\3\2\2\2\u0125\u0126\5@!\2\u0126Q\3\2\2\2"+
		"\u0127\u0128\5@!\2\u0128S\3\2\2\2\u0129\u012a\7\34\2\2\u012a\u012b\5V"+
		",\2\u012b\u012c\7\33\2\2\u012c\u012d\5X-\2\u012d\u012e\7\33\2\2\u012e"+
		"\u012f\5Z.\2\u012fU\3\2\2\2\u0130\u0133\5\\/\2\u0131\u0133\7(\2\2\u0132"+
		"\u0130\3\2\2\2\u0132\u0131\3\2\2\2\u0133W\3\2\2\2\u0134\u0137\5\\/\2\u0135"+
		"\u0137\7(\2\2\u0136\u0134\3\2\2\2\u0136\u0135\3\2\2\2\u0137Y\3\2\2\2\u0138"+
		"\u013b\5\\/\2\u0139\u013b\7(\2\2\u013a\u0138\3\2\2\2\u013a\u0139\3\2\2"+
		"\2\u013b[\3\2\2\2\u013c\u013f\7C\2\2\u013d\u013f\3\2\2\2\u013e\u013c\3"+
		"\2\2\2\u013e\u013d\3\2\2\2\u013f\u0140\3\2\2\2\u0140\u0141\5@!\2\u0141"+
		"]\3\2\2\2\u0142\u0143\7\35\2\2\u0143\u0144\5b\62\2\u0144\u0145\7\36\2"+
		"\2\u0145_\3\2\2\2\u0146\u0147\7K\2\2\u0147a\3\2\2\2\u0148\u0151\7X\2\2"+
		"\u0149\u014c\7X\2\2\u014a\u014b\7\'\2\2\u014b\u014d\7X\2\2\u014c\u014a"+
		"\3\2\2\2\u014d\u014e\3\2\2\2\u014e\u014c\3\2\2\2\u014e\u014f\3\2\2\2\u014f"+
		"\u0151\3\2\2\2\u0150\u0148\3\2\2\2\u0150\u0149\3\2\2\2\u0151c\3\2\2\2"+
		"\33jr|\u0081\u0087\u008f\u009b\u00a5\u00aa\u00b7\u00bf\u00ca\u00da\u00dd"+
		"\u00f7\u0100\u0116\u011b\u0121\u0132\u0136\u013a\u013e\u014e\u0150";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}