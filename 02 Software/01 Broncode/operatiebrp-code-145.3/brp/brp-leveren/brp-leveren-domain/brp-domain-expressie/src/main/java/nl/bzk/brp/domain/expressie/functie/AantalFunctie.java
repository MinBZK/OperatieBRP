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
import nl.bzk.brp.domain.expressie.LijstExpressie;
import nl.bzk.brp.domain.expressie.GetalLiteral;
import nl.bzk.brp.domain.expressie.signatuur.SimpeleSignatuur;
import org.springframework.stereotype.Component;

/**
 * Representeert de functie AANTAL(L). De functie geeft het aantal elementen van de lijst L terug.
 */
@Component
@FunctieKeyword("AANTAL")
final class AantalFunctie extends AbstractFunctie {

    /**
     * Constructor voor de functie.
     */
    AantalFunctie() {
        super(new SimpeleSignatuur(ExpressieType.LIJST));
    }

    @Override
    public Expressie evalueer(final List<Expressie> argumenten, final Context context) {
        final LijstExpressie argument = argumenten.get(0).alsLijst();
        return new GetalLiteral(argument.size());
    }

    @Override
    public ExpressieType getType(final List<Expressie> argumenten, final Context context) {
        return ExpressieType.GETAL;
    }
}
