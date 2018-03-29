/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.synchronisatie.persoon;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortBericht;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.VerwerkingsResultaat;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.archivering.domain.algemeen.ArchiveringOpdracht;
import nl.bzk.brp.archivering.service.algemeen.ArchiefService;
import nl.bzk.brp.domain.algemeen.AutAutUtil;
import nl.bzk.brp.domain.algemeen.Autorisatiebundel;
import nl.bzk.brp.domain.algemeen.TestAutorisaties;
import nl.bzk.brp.domain.algemeen.TestPartijBuilder;
import nl.bzk.brp.domain.berichtmodel.BasisBerichtGegevens;
import nl.bzk.brp.domain.berichtmodel.SynchroniseerPersoonAntwoordBericht;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.TestVerantwoording;
import nl.bzk.brp.domain.leveringmodel.persoon.BrpNu;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.service.algemeen.autorisatie.PartijService;
import nl.bzk.brp.service.synchronisatie.SynchronisatieVerzoek;
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

@RunWith(MockitoJUnitRunner.class)
public class SynchroniseerPersoonServiceImplTest {

    private static final String LEV_AUT_ID = "1";
    private static final String ZENDENDE_PARTIJ_CODE = "1";
    private static final Short ZENDENDE_PARTIJ_ID = 2;
    private static final String REF_NR = "ref_nr";

    private static final Partij brpPartij = TestPartijBuilder.maakBuilder().metId(1).metCode("000000").metNaam("BRP Voorziening").build();
    private static final Partij zendendePartij = TestPartijBuilder.maakBuilder().metId(ZENDENDE_PARTIJ_ID).metCode("000000").metNaam("Zendende partij").build();

    @InjectMocks
    private SynchroniseerPersoonServiceImpl service;

    @Mock
    private SynchroniseerPersoon.MaakSynchronisatieBerichtService maakSynchronisatieBerichtService;
    @Mock
    private SynchroniseerPersoonBerichtFactory synchroniseerPersoonBerichtFactory;
    @Mock
    private ArchiefService archiveringService;
    @Mock
    private PartijService partijService;

    @Captor
    private ArgumentCaptor<ArchiveringOpdracht> archiveringOpdrachtArgumentCaptor;

    private SynchronisatieVerzoek verzoek;

    @Before
    public void voorTest() {
        BrpNu.set();

        verzoek = new SynchronisatieVerzoek();
        verzoek.setSoortDienst(SoortDienst.ATTENDERING);
        verzoek.getParameters().setLeveringsAutorisatieId(LEV_AUT_ID);
        verzoek.getStuurgegevens().setZendendePartijCode(ZENDENDE_PARTIJ_CODE);
        verzoek.getStuurgegevens().setReferentieNummer(REF_NR);
        verzoek.getStuurgegevens().setZendendSysteem(BasisBerichtGegevens.BRP_SYSTEEM);

        Mockito.when(partijService.geefBrpPartij()).thenReturn(brpPartij);
    }

    @Test
    public void testHappyflow() throws Exception {
        final MaakSynchronisatieBerichtResultaat berichtResultaat = new MaakSynchronisatieBerichtResultaat(verzoek);

        final Autorisatiebundel autorisatiebundel = maakAutorisatieBundel();
        berichtResultaat.setAutorisatiebundel(autorisatiebundel);
        //@formatter:off
        berichtResultaat.setPersoonslijst(new Persoonslijst(
            MetaObject.maakBuilder()
                .metObjectElement(Element.PERSOON.getId())
                .metGroep()
                    .metGroepElement(Element.PERSOON_AFGELEIDADMINISTRATIEF.getId())
                    .metRecord()
                        .metActieInhoud(TestVerantwoording.maakActie(1))
                        .metAttribuut(Element.PERSOON_AFGELEIDADMINISTRATIEF_TIJDSTIPLAATSTEWIJZIGING.getId(), DatumUtil.nuAlsZonedDateTime())
                    .eindeRecord()
                .eindeGroep()
            .build(),
            0L));

        when(maakSynchronisatieBerichtService.verwerkVerzoek(verzoek)).thenReturn(berichtResultaat);
        final BasisBerichtGegevens basisBerichtGegevens = BasisBerichtGegevens.builder()
            .metStuurgegevens()
                .metReferentienummer(REF_NR)
                .metTijdstipVerzending(DatumUtil.nuAlsZonedDateTime())
            .eindeStuurgegevens()
        .build();
        //@formatter:on
        final SynchroniseerPersoonAntwoordBericht antwoordBericht = new SynchroniseerPersoonAntwoordBericht(basisBerichtGegevens);

        when(synchroniseerPersoonBerichtFactory.apply(any())).thenReturn(antwoordBericht);

        final SynchroniseerPersoonService.SynchronisatieCallback<String> callBack = new SynchroniseerPersoonService.SynchronisatieCallback<String>() {
            @Override
            public void verwerkResultaat(final SynchroniseerPersoonAntwoordBericht resultaat) {

            }

            @Override
            public String getResultaat() {
                return "xml";
            }
        };
        service.synchroniseer(verzoek, callBack);

        Mockito.verify(maakSynchronisatieBerichtService).verwerkVerzoek(verzoek);
        Mockito.verify(synchroniseerPersoonBerichtFactory).apply(any()); //arg wordt genewd in service..
        Mockito.verify(archiveringService, times(2)).archiveer(archiveringOpdrachtArgumentCaptor.capture());
        assertArchiveringCorrect(verzoek, autorisatiebundel, callBack, berichtResultaat);
    }

    @Test
    public void testZonderPersonenFoutiefEnZonderAutorisatie() throws Exception {
        final MaakSynchronisatieBerichtResultaat berichtResultaat = new MaakSynchronisatieBerichtResultaat(verzoek);
        berichtResultaat.setAutorisatiebundel(null);
        //@formatter:on
        when(maakSynchronisatieBerichtService.verwerkVerzoek(verzoek)).thenReturn(berichtResultaat);
        final SynchroniseerPersoonAntwoordBericht antwoordBericht = new SynchroniseerPersoonAntwoordBericht(null);
        when(synchroniseerPersoonBerichtFactory.apply(any())).thenReturn(antwoordBericht);

        final SynchroniseerPersoonService.SynchronisatieCallback<String> callBack = new SynchroniseerPersoonService.SynchronisatieCallback<String>() {
            @Override
            public void verwerkResultaat(final SynchroniseerPersoonAntwoordBericht resultaat) {

            }

            @Override
            public String getResultaat() {
                return "xml";
            }
        };
        service.synchroniseer(verzoek, callBack);

        Mockito.verify(maakSynchronisatieBerichtService).verwerkVerzoek(verzoek);
        Mockito.verify(synchroniseerPersoonBerichtFactory).apply(any()); //arg wordt genewd in service..
        Mockito.verify(archiveringService, times(2)).archiveer(archiveringOpdrachtArgumentCaptor.capture());
        assertArchiveringCorrect(verzoek, null, callBack, berichtResultaat);
    }

    private void assertArchiveringCorrect(final SynchronisatieVerzoek verzoek,
                                          final Autorisatiebundel autorisatieBundel,
                                          final SynchroniseerPersoonService.SynchronisatieCallback<String> callback,
                                          final MaakSynchronisatieBerichtResultaat resultaat) {

        ArchiveringOpdracht archiveringOpdrachtIngaand = archiveringOpdrachtArgumentCaptor.getAllValues().get(0);
        Assert.assertEquals(verzoek.getXmlBericht(), archiveringOpdrachtIngaand.getData());
        Assert.assertEquals(verzoek.getStuurgegevens().getReferentieNummer(), archiveringOpdrachtIngaand.getReferentienummer());
        Assert.assertEquals(verzoek.getStuurgegevens().getZendendSysteem(), archiveringOpdrachtIngaand.getZendendeSysteem());
        Assert.assertEquals(SoortBericht.LVG_SYN_GEEF_SYNCHRONISATIE_PERSOON, archiveringOpdrachtIngaand.getSoortBericht());
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
        Assert.assertNotNull(archiveringOpdrachtUitgaand.getTijdstipVerzending());
        Assert.assertEquals(SoortBericht.LVG_SYN_GEEF_SYNCHRONISATIE_PERSOON_R, archiveringOpdrachtUitgaand.getSoortBericht());
        Assert.assertNotNull(archiveringOpdrachtUitgaand.getReferentienummer());
        Assert.assertEquals(verzoek.getStuurgegevens().getReferentieNummer(), archiveringOpdrachtUitgaand.getCrossReferentienummer());
        Assert.assertEquals(autorisatieBundel == null ? null : autorisatieBundel.getToegangLeveringsautorisatie().getLeveringsautorisatie().getId(),
                archiveringOpdrachtUitgaand.getLeveringsAutorisatieId());
        Assert.assertEquals(resultaat.getMeldingList().isEmpty() ? VerwerkingsResultaat.GESLAAGD : VerwerkingsResultaat.FOUTIEF,
                archiveringOpdrachtUitgaand.getVerwerkingsresultaat());
        Assert.assertNotNull(archiveringOpdrachtUitgaand.getZendendePartijId());
        Assert.assertEquals(BasisBerichtGegevens.BRP_SYSTEEM, archiveringOpdrachtUitgaand.getZendendeSysteem());
        if (autorisatieBundel != null) {
            Assert.assertEquals(ZENDENDE_PARTIJ_ID, archiveringOpdrachtUitgaand.getOntvangendePartijId());
        }
    }

    private Autorisatiebundel maakAutorisatieBundel() {
        final PartijRol partijRol = new PartijRol(zendendePartij, Rol.AFNEMER);
        final SoortDienst soortDienst = SoortDienst.PLAATSING_AFNEMERINDICATIE;
        final Leveringsautorisatie leveringsautorisatie = TestAutorisaties.metSoortDienst(1, soortDienst);
        final ToegangLeveringsAutorisatie tla = new ToegangLeveringsAutorisatie(partijRol, leveringsautorisatie);
        return new Autorisatiebundel(tla, AutAutUtil.zoekDienst(leveringsautorisatie, soortDienst));
    }
}
