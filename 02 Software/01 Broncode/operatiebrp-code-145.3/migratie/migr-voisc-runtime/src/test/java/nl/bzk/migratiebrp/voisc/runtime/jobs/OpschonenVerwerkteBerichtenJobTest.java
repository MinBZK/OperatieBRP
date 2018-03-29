/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime.jobs;

import nl.bzk.migratiebrp.voisc.runtime.VoiscService;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

import java.util.Calendar;
import java.util.Date;

public class OpschonenVerwerkteBerichtenJobTest {

    @Test
    public void testExecute() {

        // Setup
        final int aantalUrenSindsVerwerkt = 336;
        final JobExecutionContext jobExecutionContext = Mockito.mock(JobExecutionContext.class);
        final VoiscService voiscService = Mockito.mock(VoiscService.class);

        final JobDataMap jdm = new JobDataMap();
        jdm.put("voiscService", voiscService);
        jdm.put("aantalUrenSindsVerwerkt", String.valueOf(aantalUrenSindsVerwerkt));
        Mockito.when(jobExecutionContext.getMergedJobDataMap()).thenReturn(jdm);

        // Execute
        final OpschonenVerwerkteBerichtenJob subject = new OpschonenVerwerkteBerichtenJob();
        subject.execute(jobExecutionContext);

        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, -aantalUrenSindsVerwerkt);
        final Date ouderDan = cal.getTime();

        ArgumentCaptor<Date> ouderDanCapturer = ArgumentCaptor.forClass(Date.class);

        // Verify
        Mockito.verify(jobExecutionContext).getMergedJobDataMap();
        Mockito.verify(voiscService).opschonenVoiscBerichten(ouderDanCapturer.capture());
        Mockito.verifyNoMoreInteractions(voiscService, jobExecutionContext);

        final Date capturedDate = ouderDanCapturer.getValue();
        Assert.assertTrue(ouderDan.getTime() >= capturedDate.getTime());
        Assert.assertTrue((ouderDan.getTime() + 10000) >= capturedDate.getTime());
    }

}
