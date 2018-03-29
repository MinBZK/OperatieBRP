/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import nl.bzk.migratiebrp.isc.jbpm.command.impl.JbpmStartTaakCommand;
import org.jbpm.jsf.JbpmJsfContext;

/**
 * Action listener voor het starten van een taak.
 */
public final class StartTaakActionListener extends AbstractActionListener {

    private final ValueExpression taskInstanceExpression;
    private final ValueExpression actorIdExpression;

    /**
     * Constructor voor de action listener.
     * @param taskInstanceExpression Het id van de taak.
     * @param actorIdExpression Het id van de actor.
     */
    public StartTaakActionListener(final ValueExpression taskInstanceExpression, final ValueExpression actorIdExpression) {
        super("startTaak");
        this.taskInstanceExpression = taskInstanceExpression;
        this.actorIdExpression = actorIdExpression;
    }

    @Override
    public void verwerkAction(final JbpmJsfContext context, final ActionEvent event) throws ActionException {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final ELContext elContext = facesContext.getELContext();

        final Long taakId = (Long) getWaarde(taskInstanceExpression, elContext);
        final String actorId = (String) getWaarde(actorIdExpression, elContext);

        if (taakId == null) {
            context.setError("Fout bij het voltooien van de taak.", "De geselecteerde taak is niet geldig.");
            return;
        }

        final JbpmStartTaakCommand command = new JbpmStartTaakCommand(taakId, actorId);
        executeCommand(context, command);
        context.addSuccessMessage("Taak gestart");
        context.selectOutcome("success");
    }

    private Object getWaarde(final ValueExpression valueExpression, final ELContext elContext) {
        return valueExpression == null ? null : valueExpression.getValue(elContext);
    }
}
