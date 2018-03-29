/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.command.client;

import java.io.IOException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.isc.jbpm.command.Command;
import nl.bzk.migratiebrp.isc.jbpm.command.exception.CommandException;
import nl.bzk.migratiebrp.isc.jbpm.command.executor.CommandServiceImpl;

/**
 * Service voor het versturen van een commando naar een remote Jbpm node.
 */
public final class CommandClientImpl implements CommandClient {

    private static final Logger LOG = LoggerFactory.getLogger();

    private MBeanServerConnection serverConnection;

    /**
     * Zet de server connectie.
     * @param serverConnection de te zetten server connectie.
     */
    public void setServerConnection(final MBeanServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    @Override
    public <T> T executeCommand(final Command<T> command) throws CommandException {
        LOG.info("Sending command: " + command.getClass().getName());

        try {
            // Invoke de operatie via JMX.
            final ObjectName objectNameCommandExecutor = new ObjectName(CommandServiceImpl.JMX_NAME);

            return (T) serverConnection.invoke(
                    objectNameCommandExecutor,
                    "executeCommand",
                    new Object[]{command},
                    new String[]{Command.class.getName()});

        } catch (final
        ReflectionException
                | MalformedObjectNameException
                | InstanceNotFoundException
                | MBeanException e) {
            throw new CommandException("Onverwachte fout bij versturen commando.", e);
        } catch (final IOException e) {
            throw new CommandException("JMX connectie fout bij versturen commando.", e);
        }

    }

}
