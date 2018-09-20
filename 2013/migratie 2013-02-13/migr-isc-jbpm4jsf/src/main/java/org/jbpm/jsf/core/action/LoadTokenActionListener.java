/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.action;

import org.jbpm.graph.exe.Token;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.JbpmJsfContext;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseId;

/**
 *
 */
public final class LoadTokenActionListener implements JbpmActionListener {

    private final ValueExpression idExpression;
    private final ValueExpression targetExpression;
    private final ValueExpression forUpdateExpression;

    public LoadTokenActionListener(final ValueExpression idExpression, final ValueExpression targetExpression, final ValueExpression forUpdateExpression) {
        this.idExpression = idExpression;
        this.targetExpression = targetExpression;
        this.forUpdateExpression = forUpdateExpression;
    }

    public String getName() {
        return "loadProcessInstance";
    }

    public void handleAction(JbpmJsfContext context, ActionEvent event) {
        try {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ELContext elContext = facesContext.getELContext();
            final Object idValue = idExpression.getValue(elContext);
            if (idValue == null) {
                context.setError("Error loading token", "The ID value is null");
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
                    context.setError("Error loading token", "The value of 'forUpdate' is null");
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
            final Token token;
            if (forUpdate) {
                token = context.getJbpmContext().getTokenForUpdate(id);
            } else {
                token = context.getJbpmContext().getToken(id);
            }
            if (token == null) {
                context.setError("Error loading token", "No token was found with an ID of " + id);
                return;
            }
            targetExpression.setValue(elContext, token);
            context.selectOutcome("success");
        } catch (Exception ex) {
            context.setError("Error loading token", ex);
            return;
        }
    }
}
