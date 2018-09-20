/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.bericht;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import nl.bzk.migratiebrp.test.common.reader.Reader;
import nl.bzk.migratiebrp.test.common.reader.ReaderFactory;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.common.operatie.Herhaal;
import nl.bzk.migratiebrp.util.common.operatie.Herhaal.Strategie;

/**
 * Test bericht.
 */
public final class TestBericht {

    // Constantes voor de standaard/variabele gemeente instellingen.
    /** Sleutel verzendende partij. */
    public static final String SLEUTEL_VERZENDENDE_PARTIJ = "verzendendePartij";
    /** Sleutel ontvangende partij. */
    public static final String SLEUTEL_ONTVANGENDE_PARTIJ = "ontvangendePartij";

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final Pattern FILE_NAME_PATTERN = Pattern.compile(
        "([0-9]*)-([0-9]*)?(IN|UIT)([0-9]*)?-([A-Za-z_]*)(-.*)?(\\..*)?",
        Pattern.CASE_INSENSITIVE);

    private final Integer volgnummer;
    private final Integer herhaalnummer;
    private final Boolean uitgaand;
    private final Integer correlatienummer;
    private final String kanaal;
    private final String inhoud;
    private final File inputFile;
    private final File outputFile;
    private final File expectedFile;
    private String verzendendePartij;
    private String ontvangendePartij;
    private final Properties testBerichtProperties;

    private final ReaderFactory readerFactory = new ReaderFactory(new Properties() {
        private static final long serialVersionUID = 1L;

        {
            setProperty("csv", "false");
        }
    });

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
            throw new IllegalArgumentException("File '" + testBericht.getName() + "' voldoet niet aan de naamgeving.");
        }

        inputFile = testBericht;

        volgnummer = TestBericht.getInt(matcher.group(1));
        herhaalnummer = TestBericht.getInt(matcher.group(2));
        uitgaand = "UIT".equalsIgnoreCase(matcher.group(3));
        correlatienummer = TestBericht.getInt(matcher.group(4));
        kanaal = matcher.group(5).toUpperCase();

        testBerichtProperties = new Properties();
        final File testBerichtPropertiesFile = new File(testBericht.getParentFile(), testBericht.getName() + ".properties");
        LOGGER.info("Looking for bericht.properties in : " + testBerichtPropertiesFile.getName());
        if (testBerichtPropertiesFile.exists()) {
            LOGGER.info("bericht.properties found");
            try (InputStream propertyStream = new FileInputStream(testBerichtPropertiesFile)) {
                testBerichtProperties.load(propertyStream);

                LOGGER.info("Properties: " + testBerichtProperties);
                verzendendePartij = testBerichtProperties.getProperty(SLEUTEL_VERZENDENDE_PARTIJ);
                ontvangendePartij = testBerichtProperties.getProperty(SLEUTEL_ONTVANGENDE_PARTIJ);

                if (testBerichtProperties.getProperty("brpGemeente") != null) {
                    throw new IllegalArgumentException("Partij property 'brpGemeente' wordt niet meer ondersteund!");

                }
                if (testBerichtProperties.getProperty("lo3Gemeente") != null) {
                    throw new IllegalArgumentException("Partij property 'lo3Gemeente' wordt niet meer ondersteund!");
                }

            } catch (final IOException e) {
                // Ignore
                LOGGER.warn("Kan .properties bestand voor testbericht niet lezen.");
            }
        }

        outputFile = new File(outputDirectory, testBericht.getName().replaceAll("xls", "txt"));
        expectedFile = new File(outputDirectory, outputFile.getName() + ".expected");

        try {
            final Reader reader = readerFactory.getReader(testBericht);
            inhoud = reader.readFile(testBericht);
        } catch (final IOException e) {
            throw new IllegalArgumentException("Kan file niet lezen", e);
        }
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
     * Geef de waarde van herhaalnummer.
     *
     * @return herhaalnummer
     */
    public Integer getHerhaalnummer() {
        return herhaalnummer;
    }

    /**
     * Geef de waarde van uitgaand.
     *
     * @return uitgaand
     */
    public Boolean getUitgaand() {
        return uitgaand;
    }

    /**
     * Geef de waarde van correlatienummer.
     *
     * @return correlatienummer
     */
    public Integer getCorrelatienummer() {
        return correlatienummer;
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

    /**
     * Geef de waarde van input file.
     *
     * @return input file
     */
    public File getInputFile() {
        return inputFile;
    }

    /**
     * Geef de waarde van output file.
     *
     * @return output file
     */
    public File getOutputFile() {
        return outputFile;
    }

    /**
     * Geef de waarde van expected file.
     *
     * @return expected file
     */
    public File getExpectedFile() {
        return expectedFile;
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

    /**
     * Haal een test bericht property op.
     *
     * @param key
     *            sleutel
     * @return waarde
     */
    public String getTestBerichtProperty(final String key) {
        return testBerichtProperties.getProperty(key);
    }

    /**
     * Haal een test bericht property op als Integer.
     *
     * @param key
     *            sleutel
     * @return waarde
     */
    public Integer getTestBerichtPropertyAsInteger(final String key) {
        final String value = testBerichtProperties.getProperty(key);
        return value == null ? null : Integer.valueOf(value);
    }

    /**
     * Geef de waarde van herhaling vertraging in millis.
     *
     * @return herhaling vertraging in millis
     */
    public long getHerhalingVertragingInMillis() {
        final Long result = Long.valueOf(testBerichtProperties.getProperty("herhaling.vertraging", Long.toString(Herhaal.STANDAARD_VERTRAGING_MILLIS)));
        LOGGER.info("getHerhalingVertragingInMillis: " + result);
        return result;
    }

    /**
     * Geef de waarde van herhaling aantal pogingen.
     *
     * @return herhaling aantal pogingen
     */
    public int getHerhalingAantalPogingen() {
        final Integer result =
                Integer.valueOf(testBerichtProperties.getProperty("herhaling.aantalpogingen", Integer.toString(Herhaal.STANDAARD_MAXIMUM_AANTAL_POGINGEN)));
        LOGGER.info("getHerhalingAantalPogingen: " + result);
        return result;
    }

    /**
     * Geef de waarde van herhaling strategie.
     *
     * @return herhaling strategie
     */
    public Strategie getHerhalingStrategie() {
        final Strategie result = Strategie.valueOf(testBerichtProperties.getProperty("herhaling.strategie", Herhaal.STANDAARD_STRATEGIE.name()));
        LOGGER.info("getHerhalingStrategie: " + result);
        return result;
    }

    /**
     * Maak een Herhaal object obv de configuratie van het bericht.
     *
     * @return Herhaal object
     */
    public Herhaal maakHerhaling() {
        return new Herhaal(getHerhalingVertragingInMillis(), getHerhalingAantalPogingen(), getHerhalingStrategie());
    }

    @Override
    public String toString() {
        return "TestBericht [volgnummer="
               + volgnummer
               + ", herhaalnummer="
               + herhaalnummer
               + ", uitgaand="
               + uitgaand
               + ", correlatienummer="
               + correlatienummer
               + ", kanaal="
               + kanaal
               + ", verzendendePartij="
               + verzendendePartij
               + ", ontvangendePartij="
               + ontvangendePartij
               + "]";
    }

}
