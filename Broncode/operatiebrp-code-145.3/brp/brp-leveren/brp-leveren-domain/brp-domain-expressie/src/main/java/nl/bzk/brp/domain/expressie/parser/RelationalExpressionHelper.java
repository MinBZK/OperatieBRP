/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.parser;

import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.OperatorType;
import nl.bzk.brp.domain.expressie.operator.EAINOperator;
import nl.bzk.brp.domain.expressie.operator.EAINWildcardOperator;
import nl.bzk.brp.domain.expressie.operator.EAOperator;
import nl.bzk.brp.domain.expressie.operator.TypeCollectieoperator;
import nl.bzk.brp.domain.expressie.operator.VergelijkingOperator;
import nl.bzk.brp.domain.expressie.parser.antlr.BRPExpressietaalParser;
import nl.bzk.brp.domain.expressie.parser.antlr.BRPExpressietaalVisitor;
import nl.bzk.brp.domain.expressie.parser.exception.ExpressieParseException;
import nl.bzk.brp.domain.expressie.parser.exception.ParserFout;

/**
 * Hulpklasse voor het visiten van een relational expressie.
 */
final class RelationalExpressionHelper {


    private RelationalExpressionHelper() {

    }

    /**
     * Maakt een resultaatexpressie.
     *
     * @param ctx     de parsercontext
     * @param visitor de parservisitor
     * @return het resultaat
     */
    static Expressie maakExpressie(final BRPExpressietaalParser.RelationalExpressionContext ctx, final BRPExpressietaalVisitor<Expressie> visitor) {
        if (ctx.relationalOp() == null) {
            return visitor.visit(ctx.ordinalExpression(0));
        }
        return maakResultaat(ctx, visitor);
    }

    private static Expressie maakResultaat(final BRPExpressietaalParser.RelationalExpressionContext ctx,
                                           final BRPExpressietaalVisitor<Expressie> visitor) {
        final Expressie result;
        final Expressie left = visitor.visit(ctx.ordinalExpression(0));
        final Expressie right = visitor.visit(ctx.ordinalExpression(1));

        if (ctx.relationalOp().OP_AIN() != null || ctx.relationalOp().OP_EIN() != null) {
            result = maakEAINOperator(ctx, left, right);
        } else if (ctx.relationalOp().OP_AIN_WILDCARD() != null || ctx.relationalOp().OP_EIN_WILDCARD() != null) {
            result = maakEAINWildcardOperator(ctx, left, right);
        } else if (ctx.relationalOp().collectionAOp() != null || ctx.relationalOp().collectionEOp() != null) {
            result = maakEAOperator(ctx, left, right);
        } else {
            ParserUtils.checkComparedTypes(left, right, parserFoutCode -> {
                throw new ExpressieParseException(new ParserFout(parserFoutCode, ctx.ordinalExpression(1).getText(),
                        ctx.ordinalExpression(1).getStart().getStartIndex()));
            });
            result = new VergelijkingOperator(left, right, OperatorType.parseWaarde(ctx.relationalOp().getText()));
        }
        return result;
    }

    private static Expressie maakEAOperator(final BRPExpressietaalParser.RelationalExpressionContext ctx, final Expressie left, final Expressie right) {
        final Expressie result;
        result = new EAOperator(left, right,
                OperatorType.parseWaarde(ctx.relationalOp().getText().substring(1, ctx.relationalOp().getText().length())),
                ctx.relationalOp().collectionAOp() != null ? TypeCollectieoperator.EN : TypeCollectieoperator.OF);
        return result;
    }

    private static Expressie maakEAINWildcardOperator(final BRPExpressietaalParser.RelationalExpressionContext ctx, final Expressie left,
                                                      final Expressie right) {
        final Expressie result;
        result = new EAINWildcardOperator(left, right, ctx.relationalOp().OP_AIN_WILDCARD() != null
                ? TypeCollectieoperator.EN : TypeCollectieoperator.OF);
        return result;
    }

    private static Expressie maakEAINOperator(final BRPExpressietaalParser.RelationalExpressionContext ctx, final Expressie left, final Expressie right) {
        final Expressie result;
        result = new EAINOperator(left, right,
                ctx.relationalOp().OP_AIN() != null ? TypeCollectieoperator.EN : TypeCollectieoperator.OF);
        return result;
    }
}
