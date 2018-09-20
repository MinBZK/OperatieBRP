/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.telling;

import java.sql.Timestamp;
import java.util.Calendar;
import nl.bzk.migratiebrp.isc.telling.job.TellingenJob;
import nl.bzk.migratiebrp.isc.telling.runtime.TellingenRuntimeService;
import org.junit.Test;
import org.mockito.Mockito;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class TellingenRuntimeMainTest extends AbstractDatabaseTest {

    private static final String PARAMETER_WACHTTIJD = "wachtPeriodeInUren";

    private JobExecutionContext jobExecutionContext;
    private TellingenRuntimeService tellingenRuntimeService;
    private TellingenJob subject;

    @Test
    public void testExecute() throws JobExecutionException {
        final int wachtPeriodeInUren = 8;
        jobExecutionContext = Mockito.mock(JobExecutionContext.class);
        tellingenRuntimeService = Mockito.mock(TellingenRuntimeService.class);

        final JobDataMap jdm = new JobDataMap();
        jdm.put("tellingenRuntimeService", tellingenRuntimeService);
        jdm.put(PARAMETER_WACHTTIJD, String.valueOf(wachtPeriodeInUren));
        Mockito.when(jobExecutionContext.getMergedJobDataMap()).thenReturn(jdm);

        // Execute
        subject = new TellingenJob();
        subject.execute(jobExecutionContext);

        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, -wachtPeriodeInUren);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, -1);
        final Timestamp datumTot = new Timestamp(cal.getTimeInMillis());
        // Verify
        Mockito.verify(jobExecutionContext).getMergedJobDataMap();
        Mockito.verify(tellingenRuntimeService).werkLopendeTellingenBij(datumTot);
        Mockito.verifyNoMoreInteractions(tellingenRuntimeService, jobExecutionContext);
    }

    @Test
    public void testExecuteZonderWachtperiode() throws JobExecutionException {
        jobExecutionContext = Mockito.mock(JobExecutionContext.class);
        tellingenRuntimeService = Mockito.mock(TellingenRuntimeService.class);

        final JobDataMap jdm = new JobDataMap();
        jdm.put("tellingenRuntimeService", tellingenRuntimeService);
        Mockito.when(jobExecutionContext.getMergedJobDataMap()).thenReturn(jdm);

        // Execute
        subject = new TellingenJob();
        subject.execute(jobExecutionContext);

        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, -27);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, -1);
        final Timestamp datumTot = new Timestamp(cal.getTimeInMillis());
        // Verify
        Mockito.verify(jobExecutionContext).getMergedJobDataMap();
        Mockito.verify(tellingenRuntimeService).werkLopendeTellingenBij(datumTot);
        Mockito.verifyNoMoreInteractions(tellingenRuntimeService, jobExecutionContext);
    }

    @Test
    public void testExecuteOngeldigeWachtperiode() throws JobExecutionException {
        jobExecutionContext = Mockito.mock(JobExecutionContext.class);
        tellingenRuntimeService = Mockito.mock(TellingenRuntimeService.class);

        final JobDataMap jdm = new JobDataMap();
        jdm.put("tellingenRuntimeService", tellingenRuntimeService);
        jdm.put(PARAMETER_WACHTTIJD, "Wachtperiode");
        Mockito.when(jobExecutionContext.getMergedJobDataMap()).thenReturn(jdm);

        // Execute
        subject = new TellingenJob();
        subject.execute(jobExecutionContext);

        // Verify
        Mockito.verify(jobExecutionContext).getMergedJobDataMap();
        Mockito.verifyNoMoreInteractions(tellingenRuntimeService, jobExecutionContext);
        Mockito.verifyZeroInteractions(tellingenRuntimeService, jobExecutionContext);
    }

    @Test
    public void testExecuteLegeWachtperiode() throws JobExecutionException {
        jobExecutionContext = Mockito.mock(JobExecutionContext.class);
        tellingenRuntimeService = Mockito.mock(TellingenRuntimeService.class);

        final JobDataMap jdm = new JobDataMap();
        jdm.put("tellingenRuntimeService", tellingenRuntimeService);
        jdm.put(PARAMETER_WACHTTIJD, "");
        Mockito.when(jobExecutionContext.getMergedJobDataMap()).thenReturn(jdm);

        // Execute
        subject = new TellingenJob();
        subject.execute(jobExecutionContext);

        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR_OF_DAY, -27);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, -1);
        final Timestamp datumTot = new Timestamp(cal.getTimeInMillis());
        // Verify
        Mockito.verify(jobExecutionContext).getMergedJobDataMap();
        Mockito.verify(tellingenRuntimeService).werkLopendeTellingenBij(datumTot);
        Mockito.verifyNoMoreInteractions(tellingenRuntimeService, jobExecutionContext);
    }
}
