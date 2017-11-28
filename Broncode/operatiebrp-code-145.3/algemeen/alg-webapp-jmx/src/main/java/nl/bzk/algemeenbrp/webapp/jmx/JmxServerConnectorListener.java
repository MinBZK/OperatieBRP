/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.webapp.jmx;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import nl.bzk.algemeenbrp.util.jmxagent.JMXAgent;

/**
 * Servlet context listener om JMX Server Connector te starten en te stoppen.
 */
public final class JmxServerConnectorListener implements ServletContextListener {

    private final JMXAgent jmxAgent;

    /**
     * Constructor.
     */
    public JmxServerConnectorListener() {
        jmxAgent = new JMXAgent();
    }

    /**
     * Start de JMX Server Connector.
     * 
     * @param arg0
     *            de servlet context event
     */
    @Override
    public void contextInitialized(final ServletContextEvent arg0) {
        jmxAgent.start();
    }

    /**
     * Stop de JMX Server Connector.
     * 
     * @param arg0
     *            de servlet context event
     */
    @Override
    public void contextDestroyed(final ServletContextEvent arg0) {
        jmxAgent.stop();
    }

}
