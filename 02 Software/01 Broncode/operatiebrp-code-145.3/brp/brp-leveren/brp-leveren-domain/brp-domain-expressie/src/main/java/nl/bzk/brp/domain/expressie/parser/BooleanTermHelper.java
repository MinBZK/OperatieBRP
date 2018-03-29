/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.parser;

import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.operator.EnOperator;
import nl.bzk.brp.domain.expressie.parser.antlr.BRPExpressietaalParser;
import nl.bzk.brp.domain.expressie.parser.antlr.BRPExpressietaalVisitor;
import nl.bzk.brp.domain.expressie.parser.exception.ExpressieParseException;
import nl.bzk.brp.domain.expressie.parser.exception.ParserFout;

/**
 * Parser delegate.
 */
final class BooleanTermHelper {

    private BooleanTermHelper() {

    }

    /**
     * Maakt een resultaatexpressie.
     *
     * @param ctx     de parsercontext
     * @param visitor de parservisitor
     * @return het resultaat
     */
    static Expressie visitBooleanTerm(final BRPExpressietaalParser.BooleanTermContext ctx, final BRPExpressietaalVisitor<Expressie> visitor) {
        final Expressie left = visitor.visit(ctx.equalityExpression());
        if (ctx.booleanTerm() == null) {
            return left;
        }
        final Expressie result;
        final Expressie right = visitor.visit(ctx.booleanTerm());
        ParserUtils.checkType(left, ExpressieType.BOOLEAN, parserFoutCode -> {
            throw new ExpressieParseException(new ParserFout(parserFoutCode, ctx.equalityExpression().getText(),
                    ctx.equalityExpression().getStart().getStartIndex()));
        });
        ParserUtils.checkType(right, ExpressieType.BOOLEAN, parserFoutCode -> {
            throw new ExpressieParseException(new ParserFout(parserFoutCode, ctx.booleanTerm().getText(),
                    ctx.booleanTerm().getStart().getStartIndex()));
        });
        result = new EnOperator(left, right);
        return result;
    }
}
