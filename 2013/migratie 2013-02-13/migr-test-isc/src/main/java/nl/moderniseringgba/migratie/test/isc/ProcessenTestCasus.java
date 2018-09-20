/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.isc;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;
import nl.moderniseringgba.migratie.test.TestCasus;
import nl.moderniseringgba.migratie.test.isc.environment.TestEnvironment;
import nl.moderniseringgba.migratie.test.resultaat.Foutmelding;
import nl.moderniseringgba.migratie.test.resultaat.TestStap;
import nl.moderniseringgba.migratie.test.resultaat.TestStatus;

/**
 * Test casus: processen.
 */
public final class ProcessenTestCasus extends TestCasus {

    /**
     * Constantes voor de standaard/variabele gemeente instellingen.
     */
    private static final String EXTENSIE_GEMEENTE_INSTELLINGEN_TEST_CASE = ".properties";

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final FileFilter FILE_FILTER = new FileFilter() {
        @Override
        public boolean accept(final File file) {
            return file.isFile() && !file.getName().contains("properties") && !file.getName().contains(".DS_Store");
        }
    };

    private final File inputDirectory;
    private final File outputDirectory;
    private final TestEnvironment environment;

    /**
     * Constructor.
     * 
     * @param thema
     *            thema
     * @param naam
     *            naam
     * @param outputFolder
     *            output folder
     * @param expectedFolder
     *            expected folder
     * @param input
     *            input
     * @param environment
     *            environment
     */
    protected ProcessenTestCasus(
            final String thema,
            final String naam,
            final File outputFolder,
            final File expectedFolder,
            final File input,
            final TestEnvironment environment) {
        super(thema, naam, outputFolder, expectedFolder);
        inputDirectory = input;
        outputDirectory = new File(outputFolder, inputDirectory.getName());
        this.environment = environment;
    }

    public String getName() {
        return inputDirectory.getName();
    }

    public File getOutputDirectory() {
        return outputDirectory;
    }

    @Override
    public ProcessenTestResultaat run() {
        final ProcessenTestResultaat result = new ProcessenTestResultaat(getThema(), getNaam());

        LOG.info("Executing test case: " + inputDirectory.getName());
        environment.clearCorrelatie();
        environment.beforeTestCase(this);

        final List<File> files = Arrays.asList(inputDirectory.listFiles(FILE_FILTER));
        Collections.sort(files);

        try {
            for (final File file : files) {
                verwerkBestand(file);
            }

            LOG.info("Ending test case ...");
            if (!environment.controleerProcesBeeindigd()) {
                environment.afterTestCase(this);
                result.setResultaat(new TestStap(TestStatus.NOK, "Het proces is niet beeindigd", null, null));
            } else if (!environment.afterTestCase(this)) {
                result.setResultaat(new TestStap(TestStatus.NOK, "Er zijn nog onverwachte berichten gevonden", null,
                        null));
            } else {
                result.setResultaat(new TestStap(TestStatus.OK));
            }
        } catch (final TestException e) {
            LOG.info("Exception in test case: " + e.getMessage());
            environment.afterTestCase(this);

            final Foutmelding fout = new Foutmelding("Onverwachte fout", e);
            final String htmlFout = debugOutputXmlEnHtml(fout, "isc");

            result.setResultaat(new TestStap(e instanceof TestNokException ? TestStatus.NOK : TestStatus.EXCEPTIE, e
                    .getMessage(), htmlFout, null));
        }

        toevoegenBestandenAanResultaat(result);

        // Proces instance
        result.setProcesInstanceId(environment.getProcesInstanceId());

        LOG.info("Test case '" + inputDirectory.getName() + ": " + result.getResultaat().getStatus() + " -> "
                + result.getResultaat().getOmschrijving());

        return result;
    }

    private void verwerkBestand(final File file) throws TestException {
        LOG.info(" - " + file.getName());
        final TestBericht bericht = new TestBericht(file, outputDirectory);

        final Properties testCaseProperties = new Properties();
        final File testCasePropertiesFile =
                new File(inputDirectory + File.separator + file.getName() + EXTENSIE_GEMEENTE_INSTELLINGEN_TEST_CASE);
        try {
            if (testCasePropertiesFile.exists()) {
                testCaseProperties.load(new FileInputStream(testCasePropertiesFile));
                bericht.setLo3Gemeente(testCaseProperties.getProperty(TestBericht.SLEUTEL_LO3_GEMEENTE,
                        bericht.getLo3Gemeente()));
                bericht.setBrpGemeente(testCaseProperties.getProperty(TestBericht.SLEUTEL_BRP_GEMEENTE,
                        bericht.getBrpGemeente()));

            }
        } catch (final IOException e1) {
            throw new TestException("Probleem bij inlezen testbericht specifiek properties");
        }

        environment.verwerkBericht(bericht);
    }

    private void toevoegenBestandenAanResultaat(final ProcessenTestResultaat result) {

        final String[] bestanden = outputDirectory.list();
        if (bestanden != null) {
            // Bestanden
            final List<String> outputFiles = Arrays.asList(bestanden);
            Collections.sort(outputFiles);

            for (final String outputFile : outputFiles) {
                result.addBestand(outputFile);
            }
        }
    }

}
