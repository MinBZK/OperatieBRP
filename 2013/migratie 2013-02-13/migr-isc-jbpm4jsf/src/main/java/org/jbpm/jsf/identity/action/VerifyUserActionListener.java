/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.identity.action;

import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.JbpmJsfContext;
import org.jbpm.identity.hibernate.IdentitySession;

import javax.el.ValueExpression;
import javax.el.ELContext;
import javax.faces.event.ActionEvent;
import javax.faces.context.FacesContext;

/**
 *
 */
public final class VerifyUserActionListener implements JbpmActionListener {
    private final ValueExpression userNameExpression;
    private final ValueExpression passwordExpression;
    private final ValueExpression userIdTargetExpression;

    public VerifyUserActionListener(final ValueExpression userNameExpression, final ValueExpression passwordExpression, final ValueExpression userIdTargetExpression) {
        this.userNameExpression = userNameExpression;
        this.passwordExpression = passwordExpression;
        this.userIdTargetExpression = userIdTargetExpression;
    }

    public String getName() {
        return "verifyUser";
    }

    public void handleAction(JbpmJsfContext context, ActionEvent event) {
        try {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ELContext elContext = facesContext.getELContext();
            final IdentitySession identitySession = new IdentitySession(context.getJbpmContext().getSession());
            final Object userNameValue = userNameExpression.getValue(elContext);
            if (userNameValue == null) {
                context.setError("Error verifying user", "User name is null");
                return;
            }
            final Object passwordValue = passwordExpression.getValue(elContext);
            if (passwordValue == null) {
                context.setError("Error verifying user", "Password is null");
                return;
            }
            final Object id = identitySession.verify(userNameValue.toString(), passwordValue.toString());
            if (id == null) {
                context.setError("Invalid user name or password");
                return;
            }
            if (userIdTargetExpression != null) {
                userIdTargetExpression.setValue(elContext, id);
            }
            context.selectOutcome("success");
        } catch (Exception ex) {
            context.setError("Error verifying user", ex);
            return;
        }
    }
}
