/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.jbpm.jsf.mig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.jbpm.JbpmContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.graph.log.TransitionLog;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.JbpmJsfContext;
import org.jbpm.logging.log.ProcessLog;

public class ListTransitionsForProcessInstanceActionListener implements JbpmActionListener {

    private final ValueExpression targetExpression;
    private final ValueExpression processInstanceExpression;

    public ListTransitionsForProcessInstanceActionListener(
            final ValueExpression processInstanceExpression,
            final ValueExpression targetExpression) {
        this.processInstanceExpression = processInstanceExpression;
        this.targetExpression = targetExpression;
    }

    @Override
    public String getName() {
        return "listTransitionsForProcessInstance";
    }

    @Override
    public void handleAction(final JbpmJsfContext context, final ActionEvent event) {
        try {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ELContext elContext = facesContext.getELContext();
            final ProcessInstance processInstance =
                    ProcessInstanceUtil.getRootProcessInstance((ProcessInstance) processInstanceExpression
                            .getValue(elContext));

            final List<TransitionLog> log = getLog(context.getJbpmContext(), processInstance.getId());
            targetExpression.setValue(elContext, Collections.unmodifiableCollection(log));
            context.selectOutcome("success");
        } catch (final Exception ex) {
            context.setError("Error loading berichten list", ex);
            return;
        }
    }

    private List<TransitionLog> getLog(final JbpmContext jbpmContext, final long processInstanceId) {
        @SuppressWarnings("unchecked")
        final Map<Token, List<ProcessLog>> logs =
                jbpmContext.getLoggingSession().findLogsByProcessInstance(processInstanceId);

        final List<TransitionLog> result = new ArrayList<TransitionLog>();
        for (final List<ProcessLog> log : logs.values()) {
            for (final ProcessLog logRegel : log) {
                if (logRegel instanceof TransitionLog) {
                    result.add((TransitionLog) logRegel);
                }

            }
        }

        Collections.sort(result, new Comparator<TransitionLog>() {
            @Override
            public int compare(final TransitionLog arg0, final TransitionLog arg1) {
                return -arg0.getDate().compareTo(arg1.getDate());
            }
        });

        return result;
    }
}
