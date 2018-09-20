/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.jbpm.spring;

import nl.moderniseringgba.migratie.logging.Logger;
import nl.moderniseringgba.migratie.logging.LoggerFactory;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

/**
 * Decision handler om een {@link SpringDecision} uit te voeren.
 */
public final class SpringDecisionHandler extends SpringHandler implements DecisionHandler {
    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger();

    private String bean;

    @SuppressWarnings("unchecked")
    @Override
    public String decide(final ExecutionContext executionContext) throws Exception {
        LOG.info("Executing spring-based decision handler: {}", bean);
        // Lookup bean in application context
        final SpringDecision decision = getBean(bean, SpringDecision.class);

        // Push execution context on stack so action handler can reach it
        ExecutionContext.pushCurrentContext(executionContext);

        // Executes
        final String result = decision.execute(executionContext.getContextInstance().getVariables());

        // Pop execution context
        ExecutionContext.popCurrentContext(executionContext);

        // result
        LOG.info("Result spring-based decision handler (bean={}): {}", bean, result);
        return result;
    }

    /**
     * @param bean
     *            the bean to set
     */
    public void setBean(final String bean) {
        this.bean = bean;
    }

}
