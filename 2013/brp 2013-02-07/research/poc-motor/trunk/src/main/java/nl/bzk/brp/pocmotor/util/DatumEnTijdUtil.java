/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.util;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Datum;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.DatumTijd;

/**
 * Utility class voor het omzetten van datum en tijd velden/objecten naar en van het formaat dat wordt gebruikt in de
 * berichten (ISO8601) naar het formaat dat intern in het model wordt gebruikt.
 */
public final class DatumEnTijdUtil {

    private static final Pattern TIMESTAMP_PATTERN = Pattern.compile("(\\d\\d\\d\\d)-(\\d\\d)-(\\d\\d)T(\\d\\d):(\\d\\d):(\\d\\d)");

    private DatumEnTijdUtil() throws IllegalAccessException {
        throw new IllegalAccessException("Utility classes mogen niet worden geinstantieerd");
    }

    /**
     * Zet een string waarde om naar de juiste {@link Date} instantie. Indien de waarde echter niet voldoet aan het
     * juist formaat (ISO8601) wordt er een exceptie gegooid.
     * @param waarde de waarde die moet worden omgezet.
     * @return de omgezette waarde.
     */
    public static final Date zetDatumEnTijdOmNaarDate(final String waarde) {
        Date date = null;
        if (waarde != null && !waarde.isEmpty()) {
            Matcher matcher = TIMESTAMP_PATTERN.matcher(waarde);
            if (matcher.matches()) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, Integer.parseInt(matcher.group(1)));
                calendar.set(Calendar.MONTH, Integer.parseInt(matcher.group(2)) - 1);
                calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(matcher.group(3)));
                calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(matcher.group(4)));
                calendar.set(Calendar.MINUTE, Integer.parseInt(matcher.group(5)));
                calendar.set(Calendar.SECOND, Integer.parseInt(matcher.group(6)));
                date = calendar.getTime();
            } else {
                throw new IllegalArgumentException("Tijdstip is in verkeerd formaat: '" + waarde + "'");
            }
        }
        return date;
    }

    /**
     * Zet een {@link Date} instantie om naar het juiste XML formaat.
     * @param date de date waarde die moet worden omgezet.
     * @return de omgezette waarde.
     */
    public static final String zetDateOmNaarDatumEnTijd(final Date date) {
        String resultaat = "";
        if (date != null) {
            resultaat = String.format("%tFT%tT", date, date);
        }
        return resultaat;
    }

    public static final DatumTijd nu() {
        Calendar cal = Calendar.getInstance();
        final DatumTijd datumTijd = new DatumTijd();
        datumTijd.setWaarde(cal.getTime());
        return datumTijd;
    }

    public static final Datum vandaag() {
        final Calendar cal = Calendar.getInstance();
        final int jaar = cal.get(Calendar.YEAR);
        final int maand = cal.get(Calendar.MONTH) + 1;
        final int dag = cal.get(Calendar.DAY_OF_MONTH);
        final StringBuilder builder = new StringBuilder();
        builder.append(jaar);
        if (maand < 10) {
            builder.append(0);
        }
        builder.append(maand);
        if (dag < 10) {
            builder.append(0);
        }
        builder.append(dag);
        Datum datum = new Datum();
        datum.setWaarde(Integer.valueOf(builder.toString()));
        return datum;
    }

}
