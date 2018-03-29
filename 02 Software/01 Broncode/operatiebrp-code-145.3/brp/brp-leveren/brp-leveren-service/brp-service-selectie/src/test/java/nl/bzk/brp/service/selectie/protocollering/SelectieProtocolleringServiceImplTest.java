/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.protocollering;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectierun;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectietaak;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SelectietaakHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.SelectietaakStatusHistorie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.HistorieVorm;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SelectietaakStatus;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.serialisatie.JsonStringSerializer;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.protocollering.domain.algemeen.LeveringPersoon;
import nl.bzk.brp.protocollering.domain.algemeen.ProtocolleringOpdracht;
import nl.bzk.brp.protocollering.service.algemeen.ProtocolleringService;
import nl.bzk.brp.service.selectie.schrijver.SelectieFileService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(MockitoJUnitRunner.class)
public class SelectieProtocolleringServiceImplTest {
    private static final JsonStringSerializer SERIALIZER = new JsonStringSerializer();

    @Mock
    private SelectieProtocolleringDataService selectieProtocolleringDataService;

    @Mock
    private SelectieFileService selectieFileService;

    @Mock
    private ProtocolleringService protocolleringService;

    @InjectMocks
    private SelectieProtocolleringServiceImpl selectieProtocolleringService;

    @Captor
    private ArgumentCaptor<ProtocolleringOpdracht> protocolleringOpdrachtArgumentCaptor;

    @Before
    public void voorTest() throws IOException {
        final Path tempDirectory = Files.createTempDirectory(null);
        Mockito.when(selectieFileService.getSelectietaakResultaatPath(Mockito.anyInt(), Mockito.anyInt())).thenReturn(tempDirectory);
        ReflectionTestUtils.setField(selectieProtocolleringService, "configPoolsize", 1);
        ReflectionTestUtils.setField(selectieProtocolleringService, "configFlushSize", 10);
        ReflectionTestUtils.setField(selectieProtocolleringService, "configVoortgangSize", 10);
        ReflectionTestUtils.setField(selectieProtocolleringService, "configMaxLooptijd", 1);
        Mockito.doAnswer(invocationOnMock -> {
            final Selectietaak selectietaak = ((Selectietaak) invocationOnMock.getArguments()[0]);
            final SelectietaakStatus selectietaakStatus = ((SelectietaakStatus) invocationOnMock.getArguments()[1]);
            if (selectietaak != null) {
                selectietaak.setStatus((short) selectietaakStatus.getId());
            }
            return selectietaak;
        })
                .when(selectieProtocolleringDataService).updateSelectietaakStatus(Mockito.any(), Mockito.any());
    }

    @Test
    public void testProtocolleer1_Peilmomenten_SelectietaakGevuld_HistorievormGeen() throws IOException {
        final Path tempFile = maakFileMetEntries(1);
        Mockito.when(selectieFileService.geefProtocolleringBestand()).thenReturn(tempFile);
        final Selectietaak selectietaak = maakSelectieTaak(SelectietaakStatus.TE_PROTOCOLLEREN);
        selectietaak.setDatumUitvoer(20140101);
        selectietaak.setPeilmomentMaterieelResultaat(20150101);
        final ZonedDateTime peilmomentFormeelResultaatZdt = ZonedDateTime.now(DatumUtil.BRP_ZONE_ID);
        selectietaak.setPeilmomentFormeelResultaat(DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(peilmomentFormeelResultaatZdt));
        selectietaak.getDienst().setHistorievormSelectie(HistorieVorm.GEEN.getId());
        List<Selectietaak> selectietaakList = Lists.newArrayList(
                selectietaak
        );
        Mockito.when(selectieProtocolleringDataService.selecteerTeProtocollerenSelectietaken()).thenReturn(selectietaakList);
        selectieProtocolleringService.start();
        Mockito.verify(selectieProtocolleringDataService, Mockito.times(1))
                .selecteerTeProtocollerenSelectietaken();
        Mockito.verify(selectieProtocolleringDataService, Mockito.times(2))
                .setVoortgang(Mockito.any(), Mockito.anyInt(), Mockito.anyInt());
        Mockito.verify(protocolleringService, Mockito.times(1))
                .protocolleer(protocolleringOpdrachtArgumentCaptor.capture());
        Mockito.verify(protocolleringService, Mockito.times(1))
                .protocolleerPersonenBijLeveringaantekening(Mockito.any(), Mockito.any());

        assertEquals(SelectietaakStatus.PROTOCOLLERING_UITGEVOERD, SelectietaakStatus.parseId(selectietaakList.get(0).getStatus().intValue()));
        final ProtocolleringOpdracht protocolleringOpdracht = protocolleringOpdrachtArgumentCaptor.getValue();
        assertEquals(20150101, protocolleringOpdracht.getDatumAanvangMaterielePeriodeResultaat().intValue());
        assertEquals(20150102, protocolleringOpdracht.getDatumEindeMaterielePeriodeResultaat().intValue());
        assertEquals(peilmomentFormeelResultaatZdt,  protocolleringOpdracht.getDatumTijdAanvangFormelePeriodeResultaat());
        assertEquals(peilmomentFormeelResultaatZdt, protocolleringOpdracht.getDatumTijdEindeFormelePeriodeResultaat());
    }

    @Test
    public void testProtocolleer1_PeilmomentenSelectietaakGevuld_HistorievormMaterieel() throws IOException {
        final Path tempFile = maakFileMetEntries(1);
        Mockito.when(selectieFileService.geefProtocolleringBestand()).thenReturn(tempFile);
        final Selectietaak selectietaak = maakSelectieTaak(SelectietaakStatus.TE_PROTOCOLLEREN);
        selectietaak.setDatumUitvoer(20140101);
        selectietaak.setPeilmomentMaterieelResultaat(20150101);
        final ZonedDateTime peilmomentFormeelResultaatZdt = ZonedDateTime.now(DatumUtil.BRP_ZONE_ID);
        selectietaak.setPeilmomentFormeelResultaat(DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(peilmomentFormeelResultaatZdt));
        selectietaak.getDienst().setHistorievormSelectie(HistorieVorm.MATERIEEL.getId());
        List<Selectietaak> selectietaakList = Lists.newArrayList(
                selectietaak
        );
        Mockito.when(selectieProtocolleringDataService.selecteerTeProtocollerenSelectietaken()).thenReturn(selectietaakList);
        selectieProtocolleringService.start();
        Mockito.verify(selectieProtocolleringDataService, Mockito.times(1))
                .selecteerTeProtocollerenSelectietaken();
        Mockito.verify(selectieProtocolleringDataService, Mockito.times(2))
                .setVoortgang(Mockito.any(), Mockito.anyInt(), Mockito.anyInt());
        Mockito.verify(protocolleringService, Mockito.times(1))
                .protocolleer(protocolleringOpdrachtArgumentCaptor.capture());
        Mockito.verify(protocolleringService, Mockito.times(1))
                .protocolleerPersonenBijLeveringaantekening(Mockito.any(), Mockito.any());

        assertEquals(SelectietaakStatus.PROTOCOLLERING_UITGEVOERD, SelectietaakStatus.parseId(selectietaakList.get(0).getStatus().intValue()));
        final ProtocolleringOpdracht protocolleringOpdracht = protocolleringOpdrachtArgumentCaptor.getValue();
        assertNull(protocolleringOpdracht.getDatumAanvangMaterielePeriodeResultaat());
        assertEquals(20150102, protocolleringOpdracht.getDatumEindeMaterielePeriodeResultaat().intValue());
        assertEquals(peilmomentFormeelResultaatZdt,  protocolleringOpdracht.getDatumTijdAanvangFormelePeriodeResultaat());
        assertEquals(peilmomentFormeelResultaatZdt, protocolleringOpdracht.getDatumTijdEindeFormelePeriodeResultaat());
    }

    @Test
    public void testProtocolleer1_PeilmomentenSelectietaakGevuld_HistorievormMaterieelFormeel() throws IOException {
        final Path tempFile = maakFileMetEntries(1);
        Mockito.when(selectieFileService.geefProtocolleringBestand()).thenReturn(tempFile);
        final Selectietaak selectietaak = maakSelectieTaak(SelectietaakStatus.TE_PROTOCOLLEREN);
        selectietaak.setDatumUitvoer(20140101);
        selectietaak.setPeilmomentMaterieelResultaat(20150101);
        final ZonedDateTime peilmomentFormeelResultaatZdt = ZonedDateTime.now(DatumUtil.BRP_ZONE_ID);
        selectietaak.setPeilmomentFormeelResultaat(DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(peilmomentFormeelResultaatZdt));
        selectietaak.getDienst().setHistorievormSelectie(HistorieVorm.MATERIEEL_FORMEEL.getId());
        List<Selectietaak> selectietaakList = Lists.newArrayList(
                selectietaak
        );
        Mockito.when(selectieProtocolleringDataService.selecteerTeProtocollerenSelectietaken()).thenReturn(selectietaakList);
        selectieProtocolleringService.start();
        Mockito.verify(selectieProtocolleringDataService, Mockito.times(1))
                .selecteerTeProtocollerenSelectietaken();
        Mockito.verify(selectieProtocolleringDataService, Mockito.times(2))
                .setVoortgang(Mockito.any(), Mockito.anyInt(), Mockito.anyInt());
        Mockito.verify(protocolleringService, Mockito.times(1))
                .protocolleer(protocolleringOpdrachtArgumentCaptor.capture());
        Mockito.verify(protocolleringService, Mockito.times(1))
                .protocolleerPersonenBijLeveringaantekening(Mockito.any(), Mockito.any());

        assertEquals(SelectietaakStatus.PROTOCOLLERING_UITGEVOERD, SelectietaakStatus.parseId(selectietaakList.get(0).getStatus().intValue()));
        final ProtocolleringOpdracht protocolleringOpdracht = protocolleringOpdrachtArgumentCaptor.getValue();
        assertNull(protocolleringOpdracht.getDatumAanvangMaterielePeriodeResultaat());
        assertEquals(20150102, protocolleringOpdracht.getDatumEindeMaterielePeriodeResultaat().intValue());
        assertNull(protocolleringOpdracht.getDatumTijdAanvangFormelePeriodeResultaat());
        assertEquals(peilmomentFormeelResultaatZdt, protocolleringOpdracht.getDatumTijdEindeFormelePeriodeResultaat());
    }

    @Test
    public void testProtocolleer1_PeilmomentenSelectietaakNull() throws IOException {
        final Path tempFile = maakFileMetEntries(1);
        Mockito.when(selectieFileService.geefProtocolleringBestand()).thenReturn(tempFile);
        final Selectietaak selectietaak = maakSelectieTaak(SelectietaakStatus.TE_PROTOCOLLEREN);
        selectietaak.setDatumUitvoer(20140101);
        selectietaak.setPeilmomentMaterieelResultaat(null);
        selectietaak.setPeilmomentFormeelResultaat(null);
        List<Selectietaak> selectietaakList = Lists.newArrayList(
                selectietaak
        );
        Mockito.when(selectieProtocolleringDataService.selecteerTeProtocollerenSelectietaken()).thenReturn(selectietaakList);
        selectieProtocolleringService.start();
        Mockito.verify(selectieProtocolleringDataService, Mockito.times(1))
                .selecteerTeProtocollerenSelectietaken();
        Mockito.verify(selectieProtocolleringDataService, Mockito.times(2))
                .setVoortgang(Mockito.any(), Mockito.anyInt(), Mockito.anyInt());
        Mockito.verify(protocolleringService, Mockito.times(1))
                .protocolleer(protocolleringOpdrachtArgumentCaptor.capture());
        Mockito.verify(protocolleringService, Mockito.times(1))
                .protocolleerPersonenBijLeveringaantekening(Mockito.any(), Mockito.any());

        assertEquals(SelectietaakStatus.PROTOCOLLERING_UITGEVOERD, SelectietaakStatus.parseId(selectietaakList.get(0).getStatus().intValue()));
        final ProtocolleringOpdracht protocolleringOpdracht = protocolleringOpdrachtArgumentCaptor.getValue();
        assertNull(protocolleringOpdracht.getDatumAanvangMaterielePeriodeResultaat());
        assertNull(protocolleringOpdracht.getDatumEindeMaterielePeriodeResultaat());
        assertNull(protocolleringOpdracht.getDatumTijdAanvangFormelePeriodeResultaat());
        assertEquals(DatumUtil.vanIntegerNaarZonedDateTime(20140101), protocolleringOpdracht.getDatumTijdEindeFormelePeriodeResultaat());
    }


    @Test
    public void testProtocolleer10() throws IOException {
        final Path tempFile = maakFileMetEntries(10);
        Mockito.when(selectieFileService.geefProtocolleringBestand()).thenReturn(tempFile);

        List<Selectietaak> selectietaakList = Lists.newArrayList(
                maakSelectieTaak(SelectietaakStatus.TE_PROTOCOLLEREN)
        );
        Mockito.when(selectieProtocolleringDataService.selecteerTeProtocollerenSelectietaken()).thenReturn(selectietaakList);
        selectieProtocolleringService.start();
        Mockito.verify(selectieProtocolleringDataService, Mockito.times(1))
                .selecteerTeProtocollerenSelectietaken();
        Mockito.verify(selectieProtocolleringDataService, Mockito.times(2))
                .setVoortgang(Mockito.any(), Mockito.anyInt(), Mockito.anyInt());
        Mockito.verify(protocolleringService, Mockito.times(1))
                .protocolleer(Mockito.any());
        Mockito.verify(protocolleringService, Mockito.times(1))
                .protocolleerPersonenBijLeveringaantekening(Mockito.any(), Mockito.any());

        assertEquals(SelectietaakStatus.PROTOCOLLERING_UITGEVOERD, SelectietaakStatus.parseId(selectietaakList.get(0).getStatus().intValue()));
    }

    @Test
    public void testProtocolleer100() throws IOException {
        final Path tempFile = maakFileMetEntries(100);
        Mockito.when(selectieFileService.geefProtocolleringBestand()).thenReturn(tempFile);

        List<Selectietaak> selectietaakList = Lists.newArrayList(
                maakSelectieTaak(SelectietaakStatus.TE_PROTOCOLLEREN)
        );
        Mockito.when(selectieProtocolleringDataService.selecteerTeProtocollerenSelectietaken()).thenReturn(selectietaakList);
        selectieProtocolleringService.start();
        Mockito.verify(selectieProtocolleringDataService, Mockito.times(1))
                .selecteerTeProtocollerenSelectietaken();
        Mockito.verify(selectieProtocolleringDataService, Mockito.times(11))
                .setVoortgang(Mockito.any(), Mockito.anyInt(), Mockito.anyInt());
        Mockito.verify(protocolleringService, Mockito.times(1))
                .protocolleer(Mockito.any());
        Mockito.verify(protocolleringService, Mockito.times(10))
                .protocolleerPersonenBijLeveringaantekening(Mockito.any(), Mockito.any());

        assertEquals(SelectietaakStatus.PROTOCOLLERING_UITGEVOERD, SelectietaakStatus.parseId(selectietaakList.get(0).getStatus().intValue()));
    }

    @Test
    public void testProtocolleerMeerdere() throws IOException {
        List<Selectietaak> selectietaakList = Lists.newArrayList(
                maakSelectieTaak(SelectietaakStatus.TE_PROTOCOLLEREN),
                maakSelectieTaak(SelectietaakStatus.TE_PROTOCOLLEREN),
                maakSelectieTaak(SelectietaakStatus.TE_PROTOCOLLEREN),
                maakSelectieTaak(SelectietaakStatus.TE_PROTOCOLLEREN),
                maakSelectieTaak(SelectietaakStatus.TE_PROTOCOLLEREN)
        );
        Mockito.when(selectieFileService.geefProtocolleringBestand()).thenReturn(
                maakFileMetEntries(1),
                maakFileMetEntries(10),
                maakFileMetEntries(100),
                maakFileMetEntries(1000),
                maakFileMetEntries(10000))
        ;

        Mockito.when(selectieProtocolleringDataService.selecteerTeProtocollerenSelectietaken()).thenReturn(selectietaakList);

        selectieProtocolleringService.start();

        Mockito.verify(selectieFileService, Mockito.times(5)).geefProtocolleringBestand();
        Mockito.verify(selectieProtocolleringDataService, Mockito.times(1))
                .selecteerTeProtocollerenSelectietaken();
        Mockito.verify(selectieProtocolleringDataService, Mockito.times(1112 + 5))
                .setVoortgang(Mockito.any(), Mockito.anyInt(), Mockito.anyInt());
        Mockito.verify(protocolleringService, Mockito.times(5))
                .protocolleer(Mockito.any());
        Mockito.verify(protocolleringService, Mockito.times(1112))
                .protocolleerPersonenBijLeveringaantekening(Mockito.any(), Mockito.any());

        selectietaakList.forEach(selectietaak -> {
            assertEquals(SelectietaakStatus.PROTOCOLLERING_UITGEVOERD, SelectietaakStatus.parseId(selectietaak.getStatus().intValue()));
        });
    }

    @Test
    public void testGeenTeProtocollerenTaken() {
        Mockito.when(selectieProtocolleringDataService.selecteerTeProtocollerenSelectietaken()).thenReturn(Collections.emptyList());
        selectieProtocolleringService.start();
        Mockito.verify(selectieProtocolleringDataService, Mockito.times(1)).selecteerTeProtocollerenSelectietaken();
        Mockito.verifyNoMoreInteractions(selectieProtocolleringDataService);
        Mockito.verifyZeroInteractions(protocolleringService, selectieFileService);
    }

    /**
     * Cornercase: protocolleringbestand bestaat niet, maar is leeg.
     */
    @Test
    public void testProtocolleerbestandBestaatNiet() throws IOException {
        final List<Selectietaak> selectietaakList = Lists.newArrayList(
                maakSelectieTaak(SelectietaakStatus.TE_PROTOCOLLEREN)
        );
        Mockito.when(selectieFileService.geefProtocolleringBestand()).thenReturn(Paths.get("blabla"));
        Mockito.when(selectieProtocolleringDataService.selecteerTeProtocollerenSelectietaken()).thenReturn(selectietaakList);

        selectieProtocolleringService.start();

        Mockito.verify(selectieProtocolleringDataService, Mockito.times(1)).selecteerTeProtocollerenSelectietaken();

        assertEquals(SelectietaakStatus.TE_PROTOCOLLEREN_BESTAND_NIET_GEVONDEN,
                SelectietaakStatus.parseId(selectietaakList.get(0).getStatus().intValue()));
    }


    /**
     * Cornercase: protocolleringbestand bestaat, maar is leeg.
     */
    @Test
    public void testProtocolleerbestandAanwezigMaarLeeg() throws IOException {
        final Path tempFile = Files.createTempFile(null, null);
        final List<Selectietaak> selectietaakList = Lists.newArrayList(
                maakSelectieTaak(SelectietaakStatus.TE_PROTOCOLLEREN)
        );
        Mockito.when(selectieFileService.geefProtocolleringBestand()).thenReturn(tempFile);
        Mockito.when(selectieProtocolleringDataService.selecteerTeProtocollerenSelectietaken()).thenReturn(selectietaakList);
        selectieProtocolleringService.start();
        Mockito.verify(selectieProtocolleringDataService, Mockito.times(1)).selecteerTeProtocollerenSelectietaken();

        assertEquals(SelectietaakStatus.PROTOCOLLERING_UITGEVOERD, SelectietaakStatus.parseId(selectietaakList.get(0).getStatus().intValue()));
    }


    @Test
    public void testStop() throws IOException {

        final Path tempFile = maakFileMetEntries(100);
        final List<Selectietaak> selectietaakList = Lists.newArrayList(
                maakSelectieTaak(SelectietaakStatus.TE_PROTOCOLLEREN)
        );

        Mockito.when(selectieFileService.geefProtocolleringBestand()).thenReturn(tempFile);
        Mockito.when(selectieProtocolleringDataService.selecteerTeProtocollerenSelectietaken()).thenReturn(selectietaakList);
        Mockito.doAnswer(invocationOnMock -> {
            selectieProtocolleringService.stop();
            return tempFile;
        })
                .when(selectieFileService).geefProtocolleringBestand();

        selectieProtocolleringService.start();

        assertEquals(SelectietaakStatus.PROTOCOLLERING_AFGEBROKEN,
                SelectietaakStatus.parseId(selectietaakList.get(0).getStatus().intValue()));
        Assert.assertFalse(selectieProtocolleringService.isRunning());
        Mockito.verify(selectieProtocolleringDataService, Mockito.times(1)).setVoortgang(Mockito.any(), Mockito.anyInt(), Mockito.anyInt());

    }

    @Test
    public void testPauze() throws IOException {

        final Path tempFile = maakFileMetEntries(100);
        final List<Selectietaak> selectietaakList = Lists.newArrayList(
                maakSelectieTaak(SelectietaakStatus.TE_PROTOCOLLEREN)
        );

        Mockito.when(selectieFileService.geefProtocolleringBestand()).thenReturn(tempFile);
        Mockito.when(selectieProtocolleringDataService.selecteerTeProtocollerenSelectietaken()).thenReturn(selectietaakList);
        Mockito.doAnswer(invocationOnMock -> {
            selectieProtocolleringService.pauzeerVerwerking(selectietaakList.get(0).getId());
            return tempFile;
        })
                .when(selectieFileService).geefProtocolleringBestand();

        selectieProtocolleringService.start();

        assertEquals(SelectietaakStatus.UITVOERING_AFGEBROKEN,
                SelectietaakStatus.parseId(selectietaakList.get(0).getStatus().intValue()));
        Assert.assertFalse(selectieProtocolleringService.isRunning());
        Mockito.verify(selectieProtocolleringDataService, Mockito.times(1)).setVoortgang(Mockito.any(), Mockito.anyInt(), Mockito.anyInt());
    }

    @Test
    public void testIOFoutTijdensProtocolleren() throws IOException {

        final Path tempFile = maakFileMetEntries(100);
        final List<Selectietaak> selectietaakList = Lists.newArrayList(
                maakSelectieTaak(SelectietaakStatus.TE_PROTOCOLLEREN)
        );

        Mockito.when(selectieFileService.geefProtocolleringBestand()).thenReturn(tempFile);
        Mockito.when(selectieProtocolleringDataService.selecteerTeProtocollerenSelectietaken()).thenReturn(selectietaakList);
        Mockito.doThrow(new IllegalArgumentException()).when(selectieProtocolleringDataService)
                .updateSelectietaakStatus(selectietaakList.get(0), SelectietaakStatus.PROTOCOLLERING_IN_UITVOERING);

        selectieProtocolleringService.start();

        assertEquals(SelectietaakStatus.PROTOCOLLERING_MISLUKT,
                SelectietaakStatus.parseId(selectietaakList.get(0).getStatus().intValue()));
        Mockito.verify(selectieProtocolleringDataService, Mockito.times(1)).selecteerTeProtocollerenSelectietaken();
    }

    @Test
    public void testFoutTijdensDeserialize() throws IOException {

        final Path tempFile = Files.createTempFile(null, null);
        Files.write(tempFile, Lists.newArrayList("onzin"));
        final List<Selectietaak> selectietaakList = Lists.newArrayList(
                maakSelectieTaak(SelectietaakStatus.TE_PROTOCOLLEREN)
        );

        Mockito.when(selectieFileService.geefProtocolleringBestand()).thenReturn(tempFile);
        Mockito.when(selectieProtocolleringDataService.selecteerTeProtocollerenSelectietaken()).thenReturn(selectietaakList);
        Mockito.doAnswer(invocationOnMock -> {
            Files.delete(tempFile);
            return null;
        })
                .when(selectieProtocolleringDataService).setVoortgang(Mockito.any(), Mockito.anyInt(), Mockito.anyInt());

        selectieProtocolleringService.start();

        assertEquals(SelectietaakStatus.PROTOCOLLERING_MISLUKT,
                SelectietaakStatus.parseId(selectietaakList.get(0).getStatus().intValue()));
        Mockito.verify(selectieProtocolleringDataService, Mockito.times(1)).selecteerTeProtocollerenSelectietaken();
    }

    @Test
    public void testFoutTijdensBepalenTotaalRegels() throws IOException {

        final Path tempFile = Files.createTempDirectory(null);
        final List<Selectietaak> selectietaakList = Lists.newArrayList(
                maakSelectieTaak(SelectietaakStatus.TE_PROTOCOLLEREN)
        );

        Mockito.when(selectieFileService.geefProtocolleringBestand()).thenReturn(tempFile);
        Mockito.when(selectieProtocolleringDataService.selecteerTeProtocollerenSelectietaken()).thenReturn(selectietaakList);
        Mockito.doAnswer(invocationOnMock -> {
            Files.delete(tempFile);
            return null;
        })
                .when(selectieProtocolleringDataService).setVoortgang(Mockito.any(), Mockito.anyInt(), Mockito.anyInt());

        selectieProtocolleringService.start();

        assertEquals(SelectietaakStatus.PROTOCOLLERING_MISLUKT,
                SelectietaakStatus.parseId(selectietaakList.get(0).getStatus().intValue()));
        Mockito.verify(selectieProtocolleringDataService, Mockito.times(1)).selecteerTeProtocollerenSelectietaken();
    }

    private Selectietaak maakSelectieTaak(final SelectietaakStatus status) {
        final Dienst dienst = AutAutUtil.zoekDienst(TestAutorisaties.metSoortDienst(SoortDienst.SELECTIE), SoortDienst.SELECTIE);
        dienst.setSoortSelectie(SoortSelectie.STANDAARD_SELECTIE.getId());
        final Autorisatiebundel autorisatiebundel = new Autorisatiebundel(
                TestAutorisaties.maak(Rol.AFNEMER, dienst), dienst);

        final Timestamp nuTijd = DatumUtil.vanZonedDateTimeNaarSqlTimeStamp(DatumUtil.nuAlsZonedDateTime());
        final Selectietaak selectietaak = new Selectietaak(dienst, autorisatiebundel.getToegangLeveringsautorisatie(), 1);
        selectietaak.setStatus((short) SelectietaakStatus.UITVOERBAAR.getId());
        selectietaak.setDatumPlanning(DatumUtil.vandaag());
        selectietaak.setDienst(autorisatiebundel.getDienst());
        selectietaak.setStatus((short) status.getId());
        selectietaak.setId(new Random().nextInt(Integer.MAX_VALUE));
        selectietaak.setPeilmomentMaterieelResultaat(20010101);
        selectietaak.setPeilmomentFormeelResultaat(new Timestamp(System.currentTimeMillis()));
        selectietaak.setUitgevoerdIn(new Selectierun(new Timestamp(System.currentTimeMillis())));

        final SelectietaakHistorie selectietaakHistorie = new SelectietaakHistorie(selectietaak);
        selectietaakHistorie.setDatumTijdRegistratie(nuTijd);
        selectietaak.addSelectietaakHistorieSet(selectietaakHistorie);

        final SelectietaakStatusHistorie selectietaakStatusHistorie = new SelectietaakStatusHistorie(selectietaak);
        selectietaakStatusHistorie.setDatumTijdRegistratie(nuTijd);
        selectietaakStatusHistorie.setStatus(selectietaak.getStatus());
        selectietaak.addSelectietaakStatusHistorieSet(selectietaakStatusHistorie);
        return selectietaak;
    }


    private Path maakFileMetEntries(int aantalEntries) throws IOException {
        final Path tempFile = Files.createTempFile(null, null);
        List<String> regels = IntStream.range(0, aantalEntries)
                .mapToObj(value -> new LeveringPersoon((long) value, DatumUtil.nuAlsZonedDateTime(), DatumUtil.vandaag()))
                .map(SERIALIZER::serialiseerNaarString)
                .collect(Collectors.toList());

        Files.write(tempFile, regels);
        return tempFile;
    }

}
