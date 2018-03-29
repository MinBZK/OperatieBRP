/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.LijstExpressie;
import nl.bzk.brp.domain.expressie.VariabeleExpressie;
import nl.bzk.brp.domain.expressie.signatuur.LijstMapSignatuur;
import org.springframework.stereotype.Component;

/**
 * Representeert de functie MAP(L,V,E). De functie geeft een lijst van waarden terug, die ontstaat door de expressie E
 * uit te voeren voor elk element uit lijst L.
 */
@Component
@FunctieKeyword("MAP")
final class MapFunctie extends AbstractFunctie {

    /**
     * Constructor voor de functie.
     */
    MapFunctie() {
        super(new LijstMapSignatuur());
    }

    @Override
    public Expressie evalueer(final List<Expressie> argumenten, final Context context) {
        final LijstExpressie compositie = argumenten.get(0).evalueer(context).alsLijst();
        final Expressie expressie = argumenten.get(2);
        final String variabele = ((VariabeleExpressie) argumenten.get(1)).getIdentifier();
        final List<Expressie> berekendeWaarden = new ArrayList<>();
        final Context newContext = new Context(context);
        for (Expressie waarde : compositie) {
            newContext.definieer(variabele, waarde);
            final Expressie berekendeWaarde = expressie.evalueer(newContext);
            berekendeWaarden.add(berekendeWaarde);
        }
        return new LijstExpressie(berekendeWaarden).alsPlatteLijst();
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
