/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.isc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import nl.moderniseringgba.migratie.test.isc.reader.Reader;
import nl.moderniseringgba.migratie.test.isc.reader.ReaderFactory;

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

    private static final Pattern FILE_NAME_PATTERN = Pattern
            .compile("([0-9]*)-([0-9]*)?(IN|UIT)([0-9]*)?-(BRP|VOSPG|SYNC|MVI|HAND|TRANS|DB)?(-.*)?",
                    Pattern.CASE_INSENSITIVE);

    private final Integer volgnummer;
    private final Integer herhaalnummer;
    private final Boolean uitgaand;
    private final Integer correlatienummer;
    private final String kanaal;
    private final String inhoud;
    private final File outputFile;
    private String lo3Gemeente;
    private String brpGemeente;

    private final ReaderFactory readerFactory = new ReaderFactory();

    /**
     * Constructor.
     * 
     * @param testBericht
     *            input file
     * @param outputDirectory
     *            output directory
     */
    public TestBericht(final File testBericht, final File outputDirectory) {
        final Matcher matcher = FILE_NAME_PATTERN.matcher(testBericht.getName());
        if (!matcher.matches()) {
            throw new RuntimeException("File '" + testBericht.getName() + "' voldoet niet aan de naamgeving.");
        }

        // CHECKSTYLE:OFF - Magic numbers
        volgnummer = getInt(matcher.group(1));
        herhaalnummer = getInt(matcher.group(2));
        uitgaand = "UIT".equalsIgnoreCase(matcher.group(3));
        correlatienummer = getInt(matcher.group(4));
        kanaal = matcher.group(5).toUpperCase();
        // CHECKSTYLE:ON

        final Properties generalProperties = new Properties();
        final File generalPropertiesFile =
                new File(testBericht.getParent() + File.separator + GEMEENTE_INSTELLINGEN_BESTAND);
        try {
            generalProperties.load(new FileInputStream(generalPropertiesFile));
            lo3Gemeente = generalProperties.getProperty(SLEUTEL_LO3_GEMEENTE, STANDAARD_BRON_GEMEENTE);
            brpGemeente = generalProperties.getProperty(SLEUTEL_BRP_GEMEENTE, STANDAARD_DOEL_GEMEENTE);
        } catch (final IOException e1) {
            lo3Gemeente = STANDAARD_BRON_GEMEENTE;
            brpGemeente = STANDAARD_DOEL_GEMEENTE;
        }

        outputFile = new File(outputDirectory, testBericht.getName().replaceAll("xls", "txt"));

        try {
            final Reader reader = readerFactory.getReader(testBericht);
            inhoud = reader.readFile(testBericht);
        } catch (final IOException e) {
            throw new RuntimeException("Kan file niet lezen", e);
        }
    }

    private static Integer getInt(final String value) {
        if (value == null || "".equals(value)) {
            return null;
        }

        return Integer.valueOf(value);
    }

    public Integer getVolgnummer() {
        return volgnummer;
    }

    public Integer getHerhaalnummer() {
        return herhaalnummer;
    }

    public Boolean getUitgaand() {
        return uitgaand;
    }

    public Integer getCorrelatienummer() {
        return correlatienummer;
    }

    public String getKanaal() {
        return kanaal;
    }

    public String getInhoud() {
        return inhoud;
    }

    public File getOutputFile() {
        return outputFile;
    }

    @Override
    public String toString() {
        return "TestBericht [volgnummer=" + volgnummer + ", uitgaand=" + uitgaand + ", correlatienummer="
                + correlatienummer + ", kanaal=" + kanaal + "]";
    }

    public String getLo3Gemeente() {
        return lo3Gemeente;
    }

    public String getBrpGemeente() {
        return brpGemeente;
    }

    public void setLo3Gemeente(final String lo3Gemeente) {
        this.lo3Gemeente = lo3Gemeente;
    }

    public void setBrpGemeente(final String brpGemeente) {
        this.brpGemeente = brpGemeente;
    }

}
