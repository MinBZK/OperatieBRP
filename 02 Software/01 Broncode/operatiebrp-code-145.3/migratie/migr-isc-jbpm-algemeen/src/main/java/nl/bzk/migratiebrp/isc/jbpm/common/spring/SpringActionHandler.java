/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.spring;

import java.util.HashMap;
import java.util.Map;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;

/**
 * Action handler om een {@link nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringAction} uit te voeren.
 */
public final class SpringActionHandler extends SpringHandler implements ActionHandler {

    /**
     * Als deze key is gevuld in het resultaat dan wordt die transition gesignald.
     */
    public static final String TRANSITION_RESULT = "transition";

    private static final long serialVersionUID = 1L;
    private static final String INPUT = "input";

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private String bean;
    private transient Map<String, Object> parameters;

    @Override
    public void execute(final ExecutionContext executionContext) {
        final Token token = executionContext.getToken();
        LOGGER.info("execute; token={}", token.getId());

        // Lookup bean in application context
        final SpringAction action = getBean(bean, SpringAction.class);

        // Add configured parameters to execution parameters
        final Map<String, Object> executionParameters = new HashMap<>();

        @SuppressWarnings("unchecked")
        final Map<String, Object> variables = executionContext.getContextInstance().getVariables(token);
        LOGGER.info("variables={}", variables);
        if (variables != null) {
            executionParameters.putAll(variables);
        }

        if (parameters != null) {
            LOGGER.info("parameters={}", parameters);
            executionParameters.putAll(parameters);
        }

        // Push execution context on stack so action handler can reach it
        final Map<String, Object> result;
        try {
            ExecutionContext.pushCurrentContext(executionContext);

            // Execution action handler
            LOGGER.info("before action execution");
            result = action.execute(executionParameters);
            LOGGER.info("action executed");
        } finally {
            // Pop execution context
            ExecutionContext.popCurrentContext(executionContext);
        }

        String transition = null;
        // Handle result
        if (result != null) {
            transition = (String) result.remove(TRANSITION_RESULT);
            if (result.containsKey(INPUT)) {
                final String origineleValue = variables == null ? "null" : (String) variables.get(INPUT);
                throw new IllegalArgumentException(
                        String.format("Het is niet toegestaan de variabele met key \"input\" te overschrijven!"
                                + "%nOriginele value: %s, afgekeurde value: %s.", origineleValue, result.get(INPUT)));
            }

            for (final Map.Entry<String, Object> entry : result.entrySet()) {
                // Overschrijf de variabele op zijn 'eigen' scope.
                executionContext.getContextInstance().setVariable(entry.getKey(), entry.getValue(), token);
            }
        }

        // Notify process instance that work item has been completed
        if (!(action instanceof NoSignal)) {
            if (transition != null) {
                LOGGER.info("signaling token with transition {}", transition);
                executionContext.getToken().signal(transition);
            } else {
                LOGGER.info("signaling token");
                executionContext.getToken().signal();
            }
        }
    }

    /**
     * Zet de waarde van bean.
     * @param bean the bean to set
     */
    public void setBean(final String bean) {
        this.bean = bean;
    }

    /**
     * Parameters to add to the execution.
     * @param parameters parameters
     */
    public void setParameters(final Map<String, Object> parameters) {
        this.parameters = parameters;
    }

}
