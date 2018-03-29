/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.jbpm.JbpmException;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

/**
 * Exception logger.
 *
 * Het volgende moet toegevoegd worden aan elke procesdefinition:
 *
 * <pre>
 * &lt;exception-handler>
 *       &lt;action name="exception-handler" class="nl.bzk.isc.jbpm.ExceptionLogger"/>
 * &lt;/exception-handler>
 * </pre>
 */
public final class ExceptionLogger implements ActionHandler {
    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public void execute(final ExecutionContext executionContext) {

        final Throwable problem = executionContext.getException();

        LOG.error("Exceptie opgetreden binnen Jbpm: " + problem.getClass().getCanonicalName(), problem);
        if (problem.getCause() != null) {
            LOG.error("exception cause: " + problem.getCause());
        }
        LOG.error("Exceptie message: " + problem.getLocalizedMessage());

        if (executionContext.getProcessInstance() != null) {
            LOG.error("processInstance id: " + executionContext.getProcessInstance().getId());
        }

        if (problem instanceof JbpmException) {
            throw (JbpmException) problem;
        } else {
            throw new JbpmException(problem);
        }
    }
}
