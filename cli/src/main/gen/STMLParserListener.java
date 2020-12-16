// Generated from /home/erikb/dev/projects/stml/src/main/antlr/STMLParser.g4 by ANTLR 4.8
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link STMLParser}.
 */
public interface STMLParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link STMLParser#statement_list}.
	 * @param ctx the parse tree
	 */
	void enterStatement_list(STMLParser.Statement_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link STMLParser#statement_list}.
	 * @param ctx the parse tree
	 */
	void exitStatement_list(STMLParser.Statement_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link STMLParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(STMLParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link STMLParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(STMLParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link STMLParser#emit_statement}.
	 * @param ctx the parse tree
	 */
	void enterEmit_statement(STMLParser.Emit_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link STMLParser#emit_statement}.
	 * @param ctx the parse tree
	 */
	void exitEmit_statement(STMLParser.Emit_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link STMLParser#type_declaration_statement}.
	 * @param ctx the parse tree
	 */
	void enterType_declaration_statement(STMLParser.Type_declaration_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link STMLParser#type_declaration_statement}.
	 * @param ctx the parse tree
	 */
	void exitType_declaration_statement(STMLParser.Type_declaration_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link STMLParser#assignment_statement}.
	 * @param ctx the parse tree
	 */
	void enterAssignment_statement(STMLParser.Assignment_statementContext ctx);
	/**
	 * Exit a parse tree produced by {@link STMLParser#assignment_statement}.
	 * @param ctx the parse tree
	 */
	void exitAssignment_statement(STMLParser.Assignment_statementContext ctx);
	/**
	 * Enter a parse tree produced by {@link STMLParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(STMLParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link STMLParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(STMLParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link STMLParser#type_constructor}.
	 * @param ctx the parse tree
	 */
	void enterType_constructor(STMLParser.Type_constructorContext ctx);
	/**
	 * Exit a parse tree produced by {@link STMLParser#type_constructor}.
	 * @param ctx the parse tree
	 */
	void exitType_constructor(STMLParser.Type_constructorContext ctx);
	/**
	 * Enter a parse tree produced by {@link STMLParser#type_argument_list}.
	 * @param ctx the parse tree
	 */
	void enterType_argument_list(STMLParser.Type_argument_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link STMLParser#type_argument_list}.
	 * @param ctx the parse tree
	 */
	void exitType_argument_list(STMLParser.Type_argument_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link STMLParser#type_argument}.
	 * @param ctx the parse tree
	 */
	void enterType_argument(STMLParser.Type_argumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link STMLParser#type_argument}.
	 * @param ctx the parse tree
	 */
	void exitType_argument(STMLParser.Type_argumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link STMLParser#type_field}.
	 * @param ctx the parse tree
	 */
	void enterType_field(STMLParser.Type_fieldContext ctx);
	/**
	 * Exit a parse tree produced by {@link STMLParser#type_field}.
	 * @param ctx the parse tree
	 */
	void exitType_field(STMLParser.Type_fieldContext ctx);
	/**
	 * Enter a parse tree produced by {@link STMLParser#enum_type_declaration}.
	 * @param ctx the parse tree
	 */
	void enterEnum_type_declaration(STMLParser.Enum_type_declarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link STMLParser#enum_type_declaration}.
	 * @param ctx the parse tree
	 */
	void exitEnum_type_declaration(STMLParser.Enum_type_declarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link STMLParser#enum_type_member_list}.
	 * @param ctx the parse tree
	 */
	void enterEnum_type_member_list(STMLParser.Enum_type_member_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link STMLParser#enum_type_member_list}.
	 * @param ctx the parse tree
	 */
	void exitEnum_type_member_list(STMLParser.Enum_type_member_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link STMLParser#struct_type_declaration}.
	 * @param ctx the parse tree
	 */
	void enterStruct_type_declaration(STMLParser.Struct_type_declarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link STMLParser#struct_type_declaration}.
	 * @param ctx the parse tree
	 */
	void exitStruct_type_declaration(STMLParser.Struct_type_declarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link STMLParser#struct_type_member_list}.
	 * @param ctx the parse tree
	 */
	void enterStruct_type_member_list(STMLParser.Struct_type_member_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link STMLParser#struct_type_member_list}.
	 * @param ctx the parse tree
	 */
	void exitStruct_type_member_list(STMLParser.Struct_type_member_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link STMLParser#type_specifier_list}.
	 * @param ctx the parse tree
	 */
	void enterType_specifier_list(STMLParser.Type_specifier_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link STMLParser#type_specifier_list}.
	 * @param ctx the parse tree
	 */
	void exitType_specifier_list(STMLParser.Type_specifier_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link STMLParser#type_specifier}.
	 * @param ctx the parse tree
	 */
	void enterType_specifier(STMLParser.Type_specifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link STMLParser#type_specifier}.
	 * @param ctx the parse tree
	 */
	void exitType_specifier(STMLParser.Type_specifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link STMLParser#template_type_specifier}.
	 * @param ctx the parse tree
	 */
	void enterTemplate_type_specifier(STMLParser.Template_type_specifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link STMLParser#template_type_specifier}.
	 * @param ctx the parse tree
	 */
	void exitTemplate_type_specifier(STMLParser.Template_type_specifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link STMLParser#custom_type_specifier}.
	 * @param ctx the parse tree
	 */
	void enterCustom_type_specifier(STMLParser.Custom_type_specifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link STMLParser#custom_type_specifier}.
	 * @param ctx the parse tree
	 */
	void exitCustom_type_specifier(STMLParser.Custom_type_specifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParseString}
	 * labeled alternative in {@link STMLParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterParseString(STMLParser.ParseStringContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParseString}
	 * labeled alternative in {@link STMLParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitParseString(STMLParser.ParseStringContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParseInteger}
	 * labeled alternative in {@link STMLParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterParseInteger(STMLParser.ParseIntegerContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParseInteger}
	 * labeled alternative in {@link STMLParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitParseInteger(STMLParser.ParseIntegerContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParseFloat}
	 * labeled alternative in {@link STMLParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterParseFloat(STMLParser.ParseFloatContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParseFloat}
	 * labeled alternative in {@link STMLParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitParseFloat(STMLParser.ParseFloatContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParseBoolean}
	 * labeled alternative in {@link STMLParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterParseBoolean(STMLParser.ParseBooleanContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParseBoolean}
	 * labeled alternative in {@link STMLParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitParseBoolean(STMLParser.ParseBooleanContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParseInlineImport}
	 * labeled alternative in {@link STMLParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterParseInlineImport(STMLParser.ParseInlineImportContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParseInlineImport}
	 * labeled alternative in {@link STMLParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitParseInlineImport(STMLParser.ParseInlineImportContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParseVariableUse}
	 * labeled alternative in {@link STMLParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterParseVariableUse(STMLParser.ParseVariableUseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParseVariableUse}
	 * labeled alternative in {@link STMLParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitParseVariableUse(STMLParser.ParseVariableUseContext ctx);
}