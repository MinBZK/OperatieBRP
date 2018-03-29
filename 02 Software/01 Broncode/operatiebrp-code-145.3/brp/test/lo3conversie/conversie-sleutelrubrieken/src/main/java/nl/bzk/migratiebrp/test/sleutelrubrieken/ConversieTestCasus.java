/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.sleutelrubrieken;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingResultaat;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Lo3BerichtenBron;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutelService;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.logging.LoggingContext;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingAntwoordBericht;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.model.MeldingElement;
import nl.bzk.brp.bijhouding.business.BijhoudingService;
import nl.bzk.brp.bijhouding.business.BijhoudingServiceImpl;
import nl.bzk.brp.domain.expressie.Expressie;
import nl.bzk.brp.domain.expressie.ExpressieException;
import nl.bzk.brp.domain.expressie.functie.Functie;
import nl.bzk.brp.domain.expressie.functie.FunctieFactory;
import nl.bzk.brp.domain.expressie.parser.ExpressieParser;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.expressie.ExpressieService;
import nl.bzk.migratiebrp.bericht.model.brp.factory.BrpBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.brp.impl.RegistreerOverlijdenBijhouding;
import nl.bzk.migratiebrp.bericht.model.lo3.parser.Lo3PersoonslijstParser;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3CategorieEnum;
import nl.bzk.migratiebrp.conversie.model.lo3.herkomst.Lo3Herkomst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.regels.expressie.ConverteerNaarExpressieService;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerLo3NaarBrpService;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.Lo3SyntaxControle;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.PreconditiesService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpPersoonslijstService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.PersoonslijstPersisteerResultaat;
import nl.bzk.migratiebrp.synchronisatie.dal.service.SyncParameters;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.test.common.resultaat.Foutmelding;
import nl.bzk.migratiebrp.test.common.resultaat.TestResultaat;
import nl.bzk.migratiebrp.test.common.resultaat.TestStap;
import nl.bzk.migratiebrp.test.common.resultaat.TestStatus;
import nl.bzk.migratiebrp.test.dal.TestCasus;
import nl.bzk.migratiebrp.test.dal.TestCasusOutputStap;
import nl.bzk.migratiebrp.test.sleutelrubrieken.ConversieTestResultaat.TestBijhoudingResultaat;
import nl.bzk.migratiebrp.test.sleutelrubrieken.brp.FunctieGewijzigd;
import nl.bzk.migratiebrp.util.common.JdbcConstants;
import nl.bzk.migratiebrp.util.excel.ExcelAdapter;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterException;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterImpl;
import nl.bzk.migratiebrp.util.excel.ExcelData;
import org.apache.commons.io.IOUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.context.request.RequestContextListener;

/**
 * Test casus: conversie lo3 naar brp.
 */
public final class ConversieTestCasus extends TestCasus {

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final int MILLIS_IN_SECOND = 1000;

    private static final String SUFFIX_SOORTEN_ADM_HAND = "soorten-admhnd";

    private static final ExcelAdapter EXCEL_ADAPTER = new ExcelAdapterImpl();
    private static final Lo3PersoonslijstParser LO3_PARSER = new Lo3PersoonslijstParser();

    @Inject
    private ConverteerNaarExpressieService converteerNaarExpressieService;

    private Lo3SyntaxControle syntaxControle;
    private PreconditiesService preconditieService;
    private ConverteerLo3NaarBrpService converteerLo3NaarBrpService;
    private BrpPersoonslijstService persoonslijstService;
    private SyncParameters syncParameters;
    private DataSource migratieDataSource;
    private EntityManager migratieEntityManager;

    private PlatformTransactionManager transactionManagerMaster;

    private nl.bzk.brp.service.algemeen.blob.PersoonslijstService persoonsgegevensService;

    private ExpressieService expressieService;

    private EntityManager brpEntityManager;
    private String serializer;

    private ObjectSleutelService objectSleutelService;

    private final File inputFile;
    private final String inputSleutelRubrieken;

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
     * @param inputFile
     *            input (file)
     * @param inputSleutelRubrieken
     *            de te testen sleutelrubrieken
     */
    protected ConversieTestCasus(
        final String thema,
        final String naam,
        final File outputFolder,
        final File expectedFolder,
        final File inputFile,
        final String inputSleutelRubrieken)
    {
        super(thema, naam, outputFolder, expectedFolder);
        this.inputFile = inputFile;
        this.inputSleutelRubrieken = inputSleutelRubrieken;
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

    /**
     * Geef de waarde van bean for brp bijhouding autowire.
     *
     * @return bean for brp bijhouding autowire
     */
    public Object getBeanForBrpBijhoudingAutowire() {
        return new Object() {

            @Inject
            public void setObjectSleutelService(final ObjectSleutelService objectSleutelService) {
                ConversieTestCasus.this.objectSleutelService = objectSleutelService;
            }

        };
    }

    @Override
    public TestResultaat run() {
        final long startInMillis = System.currentTimeMillis();
        final ConversieTestResultaat result = new ConversieTestResultaat(getThema(), getNaam());

        // Override functie gewijzigd voor debug
        @SuppressWarnings("unchecked")
        final FunctieFactory factory = (FunctieFactory) getStaticField(FunctieFactory.class, "INSTANCE");
        final Map<String, Functie> functieMapping = (Map<String, Functie>) ReflectionTestUtils.getField(factory, "keywordMapping");
        functieMapping.put("GEWIJZIGD", new FunctieGewijzigd());

        try {
            Logging.initContext();
            final String htmlInit = debugOutputString(inputSleutelRubrieken, TestCasusOutputStap.STAP_LO3, null);
            result.setInitieren(new TestStap(TestStatus.OK, null, htmlInit, null));
            // Stap 0: Initieren database
            initierenDatabase(result);

            // Setup fake HTTP request
            final ServletContext httpServletContext = new MockServletContext();
            final HttpServletRequest httpRequest = new MockHttpServletRequest();
            final RequestContextListener requestContextListener = new RequestContextListener();
            final ServletRequestEvent servletRequestEvent = new ServletRequestEvent(httpServletContext, httpRequest);
            requestContextListener.requestInitialized(servletRequestEvent);
            try {
                // Stap 2: Converteren sleutelrubrieken
                final String brpAttenderingsCriterium = testLo3NaarBrp(inputSleutelRubrieken, result);

                // Stap 1: Initieren bijhouding
                final Bijhouding bijhouding = initierenBijhouding(result);

                if (bijhouding != null) {
                    // Stap 3: Controle obv uitvoer op een bijhouding
                    for (final Long administratieveHandelingId : bijhouding.getAdministratieveHandelingIds()) {
                        final TestBijhoudingResultaat bijhoudingResult = new TestBijhoudingResultaat();
                        result.getBijhoudingen().add(bijhoudingResult);
                        bijhoudingResult.setIdAdministratieveHandeling(administratieveHandelingId);

                        testUitvoerenAttenderingsCriterium(
                            bijhouding.getPersoonId(),
                            administratieveHandelingId,
                            brpAttenderingsCriterium,
                            bijhoudingResult);
                    }
                }
            } finally {
                requestContextListener.requestDestroyed(servletRequestEvent);
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
    private void initierenDatabase(final ConversieTestResultaat result) {
        initierenBrpDatabase(migratieDataSource);

        // clear caches
        final Session s = (Session) migratieEntityManager.getDelegate();
        final SessionFactory sf = s.getSessionFactory();
        sf.getCache().evictCollectionRegions();
        sf.getCache().evictDefaultQueryRegion();
        sf.getCache().evictEntityRegions();
        sf.getCache().evictNaturalIdRegions();
        sf.getCache().evictQueryRegions();

        try (final Connection connection = migratieDataSource.getConnection()) {
            assertEquals("Niet alle personen zijn verwijderd.", 0, countFrom(connection, "kern.pers"));
            assertEquals("Niet alle IST stapels zijn verwijderd.", 0, countFrom(connection, "ist.stapel"));
            assertEquals("Niet alle onderzoeken zijn verwijderd.", 0, countFrom(connection, "kern.onderzoek"));

        } catch (final SQLException e) {
            LOG.warn("Fout bij controle queries", e);
        } catch (final IllegalStateException e) {
            final Foutmelding fout = new Foutmelding("Fout tijdens initieren (controle).", e);
            LOG.error(fout.getContext(), e);
            final String htmlFout = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_INITIEREN, SUFFIX_EXCEPTION);

            result.setInitieren(new TestStap(TestStatus.NOK, e.getMessage(), htmlFout, null));
        }
    }

    private void assertEquals(final String message, final int expected, final int actual) {
        if (expected != actual) {
            throw new IllegalStateException(message);
        }
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
     * Test conversie lo3 naar brp.
     *
     * @param sleutelRubrieken
     *            lo3
     * @param result
     *            result
     * @return attenderings criterium
     */
    private String testLo3NaarBrp(final String sleutelRubrieken, final ConversieTestResultaat result) {
        LOG.info("Test converteer LO3 naar BRP ...");
        try {
            Logging.initContext();
            final Lo3Herkomst herkomst = new Lo3Herkomst(Lo3CategorieEnum.CATEGORIE_35, -1, -1);
            final String attenderingsCriterium = converteerNaarExpressieService.converteerSleutelRubrieken(sleutelRubrieken, herkomst);
            LOG.info("attenderingscriterium: " + attenderingsCriterium);
            final String htmlAttenderingsCriterium = debugOutputString(attenderingsCriterium, TestCasusOutputStap.STAP_BRP, null);
            // Controleer verwachting
            final String expectedPlatteTekst = leesVerwachteString(TestCasusOutputStap.STAP_BRP, null);
            LOG.info("attenderingscriterium.expected: " + expectedPlatteTekst);
            if (expectedPlatteTekst != null) {
                if (!expectedPlatteTekst.equals(attenderingsCriterium)) {
                    result.setLo3NaarBrp(new TestStap(TestStatus.NOK, "Er zijn inhoudelijke verschillen", htmlAttenderingsCriterium, null));
                } else {
                    result.setLo3NaarBrp(new TestStap(TestStatus.OK, null, htmlAttenderingsCriterium, null));
                }
            } else {
                result.setLo3NaarBrp(new TestStap(TestStatus.GEEN_VERWACHTING, null, htmlAttenderingsCriterium, null));
            }

            return attenderingsCriterium;

        } catch (final Exception e) {
            final Foutmelding fout = new Foutmelding("Fout tijdens converteren LO3 naar BRP.", e);
            final String htmlFout = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_BRP, "exceptie");

            // Lees verwachte expection
            final Foutmelding verwachteFout = leesVerwachteFoutmelding(TestCasusOutputStap.STAP_BRP);
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
        } finally {
            Logging.destroyContext();
        }
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    private Bijhouding initierenBijhouding(final ConversieTestResultaat result) {
        final Bijhouding bijhouding = new Bijhouding();

        try {
            final File inputFolder = inputFile.getParentFile();
            final File persoonFile = new File(inputFolder, "persoon.xls");
            final File deltaFile = new File(inputFolder, "delta.xls");

            if (persoonFile.exists() && persoonFile.isFile() && persoonFile.canRead()) {
                LOG.info("Initiele vulling obv GBA bericht.");
                final PersoonslijstPersisteerResultaat initieleVulling = initierenPersoon(persoonFile, 0, Lo3BerichtenBron.INITIELE_VULLING);
                LOG.info("Initiele vulling van persoon; id={}", initieleVulling.getPersoon().getId());
                LOG.info("Initiele vulling van persoon; administratieve handeling(en)={}", getIds(initieleVulling.getAdministratieveHandelingen()));
                bijhouding.setPersoonId(initieleVulling.getPersoon().getId());

                // Stap 1b: Blobify
                blobify(bijhouding.getPersoonId(), result, "blob-iv");
            } else if (deltaFile.exists() && deltaFile.isFile() && deltaFile.canRead()) {
                LOG.info("Initiele vulling obv GBA bericht (delta).");
                final PersoonslijstPersisteerResultaat initieleVulling = initierenPersoon(deltaFile, 0, Lo3BerichtenBron.INITIELE_VULLING);
                LOG.info("Initiele vulling van persoon; id={}", initieleVulling.getPersoon().getId());
                LOG.info("Initiele vulling van persoon; administratieve handeling(en)={}", getIds(initieleVulling.getAdministratieveHandelingen()));
                bijhouding.setPersoonId(initieleVulling.getPersoon().getId());

                // Stap 1b: Blobify
                blobify(bijhouding.getPersoonId(), result, "blob-iv");
            }

            final File bzmBijhoudingFile = new File(inputFolder, "bzmBijhouding.xml");
            final File lo3BijhoudingFile = new File(inputFolder, "gbaBijhouding.xls");

            if (bzmBijhoudingFile.exists() && bzmBijhoudingFile.isFile() && bzmBijhoudingFile.canRead()) {
                LOG.info("Bijhouding door middel van BZM bericht");
                final Long administratieveHandelingId = initierenBzmBijhouding(bzmBijhoudingFile, result, bijhouding.getPersoonId());
                bijhouding.setAdministratieveHandelingId(administratieveHandelingId);
                // Lees persoon
                final Persoonslijst persoonsgegevens = persoonsgegevensService.getById(bijhouding.getPersoonId());

                // Lees administratieve handeling
                final nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling administratieveHandeling =
                        bepaalAdministratieveHandeling(persoonsgegevens, administratieveHandelingId);
                LOG.info("Bijhouding; administratieve handelingen={}", bijhouding.getAdministratieveHandelingIds());

                controleerSoortenAdministratieveHandeling(administratieveHandeling.getSoort().name(), result);
            } else if (lo3BijhoudingFile.exists() && lo3BijhoudingFile.isFile() && lo3BijhoudingFile.canRead()) {
                LOG.info("Bijhouden door middel van GBA synchronisatie");
                final PersoonslijstPersisteerResultaat gbaBijhouding = initierenPersoon(lo3BijhoudingFile, 0, Lo3BerichtenBron.SYNCHRONISATIE);
                final Persoon bijgehoudenPersoon = gbaBijhouding.getPersoon();
                LOG.info(
                    "Bijhouding; persoon={}, administratieve handeling(en)={}",
                    bijgehoudenPersoon.getId(),
                    getIds(gbaBijhouding.getAdministratieveHandelingen()));
                bijhouding.setPersoonId(bijgehoudenPersoon.getId());
                bijhouding.setAdministratieveHandelingIds(getIds(gbaBijhouding.getAdministratieveHandelingen()));

                controleerSoortenAdministratieveHandeling(getSoorten(gbaBijhouding.getAdministratieveHandelingen()), result);
            } else if (deltaFile.exists() && deltaFile.isFile() && deltaFile.canRead()) {
                LOG.info("Bijhouden door middel van GBA synchronisatie (delta)");
                final PersoonslijstPersisteerResultaat gbaBijhouding = initierenPersoon(deltaFile, 1, Lo3BerichtenBron.SYNCHRONISATIE);
                final Persoon bijgehoudenPersoon = gbaBijhouding.getPersoon();
                LOG.info(
                    "Bijhouding; persoon={}, administratieve handeling(en)={}",
                    bijgehoudenPersoon.getId(),
                    getIds(gbaBijhouding.getAdministratieveHandelingen()));
                bijhouding.setPersoonId(bijgehoudenPersoon.getId());
                bijhouding.setAdministratieveHandelingIds(getIds(gbaBijhouding.getAdministratieveHandelingen()));

                controleerSoortenAdministratieveHandeling(getSoorten(gbaBijhouding.getAdministratieveHandelingen()), result);
            } else {
                result.setBijhouding(new TestStap(TestStatus.GEEN_VERWACHTING));
                return null;
            }

            // Stap 1b: Blobify
            blobify(bijhouding.getPersoonId(), result, "blob");
            result.setBijhouding(new TestStap(TestStatus.OK));

        } catch (final Exception e) {
            final Foutmelding fout = new Foutmelding("Fout tijdens initieren (persoon).", e);
            LOG.error(fout.getContext(), e);
            final String htmlFout = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_ROND, SUFFIX_EXCEPTION);
            result.setBijhouding(new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden (initieren persoon)", htmlFout, null));
            return null;
        }

        return bijhouding;
    }

    private void controleerSoortenAdministratieveHandeling(final String soorten, final ConversieTestResultaat result) {
        final String htmlSoorten = debugOutputString(soorten, TestCasusOutputStap.STAP_ROND, SUFFIX_SOORTEN_ADM_HAND);
        final String expected = leesVerwachteString(TestCasusOutputStap.STAP_ROND, SUFFIX_SOORTEN_ADM_HAND);

        if (expected != null && !"".equals(expected) && !expected.equals(soorten)) {
            result.setBijhouding(new TestStap(TestStatus.NOK, "Er zijn onverwachte soorten administratieve handeling geconstateerd", htmlSoorten, null));
        }
    }

    private String getSoorten(final Set<AdministratieveHandeling> administratieveHandelingen) {
        final SortedSet<String> soorten = new TreeSet<String>();
        for (final AdministratieveHandeling administratieveHandeling : administratieveHandelingen) {
            soorten.add(administratieveHandeling.getSoort().toString());
        }

        final StringBuffer result = new StringBuffer();
        boolean first = true;
        for (final String soort : soorten) {
            if (!first) {
                result.append(",");
            } else {
                first = false;
            }
            result.append(soort);
        }
        return result.toString();
    }

    private List<Long> getIds(final Set<AdministratieveHandeling> administratieveHandelingen) {
        // Sorteer administratieve handelingen op tijdstip registratie
        final SortedMap<Timestamp, AdministratieveHandeling> gesorteerdeHandelingen = new TreeMap<>();
        for (final AdministratieveHandeling administratieveHandeling : administratieveHandelingen) {
            gesorteerdeHandelingen.put(administratieveHandeling.getDatumTijdRegistratie(), administratieveHandeling);
        }

        final List<Long> result = new ArrayList<>();
        for (final AdministratieveHandeling administratieveHandeling : gesorteerdeHandelingen.values()) {
            if (SoortAdministratieveHandeling.GBA_A_NUMMER_WIJZIGING == administratieveHandeling.getSoort()) {
                if (gesorteerdeHandelingen.size() > 1) {
                    // Skip deze handeling als er meer dan 1 is, dan zijn namelijk de wijzigingen gekoppeld aan
                    // de andere administratieve handeling.
                    continue;
                }
            }
            result.add(administratieveHandeling.getId());
        }

        return result;
    }

    private PersoonslijstPersisteerResultaat initierenPersoon(final File persoonFile, final int index, final Lo3BerichtenBron bron)
        throws IOException, ExcelAdapterException, Lo3SyntaxException, OngeldigePersoonslijstException
    {
        if (Lo3BerichtenBron.INITIELE_VULLING.equals(bron)) {
            syncParameters.setInitieleVulling(true);
            LOG.info("Verwerken GBA initiele vulling");
        } else {
            syncParameters.setInitieleVulling(false);
            LOG.info("Verwerken GBA bijhouding");
        }

        LOG.info("Inlezen Excel sheet");
        try (FileInputStream fis = new FileInputStream(persoonFile)) {
            Logging.initContext();
            SynchronisatieLogging.init();
            final List<ExcelData> excelDatas = EXCEL_ADAPTER.leesExcelBestand(fis);

            if (excelDatas.isEmpty()) {
                throw new IllegalArgumentException("Persoon.xls bevat geen persoonsgegevens");
            }

            if (excelDatas.size() < index) {
                throw new IllegalArgumentException("Persoon.xls bevat geen persoonsgegevens op index " + index + " (rij " + ('c' + index) + ")");
            }

            final ExcelData excelData = excelDatas.get(index);
            final List<Lo3CategorieWaarde> lo3Inhoud = excelData.getCategorieLijst();

            // Lo3 syntax controle
            final List<Lo3CategorieWaarde> lo3InhoudNaSyntaxControle;
            try {
                lo3InhoudNaSyntaxControle = syntaxControle.controleer(lo3Inhoud);
            } catch (final OngeldigePersoonslijstException e) {
                debugOutputXmlEnHtml(Logging.getLogging(), TestCasusOutputStap.STAP_ROND, "-syntax");
                throw e;
            }

            // Parse persoonslijst
            final Lo3Persoonslijst lo3Persoonslijst = LO3_PARSER.parse(lo3InhoudNaSyntaxControle);
            debugOutputXmlEnHtml(
                lo3Persoonslijst,
                TestCasusOutputStap.STAP_ROND,
                (Lo3BerichtenBron.INITIELE_VULLING.equals(bron) ? "iv" : "bijh") + "-lo3");

            // Controleer precondities
            final Lo3Persoonslijst schoneLo3Persoonslijst;


            try {
                schoneLo3Persoonslijst = preconditieService.verwerk(lo3Persoonslijst);
            } catch (final OngeldigePersoonslijstException e) {
                debugOutputXmlEnHtml(Logging.getLogging(), TestCasusOutputStap.STAP_ROND, "-precondities");
                throw e;
            }

            // LOG.info("Lo3 persoonslijst: {}", lo3Pl);

            final BrpPersoonslijst brpPl = converteerLo3NaarBrpService.converteerLo3Persoonslijst(schoneLo3Persoonslijst);
            debugOutputXmlEnHtml(brpPl, TestCasusOutputStap.STAP_ROND, (Lo3BerichtenBron.INITIELE_VULLING.equals(bron) ? "iv" : "bijh") + "-brp");
            // LOG.info("BRP persoonslijst: {}", brpPl);

            final Lo3Bericht lo3Bericht = new Lo3Bericht("persoon", bron, new Timestamp(System.currentTimeMillis()), "ExcelData", true);
            LOG.info("Logging: {}", lo3Bericht);

            final DefaultTransactionDefinition persisteerTransactionDefinition = new DefaultTransactionDefinition();
            persisteerTransactionDefinition.setName("Testcase-persisteerPersoonslijst");
            persisteerTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
            final TransactionStatus persisteerTransaction = transactionManagerMaster.getTransaction(persisteerTransactionDefinition);

            PersoonslijstPersisteerResultaat result = null;
            try {
                result = persoonslijstService.persisteerPersoonslijst(brpPl, lo3Bericht);
            } catch (Exception e) {
                persisteerTransaction.setRollbackOnly();
            } finally {
                if (persisteerTransaction.isRollbackOnly()) {
                    LOG.info("Rollback persisteer transactie");
                    transactionManagerMaster.rollback(persisteerTransaction);
                } else {
                    LOG.info("Commit persisteer transactie");
                    transactionManagerMaster.commit(persisteerTransaction);
                }
            }

            LOG.info("Result: {}", result);
            if (result != null) {
                LOG.info("Persoon: {}", result.getPersoon());
                LOG.info("Administratieve handeling(en): {}", result.getAdministratieveHandelingen());

                LOG.info("Persoon.id: {}", result.getPersoon().getId());
                // final PersoonHisVolledig persoonHisVolledig = blobifierService.leesBlob(result.getKey().getId());
                // LOG.info("PersoonHisVolledig: {}", persoonHisVolledig);
            }
            return result;
        } finally {
            LoggingContext.reset();
            Logging.destroyContext();
        }
    }

    private Long initierenBzmBijhouding(final File bijhoudingFile, final ConversieTestResultaat result, final Long persoonId) {
        try (FileInputStream fis = new FileInputStream(bijhoudingFile)) {

            final BijhoudingVerzoekBericht bericht = (BijhoudingVerzoekBericht) leesXmlString(fis, RegistreerOverlijdenBijhouding.class);

            final BijhoudingService bijhoudingservice = new BijhoudingServiceImpl(null,
                    null, null, null, null, null, null, null);
            final BijhoudingAntwoordBericht bijhoudingResultaat = bijhoudingservice.verwerkBrpBericht(bericht);

            // final BijhoudingBerichtContext context =
            // new BijhoudingBerichtContext(
            // new BerichtenIds(1L, 1L),
            // new Partij(null, null, new PartijCodeAttribuut(partijCode), null, null, null, null, null, null),
            // null,
            // bericht.getCommunicatieIdMap());
            //
            // final BijhoudingResultaat bijhoudingResultaat = bijhoudingsBerichtVerwerker.verwerkBrpBericht(bericht,
            // context);
            LOG.info("Bijhouding resultaat: {}", bijhoudingResultaat);

            if (bijhoudingResultaat.getMeldingen() != null && !bijhoudingResultaat.getMeldingen().isEmpty()) {
                for (final MeldingElement melding : bijhoudingResultaat.getMeldingen()) {
                    LOG.info("Melding: {} - {}", melding.getSoortNaam(), melding.getMelding());
                }
            }

            if (BijhoudingResultaat.DEELS_UITGESTELD.getNaam().equals(bijhoudingResultaat.getResultaat().getVerwerking().getWaarde())) {
                throw new IllegalStateException("Bijhouding mislukt");
            }
            LOG.info("Administratieve handeling: {}", bijhoudingResultaat.getAdministratieveHandelingID());
            return bijhoudingResultaat.getAdministratieveHandelingID();

            // return null;
        } catch (final IOException e) {
            final Foutmelding fout = new Foutmelding("Fout tijdens initieren (persoonMutatie).", e);
            final String htmlFout = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_ROND, SUFFIX_EXCEPTION);
            result.setBijhouding(new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden (initieren persoonMutatie)", htmlFout, null));
        }
        return null;
    }

    // private void vervangObjectSleutel(final Bericht bericht, final String objeSleutel) {
    // final CommunicatieIdMap idMap = bericht.getCommunicatieIdMap();
    // for (final List<BerichtIdentificeerbaar> idList : idMap.values()) {
    // if (idList != null && idList.get(0) != null && idList.get(0) instanceof PersoonBericht) {
    // final PersoonBericht pb = (PersoonBericht) idList.get(0);
    // pb.setObjectSleutel(objeSleutel);
    // }
    // }
    // }
    private static Object leesXmlString(final InputStream xmlInputStream, final Class<?> clazz) {
        try {
            return BrpBerichtFactory.SINGLETON.getBericht(IOUtils.toString(xmlInputStream));
        } catch (final IOException e) {
            LOG.info("Probleem bij lees xml string. Returning null.", e);
        }
        return null;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    private void blobify(final Long persoonId, final ConversieTestResultaat result, final String suffix) {
        if (persoonId == null) {
            return;
        }

        final DefaultTransactionDefinition blobifyTransactionDefinition = new DefaultTransactionDefinition();
        blobifyTransactionDefinition.setName("Testcase-blobify");
        blobifyTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        final TransactionStatus blobifyTransaction = transactionManagerMaster.getTransaction(blobifyTransactionDefinition);

        try {
            persoonsgegevensService.getById(persoonId);
        } catch (final Exception e) {
            LOG.warn("Fout tijdens blobificeren", e);
            blobifyTransaction.setRollbackOnly();
            final Foutmelding fout = new Foutmelding("Fout tijdens initieren (blobify).", e);
            final String htmlFout = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_ROND, SUFFIX_EXCEPTION);

            result.setBijhouding(new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden (blobify)", htmlFout, null));
        } finally {
            if (blobifyTransaction.isRollbackOnly()) {
                LOG.info("Rollback blobify transactie");
                transactionManagerMaster.rollback(blobifyTransaction);
            } else {
                LOG.info("Commit blobify transactie");
                transactionManagerMaster.commit(blobifyTransaction);
            }
        }

        // Output blob (buiten transactie van blobifier, anders zit het niet in de db)
        if ("nl.bzk.brp.serialisatie.persoon.PersoonHisVolledigStringSerializer".equals(serializer)) {
            LOG.info("Debug output BLOB");
            try {
                final Query blobQuery =
                        migratieEntityManager.createNativeQuery("select pershistorievollediggegevens from kern.perscache where pers = :persoonId");
                blobQuery.setParameter("persoonId", persoonId);
                final Object blob = blobQuery.getSingleResult();

                debugOutputString(toString(blob), TestCasusOutputStap.STAP_ROND, suffix);
            } catch (
                NoResultException
                | NonUniqueResultException e)
            {
                LOG.error("Fout bij output blob", e);
            }
        }
    }

    private String toString(final Object blob) {
        String resultaat;

        if (blob instanceof Blob) {
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                try (InputStream is = ((Blob) blob).getBinaryStream();) {
                    IOUtils.copy(is, baos);
                }
                resultaat = baos.toString(Charset.defaultCharset().toString());
            } catch (final
                IOException
                | SQLException e)
            {
                LOG.info("Probleem bij blob to string. Returning null.", e);
                resultaat = null;
            }
        } else if (blob instanceof byte[]) {
            resultaat = new String((byte[]) blob, Charset.forName(Charset.defaultCharset().toString()));
        } else {
            LOG.warn("Blob is van onverwachte class: " + blob.getClass());
            resultaat = null;
        }

        return resultaat;
    }

    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    /* ************************************************************************************************************* */
    private void testUitvoerenAttenderingsCriterium(
        final Long persoonId,
        final Long administratieveHandelingId,
        final String brpAttenderingsCriterium,
        final TestBijhoudingResultaat result)
    {
        if (brpAttenderingsCriterium == null || "".equals(brpAttenderingsCriterium)) {
            return;
        }

        // BRP Transactie initieren
        final DefaultTransactionDefinition testcaseTransactionDefinition = new DefaultTransactionDefinition();
        testcaseTransactionDefinition.setName("Testcase");
        testcaseTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        final TransactionStatus testcaseTransaction = transactionManagerMaster.getTransaction(testcaseTransactionDefinition);
        try {
            // Lees persoon
            final Persoonslijst persoonsgegevens = persoonsgegevensService.getById(persoonId);

            // Lees administratieve handeling
            final nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling administratieveHandeling =
                    bepaalAdministratieveHandeling(persoonsgegevens, administratieveHandelingId);
            if (administratieveHandeling == null) {
                throw new IllegalArgumentException("Administratieve handeling kon niet gevonden worden in persoonsgegevens.");
            }
            result.setSoortAdministratieveHandeling(administratieveHandeling.getSoort().toString());

            // Parse expressie
            LOG.info("Expressie = {}", brpAttenderingsCriterium);
            final Expressie expressie;
            try {
                expressie = ExpressieParser.parse(brpAttenderingsCriterium);
            } catch (ExpressieException e) {
                LOG.info("Expressie foutmelding = {}", e.getMessage());
                throw new IllegalArgumentException("BRP expressie kan niet geparsed worden: " + e.getMessage(), e);
            }
            LOG.info("Expressie parser resultaat = {}", expressie);

            final StringBuilder logger = new StringBuilder();
            FunctieGewijzigd.setLogger(logger);
            final boolean resultaat;
            try {
                // Uitvoeren expressie
                resultaat = expressieService.bepaalPersoonGewijzigd(persoonsgegevens.beeldVan().vorigeHandeling(), persoonsgegevens, expressie);
            } finally {
                FunctieGewijzigd.setLogger(null);
            }
            LOG.info("Expressie resultaat = {}", resultaat);

            LOG.info("Gewijzigd logging: {}", logger.toString());
            final String htmlGewijzigd = debugOutputString(logger.toString(), TestCasusOutputStap.STAP_ROND, "gewijzigd");
            result.setUitvoeren(new TestStap(resultaat ? TestStatus.OK : TestStatus.NOK, Boolean.toString(resultaat), htmlGewijzigd, null));

        } catch (final Exception e) {
            LOG.warn("Probleem tijdens uitvoeren expressie", e);
            final Foutmelding fout = new Foutmelding("Fout tijdens uitvoeren BRP expressie.", e);
            final String htmlFout = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_ROND, "-brp-exceptie");
            result.setUitvoeren(new TestStap(TestStatus.EXCEPTIE, "Exceptie", htmlFout, null));

            testcaseTransaction.setRollbackOnly();
        } finally {
            if (testcaseTransaction.isRollbackOnly()) {
                transactionManagerMaster.rollback(testcaseTransaction);
            } else {
                transactionManagerMaster.commit(testcaseTransaction);
            }
        }
    }

    private nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling bepaalAdministratieveHandeling(
        final Persoonslijst persoonsgegevens,
        final Long administratieveHandelingId)
    {
        for (final nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling administratieveHandeling : persoonsgegevens.getAdministratieveHandelingen()) {
            if (administratieveHandeling.getId().equals(administratieveHandelingId)) {
                return administratieveHandeling;
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
     * Lokale data class voor de test.
     */
    private static final class Bijhouding {

        private Long persoonId;
        private List<Long> administratieveHandelingIds;

        /**
         * Geef de waarde van persoon id.
         *
         * @return persoon id
         */
        public Long getPersoonId() {
            return persoonId;
        }

        /**
         * Zet de waarde van persoon id.
         *
         * @param persoonId
         *            persoon id
         */
        public void setPersoonId(final Long persoonId) {
            this.persoonId = persoonId;
        }

        /**
         * Geef de waarde van administratieve handeling ids.
         *
         * @return administratieve handeling ids
         */
        public List<Long> getAdministratieveHandelingIds() {
            return administratieveHandelingIds;
        }

        /**
         * Zet de waarde van administratieve handeling id.
         *
         * @param administratieveHandelingId
         *            administratieve handeling id
         */
        public void setAdministratieveHandelingId(final Long administratieveHandelingId) {
            administratieveHandelingIds = Collections.singletonList(administratieveHandelingId);
        }

        /**
         * Zet de waarde van administratieve handeling ids.
         *
         * @param administratieveHandelingIds
         *            administratieve handeling ids
         */
        public void setAdministratieveHandelingIds(final List<Long> administratieveHandelingIds) {
            this.administratieveHandelingIds = administratieveHandelingIds;
        }
    }

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
         * Zet de waarde van brp persoonslijst service.
         *
         * @param injectBrpPersoonslijstService
         *            brp persoonslijst service
         */
        @Inject
        public void setBrpPersoonslijstService(final BrpPersoonslijstService injectBrpPersoonslijstService) {
            persoonslijstService = injectBrpPersoonslijstService;
        }

        /**
         * Zet de waarde van sync parameters.
         *
         * @param injectSyncParameters
         *            sync parameters
         */
        @Inject
        public void setSyncParameters(final SyncParameters injectSyncParameters) {
            syncParameters = injectSyncParameters;
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
         * Zet de waarde van persoonsgegevens service.
         *
         * @param injectPersoonsgegevensService
         *            persoonsgegevens service
         */
        @Inject
        public void setPersoonsgegevensService(final nl.bzk.brp.service.algemeen.blob.PersoonslijstService injectPersoonsgegevensService) {
            persoonsgegevensService = injectPersoonsgegevensService;
        }

        /**
         * Zet de waarde van transaction manager master.
         *
         * @param injectTransactionManagerMaster
         *            transaction manager master
         */
        @Inject
        @Named("masterTransactionManager")
        public void setTransactionManagerMaster(final PlatformTransactionManager injectTransactionManagerMaster) {
            transactionManagerMaster = injectTransactionManagerMaster;
        }

        /**
         * Zet de waarde van sleutelrubriek gewijzigd bepaler.
         *
         * @param injectExpressieService
         *            sleutelrubriek gewijzigd bepaler
         */
        @Inject
        public void setSleutelrubriekGewijzigdBepaler(final ExpressieService injectExpressieService) {
            expressieService = injectExpressieService;
        }

        /**
         * Zet de waarde van entity manager.
         *
         * @param injectBrpEntityManager
         *            entity manager
         */
        @PersistenceContext(unitName = "nl.bzk.brp.master")
        public void setEntityManager(final EntityManager injectBrpEntityManager) {
            brpEntityManager = injectBrpEntityManager;
        }

        /**
         * Zet de waarde van serializer.
         *
         * @param injectSerializer
         *            serializer
         */
        @Value("${serialisatie.persoonHisVolledig.klassenaam:}")
        public void setSerializer(final String injectSerializer) {
            serializer = injectSerializer;
        }
    }

}
