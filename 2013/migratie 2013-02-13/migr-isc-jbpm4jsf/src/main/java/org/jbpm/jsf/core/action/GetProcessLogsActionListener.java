/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jbpm.context.log.VariableCreateLog;
import org.jbpm.context.log.VariableDeleteLog;
import org.jbpm.context.log.VariableLog;
import org.jbpm.context.log.VariableUpdateLog;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.log.ActionLog;
import org.jbpm.graph.log.NodeLog;
import org.jbpm.graph.log.ProcessInstanceCreateLog;
import org.jbpm.graph.log.ProcessInstanceEndLog;
import org.jbpm.graph.log.ProcessStateLog;
import org.jbpm.graph.log.SignalLog;
import org.jbpm.graph.log.TokenCreateLog;
import org.jbpm.graph.log.TokenEndLog;
import org.jbpm.graph.log.TransitionLog;
import org.jbpm.jsf.JbpmActionListener;
import org.jbpm.jsf.JbpmJsfContext;
import org.jbpm.logging.exe.LoggingInstance;
import org.jbpm.logging.log.CompositeLog;
import org.jbpm.logging.log.MessageLog;
import org.jbpm.logging.log.ProcessLog;
import org.jbpm.taskmgmt.log.SwimlaneAssignLog;
import org.jbpm.taskmgmt.log.SwimlaneCreateLog;
import org.jbpm.taskmgmt.log.SwimlaneLog;
import org.jbpm.taskmgmt.log.TaskAssignLog;
import org.jbpm.taskmgmt.log.TaskCreateLog;
import org.jbpm.taskmgmt.log.TaskEndLog;
import org.jbpm.taskmgmt.log.TaskLog;

import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

public final class GetProcessLogsActionListener implements JbpmActionListener {
    private final ValueExpression processInstanceExpression;
    private final ValueExpression typeExpression;
    private final ValueExpression targetExpression;

    private static final Map<String, Class<? extends ProcessLog>> logTypes;

    static {
        Map<String, Class<? extends ProcessLog>> newLogTypes = new HashMap<String, Class<? extends ProcessLog>>();
        // variables
        newLogTypes.put("variable", VariableLog.class);
        newLogTypes.put("variableUpdate", VariableUpdateLog.class);
        newLogTypes.put("variableCreate", VariableCreateLog.class);
        newLogTypes.put("variableDelete", VariableDeleteLog.class);
        // jpdl
        newLogTypes.put("action", ActionLog.class);
        newLogTypes.put("node", NodeLog.class);
        newLogTypes.put("processInstanceCreate", ProcessInstanceCreateLog.class);
        newLogTypes.put("processInstanceEnd", ProcessInstanceEndLog.class);
        newLogTypes.put("processState", ProcessStateLog.class);
        newLogTypes.put("signal", SignalLog.class);
        newLogTypes.put("tokenCreate", TokenCreateLog.class);
        newLogTypes.put("tokenEnd", TokenEndLog.class);
        newLogTypes.put("transition", TransitionLog.class);
        // misc
        newLogTypes.put("composite", CompositeLog.class);
        newLogTypes.put("message", MessageLog.class);
        // swimlane
        newLogTypes.put("swimlane", SwimlaneLog.class);
        newLogTypes.put("swimlaneAssign", SwimlaneAssignLog.class);
        newLogTypes.put("swimlaneCreate", SwimlaneCreateLog.class);
        // task
        newLogTypes.put("task", TaskLog.class);
        newLogTypes.put("taskAssign", TaskAssignLog.class);
        newLogTypes.put("taskCreate", TaskCreateLog.class);
        newLogTypes.put("taskEnd", TaskEndLog.class);
        logTypes = Collections.unmodifiableMap(newLogTypes);
    }

    public GetProcessLogsActionListener(final ValueExpression processInstanceExpression, final ValueExpression typeExpression, final ValueExpression targetExpression) {
        this.processInstanceExpression = processInstanceExpression;
        this.typeExpression = typeExpression;
        this.targetExpression = targetExpression;
    }

    public String getName() {
        return "getProcessLogs";
    }

    public void handleAction(JbpmJsfContext context, ActionEvent event) {
        try {
            final FacesContext facesContext = FacesContext.getCurrentInstance();
            final ELContext elContext = facesContext.getELContext();
            final Object processInstanceValue = processInstanceExpression.getValue(elContext);
            if (processInstanceValue == null) {
                context.setError("Error reading process logs", "The process instance value is null");
                return;
            }
            if (!(processInstanceValue instanceof ProcessInstance)) {
                context.setError("Error reading process logs", "Attempted to read process logs from something other than a process instance");
                return;
            }
            final List<?> processLogs;
            if (typeExpression != null) {
                final Object typeValue = typeExpression.getValue(elContext);
                if (typeValue == null) {
                    context.setError("Error reading process logs", "Null value for type attribute");
                    return;
                }
                final Class<?> type;
                if (typeValue instanceof Class) {
                    type = (Class<?>) typeValue;
                } else {
                    final String typeString = typeValue.toString();
                    if (logTypes.containsKey(typeString)) {
                        type = logTypes.get(typeString);
                    } else {
                        try {
                            type = Class.forName(typeString);
                        } catch (ClassNotFoundException e) {
                            context.setError("Error reading process logs", "No class by the name of '" + typeString + "' was found, and that type is not among the predefined types for log records");
                            return;
                        }
                    }
                }
                if (! ProcessLog.class.isAssignableFrom(type)) {
                    context.setError("Error reading process logs", "The given log type '" + type.getName() + "' is not a valid process log type");
                    return;
                }
                final ProcessInstance processInstance = (ProcessInstance) processInstanceValue;
                final LoggingInstance loggingInstance = processInstance.getLoggingInstance();
                processLogs = loggingInstance.getLogs(type);
            } else {
                final ProcessInstance processInstance = (ProcessInstance) processInstanceValue;
                final LoggingInstance loggingInstance = processInstance.getLoggingInstance();
                processLogs = loggingInstance.getLogs();
            }
            // TODO - add filter parameters perhaps?
            targetExpression.setValue(elContext, processLogs);
            context.selectOutcome("success");
        } catch (Exception ex) {
            context.setError("Error reading process logs", ex);
            return;
        }
    }
}
