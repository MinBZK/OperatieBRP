/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.test.brpnaarlo3;

import java.io.File;

import javax.inject.Inject;

import nl.moderniseringgba.migratie.conversie.model.Persoonslijst;
import nl.moderniseringgba.migratie.conversie.model.brp.BrpPersoonslijst;
import nl.moderniseringgba.migratie.conversie.model.lo3.Lo3Persoonslijst;
import nl.moderniseringgba.migratie.conversie.proces.brpnaarlo3.Lo3StapelHelper;
import nl.moderniseringgba.migratie.conversie.proces.impl.ConversieHook;
import nl.moderniseringgba.migratie.conversie.proces.impl.ConversieServiceImpl;
import nl.moderniseringgba.migratie.conversie.proces.impl.ConversieStap;
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
 * Conversie test casus.
 */
public final class ConversieTestCasus extends TestCasus {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final int MILLIS_IN_SECOND = 1000;

    private static final String STAP_LEZEN = "lezen";
    private static final String STAP_BRP = "brp";

    @Inject
    private BrpDalService brpDalService;

    @Inject
    private ConversieServiceImpl conversieService;

    private final Long aNummer;

    /**
     * Constructor.
     * 
     * @param thema
     *            thema
     * @param naam
     *            naam
     * @param outputFolder
     *            outputfolder
     * @param expectedFolder
     *            expected folder
     * @param aNummer
     *            anummer
     */
    protected ConversieTestCasus(
            final String thema,
            final String naam,
            final File outputFolder,
            final File expectedFolder,
            final Long aNummer) {
        super(thema, naam, outputFolder, expectedFolder);
        this.aNummer = aNummer;
    }

    @Override
    public TestResultaat run() {
        final long startInMillis = System.currentTimeMillis();

        LoggingContext.registreerActueelAdministratienummer(aNummer);

        final ConversieTestResultaat result = new ConversieTestResultaat(getThema(), getNaam());
        try {
            // Test lezen uit database
            final BrpPersoonslijst brpGelezen = testLezenBrp(aNummer, result);

            if (brpGelezen != null) {
                // Test conversie brp naar lo3
                testBrpNaarLo3(brpGelezen, result);
            }
        } finally {
            LoggingContext.reset();
        }

        final long endInMillis = System.currentTimeMillis();
        final float duration = (endInMillis - startInMillis) / MILLIS_IN_SECOND;

        LOG.info("Testcase {} took {} seconds", getNaam(), duration);

        return result;
    }

    /**
     * Test lezen uit brp.
     * 
     * @param input
     *            anummer
     * @param result
     *            resultaat
     * @return brp
     */
    protected BrpPersoonslijst testLezenBrp(final Long input, final ConversieTestResultaat result) {

        LOG.info("Test lezen uit BRP ...");
        try {
            // Opslaan in database
            final BrpPersoonslijst brp = brpDalService.bevraagPersoonslijst(input);
            final String filename = debugOutputXmlEnHtml(brp, STAP_LEZEN);

            result.setLezen(new TestStap(TestStatus.OK, null, filename, null));

            return brp;
            // CHECKSTYLE:OFF
        } catch (final Exception e) {
            // CHECKSTYLE:ON
            final Foutmelding fout = new Foutmelding("Fout tijdens lezen BRP.", e);
            final String filename = debugOutputXmlEnHtml(fout, STAP_LEZEN);

            result.setLezen(new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden tijdens lezen BRP.",
                    filename, null));

            return null;
        }
    }

    /**
     * Test conversie brp naar lo3.
     * 
     * @param input
     *            brp
     * @param resultaat
     *            resultaat
     */
    // CHECKSTYLE:OFF - Statement count
    protected final void testBrpNaarLo3(
    // CHECKSTYLE:ON
            final BrpPersoonslijst input,
            final ConversieTestResultaat resultaat) {
        LOG.info("Test converteer BRP naar LO3 ...");
        try {
            // Conversie terug
            final Lo3Persoonslijst lo3 =
                    conversieService.converteerBrpPersoonslijst(input, new TestOutputConversieHook());

            // Output
            final String filename = debugOutputXmlEnHtml(lo3, STAP_BRP);
            debugOutputLg01(lo3, STAP_BRP);

            resultaat.setConversie(new TestStap(TestStatus.OK, null, filename, null));

            // Controleer verschillen
            final Lo3Persoonslijst expectedTerug = leesVerwachteLo3Persoonslijst(STAP_BRP);

            final StringBuilder verschillenLog = new StringBuilder();
            if (!Lo3StapelHelper.vergelijkPersoonslijst(verschillenLog, expectedTerug, lo3, false)) {

                final Foutmelding fout =
                        new Foutmelding("Vergelijking", "Inhoud is ongelijk", verschillenLog.toString());

                final String htmlFilename = debugOutputXmlEnHtml(fout, STAP_BRP, "-verschillen");

                resultaat.setConversie(new TestStap(TestStatus.NOK, "Er zijn inhoudelijke verschillen", filename,
                        htmlFilename));
            }

            // CHECKSTYLE:OFF
        } catch (final Exception e) {
            // CHECKSTYLE:ON
            final Foutmelding fout = new Foutmelding("Fout tijdens converteren van BRP naar LO3.", e);
            final String filename = debugOutputXmlEnHtml(fout, STAP_BRP);

            resultaat.setConversie(new TestStap(TestStatus.EXCEPTIE,
                    "Er is een exceptie opgetreden tijdens converteren van BRP naar LO3", filename, null));
        }
    }

    /**
     * Debug output hook.
     */
    private final class TestOutputConversieHook implements ConversieHook {
        @Override
        public void stap(final ConversieStap stap, final Persoonslijst persoonslijst) {
            debugOutputXmlEnHtml(persoonslijst, "conversie" + stap.toString());
        }

    }

}
