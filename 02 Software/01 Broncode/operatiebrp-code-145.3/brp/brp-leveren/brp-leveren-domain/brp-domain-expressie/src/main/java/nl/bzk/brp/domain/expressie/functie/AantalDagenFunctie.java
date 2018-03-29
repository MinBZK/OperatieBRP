/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.functie;

import java.time.Month;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
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
 * Representeert de functies AANTAL_DAGEN(J) en AANTAL_DAGEN(J, M). De functie geeft respectievelijk het aantal dagen.
 * in jaar J en het aantal dagen in maand M van jaar J.
 * <p>
 * De functie AANTAL_DAGEN(jaar, maand) geeft het aantal dagen in de gegeven maand binnen het gegeven jaar.
 * De functie is ook te gebruiken als AANTAL_DAGEN(jaar); in dat geval is het resultaat het aantal dagen in het gegeven jaar.
 * <p>
 * <br>Voorbeelden:
 * <table summary="Voorbeelden">
 * <tr><th>Expressie</th><th>Resultaat</th></tr>
 * <tr><td>AANTAL_DAGEN(1950, 1)</td><td>31</td></tr>
 * <tr><td>AANTAL_DAGEN(1950, 2)</td><td>28</td></tr>
 * <tr><td>AANTAL_DAGEN(2000, 2)</td><td>29</td></tr>
 * <tr><td>AANTAL_DAGEN(2000)</td><td>366</td></tr>
 * <tr><td>AANTAL_DAGEN(NULL)</td><td>NULL</td></tr>
 * </table>
 */
@Component
@FunctieKeyword("AANTAL_DAGEN")
final class AantalDagenFunctie extends AbstractFunctie {

    /**
     * Constructor voor de functie.
     */
    AantalDagenFunctie() {
        super(new SignatuurOptie(
                new SimpeleSignatuur(ExpressieType.GETAL),
                new SimpeleSignatuur(ExpressieType.GETAL, ExpressieType.GETAL)));
    }

    @Override
    public Expressie evalueer(final List<Expressie> argumenten, final Context context) {
        final Expressie resultaat;
        final int jaar = (int) super.<GetalLiteral>getArgument(argumenten, 0).getWaarde();
        if (argumenten.size() == 1) {
            resultaat = new GetalLiteral(dagenInJaar(jaar));
        } else {
            final int maand = (int) super.<GetalLiteral>getArgument(argumenten, 1).getWaarde();
            if (!(maand >= Month.JANUARY.getValue() && maand <= Month.DECEMBER.getValue())) {
                throw new ExpressieRuntimeException(String.format("Invalide maandnummer (%d)", maand));
            }
            resultaat = new GetalLiteral(DatumLiteral.dagenInMaand(jaar, maand));
        }
        return resultaat;
    }

    @Override
    public ExpressieType getType(final List<Expressie> argumenten, final Context context) {
        return ExpressieType.GETAL;
    }

    /**
     * Berekent het aantal dagen dat in een gegeven jaar zit, rekening houdend met schrikkeljaren.
     *
     * @param jaar Jaartal.
     * @return Aantal dagen in een bepaald jaar.
     */
    private static int dagenInJaar(final int jaar) {
        return (int) ZonedDateTime.of(jaar, 1, 1, 0, 0, 0, 0, DatumUtil.NL_ZONE_ID)
                .range(ChronoField.DAY_OF_YEAR).getMaximum();
    }
}
