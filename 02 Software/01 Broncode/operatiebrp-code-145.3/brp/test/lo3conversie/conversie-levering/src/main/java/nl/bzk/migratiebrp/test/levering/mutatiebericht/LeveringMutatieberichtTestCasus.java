/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.levering.mutatiebericht;

import edu.emory.mathcs.backport.java.util.Arrays;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequestEvent;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.AdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Stapel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.CategorieAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.EffectAfnemerindicaties;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Lo3BerichtenBron;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortAdministratieveHandeling;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortRelatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.algemeenbrp.services.blobber.Blobber;
import nl.bzk.algemeenbrp.services.blobber.json.PersoonBlob;
import nl.bzk.algemeenbrp.services.objectsleutel.ObjectSleutelService;
import nl.bzk.algemeenbrp.util.cache.DalCacheController;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.logging.LoggingContext;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingAntwoordBericht;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.model.MeldingElement;
import nl.bzk.brp.bijhouding.bericht.parser.BijhoudingVerzoekBerichtParser;
import nl.bzk.brp.bijhouding.bericht.parser.ParseException;
import nl.bzk.brp.bijhouding.business.BijhoudingService;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Populatie;
import nl.bzk.brp.domain.leveringmodel.Actie;
import nl.bzk.brp.domain.leveringmodel.ModelAfdruk;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.gba.dataaccess.IstTabelRepository;
import nl.bzk.brp.gba.dataaccess.Lo3FilterRubriekRepository;
import nl.bzk.brp.levering.lo3.bericht.Bericht;
import nl.bzk.brp.levering.lo3.bericht.BerichtFactory;
import nl.bzk.brp.levering.lo3.bericht.BerichtImpl;
import nl.bzk.brp.levering.lo3.conversie.ConversieCache;
import nl.bzk.brp.levering.lo3.conversie.IdentificatienummerMutatie;
import nl.bzk.brp.levering.lo3.mapper.PersoonslijstMapper;
import nl.bzk.brp.service.algemeen.autorisatie.LeveringsautorisatieService;
import nl.bzk.brp.service.algemeen.blob.PersoonslijstService;
import nl.bzk.brp.service.cache.CacheController;
import nl.bzk.migratiebrp.bericht.model.brp.factory.BrpBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Header;
import nl.bzk.migratiebrp.bericht.model.lo3.Lo3Inhoud;
import nl.bzk.migratiebrp.bericht.model.lo3.factory.Lo3BerichtFactory;
import nl.bzk.migratiebrp.bericht.model.lo3.parser.Lo3PersoonslijstParser;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.brp.autorisatie.BrpAutorisatie;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.autorisatie.Lo3Autorisatie;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerBrpNaarLo3Service;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerLo3NaarBrpService;
import nl.bzk.migratiebrp.conversie.regels.proces.impl.ConverteerLo3NaarBrpServiceImpl;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.Lo3SyntaxControle;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.PreconditiesService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpAutorisatieService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpPersoonslijstService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.PartijNietGevondenException;
import nl.bzk.migratiebrp.synchronisatie.dal.service.PersoonslijstPersisteerResultaat;
import nl.bzk.migratiebrp.synchronisatie.dal.service.SyncParameters;
import nl.bzk.migratiebrp.synchronisatie.dal.service.TeLeverenAdministratieveHandelingenAanwezigException;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.test.common.autorisatie.AutorisatieReader;
import nl.bzk.migratiebrp.test.common.autorisatie.CsvAutorisatieReader;
import nl.bzk.migratiebrp.test.common.resultaat.Foutmelding;
import nl.bzk.migratiebrp.test.common.resultaat.TestResultaat;
import nl.bzk.migratiebrp.test.common.resultaat.TestStap;
import nl.bzk.migratiebrp.test.common.resultaat.TestStatus;
import nl.bzk.migratiebrp.test.common.vergelijk.Vergelijk;
import nl.bzk.migratiebrp.test.common.vergelijk.VergelijkXml;
import nl.bzk.migratiebrp.test.dal.TestCasus;
import nl.bzk.migratiebrp.test.dal.TestCasusOutputStap;
import nl.bzk.migratiebrp.test.levering.mutatiebericht.LeveringMutatieberichtTestResultaat.TestLeveringBerichtResultaat;
import nl.bzk.migratiebrp.test.levering.mutatiebericht.LeveringMutatieberichtTestResultaat.TestLeveringResultaat;
import nl.bzk.migratiebrp.util.common.JdbcConstants;
import nl.bzk.migratiebrp.util.excel.ExcelAdapter;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterException;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterImpl;
import nl.bzk.migratiebrp.util.excel.ExcelData;
import org.apache.commons.io.IOUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.context.request.RequestContextListener;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Test casus: conversie lo3 naar brp.
 */
public final class LeveringMutatieberichtTestCasus extends TestCasus {

    private static final String DASH_SEPERATOR = "-";

    private static final String IDENTIFIER_BIJHOUDING = "bijh";

    private static final String IDENTIFIER_INITIELE_VULLING = "iv";

    private static final String COMMA_SEPERATOR = ",";

    private static final Logger LOG = LoggerFactory.getLogger();

    private static final ExcelAdapter EXCEL_ADAPTER = new ExcelAdapterImpl();
    private static final Lo3PersoonslijstParser LO3_PARSER = new Lo3PersoonslijstParser();
    private static final AutorisatieReader AUTORISATIE_READER = new CsvAutorisatieReader();

    private static final Long PLAATSING_BERICHT_ADM_HAND_ID = Long.MIN_VALUE;
    private static final String PLAATSING_SOORT_ADM_HAND = "PLAATSEN";

    private static final String SUFFIX_SOORTEN_ADM_HAND = "soorten-admhnd";

    private static final int MILLIS_IN_SECOND = 1000;

    private static final String ER_ZIJN_INHOUDELIJKE_VERSCHILLEN_MAPPING = "Er zijn inhoudelijke verschillen (mapping)";
    private static final String ER_IS_EEN_EXCEPTIE_OPGETREDEN = "Er is een exceptie opgetreden";
    private static final String ISO_8859_1 = "ISO-8859-1";
    private final File inputFolder;
    private Lo3SyntaxControle syntaxControle;
    private PreconditiesService preconditieService;
    private ConverteerLo3NaarBrpService converteerLo3NaarBrpService;
    private ConverteerBrpNaarLo3Service converteerBrpNaarLo3Service;
    private BrpPersoonslijstService brpPersoonslijstService;
    private BrpAutorisatieService brpAutorisatieService;
    private SyncParameters syncParameters;
    private DataSource migratieDataSource;
    private EntityManager migratieEntityManager;
    private PlatformTransactionManager transactionManagerMaster;
    private PersoonslijstService persoonslijstService;
    private IstTabelRepository istTabelRepository;
    private PersoonslijstMapper persoonslijstMapper;

    private CacheController cacheController;
    private LeveringsautorisatieService toegangLeveringsautorisatieService;
    private Lo3FilterRubriekRepository lo3FilterRubriekRepository;
    private EntityManager brpEntityManager;
    private String serializer;

    private BerichtFactory berichtFactory;
    private DalCacheController dalCacheController;

    private BijhoudingService bijhoudingsBerichtVerwerker;
    private ObjectSleutelService objectSleutelService;

    /**
     * Constructor.
     * @param thema thema
     * @param naam naam
     * @param outputFolder output folder
     * @param expectedFolder expected folder
     * @param inputFolder input folder
     */
    protected LeveringMutatieberichtTestCasus(final String thema, final String naam, final File outputFolder, final File expectedFolder,
                                              final File inputFolder) {
        super(thema, naam, outputFolder, expectedFolder);
        this.inputFolder = inputFolder;
    }

    private static Object leesXmlString(final InputStream xmlInputStream, final Class<?> clazz) {
        try {
            return BrpBerichtFactory.SINGLETON.getBericht(IOUtils.toString(xmlInputStream));
        } catch (final IOException e) {
            LOG.info("Probleem bij lees xml string. Returning null.", e);
        }
        return null;
    }

    /**
     * Geef de waarde van bean for migratie autowire.
     * @return bean for migratie autowire
     */
    public Object getBeanForMigratieAutowire() {
        return new BeanForMigratieAutowire();
    }

    /**
     * Geef de waarde van bean for brp levering autowire.
     * @return bean for brp levering autowire
     */
    public Object getBeanForBrpLeveringAutowire() {
        return new BeanForBrpLeveringAutowire();
    }

    /**
     * Geef de waarde van bean for brp bijhouding autowire.
     * @return bean for brp bijhouding autowire
     */
    public Object getBeanForBrpBijhoudingAutowire() {
        return new Object() {
            @Inject
            public void setBijhoudingsBerichtVerwerker(final BijhoudingService bijhoudingsBerichtVerwerker) {
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
            final Integer toegangLeveringsautorisatieId = initierenAutorisatie(result);
            LOG.info("Initieren testcase: abonnement.id=" + toegangLeveringsautorisatieId);
            final Bijhouding bijhouding = initierenBijhouding(result);
            final Long persoonId = bijhouding.getPersoonId();
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

                        // Stap 2a: Lezen persoon uit BRP Database en mapping
                        final Persoonslijst persoon = testLeesPersoon(persoonId, leveringResult);

                        LOG.info("Lees persoon: id={} -> {}", persoonId, persoon);
                        if (persoon == null) {
                            continue;
                        }

                        // Stap 2b: Lezen abonnement uit BRP Database
                        final Autorisatiebundel leveringAutorisatie = leesAbonnementCacheElement(toegangLeveringsautorisatieId, leveringResult);
                        LOG.info("Lees abonnement: id={} -> {}", toegangLeveringsautorisatieId, leveringAutorisatie);
                        if (leveringAutorisatie == null) {
                            continue;
                        }

                        // Stap 2d: Lezen administratieve handeling uit BRP Database
                        final nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling administratieveHandeling =
                                bepaalAdministratieveHandeling(persoon, administratieveHandelingId, leveringResult);
                        LOG.info("Lees administratieve handeling: id={} -> {}", administratieveHandelingId, administratieveHandeling);

                        // Stap 2e: Bepalen bericht
                        final List<BerichtImpl> berichten = testBepalenBerichten(persoon, leveringAutorisatie, administratieveHandeling, leveringResult);
                        LOG.info("Berichten: {}", berichten.size());

                        if (berichten.isEmpty()) {
                            final TestLeveringBerichtResultaat berichtResult = new TestLeveringBerichtResultaat();
                            berichtResult.setSoortBericht("---");
                            leveringResult.getBerichten().add(berichtResult);
                        } else {
                            final Map<String, Integer> berichtIds = new HashMap<>();
                            for (final BerichtImpl bericht : berichten) {

                                final TestLeveringBerichtResultaat berichtResult = new TestLeveringBerichtResultaat();
                                berichtResult.setSoortBericht(bericht.geefSoortBericht());
                                leveringResult.getBerichten().add(berichtResult);

                                // Volgnummer
                                final String volgnummer = bepaalVolgnummer(leveringResult, berichtResult, berichtIds);

                                // Stap 3: Conversie
                                testConversie(bericht, volgnummer, leveringResult, berichtResult);

                                // Stap 4: Filter
                                final boolean uitvoerenLevering = testFilter(leveringAutorisatie, bericht, volgnummer, leveringResult, berichtResult);

                                // Stap 5: Maak bericht
                                testBericht(bericht, volgnummer, uitvoerenLevering, leveringResult, berichtResult);
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

    private String bepaalVolgnummer(final TestLeveringResultaat leveringResult, final TestLeveringBerichtResultaat berichtResult,
                                    final Map<String, Integer> berichtIds) {
        final String berichtId = leveringResult.getSoortAdministratieveHandeling() + DASH_SEPERATOR + berichtResult.getSoortBericht();
        if (!berichtIds.containsKey(berichtId)) {
            berichtIds.put(berichtId, 1);
            return "";
        } else {
            final Integer volgnummer = berichtIds.get(berichtId);
            berichtIds.put(berichtId, volgnummer + 1);
            return "-" + volgnummer;
        }
    }

    private String toString(final Object blob) {
        String resultaat;

        if (blob instanceof Blob) {
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                try (InputStream is = ((Blob) blob).getBinaryStream();) {
                    IOUtils.copy(is, baos);
                }
                resultaat = baos.toString(ISO_8859_1);
            } catch (final IOException | SQLException e) {
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

        // TODO: Tijdelijk totdat BMR 54 dit record toevoegd
        new JdbcTemplate(migratieDataSource).execute("delete from conv.convlo3rubriek where naam='04.73.10'");
        new JdbcTemplate(migratieDataSource).execute("insert into conv.convlo3rubriek(naam) values ('04.73.10')");

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
                final PersoonslijstPersisteerResultaat initieleVulling = initierenPersoon(persoonFile, 0, Lo3BerichtenBron.INITIELE_VULLING, null);
                LOG.info("Initiele vulling van persoon; id={} ", initieleVulling.getPersoon().getId());
                LOG.info("Initiele vulling van persoon; administratieve handeling(en)={} ", getIds(initieleVulling.getAdministratieveHandelingen()));
                bijhouding.setPersoonId(initieleVulling.getPersoon().getId());
                bijhouding.setLockVersie(initieleVulling.getPersoon().getLockVersie());

            } else if (deltaFile.exists() && deltaFile.isFile() && deltaFile.canRead()) {
                LOG.info("Initiele vulling obv GBA bericht (delta).");
                final PersoonslijstPersisteerResultaat initieleVulling = initierenPersoon(deltaFile, 0, Lo3BerichtenBron.INITIELE_VULLING, null);
                LOG.info("Initiele vulling van persoon; id={}", initieleVulling.getPersoon().getId());
                LOG.info("Initiele vulling van persoon; administratieve handeling(en)={}", getIds(initieleVulling.getAdministratieveHandelingen()));
                bijhouding.setPersoonId(initieleVulling.getPersoon().getId());
                bijhouding.setLockVersie(initieleVulling.getPersoon().getLockVersie());
            }

            // Stap 1b: Blobify
            outputPersoonBlobs(bijhouding.getPersoonId(), result, "blob-iv");
            outputPersoonsgegevens(bijhouding.getPersoonId(), result, "persoonsgegevens-iv");

            if (bijhouding.getPersoonId() != null) {
                rondverteer(bijhouding.getPersoonId(), "-iv");
            }

            final File bzmBijhoudingFile = new File(inputFolder, "bzmBijhouding.xml");
            final File lo3BijhoudingFile = new File(inputFolder, "gbaBijhouding.xls");
            final File lo3BijhoudingenDirectory = new File(inputFolder, "gbaBijhoudingen");

            if (bzmBijhoudingFile.exists() && bzmBijhoudingFile.isFile() && bzmBijhoudingFile.canRead()) {
                LOG.info("Bijhouding door middel van BZM bericht");
                final BijhoudingAntwoordBericht antwoordbericht =
                        initierenBzmBijhouding(bzmBijhoudingFile, result, bijhouding.getPersoonId(), bijhouding.getLockVersie());
                bijhouding.setAdministratieveHandelingId(antwoordbericht.getAdministratieveHandelingID());
                LOG.info("Bijhouding; administratieve handelingen={}", bijhouding.getAdministratieveHandelingIds());
                controleerSoortenAdministratieveHandeling(antwoordbericht.getAdministratieveHandelingAntwoord().getSoort().name(), result);
            } else if (lo3BijhoudingFile.exists() && lo3BijhoudingFile.isFile() && lo3BijhoudingFile.canRead()) {
                LOG.info("Bijhouden door middel van GBA synchronisatie");
                final PersoonslijstPersisteerResultaat gbaBijhouding = initierenPersoon(lo3BijhoudingFile, 0, Lo3BerichtenBron.SYNCHRONISATIE,
                        bijhouding.getPersoonId());
                final Persoon bijgehoudenPersoon = gbaBijhouding.getPersoon();
                LOG.info("Bijhouding; persoon={}, administratieve handeling(en)={} ", bijgehoudenPersoon.getId(),
                        getIds(gbaBijhouding.getAdministratieveHandelingen()));
                bijhouding.setPersoonId(bijgehoudenPersoon.getId());
                bijhouding.setAdministratieveHandelingIds(getIds(gbaBijhouding.getAdministratieveHandelingen()));

                controleerSoortenAdministratieveHandeling(getSoorten(gbaBijhouding.getAdministratieveHandelingen()), result);
            } else if (lo3BijhoudingenDirectory.exists() && lo3BijhoudingenDirectory.isDirectory()) {
                LOG.info("Bijhouden door middel van GBA synchronisatie(s)");

                final List<String> filenames = Arrays.<String>asList(lo3BijhoudingenDirectory.list((dir, name) -> name.endsWith(".xls")));
                Collections.sort(filenames);

                PersoonslijstPersisteerResultaat gbaBijhouding = null;
                for (final String lo3BijhoudingenFilename : filenames) {
                    gbaBijhouding = initierenPersoon(new File(lo3BijhoudingenDirectory, lo3BijhoudingenFilename), 0, Lo3BerichtenBron.SYNCHRONISATIE,
                            bijhouding.getPersoonId());
                    LOG.info("Bijhouding; persoon={}, administratieve handeling(en)={} ", gbaBijhouding.getPersoon().getId(),
                            getIds(gbaBijhouding.getAdministratieveHandelingen()));
                    setAdministratieveHandelingenAlsGeleverd(gbaBijhouding);
                }
                if (gbaBijhouding == null) {
                    throw new IllegalStateException("Geen bijhoudingen in gbaBijhoudingen directory aanwezig");
                }
                bijhouding.setPersoonId(gbaBijhouding.getPersoon().getId());
                bijhouding.setAdministratieveHandelingIds(getIds(gbaBijhouding.getAdministratieveHandelingen()));

                controleerSoortenAdministratieveHandeling(getSoorten(gbaBijhouding.getAdministratieveHandelingen()), result);
            } else if (deltaFile.exists() && deltaFile.isFile() && deltaFile.canRead()) {
                LOG.info("Bijhouden door middel van GBA synchronisatie (delta)");
                final PersoonslijstPersisteerResultaat gbaBijhouding = initierenPersoon(deltaFile, 1, Lo3BerichtenBron.SYNCHRONISATIE,
                        bijhouding.getPersoonId());
                final Persoon bijgehoudenPersoon = gbaBijhouding.getPersoon();
                LOG.info("Bijhouding; persoon={}, administratieve handeling(en)={}", bijgehoudenPersoon.getId(),
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
            outputPersoonBlobs(bijhouding.getPersoonId().longValue(), result, "blob");
            outputPersoonsgegevens(bijhouding.getPersoonId(), result, "persoonsgegevens");

        } catch (final Exception e) {
            final Foutmelding fout = new Foutmelding("Fout tijdens initieren (persoon).", e);
            LOG.error(fout.getContext(), e);
            final String htmlFout = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_INITIEREN, SUFFIX_EXCEPTION);
            result.setInitieren(new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden (initieren persoon)", htmlFout, null));
        }

        return bijhouding;
    }

    private void setAdministratieveHandelingenAlsGeleverd(final PersoonslijstPersisteerResultaat persoonslijstPersisteerResultaat) {
        for (final AdministratieveHandeling administratieveHandeling : persoonslijstPersisteerResultaat.getAdministratieveHandelingen()) {
            new JdbcTemplate(migratieDataSource).update("update kern.admhnd set statuslev = 4 where id = ?", administratieveHandeling.getId());
        }
    }

    private void rondverteer(final Long persoonId, final String suffix) {
        LOG.info("Rondverteer");
        try {
            final BrpPersoonslijst brpPersoonslijst = brpPersoonslijstService.bevraagPersoonslijstOpTechnischeSleutel(persoonId);
            final Lo3Persoonslijst lo3Persoonslijst = converteerBrpNaarLo3Service.converteerBrpPersoonslijst(brpPersoonslijst);
            debugOutputXmlEnHtml(lo3Persoonslijst, TestCasusOutputStap.STAP_ROND, suffix);
        } catch (final Exception e) {
            final Foutmelding fout = new Foutmelding("Fout tijdens rondverteren (persoon).", e);
            LOG.error(fout.getContext(), e);
            debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_ROND, suffix + SUFFIX_EXCEPTION);
        }
    }

    private void controleerSoortenAdministratieveHandeling(final String soorten, final LeveringMutatieberichtTestResultaat result) {
        final String expected = leesVerwachteString(TestCasusOutputStap.STAP_INITIEREN, SUFFIX_SOORTEN_ADM_HAND);

        final String htmlSoorten = debugOutputString(soorten, TestCasusOutputStap.STAP_INITIEREN, SUFFIX_SOORTEN_ADM_HAND);
        if ((expected != null) && !"".equals(expected) && !expected.equals(soorten)) {
            final String htmlVerschillen = debugOutputString("Bijgehouden soorten administratieve handeling:\n" + soorten + "\n\nVerwachting:\n" +
                            expected,
                    TestCasusOutputStap.STAP_INITIEREN, SUFFIX_SOORTEN_ADM_HAND + "-verschillen");
            result.setInitieren(
                    new TestStap(TestStatus.NOK, "Er zijn onverwachte soorten administratieve handeling geconstateerd", htmlSoorten, htmlVerschillen));
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

    private PersoonslijstPersisteerResultaat initierenPersoon(final File persoonFile, final int index, final Lo3BerichtenBron bron,
                                                              final Long teOverschrijvenPersoonId) throws IOException, ExcelAdapterException,
            Lo3SyntaxException, OngeldigePersoonslijstException,
            TeLeverenAdministratieveHandelingenAanwezigException {
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
                debugOutputXmlEnHtml(Logging.getLogging(), TestCasusOutputStap.STAP_INITIEREN,
                        (Lo3BerichtenBron.INITIELE_VULLING.equals(bron) ? IDENTIFIER_INITIELE_VULLING : IDENTIFIER_BIJHOUDING) + "-syntax");
                throw e;
            }

            // Parse persoonslijst
            final Lo3Persoonslijst lo3Persoonslijst = LO3_PARSER.parse(lo3InhoudNaSyntaxControle);
            debugOutputXmlEnHtml(lo3Persoonslijst, TestCasusOutputStap.STAP_INITIEREN,
                    (Lo3BerichtenBron.INITIELE_VULLING.equals(bron) ? IDENTIFIER_INITIELE_VULLING : IDENTIFIER_BIJHOUDING) + "-lo3");

            // Controleer precondities
            final Lo3Persoonslijst schoneLo3Persoonslijst;
            try {
                schoneLo3Persoonslijst = preconditieService.verwerk(lo3Persoonslijst);
            } catch (final OngeldigePersoonslijstException e) {
                debugOutputXmlEnHtml(Logging.getLogging(), TestCasusOutputStap.STAP_INITIEREN,
                        (Lo3BerichtenBron.INITIELE_VULLING.equals(bron) ? IDENTIFIER_INITIELE_VULLING : IDENTIFIER_BIJHOUDING) + "-precondities");
                throw e;
            }
            // LOG.info("Lo3 persoonslijst: {}", lo3Pl);

            final BrpPersoonslijst brpPl = converteerLo3NaarBrpService.converteerLo3Persoonslijst(schoneLo3Persoonslijst);
            debugOutputXmlEnHtml(brpPl, TestCasusOutputStap.STAP_INITIEREN,
                    (Lo3BerichtenBron.INITIELE_VULLING.equals(bron) ? IDENTIFIER_INITIELE_VULLING : IDENTIFIER_BIJHOUDING) + "-brp");
            // LOG.info("BRP persoonslijst: {}", brpPl);

            final Lo3Bericht lo3Bericht = new Lo3Bericht("persoon", bron, new Timestamp(System.currentTimeMillis()), "ExcelData", true);
            LOG.info("Logging: {}", lo3Bericht);

            final PersoonslijstPersisteerResultaat result =
                    teOverschrijvenPersoonId == null ? brpPersoonslijstService.persisteerPersoonslijst(brpPl, lo3Bericht)
                            : brpPersoonslijstService.persisteerPersoonslijst(brpPl, teOverschrijvenPersoonId, lo3Bericht);
            LOG.info("Result: {}", result);
            LOG.info("Persoon: {}", result.getPersoon());
            LOG.info("Administratieve handeling(en): {}", result.getAdministratieveHandelingen());

            LOG.info("Persoon.id: {}", result.getPersoon().getId());
            // final PersoonHisVolledig persoonHisVolledig =
            // blobifierService.leesBlob(result.getKey().getId());
            // LOG.info("PersoonHisVolledig: {}", persoonHisVolledig);

            return result;
        } finally {
            debugOutputXmlEnHtml(Logging.getLogging(), TestCasusOutputStap.STAP_INITIEREN,
                    (Lo3BerichtenBron.INITIELE_VULLING.equals(bron) ? IDENTIFIER_INITIELE_VULLING : IDENTIFIER_BIJHOUDING) + "-logging");

            debugOutputString(SynchronisatieLogging.getMelding(), TestCasusOutputStap.STAP_INITIEREN,
                    (Lo3BerichtenBron.INITIELE_VULLING.equals(bron) ? IDENTIFIER_INITIELE_VULLING : IDENTIFIER_BIJHOUDING) + "-synclogging");

            LoggingContext.reset();
            Logging.destroyContext();
        }

    }

    private BijhoudingAntwoordBericht initierenBzmBijhouding(final File bijhoudingFile, final LeveringMutatieberichtTestResultaat result,
                                                             final Long persoonId, final Long lockVersie)
            throws IOException, ParseException, SAXException, ParserConfigurationException, XPathExpressionException, XMLStreamException,
            TransformerException {
        final String xml;
        final String bijhouder;
        try (FileInputStream fis = new FileInputStream(bijhoudingFile)) {
            // Vervang objectsleutel
            final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            documentBuilderFactory.setNamespaceAware(true);

            final Document requestXmlDocument = documentBuilderFactory.newDocumentBuilder().parse(fis);
            vervangSleutels(requestXmlDocument, persoonId);
            bijhouder = bepaalBijhouder(requestXmlDocument);

            // Output als XML
            final TransformerFactory transformerFactory = TransformerFactory.newInstance();

            final StringWriter xmlMetVervangenObjectsleutel = new StringWriter();
            transformerFactory.newTransformer().transform(new DOMSource(requestXmlDocument), new StreamResult(xmlMetVervangenObjectsleutel));
            xml = xmlMetVervangenObjectsleutel.toString();
        }

        final String oin = bepaalOinBzmBijhouder(bijhouder);

        final BijhoudingVerzoekBerichtParser parser = new BijhoudingVerzoekBerichtParser();
        final BijhoudingVerzoekBericht bericht = parser.parse(new StreamSource(new StringReader(xml)));
        bericht.setOinWaardeOndertekenaar(oin);
        bericht.setOinWaardeTransporteur(oin);

        final BijhoudingAntwoordBericht bijhoudingResultaat = bijhoudingsBerichtVerwerker.verwerkBrpBericht(bericht);

        LOG.info("Bijhouding resultaat: {}", bijhoudingResultaat);

        if ((bijhoudingResultaat.getMeldingen() != null) && !bijhoudingResultaat.getMeldingen().isEmpty()) {
            for (final MeldingElement melding : bijhoudingResultaat.getMeldingen()) {
                LOG.info("Melding: {} - {}", melding.getSoortNaam(), melding.getRegel().getMelding());
            }
        }

        if (!VerwerkingsResultaat.GESLAAGD.getNaam().equals(bijhoudingResultaat.getResultaat().getVerwerking().getWaarde())) {
            throw new IllegalStateException("Bijhouding mislukt");
        }
        LOG.info("Administratieve handeling: {}", bijhoudingResultaat.getAdministratieveHandelingAntwoord());
        return bijhoudingResultaat;
    }

    private String bepaalOinBzmBijhouder(final String partijCode) {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(migratieDataSource);

        List<Integer> partijIds = jdbcTemplate.queryForList("select id from kern.partij where code = ?", Integer.class, partijCode);
        if (partijIds.isEmpty()) {
            LOG.info("Partij {} aanmaken", partijCode);
            jdbcTemplate.update("insert into kern.partij(code, naam) values (?, ?)", partijCode, "test" + partijCode);
            partijIds = jdbcTemplate.queryForList("select id from kern.partij where code = ?", Integer.class, partijCode);
            jdbcTemplate.update("insert into kern.his_partij(partij, tsreg, naam, datingang, indverstrbeperkingmogelijk) values (?, ?, ?, ?, ?)",
                    partijIds.get(0), Timestamp.from(DatumUtil.nuAlsZonedDateTime().toInstant()), "test" + partijCode, 20120101, false);
            dalCacheController.maakCachesLeeg();
        }

        final String oin = "oin4-" + partijCode;
        jdbcTemplate.update("update kern.partij set oin = ? where code = ?", oin, partijCode);

        List<Integer> partijRolIds = jdbcTemplate.queryForList("select id from kern.partijrol where rol = 2 and partij = ?", Integer.class, partijIds.get(0));
        if (partijRolIds.isEmpty()) {
            LOG.info("Aanmaken partij rol");
            jdbcTemplate.update("insert into kern.partijrol(partij,rol,datingang,indag) values (?, 2, 20120101, true)", partijIds.get(0));
            partijRolIds = jdbcTemplate.queryForList("select id from kern.partijrol where rol = 2 and partij = ?", Integer.class, partijIds.get(0));
        }
        LOG.info("Partijrol.id: {}", partijRolIds.get(0));
        LOG.info("Partijrol: {}", jdbcTemplate.queryForMap("select * from kern.partijrol where id = ?", partijRolIds.get(0)));

        List<Integer> partijRolHisIds = jdbcTemplate.queryForList("select id from kern.his_partijrol where partijrol = ?", Integer.class, partijRolIds.get(0));
        if (partijRolHisIds.isEmpty()) {
            jdbcTemplate.update("insert into kern.his_partijrol(partijrol,datingang,tsreg) values (?, 20120101, now())", partijRolIds.get(0));
        }
        LOG.info("Partijrol (history): {}", jdbcTemplate.queryForMap("select * from kern.his_partijrol where partijrol = ?", partijRolIds.get(0)));

        List<Integer>
                toegBijhAutIds =
                jdbcTemplate.queryForList("select id from autaut.toegangbijhautorisatie where geautoriseerde = ?", Integer.class, partijRolIds.get(0));
        if (toegBijhAutIds.isEmpty()) {
            LOG.info("Aanmaken toegangbijhautorisatie");
            // Aanmaken bijh aut
            String naam = "bijhaut4-" + partijCode + "-" + getNaam();
            jdbcTemplate.update("insert into autaut.bijhautorisatie(indmodelautorisatie, naam, datingang, indag) values (false, ?, 20120101, true)", naam);
            final List<Integer> bijAutIds = jdbcTemplate.queryForList("select id from autaut.bijhautorisatie where naam = ?", Integer.class, naam);
            jdbcTemplate
                    .update("insert into autaut.his_bijhautorisatie(bijhautorisatie, naam, datingang, tsreg) values (?, ?, 20120101, now())", bijAutIds.get(0),
                            naam);
            // Toegang
            jdbcTemplate.update("insert into autaut.toegangbijhautorisatie(geautoriseerde, bijhautorisatie, datingang, indag) values (?, ?, 20120101, true)",
                    partijRolIds.get(0), bijAutIds.get(0));
            toegBijhAutIds =
                    jdbcTemplate.queryForList("select id from autaut.toegangbijhautorisatie where geautoriseerde = ?", Integer.class, partijRolIds.get(0));
            jdbcTemplate.update("insert into autaut.his_toegangbijhautorisatie(toegangbijhautorisatie, datingang, tsreg) values (?, 20120101, now())",
                    toegBijhAutIds.get(0));

            // Bijh Adm hnd
            jdbcTemplate.update("insert into autaut.bijhautorisatiesrtadmhnd(bijhautorisatie,srtadmhnd,indag) select ?, id, true from kern.srtadmhnd",
                    bijAutIds.get(0));
            jdbcTemplate
                    .update("insert into autaut.his_bijhautorisatiesrtadmhnd(bijhautorisatiesrtadmhnd,tsreg) select id, now() from autaut"
                                    + ".bijhautorisatiesrtadmhnd where bijhautorisatie = ?",
                            bijAutIds.get(0));
        }
        LOG.info("ToegangLevsAut: {}", toegBijhAutIds.get(0));

        return oin;
    }


    private void vervangSleutels(final Document doc, final long persoonId) throws XPathExpressionException {
        vervangPersoonSleutels(doc);
        vervangHuwelijkSleutels(doc, persoonId);
    }

    private static final Pattern OBJECT_SLEUTEL_PATTERN = Pattern.compile("^\\$\\$([^=]+)=(.*)\\$\\$$");
    private static final Pattern VOORKOMEN_SLEUTEL_PATTERN = Pattern.compile("^\\$\\$actueel\\$\\$$");

    private void vervangPersoonSleutels(final Document doc) throws XPathExpressionException {
        final NodeList persoonNodes = (NodeList) XPathFactory.newInstance().newXPath().evaluate("//*[local-name()='persoon']", doc, XPathConstants.NODESET);
        for (int index = 0; index < persoonNodes.getLength(); index++) {
            final Node node = persoonNodes.item(index);
            final Node objectSleutelNode = node.getAttributes().getNamedItem("brp:objectSleutel");
            if (objectSleutelNode != null) {
                Matcher matcher = OBJECT_SLEUTEL_PATTERN.matcher(objectSleutelNode.getNodeValue());
                if (matcher.matches()) {
                    final Map<String, Object> persoon = zoekPersoon(matcher.group(1), matcher.group(2));
                    final Long persoonId = (Long) persoon.get("id");
                    final Long lockversie = (Long) persoon.get("lockversie");
                    final String objectSleutel = objectSleutelService.maakPersoonObjectSleutel(persoonId, lockversie).maskeren();
                    objectSleutelNode.setNodeValue(objectSleutel);

                    // Eventueel voorkomen sleutels voor groepen binnen persoon
                }
            }
        }
    }

    private Map<String, Object> zoekPersoon(String attribuut, String waarde) {
        final JdbcTemplate jdbc = new JdbcTemplate(migratieDataSource);
        final Map<String, Object> resultaat;
        switch (attribuut) {
            case "anr":
                resultaat = jdbc.queryForMap("select id, lockversie from kern.pers where anr = ?", waarde);
                break;
            case "bsn":
                resultaat = jdbc.queryForMap("select id, lockversie from kern.pers where bsn = ?", waarde);
                break;
            default:
                throw new IllegalArgumentException("Attribuut " + attribuut + " niet ondersteund voor zoeken persoon");
        }
        return resultaat;
    }

    private void vervangHuwelijkSleutels(final Document doc, final long persoonId) throws XPathExpressionException {
        final NodeList nodes = (NodeList) XPathFactory.newInstance().newXPath().evaluate("//*[local-name()='huwelijk']", doc, XPathConstants.NODESET);
        for (int index = 0; index < nodes.getLength(); index++) {
            final Node node = nodes.item(index);
            final Node objectSleutelNode = node.getAttributes().getNamedItem("brp:objectSleutel");
            if (objectSleutelNode != null) {
                Matcher matcher = OBJECT_SLEUTEL_PATTERN.matcher(objectSleutelNode.getNodeValue());
                if (matcher.matches()) {
                    final Long relatieId = zoekRelatie(persoonId, SoortRelatie.HUWELIJK.getId(), matcher.group(1), matcher.group(2));
                    final String objectSleutel = objectSleutelService.maakRelatieObjectSleutel(relatieId).maskeren();
                    objectSleutelNode.setNodeValue(objectSleutel);

                    vervangRelatieSleutels(node, relatieId);
                }
            }
        }
    }

    private Long zoekRelatie(long persoonId, long soortRelatieId, String attribuut, String waarde) {
        final JdbcTemplate jdbc = new JdbcTemplate(migratieDataSource);
        final Long resultaat;
        switch (attribuut) {
            case "datumAanvang":
                resultaat =
                        jdbc.queryForObject(
                                "select relatie.id from kern.relatie, kern.betr where relatie.id = betr.relatie and betr.pers = ? and relatie.srt = ? and "
                                        + "relatie.dataanv = ?",
                                Long.class, persoonId, soortRelatieId,
                                Integer.valueOf(waarde));
                break;
            default:
                throw new IllegalArgumentException("Attribuut " + attribuut + " niet ondersteund voor zoeken relatie");
        }
        return resultaat;
    }

    private void vervangRelatieSleutels(Node parent, Long relatieId) throws XPathExpressionException {
        final NodeList nodes = (NodeList) XPathFactory.newInstance().newXPath().evaluate("//*[local-name()='relatie']", parent, XPathConstants.NODESET);
        for (int index = 0; index < nodes.getLength(); index++) {
            final Node node = nodes.item(index);
            final Node voorkomenSleutelNode = node.getAttributes().getNamedItem("brp:voorkomenSleutel");
            if (voorkomenSleutelNode != null) {
                Matcher matcher = VOORKOMEN_SLEUTEL_PATTERN.matcher(voorkomenSleutelNode.getNodeValue());
                if (matcher.matches()) {
                    final JdbcTemplate jdbc = new JdbcTemplate(migratieDataSource);
                    // Eventueel andere mogelijkheden dan alleen 'actueel' inbouwen
                    final Long voorkomenId = jdbc.queryForObject("select id from kern.his_relatie where relatie = ?", Long.class, relatieId);
                    voorkomenSleutelNode.setNodeValue(voorkomenId.toString());
                }
            }
        }
    }


    private String bepaalBijhouder(final Document doc) throws XPathExpressionException {
        final NodeList
                nodes =
                (NodeList) XPathFactory.newInstance().newXPath().evaluate("//*[local-name()='zendendePartij']/text()", doc, XPathConstants.NODESET);
        if (nodes.getLength() == 0) {
            throw new IllegalArgumentException("Geen zendendePartij gevonden");
        }
        final String bijhouder = nodes.item(0).getNodeValue();
        LOG.info("Bijhouder: {}", bijhouder);
        return bijhouder;
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

            initierenPartij(migratieDataSource, lo3Autorisatie.getAfnemersindicatie());

            final BrpAutorisatie brpAutorisatie = ((ConverteerLo3NaarBrpServiceImpl) converteerLo3NaarBrpService).converteerLo3Autorisatie(lo3Autorisatie,
                    (stap, object) -> debugOutputXmlEnHtml(object, TestCasusOutputStap.STAP_INITIEREN, "autorisatie-" + stap.name()));

            final List<ToegangLeveringsAutorisatie> toegangLeveringsAutorisaties = brpAutorisatieService.persisteerAutorisatie(brpAutorisatie);
            final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = bepaalRelevanteToegangLeveringsautorisatie(toegangLeveringsAutorisaties);

            return toegangLeveringsAutorisatie.getId();

        } catch (final IOException | PartijNietGevondenException e) {
            final Foutmelding fout = new Foutmelding("Fout tijdens initieren (autorisatie).", e);
            final String htmlFout = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_INITIEREN, SUFFIX_EXCEPTION);
            result.setInitieren(new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden (initieren autorisatie)", htmlFout, null));

        } finally {
            LoggingContext.reset();
            Logging.destroyContext();
        }

        return null;
    }

    protected final void initierenPartij(final DataSource dataSource, final String partijCode) {
        final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        List<Integer> partijIds = jdbcTemplate.queryForList("select id from kern.partij where code = ?", Integer.class, partijCode);
        if (partijIds.isEmpty()) {
            LOG.info("Partij {} aanmaken", partijCode);
            jdbcTemplate.update("insert into kern.partij(code, naam) values (?, ?)", partijCode, "test" + partijCode);
            partijIds = jdbcTemplate.queryForList("select id from kern.partij where code = ?", Integer.class, partijCode);
            jdbcTemplate.update("insert into kern.his_partij(partij, tsreg, naam, datingang, indverstrbeperkingmogelijk) values (?, ?, ?, ?, ?)",
                    partijIds.get(0), Timestamp.from(DatumUtil.nuAlsZonedDateTime().toInstant()), "test" + partijCode, 20120101, false);
            dalCacheController.maakCachesLeeg();
        }
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
     * @return autorisatie filename
     */
    private String getAutorisatieFilename() {
        final String[] foundFiles = inputFolder.list((dir, name) -> name.startsWith("autorisatie") && name.endsWith(".csv"));
        return foundFiles.length == 0 ? "autorisatie.csv" : foundFiles[0];
    }

    private void outputPersoonBlobs(final Long persoonId, final LeveringMutatieberichtTestResultaat result, final String suffix) {
        if (persoonId == null) {
            return;
        }

        final DefaultTransactionDefinition blobifyTransactionDefinition = new DefaultTransactionDefinition();
        blobifyTransactionDefinition.setName("Testcase-blobify");
        blobifyTransactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        final TransactionStatus blobifyTransaction = transactionManagerMaster.getTransaction(blobifyTransactionDefinition);

        try {
            final Persoon persoon = brpEntityManager.find(Persoon.class, persoonId);
            final PersoonBlob persoonBlob = Blobber.maakBlob(persoon);
            final String persoonBlobString = new String(Blobber.toJsonBytes(persoonBlob));
            debugOutputString(persoonBlobString, TestCasusOutputStap.STAP_INITIEREN, suffix + "-hoofdpersoon");
        } catch (final Exception e) {
            LOG.warn("Fout tijdens blobificeren", e);
            blobifyTransaction.setRollbackOnly();
            final Foutmelding fout = new Foutmelding("Fout tijdens initieren (outputPersoonBlobs).", e);
            final String htmlFout = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_INITIEREN, SUFFIX_EXCEPTION);
            result.setInitieren(new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden (outputPersoonBlobs)", htmlFout, null));
        } finally {
            if (blobifyTransaction.isRollbackOnly()) {
                LOG.info("Rollback blobify transactie");
                transactionManagerMaster.rollback(blobifyTransaction);
            } else {
                LOG.info("Commit blobify transactie");
                transactionManagerMaster.commit(blobifyTransaction);
            }
        }
    }

    private void outputPersoonsgegevens(final Long persoonId, final LeveringMutatieberichtTestResultaat result, final String suffix) {
        if (persoonId == null) {
            return;
        }

        try {
            final Persoonslijst persoonslijst = persoonslijstService.getById(persoonId);
            final StringBuilder sb = new StringBuilder("Persoon:\n");
            sb.append(ModelAfdruk.maakAfdruk(persoonslijst.getMetaObject())).append("\n");
            sb.append("\nVerantwoording (" + persoonslijst.getAdministratieveHandelingen().size() + "):\n");
            for (final nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling administratieveHandeling : persoonslijst.getAdministratieveHandelingen()) {
                sb.append("  administratieve handeling; id: ").append(administratieveHandeling.getId()).append("\n");
                sb.append("    soort: ").append(administratieveHandeling.getSoort()).append("\n");
                sb.append("    partij: ").append(administratieveHandeling.getPartijCode()).append("\n");
                sb.append("    toelichting ontlening: ").append(administratieveHandeling.getToelichtingOntlening()).append("\n");
                sb.append("    tijdstip registratie: ").append(administratieveHandeling.getTijdstipRegistratie()).append("\n");
                sb.append("    tijdstip levering: ").append(administratieveHandeling.getTijdstipLevering()).append("\n");
                sb.append("    acties (" + administratieveHandeling.getActies().size() + "):\n");
                for (final Actie actie : administratieveHandeling.getActies()) {
                    sb.append("      actie; id: ").append(actie.getId()).append("\n");
                    sb.append("        soort: ").append(actie.getSoort()).append("\n");
                    sb.append("        soort: ").append(actie.getPartijCode()).append("\n");
                    sb.append("        soort: ").append(actie.getDatumOntlening()).append("\n");
                    sb.append("        soort: ").append(actie.getTijdstipRegistratie()).append("\n");
                }
            }
            debugOutputString(sb.toString(), TestCasusOutputStap.STAP_INITIEREN, suffix);
        } catch (final Exception e) {
            LOG.warn("Fout tijdens blobificeren", e);
            final Foutmelding fout = new Foutmelding("Fout tijdens initieren (outputPersoonsgegevens).", e);
            final String htmlFout = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_INITIEREN, SUFFIX_EXCEPTION);
            result.setInitieren(new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden (outputPersoonsgegevens)", htmlFout, null));
        }

    }

    /**
     * Test lezen uit brp.
     * @param persoonId persoon id
     * @param result resultaat
     */
    private Persoonslijst testLeesPersoon(final Long persoonId, final TestLeveringResultaat result) {
        if (persoonId != null) {
            LOG.info("Test lezen en mappen uit BRP ...");
            try {
                // Lezen uit database
                final Persoonslijst persoonslijst = persoonslijstService.getById(persoonId);

                if (persoonslijst == null) {
                    throw new IllegalStateException("Geen persoon gelezen uit database");
                }

                try {
                    final Set<Stapel> istStapels = new HashSet<>(istTabelRepository.leesIstStapels(persoonslijst.getId()));

                    // Mapping
                    final BrpPersoonslijst brp = persoonslijstMapper.map(persoonslijst, istStapels);
                    final String htmlBrp = debugOutputXmlEnHtml(brp, TestCasusOutputStap.STAP_LEZEN, result.getSoortAdministratieveHandeling());
                    result.setMapping(new TestStap(TestStatus.OK, null, htmlBrp, null));

                    // Vergelijk expected
                    final BrpPersoonslijst expectedBrp =
                            leesVerwachteBrpPersoonslijst(TestCasusOutputStap.STAP_LEZEN, result.getSoortAdministratieveHandeling());
                    if (expectedBrp != null) {
                        final StringBuilder verschillenLog = new StringBuilder();
                        if (!VergelijkXml.vergelijkXmlNegeerActieId(expectedBrp, brp, true, verschillenLog)) {
                            final Foutmelding fout = new Foutmelding("Vergelijking (lezen)", "Inhoud is ongelijk (mapping)", verschillenLog.toString());
                            final String htmlVerschillen =
                                    debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_LEZEN, result.getSoortAdministratieveHandeling() +
                                            SUFFIX_VERSCHILLEN);
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

                return persoonslijst;

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

    private Autorisatiebundel leesAbonnementCacheElement(final Integer toegangLeveringsautorisatieId, final TestLeveringResultaat result) {
        LOG.info("leesAbonnementCacheElement(toegangLeveringsautorisatieId={})", toegangLeveringsautorisatieId);
        try {
            cacheController.herlaadCaches();
            final ToegangLeveringsAutorisatie toegangLeveringsautorisatie =
                    toegangLeveringsautorisatieService.geefToegangLeveringsAutorisatie(toegangLeveringsautorisatieId);

            final Dienst dienst = bepaalRelevanteDienst(AutAutUtil.zoekDiensten(toegangLeveringsautorisatie.getLeveringsautorisatie()));

            return new Autorisatiebundel(toegangLeveringsautorisatie, dienst);
        } catch (final Exception e) {
            final Foutmelding fout = new Foutmelding("Fout tijdens lezen abonnement.", e);
            final String htmlFout = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_LEZEN, result.getSoortAdministratieveHandeling() +
                    SUFFIX_EXCEPTION);

            result.setMapping(new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden (lezen abonnement)", htmlFout, null));
            return null;
        }
    }

    private Dienst bepaalRelevanteDienst(final Collection<Dienst> diensten) {
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
            LOG.info("Kandidaat: {} {}", dienst.getSoortDienst(), dienst.getEffectAfnemerindicaties());
            if (isEquals(soortDienst, dienst.getSoortDienst()) && isEquals(effectAfnemerindicaties, dienst.getEffectAfnemerindicaties())) {
                return dienst;
            }
        }

        throw new IllegalArgumentException(
                "Relevante dienst '" + soortDienst + "'  met effect afnemersindicaties '" + effectAfnemerindicaties + "' kon niet worden bepaald.");
    }

    private boolean isEquals(final Object object1, final Object object2) {
        return object1 == null ? object2 == null : object1.equals(object2);
    }

    private nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling bepaalAdministratieveHandeling(final Persoonslijst persoonslijst,
                                                                                                    final Long administratieveHandelingId, final
                                                                                                    TestLeveringResultaat resultaat) {
        try {

            if (PLAATSING_BERICHT_ADM_HAND_ID.equals(administratieveHandelingId)) {
                resultaat.setSoortAdministratieveHandeling(PLAATSING_SOORT_ADM_HAND);
            } else {
                final nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling result =
                        bepaalAdministratieveHandeling(persoonslijst, administratieveHandelingId);

                final String soortAdministratieveHandeling = result.getSoort().toString();
                resultaat.setSoortAdministratieveHandeling(soortAdministratieveHandeling);

                final String expectedAdministratieveHandelingSoorten = leesVerwachteString(TestCasusOutputStap.STAP_INITIEREN, SUFFIX_SOORTEN_ADM_HAND);
                if ((expectedAdministratieveHandelingSoorten == null) || "".equals(expectedAdministratieveHandelingSoorten)) {
                    resultaat.setSoortAdministratieveHandelingStatus(TestStatus.GEEN_VERWACHTING);
                } else {
                    boolean found = false;
                    for (final String migExpectedAdmHndSoort : expectedAdministratieveHandelingSoorten.split(COMMA_SEPERATOR)) {
                        if (migExpectedAdmHndSoort.equals(soortAdministratieveHandeling)) {
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
            final String htmlFout = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_LEZEN, resultaat.getSoortAdministratieveHandeling() +
                    SUFFIX_EXCEPTION);

            resultaat.setMapping(new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden (lezen administratieve handeling)", htmlFout, null));
        }
        return null;
    }

    private nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling bepaalAdministratieveHandeling(final Persoonslijst persoonslijst,
                                                                                                    final Long administratieveHandelingId) {
        for (final nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling administratieveHandeling : persoonslijst.getAdministratieveHandelingen()) {
            if (administratieveHandeling.getId().equals(administratieveHandelingId)) {
                return administratieveHandeling;
            }
        }

        return null;
    }

    private List<BerichtImpl> testBepalenBerichten(final Persoonslijst persoon, final Autorisatiebundel leveringAutorisatie,
                                                   final nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling administratieveHandeling, final
                                                   TestLeveringResultaat resultaat) {
        try {
            final List<BerichtImpl> berichten;
            if (administratieveHandeling == null) {
                LOG.info("Bepalen Ag01 bericht");
                berichten = Collections.<BerichtImpl>singletonList((BerichtImpl) berichtFactory.maakAg01Bericht(persoon));
            } else {
                final IdentificatienummerMutatie identificatienummerMutatie = new IdentificatienummerMutatie(persoon.getMetaObject(),
                        administratieveHandeling);

                final SoortDienst soortDienst = leveringAutorisatie.getSoortDienst();
                final SoortAdministratieveHandeling soort = administratieveHandeling.getSoort();
                final CategorieAdministratieveHandeling categorie = soort.getCategorie();
                LOG.info("Bepalen bericht");
                LOG.info("dienst: {}, categorie: {}, soort: {}", new Object[]{soortDienst, categorie, soort});
                LOG.info("persoon: {}", new Object[]{persoon});

                final Map<Persoonslijst, Populatie> populatieMap = new HashMap<>();
                populatieMap.put(persoon, Populatie.BINNEN);

                final List<Bericht> syncBerichten =
                        berichtFactory.maakBerichten(leveringAutorisatie, populatieMap, administratieveHandeling, identificatienummerMutatie);

                berichten = new ArrayList<BerichtImpl>();
                for (final Bericht syncBericht : syncBerichten) {
                    berichten.add((BerichtImpl) syncBericht);
                }
                //
                // if (berichten == null || berichten.size() != 1) {
                // throw new IllegalArgumentException("Er kon niet eenduidig 1 bericht worden
                // bepaald");
                // }
                // bericht = (BerichtImpl) berichten.get(0);
            }

            return berichten;
        } catch (final Exception e) {
            final Foutmelding fout = new Foutmelding("Fout tijdens bepalen bericht.", e);
            final String htmlFout = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_LEZEN, resultaat.getSoortAdministratieveHandeling() +
                    SUFFIX_EXCEPTION);

            resultaat.setMapping(new TestStap(TestStatus.EXCEPTIE, "Er is een exceptie opgetreden (bepalen bericht)", htmlFout, null));
            return null;
        }
    }

    private void testConversie(final BerichtImpl bericht, final String volgnummer, final TestLeveringResultaat leveringResult,
                               final TestLeveringBerichtResultaat berichtResult) {
        if (bericht == null) {
            return;
        }
        LOG.info("Test terugconversie ...");
        try {
            // outputConversieStappen(bericht, leveringResult, berichtResult);

            LOG.info("Bericht.converteerNaarLo3 ...");
            bericht.converteerNaarLo3(new ConversieCache());
            LOG.info("Bericht.converteerNaarLo3 done...");

            final List<Lo3CategorieWaarde> categorieen = Lo3CategorieWaardenSorter.sorteer(getBerichtImplCategorieen(bericht));
            final String htmlCategorieen = debugOutputLo3CategorieWaarden(categorieen, TestCasusOutputStap.STAP_TERUG,
                    leveringResult.getSoortAdministratieveHandeling() + DASH_SEPERATOR + berichtResult.getSoortBericht() + volgnummer);

            final List<Lo3CategorieWaarde> expectedCategorieen =
                    Lo3CategorieWaardenSorter.sorteer(leesVerwachteLo3CategorieWaarden(TestCasusOutputStap.STAP_TERUG,
                            leveringResult.getSoortAdministratieveHandeling() + DASH_SEPERATOR + berichtResult.getSoortBericht() + volgnummer));
            if (expectedCategorieen != null) {
                final StringBuilder verschillenLog = new StringBuilder();
                if (!nl.bzk.migratiebrp.test.levering.mutatiebericht.Lo3CategorieWaardenVergelijker.vergelijk(verschillenLog, expectedCategorieen,
                        categorieen)) {
                    final Foutmelding fout = new Foutmelding("Vergelijking (conversie)", "Inhoud is ongelijk (conversie)", verschillenLog.toString());
                    final String htmlVerschillen = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_TERUG, leveringResult
                            .getSoortAdministratieveHandeling()
                            + DASH_SEPERATOR + berichtResult.getSoortBericht() + volgnummer + SUFFIX_VERSCHILLEN);
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
            final String htmlFout = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_TERUG,
                    leveringResult.getSoortAdministratieveHandeling() + DASH_SEPERATOR + berichtResult.getSoortBericht() + volgnummer + SUFFIX_EXCEPTION);

            berichtResult.setConversie(new TestStap(TestStatus.EXCEPTIE, ER_IS_EEN_EXCEPTIE_OPGETREDEN, htmlFout, null));
        }
    }

    private boolean testFilter(final Autorisatiebundel leveringAutorisatie, final BerichtImpl bericht, final String volgnummer,
                               final TestLeveringResultaat leveringResult, final TestLeveringBerichtResultaat berichtResult) {
        LOG.info("Test filter ...");
        try {
            final List<String> lo3Filterrubrieken =
                    lo3FilterRubriekRepository.haalLo3FilterRubriekenVoorDienstbundel(leveringAutorisatie.getDienst().getDienstbundel().getId());
            final StringBuilder debugOutput = new StringBuilder(lo3Filterrubrieken.size() * 10);
            for (final String lo3Filterrubriek : lo3Filterrubrieken) {
                debugOutput.append(lo3Filterrubriek).append("\n");
            }
            debugOutputString(debugOutput.toString(), TestCasusOutputStap.STAP_FILTER,
                    leveringResult.getSoortAdministratieveHandeling() + DASH_SEPERATOR + berichtResult.getSoortBericht() + volgnummer + "-rubrieken");

            // Uitvoeren filter
            final boolean uitvoerenLevering = bericht.filterRubrieken(lo3Filterrubrieken);

            final List<Lo3CategorieWaarde> gefilterdeCategorieen = Lo3CategorieWaardenSorter.sorteer(getBerichtImplCategorieenGefilterd(bericht));
            final String htmlGefilterdeCategorieen = debugOutputLo3CategorieWaarden(gefilterdeCategorieen, TestCasusOutputStap.STAP_FILTER,
                    leveringResult.getSoortAdministratieveHandeling() + DASH_SEPERATOR + berichtResult.getSoortBericht() + volgnummer);

            final List<Lo3CategorieWaarde> expectedCategorieen =
                    Lo3CategorieWaardenSorter.sorteer(leesVerwachteLo3CategorieWaarden(TestCasusOutputStap.STAP_FILTER,
                            leveringResult.getSoortAdministratieveHandeling() + DASH_SEPERATOR + berichtResult.getSoortBericht() + volgnummer));
            if (expectedCategorieen != null) {
                final StringBuilder verschillenLog = new StringBuilder();
                if (!nl.bzk.migratiebrp.test.levering.mutatiebericht.Lo3CategorieWaardenVergelijker.vergelijk(verschillenLog, expectedCategorieen,
                        gefilterdeCategorieen)) {
                    final Foutmelding fout = new Foutmelding("Vergelijking (filter)", "Inhoud is ongelijk (filter)", verschillenLog.toString());
                    final String htmlVerschillen = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_FILTER, leveringResult
                            .getSoortAdministratieveHandeling()
                            + DASH_SEPERATOR + berichtResult.getSoortBericht() + volgnummer + SUFFIX_VERSCHILLEN);
                    berichtResult
                            .setFilteren(new TestStap(TestStatus.NOK, "Er zijn inhoudelijke verschillen (filter)", htmlGefilterdeCategorieen,
                                    htmlVerschillen));
                } else {
                    berichtResult.setFilteren(new TestStap(TestStatus.OK, null, htmlGefilterdeCategorieen, null));
                }
            } else {
                berichtResult.setFilteren(new TestStap(TestStatus.GEEN_VERWACHTING, null, htmlGefilterdeCategorieen, null));
            }

            return uitvoerenLevering;
        } catch (final Exception e) {
            final Foutmelding fout = new Foutmelding("Fout tijdens filter.", e);
            final String htmlFout = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_FILTER,
                    leveringResult.getSoortAdministratieveHandeling() + DASH_SEPERATOR + berichtResult.getSoortBericht() + volgnummer + SUFFIX_EXCEPTION);

            berichtResult.setFilteren(new TestStap(TestStatus.EXCEPTIE, ER_IS_EEN_EXCEPTIE_OPGETREDEN, htmlFout, null));
        }

        return false;
    }

    private void testBericht(final BerichtImpl bericht, final String volgnummer, final boolean uitvoerenLevering, final TestLeveringResultaat
            leveringResult,
                             final TestLeveringBerichtResultaat berichtResult) {
        LOG.info("Test bericht ...");
        try {
            final String verwachteString = leesVerwachteString(TestCasusOutputStap.STAP_BERICHT,
                    leveringResult.getSoortAdministratieveHandeling() + DASH_SEPERATOR + berichtResult.getSoortBericht() + volgnummer);
            final String expectedPlatteTekst = Lo3BerichtSorter.sorteer(verwachteString);

            if (uitvoerenLevering) {
                final String platteTekst = Lo3BerichtSorter.sorteer(bericht.maakUitgaandBericht());
                final String htmlTekst = debugOutputBericht(platteTekst, TestCasusOutputStap.STAP_BERICHT,
                        leveringResult.getSoortAdministratieveHandeling() + DASH_SEPERATOR + berichtResult.getSoortBericht() + volgnummer);

                berichtResult.setBericht(new TestStap(TestStatus.OK, null, htmlTekst, null));

                if (expectedPlatteTekst != null) {
                    if (!Vergelijk.vergelijk(expectedPlatteTekst, platteTekst)) {

                        final String htmlVerschillen;
                        if ("".equals(expectedPlatteTekst)) {
                            htmlVerschillen = debugOutputString("Expected is leeg", TestCasusOutputStap.STAP_BERICHT, SUFFIX_VERSCHILLEN);
                        } else {
                            LOG.info("BERICHTEN VERSCHILLEN\n" + expectedPlatteTekst + "\n" + platteTekst);

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
                            nl.bzk.migratiebrp.test.levering.mutatiebericht.Lo3CategorieWaardenVergelijker.vergelijk(vergelijkingsLog, expectedCategorieen,
                                    actualCategorieen);
                            htmlVerschillen = debugOutputString(vergelijkingsLog.toString(), TestCasusOutputStap.STAP_BERICHT, SUFFIX_VERSCHILLEN);
                        }

                        berichtResult.setBericht(new TestStap(TestStatus.NOK, "Er zijn inhoudelijke verschillen (bericht)", htmlTekst, htmlVerschillen));
                    } else {
                        berichtResult.setBericht(new TestStap(TestStatus.OK, null, htmlTekst, null));
                    }
                } else {
                    berichtResult.setBericht(new TestStap(TestStatus.GEEN_VERWACHTING, null, htmlTekst, null));
                }
            } else {
                if (expectedPlatteTekst != null) {
                    final String htmlVerschillen = debugOutputString("Er is geen levering uitgevoerd, er is wel een verwacht bericht",
                            TestCasusOutputStap.STAP_BERICHT, SUFFIX_VERSCHILLEN);
                    berichtResult.setBericht(new TestStap(TestStatus.NOK, "Geen levering uitgevoerd, wel verwacht", null, htmlVerschillen));
                }
            }
        } catch (final Exception e) {
            final Foutmelding fout = new Foutmelding("Fout tijdens maken bericht.", e);
            final String htmlFout = debugOutputXmlEnHtml(fout, TestCasusOutputStap.STAP_BERICHT,
                    leveringResult.getSoortAdministratieveHandeling() + DASH_SEPERATOR + berichtResult.getSoortBericht() + volgnummer + SUFFIX_EXCEPTION);

            berichtResult.setBericht(new TestStap(TestStatus.EXCEPTIE, ER_IS_EEN_EXCEPTIE_OPGETREDEN, htmlFout, null));
        }
    }

    private List<Lo3CategorieWaarde> getBerichtImplCategorieen(final BerichtImpl bericht) {
        final List<Lo3CategorieWaarde> result;
        if (bericht == null) {
            result = null;
        } else {
            result = (List<Lo3CategorieWaarde>) ReflectionTestUtils.getField(bericht, "categorieen");
        }

        return result == null ? Collections.emptyList() : result;
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

        private Long persoonId;
        private List<Long> administratieveHandelingIds;
        private Long lockVersie;

        /**
         * Geef de waarde van persoon id.
         * @return persoon id
         */
        public Long getPersoonId() {
            return persoonId;
        }

        /**
         * Zet de waarde van persoon id.
         * @param persoonId persoon id
         */
        public void setPersoonId(final Long persoonId) {
            this.persoonId = persoonId;
        }

        /**
         * Geef de waarde van administratieve handeling ids.
         * @return administratieve handeling ids
         */
        public List<Long> getAdministratieveHandelingIds() {
            return administratieveHandelingIds;
        }

        /**
         * Zet de waarde van administratieve handeling ids.
         * @param administratieveHandelingIds administratieve handeling ids
         */
        public void setAdministratieveHandelingIds(final List<Long> administratieveHandelingIds) {
            this.administratieveHandelingIds = administratieveHandelingIds;
        }

        /**
         * Zet de waarde van administratieve handeling id.
         * @param administratieveHandelingId administratieve handeling id
         */
        public void setAdministratieveHandelingId(final Long administratieveHandelingId) {
            administratieveHandelingIds = Collections.singletonList(administratieveHandelingId);
        }

        /**
         * Geef de waarde van lockVersie.
         * @return lockVersie
         */
        public Long getLockVersie() {
            return lockVersie;
        }

        /**
         * Zet de waarde van lockVersie.
         * @param lockVersie lockVersie
         */
        public void setLockVersie(Long lockVersie) {
            this.lockVersie = lockVersie;
        }
    }

    /**
     * Interne bean class om Spring injectie te kunnen gebruiken voor settings.
     */
    private class BeanForMigratieAutowire {

        /**
         * Zet de waarde van lo3 syntax controle.
         * @param injectSyntaxControle lo3 syntax controle
         */
        @Inject
        public void setLo3SyntaxControle(final Lo3SyntaxControle injectSyntaxControle) {
            syntaxControle = injectSyntaxControle;
        }

        /**
         * Zet de waarde van precondities service.
         * @param injectPreconditieService precondities service
         */
        @Inject
        public void setPreconditiesService(final PreconditiesService injectPreconditieService) {
            preconditieService = injectPreconditieService;
        }

        /**
         * Zet de waarde van converteer lo3 naar brp service.
         * @param injectConverteerLo3NaarBrpService converteer lo3 naar brp service
         */
        @Inject
        public void setConverteerLo3NaarBrpService(final ConverteerLo3NaarBrpService injectConverteerLo3NaarBrpService) {
            converteerLo3NaarBrpService = injectConverteerLo3NaarBrpService;
        }

        /**
         * Zet de waarde van converteer brp naar lo3 service.
         * @param injectConverteerBrpNaarLo3Service converteer brp naar lo3 service
         */
        @Inject
        public void setConverteerBrpNaarLo3Service(final ConverteerBrpNaarLo3Service injectConverteerBrpNaarLo3Service) {
            converteerBrpNaarLo3Service = injectConverteerBrpNaarLo3Service;
        }

        /**
         * Zet de waarde van brp persoonslijst service.
         * @param injectBrpPersoonslijstService brp persoonslijst service
         */
        @Inject
        public void setBrpPersoonslijstService(final BrpPersoonslijstService injectBrpPersoonslijstService) {
            brpPersoonslijstService = injectBrpPersoonslijstService;
        }

        /**
         * Zet de waarde van brp autorisatie service.
         * @param injectBrpAutorisatieService brp autorisatie service
         */
        @Inject
        public void setBrpAutorisatieService(final BrpAutorisatieService injectBrpAutorisatieService) {
            brpAutorisatieService = injectBrpAutorisatieService;
        }

        /**
         * Zet de waarde van sync parameters.
         * @param injectSyncParameters sync parameters
         */
        @Inject
        public void setSyncParameters(final SyncParameters injectSyncParameters) {
            syncParameters = injectSyncParameters;
        }

        /**
         * Zet de waarde van data source.
         * @param injectMigratieDataSource data source
         */
        @Inject
        @Named("syncDalDataSource")
        public void setDataSource(final DataSource injectMigratieDataSource) {
            migratieDataSource = injectMigratieDataSource;
        }

        /**
         * Zet de waarde van dal cache controller.
         * @param injectDalCacheController dal cache controller
         */
        @Inject
        public void setDalCacheControlle(final DalCacheController injectDalCacheController) {
            dalCacheController = injectDalCacheController;
        }

        /**
         * Zet de waarde van entity manager.
         * @param injectMigratieEntityManager entity manager
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
         * @param injectPersoonslijstMapper persoonslijst mapper
         */
        @Inject
        public void setPersoonslijstMapper(final PersoonslijstMapper injectPersoonslijstMapper) {
            persoonslijstMapper = injectPersoonslijstMapper;
        }

        /**
         * Zet de waarde van persoonsgegevens service.
         * @param injectPersoonslijstService persoonsgegevens service
         */
        @Inject
        public void setPersoonsgegevensService(final PersoonslijstService injectPersoonslijstService) {
            persoonslijstService = injectPersoonslijstService;
        }

        /**
         * Zet de waarde van ist tabel repository.
         * @param injectIstTabelRepository ist tabel repository
         */
        @Inject
        public void setIstTabelRepository(final IstTabelRepository injectIstTabelRepository) {
            istTabelRepository = injectIstTabelRepository;
        }

        /**
         * Zet de waarde van transaction manager master.
         * @param injectTransactionManagerMaster transaction manager master
         */
        @Inject
        @Named("masterTransactionManager")
        public void setTransactionManagerMaster(final PlatformTransactionManager injectTransactionManagerMaster) {
            transactionManagerMaster = injectTransactionManagerMaster;
        }

        @Inject
        public void setCacheController(final CacheController injectCacheController) {
            cacheController = injectCacheController;
        }

        @Inject
        public void setToegangLeveringsautorisatieService(final LeveringsautorisatieService injectToegangLeveringsautorisatieService) {
            toegangLeveringsautorisatieService = injectToegangLeveringsautorisatieService;
        }

        /**
         * Zet de waarde van lo3 filter rubrieken repository.
         * @param injectLo3FilterRubriekRepository lo3 filter rubrieken repository
         */
        @Inject
        public void setLo3FilterRubriekenService(final Lo3FilterRubriekRepository injectLo3FilterRubriekRepository) {
            lo3FilterRubriekRepository = injectLo3FilterRubriekRepository;
        }

        /**
         * Zet de waarde van entity manager.
         * @param injectBrpEntityManager entity manager
         */
        @PersistenceContext(unitName = "nl.bzk.brp.master")
        public void setEntityManager(final EntityManager injectBrpEntityManager) {
            brpEntityManager = injectBrpEntityManager;
        }

        /**
         * Zet de waarde van serializer.
         * @param injectSerializer serializer
         */
        @Value("${serialisatie.persoonHisVolledig.klassenaam:}")
        public void setSerializer(final String injectSerializer) {
            serializer = injectSerializer;
        }

        /**
         * Zet de waarde van bericht factory.
         * @param injectBerichtFactory bericht factory
         */
        @Inject
        public void setBerichtFactory(final BerichtFactory injectBerichtFactory) {
            berichtFactory = injectBerichtFactory;
        }
    }
}
