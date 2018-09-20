/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.identity.action;

import org.jbpm.identity.Group;
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
public final class AddMembershipActionListener implements JbpmActionListener {

    private final ValueExpression userExpression;
    private final ValueExpression groupExpression;
    private final ValueExpression roleExpression;
    private final ValueExpression targetExpression;

    public AddMembershipActionListener(final ValueExpression userExpression, final ValueExpression groupExpression, final ValueExpression roleExpression, final ValueExpression targetExpression) {
        this.userExpression = userExpression;
        this.groupExpression = groupExpression;
        this.roleExpression = roleExpression;
        this.targetExpression = targetExpression;
    }

    public String getName() {
        return "addMembership";
    }

    public void handleAction(JbpmJsfContext context, ActionEvent event) {
        try {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ELContext elContext = facesContext.getELContext();
            final User user;
            if (userExpression == null) {
                context.setError("Error adding membership", "User expression is required");
                return;
            }
            final Object userValue = userExpression.getValue(elContext);
            if (userValue == null) {
                context.setError("Error adding membership", "User value is null");
                return;
            }
            user = (User) userValue;
            final Group group;
            if (groupExpression == null) {
                context.setError("Error adding membership", "Group expression is required");
                return;
            }
            final Object groupValue = groupExpression.getValue(elContext);
            if (groupValue == null) {
                context.setError("Error adding membership", "Group value is null");
                return;
            }
            group = (Group) groupValue;
            final Membership membership = Membership.create(user, group);
            if (roleExpression != null) {
                final Object roleValue = roleExpression.getValue(elContext);
                if (roleValue != null) {
                    final String roleValueString = roleValue.toString();
                    if (roleValueString.length() > 0) {
                        membership.setRole(roleValueString);
                    }
                }
            }
            if (targetExpression != null) {
                targetExpression.setValue(elContext, membership);
            }
            context.selectOutcome("success");
            context.addSuccessMessage("Successfully added membership");
        } catch (Exception ex) {
            context.setError("Error adding membership", ex);
            return;
        }
    }
}
