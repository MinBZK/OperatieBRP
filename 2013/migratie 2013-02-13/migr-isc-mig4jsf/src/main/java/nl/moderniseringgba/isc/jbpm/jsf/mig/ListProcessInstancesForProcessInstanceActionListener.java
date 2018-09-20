/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.jbpm.jsf.mig;

import java.util.Collections;
import java.util.List;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.JbpmJsfContext;

public class ListProcessInstancesForProcessInstanceActionListener implements JbpmActionListener {

    private final ValueExpression targetExpression;
    private final ValueExpression processInstanceExpression;

    public ListProcessInstancesForProcessInstanceActionListener(
            final ValueExpression processInstanceExpression,
            final ValueExpression targetExpression) {
        this.processInstanceExpression = processInstanceExpression;
        this.targetExpression = targetExpression;
    }

    @Override
    public String getName() {
        return "listProcessInstancesForProcessInstance";
    }

    @Override
    public void handleAction(final JbpmJsfContext context, final ActionEvent event) {
        try {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ELContext elContext = facesContext.getELContext();
            final ProcessInstance rootProcessInstance =
                    ProcessInstanceUtil.getRootProcessInstance((ProcessInstance) processInstanceExpression
                            .getValue(elContext));

            final List<ProcessInstance> allProcessInstances =
                    ProcessInstanceUtil.getProcessInstances(context.getJbpmContext(), rootProcessInstance);

            targetExpression.setValue(elContext, Collections.unmodifiableCollection(allProcessInstances));
            context.selectOutcome("success");
        } catch (final Exception ex) {
            context.setError("Error loading berichten list", ex);
            return;
        }
    }

}
