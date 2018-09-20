/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.identity.action;

import org.hibernate.Session;
import org.jbpm.identity.User;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.JbpmJsfContext;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 */
public final class DeleteUserActionListener implements JbpmActionListener {
    private final ValueExpression userExpression;

    public DeleteUserActionListener(final ValueExpression userExpression) {
        this.userExpression = userExpression;
    }

    public String getName() {
        return "deleteUser";
    }

    public void handleAction(JbpmJsfContext context, ActionEvent event) {
        try {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ELContext elContext = facesContext.getELContext();
            final User user = (User) userExpression.getValue(elContext);
            final Session session = context.getJbpmContext().getSession();
            session.delete(user);
            context.addSuccessMessage("Successfully deleted user");
        } catch (Exception ex) {
            context.setError("Failed to delete user", ex);
        }
    }
}
