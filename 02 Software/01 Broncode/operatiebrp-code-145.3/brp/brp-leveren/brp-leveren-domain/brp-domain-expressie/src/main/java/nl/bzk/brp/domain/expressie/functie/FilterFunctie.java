/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieLogger;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.LijstExpressie;
import nl.bzk.brp.domain.expressie.VariabeleExpressie;
import nl.bzk.brp.domain.expressie.signatuur.ExistentieleFunctieSignatuur;
import org.springframework.stereotype.Component;

/**
 * Representeert de functie FILTER(L,V,C). De functie geeft een lijst van waarden uit L terug die voldoen aan
 * conditie C.
 */
@Component
@FunctieKeyword("FILTER")
final class FilterFunctie extends AbstractFunctie {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * Constructor voor de functie.
     */
    FilterFunctie() {
        super(new ExistentieleFunctieSignatuur());
    }

    @Override
    public Expressie evalueer(final List<Expressie> argumenten, final Context context) {
        if (ExpressieLogger.IS_DEBUG_ENABLED) {
            LOGGER.debug("FunctieFilter {}", argumenten);
        }
        final Expressie result;
        final Expressie lijst = argumenten.get(0).evalueer(context);
        assertSignatuurCorrect(argumenten, context);

        final Expressie conditie = argumenten.get(2);
        final String variabele = ((VariabeleExpressie) argumenten.get(1)).getIdentifier();
        final List<Expressie> geselecteerdeWaarden = new ArrayList<>();
        final Context newContext = new Context(context);

        for (final Expressie waarde : lijst.alsLijst()) {
            newContext.definieer(variabele, waarde);
            final Expressie valueCondition = conditie.evalueer(newContext);
            if (valueCondition.isConstanteWaarde(ExpressieType.BOOLEAN, context)
                    && valueCondition.alsBoolean()) {
                geselecteerdeWaarden.add(waarde);
            }
        }
        result = new LijstExpressie(geselecteerdeWaarden);
        if (ExpressieLogger.IS_DEBUG_ENABLED) {
            LOGGER.debug("Resultaat {}", result);
        }
        return result;
    }

    @Override
    public ExpressieType getType(final List<Expressie> argumenten, final Context context) {
        return ExpressieType.LIJST;
    }

    @Override
    public boolean evalueerArgumenten() {
        return false;
    }
}
