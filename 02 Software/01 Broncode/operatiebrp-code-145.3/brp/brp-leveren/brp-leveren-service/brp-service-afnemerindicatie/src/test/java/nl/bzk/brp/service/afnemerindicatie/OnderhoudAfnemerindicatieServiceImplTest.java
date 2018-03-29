/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.afnemerindicatie;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortMelding;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.brp.archivering.domain.algemeen.ArchiveringOpdracht;
import nl.bzk.brp.archivering.service.algemeen.ArchiefService;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.Melding;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.OnderhoudAfnemerindicatieAntwoordBericht;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * OnderhoudAfnemerindicatieServiceImplTest.
 */
@RunWith(MockitoJUnitRunner.class)
public class OnderhoudAfnemerindicatieServiceImplTest {

    private static final String ZENDENDE_PARTIJ_CODE = "1";
    private static final Short ZENDENDE_PARTIJ_ID = 1;
    private static final ZonedDateTime TS_VERZENDING = ZonedDateTime.of(2017, 1, 1, 12, 0, 0, 0, ZoneId.systemDefault());

    @InjectMocks
    OnderhoudAfnemerindicatieServiceImpl onderhoudAfnemerindicatieService;

    @Mock
    private ArchiefService archiveringService;
    @Mock
    private OnderhoudAfnemerindicatieSynchroonBerichtFactory maakAntwoordBerichtService;
    @Mock
    private OnderhoudAfnemerindicatie.AfnemerindicatieVerzoekService afnemerindicatieVerzoekService;

    @Captor
    private ArgumentCaptor<ArchiveringOpdracht> archiveringOpdrachtArgumentCaptor;

    @Test
    public void testHappyFlow() throws Exception {

        final AfnemerindicatieVerzoek testVerzoek = maakAfnemerindicatieVerzoek();
        final Autorisatiebundel autorisatiebundel = maakAutorisatieBundel();
        final OnderhoudResultaat verzoekResultaat = new OnderhoudResultaat(testVerzoek);
        verzoekResultaat.setBrpPartij(TestPartijBuilder.maakBuilder().metId(1).metCode("000000").build());
        verzoekResultaat.setAutorisatiebundel(autorisatiebundel);
        verzoekResultaat.setPersoonslijst(new Persoonslijst(TestBuilders.maakLeegPersoon(1).build(), 0L));
        verzoekResultaat.getMeldingList().add(new Melding(SoortMelding.FOUT, Regel.R1244));

        when(afnemerindicatieVerzoekService.verwerkVerzoek(testVerzoek)).thenReturn(verzoekResultaat);
        when(maakAntwoordBerichtService.maakAntwoordBericht(verzoekResultaat)).thenReturn(null);

        final RegistreerAfnemerindicatieCallback<String> registreerAfnemerindicatieCallback = new RegistreerAfnemerindicatieCallback<String>() {
            @Override
            public void verwerkResultaat(final SoortDienst soortDienst, final OnderhoudAfnemerindicatieAntwoordBericht bericht) {

            }

            @Override
            public String getResultaat() {
                return "xml";
            }
        };
        onderhoudAfnemerindicatieService.onderhoudAfnemerindicatie(testVerzoek, registreerAfnemerindicatieCallback);

        verify(afnemerindicatieVerzoekService).verwerkVerzoek(testVerzoek);
        verify(maakAntwoordBerichtService).maakAntwoordBericht(verzoekResultaat);
        verify(archiveringService, times(2)).archiveer(archiveringOpdrachtArgumentCaptor.capture());
        assertArchiveringCorrect(testVerzoek, autorisatiebundel, registreerAfnemerindicatieCallback, verzoekResultaat);
    }


    @Test
    public void testHappyFlowGeenAutorisatieBundelGeenPersoonsgegevensGeenMeldingen() throws Exception {

        final AfnemerindicatieVerzoek testVerzoek = maakAfnemerindicatieVerzoek();
        final OnderhoudResultaat verzoekResultaat = new OnderhoudResultaat(testVerzoek);
        verzoekResultaat.setBrpPartij(TestPartijBuilder.maakBuilder().metId(1).metCode("000000").build());
        verzoekResultaat.setAutorisatiebundel(null);
        verzoekResultaat.setPersoonslijst(null);

        when(afnemerindicatieVerzoekService.verwerkVerzoek(testVerzoek)).thenReturn(verzoekResultaat);
        when(maakAntwoordBerichtService.maakAntwoordBericht(verzoekResultaat)).thenReturn(null);

        final RegistreerAfnemerindicatieCallback<String> registreerAfnemerindicatieCallback = new RegistreerAfnemerindicatieCallback<String>() {
            @Override
            public void verwerkResultaat(final SoortDienst soortDienst, final OnderhoudAfnemerindicatieAntwoordBericht bericht) {

            }

            @Override
            public String getResultaat() {
                return "xml";
            }
        };
        onderhoudAfnemerindicatieService.onderhoudAfnemerindicatie(testVerzoek, registreerAfnemerindicatieCallback);

        verify(afnemerindicatieVerzoekService).verwerkVerzoek(testVerzoek);
        verify(maakAntwoordBerichtService).maakAntwoordBericht(verzoekResultaat);
        verify(archiveringService, times(2)).archiveer(archiveringOpdrachtArgumentCaptor.capture());
        assertArchiveringCorrect(testVerzoek, null, registreerAfnemerindicatieCallback, verzoekResultaat);
    }

    private void assertArchiveringCorrect(final AfnemerindicatieVerzoek verzoek,
                                          final Autorisatiebundel autorisatieBundel,
                                          final RegistreerAfnemerindicatieCallback<String> callback,
                                          final OnderhoudResultaat resultaat) {

        ArchiveringOpdracht archiveringOpdrachtIngaand = archiveringOpdrachtArgumentCaptor.getAllValues().get(0);
        Assert.assertEquals(verzoek.getXmlBericht(), archiveringOpdrachtIngaand.getData());
        Assert.assertEquals(verzoek.getStuurgegevens().getReferentieNummer(), archiveringOpdrachtIngaand.getReferentienummer());
        Assert.assertEquals(verzoek.getStuurgegevens().getZendendSysteem(), archiveringOpdrachtIngaand.getZendendeSysteem());
        Assert.assertEquals(SoortBericht.LVG_SYN_REGISTREER_AFNEMERINDICATIE, archiveringOpdrachtIngaand.getSoortBericht());
        if (autorisatieBundel != null) {
            Assert.assertEquals(autorisatieBundel.getDienst().getId(), archiveringOpdrachtIngaand.getDienstId());
            Assert.assertEquals(autorisatieBundel.getLeveringsautorisatieId(), archiveringOpdrachtIngaand.getLeveringsAutorisatieId().intValue());
            Assert.assertEquals(autorisatieBundel.getPartij().getId(), archiveringOpdrachtIngaand.getZendendePartijId());
        } else {
            Assert.assertNull(archiveringOpdrachtIngaand.getDienstId());
            Assert.assertNull(archiveringOpdrachtIngaand.getLeveringsAutorisatieId());
            Assert.assertNull(archiveringOpdrachtIngaand.getZendendePartijId());
        }
        Assert.assertEquals(verzoek.getStuurgegevens().getTijdstipVerzending(), archiveringOpdrachtIngaand.getTijdstipVerzending());
        Assert.assertNotNull(archiveringOpdrachtIngaand.getTijdstipOntvangst());

        ArchiveringOpdracht archiveringOpdrachtUitgaand = archiveringOpdrachtArgumentCaptor.getAllValues().get(1);
        Assert.assertEquals(callback.getResultaat(), archiveringOpdrachtUitgaand.getData());
        Assert.assertEquals(resultaat.getReferentienummerAntwoordbericht(), archiveringOpdrachtUitgaand.getReferentienummer());
        Assert.assertEquals(verzoek.getStuurgegevens().getReferentieNummer(), archiveringOpdrachtUitgaand.getCrossReferentienummer());
        Assert.assertEquals(autorisatieBundel == null ? null : autorisatieBundel.getToegangLeveringsautorisatie().getLeveringsautorisatie().getId(),
                archiveringOpdrachtUitgaand.getLeveringsAutorisatieId());
        Assert.assertEquals(resultaat.getMeldingList().isEmpty() ? VerwerkingsResultaat.GESLAAGD : VerwerkingsResultaat.FOUTIEF,
                archiveringOpdrachtUitgaand.getVerwerkingsresultaat());
        if (autorisatieBundel != null) {
            Assert.assertEquals(ZENDENDE_PARTIJ_ID, archiveringOpdrachtUitgaand.getOntvangendePartijId());
        }
        Assert.assertEquals(SoortBericht.LVG_SYN_REGISTREER_AFNEMERINDICATIE_R, archiveringOpdrachtUitgaand.getSoortBericht());
    }

    private AfnemerindicatieVerzoek maakAfnemerindicatieVerzoek() {
        final AfnemerindicatieVerzoek afnemerindicatieVerzoek = new AfnemerindicatieVerzoek();
        afnemerindicatieVerzoek.setSoortDienst(SoortDienst.VERWIJDERING_AFNEMERINDICATIE);
        afnemerindicatieVerzoek.getParameters().setLeveringsAutorisatieId("1");
        afnemerindicatieVerzoek.getStuurgegevens().setReferentieNummer(null);
        afnemerindicatieVerzoek.getStuurgegevens().setZendendePartijCode(ZENDENDE_PARTIJ_CODE);
        afnemerindicatieVerzoek.getStuurgegevens().setZendendSysteem(BasisBerichtGegevens.BRP_SYSTEEM);
        afnemerindicatieVerzoek.getStuurgegevens().setTijdstipVerzending(TS_VERZENDING);
        return afnemerindicatieVerzoek;
    }

    private Autorisatiebundel maakAutorisatieBundel() {
        final PartijRol partijRol = new PartijRol(TestPartijBuilder.maakBuilder().metId(1).metCode("000001").build(), Rol.AFNEMER);
        final SoortDienst soortDienst = SoortDienst.PLAATSING_AFNEMERINDICATIE;
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        return new Autorisatiebundel(tla, AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst));
    }

}
