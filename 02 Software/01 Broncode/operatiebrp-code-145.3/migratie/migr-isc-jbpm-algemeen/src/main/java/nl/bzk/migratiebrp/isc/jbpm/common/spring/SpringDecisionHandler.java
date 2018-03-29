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
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

/**
 * Decision handler om een {@link nl.bzk.migratiebrp.isc.jbpm.common.spring.SpringDecision} uit te voeren.
 */
public final class SpringDecisionHandler extends SpringHandler implements DecisionHandler {
    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger();

    private String bean;
    private transient Map<String, Object> parameters;

    @Override
    public String decide(final ExecutionContext executionContext) {
        LOG.debug("Executing spring-based decision handler: {}", bean);
        // Lookup bean in application context
        final SpringDecision decision = getBean(bean, SpringDecision.class);

        // Push execution context on stack so action handler can reach it
        ExecutionContext.pushCurrentContext(executionContext);

        // Add configured parameters to execution parameters
        final Map<String, Object> executionParameters = new HashMap<>();
        @SuppressWarnings("unchecked")
        final Map<String, Object> variables = executionContext.getContextInstance().getVariables();
        if (variables != null) {
            executionParameters.putAll(variables);
        }
        if (parameters != null) {
            executionParameters.putAll(parameters);
        }

        // Execute
        final String result = decision.execute(executionParameters);

        // Pop execution context
        ExecutionContext.popCurrentContext(executionContext);

        // result
        LOG.debug("Result spring-based decision handler (bean={}): {}", bean, result);
        return result;
    }

    /**
     * Name of the spring bean to execute for this decision.
     * @param bean bean name
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
