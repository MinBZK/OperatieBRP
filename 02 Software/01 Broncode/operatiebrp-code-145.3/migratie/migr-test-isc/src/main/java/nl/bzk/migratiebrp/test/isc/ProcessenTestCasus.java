/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
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
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.test.common.reader.Reader;
import nl.bzk.migratiebrp.test.common.reader.TextReader;
import nl.bzk.migratiebrp.test.common.resultaat.Foutmelding;
import nl.bzk.migratiebrp.test.common.resultaat.TestStap;
import nl.bzk.migratiebrp.test.common.resultaat.TestStatus;
import nl.bzk.migratiebrp.test.dal.TestCasus;
import nl.bzk.migratiebrp.test.dal.TestCasusOutputStap;
import nl.bzk.migratiebrp.test.isc.bericht.TestBericht;
import nl.bzk.migratiebrp.test.isc.exception.TestException;
import nl.bzk.migratiebrp.test.isc.exception.TestNokException;

/**
 * Test casus: processen.
 */
public final class ProcessenTestCasus extends TestCasus {

    private static final String TRUE = "true";
    private static final Pattern FILE_NAME_PATTERN =
            Pattern.compile("([0-9]*)-([0-9]*)?(IN|UIT)([0-9]*)?-([A-Za-z_0-9]*)(-.*)?(\\..*)?", Pattern.CASE_INSENSITIVE);
    private static final int KANAAL_GROEP = 5;

    private static final String TESTCASE_SETUP = "testcase.properties";

    private static final String CONTROLE_PROCESSEN_BEEINDIGD_PROPERTY = "controle.processen.beeindigd";
    private static final String BEPAAL_ALLE_PROCESSEN_PROPERTY = "controle.bepaal.processen";

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final Set<String> UITGEVOERDE_EENMALIGE_BLOKKEN = new HashSet<>();

    private static final FileFilter FILE_FILTER = file -> file.isFile()
            && !file.getName().toLowerCase().endsWith("properties")
            && !file.getName().toLowerCase().endsWith("inject")
            && !file.getName().toLowerCase().endsWith("calculate")
            && !file.getName().toLowerCase().endsWith("extract")
            && !file.getName().toLowerCase().endsWith("check")
            && !file.getName().contains(".DS_Store")
            && !file.getName().contains("sch.naTestCase");

    private final File inputDirectory;
    private final File outputDirectory;
    private final TestEnvironment environment;

    private boolean bepaalAlleProcessen = true;
    private boolean controleProcessenBeeindigd = true;

    private Properties testcaseConfiguratie;
    private ProcessenTestContext context;

    /**
     * Constructor.
     * @param thema thema
     * @param naam naam
     * @param outputFolder output folder
     * @param expectedFolder expected folder
     * @param input input
     * @param environment environment
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

    /**
     * Geef de waarde van name.
     * @return testcasus naam
     */
    public String getName() {
        return inputDirectory.getName();
    }

    /**
     * Geef de waarde van output directory.
     * @return output directory
     */
    public File getOutputDirectory() {
        return outputDirectory;
    }

    /**
     * Geef de waarde van input directory.
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

        LOG.info("Before testcase");
        environment.beforeTestCase(this);

        LOG.info("Bepalen files");
        final List<File> files = Arrays.asList(inputDirectory.listFiles(FILE_FILTER));
        Collections.sort(files);
        Set<Long> procesIds = new HashSet<>();

        LOG.info("Verwerk bestanden");
        try {
            verwerkBestanden(files, "");

            if (bepaalAlleProcessen) {
                LOG.info("Processen bepalen...");
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

            LOG.info("After testcase...");
            boolean onverwachteBerichten = !environment.afterTestCase(this);

            LOG.info("Bepalen resultaat ...");
            if (!allesBeeindigd) {
                result.setResultaat(new TestStap(TestStatus.NOK, "Het proces is niet beeindigd", null, null));
            } else if (onverwachteBerichten) {
                result.setResultaat(new TestStap(TestStatus.NOK, "Er zijn nog onverwachte berichten gevonden", null, null));
            } else {
                result.setResultaat(new TestStap(TestStatus.OK));
            }
            LOG.info("Test case afgerond.");
        } catch (final Exception e /* Catch exception, anders klapt testcase er uit */) {
            LOG.info("Exception in test case", e);

            LOG.info("After testcase (in exception)...");
            environment.afterTestCase(this);

            LOG.info("Bepalen resultaat (in exception) ...");
            final Foutmelding fout = new Foutmelding("Onverwachte fout", e);
            final String htmlFout = debugOutputXmlEnHtml(fout, TestCasusOutputStap.ISC);

            result.setResultaat(new TestStap(bepaalTestStatus(e), e.getMessage(), htmlFout, null));
            if (bepaalAlleProcessen) {
                procesIds = environment.bepaalAlleProcessen();
            }
        }

        LOG.info("Toevoegen bestanden en processen aan resultaat");
        toevoegenBestandenAanResultaat(result);
        toevoegenProcessenAanResultaat(result, procesIds);

        LOG.info("Test case '" + inputDirectory.getName() + ": " + result.getResultaat().getStatus() + " -> " + result.getResultaat().getOmschrijving());

        return result;
    }

    private void verwerkBestanden(final List<File> files, final String voorloper) throws TestException {
        for (final File file : files) {
            final BlokStappen blokStappen = geefBlokStappen(file);
            if (blokStappen.isBlok()) {
                try {
                    final Reader reader = new TextReader();
                    final String blokStappenMapNaam = reader.readFile(file).trim();
                    final File blokDirectory = new File(file.getParent(), blokStappenMapNaam);
                    final String blokStappenIdentifier = blokDirectory.getCanonicalPath();

                    if (!blokStappen.isEenmaligUitvoeren() || !UITGEVOERDE_EENMALIGE_BLOKKEN.contains(blokStappenIdentifier)) {
                        final List<File> blokStappenBestanden = Arrays.asList(blokDirectory.listFiles(FILE_FILTER));
                        if (blokStappen.isParallel()) {
                            verwerkBlokstappenParallel(blokStappenBestanden, voorloper + blokStappen.getVoorloper());
                        } else {
                            Collections.sort(blokStappenBestanden);
                            verwerkBestanden(blokStappenBestanden, voorloper + blokStappen.getVoorloper());
                        }
                    }
                    UITGEVOERDE_EENMALIGE_BLOKKEN.add(blokStappenIdentifier);
                } catch (final IOException e) {
                    throw new IllegalArgumentException("Kan file niet lezen", e);
                }
            } else {
                verwerkBestand(file, voorloper);
            }
        }
    }

    private void verwerkBlokstappenParallel(List<File> blokStappenBestanden, String voorloper)
            throws TestException {
        final ExecutorService executorService = Executors.newFixedThreadPool(8);
        final LongAdder numberOfExceptions = new LongAdder();
        try {
            // Create a list of callables (so we can catch the exceptions)
            final List<Callable<Void>> callables =
                    blokStappenBestanden
                            .stream()
                            .map(blokStappenBestand -> (Callable<Void>) () -> {
                                try {
                                    verwerkBestanden(Collections.singletonList(blokStappenBestand), voorloper);

                                } catch (Exception e) {
                                    numberOfExceptions.add(1);
                                }
                                return null;
                            })
                            .collect(Collectors.toList());

            // Invoke all futures (waits until all callables are done)
            // Ignore all results we count exceptions using the numberOfExceptions adder
            try {
                executorService.invokeAll(callables);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        } finally {
            executorService.shutdownNow();
        }

        if (numberOfExceptions.longValue() > 0) {
            throw new TestException(numberOfExceptions.toString() + " van de parallele stappen is/zijn gefaald");
        }
    }

    private BlokStappen geefBlokStappen(final File testBericht) {
        final Matcher matcher = FILE_NAME_PATTERN.matcher(testBericht.getName());
        if (!matcher.matches()) {
            throw new IllegalArgumentException("File '" + testBericht.getName() + "' voldoet niet aan de naamgeving.");
        }
        if ("blok".equalsIgnoreCase(matcher.group(KANAAL_GROEP).toUpperCase())) {
            return new BlokStappen(true, false, false, matcher.group(1) + "-");
        } else if ("eenmalig_blok".equalsIgnoreCase(matcher.group(KANAAL_GROEP).toUpperCase())) {
            return new BlokStappen(true, true, false, matcher.group(1) + "-");
        } else if ("parallel_blok".equalsIgnoreCase(matcher.group(KANAAL_GROEP).toUpperCase())) {
            return new BlokStappen(true, false, true, matcher.group(1) + "-");
        } else {
            return new BlokStappen(false, false, false, "");
        }
    }

    private TestStatus bepaalTestStatus(final Exception e) {
        return e instanceof TestNokException ? TestStatus.NOK : TestStatus.EXCEPTIE;
    }

    private void toevoegenProcessenAanResultaat(final ProcessenTestResultaat result, final Set<Long> procesIds) {
        final List<Long> processen = new ArrayList<>(procesIds);
        Collections.sort(processen);
        result.setProcessen(processen);
    }

    private void verwerkBestand(final File file, final String voorloper) throws TestException {
        LOG.info("Verwerk bericht - " + voorloper + file.getName());
        final TestBericht bericht = new TestBericht(file, outputDirectory, voorloper);

        environment.verwerkBericht(this, bericht);
    }

    private void toevoegenBestandenAanResultaat(final ProcessenTestResultaat result) {

        final String[] bestanden = outputDirectory.list((dir, name) -> new File(dir, name).isFile());

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
     * @return testcase configuratie
     */
    public Properties getTestcaseConfiguratie() {
        return testcaseConfiguratie;
    }

    private void readSetup() {
        testcaseConfiguratie = new Properties();
        testcaseConfiguratie.setProperty("dummy", Boolean.TRUE.toString());
        testcaseConfiguratie.setProperty("quit", Boolean.TRUE.toString());

        final File testcaseSetupFile = new File(inputDirectory, TESTCASE_SETUP);
        final File themaSetupFile = new File(inputDirectory.getParentFile(), TESTCASE_SETUP);
        final File setSetupFile = new File(inputDirectory.getParentFile().getParentFile(), TESTCASE_SETUP);

        // Set specifiek
        if (setSetupFile.exists()) {
            try (FileInputStream fis = new FileInputStream(setSetupFile)) {
                load(testcaseConfiguratie, fis);
            } catch (final IOException e) {
                LOG.warn("Kan testcase configuratie (set niveau) niet lezen", e);
            }
        }

        // Thema specifiek
        if (themaSetupFile.exists()) {
            try (FileInputStream fis = new FileInputStream(themaSetupFile)) {
                load(testcaseConfiguratie, fis);
            } catch (final IOException e) {
                LOG.warn("Kan testcase configuratie (thema niveau) niet lezen", e);
            }
        }

        // Testcase specifiek
        if (testcaseSetupFile.exists()) {
            try (FileInputStream fis = new FileInputStream(testcaseSetupFile)) {
                load(testcaseConfiguratie, fis);
            } catch (final IOException e) {
                LOG.warn("Kan testcase configuratie (testcase niveau) niet lezen", e);
            }
        }

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
            return TRUE.equalsIgnoreCase(value);
        }
    }

    /**
     * Representatie van blokstappen.
     */
    private static class BlokStappen {
        private final boolean blok;
        private final boolean eenmaligUitvoeren;
        private final boolean parallel;
        private final String voorloper;

        BlokStappen(final boolean blok, final boolean eenmaligUitvoeren, final boolean parallel, final String voorloper) {
            this.blok = blok;
            this.eenmaligUitvoeren = eenmaligUitvoeren;
            this.parallel = parallel;
            this.voorloper = voorloper;
        }

        public final boolean isBlok() {
            return blok;
        }

        public final boolean isEenmaligUitvoeren() {
            return eenmaligUitvoeren;
        }

        public final boolean isParallel() {
            return parallel;
        }

        public final String getVoorloper() {
            return voorloper;
        }
    }
}
