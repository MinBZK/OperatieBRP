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
import org.jbpm.context.exe.ContextInstance;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 */
public final class GetVariableActionListener implements JbpmActionListener {
    private final ValueExpression nameExpression;
    private final ValueExpression targetExpression;
    private final ValueExpression entityExpression;

    public GetVariableActionListener(final ValueExpression nameExpression, final ValueExpression targetExpression, final ValueExpression entityExpression) {
        this.nameExpression = nameExpression;
        this.targetExpression = targetExpression;
        this.entityExpression = entityExpression;
    }

    public String getName() {
        return "getVariable";
    }

    public void handleAction(JbpmJsfContext context, ActionEvent event) {
        try {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ELContext elContext = facesContext.getELContext();
            final Object nameValue = nameExpression.getValue(elContext);
            if (nameValue == null) {
                context.setError("Error getting variable", "The value of the 'name' attribute is null");
                return;
            }
            final String name = nameValue.toString();
            final Object entity = entityExpression.getValue(elContext);
            final Object value;
            if (entity instanceof TaskInstance) {
                final TaskInstance task = (TaskInstance) entity;
                value = task.getVariable(name);
            } else if (entity instanceof Token) {
                final Token token = (Token) entity;
                final ContextInstance contextInstance = token.getProcessInstance().getContextInstance();
                value = contextInstance.getVariable(name, token);
            } else if (entity instanceof ProcessInstance) {
                final ProcessInstance processInstance = (ProcessInstance) entity;
                final ContextInstance contextInstance = processInstance.getContextInstance();
                value = contextInstance.getVariable(name);
            } else {
                context.setError("Error getting variable", "The value given for the 'entity' attribute is not a task, token, or process instance");
                return;
            }
            targetExpression.setValue(elContext, value);
            context.selectOutcome("success");
        } catch (Exception ex) {
            context.setError("Error getting variable", ex);
            return;
        }
    }
}
