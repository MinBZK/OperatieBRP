/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.action;

import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.JbpmJsfContext;
import org.jbpm.jsf.core.impl.UpdatesHashMap;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.taskmgmt.exe.TaskInstance;

import javax.faces.event.ActionEvent;
import javax.faces.context.FacesContext;
import javax.el.ValueExpression;
import javax.el.ELContext;

import java.util.Map;

public final class GetVariableMapActionListener implements JbpmActionListener {
    private final ValueExpression valueExpression;
    private final ValueExpression targetExpression;

    public GetVariableMapActionListener(final ValueExpression valueExpression, final ValueExpression targetExpression) {
        this.valueExpression = valueExpression;
        this.targetExpression = targetExpression;
    }

    public String getName() {
        return "getVariableMap";
    }

    @SuppressWarnings ({"unchecked"})
    public void handleAction(JbpmJsfContext context, ActionEvent event) {
        try {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ELContext elContext = facesContext.getELContext();
            final Object value = valueExpression.getValue(elContext);
            final Map<String,Object> updatesMap;
            if (value instanceof ProcessInstance) {
                final ProcessInstance processInstance = (ProcessInstance) value;
                updatesMap = new UpdatesHashMap(processInstance.getContextInstance().getVariables());
            } else if (value instanceof Token) {
                final Token token = (Token) value;
                updatesMap = new UpdatesHashMap(token.getProcessInstance().getContextInstance().getVariables(token));
            } else if (value instanceof TaskInstance) {
                final TaskInstance task = (TaskInstance) value;
                updatesMap = new UpdatesHashMap(task.getVariables());
            } else if (value == null) {
                context.setError("Error getting variable map", "The value was given as null");
                return;
            } else {
                context.setError("Error getting variable map", "The value is not a recognized type");
                return;
            }
            targetExpression.setValue(elContext, updatesMap);
        } catch (Exception ex) {
            context.setError("Error getting variable map", ex);
            return;
        }
    }
}
