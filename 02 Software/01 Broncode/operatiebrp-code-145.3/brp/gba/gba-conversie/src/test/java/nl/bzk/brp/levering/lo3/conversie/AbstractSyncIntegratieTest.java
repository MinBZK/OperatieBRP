/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.lo3.conversie;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.inject.Inject;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Lo3Bericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Lo3BerichtenBron;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.logging.LoggingContext;
import nl.bzk.algemeenbrp.util.common.spring.PropertiesPropertySource;
import nl.bzk.brp.domain.leveringmodel.AdministratieveHandeling;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.migratiebrp.bericht.model.lo3.parser.Lo3PersoonslijstParser;
import nl.bzk.migratiebrp.conversie.model.brp.BrpPersoonslijst;
import nl.bzk.migratiebrp.conversie.model.exceptions.Lo3SyntaxException;
import nl.bzk.migratiebrp.conversie.model.exceptions.OngeldigePersoonslijstException;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Persoonslijst;
import nl.bzk.migratiebrp.conversie.model.lo3.syntax.Lo3CategorieWaarde;
import nl.bzk.migratiebrp.conversie.regels.proces.ConverteerLo3NaarBrpService;
import nl.bzk.migratiebrp.conversie.regels.proces.logging.Logging;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.Lo3SyntaxControle;
import nl.bzk.migratiebrp.conversie.regels.proces.preconditie.PreconditiesService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.BrpPersoonslijstService;
import nl.bzk.migratiebrp.synchronisatie.dal.service.PersoonslijstPersisteerResultaat;
import nl.bzk.migratiebrp.synchronisatie.dal.service.SyncParameters;
import nl.bzk.migratiebrp.synchronisatie.dal.service.TeLeverenAdministratieveHandelingenAanwezigException;
import nl.bzk.migratiebrp.synchronisatie.logging.SynchronisatieLogging;
import nl.bzk.migratiebrp.util.excel.ExcelAdapter;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterException;
import nl.bzk.migratiebrp.util.excel.ExcelAdapterImpl;
import nl.bzk.migratiebrp.util.excel.ExcelData;

@Transactional(transactionManager = "masterTransactionManager")
@Rollback
@ContextConfiguration(locations = {"classpath:/config/datasource-beans.xml", "classpath:config/conversie-sync-test-context.xml"},
        initializers = AbstractSyncIntegratieTest.PortInitializer.class)
@RunWith(SpringJUnit4ClassRunner.class)
public abstract class AbstractSyncIntegratieTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    protected static GenericXmlApplicationContext synchronisatieContext;

    @Inject
    ApplicationContext conversieContext;

    // Laad de synchronisatie context nadat de main context geladen is. @BeforeClass is te vroeg.
    @Before
    public void testBefore() {
        final String portNummer = conversieContext.getEnvironment().getProperty("test.database.port");
        final Properties properties = new Properties();
        properties.setProperty("test.database.port", portNummer);

        if (synchronisatieContext == null) {
            synchronisatieContext = new GenericXmlApplicationContext();
            synchronisatieContext.load("classpath:/config/synchronisatie-test-context.xml");
            synchronisatieContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("ports", properties));
            synchronisatieContext.refresh();
        }
    }

    @AfterClass
    public static void destroySynchronisatie() {
        if (synchronisatieContext != null) {
            try {
                synchronisatieContext.close();
                synchronisatieContext = null;
            } catch (final Exception e) {
                LOGGER.error("Probleem bij sluiten SYNCHRONISATIE context", e);
            }
        }
    }

    protected PersoonslijstPersisteerResultaat gbaBijhouding(final String bijhoudingResourceLocation) {
        final PersoonslijstPersisteerResultaat initieleVulling =
                initierenPersoon(bijhoudingResourceLocation + "/persoon.xls", null, Lo3BerichtenBron.INITIELE_VULLING);
        return initierenPersoon(bijhoudingResourceLocation + "/gbaBijhouding.xls", initieleVulling.getPersoon().getId(), Lo3BerichtenBron.SYNCHRONISATIE);
    }

    protected AdministratieveHandeling handeling(final Persoonslijst persoonslijst, final PersoonslijstPersisteerResultaat gbaBijhouding) {
        final long admHndId = gbaBijhouding.getAdministratieveHandelingen().iterator().next().getId();

        final Optional<AdministratieveHandeling> handeling =
                persoonslijst.getAdministratieveHandelingen().stream().filter(h -> h.getId() == admHndId).findFirst();

        Assert.assertTrue("De gewenste administratieve handeling is niet gevonden", handeling.isPresent());

        return handeling.get();
    }

    private PersoonslijstPersisteerResultaat initierenPersoon(final String resource, final Number persoonId, final Lo3BerichtenBron bron) {
        // Application context dependencies
        final SyncParameters syncParameters = synchronisatieContext.getBean(SyncParameters.class);
        final ExcelAdapter excelAdapter = new ExcelAdapterImpl();
        final Lo3SyntaxControle syntaxControle = synchronisatieContext.getBean(Lo3SyntaxControle.class);
        final Lo3PersoonslijstParser lo3Parser = new Lo3PersoonslijstParser();
        final PreconditiesService preconditieService = synchronisatieContext.getBean(PreconditiesService.class);
        final ConverteerLo3NaarBrpService converteerLo3NaarBrpService = synchronisatieContext.getBean(ConverteerLo3NaarBrpService.class);
        final BrpPersoonslijstService persoonslijstService = synchronisatieContext.getBean(BrpPersoonslijstService.class);

        // Start transactie
        final PlatformTransactionManager transactionManager = synchronisatieContext.getBean("syncDalTransactionManager", PlatformTransactionManager.class);
        final TransactionDefinition transactionDefiniton = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        final TransactionStatus transaction = transactionManager.getTransaction(transactionDefiniton);

        if (Lo3BerichtenBron.INITIELE_VULLING.equals(bron)) {
            syncParameters.setInitieleVulling(true);
            LOGGER.info("Verwerken GBA initiele vulling");
        } else {
            syncParameters.setInitieleVulling(false);
            LOGGER.info("Verwerken GBA bijhouding");
        }

        LOGGER.info("Inlezen Excel sheet");
        try (InputStream fis = this.getClass().getResourceAsStream(resource)) {
            if (fis == null) {
                throw new IllegalArgumentException("Resource '" + resource + "' bestaat niet.");
            }
            Logging.initContext();
            SynchronisatieLogging.init();
            final List<ExcelData> excelDatas = excelAdapter.leesExcelBestand(fis);

            if (excelDatas.isEmpty()) {
                throw new IllegalArgumentException("Persoon.xls bevat geen persoonsgegevens");
            }

            final ExcelData excelData = excelDatas.get(0);
            final List<Lo3CategorieWaarde> lo3Inhoud = excelData.getCategorieLijst();

            // Lo3 syntax controle
            final List<Lo3CategorieWaarde> lo3InhoudNaSyntaxControle = syntaxControle.controleer(lo3Inhoud);

            // Parse persoonslijst
            final Lo3Persoonslijst lo3Persoonslijst = lo3Parser.parse(lo3InhoudNaSyntaxControle);

            // Controleer precondities
            final Lo3Persoonslijst schoneLo3Persoonslijst = preconditieService.verwerk(lo3Persoonslijst);
            // LOG.info("Lo3 persoonslijst: {}", lo3Pl);

            final BrpPersoonslijst brpPl = converteerLo3NaarBrpService.converteerLo3Persoonslijst(schoneLo3Persoonslijst);

            final Lo3Bericht lo3Bericht = new Lo3Bericht("persoon", bron, new Timestamp(System.currentTimeMillis()), "ExcelData", true);
            LOGGER.info("Bericht: {}", lo3Bericht);

            final PersoonslijstPersisteerResultaat result = persoonId == null ? persoonslijstService.persisteerPersoonslijst(brpPl, lo3Bericht)
                    : persoonslijstService.persisteerPersoonslijst(brpPl, persoonId.longValue(), lo3Bericht);
            LOGGER.info("Persoon.id: {}", result.getPersoon().getId());
            LOGGER.info("Administratieve handeling(en): {}", result.getAdministratieveHandelingen());

            return result;
        } catch (ExcelAdapterException | Lo3SyntaxException | OngeldigePersoonslijstException | TeLeverenAdministratieveHandelingenAanwezigException e) {
            transaction.setRollbackOnly();
            throw new IllegalArgumentException("Kan persoonlijst niet verwerken", e);
        } catch (final IOException ioe) {
            transaction.setRollbackOnly();
            throw new IllegalArgumentException("Kan persoonlijst niet lezen: " + resource, ioe);
        } finally {
            LoggingContext.reset();
            LOGGER.info("Logging: {}", Logging.getLogging());
            Logging.destroyContext();

            if (transaction.isRollbackOnly()) {
                transactionManager.rollback(transaction);
            } else {
                transactionManager.commit(transaction);
            }
        }
    }

    /**
     * Dynamisch poorten voor resources bepalen.
     */
    public static final class PortInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(final ConfigurableApplicationContext applicationContext) {
            final Properties properties = new Properties();
            try (ServerSocket socket = new ServerSocket(0)) {
                final int port = socket.getLocalPort();
                LOGGER.info("Configuring database to port: {}", port);
                properties.setProperty("test.database.port", Integer.toString(port));
            } catch (final IOException e) {
                throw new IllegalStateException("Kon geen port voor de database bepalen", e);
            }
            applicationContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("ports", properties));
        }
    }
}
