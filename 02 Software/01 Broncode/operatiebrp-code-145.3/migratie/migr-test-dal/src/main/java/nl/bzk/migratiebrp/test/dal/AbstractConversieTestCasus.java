/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.dal;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Lo3BerichtenBron;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.logging.LoggingContext;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.impl.Lg01Bericht;
import nl.bzk.migratiebrp.bericht.model.lo3.parser.Lo3PersoonslijstParser;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.conversie.model.exceptions.PreconditieException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3Lg01BerichtWaarde;
import nl.bzk.migratiebrp.conversie.model.proces.brpnaarlo3.Lo3StapelHelper;
import nl.bzk.migratiebrp.conversie.regels.proces.ConversieHook;
import nl.bzk.migratiebrp.conversie.regels.proces.impl.ConverteerBrpNaarLo3ServiceImpl;
import nl.bzk.migratiebrp.conversie.regels.proces.impl.ConverteerLo3NaarBrpServiceImpl;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.Lo3SyntaxControle;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.PreconditiesService;
import nl.bzk.migratiebrp.init.logging.model.domein.entities.FingerPrint;
import nl.bzk.migratiebrp.init.logging.verschilanalyse.analyse.VergelijkResultaat;
import nl.bzk.migratiebrp.init.logging.verschilanalyse.service.VerschilAnalyseService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpPersoonslijstService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.PersoonslijstPersisteerResultaat;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.test.common.reader.Reader;
import nl.bzk.migratiebrp.test.common.reader.ReaderFactory;
import nl.bzk.migratiebrp.test.common.resultaat.Foutmelding;
import nl.bzk.migratiebrp.test.common.resultaat.TestStap;
import nl.bzk.migratiebrp.test.common.resultaat.TestStatus;
import nl.bzk.migratiebrp.test.common.sql.SqlUtil;
import nl.bzk.migratiebrp.test.common.vergelijk.VergelijkSql;
import nl.bzk.migratiebrp.test.common.vergelijk.VergelijkXml;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Abstract conversie test casus met daarin methoden om bv van een Lo3 persoonslijst naar een BRP
 * persoonslijst te converteren.
 */
public abstract class AbstractConversieTestCasus extends TestCasus {
    /**
     * Aantal milliseconde in 1 seconde.
     */
    protected static final int MILLIS_IN_SECOND = 1000;

    /**
     * Lege hook tbv conversie.
     */
    private static final ConversieHook NULL_HOOK = (stap, object) -> {
        // Niets
    };

    private static final Logger LOG = LoggerFactory.getLogger();
    private final Lo3PersoonslijstParser parser = new Lo3PersoonslijstParser();
    @Inject
    private Lo3SyntaxControle syntaxControle;
    @Inject
    private PreconditiesService preconditieService;
    @Inject
    private ConverteerLo3NaarBrpServiceImpl converteerLo3NaarBrpService;
    @Inject
    private ConverteerBrpNaarLo3ServiceImpl converteerBrpNaarLo3Service;
    @Inject
    private BrpDalService brpDalService;
    @Inject
    private BrpPersoonslijstService brpPersoonslijstService;
    @Inject
    private ReaderFactory readerFactory;
    @Inject
    private VerschilAnalyseService verschilAnalyseService;
    @Inject
    @Named("syncDalDataSource")
    private DataSource syncDalDataSource;

    /**
     * Constructor van de casus.
     * @param thema thema van de casus
     * @param naam naam van de casus
     * @param outputFolder folder waar de output naar toe moet
     * @param expectedFolder folder waar de verwachte resultaten staan
     */
    protected AbstractConversieTestCasus(final String thema, final String naam, final File outputFolder, final File expectedFolder) {
        super(thema, naam, outputFolder, expectedFolder);
    }

    /**
     * Test de syntax en precondities, zonder suffix in de bestandsnamen.
     * @param input de waarden van de verschillende LO3 categorieen
     * @return een {@link Pair} met daarin links een {@link Lo3Persoonslijst} gevuld met de opgegeven input en rechts de {@link TestStap} met de resultaten van
     * deze stap.
     */
    protected final Pair<Lo3Persoonslijst, TestStap> testSyntaxPrecondities(final List<Lo3CategorieWaarde> input) {
        return testSyntaxPrecondities(input, null);
    }

    /**
     * Test de syntax en precondities, met evt. suffix in de bestandsnamen.
     * @param input de waarden van de verschillende LO3 categorieen
     * @param suffix suffix die in de bestandsnaam voorkomt. Indien null, dan wordt er geen suffix gebruikt
     * @return een {@link Pair} met daarin links een {@link Lo3Persoonslijst} gevuld met de opgegeven input en rechts de {@link TestStap} met de resultaten van
     * deze stap.
     */
    protected final Pair<Lo3Persoonslijst, TestStap> testSyntaxPrecondities(final List<Lo3CategorieWaarde> input, final String suffix) {
        Lo3Persoonslijst lo3Pl = null;
        TestStap testStap;
        LOG.info("Test controleer syntax en precondities ...");

        try {
            // Syntax
            final List<Lo3CategorieWaarde> syntax = syntaxControle.controleer(input);

            // Parse
            final Lo3Persoonslijst lo3 = parser.parse(syntax);

            // Precondities
            lo3Pl = preconditieService.verwerk(lo3);
            final String lo3Html = debugOutputXmlEnHtml(lo3Pl, TestCasusOutputStap.STAP_SYNTAX_PRECONDITIES, suffix);

            testStap = new TestStap(TestStatus.OK, null, lo3Html, null);
        } catch (final OngeldigePersoonslijstException ope) {
            final String htmlBrp = debugOutputXmlEnHtml(ope, TestCasusOutputStap.STAP_SYNTAX_PRECONDITIES, suffix);
            final OngeldigePersoonslijstException expectedException =
                    leesVerwachteOngeldigePersoonslijstException(TestCasusOutputStap.STAP_SYNTAX_PRECONDITIES, suffix);

            if (expectedException == null) {
                testStap = new TestStap(TestStatus.NOK, null, htmlBrp, null);
            } else {
                if (expectedException.equals(ope)) {
                    testStap = new TestStap(TestStatus.OK, null, htmlBrp, null);
                } else {
                    final Foutmelding fout = new Foutmelding("Vergelijking ongeldige persoonslijst", ope);
                    final String htmlFilename = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_SYNTAX_PRECONDITIES, suffix + SUFFIX_VERSCHILLEN);
                    testStap = new TestStap(TestStatus.NOK, "Exception niet niet de verwachte", htmlBrp, htmlFilename);
                }
            }
        }
        return new ImmutablePair<>(lo3Pl, testStap);
    }

    /**
     * Test conversie lo3 naar brp, zonder suffix in de bestandsnamen.
     * @param input Lo3 Persoonslijst welke geconvertereerd moet worden
     * @return een {@link Pair} met daarin links een {@link BrpPersoonslijst} en rechts de {@link TestStap} met de resultaten van deze stap.
     */
    protected final Pair<BrpPersoonslijst, TestStap> testLo3NaarBrp(final Lo3Persoonslijst input) {
        return testLo3NaarBrp(input, null);
    }

    /**
     * Test conversie lo3 naar brp, met mogelijk een suffix in de bestandsnamen.
     * @param input Lo3 Persoonslijst welke geconvertereerd moet worden
     * @param suffix suffix die in de bestandsnaam voorkomt. Indien null, dan wordt er geen suffix gebruikt
     * @return een {@link Pair} met daarin links een {@link BrpPersoonslijst} en rechts de {@link TestStap} met de resultaten van deze stap.
     */
    protected final Pair<BrpPersoonslijst, TestStap> testLo3NaarBrp(final Lo3Persoonslijst input, final String suffix) {
        LOG.info("Test converteer LO3 naar BRP ...");
        BrpPersoonslijst brp = null;
        TestStap testStap;
        try {
            brp = converteerLo3NaarBrpService.converteerLo3Persoonslijst(input, NULL_HOOK);
            brp.sorteer();
            testStap = controleerBrpResultaat(suffix, brp, null, TestCasusOutputStap.STAP_BRP);
        } catch (final Exception e) {
            final Foutmelding fout = new Foutmelding("Fout tijdens converteren LO3 naar BRP.", e);
            final String htmlFout = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_BRP, suffix);
            testStap = new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden (conversie naar brp)", htmlFout, null);
        }

        return new ImmutablePair<>(brp, testStap);
    }

    private TestStap controleerBrpResultaat(final String suffix, final BrpPersoonslijst brp, final BrpPersoonslijst controleBrpPersoonslijst,
                                            final TestCasusOutputStap outputStap) {
        final TestStap testStap;
        final String htmlBrp = debugOutputXmlEnHtml(brp, outputStap, suffix);
        final BrpPersoonslijst verwachteBrpPersoonslijst = leesVerwachteBrpPersoonslijst(outputStap, suffix);

        if (verwachteBrpPersoonslijst == null && controleBrpPersoonslijst == null) {
            testStap = new TestStap(TestStatus.GEEN_VERWACHTING, null, htmlBrp, null);
        } else {
            final BrpPersoonslijst basisPersoonslijst;
            if (verwachteBrpPersoonslijst == null) {
                basisPersoonslijst = controleBrpPersoonslijst;
            } else {
                // Niet zeker of de verwachte persoonslijst al gesorteerd is opgeslagen, dus voor de zekerheid sorteren
                basisPersoonslijst = verwachteBrpPersoonslijst;
                basisPersoonslijst.sorteer();
            }
            final StringBuilder verschillenLog = new StringBuilder();
            if (VergelijkXml
                    .vergelijkXmlNegeerActieId(basisPersoonslijst, brp, outputStap == TestCasusOutputStap.STAP_LEZEN, verschillenLog)) {
                testStap = new TestStap(TestStatus.OK, null, htmlBrp, null);
            } else {
                final Foutmelding fout = new Foutmelding("Vergelijking (brp)", "Inhoud is ongelijk (brp)", verschillenLog.toString());
                final String htmlVerschillen = debugOutputXmlEnHtml(fout, outputStap, suffix + SUFFIX_VERSCHILLEN);

                testStap = new TestStap(TestStatus.NOK, "Er zijn inhoudelijke verschillen (brp)", htmlBrp, htmlVerschillen);
            }
        }
        return testStap;
    }

    private void opmakenVerschillenFingerprints(final StringBuilder sb, final List<String> list, final String message) {
        if (list.isEmpty()) {
            return;
        }

        sb.append(message).append(EOL);
        for (final String listItem : list) {
            sb.append(listItem).append(EOL);
        }
        sb.append(EOL);
    }

    private List<String> checkFingerprints(final List<String> left, final List<String> right) {
        final List<String> resultaat = new ArrayList<>();
        for (final String leftItem : left) {
            if (!right.contains(leftItem)) {
                resultaat.add(leftItem);
            }
        }
        return resultaat;
    }

    /**
     * Test opslaan in BRP, met mogelijk een suffix in de bestandsnamen.
     * @param lo3Lg01BerichtWaarde object met daarin de gegevens uit een lg01 bericht.
     * @param input de BrpPersoonlijst die opgeslagen moet worden
     * @param suffix suffix die in de bestandsnaam voorkomt. Indien null, dan wordt er geen suffix gebruikt
     * @param teOverschrijvenPersoonId het Id van de te overschrijven persoon
     * @return een {@link Pair} met daarin links een persoon.id en rechts de {@link TestStap} met de resultaten van deze stap.
     */
    private Pair<Long, TestStap> testOpslaanBrp(final Lo3Lg01BerichtWaarde lo3Lg01BerichtWaarde, final BrpPersoonslijst input,
                                                   final Long teOverschrijvenPersoonId, final String suffix) {
        Long persoonId = null;
        TestStap testStap = null;
        if (input != null) {
            LOG.info("Test opslaan in BRP ...");
            try {
                // Opslaan in database
                final Lo3Bericht lo3Bericht = new Lo3Bericht("ConversieTestCasus", Lo3BerichtenBron.INITIELE_VULLING, new Timestamp(System.currentTimeMillis()),
                        maakLg01String(lo3Lg01BerichtWaarde), true);
                final PersoonslijstPersisteerResultaat persoonslijstPersisteerResultaat;
                if (teOverschrijvenPersoonId != null) {
                    persoonslijstPersisteerResultaat = getBrpPersoonslijstService().persisteerPersoonslijst(input, teOverschrijvenPersoonId, lo3Bericht);
                } else {
                    persoonslijstPersisteerResultaat = getBrpPersoonslijstService().persisteerPersoonslijst(input, lo3Bericht);
                }
                setAdministratieveHandelingenAlsGeleverd(persoonslijstPersisteerResultaat);

                testStap = new TestStap(TestStatus.OK);

                // Juiste *iets* teruggeven om test door te laten lopen naar 'lezen'.
                persoonId = persoonslijstPersisteerResultaat.getPersoon().getId();
            } catch (final Exception e) {
                final Foutmelding fout = new Foutmelding("Fout tijdens opslaan BRP.", e);
                final String htmlFout = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_OPSLAAN, suffix);

                testStap = new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden (opslaan)", htmlFout, null);
            }
        }
        return new ImmutablePair<>(persoonId, testStap);
    }

    private void setAdministratieveHandelingenAlsGeleverd(final PersoonslijstPersisteerResultaat persoonslijstPersisteerResultaat) {
        for (final AdministratieveHandeling administratieveHandeling : persoonslijstPersisteerResultaat.getAdministratieveHandelingen()) {
            new JdbcTemplate(syncDalDataSource).update("update kern.admhnd set statuslev = 4 where id = ?", administratieveHandeling.getId());
        }
    }

    /**
     * Test lezen uit brp.
     * @param teLezenPersoonId anummer
     * @param result resultaat
     * @param opgeslagenLijst (optioneel) opgeslagen brp lijst om te vergelijken
     * @return brp
     */
    private BrpPersoonslijst testLezenBrp(final Long teLezenPersoonId, final ConversieTestResultaat result, final BrpPersoonslijst opgeslagenLijst,
                                          final String suffix) {
        if (teLezenPersoonId == null) {
            return null;
        }
        BrpPersoonslijst resultaat = null;
        TestStap testStap;

        LOG.info("Test lezen uit BRP ...");
        final TestCasusOutputStap stap = TestCasusOutputStap.STAP_LEZEN;
        try {
            // Opvragen uit database
            resultaat = getBrpPersoonslijstService().bevraagPersoonslijstOpTechnischeSleutel(teLezenPersoonId);
            resultaat.sorteer();
            testStap = controleerBrpResultaat(suffix, resultaat, opgeslagenLijst, stap);
        } catch (final Exception e) {
            final Foutmelding fout = new Foutmelding("Fout tijdens lezen BRP.", e);
            final String htmlFout = debugOutputXmlEnHtml(fout, stap, suffix);

            testStap = new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden (lezen)", htmlFout, null);
        }

        result.setLezenBrp(testStap);
        return resultaat;
    }

    private BrpPersoonslijstService getBrpPersoonslijstService() {
        return brpPersoonslijstService;
    }

    /**
     * Controleert de opgegeven logging tegen de eventuele verwachte logging.
     * @param logging de logging die gecontroleerd moet worden
     * @return een {@link TestStap} met de resultaten van deze stap.
     */
    protected final TestStap controleerLogging(final Logging logging) {
        return controleerLogging(logging, null);
    }

    /**
     * Controleert de opgegeven logging tegen de eventuele verwachte logging.
     * @param logging de logging die gecontroleerd moet worden
     * @param suffix suffix die in de bestandsnaam voorkomt. Indien null, dan wordt er geen suffix gebruikt
     * @return een {@link TestStap} met de resultaten van deze stap.
     */
    protected final TestStap controleerLogging(final Logging logging, final String suffix) {
        final TestStap testStap;
        final String loggingHtml = debugOutputXmlEnHtml(logging, TestCasusOutputStap.STAP_LOGGING, suffix);

        // Controleer verwachting logging
        final Logging verwachteLogging = leesVerwachteLogging(TestCasusOutputStap.STAP_LOGGING, suffix);

        if (verwachteLogging == null) {
            testStap = new TestStap(TestStatus.GEEN_VERWACHTING, null, loggingHtml, null);
        } else {
            final StringBuilder verschillenLog = new StringBuilder();
            if (vergelijkLog(verschillenLog, verwachteLogging, logging)) {
                testStap = new TestStap(TestStatus.OK, null, loggingHtml, null);
            } else {
                final Foutmelding fout = new Foutmelding("Vergelijking (logging)", "Inhoud is ongelijk (logging)", verschillenLog.toString());
                final String htmlVerschillen = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_LOGGING, suffix + SUFFIX_VERSCHILLEN);
                testStap = new TestStap(TestStatus.NOK, "Er zijn inhoudelijke verschillen (logging)", loggingHtml, htmlVerschillen);
            }
        }
        return testStap;
    }

    /**
     * Controleert de opgegeven verwerkingsmelding tegen de eventuele verwachte verwerkingsmelding.
     * @param verwerkingsmelding de logging die gecontroleerd moet worden
     * @param suffix suffix die in de bestandsnaam voorkomt. Indien null, dan wordt er geen suffix gebruikt
     * @return een {@link TestStap} met de resultaten van deze stap.
     */
    protected final TestStap controleerKruimelpad(final String verwerkingsmelding, final String suffix) {
        final TestStap testStap;
        final String verwerkingsmeldingFilename = debugOutputString(verwerkingsmelding, TestCasusOutputStap.STAP_VERWERKINGSMELDING, suffix);

        // Controleer verwachting verwerkingsmelding
        final String verwachteVerwerkingsmelding = leesVerwachteString(TestCasusOutputStap.STAP_VERWERKINGSMELDING, suffix);

        if (verwachteVerwerkingsmelding == null) {
            testStap = new TestStap(TestStatus.GEEN_VERWACHTING, null, verwerkingsmeldingFilename, null);
        } else {
            final StringBuilder verschillenLog = new StringBuilder();
            if (vergelijkVerwerkingsmeldingen(verschillenLog, verwachteVerwerkingsmelding, verwerkingsmelding)) {
                testStap = new TestStap(TestStatus.OK, null, verwerkingsmeldingFilename, null);
            } else {
                final Foutmelding fout =
                        new Foutmelding("Vergelijking (verwerkingsmelding)", "Inhoud is ongelijk (verwerkingsmelding)", verschillenLog.toString());
                final String htmlVerschillen = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_VERWERKINGSMELDING, suffix + SUFFIX_VERSCHILLEN);
                testStap = new TestStap(TestStatus.NOK, "Er zijn inhoudelijke verschillen (verwerkingsmelding)", verwerkingsmeldingFilename, htmlVerschillen);
            }
        }
        return testStap;
    }

    /**
     * Test de database met de meegegeven lijst van queries, met mogelijk een suffix in de
     * bestandsnamen.
     * @param sqlQueryFiles een lijst met {@link File} objecten met daarin de SQL-queries die uitgevoerd moeten worden.
     * @return een {@link Map} als key de naam van het uitgevoerde query-bestand en als value een {@link TestStap} -object met de resultaten.
     */
    protected final Map<String, TestStap> testSqlQueries(final List<File> sqlQueryFiles) {
        final Map<String, TestStap> results = new TreeMap<>();

        for (final File sqlFile : sqlQueryFiles) {
            final String sqlFilename = sqlFile.getName();
            final TestStap testStap;
            String sqlQuery = "select 'Bestand niet ingelezen " + sqlFilename + "' :: varchar (100) as foutmelding ";
            if (readerFactory.accept(sqlFile)) {
                final Reader reader = readerFactory.getReader(sqlFile);
                try {
                    sqlQuery = reader.readFile(sqlFile);
                } catch (final IOException e) {
                    LOG.error("Fout tijdens inlezen SQL-bestand", e);
                }
            }
            final List<Map<String, Object>> sqlResults = SqlUtil.executeQuery(syncDalDataSource, sqlQuery);
            final String csvSqlResult = debugOutputCsv(sqlFilename, sqlResults, TestCasusOutputStap.STAP_SQL);

            // Controleer verwachten
            final List<Map<String, Object>> expectedSqlResults = leesVerwachteSqlResultaten(sqlFilename, TestCasusOutputStap.STAP_SQL);
            if (expectedSqlResults == null) {
                testStap = new TestStap(TestStatus.GEEN_VERWACHTING, null, csvSqlResult, null);

            } else {
                final StringBuilder verschillenLog = new StringBuilder();
                if (VergelijkSql.vergelijkSqlResultaten(verschillenLog, sqlResults, expectedSqlResults)) {
                    testStap = new TestStap(TestStatus.OK, null, csvSqlResult, null);
                } else {
                    final Foutmelding fout = new Foutmelding("Vergelijking (sql)", "Inhoud is ongelijk (sql)", verschillenLog.toString());
                    final String htmlVerschillen = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_SQL, sqlFilename + SUFFIX_VERSCHILLEN);

                    testStap = new TestStap(TestStatus.NOK, "Er zijn inhoudelijke verschillen (sql)", csvSqlResult, htmlVerschillen);
                }
            }
            results.put(sqlFilename, testStap);
        }
        return results;
    }

    /**
     * Retourneer de Lo3PersoonslijstParser aan subclasses.
     * @return de Lo3PersoonslijstParser
     */
    private Lo3PersoonslijstParser getLo3PersoonslijstParser() {
        return parser;
    }

    private String maakLg01String(final Lo3Lg01BerichtWaarde lo3Lg01BerichtWaarde) {
        final List<Lo3CategorieWaarde> lo3CategorieWaarden = lo3Lg01BerichtWaarde.getLo3CategorieWaardeList();
        final Lo3Persoonslijst uncheckedPl = parser.parse(lo3CategorieWaarden);

        final Lg01Bericht bericht = new Lg01Bericht();
        bericht.setLo3Persoonslijst(uncheckedPl);

        return Lo3Inhoud.formatInhoud(bericht.formatInhoud());
    }

    /**
     * Initialiseert de logging en parsede lijst van categorie waarden naar een Lo3Persoonslijst.
     * Deze lijst wordt vervolgens weg geschreven naar XML/Html en Lg01 formaat.
     * @param lo3CategorieWaarden lijst met waarden per categorie
     * @return HTML representatie van de LO3 Persoonslijst.
     */
    protected String initializeerLogging(final List<Lo3CategorieWaarde> lo3CategorieWaarden) {
        final TestCasusOutputStap stap = TestCasusOutputStap.STAP_LO3;
        final Lo3Persoonslijst uncheckedPl = getLo3PersoonslijstParser().parse(lo3CategorieWaarden);
        final String htmlBron = debugOutputXmlEnHtml(uncheckedPl, stap, null);

        debugOutputLg01(uncheckedPl, stap, null);

        SynchronisatieLogging.init();
        Logging.initContext();
        LoggingContext.registreerActueelAdministratienummer(uncheckedPl.getActueelAdministratienummer());
        return htmlBron;
    }

    /**
     * Opslaan/lezen en terugconversie stappen.
     * @param result resultaat object waar de resultaten van de stappen in weg geschreven wordt
     * @param lo3Lg01BerichtWaarde object met daarin de bericht waarden uit het Lg01 bericht
     * @param checkedPl de LO3-persoonslijst die gecontroleerd is tegen syntax/precondities
     * @param brp de BRP Persoonslijst welke opgeslagen moet worden
     * @return het persoonId van de persoon die is opgeslagen
     */
    protected final Long opslaanLezenTerugConversieBrp(final ConversieTestResultaat result, final Lo3Lg01BerichtWaarde lo3Lg01BerichtWaarde,
                                                          final Lo3Persoonslijst checkedPl, final BrpPersoonslijst brp) {
        return opslaanLezenTerugConversieBrp(result, lo3Lg01BerichtWaarde, checkedPl, brp, null, null);
    }

    /**
     * Opslaan/lezen en terugconversie stappen.
     * @param result resultaat object waar de resultaten van de stappen in weg geschreven wordt
     * @param lo3Lg01BerichtWaarde object met daarin de bericht waarden uit het Lg01 bericht
     * @param checkedPl de LO3-persoonslijst die gecontroleerd is tegen syntax/precondities
     * @param brp de BRP Persoonslijst welke opgeslagen moet worden
     * @param suffix de suffix als er meer dan 1 persoonlijst aangeboden is aan de test
     * @param teVervangenPersoonId het Id van de te vervangen persoon Id
     * @return het persoonId van de persoon die is opgeslagen
     */
    protected final Long opslaanLezenTerugConversieBrp(final ConversieTestResultaat result, final Lo3Lg01BerichtWaarde lo3Lg01BerichtWaarde,
                                                          final Lo3Persoonslijst checkedPl, final BrpPersoonslijst brp, final Long teVervangenPersoonId,
                                                          final String suffix) {
        // Test opslaan
        final Pair<Long, TestStap> opslaanBrpResult = testOpslaanBrp(lo3Lg01BerichtWaarde, brp, teVervangenPersoonId, suffix);
        final Long persoonId = opslaanBrpResult.getLeft();
        result.setOpslaanBrp(opslaanBrpResult.getRight());

        if (!checkedPl.isDummyPl()) {
            // Test lezen uit database
            final BrpPersoonslijst brpGelezen = testLezenBrp(persoonId, result, brp, suffix);
            // Test conversie brp naar lo3
            final Pair<TestStap, TestStap> brpNaarLo3Stap = testTerugconversie(brpGelezen, checkedPl, TestCasusOutputStap.STAP_TERUG, suffix);
            result.setBrpNaarLo3(brpNaarLo3Stap.getLeft());
            result.setBrpNaarLo3VerschilAnalyse(brpNaarLo3Stap.getRight());
        }

        return opslaanBrpResult.getLeft();
    }

    /**
     * Voert de terug conversie uit en controleert de terug geconverteerde persoonslijst tegen of de
     * orginele persoonslijst of tegen een verwachte persoonslijst als deze er is.
     * @param brpPersoonslijst BRP persoonslijst die terug geconverteerd moet worden
     * @param origineel origineel LO3 persoonslijst
     * @param outputStap outputstap voor de basis vergelijker. Deze stap zet intern ook de outputstap voor de verschilanalyse.
     * @param suffix de suffix als er meer dan 1 persoonlijst aangeboden is aan de test
     * @return een {@link Pair} met daarin 2 {@link TestStap}. Links/key is de stap van de basis vergelijker, rechts/value is de stap van de verschil analayse.
     */
    protected final Pair<TestStap, TestStap> testTerugconversie(final BrpPersoonslijst brpPersoonslijst, final Lo3Persoonslijst origineel,
                                                                final TestCasusOutputStap outputStap, final String suffix) {
        if (brpPersoonslijst == null || origineel.isDummyPl()) {
            return new ImmutablePair<>(null, null);
        }

        final TestCasusOutputStap vaOutputStap;
        if (TestCasusOutputStap.STAP_ROND.equals(outputStap)) {
            vaOutputStap = TestCasusOutputStap.STAP_ROND_VA;
        } else {
            vaOutputStap = TestCasusOutputStap.STAP_TERUG_VA;
        }

        Pair<TestStap, TestStap> resultaat;
        LOG.info("Test terugconversie (" + outputStap.toString() + ")...");
        try {
            final Lo3Persoonslijst terugConverteerdePL = converteerBrpNaarLo3Service.converteerBrpPersoonslijst(brpPersoonslijst, NULL_HOOK);
            debugOutputLg01(terugConverteerdePL, outputStap, suffix);

            final Lo3Persoonslijst verwachteLo3Persoonslijst = leesVerwachteLo3Persoonslijst(outputStap, suffix);

            final Lo3Persoonslijst basisPL;
            if (verwachteLo3Persoonslijst == null) {
                basisPL = origineel;
            } else {
                basisPL = verwachteLo3Persoonslijst;
            }
            final TestStap testStapBasisVergelijker = controleerMetBasisVergelijker(basisPL, terugConverteerdePL, outputStap, suffix);
            // Verschil analyse gaat altijd tegen het origineel
            final TestStap testStapVerschilAnalyse = controleerMetVerschilAnalyse(origineel, terugConverteerdePL, vaOutputStap, suffix);
            resultaat = new ImmutablePair<>(testStapBasisVergelijker, testStapVerschilAnalyse);
        } catch (final PreconditieException pe) {
            final String htmlFout = debugOutputXmlEnHtml(pe, outputStap, suffix);
            resultaat = new ImmutablePair<>(null, new TestStap(TestStatus.EXCEPTIE, "Er is een preconditie exceptie opgetreden", htmlFout, null));
            // Alle fouten die mogelijk kunnen optreden tijdens terug conversie hier opvangen en
            // netjes tonen als fout.
        } catch (final Exception e) {
            final Foutmelding fout = new Foutmelding("Fout tijdens terug conversie van BRP naar LO3.", e);
            final String htmlFout = debugOutputXmlEnHtml(fout, outputStap, suffix);

            resultaat = new ImmutablePair<>(new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden", htmlFout, null), null);
        }
        return resultaat;
    }

    private TestStap controleerMetBasisVergelijker(final Lo3Persoonslijst origineel, final Lo3Persoonslijst terugConversie,
                                                   final TestCasusOutputStap outputStap, final String suffix) {
        final String htmlLo3 = debugOutputXmlEnHtml(terugConversie, outputStap, suffix);

        // Controleer verwachting
        final Lo3Persoonslijst expectedLo3 = leesVerwachteLo3Persoonslijst(outputStap, suffix);

        final StringBuilder verschillenLog = new StringBuilder();
        final TestStap resultaat;
        if (Lo3StapelHelper.vergelijkPersoonslijst(verschillenLog, expectedLo3 == null ? origineel : expectedLo3, terugConversie)) {
            resultaat = new TestStap(TestStatus.OK, null, htmlLo3, null);
        } else {
            final Foutmelding fout = new Foutmelding("Basis vergelijker", "Inhoud is ongelijk", verschillenLog.toString());
            final String htmlVerschillen = debugOutputXmlEnHtml(fout, outputStap, suffix + SUFFIX_VERSCHILLEN);
            resultaat = new TestStap(TestStatus.NOK, "Er zijn inhoudelijke verschillen", htmlLo3, htmlVerschillen);
        }

        return resultaat;
    }

    private TestStap controleerMetVerschilAnalyse(final Lo3Persoonslijst origineel, final Lo3Persoonslijst terugConversie, final TestCasusOutputStap outputStap,
                                                  final String suffix) {
        final List<VergelijkResultaat> verschillen = verschilAnalyseService.bepaalVerschillen(origineel, terugConversie);
        final List<String> actualFingerprints = new ArrayList<>();
        for (final VergelijkResultaat verschil : verschillen) {
            final FingerPrint fingerPrint = verschil.getVingerafdruk();
            actualFingerprints.add(fingerPrint.getVoorkomenVerschil());
        }

        final String htmlFingerprints = debugOutputTxt(actualFingerprints, suffix, outputStap);
        final List<String> expectedFingerPrints = leesVerwachteFingerPrints(outputStap, suffix);

        final TestStatus testStatus;
        final String testOmschrijving;
        final String verschillenFingerprints;

        if (expectedFingerPrints.isEmpty()) {
            testStatus = actualFingerprints.isEmpty() ? TestStatus.OK : TestStatus.GEEN_VERWACHTING;
            testOmschrijving = null;
            verschillenFingerprints = null;
        } else {
            final List<String> notExpected = checkFingerprints(expectedFingerPrints, actualFingerprints);
            final List<String> notActual = checkFingerprints(actualFingerprints, expectedFingerPrints);

            if (!notExpected.isEmpty() || !notActual.isEmpty()) {
                // een verkregen fingerprint is niet verwacht of een verwachte fingerprint is niet
                // verkregen.
                final StringBuilder sb = new StringBuilder();
                opmakenVerschillenFingerprints(sb, notExpected, "Fingerprints verwacht, maar niet verkregen");
                opmakenVerschillenFingerprints(sb, notActual, "Fingerprints verkregen, maar niet verwacht");

                final String htmlVerschillen = debugOutputTxt(Collections.singletonList(sb.toString()), suffix + SUFFIX_VERSCHILLEN, outputStap);
                testStatus = TestStatus.NOK;
                testOmschrijving = "Een of meerdere vingerafdrukken komen niet overeen";
                verschillenFingerprints = htmlVerschillen;
            } else {
                testStatus = TestStatus.OK;
                testOmschrijving = null;
                verschillenFingerprints = null;
            }
        }
        return new TestStap(testStatus, testOmschrijving, htmlFingerprints, verschillenFingerprints);
    }
}
