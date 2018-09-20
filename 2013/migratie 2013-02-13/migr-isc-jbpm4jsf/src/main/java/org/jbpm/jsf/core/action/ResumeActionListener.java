/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.action;

import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.JbpmJsfContext;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.graph.exe.ProcessInstance;

import javax.el.ValueExpression;
import javax.el.ELContext;
import javax.faces.event.ActionEvent;
import javax.faces.context.FacesContext;

/**
 *
 */
public final class ResumeActionListener implements JbpmActionListener {

    private final ValueExpression valueExpression;

    public ResumeActionListener(final ValueExpression valueExpression) {
        this.valueExpression = valueExpression;
    }

    public String getName() {
        return "resume";
    }

    public void handleAction(JbpmJsfContext context, ActionEvent event) {
        try {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ELContext elContext = facesContext.getELContext();
            final Object value = valueExpression.getValue(elContext);
            if (value == null) {
                context.setError("Resume failed", "The value is null");
                return;
            }
            if (value instanceof TaskInstance) {
                ((TaskInstance)value).resume();
                context.addSuccessMessage("Task instance resumed");
            } else if (value instanceof Token) {
                ((Token)value).resume();
                context.addSuccessMessage("Token resumed");
            } else if (value instanceof ProcessInstance) {
                ((ProcessInstance)value).resume();
                context.addSuccessMessage("Process instance resumed");
            } else {
                context.setError("Resume failed", "The value type is not recognized");
                return;
            }
            context.getJbpmContext().getSession().flush();
            context.selectOutcome("success");
        } catch (Exception ex) {
            context.setError("Resume failed", ex);
            return;
        }
    }
}
