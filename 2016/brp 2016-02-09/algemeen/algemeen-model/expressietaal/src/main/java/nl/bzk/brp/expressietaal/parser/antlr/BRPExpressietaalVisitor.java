/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

// Generated from BRPExpressietaal.g4 by ANTLR 4.1

package nl.bzk.brp.expressietaal.parser.antlr;

import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link BRPExpressietaalParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface BRPExpressietaalVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#relationalOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationalOp(@NotNull BRPExpressietaalParser.RelationalOpContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#bracketedExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBracketedExp(@NotNull BRPExpressietaalParser.BracketedExpContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#second}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSecond(@NotNull BRPExpressietaalParser.SecondContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#expressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionList(@NotNull BRPExpressietaalParser.ExpressionListContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#unaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpression(@NotNull BRPExpressietaalParser.UnaryExpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#relativeYear}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelativeYear(@NotNull BRPExpressietaalParser.RelativeYearContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#stringLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringLiteral(@NotNull BRPExpressietaalParser.StringLiteralContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#relativeDay}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelativeDay(@NotNull BRPExpressietaalParser.RelativeDayContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction(@NotNull BRPExpressietaalParser.FunctionContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#periodLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPeriodLiteral(@NotNull BRPExpressietaalParser.PeriodLiteralContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#ordinalOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrdinalOp(@NotNull BRPExpressietaalParser.OrdinalOpContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#year}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitYear(@NotNull BRPExpressietaalParser.YearContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#negatableExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegatableExpression(@NotNull BRPExpressietaalParser.NegatableExpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#periodPart}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPeriodPart(@NotNull BRPExpressietaalParser.PeriodPartContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#negationOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegationOperator(@NotNull BRPExpressietaalParser.NegationOperatorContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#groep}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroep(@NotNull BRPExpressietaalParser.GroepContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#assignments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignments(@NotNull BRPExpressietaalParser.AssignmentsContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#relativeMonth}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelativeMonth(@NotNull BRPExpressietaalParser.RelativeMonthContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#brp_expressie}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBrp_expressie(@NotNull BRPExpressietaalParser.Brp_expressieContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp(@NotNull BRPExpressietaalParser.ExpContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#attribute_path}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttribute_path(@NotNull BRPExpressietaalParser.Attribute_pathContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#month}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMonth(@NotNull BRPExpressietaalParser.MonthContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#closure}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClosure(@NotNull BRPExpressietaalParser.ClosureContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#day}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDay(@NotNull BRPExpressietaalParser.DayContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#functionName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionName(@NotNull BRPExpressietaalParser.FunctionNameContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#dateTimeLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDateTimeLiteral(@NotNull BRPExpressietaalParser.DateTimeLiteralContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#objectIdentifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitObjectIdentifier(@NotNull BRPExpressietaalParser.ObjectIdentifierContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#attribute}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttribute(@NotNull BRPExpressietaalParser.AttributeContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#booleanTerm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanTerm(@NotNull BRPExpressietaalParser.BooleanTermContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#minute}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMinute(@NotNull BRPExpressietaalParser.MinuteContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#relationalExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationalExpression(@NotNull BRPExpressietaalParser.RelationalExpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#existFunctionName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExistFunctionName(@NotNull BRPExpressietaalParser.ExistFunctionNameContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#ordinalExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrdinalExpression(@NotNull BRPExpressietaalParser.OrdinalExpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#numericLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumericLiteral(@NotNull BRPExpressietaalParser.NumericLiteralContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#booleanExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanExp(@NotNull BRPExpressietaalParser.BooleanExpContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#groep_path}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroep_path(@NotNull BRPExpressietaalParser.Groep_pathContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#booleanLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanLiteral(@NotNull BRPExpressietaalParser.BooleanLiteralContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#attributeReference}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttributeReference(@NotNull BRPExpressietaalParser.AttributeReferenceContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#hour}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHour(@NotNull BRPExpressietaalParser.HourContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#emptyList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmptyList(@NotNull BRPExpressietaalParser.EmptyListContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#nullLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNullLiteral(@NotNull BRPExpressietaalParser.NullLiteralContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(@NotNull BRPExpressietaalParser.AssignmentContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#equalityExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExpression(@NotNull BRPExpressietaalParser.EqualityExpressionContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#dateLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDateLiteral(@NotNull BRPExpressietaalParser.DateLiteralContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#groepReference}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGroepReference(@NotNull BRPExpressietaalParser.GroepReferenceContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#existFunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExistFunction(@NotNull BRPExpressietaalParser.ExistFunctionContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#monthName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMonthName(@NotNull BRPExpressietaalParser.MonthNameContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#attributeCodeLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttributeCodeLiteral(@NotNull BRPExpressietaalParser.AttributeCodeLiteralContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#nonEmptyList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNonEmptyList(@NotNull BRPExpressietaalParser.NonEmptyListContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#variable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable(@NotNull BRPExpressietaalParser.VariableContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#equalityOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityOp(@NotNull BRPExpressietaalParser.EqualityOpContext ctx);

	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(@NotNull BRPExpressietaalParser.LiteralContext ctx);
}