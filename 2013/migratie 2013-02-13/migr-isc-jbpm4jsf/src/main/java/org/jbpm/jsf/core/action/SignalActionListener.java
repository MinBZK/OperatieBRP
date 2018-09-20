/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.action;

import org.jbpm.graph.def.Transition;
import org.jbpm.graph.exe.Token;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.JbpmJsfContext;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 */
public final class SignalActionListener implements JbpmActionListener {

    private final ValueExpression valueExpression;
    private final ValueExpression transitionExpression;

    public SignalActionListener(final ValueExpression valueExpression, final ValueExpression transitionExpression) {
        this.valueExpression = valueExpression;
        this.transitionExpression = transitionExpression;
    }

    public String getName() {
        return "signal";
    }

    public void handleAction(JbpmJsfContext context, ActionEvent event) {
        try {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ELContext elContext = facesContext.getELContext();
            final Object value = valueExpression.getValue(elContext);
            final Token token;
            if (value == null) {
                context.setError("Signal failed", "The value is null");
                return;
            }
            if (value instanceof Token) {
                token = (Token) value;
            } else if (value instanceof ProcessInstance) {
                token = ((ProcessInstance)value).getRootToken();
            } else {
                context.setError("Signal failed", "The value type is not recognized");
                return;
            }
            if (transitionExpression != null) {
                final Object transitionValue = transitionExpression.getValue(elContext);
                if (transitionValue == null) {
                    token.signal();
                } else if (transitionValue instanceof Transition) {
                    token.signal((Transition)transitionValue);
                } else {
                    token.signal(transitionValue.toString());
                }
            } else {
                token.signal();
            }
            context.addSuccessMessage((value instanceof ProcessInstance) ? "Process instance" : "Token" + " signalled");
            context.getJbpmContext().getSession().flush();
            context.selectOutcome("success");
        } catch (Exception ex) {
            context.setError("Error signalling token", ex);
        }
    }
}