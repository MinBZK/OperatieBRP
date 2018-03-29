/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.parser;

import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.OperatorType;
import nl.bzk.brp.domain.expressie.operator.RekenOperator;
import nl.bzk.brp.domain.expressie.parser.antlr.BRPExpressietaalParser;
import nl.bzk.brp.domain.expressie.parser.antlr.BRPExpressietaalVisitor;

/**
 * Parser delegate.
 */
final class OrdinalExpressionHelper {

    private OrdinalExpressionHelper() {
    }

    /**
     * Maakt een resultaatexpressie.
     * @param ctx de parsercontext
     * @param visitor de parservisitor
     * @return het resultaat
     */
    static Expressie visitOrdinalExpression(final BRPExpressietaalParser.OrdinalExpressionContext ctx,
                                            final BRPExpressietaalVisitor<Expressie> visitor) {
        final Expressie left = visitor.visit(ctx.negatableExpression());
        if (ctx.ordinalOp() == null) {
            return left;
        }
        final Expressie right = visitor.visit(ctx.ordinalExpression());
        ParserUtils.assertOrdinalType(left, ctx);
        ParserUtils.assertOrdinalType(right, ctx);
        return new RekenOperator(left, right, ctx.ordinalOp().OP_PLUS() != null ? OperatorType.PLUS : OperatorType.MIN);
    }
}
