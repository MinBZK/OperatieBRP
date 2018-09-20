/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.action;

import java.util.Collection;
import java.util.Collections;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.JbpmJsfContext;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

public final class ListTasksForProcessInstanceActionListener implements JbpmActionListener {
    private final ValueExpression targetExpression;
    private final ValueExpression processInstanceExpression;

    public ListTasksForProcessInstanceActionListener(final ValueExpression processInstanceExpression, final ValueExpression targetExpression) {
        this.processInstanceExpression = processInstanceExpression;
        this.targetExpression = targetExpression;
    }

    public String getName() {
        return "listTasksForProcessInstance";
    }

    public void handleAction(JbpmJsfContext context, ActionEvent event) {
        try {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ELContext elContext = facesContext.getELContext();
            final ProcessInstance processInstance = (ProcessInstance) processInstanceExpression.getValue(elContext);
            Collection<?> taskInstances = processInstance.getTaskMgmtInstance().getTaskInstances();
            taskInstances = Collections.unmodifiableCollection(taskInstances);
            targetExpression.setValue(elContext, taskInstances);
            context.selectOutcome("success");
        } catch (Exception ex) {
            context.setError("Error loading task list", ex);
            return;
        }
    }
}
