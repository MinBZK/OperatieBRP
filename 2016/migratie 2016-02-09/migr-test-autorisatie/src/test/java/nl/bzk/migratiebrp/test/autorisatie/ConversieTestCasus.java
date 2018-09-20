/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.autorisatie;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.inject.Inject;
import javax.sql.DataSource;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAutorisatie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Autorisatie;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerLo3NaarBrpService;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.PreconditiesService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import nl.bzk.migratiebrp.test.common.output.TestOutputException;
import nl.bzk.migratiebrp.test.common.resultaat.Foutmelding;
import nl.bzk.migratiebrp.test.common.resultaat.TestResultaat;
import nl.bzk.migratiebrp.test.common.resultaat.TestStap;
import nl.bzk.migratiebrp.test.common.resultaat.TestStatus;
import nl.bzk.migratiebrp.test.dal.TestCasus;
import nl.bzk.migratiebrp.test.dal.TestCasusOutputStap;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.common.logging.LoggingContext;

/**
 * Test casus: conversie lo3 naar brp.
 */
@SuppressWarnings("checkstyle:illegalcatch")
public final class ConversieTestCasus extends TestCasus {
    private static final String SUFFIX_VERSCHILLEN = "-verschillen";

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final int MILLIS_IN_SECOND = 1000;

    private static final String TESTCASE_SETUP = "testcase.properties";

    private static final String SKIP_PARTIJ = "skip.partij";
    private static final String STR_EXCEPTION = "exception";

    private static String laatsteTestCase;
    private final Lo3Autorisatie input;
    private final File inputFile;
    private boolean skipPartij;
    @Inject
    private PreconditiesService preconditieService;
    @Inject
    private BrpDalService brpDalService;
    @Inject
    private ConverteerLo3NaarBrpService converteerLo3NaarBrpService;
    @Inject
    private DataSource dataSource;

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
     *            input lo3autorisatie
     * @param inputFile
     *            input file
     */
    protected ConversieTestCasus(
        final String thema,
        final String naam,
        final File outputFolder,
        final File expectedFolder,
        final Lo3Autorisatie input,
        final File inputFile)
    {
        super(thema, naam, outputFolder, expectedFolder);
        this.input = input;
        this.inputFile = inputFile;
    }

    @Override
    public TestResultaat run() {
        final long startInMillis = System.currentTimeMillis();
        final ConversieTestResultaat result = new ConversieTestResultaat(getThema(), getNaam());

        try {
            Logging.initContext();
            // Stap 0: initieren
            readSetup();
            initieren(input, result);

            if (TestStatus.OK.equals(result.getInitieren().getStatus())) {

                Logging.initContext();
                final Logging logging = Logging.getLogging();

                // Stap 1a: Precondities
                final Lo3Autorisatie aut = testPrecondities(input, result);

                // Stap 1b: Converteren (input=lo3 model, output=brp model)
                final BrpAutorisatie brp = testLo3NaarBrp(aut, result);

                // Stap 1c: Controleer logging
                controleerLogging(logging, result);

                // Stap 2: Opslaan (input=brp model, output=partijCode)
                final Integer partijCode = testOpslaanBrp(brp, result);

                // Stap 3: Lezen (input=brp model, partij)
                testLezenBrp(partijCode, brp, result);

            }

        } finally {
            LoggingContext.reset();
            Logging.destroyContext();
        }

        final long endInMillis = System.currentTimeMillis();
        final float duration = (endInMillis - startInMillis) / (float) MILLIS_IN_SECOND;

        LOG.info("Testcase {} took {} seconds", getNaam(), duration);

        return result;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Initieren (schrijf debug info).
     *
     * @param lo3
     *            lo3
     * @param result
     *            result
     */
    private void initieren(final Lo3Autorisatie lo3, final ConversieTestResultaat result) {
        try {
            final String testCase = result.getNaam();
            LOG.info("Testcase: {}", testCase);

            if (laatsteTestCase == null || !testCase.equals(laatsteTestCase)) {
                LOG.info("Init database");
                initierenBrpDatabase(dataSource);
                laatsteTestCase = testCase;
            }
            final String htmlLo3 = debugOutputXmlEnHtml(lo3, TestCasusOutputStap.STAP_LO3);
            result.setInitieren(new TestStap(TestStatus.OK, null, htmlLo3, null));

        } catch (final TestOutputException e) {
            final Foutmelding fout = new Foutmelding("Fout tijdens initieren.", e);
            final String htmlFout = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_LO3, STR_EXCEPTION);

            result.setInitieren(new TestStap(TestStatus.NOK, e.getMessage(), htmlFout, null));
        }
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Test de precondities.
     */
    private Lo3Autorisatie testPrecondities(final Lo3Autorisatie lo3Autorisatie, final ConversieTestResultaat result) {
        if (lo3Autorisatie != null) {
            LOG.info("Test controleer precondities ...");

            try {
                // Precondities
                preconditieService.verwerk(lo3Autorisatie);
                result.setPrecondities(new TestStap(TestStatus.OK));

                return lo3Autorisatie;
            } catch (final Exception aoe) {
                final Foutmelding fout = new Foutmelding("Fout tijdens controleren precondities.", aoe);
                final String htmlFout = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_SYNTAX_PRECONDITIES);

                // Lees verwachte expection
                final Foutmelding verwachteFout = leesVerwachteFoutmelding(TestCasusOutputStap.STAP_SYNTAX_PRECONDITIES);
                if (verwachteFout != null) {
                    if (verwachteFout.equals(fout)) {
                        result.setPrecondities(
                            new TestStap(TestStatus.OK, "Er is een verwachte exceptie opgetreden (controleren precondities)", htmlFout, null));
                    } else {
                        result.setPrecondities(
                            new TestStap(TestStatus.NOK, "Er is een anders dan verwachte exceptie opgetreden (controleren precondities)", htmlFout, null));
                    }
                } else {
                    result.setPrecondities(new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden (controleren precondities)", htmlFout, null));

                }

            }
        }
        return null;
    }

    /**
     * Test conversie lo3 naar brp.
     *
     * @param lo3
     *            lo3
     * @param result
     *            result
     * @return bp
     */
    private BrpAutorisatie testLo3NaarBrp(final Lo3Autorisatie lo3, final ConversieTestResultaat result) {
        if (lo3 != null) {
            LOG.info("Test converteer LO3 naar BRP ...");
            try {

                final BrpAutorisatie brp = BrpAutorisatieSorter.sorteerAutorisatie(converteerLo3NaarBrpService.converteerLo3Autorisatie(lo3));
                final String htmlBrp = debugOutputXmlEnHtml(brp, TestCasusOutputStap.STAP_BRP);

                try {
                    // Controleer verwachten
                    final BrpAutorisatie expectedBrp = leesVerwachteBrpAutorisatie(TestCasusOutputStap.STAP_BRP);
                    if (expectedBrp == null) {
                        result.setLo3NaarBrp(new TestStap(TestStatus.GEEN_VERWACHTING, null, htmlBrp, null));
                    } else {
                        final BrpAutorisatie expected = BrpAutorisatieSorter.sorteerAutorisatie(expectedBrp);
                        final StringBuilder verschillenLog = new StringBuilder();
                        if (BrpAutorisatieVergelijker.vergelijkAutorisaties(verschillenLog, expected, brp, false)) {
                            result.setLo3NaarBrp(new TestStap(TestStatus.OK, null, htmlBrp, null));
                        } else {
                            final Foutmelding fout =
                                    new Foutmelding("Vergelijking (lo3 naar brp)", "Inhoud is ongelijk (lo3 naar brp)", verschillenLog.toString());
                            final String htmlVerschillen = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_BRP, SUFFIX_VERSCHILLEN);

                            result.setLo3NaarBrp(new TestStap(TestStatus.NOK, "Er zijn inhoudelijke verschillen (brp)", htmlBrp, htmlVerschillen));
                        }
                    }
                } catch (final Exception e) {
                    final Foutmelding fout = new Foutmelding("Fout tijdens vergelijken verwachting (converteren LO3 naar BRP).", e);
                    final String htmlFout = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_BRP, STR_EXCEPTION);

                    result.setLo3NaarBrp(
                        new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden (vergelijken verwachting conversie naar brp) ", htmlFout, null));

                }
                return brp;
            } catch (final Exception e) {
                final Foutmelding fout = new Foutmelding("Fout tijdens converteren LO3 naar BRP.", e);
                final String htmlFout = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_BRP);

                final Foutmelding expectedFout = leesVerwachteFoutmelding(TestCasusOutputStap.STAP_BRP);
                if (expectedFout != null) {
                    if (expectedFout.equals(fout)) {
                        result.setLo3NaarBrp(new TestStap(TestStatus.OK, "De verwachte fout is opgetreden", htmlFout, null));
                    } else {
                        result.setLo3NaarBrp(
                            new TestStap(TestStatus.NOK, "Er is een anders dan verwacht fout opgetreden (conversie naar brp)", htmlFout, null));
                    }
                } else {
                    result.setLo3NaarBrp(new TestStap(TestStatus.NOK, "Onverwachte fout  (conversie naar brp)", htmlFout, null));

                }
            }
        }
        return null;
    }

    private void controleerLogging(final Logging logging, final ConversieTestResultaat result) {
        final String loggingHtml = debugOutputXmlEnHtml(logging, TestCasusOutputStap.STAP_LOGGING);

        // Controleer verwachting logging
        final Logging verwachteLogging = leesVerwachteLogging(TestCasusOutputStap.STAP_LOGGING);

        if (verwachteLogging == null) {
            if (logging.getRegels().isEmpty()) {
                result.setConversieLog(new TestStap(TestStatus.OK, null, loggingHtml, null));
            } else {
                result.setConversieLog(new TestStap(TestStatus.GEEN_VERWACHTING, null, loggingHtml, null));
            }
        } else {
            final StringBuilder verschillenLog = new StringBuilder();
            if (vergelijkLog(verschillenLog, verwachteLogging, logging)) {
                result.setConversieLog(new TestStap(TestStatus.OK, null, loggingHtml, null));
            } else {
                final Foutmelding fout = new Foutmelding("Vergelijking (logging)", "Inhoud is ongelijk (logging)", verschillenLog.toString());
                final String htmlVerschillen = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_LOGGING, SUFFIX_VERSCHILLEN);
                result.setConversieLog(new TestStap(TestStatus.NOK, "Er zijn inhoudelijke verschillen (logging)", loggingHtml, htmlVerschillen));
            }
        }
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Test opslaan in BRP.
     *
     * @param brpAutorisatie
     *            brp
     * @param result
     *            resultaat
     * @return anummer
     */
    private Integer testOpslaanBrp(final BrpAutorisatie brpAutorisatie, final ConversieTestResultaat result) {
        if (brpAutorisatie != null) {
            LOG.info("Test opslaan in BRP ...");
            try {
                // Opslaan in database
                brpDalService.persisteerAutorisatie(brpAutorisatie);

                result.setOpslaanBrp(new TestStap(TestStatus.OK));

                // Partij teruggeven om hiermee vervolgens uit de db te lezen
                return brpAutorisatie.getPartij().getPartijCode().getWaarde();

            } catch (final Exception e) {
                final Foutmelding fout = new Foutmelding("Fout tijdens opslaan BRP.", e);
                final String htmlFout = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_OPSLAAN);

                result.setOpslaanBrp(new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden (opslaan)", htmlFout, null));
            }
        }

        return null;

    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Test lezen uit brp.
     *
     * @param partijCode
     *            partijCode
     * @param inputBrp
     *            brp input
     * @param result
     *            resultaat
     */

    private void testLezenBrp(final Integer partijCode, final BrpAutorisatie inputBrp, final ConversieTestResultaat result) {

        if (partijCode != null) {
            LOG.info("Test lezen uit BRP ...");
            try {
                // Lezen uit database
                final String besluitNaam = null;
                Integer ingangsDatumRegel = null;
                if (inputBrp.getLeveringsAutorisatieLijst() != null && !inputBrp.getLeveringsAutorisatieLijst().isEmpty()) {
                    ingangsDatumRegel =
                            inputBrp.getLeveringsAutorisatieLijst().get(0).getLeveringsautorisatieStapel().get(0).getInhoud().getDatumIngang().getWaarde();
                }

                final BrpAutorisatie brp =
                        BrpAutorisatieSorter.sorteerAutorisatie(brpDalService.bevraagAutorisatie(partijCode, besluitNaam, ingangsDatumRegel));
                final String htmlBrp = debugOutputXmlEnHtml(brp, TestCasusOutputStap.STAP_LEZEN);

                try {
                    BrpAutorisatie expectedBrp = leesVerwachteBrpAutorisatie(TestCasusOutputStap.STAP_LEZEN);
                    if (expectedBrp == null) {
                        expectedBrp = inputBrp;
                    }

                    final BrpAutorisatie expected = BrpAutorisatieSorter.sorteerAutorisatie(expectedBrp);

                    final StringBuilder verschillenLog = new StringBuilder();
                    if (BrpAutorisatieVergelijker.vergelijkAutorisaties(verschillenLog, expected, brp, skipPartij)) {
                        result.setLezenBrp(new TestStap(TestStatus.OK, null, htmlBrp, null));
                    } else {
                        final Foutmelding fout = new Foutmelding("Vergelijking (lezen)", "Inhoud is ongelijk (lezen)", verschillenLog.toString());
                        final String htmlVerschillen = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_LEZEN, SUFFIX_VERSCHILLEN);

                        result.setLezenBrp(new TestStap(TestStatus.NOK, "Er zijn inhoudelijke verschillen (lezen)", htmlBrp, htmlVerschillen));
                    }
                } catch (final Exception e) {
                    final Foutmelding fout = new Foutmelding("Fout tijdens vergelijken verwachting (lezen).", e);
                    final String htmlFout = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_LEZEN, STR_EXCEPTION);

                    result.setLezenBrp(
                        new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden (vergelijken verwachting conversie naar brp)", htmlFout, null));

                }
            } catch (final Exception e) {
                final Foutmelding fout = new Foutmelding("Fout tijdens lezen BRP.", e);
                final String htmlFout = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_LEZEN);

                result.setLezenBrp(new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden (lezen)", htmlFout, null));
            }
        }
    }

    private void readSetup() {
        final File testcaseSetupFile = new File(inputFile.getParentFile(), inputFile.getName() + ".properties");
        final File themaSetupFile = new File(inputFile.getParentFile(), TESTCASE_SETUP);
        final File setSetupFile = new File(inputFile.getParentFile().getParentFile(), TESTCASE_SETUP);

        // Set specifiek
        if (setSetupFile.exists()) {
            readSetup(setSetupFile);
        }

        // Thema specifiek
        if (themaSetupFile.exists()) {
            readSetup(themaSetupFile);
        }

        // Testcase specifiek
        if (testcaseSetupFile.exists()) {
            readSetup(testcaseSetupFile);
        }
    }

    private void readSetup(final File file) {
        final Properties properties = new Properties();
        try (InputStream fis = new FileInputStream(file)) {
            properties.load(fis);

            skipPartij = getSetting(properties.getProperty(SKIP_PARTIJ), skipPartij);

        } catch (final IOException e) {
            LOG.info("Probleem met het lezen van de testcasus setup: " + file.getPath());
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
