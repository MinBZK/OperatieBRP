/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.action;

import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.JbpmJsfContext;
import org.jbpm.taskmgmt.exe.TaskInstance;

import javax.el.ValueExpression;
import javax.el.ELContext;
import javax.faces.event.ActionEvent;
import javax.faces.context.FacesContext;

import java.util.Date;

/**
 *
 */
public final class UpdateTaskStartActionListener implements JbpmActionListener {
    private final ValueExpression taskExpression;
    private final ValueExpression startDateExpression;


    public UpdateTaskStartActionListener(final ValueExpression startDateExpression, final ValueExpression taskExpression) {
        this.startDateExpression = startDateExpression;
        this.taskExpression = taskExpression;
    }

    public String getName() {
        return "updateTaskStart";
    }

    public void handleAction(JbpmJsfContext context, ActionEvent event) {
        try {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ELContext elContext = facesContext.getELContext();
            final Object taskInstanceValue = taskExpression.getValue(elContext);
            if (taskInstanceValue == null) {
                context.setError("Error updating task", "The task instance value is null");
                return;
            }
            if (!(taskInstanceValue instanceof TaskInstance)) {
                context.setError("Error updating task", "Attempted to resume something other than a task instance");
                return;
            }
            final TaskInstance taskInstance = (TaskInstance) taskInstanceValue;
            final Date start;
            if (startDateExpression != null) {
                final Object startDateValue = startDateExpression.getValue(elContext);
                if (startDateValue == null) {
                    context.setError("Error updating task", "Start date value is null");
                    return;
                }
                if (startDateValue instanceof Date) {
                    start = (Date) startDateValue;
                } else if (startDateValue instanceof Long) {
                    start = new Date(((Long)startDateValue).longValue());
                } else {
                    context.setError("Error updating task", "Start date value is not a recognized type");
                    return;
                }
            } else {
                start = new Date();
            }
            taskInstance.setStart(start);
            context.getJbpmContext().getSession().flush();
        } catch (Exception ex) {
            context.setError("Error updating task", ex);
            return;
        }
    }
}
