/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.brpnaarlo3;

import java.io.File;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.logging.LoggingContext;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.exceptions.PreconditieException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.conversie.regels.proces.ConversieHook;
import nl.bzk.migratiebrp.conversie.regels.proces.impl.ConverteerBrpNaarLo3ServiceImpl;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpPersoonslijstService;
import nl.bzk.migratiebrp.test.common.resultaat.Foutmelding;
import nl.bzk.migratiebrp.test.common.resultaat.TestResultaat;
import nl.bzk.migratiebrp.test.common.resultaat.TestStap;
import nl.bzk.migratiebrp.test.common.resultaat.TestStatus;
import nl.bzk.migratiebrp.test.common.vergelijk.VergelijkXml;
import nl.bzk.migratiebrp.test.dal.TestCasus;
import nl.bzk.migratiebrp.test.dal.TestCasusOutputStap;

/**
 * Conversie test casus.
 */
public final class BrpNaarLo3ConversieTestCasus extends TestCasus {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final int MILLIS_IN_SECOND = 1000;

    private static final ConversieHook NULL_HOOK = (stap, object) -> {
        // Niets
    };

    @Inject
    private BrpPersoonslijstService persoonslijstService;

    @Inject
    private ConverteerBrpNaarLo3ServiceImpl converteerBrpNaarLo3Service;

    private final String aNummer;
    private final Exception exception;

    /**
     * Constructor.
     * @param thema thema
     * @param naam naam
     * @param outputFolder outputfolder
     * @param expectedFolder expected folder
     * @param aNummer anummer
     */
    protected BrpNaarLo3ConversieTestCasus(final String thema, final String naam, final File outputFolder, final File expectedFolder, final String aNummer) {
        super(thema, naam, outputFolder, expectedFolder);
        this.aNummer = aNummer;
        exception = null;
    }

    /**
     * Constructor.
     * @param thema thema
     * @param naam naam
     * @param outputFolder outputfolder
     * @param expectedFolder expected folder
     * @param exception exception
     */
    protected BrpNaarLo3ConversieTestCasus(
            final String thema,
            final String naam,
            final File outputFolder,
            final File expectedFolder,
            final Exception exception) {
        super(thema, naam, outputFolder, expectedFolder);
        aNummer = null;
        this.exception = exception;
    }

    @Override
    public TestResultaat run() {
        final long startInMillis = System.currentTimeMillis();
        final BrpNaarLo3ConversieTestResultaat result = new BrpNaarLo3ConversieTestResultaat(getThema(), getNaam());

        if (exception != null) {
            final Foutmelding foutmelding = new Foutmelding(TestCasusOutputStap.STAP_LEZEN.getNaam(), exception);
            result.setLezen(
                    new TestStap(
                            TestStatus.EXCEPTIE,
                            "Er is een exceptie opgetreden (inlezen PL)",
                            debugOutputXmlEnHtml(foutmelding, TestCasusOutputStap.STAP_LEZEN),
                            null));
        } else {
            try {
                LoggingContext.registreerActueelAdministratienummer(aNummer);
                // Test lezen uit database
                final BrpPersoonslijst brpGelezen = testLezenBrp(aNummer, result);

                if (brpGelezen != null) {
                    // Test conversie brp naar lo3
                    testBrpNaarLo3(brpGelezen, result);
                }
            } finally {
                LoggingContext.reset();
            }
        }
        final long endInMillis = System.currentTimeMillis();
        final float duration = (endInMillis - startInMillis) / (float) MILLIS_IN_SECOND;

        LOG.info("Testcase {} took {} seconds", getNaam(), duration);

        return result;
    }

    /**
     * Test lezen uit brp.
     *
     * De in de .xls aanwezige persoonslijst is ingelezen in Entity-formaat en opgeslagen in de BRP DB. Deze wordt hier
     * nu weer ingelezen in Conversiemodel-formaat. Optioneel kan op dit punt een expected worden opgegeven.
     * @param input anummer
     * @param result resultaat
     * @return brp
     */
    private BrpPersoonslijst testLezenBrp(final String input, final BrpNaarLo3ConversieTestResultaat result) {
        LOG.info("Test lezen uit BRP ...");
        try {
            // Opslaan in database
            final BrpPersoonslijst brp = persoonslijstService.bevraagPersoonslijst(input);

            final String filename = debugOutputXmlEnHtml(brp, TestCasusOutputStap.STAP_LEZEN);

            final BrpPersoonslijst verwacht = leesVerwachteBrpPersoonslijst(TestCasusOutputStap.STAP_LEZEN);

            if (verwacht == null) {
                result.setLezen(new TestStap(TestStatus.GEEN_VERWACHTING, null, filename, null));
            } else {
                final StringBuilder verschillenLog = new StringBuilder();
                if (VergelijkXml.vergelijkXmlNegeerActieId(verwacht, brp, true, verschillenLog)) {
                    result.setLezen(new TestStap(TestStatus.OK, null, filename, null));
                } else {
                    final Foutmelding fout = new Foutmelding("Vergelijking", "Inhoud is ongelijk", verschillenLog.toString());

                    final String htmlFilename = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_LEZEN, SUFFIX_VERSCHILLEN);

                    result.setConversie(new TestStap(TestStatus.NOK, "Er zijn inhoudelijke verschillen", filename, htmlFilename));
                }

            }

            return brp;
        } catch (final Exception e) {
            final Foutmelding fout = new Foutmelding("Fout tijdens lezen BRP.", e);
            final String filename = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_LEZEN);

            result.setLezen(new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden tijdens lezen BRP.", filename, null));

            return null;
        }
    }

    /**
     * Test conversie brp naar lo3.
     * @param input brp
     * @param resultaat resultaat
     */
    private void testBrpNaarLo3(final BrpPersoonslijst input, final BrpNaarLo3ConversieTestResultaat resultaat) {
        LOG.info("Test converteer BRP naar LO3 ...");
        try {
            // Conversie terug
            final Lo3Persoonslijst lo3 = converteerBrpNaarLo3Service.converteerBrpPersoonslijst(input, NULL_HOOK);
            final String htmlBrp = debugOutputXmlEnHtml(lo3, TestCasusOutputStap.STAP_LO3);

            // Controleer verwachten
            final Lo3Persoonslijst expectedLo3 = leesVerwachteLo3Persoonslijst(TestCasusOutputStap.STAP_LO3, null);
            if (expectedLo3 == null) {
                resultaat.setConversie(new TestStap(TestStatus.GEEN_VERWACHTING, null, htmlBrp, null));
            } else {
                final StringBuilder verschillenLog = new StringBuilder();
                if (Lo3StapelHelper.vergelijkPersoonslijst(verschillenLog, expectedLo3, lo3)) {
                    resultaat.setConversie(new TestStap(TestStatus.OK, null, htmlBrp, null));
                } else {
                    final Foutmelding fout = new Foutmelding("Vergelijking", "Inhoud is ongelijk", verschillenLog.toString());

                    final String htmlFilename = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_LO3, SUFFIX_VERSCHILLEN);

                    resultaat.setConversie(new TestStap(TestStatus.NOK, "Er zijn inhoudelijke verschillen", htmlBrp, htmlFilename));
                }
            }
        } catch (final PreconditieException pe) {
            final PreconditieException expectedException = leesVerwachtePreconditieException(TestCasusOutputStap.STAP_LO3);
            final String htmlBrp = debugOutputXmlEnHtml(pe, TestCasusOutputStap.STAP_LO3);
            if (expectedException == null) {
                resultaat.setConversie(new TestStap(TestStatus.NOK, null, htmlBrp, null));
            } else {
                if (expectedException.equals(pe)) {
                    resultaat.setConversie(new TestStap(TestStatus.OK, null, htmlBrp, null));
                } else {
                    final StringBuilder verschil = new StringBuilder();
                    verschil.append("Preconditie verwacht: ").append(expectedException.getPreconditieNaam()).append("\n");
                    verschil.append("Preconditie gekregen: ").append(pe.getPreconditieNaam());
                    final Foutmelding fout = new Foutmelding("Vergelijking precondities", "Exceptions zijn niet gelijk", verschil.toString());
                    final String htmlFilename = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_LO3, SUFFIX_VERSCHILLEN);
                    resultaat.setConversie(new TestStap(TestStatus.NOK, "Exception niet niet de verwachte", htmlBrp, htmlFilename));
                }
            }
        } catch (final Exception e) {
            final Foutmelding fout = new Foutmelding("Fout tijdens converteren van BRP naar LO3.", e);
            final String filename = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_LO3);

            resultaat.setConversie(
                    new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden tijdens converteren van BRP naar LO3", filename, null));
        }
    }
}
