/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.expressie.brp;

import java.util.List;
import nl.bzk.brp.expressietaal.Context;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.ExpressieType;
import nl.bzk.brp.expressietaal.expressies.functies.Functieberekening;
import nl.bzk.brp.expressietaal.expressies.functies.signatuur.Signatuur;
import nl.bzk.brp.expressietaal.expressies.functies.signatuur.SimpeleSignatuur;
import nl.bzk.brp.expressietaal.expressies.literals.DatumLiteralExpressie;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Representeert de functie SELECTIE_DATUM(). De functie geeft als resultaat de selectiedatum. Echter deze implementatie
 * kan op een vaste datum worden gezet tbv regressie testen
 */
public final class FunctieSelectieDatum implements Functieberekening {

    private static final Signatuur SIGNATUUR = new SimpeleSignatuur();

    private static DateTime selectieDatum = new DateTime();

    /**
     * Zet de waarde voor 'selectiedatum'.
     * 
     * @param waarde
     *            De te zetten waarde.
     */
    public static void setSelectieDatum(final String waarde) {
        final DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyyMMdd");
        selectieDatum = fmt.parseDateTime(waarde);
    }

    /**
     * Reset de waarde van 'selectiedatum'.
     */
    public static void resetSelectieDatum() {
        selectieDatum = null;
    }

    @Override
    public List<Expressie> vulDefaultArgumentenIn(final List<Expressie> argumenten) {
        return argumenten;
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
        return new DatumLiteralExpressie(selectieDatum);
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
