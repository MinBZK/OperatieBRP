/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.lezer.job;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;

import java.util.concurrent.CountDownLatch;
import nl.bzk.brp.service.selectie.lezer.status.SelectieJobRunStatus;
import nl.bzk.brp.service.selectie.lezer.status.SelectieJobRunStatusService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class WachtSchrijvenCompleetTest {

    @InjectMocks
    private WachtSchrijvenCompleet wachtSchrijvenCompleet;

    @Mock
    private SelectieJobRunStatusService selectieJobRunStatusService;
    @Mock
    private CountDownLatch countDownLatch;

    @Test
    public void test_klaar() throws Exception {
        SelectieJobRunStatus selectieJobRunStatus = new SelectieJobRunStatus();

        Mockito.when(selectieJobRunStatusService.getStatus()).thenReturn(selectieJobRunStatus);

        wachtSchrijvenCompleet.call();

        Mockito.verify(selectieJobRunStatusService, times(1)).getStatus();
        Mockito.verify(countDownLatch, times(1)).countDown();
    }

    @Test
    public void test_stoppen() throws Exception {
        SelectieJobRunStatus selectieJobRunStatus = new SelectieJobRunStatus();
        ReflectionTestUtils.setField(selectieJobRunStatus, "moetStoppen", true);
        Mockito.when(selectieJobRunStatusService.getStatus()).thenReturn(selectieJobRunStatus);

        wachtSchrijvenCompleet.call();

        Mockito.verify(selectieJobRunStatusService, times(1)).getStatus();
        Mockito.verify(countDownLatch, times(1)).countDown();
    }

}