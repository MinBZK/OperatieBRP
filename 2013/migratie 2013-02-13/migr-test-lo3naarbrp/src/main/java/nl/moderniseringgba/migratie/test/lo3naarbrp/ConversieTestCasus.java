/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.lo3naarbrp;

import java.io.File;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.conversie.model.Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpBepalenGegevensSet;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpBepalenMaterieleHistorie;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.BrpStapelHelper;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper;
import nl.moderniseringgba.migratie.conversie.proces.impl.ConversieHook;
import nl.moderniseringgba.migratie.conversie.proces.impl.ConversieServiceImpl;
import nl.moderniseringgba.migratie.conversie.proces.impl.ConversieStap;
import nl.moderniseringgba.migratie.conversie.proces.logging.Logging;
import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;
import nl.moderniseringgba.migratie.logging.LoggingContext;
import nl.moderniseringgba.migratie.synchronisatie.service.BrpDalService;
import nl.moderniseringgba.migratie.test.TestCasus;
import nl.moderniseringgba.migratie.test.resultaat.Foutmelding;
import nl.moderniseringgba.migratie.test.resultaat.TestResultaat;
import nl.moderniseringgba.migratie.test.resultaat.TestStap;
import nl.moderniseringgba.migratie.test.resultaat.TestStatus;

/**
 * Test casus: conversie lo3 naar brp.
 */
public final class ConversieTestCasus extends TestCasus {
    private static final String SUFFIX_VERSCHILLEN = "-verschillen";

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final int MILLIS_IN_SECOND = 1000;

    private static final String STAP_LO3 = "lo3";
    private static final String STAP_BRP = "brp";
    private static final String STAP_ROND = "rond";
    private static final String STAP_OPSLAAN = "opslaan";
    private static final String STAP_LEZEN = "lezen";
    private static final String STAP_TERUG = "terug";

    @Inject
    private BrpDalService brpDalService;
    @Inject
    private ConversieServiceImpl conversieService;

    @Inject
    private BrpBepalenGegevensSet brpBepalenGegevensSet;
    @Inject
    private BrpBepalenMaterieleHistorie brpBepalenMaterieleHistorie;

    private final Lo3Persoonslijst lo3;

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
     * @param lo3
     *            lo3 persoonslijst
     */
    protected ConversieTestCasus(
            final String thema,
            final String naam,
            final File outputFolder,
            final File expectedFolder,
            final Lo3Persoonslijst lo3) {
        super(thema, naam, outputFolder, expectedFolder);
        this.lo3 = lo3;
    }

    @Override
    public TestResultaat run() {
        Logging.initContext();
        final long startInMillis = System.currentTimeMillis();

        final Long administratienummer = lo3.getActueelAdministratienummer();
        LoggingContext.registreerActueelAdministratienummer(administratienummer);

        final ConversieTestResultaat result = new ConversieTestResultaat(getThema(), getNaam());
        result.setBron(debugOutputXmlEnHtml(lo3, STAP_LO3));
        debugOutputLg01(lo3, STAP_LO3);

        try {
            // Valideer conversie van lo3 naar brp
            final BrpPersoonslijst brp = testLo3NaarBrp(lo3, result);

            // Test rondverteer
            testRondverteer(brp, lo3, result);

            // Test opslaan
            final Long anummer = testOpslaanBrp(brp, result);

            // Test lezen uit database
            final BrpPersoonslijst brpGelezen = testLezenBrp(anummer, result, brp);

            // Test conversie brp naar lo3
            testBrpNaarLo3(brpGelezen, result, lo3);

        } finally {
            LoggingContext.reset();
            Logging.destroyContext();
        }

        final long endInMillis = System.currentTimeMillis();
        final float duration = (endInMillis - startInMillis) / MILLIS_IN_SECOND;

        LOG.info("Testcase {} took {} seconds", getNaam(), duration);

        return result;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Test conversie lo3 naar brp.
     * 
     * @param input
     *            lo3
     * @param result
     *            result
     * @return bp
     */
    protected BrpPersoonslijst testLo3NaarBrp(final Lo3Persoonslijst input, final ConversieTestResultaat result) {
        LOG.info("Test converteer LO3 naar BRP ...");
        try {
            final BrpPersoonslijst brp =
                    conversieService.converteerLo3Persoonslijst(input, new TestOutputConversieHook("conversie"));
            final String htmlBrp = debugOutputXmlEnHtml(brp, STAP_BRP);

            // Controleer verwachten
            final BrpPersoonslijst expectedBrp = leesVerwachteBrpPersoonslijst(STAP_BRP);
            if (expectedBrp == null) {
                result.setLo3NaarBrp(new TestStap(TestStatus.GEEN_VERWACHTING, null, htmlBrp, null));
            } else {
                final StringBuilder verschillenLog = new StringBuilder();
                if (BrpStapelHelper.vergelijkPersoonslijsten(verschillenLog, expectedBrp, brp, true)) {
                    result.setLo3NaarBrp(new TestStap(TestStatus.OK, null, htmlBrp, null));
                } else {
                    final Foutmelding fout =
                            new Foutmelding("Vergelijking (brp)", "Inhoud is ongelijk (brp)",
                                    verschillenLog.toString());
                    final String htmlVerschillen = debugOutputXmlEnHtml(fout, STAP_BRP, SUFFIX_VERSCHILLEN);

                    result.setLo3NaarBrp(new TestStap(TestStatus.NOK, "Er zijn inhoudelijke verschillen (brp)",
                            htmlBrp, htmlVerschillen));
                }
            }

            final Logging logging = Logging.getLogging();
            final String loggingHtml = debugOutputXmlEnHtml(logging, "logging");
            result.setConversieLog(new TestStap(TestStatus.OK, null, loggingHtml, null));
            return brp;
            // CHECKSTYLE:OFF
        } catch (final Exception e) {
            // CHECKSTYLE:ON
            final Foutmelding fout = new Foutmelding("Fout tijdens converteren LO3 naar BRP.", e);
            final String htmlFout = debugOutputXmlEnHtml(fout, STAP_BRP);
            result.setLo3NaarBrp(new TestStap(TestStatus.EXCEPTIE,
                    "Er is een exceptie opgetreden (conversie naar brp)", htmlFout, null));

            return null;
        }
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Test rondverteer.
     * 
     * @param orgineel
     *            orgineel lo3
     * @param input
     *            brp
     * @param result
     *            resultaat
     */
    protected void testRondverteer(
            final BrpPersoonslijst input,
            final Lo3Persoonslijst orgineel,
            final ConversieTestResultaat result) {
        if (input == null) {
            return;
        }

        LOG.info("Test rondverteer ...");
        try {
            final Lo3Persoonslijst lo3 =
                    conversieService.converteerBrpPersoonslijst(input, new TestOutputConversieHook(STAP_ROND));
            final String htmlLo3 = debugOutputXmlEnHtml(lo3, STAP_ROND);
            debugOutputLg01(lo3, STAP_ROND);

            // Controleer verwachting
            final Lo3Persoonslijst expectedLo3 = leesVerwachteLo3Persoonslijst(STAP_ROND);

            final StringBuilder verschillenLog = new StringBuilder();
            if (Lo3StapelHelper.vergelijkPersoonslijst(verschillenLog, expectedLo3 == null ? orgineel : expectedLo3,
                    lo3, true)) {
                result.setRondverteer(new TestStap(TestStatus.OK, null, htmlLo3, null));
            } else {
                final Foutmelding fout =
                        new Foutmelding("Vergelijking (rond)", "Inhoud is ongelijk (rond)", verschillenLog.toString());
                final String htmlVerschillen = debugOutputXmlEnHtml(fout, STAP_ROND, SUFFIX_VERSCHILLEN);

                LOG.debug("Rondverteren niet juist (vergelijking met origeel).");
                result.setRondverteer(new TestStap(TestStatus.NOK, "Er zijn inhoudelijke verschillen (rond)",
                        htmlLo3, htmlVerschillen));
            }

            // CHECKSTYLE:OFF
        } catch (final Exception e) {
            // CHECKSTYLE:ON
            final Foutmelding fout = new Foutmelding("Fout tijdens rondverteren van BRP naar LO3.", e);
            final String htmlFout = debugOutputXmlEnHtml(fout, STAP_ROND);

            result.setRondverteer(new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden (rond)", htmlFout,
                    null));
        }
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Test opslaan in BRP.
     * 
     * @param input
     *            brp
     * @param result
     *            resultaat
     * @return anummer
     */
    protected Long testOpslaanBrp(final BrpPersoonslijst input, final ConversieTestResultaat result) {
        if (input != null) {
            LOG.info("Test opslaan in BRP ...");
            try {
                // Opslaan in database
                brpDalService.persisteerPersoonslijst(input);

                result.setOpslaanBrp(new TestStap(TestStatus.OK));

                // Juiste *iets* teruggeven om test door te laten lopen naar 'lezen'.
                return input.getActueelAdministratienummer();
                // CHECKSTYLE:OFF
            } catch (final Exception e) {
                // CHECKSTYLE:ON
                final Foutmelding fout = new Foutmelding("Fout tijdens opslaan BRP.", e);
                final String htmlFout = debugOutputXmlEnHtml(fout, STAP_OPSLAAN);

                result.setOpslaanBrp(new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden (opslaan)",
                        htmlFout, null));
            }
        }
        return null;

    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Test lezen uit brp.
     * 
     * @param input
     *            anummer
     * @param result
     *            resultaat
     * @param opgeslagenLijst
     *            (optioneel) opgeslagen brp lijst om te vergelijken
     * @return brp
     */
    protected BrpPersoonslijst testLezenBrp(
            final Long input,
            final ConversieTestResultaat result,
            final BrpPersoonslijst opgeslagenLijst) {
        if (input != null) {
            LOG.info("Test lezen uit BRP ...");
            try {
                // Opslaan in database
                final BrpPersoonslijst brp = brpDalService.bevraagPersoonslijst(input);
                final String htmlBrp = debugOutputXmlEnHtml(brp, STAP_LEZEN);

                result.setLezenBrp(new TestStap(TestStatus.OK, null, htmlBrp, null));

                // Deel van de terugconversie uitvoeren voor vergelijking
                // (aanvullen gegevensset gegevens en materiele historie)
                BrpPersoonslijst vergelijkBrp = brpBepalenGegevensSet.converteer(brp);
                vergelijkBrp = brpBepalenMaterieleHistorie.converteer(vergelijkBrp);

                final StringBuilder verschillenLog = new StringBuilder();
                if (!BrpStapelHelper.vergelijkPersoonslijsten(verschillenLog, opgeslagenLijst, vergelijkBrp, false)) {
                    final Foutmelding fout =
                            new Foutmelding("Vergelijking (lezen)", "Inhoud is ongelijk (lezen)",
                                    verschillenLog.toString());
                    final String htmlVerschillen = debugOutputXmlEnHtml(fout, STAP_LEZEN);

                    result.setLezenBrp(new TestStap(TestStatus.NOK, "Er zijn inhoudelijke verschillen (lezen)",
                            htmlBrp, htmlVerschillen));
                }

                return brp;
                // CHECKSTYLE:OFF
            } catch (final Exception e) {
                // CHECKSTYLE:ON
                final Foutmelding fout = new Foutmelding("Fout tijdens lezen BRP.", e);
                final String htmlFout = debugOutputXmlEnHtml(fout, STAP_LEZEN);

                result.setLezenBrp(new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden (lezen)",
                        htmlFout, null));

            }
        }

        return null;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Test conversie brp naar lo3.
     * 
     * @param input
     *            brp
     * @param result
     *            resultaat
     * @param orgineel
     *            (optioneel) orginele lo3 om mee te vergelijken
     */
    // CHECKSTYLE:OFF - Statement count
    protected final void testBrpNaarLo3(
    // CHECKSTYLE:ON
            final BrpPersoonslijst input,
            final ConversieTestResultaat result,
            final Lo3Persoonslijst orgineel) {
        if (input == null) {
            return;
        }

        LOG.info("Test converteer BRP naar LO3 ...");
        try {
            final Lo3Persoonslijst lo3 =
                    conversieService.converteerBrpPersoonslijst(input, new TestOutputConversieHook(STAP_TERUG));
            final String htmlLo3 = debugOutputXmlEnHtml(lo3, STAP_TERUG);
            debugOutputLg01(lo3, STAP_TERUG);

            result.setBrpNaarLo3(new TestStap(TestStatus.OK, null, htmlLo3, null));

            final Lo3Persoonslijst expectedTerug = leesVerwachteLo3Persoonslijst(STAP_TERUG);

            final StringBuilder verschillenLog = new StringBuilder();
            if (!Lo3StapelHelper.vergelijkPersoonslijst(verschillenLog, expectedTerug == null ? orgineel
                    : expectedTerug, lo3, false)) {

                final Foutmelding fout =
                        new Foutmelding("Vergelijking (terug)", "Inhoud is ongelijk (terug)",
                                verschillenLog.toString());
                final String htmlVerschillen = debugOutputXmlEnHtml(fout, STAP_TERUG, SUFFIX_VERSCHILLEN);

                result.setBrpNaarLo3(new TestStap(TestStatus.NOK, "Er zijn inhoudelijke verschillen (terug)",
                        htmlLo3, htmlVerschillen));
            }

            // CHECKSTYLE:OFF
        } catch (final Exception e) {
            // CHECKSTYLE:ON
            final Foutmelding fout = new Foutmelding("Fout tijdens converteren van BRP naar LO3.", e);
            final String htmlFout = debugOutputXmlEnHtml(fout, STAP_TERUG);

            result.setBrpNaarLo3(new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden (terug)", htmlFout,
                    null));
        }
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Debug output hook.
     */
    public final class TestOutputConversieHook implements ConversieHook {
        private final String prefix;

        /**
         * Constructor.
         * 
         * @param prefix
         *            prefix
         */
        TestOutputConversieHook(final String prefix) {
            this.prefix = prefix;
        }

        @Override
        public void stap(final ConversieStap stap, final Persoonslijst persoonslijst) {
            debugOutputXmlEnHtml(persoonslijst, prefix + "-" + stap.toString());
        }
    }

}
