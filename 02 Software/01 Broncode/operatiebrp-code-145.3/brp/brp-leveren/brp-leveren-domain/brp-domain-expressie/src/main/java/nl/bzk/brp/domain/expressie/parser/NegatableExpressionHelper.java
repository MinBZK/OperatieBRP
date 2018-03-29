/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.parser;

import java.util.function.Function;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.GetalLiteral;
import nl.bzk.brp.domain.expressie.operator.LogischeInverseOperator;
import nl.bzk.brp.domain.expressie.operator.NumeriekeInverseOperator;
import nl.bzk.brp.domain.expressie.parser.antlr.BRPExpressietaalParser;
import nl.bzk.brp.domain.expressie.parser.antlr.BRPExpressietaalVisitor;
import nl.bzk.brp.domain.expressie.parser.exception.ExpressieParseException;
import nl.bzk.brp.domain.expressie.parser.exception.ParserFout;
import nl.bzk.brp.domain.expressie.parser.exception.ParserFoutCode;

/**
 * Parser delegate.
 */
final class NegatableExpressionHelper {

    private NegatableExpressionHelper() {
    }

    /**
     * Maakt een resultaatexpressie.
     *
     * @param ctx     de parsercontext
     * @param visitor de parservisitor
     * @return het resultaat
     */
    static Expressie visitNegatableExpression(final BRPExpressietaalParser.NegatableExpressionContext ctx,
                                              final BRPExpressietaalVisitor<Expressie> visitor) {
        final Expressie exp = visitor.visit(ctx.unaryExpression());
        if (ctx.negationOperator() == null) {
            return exp;
        }
        final Expressie result;
        final Function<ParserFoutCode, ExpressieParseException> foutHandler = parserFoutCode -> {
            throw new ExpressieParseException(new ParserFout(parserFoutCode, ctx.unaryExpression().getText(),
                    ctx.unaryExpression().getStart().getStartIndex()));
        };

        if (ctx.negationOperator().OP_MINUS() != null) {
            // Een expressie die begint met een minteken(operator) vereist een getal als operand.
            ParserUtils.assertExpressieTypeGelijkAan(foutHandler, exp, ExpressieType.GETAL);
            if (exp.isConstanteWaarde(ExpressieType.GETAL)) {
                // Als de expressie een constante waarde is, kan het resultaat ook een constante waarde zijn
                // (de negatieve waarde van de constante).
                result = new GetalLiteral(-((GetalLiteral) exp).getWaarde());
            } else {
                // Getal kan pas evaluatietijd bepaald worden
                result = new NumeriekeInverseOperator(exp);
            }
        } else {
            // Een expressie die begint met de logische ontkenning vereist een boolean expressie als operand.
            ParserUtils.assertExpressieTypeGelijkAan(foutHandler, exp, ExpressieType.BOOLEAN);
            result = new LogischeInverseOperator(exp);
        }
        return result;
    }

}
