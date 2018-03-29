/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.selectie.afnemerindicatie;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienstbundel;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsautorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangLeveringsAutorisatie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Element;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.LeverwijzeSelectie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Protocolleringsniveau;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Regel;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Richting;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Rol;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortDienst;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortSelectie;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.Stelsel;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.archivering.domain.algemeen.ArchiveringOpdracht;
import nl.bzk.brp.domain.internbericht.selectie.SelectieAfnemerindicatieTaak;
import nl.bzk.brp.domain.internbericht.selectie.SelectieFragmentSchrijfBericht;
import nl.bzk.brp.domain.internbericht.verzendingmodel.AfnemerBericht;
import nl.bzk.brp.domain.internbericht.verzendingmodel.SynchronisatieBerichtGegevens;
import nl.bzk.brp.domain.leveringmodel.MetaObject;
import nl.bzk.brp.domain.leveringmodel.helper.TestBuilders;
import nl.bzk.brp.domain.leveringmodel.persoon.Persoonslijst;
import nl.bzk.brp.protocollering.domain.algemeen.ProtocolleringOpdracht;
import nl.bzk.brp.service.algemeen.PlaatsAfnemerBerichtService;
import nl.bzk.brp.service.algemeen.StapMeldingException;
import nl.bzk.brp.service.algemeen.VerstrekkingsbeperkingService;
import nl.bzk.brp.service.algemeen.afnemerindicatie.GeneriekeOnderhoudAfnemerindicatieStappen;
import nl.bzk.brp.service.algemeen.blob.AfnemerindicatieParameters;
import nl.bzk.brp.service.algemeen.blob.PersoonAfnemerindicatieService;
import nl.bzk.brp.service.algemeen.blob.PersoonslijstService;
import nl.bzk.brp.service.cache.LeveringsAutorisatieCache;
import nl.bzk.brp.service.selectie.verwerker.SelectieSchrijfTaakPublicatieService;
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

/**
 * Unit test voor {@link OnderhoudAfnemerindicatieSelectieServiceImplTest}.
 */
@RunWith(MockitoJUnitRunner.class)
public class OnderhoudAfnemerindicatieSelectieServiceImplTest {

    private static final short PARTIJ_ID = (short) 1;
    private static final int DIENST_ID_PLAATSEN = 1;
    private static final int DIENST_ID_VERWIJDEREN = 2;
    private static final int DIENST_ID_BESTAND = 3;
    private static final int TOEGANG_LEVERINGSAUTORISATIE_ID = 1;
    private static final long PERSOON_ID = 1L;
    private static final long PERSOON_LOCK_VERSION = 1L;
    private static final long AFNEMERINDICATIE_LOCK_VERSION = 1L;
    private static final int LEVERINGSAUTORISATIE_ID = 1;
    private static final int SELECTIE_TAAK_ID = 10;

    @InjectMocks
    private OnderhoudAfnemerindicatieSelectieServiceImpl service;
    @Mock
    private LeveringsAutorisatieCache leveringsAutorisatieCache;
    @Mock
    private PersoonAfnemerindicatieService persoonAfnemerindicatieService;
    @Mock
    private PlaatsAfnemerBerichtService plaatsAfnemerBerichtService;
    @Mock
    private PersoonslijstService persoonslijstService;
    @Mock
    private VerstrekkingsbeperkingService verstrekkingsbeperkingService;
    @Mock
    private GeneriekeOnderhoudAfnemerindicatieStappen.ValideerRegelsPlaatsing valideerRegelsPlaatsing;
    @Mock
    private GeneriekeOnderhoudAfnemerindicatieStappen.ValideerRegelsVerwijderen valideerRegelsVerwijderen;
    @Mock
    private SelectieSchrijfTaakPublicatieService selectieSchrijfTaakPublicatieService;
    @Captor
    private ArgumentCaptor<List<AfnemerBericht>> afnemerBerichtCaptor;
    @Captor
    private ArgumentCaptor<List<SelectieFragmentSchrijfBericht>> schrijfTakenCaptor;

    private SelectieAfnemerindicatieTaak verzoekPlaatsen;
    private SelectieAfnemerindicatieTaak verzoekVerwijderen;
    private SelectieAfnemerindicatieTaak verzoekBestand;
    private ProtocolleringOpdracht protocolleringOpdracht;
    private Partij partij;
    private Dienst dienstPlaatsen;
    private Dienst dienstVerwijderen;
    private Dienst diensBestand;

    @Before
    public void before() throws Exception {
        verzoekPlaatsen = new SelectieAfnemerindicatieTaak();
        verzoekPlaatsen.setDienstId(DIENST_ID_PLAATSEN);
        verzoekPlaatsen.setToegangLeveringsautorisatieId(TOEGANG_LEVERINGSAUTORISATIE_ID);
        verzoekPlaatsen.setPersoonId(PERSOON_ID);
        verzoekPlaatsen.setSelectieTaakId(SELECTIE_TAAK_ID);

        verzoekVerwijderen = new SelectieAfnemerindicatieTaak();
        verzoekVerwijderen.setDienstId(DIENST_ID_VERWIJDEREN);
        verzoekVerwijderen.setToegangLeveringsautorisatieId(TOEGANG_LEVERINGSAUTORISATIE_ID);
        verzoekVerwijderen.setPersoonId(PERSOON_ID);
        verzoekVerwijderen.setSelectieTaakId(SELECTIE_TAAK_ID);

        verzoekBestand = new SelectieAfnemerindicatieTaak();
        verzoekBestand.setDienstId(DIENST_ID_BESTAND);
        verzoekBestand.setToegangLeveringsautorisatieId(TOEGANG_LEVERINGSAUTORISATIE_ID);
        verzoekBestand.setPersoonId(PERSOON_ID);
        verzoekBestand.setSelectieTaakId(SELECTIE_TAAK_ID);

        protocolleringOpdracht = new ProtocolleringOpdracht();

        ArchiveringOpdracht archiveringOpdracht = new ArchiveringOpdracht(Richting.UITGAAND, DatumUtil.nuAlsZonedDateTime());
        final SynchronisatieBerichtGegevens.Builder synchronisatieBerichtGegevens = SynchronisatieBerichtGegevens.builder()
                .metSoortDienst(SoortDienst.SELECTIE)
                .metStelsel(Stelsel.BRP)
                .metProtocolleringsniveau(Protocolleringsniveau.GEEN_BEPERKINGEN)
                .metBrpEndpointURI("http://")
                .metProtocolleringOpdracht(protocolleringOpdracht)
                .metArchiveringOpdracht(archiveringOpdracht);

        verzoekPlaatsen.setSynchronisatieBerichtGegevens(synchronisatieBerichtGegevens.build());
        verzoekPlaatsen.setSoortSelectie(SoortSelectie.SELECTIE_MET_PLAATSING_AFNEMERINDICATIE);
        verzoekVerwijderen.setSynchronisatieBerichtGegevens(synchronisatieBerichtGegevens.build());
        verzoekVerwijderen.setSoortSelectie(SoortSelectie.SELECTIE_MET_VERWIJDERING_AFNEMERINDICATIE);
        verzoekBestand.setBericht("xml");
        verzoekBestand.setSoortSelectie(SoortSelectie.SELECTIE_MET_PLAATSING_AFNEMERINDICATIE);

        final Leveringsautorisatie leveringsautorisatie = new Leveringsautorisatie(Stelsel.BRP, false);
        leveringsautorisatie.setId(LEVERINGSAUTORISATIE_ID);
        final Dienstbundel dienstbundel = new Dienstbundel(leveringsautorisatie);
        dienstPlaatsen = new Dienst(dienstbundel, SoortDienst.SELECTIE);
        dienstPlaatsen.setId(DIENST_ID_PLAATSEN);
        dienstPlaatsen.setSoortSelectie(SoortSelectie.SELECTIE_MET_PLAATSING_AFNEMERINDICATIE.getId());
        dienstPlaatsen.setIndVerzVolBerBijWijzAfniNaSelectie(true);
        dienstVerwijderen = new Dienst(dienstbundel, SoortDienst.SELECTIE);
        dienstVerwijderen.setId(DIENST_ID_VERWIJDEREN);
        dienstVerwijderen.setSoortSelectie(SoortSelectie.SELECTIE_MET_VERWIJDERING_AFNEMERINDICATIE.getId());
        dienstVerwijderen.setIndVerzVolBerBijWijzAfniNaSelectie(true);
        diensBestand = new Dienst(dienstbundel, SoortDienst.SELECTIE);
        diensBestand.setId(DIENST_ID_BESTAND);
        diensBestand.setSoortSelectie(SoortSelectie.SELECTIE_MET_PLAATSING_AFNEMERINDICATIE.getId());
        diensBestand.setLeverwijzeSelectie(LeverwijzeSelectie.STANDAARD.getId());
        dienstbundel.addDienstSet(diensBestand);
        dienstbundel.addDienstSet(dienstVerwijderen);
        dienstbundel.addDienstSet(dienstPlaatsen);

        leveringsautorisatie.addDienstbundelSet(dienstbundel);
        partij = new Partij("test", "000001");
        partij.setId(PARTIJ_ID);
        final ToegangLeveringsAutorisatie toegangLeveringsAutorisatie = new ToegangLeveringsAutorisatie(new PartijRol(partij,
                Rol.AFNEMER), leveringsautorisatie);
        when(leveringsAutorisatieCache.geefToegangLeveringsautorisatie(TOEGANG_LEVERINGSAUTORISATIE_ID)).thenReturn(toegangLeveringsAutorisatie);
        final MetaObject metaObject = TestBuilders.maakLeegPersoon()
                .metGroep()
                .metGroepElement(Element.PERSOON_VERSIE.getId())
                .metRecord().metAttribuut(Element.PERSOON_VERSIE_LOCK.getId(), PERSOON_LOCK_VERSION).eindeRecord()
                .eindeGroep().build();
        when(persoonslijstService.getById(Mockito.anyInt())).thenReturn(new Persoonslijst(metaObject, 1L));
        when(verstrekkingsbeperkingService.heeftGeldigeVerstrekkingsbeperking(Mockito.any(), Mockito.any())).thenReturn(false);
    }

    @Test
    public void testPlaatsenAfnemerindicatieMetVolledigBericht() throws Exception {
        service.verwerk(Collections.singleton(verzoekPlaatsen));

        final InOrder
                inOrder =
                inOrder(persoonAfnemerindicatieService, plaatsAfnemerBerichtService);
        inOrder.verify(persoonAfnemerindicatieService)
                .plaatsAfnemerindicatie(refEq(new AfnemerindicatieParameters(PERSOON_ID, PERSOON_LOCK_VERSION, AFNEMERINDICATIE_LOCK_VERSION)), eq(partij),
                        eq(LEVERINGSAUTORISATIE_ID),
                        eq(DIENST_ID_PLAATSEN), eq(null), eq(null), any());
        inOrder.verify(plaatsAfnemerBerichtService).plaatsAfnemerberichten(afnemerBerichtCaptor.capture());
        final AfnemerBericht params = afnemerBerichtCaptor.getValue().get(0);
        assertThat(params.getSynchronisatieBerichtGegevens().getProtocolleringOpdracht(), is(protocolleringOpdracht));

    }

    @Test
    public void testPlaatsenAfnemerindicatieMetBestand() throws Exception {
        service.verwerk(Collections.singleton(verzoekBestand));

        final InOrder
                inOrder =
                inOrder(persoonAfnemerindicatieService, selectieSchrijfTaakPublicatieService);
        inOrder.verify(persoonAfnemerindicatieService)
                .plaatsAfnemerindicatie(refEq(new AfnemerindicatieParameters(PERSOON_ID, PERSOON_LOCK_VERSION, AFNEMERINDICATIE_LOCK_VERSION)), eq(partij),
                        eq(LEVERINGSAUTORISATIE_ID),
                        eq(DIENST_ID_BESTAND), eq(null), eq(null), any());

        inOrder.verify(selectieSchrijfTaakPublicatieService).publiceerSchrijfTaken(schrijfTakenCaptor.capture());
        final SelectieFragmentSchrijfBericht params = schrijfTakenCaptor.getValue().get(0);
        Assert.assertTrue(params.getBerichten().size() == 1);
        Mockito.verifyZeroInteractions(plaatsAfnemerBerichtService);

        assertThat(params.getBerichten().get(0), is(verzoekBestand.getBericht()));

    }

    @Test
    public void testVerwijderAfnemerindicatieMetVolledigBericht() throws Exception {
        service.verwerk(Collections.singleton(verzoekVerwijderen));

        final InOrder
                inOrder =
                inOrder(valideerRegelsVerwijderen, persoonAfnemerindicatieService, plaatsAfnemerBerichtService);
        inOrder.verify(persoonAfnemerindicatieService)
                .verwijderAfnemerindicatie(refEq(new AfnemerindicatieParameters(PERSOON_ID, PERSOON_LOCK_VERSION, AFNEMERINDICATIE_LOCK_VERSION)), eq(partij),
                        eq(DIENST_ID_VERWIJDEREN),
                        eq(LEVERINGSAUTORISATIE_ID));
        inOrder.verify(plaatsAfnemerBerichtService).plaatsAfnemerberichten(afnemerBerichtCaptor.capture());
        final AfnemerBericht params = afnemerBerichtCaptor.getValue().get(0);
        assertThat(params.getSynchronisatieBerichtGegevens().getProtocolleringOpdracht(), is(protocolleringOpdracht));

    }

    @Test
    public void testVerwijderAfnemerindicatieMetVolledigBerichtMetVerstrekkingsbeperking() throws Exception {
        when(verstrekkingsbeperkingService.heeftGeldigeVerstrekkingsbeperking(Mockito.any(), Mockito.any())).thenReturn(true);
        service.verwerk(Collections.singleton(verzoekVerwijderen));

        final InOrder
                inOrder =
                inOrder(valideerRegelsVerwijderen, persoonAfnemerindicatieService);
        inOrder.verify(persoonAfnemerindicatieService)
                .verwijderAfnemerindicatie(refEq(new AfnemerindicatieParameters(PERSOON_ID, PERSOON_LOCK_VERSION, AFNEMERINDICATIE_LOCK_VERSION)), eq(partij),
                        eq(DIENST_ID_VERWIJDEREN),
                        eq(LEVERINGSAUTORISATIE_ID));
        Mockito.verifyZeroInteractions(plaatsAfnemerBerichtService);

    }

    @Test
    public void testPlaatsenAfnemerindicatieZonderVolledigBericht() throws Exception {
        dienstPlaatsen.setIndVerzVolBerBijWijzAfniNaSelectie(false);
        service.verwerk(Collections.singleton(verzoekPlaatsen));

        final InOrder
                inOrder =
                inOrder(valideerRegelsPlaatsing, persoonAfnemerindicatieService, plaatsAfnemerBerichtService);
        inOrder.verify(persoonAfnemerindicatieService)
                .plaatsAfnemerindicatie(refEq(new AfnemerindicatieParameters(PERSOON_ID, PERSOON_LOCK_VERSION, AFNEMERINDICATIE_LOCK_VERSION)), eq(partij),
                        eq(LEVERINGSAUTORISATIE_ID),
                        eq(DIENST_ID_PLAATSEN), eq(null), eq(null), any());
        Mockito.verifyZeroInteractions(plaatsAfnemerBerichtService);
    }


    @Test
    public void testBijValidatieFoutGeenPlaatsenMaarGeenExceptie() throws Exception {
        doThrow(new StapMeldingException(Regel.R1401)).when(valideerRegelsPlaatsing)
                .valideer(any());

        service.verwerk(Collections.singleton(verzoekPlaatsen));

        Mockito.verifyZeroInteractions(persoonAfnemerindicatieService);
    }

    @Test
    public void testBijValidatieFoutGeenVerwijderenMaarGeenExceptie() throws Exception {
        doThrow(new StapMeldingException(Regel.R1401)).when(valideerRegelsVerwijderen)
                .valideer(any(), any());

        service.verwerk(Collections.singleton(verzoekVerwijderen));

        Mockito.verifyZeroInteractions(persoonAfnemerindicatieService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBijExceptieGeenPlaatsenEnExceptie() throws Exception {
        doThrow(new IllegalArgumentException("fout")).when(valideerRegelsPlaatsing)
                .valideer(any());

        service.verwerk(Collections.singleton(verzoekPlaatsen));

        Mockito.verifyZeroInteractions(persoonAfnemerindicatieService);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBijExceptieGeenVerwijderenEnExceptie() throws Exception {
        doThrow(new IllegalArgumentException("fout")).when(valideerRegelsVerwijderen)
                .valideer(any(), any());

        service.verwerk(Collections.singleton(verzoekVerwijderen));

        Mockito.verifyZeroInteractions(persoonAfnemerindicatieService);
    }
}
