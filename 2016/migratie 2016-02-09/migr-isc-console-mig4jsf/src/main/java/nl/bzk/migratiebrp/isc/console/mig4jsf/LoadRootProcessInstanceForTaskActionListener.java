/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.console.mig4jsf;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.jbpm.JbpmContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.jsf.JbpmJsfContext;
import org.jbpm.taskmgmt.exe.TaskInstance;

/**
 * Laad root process instance voor taak action listener.
 */
public final class LoadRootProcessInstanceForTaskActionListener extends AbstractActionListener {

    private final ValueExpression taskIdExpression;
    private final ValueExpression targetExpression;

    /**
     * Constructor.
     *
     * @param taskIdExpression
     *            task id expression
     * @param targetExpression
     *            target expression
     */
    public LoadRootProcessInstanceForTaskActionListener(final ValueExpression taskIdExpression, final ValueExpression targetExpression) {
        super("loadRootProcessInstanceForTask");
        this.taskIdExpression = taskIdExpression;
        this.targetExpression = targetExpression;
    }

    @Override
    public void verwerkAction(final JbpmJsfContext context, final ActionEvent event) {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final ELContext elContext = facesContext.getELContext();

        final Object taskIdValue = taskIdExpression.getValue(elContext);
        if (taskIdValue == null) {
            context.setError("Error loading process", "The taskId value is null");
            return;
        }
        final long taskId;
        if (taskIdValue instanceof Long) {
            taskId = (Long) taskIdValue;
        } else {
            taskId = Long.parseLong(taskIdValue.toString());
        }

        final ProcessInstance processInstance = getProcessInstance(context.getJbpmContext(), taskId);
        targetExpression.setValue(elContext, processInstance);
        context.selectOutcome("success");
    }

    private ProcessInstance getProcessInstance(final JbpmContext jbpmContext, final long taskId) {
        final TaskInstance task = jbpmContext.loadTaskInstance(taskId);
        return ProcessInstanceUtil.getRootProcessInstance(task.getProcessInstance());
    }

}
