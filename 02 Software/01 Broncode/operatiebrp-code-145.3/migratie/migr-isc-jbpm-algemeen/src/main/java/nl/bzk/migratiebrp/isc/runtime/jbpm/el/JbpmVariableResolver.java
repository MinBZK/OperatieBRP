/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.jbpm.el;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
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

    private final Map<String, BiFunction<String, ExecutionContext, Object>> resolverMap = new HashMap<>();

    /**
     * Constructor.
     */
    public JbpmVariableResolver() {
        resolverMap.put("taskInstance", (name, executionContext) -> executionContext.getTaskInstance());
        resolverMap.put("processInstance", (name, executionContext) -> executionContext.getProcessInstance());
        resolverMap.put("processDefinition", (name, executionContext) -> executionContext.getProcessDefinition());
        resolverMap.put("token", (name, executionContext) -> executionContext.getToken());
        resolverMap.put("taskMgmtInstance", (name, executionContext) -> executionContext.getTaskMgmtInstance());
        resolverMap.put("contextInstance", (name, executionContext) -> executionContext.getContextInstance());
    }

    @Override
    public Object resolveVariable(final String name) {
        final ExecutionContext executionContext = ExecutionContext.currentExecutionContext();
        return resolverMap.getOrDefault(name, this::getDefaultObject).apply(name, executionContext);
    }

    private Object getDefaultObject(final String name, final ExecutionContext executionContext) {
        Object result;
        final TaskInstance taskInstance = executionContext.getTaskInstance();
        if (taskInstance != null && taskInstance.hasVariableLocally(name)) {
            result = taskInstance.getVariable(name);
        } else {
            result = checkContextInstance(name, executionContext);
            if (result == null) {
                result = checkTaskManagementInstance(name, executionContext);
            }
            if (result == null) {
                result = Configs.hasObject(name) ? Configs.getObject(name) : null;
            }
        }
        return result;
    }

    private Object checkContextInstance(final String name, final ExecutionContext executionContext) {
        Object result = null;
        final ContextInstance contextInstance = executionContext.getContextInstance();
        if (contextInstance != null) {
            final Token token = executionContext.getToken();
            if (contextInstance.hasVariable(name, token)) {
                result = contextInstance.getVariable(name, token);
            } else if (contextInstance.hasTransientVariable(name)) {
                result = contextInstance.getTransientVariable(name);
            }
        }
        return result;
    }

    private Object checkTaskManagementInstance(final String name, final ExecutionContext executionContext) {
        Object result = null;
        final TaskMgmtInstance taskMgmtInstance = executionContext.getTaskMgmtInstance();
        if (taskMgmtInstance != null) {
            final SwimlaneInstance swimlaneInstance = taskMgmtInstance.getSwimlaneInstance(name);
            if (swimlaneInstance != null) {
                result = swimlaneInstance.getActorId();
            }
        }
        return result;
    }
}
