// Generated from /home/erikb/dev/projects/stml/src/main/antlr/STMLParser.g4 by ANTLR 4.8
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link STMLParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface STMLParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link STMLParser#statement_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement_list(STMLParser.Statement_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link STMLParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(STMLParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link STMLParser#emit_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmit_statement(STMLParser.Emit_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link STMLParser#type_declaration_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType_declaration_statement(STMLParser.Type_declaration_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link STMLParser#assignment_statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment_statement(STMLParser.Assignment_statementContext ctx);
	/**
	 * Visit a parse tree produced by {@link STMLParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(STMLParser.ValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link STMLParser#type_constructor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType_constructor(STMLParser.Type_constructorContext ctx);
	/**
	 * Visit a parse tree produced by {@link STMLParser#type_argument_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType_argument_list(STMLParser.Type_argument_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link STMLParser#type_argument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType_argument(STMLParser.Type_argumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link STMLParser#type_field}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType_field(STMLParser.Type_fieldContext ctx);
	/**
	 * Visit a parse tree produced by {@link STMLParser#enum_type_declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnum_type_declaration(STMLParser.Enum_type_declarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link STMLParser#enum_type_member_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnum_type_member_list(STMLParser.Enum_type_member_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link STMLParser#struct_type_declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStruct_type_declaration(STMLParser.Struct_type_declarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link STMLParser#struct_type_member_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStruct_type_member_list(STMLParser.Struct_type_member_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link STMLParser#type_specifier_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType_specifier_list(STMLParser.Type_specifier_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link STMLParser#type_specifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType_specifier(STMLParser.Type_specifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link STMLParser#template_type_specifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTemplate_type_specifier(STMLParser.Template_type_specifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link STMLParser#custom_type_specifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCustom_type_specifier(STMLParser.Custom_type_specifierContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParseString}
	 * labeled alternative in {@link STMLParser#constant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParseString(STMLParser.ParseStringContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParseInteger}
	 * labeled alternative in {@link STMLParser#constant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParseInteger(STMLParser.ParseIntegerContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParseFloat}
	 * labeled alternative in {@link STMLParser#constant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParseFloat(STMLParser.ParseFloatContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParseBoolean}
	 * labeled alternative in {@link STMLParser#constant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParseBoolean(STMLParser.ParseBooleanContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParseInlineImport}
	 * labeled alternative in {@link STMLParser#constant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParseInlineImport(STMLParser.ParseInlineImportContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ParseVariableUse}
	 * labeled alternative in {@link STMLParser#constant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParseVariableUse(STMLParser.ParseVariableUseContext ctx);
}