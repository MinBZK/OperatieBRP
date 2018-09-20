/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import nl.bzk.migratiebrp.test.common.resultaat.Foutmelding;
import nl.bzk.migratiebrp.test.common.resultaat.TestStap;
import nl.bzk.migratiebrp.test.common.resultaat.TestStatus;
import nl.bzk.migratiebrp.test.dal.TestCasus;
import nl.bzk.migratiebrp.test.dal.TestCasusOutputStap;
import nl.bzk.migratiebrp.test.isc.bericht.TestBericht;
import nl.bzk.migratiebrp.test.isc.exception.TestException;
import nl.bzk.migratiebrp.test.isc.exception.TestNokException;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;

/**
 * Test casus: processen.
 */
public final class ProcessenTestCasus extends TestCasus {

    private static final String TESTCASE_SETUP = "testcase.properties";

    private static final String CONTROLE_PROCESSEN_BEEINDIGD_PROPERTY = "controle.processen.beeindigd";
    private static final String BEPAAL_ALLE_PROCESSEN_PROPERTY = "controle.bepaal.processen";

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final FileFilter FILE_FILTER = new FileFilter() {
        @Override
        public boolean accept(final File file) {
            return file.isFile()
                   && !file.getName().toLowerCase().endsWith("properties")
                   && !file.getName().toLowerCase().endsWith("inject")
                   && !file.getName().toLowerCase().endsWith("extract")
                   && !file.getName().toLowerCase().endsWith("check")
                   && !file.getName().contains(".DS_Store")
                   && !file.getName().contains("sch.naTestCase");
        }
    };

    private final File inputDirectory;
    private final File outputDirectory;
    private final TestEnvironment environment;

    private boolean bepaalAlleProcessen = true;
    private boolean controleProcessenBeeindigd = true;

    private Properties testcaseConfiguratie;
    private ProcessenTestContext context;

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
        final TestEnvironment environment)
    {
        super(thema, naam, outputFolder, expectedFolder);
        inputDirectory = input;
        outputDirectory = new File(outputFolder, inputDirectory.getName());
        this.environment = environment;
    }

    /**
     * Geef de waarde van name.
     *
     * @return testcasus naam
     */
    public String getName() {
        return inputDirectory.getName();
    }

    /**
     * Geef de waarde van output directory.
     *
     * @return output directory
     */
    public File getOutputDirectory() {
        return outputDirectory;
    }

    /**
     * Geef de waarde van input directory.
     *
     * @return input directory
     */
    public File getInputDirectory() {
        return inputDirectory;
    }

    @Override
    public ProcessenTestResultaat run() {
        final ProcessenTestResultaat result = new ProcessenTestResultaat(getThema(), getNaam());
        context = new ProcessenTestContext();

        LOG.info("Uitvoeren test case: " + inputDirectory.getName());
        readSetup();

        environment.beforeTestCase(this);

        final List<File> files = Arrays.asList(inputDirectory.listFiles(FILE_FILTER));
        Collections.sort(files);
        Set<Long> procesIds = new HashSet<>();

        File huidigeFile = null;
        try {
            for (final File file : files) {
                huidigeFile = file;
                verwerkBestand(file);
            }
            huidigeFile = null;

            LOG.info("Afronden test case ...");
            if (bepaalAlleProcessen) {
                procesIds = environment.bepaalAlleProcessen();
            }

            boolean allesBeeindigd = true;
            if (controleProcessenBeeindigd) {
                LOG.info("Controle alle processen beeindigd...");
                for (final Long procesId : procesIds) {
                    if (!environment.controleerProcesBeeindigd(procesId)) {
                        allesBeeindigd = false;
                        break;
                    }
                }
            }

            if (!allesBeeindigd) {
                environment.afterTestCase(this);
                result.setResultaat(new TestStap(TestStatus.NOK, "Het proces is niet beeindigd", null, null));
            } else if (!environment.afterTestCase(this)) {
                result.setResultaat(new TestStap(TestStatus.NOK, "Er zijn nog onverwachte berichten gevonden", null, null));
            } else {
                result.setResultaat(new TestStap(TestStatus.OK));
            }
            LOG.info("Test case afgerond.");
        } catch (final Exception e /* Catch exception, anders klapt testcase er uit */) {
            LOG.info("Exception in test case", e);
            if (huidigeFile != null) {
                LOG.info("Stap in uitvoering: " + huidigeFile.getName());
            }

            environment.afterTestCase(this);

            final Foutmelding fout = new Foutmelding("Onverwachte fout", e);
            final String htmlFout = debugOutputXmlEnHtml(fout, TestCasusOutputStap.ISC);

            result.setResultaat(new TestStap(bepaalTestStatus(e), e.getMessage(), htmlFout, null));
            if (bepaalAlleProcessen) {
                procesIds = environment.bepaalAlleProcessen();
            }
        }

        toevoegenBestandenAanResultaat(result);
        toevoegenProcessenAanResultaat(result, procesIds);

        LOG.info("Test case '" + inputDirectory.getName() + ": " + result.getResultaat().getStatus() + " -> " + result.getResultaat().getOmschrijving());

        return result;
    }

    private TestStatus bepaalTestStatus(final Exception e) {
        return e instanceof TestNokException ? TestStatus.NOK : TestStatus.EXCEPTIE;
    }

    private void toevoegenProcessenAanResultaat(final ProcessenTestResultaat result, final Set<Long> procesIds) {
        final List<Long> processen = new ArrayList<>(procesIds);
        Collections.sort(processen);
        result.setProcessen(processen);
    }

    private void verwerkBestand(final File file) throws TestException {
        LOG.info(" - " + file.getName());
        final TestBericht bericht = new TestBericht(file, outputDirectory);

        environment.verwerkBericht(this, bericht);
    }

    private void toevoegenBestandenAanResultaat(final ProcessenTestResultaat result) {

        final String[] bestanden = outputDirectory.list(new FilenameFilter() {

            @Override
            public boolean accept(final File dir, final String name) {
                return new File(dir, name).isFile();
            }
        });

        if (bestanden != null) {
            // Bestanden
            final List<String> outputFiles = Arrays.asList(bestanden);
            Collections.sort(outputFiles);

            for (final String outputFile : outputFiles) {
                result.addBestand(outputFile);
            }
        }
    }

    /* ****************************************** */
    /* *** CONTEXT ****************************** */
    /* ****************************************** */

    /**
     * Geef de waarde van context.
     *
     * @return context
     */
    public ProcessenTestContext getContext() {
        return context;
    }

    /* ****************************************** */
    /* *** TEST CASE SETUP ********************** */
    /* ****************************************** */

    /**
     * Geef de waarde van testcase configuratie.
     *
     * @return testcase configuratie
     */
    public Properties getTestcaseConfiguratie() {
        return testcaseConfiguratie;
    }

    private void readSetup() {
        testcaseConfiguratie = new Properties();
        testcaseConfiguratie.setProperty("dummy", "true");
        testcaseConfiguratie.setProperty("quit", "true");

        final File testcaseSetupFile = new File(inputDirectory, TESTCASE_SETUP);
        final File themaSetupFile = new File(inputDirectory.getParentFile(), TESTCASE_SETUP);
        final File setSetupFile = new File(inputDirectory.getParentFile().getParentFile(), TESTCASE_SETUP);

        // System.out.println("Basis configuratie: " + testcaseConfiguratie);

        // Set specifiek
        if (setSetupFile.exists()) {
            try (FileInputStream fis = new FileInputStream(setSetupFile)) {
                load(testcaseConfiguratie, fis);
            } catch (final IOException e) {
                LOG.warn("Kan testcase configuratie (set niveau) niet lezen", e);
            }
        }
        // System.out.println("Configuratie (na set instelling): " + testcaseConfiguratie);

        // Thema specifiek
        if (themaSetupFile.exists()) {
            try (FileInputStream fis = new FileInputStream(themaSetupFile)) {
                load(testcaseConfiguratie, fis);
            } catch (final IOException e) {
                LOG.warn("Kan testcase configuratie (thema niveau) niet lezen", e);
            }
        }
        // System.out.println("Configuratie (na thema instelling): " + testcaseConfiguratie);

        // Testcase specifiek
        if (testcaseSetupFile.exists()) {
            try (FileInputStream fis = new FileInputStream(testcaseSetupFile)) {
                load(testcaseConfiguratie, fis);
            } catch (final IOException e) {
                LOG.warn("Kan testcase configuratie (testcase niveau) niet lezen", e);
            }
        }
        // System.out.println("Configuratie (na testcase instelling): " + testcaseConfiguratie);

        controleProcessenBeeindigd = getSetting(testcaseConfiguratie.getProperty(CONTROLE_PROCESSEN_BEEINDIGD_PROPERTY), true);
        bepaalAlleProcessen = getSetting(testcaseConfiguratie.getProperty(BEPAAL_ALLE_PROCESSEN_PROPERTY), true);
    }

    private void load(final Properties configuratie, final InputStream is) throws IOException {
        final Properties properties = new Properties();
        properties.load(is);

        final Enumeration<String> propertyNames = (Enumeration<String>) properties.propertyNames();
        while (propertyNames.hasMoreElements()) {
            final String propertyName = propertyNames.nextElement();
            configuratie.setProperty(propertyName.toLowerCase(), properties.getProperty(propertyName));
        }

    }

    private boolean getSetting(final String value, final boolean defaultValue) {
        if (value == null || "".equals(value)) {
            return defaultValue;
        } else {
            return "true".equalsIgnoreCase(value);
        }
    }
}
