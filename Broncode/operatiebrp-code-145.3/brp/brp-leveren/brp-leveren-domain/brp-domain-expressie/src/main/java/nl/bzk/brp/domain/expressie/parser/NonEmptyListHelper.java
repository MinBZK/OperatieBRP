/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.parser;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.LijstExpressie;
import nl.bzk.brp.domain.expressie.parser.antlr.BRPExpressietaalParser;
import nl.bzk.brp.domain.expressie.parser.antlr.BRPExpressietaalVisitor;

/**
 * Parser delegate.
 */
final class NonEmptyListHelper {

    private NonEmptyListHelper() {
    }

    /**
     * Maakt een resultaatexpressie.
     *
     * @param ctx     de parsercontext
     * @param visitor de parservisitor
     * @return het resultaat
     */
    static Expressie visitNonEmptyList(final BRPExpressietaalParser.NonEmptyListContext ctx, final BRPExpressietaalVisitor<Expressie> visitor) {
        final List<BRPExpressietaalParser.ExpContext> elements = ctx.exp();
        final List<Expressie> lijst = new ArrayList<>();
        if (elements != null) {
            for (final BRPExpressietaalParser.ExpContext e : elements) {
                lijst.add(visitor.visit(e));
            }
        }
        return new LijstExpressie(lijst);
    }
}
