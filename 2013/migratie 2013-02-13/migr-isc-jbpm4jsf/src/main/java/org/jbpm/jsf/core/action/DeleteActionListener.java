/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.action;

import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.job.Job;
import org.jbpm.job.Timer;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.JbpmJsfContext;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 */
public final class DeleteActionListener implements JbpmActionListener {
    private final ValueExpression valueExpression;

    public DeleteActionListener(final ValueExpression valueExpression) {
        this.valueExpression = valueExpression;
    }

    public String getName() {
        return "delete";
    }

    public void handleAction(JbpmJsfContext context, ActionEvent event) {
        try {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ELContext elContext = facesContext.getELContext();
            final Object value = valueExpression.getValue(elContext);
            if (value == null) {
                context.setError("Delete failed", "The value is null");
                return;
            }
            if (value instanceof ProcessDefinition) {
                context.getJbpmContext().getGraphSession().deleteProcessDefinition((ProcessDefinition) value);
                context.addSuccessMessage("Process deleted");
            } else if (value instanceof ProcessInstance) {
                context.getJbpmContext().getGraphSession().deleteProcessInstance((ProcessInstance) value);
                context.addSuccessMessage("Process instance deleted");
            } else if (value instanceof Timer) {
                context.getJbpmContext().getServices().getSchedulerService().deleteTimer((Timer) value);
                context.addSuccessMessage("Timer deleted");
            } else if (value instanceof Job) {
                context.getJbpmContext().getJobSession().deleteJob((Job) value);
                context.addSuccessMessage("Job deleted");
            } else {
                context.setError("Delete failed", "Value type is not recognized");
                return;
            }
            context.getJbpmContext().getSession().flush();
            context.selectOutcome("success");
        } catch (Exception ex) {
            context.setError("Delete failed", ex);
            return;
        }
    }
}
