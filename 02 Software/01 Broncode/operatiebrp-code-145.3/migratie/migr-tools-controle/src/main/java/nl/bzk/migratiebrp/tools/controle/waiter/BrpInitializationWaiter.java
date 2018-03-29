/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.tools.controle.waiter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.management.InstanceNotFoundException;
import javax.management.IntrospectionException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.util.common.operatie.Herhaal;
import nl.bzk.migratiebrp.util.common.operatie.HerhaalException;

/**
 * Stiekeme bean om te kunnen wachten totdat een Brp installatie is geinitialiseerd.
 */
public final class BrpInitializationWaiter {

    private static final Logger LOG = LoggerFactory.getLogger();

    private BrpInitializationWaiter() {
        // Niet instantieerbaar
    }

    /**
     * Run waiter.
     * @param args arguments
     */
    public static void main(final String[] args) {
        final List<String> arguments = new ArrayList<>();
        for (final String arg : args) {
            arguments.add(arg.toLowerCase());
        }
        final boolean checkMessage = arguments.contains("messagebroker");
        final boolean checkGba = arguments.contains("gbacentrale");
        final boolean checkBijhouding = arguments.contains("bijhouding");
        final boolean checkBevraging = arguments.contains("bevraging");
        final boolean checkLevering = arguments.contains("mutatielevering");
        final boolean checkVerzending = arguments.contains("verzending");

        // Controleer Brp gestart
        try {
            final String server = PropertyUtil.getProperty("brp.server");
            BrpInitializationWaiter.controleerBrpGestart(server, checkMessage, checkGba, checkBijhouding, checkBevraging, checkLevering, checkVerzending);
        } catch (final HerhaalException e) {
            throw new RuntimeException(e);
        }
    }

    private static void controleerBrpGestart(
            final String server,
            final boolean checkMessage,
            final boolean checkGba,
            final boolean checkBijhouding,
            final boolean checkBevraging,
            final boolean checkLevering,
            final boolean checkVerzending) throws HerhaalException {
        LOG.info("Controle op initialisatie Brp gestart");

        final Herhaal herhaling = new Herhaal(10000, 60, Herhaal.Strategie.REGELMATIG);
        herhaling.herhaal(new Runnable() {
            @Override
            public void run() {
                try {
                    final JMXConnector connector = JMXConnectorFactory.connect(new JMXServiceURL(server));
                    final MBeanServerConnection connection = connector.getMBeanServerConnection();

                    if (checkMessage) {
                        connection.getMBeanInfo(new ObjectName("nl.bzk.brp:type=Broker,brokerName=BrpMessageBroker"));
                    }
                    if (checkGba) {
                        connection.getMBeanInfo(new ObjectName("centrale-caches:name=Caches"));
                    }
                    if (checkBijhouding) {
                        LOG.debug("Niets om op te controleren");
                    }
                    if (checkBevraging) {
                        connection.getMBeanInfo(new ObjectName("bevraging-caches:name=Caches"));
                    }
                    if (checkLevering) {
                        connection.getMBeanInfo(new ObjectName("levering-caches:name=Caches"));
                        connection.getMBeanInfo(new ObjectName("levering-nl.bzk.brp:name=MutatieLevering"));
                    }
                    if (checkVerzending) {
                        connection.getMBeanInfo(new ObjectName("verzending-caches:name=Caches"));
                    }
                } catch (final
                IOException
                        | InstanceNotFoundException
                        | IntrospectionException
                        | ReflectionException
                        | MalformedObjectNameException e) {
                    LOG.info("Brp niet gestart: " + e);
                    throw new RuntimeException("Brp niet gestart.");
                }
            }
        });
        LOG.info("Brp gestart");
    }
}
