/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.expressie.brp;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.expressies.functies.Functieberekening;
import nl.bzk.brp.expressietaal.expressies.functies.signatuur.Signatuur;
import nl.bzk.brp.expressietaal.expressies.functies.signatuur.SignatuurOptie;
import nl.bzk.brp.expressietaal.expressies.functies.signatuur.SimpeleSignatuur;
import nl.bzk.brp.expressietaal.expressies.literals.DatumLiteralExpressie;
import nl.bzk.brp.expressietaal.expressies.literals.GetalLiteralExpressie;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Representeert de functie VANDAAG(X). De functie geeft als resultaat de huidige datum (bij executie van de expressie),
 * waarbij opgeteld X jaar. Echter deze implementatie kan op een vaste datum worden gezet tbv regressie testen
 */
public final class FunctieVandaag implements Functieberekening {

    private static final Signatuur SIGNATUUR = new SignatuurOptie(new SimpeleSignatuur(ExpressieType.GETAL), new SimpeleSignatuur());

    private static DateTime vandaag = new DateTime();

    /**
     * Zet de waarde 'vandaag'.
     * 
     * @param waarde
     *            De te zetten waarde.
     */
    public static void setVandaag(final String waarde) {
        final DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMdd");
        vandaag = fmt.parseDateTime(waarde);
    }

    /**
     * Reset de waarde van 'vandaag'.
     */
    public static void resetVandaag() {
        vandaag = null;
    }

    @Override
    public List<Expressie> vulDefaultArgumentenIn(final List<Expressie> argumenten) {
        List<Expressie> volledigeArgumenten;
        if (argumenten.isEmpty()) {
            volledigeArgumenten = new ArrayList<Expressie>();
            volledigeArgumenten.add(new GetalLiteralExpressie(0));
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
    public ExpressieType getTypeVanElementen(final List<Expressie> argumenten, final Context context) {
        return ExpressieType.ONBEKEND_TYPE;
    }

    @Override
    public boolean evalueerArgumenten() {
        return true;
    }

    @Override
    public Expressie pasToe(final List<Expressie> argumenten, final Context context) {
        final Expressie argument = argumenten.get(0);
        return new DatumLiteralExpressie((vandaag == null ? new DateTime() : vandaag).plusYears(argument.alsInteger()));
    }

    @Override
    public boolean berekenBijOptimalisatie() {
        return false;
    }

    @Override
    public Expressie optimaliseer(final List<Expressie> argumenten, final Context context) {
        return null;
    }
}
