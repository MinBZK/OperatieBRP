/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.action;

import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.JbpmJsfContext;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.taskmgmt.exe.TaskInstance;

import javax.el.ValueExpression;
import javax.el.ELContext;
import javax.faces.event.ActionEvent;
import javax.faces.context.FacesContext;

import java.util.List;
import java.util.Collections;

public final class ListTasksForProcessActionListener implements JbpmActionListener {
    private final ValueExpression targetExpression;
    private final ValueExpression processExpression;

    public ListTasksForProcessActionListener(final ValueExpression processExpression, final ValueExpression targetExpression) {
        this.processExpression = processExpression;
        this.targetExpression = targetExpression;
    }

    public String getName() {
        return "listTasksForProcess";
    }

    @SuppressWarnings ({"unchecked"})
    public void handleAction(JbpmJsfContext context, ActionEvent event) {
        try {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ELContext elContext = facesContext.getELContext();
            final ProcessDefinition processDefinition = (ProcessDefinition) processExpression.getValue(elContext);
            final List<TaskInstance> taskList =
                Collections.unmodifiableList(context.getJbpmContext().getSession()
                    .createQuery("select ti from org.jbpm.taskmgmt.exe.TaskInstance ti join ti.processInstance pi where pi.processDefinition = ?")
                    .setEntity(0, processDefinition)
                    .list());
            targetExpression.setValue(elContext, taskList);
            context.selectOutcome("success");
        } catch (Exception ex) {
            context.setError("Error loading task list", ex);
            return;
        }
    }
}
