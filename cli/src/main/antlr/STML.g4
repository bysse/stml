grammar STML;

statement_list
    : statement+ EOF
    ;

statement
    : type_declaration_statement
    | assignment_statement
    | emit_statement
    | NL+
    ;

emit_statement
    : Emit value csonl?
    ;

type_declaration_statement
    : enum_type_declaration
    | struct_type_declaration
    ;

assignment_statement
    : Let IDENTIFIER EQUAL value csonl?
    ;

// Constructions and variables
value
    : constant
    | type_field
    | type_constructor
    ;

type_constructor
    : InlineImport LeftParen NL* key_value_list RightParen                  # type_constructor_import
    | type_specifier LeftParen NL* key_value_list? RightParen               # type_constructor_map
    | type_specifier LeftParen NL* value_list? RightParen                   # type_constructor_list
    | template_type_specifier? LeftBrace NL* key_value_list? RightBrace     # type_constructor_map_short
    | template_type_specifier? LeftBracket NL* value_list? RightBracket     # type_constructor_list_short
    ;

key_value_list
    : key_value (csonl key_value)* csonl?
    ;

key_value
    : any_identifier (COLON | EQUAL) value
    ;

value_list
    : value (csonl value)* csonl?
    ;

type_field
    : IDENTIFIER (DOT any_identifier)+
    ;

// Enum
enum_type_declaration
    : Enum IDENTIFIER NL* LeftBrace NL* enum_type_member_list? RightBrace
    | Enum IDENTIFIER (Extends|LT) IDENTIFIER NL* LeftBrace NL* enum_type_member_list? RightBrace
    ;

enum_type_member_list
    : IDENTIFIER ( csonl IDENTIFIER )* csonl?
    ;

// Struct
struct_type_declaration
    : Struct IDENTIFIER NL* LeftBrace NL* struct_type_member_list? RightBrace
    | Struct IDENTIFIER (Extends|LT) IDENTIFIER NL* LeftBrace NL* struct_type_member_list? RightBrace
    ;
struct_type_member_list
    : struct_type_member (csonl struct_type_member)* csonl?
    ;

struct_type_member
    : any_identifier QUESTION? COLON type_specifier_list ((COLON | EQUAL) value)?
    | any_identifier QUESTION? (COLON | EQUAL) value
    | any_identifier COLON CONST ((COLON | EQUAL) value)
    ;

// Type specifiers
type_specifier_list
    : type_specifier ( OR type_specifier )*
    ;

type_specifier
    : ANY
    | BOOLEAN
    | LIST template_type_specifier?
    | MAP template_type_specifier?
    | NULL
    | NUMBER
    | INTEGER
    | STRING (LT REGEX GT)?
    | IDENTIFIER
    ;

template_type_specifier
    : LT type_specifier GT
    ;

any_identifier : IDENTIFIER | StringConstant;

constant
    : StringConstant                                        # ParseString
    | IntegerConstant                                       # ParseInteger
    | FractionalConstant                                    # ParseFloat
    | BooleanConstant                                       # ParseBoolean
    | InlineImport LeftParen StringConstant RightParen      # ParseInlineImport
    | NULL                                                  # ParseNull
    | IDENTIFIER                                            # ParseVariableUse
    ;

csonl // comma, semicolon or new line
    : (SEPARATOR NL*) | NL+
    ;

BooleanConstant
    : TRUE | FALSE
    ;

SEPARATOR : SEMICOLON | COMMA;

ANY             : 'any' | 'Any' | '*';
BOOLEAN         : 'bool' | 'Bool' | 'boolean' | 'Boolean' ;
CONST           : 'const';
LIST            : 'List';
MAP             : 'Map' ;
NUMBER          : 'number' | 'Number' ;
INTEGER         : 'int' | 'Integer' ;
STRING          : 'string' | 'String' ;
NULL            : 'null' | 'NULL';

Emit            : 'emit';
Enum            : 'enum';
Extends         : 'extends';
Let             : 'let';
InlineImport    : 'import';
Struct          : 'struct';

LT              : '<';
GT              : '>';
COLON           : ':';

TRUE            : 'true' | 'True' | 'yes' | 'Yes' | 'on' | 'On';
FALSE           : 'false' | 'False' | 'no' | 'No' | 'off' | 'Off';

COMMA           : ',';
SEMICOLON       : ';';
LeftParen       : '(';
RightParen      : ')';
LeftBracket     : '[';
RightBracket    : ']';
LeftBrace       : '{';
RightBrace      : '}';
OR              : '|';
DOT             : '.';
EQUAL           : '=';
QUESTION        : '?';

REGEX
    : '/' ('\\/' | ~('/'))+? '/'
    ;

IDENTIFIER
    : Nondigit ( Nondigit | Digit)*
    ;

fragment Nondigit       : [a-zA-Z_/-];
fragment NonZeroDigit   : [1-9];
fragment Digit          : [0-9];

IntegerConstant
    : DecimalConstant
    | HexadecimalConstant
    ;

FractionalConstant
    :   DigitSequence? '.' DigitSequence
    |   DigitSequence '.'
    ;

fragment Sign
    :   '+' | '-'
    ;

DigitSequence
    :   Digit+
    ;

DecimalConstant
    :   [1-9] Digit*
    ;

fragment HexadecimalConstant
    :   '0' [xX] [0-9a-fA-F]+
    ;

fragment ESCAPED_QUOTE : '\\"';
StringConstant
    :   '"' ( ESCAPED_QUOTE | ~('\n'|'\r') )*? '"'
    ;

Whitespace
    :   [ \t]+
        -> channel(HIDDEN)
    ;


NL
    :   (   '\r' '\n'? |   '\n' )+
//        -> skip
    ;


BlockComment
    :   '/*' .*? '*/'
        -> channel(HIDDEN)
    ;

LineComment
    :   '//' ~[\r\n]*
        -> channel(HIDDEN)
    ;
