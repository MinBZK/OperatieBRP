/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.runtime.jobs;

import java.util.Date;
import nl.bzk.migratiebrp.voisc.runtime.VoiscService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

@RunWith(MockitoJUnitRunner.class)
public class HerstelStatusBerichtenJobTest {

    @Mock
    private VoiscService voiscService;

    @Mock
    private JobExecutionContext jobExecutionContext;

    @Mock
    private JobDataMap jobDataMap;

    @InjectMocks
    private HerstelStatusBerichtenJob subject;

    @Before
    public void setupJobExecutionContext() {
        Mockito.when(jobExecutionContext.getMergedJobDataMap()).thenReturn(jobDataMap);
        Mockito.when(jobDataMap.get("voiscService")).thenReturn(voiscService);
        Mockito.when(jobDataMap.get("aantalUrenSindsInVerwerking")).thenReturn("6");
    }

    @Test
    public void test() {
        subject.execute(jobExecutionContext);

        Mockito.verify(jobExecutionContext).getMergedJobDataMap();
        Mockito.verify(jobDataMap).get("voiscService");
        Mockito.verify(jobDataMap).get("aantalUrenSindsInVerwerking");

        final ArgumentCaptor<Date> dateCaptor = ArgumentCaptor.forClass(Date.class);
        Mockito.verify(voiscService).herstellenVoiscBerichten(dateCaptor.capture());
        final Date date = dateCaptor.getValue();
        Assert.assertNotNull(date);
        Assert.assertTrue(date.getTime() < System.currentTimeMillis());

        Mockito.verifyNoMoreInteractions(voiscService);
    }
}
