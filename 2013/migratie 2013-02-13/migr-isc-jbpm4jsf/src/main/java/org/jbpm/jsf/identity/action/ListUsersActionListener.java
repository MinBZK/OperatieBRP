/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.identity.action;

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
public final class ListUsersActionListener implements JbpmActionListener {
    private final ValueExpression targetExpression;

    public ListUsersActionListener(final ValueExpression targetExpression) {
        this.targetExpression = targetExpression;
    }

    public String getName() {
        return "listUsers";
    }

    public void handleAction(JbpmJsfContext context, ActionEvent event) {
        try {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ELContext elContext = facesContext.getELContext();
            final IdentitySession identitySession = new IdentitySession(context.getJbpmContext().getSession());
            targetExpression.setValue(elContext, identitySession.getUsers());
            context.selectOutcome("success");
        } catch (Exception ex) {
            context.setError("Error loading user list", ex);
            return;
        }
    }
}
