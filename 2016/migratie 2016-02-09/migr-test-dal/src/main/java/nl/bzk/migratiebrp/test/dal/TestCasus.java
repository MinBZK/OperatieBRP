/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.dal;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.inject.Inject;
import javax.sql.DataSource;
import junit.framework.ComparisonCompactor;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lg01Bericht;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAfnemersindicaties;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAutorisatie;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.conversie.model.exceptions.PreconditieException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.logging.LogRegel;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.isc.console.mig4jsf.pretty.PrettyLo3;
import nl.bzk.migratiebrp.test.common.output.Lo3CategorieWaarden;
import nl.bzk.migratiebrp.test.common.output.TestOutput;
import nl.bzk.migratiebrp.test.common.output.TestOutputException;
import nl.bzk.migratiebrp.test.common.reader.Reader;
import nl.bzk.migratiebrp.test.common.reader.ReaderFactory;
import nl.bzk.migratiebrp.test.common.reader.TextReader;
import nl.bzk.migratiebrp.test.common.resultaat.Foutmelding;
import nl.bzk.migratiebrp.test.common.resultaat.TestResultaat;
import nl.bzk.migratiebrp.test.common.resultaat.TestStap;
import nl.bzk.migratiebrp.test.common.resultaat.TestStatus;
import nl.bzk.migratiebrp.test.common.writer.Writer;
import nl.bzk.migratiebrp.test.common.writer.WriterFactory;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Test casus: basis.
 */
public abstract class TestCasus {
    /** Suffix om de verschillen-bestanden mee aan te duiden. */
    protected static final String SUFFIX_VERSCHILLEN = "-verschillen";
    /** Suffix om de exception-bestanden mee aan te duiden. */
    protected static final String SUFFIX_EXCEPTION = "-exception";
    /**
     * End Of Line aanduiding.
     */
    protected static final String EOL = "\n";

    private static final int VERSCHIL_CONTEXT = 50;

    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String FOLDER_XML = "xml-";
    private static final String FOLDER_TXT = "txt-";
    private static final String FOLDER_HTML = "html-";
    private static final String FOLDER_LG01 = "lg01-";
    private static final String FOLDER_CSV = "csv-";

    private static final String EXTENSIE_XML = ".xml";
    private static final String EXTENSIE_HTML = ".html";
    private static final String EXTENSIE_TXT = ".txt";
    private static final String EXTENSIE_LG01 = EXTENSIE_TXT;
    private static final String EXTENSIE_CSV = ".csv";

    private static final String HTML_BERICHT_PREFIX =
            "<html><head><meta charset='UTF-8'><style>"
                                                      + "body { font-weight:normal; font-family:verdana,sans-serif; font-size:10pt; background-repeat:no-repeat; margin:0;"
                                                      + " border-width:0; padding:0; } "
                                                      + "html { background-attachment:scroll; background-repeat:no-repeat; margin:0; padding:0; border-width:0; } "
                                                      + "button, input, select, option, textarea { font-family:arial,helvetica,sans-serif; font-weight:normal; font-size:10pt;"
                                                      + " line-height:normal; } "
                                                      + "textarea { width:560px; height:130px; } "
                                                      + "table.dataform { border-style:solid; border-width:1px; border-collapse:collapse; border-color:#000000; margin-left:auto; "
                                                      + " margin-right:auto; } "
                                                      + "table.dataform td, table.dataform th { padding-top:3px; padding-bottom:3px; padding-left:5px; padding-right:5px; } "
                                                      + "table.dataform thead tr th { text-align:left; background-color:#990000; color:#ffff99; font-weight:bold;"
                                                      + " border-bottom-style:solid; border-bottom-width:1px; border-bottom-color:#000000; } "
                                                      + "table.dataform tbody th { text-align:left; text-decoration:none; font-size:11px; white-space:nowrap;"
                                                      + " background:#444444; color:#ffffff; border-bottom-style:solid; border-bottom-width:1px; border-bottom-color:#000000;"
                                                      + " border-collapse:collapse; width:130px; } "
                                                      + "table.dataform tbody td { text-align:left; text-decoration:none; font-size:11px; white-space:nowrap;"
                                                      + " background:#dddddd; color:#000000; border-bottom-style:solid; border-bottom-width:1px; border-bottom-color:#000000;"
                                                      + " border-collapse:collapse; width:260px; } "
                                                      + "a.foldToggle { display:none; } "
                                                      + "</style></head><body>";
    private static final String HTML_BERICHT_SUFFIX = "</body></html>";

    private static final String FILE_WRITE_ERROR = "Kan directory/bestand niet aanmaken";

    private final String thema;

    private final String naam;
    private final File outputFolder;
    private final File expectedFolder;

    @Inject
    private WriterFactory writerFactory;

    @Inject
    private ReaderFactory readerFactory;

    /**
     * Test casus.
     *
     * @param thema
     *            thema
     * @param naam
     *            naam
     * @param outputFolder
     *            output folder
     * @param expectedFolder
     *            verwachting folder
     */
    protected TestCasus(final String thema, final String naam, final File outputFolder, final File expectedFolder) {
        this.thema = thema;
        this.naam = naam;
        this.outputFolder = outputFolder;
        this.expectedFolder = expectedFolder;
    }

    /**
     * Geef de waarde van thema.
     *
     * @return thema
     */
    public final String getThema() {
        return thema;
    }

    /**
     * Geef de waarde van naam.
     *
     * @return naam
     */
    public final String getNaam() {
        return naam;
    }

    /**
     * Geef de waarde van output folder.
     *
     * @return output folder
     */
    public final File getOutputFolder() {
        return outputFolder;
    }

    /**
     * Geef de waarde van expected folder.
     *
     * @return expected folder
     */
    public final File getExpectedFolder() {
        return expectedFolder;
    }

    /**
     * Voer een test casus uit.
     *
     * @return test resultaat
     */
    public abstract TestResultaat run();

    /**
     * Output HTML en XML.
     *
     * @param object
     *            object om te outputten
     * @param stap
     *            stap
     * @return filenaam van de HTML
     */
    protected final String debugOutputXmlEnHtml(final Object object, final TestCasusOutputStap stap) {
        return debugOutputXmlEnHtml(object, stap, null);
    }

    /**
     * Output lo3 categorieen.
     *
     * @param categorieen
     *            categorieen
     * @param stap
     *            stap
     * @return filenaam van de HTML
     */
    protected final String debugOutputLo3CategorieWaarden(final List<Lo3CategorieWaarde> categorieen, final TestCasusOutputStap stap) {
        return debugOutputXmlEnHtml(new Lo3CategorieWaarden(categorieen), stap);
    }

    /**
     * Output lo3 categorieen.
     *
     * @param categorieen
     *            categorieen
     * @param stap
     *            stap
     * @param suffix
     *            suffix
     * @return filenaam van de HTML
     */
    protected final String debugOutputLo3CategorieWaarden(
        final List<Lo3CategorieWaarde> categorieen,
        final TestCasusOutputStap stap,
        final String suffix)
    {
        return debugOutputXmlEnHtml(new Lo3CategorieWaarden(categorieen), stap, suffix);
    }

    /**
     * Output SQL content to CSV file.
     *
     * @param sqlFilename
     *            bestandnaam waar de CSV wordt weg geschreven
     * @param sqlResults
     *            resultaten van een SQL-query die opgeslagen moet worden
     * @param stap
     *            In welke stap die gebeurd
     * @return de bestandsnaam van het csv-bestand
     */
    protected final String debugOutputCsv(final String sqlFilename, final List<Map<String, Object>> sqlResults, final TestCasusOutputStap stap) {
        final String csvFileName = maakBestandsnaam(FOLDER_CSV, stap, sqlFilename, EXTENSIE_CSV);
        final File csvFile = new File(outputFolder, csvFileName);
        try {
            if (createDirectory(csvFile.getParentFile()) && writerFactory.accept(csvFile)) {
                final Writer writer = writerFactory.getWriter(csvFile);
                writer.writeSqlResultToFile(csvFile, sqlResults);
            } else {
                throw new IOException(FILE_WRITE_ERROR);
            }
            // Exception wordt hier gevangen om de flow van de tests niet te onderbreken door onverwachte
            // fouten. Cause wordt doorgegooid in een TestOutputException
        } catch (final Exception e) {
            handleOutputException(e);
        }

        return csvFileName;
    }

    /**
     * Schrijft de content naar een TXT-bestand weg. Bijvoorbeeld de output van de verschilanalyse (fingerprints).
     *
     * @param content
     *            content die opgeslagen moet worden.
     * @param suffix
     *            optioneel suffix voor de bestandsnaam
     * @param stap
     *            In welke stap die gebeurd
     * @return de bestandsnaam van het gemaakte bestand
     */
    protected final String debugOutputTxt(final List<String> content, final String suffix, final TestCasusOutputStap stap) {
        final String txtFileName = maakBestandsnaam(FOLDER_TXT, stap, suffix, EXTENSIE_TXT);
        final File txtFile = new File(outputFolder, txtFileName);

        try {
            if (createDirectory(txtFile.getParentFile()) && writerFactory.accept(txtFile)) {
                final Writer writer = writerFactory.getWriter(txtFile);
                writer.writeToFile(txtFile, content);
            } else {
                throw new IOException(FILE_WRITE_ERROR);
            }
            // Exception wordt hier gevangen om de flow van de tests niet te onderbreken door onverwachte
            // fouten. Cause wordt doorgegooid in een TestOutputException
        } catch (final Exception e) {
            handleOutputException(e);
        }

        return txtFileName;
    }

    /**
     * Schrijft de content naar een TXT-bestand weg. Bijvoorbeeld de output van de verschilanalyse (fingerprints).
     *
     * @param value
     *            content die opgeslagen moet worden.
     * @param stap
     *            In welke stap die gebeurd
     * @param suffix
     *            optioneel suffix voor de bestandsnaam
     * @return de bestandsnaam van het gemaakte bestand
     */
    protected final String debugOutputString(final String value, final TestCasusOutputStap stap, final String suffix) {
        final String txtFileName = maakBestandsnaam(FOLDER_TXT, stap, suffix, EXTENSIE_TXT);
        outputToFile(value, new File(outputFolder, txtFileName));

        return txtFileName;
    }

    /**
     * Schrijft de content (van een Lo3 bericht) naar een TXT-bestand weg; gebruikt de pretty print functionaliteit van
     * de JBPM console om het bericht in HTML weer te geven.
     *
     * @param value
     *            content die opgeslagen moet worden.
     * @param stap
     *            In welke stap die gebeurd
     * @param suffix
     *            optioneel suffix voor de bestandsnaam
     * @return de bestandsnaam van het gemaakte bestand
     */
    protected final String debugOutputBericht(final String value, final TestCasusOutputStap stap, final String suffix) {
        final String txtFileName = maakBestandsnaam(FOLDER_TXT, stap, suffix, EXTENSIE_TXT);
        final String htmlFileName = maakBestandsnaam(FOLDER_HTML, stap, suffix, EXTENSIE_HTML);

        outputToFile(value, new File(outputFolder, txtFileName));
        final String htmlValue = HTML_BERICHT_PREFIX + new PrettyLo3().prettyPrint(value) + HTML_BERICHT_SUFFIX;
        outputToFile(htmlValue, new File(outputFolder, htmlFileName));

        return htmlFileName;
    }

    private void outputToFile(final String value, final File file) {
        final boolean okToWrite = createDirectory(file.getParentFile());
        try (final FileOutputStream fos = new FileOutputStream(file)) {
            if (okToWrite) {
                if (value != null) {
                    fos.write(value.getBytes(StandardCharsets.UTF_8));
                }
            } else {
                throw new IOException(FILE_WRITE_ERROR);
            }
        } catch (final Exception e) {
            handleOutputException(e);
        }
    }

    /**
     * Output HTML en XML.
     *
     * @param object
     *            object dat weggeschreven moet worden
     * @param stap
     *            stap
     * @param suffix
     *            optioneel suffix voor de bestandsnaam
     * @return filenaam van de HTML
     */
    protected final String debugOutputXmlEnHtml(final Object object, final TestCasusOutputStap stap, final String suffix) {
        final File xmlFile = new File(outputFolder, maakBestandsnaam(FOLDER_XML, stap, suffix, EXTENSIE_XML));
        final String htmlFilename = maakBestandsnaam(FOLDER_HTML, stap, suffix, EXTENSIE_HTML);
        final File htmlFile = new File(outputFolder, htmlFilename);

        TestOutput.outputXmlEnHtml(object, xmlFile, htmlFile);

        return htmlFilename;
    }

    /**
     * Output LG01.
     *
     * @param lo3
     *            lo3 persoonlijst
     * @param stap
     *            stap
     */
    protected final void debugOutputLg01(final Lo3Persoonslijst lo3, final TestCasusOutputStap stap) {
        debugOutputLg01(lo3, stap, null);
    }

    /**
     * Output LG01.
     *
     * @param lo3
     *            lo3 persoonlijst
     * @param stap
     *            stap
     * @param suffix
     *            suffix voor de output file zodat meerdere pl-en met dezelfde naam onderscheiden kunnen worden.
     */
    protected final void debugOutputLg01(final Lo3Persoonslijst lo3, final TestCasusOutputStap stap, final String suffix) {
        final Lg01Bericht bericht = new Lg01Bericht();
        bericht.setLo3Persoonslijst(lo3);

        final String lg01Filename = maakBestandsnaam(FOLDER_LG01, stap, suffix, EXTENSIE_LG01);
        final File lg01File = new File(outputFolder, lg01Filename);
        final boolean okToWrite = createDirectory(lg01File.getParentFile());
        try (final FileOutputStream fos = new FileOutputStream(lg01File)) {
            if (okToWrite) {
                fos.write(bericht.format().getBytes(StandardCharsets.UTF_8));
            } else {
                throw new IOException(FILE_WRITE_ERROR);
            }
        } catch (final Exception e) {
            handleOutputException(e);
        }
    }

    /**
     * Lees verwachte lo3 categorieen.
     *
     * @param stap
     *            stap
     * @return lo3 categorieen
     */
    protected final List<Lo3CategorieWaarde> leesVerwachteLo3CategorieWaarden(final TestCasusOutputStap stap) {
        return leesVerwachteLo3CategorieWaarden(stap, null);
    }

    /**
     * Lees verwachte lo3 categorieen.
     *
     * @param stap
     *            stap
     * @param suffix
     *            suffix
     * @return lo3 categorieen
     */
    protected final List<Lo3CategorieWaarde> leesVerwachteLo3CategorieWaarden(final TestCasusOutputStap stap, final String suffix) {
        final File expectedXmlFile = new File(expectedFolder, maakBestandsnaam(FOLDER_XML, stap, suffix, EXTENSIE_XML));
        final Lo3CategorieWaarden lo3CategorieWaarden = TestOutput.readXml(Lo3CategorieWaarden.class, expectedXmlFile);
        return lo3CategorieWaarden == null ? null : lo3CategorieWaarden.getCategorieen();
    }

    /**
     * Lees verwachte lo3 persoonslijst.
     *
     * @param stap
     *            stap
     * @param suffix
     *            suffix voor de output file zodat meerdere pl-en met dezelfde naam onderscheiden kunnen worden.
     *
     * @return lo3 persoonslijst
     */
    protected final Lo3Persoonslijst leesVerwachteLo3Persoonslijst(final TestCasusOutputStap stap, final String suffix) {
        final File expectedXmlFile = new File(expectedFolder, maakBestandsnaam(FOLDER_XML, stap, suffix, EXTENSIE_XML));
        return TestOutput.readXml(Lo3Persoonslijst.class, expectedXmlFile);
    }

    /**
     * Lees verwachte preconditie exception.
     *
     * @param stap
     *            stap
     * @return een gevulde PreconditieException
     */
    protected final PreconditieException leesVerwachtePreconditieException(final TestCasusOutputStap stap) {
        try {
            final File expectedXmlFile = new File(expectedFolder, maakBestandsnaam(FOLDER_XML, stap, null, EXTENSIE_XML));
            return TestOutput.readXml(PreconditieException.class, expectedXmlFile);
        } catch (final TestOutputException e) {
            // log in standard output
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lees verwachte sql resultaten in.
     *
     * @param sqlFilename
     *            bestandsnaam van de verwachte SQL resultaten
     * @param stap
     *            stap waain de resultaten worden opgevraagd
     * @return de SQL resultaten die gelezen zijn. Als er een exceptie optreedt, dan wordt deze gelogd en wordt er een
     *         null resultaat terug gegeven.
     */
    protected final List<Map<String, Object>> leesVerwachteSqlResultaten(final String sqlFilename, final TestCasusOutputStap stap) {
        List<Map<String, Object>> results = null;
        try {
            final File expectedCsvFile = new File(expectedFolder, maakBestandsnaam(FOLDER_CSV, stap, sqlFilename, EXTENSIE_CSV));
            if (readerFactory.accept(expectedCsvFile)) {
                final Reader reader = readerFactory.getReader(expectedCsvFile);
                results = reader.readFileAsSqlOutput(expectedCsvFile);
            } else {
                throw new IOException(FILE_WRITE_ERROR);
            }
            // Exception wordt hier gevangen om de flow van de tests niet te onderbreken door onverwachte
            // fouten. Cause wordt gelog.
        } catch (final Exception e) {
            LOG.error("Fout tijdens inlezen verwachte CSV", e);
        }
        return results;
    }

    /**
     * Lees verwachte fingerprints van de verschil analyse in.
     *
     * @param stap
     *            stap waarin de fingerprints gelezen worden
     * @param suffix
     *            suffix voor de output file zodat meerdere pl-en met dezelfde naam onderscheiden kunnen worden.
     * @return Lijst met verwachte fingerprints. De lijst is leeg als het bestand niet gevonden wordt of als er een
     *         exception optreedt.
     */
    protected final List<String> leesVerwachteFingerPrints(final TestCasusOutputStap stap, final String suffix) {
        List<String> resultaten = new ArrayList<>();
        try {
            final File expectedFile = new File(expectedFolder, maakBestandsnaam(FOLDER_TXT, stap, suffix, EXTENSIE_TXT));
            if (expectedFile.exists()) {
                if (readerFactory.accept(expectedFile)) {
                    final Reader reader = readerFactory.getReader(expectedFile);
                    final String fileContent = reader.readFile(expectedFile);
                    if (!fileContent.isEmpty()) {
                        resultaten = Arrays.asList(fileContent.split(EOL));
                    }
                } else {
                    throw new IOException(FILE_WRITE_ERROR);
                }
            }
            // Exception wordt hier gevangen om de flow van de tests niet te onderbreken door onverwachte
            // fouten. Cause wordt gelog.
        } catch (final Exception e) {
            LOG.error("Fout tijdens inlezen verwachte fingerprints", e);
        }
        return resultaten;
    }

    /**
     * Leest een bestand als 1 string 1.
     *
     * @param stap
     *            test stap
     * @param suffix
     *            suffix van de persoonslijst
     * @return de inhoud van een bestand als 1 string.
     */
    protected final String leesVerwachteString(final TestCasusOutputStap stap, final String suffix) {
        String result;
        try {
            final String bestandsnaam = maakBestandsnaam(FOLDER_TXT, stap, suffix, EXTENSIE_TXT);
            final File expectedFile = new File(expectedFolder, bestandsnaam);

            if (expectedFile.exists()) {
                result = new TextReader().readFile(expectedFile);
            } else {
                result = null;
            }
        } catch (final IOException e) {
            // log in standard output
            e.printStackTrace();
            result = null;
        }
        return result;
    }

    /**
     * Lees verwachte ongeldige persoonslijst exception.
     *
     * @param stap
     *            stap
     * @param suffix
     *            suffix voor de output file zodat meerdere pl-en met dezelfde naam onderscheiden kunnen worden.
     * @return een gevulde PreconditieException
     */
    protected final OngeldigePersoonslijstException leesVerwachteOngeldigePersoonslijstException(final TestCasusOutputStap stap, final String suffix) {
        try {
            final File expectedXmlFile = new File(expectedFolder, maakBestandsnaam(FOLDER_XML, stap, suffix, EXTENSIE_XML));
            return TestOutput.readXml(OngeldigePersoonslijstException.class, expectedXmlFile);
        } catch (final TestOutputException e) {
            // log in standard output
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lees verwachte foutmelding.
     *
     * @param stap
     *            stap
     * @return een gevulde Foutmelding
     */
    protected final Foutmelding leesVerwachteFoutmelding(final TestCasusOutputStap stap) {
        try {
            final File expectedXmlFile = new File(expectedFolder, maakBestandsnaam(FOLDER_XML, stap, null, EXTENSIE_XML));
            return TestOutput.readXml(Foutmelding.class, expectedXmlFile);
        } catch (final TestOutputException e) {
            // log in standard output
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lees verwachte brp persoonslijst.
     *
     * @param stap
     *            stap
     * @return brp persoonslijst
     */
    protected final BrpPersoonslijst leesVerwachteBrpPersoonslijst(final TestCasusOutputStap stap) {
        return leesVerwachteBrpPersoonslijst(stap, null);
    }

    /**
     * Lees verwachte brp persoonslijst.
     *
     * @param stap
     *            stap
     * @param suffix
     *            suffix voor de output file zodat meerdere pl-en met dezelfde naam onderscheiden kunnen worden.
     * @return brp persoonslijst
     */
    protected final BrpPersoonslijst leesVerwachteBrpPersoonslijst(final TestCasusOutputStap stap, final String suffix) {
        final File expectedXmlFile = new File(expectedFolder, maakBestandsnaam(FOLDER_XML, stap, suffix, EXTENSIE_XML));
        return TestOutput.readXml(BrpPersoonslijst.class, expectedXmlFile);
    }

    /**
     * Lees verwachte brp autorisatie.
     *
     * @param stap
     *            stap
     * @return brp autorisatie
     */
    protected final BrpAutorisatie leesVerwachteBrpAutorisatie(final TestCasusOutputStap stap) {
        final File expectedXmlFile = new File(expectedFolder, maakBestandsnaam(FOLDER_XML, stap, null, EXTENSIE_XML));
        return TestOutput.readXml(BrpAutorisatie.class, expectedXmlFile);
    }

    /**
     * Lees verwachte brp afnemersindicaties.
     *
     * @param stap
     *            stap
     * @return brp autorisatie
     */
    protected final BrpAfnemersindicaties leesVerwachteBrpAfnemersindicaties(final TestCasusOutputStap stap) {
        final File expectedXmlFile = new File(expectedFolder, maakBestandsnaam(FOLDER_XML, stap, null, EXTENSIE_XML));
        return TestOutput.readXml(BrpAfnemersindicaties.class, expectedXmlFile);
    }

    /**
     * Lees verwachte logging.
     *
     * @param stap
     *            stap
     * @return logging
     */
    protected final Logging leesVerwachteLogging(final TestCasusOutputStap stap) {
        return leesVerwachteLogging(stap, null);
    }

    /**
     * Lees verwachte logging.
     *
     * @param stap
     *            stap
     * @param suffix
     *            suffix voor de output file zodat logging voor meerdere pl-en met dezelfde naam onderscheiden kunnen
     *            worden.
     * @return logging
     */
    protected final Logging leesVerwachteLogging(final TestCasusOutputStap stap, final String suffix) {
        final File expectedXmlFile = new File(expectedFolder, maakBestandsnaam(FOLDER_XML, stap, suffix, EXTENSIE_XML));
        return TestOutput.readXml(Logging.class, expectedXmlFile);
    }

    /**
     * Vergelijkt de gekregen log met de verwachte log. Verschillen worden dmv een verschilLog terug gegeven
     *
     * @param verschilLog
     *            log waarin de verschillen worden opgeslagen
     * @param verwachteLogging
     *            de verwachte logging
     * @param gekregenLogging
     *            de gekregen logging
     * @return true als er geen verschillen zijn
     */
    protected final boolean vergelijkLog(final StringBuilder verschilLog, final Logging verwachteLogging, final Logging gekregenLogging) {
        final List<String> expected = reworkLoggingToList(verwachteLogging);
        final List<String> actual = reworkLoggingToList(gekregenLogging);

        boolean result = true;
        final String expectedLabel = "expected";
        final String actualLabel = "actual";
        final String mesg = "Vergelijk logregel:%n\t%1$8s=%3$s%n\t%2$8s=%4$s%n";
        for (final String expectedRegel : expected) {
            if (!actual.contains(expectedRegel)) {
                verschilLog.append(String.format(mesg, expectedLabel, actualLabel, expectedRegel, ""));
                result = false;
            }
        }
        for (final String actualRegel : actual) {
            if (!expected.contains(actualRegel)) {
                verschilLog.append(String.format(mesg, expectedLabel, actualLabel, "", actualRegel));
                result = false;
            }
        }
        return result;
    }

    /**
     * Vergelijkt de gekregen verwerkingsmelding met de verwachte verwerkingsmelding. Verschillen worden dmv een
     * verschilLog terug gegeven
     *
     * @param verschilLog
     *            log waarin de verschillen worden opgeslagen
     * @param verwachteVerwerkingsmelding
     *            de verwachte verwerkingsmelding
     * @param gekregenVerwerkingsmelding
     *            de gekregen verwerkingsmelding
     * @return true als er geen verschillen zijn
     */
    protected final boolean vergelijkVerwerkingsmeldingen(
        final StringBuilder verschilLog,
        final String verwachteVerwerkingsmelding,
        final String gekregenVerwerkingsmelding)
    {
        final boolean result;
        if (!Objects.equals(verwachteVerwerkingsmelding, gekregenVerwerkingsmelding)) {
            final ComparisonCompactor cc = new ComparisonCompactor(VERSCHIL_CONTEXT, verwachteVerwerkingsmelding, gekregenVerwerkingsmelding);
            verschilLog.append(cc.compact("onverwachte verwerkingsmeldingen: "));
            result = false;
        } else {
            result = true;
        }
        return result;
    }

    private List<String> reworkLoggingToList(final Logging logging) {
        final List<String> result = new ArrayList<>();
        if (logging != null) {
            for (final LogRegel regel : logging.getRegels()) {
                result.add(regel.toString());
            }
        }

        return result;
    }

    /**
     * Controleert de opgegeven exception tegen een verwachte exception.
     *
     * @param e
     *            de exception die gecontroleerd moet worden
     * @param stap
     *            in welke stap dit gecontroleerd wordt
     * @return een {@link TestStap} object met daarin een OK als de exception verwacht is, anders NOK.
     */
    protected final TestStap controleerFoutmelding(final Exception e, final TestCasusOutputStap stap) {
        final Foutmelding foutmeldingException = new Foutmelding(e.getClass().getName(), e);
        final String htmlBrp = debugOutputXmlEnHtml(foutmeldingException, stap);
        final Foutmelding expectedFoutmelding = leesVerwachteFoutmelding(stap);
        final TestStap testStap;
        if (expectedFoutmelding == null) {
            testStap = new TestStap(TestStatus.NOK, null, htmlBrp, null);
        } else {
            if (expectedFoutmelding.equals(foutmeldingException)) {
                testStap = new TestStap(TestStatus.OK, null, htmlBrp, null);
            } else {
                final StringBuilder verschil = vergelijkFoutmeldingen(expectedFoutmelding, foutmeldingException);
                final Foutmelding fout = new Foutmelding("Vergelijking exception", "Exceptions zijn niet gelijk", verschil.toString());
                final String htmlFilename = debugOutputXmlEnHtml(fout, stap, SUFFIX_VERSCHILLEN);
                testStap = new TestStap(TestStatus.NOK, "Exception niet niet de verwachte", htmlBrp, htmlFilename);
            }
        }
        return testStap;
    }

    private StringBuilder vergelijkFoutmeldingen(final Foutmelding expected, final Foutmelding actual) {
        final StringBuilder verschil = new StringBuilder();
        verschil.append("Message verwacht: ").append(expected.getMessage()).append(EOL);
        verschil.append("Message gekregen: ").append(actual.getMessage()).append(EOL);
        verschil.append("Context verwacht: ").append(expected.getContext()).append(EOL);
        verschil.append("Context gekregen: ").append(actual.getContext());
        return verschil;
    }

    private String maakBestandsnaam(final String folderNaam, final TestCasusOutputStap stap, final String suffix, final String extensie) {
        return folderNaam + stap.getNaam() + File.separator + naam + (suffix == null ? "" : "-" + suffix) + extensie;
    }

    private void handleOutputException(final Exception e) {
        LOG.error("Exception: >>" + e.getMessage() + "<<");
        throw new TestOutputException("Fout tijdens output.", e);
    }

    /**
     * Combineert de methode {@link java.io.File#exists()} en {@link java.io.File#mkdir()}. Dit moet omdat de
     * {@link java.io.File#mkdir()} false terug geeft als de directory al bestaat.
     *
     * @param directory
     *            controleren of deze directory bestaat of gemaakt moet worden.
     * @return true als de directory bestaat of succesvol is aangemaakt.
     */
    private boolean createDirectory(final File directory) {
        return directory.exists() || directory.mkdirs();
    }

    /**
     * Verwijderd alle abonnementen, 'nieuwe' partijen en personen uit de BRP database.
     *
     * @param dataSource
     *            data source
     */
    protected final void initierenBrpDatabase(final DataSource dataSource) {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        initierenBrpDatabaseAutAut(jdbcTemplate);
        initierenBrpDatabaseBer(jdbcTemplate);
        initierenBrpDatabaseBrm(jdbcTemplate);
        initierenBrpDatabaseIst(jdbcTemplate);
        initierenBrpDatabaseMigblok(jdbcTemplate);
        initierenBrpDatabaseVerconv(jdbcTemplate);
        initierenBrpDatabaseKern(jdbcTemplate);
    }

    private void update(final JdbcTemplate jdbcTemplate, final String sql, final Object... arguments) {
        try {
            jdbcTemplate.update(sql, arguments);
        } catch (final DataAccessException e) {
            LOG.warn("Kan sql '{}' niet uivoeren", sql, e);
        }
    }

    private void deleteFrom(final JdbcTemplate jdbcTemplate, final String... tables) {
        for (final String table : tables) {
            update(jdbcTemplate, "DELETE FROM " + table);
        }
    }

    /**
     * Delete alles van het schema 'AUTAUT'.
     *
     * @param jdbcTemplate
     *            Het te gebruiken jdbcTemplate.
     */
    protected final void initierenBrpDatabaseAutAut(final JdbcTemplate jdbcTemplate) {
        deleteFrom(
            jdbcTemplate,
            "autaut.his_dienst",
            "autaut.his_dienstattendering",
            "autaut.his_dienstbundel",
            "autaut.his_dienstbundelgroep",
            "autaut.his_dienstbundelgroepattr",
            "autaut.his_dienstbundello3rubriek",
            "autaut.his_dienstselectie",
            "autaut.his_levsautorisatie",
            "autaut.his_partijfiatuitz",
            "autaut.his_persafnemerindicatie",
            "autaut.his_toegangbijhautorisatie",
            "autaut.his_toeganglevsautorisatie");

        deleteFrom(
            jdbcTemplate,
            "autaut.bijhautorisatiesrtadmhnd",
            "autaut.partijfiatuitz",
            "autaut.persafnemerindicatie",
            "autaut.dienst",
            "autaut.dienstbundelgroepattr",
            "autaut.dienstbundello3rubriek",
            "autaut.dienstbundel",
            "autaut.dienstbundelgroep",
            "autaut.toegangbijhautorisatie",
            "autaut.toeganglevsautorisatie",
            "autaut.levsautorisatie");
    }

    /**
     * Delete alles van het schema 'BER'.
     *
     * @param jdbcTemplate
     *            Het te gebruiken jdbcTemplate.
     */
    protected final void initierenBrpDatabaseBer(final JdbcTemplate jdbcTemplate) {
        deleteFrom(jdbcTemplate, "ber.berpers", "ber.ber");
    }

    /**
     * Delete alles van het schema 'BRM'.
     *
     * @param jdbcTemplate
     *            Het te gebruiken jdbcTemplate.
     */
    protected final void initierenBrpDatabaseBrm(final JdbcTemplate jdbcTemplate) {
        deleteFrom(jdbcTemplate, "brm.his_regelsituatie");
        deleteFrom(jdbcTemplate, "brm.regelsituatie");
    }

    /**
     * Delete alles van het schema 'IST'.
     *
     * @param jdbcTemplate
     *            Het te gebruiken jdbcTemplate.
     */
    protected final void initierenBrpDatabaseIst(final JdbcTemplate jdbcTemplate) {
        deleteFrom(jdbcTemplate, "ist.autorisatietabel");
        deleteFrom(jdbcTemplate, "ist.stapelrelatie", "ist.stapelvoorkomen", "ist.stapel");
    }

    /**
     * Delete alles van het schema 'KERN'.
     *
     * @param jdbcTemplate
     *            Het te gebruiken jdbcTemplate.
     */
    protected final void initierenBrpDatabaseKern(final JdbcTemplate jdbcTemplate) {
        deleteFrom(
            jdbcTemplate,
            "kern.his_betr",
            "kern.his_doc",
            "kern.his_onderzoek",
            "kern.his_onderzoekafgeleidadminis",
            "kern.his_ouderouderlijkgezag",
            "kern.his_ouderouderschap",
            "kern.his_partijonderzoek",
            "kern.his_partijrol",
            "kern.his_persadres",
            "kern.his_persafgeleidadministrati",
            "kern.his_persbijhouding",
            "kern.his_persdeelneuverkiezingen",
            "kern.his_persgeboorte",
            "kern.his_persgeslachtsaand",
            "kern.his_persgeslnaamcomp",
            "kern.his_persids",
            "kern.his_persindicatie",
            "kern.his_persinschr",
            "kern.his_persmigratie",
            "kern.his_persnaamgebruik",
            "kern.his_persnation",
            "kern.his_persnrverwijzing",
            "kern.his_personderzoek",
            "kern.his_persoverlijden",
            "kern.his_perspk",
            "kern.his_persreisdoc",
            "kern.his_perssamengesteldenaam",
            "kern.his_persuitslkiesr",
            "kern.his_persverblijfsr",
            "kern.his_persverificatie",
            "kern.his_persverstrbeperking",
            "kern.his_persvoornaam",
            "kern.his_relatie",
            "kern.his_terugmelding",
            "kern.his_terugmeldingcontactpers");

        update(jdbcTemplate, "update kern.his_partij set actieinh=null, actieverval=null, actievervaltbvlevmuts=null");

        deleteFrom(
            jdbcTemplate,
            "kern.gegeveninterugmelding",
            "kern.terugmelding",
            "kern.gegeveninonderzoek",
            "kern.partijonderzoek",
            "kern.personderzoek",
            "kern.onderzoek",
            "kern.betr",
            "kern.persadres",
            "kern.perscache",
            "kern.persgeslnaamcomp",
            "kern.persindicatie",
            "kern.persnation",
            "kern.persreisdoc",
            "kern.persverificatie",
            "kern.persverstrbeperking",
            "kern.persvoornaam",
            "kern.pers",
            "kern.relatie",
            "kern.actiebron",
            "kern.regelverantwoording",
            "kern.actie",
            "kern.admhndgedeblokkeerdemelding",
            "kern.admhnd",
            "kern.doc",
            "kern.gedeblokkeerdemelding");

        final Integer vanafPartijId = jdbcTemplate.queryForObject("SELECT id FROM kern.partij WHERE code = 701801", Integer.class);
        update(jdbcTemplate, "DELETE FROM kern.partijrol WHERE partij > ?", vanafPartijId);
        update(jdbcTemplate, "DELETE FROM kern.his_partij WHERE partij > ?", vanafPartijId);
        update(jdbcTemplate, "DELETE FROM kern.partij WHERE id > ?", vanafPartijId);
    }

    /**
     * Delete alles van het schema 'MIGBLOK'.
     *
     * @param jdbcTemplate
     *            Het te gebruiken jdbcTemplate.
     */
    protected final void initierenBrpDatabaseMigblok(final JdbcTemplate jdbcTemplate) {
        deleteFrom(jdbcTemplate, "migblok.blokkering");
    }

    /**
     * Delete alles van het schema 'VERCONV'.
     *
     * @param jdbcTemplate
     *            Het te gebruiken jdbcTemplate.
     */
    protected final void initierenBrpDatabaseVerconv(final JdbcTemplate jdbcTemplate) {
        deleteFrom(jdbcTemplate, "verconv.lo3melding", "verconv.lo3voorkomen", "verconv.lo3ber", "verconv.lo3aandouder");
    }
}
