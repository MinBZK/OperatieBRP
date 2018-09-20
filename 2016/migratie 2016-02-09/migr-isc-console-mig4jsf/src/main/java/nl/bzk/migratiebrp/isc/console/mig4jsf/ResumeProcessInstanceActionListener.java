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
import nl.bzk.migratiebrp.isc.jbpm.command.client.CommandClient;
import nl.bzk.migratiebrp.isc.jbpm.command.exception.CommandException;
import nl.bzk.migratiebrp.isc.jbpm.command.impl.JbpmResumeCommand;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringService;
import nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringServiceFactory;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.jsf.JbpmJsfContext;
import org.jbpm.logging.log.MessageLog;

/**
 * Resume a process instance.
 */
public final class ResumeProcessInstanceActionListener extends AbstractActionListener {

    private static final String MESSAGE_FAIL = "Het proces kon niet worden hervat.";
    private static final String MESSAGE_OK = "Het proces is hervat.";

    private final ValueExpression processInstanceExpression;

    /**
     * Constructor.
     *
     * @param processInstanceExpression
     *            process instance expression
     */
    public ResumeProcessInstanceActionListener(final ValueExpression processInstanceExpression) {
        super("resumeProcessInstance");
        this.processInstanceExpression = processInstanceExpression;
    }

    @Override
    public void verwerkAction(final JbpmJsfContext context, final ActionEvent event) throws CommandException {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final ELContext elContext = facesContext.getELContext();

        final ProcessInstance processInstance = (ProcessInstance) processInstanceExpression.getValue(elContext);
        if (processInstance == null) {
            context.setError(MESSAGE_FAIL, "Geen process instantie doorgegeven");
            return;
        }

        final JbpmResumeCommand jbpmResumeCommand = new JbpmResumeCommand(processInstance.getId());

        final SpringService springService = (SpringService) context.getJbpmContext().getServiceFactory(SpringServiceFactory.SERVICE_NAME).openService();
        final CommandClient commandClient = springService.getBean(CommandClient.class);
        commandClient.executeCommand(jbpmResumeCommand);

        processInstance.getRootToken().addLog(new MessageLog("Beheerder heeft procesinstantie (id=" + processInstance.getId() + ") hervat."));

        context.addSuccessMessage(MESSAGE_OK);
        context.selectOutcome("success");
    }
}
