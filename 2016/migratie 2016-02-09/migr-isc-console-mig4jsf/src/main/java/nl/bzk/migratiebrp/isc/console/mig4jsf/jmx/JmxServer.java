/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf.jmx;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.management.JMException;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

/**
 * JMX Server facade (inclusief cache).
 */
public final class JmxServer {

    private final MBeanServerConnection serverConnection;

    /**
     * Constructor.
     * 
     * @param url
     *            url
     * @param username
     *            username
     * @param password
     *            password
     * @throws IOException
     *             als geen verbinding gemaakt kan worden
     */
    public JmxServer(final String url, final String username, final String password) throws IOException {
        final JMXServiceURL jmxUrl = new JMXServiceURL(url);

        final Map<String, Object> env = new HashMap<>();
        if (username != null && !"".equals(username) && password != null && !"".equals(password)) {
            final String[] creds = {username, password };
            env.put(JMXConnector.CREDENTIALS, creds);
        }

        final JMXConnector jmxConnector = JMXConnectorFactory.connect(jmxUrl, env);
        serverConnection = jmxConnector.getMBeanServerConnection();
    }

    /**
     * haal de waarde van een attribute op van een jmx object.
     * 
     * @param objectName
     *            object naam
     * @param attributeName
     *            attribute naam
     * @return waarde
     * @throws IOException
     *             bij communicatie fouten
     * @throws JMException
     *             bij JMX fouten
     */
    public Object getAttribute(final String objectName, final String attributeName) throws IOException, JMException {
        final ObjectName name = new ObjectName(objectName);
        return serverConnection.getAttribute(name, attributeName);
    }

    /**
     * Lijst van esb deployments.
     * 
     * @param prefix
     *            prefix
     * @return lijst van esb deployment object namen
     * @throws IOException
     *             bij communicatie fouten
     * @throws JMException
     *             bij JMX fouten
     */
    public List<String> listEsbDeployments(final String prefix) throws JMException, IOException {
        final List<String> result = new ArrayList<>();
        final ObjectName objectName = new ObjectName("jboss.esb:deployment=" + prefix + "*");
        for (final ObjectName resultName : serverConnection.queryNames(objectName, null)) {
            result.add(resultName.getKeyPropertyList().get("deployment"));
        }
        return result;
    }
}
