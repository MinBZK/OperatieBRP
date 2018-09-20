/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.business.util;

import java.util.Calendar;
import java.util.Date;

import org.jibx.runtime.JiBXException;
import org.jibx.runtime.Utility;

/**
 * Utility class voor het omzetten van datums naar de string/numerieke variant die we voor sommige datums in de BRP
 * gebruiken.
 */
public final class DatumUtility {

    private static final int YEAR_MULTIPLIER = 10000;
    private static final int MONTH_MULTIPLIER = 100;

    /**
     * Default private constructor omdat deze utility class niet geinstantieerd mag/dient te worden.
     */
    private DatumUtility() {
    }

    /**
     * Zet de opgegeven datum om naar zijn interne {@link Integer} representatie.
     * @param datum de datum die omgezet dient te worden.
     * @return de numerieke representatie van de opgegeven datum.
     */
    public static Integer zetDatumOmNaarInteger(final Date datum) {
        Calendar kalender = Calendar.getInstance();
        kalender.setTime(datum);
        return zetDatumOmNaarInteger(kalender);
    }

    /**
     * Zet de opgegeven datum om naar zijn interne {@link Integer} representatie.
     * @param datum de datum die omgezet dient te worden.
     * @return de numerieke representatie van de opgegeven datum.
     */
    public static Integer zetDatumOmNaarInteger(final Calendar datum) {
        int waarde = 0;
        waarde += datum.get(Calendar.YEAR) * YEAR_MULTIPLIER;
        waarde += (datum.get(Calendar.MONTH) + 1) * MONTH_MULTIPLIER;
        waarde += datum.get(Calendar.DAY_OF_MONTH);
        return waarde;
    }

    public static Date zetIntDatumOmNaarDatum(Integer datumInt) {
        final String datumString = datumInt.toString();
        int year = Integer.parseInt(datumString.substring(4));
        int month = Integer.parseInt(datumString.substring(4,5));
        int day = Integer.parseInt(datumString.substring(5,6));
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }

    public static Calendar zetXsdDatumTijdOmInDatum(String xsdDatumTijd) throws JiBXException {
        Calendar cal = Calendar.getInstance();
        Date datum = Utility.deserializeDateTime(xsdDatumTijd);
        cal.setTime(datum);
        return cal;
    }

}
