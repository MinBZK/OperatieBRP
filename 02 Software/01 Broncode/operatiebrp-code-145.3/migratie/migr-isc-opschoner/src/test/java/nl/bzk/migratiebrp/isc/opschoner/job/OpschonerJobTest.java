/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.opschoner.job;

import java.sql.Timestamp;
import nl.bzk.migratiebrp.isc.opschoner.service.OpschonerService;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class OpschonerJobTest {

    private static final String PARAMETER_WACHTTIJD = "wachtPeriodeInUren";

    private JobExecutionContext jobExecutionContext;
    private OpschonerService opschonerService;
    private OpschoonJob subject;

    @Test
    public void testExecute() throws JobExecutionException {
        final int wachtPeriodeInUren = 8;
        jobExecutionContext = Mockito.mock(JobExecutionContext.class);
        opschonerService = Mockito.mock(OpschonerService.class);

        final JobDataMap jdm = new JobDataMap();
        jdm.put("opschonerService", opschonerService);
        jdm.put(PARAMETER_WACHTTIJD, String.valueOf(wachtPeriodeInUren));
        Mockito.when(jobExecutionContext.getMergedJobDataMap()).thenReturn(jdm);

        // Execute
        subject = new OpschoonJob();
        subject.execute(jobExecutionContext);

        // Verify
        Mockito.verify(jobExecutionContext).getMergedJobDataMap();
        Mockito.verify(opschonerService).opschonenProcessen(Matchers.any(Timestamp.class), Matchers.anyInt());
        Mockito.verifyNoMoreInteractions(jobExecutionContext);
    }

    @Test
    public void testExecuteZonderWachtperiode() throws JobExecutionException {
        jobExecutionContext = Mockito.mock(JobExecutionContext.class);
        opschonerService = Mockito.mock(OpschonerService.class);

        final JobDataMap jdm = new JobDataMap();
        jdm.put("opschonerService", opschonerService);
        Mockito.when(jobExecutionContext.getMergedJobDataMap()).thenReturn(jdm);

        // Execute
        subject = new OpschoonJob();
        subject.execute(jobExecutionContext);

        // Verify
        Mockito.verify(jobExecutionContext).getMergedJobDataMap();
        Mockito.verify(opschonerService).opschonenProcessen(Matchers.any(Timestamp.class), Matchers.anyInt());
        Mockito.verifyNoMoreInteractions(jobExecutionContext);
    }

    @Test
    public void testExecuteOngeldigeWachtperiode() throws JobExecutionException {
        jobExecutionContext = Mockito.mock(JobExecutionContext.class);
        opschonerService = Mockito.mock(OpschonerService.class);

        final JobDataMap jdm = new JobDataMap();
        jdm.put("opschonerService", opschonerService);
        jdm.put(PARAMETER_WACHTTIJD, "Wachtperiode");
        Mockito.when(jobExecutionContext.getMergedJobDataMap()).thenReturn(jdm);

        // Execute
        subject = new OpschoonJob();
        subject.execute(jobExecutionContext);

        // Verify
        Mockito.verify(jobExecutionContext).getMergedJobDataMap();
        Mockito.verifyNoMoreInteractions(opschonerService, jobExecutionContext);
        Mockito.verifyZeroInteractions(opschonerService, jobExecutionContext);
    }

    @Test
    public void testExecuteLegeWachtperiode() throws JobExecutionException {
        jobExecutionContext = Mockito.mock(JobExecutionContext.class);
        opschonerService = Mockito.mock(OpschonerService.class);

        final JobDataMap jdm = new JobDataMap();
        jdm.put("opschonerService", opschonerService);
        jdm.put(PARAMETER_WACHTTIJD, "");
        Mockito.when(jobExecutionContext.getMergedJobDataMap()).thenReturn(jdm);

        // Execute
        subject = new OpschoonJob();
        subject.execute(jobExecutionContext);

        // Verify
        Mockito.verify(jobExecutionContext).getMergedJobDataMap();
        Mockito.verify(opschonerService).opschonenProcessen(Matchers.any(Timestamp.class), Matchers.anyInt());
        Mockito.verifyNoMoreInteractions(jobExecutionContext);

    }
}
