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
public final class CreateGroupActionListener implements JbpmActionListener {

    private final ValueExpression groupNameExpression;
    private final ValueExpression parentGroupExpression;
    private final ValueExpression typeExpression;
    private final ValueExpression targetExpression;

    public CreateGroupActionListener(final ValueExpression groupNameExpression, final ValueExpression parentGroupExpression, final ValueExpression typeExpression, final ValueExpression targetExpression) {
        this.groupNameExpression = groupNameExpression;
        this.parentGroupExpression = parentGroupExpression;
        this.typeExpression = typeExpression;
        this.targetExpression = targetExpression;
    }

    public String getName() {
        return "createGroup";
    }

    public void handleAction(JbpmJsfContext context, ActionEvent event) {
        try {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ELContext elContext = facesContext.getELContext();
            final IdentitySession identitySession = new IdentitySession(context.getJbpmContext().getSession());
            if (groupNameExpression == null) {
                context.setError("Error creating group", "The group name expression is missing");
                return;
            }
            final Object groupNameValue = groupNameExpression.getValue(elContext);
            if (groupNameValue == null) {
                context.setError("Error creating group", "The group name value is null");
                return;
            }
            final Group group = new Group(groupNameValue.toString());
            if (parentGroupExpression != null) {
                final Object parentGroupValue = parentGroupExpression.getValue(elContext);
                if (parentGroupValue instanceof Group) {
                    group.setParent((Group)parentGroupValue);
                } else if (parentGroupValue != null) {
                    context.setError("Error creating group", "The parent group is not a Group instance");
                    return;
                }
            }
            if (typeExpression != null) {
                final Object typeValue = typeExpression.getValue(elContext);
                if (typeValue == null) {
                    context.setError("Error creating group", "The type value is null");
                    return;
                }
                group.setType(typeValue.toString());
            }
            identitySession.saveEntity(group);
            if (targetExpression != null) {
                targetExpression.setValue(elContext, group);
            }
            context.selectOutcome("success");
            context.addSuccessMessage("Group created successfully");
        } catch (Exception ex) {
            context.setError("Error loading group", ex);
            return;
        }
    }
}
