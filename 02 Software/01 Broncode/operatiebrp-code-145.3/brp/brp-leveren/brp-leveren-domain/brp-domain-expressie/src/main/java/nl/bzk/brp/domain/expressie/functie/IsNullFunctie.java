/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

import java.util.List;
import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.BooleanLiteral;
import nl.bzk.brp.domain.expressie.signatuur.SimpeleSignatuur;
import org.springframework.stereotype.Component;

/**
 * Functie IS_NULL(E) die test of een expressie een null-waarde is.
 */
@Component
@FunctieKeyword("IS_NULL")
final class IsNullFunctie extends AbstractFunctie {

    /**
     * Constructor voor de functie.
     */
    IsNullFunctie() {
        super(new SimpeleSignatuur(ExpressieType.ONBEKEND_TYPE));
    }

    @Override
    public Expressie evalueer(final List<Expressie> argumenten, final Context context) {
        final Expressie argument = argumenten.get(0).evalueer(context);
        return BooleanLiteral.valueOf(argument.isNull());
    }

    @Override
    public ExpressieType getType(final List<Expressie> argumenten, final Context context) {
        return ExpressieType.BOOLEAN;
    }

    @Override
    public boolean evalueerArgumenten() {
        return false;
    }
}
