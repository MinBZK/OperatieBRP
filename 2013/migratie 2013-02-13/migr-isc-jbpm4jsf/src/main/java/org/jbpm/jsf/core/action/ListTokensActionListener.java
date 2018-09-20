/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.action;

import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.JbpmJsfContext;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

public final class ListTokensActionListener implements JbpmActionListener {
    private final ValueExpression targetExpression;
    private final ValueExpression processInstanceExpression;

    public ListTokensActionListener(final ValueExpression processInstanceExpression, final ValueExpression targetExpression) {
        this.processInstanceExpression = processInstanceExpression;
        this.targetExpression = targetExpression;
    }

    public String getName() {
        return "listTokens";
    }

    public void handleAction(JbpmJsfContext context, ActionEvent event) {
        try {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ELContext elContext = facesContext.getELContext();
            final Object processInstanceValue = processInstanceExpression.getValue(elContext);
            if (!(processInstanceValue instanceof ProcessInstance)) {
                context.setError("Error listing token instances", "The given process instance is not an instance of ProcessInstance");
                return;
            }
            targetExpression.setValue(elContext, ((ProcessInstance)processInstanceValue).findAllTokens());
            context.selectOutcome("success");
        } catch (Exception ex) {
            context.setError("Error listing token instances", ex);
            return;
        }
    }
}
