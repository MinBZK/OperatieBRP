/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.parser;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import nl.bzk.brp.domain.expressie.BooleanLiteral;
import nl.bzk.brp.domain.expressie.ClosureExpressie;
import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.ElementnaamLiteral;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.GetalLiteral;
import nl.bzk.brp.domain.expressie.LijstExpressie;
import nl.bzk.brp.domain.expressie.NullLiteral;
import nl.bzk.brp.domain.expressie.StringLiteral;
import nl.bzk.brp.domain.expressie.VariabeleExpressie;
import nl.bzk.brp.domain.expressie.functie.FunctieExpressie;
import nl.bzk.brp.domain.expressie.parser.antlr.BRPExpressietaalBaseVisitor;
import nl.bzk.brp.domain.expressie.parser.antlr.BRPExpressietaalParser;

/**
 * Visitor voor de parse tree die ANTLR heeft gegenereerd.
 */
final class ExpressieVisitor extends BRPExpressietaalBaseVisitor<Expressie> {

    private Context context;

    /**
     * Constructor.
     * @param aInitialContext De context van gedefinieerde identifier waarmee de visitor begint. In de regel zal hierin de variabele 'persoon' zijn
     * gedefineerd.
     */
    ExpressieVisitor(final Context aInitialContext) {
        this.context = aInitialContext;
    }

    @Override
    public Expressie visitClosure(final BRPExpressietaalParser.ClosureContext ctx) {
        final Expressie result;
        if (ctx.assignments() == null) {
            result = visit(ctx.booleanExp());
        } else {
            // Verzamel alle assignments en voeg ze toe aan de context van de closure (closureContext).
            final Context closureContext = new Context();
            for (final BRPExpressietaalParser.AssignmentContext assignment : ctx.assignments().assignment()) {
                final Expressie value = visit(assignment.exp());
                closureContext.definieer(assignment.variable().getText(), value);
            }

            // De gedefinieerde variabelen zijn ook bruikbaar in de expressie van de closure en dus moeten ze voor
            // de parser beschikbaar zijn in een context.
            context = new Context(context);
            for (final String identifier : closureContext.identifiers()) {
                final Expressie value = closureContext.zoekWaarde(identifier);
                context.declareer(identifier, value.getType(null));
            }
            final Expressie body = visit(ctx.booleanExp());
            context = context.getParent();

            // Het resultaat is een ClosureExpressie met de verzamelde assignments en de vertaalde body.
            result = new ClosureExpressie(body, closureContext);
        }
        return result;
    }

    @Override
    public Expressie visitExistFunction(final BRPExpressietaalParser.ExistFunctionContext ctx) {
        final Expressie result;
        final List<Expressie> argumenten = new ArrayList<>();
        final Expressie lijst = visit(ctx.exp(0));

        argumenten.add(lijst);

        final String variabele = ctx.variable().getText();
        argumenten.add(new VariabeleExpressie(variabele));
        context = new Context(context);
        context.declareer(variabele, ExpressieType.ONBEKEND_TYPE);
        final Expressie body = visit(ctx.exp(1));
        argumenten.add(body);
        context = context.getParent();
        result = new FunctieExpressie(ctx.existFunctionName().getText(), argumenten);

        return result;
    }

    @Override
    public Expressie visitBracketedExp(final BRPExpressietaalParser.BracketedExpContext ctx) {
        return visit(ctx.exp());
    }

    @Override
    public Expressie visitNumericLiteral(final BRPExpressietaalParser.NumericLiteralContext ctx) {
        return new GetalLiteral(Long.parseLong(ctx.getText()));
    }

    @Override
    public Expressie visitNullLiteral(final BRPExpressietaalParser.NullLiteralContext ctx) {
        return NullLiteral.INSTANCE;
    }

    @Override
    public Expressie visitBooleanLiteral(final BRPExpressietaalParser.BooleanLiteralContext ctx) {
        return BooleanLiteral.valueOf(ctx.TRUE_CONSTANT() != null);
    }

    @Override
    public Expressie visitStringLiteral(final BRPExpressietaalParser.StringLiteralContext ctx) {
        return new StringLiteral(ctx.getText().substring(1, ctx.getText().length() - 1));
    }

    @Override
    public Expressie visitDateLiteral(final BRPExpressietaalParser.DateLiteralContext ctx) {
        return DatumParserUtil.visitDateLiteral(ctx);
    }

    @Override
    public Expressie visitDateTimeLiteral(@Nonnull final BRPExpressietaalParser.DateTimeLiteralContext ctx) {
        return DatumParserUtil.visitDateTimeLiteral(ctx);
    }

    @Override
    public Expressie visitPeriodLiteral(final BRPExpressietaalParser.PeriodLiteralContext ctx) {
        return DatumParserUtil.visitPeriodLiteral(ctx);
    }

    @Override
    public Expressie visitEmptyList(final BRPExpressietaalParser.EmptyListContext ctx) {
        return new LijstExpressie();
    }

    @Override
    public Expressie visitNonEmptyList(final BRPExpressietaalParser.NonEmptyListContext ctx) {
        return NonEmptyListHelper.visitNonEmptyList(ctx, this);
    }

    @Override
    public Expressie visitNegatableExpression(final BRPExpressietaalParser.NegatableExpressionContext ctx) {
        return NegatableExpressionHelper.visitNegatableExpression(ctx, this);
    }

    @Override
    public Expressie visitOrdinalExpression(final BRPExpressietaalParser.OrdinalExpressionContext ctx) {
        return OrdinalExpressionHelper.visitOrdinalExpression(ctx, this);
    }

    @Override
    public Expressie visitRelationalExpression(final BRPExpressietaalParser.RelationalExpressionContext ctx) {
        return RelationalExpressionHelper.maakExpressie(ctx, this);
    }

    @Override
    public Expressie visitEqualityExpression(final BRPExpressietaalParser.EqualityExpressionContext ctx) {
        return EqualityExpressionHelper.visitEqualityExpression(ctx, this);
    }

    @Override
    public Expressie visitBooleanTerm(final BRPExpressietaalParser.BooleanTermContext ctx) {
        return BooleanTermHelper.visitBooleanTerm(ctx, this);
    }

    @Override
    public Expressie visitBooleanExp(final BRPExpressietaalParser.BooleanExpContext ctx) {
        return BooleanExpressionHelper.visitBooleanExp(ctx, this);
    }

    @Override
    public Expressie visitFunction(final BRPExpressietaalParser.FunctionContext ctx) {
        return FunctionHelper.visitFunction(ctx, this);
    }

    @Override
    public Expressie visitElement(final BRPExpressietaalParser.ElementContext ctx) {
        return ElementHelper.visitElement(ctx, context);
    }

    @Override
    public Expressie visitElementCodeLiteral(final BRPExpressietaalParser.ElementCodeLiteralContext ctx) {
        return new ElementnaamLiteral(ctx.element_path().getText());
    }
}
