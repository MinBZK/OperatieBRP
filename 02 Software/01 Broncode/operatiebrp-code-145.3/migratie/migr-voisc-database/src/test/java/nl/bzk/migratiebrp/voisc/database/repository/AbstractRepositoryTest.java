/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.database.repository;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Properties;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.spring.PropertiesPropertySource;
import nl.bzk.migratiebrp.voisc.database.repository.AbstractRepositoryTest.PortInitializer;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:voisc-database.xml", "classpath:test-datasource.xml", "classpath:test-jta.xml"},
        initializers = {PortInitializer.class})
@Transactional()
@Rollback(true)
public abstract class AbstractRepositoryTest {

    private static final Logger LOG = LoggerFactory.getLogger();

    /**
     * Dynamisch poorten voor resources bepalen.
     */
    public static final class PortInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(final ConfigurableApplicationContext applicationContext) {
            final Properties properties = new Properties();
            try (ServerSocket socket = new ServerSocket(0)) {
                final int port = socket.getLocalPort();
                LOG.info("Configuring database to port: {}", port);
                properties.setProperty("test.database.port", Integer.toString(port));
            } catch (final IOException e) {
                throw new IllegalStateException("Kon geen port voor de database bepalen", e);
            }
            applicationContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("ports", properties));
        }
    }
}
