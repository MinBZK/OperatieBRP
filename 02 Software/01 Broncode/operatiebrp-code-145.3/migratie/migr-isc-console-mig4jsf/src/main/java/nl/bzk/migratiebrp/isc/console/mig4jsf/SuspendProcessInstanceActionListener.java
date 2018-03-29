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
import nl.bzk.migratiebrp.isc.jbpm.command.impl.JbpmSuspendCommand;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.jsf.JbpmJsfContext;
import org.jbpm.logging.log.MessageLog;

/**
 * Suspend process instance.
 */
public final class SuspendProcessInstanceActionListener extends AbstractActionListener {

    private static final String MESSAGE_FAIL = "Het proces kon niet worden opgeschort.";
    private static final String MESSAGE_OK = "Het proces is opgeschort.";

    private final ValueExpression processInstanceExpression;

    /**
     * Constructor.
     * @param processInstanceExpression process instance expression
     */
    public SuspendProcessInstanceActionListener(final ValueExpression processInstanceExpression) {
        super("suspendProcessInstance");
        this.processInstanceExpression = processInstanceExpression;
    }

    @Override
    public void verwerkAction(final JbpmJsfContext context, final ActionEvent event) throws ActionException {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final ELContext elContext = facesContext.getELContext();

        final ProcessInstance processInstance = (ProcessInstance) processInstanceExpression.getValue(elContext);
        if (processInstance == null) {
            context.setError(MESSAGE_FAIL, "Geen process instantie doorgegeven");
            return;
        }

        final JbpmSuspendCommand jbpmSuspendCommand = new JbpmSuspendCommand(processInstance.getId());
        executeCommand(context, jbpmSuspendCommand);

        processInstance.getRootToken().addLog(new MessageLog("Beheerder heeft procesinstantie (id=" + processInstance.getId() + ") opgeschort."));

        context.addSuccessMessage(MESSAGE_OK);
        context.selectOutcome("success");
    }
}
