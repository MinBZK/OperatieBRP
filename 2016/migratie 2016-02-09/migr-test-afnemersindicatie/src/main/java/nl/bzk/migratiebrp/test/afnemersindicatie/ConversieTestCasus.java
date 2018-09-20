/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.afnemersindicatie;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import nl.bzk.migratiebrp.bericht.model.lo3.parser.Lo3PersoonslijstParser;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.impl.AfnemersindicatiesBericht;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAfnemersindicaties;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAutorisatie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Afnemersindicatie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Autorisatie;
import nl.bzk.migratiebrp.conversie.regels.proces.ConversieHook;
import nl.bzk.migratiebrp.conversie.regels.proces.ConversieStap;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerLo3NaarBrpService;
import nl.bzk.migratiebrp.conversie.regels.proces.impl.ConverteerLo3NaarBrpServiceImpl;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.PreconditiesService;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Bericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3BerichtenBron;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import nl.bzk.migratiebrp.test.common.autorisatie.AutorisatieReader;
import nl.bzk.migratiebrp.test.common.autorisatie.CsvAutorisatieReader;
import nl.bzk.migratiebrp.test.common.resultaat.Foutmelding;
import nl.bzk.migratiebrp.test.common.resultaat.TestResultaat;
import nl.bzk.migratiebrp.test.common.resultaat.TestStap;
import nl.bzk.migratiebrp.test.common.resultaat.TestStatus;
import nl.bzk.migratiebrp.test.common.util.BaseFilter;
import nl.bzk.migratiebrp.test.common.util.FilterType;
import nl.bzk.migratiebrp.test.dal.TestCasus;
import nl.bzk.migratiebrp.test.dal.TestCasusOutputStap;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.common.logging.LoggingContext;
import nl.bzk.migratiebrp.util.excel.ExcelAdapter;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterImpl;
import nl.bzk.migratiebrp.util.excel.ExcelData;
import org.apache.commons.io.IOUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Test casus: conversie lo3 naar brp.
 */
@SuppressWarnings("checkstyle:illegalcatch")
public final class ConversieTestCasus extends TestCasus {

    private static final ExcelAdapter EXCEL_ADAPTER = new ExcelAdapterImpl();
    private static final Lo3PersoonslijstParser LO3_PARSER = new Lo3PersoonslijstParser();
    private static final AutorisatieReader AUTORISATIE_READER = new CsvAutorisatieReader();

    private static final String SUFFIX_VERSCHILLEN = "-verschillen";

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final int MILLIS_IN_SECOND = 1000;

    private static final TestCasusOutputStap STAP_INITIEREN = TestCasusOutputStap.STAP_LO3;
    private static final TestCasusOutputStap STAP_LO3_NAAR_BRP = TestCasusOutputStap.STAP_BRP;
    private static final TestCasusOutputStap STAP_OPSLAAN = TestCasusOutputStap.STAP_OPSLAAN;
    private static final TestCasusOutputStap STAP_LEZEN = TestCasusOutputStap.STAP_LEZEN;
    private static final String FOUT_TIJDENS_INITIEREN_TESTCASUS = "Fout tijdens initieren testcasus.";
    private static final String EXCEPTIE = "exceptie";
    private static final String ER_IS_EEN_EXCEPTIE_OPGETREDEN_CONVERSIE_NAAR_BRP = "Er is een exceptie opgetreden (conversie naar brp)";

    @Inject
    private PreconditiesService preconditieService;
    @Inject
    private ConverteerLo3NaarBrpService converteerLo3NaarBrpService;
    @Inject
    private BrpDalService brpDalService;

    @Inject
    @Named("syncDalDataSource")
    private DataSource dataSource;

    @PersistenceContext
    private EntityManager em;

    private final File lo3AfnemersindicatieFile;

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
     * @param lo3AfnemersindicatieFile
     *            input (file)
     */
    protected ConversieTestCasus(
        final String thema,
        final String naam,
        final File outputFolder,
        final File expectedFolder,
        final File lo3AfnemersindicatieFile)
    {
        super(thema, naam, outputFolder, expectedFolder);
        this.lo3AfnemersindicatieFile = lo3AfnemersindicatieFile;
    }

    @Override
    public TestResultaat run() {
        final long startInMillis = System.currentTimeMillis();
        final ConversieTestResultaat result = new ConversieTestResultaat(getThema(), getNaam());

        try {
            // Stap 1: Brondata klaarzetten
            Lo3Afnemersindicatie lo3 = initieren(result);

            Logging.initContext();
            final Logging logging = Logging.getLogging();

            // Stap 2a: Precondities
            lo3 = testPrecondities(lo3, result);

            // Stap 2b: Converteren
            final BrpAfnemersindicaties brp = testLo3NaarBrp(lo3, result);

            // Stap 3: Opslaan
            final Long aNummer = testOpslaanBrp(brp, result);

            // Stap 3a: Controleer logging
            controleerLogging(logging, result);

            // Stap 4: Lezen
            testLezenBrp(aNummer, brp, result);
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

    private Lo3Afnemersindicatie initieren(final ConversieTestResultaat result) {
        initierenBrpDatabase(dataSource);

        // clear caches
        final Session s = (Session) em.getDelegate();
        final SessionFactory sf = s.getSessionFactory();
        sf.getCache().evictCollectionRegions();
        sf.getCache().evictDefaultQueryRegion();
        sf.getCache().evictEntityRegions();
        sf.getCache().evictNaturalIdRegions();
        sf.getCache().evictQueryRegions();

        // lo3AfnemersindicatieFile.parent/personen directory uitlezen, converteren en opslaan
        Logging.initContext();
        try {
            vulPersonen(new File(lo3AfnemersindicatieFile.getParentFile(), "personen"));
        } catch (final Exception e) {
            final Foutmelding fout = new Foutmelding(FOUT_TIJDENS_INITIEREN_TESTCASUS, e);
            final String htmlFout = debugOutputXmlEnHtml(fout, STAP_INITIEREN);
            result.setInitieren(new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden (initieren: personen)", htmlFout, null));

            return null;
        } finally {
            debugOutputXmlEnHtml(Logging.getLogging(), TestCasusOutputStap.STAP_INITIEREN, "logging-personen");
            Logging.destroyContext();
        }

        // lo3AfnemersindicatieFile.parent/autorisatie directory uitlezen, converteren en opslaan
        Logging.initContext();
        try {
            vulAutorisatie(new File(lo3AfnemersindicatieFile.getParentFile(), "autorisatie"));
        } catch (final Exception e) {
            final Foutmelding fout = new Foutmelding(FOUT_TIJDENS_INITIEREN_TESTCASUS, e);
            final String htmlFout = debugOutputXmlEnHtml(fout, STAP_INITIEREN);
            result.setInitieren(new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden (initieren: autorisatie)", htmlFout, null));

            return null;
        } finally {
            debugOutputXmlEnHtml(Logging.getLogging(), TestCasusOutputStap.STAP_INITIEREN, "logging-autorisatie");
            Logging.destroyContext();
        }

        // lo3AfnemersindicatieFile uitlezen
        try (InputStream in = new FileInputStream(lo3AfnemersindicatieFile)) {
            final String xml = IOUtils.toString(in);
            final AfnemersindicatiesBericht inputBericht = (AfnemersindicatiesBericht) SyncBerichtFactory.SINGLETON.getBericht(xml);

            final Lo3Afnemersindicatie lo3 = inputBericht.getAfnemersindicaties();
            final String htmlLo3 = debugOutputXmlEnHtml(lo3, STAP_INITIEREN);
            result.setInitieren(new TestStap(TestStatus.OK, null, htmlLo3, null));
            return lo3;
        } catch (final Exception e) {
            final Foutmelding fout = new Foutmelding(FOUT_TIJDENS_INITIEREN_TESTCASUS, e);
            final String htmlFout = debugOutputXmlEnHtml(fout, STAP_INITIEREN);

            // Lees verwachte expection
            final Foutmelding verwachteFout = leesVerwachteFoutmelding(TestCasusOutputStap.STAP_INITIEREN);
            if (verwachteFout != null) {
                if (verwachteFout.equals(fout)) {
                    result.setInitieren(new TestStap(TestStatus.OK, "Er is een verwachte exceptie opgetreden (initieren: lees input)", htmlFout, null));
                } else {
                    result.setInitieren(
                        new TestStap(TestStatus.NOK, "Er is een anders dan verwachte exceptie opgetreden (initieren: lees input)", htmlFout, null));
                }
            } else {
                result.setInitieren(new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden (initieren: lees input)", htmlFout, null));
            }

            return null;
        }

    }

    private void vulPersonen(final File directory) throws Exception {
        for (final File persoonFile : directory.listFiles(new BaseFilter(FilterType.FILE))) {
            final List<ExcelData> excelDatas = EXCEL_ADAPTER.leesExcelBestand(new FileInputStream(persoonFile));
            for (final ExcelData excelData : excelDatas) {
                Lo3Persoonslijst lo3Pl = LO3_PARSER.parse(excelData.getCategorieLijst());
                lo3Pl = preconditieService.verwerk(lo3Pl);
                final BrpPersoonslijst brpPl = converteerLo3NaarBrpService.converteerLo3Persoonslijst(lo3Pl);
                final Lo3Bericht lo3Bericht =
                        new Lo3Bericht(
                            persoonFile.getName(),
                            Lo3BerichtenBron.INITIELE_VULLING,
                            new Timestamp(System.currentTimeMillis()),
                            "ExcelData",
                            true);
                brpDalService.persisteerPersoonslijst(brpPl, lo3Bericht);
            }
        }
    }

    private void vulAutorisatie(final File directory) throws Exception {
        for (final File autorisatieFile : directory.listFiles(new BaseFilter(FilterType.FILE))) {
            final Collection<Lo3Autorisatie> lo3Autorisaties = AUTORISATIE_READER.read(new FileInputStream(autorisatieFile));
            for (final Lo3Autorisatie lo3Autorisatie : lo3Autorisaties) {
                // Controleer precondities
                preconditieService.verwerk(lo3Autorisatie);

                // Converteer naar BRP
                final BrpAutorisatie brpAutorisatie = converteerLo3NaarBrpService.converteerLo3Autorisatie(lo3Autorisatie);

                // Opslaan in BRP
                brpDalService.persisteerAutorisatie(brpAutorisatie);
            }
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
    private Lo3Afnemersindicatie testPrecondities(final Lo3Afnemersindicatie input, final ConversieTestResultaat result) {
        if (input == null) {
            return null;
        }
        LOG.info("Test controleer precondities ...");

        try {
            // Precondities
            final Lo3Afnemersindicatie resultaat = preconditieService.verwerk(input);
            result.setPrecondities(new TestStap(TestStatus.OK));

            return resultaat;

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

            return null;
        }
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Test conversie lo3 naar brp.
     *
     * @param lo3
     *            lo3
     * @param result
     *            result
     * @return bp
     */
    private BrpAfnemersindicaties testLo3NaarBrp(final Lo3Afnemersindicatie lo3, final ConversieTestResultaat result) {
        if (lo3 == null) {
            return null;
        }

        LOG.info("Test converteer LO3 naar BRP ...");
        try {

            final BrpAfnemersindicaties brp;
            brp =
                    BrpAfnemersindicatiesSorter.sorteer(
                        ((ConverteerLo3NaarBrpServiceImpl) converteerLo3NaarBrpService).converteerLo3Afnemersindicaties(
                            lo3,
                            new LoggingConversieHook(STAP_LO3_NAAR_BRP)));
            final String htmlBrp = debugOutputXmlEnHtml(brp, STAP_LO3_NAAR_BRP);

            try {
                // Controleer verwachting
                final BrpAfnemersindicaties expectedBrp = BrpAfnemersindicatiesSorter.sorteer(leesVerwachteBrpAfnemersindicaties(STAP_LO3_NAAR_BRP));
                if (expectedBrp == null) {
                    result.setLo3NaarBrp(new TestStap(TestStatus.GEEN_VERWACHTING, null, htmlBrp, null));
                } else {
                    final StringBuilder verschillenLog = new StringBuilder();
                    if (BrpAfnemersindicatiesVergelijker.vergelijk(verschillenLog, expectedBrp, brp)) {
                        result.setLo3NaarBrp(new TestStap(TestStatus.OK, null, htmlBrp, null));
                    } else {
                        final Foutmelding fout =
                                new Foutmelding("Vergelijking (lo3 naar brp)", "Inhoud is ongelijk (lo3 naar brp)", verschillenLog.toString());
                        final String htmlVerschillen = debugOutputXmlEnHtml(fout, STAP_LO3_NAAR_BRP, SUFFIX_VERSCHILLEN);

                        result.setLo3NaarBrp(new TestStap(TestStatus.NOK, "Er zijn inhoudelijke verschillen (brp)", htmlBrp, htmlVerschillen));
                    }
                }
            } catch (final Exception e) {
                final Foutmelding fout = new Foutmelding("Fout tijdens vergelijken verwachting (conversie LO3 naar BRP).", e);
                final String htmlFout = debugOutputXmlEnHtml(fout, STAP_LO3_NAAR_BRP, EXCEPTIE);
                result.setLo3NaarBrp(new TestStap(TestStatus.EXCEPTIE, ER_IS_EEN_EXCEPTIE_OPGETREDEN_CONVERSIE_NAAR_BRP, htmlFout, null));

            }

            return brp;
        } catch (final Exception e) {
            final Foutmelding fout = new Foutmelding("Fout tijdens converteren LO3 naar BRP.", e);
            final String htmlFout = debugOutputXmlEnHtml(fout, STAP_LO3_NAAR_BRP, EXCEPTIE);
            result.setLo3NaarBrp(new TestStap(TestStatus.EXCEPTIE, ER_IS_EEN_EXCEPTIE_OPGETREDEN_CONVERSIE_NAAR_BRP, htmlFout, null));
            return null;
        }
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

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
     * @param input
     *            brp
     * @param result
     *            resultaat
     * @return anummer
     */
    private Long testOpslaanBrp(final BrpAfnemersindicaties input, final ConversieTestResultaat result) {
        if (input != null) {
            LOG.info("Test opslaan in BRP ...");
            try {
                // Opslaan in database
                brpDalService.persisteerAfnemersindicaties(input);

                result.setOpslaanBrp(new TestStap(TestStatus.OK));

                return input.getAdministratienummer();
            } catch (final Exception e) {
                final Foutmelding fout = new Foutmelding("Fout tijdens opslaan BRP.", e);
                final String htmlFout = debugOutputXmlEnHtml(fout, STAP_OPSLAAN);

                // Lees verwachte expection
                final Foutmelding verwachteFout = leesVerwachteFoutmelding(STAP_OPSLAAN);
                if (verwachteFout != null) {
                    if (verwachteFout.equals(fout)) {
                        result.setOpslaanBrp(new TestStap(TestStatus.OK, "Er is een verwachte exceptie opgetreden (opslaan)", htmlFout, null));
                    } else {
                        result.setOpslaanBrp(new TestStap(TestStatus.NOK, "Er is een anders dan verwachte exceptie opgetreden (opslaan)", htmlFout, null));
                    }
                } else {
                    result.setOpslaanBrp(new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden (opslaan)", htmlFout, null));

                }

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
     * @param aNummer
     *            anummer
     * @param originalBrp
     *            brp autorisatie om te vergelijken
     * @param result
     *            resultaat
     */

    private void testLezenBrp(final Long aNummer, final BrpAfnemersindicaties originalBrp, final ConversieTestResultaat result) {

        if (aNummer != null) {
            LOG.info("Test lezen uit BRP ...");
            try {
                // Lezen uit database
                final BrpAfnemersindicaties brp = BrpAfnemersindicatiesSorter.sorteer(brpDalService.bevraagAfnemersindicaties(aNummer));
                final String htmlBrp = debugOutputXmlEnHtml(brp, STAP_LEZEN);

                final BrpAfnemersindicaties expectedBrp = leesVerwachteBrpAfnemersindicaties(STAP_LEZEN);
                final BrpAfnemersindicaties expected = BrpAfnemersindicatiesSorter.sorteer(expectedBrp == null ? originalBrp : expectedBrp);

                final StringBuilder verschillenLog = new StringBuilder();
                if (BrpAfnemersindicatiesVergelijker.vergelijk(verschillenLog, expected, brp)) {
                    result.setLezenBrp(new TestStap(TestStatus.OK, null, htmlBrp, null));
                } else {
                    final Foutmelding fout = new Foutmelding("Vergelijking (lezen)", "Inhoud is ongelijk (lezen)", verschillenLog.toString());
                    final String htmlVerschillen = debugOutputXmlEnHtml(fout, STAP_LEZEN, SUFFIX_VERSCHILLEN);

                    result.setLezenBrp(new TestStap(TestStatus.NOK, "Er zijn inhoudelijke verschillen (lezen)", htmlBrp, htmlVerschillen));
                }

            } catch (final Exception e) {
                final Foutmelding fout = new Foutmelding("Fout tijdens lezen BRP.", e);
                final String htmlFout = debugOutputXmlEnHtml(fout, STAP_LEZEN);

                // Lees verwachte expection
                final Foutmelding verwachteFout = leesVerwachteFoutmelding(STAP_LEZEN);
                if (verwachteFout != null) {
                    if (verwachteFout.equals(fout)) {
                        result.setLezenBrp(new TestStap(TestStatus.OK, "Er is een verwachte exceptie opgetreden (lezen)", htmlFout, null));
                    } else {
                        result.setLezenBrp(new TestStap(TestStatus.NOK, "Er is een anders dan verwachte exceptie opgetreden (lezen)", htmlFout, null));
                    }
                } else {
                    result.setLezenBrp(new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden (lezen)", htmlFout, null));
                }
            }
        }
    }

    /**
     * Implementatie van de conversiehook.
     */
    public final class LoggingConversieHook implements ConversieHook {
        private final TestCasusOutputStap casusStap;

        /**
         * Default constructor.
         *
         * @param casusStap
         *            De test casus stap.
         */
        public LoggingConversieHook(final TestCasusOutputStap casusStap) {
            this.casusStap = casusStap;
        }

        @Override
        public void stap(final ConversieStap stap, final Object object) {
            debugOutputXmlEnHtml(object, casusStap, stap.toString());
        }

    }
}
