/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarbrp.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.sql.DataSource;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.bericht.model.sync.SyncBericht;
import nl.bzk.migratiebrp.bericht.model.sync.factory.SyncBerichtFactory;
import nl.bzk.migratiebrp.bericht.model.sync.impl.ProtocolleringBericht;
import nl.bzk.migratiebrp.init.naarbrp.domein.ConversieResultaat;
import nl.bzk.migratiebrp.init.naarbrp.repository.jdbc.JdbcProtocolleringRepository;
import nl.bzk.migratiebrp.init.naarbrp.util.DBUnitUtil;
import nl.bzk.migratiebrp.init.naarbrp.verwerker.impl.VerzendProtocolleringBerichtVerwerker;
import org.dbunit.Assertion;
import org.dbunit.DatabaseUnitException;
import org.dbunit.assertion.DefaultFailureHandler;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jms.core.JmsTemplate;

@RunWith(JUnitParamsRunner.class)
public class ProtocolleringRepositoryTest {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final int BATCH_GROOTTE = 2;
    private static final int PROTOCOLLERING_GROOTTE = 2;

    private static GenericXmlApplicationContext applicationContext;

    @Inject
    private DBUnitUtil dbUnitUtil;
    @Inject
    private Destination destination;
    @Inject
    private JmsTemplate jmsTemplate;

    private DataSource dataSource;
    private ProtocolleringRepository protocolleringRepository;
    private IDatabaseConnection connection;

    @BeforeClass
    public static void initialize() {
        // Setup application context
        applicationContext = new GenericXmlApplicationContext();
        applicationContext.load("classpath:test-embedded-database-brp-gbav.xml", "classpath:test-embedded-hornetq.xml");
        new PortInitializer().initialize(applicationContext);
        applicationContext.refresh();
    }

    @AfterClass
    public static void destroy() {
        applicationContext.close();
    }

    @Before
    public void setup() throws DatabaseUnitException, SQLException {
        applicationContext.getAutowireCapableBeanFactory().autowireBean(this);

        protocolleringRepository = applicationContext.getAutowireCapableBeanFactory().createBean(JdbcProtocolleringRepository.class);
        dataSource = applicationContext.getBean(DataSource.class);
        connection = dbUnitUtil.connection();

        // Clean initvul tabellen
        final JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        jdbc.execute("delete from initvul.initvullingresult_protocollering_activiteit");
        jdbc.execute("delete from initvul.initvullingresult_protocollering_brp_toeglevaut");
        jdbc.execute("delete from initvul.initvullingresult_protocollering_brp_dienst");
        jdbc.execute("delete from initvul.initvullingresult_protocollering_brp_pers");
        jdbc.execute("delete from initvul.initvullingresult_protocollering_toeglevaut");
        jdbc.execute("delete from initvul.initvullingresult_protocollering_dienst");
        jdbc.execute("delete from initvul.initvullingresult_protocollering");
    }

    @After
    public void teardown() throws DatabaseUnitException, SQLException {
        connection.close();
    }

    private Path[] listFilesForTestLaadInitVullingTable() throws IOException, DatabaseUnitException, SQLException {
        final ResourceLoader resourceLoader = new DefaultResourceLoader();
        return Files.walk(Paths.get(resourceLoader.getResource("/data/protocollering/laad_init_vulling_table").getURI()), 1)
                .filter(path -> !path.getFileName().equals(Paths.get("/data/protocollering/laad_init_vulling_table").getFileName()))
                .filter(path -> Files.isDirectory(path)).collect(Collectors.toList()).toArray(new Path[]{});
    }

    @Test
    @Parameters(method = "listFilesForTestLaadInitVullingTable")
    public void testLaadInitVullingTable(Path path) {
        LOGGER.info(String.format("Running laadInitVullingTable with fixture data in %s...", path.getName(path.getNameCount() - 1)));
        try {
            testWithFixture(path, "/data/protocollering/laad_init_vulling_table/teardown.xml", () -> protocolleringRepository.laadInitVullingTable());
        } catch (final DatabaseUnitException | SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testNavulling() throws IOException, DatabaseUnitException, SQLException {
        final LocalDateTime dt = LocalDateTime.of(2001, 1, 1, 12, 0, 0, 0);
        testWithFixture(Paths.get(applicationContext.getResource("/data/protocollering/navulling").getURI()), null,
                () -> protocolleringRepository.laadInitVullingTable(dt));
    }

    @Test
    public void testVerwerk() throws IOException, DatabaseUnitException, SQLException {
        testWithFixture(Paths.get(applicationContext.getResource("/data/protocollering/verwerk").getURI()), null, () -> {
            final VerzendProtocolleringBerichtVerwerker verwerker =
                    new VerzendProtocolleringBerichtVerwerker(destination, jmsTemplate, protocolleringRepository);
            final boolean result = protocolleringRepository.verwerk(ConversieResultaat.TE_VERZENDEN, verwerker, BATCH_GROOTTE, PROTOCOLLERING_GROOTTE);
            Assert.assertFalse("resultaat mag niet waar zijn", result);

            final TextMessage message = (TextMessage) jmsTemplate.receive(destination);
            try {
                final SyncBericht bericht = SyncBerichtFactory.SINGLETON.getBericht(message.getText());
                Assert.assertTrue("Bericht moet een ProtocolleringBericht zijn", bericht instanceof ProtocolleringBericht);
            } catch (final JMSException e) {
                Assert.fail(e.getMessage());
            }
        });
    }

    @Test
    public void testUpdateStatussenEnkele() throws IOException, DatabaseUnitException, SQLException {
        testWithFixture(Paths.get(applicationContext.getResource("/data/protocollering/update_statussen/enkele").getURI()), null,
                () -> protocolleringRepository.updateStatussen(Collections.singletonList(1L), ConversieResultaat.VERZONDEN));
    }

    @Test
    public void testUpdateStatussenMeerdere() throws IOException, DatabaseUnitException, SQLException {
        testWithFixture(Paths.get(applicationContext.getResource("/data/protocollering/update_statussen/meerdere").getURI()), null,
                () -> protocolleringRepository.updateStatussen(Arrays.asList(1L, 2L), ConversieResultaat.VERZONDEN));
    }

    @Test
    public void testUpdateStatussenGeen() throws IOException, DatabaseUnitException, SQLException {
        testWithFixture(Paths.get(applicationContext.getResource("/data/protocollering/update_statussen/geen").getURI()), null,
                () -> protocolleringRepository.updateStatussen(Collections.emptyList(), ConversieResultaat.VERZONDEN));
    }

    private void testWithFixture(final Path path, final String tearDownFile, final Runnable function) throws DatabaseUnitException, SQLException, IOException {
        if (tearDownFile == null) {
            DatabaseOperation.DELETE_ALL.execute(connection, dbUnitUtil.createDataset(path.resolve("dataset.xml")));
        } else {
            DatabaseOperation.DELETE_ALL.execute(connection, dbUnitUtil.createDataset(tearDownFile));
        }
        DatabaseOperation.INSERT.execute(connection, dbUnitUtil.createDataset(path.resolve("dataset.xml")));

        function.run();

        final JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        LOGGER.info("AANTAL IN initvul.initvullingresult_protocollering_activiteit (stap 1): {}",
                jdbc.queryForObject("select count(*) from initvul.initvullingresult_protocollering_activiteit", Integer.class));
        LOGGER.info("AANTAL IN initvul.initvullingresult_protocollering_brp_toeglevaut (stap 2a prepare): {}",
                jdbc.queryForObject("select count(*) from initvul.initvullingresult_protocollering_brp_toeglevaut", Integer.class));
        LOGGER.info("AANTAL IN initvul.initvullingresult_protocollering_brp_dienst (stap 2b prepare): {}",
                jdbc.queryForObject("select count(*) from initvul.initvullingresult_protocollering_brp_dienst", Integer.class));
        LOGGER.info("AANTAL IN initvul.initvullingresult_protocollering_brp_pers (stap 2c prepare): {}",
                jdbc.queryForObject("select count(*) from initvul.initvullingresult_protocollering_brp_pers", Integer.class));
        LOGGER.info("AANTAL IN initvul.initvullingresult_protocollering_toeglevaut (stap 2a): {}",
                jdbc.queryForObject("select count(*) from initvul.initvullingresult_protocollering_toeglevaut", Integer.class));
        LOGGER.info("AANTAL IN initvul.initvullingresult_protocollering_dienst (stap 2b): {}",
                jdbc.queryForObject("select count(*) from initvul.initvullingresult_protocollering_dienst", Integer.class));
        LOGGER.info("AANTAL IN initvul.initvullingresult_protocollering (stap 2c): {}",
                jdbc.queryForObject("select count(*) from initvul.initvullingresult_protocollering", Integer.class));

        final IDataSet databaseDataSet = connection.createDataSet();
        final ITable actualTable = databaseDataSet.getTable("initvul.initvullingresult_protocollering");
        final IDataSet expectedDataSet = dbUnitUtil.createDataset(path.resolve("expected.xml"));
        final ITable expectedTable = expectedDataSet.getTable("initvul.initvullingresult_protocollering");

        Assertion.assertEquals(expectedTable, actualTable, new DefaultFailureHandler() {
            @Override
            public Error createFailure(final String message, final String expected, final String actual) {
                return super.createFailure(path.getName(path.getNameCount() - 1) + " - " + message, expected, actual);
            }

            @Override
            public Error createFailure(final String message) {
                return super.createFailure(path.getName(path.getNameCount() - 1) + " - " + message);
            }
        });
    }
}
