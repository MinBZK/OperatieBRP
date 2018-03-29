/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.parser;

import java.util.function.Function;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.operator.OfOperator;
import nl.bzk.brp.domain.expressie.parser.antlr.BRPExpressietaalParser;
import nl.bzk.brp.domain.expressie.parser.antlr.BRPExpressietaalVisitor;
import nl.bzk.brp.domain.expressie.parser.exception.ExpressieParseException;
import nl.bzk.brp.domain.expressie.parser.exception.ParserFout;
import nl.bzk.brp.domain.expressie.parser.exception.ParserFoutCode;

/**
 * Parser delegate.
 */
final class BooleanExpressionHelper {

    private BooleanExpressionHelper() {
    }

    /**
     * Maakt een resultaatexpressie.
     *
     * @param ctx     de parsercontext
     * @param visitor de parservisitor
     * @return het resultaat
     */
    static Expressie visitBooleanExp(final BRPExpressietaalParser.BooleanExpContext ctx, final BRPExpressietaalVisitor<Expressie> visitor) {
        final Expressie left = visitor.visit(ctx.booleanTerm());
        if (ctx.booleanExp() == null) {
            return left;
        }
        final Expressie result;
        final Expressie right = visitor.visit(ctx.booleanExp());
        final Function<ParserFoutCode, ExpressieParseException> foutHandler = parserFoutCode -> {
            throw new ExpressieParseException(new ParserFout(parserFoutCode, ctx.booleanExp().getText(),
                    ctx.booleanExp().getStart().getStartIndex()));
        };
        ParserUtils.checkType(left, ExpressieType.BOOLEAN, foutHandler);
        ParserUtils.checkType(right, ExpressieType.BOOLEAN, foutHandler);
        result = new OfOperator(left, right);
        return result;
    }
}
