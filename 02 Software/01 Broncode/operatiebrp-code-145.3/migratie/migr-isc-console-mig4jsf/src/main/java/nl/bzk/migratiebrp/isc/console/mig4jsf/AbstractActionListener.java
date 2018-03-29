/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

import javax.faces.event.ActionEvent;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.isc.jbpm.command.Command;
import nl.bzk.migratiebrp.isc.jbpm.command.client.CommandClient;
import nl.bzk.migratiebrp.isc.jbpm.command.exception.CommandException;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringService;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringServiceFactory;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.JbpmJsfContext;

/**
 * Basis action listener.
 */
public abstract class AbstractActionListener implements JbpmActionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    /**
     * name.
     **/
    private final String name;

    /**
     * Constructior.
     * @param name name
     */
    protected AbstractActionListener(final String name) {
        this.name = name;
    }

    @Override
    public final String getName() {
        return name;
    }

    /**
     * Verwerk de actie (vangt excepties op om deze netjes te tonen in de UI ipv te crashen. Delegeert de verwerking aan
     * {@link #verwerkAction(JbpmJsfContext, ActionEvent)}.
     * @param context context
     * @param event event
     */
    @Override
    public final void handleAction(final JbpmJsfContext context, final ActionEvent event) {
        try {
            verwerkAction(context, event);
        } catch (final Exception ex /* Catch exception voor robustheid in UI */) {
            LOGGER.info("Fout bij uitvoeren '{}'.", name, ex);
            context.setError("Fout bij uitvoeren '" + name + "'.", ex);
        }
    }

    /**
     * Verwerk de actie.
     * @param context context
     * @param event event
     * @throws ActionException bij fouten
     */
    protected abstract void verwerkAction(final JbpmJsfContext context, final ActionEvent event) throws ActionException;

    protected void executeCommand(JbpmJsfContext context, Command<Void> command) throws ActionException {
        final SpringService springService = (SpringService) context.getJbpmContext().getServiceFactory(SpringServiceFactory.SERVICE_NAME).openService();
        final CommandClient commandClient = springService.getBean(CommandClient.class);
        try {
            commandClient.executeCommand(command);
        } catch (CommandException e) {
            throw new ActionException(e);
        }

    }

}
