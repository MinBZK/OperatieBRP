/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.neoload;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Properties;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.spring.PropertiesPropertySource;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.context.support.GenericXmlApplicationContext;

public class AbstractIT {

    private static final Logger LOGGER = LoggerFactory.getLogger();


    private static Properties portProperties = new Properties();
    private static GenericXmlApplicationContext mailboxContext;

    private GenericXmlApplicationContext testContext;

    private static final Logger LOG = LoggerFactory.getLogger();

    @BeforeClass
    public static void startDependencies() throws IOException {

        try (ServerSocket mailboxPort = new ServerSocket(0)) {
            LOGGER.info("Configuring mailbox to port: " + mailboxPort.getLocalPort());
            portProperties.setProperty("test.mailbox.port", Integer.toString(mailboxPort.getLocalPort()));
        }

        // Start Mailbox
        LOG.info("Starten MAILBOX ...");
        final Properties mailboxProperties = new Properties();
        mailboxProperties.setProperty("mailbox.port", portProperties.getProperty("test.mailbox.port"));
        mailboxProperties.setProperty("mailbox.factory.type", "memory");
        mailboxProperties.setProperty("mailbox.jmx.serialize.port", "0");
        mailboxContext = new GenericXmlApplicationContext();
        mailboxContext.load("classpath:tools-mailbox.xml");
        mailboxContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("configuration", mailboxProperties));
        mailboxContext.refresh();
        LOG.info("MAILBOX gestart");

    }

    @AfterClass
    public static void stopTestContext() {
        if (mailboxContext != null) {
            try {
                LOG.info("Stoppen MAILBOX context ...");
                mailboxContext.close();
                LOG.info("MAILBOX context gestopt");
            } catch (final Exception e) {
                LOG.warn("Probleem bij sluiten MAILBOX context", e);
            }
        }
    }

    int getMailboxPort() {
        return Integer.parseInt(portProperties.getProperty("test.mailbox.port"));
    }

//    @Before
//    public void start() throws IOException, JMSException {
//        // Create test context
//        LOG.info("Starten TEST context ...");
//        testContext = new GenericXmlApplicationContext();
//        testContext.load("classpath:test-context.xml");
//        testContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("configuration", portProperties));
//        testContext.refresh();
//        LOG.info("TEST context gestart");
//
//        testContext.getAutowireCapableBeanFactory().autowireBean(this);
//    }
//
//    @After
//    public void shutdown() throws IOException, JMException {
//        if (testContext != null) {
//            try {
//                LOG.info("Stoppen TEST context ...");
//                testContext.close();
//                LOG.info("TEST context gestopt");
//            } catch (final Exception e) {
//                LOG.warn("Probleem bij sluiten TEST context", e);
//            }
//        }
//    }
}
