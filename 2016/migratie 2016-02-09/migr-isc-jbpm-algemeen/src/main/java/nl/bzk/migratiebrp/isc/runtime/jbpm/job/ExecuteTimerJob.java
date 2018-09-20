/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.jbpm.job;

import nl.bzk.migratiebrp.util.common.logging.FunctioneleMelding;
import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.quartz.JobDataMap;
import org.springframework.context.ApplicationContext;

/**
 * Quartz job om een JBPM timer uit te voeren.
 */
public final class ExecuteTimerJob extends AbstractExecuteJob {

    /** Job data map key waaronder het ID van de uit te voeren timer is opgeslagen. */
    public static final String TIMER_ID_KEY = "jbpm.timer.id";

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    protected void execute(final JobDataMap jobDataMap, final ApplicationContext applicationContext) {
        final Long timerId = jobDataMap.getLong(TIMER_ID_KEY);
        LOG.info("Executing timer {}", timerId);

        final ExecuteService service = applicationContext.getBean(ExecuteService.class);
        service.executeTimer(timerId);
        LOG.info(FunctioneleMelding.ISC_TIMER_VERWERKT);
    }
}
