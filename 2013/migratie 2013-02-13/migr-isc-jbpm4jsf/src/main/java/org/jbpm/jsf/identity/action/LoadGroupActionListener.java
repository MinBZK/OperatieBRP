/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.identity.action;

import org.jbpm.identity.Group;
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
public final class LoadGroupActionListener implements JbpmActionListener {

    private final ValueExpression idExpression;
    private final ValueExpression targetExpression;

    public LoadGroupActionListener(final ValueExpression idExpression, final ValueExpression targetExpression) {
        this.idExpression = idExpression;
        this.targetExpression = targetExpression;
    }

    public String getName() {
        return "loadGroup";
    }

    public void handleAction(JbpmJsfContext context, ActionEvent event) {
        try {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ELContext elContext = facesContext.getELContext();
            final Object idValue = idExpression.getValue(elContext);
            final IdentitySession identitySession = new IdentitySession(context.getJbpmContext().getSession());
            if (idValue == null) {
                context.setError("Error loading group", "The ID value is null");
                return;
            }
            final long id;
            if (idValue instanceof Long) {
                id = ((Long)idValue).longValue();
            } else {
                id = Long.valueOf(idValue.toString()).longValue();
            }
            final Group group = identitySession.loadGroup(id);
            if (group == null) {
                context.setError("Error loading group", "No group was found with an ID of " + id);
                return;
            }
            targetExpression.setValue(elContext, group);
            context.selectOutcome("success");
        } catch (Exception ex) {
            context.setError("Error loading group", ex);
            return;
        }
    }
}
