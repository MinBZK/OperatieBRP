/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.levering.mutatiebericht;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
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
import javax.persistence.TypedQuery;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import nl.bzk.brp.bijhouding.business.service.BijhoudingsBerichtVerwerker;
import nl.bzk.brp.blobifier.service.BlobifierService;
import nl.bzk.brp.bijhouding.business.dto.bijhouding.BijhoudingResultaat;
import nl.bzk.brp.bijhouding.business.stappen.context.BijhoudingBerichtContext;
import nl.bzk.brp.gba.dataaccess.IstTabelRepository;
import nl.bzk.brp.gba.dataaccess.Lo3FilterRubriekRepository;
import nl.bzk.brp.levering.lo3.bericht.BerichtFactory;
import nl.bzk.brp.levering.lo3.bericht.BerichtImpl;
import nl.bzk.brp.levering.lo3.conversie.ConversieCache;
import nl.bzk.brp.levering.lo3.mapper.PersoonslijstMapper;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.PartijCodeAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.EffectAfnemerindicaties;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.CategorieAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.basis.BerichtIdentificeerbaar;
import nl.bzk.brp.model.basis.CommunicatieIdMap;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;
import nl.bzk.brp.model.bericht.kern.PersoonBericht;
import nl.bzk.brp.model.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.model.bijhouding.RegistreerOverlijdenBericht;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.levering.SynchronisatieBericht;
import nl.bzk.brp.model.logisch.ist.Stapel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.webservice.business.service.ObjectSleutelService;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Header;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.factory.Lo3BerichtFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.parser.Lo3PersoonslijstParser;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAutorisatie;
import nl.bzk.migratiebrp.conversie.model.brp.util.BrpVergelijker;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Autorisatie;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.regels.proces.ConversieHook;
import nl.bzk.migratiebrp.conversie.regels.proces.ConversieStap;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerBrpNaarLo3Service;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerLo3NaarBrpService;
import nl.bzk.migratiebrp.conversie.regels.proces.impl.ConverteerLo3NaarBrpServiceImpl;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.Lo3SyntaxControle;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.PreconditiesService;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.ToegangLeveringsAutorisatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.AdministratieveHandeling;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3Bericht;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Lo3BerichtenBron;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpDalService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.PersoonslijstPersisteerResultaat;
import nl.bzk.migratiebrp.synchronisatie.dal.service.SyncParameters;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.test.common.autorisatie.AutorisatieReader;
import nl.bzk.migratiebrp.test.common.autorisatie.CsvAutorisatieReader;
import nl.bzk.migratiebrp.test.common.resultaat.Foutmelding;
import nl.bzk.migratiebrp.test.common.resultaat.TestResultaat;
import nl.bzk.migratiebrp.test.common.resultaat.TestStap;
import nl.bzk.migratiebrp.test.common.resultaat.TestStatus;
import nl.bzk.migratiebrp.test.dal.TestCasus;
import nl.bzk.migratiebrp.test.dal.TestCasusOutputStap;
import nl.bzk.migratiebrp.test.levering.mutatiebericht.LeveringMutatieberichtTestResultaat.TestLeveringBerichtResultaat;
import nl.bzk.migratiebrp.test.levering.mutatiebericht.LeveringMutatieberichtTestResultaat.TestLeveringResultaat;
import nl.bzk.migratiebrp.util.common.JdbcConstants;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.common.logging.LoggingContext;
import nl.bzk.migratiebrp.util.excel.ExcelAdapter;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterException;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterImpl;
import nl.bzk.migratiebrp.util.excel.ExcelData;
import org.apache.poi.util.IOUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;
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
public final class LeveringMutatieberichtTestCasus extends TestCasus {

    private static final String IDENTIFIER_BLOB_INITIELE_VULLING = "blob-iv";

    private static final String DASH_SEPERATOR = "-";

    private static final String IDENTIFIER_BIJHOUDING = "bijh";

    private static final String IDENTIFIER_INITIELE_VULLING = "iv";

    private static final String COMMA_SEPERATOR = ",";

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final ExcelAdapter           EXCEL_ADAPTER      = new ExcelAdapterImpl();
    private static final Lo3PersoonslijstParser LO3_PARSER         = new Lo3PersoonslijstParser();
    private static final AutorisatieReader      AUTORISATIE_READER = new CsvAutorisatieReader();

    private static final Long   PLAATSING_BERICHT_ADM_HAND_ID = Long.MIN_VALUE;
    private static final String PLAATSING_SOORT_ADM_HAND      = "PLAATSEN";

    private static final String SUFFIX_SOORTEN_ADM_HAND = "soorten-admhnd";

    private static final int MILLIS_IN_SECOND = 1000;

    private static final String ER_ZIJN_INHOUDELIJKE_VERSCHILLEN_MAPPING = "Er zijn inhoudelijke verschillen (mapping)";
    private static final String ER_IS_EEN_EXCEPTIE_OPGETREDEN            = "Er is een exceptie opgetreden";
    private static final String ISO_8859_1                               = "ISO-8859-1";

    private Lo3SyntaxControle           syntaxControle;
    private PreconditiesService         preconditieService;
    private ConverteerLo3NaarBrpService converteerLo3NaarBrpService;
    private ConverteerBrpNaarLo3Service converteerBrpNaarLo3Service;
    private BrpDalService               brpDalService;
    private SyncParameters              syncParameters;
    private DataSource                  migratieDataSource;
    private EntityManager               migratieEntityManager;

    private final File inputFolder;

    private PlatformTransactionManager transactionManagerMaster;
    private BlobifierService           blobifierService;
    private IstTabelRepository         istTabelRepository;
    private PersoonslijstMapper        persoonslijstMapper;

    private Lo3FilterRubriekRepository lo3FilterRubriekRepository;
    private EntityManager              brpEntityManager;
    private String                     serializer;

    private BerichtFactory berichtFactory;

    private BijhoudingsBerichtVerwerker bijhoudingsBerichtVerwerker;
    private ObjectSleutelService        objectSleutelService;

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
     * @param inputFolder
     *            input folder
     */
    protected LeveringMutatieberichtTestCasus(
        final String thema,
        final String naam,
        final File outputFolder,
        final File expectedFolder,
        final File inputFolder)
    {
        super(thema, naam, outputFolder, expectedFolder);
        this.inputFolder = inputFolder;
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
            public void setBijhoudingsBerichtVerwerker(final BijhoudingsBerichtVerwerker bijhoudingsBerichtVerwerker) {
                LeveringMutatieberichtTestCasus.this.bijhoudingsBerichtVerwerker = bijhoudingsBerichtVerwerker;
            }

            @Inject
            public void setObjectSleutelService(final ObjectSleutelService objectSleutelService) {
                LeveringMutatieberichtTestCasus.this.objectSleutelService = objectSleutelService;
            }
        };
    }

    @Override
    public TestResultaat run() {
        final long startInMillis = System.currentTimeMillis();
        final LeveringMutatieberichtTestResultaat result = new LeveringMutatieberichtTestResultaat(getThema(), getNaam());

        // Stap 1a: Brondata klaarzetten
        result.setInitieren(new TestStap(TestStatus.OK, null, null, null));
        initieren(result);

        // Setup fake HTTP request
        final ServletContext httpServletContext = new MockServletContext();
        final HttpServletRequest httpRequest = new MockHttpServletRequest();
        final RequestContextListener requestContextListener = new RequestContextListener();
        final ServletRequestEvent servletRequestEvent = new ServletRequestEvent(httpServletContext, httpRequest);
        requestContextListener.requestInitialized(servletRequestEvent);
        try {
            final Integer abonnementId = initierenAutorisatie(result);
            LOG.info("Initieren testcase: abonnement.id=" + abonnementId);
            final Bijhouding bijhouding = initierenBijhouding(result);
            final Integer persoonId = bijhouding.getPersoonId();
            LOG.info("Initieren testcase: persoon.id=" + persoonId);

            final List<Long> administratieveHandelingIds = bijhouding.getAdministratieveHandelingIds();
            LOG.info("Initieren testcase: administratievehandeling ids=" + administratieveHandelingIds);

            if (administratieveHandelingIds != null) {
                for (final Long administratieveHandelingId : administratieveHandelingIds) {

                    // Stap 1c: BRP Transactie initieren
                    final DefaultTransactionDefinition testcaseTransactionDefinition = new DefaultTransactionDefinition();
                    testcaseTransactionDefinition.setName("Testcase");
                    testcaseTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                    final TransactionStatus testcaseTransaction = transactionManagerMaster.getTransaction(testcaseTransactionDefinition);
                    try {
                        // LeveringResultaat
                        final TestLeveringResultaat leveringResult = new TestLeveringResultaat();
                        leveringResult.setIdAdministratieveHandeling(administratieveHandelingId);

                        result.getLeveringen().add(leveringResult);

                        // Stap 2d: Lezen administratieve handeling uit BRP Database
                        final AdministratieveHandelingModel administratieveHandeling =
                            leesAdministratieveHandeling(administratieveHandelingId, leveringResult);
                        LOG.info("Lees administratieve handeling: id={} -> {}", administratieveHandelingId, administratieveHandeling);

                        // Stap 2a: Lezen persoon uit BRP Database en mapping
                        final PersoonHisVolledig persoon = testLeesPersoon(persoonId, leveringResult);
                        LOG.info("Lees persoon: id={} -> {}", persoonId, persoon);
                        if (persoon == null) {
                            continue;
                        }

                        // Stap 2b: Lezen abonnement uit BRP Database
                        final Leveringinformatie leveringAutorisatie = leesAbonnementCacheElement(abonnementId, leveringResult);
                        LOG.info("Lees abonnement: id={} -> {}", abonnementId, leveringAutorisatie);
                        if (leveringAutorisatie == null) {
                            continue;
                        }

                        // Stap 2c: TODO bepalen hoe die populatie map gebruikt moet worden
                        final Map<Integer, Populatie> populatieMap = new HashMap<>();

                        // Stap 2e: Bepalen bericht
                        final List<BerichtImpl> berichten =
                            testBepalenBericht(persoon, leveringAutorisatie, populatieMap, administratieveHandeling, leveringResult);
                        LOG.info("Berichten: {}", berichten.size());

                        if (berichten.isEmpty()) {
                            final TestLeveringBerichtResultaat berichtResult = new TestLeveringBerichtResultaat();
                            berichtResult.setSoortBericht("---");
                            leveringResult.getBerichten().add(berichtResult);
                        } else {
                            for (final BerichtImpl bericht : berichten) {
                                final TestLeveringBerichtResultaat berichtResult = new TestLeveringBerichtResultaat();
                                berichtResult.setSoortBericht(bericht.geefSoortBericht());
                                leveringResult.getBerichten().add(berichtResult);

                                // Stap 3: Conversie
                                testConversie(bericht, leveringResult, berichtResult);

                                // Stap 4: Filter
                                final boolean uitvoerenLevering = testFilter(leveringAutorisatie, bericht, leveringResult, berichtResult);

                                // Stap 5: Maak bericht
                                if (uitvoerenLevering) {
                                    testBericht(bericht, leveringResult, berichtResult);
                                }
                            }
                        }
                    } catch (final Exception e) {
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
            }
        } finally {
            requestContextListener.requestDestroyed(servletRequestEvent);
        }

        final long endInMillis = System.currentTimeMillis();
        final float duration = (endInMillis - startInMillis) / (float) MILLIS_IN_SECOND;

        LOG.info("Testcase {} took {} seconds", getNaam(), duration);

        return result;
    }

    private String toString(final Object blob) {
        String resultaat;

        if (blob instanceof Blob) {
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                try (InputStream is = ((Blob) blob).getBinaryStream();) {
                    IOUtils.copy(is, baos);
                }
                resultaat = baos.toString(ISO_8859_1);
            } catch (final
                IOException
                | SQLException e)
            {
                LOG.info("Probleem bij blob to string. Returning null.", e);
                resultaat = null;
            }
        } else if (blob instanceof byte[]) {
            resultaat = new String((byte[]) blob, Charset.forName(ISO_8859_1));
        } else {
            LOG.warn("Blob is van onverwachte class: " + blob.getClass());
            resultaat = null;
        }

        return resultaat;
    }

    private void initieren(final LeveringMutatieberichtTestResultaat result) {
        super.initierenBrpDatabase(migratieDataSource);

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

    private Bijhouding initierenBijhouding(final LeveringMutatieberichtTestResultaat result) {
        final Bijhouding bijhouding = new Bijhouding();

        try {
            final File persoonFile = new File(inputFolder, "persoon.xls");
            final File deltaFile = new File(inputFolder, "delta.xls");

            if (persoonFile.exists() && persoonFile.isFile() && persoonFile.canRead()) {
                LOG.info("Initiele vulling obv GBA bericht.");
                final PersoonslijstPersisteerResultaat initieleVulling = initierenPersoon(persoonFile, 0, Lo3BerichtenBron.INITIELE_VULLING);
                LOG.info("Initiele vulling van persoon; id={} ", initieleVulling.getPersoon().getId());
                LOG.info("Initiele vulling van persoon; administratieve handeling(en)={} ", getIds(initieleVulling.getAdministratieveHandelingen()));
                bijhouding.setPersoonId(initieleVulling.getPersoon().getId());

                // Stap 1b: Blobify
                blobify(bijhouding.getPersoonId(), result, IDENTIFIER_BLOB_INITIELE_VULLING);
            } else if (deltaFile.exists() && deltaFile.isFile() && deltaFile.canRead()) {
                LOG.info("Initiele vulling obv GBA bericht (delta).");
                final PersoonslijstPersisteerResultaat initieleVulling = initierenPersoon(deltaFile, 0, Lo3BerichtenBron.INITIELE_VULLING);
                LOG.info("Initiele vulling van persoon; id={}", initieleVulling.getPersoon().getId());
                LOG.info("Initiele vulling van persoon; administratieve handeling(en)={}", getIds(initieleVulling.getAdministratieveHandelingen()));
                bijhouding.setPersoonId(initieleVulling.getPersoon().getId());

                // Stap 1b: Blobify
                blobify(bijhouding.getPersoonId(), result, IDENTIFIER_BLOB_INITIELE_VULLING);
            }

            if (bijhouding.getPersoonId() != null) {
                rondverteer(bijhouding.getPersoonId(), "-iv");
            }

            final File bzmBijhoudingFile = new File(inputFolder, "bzmBijhouding.xml");
            final File lo3BijhoudingFile = new File(inputFolder, "gbaBijhouding.xls");

            if (bzmBijhoudingFile.exists() && bzmBijhoudingFile.isFile() && bzmBijhoudingFile.canRead()) {
                LOG.info("Bijhouding door middel van BZM bericht");
                final AdministratieveHandelingModel administratieveHandeling =
                        initierenBzmBijhouding(bzmBijhoudingFile, result, bijhouding.getPersoonId());
                bijhouding.setAdministratieveHandelingId(administratieveHandeling.getID());
                LOG.info("Bijhouding; administratieve handelingen={}", bijhouding.getAdministratieveHandelingIds());

                controleerSoortenAdministratieveHandeling(administratieveHandeling.getSoort().getWaarde().toString(), result);
            } else if (lo3BijhoudingFile.exists() && lo3BijhoudingFile.isFile() && lo3BijhoudingFile.canRead()) {
                LOG.info("Bijhouden door middel van GBA synchronisatie");
                final PersoonslijstPersisteerResultaat gbaBijhouding = initierenPersoon(lo3BijhoudingFile, 0, Lo3BerichtenBron.SYNCHRONISATIE);
                final Persoon bijgehoudenPersoon = gbaBijhouding.getPersoon();
                LOG.info(
                    "Bijhouding; persoon={}, administratieve handeling(en)={} ",
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
                LOG.info("Simuleer maken bericht voor plaatsen afnemersindicatie");
                bijhouding.setAdministratieveHandelingId(PLAATSING_BERICHT_ADM_HAND_ID);
                controleerSoortenAdministratieveHandeling(PLAATSING_SOORT_ADM_HAND, result);
            }

            if (bijhouding.getPersoonId() != null) {
                rondverteer(bijhouding.getPersoonId(), "");
            }

            // Stap 1b: Blobify
            blobify(bijhouding.getPersoonId(), result, "blob");

        } catch (final Exception e) {
            final Foutmelding fout = new Foutmelding("Fout tijdens initieren (persoon).", e);
            LOG.error(fout.getContext(), e);
            final String htmlFout = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_INITIEREN, SUFFIX_EXCEPTION);
            result.setInitieren(new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden (initieren persoon)", htmlFout, null));
        }

        return bijhouding;
    }

    private void rondverteer(final Integer persoonId, final String suffix) {
        LOG.info("Rondverteer");
        try {
            final BrpPersoonslijst brpPersoonslijst = brpDalService.bevraagPersoonslijstOpTechnischeSleutel(persoonId.longValue());
            final Lo3Persoonslijst lo3Persoonslijst = converteerBrpNaarLo3Service.converteerBrpPersoonslijst(brpPersoonslijst);
            debugOutputXmlEnHtml(lo3Persoonslijst, TestCasusOutputStap.STAP_ROND, suffix);
        } catch (final Exception e) {
            final Foutmelding fout = new Foutmelding("Fout tijdens rondverteren (persoon).", e);
            LOG.error(fout.getContext(), e);
            debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_ROND, suffix + SUFFIX_EXCEPTION);
        }
    }

    private void controleerSoortenAdministratieveHandeling(final String soorten, final LeveringMutatieberichtTestResultaat result) {
        final String htmlSoorten = debugOutputString(soorten, TestCasusOutputStap.STAP_INITIEREN, SUFFIX_SOORTEN_ADM_HAND);
        final String expected = leesVerwachteString(TestCasusOutputStap.STAP_INITIEREN, SUFFIX_SOORTEN_ADM_HAND);

        if (expected != null && !"".equals(expected) && !expected.equals(soorten)) {
            result.setInitieren(new TestStap(TestStatus.NOK, "Er zijn onverwachte soorten administratieve handeling geconstateerd", htmlSoorten, null));
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
                result.append(COMMA_SEPERATOR);
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
                debugOutputXmlEnHtml(
                    Logging.getLogging(),
                    TestCasusOutputStap.STAP_INITIEREN,
                    (Lo3BerichtenBron.INITIELE_VULLING.equals(bron) ? IDENTIFIER_INITIELE_VULLING : IDENTIFIER_BIJHOUDING) + "-syntax");
                throw e;
            }

            // Parse persoonslijst
            final Lo3Persoonslijst lo3Persoonslijst = LO3_PARSER.parse(lo3InhoudNaSyntaxControle);
            debugOutputXmlEnHtml(
                lo3Persoonslijst,
                TestCasusOutputStap.STAP_INITIEREN,
                (Lo3BerichtenBron.INITIELE_VULLING.equals(bron) ? IDENTIFIER_INITIELE_VULLING : IDENTIFIER_BIJHOUDING) + "-lo3");

            // Controleer precondities
            final Lo3Persoonslijst schoneLo3Persoonslijst;
            try {
                schoneLo3Persoonslijst = preconditieService.verwerk(lo3Persoonslijst);
            } catch (final OngeldigePersoonslijstException e) {
                debugOutputXmlEnHtml(
                    Logging.getLogging(),
                    TestCasusOutputStap.STAP_INITIEREN,
                    (Lo3BerichtenBron.INITIELE_VULLING.equals(bron) ? IDENTIFIER_INITIELE_VULLING : IDENTIFIER_BIJHOUDING) + "-precondities");
                throw e;
            }
            // LOG.info("Lo3 persoonslijst: {}", lo3Pl);

            final BrpPersoonslijst brpPl = converteerLo3NaarBrpService.converteerLo3Persoonslijst(schoneLo3Persoonslijst);
            debugOutputXmlEnHtml(
                brpPl,
                TestCasusOutputStap.STAP_INITIEREN,
                (Lo3BerichtenBron.INITIELE_VULLING.equals(bron) ? IDENTIFIER_INITIELE_VULLING : IDENTIFIER_BIJHOUDING) + "-brp");
            // LOG.info("BRP persoonslijst: {}", brpPl);

            final Lo3Bericht lo3Bericht = new Lo3Bericht("persoon", bron, new Timestamp(System.currentTimeMillis()), "ExcelData", true);
            LOG.info("Logging: {}", lo3Bericht);

            final boolean isAnummerWijziging;
            final long anummerTeVervangenPersoon;
            if (isAnummerWijziging(excelData.getHeaders())) {
                isAnummerWijziging = true;
                anummerTeVervangenPersoon = Long.parseLong(excelData.getHeaders()[4]);
            } else {
                isAnummerWijziging = false;
                anummerTeVervangenPersoon = brpPl.getActueelAdministratienummer();
            }

            LOG.info("A-nummer wijziging? {}", isAnummerWijziging);
            LOG.info("A-nummer te vervangen persoon: {}", anummerTeVervangenPersoon);

            final PersoonslijstPersisteerResultaat result =
                    brpDalService.persisteerPersoonslijst(brpPl, anummerTeVervangenPersoon, isAnummerWijziging, lo3Bericht);
            LOG.info("Result: {}", result);
            LOG.info("Persoon: {}", result.getPersoon());
            LOG.info("Administratieve handeling(en): {}", result.getAdministratieveHandelingen());

            LOG.info("Persoon.id: {}", result.getPersoon().getId());
            // final PersoonHisVolledig persoonHisVolledig = blobifierService.leesBlob(result.getKey().getId());
            // LOG.info("PersoonHisVolledig: {}", persoonHisVolledig);

            return result;
        } finally {
            debugOutputXmlEnHtml(
                Logging.getLogging(),
                TestCasusOutputStap.STAP_INITIEREN,
                (Lo3BerichtenBron.INITIELE_VULLING.equals(bron) ? IDENTIFIER_INITIELE_VULLING : IDENTIFIER_BIJHOUDING) + "-logging");

            debugOutputString(
                SynchronisatieLogging.getMelding(),
                TestCasusOutputStap.STAP_INITIEREN,
                (Lo3BerichtenBron.INITIELE_VULLING.equals(bron) ? IDENTIFIER_INITIELE_VULLING : IDENTIFIER_BIJHOUDING) + "-synclogging");

            LoggingContext.reset();
            Logging.destroyContext();
        }

    }

    private boolean isAnummerWijziging(final String[] headers) {
        return headers.length == 5 && "Lg01".equals(headers[1]) && isAnummer(headers[3]) && isAnummer(headers[4]);
    }

    private boolean isAnummer(final String value) {
        try {
            if (value != null && !"".equals(value) && Long.parseLong(value) > 0) {
                return true;
            }
        } catch (final NumberFormatException e) {
            // Ignore
            LOG.debug("NumberFormatException bij a-nummer", e);
        }
        return false;
    }

    private AdministratieveHandelingModel initierenBzmBijhouding(
        final File bijhoudingFile,
        final LeveringMutatieberichtTestResultaat result,
        final Integer persoonId)
    {
        try (FileInputStream fis = new FileInputStream(bijhoudingFile)) {

            final BijhoudingsBericht bericht = (BijhoudingsBericht) leesXmlString(fis, RegistreerOverlijdenBericht.class);
            final Integer partijCode = Integer.valueOf(bericht.getStuurgegevens().getZendendePartijCode());
            final String objectSleutel = objectSleutelService.genereerObjectSleutelString(persoonId, partijCode);
            vervangObjectSleutel(bericht, objectSleutel);

            final BijhoudingBerichtContext context =
                    new BijhoudingBerichtContext(
                        new BerichtenIds(1L, 1L),
                        new Partij(null, null, new PartijCodeAttribuut(partijCode), null, null, null, null, null, null),
                        null,
                        bericht.getCommunicatieIdMap());

            final BijhoudingResultaat bijhoudingResultaat = bijhoudingsBerichtVerwerker.verwerkBericht(bericht, context);
            LOG.info("Bijhouding resultaat: {}", bijhoudingResultaat);

            if (bijhoudingResultaat.getMeldingen() != null && !bijhoudingResultaat.getMeldingen().isEmpty()) {
                for (final Melding melding : bijhoudingResultaat.getMeldingen()) {
                    LOG.info("Melding: {} - {}", melding.getSoort(), melding.getMeldingTekst());
                }
            }

            if (!bijhoudingResultaat.isSuccesvol()) {
                throw new IllegalStateException("Bijhouding mislukt");
            }
            LOG.info("Administratieve handeling: {}", bijhoudingResultaat.getAdministratieveHandeling());
            return bijhoudingResultaat.getAdministratieveHandeling();

        } catch (final IOException e) {
            final Foutmelding fout = new Foutmelding("Fout tijdens initieren (persoonMutatie).", e);
            final String htmlFout = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_INITIEREN, SUFFIX_EXCEPTION);
            result.setInitieren(new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden (initieren persoonMutatie)", htmlFout, null));
        }

        return null;
    }

    private void vervangObjectSleutel(final BerichtBericht bericht, final String objeSleutel) {
        final CommunicatieIdMap idMap = bericht.getCommunicatieIdMap();
        for (final List<BerichtIdentificeerbaar> idList : idMap.values()) {
            if (idList != null && idList.get(0) != null && idList.get(0) instanceof PersoonBericht) {
                final PersoonBericht pb = (PersoonBericht) idList.get(0);
                pb.setObjectSleutel(objeSleutel);
            }
        }
    }

    private static Object leesXmlString(final InputStream xmlInputStrem, final Class<?> clazz) {
        try {
            final IBindingFactory bfact = BindingDirectory.getFactory(clazz);

            final IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
            return uctx.unmarshalDocument(xmlInputStrem, null);
        } catch (final JiBXException e) {
            LOG.info("Probleem bij lees xml string. Returning null.", e);
        }
        return null;
    }

    private Integer initierenAutorisatie(final LeveringMutatieberichtTestResultaat result) {
        final String autorisatieFilename = getAutorisatieFilename();

        try (FileInputStream fis = new FileInputStream(new File(inputFolder, autorisatieFilename))) {
            Logging.initContext();
            final Collection<Lo3Autorisatie> lo3Autorisaties = AUTORISATIE_READER.read(fis);

            if (lo3Autorisaties.isEmpty()) {
                throw new IllegalArgumentException(autorisatieFilename + " bevat geen autorisaties");
            }

            if (lo3Autorisaties.size() > 1) {
                throw new IllegalArgumentException(autorisatieFilename + " bevat gegevens voor meer dan 1 autorisatie");
            }

            final Lo3Autorisatie lo3Autorisatie = lo3Autorisaties.iterator().next();
            debugOutputXmlEnHtml(lo3Autorisatie, TestCasusOutputStap.STAP_INITIEREN, "autorisatie-lo3");
            final BrpAutorisatie brpAutorisatie =
                    ((ConverteerLo3NaarBrpServiceImpl) converteerLo3NaarBrpService).converteerLo3Autorisatie(lo3Autorisatie, new ConversieHook()
            {

                        @Override
                        public void stap(final ConversieStap stap, final Object object) {
                            debugOutputXmlEnHtml(object, TestCasusOutputStap.STAP_INITIEREN, "autorisatie-" + stap.name());
                        }
                    });

            final List<ToegangLeveringsAutorisatie> toegangLeveringsAutorisaties = brpDalService.persisteerAutorisatie(brpAutorisatie);
            final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = bepaalRelevanteToegangLeveringsautorisatie(toegangLeveringsAutorisaties);

            return toegangLeveringsAutorisatie.getId();

        } catch (final IOException e) {
            final Foutmelding fout = new Foutmelding("Fout tijdens initieren (autorisatie).", e);
            final String htmlFout = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_INITIEREN, SUFFIX_EXCEPTION);
            result.setInitieren(new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden (initieren autorisatie)", htmlFout, null));

        } finally {
            LoggingContext.reset();
            Logging.destroyContext();
        }

        return null;
    }

    private ToegangLeveringsAutorisatie bepaalRelevanteToegangLeveringsautorisatie(final List<ToegangLeveringsAutorisatie> toegangLeveringsAutorisaties) {
        for (final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie : toegangLeveringsAutorisaties) {
            if (toegangLeveringsAutorisatie.getDatumEinde() == null) {
                return toegangLeveringsAutorisatie;
            }
        }

        throw new IllegalArgumentException("Geen actuele toegang leveringsaurisatie");
    }

    /**
     * Geef de waarde van autorisatie filename.
     *
     * @return autorisatie filename
     */
    private String getAutorisatieFilename() {
        final String[] foundFiles = inputFolder.list(new FilenameFilter() {
            @Override
            public boolean accept(final File dir, final String name) {
                return name.startsWith("autorisatie") && name.endsWith(".csv");
            }
        });
        return foundFiles.length == 0 ? "autorisatie.csv" : foundFiles[0];
    }

    private void blobify(final Integer persoonId, final LeveringMutatieberichtTestResultaat result, final String suffix) {
        if (persoonId == null) {
            return;
        }

        final DefaultTransactionDefinition blobifyTransactionDefinition = new DefaultTransactionDefinition();
        blobifyTransactionDefinition.setName("Testcase-blobify");
        blobifyTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        final TransactionStatus blobifyTransaction = transactionManagerMaster.getTransaction(blobifyTransactionDefinition);

        try {
            blobifierService.blobify(persoonId, true);
        } catch (final Exception e) {
            LOG.warn("Fout tijdens blobificeren", e);
            blobifyTransaction.setRollbackOnly();
            final Foutmelding fout = new Foutmelding("Fout tijdens initieren (blobify).", e);
            final String htmlFout = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_INITIEREN, SUFFIX_EXCEPTION);
            result.setInitieren(new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden (blobify)", htmlFout, null));
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

                debugOutputString(toString(blob), TestCasusOutputStap.STAP_INITIEREN, suffix);
            } catch (
                NoResultException
                | NonUniqueResultException e)
            {
                LOG.error("Fout bij output blob", e);
            }
        }
    }

    /**
     * Test lezen uit brp.
     *
     * @param persoonId
     *            persoon id
     * @param result
     *            resultaat
     */
    private PersoonHisVolledig testLeesPersoon(final Integer persoonId, final TestLeveringResultaat result) {
        if (persoonId != null) {
            LOG.info("Test lezen en mappen uit BRP ...");
            try {
                // Lezen uit database
                final PersoonHisVolledig persoonHisVolledig = blobifierService.leesBlob(persoonId);

                if (persoonHisVolledig == null) {
                    throw new IllegalStateException("Geen persoon gelezen uit database");
                }

                try {
                    final Set<Stapel> istStapels = new HashSet<>(istTabelRepository.leesIstStapels(persoonHisVolledig));

                    // Mapping
                    final BrpPersoonslijst brp = persoonslijstMapper.map(persoonHisVolledig, istStapels);
                    final String htmlBrp = debugOutputXmlEnHtml(brp, TestCasusOutputStap.STAP_LEZEN, result.getSoortAdministratieveHandeling());
                    result.setMapping(new TestStap(TestStatus.OK, null, htmlBrp, null));

                    // Vergelijk expected
                    final BrpPersoonslijst expectedBrp =
                            leesVerwachteBrpPersoonslijst(TestCasusOutputStap.STAP_LEZEN, result.getSoortAdministratieveHandeling());
                    if (expectedBrp != null) {
                        final StringBuilder verschillenLog = new StringBuilder();
                        if (!BrpVergelijker.vergelijkPersoonslijsten(verschillenLog, expectedBrp, brp, true, true)) {
                            final Foutmelding fout = new Foutmelding("Vergelijking (lezen)", "Inhoud is ongelijk (mapping)", verschillenLog.toString());
                            final String htmlVerschillen =
                                    debugOutputXmlEnHtml(
                                        fout,
                                        TestCasusOutputStap.STAP_LEZEN,
                                        result.getSoortAdministratieveHandeling() + SUFFIX_VERSCHILLEN);
                            result.setMapping(new TestStap(TestStatus.NOK, ER_ZIJN_INHOUDELIJKE_VERSCHILLEN_MAPPING, htmlBrp, htmlVerschillen));
                        }
                    }
                } catch (final Exception e) {
                    LOG.warn("Fout tijdens mapping", e);

                    final Foutmelding fout = new Foutmelding("Fout tijdens mapping.", e);
                    final String htmlFout =
                            debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_LEZEN, result.getSoortAdministratieveHandeling() + SUFFIX_EXCEPTION);

                    result.setMapping(new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden (mapping)", htmlFout, null));
                }

                return persoonHisVolledig;

            } catch (final Exception e) {
                LOG.warn("Fout tijdens lezen blob", e);

                final Foutmelding fout = new Foutmelding("Fout tijdens lezen blob.", e);
                final String htmlFout =
                        debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_LEZEN, result.getSoortAdministratieveHandeling() + SUFFIX_EXCEPTION);

                result.setMapping(new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden (lezen blob)", htmlFout, null));
            }
        }

        return null;
    }

    private Leveringinformatie leesAbonnementCacheElement(final Integer toegangLeveringsautorisatieId, final TestLeveringResultaat result) {
        try {
            final ToegangLeveringsautorisatie toegangLeveringsautorisatie =
                    brpEntityManager.find(ToegangLeveringsautorisatie.class, toegangLeveringsautorisatieId);

            final TypedQuery<Dienst> dienstQuery =
                    brpEntityManager.createQuery(
                        "select dienst from Dienst dienst where dienst.dienstbundel.leveringsautorisatie = :leveringsautorisatie",
                        Dienst.class);
            dienstQuery.setParameter("leveringsautorisatie", toegangLeveringsautorisatie.getLeveringsautorisatie());
            final List<Dienst> diensten = dienstQuery.getResultList();
            final Dienst dienst = bepaalRelevanteDienst(diensten);

            return new Leveringinformatie(toegangLeveringsautorisatie, dienst);
        } catch (final Exception e) {
            final Foutmelding fout = new Foutmelding("Fout tijdens lezen abonnement.", e);
            final String htmlFout =
                    debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_LEZEN, result.getSoortAdministratieveHandeling() + SUFFIX_EXCEPTION);

            result.setMapping(new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden (lezen abonnement)", htmlFout, null));
            return null;
        }
    }

    private Dienst bepaalRelevanteDienst(final List<Dienst> diensten) {
        final File dienstFile = new File(inputFolder, "dienst.txt");
        SoortDienst soortDienst = SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_AFNEMERINDICATIE;
        EffectAfnemerindicaties effectAfnemerindicaties = null;
        if (dienstFile.exists() && dienstFile.isFile() && dienstFile.canRead()) {
            try (FileInputStream fis = new FileInputStream(dienstFile)) {
                final String config = new String(IOUtils.toByteArray(fis), ISO_8859_1);
                LOG.info("Bepalen dienst obv dienst.txt: {}", config);

                final String[] configSplit = config.split(",");
                soortDienst = SoortDienst.valueOf(configSplit[0]);
                if (configSplit.length > 1) {
                    effectAfnemerindicaties = EffectAfnemerindicaties.valueOf(configSplit[1]);
                }
            } catch (final IOException e) {
                LOG.error("Onverwachte fout opgetreden bij lezen 'dienst.txt'.");
            }
        }

        LOG.info("Zoek dienst obv {} en {}", soortDienst, effectAfnemerindicaties);
        for (final Dienst dienst : diensten) {
            LOG.info("Kandidaat: {} {}", dienst.getSoort(), dienst.getEffectAfnemerindicaties());
            if (isEquals(soortDienst, dienst.getSoort()) && isEquals(effectAfnemerindicaties, dienst.getEffectAfnemerindicaties())) {
                return dienst;
            }
        }

        throw new IllegalArgumentException(
            "Relevante dienst '" + soortDienst + "'  met effect afnemersindicaties '" + effectAfnemerindicaties + "' kon niet worden bepaald.");
    }

    private boolean isEquals(final Object object1, final Object object2) {
        return object1 == null ? object2 == null : object1.equals(object2);
    }

    private AdministratieveHandelingModel leesAdministratieveHandeling(final Long administratieveHandelingId, final TestLeveringResultaat resultaat) {
        try {

            if (PLAATSING_BERICHT_ADM_HAND_ID.equals(administratieveHandelingId)) {
                resultaat.setSoortAdministratieveHandeling(PLAATSING_SOORT_ADM_HAND);
            } else {
                final AdministratieveHandelingModel result = brpEntityManager.find(AdministratieveHandelingModel.class, administratieveHandelingId);
                if (result == null) {
                    throw new IllegalArgumentException("Administratieve handeling kon niet uit de database worden gelezen.");
                }
                resultaat.setSoortAdministratieveHandeling(result.getSoort().getWaarde().toString());

                final String expectedAdministratieveHandelingSoorten = leesVerwachteString(TestCasusOutputStap.STAP_INITIEREN, SUFFIX_SOORTEN_ADM_HAND);
                if (expectedAdministratieveHandelingSoorten == null || "".equals(expectedAdministratieveHandelingSoorten)) {
                    resultaat.setSoortAdministratieveHandelingStatus(TestStatus.GEEN_VERWACHTING);
                } else {
                    // Hack, omdat de ene enum uit MIGRATIE komt en de andere uit BRP. BRP ordinal moet gelijk zijn aan
                    // MIG id.
                    final int brpOrdinal = result.getSoort().getWaarde().ordinal();
                    boolean found = false;
                    for (final String migExpectedAdmHndSoort : expectedAdministratieveHandelingSoorten.split(COMMA_SEPERATOR)) {
                        if (getMigratieSoortAdministratieveHandelingId(migExpectedAdmHndSoort) == brpOrdinal) {
                            found = true;
                            break;
                        }
                    }

                    if (found) {
                        resultaat.setSoortAdministratieveHandelingStatus(TestStatus.OK);
                    } else {
                        resultaat.setSoortAdministratieveHandelingStatus(TestStatus.NOK);
                    }
                }

                return result;
            }
        } catch (final Exception e) {
            final Foutmelding fout = new Foutmelding("Fout tijdens lezen administratieve handeling.", e);
            resultaat.setSoortAdministratieveHandeling("FOUT");
            final String htmlFout =
                    debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_LEZEN, resultaat.getSoortAdministratieveHandeling() + SUFFIX_EXCEPTION);

            resultaat.setMapping(new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden (lezen administratieve handeling)", htmlFout, null));
        }
        return null;
    }

    private int getMigratieSoortAdministratieveHandelingId(final String migExpectedAdmHndSoort) {
        try {
            return nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.SoortAdministratieveHandeling.valueOf(migExpectedAdmHndSoort).getId();
        } catch (final IllegalArgumentException e) {
            return -1;
        }
    }

    private List<BerichtImpl> testBepalenBericht(
        final PersoonHisVolledig persoon,
        final Leveringinformatie leveringAutorisatie,
        final Map<Integer, Populatie> populatieMap,
        final AdministratieveHandelingModel administratieveHandeling,
        final TestLeveringResultaat resultaat)
    {
        try {
            final List<BerichtImpl> berichten;
            if (administratieveHandeling == null) {
                LOG.info("Bepalen Ag01 bericht");
                berichten = Collections.<BerichtImpl>singletonList((BerichtImpl) berichtFactory.maakAg01Bericht(persoon));
            } else {

                final SoortDienst soortDienst = leveringAutorisatie.getSoortDienst();
                final SoortAdministratieveHandeling soort = administratieveHandeling.getSoort().getWaarde();
                final CategorieAdministratieveHandeling categorie = soort.getCategorieAdministratieveHandeling();
                LOG.info("Bepalen bericht");
                LOG.info("dienst: {}, categorie: {}, soort: {}", new Object[] {soortDienst, categorie, soort });
                LOG.info("persoon: {}", new Object[] {persoon });
                final List<SynchronisatieBericht> syncBerichten =
                        berichtFactory.maakBerichten(Arrays.asList(persoon), leveringAutorisatie, populatieMap, administratieveHandeling);

                berichten = new ArrayList<BerichtImpl>();
                for (final SynchronisatieBericht syncBericht : syncBerichten) {
                    berichten.add((BerichtImpl) syncBericht);
                }
                //
                // if (berichten == null || berichten.size() != 1) {
                // throw new IllegalArgumentException("Er kon niet eenduidig 1 bericht worden bepaald");
                // }
                // bericht = (BerichtImpl) berichten.get(0);
            }

            return berichten;
        } catch (final Exception e) {
            final Foutmelding fout = new Foutmelding("Fout tijdens bepalen bericht.", e);
            final String htmlFout =
                    debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_LEZEN, resultaat.getSoortAdministratieveHandeling() + SUFFIX_EXCEPTION);

            resultaat.setMapping(new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden (bepalen bericht)", htmlFout, null));
            return null;
        }
    }

    private void testConversie(final BerichtImpl bericht, final TestLeveringResultaat leveringResult, final TestLeveringBerichtResultaat berichtResult) {
        if (bericht == null) {
            return;
        }
        LOG.info("Test terugconversie ...");
        try {
            // outputConversieStappen(bericht, leveringResult, berichtResult);

            // LOG.info("Bericht.converteerNaarLo3 ...");
            bericht.converteerNaarLo3(new ConversieCache());
            // LOG.info("Bericht.converteerNaarLo3 done...");

            final List<Lo3CategorieWaarde> categorieen = Lo3CategorieWaardenSorter.sorteer(getBerichtImplCategorieen(bericht));
            final String htmlCategorieen =
                    debugOutputLo3CategorieWaarden(
                        categorieen,
                        TestCasusOutputStap.STAP_TERUG,
                        leveringResult.getSoortAdministratieveHandeling() + DASH_SEPERATOR + berichtResult.getSoortBericht());

            final List<Lo3CategorieWaarde> expectedCategorieen =
                    Lo3CategorieWaardenSorter.sorteer(
                        leesVerwachteLo3CategorieWaarden(
                            TestCasusOutputStap.STAP_TERUG,
                            leveringResult.getSoortAdministratieveHandeling() + DASH_SEPERATOR + berichtResult.getSoortBericht()));
            if (expectedCategorieen != null) {
                final StringBuilder verschillenLog = new StringBuilder();
                if (!nl.bzk.migratiebrp.test.levering.mutatiebericht.Lo3CategorieWaardenVergelijker.vergelijk(
                    verschillenLog,
                    expectedCategorieen,
                    categorieen))
                {
                    final Foutmelding fout = new Foutmelding("Vergelijking (conversie)", "Inhoud is ongelijk (conversie)", verschillenLog.toString());
                    final String htmlVerschillen =
                            debugOutputXmlEnHtml(
                                fout,
                                TestCasusOutputStap.STAP_TERUG,
                                leveringResult.getSoortAdministratieveHandeling() + DASH_SEPERATOR + berichtResult.getSoortBericht() + SUFFIX_VERSCHILLEN);
                    berichtResult.setConversie(new TestStap(TestStatus.NOK, ER_ZIJN_INHOUDELIJKE_VERSCHILLEN_MAPPING, htmlCategorieen, htmlVerschillen));
                } else {
                    berichtResult.setConversie(new TestStap(TestStatus.OK, null, htmlCategorieen, null));
                }
            } else {
                berichtResult.setConversie(new TestStap(TestStatus.GEEN_VERWACHTING, null, htmlCategorieen, null));
            }
        } catch (final Exception e) {
            LOG.info("Exception tijdens testConversie()", e);
            final Foutmelding fout = new Foutmelding("Fout tijdens conversie van BRP naar LO3.", e);
            final String htmlFout =
                    debugOutputXmlEnHtml(
                        fout,
                        TestCasusOutputStap.STAP_TERUG,
                        leveringResult.getSoortAdministratieveHandeling() + DASH_SEPERATOR + berichtResult.getSoortBericht() + SUFFIX_EXCEPTION);

            berichtResult.setConversie(new TestStap(TestStatus.EXCEPTIE, ER_IS_EEN_EXCEPTIE_OPGETREDEN, htmlFout, null));
        }
    }

    private boolean testFilter(
        final Leveringinformatie leveringAutorisatie,
        final BerichtImpl bericht,
        final TestLeveringResultaat leveringResult,
        final TestLeveringBerichtResultaat berichtResult)
    {
        final List<Lo3CategorieWaarde> categorieen = getBerichtImplCategorieen(bericht);
        if (categorieen == null || categorieen.isEmpty()) {
            return false;
        }
        LOG.info("Test filter ...");
        try {
            // final Abonnement abonnement = brpEntityManager.find(Abonnement.class, abonnementId);
            // ((Lo3FilterExpressieServiceImpl) lo3FilterExpressieService).herlaadLo3FilterCache();
            final List<String> lo3Filterrubrieken =
                    lo3FilterRubriekRepository.haalLo3FilterRubriekenVoorDienstbundel(leveringAutorisatie.getDienst().getDienstbundel());
            final StringBuilder debugOutput = new StringBuilder(lo3Filterrubrieken.size() * 10);
            for (final String lo3Filterrubriek : lo3Filterrubrieken) {
                debugOutput.append(lo3Filterrubriek).append("\n");
            }
            debugOutputString(
                debugOutput.toString(),
                TestCasusOutputStap.STAP_FILTER,
                leveringResult.getSoortAdministratieveHandeling() + DASH_SEPERATOR + berichtResult.getSoortBericht() + "-rubrieken");

            // Uitvoeren filter
            final boolean uitvoerenLevering = bericht.filterRubrieken(lo3Filterrubrieken);

            final List<Lo3CategorieWaarde> gefilterdeCategorieen = Lo3CategorieWaardenSorter.sorteer(getBerichtImplCategorieenGefilterd(bericht));
            final String htmlGefilterdeCategorieen =
                    debugOutputLo3CategorieWaarden(
                        gefilterdeCategorieen,
                        TestCasusOutputStap.STAP_FILTER,
                        leveringResult.getSoortAdministratieveHandeling() + DASH_SEPERATOR + berichtResult.getSoortBericht());

            final List<Lo3CategorieWaarde> expectedCategorieen =
                    Lo3CategorieWaardenSorter.sorteer(
                        leesVerwachteLo3CategorieWaarden(
                            TestCasusOutputStap.STAP_FILTER,
                            leveringResult.getSoortAdministratieveHandeling() + DASH_SEPERATOR + berichtResult.getSoortBericht()));
            if (expectedCategorieen != null) {
                final StringBuilder verschillenLog = new StringBuilder();
                if (!nl.bzk.migratiebrp.test.levering.mutatiebericht.Lo3CategorieWaardenVergelijker.vergelijk(
                    verschillenLog,
                    expectedCategorieen,
                    gefilterdeCategorieen))
                {
                    final Foutmelding fout = new Foutmelding("Vergelijking (filter)", "Inhoud is ongelijk (filter)", verschillenLog.toString());
                    final String htmlVerschillen =
                            debugOutputXmlEnHtml(
                                fout,
                                TestCasusOutputStap.STAP_FILTER,
                                leveringResult.getSoortAdministratieveHandeling() + DASH_SEPERATOR + berichtResult.getSoortBericht() + SUFFIX_VERSCHILLEN);
                    berichtResult.setFilteren(
                        new TestStap(TestStatus.NOK, "Er zijn inhoudelijke verschillen (filter)", htmlGefilterdeCategorieen, htmlVerschillen));
                } else {
                    berichtResult.setFilteren(new TestStap(TestStatus.OK, null, htmlGefilterdeCategorieen, null));
                }
            } else {
                berichtResult.setFilteren(new TestStap(TestStatus.GEEN_VERWACHTING, null, htmlGefilterdeCategorieen, null));
            }

            return uitvoerenLevering;
        } catch (final Exception e) {
            final Foutmelding fout = new Foutmelding("Fout tijdens filter.", e);
            final String htmlFout =
                    debugOutputXmlEnHtml(
                        fout,
                        TestCasusOutputStap.STAP_FILTER,
                        leveringResult.getSoortAdministratieveHandeling() + DASH_SEPERATOR + berichtResult.getSoortBericht() + SUFFIX_EXCEPTION);

            berichtResult.setFilteren(new TestStap(TestStatus.EXCEPTIE, ER_IS_EEN_EXCEPTIE_OPGETREDEN, htmlFout, null));
        }

        return false;
    }

    private void testBericht(final BerichtImpl bericht, final TestLeveringResultaat leveringResult, final TestLeveringBerichtResultaat berichtResult) {
        // final List<Lo3CategorieWaarde> categorieen = getBerichtImplCategorieenGefilterd(bericht);
        // if (categorieen == null || categorieen.isEmpty()) {
        // return;
        // }
        LOG.info("Test bericht ...");
        try {
            final String platteTekst = Lo3BerichtSorter.sorteer(bericht.maakUitgaandBericht());
            final String htmlTekst =
                    debugOutputBericht(
                        platteTekst,
                        TestCasusOutputStap.STAP_BERICHT,
                        leveringResult.getSoortAdministratieveHandeling() + DASH_SEPERATOR + berichtResult.getSoortBericht());

            berichtResult.setBericht(new TestStap(TestStatus.OK, null, htmlTekst, null));

            final String expectedPlatteTekst =
                    Lo3BerichtSorter.sorteer(
                        leesVerwachteString(
                            TestCasusOutputStap.STAP_BERICHT,
                            leveringResult.getSoortAdministratieveHandeling() + DASH_SEPERATOR + berichtResult.getSoortBericht()));

            if (expectedPlatteTekst != null) {
                if (!expectedPlatteTekst.equals(platteTekst)) {
                    // Bepaal verschillen
                    final Lo3BerichtFactory lo3BerichtFactory = new Lo3BerichtFactory();
                    final nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht expectedBericht = lo3BerichtFactory.getBericht(expectedPlatteTekst);
                    final nl.bzk.migratiebrp.bericht.model.lo3.Lo3Bericht actualBericht = lo3BerichtFactory.getBericht(platteTekst);

                    final Lo3Header expectedHeader = expectedBericht.getHeader();
                    final int expectedHeaderSize = Lo3BerichtSorter.getTotalHeaderSize(expectedHeader, expectedPlatteTekst);
                    final String expectedInhoud = expectedPlatteTekst.substring(expectedHeaderSize);
                    final List<Lo3CategorieWaarde> expectedCategorieen = Lo3Inhoud.parseInhoud(expectedInhoud);

                    final Lo3Header actualHeader = actualBericht.getHeader();
                    final int actualHeaderSize = Lo3BerichtSorter.getTotalHeaderSize(actualHeader, platteTekst);
                    final String actualInhoud = platteTekst == null ? null : platteTekst.substring(actualHeaderSize);
                    final List<Lo3CategorieWaarde> actualCategorieen = Lo3Inhoud.parseInhoud(actualInhoud);

                    final StringBuilder vergelijkingsLog = new StringBuilder();
                    nl.bzk.migratiebrp.test.levering.mutatiebericht.Lo3CategorieWaardenVergelijker.vergelijk(
                        vergelijkingsLog,
                        expectedCategorieen,
                        actualCategorieen);
                    final String htmlVerschillen = debugOutputString(vergelijkingsLog.toString(), TestCasusOutputStap.STAP_BERICHT, SUFFIX_VERSCHILLEN);

                    berichtResult.setBericht(new TestStap(TestStatus.NOK, "Er zijn inhoudelijke verschillen (bericht)", htmlTekst, htmlVerschillen));
                } else {
                    berichtResult.setBericht(new TestStap(TestStatus.OK, null, htmlTekst, null));
                }
            } else {
                berichtResult.setBericht(new TestStap(TestStatus.GEEN_VERWACHTING, null, htmlTekst, null));
            }

        } catch (final Exception e) {
            final Foutmelding fout = new Foutmelding("Fout tijdens maken bericht.", e);
            final String htmlFout =
                    debugOutputXmlEnHtml(
                        fout,
                        TestCasusOutputStap.STAP_BERICHT,
                        leveringResult.getSoortAdministratieveHandeling() + DASH_SEPERATOR + berichtResult.getSoortBericht() + SUFFIX_EXCEPTION);

            berichtResult.setBericht(new TestStap(TestStatus.EXCEPTIE, ER_IS_EEN_EXCEPTIE_OPGETREDEN, htmlFout, null));
        }
    }

    private List<Lo3CategorieWaarde> getBerichtImplCategorieen(final BerichtImpl bericht) {
        if (bericht == null) {
            return null;
        }
        return (List<Lo3CategorieWaarde>) ReflectionTestUtils.getField(bericht, "categorieen");
    }

    private List<Lo3CategorieWaarde> getBerichtImplCategorieenGefilterd(final BerichtImpl bericht) {
        if (bericht == null) {
            return null;
        }
        return (List<Lo3CategorieWaarde>) ReflectionTestUtils.getField(bericht, "categorieenGefilterd");
    }

    /**
     * Lokale data class voor de test.
     */
    private static final class Bijhouding {
        private Integer persoonId;
        private List<Long> administratieveHandelingIds;

        /**
         * Geef de waarde van persoon id.
         *
         * @return persoon id
         */
        public Integer getPersoonId() {
            return persoonId;
        }

        /**
         * Zet de waarde van persoon id.
         *
         * @param persoonId
         *            persoon id
         */
        public void setPersoonId(final Integer persoonId) {
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
         * Zet de waarde van converteer brp naar lo3 service.
         *
         * @param injectConverteerBrpNaarLo3Service
         *            converteer brp naar lo3 service
         */
        @Inject
        public void setConverteerBrpNaarLo3Service(final ConverteerBrpNaarLo3Service injectConverteerBrpNaarLo3Service) {
            converteerBrpNaarLo3Service = injectConverteerBrpNaarLo3Service;
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
         * Zet de waarde van persoonslijst mapper.
         *
         * @param injectPersoonslijstMapper
         *            persoonslijst mapper
         */
        @Inject
        public void setPersoonslijstMapper(final PersoonslijstMapper injectPersoonslijstMapper) {
            persoonslijstMapper = injectPersoonslijstMapper;
        }

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
         * Zet de waarde van ist tabel repository.
         *
         * @param injectIstTabelRepository
         *            ist tabel repository
         */
        @Inject
        public void setIstTabelRepository(final IstTabelRepository injectIstTabelRepository) {
            istTabelRepository = injectIstTabelRepository;
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

        /**
         * Zet de waarde van lo3 filter rubrieken repository.
         *
         * @param injectLo3FilterRubriekRepository
         *            lo3 filter rubrieken repository
         */
        @Inject
        public void setLo3FilterRubriekenService(final Lo3FilterRubriekRepository injectLo3FilterRubriekRepository) {
            lo3FilterRubriekRepository = injectLo3FilterRubriekRepository;
        }

        /**
         * Zet de waarde van entity manager.
         *
         * @param injectBrpEntityManager
         *            entity manager
         */
        @PersistenceContext(unitName = "nl.bzk.brp.alleenlezen")
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

        /**
         * Zet de waarde van bericht factory.
         *
         * @param injectBerichtFactory
         *            bericht factory
         */
        @Inject
        public void setBerichtFactory(final BerichtFactory injectBerichtFactory) {
            berichtFactory = injectBerichtFactory;
        }
    }
}
