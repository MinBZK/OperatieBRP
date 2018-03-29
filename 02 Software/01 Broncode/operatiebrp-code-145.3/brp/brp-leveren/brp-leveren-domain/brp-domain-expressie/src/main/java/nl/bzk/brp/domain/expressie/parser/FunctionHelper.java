/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.parser;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.functie.FunctieExpressie;
import nl.bzk.brp.domain.expressie.parser.antlr.BRPExpressietaalParser;
import nl.bzk.brp.domain.expressie.parser.antlr.BRPExpressietaalVisitor;

/**
 * Parser delegate.
 */
final class FunctionHelper {

    private FunctionHelper() {
    }

    /**
     * Maakt een resultaatexpressie.
     *
     * @param ctx     de parsercontext
     * @param visitor de parservisitor
     * @return het resultaat
     */
    static Expressie visitFunction(final BRPExpressietaalParser.FunctionContext ctx, final BRPExpressietaalVisitor<Expressie> visitor) {
        final List<Expressie> argumenten = new ArrayList<>();
        final List<BRPExpressietaalParser.ExpContext> expList = ctx.exp();
        for (final BRPExpressietaalParser.ExpContext e : expList) {
            final Expressie argument = visitor.visit(e);
            argumenten.add(argument);
        }
        return new FunctieExpressie(ctx.functionName().getText(), argumenten);
    }
}
