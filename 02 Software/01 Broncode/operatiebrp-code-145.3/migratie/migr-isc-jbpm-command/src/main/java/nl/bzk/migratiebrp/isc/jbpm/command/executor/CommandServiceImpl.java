/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.command.executor;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.isc.jbpm.command.Command;
import nl.bzk.migratiebrp.isc.jbpm.command.exception.CommandException;
import nl.bzk.migratiebrp.isc.runtime.jbpm.JbpmInvoker;
import nl.bzk.migratiebrp.util.common.jmx.UseDynamicDomain;

import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * Implementatie voor het via JMX uitvoeren van commands.
 */
@UseDynamicDomain
@ManagedResource(objectName = CommandServiceImpl.JMX_NAME, description = "JMX Service voor ISC commando's")
public final class CommandServiceImpl implements CommandService {

    /**
     * JMX Naam.
     */
    public static final String JMX_NAME = "nl.bzk.migratiebrp.isc:name=COMMANDO";
    private static final Logger LOGGER = LoggerFactory.getLogger();

    private JbpmInvoker jbpmInvoker;

    /**
     * Zet de JBPM invoker.
     * @param jbpmInvoker de te zetten JBPM invoker.
     */
    public void setJbpmInvoker(final JbpmInvoker jbpmInvoker) {
        this.jbpmInvoker = jbpmInvoker;
    }

    @Override
    @ManagedOperation(description = "Commando uitvoeren")
    public <T> T executeCommand(final Command<T> command) throws CommandException {
        try {
            return jbpmInvoker.executeInContext(command);
        } catch (final Exception ex /* Exception opvangen vanwege robustheid webapp/JMX service */) {
            LOGGER.error("Het uitvoeren van het command {} is geresulteerd in een exceptie", command, ex);
            // Geef de exceptie door zodat de webapp deze kan afhandelen.
            throw new CommandException("Exceptie bij het uitvoeren van het command.", ex);
        }
    }
}
