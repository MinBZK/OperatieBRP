/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.jmx.server;

import java.net.MalformedURLException;
import java.util.Map;
import javax.management.MBeanServer;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerProvider;
import javax.management.remote.JMXServiceURL;
import nl.bzk.algemeenbrp.util.jmx.SimpleJmx;

/**
 * JMX Server provider.
 */
public final class ServerProvider implements JMXConnectorServerProvider {

    @Override
    public JMXConnectorServer newJMXConnectorServer(final JMXServiceURL url, final Map<String, ?> environment,
                                                    final MBeanServer server) throws MalformedURLException {
        final String protocol = url.getProtocol();
        if (!SimpleJmx.PROTOCOL.equals(protocol)) {
            throw new MalformedURLException(
                    "Invalid protocol '" + protocol + "' for provider " + this.getClass().getName());
        }
        return new ServerConnector(url, environment, server);
    }
}
