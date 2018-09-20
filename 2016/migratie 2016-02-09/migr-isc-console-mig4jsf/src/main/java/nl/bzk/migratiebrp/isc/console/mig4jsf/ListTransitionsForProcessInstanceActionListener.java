/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

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
import org.jbpm.jsf.JbpmJsfContext;
import org.jbpm.logging.log.MessageLog;
import org.jbpm.logging.log.ProcessLog;

/**
 * List transitions (uit log) voor proces instance.
 */
public final class ListTransitionsForProcessInstanceActionListener extends AbstractActionListener {

    private final ValueExpression targetExpression;
    private final ValueExpression processInstanceExpression;

    /**
     * Constructor.
     *
     * @param processInstanceExpression
     *            process instance expression
     * @param targetExpression
     *            target expression
     */
    public ListTransitionsForProcessInstanceActionListener(final ValueExpression processInstanceExpression, final ValueExpression targetExpression) {
        super("listTransitionsForProcessInstance");
        this.processInstanceExpression = processInstanceExpression;
        this.targetExpression = targetExpression;
    }

    @Override
    public void verwerkAction(final JbpmJsfContext context, final ActionEvent event) {
        final JbpmContext jbpmContext = context.getJbpmContext();
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final ELContext elContext = facesContext.getELContext();
        final ProcessInstance rootProcessInstance =
                ProcessInstanceUtil.getRootProcessInstance((ProcessInstance) processInstanceExpression.getValue(elContext));

        final List<ProcessInstance> processInstances = ProcessInstanceUtil.getProcessInstances(jbpmContext, rootProcessInstance);

        final List<ProcessLog> log = new ArrayList<>();
        for (final ProcessInstance processInstance : processInstances) {
            getLog(jbpmContext, log, processInstance.getId());
        }

        Collections.sort(log, new Comparator<ProcessLog>() {
            @Override
            public int compare(final ProcessLog arg0, final ProcessLog arg1) {
                return arg1.getDate().compareTo(arg0.getDate());
            }
        });

        targetExpression.setValue(elContext, Collections.unmodifiableCollection(log));
        context.selectOutcome("success");
    }

    private void getLog(final JbpmContext jbpmContext, final List<ProcessLog> result, final long processInstanceId) {
        @SuppressWarnings("unchecked")
        final Map<Token, List<ProcessLog>> logs = jbpmContext.getLoggingSession().findLogsByProcessInstance(processInstanceId);

        for (final List<ProcessLog> log : logs.values()) {
            for (final ProcessLog logRegel : log) {
                if (logRegel instanceof TransitionLog || logRegel instanceof MessageLog) {
                    result.add(logRegel);
                }
            }
        }
    }
}
