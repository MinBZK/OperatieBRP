/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.action;

import java.util.Collections;
import java.util.List;
import org.hibernate.Session;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.JbpmJsfContext;
import org.jbpm.taskmgmt.exe.TaskInstance;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 */
public final class ListTasksActionListener implements JbpmActionListener {
    private final ValueExpression targetExpression;
    private final ValueExpression includeEndedExpression;

    public ListTasksActionListener(final ValueExpression includeEndedExpression, final ValueExpression targetExpression) {
        this.includeEndedExpression = includeEndedExpression;
        this.targetExpression = targetExpression;
    }

    public String getName() {
        return "listTasks";
    }

    @SuppressWarnings ({"unchecked"})
    public void handleAction(JbpmJsfContext context, ActionEvent event) {
        try {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ELContext elContext = facesContext.getELContext();
            final Boolean includeEnded = (Boolean) includeEndedExpression.getValue(elContext);
            final Session session = context.getJbpmContext().getSession();
            final List<TaskInstance> taskList;
            if (includeEnded != null && includeEnded.booleanValue()) {
                taskList = session.createQuery("from org.jbpm.taskmgmt.exe.TaskInstance ti").list();
            } else {
                taskList = session.createQuery("from org.jbpm.taskmgmt.exe.TaskInstance ti where ti.end is not null").list();
            }
            targetExpression.setValue(elContext, Collections.unmodifiableList(taskList));
            context.selectOutcome("success");
        } catch (Exception ex) {
            context.setError("Error loading task list", ex);
            return;
        }
    }
}
