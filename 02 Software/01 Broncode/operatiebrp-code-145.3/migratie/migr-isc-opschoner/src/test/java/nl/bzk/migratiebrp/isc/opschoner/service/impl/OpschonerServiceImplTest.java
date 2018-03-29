/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.opschoner.service.impl;

import static org.mockito.Mockito.times;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.bzk.migratiebrp.isc.opschoner.exception.NietVerwijderbareProcesInstantieException;
import nl.bzk.migratiebrp.isc.opschoner.service.ProcesService;
import nl.bzk.migratiebrp.isc.opschoner.service.RuntimeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.transaction.PlatformTransactionManager;

@RunWith(MockitoJUnitRunner.class)
public class OpschonerServiceImplTest {

    private static final String RUNTIME_NAAM = "opschonen";

    @Mock
    private RuntimeService runtimeService;

    @Mock
    private ProcesService procesService;

    @Mock
    private PlatformTransactionManager transactionManager;

    @InjectMocks
    private OpschonerServiceImpl subject;

    @Test
    public void testOk() throws NietVerwijderbareProcesInstantieException {

        final Timestamp ouderDan = new Timestamp(System.currentTimeMillis());

        final Long procesEen = (long) 1;
        final Long procesTwee = (long) 2;
        final Long procesDrie = (long) 3;
        final Long procesVier = (long) 4;

        final List<Long> procesIdsEersteLoop = new ArrayList<>();
        procesIdsEersteLoop.add(procesEen);
        procesIdsEersteLoop.add(procesTwee);

        final List<Long> procesIdsTweedeLoop = new ArrayList<>();
        procesIdsTweedeLoop.add(procesDrie);
        procesIdsTweedeLoop.add(procesDrie);
        procesIdsTweedeLoop.add(procesVier);

        Mockito.when(procesService.selecteerIdsVanOpTeSchonenProcessen(ouderDan))
                .thenReturn(procesIdsEersteLoop)
                .thenReturn(procesIdsTweedeLoop)
                .thenReturn(Collections.<Long>emptyList());

        subject.opschonenProcessen(ouderDan, 0);

        Mockito.verify(runtimeService).lockRuntime(RUNTIME_NAAM);
        Mockito.verify(procesService, times(3)).selecteerIdsVanOpTeSchonenProcessen(ouderDan);
        Mockito.verify(procesService, times(4)).controleerProcesVerwijderbaar(
                Matchers.anyLong(),
                Matchers.anyListOf(Long.class),
                Matchers.anyListOf(Long.class));
        Mockito.verify(procesService, times(4)).verwijderProces(Matchers.anyLong(), Matchers.anyListOf(Long.class), Matchers.anyListOf(Long.class));
        Mockito.verify(runtimeService).unlockRuntime(RUNTIME_NAAM);

        Mockito.verifyNoMoreInteractions(runtimeService, procesService);
    }

    @Test
    public void testGeenProcessen() throws NietVerwijderbareProcesInstantieException {

        final Timestamp ouderDan = new Timestamp(System.currentTimeMillis());

        final List<Long> procesIds = new ArrayList<>();

        Mockito.when(procesService.selecteerIdsVanOpTeSchonenProcessen(ouderDan)).thenReturn(procesIds);

        subject.opschonenProcessen(ouderDan, 0);

        Mockito.verify(runtimeService).lockRuntime(RUNTIME_NAAM);
        Mockito.verify(procesService, times(1)).selecteerIdsVanOpTeSchonenProcessen(ouderDan);
        Mockito.verify(runtimeService).unlockRuntime(RUNTIME_NAAM);

        Mockito.verifyNoMoreInteractions(runtimeService, procesService);
    }

    @Test
    public void testEenProcesNietVerwijderbaar() throws NietVerwijderbareProcesInstantieException {

        final Timestamp ouderDan = new Timestamp(System.currentTimeMillis());
        final Timestamp laatsteActiviteitDatum = new Timestamp(System.currentTimeMillis() + 1000);

        final List<Long> procesIds = new ArrayList<>();
        final Long procesEen = (long) 1;
        procesIds.add(procesEen);

        Mockito.when(procesService.selecteerIdsVanOpTeSchonenProcessen(ouderDan)).thenReturn(procesIds).thenReturn(Collections.<Long>emptyList());
        Mockito.doThrow(new NietVerwijderbareProcesInstantieException("Proces niet beeindigd", laatsteActiviteitDatum))
                .when(procesService)
                .controleerProcesVerwijderbaar(Matchers.anyLong(), Matchers.anyListOf(Long.class), Matchers.anyListOf(Long.class));

        subject.opschonenProcessen(ouderDan, 0);

        Mockito.verify(runtimeService).lockRuntime(RUNTIME_NAAM);
        Mockito.verify(procesService, times(2)).selecteerIdsVanOpTeSchonenProcessen(ouderDan);
        Mockito.verify(procesService, times(1)).controleerProcesVerwijderbaar(
                Matchers.anyLong(),
                Matchers.anyListOf(Long.class),
                Matchers.anyListOf(Long.class));
        Mockito.verify(procesService, times(1)).updateVerwachteVerwijderDatumProces(Matchers.anyLong(), Matchers.any(Timestamp.class));
        Mockito.verify(runtimeService).unlockRuntime(RUNTIME_NAAM);

        Mockito.verifyNoMoreInteractions(runtimeService, procesService);
    }

    @Test
    public void testNull() {
        subject.opschonenProcessen(null, 0);
        Mockito.verifyNoMoreInteractions(runtimeService, procesService);
    }

    @Test
    public void testLockRuntimeFaalt() {

        final Timestamp ouderDan = new Timestamp(System.currentTimeMillis());

        Mockito.doThrow(new RecoverableDataAccessException("Ongeldige actie")).when(runtimeService).lockRuntime(RUNTIME_NAAM);

        subject.opschonenProcessen(ouderDan, 0);

        Mockito.verify(runtimeService).lockRuntime(RUNTIME_NAAM);

        Mockito.verifyNoMoreInteractions(runtimeService, procesService);
    }

    @Test
    public void testExceptieInRuntime() throws NietVerwijderbareProcesInstantieException {

        final Timestamp ouderDan = new Timestamp(System.currentTimeMillis());

        final List<Long> procesIds = new ArrayList<>();
        final Long procesEen = (long) 1;
        procesIds.add(procesEen);

        Mockito.when(procesService.selecteerIdsVanOpTeSchonenProcessen(ouderDan)).thenReturn(procesIds).thenReturn(Collections.<Long>emptyList());
        Mockito.doThrow(new NullPointerException("NullPointer"))
                .when(procesService)
                .controleerProcesVerwijderbaar(Matchers.anyLong(), Matchers.anyListOf(Long.class), Matchers.anyListOf(Long.class));

        try {
            subject.opschonenProcessen(ouderDan, 0);
        } catch (final NullPointerException exceptie) {

        }

        Mockito.verify(runtimeService).lockRuntime(RUNTIME_NAAM);
        Mockito.verify(procesService, times(1)).selecteerIdsVanOpTeSchonenProcessen(ouderDan);
        Mockito.verify(procesService, times(1)).controleerProcesVerwijderbaar(
                Matchers.anyLong(),
                Matchers.anyListOf(Long.class),
                Matchers.anyListOf(Long.class));
        Mockito.verify(runtimeService).unlockRuntime(RUNTIME_NAAM);

        Mockito.verifyNoMoreInteractions(runtimeService, procesService);
    }

}
