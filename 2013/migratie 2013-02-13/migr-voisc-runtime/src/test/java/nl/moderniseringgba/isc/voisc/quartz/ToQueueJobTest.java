/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.quartz;

import static org.junit.Assert.fail;

import java.util.Date;

import nl.moderniseringgba.isc.voisc.VoaService;
import nl.moderniseringgba.isc.voisc.exceptions.VoaRuntimeException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.spi.TriggerFiredBundle;

@RunWith(MockitoJUnitRunner.class)
public class ToQueueJobTest {

    @Mock
    private VoaService voiscService;

    @InjectMocks
    private ToQueueJob toQueueJob;

    @Test
    public void onMessageSuccesTest() {
        final Scheduler scheduler = new org.springframework.scheduling.quartz.SchedulerFactoryBean().getScheduler();
        final TriggerFiredBundle triggerFiredBundle =
                new TriggerFiredBundle(new JobDetail(), new org.springframework.scheduling.quartz.CronTriggerBean(),
                        null, true, new Date(), new Date(), new Date(), new Date());
        final Job job = new VoaJob();
        final JobExecutionContext jobExecContext = new JobExecutionContext(scheduler, triggerFiredBundle, job);
        jobExecContext.getMergedJobDataMap().put("voiscService", voiscService);
        try {
            toQueueJob.execute(jobExecContext);
        } catch (final JobExecutionException e) {
            fail("Er is een fout opgetreden, dat is niet de bedoeling.");
        }
    }

    @Test
    public void onMessageSQLErrorTest() {
        final Scheduler scheduler = new org.springframework.scheduling.quartz.SchedulerFactoryBean().getScheduler();
        final TriggerFiredBundle triggerFiredBundle =
                new TriggerFiredBundle(new JobDetail(), new org.springframework.scheduling.quartz.CronTriggerBean(),
                        null, true, new Date(), new Date(), new Date(), new Date());
        final Job job = new VoaJob();
        final JobExecutionContext jobExecContext = new JobExecutionContext(scheduler, triggerFiredBundle, job);
        jobExecContext.getMergedJobDataMap().put("voiscService", voiscService);

        try {
            toQueueJob.execute(jobExecContext);
        } catch (final JobExecutionException e) {
            fail("Er is een fout opgetreden, dat is niet de bedoeling.");
        } catch (final VoaRuntimeException vre) {
            fail("Er zou geen voaRuntimeException gegooid moeten worden.");
        }
    }
}
