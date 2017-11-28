/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

// Generated from BRPExpressietaal.g4 by ANTLR 4.7

package nl.bzk.brp.domain.expressie.parser.antlr;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class BRPExpressietaalLexer extends Lexer {
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
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
		"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
		"T__17", "T__18", "T__19", "T__20", "T__21", "T__22", "T__23", "T__24", 
		"T__25", "T__26", "T__27", "STRING", "INTEGER", "WS", "LP", "RP", "LL", 
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


	public BRPExpressietaalLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "BRPExpressietaal.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2X\u029f\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\4D\tD\4E\tE\4F\tF\4G\tG\4H\tH\4I"+
		"\tI\4J\tJ\4K\tK\4L\tL\4M\tM\4N\tN\4O\tO\4P\tP\4Q\tQ\4R\tR\4S\tS\4T\tT"+
		"\4U\tU\4V\tV\4W\tW\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3"+
		"\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3"+
		"\6\3\6\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\n\3"+
		"\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\21\3\21\3\21"+
		"\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\26\3\26"+
		"\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\30"+
		"\3\30\3\30\3\31\3\31\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3\35"+
		"\3\36\3\36\3\36\7\36\u0169\n\36\f\36\16\36\u016c\13\36\3\36\3\36\3\37"+
		"\6\37\u0171\n\37\r\37\16\37\u0172\3 \6 \u0176\n \r \16 \u0177\3 \3 \3"+
		"!\3!\3\"\3\"\3#\3#\3$\3$\3%\3%\3&\3&\3\'\3\'\3(\3(\3(\3)\3)\3)\3*\3*\3"+
		"+\3+\3+\3+\3,\3,\3,\3,\3-\3-\3-\3.\3.\3.\3.\3/\3/\3/\3/\3\60\3\60\3\60"+
		"\3\61\3\61\3\61\3\62\3\62\3\62\3\63\3\63\3\64\3\64\3\64\3\65\3\65\3\65"+
		"\3\66\3\66\3\67\3\67\3\67\3\67\38\38\38\38\39\39\39\3:\3:\3:\3:\3;\3;"+
		"\3;\3;\3<\3<\3<\3=\3=\3=\3=\3=\3>\3>\3>\3>\3?\3?\3?\3?\3?\3@\3@\3@\3@"+
		"\3A\3A\3B\3B\3C\3C\3C\3D\3D\3D\3E\3E\3E\3E\3E\3F\3F\3G\3G\3G\3G\3G\3G"+
		"\3G\3G\3H\3H\3H\3H\3H\3H\3H\3H\5H\u0201\nH\3I\3I\3I\3I\3I\3I\3I\3I\3I"+
		"\3I\3I\5I\u020e\nI\3J\3J\3J\3J\3J\3K\3K\3K\3K\3K\3K\3K\3K\3K\3K\5K\u021f"+
		"\nK\3L\3L\3L\3L\3L\3L\3L\3L\3L\3L\3L\5L\u022c\nL\3M\3M\3M\3M\3M\3M\3M"+
		"\3M\5M\u0236\nM\3N\3N\3N\3N\3N\3N\3N\3N\5N\u0240\nN\3O\3O\3O\3O\3P\3P"+
		"\3P\3P\3P\3P\3P\5P\u024d\nP\3Q\3Q\3Q\3Q\3Q\3Q\3Q\5Q\u0256\nQ\3R\3R\3R"+
		"\3R\3R\3R\3R\3R\3R\3R\3R\5R\u0263\nR\3S\3S\3S\3S\3S\3S\3S\3S\3S\3S\3S"+
		"\3S\5S\u0271\nS\3T\3T\3T\3T\3T\3T\3T\3T\3T\3T\5T\u027d\nT\3U\3U\3U\3U"+
		"\3U\3U\3U\3U\3U\3U\3U\5U\u028a\nU\3V\3V\3V\3V\3V\3V\3V\3V\3V\3V\3V\5V"+
		"\u0297\nV\3W\3W\7W\u029b\nW\fW\16W\u029e\13W\3\u016a\2X\3\3\5\4\7\5\t"+
		"\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23"+
		"%\24\'\25)\26+\27-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G"+
		"%I&K\'M(O)Q*S+U,W-Y.[/]\60_\61a\62c\63e\64g\65i\66k\67m8o9q:s;u<w=y>{"+
		"?}@\177A\u0081B\u0083C\u0085D\u0087E\u0089F\u008bG\u008dH\u008fI\u0091"+
		"J\u0093K\u0095L\u0097M\u0099N\u009bO\u009dP\u009fQ\u00a1R\u00a3S\u00a5"+
		"T\u00a7U\u00a9V\u00abW\u00adX\3\2\6\3\2\62;\5\2\13\f\17\17\"\"\4\2C\\"+
		"c|\6\2\62;C\\aac|\2\u02b0\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2"+
		"\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2"+
		"\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3"+
		"\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2"+
		"\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67"+
		"\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2"+
		"\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2"+
		"\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]"+
		"\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2"+
		"\2\2\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2u\3\2\2\2"+
		"\2w\3\2\2\2\2y\3\2\2\2\2{\3\2\2\2\2}\3\2\2\2\2\177\3\2\2\2\2\u0081\3\2"+
		"\2\2\2\u0083\3\2\2\2\2\u0085\3\2\2\2\2\u0087\3\2\2\2\2\u0089\3\2\2\2\2"+
		"\u008b\3\2\2\2\2\u008d\3\2\2\2\2\u008f\3\2\2\2\2\u0091\3\2\2\2\2\u0093"+
		"\3\2\2\2\2\u0095\3\2\2\2\2\u0097\3\2\2\2\2\u0099\3\2\2\2\2\u009b\3\2\2"+
		"\2\2\u009d\3\2\2\2\2\u009f\3\2\2\2\2\u00a1\3\2\2\2\2\u00a3\3\2\2\2\2\u00a5"+
		"\3\2\2\2\2\u00a7\3\2\2\2\2\u00a9\3\2\2\2\2\u00ab\3\2\2\2\2\u00ad\3\2\2"+
		"\2\3\u00af\3\2\2\2\5\u00b7\3\2\2\2\7\u00bd\3\2\2\2\t\u00c5\3\2\2\2\13"+
		"\u00c9\3\2\2\2\r\u00cf\3\2\2\2\17\u00d4\3\2\2\2\21\u00e1\3\2\2\2\23\u00ed"+
		"\3\2\2\2\25\u00f4\3\2\2\2\27\u00f8\3\2\2\2\31\u00fd\3\2\2\2\33\u010a\3"+
		"\2\2\2\35\u010f\3\2\2\2\37\u0119\3\2\2\2!\u011c\3\2\2\2#\u0120\3\2\2\2"+
		"%\u0126\3\2\2\2\'\u0129\3\2\2\2)\u0138\3\2\2\2+\u0147\3\2\2\2-\u014d\3"+
		"\2\2\2/\u0152\3\2\2\2\61\u0159\3\2\2\2\63\u015d\3\2\2\2\65\u015f\3\2\2"+
		"\2\67\u0161\3\2\2\29\u0163\3\2\2\2;\u0165\3\2\2\2=\u0170\3\2\2\2?\u0175"+
		"\3\2\2\2A\u017b\3\2\2\2C\u017d\3\2\2\2E\u017f\3\2\2\2G\u0181\3\2\2\2I"+
		"\u0183\3\2\2\2K\u0185\3\2\2\2M\u0187\3\2\2\2O\u0189\3\2\2\2Q\u018c\3\2"+
		"\2\2S\u018f\3\2\2\2U\u0191\3\2\2\2W\u0195\3\2\2\2Y\u0199\3\2\2\2[\u019c"+
		"\3\2\2\2]\u01a0\3\2\2\2_\u01a4\3\2\2\2a\u01a7\3\2\2\2c\u01aa\3\2\2\2e"+
		"\u01ad\3\2\2\2g\u01af\3\2\2\2i\u01b2\3\2\2\2k\u01b5\3\2\2\2m\u01b7\3\2"+
		"\2\2o\u01bb\3\2\2\2q\u01bf\3\2\2\2s\u01c2\3\2\2\2u\u01c6\3\2\2\2w\u01ca"+
		"\3\2\2\2y\u01cd\3\2\2\2{\u01d2\3\2\2\2}\u01d6\3\2\2\2\177\u01db\3\2\2"+
		"\2\u0081\u01df\3\2\2\2\u0083\u01e1\3\2\2\2\u0085\u01e3\3\2\2\2\u0087\u01e6"+
		"\3\2\2\2\u0089\u01e9\3\2\2\2\u008b\u01ee\3\2\2\2\u008d\u01f0\3\2\2\2\u008f"+
		"\u0200\3\2\2\2\u0091\u020d\3\2\2\2\u0093\u020f\3\2\2\2\u0095\u021e\3\2"+
		"\2\2\u0097\u022b\3\2\2\2\u0099\u0235\3\2\2\2\u009b\u023f\3\2\2\2\u009d"+
		"\u0241\3\2\2\2\u009f\u024c\3\2\2\2\u00a1\u0255\3\2\2\2\u00a3\u0262\3\2"+
		"\2\2\u00a5\u0270\3\2\2\2\u00a7\u027c\3\2\2\2\u00a9\u0289\3\2\2\2\u00ab"+
		"\u0296\3\2\2\2\u00ad\u0298\3\2\2\2\u00af\u00b0\7K\2\2\u00b0\u00b1\7U\2"+
		"\2\u00b1\u00b2\7a\2\2\u00b2\u00b3\7P\2\2\u00b3\u00b4\7W\2\2\u00b4\u00b5"+
		"\7N\2\2\u00b5\u00b6\7N\2\2\u00b6\4\3\2\2\2\u00b7\u00b8\7F\2\2\u00b8\u00b9"+
		"\7C\2\2\u00b9\u00ba\7V\2\2\u00ba\u00bb\7W\2\2\u00bb\u00bc\7O\2\2\u00bc"+
		"\6\3\2\2\2\u00bd\u00be\7X\2\2\u00be\u00bf\7C\2\2\u00bf\u00c0\7P\2\2\u00c0"+
		"\u00c1\7F\2\2\u00c1\u00c2\7C\2\2\u00c2\u00c3\7C\2\2\u00c3\u00c4\7I\2\2"+
		"\u00c4\b\3\2\2\2\u00c5\u00c6\7F\2\2\u00c6\u00c7\7C\2\2\u00c7\u00c8\7I"+
		"\2\2\u00c8\n\3\2\2\2\u00c9\u00ca\7O\2\2\u00ca\u00cb\7C\2\2\u00cb\u00cc"+
		"\7C\2\2\u00cc\u00cd\7P\2\2\u00cd\u00ce\7F\2\2\u00ce\f\3\2\2\2\u00cf\u00d0"+
		"\7L\2\2\u00d0\u00d1\7C\2\2\u00d1\u00d2\7C\2\2\u00d2\u00d3\7T\2\2\u00d3"+
		"\16\3\2\2\2\u00d4\u00d5\7C\2\2\u00d5\u00d6\7C\2\2\u00d6\u00d7\7P\2\2\u00d7"+
		"\u00d8\7V\2\2\u00d8\u00d9\7C\2\2\u00d9\u00da\7N\2\2\u00da\u00db\7a\2\2"+
		"\u00db\u00dc\7F\2\2\u00dc\u00dd\7C\2\2\u00dd\u00de\7I\2\2\u00de\u00df"+
		"\7G\2\2\u00df\u00e0\7P\2\2\u00e0\20\3\2\2\2\u00e1\u00e2\7N\2\2\u00e2\u00e3"+
		"\7C\2\2\u00e3\u00e4\7C\2\2\u00e4\u00e5\7V\2\2\u00e5\u00e6\7U\2\2\u00e6"+
		"\u00e7\7V\2\2\u00e7\u00e8\7G\2\2\u00e8\u00e9\7a\2\2\u00e9\u00ea\7F\2\2"+
		"\u00ea\u00eb\7C\2\2\u00eb\u00ec\7I\2\2\u00ec\22\3\2\2\2\u00ed\u00ee\7"+
		"C\2\2\u00ee\u00ef\7C\2\2\u00ef\u00f0\7P\2\2\u00f0\u00f1\7V\2\2\u00f1\u00f2"+
		"\7C\2\2\u00f2\u00f3\7N\2\2\u00f3\24\3\2\2\2\u00f4\u00f5\7C\2\2\u00f5\u00f6"+
		"\7N\2\2\u00f6\u00f7\7U\2\2\u00f7\26\3\2\2\2\u00f8\u00f9\7J\2\2\u00f9\u00fa"+
		"\7K\2\2\u00fa\u00fb\7U\2\2\u00fb\u00fc\7O\2\2\u00fc\30\3\2\2\2\u00fd\u00fe"+
		"\7J\2\2\u00fe\u00ff\7K\2\2\u00ff\u0100\7U\2\2\u0100\u0101\7O\2\2\u0101"+
		"\u0102\7a\2\2\u0102\u0103\7N\2\2\u0103\u0104\7C\2\2\u0104\u0105\7C\2\2"+
		"\u0105\u0106\7V\2\2\u0106\u0107\7U\2\2\u0107\u0108\7V\2\2\u0108\u0109"+
		"\7G\2\2\u0109\32\3\2\2\2\u010a\u010b\7J\2\2\u010b\u010c\7K\2\2\u010c\u010d"+
		"\7U\2\2\u010d\u010e\7H\2\2\u010e\34\3\2\2\2\u010f\u0110\7I\2\2\u0110\u0111"+
		"\7G\2\2\u0111\u0112\7Y\2\2\u0112\u0113\7K\2\2\u0113\u0114\7L\2\2\u0114"+
		"\u0115\7\\\2\2\u0115\u0116\7K\2\2\u0116\u0117\7I\2\2\u0117\u0118\7F\2"+
		"\2\u0118\36\3\2\2\2\u0119\u011a\7M\2\2\u011a\u011b\7X\2\2\u011b \3\2\2"+
		"\2\u011c\u011d\7M\2\2\u011d\u011e\7P\2\2\u011e\u011f\7X\2\2\u011f\"\3"+
		"\2\2\2\u0120\u0121\7C\2\2\u0121\u0122\7E\2\2\u0122\u0123\7V\2\2\u0123"+
		"\u0124\7K\2\2\u0124\u0125\7G\2\2\u0125$\3\2\2\2\u0126\u0127\7C\2\2\u0127"+
		"\u0128\7J\2\2\u0128&\3\2\2\2\u0129\u012a\7U\2\2\u012a\u012b\7G\2\2\u012b"+
		"\u012c\7N\2\2\u012c\u012d\7G\2\2\u012d\u012e\7E\2\2\u012e\u012f\7V\2\2"+
		"\u012f\u0130\7K\2\2\u0130\u0131\7G\2\2\u0131\u0132\7a\2\2\u0132\u0133"+
		"\7F\2\2\u0133\u0134\7C\2\2\u0134\u0135\7V\2\2\u0135\u0136\7W\2\2\u0136"+
		"\u0137\7O\2\2\u0137(\3\2\2\2\u0138\u0139\7U\2\2\u0139\u013a\7G\2\2\u013a"+
		"\u013b\7N\2\2\u013b\u013c\7G\2\2\u013c\u013d\7E\2\2\u013d\u013e\7V\2\2"+
		"\u013e\u013f\7K\2\2\u013f\u0140\7G\2\2\u0140\u0141\7a\2\2\u0141\u0142"+
		"\7N\2\2\u0142\u0143\7K\2\2\u0143\u0144\7L\2\2\u0144\u0145\7U\2\2\u0145"+
		"\u0146\7V\2\2\u0146*\3\2\2\2\u0147\u0148\7G\2\2\u0148\u0149\7T\2\2\u0149"+
		"\u014a\7a\2\2\u014a\u014b\7K\2\2\u014b\u014c\7U\2\2\u014c,\3\2\2\2\u014d"+
		"\u014e\7C\2\2\u014e\u014f\7N\2\2\u014f\u0150\7N\2\2\u0150\u0151\7G\2\2"+
		"\u0151.\3\2\2\2\u0152\u0153\7H\2\2\u0153\u0154\7K\2\2\u0154\u0155\7N\2"+
		"\2\u0155\u0156\7V\2\2\u0156\u0157\7G\2\2\u0157\u0158\7T\2\2\u0158\60\3"+
		"\2\2\2\u0159\u015a\7O\2\2\u015a\u015b\7C\2\2\u015b\u015c\7R\2\2\u015c"+
		"\62\3\2\2\2\u015d\u015e\7\61\2\2\u015e\64\3\2\2\2\u015f\u0160\7`\2\2\u0160"+
		"\66\3\2\2\2\u0161\u0162\7]\2\2\u01628\3\2\2\2\u0163\u0164\7_\2\2\u0164"+
		":\3\2\2\2\u0165\u016a\7$\2\2\u0166\u0169\13\2\2\2\u0167\u0169\7\"\2\2"+
		"\u0168\u0166\3\2\2\2\u0168\u0167\3\2\2\2\u0169\u016c\3\2\2\2\u016a\u016b"+
		"\3\2\2\2\u016a\u0168\3\2\2\2\u016b\u016d\3\2\2\2\u016c\u016a\3\2\2\2\u016d"+
		"\u016e\7$\2\2\u016e<\3\2\2\2\u016f\u0171\t\2\2\2\u0170\u016f\3\2\2\2\u0171"+
		"\u0172\3\2\2\2\u0172\u0170\3\2\2\2\u0172\u0173\3\2\2\2\u0173>\3\2\2\2"+
		"\u0174\u0176\t\3\2\2\u0175\u0174\3\2\2\2\u0176\u0177\3\2\2\2\u0177\u0175"+
		"\3\2\2\2\u0177\u0178\3\2\2\2\u0178\u0179\3\2\2\2\u0179\u017a\b \2\2\u017a"+
		"@\3\2\2\2\u017b\u017c\7*\2\2\u017cB\3\2\2\2\u017d\u017e\7+\2\2\u017eD"+
		"\3\2\2\2\u017f\u0180\7}\2\2\u0180F\3\2\2\2\u0181\u0182\7\177\2\2\u0182"+
		"H\3\2\2\2\u0183\u0184\7.\2\2\u0184J\3\2\2\2\u0185\u0186\7\60\2\2\u0186"+
		"L\3\2\2\2\u0187\u0188\7A\2\2\u0188N\3\2\2\2\u0189\u018a\7G\2\2\u018a\u018b"+
		"\7?\2\2\u018bP\3\2\2\2\u018c\u018d\7C\2\2\u018d\u018e\7?\2\2\u018eR\3"+
		"\2\2\2\u018f\u0190\7?\2\2\u0190T\3\2\2\2\u0191\u0192\7G\2\2\u0192\u0193"+
		"\7>\2\2\u0193\u0194\7@\2\2\u0194V\3\2\2\2\u0195\u0196\7C\2\2\u0196\u0197"+
		"\7>\2\2\u0197\u0198\7@\2\2\u0198X\3\2\2\2\u0199\u019a\7>\2\2\u019a\u019b"+
		"\7@\2\2\u019bZ\3\2\2\2\u019c\u019d\7G\2\2\u019d\u019e\7?\2\2\u019e\u019f"+
		"\7\'\2\2\u019f\\\3\2\2\2\u01a0\u01a1\7C\2\2\u01a1\u01a2\7?\2\2\u01a2\u01a3"+
		"\7\'\2\2\u01a3^\3\2\2\2\u01a4\u01a5\7?\2\2\u01a5\u01a6\7\'\2\2\u01a6`"+
		"\3\2\2\2\u01a7\u01a8\7G\2\2\u01a8\u01a9\7>\2\2\u01a9b\3\2\2\2\u01aa\u01ab"+
		"\7C\2\2\u01ab\u01ac\7>\2\2\u01acd\3\2\2\2\u01ad\u01ae\7>\2\2\u01aef\3"+
		"\2\2\2\u01af\u01b0\7G\2\2\u01b0\u01b1\7@\2\2\u01b1h\3\2\2\2\u01b2\u01b3"+
		"\7C\2\2\u01b3\u01b4\7@\2\2\u01b4j\3\2\2\2\u01b5\u01b6\7@\2\2\u01b6l\3"+
		"\2\2\2\u01b7\u01b8\7G\2\2\u01b8\u01b9\7>\2\2\u01b9\u01ba\7?\2\2\u01ba"+
		"n\3\2\2\2\u01bb\u01bc\7C\2\2\u01bc\u01bd\7>\2\2\u01bd\u01be\7?\2\2\u01be"+
		"p\3\2\2\2\u01bf\u01c0\7>\2\2\u01c0\u01c1\7?\2\2\u01c1r\3\2\2\2\u01c2\u01c3"+
		"\7G\2\2\u01c3\u01c4\7@\2\2\u01c4\u01c5\7?\2\2\u01c5t\3\2\2\2\u01c6\u01c7"+
		"\7C\2\2\u01c7\u01c8\7@\2\2\u01c8\u01c9\7?\2\2\u01c9v\3\2\2\2\u01ca\u01cb"+
		"\7@\2\2\u01cb\u01cc\7?\2\2\u01ccx\3\2\2\2\u01cd\u01ce\7C\2\2\u01ce\u01cf"+
		"\7K\2\2\u01cf\u01d0\7P\2\2\u01d0\u01d1\7\'\2\2\u01d1z\3\2\2\2\u01d2\u01d3"+
		"\7C\2\2\u01d3\u01d4\7K\2\2\u01d4\u01d5\7P\2\2\u01d5|\3\2\2\2\u01d6\u01d7"+
		"\7G\2\2\u01d7\u01d8\7K\2\2\u01d8\u01d9\7P\2\2\u01d9\u01da\7\'\2\2\u01da"+
		"~\3\2\2\2\u01db\u01dc\7G\2\2\u01dc\u01dd\7K\2\2\u01dd\u01de\7P\2\2\u01de"+
		"\u0080\3\2\2\2\u01df\u01e0\7-\2\2\u01e0\u0082\3\2\2\2\u01e1\u01e2\7/\2"+
		"\2\u01e2\u0084\3\2\2\2\u01e3\u01e4\7Q\2\2\u01e4\u01e5\7H\2\2\u01e5\u0086"+
		"\3\2\2\2\u01e6\u01e7\7G\2\2\u01e7\u01e8\7P\2\2\u01e8\u0088\3\2\2\2\u01e9"+
		"\u01ea\7P\2\2\u01ea\u01eb\7K\2\2\u01eb\u01ec\7G\2\2\u01ec\u01ed\7V\2\2"+
		"\u01ed\u008a\3\2\2\2\u01ee\u01ef\7&\2\2\u01ef\u008c\3\2\2\2\u01f0\u01f1"+
		"\7Y\2\2\u01f1\u01f2\7C\2\2\u01f2\u01f3\7C\2\2\u01f3\u01f4\7T\2\2\u01f4"+
		"\u01f5\7D\2\2\u01f5\u01f6\7K\2\2\u01f6\u01f7\7L\2\2\u01f7\u008e\3\2\2"+
		"\2\u01f8\u01f9\7Y\2\2\u01f9\u01fa\7C\2\2\u01fa\u01fb\7C\2\2\u01fb\u0201"+
		"\7T\2\2\u01fc\u01fd\7V\2\2\u01fd\u01fe\7T\2\2\u01fe\u01ff\7W\2\2\u01ff"+
		"\u0201\7G\2\2\u0200\u01f8\3\2\2\2\u0200\u01fc\3\2\2\2\u0201\u0090\3\2"+
		"\2\2\u0202\u0203\7Q\2\2\u0203\u0204\7P\2\2\u0204\u0205\7Y\2\2\u0205\u0206"+
		"\7C\2\2\u0206\u0207\7C\2\2\u0207\u020e\7T\2\2\u0208\u0209\7H\2\2\u0209"+
		"\u020a\7C\2\2\u020a\u020b\7N\2\2\u020b\u020c\7U\2\2\u020c\u020e\7G\2\2"+
		"\u020d\u0202\3\2\2\2\u020d\u0208\3\2\2\2\u020e\u0092\3\2\2\2\u020f\u0210"+
		"\7P\2\2\u0210\u0211\7W\2\2\u0211\u0212\7N\2\2\u0212\u0213\7N\2\2\u0213"+
		"\u0094\3\2\2\2\u0214\u0215\7L\2\2\u0215\u0216\7C\2\2\u0216\u021f\7P\2"+
		"\2\u0217\u0218\7L\2\2\u0218\u0219\7C\2\2\u0219\u021a\7P\2\2\u021a\u021b"+
		"\7W\2\2\u021b\u021c\7C\2\2\u021c\u021d\7T\2\2\u021d\u021f\7K\2\2\u021e"+
		"\u0214\3\2\2\2\u021e\u0217\3\2\2\2\u021f\u0096\3\2\2\2\u0220\u0221\7H"+
		"\2\2\u0221\u0222\7G\2\2\u0222\u022c\7D\2\2\u0223\u0224\7H\2\2\u0224\u0225"+
		"\7G\2\2\u0225\u0226\7D\2\2\u0226\u0227\7T\2\2\u0227\u0228\7W\2\2\u0228"+
		"\u0229\7C\2\2\u0229\u022a\7T\2\2\u022a\u022c\7K\2\2\u022b\u0220\3\2\2"+
		"\2\u022b\u0223\3\2\2\2\u022c\u0098\3\2\2\2\u022d\u022e\7O\2\2\u022e\u022f"+
		"\7T\2\2\u022f\u0236\7V\2\2\u0230\u0231\7O\2\2\u0231\u0232\7C\2\2\u0232"+
		"\u0233\7C\2\2\u0233\u0234\7T\2\2\u0234\u0236\7V\2\2\u0235\u022d\3\2\2"+
		"\2\u0235\u0230\3\2\2\2\u0236\u009a\3\2\2\2\u0237\u0238\7C\2\2\u0238\u0239"+
		"\7R\2\2\u0239\u0240\7T\2\2\u023a\u023b\7C\2\2\u023b\u023c\7R\2\2\u023c"+
		"\u023d\7T\2\2\u023d\u023e\7K\2\2\u023e\u0240\7N\2\2\u023f\u0237\3\2\2"+
		"\2\u023f\u023a\3\2\2\2\u0240\u009c\3\2\2\2\u0241\u0242\7O\2\2\u0242\u0243"+
		"\7G\2\2\u0243\u0244\7K\2\2\u0244\u009e\3\2\2\2\u0245\u0246\7L\2\2\u0246"+
		"\u0247\7W\2\2\u0247\u0248\7P\2\2\u0248\u024d\7K\2\2\u0249\u024a\7L\2\2"+
		"\u024a\u024b\7W\2\2\u024b\u024d\7P\2\2\u024c\u0245\3\2\2\2\u024c\u0249"+
		"\3\2\2\2\u024d\u00a0\3\2\2\2\u024e\u024f\7L\2\2\u024f\u0250\7W\2\2\u0250"+
		"\u0251\7N\2\2\u0251\u0256\7K\2\2\u0252\u0253\7L\2\2\u0253\u0254\7W\2\2"+
		"\u0254\u0256\7N\2\2\u0255\u024e\3\2\2\2\u0255\u0252\3\2\2\2\u0256\u00a2"+
		"\3\2\2\2\u0257\u0258\7C\2\2\u0258\u0259\7W\2\2\u0259\u025a\7I\2\2\u025a"+
		"\u025b\7W\2\2\u025b\u025c\7U\2\2\u025c\u025d\7V\2\2\u025d\u025e\7W\2\2"+
		"\u025e\u0263\7U\2\2\u025f\u0260\7C\2\2\u0260\u0261\7W\2\2\u0261\u0263"+
		"\7I\2\2\u0262\u0257\3\2\2\2\u0262\u025f\3\2\2\2\u0263\u00a4\3\2\2\2\u0264"+
		"\u0265\7U\2\2\u0265\u0266\7G\2\2\u0266\u0267\7R\2\2\u0267\u0268\7V\2\2"+
		"\u0268\u0269\7G\2\2\u0269\u026a\7O\2\2\u026a\u026b\7D\2\2\u026b\u026c"+
		"\7G\2\2\u026c\u0271\7T\2\2\u026d\u026e\7U\2\2\u026e\u026f\7G\2\2\u026f"+
		"\u0271\7R\2\2\u0270\u0264\3\2\2\2\u0270\u026d\3\2\2\2\u0271\u00a6\3\2"+
		"\2\2\u0272\u0273\7Q\2\2\u0273\u0274\7M\2\2\u0274\u0275\7V\2\2\u0275\u0276"+
		"\7Q\2\2\u0276\u0277\7D\2\2\u0277\u0278\7G\2\2\u0278\u027d\7T\2\2\u0279"+
		"\u027a\7Q\2\2\u027a\u027b\7M\2\2\u027b\u027d\7V\2\2\u027c\u0272\3\2\2"+
		"\2\u027c\u0279\3\2\2\2\u027d\u00a8\3\2\2\2\u027e\u027f\7P\2\2\u027f\u0280"+
		"\7Q\2\2\u0280\u0281\7X\2\2\u0281\u0282\7G\2\2\u0282\u0283\7O\2\2\u0283"+
		"\u0284\7D\2\2\u0284\u0285\7G\2\2\u0285\u028a\7T\2\2\u0286\u0287\7P\2\2"+
		"\u0287\u0288\7Q\2\2\u0288\u028a\7X\2\2\u0289\u027e\3\2\2\2\u0289\u0286"+
		"\3\2\2\2\u028a\u00aa\3\2\2\2\u028b\u028c\7F\2\2\u028c\u028d\7G\2\2\u028d"+
		"\u028e\7E\2\2\u028e\u028f\7G\2\2\u028f\u0290\7O\2\2\u0290\u0291\7D\2\2"+
		"\u0291\u0292\7G\2\2\u0292\u0297\7T\2\2\u0293\u0294\7F\2\2\u0294\u0295"+
		"\7G\2\2\u0295\u0297\7E\2\2\u0296\u028b\3\2\2\2\u0296\u0293\3\2\2\2\u0297"+
		"\u00ac\3\2\2\2\u0298\u029c\t\4\2\2\u0299\u029b\t\5\2\2\u029a\u0299\3\2"+
		"\2\2\u029b\u029e\3\2\2\2\u029c\u029a\3\2\2\2\u029c\u029d\3\2\2\2\u029d"+
		"\u00ae\3\2\2\2\u029e\u029c\3\2\2\2\25\2\u0168\u016a\u0172\u0177\u0200"+
		"\u020d\u021e\u022b\u0235\u023f\u024c\u0255\u0262\u0270\u027c\u0289\u0296"+
		"\u029c\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}