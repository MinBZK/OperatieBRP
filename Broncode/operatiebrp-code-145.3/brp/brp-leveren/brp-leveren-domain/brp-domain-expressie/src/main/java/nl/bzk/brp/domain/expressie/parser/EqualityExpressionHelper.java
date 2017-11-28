/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.parser;

import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.OperatorType;
import nl.bzk.brp.domain.expressie.operator.VergelijkingOperator;
import nl.bzk.brp.domain.expressie.parser.antlr.BRPExpressietaalParser;
import nl.bzk.brp.domain.expressie.parser.antlr.BRPExpressietaalVisitor;
import nl.bzk.brp.domain.expressie.parser.exception.ExpressieParseException;
import nl.bzk.brp.domain.expressie.parser.exception.ParserFout;

/**
 * Parser delegate.
 */
final class EqualityExpressionHelper {

    private EqualityExpressionHelper() {

    }

    /**
     * Maakt een resultaatexpressie.
     *
     * @param ctx     de parsercontext
     * @param visitor de parservisitor
     * @return het resultaat
     */
    static Expressie visitEqualityExpression(final BRPExpressietaalParser.EqualityExpressionContext ctx, final BRPExpressietaalVisitor<Expressie> visitor) {
        final Expressie result;
        final Expressie left = visitor.visit(ctx.relationalExpression(0));
        if (ctx.equalityOp() != null) {
            final Expressie right = visitor.visit(ctx.relationalExpression(1));
            ParserUtils.checkComparedTypes(left, right, parserFoutCode -> {
                throw new ExpressieParseException(new ParserFout(parserFoutCode, ctx.relationalExpression(1).getText(),
                        ctx.relationalExpression(1).getStart().getStartIndex()));
            });
            result = new VergelijkingOperator(left, right, OperatorType.parseWaarde(ctx.equalityOp().getText()));
        } else {
            result = left;
        }
        return result;
    }
}
