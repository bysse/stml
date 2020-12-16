// Generated from /home/erikb/dev/projects/stml/src/main/antlr/STMLLexer.g4 by ANTLR 4.8
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class STMLLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		BooleanConstant=1, Separator=2, ANY=3, BOOLEAN=4, LIST=5, MAP=6, NUMBER=7, 
		STRING=8, Emit=9, Enum=10, External=11, Extends=12, Let=13, InlineImport=14, 
		Struct=15, TypeLeft=16, TypeRight=17, COLON=18, TRUE=19, FALSE=20, COMMA=21, 
		SEMICOLON=22, LeftParen=23, RightParen=24, LeftBracket=25, RightBracket=26, 
		LeftBrace=27, RightBrace=28, OR=29, DOT=30, EQUAL=31, IDENTIFIER=32, IntegerConstant=33, 
		FractionalConstant=34, DigitSequence=35, DecimalConstant=36, StringConstant=37, 
		Whitespace=38, NEWLINE_SKIP=39, BlockComment=40, LineComment=41, NEWLINE=42;
	public static final int
		NL_MATTERS=1;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE", "NL_MATTERS"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"BooleanConstant", "Separator", "ANY", "BOOLEAN", "LIST", "MAP", "NUMBER", 
			"STRING", "Emit", "Enum", "External", "Extends", "Let", "InlineImport", 
			"Struct", "TypeLeft", "TypeRight", "COLON", "TRUE", "FALSE", "COMMA", 
			"SEMICOLON", "LeftParen", "RightParen", "LeftBracket", "RightBracket", 
			"LeftBrace", "RightBrace", "OR", "DOT", "EQUAL", "IDENTIFIER", "Nondigit", 
			"NonZeroDigit", "Digit", "IntegerConstant", "FractionalConstant", "Sign", 
			"DigitSequence", "DecimalConstant", "HexadecimalConstant", "ESCAPED_QUOTE", 
			"StringConstant", "Whitespace", "NEWLINE_SKIP", "BlockComment", "LineComment", 
			"NEWLINE"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, "'emit'", "'enum'", 
			"'ext'", "'extends'", "'let'", "'$import'", "'struct'", "'<'", "'>'", 
			"':'", null, null, "','", "';'", "'('", "')'", "'['", "']'", "'{'", "'}'", 
			"'|'", "'.'", "'='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "BooleanConstant", "Separator", "ANY", "BOOLEAN", "LIST", "MAP", 
			"NUMBER", "STRING", "Emit", "Enum", "External", "Extends", "Let", "InlineImport", 
			"Struct", "TypeLeft", "TypeRight", "COLON", "TRUE", "FALSE", "COMMA", 
			"SEMICOLON", "LeftParen", "RightParen", "LeftBracket", "RightBracket", 
			"LeftBrace", "RightBrace", "OR", "DOT", "EQUAL", "IDENTIFIER", "IntegerConstant", 
			"FractionalConstant", "DigitSequence", "DecimalConstant", "StringConstant", 
			"Whitespace", "NEWLINE_SKIP", "BlockComment", "LineComment", "NEWLINE"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
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


	public STMLLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "STMLLexer.g4"; }

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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2,\u019d\b\1\b\1\4"+
		"\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n"+
		"\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t"+
		"+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\3\2\3\2\5\2g\n\2\3\3\3\3"+
		"\3\3\5\3l\n\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4u\n\4\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5"+
		"\5\u008d\n\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\3\6\5\6\u0097\n\6\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\5\7\u009f\n\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\b"+
		"\3\b\5\b\u00ad\n\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\t"+
		"\u00bb\n\t\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3"+
		"\f\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\17\3\17\3\17"+
		"\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21"+
		"\3\22\3\22\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\3\24\3\24\3\24\5\24\u00fe\n\24\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\3\25\5\25\u0114\n\25\3\26\3\26\3\27\3\27\3\30\3\30\3\31\3\31"+
		"\3\32\3\32\3\33\3\33\3\34\3\34\3\34\3\34\3\35\3\35\3\35\3\35\3\36\3\36"+
		"\3\37\3\37\3 \3 \3!\3!\3!\7!\u0133\n!\f!\16!\u0136\13!\3\"\3\"\3#\3#\3"+
		"$\3$\3%\3%\5%\u0140\n%\3&\5&\u0143\n&\3&\3&\3&\3&\3&\5&\u014a\n&\3\'\3"+
		"\'\3(\6(\u014f\n(\r(\16(\u0150\3)\3)\7)\u0155\n)\f)\16)\u0158\13)\3*\3"+
		"*\3*\6*\u015d\n*\r*\16*\u015e\3+\3+\3+\3,\3,\3,\7,\u0167\n,\f,\16,\u016a"+
		"\13,\3,\3,\3-\6-\u016f\n-\r-\16-\u0170\3-\3-\3.\3.\5.\u0177\n.\3.\5.\u017a"+
		"\n.\3.\3.\3/\3/\3/\3/\7/\u0182\n/\f/\16/\u0185\13/\3/\3/\3/\3/\3/\3\60"+
		"\3\60\3\60\3\60\7\60\u0190\n\60\f\60\16\60\u0193\13\60\3\60\3\60\3\61"+
		"\3\61\5\61\u0199\n\61\3\61\5\61\u019c\n\61\4\u0168\u0183\2\62\4\3\6\4"+
		"\b\5\n\6\f\7\16\b\20\t\22\n\24\13\26\f\30\r\32\16\34\17\36\20 \21\"\22"+
		"$\23&\24(\25*\26,\27.\30\60\31\62\32\64\33\66\348\35:\36<\37> @!B\"D\2"+
		"F\2H\2J#L$N\2P%R&T\2V\2X\'Z(\\)^*`+b,\4\2\3\n\7\2//\61\61C\\aac|\3\2\63"+
		";\3\2\62;\4\2--//\4\2ZZzz\5\2\62;CHch\4\2\f\f\17\17\4\2\13\13\"\"\2\u01bc"+
		"\2\4\3\2\2\2\2\6\3\2\2\2\2\b\3\2\2\2\2\n\3\2\2\2\2\f\3\2\2\2\2\16\3\2"+
		"\2\2\2\20\3\2\2\2\2\22\3\2\2\2\2\24\3\2\2\2\2\26\3\2\2\2\2\30\3\2\2\2"+
		"\2\32\3\2\2\2\2\34\3\2\2\2\2\36\3\2\2\2\2 \3\2\2\2\2\"\3\2\2\2\2$\3\2"+
		"\2\2\2&\3\2\2\2\2(\3\2\2\2\2*\3\2\2\2\2,\3\2\2\2\2.\3\2\2\2\2\60\3\2\2"+
		"\2\2\62\3\2\2\2\2\64\3\2\2\2\2\66\3\2\2\2\28\3\2\2\2\2:\3\2\2\2\2<\3\2"+
		"\2\2\2>\3\2\2\2\2@\3\2\2\2\2B\3\2\2\2\2J\3\2\2\2\2L\3\2\2\2\2P\3\2\2\2"+
		"\2R\3\2\2\2\2X\3\2\2\2\2Z\3\2\2\2\2\\\3\2\2\2\2^\3\2\2\2\2`\3\2\2\2\3"+
		"b\3\2\2\2\4f\3\2\2\2\6k\3\2\2\2\bt\3\2\2\2\n\u008c\3\2\2\2\f\u0096\3\2"+
		"\2\2\16\u009e\3\2\2\2\20\u00ac\3\2\2\2\22\u00ba\3\2\2\2\24\u00bc\3\2\2"+
		"\2\26\u00c1\3\2\2\2\30\u00c6\3\2\2\2\32\u00ca\3\2\2\2\34\u00d2\3\2\2\2"+
		"\36\u00d6\3\2\2\2 \u00de\3\2\2\2\"\u00e5\3\2\2\2$\u00e7\3\2\2\2&\u00e9"+
		"\3\2\2\2(\u00fd\3\2\2\2*\u0113\3\2\2\2,\u0115\3\2\2\2.\u0117\3\2\2\2\60"+
		"\u0119\3\2\2\2\62\u011b\3\2\2\2\64\u011d\3\2\2\2\66\u011f\3\2\2\28\u0121"+
		"\3\2\2\2:\u0125\3\2\2\2<\u0129\3\2\2\2>\u012b\3\2\2\2@\u012d\3\2\2\2B"+
		"\u012f\3\2\2\2D\u0137\3\2\2\2F\u0139\3\2\2\2H\u013b\3\2\2\2J\u013f\3\2"+
		"\2\2L\u0149\3\2\2\2N\u014b\3\2\2\2P\u014e\3\2\2\2R\u0152\3\2\2\2T\u0159"+
		"\3\2\2\2V\u0160\3\2\2\2X\u0163\3\2\2\2Z\u016e\3\2\2\2\\\u0179\3\2\2\2"+
		"^\u017d\3\2\2\2`\u018b\3\2\2\2b\u019b\3\2\2\2dg\5(\24\2eg\5*\25\2fd\3"+
		"\2\2\2fe\3\2\2\2g\5\3\2\2\2hl\5b\61\2il\5.\27\2jl\5,\26\2kh\3\2\2\2ki"+
		"\3\2\2\2kj\3\2\2\2l\7\3\2\2\2mn\7c\2\2no\7p\2\2ou\7{\2\2pq\7C\2\2qr\7"+
		"p\2\2ru\7{\2\2su\7,\2\2tm\3\2\2\2tp\3\2\2\2ts\3\2\2\2u\t\3\2\2\2vw\7d"+
		"\2\2wx\7q\2\2xy\7q\2\2y\u008d\7n\2\2z{\7D\2\2{|\7q\2\2|}\7q\2\2}\u008d"+
		"\7n\2\2~\177\7d\2\2\177\u0080\7q\2\2\u0080\u0081\7q\2\2\u0081\u0082\7"+
		"n\2\2\u0082\u0083\7g\2\2\u0083\u0084\7c\2\2\u0084\u008d\7p\2\2\u0085\u0086"+
		"\7D\2\2\u0086\u0087\7q\2\2\u0087\u0088\7q\2\2\u0088\u0089\7n\2\2\u0089"+
		"\u008a\7g\2\2\u008a\u008b\7c\2\2\u008b\u008d\7p\2\2\u008cv\3\2\2\2\u008c"+
		"z\3\2\2\2\u008c~\3\2\2\2\u008c\u0085\3\2\2\2\u008d\13\3\2\2\2\u008e\u008f"+
		"\7n\2\2\u008f\u0090\7k\2\2\u0090\u0091\7u\2\2\u0091\u0097\7v\2\2\u0092"+
		"\u0093\7N\2\2\u0093\u0094\7k\2\2\u0094\u0095\7u\2\2\u0095\u0097\7v\2\2"+
		"\u0096\u008e\3\2\2\2\u0096\u0092\3\2\2\2\u0097\r\3\2\2\2\u0098\u0099\7"+
		"o\2\2\u0099\u009a\7c\2\2\u009a\u009f\7r\2\2\u009b\u009c\7O\2\2\u009c\u009d"+
		"\7c\2\2\u009d\u009f\7r\2\2\u009e\u0098\3\2\2\2\u009e\u009b\3\2\2\2\u009f"+
		"\17\3\2\2\2\u00a0\u00a1\7p\2\2\u00a1\u00a2\7w\2\2\u00a2\u00a3\7o\2\2\u00a3"+
		"\u00a4\7d\2\2\u00a4\u00a5\7g\2\2\u00a5\u00ad\7t\2\2\u00a6\u00a7\7P\2\2"+
		"\u00a7\u00a8\7w\2\2\u00a8\u00a9\7o\2\2\u00a9\u00aa\7d\2\2\u00aa\u00ab"+
		"\7g\2\2\u00ab\u00ad\7t\2\2\u00ac\u00a0\3\2\2\2\u00ac\u00a6\3\2\2\2\u00ad"+
		"\21\3\2\2\2\u00ae\u00af\7u\2\2\u00af\u00b0\7v\2\2\u00b0\u00b1\7t\2\2\u00b1"+
		"\u00b2\7k\2\2\u00b2\u00b3\7p\2\2\u00b3\u00bb\7i\2\2\u00b4\u00b5\7U\2\2"+
		"\u00b5\u00b6\7v\2\2\u00b6\u00b7\7t\2\2\u00b7\u00b8\7k\2\2\u00b8\u00b9"+
		"\7p\2\2\u00b9\u00bb\7i\2\2\u00ba\u00ae\3\2\2\2\u00ba\u00b4\3\2\2\2\u00bb"+
		"\23\3\2\2\2\u00bc\u00bd\7g\2\2\u00bd\u00be\7o\2\2\u00be\u00bf\7k\2\2\u00bf"+
		"\u00c0\7v\2\2\u00c0\25\3\2\2\2\u00c1\u00c2\7g\2\2\u00c2\u00c3\7p\2\2\u00c3"+
		"\u00c4\7w\2\2\u00c4\u00c5\7o\2\2\u00c5\27\3\2\2\2\u00c6\u00c7\7g\2\2\u00c7"+
		"\u00c8\7z\2\2\u00c8\u00c9\7v\2\2\u00c9\31\3\2\2\2\u00ca\u00cb\7g\2\2\u00cb"+
		"\u00cc\7z\2\2\u00cc\u00cd\7v\2\2\u00cd\u00ce\7g\2\2\u00ce\u00cf\7p\2\2"+
		"\u00cf\u00d0\7f\2\2\u00d0\u00d1\7u\2\2\u00d1\33\3\2\2\2\u00d2\u00d3\7"+
		"n\2\2\u00d3\u00d4\7g\2\2\u00d4\u00d5\7v\2\2\u00d5\35\3\2\2\2\u00d6\u00d7"+
		"\7&\2\2\u00d7\u00d8\7k\2\2\u00d8\u00d9\7o\2\2\u00d9\u00da\7r\2\2\u00da"+
		"\u00db\7q\2\2\u00db\u00dc\7t\2\2\u00dc\u00dd\7v\2\2\u00dd\37\3\2\2\2\u00de"+
		"\u00df\7u\2\2\u00df\u00e0\7v\2\2\u00e0\u00e1\7t\2\2\u00e1\u00e2\7w\2\2"+
		"\u00e2\u00e3\7e\2\2\u00e3\u00e4\7v\2\2\u00e4!\3\2\2\2\u00e5\u00e6\7>\2"+
		"\2\u00e6#\3\2\2\2\u00e7\u00e8\7@\2\2\u00e8%\3\2\2\2\u00e9\u00ea\7<\2\2"+
		"\u00ea\'\3\2\2\2\u00eb\u00ec\7v\2\2\u00ec\u00ed\7t\2\2\u00ed\u00ee\7w"+
		"\2\2\u00ee\u00fe\7g\2\2\u00ef\u00f0\7V\2\2\u00f0\u00f1\7t\2\2\u00f1\u00f2"+
		"\7w\2\2\u00f2\u00fe\7g\2\2\u00f3\u00f4\7{\2\2\u00f4\u00f5\7g\2\2\u00f5"+
		"\u00fe\7u\2\2\u00f6\u00f7\7[\2\2\u00f7\u00f8\7g\2\2\u00f8\u00fe\7u\2\2"+
		"\u00f9\u00fa\7q\2\2\u00fa\u00fe\7p\2\2\u00fb\u00fc\7Q\2\2\u00fc\u00fe"+
		"\7p\2\2\u00fd\u00eb\3\2\2\2\u00fd\u00ef\3\2\2\2\u00fd\u00f3\3\2\2\2\u00fd"+
		"\u00f6\3\2\2\2\u00fd\u00f9\3\2\2\2\u00fd\u00fb\3\2\2\2\u00fe)\3\2\2\2"+
		"\u00ff\u0100\7h\2\2\u0100\u0101\7c\2\2\u0101\u0102\7n\2\2\u0102\u0103"+
		"\7u\2\2\u0103\u0114\7g\2\2\u0104\u0105\7H\2\2\u0105\u0106\7c\2\2\u0106"+
		"\u0107\7n\2\2\u0107\u0108\7u\2\2\u0108\u0114\7g\2\2\u0109\u010a\7p\2\2"+
		"\u010a\u0114\7q\2\2\u010b\u010c\7P\2\2\u010c\u0114\7q\2\2\u010d\u010e"+
		"\7q\2\2\u010e\u010f\7h\2\2\u010f\u0114\7h\2\2\u0110\u0111\7Q\2\2\u0111"+
		"\u0112\7h\2\2\u0112\u0114\7h\2\2\u0113\u00ff\3\2\2\2\u0113\u0104\3\2\2"+
		"\2\u0113\u0109\3\2\2\2\u0113\u010b\3\2\2\2\u0113\u010d\3\2\2\2\u0113\u0110"+
		"\3\2\2\2\u0114+\3\2\2\2\u0115\u0116\7.\2\2\u0116-\3\2\2\2\u0117\u0118"+
		"\7=\2\2\u0118/\3\2\2\2\u0119\u011a\7*\2\2\u011a\61\3\2\2\2\u011b\u011c"+
		"\7+\2\2\u011c\63\3\2\2\2\u011d\u011e\7]\2\2\u011e\65\3\2\2\2\u011f\u0120"+
		"\7_\2\2\u0120\67\3\2\2\2\u0121\u0122\7}\2\2\u0122\u0123\3\2\2\2\u0123"+
		"\u0124\b\34\2\2\u01249\3\2\2\2\u0125\u0126\7\177\2\2\u0126\u0127\3\2\2"+
		"\2\u0127\u0128\b\35\3\2\u0128;\3\2\2\2\u0129\u012a\7~\2\2\u012a=\3\2\2"+
		"\2\u012b\u012c\7\60\2\2\u012c?\3\2\2\2\u012d\u012e\7?\2\2\u012eA\3\2\2"+
		"\2\u012f\u0134\5D\"\2\u0130\u0133\5D\"\2\u0131\u0133\5H$\2\u0132\u0130"+
		"\3\2\2\2\u0132\u0131\3\2\2\2\u0133\u0136\3\2\2\2\u0134\u0132\3\2\2\2\u0134"+
		"\u0135\3\2\2\2\u0135C\3\2\2\2\u0136\u0134\3\2\2\2\u0137\u0138\t\2\2\2"+
		"\u0138E\3\2\2\2\u0139\u013a\t\3\2\2\u013aG\3\2\2\2\u013b\u013c\t\4\2\2"+
		"\u013cI\3\2\2\2\u013d\u0140\5R)\2\u013e\u0140\5T*\2\u013f\u013d\3\2\2"+
		"\2\u013f\u013e\3\2\2\2\u0140K\3\2\2\2\u0141\u0143\5P(\2\u0142\u0141\3"+
		"\2\2\2\u0142\u0143\3\2\2\2\u0143\u0144\3\2\2\2\u0144\u0145\7\60\2\2\u0145"+
		"\u014a\5P(\2\u0146\u0147\5P(\2\u0147\u0148\7\60\2\2\u0148\u014a\3\2\2"+
		"\2\u0149\u0142\3\2\2\2\u0149\u0146\3\2\2\2\u014aM\3\2\2\2\u014b\u014c"+
		"\t\5\2\2\u014cO\3\2\2\2\u014d\u014f\5H$\2\u014e\u014d\3\2\2\2\u014f\u0150"+
		"\3\2\2\2\u0150\u014e\3\2\2\2\u0150\u0151\3\2\2\2\u0151Q\3\2\2\2\u0152"+
		"\u0156\t\3\2\2\u0153\u0155\5H$\2\u0154\u0153\3\2\2\2\u0155\u0158\3\2\2"+
		"\2\u0156\u0154\3\2\2\2\u0156\u0157\3\2\2\2\u0157S\3\2\2\2\u0158\u0156"+
		"\3\2\2\2\u0159\u015a\7\62\2\2\u015a\u015c\t\6\2\2\u015b\u015d\t\7\2\2"+
		"\u015c\u015b\3\2\2\2\u015d\u015e\3\2\2\2\u015e\u015c\3\2\2\2\u015e\u015f"+
		"\3\2\2\2\u015fU\3\2\2\2\u0160\u0161\7^\2\2\u0161\u0162\7$\2\2\u0162W\3"+
		"\2\2\2\u0163\u0168\7$\2\2\u0164\u0167\5V+\2\u0165\u0167\n\b\2\2\u0166"+
		"\u0164\3\2\2\2\u0166\u0165\3\2\2\2\u0167\u016a\3\2\2\2\u0168\u0169\3\2"+
		"\2\2\u0168\u0166\3\2\2\2\u0169\u016b\3\2\2\2\u016a\u0168\3\2\2\2\u016b"+
		"\u016c\7$\2\2\u016cY\3\2\2\2\u016d\u016f\t\t\2\2\u016e\u016d\3\2\2\2\u016f"+
		"\u0170\3\2\2\2\u0170\u016e\3\2\2\2\u0170\u0171\3\2\2\2\u0171\u0172\3\2"+
		"\2\2\u0172\u0173\b-\4\2\u0173[\3\2\2\2\u0174\u0176\7\17\2\2\u0175\u0177"+
		"\7\f\2\2\u0176\u0175\3\2\2\2\u0176\u0177\3\2\2\2\u0177\u017a\3\2\2\2\u0178"+
		"\u017a\7\f\2\2\u0179\u0174\3\2\2\2\u0179\u0178\3\2\2\2\u017a\u017b\3\2"+
		"\2\2\u017b\u017c\b.\4\2\u017c]\3\2\2\2\u017d\u017e\7\61\2\2\u017e\u017f"+
		"\7,\2\2\u017f\u0183\3\2\2\2\u0180\u0182\13\2\2\2\u0181\u0180\3\2\2\2\u0182"+
		"\u0185\3\2\2\2\u0183\u0184\3\2\2\2\u0183\u0181\3\2\2\2\u0184\u0186\3\2"+
		"\2\2\u0185\u0183\3\2\2\2\u0186\u0187\7,\2\2\u0187\u0188\7\61\2\2\u0188"+
		"\u0189\3\2\2\2\u0189\u018a\b/\4\2\u018a_\3\2\2\2\u018b\u018c\7\61\2\2"+
		"\u018c\u018d\7\61\2\2\u018d\u0191\3\2\2\2\u018e\u0190\n\b\2\2\u018f\u018e"+
		"\3\2\2\2\u0190\u0193\3\2\2\2\u0191\u018f\3\2\2\2\u0191\u0192\3\2\2\2\u0192"+
		"\u0194\3\2\2\2\u0193\u0191\3\2\2\2\u0194\u0195\b\60\4\2\u0195a\3\2\2\2"+
		"\u0196\u0198\7\17\2\2\u0197\u0199\7\f\2\2\u0198\u0197\3\2\2\2\u0198\u0199"+
		"\3\2\2\2\u0199\u019c\3\2\2\2\u019a\u019c\7\f\2\2\u019b\u0196\3\2\2\2\u019b"+
		"\u019a\3\2\2\2\u019cc\3\2\2\2\37\2\3fkt\u008c\u0096\u009e\u00ac\u00ba"+
		"\u00fd\u0113\u0132\u0134\u013f\u0142\u0149\u0150\u0156\u015e\u0166\u0168"+
		"\u0170\u0176\u0179\u0183\u0191\u0198\u019b\5\7\3\2\6\2\2\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}