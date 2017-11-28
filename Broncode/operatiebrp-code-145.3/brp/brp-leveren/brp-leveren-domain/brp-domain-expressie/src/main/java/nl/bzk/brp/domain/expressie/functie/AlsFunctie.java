/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

import java.util.List;
import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieRuntimeException;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.NullLiteral;
import nl.bzk.brp.domain.expressie.signatuur.SimpeleSignatuur;
import org.springframework.stereotype.Component;

/**
 * Representeert de functie ALS(C,T,F). Als conditie C waar is, geeft de functie T terug, anders F.
 */
@Component
@FunctieKeyword("ALS")
final class AlsFunctie extends AbstractFunctie {

    private static final int INDEX_2 = 2;

    /**
     * Constructor voor de functie.
     */
    AlsFunctie() {
        super(new SimpeleSignatuur(ExpressieType.BOOLEAN, ExpressieType.ONBEKEND_TYPE, ExpressieType.ONBEKEND_TYPE));
    }

    @Override
    public Expressie evalueer(final List<Expressie> argumenten, final Context context) {
        final Expressie result;
        final Expressie conditie = argumenten.get(0).evalueer(context);
        if (conditie.isNull()) {
            result = conditie;
        } else if (conditie.isConstanteWaarde(ExpressieType.BOOLEAN, context)) {
            getSignatuur().test(argumenten, context);
            result = conditie.alsBoolean()
                    ? argumenten.get(1).evalueer(context)
                    : argumenten.get(INDEX_2).evalueer(context);
        } else if (conditie.getType(context) == ExpressieType.BOOLEAN) {
            result = NullLiteral.INSTANCE;
        } else {
            throw new ExpressieRuntimeException(ExpressieRuntimeException.INCORRECTE_EXPRESSIE);
        }
        return result;
    }

    @Override
    public ExpressieType getType(final List<Expressie> argumenten, final Context context) {
        final ExpressieType result;
        final ExpressieType t1 = argumenten.get(1).getType(context);
        final ExpressieType t2 = argumenten.get(INDEX_2).getType(context);
        if (t1.equals(t2) || !t1.equals(ExpressieType.NULL) && t2.equals(ExpressieType.NULL)) {
            result = t1;
        } else if (t1.equals(ExpressieType.NULL) && !t2.equals(ExpressieType.NULL)) {
            result = t2;
        } else {
            result = ExpressieType.ONBEKEND_TYPE;
        }
        return result;
    }

    @Override
    public boolean evalueerArgumenten() {
        return false;
    }
}
