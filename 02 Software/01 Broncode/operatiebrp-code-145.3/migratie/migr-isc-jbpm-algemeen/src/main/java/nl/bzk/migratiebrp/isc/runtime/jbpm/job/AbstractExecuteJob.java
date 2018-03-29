/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.jbpm.job;

import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.algemeenbrp.util.common.logging.MDCProcessor;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

/**
 * Basis implementatie voor uitvoeren van een (timer of) job als quartz job.
 */
public abstract class AbstractExecuteJob implements Job {

    /**
     * Job data map key waarop het aantal retries wordt geregistreerd.
     */
    public static final String RETRIES_KEY = "jbpm.job.retries";

    /**
     * Maximum aantal retries.
     */
    public static final int MAXIMUM_NUMBER_OF_RETRIES = 3;

    private static final Logger LOG = LoggerFactory.getLogger();

    @Override
    public final void execute(final JobExecutionContext context) throws JobExecutionException {
        MDCProcessor.startVerwerking().run(() -> executeJob(context));
    }

    private void executeJob(JobExecutionContext context) throws JobExecutionException {
        LOG.info("Start uitvoeren job ...");
        final JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();

        ApplicationContext applicationContext;
        try {
            applicationContext = (ApplicationContext) context.getScheduler().getContext().get("applicationContext");
        } catch (final SchedulerException e) {
            throw new JobExecutionException(e);
        }
        if (applicationContext == null) {
            LOG.warn("Geen application context aanwezig voor uitvoeren job.");
            throw new JobExecutionException("Geen application context aanwezig");
        }

        try {
            execute(jobDataMap, applicationContext);
        } catch (final Exception e /* Catch exception voor robustheid */) {
            final int numberOfRetries = increaseAndGetRetries(jobDataMap);
            if (numberOfRetries <= MAXIMUM_NUMBER_OF_RETRIES) {
                LOG.info("Exceptie tijdens uitvoeren job. Job wordt opnieuw aangeboden", e);
                throw new JobExecutionException(e, true);
            } else {
                LOG.warn("Exceptie tijdens uitvoeren job. Job al meer dan het maximum keer aangeboden. Job wordt genegeerd.", e);
                throw new JobExecutionException(e, false);
            }
        }
        LOG.info("Job uitgevoerd.");
    }

    /**
     * Voer de job uit.
     * @param jobDataMap job data map
     * @param applicationContext spring application context
     */
    protected abstract void execute(final JobDataMap jobDataMap, final ApplicationContext applicationContext);

    private int increaseAndGetRetries(final JobDataMap jobDataMap) {
        final int retries;
        if (!jobDataMap.containsKey(RETRIES_KEY)) {
            retries = 1;
        } else {
            retries = jobDataMap.getInt(RETRIES_KEY) + 1;
        }
        jobDataMap.put(RETRIES_KEY, retries);

        return retries;
    }
}
