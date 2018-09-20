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
import javax.faces.event.PhaseId;

/**
 *
 */
public final class LoadTaskActionListener implements JbpmActionListener {

    private final ValueExpression idExpression;
    private final ValueExpression targetExpression;
    private final ValueExpression forUpdateExpression;

    public LoadTaskActionListener(final ValueExpression idExpression, final ValueExpression targetExpression, final ValueExpression forUpdateExpression) {
        this.idExpression = idExpression;
        this.targetExpression = targetExpression;
        this.forUpdateExpression = forUpdateExpression;
    }

    public String getName() {
        return "loadTask";
    }

    public void handleAction(JbpmJsfContext context, ActionEvent event) {
        try {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ELContext elContext = facesContext.getELContext();
            final Object idValue = idExpression.getValue(elContext);
            if (idValue == null) {
                context.setError("Error loading task instance", "The ID value is null");
                return;
            }
            final long id;
            if (idValue instanceof Long) {
                id = ((Long)idValue).longValue();
            } else {
                id = Long.valueOf(idValue.toString()).longValue();
            }
            final boolean forUpdate;
            if (forUpdateExpression != null) {
                final Object forUpdateValue = forUpdateExpression.getValue(elContext);
                if (forUpdateValue == null) {
                    context.setError("Error loading task instance", "The value of 'forUpdate' is null");
                    return;
                }
                if (forUpdateValue instanceof Boolean) {
                    forUpdate = ((Boolean)forUpdateValue).booleanValue();
                } else {
                    forUpdate = Boolean.parseBoolean(forUpdateValue.toString());
                }
            } else {
                forUpdate = event.getPhaseId() != PhaseId.RENDER_RESPONSE;
            }
            final TaskInstance taskInstance;
            if (forUpdate) {
                taskInstance = context.getJbpmContext().getTaskInstanceForUpdate(id);
            } else {
                taskInstance = context.getJbpmContext().getTaskInstance(id);
            }
            if (taskInstance == null) {
                context.setError("Error loading task instance", "No task instance was found with an ID of " + id);
                return;
            }
            targetExpression.setValue(elContext, taskInstance);
            context.selectOutcome("success");
        } catch (Exception ex) {
            context.setError("Error loading task", ex);
            return;
        }
    }
}
