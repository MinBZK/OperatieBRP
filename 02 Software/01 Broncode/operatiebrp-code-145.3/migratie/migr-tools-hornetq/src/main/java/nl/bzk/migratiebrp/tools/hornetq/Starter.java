/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.hornetq;

import nl.bzk.algemeenbrp.util.jmxagent.JMXAgent;
import org.hornetq.integration.bootstrap.HornetQBootstrapServer;

/**
 * Start JMX Connector Server en Hornetq.
 */
public final class Starter {

    private Starter() {
    }

    /**
     * Start JMX Connector Server en Hornetq.
     * @param args args
     * @throws Exception Als iets fout gaat
     */
    public static void main(final String[] args) throws Exception {
        final JMXAgent jmxagent = new JMXAgent();
        jmxagent.start();
        HornetQBootstrapServer.main(args);
    }
}
