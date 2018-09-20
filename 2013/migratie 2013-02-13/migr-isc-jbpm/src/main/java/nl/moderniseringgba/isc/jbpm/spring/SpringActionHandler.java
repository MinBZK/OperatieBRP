/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.jbpm.spring;

import java.util.Collections;
import java.util.Map;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

/**
 * Action handler om een {@link SpringAction} uit te voeren.
 */
public final class SpringActionHandler extends SpringHandler implements ActionHandler {
    private static final long serialVersionUID = 1L;

    private static final String INPUT = "input";

    private String bean;

    @Override
    public void execute(final ExecutionContext executionContext) {
        // Lookup bean in application context
        final SpringAction action = getBean(bean, SpringAction.class);

        // Get variables
        @SuppressWarnings("unchecked")
        final Map<String, Object> variables = executionContext.getContextInstance().getVariables();

        // Push execution context on stack so action handler can reach it
        ExecutionContext.pushCurrentContext(executionContext);

        // Execution action handler
        final Map<String, Object> result =
                action.execute(variables == null ? Collections.<String, Object>emptyMap() : variables);

        // Handle result
        if (result != null) {
            if (result.containsKey(INPUT)) {
                throw new IllegalArgumentException(String.format(
                        "Het is niet toegestaan de variabele met key \"input\" te overschrijven!"
                                + "\nOriginele value: ?, afgekeurde value: ?.", variables.get(INPUT),
                        result.get(INPUT)));
            }
            executionContext.getContextInstance().addVariables(result);
        }

        // Pop execution context
        ExecutionContext.popCurrentContext(executionContext);

        // Notify process instance that work item has been completed
        if (!(action instanceof NoSignal)) {
            executionContext.getToken().signal();
        }
    }

    /**
     * @param bean
     *            the bean to set
     */
    public void setBean(final String bean) {
        this.bean = bean;
    }

}
