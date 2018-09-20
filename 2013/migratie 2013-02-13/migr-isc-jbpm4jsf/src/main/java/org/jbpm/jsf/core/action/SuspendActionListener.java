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
public final class SuspendActionListener implements JbpmActionListener {

    private final ValueExpression valueExpression;

    public SuspendActionListener(final ValueExpression valueExpression) {
        this.valueExpression = valueExpression;
    }

    public String getName() {
        return "suspend";
    }

    public void handleAction(JbpmJsfContext context, ActionEvent event) {
        try {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ELContext elContext = facesContext.getELContext();
            final Object value = valueExpression.getValue(elContext);
            if (value == null) {
                context.setError("Suspend failed", "The value is null");
                return;
            }
            if (value instanceof TaskInstance) {
                ((TaskInstance)value).suspend();
                context.addSuccessMessage("Task instance suspended");
            } else if (value instanceof Token) {
                ((Token)value).suspend();
                context.addSuccessMessage("Token suspended");
            } else if (value instanceof ProcessInstance) {
                ((ProcessInstance)value).suspend();
                context.addSuccessMessage("Process instance suspended");
            } else {
                context.setError("Suspend failed", "The value type is not recognized");
                return;
            }
            context.getJbpmContext().getSession().flush();
            context.selectOutcome("success");
        } catch (Exception ex) {
            context.setError("Suspend failed", ex);
            return;
        }
    }
}
