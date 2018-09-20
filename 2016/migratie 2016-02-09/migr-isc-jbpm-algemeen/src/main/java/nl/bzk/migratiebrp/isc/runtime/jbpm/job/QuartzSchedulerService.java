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
import org.jbpm.configuration.ObjectFactory;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.job.Timer;
import org.jbpm.scheduler.SchedulerService;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

/**
 * Alternatief voor {@link org.jbpm.scheduler.db.DbSchedulerService} obv Quartz.
 */
public final class QuartzSchedulerService implements SchedulerService {

    private static final long serialVersionUID = 1L;
    private static final Logger LOG = LoggerFactory.getLogger();
    private static final String CALENDAR_RESOURCE = "resource.business.calendar";

    private final transient Scheduler scheduler;
    private final JbpmContext jbpmContext;

    /**
     * Constructor.
     *
     * @param scheduler
     *            scheduler
     */
    public QuartzSchedulerService(final Scheduler scheduler) {
        this.scheduler = scheduler;
        jbpmContext = JbpmContext.getCurrentJbpmContext();
        if (jbpmContext == null) {
            throw new JbpmException("no active jbpm context");
        }
    }

    @Override
    public void createTimer(final Timer timer) {
        LOG.info("createTimer(timer={})", timer);
        // save business calendar resource used to create timer
        // https://jira.jboss.org/browse/JBPM-2958
        final ObjectFactory objectFactory = jbpmContext.getObjectFactory();
        if (objectFactory.hasObject(CALENDAR_RESOURCE)) {
            timer.setCalendarResource((String) objectFactory.createObject(CALENDAR_RESOURCE));
        }

        jbpmContext.getJobSession().saveJob(timer);
        LOG.info("send; timer.id={}", timer.getId());

        final JobDetail jobDetail =
                JobBuilder.newJob(ExecuteTimerJob.class)
                          .withIdentity("job-" + timer.getId(), "jbpm-timers")
                          .usingJobData(ExecuteTimerJob.TIMER_ID_KEY, timer.getId())
                          .storeDurably()
                          .withDescription("Job voor JBPM timer " + timer.getId())
                          .requestRecovery()
                          .build();

        final Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger-" + timer.getId(), "jbpm-timers").startAt(timer.getDueDate()).build();

        try {
            scheduler.scheduleJob(jobDetail, trigger);
            LOG.info("Scheduled job for timer {}", timer.getId());
        } catch (final SchedulerException e) {
            throw new JbpmException("Could not schedule job", e);
        }
    }

    @Override
    public void deleteTimer(final Timer timer) {
        LOG.info("deleteTimer(timer={})", timer);
        jbpmContext.getJobSession().deleteJob(timer);
    }

    @Override
    public void deleteTimersByName(final String timerName, final Token token) {
        LOG.info("deleteTimersByName(timerName={},token={})", timerName, token);
        jbpmContext.getJobSession().deleteTimersByName(timerName, token);
    }

    @Override
    public void deleteTimersByProcessInstance(final ProcessInstance processInstance) {
        LOG.info("deleteTimersByProcessInstance(processInstance={})", processInstance);
        jbpmContext.getJobSession().deleteJobsForProcessInstance(processInstance);
    }

    @Override
    public void close() {
        // Nothing
    }

}
