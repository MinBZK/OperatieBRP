/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.action;

import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.JbpmJsfContext;
import org.jbpm.jsf.core.impl.UpdatesHashMap;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;

import javax.faces.event.ActionEvent;
import javax.faces.context.FacesContext;
import javax.el.ValueExpression;
import javax.el.ELContext;

import java.util.Set;

/**
 *
 */
public final class ApplyVariableMapActionListener implements JbpmActionListener {
    private final ValueExpression variableMapExpression;
    private final ValueExpression targetExpression;

    public ApplyVariableMapActionListener(final ValueExpression variableMapExpression, final ValueExpression targetExpression) {
        this.variableMapExpression = variableMapExpression;
        this.targetExpression = targetExpression;
    }

    public String getName() {
        return "applyVariableMap";
    }

    public void handleAction(JbpmJsfContext context, ActionEvent event) {
        try {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ELContext elContext = facesContext.getELContext();
            final UpdatesHashMap updatesHashMap = (UpdatesHashMap) variableMapExpression.getValue(elContext);
            final Set<String> deletes = updatesHashMap.deletesSet();
            final Set<String> updates = updatesHashMap.updatesSet();
            boolean updated = false;

            final Object targetValue = targetExpression.getValue(elContext);
            if (targetValue instanceof ProcessInstance) {
                final ProcessInstance processInstance = (ProcessInstance) targetValue;
                final ContextInstance contextInstance = processInstance.getContextInstance();
                for (String name : deletes) {
                    contextInstance.deleteVariable(name);
                    updated = true;
                }
                for (String name : updates) {
                    contextInstance.setVariable(name, updatesHashMap.get(name));
                    updated = true;
                }
            } else if (targetValue instanceof Token) {
                final Token token = (Token) targetValue;
                final ProcessInstance processInstance = token.getProcessInstance();
                final ContextInstance contextInstance = processInstance.getContextInstance();
                for (String name : deletes) {
                    contextInstance.deleteVariable(name, token);
                    updated = true;
                }
                for (String name : updates) {
                    contextInstance.setVariable(name, updatesHashMap.get(name), token);
                    updated = true;
                }
            } else if (targetValue instanceof TaskInstance) {
                final TaskInstance task = (TaskInstance) targetValue;
                for (String name : deletes) {
                    task.deleteVariable(name);
                    updated = true;
                }
                for (String name : updates) {
                    task.setVariable(name, updatesHashMap.get(name));
                    updated = true;
                }
            } else if (targetValue == null) {
                context.setError("Error updating variable map", "The target value was given as null");
                return;
            } else {
                context.setError("Error updating variable map", "The target value is not a recognized type");
                return;
            }
            if (updated) {
                context.addSuccessMessage("Variables updated");
            }
        } catch (Exception ex) {
            context.setError("Error updating variable map", ex);
        }
    }
}
