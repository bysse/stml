// Generated from /home/erikb/dev/projects/stml/src/main/antlr/STMLParser.g4 by ANTLR 4.8
import org.antlr.v4.runtime.dfa.DFA;

import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class STMLParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, BooleanConstant=3, Separator=4, ANY=5, BOOLEAN=6, LIST=7, 
		MAP=8, NUMBER=9, STRING=10, Emit=11, Enum=12, External=13, Extends=14, 
		Let=15, InlineImport=16, Struct=17, TypeLeft=18, TypeRight=19, COLON=20, 
		TRUE=21, FALSE=22, COMMA=23, SEMICOLON=24, LeftParen=25, RightParen=26, 
		LeftBracket=27, RightBracket=28, LeftBrace=29, RightBrace=30, OR=31, DOT=32, 
		EQUAL=33, IDENTIFIER=34, IntegerConstant=35, FractionalConstant=36, DigitSequence=37, 
		DecimalConstant=38, StringConstant=39, Whitespace=40, NEWLINE=41, BlockComment=42, 
		LineComment=43;
	public static final int
		RULE_statement_list = 0, RULE_statement = 1, RULE_emit_statement = 2, 
		RULE_type_declaration_statement = 3, RULE_assignment_statement = 4, RULE_value = 5, 
		RULE_type_constructor = 6, RULE_type_argument_list = 7, RULE_type_argument = 8, 
		RULE_type_field = 9, RULE_enum_type_declaration = 10, RULE_enum_type_member_list = 11, 
		RULE_struct_type_declaration = 12, RULE_struct_type_member_list = 13, 
		RULE_type_specifier_list = 14, RULE_type_specifier = 15, RULE_template_type_specifier = 16, 
		RULE_custom_type_specifier = 17, RULE_constant = 18;
	private static String[] makeRuleNames() {
		return new String[] {
			"statement_list", "statement", "emit_statement", "type_declaration_statement", 
			"assignment_statement", "value", "type_constructor", "type_argument_list", 
			"type_argument", "type_field", "enum_type_declaration", "enum_type_member_list", 
			"struct_type_declaration", "struct_type_member_list", "type_specifier_list", 
			"type_specifier", "template_type_specifier", "custom_type_specifier", 
			"constant"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'\r'", "'\n'", null, null, null, null, null, null, null, null, 
			"'emit'", "'enum'", "'ext'", "'extends'", "'let'", "'$import'", "'struct'", 
			"'<'", "'>'", "':'", null, null, "','", "';'", "'('", "')'", "'['", "']'", 
			"'{'", "'}'", "'|'", "'.'", "'='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, "BooleanConstant", "Separator", "ANY", "BOOLEAN", "LIST", 
			"MAP", "NUMBER", "STRING", "Emit", "Enum", "External", "Extends", "Let", 
			"InlineImport", "Struct", "TypeLeft", "TypeRight", "COLON", "TRUE", "FALSE", 
			"COMMA", "SEMICOLON", "LeftParen", "RightParen", "LeftBracket", "RightBracket", 
			"LeftBrace", "RightBrace", "OR", "DOT", "EQUAL", "IDENTIFIER", "IntegerConstant", 
			"FractionalConstant", "DigitSequence", "DecimalConstant", "StringConstant", 
			"Whitespace", "NEWLINE", "BlockComment", "LineComment"
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

	@Override
	public String getGrammarFileName() { return "STMLParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public STMLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class Statement_listContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(STMLParser.EOF, 0); }
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public Statement_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).enterStatement_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).exitStatement_list(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STMLParserVisitor ) return ((STMLParserVisitor<? extends T>)visitor).visitStatement_list(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Statement_listContext statement_list() throws RecognitionException {
		Statement_listContext _localctx = new Statement_listContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_statement_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(39); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(38);
				statement();
				}
				}
				setState(41); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Emit) | (1L << Enum) | (1L << Let) | (1L << Struct))) != 0) );
			setState(43);
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

	public static class StatementContext extends ParserRuleContext {
		public Type_declaration_statementContext type_declaration_statement() {
			return getRuleContext(Type_declaration_statementContext.class,0);
		}
		public Assignment_statementContext assignment_statement() {
			return getRuleContext(Assignment_statementContext.class,0);
		}
		public Emit_statementContext emit_statement() {
			return getRuleContext(Emit_statementContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).exitStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STMLParserVisitor ) return ((STMLParserVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_statement);
		try {
			setState(48);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Enum:
			case Struct:
				enterOuterAlt(_localctx, 1);
				{
				setState(45);
				type_declaration_statement();
				}
				break;
			case Let:
				enterOuterAlt(_localctx, 2);
				{
				setState(46);
				assignment_statement();
				}
				break;
			case Emit:
				enterOuterAlt(_localctx, 3);
				{
				setState(47);
				emit_statement();
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

	public static class Emit_statementContext extends ParserRuleContext {
		public TerminalNode Emit() { return getToken(STMLParser.Emit, 0); }
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(STMLParser.SEMICOLON, 0); }
		public Emit_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_emit_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).enterEmit_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).exitEmit_statement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STMLParserVisitor ) return ((STMLParserVisitor<? extends T>)visitor).visitEmit_statement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Emit_statementContext emit_statement() throws RecognitionException {
		Emit_statementContext _localctx = new Emit_statementContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_emit_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(50);
			match(Emit);
			setState(51);
			value();
			setState(52);
			match(SEMICOLON);
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

	public static class Type_declaration_statementContext extends ParserRuleContext {
		public Enum_type_declarationContext enum_type_declaration() {
			return getRuleContext(Enum_type_declarationContext.class,0);
		}
		public Struct_type_declarationContext struct_type_declaration() {
			return getRuleContext(Struct_type_declarationContext.class,0);
		}
		public Type_declaration_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_declaration_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).enterType_declaration_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).exitType_declaration_statement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STMLParserVisitor ) return ((STMLParserVisitor<? extends T>)visitor).visitType_declaration_statement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Type_declaration_statementContext type_declaration_statement() throws RecognitionException {
		Type_declaration_statementContext _localctx = new Type_declaration_statementContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_type_declaration_statement);
		try {
			setState(56);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Enum:
				enterOuterAlt(_localctx, 1);
				{
				setState(54);
				enum_type_declaration();
				}
				break;
			case Struct:
				enterOuterAlt(_localctx, 2);
				{
				setState(55);
				struct_type_declaration();
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

	public static class Assignment_statementContext extends ParserRuleContext {
		public TerminalNode Let() { return getToken(STMLParser.Let, 0); }
		public TerminalNode IDENTIFIER() { return getToken(STMLParser.IDENTIFIER, 0); }
		public TerminalNode EQUAL() { return getToken(STMLParser.EQUAL, 0); }
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public TerminalNode SEMICOLON() { return getToken(STMLParser.SEMICOLON, 0); }
		public Assignment_statementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).enterAssignment_statement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).exitAssignment_statement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STMLParserVisitor ) return ((STMLParserVisitor<? extends T>)visitor).visitAssignment_statement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Assignment_statementContext assignment_statement() throws RecognitionException {
		Assignment_statementContext _localctx = new Assignment_statementContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_assignment_statement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(58);
			match(Let);
			setState(59);
			match(IDENTIFIER);
			setState(60);
			match(EQUAL);
			setState(61);
			value();
			setState(62);
			match(SEMICOLON);
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

	public static class ValueContext extends ParserRuleContext {
		public ConstantContext constant() {
			return getRuleContext(ConstantContext.class,0);
		}
		public Type_fieldContext type_field() {
			return getRuleContext(Type_fieldContext.class,0);
		}
		public Type_constructorContext type_constructor() {
			return getRuleContext(Type_constructorContext.class,0);
		}
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).enterValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).exitValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STMLParserVisitor ) return ((STMLParserVisitor<? extends T>)visitor).visitValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_value);
		try {
			setState(67);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(64);
				constant();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(65);
				type_field();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(66);
				type_constructor();
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

	public static class Type_constructorContext extends ParserRuleContext {
		public Type_specifierContext type_specifier() {
			return getRuleContext(Type_specifierContext.class,0);
		}
		public TerminalNode LeftParen() { return getToken(STMLParser.LeftParen, 0); }
		public TerminalNode RightParen() { return getToken(STMLParser.RightParen, 0); }
		public Type_argument_listContext type_argument_list() {
			return getRuleContext(Type_argument_listContext.class,0);
		}
		public Type_constructorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_constructor; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).enterType_constructor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).exitType_constructor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STMLParserVisitor ) return ((STMLParserVisitor<? extends T>)visitor).visitType_constructor(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Type_constructorContext type_constructor() throws RecognitionException {
		Type_constructorContext _localctx = new Type_constructorContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_type_constructor);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(69);
			type_specifier();
			setState(70);
			match(LeftParen);
			setState(72);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IDENTIFIER) {
				{
				setState(71);
				type_argument_list();
				}
			}

			setState(74);
			match(RightParen);
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

	public static class Type_argument_listContext extends ParserRuleContext {
		public List<Type_argumentContext> type_argument() {
			return getRuleContexts(Type_argumentContext.class);
		}
		public Type_argumentContext type_argument(int i) {
			return getRuleContext(Type_argumentContext.class,i);
		}
		public List<TerminalNode> Separator() { return getTokens(STMLParser.Separator); }
		public TerminalNode Separator(int i) {
			return getToken(STMLParser.Separator, i);
		}
		public Type_argument_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_argument_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).enterType_argument_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).exitType_argument_list(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STMLParserVisitor ) return ((STMLParserVisitor<? extends T>)visitor).visitType_argument_list(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Type_argument_listContext type_argument_list() throws RecognitionException {
		Type_argument_listContext _localctx = new Type_argument_listContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_type_argument_list);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(76);
			type_argument();
			setState(81);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(77);
					match(Separator);
					setState(78);
					type_argument();
					}
					} 
				}
				setState(83);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
			}
			setState(85);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Separator) {
				{
				setState(84);
				match(Separator);
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

	public static class Type_argumentContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(STMLParser.IDENTIFIER, 0); }
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public TerminalNode COLON() { return getToken(STMLParser.COLON, 0); }
		public TerminalNode EQUAL() { return getToken(STMLParser.EQUAL, 0); }
		public Type_argumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_argument; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).enterType_argument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).exitType_argument(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STMLParserVisitor ) return ((STMLParserVisitor<? extends T>)visitor).visitType_argument(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Type_argumentContext type_argument() throws RecognitionException {
		Type_argumentContext _localctx = new Type_argumentContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_type_argument);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(87);
			match(IDENTIFIER);
			setState(88);
			_la = _input.LA(1);
			if ( !(_la==COLON || _la==EQUAL) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(89);
			value();
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

	public static class Type_fieldContext extends ParserRuleContext {
		public List<TerminalNode> IDENTIFIER() { return getTokens(STMLParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(STMLParser.IDENTIFIER, i);
		}
		public List<TerminalNode> DOT() { return getTokens(STMLParser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(STMLParser.DOT, i);
		}
		public Type_fieldContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_field; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).enterType_field(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).exitType_field(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STMLParserVisitor ) return ((STMLParserVisitor<? extends T>)visitor).visitType_field(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Type_fieldContext type_field() throws RecognitionException {
		Type_fieldContext _localctx = new Type_fieldContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_type_field);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(91);
			match(IDENTIFIER);
			setState(94); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(92);
				match(DOT);
				setState(93);
				match(IDENTIFIER);
				}
				}
				setState(96); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==DOT );
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

	public static class Enum_type_declarationContext extends ParserRuleContext {
		public TerminalNode Enum() { return getToken(STMLParser.Enum, 0); }
		public List<TerminalNode> IDENTIFIER() { return getTokens(STMLParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(STMLParser.IDENTIFIER, i);
		}
		public TerminalNode LeftBrace() { return getToken(STMLParser.LeftBrace, 0); }
		public TerminalNode RightBrace() { return getToken(STMLParser.RightBrace, 0); }
		public Enum_type_member_listContext enum_type_member_list() {
			return getRuleContext(Enum_type_member_listContext.class,0);
		}
		public TerminalNode Extends() { return getToken(STMLParser.Extends, 0); }
		public Enum_type_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enum_type_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).enterEnum_type_declaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).exitEnum_type_declaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STMLParserVisitor ) return ((STMLParserVisitor<? extends T>)visitor).visitEnum_type_declaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Enum_type_declarationContext enum_type_declaration() throws RecognitionException {
		Enum_type_declarationContext _localctx = new Enum_type_declarationContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_enum_type_declaration);
		int _la;
		try {
			setState(114);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(98);
				match(Enum);
				setState(99);
				match(IDENTIFIER);
				setState(100);
				match(LeftBrace);
				setState(102);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==IDENTIFIER) {
					{
					setState(101);
					enum_type_member_list();
					}
				}

				setState(104);
				match(RightBrace);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(105);
				match(Enum);
				setState(106);
				match(IDENTIFIER);
				setState(107);
				match(Extends);
				setState(108);
				match(IDENTIFIER);
				setState(109);
				match(LeftBrace);
				setState(111);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==IDENTIFIER) {
					{
					setState(110);
					enum_type_member_list();
					}
				}

				setState(113);
				match(RightBrace);
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

	public static class Enum_type_member_listContext extends ParserRuleContext {
		public List<TerminalNode> IDENTIFIER() { return getTokens(STMLParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(STMLParser.IDENTIFIER, i);
		}
		public List<TerminalNode> SEMICOLON() { return getTokens(STMLParser.SEMICOLON); }
		public TerminalNode SEMICOLON(int i) {
			return getToken(STMLParser.SEMICOLON, i);
		}
		public Enum_type_member_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_enum_type_member_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).enterEnum_type_member_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).exitEnum_type_member_list(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STMLParserVisitor ) return ((STMLParserVisitor<? extends T>)visitor).visitEnum_type_member_list(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Enum_type_member_listContext enum_type_member_list() throws RecognitionException {
		Enum_type_member_listContext _localctx = new Enum_type_member_listContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_enum_type_member_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(118); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(116);
				match(IDENTIFIER);
				setState(117);
				match(SEMICOLON);
				}
				}
				setState(120); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==IDENTIFIER );
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

	public static class Struct_type_declarationContext extends ParserRuleContext {
		public TerminalNode Struct() { return getToken(STMLParser.Struct, 0); }
		public TerminalNode IDENTIFIER() { return getToken(STMLParser.IDENTIFIER, 0); }
		public TerminalNode LeftBrace() { return getToken(STMLParser.LeftBrace, 0); }
		public TerminalNode RightBrace() { return getToken(STMLParser.RightBrace, 0); }
		public Template_type_specifierContext template_type_specifier() {
			return getRuleContext(Template_type_specifierContext.class,0);
		}
		public Struct_type_member_listContext struct_type_member_list() {
			return getRuleContext(Struct_type_member_listContext.class,0);
		}
		public TerminalNode Extends() { return getToken(STMLParser.Extends, 0); }
		public Custom_type_specifierContext custom_type_specifier() {
			return getRuleContext(Custom_type_specifierContext.class,0);
		}
		public Struct_type_declarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_struct_type_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).enterStruct_type_declaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).exitStruct_type_declaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STMLParserVisitor ) return ((STMLParserVisitor<? extends T>)visitor).visitStruct_type_declaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Struct_type_declarationContext struct_type_declaration() throws RecognitionException {
		Struct_type_declarationContext _localctx = new Struct_type_declarationContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_struct_type_declaration);
		int _la;
		try {
			setState(145);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(122);
				match(Struct);
				setState(123);
				match(IDENTIFIER);
				setState(125);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==TypeLeft) {
					{
					setState(124);
					template_type_specifier();
					}
				}

				setState(127);
				match(LeftBrace);
				setState(129);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==IDENTIFIER) {
					{
					setState(128);
					struct_type_member_list();
					}
				}

				setState(131);
				match(RightBrace);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(132);
				match(Struct);
				setState(133);
				match(IDENTIFIER);
				setState(135);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==TypeLeft) {
					{
					setState(134);
					template_type_specifier();
					}
				}

				setState(137);
				match(Extends);
				setState(138);
				custom_type_specifier();
				setState(139);
				match(LeftBrace);
				setState(141);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==IDENTIFIER) {
					{
					setState(140);
					struct_type_member_list();
					}
				}

				setState(143);
				match(RightBrace);
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

	public static class Struct_type_member_listContext extends ParserRuleContext {
		public List<TerminalNode> IDENTIFIER() { return getTokens(STMLParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(STMLParser.IDENTIFIER, i);
		}
		public List<TerminalNode> COLON() { return getTokens(STMLParser.COLON); }
		public TerminalNode COLON(int i) {
			return getToken(STMLParser.COLON, i);
		}
		public List<Type_specifier_listContext> type_specifier_list() {
			return getRuleContexts(Type_specifier_listContext.class);
		}
		public Type_specifier_listContext type_specifier_list(int i) {
			return getRuleContext(Type_specifier_listContext.class,i);
		}
		public List<TerminalNode> SEMICOLON() { return getTokens(STMLParser.SEMICOLON); }
		public TerminalNode SEMICOLON(int i) {
			return getToken(STMLParser.SEMICOLON, i);
		}
		public List<ValueContext> value() {
			return getRuleContexts(ValueContext.class);
		}
		public ValueContext value(int i) {
			return getRuleContext(ValueContext.class,i);
		}
		public List<TerminalNode> EQUAL() { return getTokens(STMLParser.EQUAL); }
		public TerminalNode EQUAL(int i) {
			return getToken(STMLParser.EQUAL, i);
		}
		public Struct_type_member_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_struct_type_member_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).enterStruct_type_member_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).exitStruct_type_member_list(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STMLParserVisitor ) return ((STMLParserVisitor<? extends T>)visitor).visitStruct_type_member_list(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Struct_type_member_listContext struct_type_member_list() throws RecognitionException {
		Struct_type_member_listContext _localctx = new Struct_type_member_listContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_struct_type_member_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(156); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(147);
				match(IDENTIFIER);
				setState(148);
				match(COLON);
				setState(149);
				type_specifier_list();
				setState(152);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==COLON || _la==EQUAL) {
					{
					setState(150);
					_la = _input.LA(1);
					if ( !(_la==COLON || _la==EQUAL) ) {
					_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					setState(151);
					value();
					}
				}

				setState(154);
				match(SEMICOLON);
				}
				}
				setState(158); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==IDENTIFIER );
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

	public static class Type_specifier_listContext extends ParserRuleContext {
		public List<Type_specifierContext> type_specifier() {
			return getRuleContexts(Type_specifierContext.class);
		}
		public Type_specifierContext type_specifier(int i) {
			return getRuleContext(Type_specifierContext.class,i);
		}
		public List<TerminalNode> OR() { return getTokens(STMLParser.OR); }
		public TerminalNode OR(int i) {
			return getToken(STMLParser.OR, i);
		}
		public Type_specifier_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_specifier_list; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).enterType_specifier_list(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).exitType_specifier_list(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STMLParserVisitor ) return ((STMLParserVisitor<? extends T>)visitor).visitType_specifier_list(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Type_specifier_listContext type_specifier_list() throws RecognitionException {
		Type_specifier_listContext _localctx = new Type_specifier_listContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_type_specifier_list);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(160);
			type_specifier();
			setState(165);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR) {
				{
				{
				setState(161);
				match(OR);
				setState(162);
				type_specifier();
				}
				}
				setState(167);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	public static class Type_specifierContext extends ParserRuleContext {
		public TerminalNode ANY() { return getToken(STMLParser.ANY, 0); }
		public TerminalNode BOOLEAN() { return getToken(STMLParser.BOOLEAN, 0); }
		public TerminalNode LIST() { return getToken(STMLParser.LIST, 0); }
		public Template_type_specifierContext template_type_specifier() {
			return getRuleContext(Template_type_specifierContext.class,0);
		}
		public TerminalNode MAP() { return getToken(STMLParser.MAP, 0); }
		public TerminalNode NUMBER() { return getToken(STMLParser.NUMBER, 0); }
		public TerminalNode STRING() { return getToken(STMLParser.STRING, 0); }
		public Custom_type_specifierContext custom_type_specifier() {
			return getRuleContext(Custom_type_specifierContext.class,0);
		}
		public Type_specifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type_specifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).enterType_specifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).exitType_specifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STMLParserVisitor ) return ((STMLParserVisitor<? extends T>)visitor).visitType_specifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Type_specifierContext type_specifier() throws RecognitionException {
		Type_specifierContext _localctx = new Type_specifierContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_type_specifier);
		int _la;
		try {
			setState(181);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ANY:
				enterOuterAlt(_localctx, 1);
				{
				setState(168);
				match(ANY);
				}
				break;
			case BOOLEAN:
				enterOuterAlt(_localctx, 2);
				{
				setState(169);
				match(BOOLEAN);
				}
				break;
			case LIST:
				enterOuterAlt(_localctx, 3);
				{
				setState(170);
				match(LIST);
				setState(172);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==TypeLeft) {
					{
					setState(171);
					template_type_specifier();
					}
				}

				}
				break;
			case MAP:
				enterOuterAlt(_localctx, 4);
				{
				setState(174);
				match(MAP);
				setState(176);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==TypeLeft) {
					{
					setState(175);
					template_type_specifier();
					}
				}

				}
				break;
			case NUMBER:
				enterOuterAlt(_localctx, 5);
				{
				setState(178);
				match(NUMBER);
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 6);
				{
				setState(179);
				match(STRING);
				}
				break;
			case IDENTIFIER:
				enterOuterAlt(_localctx, 7);
				{
				setState(180);
				custom_type_specifier();
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

	public static class Template_type_specifierContext extends ParserRuleContext {
		public TerminalNode TypeLeft() { return getToken(STMLParser.TypeLeft, 0); }
		public Type_specifierContext type_specifier() {
			return getRuleContext(Type_specifierContext.class,0);
		}
		public TerminalNode TypeRight() { return getToken(STMLParser.TypeRight, 0); }
		public Template_type_specifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_template_type_specifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).enterTemplate_type_specifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).exitTemplate_type_specifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STMLParserVisitor ) return ((STMLParserVisitor<? extends T>)visitor).visitTemplate_type_specifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Template_type_specifierContext template_type_specifier() throws RecognitionException {
		Template_type_specifierContext _localctx = new Template_type_specifierContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_template_type_specifier);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(183);
			match(TypeLeft);
			setState(184);
			type_specifier();
			setState(185);
			match(TypeRight);
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

	public static class Custom_type_specifierContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(STMLParser.IDENTIFIER, 0); }
		public Template_type_specifierContext template_type_specifier() {
			return getRuleContext(Template_type_specifierContext.class,0);
		}
		public Custom_type_specifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_custom_type_specifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).enterCustom_type_specifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).exitCustom_type_specifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STMLParserVisitor ) return ((STMLParserVisitor<? extends T>)visitor).visitCustom_type_specifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Custom_type_specifierContext custom_type_specifier() throws RecognitionException {
		Custom_type_specifierContext _localctx = new Custom_type_specifierContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_custom_type_specifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(187);
			match(IDENTIFIER);
			setState(189);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==TypeLeft) {
				{
				setState(188);
				template_type_specifier();
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

	public static class ConstantContext extends ParserRuleContext {
		public ConstantContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constant; }
	 
		public ConstantContext() { }
		public void copyFrom(ConstantContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ParseFloatContext extends ConstantContext {
		public TerminalNode FractionalConstant() { return getToken(STMLParser.FractionalConstant, 0); }
		public ParseFloatContext(ConstantContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).enterParseFloat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).exitParseFloat(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STMLParserVisitor ) return ((STMLParserVisitor<? extends T>)visitor).visitParseFloat(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParseIntegerContext extends ConstantContext {
		public TerminalNode IntegerConstant() { return getToken(STMLParser.IntegerConstant, 0); }
		public ParseIntegerContext(ConstantContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).enterParseInteger(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).exitParseInteger(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STMLParserVisitor ) return ((STMLParserVisitor<? extends T>)visitor).visitParseInteger(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParseInlineImportContext extends ConstantContext {
		public TerminalNode InlineImport() { return getToken(STMLParser.InlineImport, 0); }
		public TerminalNode LeftParen() { return getToken(STMLParser.LeftParen, 0); }
		public TerminalNode StringConstant() { return getToken(STMLParser.StringConstant, 0); }
		public TerminalNode RightParen() { return getToken(STMLParser.RightParen, 0); }
		public ParseInlineImportContext(ConstantContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).enterParseInlineImport(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).exitParseInlineImport(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STMLParserVisitor ) return ((STMLParserVisitor<? extends T>)visitor).visitParseInlineImport(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParseVariableUseContext extends ConstantContext {
		public TerminalNode IDENTIFIER() { return getToken(STMLParser.IDENTIFIER, 0); }
		public ParseVariableUseContext(ConstantContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).enterParseVariableUse(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).exitParseVariableUse(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STMLParserVisitor ) return ((STMLParserVisitor<? extends T>)visitor).visitParseVariableUse(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParseBooleanContext extends ConstantContext {
		public TerminalNode BooleanConstant() { return getToken(STMLParser.BooleanConstant, 0); }
		public ParseBooleanContext(ConstantContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).enterParseBoolean(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).exitParseBoolean(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STMLParserVisitor ) return ((STMLParserVisitor<? extends T>)visitor).visitParseBoolean(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParseStringContext extends ConstantContext {
		public TerminalNode StringConstant() { return getToken(STMLParser.StringConstant, 0); }
		public ParseStringContext(ConstantContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).enterParseString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof STMLParserListener ) ((STMLParserListener)listener).exitParseString(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof STMLParserVisitor ) return ((STMLParserVisitor<? extends T>)visitor).visitParseString(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConstantContext constant() throws RecognitionException {
		ConstantContext _localctx = new ConstantContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_constant);
		try {
			setState(200);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case StringConstant:
				_localctx = new ParseStringContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(191);
				match(StringConstant);
				}
				break;
			case IntegerConstant:
				_localctx = new ParseIntegerContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(192);
				match(IntegerConstant);
				}
				break;
			case FractionalConstant:
				_localctx = new ParseFloatContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(193);
				match(FractionalConstant);
				}
				break;
			case BooleanConstant:
				_localctx = new ParseBooleanContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(194);
				match(BooleanConstant);
				}
				break;
			case InlineImport:
				_localctx = new ParseInlineImportContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(195);
				match(InlineImport);
				setState(196);
				match(LeftParen);
				setState(197);
				match(StringConstant);
				setState(198);
				match(RightParen);
				}
				break;
			case IDENTIFIER:
				_localctx = new ParseVariableUseContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(199);
				match(IDENTIFIER);
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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3-\u00cd\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\3\2\6\2*\n\2\r\2\16\2+\3\2\3\2\3\3\3\3\3\3\5\3\63"+
		"\n\3\3\4\3\4\3\4\3\4\3\5\3\5\5\5;\n\5\3\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7"+
		"\3\7\5\7F\n\7\3\b\3\b\3\b\5\bK\n\b\3\b\3\b\3\t\3\t\3\t\7\tR\n\t\f\t\16"+
		"\tU\13\t\3\t\5\tX\n\t\3\n\3\n\3\n\3\n\3\13\3\13\3\13\6\13a\n\13\r\13\16"+
		"\13b\3\f\3\f\3\f\3\f\5\fi\n\f\3\f\3\f\3\f\3\f\3\f\3\f\3\f\5\fr\n\f\3\f"+
		"\5\fu\n\f\3\r\3\r\6\ry\n\r\r\r\16\rz\3\16\3\16\3\16\5\16\u0080\n\16\3"+
		"\16\3\16\5\16\u0084\n\16\3\16\3\16\3\16\3\16\5\16\u008a\n\16\3\16\3\16"+
		"\3\16\3\16\5\16\u0090\n\16\3\16\3\16\5\16\u0094\n\16\3\17\3\17\3\17\3"+
		"\17\3\17\5\17\u009b\n\17\3\17\3\17\6\17\u009f\n\17\r\17\16\17\u00a0\3"+
		"\20\3\20\3\20\7\20\u00a6\n\20\f\20\16\20\u00a9\13\20\3\21\3\21\3\21\3"+
		"\21\5\21\u00af\n\21\3\21\3\21\5\21\u00b3\n\21\3\21\3\21\3\21\5\21\u00b8"+
		"\n\21\3\22\3\22\3\22\3\22\3\23\3\23\5\23\u00c0\n\23\3\24\3\24\3\24\3\24"+
		"\3\24\3\24\3\24\3\24\3\24\5\24\u00cb\n\24\3\24\2\2\25\2\4\6\b\n\f\16\20"+
		"\22\24\26\30\32\34\36 \"$&\2\3\4\2\26\26##\2\u00dd\2)\3\2\2\2\4\62\3\2"+
		"\2\2\6\64\3\2\2\2\b:\3\2\2\2\n<\3\2\2\2\fE\3\2\2\2\16G\3\2\2\2\20N\3\2"+
		"\2\2\22Y\3\2\2\2\24]\3\2\2\2\26t\3\2\2\2\30x\3\2\2\2\32\u0093\3\2\2\2"+
		"\34\u009e\3\2\2\2\36\u00a2\3\2\2\2 \u00b7\3\2\2\2\"\u00b9\3\2\2\2$\u00bd"+
		"\3\2\2\2&\u00ca\3\2\2\2(*\5\4\3\2)(\3\2\2\2*+\3\2\2\2+)\3\2\2\2+,\3\2"+
		"\2\2,-\3\2\2\2-.\7\2\2\3.\3\3\2\2\2/\63\5\b\5\2\60\63\5\n\6\2\61\63\5"+
		"\6\4\2\62/\3\2\2\2\62\60\3\2\2\2\62\61\3\2\2\2\63\5\3\2\2\2\64\65\7\r"+
		"\2\2\65\66\5\f\7\2\66\67\7\32\2\2\67\7\3\2\2\28;\5\26\f\29;\5\32\16\2"+
		":8\3\2\2\2:9\3\2\2\2;\t\3\2\2\2<=\7\21\2\2=>\7$\2\2>?\7#\2\2?@\5\f\7\2"+
		"@A\7\32\2\2A\13\3\2\2\2BF\5&\24\2CF\5\24\13\2DF\5\16\b\2EB\3\2\2\2EC\3"+
		"\2\2\2ED\3\2\2\2F\r\3\2\2\2GH\5 \21\2HJ\7\33\2\2IK\5\20\t\2JI\3\2\2\2"+
		"JK\3\2\2\2KL\3\2\2\2LM\7\34\2\2M\17\3\2\2\2NS\5\22\n\2OP\7\6\2\2PR\5\22"+
		"\n\2QO\3\2\2\2RU\3\2\2\2SQ\3\2\2\2ST\3\2\2\2TW\3\2\2\2US\3\2\2\2VX\7\6"+
		"\2\2WV\3\2\2\2WX\3\2\2\2X\21\3\2\2\2YZ\7$\2\2Z[\t\2\2\2[\\\5\f\7\2\\\23"+
		"\3\2\2\2]`\7$\2\2^_\7\"\2\2_a\7$\2\2`^\3\2\2\2ab\3\2\2\2b`\3\2\2\2bc\3"+
		"\2\2\2c\25\3\2\2\2de\7\16\2\2ef\7$\2\2fh\7\37\2\2gi\5\30\r\2hg\3\2\2\2"+
		"hi\3\2\2\2ij\3\2\2\2ju\7 \2\2kl\7\16\2\2lm\7$\2\2mn\7\20\2\2no\7$\2\2"+
		"oq\7\37\2\2pr\5\30\r\2qp\3\2\2\2qr\3\2\2\2rs\3\2\2\2su\7 \2\2td\3\2\2"+
		"\2tk\3\2\2\2u\27\3\2\2\2vw\7$\2\2wy\7\32\2\2xv\3\2\2\2yz\3\2\2\2zx\3\2"+
		"\2\2z{\3\2\2\2{\31\3\2\2\2|}\7\23\2\2}\177\7$\2\2~\u0080\5\"\22\2\177"+
		"~\3\2\2\2\177\u0080\3\2\2\2\u0080\u0081\3\2\2\2\u0081\u0083\7\37\2\2\u0082"+
		"\u0084\5\34\17\2\u0083\u0082\3\2\2\2\u0083\u0084\3\2\2\2\u0084\u0085\3"+
		"\2\2\2\u0085\u0094\7 \2\2\u0086\u0087\7\23\2\2\u0087\u0089\7$\2\2\u0088"+
		"\u008a\5\"\22\2\u0089\u0088\3\2\2\2\u0089\u008a\3\2\2\2\u008a\u008b\3"+
		"\2\2\2\u008b\u008c\7\20\2\2\u008c\u008d\5$\23\2\u008d\u008f\7\37\2\2\u008e"+
		"\u0090\5\34\17\2\u008f\u008e\3\2\2\2\u008f\u0090\3\2\2\2\u0090\u0091\3"+
		"\2\2\2\u0091\u0092\7 \2\2\u0092\u0094\3\2\2\2\u0093|\3\2\2\2\u0093\u0086"+
		"\3\2\2\2\u0094\33\3\2\2\2\u0095\u0096\7$\2\2\u0096\u0097\7\26\2\2\u0097"+
		"\u009a\5\36\20\2\u0098\u0099\t\2\2\2\u0099\u009b\5\f\7\2\u009a\u0098\3"+
		"\2\2\2\u009a\u009b\3\2\2\2\u009b\u009c\3\2\2\2\u009c\u009d\7\32\2\2\u009d"+
		"\u009f\3\2\2\2\u009e\u0095\3\2\2\2\u009f\u00a0\3\2\2\2\u00a0\u009e\3\2"+
		"\2\2\u00a0\u00a1\3\2\2\2\u00a1\35\3\2\2\2\u00a2\u00a7\5 \21\2\u00a3\u00a4"+
		"\7!\2\2\u00a4\u00a6\5 \21\2\u00a5\u00a3\3\2\2\2\u00a6\u00a9\3\2\2\2\u00a7"+
		"\u00a5\3\2\2\2\u00a7\u00a8\3\2\2\2\u00a8\37\3\2\2\2\u00a9\u00a7\3\2\2"+
		"\2\u00aa\u00b8\7\7\2\2\u00ab\u00b8\7\b\2\2\u00ac\u00ae\7\t\2\2\u00ad\u00af"+
		"\5\"\22\2\u00ae\u00ad\3\2\2\2\u00ae\u00af\3\2\2\2\u00af\u00b8\3\2\2\2"+
		"\u00b0\u00b2\7\n\2\2\u00b1\u00b3\5\"\22\2\u00b2\u00b1\3\2\2\2\u00b2\u00b3"+
		"\3\2\2\2\u00b3\u00b8\3\2\2\2\u00b4\u00b8\7\13\2\2\u00b5\u00b8\7\f\2\2"+
		"\u00b6\u00b8\5$\23\2\u00b7\u00aa\3\2\2\2\u00b7\u00ab\3\2\2\2\u00b7\u00ac"+
		"\3\2\2\2\u00b7\u00b0\3\2\2\2\u00b7\u00b4\3\2\2\2\u00b7\u00b5\3\2\2\2\u00b7"+
		"\u00b6\3\2\2\2\u00b8!\3\2\2\2\u00b9\u00ba\7\24\2\2\u00ba\u00bb\5 \21\2"+
		"\u00bb\u00bc\7\25\2\2\u00bc#\3\2\2\2\u00bd\u00bf\7$\2\2\u00be\u00c0\5"+
		"\"\22\2\u00bf\u00be\3\2\2\2\u00bf\u00c0\3\2\2\2\u00c0%\3\2\2\2\u00c1\u00cb"+
		"\7)\2\2\u00c2\u00cb\7%\2\2\u00c3\u00cb\7&\2\2\u00c4\u00cb\7\5\2\2\u00c5"+
		"\u00c6\7\22\2\2\u00c6\u00c7\7\33\2\2\u00c7\u00c8\7)\2\2\u00c8\u00cb\7"+
		"\34\2\2\u00c9\u00cb\7$\2\2\u00ca\u00c1\3\2\2\2\u00ca\u00c2\3\2\2\2\u00ca"+
		"\u00c3\3\2\2\2\u00ca\u00c4\3\2\2\2\u00ca\u00c5\3\2\2\2\u00ca\u00c9\3\2"+
		"\2\2\u00cb\'\3\2\2\2\33+\62:EJSWbhqtz\177\u0083\u0089\u008f\u0093\u009a"+
		"\u00a0\u00a7\u00ae\u00b2\u00b7\u00bf\u00ca";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}