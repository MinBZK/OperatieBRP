/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.synchronisatie.stamgegeven;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import com.google.common.collect.Iterables;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.archivering.domain.algemeen.ArchiveringOpdracht;
import nl.bzk.brp.archivering.service.algemeen.ArchiefService;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.SynchroniseerStamgegevenBericht;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.service.algemeen.StapException;
import nl.bzk.brp.service.synchronisatie.SynchronisatieVerzoek;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SynchroniseerStamgegevenServiceImplTest {

    private static final String ZENDENDE_PARTIJ_CODE = "1";
    private static final String REF_NR = "refnr";
    private static final Short ZENDENDE_PARTIJ_ID = 1;
    private static final String XML_BERICHT = "xml bericht";
    private static final String XML_RESULTAAT = "xml resultaat";

    private static final ZonedDateTime TS_VERZENDING = ZonedDateTime.of(2017, 1, 1, 12, 0, 0, 0, ZoneId.systemDefault());

    @InjectMocks
    private SynchroniseerStamgegevenServiceImpl service;

    @Mock
    private ArchiefService archiveringService;
    @Mock
    private SynchroniseerStamgegeven.BepaalStamgegevenService bepaalStamgegevenService;
    @Mock
    private SynchroniseerStamgegevenBerichtFactory maakAntwoordBerichtService;

    @Captor
    private ArgumentCaptor<ArchiveringOpdracht> archiveringOpdrachtArgumentCaptor;

    @Before
    public void before() {
        BrpNu.set();
    }

    @Test
    public void testHappyflow() throws Exception {
        final SynchronisatieVerzoek verzoek = maakVerzoek();

        final BepaalStamgegevenResultaat resultaat = new BepaalStamgegevenResultaat(verzoek);
        Leveringsautorisatie la = TestAutorisaties.metSoortDienst(SoortDienst.SYNCHRONISATIE_STAMGEGEVEN);
        final Partij zendendePartij = new Partij("dummy", "000001");
        zendendePartij.setId((short) 1);
        ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(new PartijRol(zendendePartij, Rol.AFNEMER), la);
        final Autorisatiebundel autorisatieBundel = new Autorisatiebundel(tla,
                Iterables.getOnlyElement(la.getDienstbundelSet()).getDienstSet().iterator().next());
        resultaat.setAutorisatiebundel(autorisatieBundel);
        resultaat.setBrpPartij(TestPartijBuilder.maakBuilder().metId(Short.MAX_VALUE).metCode("000000").build());

        //@formatter:on
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
                .metStuurgegevens()
                .metReferentienummer(REF_NR)
                .metTijdstipVerzending(DatumUtil.nuAlsZonedDateTime())
                .eindeStuurgegevens()
                .build();

        final SynchroniseerStamgegevenBericht bericht = new SynchroniseerStamgegevenBericht(basisBerichtGegevens, null);
        //@formatter:off

        //mocks
        when(bepaalStamgegevenService.maakResultaat(verzoek)).thenReturn(resultaat);
        when(maakAntwoordBerichtService.maakBericht(resultaat)).thenReturn(bericht);
        final SynchroniseerStamgegevenCallback<String> callback = maakCallBack();

        service.maakResponse(verzoek, callback);

        final InOrder inOrder = Mockito.inOrder(
                bepaalStamgegevenService,
                maakAntwoordBerichtService,
                archiveringService
        );


        inOrder.verify(archiveringService, times(2)).archiveer(archiveringOpdrachtArgumentCaptor.capture());
        inOrder.verifyNoMoreInteractions();
        assertArchiveringCorrect(verzoek, autorisatieBundel, callback, bericht, resultaat);
    }



    @Test
    public void testHappyflowGeenAutorisatieBundel() throws Exception {
        final SynchronisatieVerzoek verzoek = maakVerzoek();
        final BepaalStamgegevenResultaat resultaat = new BepaalStamgegevenResultaat(verzoek);
        final Autorisatiebundel autorisatieBundel = null;
        resultaat.setAutorisatiebundel(autorisatieBundel);
        resultaat.setBrpPartij(TestPartijBuilder.maakBuilder().metId(Short.MAX_VALUE).metCode("000000").build());
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder().metStuurgegevens().metReferentienummer(REF_NR)
                        .metTijdstipVerzending(DatumUtil.nuAlsZonedDateTime())
                        .eindeStuurgegevens().build();
        final SynchroniseerStamgegevenBericht bericht = new SynchroniseerStamgegevenBericht(basisBerichtGegevens, null);
        final SynchroniseerStamgegevenCallback<String> callback = maakCallBack();

        when(bepaalStamgegevenService.maakResultaat(verzoek)).thenReturn(resultaat);
        when(maakAntwoordBerichtService.maakBericht(resultaat)).thenReturn(bericht);

        service.maakResponse(verzoek, callback);

        final InOrder inOrder = Mockito.inOrder(
                bepaalStamgegevenService,
                maakAntwoordBerichtService,
                archiveringService
        );
        inOrder.verify(archiveringService, times(2)).archiveer(archiveringOpdrachtArgumentCaptor.capture());
        inOrder.verifyNoMoreInteractions();
        assertArchiveringCorrect(verzoek, autorisatieBundel, callback, bericht, resultaat);
    }

    @Test
    public void testHappyflowMeldingListGevuld() throws Exception {
        final SynchronisatieVerzoek verzoek = maakVerzoek();
        final BepaalStamgegevenResultaat resultaat = new BepaalStamgegevenResultaat(verzoek);
        final Autorisatiebundel autorisatieBundel = maakAutorisatieBundel();
        resultaat.setAutorisatiebundel(autorisatieBundel);
        resultaat.setBrpPartij(TestPartijBuilder.maakBuilder().metId(Short.MAX_VALUE).metCode("000000").build());
        resultaat.getMeldingList().add(new Melding(Regel.R1244));
        final SynchroniseerStamgegevenBericht bericht = new SynchroniseerStamgegevenBericht(
                BasisBerichtGegevens.builder().metStuurgegevens().metReferentienummer(REF_NR)
                        .metTijdstipVerzending(DatumUtil.nuAlsZonedDateTime())
                        .eindeStuurgegevens().build(), null);
        final SynchroniseerStamgegevenCallback<String> callback = maakCallBack();

        when(bepaalStamgegevenService.maakResultaat(verzoek)).thenReturn(resultaat);
        when(maakAntwoordBerichtService.maakBericht(resultaat)).thenReturn(bericht);

        service.maakResponse(verzoek, maakCallBack());

        final InOrder inOrder = Mockito.inOrder(
                bepaalStamgegevenService,
                maakAntwoordBerichtService,
                archiveringService
        );
        inOrder.verify(archiveringService, times(2)).archiveer(archiveringOpdrachtArgumentCaptor.capture());
        inOrder.verifyNoMoreInteractions();
        assertArchiveringCorrect(verzoek, autorisatieBundel, callback, bericht, resultaat);
    }

    @SuppressWarnings({"unchecked"})
    @Test(expected = RuntimeException.class)
    public void testFoutBijMaakAntwoordBericht() throws StapException {

        final SynchronisatieVerzoek verzoek = new SynchronisatieVerzoek();
        final BepaalStamgegevenResultaat resultaat = new BepaalStamgegevenResultaat(verzoek);
        when(bepaalStamgegevenService.maakResultaat(verzoek)).thenReturn(resultaat);
        when(maakAntwoordBerichtService.maakBericht(resultaat)).thenThrow(RuntimeException.class);

        service.maakResponse(verzoek, maakCallBack());
    }

    private void assertArchiveringCorrect(final SynchronisatieVerzoek verzoek,
                                          final Autorisatiebundel autorisatieBundel,
                                          final SynchroniseerStamgegevenCallback<String> callback,
                                          final SynchroniseerStamgegevenBericht bericht,
                                          final BepaalStamgegevenResultaat resultaat) {

        ArchiveringOpdracht archiveringOpdrachtIngaand = archiveringOpdrachtArgumentCaptor.getAllValues().get(0);
        Assert.assertEquals(verzoek.getXmlBericht(), archiveringOpdrachtIngaand.getData());
        Assert.assertEquals(verzoek.getStuurgegevens().getReferentieNummer(), archiveringOpdrachtIngaand.getReferentienummer());
        Assert.assertEquals(verzoek.getStuurgegevens().getZendendSysteem(), archiveringOpdrachtIngaand.getZendendeSysteem());
        Assert.assertEquals(SoortBericht.LVG_SYN_GEEF_SYNCHRONISATIE_STAMGEGEVEN, archiveringOpdrachtIngaand.getSoortBericht());
        if(autorisatieBundel != null) {
            Assert.assertEquals(autorisatieBundel.getDienst().getId(), archiveringOpdrachtIngaand.getDienstId());
            Assert.assertEquals(autorisatieBundel.getLeveringsautorisatieId(), archiveringOpdrachtIngaand.getLeveringsAutorisatieId().intValue());
            Assert.assertEquals(autorisatieBundel.getPartij().getId(), archiveringOpdrachtIngaand.getZendendePartijId());
        }
        else {
            Assert.assertNull(archiveringOpdrachtIngaand.getDienstId());
            Assert.assertNull(archiveringOpdrachtIngaand.getLeveringsAutorisatieId());
            Assert.assertNull(archiveringOpdrachtIngaand.getZendendePartijId());
        }
        Assert.assertEquals(verzoek.getStuurgegevens().getTijdstipVerzending(), archiveringOpdrachtIngaand.getTijdstipVerzending());
        Assert.assertNotNull(archiveringOpdrachtIngaand.getTijdstipOntvangst());

        ArchiveringOpdracht archiveringOpdrachtUitgaand = archiveringOpdrachtArgumentCaptor.getAllValues().get(1);
        Assert.assertEquals(callback.getResultaat(), archiveringOpdrachtUitgaand.getData());
        Assert.assertNotNull(archiveringOpdrachtUitgaand.getTijdstipVerzending());
        Assert.assertEquals(bericht.getBasisBerichtGegevens().getStuurgegevens().getReferentienummer(), archiveringOpdrachtUitgaand.getReferentienummer());
        Assert.assertEquals(verzoek.getStuurgegevens().getReferentieNummer(), archiveringOpdrachtUitgaand.getCrossReferentienummer());
        Assert.assertEquals(autorisatieBundel == null ? null : autorisatieBundel.getToegangLeveringsautorisatie().getLeveringsautorisatie().getId(), archiveringOpdrachtUitgaand.getLeveringsAutorisatieId());
        Assert.assertEquals(resultaat.getMeldingList().isEmpty() ? VerwerkingsResultaat.GESLAAGD : VerwerkingsResultaat.FOUTIEF, archiveringOpdrachtUitgaand.getVerwerkingsresultaat());
        Assert.assertEquals(resultaat.getBrpPartij().getId(), archiveringOpdrachtUitgaand.getZendendePartijId());
        Assert.assertEquals(BasisBerichtGegevens.BRP_SYSTEEM, archiveringOpdrachtUitgaand.getZendendeSysteem());
        if(autorisatieBundel != null) {
            Assert.assertEquals(ZENDENDE_PARTIJ_ID, archiveringOpdrachtUitgaand.getOntvangendePartijId());
        }
        Assert.assertEquals(SoortBericht.LVG_SYN_GEEF_SYNCHRONISATIE_STAMGEGEVEN_R, archiveringOpdrachtUitgaand.getSoortBericht());
    }

    private SynchronisatieVerzoek maakVerzoek() {
        final SynchronisatieVerzoek verzoek = new SynchronisatieVerzoek();
        verzoek.getStuurgegevens().setZendendePartijCode(ZENDENDE_PARTIJ_CODE);
        verzoek.getStuurgegevens().setReferentieNummer(REF_NR);
        verzoek.setXmlBericht(XML_BERICHT);
        verzoek.getStuurgegevens().setTijdstipVerzending(TS_VERZENDING);
        return verzoek;
    }

    private Autorisatiebundel maakAutorisatieBundel() {
        final PartijRol partijRol = new PartijRol(TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build(), Rol.AFNEMER);
        final SoortDienst soortDienst = SoortDienst.PLAATSING_AFNEMERINDICATIE;
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        return new Autorisatiebundel(tla, AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst));
    }

    private SynchroniseerStamgegevenCallback<String> maakCallBack() {
        return new SynchroniseerStamgegevenCallback<String>() {
            @Override
            public void verwerkResultaat(final SynchroniseerStamgegevenBericht resultaat) {

            }

            public String getResultaat() {
                return XML_RESULTAAT;
            }
        };
    }
}
