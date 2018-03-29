/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.expressie.brp;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.domain.expressie.Context;
import nl.bzk.brp.domain.expressie.DatumLiteral;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieType;
import nl.bzk.brp.domain.expressie.GetalLiteral;
import nl.bzk.brp.domain.expressie.functie.Functie;
import nl.bzk.brp.domain.expressie.signatuur.Signatuur;
import nl.bzk.brp.domain.expressie.signatuur.SignatuurOptie;
import nl.bzk.brp.domain.expressie.signatuur.SimpeleSignatuur;

/**
 * Representeert de functie VANDAAG(X). De functie geeft als resultaat de huidige datum (bij executie van de expressie),
 * waarbij opgeteld X jaar. Echter deze implementatie kan op een vaste datum worden gezet tbv regressie testen
 */
public final class FunctieVandaag implements Functie {

    private static final Signatuur SIGNATUUR = new SignatuurOptie(new SimpeleSignatuur(ExpressieType.GETAL), new SimpeleSignatuur());

    private static String vandaag = String.valueOf(DatumUtil.vandaag());

    /**
     * Zet de waarde 'vandaag'.
     * @param waarde De te zetten waarde.
     */
    public static void setVandaag(final String waarde) {
        vandaag = waarde;
    }

    /**
     * Reset de waarde van 'vandaag'.
     */
    public static void resetVandaag() {
        vandaag = null;
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
    public Signatuur getSignatuur() {
        return SIGNATUUR;
    }

    @Override
    public ExpressieType getType(final List<Expressie> argumenten, final Context context) {
        return ExpressieType.DATUM;
    }

    @Override
    public Expressie evalueer(final List<Expressie> argumenten, final Context context) {
        final GetalLiteral argument = (GetalLiteral) argumenten.get(0);
        final ZonedDateTime zonedDateTime = vandaag != null
                ? DatumUtil.vanDateNaarZonedDateTime(DatumUtil.vanStringNaarDatum(vandaag))
                : DatumUtil.nuAlsZonedDateTime();
        return new DatumLiteral(zonedDateTime.plusYears(argument.getWaarde()));
    }

    @Override
    public boolean evalueerArgumenten() {
        return true;
    }

    @Override
    public String getKeyword() {
        return "VANDAAG";
    }

}
