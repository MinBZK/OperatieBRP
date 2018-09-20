/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.quartz;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nl.moderniseringgba.isc.migratie.domein.entities.Gemeente;
import nl.moderniseringgba.isc.migratie.repository.GemeenteRepository;
import nl.moderniseringgba.isc.voisc.VoaService;
import nl.moderniseringgba.isc.voisc.entities.Bericht;
import nl.moderniseringgba.isc.voisc.entities.LogboekRegel;
import nl.moderniseringgba.isc.voisc.entities.Mailbox;
import nl.moderniseringgba.isc.voisc.exceptions.VoaException;
import nl.moderniseringgba.isc.voisc.mailbox.VoiscDbProxy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.spi.TriggerFiredBundle;

@RunWith(MockitoJUnitRunner.class)
public class VoaJobTest {

    @Mock
    private VoaService voiscService;

    @Mock
    private VoiscDbProxy voiscDbProxy;

    @Mock
    private GemeenteRepository gemeenteRepo;

    @InjectMocks
    private VoaJob voaJob;

    @Test
    public void onMessageSuccesTest() {
        final Scheduler scheduler = new org.springframework.scheduling.quartz.SchedulerFactoryBean().getScheduler();
        final TriggerFiredBundle triggerFiredBundle =
                new TriggerFiredBundle(new JobDetail(), new org.springframework.scheduling.quartz.CronTriggerBean(),
                        null, true, new Date(), new Date(), new Date(), new Date());
        final Job job = new VoaJob();
        final JobExecutionContext jobExecContext = new JobExecutionContext(scheduler, triggerFiredBundle, job);
        jobExecContext.getMergedJobDataMap().put("voiscService", voiscService);
        jobExecContext.getMergedJobDataMap().put("voiscDbProxy", voiscDbProxy);
        jobExecContext.getMergedJobDataMap().put("gemeenteRepo", gemeenteRepo);
        try {
            final List<Gemeente> brpGemeenten = new ArrayList<Gemeente>();
            final Gemeente gemeente1 = new Gemeente();
            gemeente1.setGemeenteCode(1904);
            brpGemeenten.add(gemeente1);
            Mockito.when(gemeenteRepo.getBrpActiveGemeente()).thenReturn(brpGemeenten);

            final Mailbox mailbox = new Mailbox();
            Mockito.when(voiscDbProxy.getMailboxByGemeentecode(Matchers.anyString())).thenReturn(mailbox);

            final List<Bericht> berichten = new ArrayList<Bericht>();
            Mockito.when(voiscService.getMessagesToSend(Matchers.any(Mailbox.class))).thenReturn(berichten);

            voaJob.execute(jobExecContext);
        } catch (final JobExecutionException e) {
            fail("Er is een fout opgetreden, dat is niet de bedoeling.");
        }
    }

    @Test
    public void onMessageStopJobTest() {
        final Scheduler scheduler = new org.springframework.scheduling.quartz.SchedulerFactoryBean().getScheduler();
        final TriggerFiredBundle triggerFiredBundle =
                new TriggerFiredBundle(new JobDetail(), new org.springframework.scheduling.quartz.CronTriggerBean(),
                        null, true, new Date(), new Date(), new Date(), new Date());
        final Job job = new VoaJob();
        final JobExecutionContext jobExecContext = new JobExecutionContext(scheduler, triggerFiredBundle, job);
        jobExecContext.getMergedJobDataMap().put("voiscService", voiscService);
        jobExecContext.getMergedJobDataMap().put("voiscDbProxy", voiscDbProxy);
        jobExecContext.getMergedJobDataMap().put("gemeenteRepo", gemeenteRepo);
        try {
            try {
                voaJob.destroy();
            } catch (final Exception e) {
                fail("destroy failed.");
            }
            final List<Gemeente> brpGemeenten = new ArrayList<Gemeente>();
            final Gemeente gemeente1 = new Gemeente();
            gemeente1.setGemeenteCode(1904);
            brpGemeenten.add(gemeente1);
            Mockito.when(gemeenteRepo.getBrpActiveGemeente()).thenReturn(brpGemeenten);

            final Mailbox mailbox = new Mailbox();
            Mockito.when(voiscDbProxy.getMailboxByGemeentecode(Matchers.anyString())).thenReturn(mailbox);

            Mockito.doAnswer(new Answer<Bericht>() {
                @Override
                public Bericht answer(final InvocationOnMock invocation) {
                    fail("Dit zou niet moeten, het proces zou moeten stoppen.");
                    return null;
                }
            }).when(voiscService).getMessagesToSend(Matchers.any(Mailbox.class));

            voaJob.execute(jobExecContext);
        } catch (final JobExecutionException e) {
            fail("Er is een fout opgetreden, dat is niet de bedoeling.");
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void onMessageFoutmeldingTest() {
        final Scheduler scheduler = new org.springframework.scheduling.quartz.SchedulerFactoryBean().getScheduler();
        final TriggerFiredBundle triggerFiredBundle =
                new TriggerFiredBundle(new JobDetail(), new org.springframework.scheduling.quartz.CronTriggerBean(),
                        null, true, new Date(), new Date(), new Date(), new Date());
        final Job job = new VoaJob();
        final JobExecutionContext jobExecContext = new JobExecutionContext(scheduler, triggerFiredBundle, job);
        jobExecContext.getMergedJobDataMap().put("voiscService", voiscService);
        jobExecContext.getMergedJobDataMap().put("voiscDbProxy", voiscDbProxy);
        jobExecContext.getMergedJobDataMap().put("gemeenteRepo", gemeenteRepo);
        try {
            final List<Gemeente> brpGemeenten = new ArrayList<Gemeente>();
            final Gemeente gemeente1 = new Gemeente();
            gemeente1.setGemeenteCode(1904);
            brpGemeenten.add(gemeente1);
            Mockito.when(gemeenteRepo.getBrpActiveGemeente()).thenReturn(brpGemeenten);

            final Mailbox mailbox = new Mailbox();
            Mockito.when(voiscDbProxy.getMailboxByGemeentecode(Matchers.anyString())).thenReturn(mailbox);

            final List<Bericht> berichten = new ArrayList<Bericht>();
            Mockito.when(voiscService.getMessagesToSend(Matchers.any(Mailbox.class))).thenReturn(berichten);

            try {
                Mockito.doAnswer(new Answer<Object>() {
                    @Override
                    public Object answer(final InvocationOnMock invocation) throws Throwable {
                        final LogboekRegel logboekRegel = (LogboekRegel) invocation.getArguments()[0];
                        logboekRegel.setFoutmelding("Iets fout gegaan");
                        return null;
                    }
                }).when(voiscService).login(Matchers.any(LogboekRegel.class), Matchers.any(Mailbox.class));
                Mockito.doAnswer(new Answer<Bericht>() {
                    @Override
                    public Bericht answer(final InvocationOnMock invocation) {
                        fail("Dit zou niet moeten, er was een eerdere foutmelding.");
                        return null;
                    }
                }).when(voiscService)
                        .sendMessagesToMailbox(Matchers.any(LogboekRegel.class), Matchers.any(List.class));
            } catch (final VoaException e) {
                fail("Er zou geen VoaException gegooid moeten worden");
            }

            voaJob.execute(jobExecContext);
        } catch (final JobExecutionException e) {
            fail("Er is een fout opgetreden, dat is niet de bedoeling.");
        }
    }
}
