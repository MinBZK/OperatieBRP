/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.util.jmx.client;

import java.net.MalformedURLException;
import java.util.Map;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorProvider;
import javax.management.remote.JMXServiceURL;
import nl.bzk.algemeenbrp.util.jmx.SimpleJmx;

/**
 * JMX Client provider.
 */
public final class ClientProvider implements JMXConnectorProvider {

    @Override
    public JMXConnector newJMXConnector(final JMXServiceURL url, final Map<String, ?> environment)
            throws MalformedURLException {
        final String protocol = url.getProtocol();
        if (!SimpleJmx.PROTOCOL.equals(protocol)) {
            throw new MalformedURLException(
                    "Invalid protocol '" + protocol + "' for provider " + this.getClass().getName());
        }

        return new ClientConnector(url, environment);
    }
}
