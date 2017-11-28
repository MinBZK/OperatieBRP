/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

import java.time.Month;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieRuntimeException;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.DatumLiteral;
import nl.bzk.brp.domain.expressie.GetalLiteral;
import nl.bzk.brp.domain.expressie.signatuur.SignatuurOptie;
import nl.bzk.brp.domain.expressie.signatuur.SimpeleSignatuur;
import org.springframework.stereotype.Component;

/**
 * Representeert de functies LAATSTE_DAG(J) en LAATSTE_DAG(J, M). De functie geeft de datum van de
 * laatste dag in jaar J respectievelijk van de laatste dag in maand M van jaar J.
 */
@Component
@FunctieKeyword("LAATSTE_DAG")
final class LaatsteDagFunctie extends AbstractFunctie {

    /**
     * Constructor voor de functie.
     */
    LaatsteDagFunctie() {
        super(new SignatuurOptie(
                new SimpeleSignatuur(ExpressieType.GETAL),
                new SimpeleSignatuur(ExpressieType.GETAL, ExpressieType.GETAL)));
    }

    @Override
    public Expressie evalueer(final List<Expressie> argumenten, final Context context) {
        final Expressie resultaat;
        final int jaar = (int) super.<GetalLiteral>getArgument(argumenten, 0).getWaarde();
        if (argumenten.size() == 1) {
            resultaat = new DatumLiteral(getLaatsteDagInJaar(jaar));
        } else {
            final int maand = (int) super.<GetalLiteral>getArgument(argumenten, 1).getWaarde();
            if (!(maand >= Month.JANUARY.getValue() && maand <= Month.DECEMBER.getValue())) {
                throw new ExpressieRuntimeException(String.format("Invalide maandnummer (%d)", maand));
            }
            resultaat = new DatumLiteral(getLaatsteDagInMaand(jaar, maand));
        }
        return resultaat;
    }


    @Override
    public ExpressieType getType(final List<Expressie> argumenten, final Context context) {
        return ExpressieType.DATUM;
    }

    /**
     * Geeft de datum van de laatste dag in het gegeven jaar.
     *
     * @param jaar Jaartal.
     * @return Laatste dag van het jaar.
     */
    private static ZonedDateTime getLaatsteDagInJaar(final int jaar) {
        return ZonedDateTime.of(jaar, 1, 1, 0, 0, 0, 0, DatumUtil.NL_ZONE_ID).with(TemporalAdjusters.lastDayOfYear());
    }

    /**
     * Geeft de datum van de laatste dag in de gegeven maand in het gegeven jaar.
     *
     * @param jaar  Jaartal.
     * @param maand Maandnummer [1..12].
     * @return Laatste dag van de maand.
     */
    private static ZonedDateTime getLaatsteDagInMaand(final int jaar, final int maand) {
        return ZonedDateTime.of(jaar, maand, 1, 0, 0, 0, 0, DatumUtil.NL_ZONE_ID).with(TemporalAdjusters.lastDayOfMonth());
    }
}
