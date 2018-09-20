/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.telling.runtime;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import nl.bzk.migratiebrp.isc.telling.entiteit.Bericht;
import nl.bzk.migratiebrp.isc.telling.entiteit.BerichtTelling;
import nl.bzk.migratiebrp.isc.telling.entiteit.ProcesExtractie;
import nl.bzk.migratiebrp.isc.telling.entiteit.ProcesTelling;
import nl.bzk.migratiebrp.isc.telling.repository.BerichtRepository;
import nl.bzk.migratiebrp.isc.telling.repository.BerichtTellingRepository;
import nl.bzk.migratiebrp.isc.telling.repository.ProcesExtractieRepository;
import nl.bzk.migratiebrp.isc.telling.repository.ProcesTellingenRepository;
import nl.bzk.migratiebrp.isc.telling.repository.RuntimeRepository;
import nl.bzk.migratiebrp.isc.telling.runtime.impl.TellingenRuntimeServiceImpl;
import org.hibernate.HibernateException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class TellingenRuntimeServiceImplTest {

    private static final String RUNTIME_NAAM = "tellingen";
    private static Timestamp datumTot;
    private static final Integer MAXIMALE_BATCH_GROOTTE = 5000;
    private static final String BERICHT_TYPE = "Lg01";
    private static final String KANAAL = "VOSPG";
    private static final String GENEGEERD = "herhalingGenegeerd";
    private static final String PROCES = "uc202";
    private static Timestamp startDatum;
    private static Timestamp eindDatum;

    @Mock
    private ProcesExtractieRepository procesExtractieRepositoryService;
    @Mock
    private BerichtRepository berichtRepositoryService;
    @Mock
    private BerichtTellingRepository berichtTellingenRepositoryService;
    @Mock
    private ProcesTellingenRepository procesTellingenRepositoryService;
    @Mock
    private RuntimeRepository runtimeRepositoryService;

    @InjectMocks
    private TellingenRuntimeServiceImpl service;

    @Before
    public void initMocks() {
        final Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        datumTot = new Timestamp(calendar.getTimeInMillis());
        calendar.add(Calendar.HOUR_OF_DAY, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        eindDatum = new Timestamp(calendar.getTimeInMillis());
        calendar.add(Calendar.HOUR_OF_DAY, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        startDatum = new Timestamp(calendar.getTimeInMillis());
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testInsertRuntimeFaalt() {

        when(runtimeRepositoryService.voegRuntimeToe(RUNTIME_NAAM)).thenReturn(false);

        service.werkLopendeTellingenBij(datumTot);

        verify(runtimeRepositoryService, times(1)).voegRuntimeToe(RUNTIME_NAAM);
        Mockito.verifyNoMoreInteractions(runtimeRepositoryService);
        Mockito.verifyZeroInteractions(
            procesExtractieRepositoryService,
            berichtRepositoryService,
            berichtTellingenRepositoryService,
            procesTellingenRepositoryService);
    }

    @Test
    public void testGeenBerichtenEnGeenProcesExtracties() {

        when(runtimeRepositoryService.voegRuntimeToe(RUNTIME_NAAM)).thenReturn(true);
        when(berichtRepositoryService.telInTellingTeVerwerkenBerichten(datumTot)).thenReturn(0L);
        when(berichtRepositoryService.selecteerInTellingTeVerwerkenBerichten(datumTot, MAXIMALE_BATCH_GROOTTE)).thenReturn(
            new ArrayList<Bericht>());

        service.werkLopendeTellingenBij(datumTot);

        verify(runtimeRepositoryService, times(1)).voegRuntimeToe(RUNTIME_NAAM);
        verify(berichtRepositoryService, times(1)).telInTellingTeVerwerkenBerichten(datumTot);
        verify(berichtRepositoryService, times(1)).selecteerInTellingTeVerwerkenBerichten(datumTot, 0);
        verify(procesExtractieRepositoryService, times(1)).telInTellingTeVerwerkenGestarteProcessen(datumTot);
        verify(procesExtractieRepositoryService, times(1)).selecteerInTellingTeVerwerkenGestarteProcesInstanties(datumTot, 0);
        verify(procesExtractieRepositoryService, times(1)).telInTellingTeVerwerkenBeeindigdeProcessen(datumTot);
        verify(procesExtractieRepositoryService, times(1)).selecteerInTellingTeVerwerkenBeeindigdeProcesInstanties(datumTot, 0);
        verify(runtimeRepositoryService, times(1)).verwijderRuntime(RUNTIME_NAAM);
        Mockito.verifyNoMoreInteractions(runtimeRepositoryService);
        Mockito.verifyZeroInteractions(
            procesExtractieRepositoryService,
            berichtTellingenRepositoryService,
            procesTellingenRepositoryService);
    }

    @Test
    public void testWelBerichtenEnProcesExtractiesWelTellingen() {

        final List<Bericht> berichtenLijst = new ArrayList<>();
        final Bericht berichtEen = new Bericht();
        berichtEen.setId(1L);
        berichtEen.setKanaal(KANAAL);
        berichtEen.setNaam(BERICHT_TYPE);
        berichtEen.setTijdstip(datumTot);
        berichtEen.setIndicatieGeteld(false);
        final Bericht berichtTwee = new Bericht();
        berichtTwee.setId(2L);
        berichtTwee.setKanaal(KANAAL);
        berichtTwee.setNaam(BERICHT_TYPE);
        berichtTwee.setTijdstip(datumTot);
        berichtTwee.setIndicatieGeteld(false);
        berichtenLijst.add(berichtEen);
        berichtenLijst.add(berichtTwee);

        final List<ProcesExtractie> procesExtractieGestartLijst = new ArrayList<>();
        final List<ProcesExtractie> procesExtractieBeeindigdLijst = new ArrayList<>();

        final ProcesExtractie procesExtractieEen = new ProcesExtractie();
        procesExtractieEen.setBerichtType(BERICHT_TYPE);
        procesExtractieEen.setEindDatum(eindDatum);
        procesExtractieEen.setIndicatieBeeindigdGeteld(false);
        procesExtractieEen.setIndicatieGestartGeteld(false);
        procesExtractieEen.setKanaal(KANAAL);
        procesExtractieEen.setProcesnaam(PROCES);
        procesExtractieEen.setProcesInstantieId(123L);
        procesExtractieEen.setStartDatum(startDatum);

        final ProcesExtractie procesExtractieTwee = new ProcesExtractie();
        procesExtractieTwee.setBerichtType(BERICHT_TYPE);
        procesExtractieTwee.setIndicatieBeeindigdGeteld(false);
        procesExtractieTwee.setIndicatieGestartGeteld(false);
        procesExtractieTwee.setKanaal(KANAAL);
        procesExtractieTwee.setProcesnaam(PROCES);
        procesExtractieTwee.setProcesInstantieId(321L);
        procesExtractieTwee.setStartDatum(startDatum);

        procesExtractieGestartLijst.add(procesExtractieEen);
        procesExtractieGestartLijst.add(procesExtractieTwee);
        procesExtractieBeeindigdLijst.add(procesExtractieEen);

        final BerichtTelling berichtTelling = new BerichtTelling();
        berichtTelling.setBerichtType(BERICHT_TYPE);
        berichtTelling.setDatum(datumTot);
        berichtTelling.setAantalIngaand(123);
        berichtTelling.setAantalUitgaand(321);
        berichtTelling.setKanaal(KANAAL);

        final ProcesTelling procesTelling = new ProcesTelling();
        procesTelling.setBerichtType(BERICHT_TYPE);
        procesTelling.setKanaal(KANAAL);
        procesTelling.setProcesnaam(PROCES);
        procesTelling.setAantalGestarteProcessen(654);
        procesTelling.setAantalBeeindigdeProcessen(456);

        when(runtimeRepositoryService.voegRuntimeToe(RUNTIME_NAAM)).thenReturn(true);
        when(berichtRepositoryService.telInTellingTeVerwerkenBerichten(datumTot)).thenReturn(2L);
        when(berichtRepositoryService.selecteerInTellingTeVerwerkenBerichten(datumTot, 2)).thenReturn(berichtenLijst);
        when(berichtTellingenRepositoryService.haalBerichtTellingOp(BERICHT_TYPE, KANAAL, datumTot)).thenReturn(berichtTelling);
        when(berichtRepositoryService.updateIndicatieGeteldBerichten(Matchers.anyListOf(Long.class))).thenReturn(true);
        when(procesExtractieRepositoryService.telInTellingTeVerwerkenGestarteProcessen(datumTot)).thenReturn(2L);
        when(procesExtractieRepositoryService.telInTellingTeVerwerkenBeeindigdeProcessen(datumTot)).thenReturn(1L);
        when(procesExtractieRepositoryService.selecteerInTellingTeVerwerkenGestarteProcesInstanties(datumTot, 2)).thenReturn(
            procesExtractieGestartLijst);
        when(procesExtractieRepositoryService.selecteerInTellingTeVerwerkenBeeindigdeProcesInstanties(datumTot, 1)).thenReturn(
            procesExtractieBeeindigdLijst);
        when(procesTellingenRepositoryService.haalProcesTellingOp(PROCES, KANAAL, BERICHT_TYPE, startDatum)).thenReturn(procesTelling);
        when(procesExtractieRepositoryService.updateIndicatieGestartGeteldProcesExtracties(Matchers.anyListOf(Long.class))).thenReturn(true);
        when(procesExtractieRepositoryService.updateIndicatieBeeindigdGeteldProcesExtracties(Matchers.anyListOf(Long.class))).thenReturn(
            true);

        service.werkLopendeTellingenBij(datumTot);

        verify(runtimeRepositoryService, times(1)).voegRuntimeToe(RUNTIME_NAAM);
        verify(berichtRepositoryService, times(1)).telInTellingTeVerwerkenBerichten(datumTot);
        verify(berichtRepositoryService, times(1)).selecteerInTellingTeVerwerkenBerichten(datumTot, 2);
        verify(berichtTellingenRepositoryService, times(1)).haalBerichtTellingOp(BERICHT_TYPE, KANAAL, datumTot);
        verify(berichtTellingenRepositoryService, times(1)).save(berichtTelling);
        verify(procesExtractieRepositoryService, times(1)).telInTellingTeVerwerkenGestarteProcessen(datumTot);
        verify(procesExtractieRepositoryService, times(1)).selecteerInTellingTeVerwerkenGestarteProcesInstanties(datumTot, 2);
        verify(procesExtractieRepositoryService, times(1)).updateIndicatieGestartGeteldProcesExtracties(Matchers.anyListOf(Long.class));
        verify(procesExtractieRepositoryService, times(1)).telInTellingTeVerwerkenBeeindigdeProcessen(datumTot);
        verify(procesExtractieRepositoryService, times(1)).selecteerInTellingTeVerwerkenBeeindigdeProcesInstanties(datumTot, 1);
        verify(procesExtractieRepositoryService, times(1)).updateIndicatieBeeindigdGeteldProcesExtracties(Matchers.anyListOf(Long.class));
        verify(procesTellingenRepositoryService, times(2)).save(procesTelling);
        verify(runtimeRepositoryService, times(1)).verwijderRuntime(RUNTIME_NAAM);
        Mockito.verifyNoMoreInteractions(runtimeRepositoryService);

        Assert.assertEquals(Integer.valueOf(123), berichtTelling.getAantalIngaand());
        Assert.assertEquals(Integer.valueOf(323), berichtTelling.getAantalUitgaand());
        Assert.assertEquals(Integer.valueOf(656), procesTelling.getAantalGestarteProcessen());
        Assert.assertEquals(Integer.valueOf(457), procesTelling.getAantalBeeindigdeProcessen());

    }

    @Test
    public void testWelBerichtenGeenProcesExtractiesWelTellingen() {

        final List<Bericht> berichtenLijst = new ArrayList<>();
        final Bericht berichtEen = new Bericht();
        berichtEen.setId(1L);
        berichtEen.setKanaal(KANAAL);
        berichtEen.setNaam(BERICHT_TYPE);
        berichtEen.setTijdstip(datumTot);
        berichtEen.setIndicatieGeteld(false);
        final Bericht berichtTwee = new Bericht();
        berichtTwee.setId(2L);
        berichtTwee.setKanaal(KANAAL);
        berichtTwee.setNaam(BERICHT_TYPE);
        berichtTwee.setTijdstip(datumTot);
        berichtTwee.setIndicatieGeteld(false);
        berichtenLijst.add(berichtEen);
        berichtenLijst.add(berichtTwee);

        final BerichtTelling berichtTelling = new BerichtTelling();
        berichtTelling.setBerichtType(BERICHT_TYPE);
        berichtTelling.setDatum(datumTot);
        berichtTelling.setAantalIngaand(123);
        berichtTelling.setAantalUitgaand(321);
        berichtTelling.setKanaal(KANAAL);

        when(runtimeRepositoryService.voegRuntimeToe(RUNTIME_NAAM)).thenReturn(true);
        when(berichtRepositoryService.telInTellingTeVerwerkenBerichten(datumTot)).thenReturn(2L);
        when(berichtRepositoryService.selecteerInTellingTeVerwerkenBerichten(datumTot, 2)).thenReturn(berichtenLijst);
        when(berichtTellingenRepositoryService.haalBerichtTellingOp(BERICHT_TYPE, KANAAL, datumTot)).thenReturn(berichtTelling);
        when(berichtRepositoryService.updateIndicatieGeteldBerichten(Matchers.anyListOf(Long.class))).thenReturn(true);
        when(procesExtractieRepositoryService.telInTellingTeVerwerkenGestarteProcessen(datumTot)).thenReturn(0L);
        when(procesExtractieRepositoryService.telInTellingTeVerwerkenBeeindigdeProcessen(datumTot)).thenReturn(0L);

        service.werkLopendeTellingenBij(datumTot);

        verify(runtimeRepositoryService, times(1)).voegRuntimeToe(RUNTIME_NAAM);
        verify(berichtRepositoryService, times(1)).telInTellingTeVerwerkenBerichten(datumTot);
        verify(berichtRepositoryService, times(1)).selecteerInTellingTeVerwerkenBerichten(datumTot, 2);
        verify(berichtTellingenRepositoryService, times(1)).haalBerichtTellingOp(BERICHT_TYPE, KANAAL, datumTot);
        verify(berichtTellingenRepositoryService, times(1)).save(berichtTelling);
        verify(procesExtractieRepositoryService, times(1)).telInTellingTeVerwerkenGestarteProcessen(datumTot);
        verify(procesExtractieRepositoryService, times(1)).telInTellingTeVerwerkenBeeindigdeProcessen(datumTot);
        verify(runtimeRepositoryService, times(1)).verwijderRuntime(RUNTIME_NAAM);
        Mockito.verifyNoMoreInteractions(runtimeRepositoryService);
        Mockito.verifyZeroInteractions(procesTellingenRepositoryService);

        Assert.assertEquals(Integer.valueOf(123), berichtTelling.getAantalIngaand());
        Assert.assertEquals(Integer.valueOf(323), berichtTelling.getAantalUitgaand());

    }

    @Test
    public void testWelBerichtenEnProcesExtractiesGeenTellingen() {

        final List<Bericht> berichtenLijst = new ArrayList<>();
        final Bericht berichtEen = new Bericht();
        berichtEen.setId(1L);
        berichtEen.setKanaal(KANAAL);
        berichtEen.setNaam(BERICHT_TYPE);
        berichtEen.setTijdstip(datumTot);
        berichtEen.setIndicatieGeteld(false);
        final Bericht berichtTwee = new Bericht();
        berichtTwee.setId(2L);
        berichtTwee.setKanaal(KANAAL);
        berichtTwee.setNaam(BERICHT_TYPE);
        berichtTwee.setTijdstip(datumTot);
        berichtTwee.setIndicatieGeteld(false);
        berichtenLijst.add(berichtEen);
        berichtenLijst.add(berichtTwee);

        final List<ProcesExtractie> procesExtractieGestartLijst = new ArrayList<>();
        final List<ProcesExtractie> procesExtractieBeeindigdLijst = new ArrayList<>();

        final ProcesExtractie procesExtractieEen = new ProcesExtractie();
        procesExtractieEen.setBerichtType(BERICHT_TYPE);
        procesExtractieEen.setEindDatum(eindDatum);
        procesExtractieEen.setIndicatieBeeindigdGeteld(false);
        procesExtractieEen.setIndicatieGestartGeteld(false);
        procesExtractieEen.setKanaal(KANAAL);
        procesExtractieEen.setProcesnaam(PROCES);
        procesExtractieEen.setProcesInstantieId(123L);
        procesExtractieEen.setStartDatum(startDatum);

        final ProcesExtractie procesExtractieTwee = new ProcesExtractie();
        procesExtractieTwee.setBerichtType(BERICHT_TYPE);
        procesExtractieTwee.setIndicatieBeeindigdGeteld(false);
        procesExtractieTwee.setIndicatieGestartGeteld(false);
        procesExtractieTwee.setKanaal(KANAAL);
        procesExtractieTwee.setProcesnaam(PROCES);
        procesExtractieTwee.setProcesInstantieId(321L);
        procesExtractieTwee.setStartDatum(startDatum);

        procesExtractieGestartLijst.add(procesExtractieEen);
        procesExtractieGestartLijst.add(procesExtractieTwee);
        procesExtractieBeeindigdLijst.add(procesExtractieEen);

        when(runtimeRepositoryService.voegRuntimeToe(RUNTIME_NAAM)).thenReturn(true);
        when(berichtRepositoryService.telInTellingTeVerwerkenBerichten(datumTot)).thenReturn(2L);
        when(berichtRepositoryService.selecteerInTellingTeVerwerkenBerichten(datumTot, 2)).thenReturn(berichtenLijst);
        when(berichtRepositoryService.updateIndicatieGeteldBerichten(Matchers.anyListOf(Long.class))).thenReturn(true);
        when(procesExtractieRepositoryService.telInTellingTeVerwerkenGestarteProcessen(datumTot)).thenReturn(2L);
        when(procesExtractieRepositoryService.telInTellingTeVerwerkenBeeindigdeProcessen(datumTot)).thenReturn(1L);
        when(procesExtractieRepositoryService.selecteerInTellingTeVerwerkenGestarteProcesInstanties(datumTot, 2)).thenReturn(
            procesExtractieGestartLijst);
        when(procesExtractieRepositoryService.selecteerInTellingTeVerwerkenBeeindigdeProcesInstanties(datumTot, 1)).thenReturn(
            procesExtractieBeeindigdLijst);
        when(procesExtractieRepositoryService.updateIndicatieGestartGeteldProcesExtracties(Matchers.anyListOf(Long.class))).thenReturn(true);
        when(procesExtractieRepositoryService.updateIndicatieBeeindigdGeteldProcesExtracties(Matchers.anyListOf(Long.class))).thenReturn(
            true);

        service.werkLopendeTellingenBij(datumTot);

        verify(runtimeRepositoryService, times(1)).voegRuntimeToe(RUNTIME_NAAM);
        verify(berichtRepositoryService, times(1)).telInTellingTeVerwerkenBerichten(datumTot);
        verify(berichtRepositoryService, times(1)).selecteerInTellingTeVerwerkenBerichten(datumTot, 2);
        verify(berichtTellingenRepositoryService, times(1)).haalBerichtTellingOp(BERICHT_TYPE, KANAAL, datumTot);
        verify(berichtTellingenRepositoryService, times(1)).save(Matchers.any(BerichtTelling.class));
        verify(procesExtractieRepositoryService, times(1)).telInTellingTeVerwerkenGestarteProcessen(datumTot);
        verify(procesExtractieRepositoryService, times(1)).selecteerInTellingTeVerwerkenGestarteProcesInstanties(datumTot, 2);
        verify(procesExtractieRepositoryService, times(1)).updateIndicatieGestartGeteldProcesExtracties(Matchers.anyListOf(Long.class));
        verify(procesExtractieRepositoryService, times(1)).telInTellingTeVerwerkenBeeindigdeProcessen(datumTot);
        verify(procesExtractieRepositoryService, times(1)).selecteerInTellingTeVerwerkenBeeindigdeProcesInstanties(datumTot, 1);
        verify(procesExtractieRepositoryService, times(1)).updateIndicatieBeeindigdGeteldProcesExtracties(Matchers.anyListOf(Long.class));
        verify(procesTellingenRepositoryService, times(2)).save(Matchers.any(ProcesTelling.class));
        verify(runtimeRepositoryService, times(1)).verwijderRuntime(RUNTIME_NAAM);
        Mockito.verifyNoMoreInteractions(runtimeRepositoryService);

    }

    @Test
    public void testGeenBerichtenWelProcesExtractiesWelTellingen() {

        final List<ProcesExtractie> procesExtractieGestartLijst = new ArrayList<>();
        final List<ProcesExtractie> procesExtractieBeeindigdLijst = new ArrayList<>();

        final ProcesExtractie procesExtractieEen = new ProcesExtractie();
        procesExtractieEen.setBerichtType(BERICHT_TYPE);
        procesExtractieEen.setEindDatum(eindDatum);
        procesExtractieEen.setIndicatieBeeindigdGeteld(false);
        procesExtractieEen.setIndicatieGestartGeteld(false);
        procesExtractieEen.setKanaal(KANAAL);
        procesExtractieEen.setProcesnaam(PROCES);
        procesExtractieEen.setProcesInstantieId(123L);
        procesExtractieEen.setStartDatum(startDatum);

        final ProcesExtractie procesExtractieTwee = new ProcesExtractie();
        procesExtractieTwee.setBerichtType(BERICHT_TYPE);
        procesExtractieTwee.setIndicatieBeeindigdGeteld(false);
        procesExtractieTwee.setIndicatieGestartGeteld(false);
        procesExtractieTwee.setKanaal(KANAAL);
        procesExtractieTwee.setProcesnaam(PROCES);
        procesExtractieTwee.setProcesInstantieId(321L);
        procesExtractieTwee.setStartDatum(startDatum);

        procesExtractieGestartLijst.add(procesExtractieEen);
        procesExtractieGestartLijst.add(procesExtractieTwee);
        procesExtractieBeeindigdLijst.add(procesExtractieEen);

        final ProcesTelling procesTelling = new ProcesTelling();
        procesTelling.setBerichtType(BERICHT_TYPE);
        procesTelling.setKanaal(KANAAL);
        procesTelling.setProcesnaam(PROCES);
        procesTelling.setAantalGestarteProcessen(654);
        procesTelling.setAantalBeeindigdeProcessen(456);

        when(runtimeRepositoryService.voegRuntimeToe(RUNTIME_NAAM)).thenReturn(true);
        when(berichtRepositoryService.telInTellingTeVerwerkenBerichten(datumTot)).thenReturn(0L);
        when(procesExtractieRepositoryService.telInTellingTeVerwerkenGestarteProcessen(datumTot)).thenReturn(2L);
        when(procesExtractieRepositoryService.telInTellingTeVerwerkenBeeindigdeProcessen(datumTot)).thenReturn(1L);
        when(procesExtractieRepositoryService.selecteerInTellingTeVerwerkenGestarteProcesInstanties(datumTot, 2)).thenReturn(
            procesExtractieGestartLijst);
        when(procesExtractieRepositoryService.selecteerInTellingTeVerwerkenBeeindigdeProcesInstanties(datumTot, 1)).thenReturn(
            procesExtractieBeeindigdLijst);
        when(procesTellingenRepositoryService.haalProcesTellingOp(PROCES, KANAAL, BERICHT_TYPE, startDatum)).thenReturn(procesTelling);
        when(procesExtractieRepositoryService.updateIndicatieGestartGeteldProcesExtracties(Matchers.anyListOf(Long.class))).thenReturn(true);
        when(procesExtractieRepositoryService.updateIndicatieBeeindigdGeteldProcesExtracties(Matchers.anyListOf(Long.class))).thenReturn(
            true);

        service.werkLopendeTellingenBij(datumTot);

        verify(runtimeRepositoryService, times(1)).voegRuntimeToe(RUNTIME_NAAM);
        verify(berichtRepositoryService, times(1)).telInTellingTeVerwerkenBerichten(datumTot);
        verify(procesExtractieRepositoryService, times(1)).telInTellingTeVerwerkenGestarteProcessen(datumTot);
        verify(procesExtractieRepositoryService, times(1)).selecteerInTellingTeVerwerkenGestarteProcesInstanties(datumTot, 2);
        verify(procesExtractieRepositoryService, times(1)).updateIndicatieGestartGeteldProcesExtracties(Matchers.anyListOf(Long.class));
        verify(procesExtractieRepositoryService, times(1)).telInTellingTeVerwerkenBeeindigdeProcessen(datumTot);
        verify(procesExtractieRepositoryService, times(1)).selecteerInTellingTeVerwerkenBeeindigdeProcesInstanties(datumTot, 1);
        verify(procesExtractieRepositoryService, times(1)).updateIndicatieBeeindigdGeteldProcesExtracties(Matchers.anyListOf(Long.class));
        verify(procesTellingenRepositoryService, times(2)).save(procesTelling);
        verify(runtimeRepositoryService, times(1)).verwijderRuntime(RUNTIME_NAAM);
        Mockito.verifyNoMoreInteractions(runtimeRepositoryService);
        Mockito.verifyZeroInteractions(berichtTellingenRepositoryService);

        Assert.assertEquals(Integer.valueOf(656), procesTelling.getAantalGestarteProcessen());
        Assert.assertEquals(Integer.valueOf(457), procesTelling.getAantalBeeindigdeProcessen());

    }

    @Test
    public void testWelBerichtenGeenProcesExtractiesGeenTellingen() {

        final List<Bericht> berichtenLijst = new ArrayList<>();
        final Bericht berichtEen = new Bericht();
        berichtEen.setId(1L);
        berichtEen.setKanaal(KANAAL);
        berichtEen.setNaam(BERICHT_TYPE);
        berichtEen.setTijdstip(datumTot);
        berichtEen.setIndicatieGeteld(false);
        berichtEen.setRichting(Bericht.Direction.INKOMEND.getCode());
        final Bericht berichtTwee = new Bericht();
        berichtTwee.setId(2L);
        berichtTwee.setKanaal(KANAAL);
        berichtTwee.setNaam(BERICHT_TYPE);
        berichtTwee.setTijdstip(datumTot);
        berichtTwee.setIndicatieGeteld(false);
        final Bericht berichtDrie = new Bericht();
        berichtDrie.setId(3L);
        berichtDrie.setJbpmActie(GENEGEERD);
        berichtDrie.setKanaal(KANAAL);
        berichtDrie.setNaam(BERICHT_TYPE);
        berichtDrie.setTijdstip(datumTot);
        berichtDrie.setIndicatieGeteld(false);
        berichtDrie.setRichting(Bericht.Direction.UITGAAND.getCode());
        final Bericht berichtVier = new Bericht();
        berichtVier.setId(4L);
        berichtVier.setKanaal(KANAAL);
        berichtVier.setNaam(BERICHT_TYPE);
        berichtVier.setTijdstip(datumTot);
        berichtVier.setIndicatieGeteld(false);
        berichtVier.setRichting(Bericht.Direction.UITGAAND.getCode());
        berichtenLijst.add(berichtEen);
        berichtenLijst.add(berichtTwee);
        berichtenLijst.add(berichtDrie);
        berichtenLijst.add(berichtVier);

        when(runtimeRepositoryService.voegRuntimeToe(RUNTIME_NAAM)).thenReturn(true);
        when(berichtRepositoryService.telInTellingTeVerwerkenBerichten(datumTot)).thenReturn(2L);
        when(berichtRepositoryService.selecteerInTellingTeVerwerkenBerichten(datumTot, 2)).thenReturn(berichtenLijst);
        when(berichtRepositoryService.updateIndicatieGeteldBerichten(Matchers.anyListOf(Long.class))).thenReturn(true);
        when(procesExtractieRepositoryService.telInTellingTeVerwerkenGestarteProcessen(datumTot)).thenReturn(0L);
        when(procesExtractieRepositoryService.telInTellingTeVerwerkenBeeindigdeProcessen(datumTot)).thenReturn(0L);

        service.werkLopendeTellingenBij(datumTot);

        verify(runtimeRepositoryService, times(1)).voegRuntimeToe(RUNTIME_NAAM);
        verify(berichtRepositoryService, times(1)).telInTellingTeVerwerkenBerichten(datumTot);
        verify(berichtRepositoryService, times(1)).selecteerInTellingTeVerwerkenBerichten(datumTot, 2);
        verify(berichtTellingenRepositoryService, times(1)).haalBerichtTellingOp(BERICHT_TYPE, KANAAL, datumTot);
        verify(berichtTellingenRepositoryService, times(1)).save(Matchers.any(BerichtTelling.class));
        verify(procesExtractieRepositoryService, times(1)).telInTellingTeVerwerkenGestarteProcessen(datumTot);
        verify(procesExtractieRepositoryService, times(1)).telInTellingTeVerwerkenBeeindigdeProcessen(datumTot);
        verify(runtimeRepositoryService, times(1)).verwijderRuntime(RUNTIME_NAAM);
        Mockito.verifyNoMoreInteractions(runtimeRepositoryService);
        Mockito.verifyZeroInteractions(procesTellingenRepositoryService);

    }

    @Test
    public void testGeenBerichtenWelProcesExtractiesGeenTellingen() {

        final List<ProcesExtractie> procesExtractieGestartLijst = new ArrayList<>();
        final List<ProcesExtractie> procesExtractieBeeindigdLijst = new ArrayList<>();

        final ProcesExtractie procesExtractieEen = new ProcesExtractie();
        procesExtractieEen.setBerichtType(BERICHT_TYPE);
        procesExtractieEen.setEindDatum(eindDatum);
        procesExtractieEen.setIndicatieBeeindigdGeteld(false);
        procesExtractieEen.setIndicatieGestartGeteld(false);
        procesExtractieEen.setKanaal(KANAAL);
        procesExtractieEen.setProcesnaam(PROCES);
        procesExtractieEen.setProcesInstantieId(123L);
        procesExtractieEen.setStartDatum(startDatum);

        final ProcesExtractie procesExtractieTwee = new ProcesExtractie();
        procesExtractieTwee.setBerichtType(BERICHT_TYPE);
        procesExtractieTwee.setIndicatieBeeindigdGeteld(false);
        procesExtractieTwee.setIndicatieGestartGeteld(false);
        procesExtractieTwee.setKanaal(KANAAL);
        procesExtractieTwee.setProcesnaam(PROCES);
        procesExtractieTwee.setProcesInstantieId(321L);
        procesExtractieTwee.setStartDatum(startDatum);

        procesExtractieGestartLijst.add(procesExtractieEen);
        procesExtractieGestartLijst.add(procesExtractieTwee);
        procesExtractieBeeindigdLijst.add(procesExtractieEen);

        when(runtimeRepositoryService.voegRuntimeToe(RUNTIME_NAAM)).thenReturn(true);
        when(berichtRepositoryService.telInTellingTeVerwerkenBerichten(datumTot)).thenReturn(0L);
        when(procesExtractieRepositoryService.telInTellingTeVerwerkenGestarteProcessen(datumTot)).thenReturn(2L);
        when(procesExtractieRepositoryService.telInTellingTeVerwerkenBeeindigdeProcessen(datumTot)).thenReturn(1L);
        when(procesExtractieRepositoryService.selecteerInTellingTeVerwerkenGestarteProcesInstanties(datumTot, 2)).thenReturn(
            procesExtractieGestartLijst);
        when(procesExtractieRepositoryService.selecteerInTellingTeVerwerkenBeeindigdeProcesInstanties(datumTot, 1)).thenReturn(
            procesExtractieBeeindigdLijst);
        when(procesExtractieRepositoryService.updateIndicatieGestartGeteldProcesExtracties(Matchers.anyListOf(Long.class))).thenReturn(true);
        when(procesExtractieRepositoryService.updateIndicatieBeeindigdGeteldProcesExtracties(Matchers.anyListOf(Long.class))).thenReturn(
            true);

        service.werkLopendeTellingenBij(datumTot);

        verify(runtimeRepositoryService, times(1)).voegRuntimeToe(RUNTIME_NAAM);
        verify(berichtRepositoryService, times(1)).telInTellingTeVerwerkenBerichten(datumTot);
        verify(procesExtractieRepositoryService, times(1)).telInTellingTeVerwerkenGestarteProcessen(datumTot);
        verify(procesExtractieRepositoryService, times(1)).selecteerInTellingTeVerwerkenGestarteProcesInstanties(datumTot, 2);
        verify(procesExtractieRepositoryService, times(1)).updateIndicatieGestartGeteldProcesExtracties(Matchers.anyListOf(Long.class));
        verify(procesExtractieRepositoryService, times(1)).telInTellingTeVerwerkenBeeindigdeProcessen(datumTot);
        verify(procesExtractieRepositoryService, times(1)).selecteerInTellingTeVerwerkenBeeindigdeProcesInstanties(datumTot, 1);
        verify(procesExtractieRepositoryService, times(1)).updateIndicatieBeeindigdGeteldProcesExtracties(Matchers.anyListOf(Long.class));
        verify(procesTellingenRepositoryService, times(2)).save(Matchers.any(ProcesTelling.class));
        verify(runtimeRepositoryService, times(1)).verwijderRuntime(RUNTIME_NAAM);
        Mockito.verifyNoMoreInteractions(runtimeRepositoryService);
        Mockito.verifyZeroInteractions(berichtTellingenRepositoryService);
    }

    @Test
    public void testWelBerichtenGeenProcesExtractiesMetTellingGrootAantal() {

        final List<Bericht> berichtenLijst = new ArrayList<>();

        for (int teller = 0; teller <= MAXIMALE_BATCH_GROOTTE; teller++) {
            final Bericht berichtEen = new Bericht();
            berichtEen.setId((long) teller);
            berichtEen.setKanaal(KANAAL);
            berichtEen.setNaam(BERICHT_TYPE);
            berichtEen.setTijdstip(datumTot);
            berichtEen.setIndicatieGeteld(false);
            berichtEen.setRichting(Bericht.Direction.INKOMEND.getCode());
            berichtenLijst.add(berichtEen);
        }

        final BerichtTelling berichtTelling = new BerichtTelling();
        berichtTelling.setBerichtType(BERICHT_TYPE);
        berichtTelling.setDatum(datumTot);
        berichtTelling.setAantalIngaand(123);
        berichtTelling.setAantalUitgaand(321);
        berichtTelling.setKanaal(KANAAL);

        when(runtimeRepositoryService.voegRuntimeToe(RUNTIME_NAAM)).thenReturn(true);
        when(berichtRepositoryService.telInTellingTeVerwerkenBerichten(datumTot)).thenReturn(5001L);
        when(berichtRepositoryService.selecteerInTellingTeVerwerkenBerichten(datumTot, 5000)).thenReturn(berichtenLijst.subList(0, 5000));
        when(berichtRepositoryService.selecteerInTellingTeVerwerkenBerichten(datumTot, 1)).thenReturn(
            berichtenLijst.subList(5000, berichtenLijst.size()));
        when(berichtRepositoryService.updateIndicatieGeteldBerichten(Matchers.anyListOf(Long.class))).thenReturn(true);
        when(berichtTellingenRepositoryService.haalBerichtTellingOp(BERICHT_TYPE, KANAAL, datumTot)).thenReturn(berichtTelling);
        when(procesExtractieRepositoryService.telInTellingTeVerwerkenGestarteProcessen(datumTot)).thenReturn(0L);
        when(procesExtractieRepositoryService.telInTellingTeVerwerkenBeeindigdeProcessen(datumTot)).thenReturn(0L);

        service.werkLopendeTellingenBij(datumTot);

        verify(runtimeRepositoryService, times(1)).voegRuntimeToe(RUNTIME_NAAM);
        verify(berichtRepositoryService, times(1)).telInTellingTeVerwerkenBerichten(datumTot);
        verify(berichtRepositoryService, times(1)).selecteerInTellingTeVerwerkenBerichten(datumTot, 5000);
        verify(berichtRepositoryService, times(1)).selecteerInTellingTeVerwerkenBerichten(datumTot, 1);
        verify(berichtTellingenRepositoryService, times(2)).haalBerichtTellingOp(BERICHT_TYPE, KANAAL, datumTot);
        verify(berichtTellingenRepositoryService, times(2)).save(Matchers.any(BerichtTelling.class));
        verify(procesExtractieRepositoryService, times(1)).telInTellingTeVerwerkenGestarteProcessen(datumTot);
        verify(procesExtractieRepositoryService, times(1)).telInTellingTeVerwerkenBeeindigdeProcessen(datumTot);
        verify(runtimeRepositoryService, times(1)).verwijderRuntime(RUNTIME_NAAM);
        Mockito.verifyNoMoreInteractions(runtimeRepositoryService);
        Mockito.verifyZeroInteractions(procesTellingenRepositoryService);

    }

    @Test
    public void testGeenBerichtenWelProcesExtractiesGeenTellingenGrootAantal() {

        final List<ProcesExtractie> procesExtractieGestartLijst = new ArrayList<>();
        final List<ProcesExtractie> procesExtractieBeeindigdLijst = new ArrayList<>();

        for (int teller = 0; teller <= MAXIMALE_BATCH_GROOTTE; teller++) {

            final ProcesExtractie procesExtractie = new ProcesExtractie();
            procesExtractie.setBerichtType(BERICHT_TYPE);
            procesExtractie.setEindDatum(eindDatum);
            procesExtractie.setIndicatieBeeindigdGeteld(false);
            procesExtractie.setIndicatieGestartGeteld(false);
            procesExtractie.setKanaal(KANAAL);
            procesExtractie.setProcesnaam(PROCES);
            procesExtractie.setProcesInstantieId((long) teller);
            procesExtractie.setStartDatum(startDatum);

            procesExtractieGestartLijst.add(procesExtractie);
            procesExtractieBeeindigdLijst.add(procesExtractie);
        }

        when(runtimeRepositoryService.voegRuntimeToe(RUNTIME_NAAM)).thenReturn(true);
        when(berichtRepositoryService.telInTellingTeVerwerkenBerichten(datumTot)).thenReturn(0L);
        when(procesExtractieRepositoryService.telInTellingTeVerwerkenGestarteProcessen(datumTot)).thenReturn(5001L);
        when(procesExtractieRepositoryService.telInTellingTeVerwerkenBeeindigdeProcessen(datumTot)).thenReturn(5001L);
        when(procesExtractieRepositoryService.selecteerInTellingTeVerwerkenGestarteProcesInstanties(datumTot, 5000)).thenReturn(
            procesExtractieGestartLijst.subList(0, 5000));
        when(procesExtractieRepositoryService.selecteerInTellingTeVerwerkenGestarteProcesInstanties(datumTot, 1)).thenReturn(
            procesExtractieGestartLijst.subList(5000, procesExtractieGestartLijst.size()));
        when(procesExtractieRepositoryService.selecteerInTellingTeVerwerkenBeeindigdeProcesInstanties(datumTot, 5000)).thenReturn(
            procesExtractieBeeindigdLijst.subList(0, 5000));
        when(procesExtractieRepositoryService.selecteerInTellingTeVerwerkenBeeindigdeProcesInstanties(datumTot, 1)).thenReturn(
            procesExtractieBeeindigdLijst.subList(5000, procesExtractieBeeindigdLijst.size()));
        when(procesExtractieRepositoryService.updateIndicatieGestartGeteldProcesExtracties(Matchers.anyListOf(Long.class))).thenReturn(true);
        when(procesExtractieRepositoryService.updateIndicatieBeeindigdGeteldProcesExtracties(Matchers.anyListOf(Long.class))).thenReturn(
            true);

        service.werkLopendeTellingenBij(datumTot);

        verify(runtimeRepositoryService, times(1)).voegRuntimeToe(RUNTIME_NAAM);
        verify(berichtRepositoryService, times(1)).telInTellingTeVerwerkenBerichten(datumTot);
        verify(procesExtractieRepositoryService, times(1)).telInTellingTeVerwerkenGestarteProcessen(datumTot);
        verify(procesExtractieRepositoryService, times(1)).selecteerInTellingTeVerwerkenGestarteProcesInstanties(datumTot, 5000);
        verify(procesExtractieRepositoryService, times(1)).selecteerInTellingTeVerwerkenGestarteProcesInstanties(datumTot, 1);
        verify(procesExtractieRepositoryService, times(2)).updateIndicatieGestartGeteldProcesExtracties(Matchers.anyListOf(Long.class));
        verify(procesExtractieRepositoryService, times(1)).telInTellingTeVerwerkenBeeindigdeProcessen(datumTot);
        verify(procesExtractieRepositoryService, times(1)).selecteerInTellingTeVerwerkenBeeindigdeProcesInstanties(datumTot, 5000);
        verify(procesExtractieRepositoryService, times(1)).selecteerInTellingTeVerwerkenBeeindigdeProcesInstanties(datumTot, 1);
        verify(procesExtractieRepositoryService, times(2)).updateIndicatieBeeindigdGeteldProcesExtracties(Matchers.anyListOf(Long.class));
        verify(procesTellingenRepositoryService, times(4)).save(Matchers.any(ProcesTelling.class));
        verify(runtimeRepositoryService, times(1)).verwijderRuntime(RUNTIME_NAAM);
        Mockito.verifyNoMoreInteractions(runtimeRepositoryService);
        Mockito.verifyZeroInteractions(berichtTellingenRepositoryService);
    }

    @Test
    public void testWelBerichtenBijwerkenMislukt() {

        final List<Bericht> berichtenLijst = new ArrayList<>();
        final Bericht berichtEen = new Bericht();
        berichtEen.setId(1L);
        berichtEen.setKanaal(KANAAL);
        berichtEen.setNaam(BERICHT_TYPE);
        berichtEen.setTijdstip(datumTot);
        berichtEen.setIndicatieGeteld(false);
        berichtEen.setRichting(Bericht.Direction.INKOMEND.getCode());
        berichtenLijst.add(berichtEen);

        when(runtimeRepositoryService.voegRuntimeToe(RUNTIME_NAAM)).thenReturn(true);
        when(berichtRepositoryService.telInTellingTeVerwerkenBerichten(datumTot)).thenReturn(1L);
        when(berichtRepositoryService.selecteerInTellingTeVerwerkenBerichten(datumTot, 1)).thenReturn(berichtenLijst);
        when(berichtRepositoryService.updateIndicatieGeteldBerichten(Matchers.anyListOf(Long.class))).thenReturn(false);

        service.werkLopendeTellingenBij(datumTot);

        verify(runtimeRepositoryService, times(1)).voegRuntimeToe(RUNTIME_NAAM);
        verify(berichtRepositoryService, times(1)).telInTellingTeVerwerkenBerichten(datumTot);
        verify(berichtRepositoryService, times(1)).selecteerInTellingTeVerwerkenBerichten(datumTot, 1);
        verify(berichtTellingenRepositoryService, times(1)).haalBerichtTellingOp(BERICHT_TYPE, KANAAL, datumTot);
        verify(berichtTellingenRepositoryService, times(1)).save(Matchers.any(BerichtTelling.class));
        verify(runtimeRepositoryService, times(1)).verwijderRuntime(RUNTIME_NAAM);
        Mockito.verifyNoMoreInteractions(runtimeRepositoryService);
        Mockito.verifyZeroInteractions(procesTellingenRepositoryService);

    }

    @Test
    public void testWelProcesExtractiesBijwerkenGestartMislukt() {

        final List<ProcesExtractie> procesExtractieGestartLijst = new ArrayList<>();
        final List<ProcesExtractie> procesExtractieBeeindigdLijst = new ArrayList<>();

        final ProcesExtractie procesExtractieEen = new ProcesExtractie();
        procesExtractieEen.setBerichtType(BERICHT_TYPE);
        procesExtractieEen.setEindDatum(eindDatum);
        procesExtractieEen.setIndicatieBeeindigdGeteld(false);
        procesExtractieEen.setIndicatieGestartGeteld(false);
        procesExtractieEen.setKanaal(KANAAL);
        procesExtractieEen.setProcesnaam(PROCES);
        procesExtractieEen.setProcesInstantieId(123L);
        procesExtractieEen.setStartDatum(startDatum);

        final ProcesExtractie procesExtractieTwee = new ProcesExtractie();
        procesExtractieTwee.setBerichtType(BERICHT_TYPE);
        procesExtractieTwee.setIndicatieBeeindigdGeteld(false);
        procesExtractieTwee.setIndicatieGestartGeteld(false);
        procesExtractieTwee.setKanaal(KANAAL);
        procesExtractieTwee.setProcesnaam(PROCES);
        procesExtractieTwee.setProcesInstantieId(321L);
        procesExtractieTwee.setStartDatum(startDatum);

        procesExtractieGestartLijst.add(procesExtractieEen);
        procesExtractieGestartLijst.add(procesExtractieTwee);
        procesExtractieBeeindigdLijst.add(procesExtractieEen);

        when(runtimeRepositoryService.voegRuntimeToe(RUNTIME_NAAM)).thenReturn(true);
        when(berichtRepositoryService.telInTellingTeVerwerkenBerichten(datumTot)).thenReturn(0L);
        when(procesExtractieRepositoryService.telInTellingTeVerwerkenGestarteProcessen(datumTot)).thenReturn(2L);
        when(procesExtractieRepositoryService.telInTellingTeVerwerkenBeeindigdeProcessen(datumTot)).thenReturn(1L);
        when(procesExtractieRepositoryService.selecteerInTellingTeVerwerkenGestarteProcesInstanties(datumTot, 2)).thenReturn(
            procesExtractieGestartLijst);
        when(procesExtractieRepositoryService.selecteerInTellingTeVerwerkenBeeindigdeProcesInstanties(datumTot, 1)).thenReturn(
            procesExtractieBeeindigdLijst);
        when(procesExtractieRepositoryService.updateIndicatieGestartGeteldProcesExtracties(Matchers.anyListOf(Long.class))).thenReturn(
            false);

        service.werkLopendeTellingenBij(datumTot);

        verify(runtimeRepositoryService, times(1)).voegRuntimeToe(RUNTIME_NAAM);
        verify(berichtRepositoryService, times(1)).telInTellingTeVerwerkenBerichten(datumTot);
        verify(procesExtractieRepositoryService, times(1)).telInTellingTeVerwerkenGestarteProcessen(datumTot);
        verify(procesExtractieRepositoryService, times(1)).selecteerInTellingTeVerwerkenGestarteProcesInstanties(datumTot, 2);
        verify(procesExtractieRepositoryService, times(1)).updateIndicatieGestartGeteldProcesExtracties(Matchers.anyListOf(Long.class));
        verify(runtimeRepositoryService, times(1)).verwijderRuntime(RUNTIME_NAAM);
        Mockito.verifyNoMoreInteractions(runtimeRepositoryService);
        Mockito.verifyZeroInteractions(berichtTellingenRepositoryService);
    }

    @Test
    public void testWelProcesExtractiesBijwerkenBeeindigdMislukt() {

        final List<ProcesExtractie> procesExtractieGestartLijst = new ArrayList<>();
        final List<ProcesExtractie> procesExtractieBeeindigdLijst = new ArrayList<>();

        final ProcesExtractie procesExtractieEen = new ProcesExtractie();
        procesExtractieEen.setBerichtType(BERICHT_TYPE);
        procesExtractieEen.setEindDatum(eindDatum);
        procesExtractieEen.setIndicatieBeeindigdGeteld(false);
        procesExtractieEen.setIndicatieGestartGeteld(false);
        procesExtractieEen.setKanaal(KANAAL);
        procesExtractieEen.setProcesnaam(PROCES);
        procesExtractieEen.setProcesInstantieId(123L);
        procesExtractieEen.setStartDatum(startDatum);

        final ProcesExtractie procesExtractieTwee = new ProcesExtractie();
        procesExtractieTwee.setBerichtType(BERICHT_TYPE);
        procesExtractieTwee.setIndicatieBeeindigdGeteld(false);
        procesExtractieTwee.setIndicatieGestartGeteld(false);
        procesExtractieTwee.setKanaal(KANAAL);
        procesExtractieTwee.setProcesnaam(PROCES);
        procesExtractieTwee.setProcesInstantieId(321L);
        procesExtractieTwee.setStartDatum(startDatum);

        procesExtractieGestartLijst.add(procesExtractieEen);
        procesExtractieGestartLijst.add(procesExtractieTwee);
        procesExtractieBeeindigdLijst.add(procesExtractieEen);

        when(runtimeRepositoryService.voegRuntimeToe(RUNTIME_NAAM)).thenReturn(true);
        when(berichtRepositoryService.telInTellingTeVerwerkenBerichten(datumTot)).thenReturn(0L);
        when(procesExtractieRepositoryService.telInTellingTeVerwerkenGestarteProcessen(datumTot)).thenReturn(2L);
        when(procesExtractieRepositoryService.telInTellingTeVerwerkenBeeindigdeProcessen(datumTot)).thenReturn(1L);
        when(procesExtractieRepositoryService.selecteerInTellingTeVerwerkenGestarteProcesInstanties(datumTot, 2)).thenReturn(
            procesExtractieGestartLijst);
        when(procesExtractieRepositoryService.selecteerInTellingTeVerwerkenBeeindigdeProcesInstanties(datumTot, 1)).thenReturn(
            procesExtractieBeeindigdLijst);
        when(procesExtractieRepositoryService.updateIndicatieGestartGeteldProcesExtracties(Matchers.anyListOf(Long.class))).thenReturn(true);
        when(procesExtractieRepositoryService.updateIndicatieBeeindigdGeteldProcesExtracties(Matchers.anyListOf(Long.class))).thenReturn(
            false);

        service.werkLopendeTellingenBij(datumTot);

        verify(runtimeRepositoryService, times(1)).voegRuntimeToe(RUNTIME_NAAM);
        verify(berichtRepositoryService, times(1)).telInTellingTeVerwerkenBerichten(datumTot);
        verify(procesExtractieRepositoryService, times(1)).telInTellingTeVerwerkenGestarteProcessen(datumTot);
        verify(procesExtractieRepositoryService, times(1)).selecteerInTellingTeVerwerkenGestarteProcesInstanties(datumTot, 2);
        verify(procesExtractieRepositoryService, times(1)).updateIndicatieGestartGeteldProcesExtracties(Matchers.anyListOf(Long.class));
        verify(procesExtractieRepositoryService, times(1)).telInTellingTeVerwerkenBeeindigdeProcessen(datumTot);
        verify(procesExtractieRepositoryService, times(1)).selecteerInTellingTeVerwerkenBeeindigdeProcesInstanties(datumTot, 1);
        verify(procesExtractieRepositoryService, times(1)).updateIndicatieBeeindigdGeteldProcesExtracties(Matchers.anyListOf(Long.class));
        verify(procesTellingenRepositoryService, times(2)).save(Matchers.any(ProcesTelling.class));
        verify(runtimeRepositoryService, times(1)).verwijderRuntime(RUNTIME_NAAM);
        Mockito.verifyNoMoreInteractions(runtimeRepositoryService);
        Mockito.verifyZeroInteractions(berichtTellingenRepositoryService);
    }

    @Test
    public void testWelBerichtenExceptieBijwerken() {

        final List<Bericht> berichtenLijst = new ArrayList<>();
        final Bericht berichtEen = new Bericht();
        berichtEen.setId(1L);
        berichtEen.setKanaal(KANAAL);
        berichtEen.setNaam(BERICHT_TYPE);
        berichtEen.setTijdstip(datumTot);
        berichtEen.setIndicatieGeteld(false);
        berichtEen.setRichting(Bericht.Direction.INKOMEND.getCode());
        berichtenLijst.add(berichtEen);

        when(runtimeRepositoryService.voegRuntimeToe(RUNTIME_NAAM)).thenReturn(true);
        when(berichtRepositoryService.telInTellingTeVerwerkenBerichten(datumTot)).thenReturn(1L);
        when(berichtRepositoryService.selecteerInTellingTeVerwerkenBerichten(datumTot, 1)).thenReturn(berichtenLijst);
        when(berichtRepositoryService.updateIndicatieGeteldBerichten(Matchers.anyListOf(Long.class))).thenThrow(
            new HibernateException("Exceptie"));

        service.werkLopendeTellingenBij(datumTot);

        verify(runtimeRepositoryService, times(1)).voegRuntimeToe(RUNTIME_NAAM);
        verify(berichtRepositoryService, times(1)).telInTellingTeVerwerkenBerichten(datumTot);
        verify(berichtRepositoryService, times(1)).selecteerInTellingTeVerwerkenBerichten(datumTot, 1);
        verify(berichtTellingenRepositoryService, times(1)).haalBerichtTellingOp(BERICHT_TYPE, KANAAL, datumTot);
        verify(berichtTellingenRepositoryService, times(1)).save(Matchers.any(BerichtTelling.class));
        verify(runtimeRepositoryService, times(1)).verwijderRuntime(RUNTIME_NAAM);
        Mockito.verifyNoMoreInteractions(runtimeRepositoryService);
        Mockito.verifyZeroInteractions(procesTellingenRepositoryService);

    }

    @Test
    public void testWelProcesExtractiesExceptieBijwerkenGestart() {

        final List<ProcesExtractie> procesExtractieGestartLijst = new ArrayList<>();
        final List<ProcesExtractie> procesExtractieBeeindigdLijst = new ArrayList<>();

        final ProcesExtractie procesExtractieEen = new ProcesExtractie();
        procesExtractieEen.setBerichtType(BERICHT_TYPE);
        procesExtractieEen.setEindDatum(eindDatum);
        procesExtractieEen.setIndicatieBeeindigdGeteld(false);
        procesExtractieEen.setIndicatieGestartGeteld(false);
        procesExtractieEen.setKanaal(KANAAL);
        procesExtractieEen.setProcesnaam(PROCES);
        procesExtractieEen.setProcesInstantieId(123L);
        procesExtractieEen.setStartDatum(startDatum);

        final ProcesExtractie procesExtractieTwee = new ProcesExtractie();
        procesExtractieTwee.setBerichtType(BERICHT_TYPE);
        procesExtractieTwee.setIndicatieBeeindigdGeteld(false);
        procesExtractieTwee.setIndicatieGestartGeteld(false);
        procesExtractieTwee.setKanaal(KANAAL);
        procesExtractieTwee.setProcesnaam(PROCES);
        procesExtractieTwee.setProcesInstantieId(321L);
        procesExtractieTwee.setStartDatum(startDatum);

        procesExtractieGestartLijst.add(procesExtractieEen);
        procesExtractieGestartLijst.add(procesExtractieTwee);
        procesExtractieBeeindigdLijst.add(procesExtractieEen);

        when(runtimeRepositoryService.voegRuntimeToe(RUNTIME_NAAM)).thenReturn(true);
        when(berichtRepositoryService.telInTellingTeVerwerkenBerichten(datumTot)).thenReturn(0L);
        when(procesExtractieRepositoryService.telInTellingTeVerwerkenGestarteProcessen(datumTot)).thenReturn(2L);
        when(procesExtractieRepositoryService.telInTellingTeVerwerkenBeeindigdeProcessen(datumTot)).thenReturn(1L);
        when(procesExtractieRepositoryService.selecteerInTellingTeVerwerkenGestarteProcesInstanties(datumTot, 2)).thenReturn(
            procesExtractieGestartLijst);
        when(procesExtractieRepositoryService.selecteerInTellingTeVerwerkenBeeindigdeProcesInstanties(datumTot, 1)).thenReturn(
            procesExtractieBeeindigdLijst);
        when(procesExtractieRepositoryService.updateIndicatieGestartGeteldProcesExtracties(Matchers.anyListOf(Long.class))).thenThrow(
            new HibernateException("Exceptie"));

        service.werkLopendeTellingenBij(datumTot);

        verify(runtimeRepositoryService, times(1)).voegRuntimeToe(RUNTIME_NAAM);
        verify(berichtRepositoryService, times(1)).telInTellingTeVerwerkenBerichten(datumTot);
        verify(procesExtractieRepositoryService, times(1)).telInTellingTeVerwerkenGestarteProcessen(datumTot);
        verify(procesExtractieRepositoryService, times(1)).selecteerInTellingTeVerwerkenGestarteProcesInstanties(datumTot, 2);
        verify(procesExtractieRepositoryService, times(1)).updateIndicatieGestartGeteldProcesExtracties(Matchers.anyListOf(Long.class));
        verify(runtimeRepositoryService, times(1)).verwijderRuntime(RUNTIME_NAAM);
        Mockito.verifyNoMoreInteractions(runtimeRepositoryService);
        Mockito.verifyZeroInteractions(berichtTellingenRepositoryService);
    }

    @Test
    public void testWelProcesExtractiesExceptieBijwerkenBeeindigd() {

        final List<ProcesExtractie> procesExtractieGestartLijst = new ArrayList<>();
        final List<ProcesExtractie> procesExtractieBeeindigdLijst = new ArrayList<>();

        final ProcesExtractie procesExtractieEen = new ProcesExtractie();
        procesExtractieEen.setBerichtType(BERICHT_TYPE);
        procesExtractieEen.setEindDatum(eindDatum);
        procesExtractieEen.setIndicatieBeeindigdGeteld(false);
        procesExtractieEen.setIndicatieGestartGeteld(false);
        procesExtractieEen.setKanaal(KANAAL);
        procesExtractieEen.setProcesnaam(PROCES);
        procesExtractieEen.setProcesInstantieId(123L);
        procesExtractieEen.setStartDatum(startDatum);

        final ProcesExtractie procesExtractieTwee = new ProcesExtractie();
        procesExtractieTwee.setBerichtType(BERICHT_TYPE);
        procesExtractieTwee.setIndicatieBeeindigdGeteld(false);
        procesExtractieTwee.setIndicatieGestartGeteld(false);
        procesExtractieTwee.setKanaal(KANAAL);
        procesExtractieTwee.setProcesnaam(PROCES);
        procesExtractieTwee.setProcesInstantieId(321L);
        procesExtractieTwee.setStartDatum(startDatum);

        procesExtractieGestartLijst.add(procesExtractieEen);
        procesExtractieGestartLijst.add(procesExtractieTwee);
        procesExtractieBeeindigdLijst.add(procesExtractieEen);

        when(runtimeRepositoryService.voegRuntimeToe(RUNTIME_NAAM)).thenReturn(true);
        when(berichtRepositoryService.telInTellingTeVerwerkenBerichten(datumTot)).thenReturn(0L);
        when(procesExtractieRepositoryService.telInTellingTeVerwerkenGestarteProcessen(datumTot)).thenReturn(2L);
        when(procesExtractieRepositoryService.telInTellingTeVerwerkenBeeindigdeProcessen(datumTot)).thenReturn(1L);
        when(procesExtractieRepositoryService.selecteerInTellingTeVerwerkenGestarteProcesInstanties(datumTot, 2)).thenReturn(
            procesExtractieGestartLijst);
        when(procesExtractieRepositoryService.selecteerInTellingTeVerwerkenBeeindigdeProcesInstanties(datumTot, 1)).thenReturn(
            procesExtractieBeeindigdLijst);
        when(procesExtractieRepositoryService.updateIndicatieGestartGeteldProcesExtracties(Matchers.anyListOf(Long.class))).thenReturn(true);
        when(procesExtractieRepositoryService.updateIndicatieBeeindigdGeteldProcesExtracties(Matchers.anyListOf(Long.class))).thenThrow(
            new HibernateException("Exceptie"));

        service.werkLopendeTellingenBij(datumTot);

        verify(runtimeRepositoryService, times(1)).voegRuntimeToe(RUNTIME_NAAM);
        verify(berichtRepositoryService, times(1)).telInTellingTeVerwerkenBerichten(datumTot);
        verify(procesExtractieRepositoryService, times(1)).telInTellingTeVerwerkenGestarteProcessen(datumTot);
        verify(procesExtractieRepositoryService, times(1)).selecteerInTellingTeVerwerkenGestarteProcesInstanties(datumTot, 2);
        verify(procesExtractieRepositoryService, times(1)).updateIndicatieGestartGeteldProcesExtracties(Matchers.anyListOf(Long.class));
        verify(procesExtractieRepositoryService, times(1)).telInTellingTeVerwerkenBeeindigdeProcessen(datumTot);
        verify(procesExtractieRepositoryService, times(1)).selecteerInTellingTeVerwerkenBeeindigdeProcesInstanties(datumTot, 1);
        verify(procesExtractieRepositoryService, times(1)).updateIndicatieBeeindigdGeteldProcesExtracties(Matchers.anyListOf(Long.class));
        verify(procesTellingenRepositoryService, times(2)).save(Matchers.any(ProcesTelling.class));
        verify(runtimeRepositoryService, times(1)).verwijderRuntime(RUNTIME_NAAM);
        Mockito.verifyNoMoreInteractions(runtimeRepositoryService);
        Mockito.verifyZeroInteractions(berichtTellingenRepositoryService);
    }
}
