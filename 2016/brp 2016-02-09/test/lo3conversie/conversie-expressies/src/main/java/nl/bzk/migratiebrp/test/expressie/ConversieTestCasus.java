/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.expressie;

import antlr.RecognitionException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import nl.bzk.brp.blobifier.service.AfnemerIndicatieBlobifierService;
import nl.bzk.brp.blobifier.service.BlobifierService;
import nl.bzk.brp.expressietaal.Expressie;
import nl.bzk.brp.expressietaal.Keyword;
import nl.bzk.brp.expressietaal.expressies.VariabeleExpressie;
import nl.bzk.brp.expressietaal.expressies.functies.FunctieExpressie;
import nl.bzk.brp.expressietaal.expressies.functies.Functieberekening;
import nl.bzk.brp.expressietaal.expressies.operatoren.AbstractBinaireOperatorExpressie;
import nl.bzk.brp.expressietaal.expressies.operatoren.AbstractUnaireOperatorExpressie;
import nl.bzk.brp.expressietaal.parser.BRPExpressies;
import nl.bzk.brp.expressietaal.parser.ParserResultaat;
import nl.bzk.brp.model.hisvolledig.impl.autaut.PersoonAfnemerindicatieHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.momentview.kern.PersoonView;
import nl.bzk.migratiebrp.bericht.model.BerichtSyntaxException;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.parser.Lo3PersoonslijstParser;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAfnemersindicatie;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAfnemersindicaties;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAutorisatie;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Historie;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Stapel;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Afnemersindicatie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AfnemersindicatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Autorisatie;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3AutorisatieInhoud;
import nl.bzk.migratiebrp.conversie.model.lo3.codes.Lo3IndicatieGeheimCodeEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3ElementEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.regels.expressie.ConverteerNaarExpressieService;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerLo3NaarBrpService;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.Lo3SyntaxControle;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.PreconditiesService;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Bericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3BerichtenBron;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.PersoonslijstPersisteerResultaat;
import nl.bzk.migratiebrp.test.common.reader.Reader;
import nl.bzk.migratiebrp.test.common.reader.ReaderFactory;
import nl.bzk.migratiebrp.test.common.resultaat.Foutmelding;
import nl.bzk.migratiebrp.test.common.resultaat.TestResultaat;
import nl.bzk.migratiebrp.test.common.resultaat.TestStap;
import nl.bzk.migratiebrp.test.common.resultaat.TestStatus;
import nl.bzk.migratiebrp.test.common.util.BaseFilter;
import nl.bzk.migratiebrp.test.common.util.FilterType;
import nl.bzk.migratiebrp.test.dal.TestCasus;
import nl.bzk.migratiebrp.test.dal.TestCasusOutputStap;
import nl.bzk.migratiebrp.test.expressie.ConversieTestResultaat.PersoonControleResultaat;
import nl.bzk.migratiebrp.test.expressie.brp.FunctieSelectieDatum;
import nl.bzk.migratiebrp.test.expressie.brp.FunctieVandaag;
import nl.bzk.migratiebrp.util.common.JdbcConstants;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.common.logging.LoggingContext;
import nl.gba.gbav.impl.util.UtilsImpl;
import nl.gba.gbav.lo3.LO3PL;
import nl.gba.gbav.util.configuration.DeploymentContext;
import nl.gba.gbav.util.configuration.ServiceLocator;
import nl.gba.gbav.util.security.SecurityContext;
import nl.ictu.spg.domain.lo3.pl.LO3PLImpl;
import nl.ictu.spg.domain.lo3.util.LO3LelijkParser;
import nl.ictu.spg.domain.lo3.voorwaarderegel.SelectieVoorwaardeRegel;
import nl.ictu.spg.domain.lo3.voorwaarderegel.SpontaanVoorwaardeRegel;
import nl.ictu.spg.domain.lo3.voorwaarderegel.VoorwaardeRegel;
import nl.ictu.spg.domain.lo3.voorwaarderegel.VoorwaardeRegelInterpretException;
import nl.ictu.spg.domain.lo3.voorwaarderegel.antlr.VoorwaardeRegelInterpreter;
import nl.ictu.spg.domain.pl.util.PLAssembler;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

/**
 * Test casus: conversie lo3 naar brp.
 */
public final class ConversieTestCasus extends TestCasus {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final int MILLIS_IN_SECOND = 1000;

    private static final TestCasusOutputStap STAP_INITIEREN = TestCasusOutputStap.STAP_LO3;
    private static final TestCasusOutputStap STAP_LO3_NAAR_BRP = TestCasusOutputStap.STAP_BRP;

    private static final Lo3PersoonslijstParser LO3_PARSER = new Lo3PersoonslijstParser();

    @Inject
    private ConverteerNaarExpressieService converteerNaarExpressieService;

    private Lo3SyntaxControle syntaxControle;
    private PreconditiesService preconditieService;
    private ConverteerLo3NaarBrpService converteerLo3NaarBrpService;
    private BrpDalService brpDalService;
    private DataSource migratieDataSource;
    private EntityManager migratieEntityManager;

    private PlatformTransactionManager transactionManagerMaster;
    private BlobifierService blobifierService;
    private AfnemerIndicatieBlobifierService afnemerIndicatieBlobifierService;

    private final File basisInputFile;
    private final File inputFile;
    private final String inputVoorwaarderegel;

    private final ReaderFactory readerFactory = new ReaderFactory();

    private String vasteWaardeVandaag;
    private String vasteWaardeSelectieDatum;

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
     * @param basisInputFile
     *            basis input (file)
     * @param inputFile
     *            input (file)
     * @param inputVoorwaarderegel
     *            de te testen voorwaarde regel
     */
    protected ConversieTestCasus(
        final String thema,
        final String naam,
        final File outputFolder,
        final File expectedFolder,
        final File basisInputFile,
        final File inputFile,
        final String inputVoorwaarderegel)
    {
        super(thema, naam, outputFolder, expectedFolder);
        this.basisInputFile = basisInputFile;
        this.inputFile = inputFile;
        this.inputVoorwaarderegel = inputVoorwaarderegel;
    }

    /**
     * Geef de waarde van bean for migratie autowire.
     *
     * @return bean for migratie autowire
     */
    public Object getBeanForMigratieAutowire() {
        return new BeanForMigratieAutowire();
    }

    /**
     * Geef de waarde van bean for brp levering autowire.
     *
     * @return bean for brp levering autowire
     */
    public Object getBeanForBrpLeveringAutowire() {
        return new BeanForBrpLeveringAutowire();
    }

    @Override
    public TestResultaat run() {
        final long startInMillis = System.currentTimeMillis();
        final ConversieTestResultaat result = new ConversieTestResultaat(getThema(), getNaam());

        try {
            Logging.initContext();
            initialiseerVasteWaarden();

            // Stap 1: Initieren
            final String lo3Voorwaarderegel = inputVoorwaarderegel;
            final String htmlExpressie = debugOutputString(lo3Voorwaarderegel, STAP_INITIEREN, null);
            result.setInitieren(new TestStap(TestStatus.OK, lo3Voorwaarderegel, htmlExpressie, null));

            // Stap 2: Converteren
            final String brpExpressie = testLo3NaarBrp(lo3Voorwaarderegel, result);

            // Stap 3: Controle obv uitvoer op een persoon
            testUitvoerenRegels(lo3Voorwaarderegel, brpExpressie, result);

        } finally {
            LoggingContext.reset();
            Logging.destroyContext();
            resetVasteWaarden();
        }

        final long endInMillis = System.currentTimeMillis();
        final float duration = (endInMillis - startInMillis) / (float) MILLIS_IN_SECOND;

        LOG.info("Testcase {} took {} seconds", getNaam(), duration);

        return result;
    }

    private void initialiseerVasteWaarden() {
        @SuppressWarnings("unchecked")
        final Dictionary<Keyword, Functieberekening> functieMapping =
                (Dictionary<Keyword, Functieberekening>) getStaticField(FunctieExpressie.class, "KEYWORD_MAPPING");

        functieMapping.put(Keyword.VANDAAG, new FunctieVandaag());
        // functieMapping.put("SELECTIE_DATUM", new FunctieSelectieDatum());

        resetVasteWaarden();

        laadVasteWaarden(basisInputFile.getName() + ".properties");
        laadVasteWaarden(inputFile.getName() + ".properties");

    }

    private void laadVasteWaarden(final String naam) {
        final File propertiesFile = new File(inputFile.getParentFile(), naam);
        if (propertiesFile.canRead()) {
            final Properties properties = new Properties();
            try (InputStream is = new FileInputStream(propertiesFile)) {
                properties.load(is);
            } catch (final IOException e) {
                throw new IllegalArgumentException("Kan test properties niet laden", e);
            }

            if (properties.containsKey("vandaag")) {
                vasteWaardeVandaag = properties.getProperty("vandaag");
            }
            if (properties.containsKey("selectiedatum")) {
                vasteWaardeSelectieDatum = properties.getProperty("selectiedatum");
            }
        }

    }

    private void resetVasteWaarden() {
        vasteWaardeVandaag = null;
        vasteWaardeSelectieDatum = null;
        FunctieVandaag.resetVandaag();
        FunctieSelectieDatum.resetSelectieDatum();
    }

    private Object getStaticField(final Class<?> clazz, final String fieldName) {
        try {
            final Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(null);
        } catch (final ReflectiveOperationException e) {
            throw new IllegalArgumentException("Kan statisch veld " + fieldName + " op klasse " + clazz + " niet ophalen", e);
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
     * @param lo3Voorwaarderegel
     *            lo3
     * @param result
     *            result
     * @return expressie
     */
    private String testLo3NaarBrp(final String lo3Voorwaarderegel, final ConversieTestResultaat result) {
        LOG.info("Test converteer LO3 naar BRP ...");
        try {

            final String expressie = converteerNaarExpressieService.converteerVoorwaardeRegel(lo3Voorwaarderegel);

            final String htmlExpressie = debugOutputString(expressie, STAP_LO3_NAAR_BRP, null);
            // Controleer verwachting
            final String expectedPlatteTekst = leesVerwachteString(STAP_LO3_NAAR_BRP, null);
            if (expectedPlatteTekst != null) {
                if (!expectedPlatteTekst.equals(expressie)) {
                    result.setLo3NaarBrp(new TestStap(TestStatus.NOK, expressie, htmlExpressie, null));
                } else {
                    result.setLo3NaarBrp(new TestStap(TestStatus.OK, expressie, htmlExpressie, null));
                }
            } else {
                result.setLo3NaarBrp(new TestStap(TestStatus.GEEN_VERWACHTING, expressie, htmlExpressie, null));
            }

            return expressie;

        } catch (final Exception e) {
            final Foutmelding fout = new Foutmelding("Fout tijdens converteren LO3 naar BRP.", e);
            final String htmlFout = debugOutputXmlEnHtml(fout, STAP_LO3_NAAR_BRP, "exceptie");

            // Lees verwachte expection
            final Foutmelding verwachteFout = leesVerwachteFoutmelding(STAP_LO3_NAAR_BRP);
            if (verwachteFout != null) {
                if (verwachteFout.equals(fout)) {
                    result.setLo3NaarBrp(new TestStap(TestStatus.OK, "Er is een verwachte exceptie opgetreden (conversie naar brp)", htmlFout, null));
                } else {
                    result.setLo3NaarBrp(
                        new TestStap(TestStatus.NOK, "Er is een anders dan verwachte exceptie opgetreden (conversie naar brp)", htmlFout, null));
                }
            } else {
                result.setLo3NaarBrp(new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden (conversie naar brp)", htmlFout, null));
            }

            return null;
        }
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private void testUitvoerenRegels(final String lo3Voorwaarderegel, final String brpExpressie, final ConversieTestResultaat result) {
        final File personenDirectory = new File(inputFile.getParent(), "personen");
        if (!personenDirectory.exists() || !personenDirectory.isDirectory()) {
            return;
        }

        for (final File persoonFile : personenDirectory.listFiles(new BaseFilter(FilterType.FILE))) {
            try {
                testUitvoerenRegels(persoonFile, lo3Voorwaarderegel, brpExpressie, result);
            } catch (final IOException e) {
                LOG.error("Kan persoon niet lezen", e);
            }
        }
    }

    private void testUitvoerenRegels(
        final File persoonFile,
        final String lo3Voorwaarderegel,
        final String brpExpressie,
        final ConversieTestResultaat result) throws IOException
    {
        final PersoonControleResultaat resultaat = new PersoonControleResultaat(persoonFile.getName());
        result.getPersonen().add(resultaat);

        // Lees lo3
        final Reader reader = readerFactory.getReader(persoonFile);
        final String input = reader.readFile(persoonFile);
        final String bestandType = input.substring(8, 12);
        if (!"lg01".equals(bestandType.toLowerCase())) {
            throw new IllegalArgumentException("Controle persoon moet een lg01 bericht zijn (is nu een '" + bestandType + "')");
        }
        final String pltext = input.substring(49);

        Boolean resultaatGba;
        try {
            resultaatGba = uitvoerenGbaRegel(pltext, lo3Voorwaarderegel);
            resultaat.setGbav(new TestStap(TestStatus.OK, resultaatGba.toString(), null, null));
        } catch (final Exception e) {
            final Foutmelding fout = new Foutmelding("Fout tijdens uitvoeren GBA voorwaarderegel.", e);
            final String htmlFout = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_ROND, persoonFile.getName() + "-gba-exceptie");
            resultaat.setGbav(new TestStap(TestStatus.EXCEPTIE, "Exceptie", htmlFout, null));
            resultaatGba = null;
        }

        Boolean resultaatBrp;
        if (brpExpressie == null || "".equals(brpExpressie)) {
            resultaatBrp = null;
        } else {
            try {
                final StringBuilder expressieLogging = new StringBuilder();
                resultaatBrp = uitvoerenBrpRegel(persoonFile.getName(), pltext, brpExpressie, expressieLogging);
                final String htmlLogging =
                        debugOutputString(expressieLogging.toString(), TestCasusOutputStap.STAP_ROND, persoonFile.getName() + "-expressies");
                resultaat.setBrp(new TestStap(TestStatus.OK, resultaatBrp.toString(), htmlLogging, null));
            } catch (final Exception e) {
                final Foutmelding fout = new Foutmelding("Fout tijdens uitvoeren BRP expressie.", e);
                final String htmlFout = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_ROND, persoonFile.getName() + "-brp-exceptie");
                resultaat.setBrp(new TestStap(TestStatus.EXCEPTIE, "Exceptie", htmlFout, null));
                resultaatBrp = null;
            }
        }

        final TestStatus status;
        if (resultaatGba == null || resultaatBrp == null) {
            status = TestStatus.GEEN_VERWACHTING;
        } else {
            status = resultaatGba.equals(resultaatBrp) ? TestStatus.OK : TestStatus.NOK;
        }

        resultaat.setStatus(status);

    }

    private Boolean uitvoerenGbaRegel(final String pltext, final String lo3Voorwaarderegel) {
        // Parse PL
        if (!ServiceLocator.isInitialized()) {
            ServiceLocator.initialize(new TestServiceLocator());
        }
        final PLAssembler assembler = new PLAssembler();
        final LO3PL persoonslijst = new LO3PLImpl(-1);
        assembler.startOfTraversal(persoonslijst);

        final LO3LelijkParser parser = new LO3LelijkParser();
        parser.parse(pltext, assembler);

        // Parse regel
        LOG.info("Voorwaarde = " + lo3Voorwaarderegel);
        final boolean uitvoerenAlsSelectie = lo3Voorwaarderegel.contains("19.89.20");
        final VoorwaardeRegel regel;
        if (uitvoerenAlsSelectie) {
            regel = new SelectieVoorwaardeRegel(lo3Voorwaarderegel);
        } else {
            regel = new SpontaanVoorwaardeRegel(lo3Voorwaarderegel);

        }
        LOG.info("Voorwaarde parser = " + regel);

        final VoorwaardeRegelInterpreter interpreter = (VoorwaardeRegelInterpreter) ReflectionTestUtils.getField(regel, "interpreter");
        // Vaste waarden in interpreter vastzetten
        if (vasteWaardeVandaag == null) {
            interpreter.resetVandaagDatum();
        } else {
            final Collection vandaagDatum = new java.util.HashSet();
            vandaagDatum.add(Integer.valueOf(vasteWaardeVandaag));
            ReflectionTestUtils.setField(interpreter, "vandaagDatum", vandaagDatum);
            // System.out.println("VandaagDatum: " + vandaagDatum);
        }

        if (uitvoerenAlsSelectie) {
            if (vasteWaardeSelectieDatum != null) {
                interpreter.setSelectieDatum(Integer.parseInt(vasteWaardeSelectieDatum));
            } else {
                throw new IllegalArgumentException("Voorwaarderegel bevat 19.89.20 maar er is geen selectiedatum aangeleverd.");
            }
        }

        // Uitvoeren regel
        interpreter.setPl(persoonslijst);
        final boolean result;
        try {
            result = interpreter.adhocvoorwaarde(regel.getAST());
        } catch (final RecognitionException e) {
            throw new VoorwaardeRegelInterpretException(e);
        }
        LOG.info("Voorwaarde isValid = " + result);

        return result;
    }

    private Boolean uitvoerenBrpRegel(final String persoonFilename, final String pltext, final String brpExpressie, final StringBuilder expressieLogging)
        throws BerichtSyntaxException, OngeldigePersoonslijstException
    {
        // BRP opschonen
        initierenDatabase();

        // Conversie initieren
        Logging.initContext();
        final BrpPersoonslijst brpPl;
        final Lo3Afnemersindicatie lo3Afnemersindicaties;
        try {
            // Parse
            final List<Lo3CategorieWaarde> lo3Inhoud = Lo3Inhoud.parseInhoud(pltext);

            // Afnemersindicaties
            final List<Lo3Stapel<Lo3AfnemersindicatieInhoud>> lo3AfnemersindicatieStapels = splitAfnemersindicaties(lo3Inhoud);

            // Lo3 syntax controle
            final List<Lo3CategorieWaarde> lo3InhoudNaSyntaxControle;
            try {
                lo3InhoudNaSyntaxControle = syntaxControle.controleer(lo3Inhoud);
            } catch (final OngeldigePersoonslijstException e) {
                LOG.info("Persoon heeft syntax fouten");
                debugOutputXmlEnHtml(Logging.getLogging(), TestCasusOutputStap.STAP_ROND, persoonFilename + "-syntax");
                throw e;
            }

            // Parse persoonslijst
            final Lo3Persoonslijst lo3Persoonslijst = LO3_PARSER.parse(lo3InhoudNaSyntaxControle);
            lo3Afnemersindicaties = new Lo3Afnemersindicatie(lo3Persoonslijst.getActueelAdministratienummer(), lo3AfnemersindicatieStapels);

            // Controleer precondities
            final Lo3Persoonslijst schoneLo3Persoonslijst;
            try {
                schoneLo3Persoonslijst = preconditieService.verwerk(lo3Persoonslijst);
            } catch (final OngeldigePersoonslijstException e) {
                LOG.info("Persoon heeft preconditie fouten");
                debugOutputXmlEnHtml(Logging.getLogging(), TestCasusOutputStap.STAP_ROND, persoonFilename + "-precondities");
                throw e;
            }

            brpPl = converteerLo3NaarBrpService.converteerLo3Persoonslijst(schoneLo3Persoonslijst);
            debugOutputXmlEnHtml(brpPl, TestCasusOutputStap.STAP_ROND, persoonFilename + "-brp");
        } finally {
            Logging.destroyContext();
        }

        final Lo3Bericht lo3Bericht =
                new Lo3Bericht("persoon", Lo3BerichtenBron.SYNCHRONISATIE, new Timestamp(System.currentTimeMillis()), "ExcelData", true);
        final PersoonslijstPersisteerResultaat result =
                brpDalService.persisteerPersoonslijst(brpPl, brpPl.getActueelAdministratienummer(), false, lo3Bericht);

        verwerkAfnemersindicaties(lo3Afnemersindicaties);

        // BRP Transactie initieren
        final DefaultTransactionDefinition testcaseTransactionDefinition = new DefaultTransactionDefinition();
        testcaseTransactionDefinition.setName("Testcase");
        testcaseTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        final TransactionStatus testcaseTransaction = transactionManagerMaster.getTransaction(testcaseTransactionDefinition);
        try {
            // Lezen uit database
            final PersoonHisVolledig persoonHisVolledig = blobifierService.leesBlob(result.getPersoon().getId());
            final Set<PersoonAfnemerindicatieHisVolledigImpl> brpAfnemerindicaties =
                    afnemerIndicatieBlobifierService.leesBlob(result.getPersoon().getId());
            for (final PersoonAfnemerindicatieHisVolledigImpl brpAfnemerindicatie : brpAfnemerindicaties) {
                LOG.info("Persoon heeft afnemersindicatie voor partij met code {}", brpAfnemerindicatie.getAfnemer().getWaarde().getCode().getWaarde());
            }

            // Vaste waarden in expressie vastzetten
            if (vasteWaardeVandaag != null) {
                FunctieVandaag.setVandaag(vasteWaardeVandaag);
            }
            if (vasteWaardeSelectieDatum != null) {
                FunctieSelectieDatum.setSelectieDatum(vasteWaardeSelectieDatum);
            }

            // Parse expressie
            LOG.info("Expressie = " + brpExpressie);
            final ParserResultaat parserResultaat = BRPExpressies.parse(brpExpressie);
            final Expressie expressie = parserResultaat.getExpressie();
            if (expressie == null) {
                LOG.info("Expressie foutmelding = " + parserResultaat.getFoutmelding());
                throw new IllegalArgumentException("BRP expressie kan niet geparsed worden: " + parserResultaat.getFoutmelding());
            }
            LOG.info("Expressie parser resultaat = " + expressie);

            // Expressie wordt uitegevoerd op een PersoonView
            final PersoonView persoonView = new PersoonView(persoonHisVolledig);
            final Expressie evaluatieResultaat = uitvoerenBRPexpressie(expressie, persoonView, expressieLogging);

            LOG.info("Expressie evaluatie resultaat = " + evaluatieResultaat);
            return evaluatieResultaat.alsBoolean();

        } catch (final Exception e) {
            LOG.warn("Probleem tijdens uitvoeren expressie", e);
            testcaseTransaction.setRollbackOnly();
            throw e;
        } finally {
            if (testcaseTransaction.isRollbackOnly()) {
                transactionManagerMaster.rollback(testcaseTransaction);
            } else {
                transactionManagerMaster.commit(testcaseTransaction);
            }
        }
    }

    private List<Lo3Stapel<Lo3AfnemersindicatieInhoud>> splitAfnemersindicaties(final List<Lo3CategorieWaarde> lo3Inhoud) {
        final List<Lo3Stapel<Lo3AfnemersindicatieInhoud>> result = new ArrayList<>();
        List<Lo3Categorie<Lo3AfnemersindicatieInhoud>> huidigeStapel = null;

        final Iterator<Lo3CategorieWaarde> inhoudIterator = lo3Inhoud.iterator();
        while (inhoudIterator.hasNext()) {
            final Lo3CategorieWaarde categorie = inhoudIterator.next();
            if (Lo3CategorieEnum.CATEGORIE_14 == categorie.getCategorie() || Lo3CategorieEnum.CATEGORIE_64 == categorie.getCategorie()) {
                if (Lo3CategorieEnum.CATEGORIE_14 == categorie.getCategorie()) {
                    if (huidigeStapel != null) {
                        result.add(new Lo3Stapel<Lo3AfnemersindicatieInhoud>(huidigeStapel));
                    }
                    huidigeStapel = new ArrayList<>();
                }

                final String afnemerindicatieWaarde = categorie.getElement(Lo3ElementEnum.ELEMENT_4010);
                final Integer afnemerindicatie =
                        afnemerindicatieWaarde == null | "".equals(afnemerindicatieWaarde) ? null : Integer.valueOf(afnemerindicatieWaarde);
                LOG.info("Input heeft afnemersindicatie {}", afnemerindicatie);
                final Lo3AfnemersindicatieInhoud inhoud = new Lo3AfnemersindicatieInhoud(afnemerindicatie);
                final Lo3Herkomst herkomst = categorie.getLo3Herkomst();
                final Lo3Datum ingangsdatum = new Lo3Datum(Integer.valueOf(categorie.getElement(Lo3ElementEnum.ELEMENT_8510)));
                final Lo3Historie historie = new Lo3Historie(null, ingangsdatum, ingangsdatum);

                huidigeStapel.add(new Lo3Categorie<Lo3AfnemersindicatieInhoud>(inhoud, null, historie, herkomst));

                inhoudIterator.remove();
            }
        }
        if (huidigeStapel != null) {
            result.add(new Lo3Stapel<Lo3AfnemersindicatieInhoud>(huidigeStapel));
        }

        return result;
    }

    private void verwerkAfnemersindicaties(final Lo3Afnemersindicatie lo3Afnemersindicaties) {
        Logging.initContext();
        try {
            final BrpAfnemersindicaties brpAfnemersindicaties = converteerLo3NaarBrpService.converteerLo3Afnemersindicaties(lo3Afnemersindicaties);

            for (final BrpAfnemersindicatie brpAfnemersindicatie : brpAfnemersindicaties.getAfnemersindicaties()) {
                // Aanmaken partij en abonnement
                aanmakenPartijEnAbonnementInDatabaseVoorAfnemersindicatie(brpAfnemersindicatie.getPartijCode().getWaarde());
            }

            brpDalService.persisteerAfnemersindicaties(brpAfnemersindicaties);
        } catch (final Lo3SyntaxException e) {
            LOG.error("Probleem met converteren en opslaan afnemersindicatie (maken autorisatie)", e);
        } finally {
            Logging.destroyContext();
        }
    }

    private void aanmakenPartijEnAbonnementInDatabaseVoorAfnemersindicatie(final Integer partijCode) throws Lo3SyntaxException {
        final Lo3AutorisatieInhoud inhoud = new Lo3AutorisatieInhoud();
        inhoud.setAfnemersindicatie(partijCode);
        inhoud.setAfnemernaam("Partij " + partijCode);
        inhoud.setMediumSpontaan("N");
        inhoud.setRubrieknummerSpontaan("01.01.10");
        inhoud.setIndicatieGeheimhouding(Lo3IndicatieGeheimCodeEnum.GEEN_BEPERKING.asElement());
        // inhoud.setVersieNr(1);
        inhoud.setDatumIngang(new Lo3Datum(19700101));
        inhoud.setVerstrekkingsbeperking(0);

        final Lo3Historie historie = new Lo3Historie(null, new Lo3Datum(19700101), new Lo3Datum(19700101));
        final Lo3Herkomst herkomst = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_35, -1, -1);

        final Lo3Categorie<Lo3AutorisatieInhoud> categorie = new Lo3Categorie<>(inhoud, null, historie, herkomst);
        final Lo3Autorisatie lo3Autorisatie = new Lo3Autorisatie(new Lo3Stapel<Lo3AutorisatieInhoud>(Arrays.asList(categorie)));

        final BrpAutorisatie brpAutorisatie = converteerLo3NaarBrpService.converteerLo3Autorisatie(lo3Autorisatie);
        brpDalService.persisteerAutorisatie(brpAutorisatie);
    }

    private Expressie uitvoerenBRPexpressie(final Expressie expressie, final PersoonView persoon, final StringBuilder expressieLogging) {
        if (expressie instanceof VariabeleExpressie) {
            LOG.info("Skipping execution of VariabeleExpressie: {}", expressie);
            return null;
        }

        final Expressie resultaat = BRPExpressies.evalueer(expressie, persoon);
        expressieLogging.append(expressie).append(" ---> ").append(resultaat).append("\n");

        if (expressie instanceof AbstractUnaireOperatorExpressie) {
            final AbstractUnaireOperatorExpressie unaireOperatorExpressie = (AbstractUnaireOperatorExpressie) expressie;
            uitvoerenBRPexpressie(unaireOperatorExpressie.getOperand(), persoon, expressieLogging);
        } else if (expressie instanceof AbstractBinaireOperatorExpressie) {
            final AbstractBinaireOperatorExpressie binaireOperatorExpressie = (AbstractBinaireOperatorExpressie) expressie;
            uitvoerenBRPexpressie(binaireOperatorExpressie.getOperandLinks(), persoon, expressieLogging);
            uitvoerenBRPexpressie(binaireOperatorExpressie.getOperandRechts(), persoon, expressieLogging);
        } else if (expressie instanceof FunctieExpressie) {
            final FunctieExpressie functieExpressie = (FunctieExpressie) expressie;
            final List<Expressie> argumenten = (List<Expressie>) ReflectionTestUtils.getField(functieExpressie, "argumenten");

            for (final Expressie argument : argumenten) {
                uitvoerenBRPexpressie(argument, persoon, expressieLogging);
            }
        }

        return resultaat;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    private void initierenDatabase() {
        initierenBrpDatabase(migratieDataSource);

        // clear caches
        final Session s = (Session) migratieEntityManager.getDelegate();
        final SessionFactory sf = s.getSessionFactory();
        sf.getCache().evictCollectionRegions();
        sf.getCache().evictDefaultQueryRegion();
        sf.getCache().evictEntityRegions();
        sf.getCache().evictNaturalIdRegions();
        sf.getCache().evictQueryRegions();

    }

    private int countFrom(final Connection connection, final String table) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement("select count(*) from " + table)) {
            try (final ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt(JdbcConstants.COLUMN_1);
            }
        }
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Locator voor MGBA.
     */
    private static class TestServiceLocator extends ServiceLocator {

        @Override
        public DeploymentContext getDeploymentContext() {
            throw new UnsupportedOperationException("getDeploymentContext() not supported");
        }

        @Override
        public SecurityContext getSecurityContext() {
            throw new UnsupportedOperationException("getSecurityContext() not supported");
        }

        @Override
        public Object getService(final String service) {
            switch (service) {
                case "gbavUtils":
                    return new UtilsImpl();
                default:
                    throw new UnsupportedOperationException("getService(): '" + service + "' not supported");

            }
        }

        @Override
        public String getSystemProperty(final String arg0) {
            throw new UnsupportedOperationException("getSystemProperty(" + arg0 + ") not supported");
        }

    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */

    /**
     * Interne bean class om Spring injectie te kunnen gebruiken voor settings.
     */
    private class BeanForMigratieAutowire {

        /**
         * Zet de waarde van lo3 syntax controle.
         *
         * @param injectSyntaxControle
         *            lo3 syntax controle
         */
        @Inject
        public void setLo3SyntaxControle(final Lo3SyntaxControle injectSyntaxControle) {
            syntaxControle = injectSyntaxControle;
        }

        /**
         * Zet de waarde van precondities service.
         *
         * @param injectPreconditieService
         *            precondities service
         */
        @Inject
        public void setPreconditiesService(final PreconditiesService injectPreconditieService) {
            preconditieService = injectPreconditieService;
        }

        /**
         * Zet de waarde van converteer lo3 naar brp service.
         *
         * @param injectConverteerLo3NaarBrpService
         *            converteer lo3 naar brp service
         */
        @Inject
        public void setConverteerLo3NaarBrpService(final ConverteerLo3NaarBrpService injectConverteerLo3NaarBrpService) {
            converteerLo3NaarBrpService = injectConverteerLo3NaarBrpService;
        }

        /**
         * Zet je converteer naar expressie service
         *
         * @param injectConverteerNaarExpressieService
         *            De te zetten service.
         */
        @Inject
        public void setConverteerNaarExpressieService(final ConverteerNaarExpressieService injectConverteerNaarExpressieService) {
            converteerNaarExpressieService = injectConverteerNaarExpressieService;
        }

        /**
         * Zet de waarde van brp dal service.
         *
         * @param injectBrpDalService
         *            brp dal service
         */
        @Inject
        public void setBrpDalService(final BrpDalService injectBrpDalService) {
            brpDalService = injectBrpDalService;
        }

        /**
         * Zet de waarde van data source.
         *
         * @param injectMigratieDataSource
         *            data source
         */
        @Inject
        @Named("syncDalDataSource")
        public void setDataSource(final DataSource injectMigratieDataSource) {
            migratieDataSource = injectMigratieDataSource;
        }

        /**
         * Zet de waarde van entity manager.
         *
         * @param injectMigratieEntityManager
         *            entity manager
         */
        @PersistenceContext
        public void setEntityManager(final EntityManager injectMigratieEntityManager) {
            migratieEntityManager = injectMigratieEntityManager;
        }
    }

    /**
     * Interne bean class om Spring injectie te kunnen gebruiken voor settings.
     */
    private class BeanForBrpLeveringAutowire {

        /**
         * Zet de waarde van blobifier service.
         *
         * @param injectBlobifierService
         *            blobifier service
         */
        @Inject
        public void setBlobifierService(final BlobifierService injectBlobifierService) {
            blobifierService = injectBlobifierService;
        }

        /**
         * Zet de waarde van afnemersindicatie blobifier service.
         *
         * @param injectAfnemerIndicatieBlobifierService
         *            afnemersindicatie blobifier service
         */
        @Inject
        public void setBlobifierService(final AfnemerIndicatieBlobifierService injectAfnemerIndicatieBlobifierService) {
            afnemerIndicatieBlobifierService = injectAfnemerIndicatieBlobifierService;
        }

        /**
         * Zet de waarde van transaction manager master.
         *
         * @param injectTransactionManagerMaster
         *            transaction manager master
         */
        @Inject
        @Named("lezenSchrijvenTransactionManager")
        public void setTransactionManagerMaster(final PlatformTransactionManager injectTransactionManagerMaster) {
            transactionManagerMaster = injectTransactionManagerMaster;
        }

    }

}
