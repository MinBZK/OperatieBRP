/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.DatumLiteral;
import nl.bzk.brp.domain.expressie.GetalLiteral;
import nl.bzk.brp.domain.expressie.signatuur.SignatuurOptie;
import nl.bzk.brp.domain.expressie.signatuur.SimpeleSignatuur;
import org.springframework.stereotype.Component;

/**
 * Representeert de functie VANDAAG(X). De functie geeft als resultaat de huidige datum (bij executie van de expressie),
 * waarbij opgeteld X jaar.
 */
@Component
@FunctieKeyword("VANDAAG")
final class VandaagFunctie extends AbstractFunctie {

    /**
     * Constructor voor de functie.
     */
    VandaagFunctie() {
        super(new SignatuurOptie(
                new SimpeleSignatuur(ExpressieType.GETAL),
                new SimpeleSignatuur()));
    }

    @Override
    public Expressie evalueer(final List<Expressie> argumenten, final Context context) {
        final GetalLiteral argument = getArgument(argumenten, 0);
        return new DatumLiteral(DatumUtil.nuAlsZonedDateTime().plusYears(argument.getWaarde()));
    }

    @Override
    public List<Expressie> init(final List<Expressie> argumenten) {
        final List<Expressie> volledigeArgumenten;
        if (argumenten.isEmpty()) {
            volledigeArgumenten = new ArrayList<>();
            volledigeArgumenten.add(new GetalLiteral(0));
        } else {
            volledigeArgumenten = argumenten;
        }
        return volledigeArgumenten;
    }

    @Override
    public ExpressieType getType(final List<Expressie> argumenten, final Context context) {
        return ExpressieType.DATUM;
    }
}
