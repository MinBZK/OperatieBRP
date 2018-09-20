/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.perf.synchronisatie.bericht;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Test bericht.
 */
public final class TestBericht {

    private static final Pattern FILE_NAME_PATTERN = Pattern.compile("([0-9]*)-([0-9]*)-([0-9]*)-([0-9])*.*", Pattern.CASE_INSENSITIVE);

    private final Integer volgnummer;
    private final String inhoud;
    private final String verzendendePartij;
    private final String ontvangendePartij;

    /**
     * Constructor.
     * 
     * @param berichtInhoud
     *            inhoud van het bericht
     * @param filename
     *            bestandsnaam
     */
    public TestBericht(final String berichtInhoud, final String filename) {
        final Matcher matcher = FILE_NAME_PATTERN.matcher(filename);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("File '" + filename + "' voldoet niet aan de naamgeving.");
        }

        final int indexVolgnummer = 1;
        final int indexVerzendendePartij = 2;
        final int indexOntvangendePartij = 3;

        volgnummer = TestBericht.getInt(matcher.group(indexVolgnummer));
        verzendendePartij = matcher.group(indexVerzendendePartij);
        ontvangendePartij = matcher.group(indexOntvangendePartij);
        inhoud = berichtInhoud;
    }

    private static Integer getInt(final String value) {
        if (value == null || "".equals(value)) {
            return null;
        }

        return Integer.valueOf(value);
    }

    /**
     * Geef de waarde van volgnummer.
     *
     * @return volgnummer
     */
    public Integer getVolgnummer() {
        return volgnummer;
    }

    /**
     * Geef de waarde van inhoud.
     *
     * @return inhoud
     */
    public String getInhoud() {
        return inhoud;
    }

    /**
     * Geef de waarde van verzendende partij.
     *
     * @return verzendende partij
     */
    public String getVerzendendePartij() {
        return verzendendePartij;
    }

    /**
     * Geef de waarde van ontvangende partij.
     *
     * @return ontvangende partij
     */
    public String getOntvangendePartij() {
        return ontvangendePartij;
    }

    @Override
    public String toString() {
        return "TestBericht [volgnummer=" + volgnummer + ", verzendendePartij=" + verzendendePartij + ", ontvangendePartij=" + ontvangendePartij + "]";
    }

}
