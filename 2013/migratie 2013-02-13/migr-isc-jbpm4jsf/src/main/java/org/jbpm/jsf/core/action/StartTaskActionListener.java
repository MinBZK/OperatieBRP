/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.action;

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
public final class StartTaskActionListener implements JbpmActionListener {

    private final ValueExpression taskInstanceExpression;
    private final ValueExpression actorIdExpression;
    private final ValueExpression overwriteSwimlaneExpression;

    public StartTaskActionListener(final ValueExpression taskInstanceExpression, final ValueExpression actorIdExpression, final ValueExpression overwriteSwimlaneExpression) {
        this.taskInstanceExpression = taskInstanceExpression;
        this.actorIdExpression = actorIdExpression;
        this.overwriteSwimlaneExpression = overwriteSwimlaneExpression;
    }

    public String getName() {
        return "startTask";
    }

    public void handleAction(JbpmJsfContext context, ActionEvent event) {
        try {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ELContext elContext = facesContext.getELContext();
            final Object taskInstanceValue = taskInstanceExpression.getValue(elContext);
            if (taskInstanceValue == null) {
                context.setError("Error starting task", "The task instance value is null");
                return;
            }
            if (!(taskInstanceValue instanceof TaskInstance)) {
                context.setError("Error starting task", "Attempted to start something other than a task instance");
                return;
            }
            final TaskInstance taskInstance = (TaskInstance) taskInstanceValue;
            if (actorIdExpression != null) {
                final Object actorIdValue = actorIdExpression.getValue(elContext);
                if (actorIdValue == null) {
                    context.setError("Error starting task", "Actor ID expression resolved to null");
                    return;
                }
                final String actorId = actorIdValue.toString();
                if (overwriteSwimlaneExpression != null) {
                    final Object overwriteSwimlaneValue = overwriteSwimlaneExpression.getValue(elContext);
                    if (overwriteSwimlaneValue == null) {
                        context.setError("Error starting task", "Overwrite swimlane expression resolved to null");
                        return;
                    }
                    final Boolean overwriteSwimlane;
                    if (overwriteSwimlaneValue instanceof Boolean) {
                        overwriteSwimlane = (Boolean) overwriteSwimlaneValue;
                    } else {
                        overwriteSwimlane = Boolean.valueOf(overwriteSwimlaneValue.toString());
                    }
                    taskInstance.start(actorId, overwriteSwimlane.booleanValue());
                } else {
                    taskInstance.start(actorId);
                }
            } else {
                taskInstance.start();
            }
            context.addSuccessMessage("Task started");
            context.getJbpmContext().getSession().flush();
            context.selectOutcome("success");
        } catch (Exception ex) {
            context.setError("Error starting task", ex);
            return;
        }
    }
}
