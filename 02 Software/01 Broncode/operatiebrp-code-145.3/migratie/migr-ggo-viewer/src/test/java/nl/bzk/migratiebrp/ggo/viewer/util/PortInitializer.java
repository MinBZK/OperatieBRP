/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.util;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Properties;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.spring.PropertiesPropertySource;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * Utility voor initialiseren poorten voor testen.
 */
public class PortInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(final ConfigurableApplicationContext applicationContext) {
        final Properties properties = new Properties();
        try (ServerSocket socket = new ServerSocket(0)) {
            final int port = socket.getLocalPort();
            Logger log = LoggerFactory.getLogger();
            log.info("Configuring database to port: {}", port);
            properties.setProperty("test.database.port", Integer.toString(port));
        } catch (final IOException e) {
            throw new IllegalStateException("Kon geen port voor de database bepalen", e);
        }
        applicationContext.getEnvironment().getPropertySources().addLast(new PropertiesPropertySource("ports", properties));
    }
}

