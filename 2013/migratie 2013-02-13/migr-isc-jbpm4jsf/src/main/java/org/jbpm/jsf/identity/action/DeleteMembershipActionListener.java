/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.identity.action;

import java.util.Iterator;
import java.util.Set;
import org.jbpm.identity.Membership;
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
public final class DeleteMembershipActionListener implements JbpmActionListener {
    private final ValueExpression userExpression;
    private final ValueExpression membershipIdExpression;


    public DeleteMembershipActionListener(final ValueExpression userExpression, final ValueExpression membershipIdExpression) {
        this.userExpression = userExpression;
        this.membershipIdExpression = membershipIdExpression;
    }

    public String getName() {
        return "deleteMembership";
    }

    @SuppressWarnings ({"unchecked"})
    public void handleAction(JbpmJsfContext context, ActionEvent event) {
        try {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ELContext elContext = facesContext.getELContext();
            final User user = (User) userExpression.getValue(elContext);
            final long membershipId = ((Long)membershipIdExpression.getValue(elContext)).longValue();
            final Set<Membership> membershipSet = user.getMemberships();
            final Iterator<Membership> it = membershipSet.iterator();
            while (it.hasNext()) {
                Membership membership = it.next();
                if (membership.getId() == membershipId) {
                    it.remove();
                    context.getJbpmContext().getSession().delete(membership);
                    break;
                }
            }
            context.addSuccessMessage("Successfully deleted membership");
        } catch (Exception ex) {
            context.setError("Failed to delete membership", ex);
        }
    }
}
