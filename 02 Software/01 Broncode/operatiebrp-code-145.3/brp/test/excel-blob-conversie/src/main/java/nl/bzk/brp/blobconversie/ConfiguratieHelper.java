/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.blobconversie;

import com.google.common.collect.Lists;
import edu.emory.mathcs.backport.java.util.Collections;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import javax.sql.DataSource;
import nl.bzk.algemeenbrp.jta.util.UniqueName;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.blobconversie.service.Blob2FileService;
import nl.bzk.brp.blobconversie.service.Excel2DatabaseService;
import org.apache.commons.io.FileUtils;
import org.hsqldb.server.Server;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

/**
 */
final class ConfiguratieHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final ThreadLocal<ConfiguratieHelper> HELPER_THREAD_LOCAL = new ThreadLocal<ConfiguratieHelper>() {

        @Override
        protected ConfiguratieHelper initialValue() {
            try {
                return new ConfiguratieHelper();
            } catch (final Exception e) {
                LOGGER.error("Configuratie kan niet ingeladen worden", e);
                System.exit(-1);
                return null;
            }
        }
    };
    private static final UniqueName UNIQUE_NAME = new UniqueName();
    private static final Collection<ConfiguratieHelper> HELPER_LIST = Collections.synchronizedCollection(Lists.newLinkedList());

    private final AtomicInteger uniekeNaam = new AtomicInteger(5555);

    static {
        UNIQUE_NAME.setBaseName("syncDalDatabase");
    }
    private final DatabaseManager            databaseManager;
    private       Excel2DatabaseService      excel2DatabaseService;
    private       Blob2FileService           blob2FileService;
    private       DataSource                 dataSource;
    private       AbstractApplicationContext serviceContext;
    private AbstractApplicationContext dbContext;

    private static final Random RANDOM = new Random();
    private final DataSourceInitializer dataSourceInitializer;

    /**
     * Constructor waarin de omgeving wordt meegegeven waarmee de GBA-sync opgestart kan worden.
     *
     */
    private ConfiguratieHelper() throws Exception {
        HELPER_LIST.add(this);
        databaseManager = new DatabaseManager();
        LOGGER.info("Start db");
        databaseManager.start();
        LOGGER.info("Init services");

        dataSourceInitializer = new DataSourceInitializer();
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator(
                new ClassPathResource("bmr/HSQLDB/HSQLDB.sql"),
                new ClassPathResource("bmr/HSQLDB/PENDING_BMR_CHANGES.sql")
        );
        populator.setSqlScriptEncoding("UTF-8");
        dataSourceInitializer.setDatabasePopulator(populator);


        initServices();
    }

    static void cleanup() {
        for (final ConfiguratieHelper configuratieHelper : HELPER_LIST) {
            configuratieHelper.stop();
        }
    }

    Excel2DatabaseService getExcel2DatabaseService() {
        return excel2DatabaseService;
    }

    Blob2FileService getBlob2FileService() {
        return blob2FileService;
    }

    private void vulDatabase() throws Exception {
        LOGGER.info("Start vul db");
        dataSourceInitializer.afterPropertiesSet();
        LOGGER.info("Eind vul db");
    }

    private void initServices() throws Exception {

        final PropertyPlaceholderConfigurer propConfig = new PropertyPlaceholderConfigurer();
        final Properties properties = new Properties();
        properties.put("uniekeNaam", "syncDatabase" + (RANDOM.nextInt() + uniekeNaam.incrementAndGet()));
        properties.put("db.ServerName", "localhost");
        properties.put("db.PortNumber", String.valueOf(databaseManager.getPoort()));
        propConfig.setProperties(properties);

        //setup datasource
        final ClassPathXmlApplicationContext tempDbContext = new ClassPathXmlApplicationContext();
        tempDbContext.addBeanFactoryPostProcessor(propConfig);
        tempDbContext.setConfigLocation("classpath:/datasource-config.xml");
        tempDbContext.refresh();
        this.dataSource = (DataSource) tempDbContext.getBean("syncDalDataSource");
        dataSourceInitializer.setDataSource(dataSource);
        //vul db
        vulDatabase();

        //start services
        final ClassPathXmlApplicationContext tempServiceContext = new ClassPathXmlApplicationContext();
        tempServiceContext.setConfigLocation("classpath:/service-config.xml");
        tempServiceContext.setParent(tempDbContext);
        tempServiceContext.addBeanFactoryPostProcessor(propConfig);
        tempServiceContext.refresh();

        this.excel2DatabaseService = tempServiceContext.getBean(Excel2DatabaseService.class);
        this.blob2FileService = tempServiceContext.getBean(Blob2FileService.class);
        this.serviceContext = tempServiceContext;
        this.dbContext = tempDbContext;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    static ConfiguratieHelper get() {
        return HELPER_THREAD_LOCAL.get();
    }

    public void stop() {
        try {
            LOGGER.info("Close ServiceContext");
            serviceContext.close();
        } finally {
            try {
                LOGGER.info("Close DbContext");
                dbContext.close();
            } finally {
                LOGGER.info("Close DatabaseManager");
                databaseManager.stop();
            }
        }
        LOGGER.info("Teardown complete");
    }

    void prepareNextRun() throws Exception {
        LOGGER.info("Start prepareNextRun");
        dataSourceInitializer.afterPropertiesSet();
        LOGGER.info("Einde prepareNextRun");
    }

    private static final class DatabaseManager {

        private final File dbLocation; // change it to your db location
        private Server server;
        private final int        vrijePoort = PoortManager.get().geefVrijePoort();

        DatabaseManager() throws IOException {
            final File dir = new File(FileUtils.getTempDirectory(), "brp");
            dir.mkdir();
            dbLocation = File.createTempFile("persoon", ".brpdb", dir);
        }

        void start() {
            server = new org.hsqldb.Server();
            server.setPort(vrijePoort);
            server.setDaemon(true);
            server.setDatabaseName(0, "brp");
            server.setDatabasePath(0, "file:" + dbLocation.getAbsolutePath() + ";");
            server.setSilent(true);
            server.start();
        }

        void stop() {
            server.signalCloseAllServerConnections();
            server.stop();
            server.shutdown();
            FileUtils.deleteQuietly(dbLocation);
        }

        int getPoort() {
            return vrijePoort;
        }


    }
}
