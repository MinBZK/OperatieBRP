/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.action;

import org.jbpm.graph.def.Transition;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.JbpmJsfContext;
import org.jbpm.taskmgmt.exe.TaskInstance;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 */
public final class CompleteTaskActionListener implements JbpmActionListener {

    private final ValueExpression taskInstanceExpression;
    private final ValueExpression transitionExpression;

    public CompleteTaskActionListener(final ValueExpression taskInstanceExpression, final ValueExpression transitionExpression) {
        this.taskInstanceExpression = taskInstanceExpression;
        this.transitionExpression = transitionExpression;
    }

    public String getName() {
        return "completeTask";
    }

    public void handleAction(JbpmJsfContext context, ActionEvent event) {
        try {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ELContext elContext = facesContext.getELContext();
            final Object taskInstanceValue = taskInstanceExpression.getValue(elContext);
            if (taskInstanceValue == null) {
                context.setError("Error completing task", "The task instance value is null");
                return;
            }
            if (!(taskInstanceValue instanceof TaskInstance)) {
                context.setError("Error completing task", "Attempted to complete something other than a task instance");
                return;
            }
            final TaskInstance taskInstance = (TaskInstance) taskInstanceValue;
            if (transitionExpression != null) {
                final Object transitionValue = transitionExpression.getValue(elContext);
                if (transitionValue == null) {
                    taskInstance.end();
                } else if (transitionValue instanceof Transition) {
                    taskInstance.end((Transition)transitionValue);
                } else {
                    final String transitionName = transitionValue.toString();
                    taskInstance.end(transitionName);
                }
            } else {
                taskInstance.end();
            }
            context.addSuccessMessage("Task completed");
            context.getJbpmContext().getSession().flush();
            context.selectOutcome("success");
        } catch (Exception ex) {
            context.setError("Error completing task", ex);
            return;
        }
    }
}
