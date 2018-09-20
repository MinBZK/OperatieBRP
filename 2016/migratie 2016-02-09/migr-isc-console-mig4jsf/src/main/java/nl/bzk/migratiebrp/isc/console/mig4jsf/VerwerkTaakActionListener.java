/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import nl.bzk.migratiebrp.isc.jbpm.command.client.CommandClient;
import nl.bzk.migratiebrp.isc.jbpm.command.exception.CommandException;
import nl.bzk.migratiebrp.isc.jbpm.command.impl.JbpmTaakCommand;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringService;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringServiceFactory;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.jbpm.jsf.JbpmJsfContext;
import org.jbpm.jsf.core.impl.UpdatesHashMap;

/**
 * Action listener voor het verwerken van een taak.
 */
public final class VerwerkTaakActionListener extends AbstractActionListener {

    private static final Logger LOG = LoggerFactory.getLogger();

    private final ValueExpression idExpression;
    private final ValueExpression transitionExpression;
    private final ValueExpression commentExpression;
    private final ValueExpression variableMapExpression;

    /**
     * Constructor voor de action listener.
     *
     * @param idExpression
     *            Het id van de taak.
     * @param transitionExpression
     *            De gekozen transitie.
     * @param commentExpression
     *            Eventueel toegevoegd commentaar.
     * @param variableMapExpression
     *            Verzameling van variabelen behorende bij de taak.
     */
    public VerwerkTaakActionListener(
        final ValueExpression idExpression,
        final ValueExpression transitionExpression,
        final ValueExpression commentExpression,
        final ValueExpression variableMapExpression)
    {
        super("verwerkTaak");
        this.idExpression = idExpression;
        this.transitionExpression = transitionExpression;
        this.commentExpression = commentExpression;
        this.variableMapExpression = variableMapExpression;
    }

    @Override
    public void verwerkAction(final JbpmJsfContext context, final ActionEvent event) throws CommandException {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final ELContext elContext = facesContext.getELContext();

        final Long id = (Long) getWaarde(idExpression, elContext);
        if (id == null) {
            context.setError("Fout bij het voltooien van de taak.", "De geselecteerde taak is niet geldig.");
            return;
        }

        final String transitie = (String) getWaarde(transitionExpression, elContext);
        final String commentaar = (String) getWaarde(commentExpression, elContext);
        final UpdatesHashMap wijzigingenHashMap = (UpdatesHashMap) getWaarde(variableMapExpression, elContext);

        LOG.debug("Id = {}", id);
        LOG.debug("Transitie = {}", transitie);
        LOG.debug("Commentaar = {}", commentaar);
        LOG.debug("VariableMap = {}", wijzigingenHashMap);

        final Map<String, Object> updateSet = new HashMap<>();
        final Set<String> deletes = new HashSet<>();

        if (wijzigingenHashMap != null) {
            deletes.addAll(wijzigingenHashMap.deletesSet());

            for (final String sleutelUpdate : wijzigingenHashMap.updatesSet()) {
                updateSet.put(sleutelUpdate, wijzigingenHashMap.get(sleutelUpdate));
            }
        }

        final JbpmTaakCommand command = new JbpmTaakCommand(id, deletes, updateSet, commentaar, transitie);
        final SpringService springService = (SpringService) context.getJbpmContext().getServiceFactory(SpringServiceFactory.SERVICE_NAME).openService();
        final CommandClient commandClient = springService.getBean(CommandClient.class);
        commandClient.executeCommand(command);

        context.addSuccessMessage("Taak voltooid.");
        context.selectOutcome("success");
    }

    private Object getWaarde(final ValueExpression valueExpression, final ELContext elContext) {
        return valueExpression == null ? null : valueExpression.getValue(elContext);
    }
}
