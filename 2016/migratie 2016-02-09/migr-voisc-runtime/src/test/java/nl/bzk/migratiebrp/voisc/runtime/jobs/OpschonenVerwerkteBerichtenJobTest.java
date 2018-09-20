/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime.jobs;

import java.util.Calendar;
import java.util.Date;
import nl.bzk.migratiebrp.voisc.runtime.VoiscService;
import org.junit.Test;
import org.mockito.Mockito;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

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
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        final Date ouderDan = cal.getTime();
        // Verify
        Mockito.verify(jobExecutionContext).getMergedJobDataMap();
        Mockito.verify(voiscService).opschonenVoiscBerichten(ouderDan);
        Mockito.verifyNoMoreInteractions(voiscService, jobExecutionContext);
    }

}
