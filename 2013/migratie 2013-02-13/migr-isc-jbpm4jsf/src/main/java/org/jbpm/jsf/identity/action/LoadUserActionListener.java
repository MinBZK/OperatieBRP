/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.identity.action;

import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.JbpmJsfContext;
import org.jbpm.identity.User;
import org.jbpm.identity.hibernate.IdentitySession;

import javax.faces.event.ActionEvent;
import javax.faces.context.FacesContext;
import javax.el.ValueExpression;
import javax.el.ELContext;

/**
 *
 */
public final class LoadUserActionListener implements JbpmActionListener {

    private final ValueExpression idExpression;
    private final ValueExpression targetExpression;

    public LoadUserActionListener(final ValueExpression idExpression, final ValueExpression targetExpression) {
        this.idExpression = idExpression;
        this.targetExpression = targetExpression;
    }

    public String getName() {
        return "loadUser";
    }

    public void handleAction(JbpmJsfContext context, ActionEvent event) {
        try {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ELContext elContext = facesContext.getELContext();
            final Object idValue = idExpression.getValue(elContext);
            final IdentitySession identitySession = new IdentitySession(context.getJbpmContext().getSession());
            if (idValue == null) {
                context.setError("Error loading user", "The ID value is null");
                return;
            }
            final long id;
            if (idValue instanceof Long) {
                id = ((Long)idValue).longValue();
            } else {
                id = Long.valueOf(idValue.toString()).longValue();
            }
            final User user = identitySession.loadUser(id);
            if (user == null) {
                context.setError("Error loading user", "No user was found with an ID of " + id);
                return;
            }
            targetExpression.setValue(elContext, user);
            context.selectOutcome("success");
        } catch (Exception ex) {
            context.setError("Error loading user", ex);
            return;
        }
    }
}
