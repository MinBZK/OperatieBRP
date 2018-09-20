/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.jbpm.el;

import org.jbpm.JbpmConfiguration.Configs;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;
import org.jbpm.jpdl.el.VariableResolver;
import org.jbpm.taskmgmt.exe.SwimlaneInstance;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.taskmgmt.exe.TaskMgmtInstance;

/**
 * Jbpm variable resolver.
 *
 * Bevat bugfixes tov {@link org.jbpm.jpdl.el.impl.JbpmVariableResolver}.
 */
public final class JbpmVariableResolver implements VariableResolver {

    @Override
    public Object resolveVariable(final String name) {
        final ExecutionContext executionContext = ExecutionContext.currentExecutionContext();

        if ("taskInstance".equals(name)) {
            return executionContext.getTaskInstance();
        }
        if ("processInstance".equals(name)) {
            return executionContext.getProcessInstance();
        }
        if ("processDefinition".equals(name)) {
            return executionContext.getProcessDefinition();
        }
        if ("token".equals(name)) {
            return executionContext.getToken();
        }
        if ("taskMgmtInstance".equals(name)) {
            return executionContext.getTaskMgmtInstance();
        }
        if ("contextInstance".equals(name)) {
            return executionContext.getContextInstance();
        }

        final TaskInstance taskInstance = executionContext.getTaskInstance();
        if (taskInstance != null && taskInstance.hasVariableLocally(name)) {
            return taskInstance.getVariable(name);
        }

        final ContextInstance contextInstance = executionContext.getContextInstance();
        if (contextInstance != null) {
            final Token token = executionContext.getToken();
            if (contextInstance.hasVariable(name, token)) {
                // BUGFIX: token werd niet doorgegeven bij getVariable()
                return contextInstance.getVariable(name, token);
            }
            if (contextInstance.hasTransientVariable(name)) {
                return contextInstance.getTransientVariable(name);
            }
        }

        final TaskMgmtInstance taskMgmtInstance = executionContext.getTaskMgmtInstance();
        if (taskMgmtInstance != null) {
            final SwimlaneInstance swimlaneInstance = taskMgmtInstance.getSwimlaneInstance(name);
            if (swimlaneInstance != null) {
                return swimlaneInstance.getActorId();
            }
        }

        return Configs.hasObject(name) ? Configs.getObject(name) : null;
    }
}
