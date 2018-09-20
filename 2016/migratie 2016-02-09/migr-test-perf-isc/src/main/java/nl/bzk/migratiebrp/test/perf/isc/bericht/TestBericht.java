/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.perf.isc.bericht;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Test bericht.
 */
public final class TestBericht {

    // Constantes voor de standaard/variabele gemeente instellingen.
    /** Default bron gemeente. */
    public static final String STANDAARD_BRON_GEMEENTE = "0599";
    /** Default doel gemeente. */
    public static final String STANDAARD_DOEL_GEMEENTE = "0600";
    /** Sleutel Lo3 gemeente. */
    public static final String SLEUTEL_LO3_GEMEENTE = "lo3Gemeente";
    /** Sleutel brp gemeente. */
    public static final String SLEUTEL_BRP_GEMEENTE = "brpGemeente";
    /** Property bestand. */
    public static final String GEMEENTE_INSTELLINGEN_BESTAND = "gemeente.properties";

    private static final Pattern FILE_NAME_PATTERN = Pattern.compile(
        "([0-9]*)-([0-9]*)?(IN|UIT)([0-9]*)?-(BRP|VOSPG|SYNC|HAND|TRANS|NODECHECK|VOISC|GEMEENTE|MAILBOX|CLEANDB|SQL_BRP|SQL_ISC|TIMEOUT_JMS)?(-.*)?",
        Pattern.CASE_INSENSITIVE);

    private final Integer volgnummer;
    private final Boolean uitgaand;
    private final Integer correlatienummer;
    private final String kanaal;
    private final String inhoud;
    private String lo3Gemeente;
    private String brpGemeente;

    /**
     * Constructor.
     *
     * @param berichtInhoud
     *            inhoud van het bericht
     * @param filename
     *            bestandsnaam
     * @param fileDir
     *            directory waar het bestand zich bevindt
     */
    public TestBericht(final String berichtInhoud, final String filename, final String fileDir) {
        final Matcher matcher = FILE_NAME_PATTERN.matcher(filename);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("File '" + filename + "' voldoet niet aan de naamgeving.");
        }

        final int indexVolgnummer = 1;
        final int indexUitgaand = 3;
        final int indexCorrelatienummer = 4;
        final int indexKanaal = 5;

        volgnummer = TestBericht.getInt(matcher.group(indexVolgnummer));
        uitgaand = "UIT".equalsIgnoreCase(matcher.group(indexUitgaand));
        correlatienummer = TestBericht.getInt(matcher.group(indexCorrelatienummer));
        kanaal = matcher.group(indexKanaal).toUpperCase();

        final Properties generalProperties = new Properties();
        final File generalPropertiesFile = new File(fileDir + File.separator + GEMEENTE_INSTELLINGEN_BESTAND);
        try {
            try (FileInputStream generalPropertiesInputStream = new FileInputStream(generalPropertiesFile)) {
                generalProperties.load(generalPropertiesInputStream);
            }
            lo3Gemeente = generalProperties.getProperty(SLEUTEL_LO3_GEMEENTE, STANDAARD_BRON_GEMEENTE);
            brpGemeente = generalProperties.getProperty(SLEUTEL_BRP_GEMEENTE, STANDAARD_DOEL_GEMEENTE);
        } catch (final IOException e1) {
            lo3Gemeente = STANDAARD_BRON_GEMEENTE;
            brpGemeente = STANDAARD_DOEL_GEMEENTE;
        }

        // outputFile = new File(outputDirectory, filename.replaceAll("xls", "txt"));

        inhoud = berichtInhoud;
    }

    private static Integer getInt(final String value) {
        if (value == null || "".equals(value)) {
            return null;
        }

        return Integer.valueOf(value);
    }


    /**
     * Geef de waarde van kanaal.
     *
     * @return kanaal
     */
    public String getKanaal() {
        return kanaal;
    }

    /**
     * Geef de waarde van inhoud.
     *
     * @return inhoud
     */
    public String getInhoud() {
        return inhoud;
    }

    @Override
    public String toString() {
        return "TestBericht [volgnummer=" + volgnummer + ", uitgaand=" + uitgaand + ", correlatienummer=" + correlatienummer + ", kanaal=" + kanaal + "]";
    }

    /**
     * Geef de waarde van lo3 gemeente.
     *
     * @return lo3 gemeente
     */
    public String getLo3Gemeente() {
        return lo3Gemeente;
    }

    /**
     * Geef de waarde van brp gemeente.
     *
     * @return brp gemeente
     */
    public String getBrpGemeente() {
        return brpGemeente;
    }

    /**
     * Zet de waarde van lo3 gemeente.
     *
     * @param lo3Gemeente
     *            lo3 gemeente
     */
    public void setLo3Gemeente(final String lo3Gemeente) {
        this.lo3Gemeente = lo3Gemeente;
    }

    /**
     * Zet de waarde van brp gemeente.
     *
     * @param brpGemeente
     *            brp gemeente
     */
    public void setBrpGemeente(final String brpGemeente) {
        this.brpGemeente = brpGemeente;
    }
}
