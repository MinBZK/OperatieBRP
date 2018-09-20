/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package org.jbpm.jsf.core;

import java.util.TimeZone;

import org.jbpm.jsf.core.handler.AddCommentHandler;
import org.jbpm.jsf.core.handler.AssignTaskHandler;
import org.jbpm.jsf.core.handler.CancelHandler;
import org.jbpm.jsf.core.handler.CompleteTaskHandler;
import org.jbpm.jsf.core.handler.DeleteHandler;
import org.jbpm.jsf.core.handler.DeployProcessHandler;
import org.jbpm.jsf.core.handler.GetDiagramInfoHandler;
import org.jbpm.jsf.core.handler.GetProcessLogsHandler;
import org.jbpm.jsf.core.handler.GetVariableHandler;
import org.jbpm.jsf.core.handler.JbpmActionListenerHandler;
import org.jbpm.jsf.core.handler.ListJobsHandler;
import org.jbpm.jsf.core.handler.ListProcessInstancesHandler;
import org.jbpm.jsf.core.handler.ListProcessesHandler;
import org.jbpm.jsf.core.handler.ListTasksForProcessInstanceHandler;
import org.jbpm.jsf.core.handler.ListTasksHandler;
import org.jbpm.jsf.core.handler.ListTokensHandler;
import org.jbpm.jsf.core.handler.LoadJobHandler;
import org.jbpm.jsf.core.handler.LoadProcessHandler;
import org.jbpm.jsf.core.handler.LoadProcessInstanceHandler;
import org.jbpm.jsf.core.handler.LoadTaskHandler;
import org.jbpm.jsf.core.handler.LoadTokenHandler;
import org.jbpm.jsf.core.handler.MoveTokenHandler;
import org.jbpm.jsf.core.handler.RemoveVariableHandler;
import org.jbpm.jsf.core.handler.ResumeHandler;
import org.jbpm.jsf.core.handler.SignalHandler;
import org.jbpm.jsf.core.handler.StartProcessHandler;
import org.jbpm.jsf.core.handler.StartTaskHandler;
import org.jbpm.jsf.core.handler.SuspendHandler;
import org.jbpm.jsf.core.handler.UpdateTaskStartHandler;
import org.jbpm.jsf.core.handler.UpdateVariableHandler;
import org.jbpm.jsf.core.handler.ListTasksForActorHandler;
import org.jbpm.jsf.core.handler.GetVariableMapHandler;
import org.jbpm.jsf.core.handler.ListTasksForProcessHandler;
import org.jbpm.jsf.core.handler.IncludeProcessFileHandler;
import org.jbpm.jsf.core.handler.ApplyVariableMapHandler;
import org.jbpm.jsf.core.handler.GetTaskFormInfoHandler;
import org.jbpm.jsf.core.handler.TaskFormHandler;
import org.jbpm.jsf.core.ui.UITaskForm;

import com.sun.facelets.tag.AbstractTagLibrary;

import javax.faces.context.FacesContext;
import javax.faces.application.Application;

/**
 *
 */
public final class CoreLibrary extends AbstractTagLibrary {
    public CoreLibrary() {
        super("http://jbpm.org/jbpm4jsf/core");

        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final Application application = facesContext.getApplication();

        // Actions

        addTagHandler("listProcesses", ListProcessesHandler.class);
        addTagHandler("listProcessInstances", ListProcessInstancesHandler.class);
        addTagHandler("listTokens", ListTokensHandler.class);
        addTagHandler("listJobs", ListJobsHandler.class);
        addTagHandler("listTasks", ListTasksHandler.class);
        addTagHandler("listTasksForActor", ListTasksForActorHandler.class);
        addTagHandler("listTasksForProcess", ListTasksForProcessHandler.class);
        addTagHandler("listTasksForProcessInstance", ListTasksForProcessInstanceHandler.class);

        addTagHandler("loadProcess", LoadProcessHandler.class);
        addTagHandler("loadTask", LoadTaskHandler.class);
        addTagHandler("loadProcessInstance", LoadProcessInstanceHandler.class);
        addTagHandler("loadToken", LoadTokenHandler.class);
        addTagHandler("loadJob", LoadJobHandler.class);

        addTagHandler("cancel", CancelHandler.class);
        addTagHandler("delete", DeleteHandler.class);
        addTagHandler("suspend", SuspendHandler.class);
        addTagHandler("resume", ResumeHandler.class);

        addTagHandler("signal", SignalHandler.class);

        addTagHandler("addComment", AddCommentHandler.class);

        addTagHandler("assignTask", AssignTaskHandler.class);

        addTagHandler("startProcess", StartProcessHandler.class);
        addTagHandler("deployProcess", DeployProcessHandler.class);
        addTagHandler("getDiagramInfo", GetDiagramInfoHandler.class);

        addTagHandler("startTask", StartTaskHandler.class);
        addTagHandler("completeTask", CompleteTaskHandler.class);
        addTagHandler("updateTaskStart", UpdateTaskStartHandler.class);
        addTagHandler("getTaskFormInfo", GetTaskFormInfoHandler.class);

        addTagHandler("moveToken", MoveTokenHandler.class);

        addTagHandler("updateVariable", UpdateVariableHandler.class);
        addTagHandler("removeVariable", RemoveVariableHandler.class);
        addTagHandler("getVariable", GetVariableHandler.class);
        addTagHandler("getVariableMap", GetVariableMapHandler.class);
        addTagHandler("applyVariableMap", ApplyVariableMapHandler.class);

        addTagHandler("getProcessLogs", GetProcessLogsHandler.class);

        addTagHandler("includeProcessFile", IncludeProcessFileHandler.class);

        addTagHandler("jbpmActionListener", JbpmActionListenerHandler.class);

        application.addComponent(UITaskForm.COMPONENT_TYPE, UITaskForm.class.getName());
        addComponent("taskForm", UITaskForm.COMPONENT_TYPE, UITaskForm.RENDERER_TYPE, TaskFormHandler.class);

        try {
            addFunction("getServerTimeZone", TimeZone.class.getMethod("getDefault"));
        } catch (NoSuchMethodException e) {
            throw new AssertionError(e);
        }
    }
}
