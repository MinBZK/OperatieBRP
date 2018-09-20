/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.action;

import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.JbpmJsfContext;
import org.jbpm.taskmgmt.exe.TaskMgmtInstance;

import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

public final class StartProcessActionListener implements JbpmActionListener {

    private final ValueExpression processExpression;
    private final ValueExpression instanceExpression;


    public StartProcessActionListener(final ValueExpression processExpression, final ValueExpression instanceExpression) {
        this.processExpression = processExpression;
        this.instanceExpression = instanceExpression;
    }

    public String getName() {
        return "startProcess";
    }

    public void handleAction(JbpmJsfContext context, ActionEvent event) {
        try {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ELContext elContext = facesContext.getELContext();
            final Object processValue = processExpression.getValue(elContext);
            if (processValue == null) {
                context.setError("Error starting process", "The process value is null");
                return;
            }
            if (!(processValue instanceof ProcessDefinition)) {
                context.setError("Error starting process", "Attempted to start something other than a process");
                return;
            }
            final ProcessDefinition definition = (ProcessDefinition) processValue;
            final ProcessInstance instance = definition.createProcessInstance();
            // Signal the root token based on the following criteria:
            // 1. If there is no start task, and
            // 2. If the root token is still at the start state, and
            // 3. If the start state has a default leaving transition, then
            // signal the token along the default transition.
            context.addSuccessMessage("Started process");

            final TaskMgmtInstance taskMgmtInstance = instance.getTaskMgmtInstance();
            // final TaskInstance startTaskInstance =
            taskMgmtInstance.createStartTaskInstance();

            /* next piece causes NPE.
             * and i don't think it is needed to signal a new process automatically.  that can 
             * be done in the console itself as well.  
             * TODO it would be nice if the console automatically navigated to the screen where 
             * you can see the root token and actually give the signal

            if (startTaskInstance == null) {
                // There is no start task
                final Node initialNode = definition.getStartState();
                final Token rootToken = instance.getRootToken();
                final Node rootTokenNode = rootToken.getNode();
                if (initialNode.getId() == rootTokenNode.getId()) {
                    // The root token is still at the start node
                    final Transition defaultLeavingTransition = initialNode.getDefaultLeavingTransition();
                    if (defaultLeavingTransition != null) {
                        // There's a default transition
                        rootToken.signal(defaultLeavingTransition);
                        context.addSuccessMessage("Signalled root token");
                    }
                }
            }
            */
            
            context.selectOutcome("started");
            if (instance.hasEnded()) {
                context.selectOutcome("finished");
                context.addSuccessMessage("Process completed");
            }
            if (instanceExpression != null) {
                try {
                    instanceExpression.setValue(elContext, instance);
                } catch (ELException ex) {
                    context.setError("Error setting value of " + instanceExpression.getExpressionString(), ex);
                    return;
                }
            }
            // Nothing else saves the process, so we must
            context.getJbpmContext().save(instance);
        } catch (Exception ex) {
            context.setError("Error starting process", ex);
            return;
        }
    }
}
