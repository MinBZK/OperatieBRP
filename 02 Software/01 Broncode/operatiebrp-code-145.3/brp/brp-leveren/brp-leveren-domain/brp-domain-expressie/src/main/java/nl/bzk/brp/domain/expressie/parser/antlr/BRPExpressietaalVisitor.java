/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

// Generated from BRPExpressietaal.g4 by ANTLR 4.7

package nl.bzk.brp.domain.expressie.parser.antlr;

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
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#brp_expressie}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBrp_expressie(BRPExpressietaalParser.Brp_expressieContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#exp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExp(BRPExpressietaalParser.ExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#closure}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClosure(BRPExpressietaalParser.ClosureContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#assignments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignments(BRPExpressietaalParser.AssignmentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(BRPExpressietaalParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#booleanExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanExp(BRPExpressietaalParser.BooleanExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#booleanTerm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanTerm(BRPExpressietaalParser.BooleanTermContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#equalityExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExpression(BRPExpressietaalParser.EqualityExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#equalityOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityOp(BRPExpressietaalParser.EqualityOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#relationalExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationalExpression(BRPExpressietaalParser.RelationalExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#relationalOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationalOp(BRPExpressietaalParser.RelationalOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#collectionEOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCollectionEOp(BRPExpressietaalParser.CollectionEOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#collectionAOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCollectionAOp(BRPExpressietaalParser.CollectionAOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#ordinalExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrdinalExpression(BRPExpressietaalParser.OrdinalExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#ordinalOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrdinalOp(BRPExpressietaalParser.OrdinalOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#negatableExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegatableExpression(BRPExpressietaalParser.NegatableExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#negationOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegationOperator(BRPExpressietaalParser.NegationOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#unaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpression(BRPExpressietaalParser.UnaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#bracketedExp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBracketedExp(BRPExpressietaalParser.BracketedExpContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#expressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionList(BRPExpressietaalParser.ExpressionListContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#emptyList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmptyList(BRPExpressietaalParser.EmptyListContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#nonEmptyList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNonEmptyList(BRPExpressietaalParser.NonEmptyListContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#element}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElement(BRPExpressietaalParser.ElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#variable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable(BRPExpressietaalParser.VariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction(BRPExpressietaalParser.FunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#functionName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionName(BRPExpressietaalParser.FunctionNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#existFunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExistFunction(BRPExpressietaalParser.ExistFunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#existFunctionName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExistFunctionName(BRPExpressietaalParser.ExistFunctionNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(BRPExpressietaalParser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#stringLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringLiteral(BRPExpressietaalParser.StringLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#booleanLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanLiteral(BRPExpressietaalParser.BooleanLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#numericLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumericLiteral(BRPExpressietaalParser.NumericLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#dateTimeLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDateTimeLiteral(BRPExpressietaalParser.DateTimeLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#dateLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDateLiteral(BRPExpressietaalParser.DateLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#year}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitYear(BRPExpressietaalParser.YearContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#month}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMonth(BRPExpressietaalParser.MonthContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#monthName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMonthName(BRPExpressietaalParser.MonthNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#day}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDay(BRPExpressietaalParser.DayContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#hour}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHour(BRPExpressietaalParser.HourContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#minute}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMinute(BRPExpressietaalParser.MinuteContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#second}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSecond(BRPExpressietaalParser.SecondContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#periodLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPeriodLiteral(BRPExpressietaalParser.PeriodLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#relativeYear}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelativeYear(BRPExpressietaalParser.RelativeYearContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#relativeMonth}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelativeMonth(BRPExpressietaalParser.RelativeMonthContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#relativeDay}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelativeDay(BRPExpressietaalParser.RelativeDayContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#periodPart}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPeriodPart(BRPExpressietaalParser.PeriodPartContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#elementCodeLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementCodeLiteral(BRPExpressietaalParser.ElementCodeLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#nullLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNullLiteral(BRPExpressietaalParser.NullLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link BRPExpressietaalParser#element_path}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElement_path(BRPExpressietaalParser.Element_pathContext ctx);
}