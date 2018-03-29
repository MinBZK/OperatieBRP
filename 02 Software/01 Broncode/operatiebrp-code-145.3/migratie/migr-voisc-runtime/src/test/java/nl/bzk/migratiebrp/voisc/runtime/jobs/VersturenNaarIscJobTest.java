/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime.jobs;

import nl.bzk.migratiebrp.voisc.runtime.VoiscService;
import org.junit.Test;
import org.mockito.Mockito;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

public class VersturenNaarIscJobTest {

    @Test
    public void testExecute() {

        final JobExecutionContext jobExecutionContext = Mockito.mock(JobExecutionContext.class);
        final VoiscService voiscService = Mockito.mock(VoiscService.class);

        final JobDataMap jdm = new JobDataMap();
        jdm.put("voiscService", voiscService);
        Mockito.when(jobExecutionContext.getMergedJobDataMap()).thenReturn(jdm);

        // Execute
        final VersturenNaarIscJob subject = new VersturenNaarIscJob();
        subject.execute(jobExecutionContext);

        Mockito.verify(voiscService).berichtenVerzendenNaarIsc();
        Mockito.verifyNoMoreInteractions(voiscService);
    }
}
