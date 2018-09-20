/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.controle.waiter;

import java.util.Arrays;
import java.util.List;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.common.operatie.Herhaal;
import nl.bzk.migratiebrp.util.common.operatie.HerhaalException;

/**
 * Stiekeme bean om te kunnen wachten totdat een Brp installatie is geinitialiseerd.
 *
 */
public final class BrpInitializationWaiter {

    private static final Logger LOG = LoggerFactory.getLogger();

    private BrpInitializationWaiter() {
        // Niet instantieerbaar
    }

    /**
     * Run waiter.
     *
     * @param args
     *            arguments
     */
    public static void main(final String[] args) {
        final List<String> arguments = Arrays.asList(args);
        final boolean checkRoutering = arguments.contains("routering");
        final boolean checkLevering = arguments.contains("levering");

        // Controleer Brp gestart
        try {
            final String server = PropertyUtil.getProperty("brp.server");
            BrpInitializationWaiter.controleerBrpGestart(server, checkRoutering, checkLevering);
        } catch (final HerhaalException e) {
            throw new RuntimeException(e);
        }
    }

    private static void controleerBrpGestart(final String server, final boolean checkRoutering, final boolean checkLevering) throws HerhaalException {

        LOG.info("Controle op initialisatie Brp gestart");

        final Herhaal herhaling = new Herhaal(10000, 60, Herhaal.Strategie.REGELMATIG);
        herhaling.herhaal(new Runnable() {
            @Override
            public void run() {
                try {
                    final JMXConnector connector = JMXConnectorFactory.connect(new JMXServiceURL(server));
                    final MBeanServerConnection connection = connector.getMBeanServerConnection();

                    if (checkRoutering) {
                        connection.getMBeanInfo(new ObjectName("nl.bzk.brp:type=Broker,brokerName=BrpMessageBroker"));
                    }

                    if (checkLevering) {
                        connection.getMBeanInfo(new ObjectName("verzending-nl.bzk.brp.levering.verzending:name=Verzender"));
                    }
                } catch (final Exception e) {
                    LOG.info("Brp niet gestart: " + e);
                    throw new RuntimeException("Brp niet gestart.");
                }
            }
        });
        LOG.info("Brp gestart");
    }
}
