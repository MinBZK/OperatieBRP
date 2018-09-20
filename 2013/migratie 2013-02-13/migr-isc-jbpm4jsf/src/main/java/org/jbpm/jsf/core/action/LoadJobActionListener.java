/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.action;

import org.jbpm.job.Job;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.JbpmJsfContext;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 */
public final class LoadJobActionListener implements JbpmActionListener {

    private final ValueExpression idExpression;
    private final ValueExpression targetExpression;

    public LoadJobActionListener(final ValueExpression idExpression, final ValueExpression targetExpression) {
        this.idExpression = idExpression;
        this.targetExpression = targetExpression;
    }

    public String getName() {
        return "loadJob";
    }

    public void handleAction(JbpmJsfContext context, ActionEvent event) {
        try {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ELContext elContext = facesContext.getELContext();
            final Object idValue = idExpression.getValue(elContext);
            if (idValue == null) {
                context.setError("Error loading job instance", "The ID value is null");
                return;
            }
            final long id;
            if (idValue instanceof Long) {
                id = ((Long)idValue).longValue();
            } else {
                id = Long.valueOf(idValue.toString()).longValue();
            }
            final Job job;
            job = context.getJbpmContext().getJobSession().getJob(id);
            if (job == null) {
                context.setError("Error loading job instance", "No job instance was found with an ID of " + id);
                return;
            }
            targetExpression.setValue(elContext, job);
            context.selectOutcome("success");
        } catch (Exception ex) {
            context.setError("Error loading job", ex);
            return;
        }
    }
}
