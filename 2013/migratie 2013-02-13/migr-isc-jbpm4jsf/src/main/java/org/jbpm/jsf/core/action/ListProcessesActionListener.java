/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.action;

import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.JbpmJsfContext;
import org.jbpm.graph.def.ProcessDefinition;

import javax.el.ValueExpression;
import javax.el.ELContext;
import javax.faces.event.ActionEvent;
import javax.faces.context.FacesContext;

import java.util.List;

/**
 *
 */
public final class ListProcessesActionListener implements JbpmActionListener {
    private final ValueExpression targetExpression;

    public ListProcessesActionListener(final ValueExpression targetExpression) {
        this.targetExpression = targetExpression;
    }

    public String getName() {
        return "listProcesses";
    }

    @SuppressWarnings ({"unchecked"})
    public void handleAction(JbpmJsfContext context, ActionEvent event) {
        try {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ELContext elContext = facesContext.getELContext();
            final List<ProcessDefinition> processList = context.getJbpmContext().getGraphSession().findAllProcessDefinitions();
            targetExpression.setValue(elContext, processList);
            context.selectOutcome("success");
        } catch (Exception ex) {
            context.setError("Error loading process list", ex);
            return;
        }
    }
}
