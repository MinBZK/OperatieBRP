/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.jmxcollector;

import java.io.IOException;
import java.util.Collections;
import javax.inject.Inject;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Configuratie van de verschillende JMX connectionservers.
 */
@Configuration
class Connecties {

    private static final String DEFAULT_USERNAME = "admin";
    private static final String DEFAULT_WACHTWOORD = "admin";
    private static final String DEFAULT_HOST = "localhost";
    private static final String DEFAULT_PORT = "3481";

    private final Environment environment;

    @Inject
    Connecties(final Environment environment) {
        this.environment = environment;
    }

    @Bean("brpMessagebrokerJmxConnector")
    public JMXConnector getBrpMessagebrokerJmxConnector() throws IOException {
        return maakJMXConnector("brp.messagebroker.jmx.service.host", "brp.messagebroker.jmx.service.port",
                getCredentials("brp.messagebroker.jmx.service.username", "brp.messagebroker.jmx.service.password"));
    }


    @Bean("routeringJmxConnector")
    public JMXConnector getRouteringJmxConnector() throws IOException {
        return maakJMXConnector("routering.jmx.service.host", "routering.jmx.service.port",
                getCredentials("routering.jmx.service.username", "routering.jmx.service.password"));
    }

    private String[] getCredentials(final String usernameProperty, final String passwordProperty) {
        return new String[]{environment.getProperty(usernameProperty, DEFAULT_USERNAME),
                environment.getProperty(passwordProperty, DEFAULT_WACHTWOORD)};
    }

    private JMXConnector maakJMXConnector(String hostProperty, String portProperty, String[] credentials) throws IOException {
        final String host = environment.getProperty(hostProperty, DEFAULT_HOST);
        final String port = environment.getProperty(portProperty, DEFAULT_PORT);
        final JMXServiceURL url = new JMXServiceURL(String.format("service:jmx:simple://%s:%s", host, port));
        return JMXConnectorFactory.newJMXConnector(url, Collections.singletonMap("jmx.remote.credentials", credentials));
    }
}
