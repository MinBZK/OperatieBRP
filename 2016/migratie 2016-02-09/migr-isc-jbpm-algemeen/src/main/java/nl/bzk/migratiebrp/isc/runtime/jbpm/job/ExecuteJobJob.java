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
 * Quartz job om een JBPM job uit te voeren.
 */
public final class ExecuteJobJob extends AbstractExecuteJob {

    /** Job data map key waaronder het JOB id wordt opgeslagen. */
    public static final String JOB_ID_KEY = "jbpm.job.id";

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    protected void execute(final JobDataMap jobDataMap, final ApplicationContext applicationContext) {
        final Long jobId = jobDataMap.getLong(JOB_ID_KEY);
        LOG.info("Executing job {} ...", jobId);
        final ExecuteService service = applicationContext.getBean(ExecuteService.class);
        service.executeJob(jobId);
        LOG.info("Job {} executed.", jobId);
        LOG.info(FunctioneleMelding.ISC_JOB_VERWERKT);
    }

}
