/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.identity.action;

import org.jbpm.identity.User;
import org.jbpm.identity.hibernate.IdentitySession;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.JbpmJsfContext;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 */
public final class CreateUserActionListener implements JbpmActionListener {

    private final ValueExpression userNameExpression;
    private final ValueExpression passwordExpression;
    private final ValueExpression passwordConfirmExpression;
    private final ValueExpression emailExpression;
    private final ValueExpression targetExpression;

    public CreateUserActionListener(final ValueExpression userNameExpression, final ValueExpression passwordExpression, final ValueExpression passwordConfirmExpression, final ValueExpression emailExpression, final ValueExpression targetExpression) {
        this.userNameExpression = userNameExpression;
        this.passwordExpression = passwordExpression;
        this.passwordConfirmExpression = passwordConfirmExpression;
        this.emailExpression = emailExpression;
        this.targetExpression = targetExpression;
    }

    public String getName() {
        return "createUser";
    }

    public void handleAction(JbpmJsfContext context, ActionEvent event) {
        try {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ELContext elContext = facesContext.getELContext();
            final IdentitySession identitySession = new IdentitySession(context.getJbpmContext().getSession());
            if (userNameExpression == null) {
                context.setError("Error creating user", "The user name expression is missing");
                return;
            }
            final Object userNameValue = userNameExpression.getValue(elContext);
            if (userNameValue == null) {
                context.setError("Error creating user", "The user name value is null");
                return;
            }
            final User user = new User(userNameValue.toString());
            if (passwordExpression != null) {
                final Object passwordValue = passwordExpression.getValue(elContext);
                if (passwordValue == null) {
                    context.setError("Error creating user", "The password value is null");
                    return;
                }
                if (passwordConfirmExpression != null) {
                    final Object passwordConfirmValue = passwordConfirmExpression.getValue(elContext);
                    if (! passwordValue.equals(passwordConfirmValue)) {
                        context.setError("Error creating user", "The password confirmation value does not match the password value");
                        return;
                    }
                }
                user.setPassword(passwordValue.toString());
            }
            if (emailExpression != null) {
                final Object emailValue = emailExpression.getValue(elContext);
                if (emailValue == null) {
                    context.setError("Error creating user", "The email value is null");
                    return;
                }
                user.setEmail(emailValue.toString());
            }
            identitySession.saveEntity(user);
            if (targetExpression != null) {
                targetExpression.setValue(elContext, user);
            }
            context.selectOutcome("success");
            context.addSuccessMessage("User created successfully");
        } catch (Exception ex) {
            context.setError("Error loading user", ex);
            return;
        }
    }
}
