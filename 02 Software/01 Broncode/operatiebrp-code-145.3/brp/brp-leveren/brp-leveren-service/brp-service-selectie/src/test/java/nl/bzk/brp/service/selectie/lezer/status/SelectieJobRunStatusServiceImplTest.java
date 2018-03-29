/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.lezer.status;

import static org.junit.Assert.*;

import nl.bzk.brp.service.selectie.algemeen.JobEventStopType;
import nl.bzk.brp.service.selectie.algemeen.JobStopEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SelectieJobRunStatusServiceImplTest {

    @InjectMocks
    private SelectieJobRunStatusServiceImpl selectieJobRunStatusService;

    @Test
    public void getStatus() throws Exception {
        assertNotNull(selectieJobRunStatusService.getStatus());
    }

    @Test
    public void newStatus() throws Exception {
        final SelectieJobRunStatus status = selectieJobRunStatusService.getStatus();
        final SelectieJobRunStatus nieuweStatus = selectieJobRunStatusService.newStatus();

        assertNotNull(nieuweStatus);
        assertFalse(status == nieuweStatus);
    }

    @Test
    public void onApplicationEvent_Stop() throws Exception {
        JobStopEvent jobStopEvent = new JobStopEvent(new Object(), JobEventStopType.STOP);

        selectieJobRunStatusService.onApplicationEvent(jobStopEvent);

        assertTrue(selectieJobRunStatusService.getStatus().moetStoppen());
    }

    @Test
    public void onApplicationEvent_Fout() throws Exception {
        JobStopEvent foutEvent = new JobStopEvent(new Object(), JobEventStopType.FOUT);

        selectieJobRunStatusService.onApplicationEvent(foutEvent);

        assertTrue(selectieJobRunStatusService.getStatus().isError());
    }

}