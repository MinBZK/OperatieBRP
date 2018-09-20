/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.runtime.jbpm.job;

import nl.bzk.migratiebrp.util.common.logging.Logger;
import nl.bzk.migratiebrp.util.common.logging.LoggerFactory;
import org.jbpm.JbpmContext;
import org.jbpm.JbpmException;
import org.jbpm.job.Job;
import org.jbpm.msg.MessageService;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

/**
 * Alternatief voor {@link org.jbpm.msg.db.DbMessageService} obv Quartz.
 */
public final class QuartzMessageService implements MessageService {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger();

    private final transient Scheduler scheduler;
    private final JbpmContext jbpmContext;

    /**
     * Constructor.
     *
     * @param scheduler
     *            scheduler
     */
    public QuartzMessageService(final Scheduler scheduler) {
        this.scheduler = scheduler;
        jbpmContext = JbpmContext.getCurrentJbpmContext();
        if (jbpmContext == null) {
            throw new JbpmException("no active jbpm context");
        }
    }

    @Override
    public void send(final Job job) {
        LOG.info("send(job={})", job);

        jbpmContext.getJobSession().saveJob(job);
        LOG.info("send; job.id={}", job.getId());

        final JobDetail jobDetail =
                JobBuilder.newJob(ExecuteJobJob.class)
                          .withIdentity("job-" + job.getId(), "jbpm-jobs")
                          .usingJobData(ExecuteJobJob.JOB_ID_KEY, job.getId())
                          .storeDurably()
                          .withDescription("Job voor JBPM job " + job.getId())
                          .requestRecovery()
                          .build();

        final Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger-" + job.getId(), "jbpm-jobs").build();

        try {
            scheduler.scheduleJob(jobDetail, trigger);
            LOG.info("Scheduled job for job {}", job.getId());
        } catch (final SchedulerException e) {
            throw new JbpmException("Could not schedule job", e);
        }
    }

    @Override
    public void close() {
        // Nothing
    }

}
