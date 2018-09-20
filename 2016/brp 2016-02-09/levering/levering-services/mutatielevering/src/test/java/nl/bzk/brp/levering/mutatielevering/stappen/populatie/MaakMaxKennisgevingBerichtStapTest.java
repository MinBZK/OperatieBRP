/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.populatie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Mockito.anyMap;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.business.stappen.AbstractStap;
import nl.bzk.brp.business.stappen.Stap;
import nl.bzk.brp.levering.business.bericht.BerichtFactory;
import nl.bzk.brp.levering.business.bericht.BerichtService;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerp;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerpImpl;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContext;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContextImpl;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieVerwerkingResultaat;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.model.Populatie;
import nl.bzk.brp.levering.mutatielevering.excepties.StapPreValidatieExceptie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumEvtDeelsOnbekendAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Protocolleringsniveau;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortActieAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Stelsel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.hisvolledig.predikaat.HuidigeSituatiePredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.AbstractSynchronisatieBericht;
import nl.bzk.brp.model.levering.AdministratieveHandelingSynchronisatie;
import nl.bzk.brp.model.levering.MutatieBericht;
import nl.bzk.brp.model.levering.SynchronisatieBericht;
import nl.bzk.brp.model.levering.VolledigBericht;
import nl.bzk.brp.model.operationeel.kern.ActieModel;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import support.AdministratieveHandelingTestBouwer;

@RunWith(MockitoJUnitRunner.class)
public class MaakMaxKennisgevingBerichtStapTest {

    @InjectMocks
    private final MaakMaxKennisgevingBerichtStap maakMaxKennisgevingBerichtStap = new MaakMaxKennisgevingBerichtStap();

    @Mock
    private BerichtFactory berichtFactory;

    @Mock
    private BerichtService berichtService;

    @Mock
    private Leveringinformatie leveringAutorisatie;

    private final AdministratieveHandelingModel administratieveHandelingModel = AdministratieveHandelingTestBouwer.getTestAdministratieveHandeling();

    private LeveringautorisatieStappenOnderwerp onderwerp;

    private LeveringsautorisatieVerwerkingContext context;

    private final LeveringautorisatieVerwerkingResultaat resultaat = new LeveringautorisatieVerwerkingResultaat();

    private PersoonHisVolledig persoon1;
    private PersoonHisVolledig persoon2;

    private final Map<Integer, Populatie> persoonPopulatie = new HashMap<>();

    private final List<SynchronisatieBericht>  berichten                = new ArrayList<>();
    private final List<PersoonHisVolledig>     bijgehoudenPersonen      = new ArrayList<>();
    private final List<PersoonHisVolledigView> bijgehoudenPersonenViews = new ArrayList<>();

    @Before
    public final void setup() {
        persoon1 = maakPersoon(1);
        persoon2 = maakPersoon(2);

        bijgehoudenPersonen.add(persoon1);
        bijgehoudenPersonen.add(persoon2);

        bijgehoudenPersonenViews.add(new PersoonHisVolledigView(persoon1, new HuidigeSituatiePredikaat()));
        bijgehoudenPersonenViews.add(new PersoonHisVolledigView(persoon2, new HuidigeSituatiePredikaat()));

        when(leveringAutorisatie.getSoortDienst()).thenReturn(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING);
        final Leveringsautorisatie leveringsautorisatie = TestLeveringsautorisatieBuilder.maker().metNaam("TestLeveringsautorisatie").metPopulatiebeperking
            ("WAAR")
            .metProtocolleringsniveau(Protocolleringsniveau
                .GEEN_BEPERKINGEN).metDatumIngang(DatumAttribuut.gisteren()).maak();
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie
            (leveringsautorisatie).maak();
        when(leveringAutorisatie.getToegangLeveringsautorisatie())
            .thenReturn(toegangLeveringsautorisatie);

        onderwerp = new LeveringautorisatieStappenOnderwerpImpl(leveringAutorisatie, administratieveHandelingModel.getID(), Stelsel.BRP);

        persoonPopulatie.put(1, Populatie.BINNEN);
        persoonPopulatie.put(2, Populatie.BINNEN);
        context = new LeveringsautorisatieVerwerkingContextImpl(administratieveHandelingModel, bijgehoudenPersonen, persoonPopulatie, null, null);
        context.setBijgehoudenPersoonViews(bijgehoudenPersonenViews);

        Mockito.when(berichtFactory.maakBerichten(Mockito.anyListOf(PersoonHisVolledigView.class), Mockito.any(Leveringinformatie.class), Mockito.anyMapOf(
            Integer.class, Populatie.class), Mockito.any(AdministratieveHandelingModel.class))).thenReturn(berichten);
    }

    @Test
    public final void testVoerStapUitVoorKennisgevingbericht() {
        final VolledigBericht volledigBericht = maakVolledigBericht(bijgehoudenPersonenViews);
        berichten.add(volledigBericht);

        // when
        final boolean stapResultaat = maakMaxKennisgevingBerichtStap.voerStapUit(onderwerp, context, resultaat);

        // then
        assertEquals(AbstractStap.DOORGAAN, stapResultaat);
        assertNotNull(context.getLeveringBerichten());

        final List<SynchronisatieBericht> leveringBerichten = context.getLeveringBerichten();

        final SynchronisatieBericht bericht = leveringBerichten.get(0);
        Assert.assertEquals(bericht.getAdministratieveHandeling().getBijgehoudenPersonen().get(0).getID(), persoon1.getID());
        Assert.assertEquals(bericht.getAdministratieveHandeling().getBijgehoudenPersonen().get(1).getID(), persoon2.getID());
    }

    @Test
    public final void testVoerStapUitVoorVolledigBericht() {
        final MutatieBericht mutatieBericht = maakMutatieBericht(bijgehoudenPersonenViews);
        berichten.add(mutatieBericht);

        // when
        final boolean stapResultaat = maakMaxKennisgevingBerichtStap.voerStapUit(onderwerp, context, resultaat);

        // then
        assertEquals(AbstractStap.DOORGAAN, stapResultaat);
        assertNotNull(context.getLeveringBerichten());

        final List<SynchronisatieBericht> leveringBerichten = context.getLeveringBerichten();

        final SynchronisatieBericht bericht = leveringBerichten.get(0);
        Assert.assertEquals(bericht.getAdministratieveHandeling().getBijgehoudenPersonen().get(0).getID(), persoon1.getID());
        Assert.assertEquals(bericht.getAdministratieveHandeling().getBijgehoudenPersonen().get(1).getID(), persoon2.getID());
    }

    @Test
    public final void testVoerStapUitMetNietAllePopulatiesGevuld() {
        final MutatieBericht mutatieBericht = maakMutatieBericht(bijgehoudenPersonenViews);
        berichten.add(mutatieBericht);

        // given
        final Map<Integer, Populatie> persoonPopulatieMap = new HashMap<>();
        persoonPopulatieMap.put(1, null);
        persoonPopulatieMap.put(2, Populatie.BINNEN);
        persoonPopulatieMap.put(3, null);
        context = new LeveringsautorisatieVerwerkingContextImpl(context.getAdministratieveHandeling(), context.getBijgehoudenPersonenVolledig(),
            persoonPopulatieMap, null, null);
        context.setBijgehoudenPersoonViews(bijgehoudenPersonenViews);

        // when
        final boolean stapResultaat = maakMaxKennisgevingBerichtStap.voerStapUit(onderwerp, context, resultaat);

        // then
        assertEquals(AbstractStap.DOORGAAN, stapResultaat);
        assertNotNull(context.getLeveringBerichten());

        final ArgumentCaptor<List> argumentCaptor = ArgumentCaptor.forClass(List.class);

        verify(berichtFactory).maakBerichten(argumentCaptor.capture(), any(Leveringinformatie.class), anyMap(), any(AdministratieveHandelingModel.class));

        final List<PersoonHisVolledig> lijstVanPersonenInBericht = argumentCaptor.getValue();
        assertEquals(1, lijstVanPersonenInBericht.size());
        assertEquals(2, lijstVanPersonenInBericht.get(0).getID().intValue());
    }

    @Test
    public final void testGeenPersoonDeltasAanwezig() {
        context.setBijgehoudenPersoonViews(new ArrayList<PersoonHisVolledigView>());

        doReturn(new ArrayList<SynchronisatieBericht>()).when(berichtFactory)
            .maakBerichten(anyListOf(PersoonHisVolledigView.class), any(Leveringinformatie.class), anyMap(), any(AdministratieveHandelingModel.class));

        final boolean stapResultaat =
            maakMaxKennisgevingBerichtStap.voerStapUit(onderwerp, context, resultaat);

        assertEquals(Stap.STOPPEN, stapResultaat);
        assertTrue(resultaat.bevatStoppendeFouten());
    }

    @Test(expected = StapPreValidatieExceptie.class)
    public final void testGeenBijgehoudenPersonenAanwezig() {
        context = new LeveringsautorisatieVerwerkingContextImpl(null, null, null, null, null);

        maakMaxKennisgevingBerichtStap.voerStapUit(onderwerp, context, resultaat);
    }

    @Test(expected = StapPreValidatieExceptie.class)
    public final void testGeenAdministratieveHandelingAanwezig() {
        context = new LeveringsautorisatieVerwerkingContextImpl(null, bijgehoudenPersonen, null, null, null);

        maakMaxKennisgevingBerichtStap.voerStapUit(onderwerp, context, resultaat);
    }

    @Test
    public final void testVoerStapUitLegeLijstLeveringBerichten() {
        doReturn(new ArrayList<AbstractSynchronisatieBericht>()).when(berichtFactory).maakBerichten(anyListOf(PersoonHisVolledigView.class),
            any(Leveringinformatie.class), anyMap(), any(AdministratieveHandelingModel.class));

        // when
        final boolean stapResultaat = maakMaxKennisgevingBerichtStap.voerStapUit(onderwerp, context, resultaat);

        // then
        assertEquals(Stap.STOPPEN, stapResultaat);
        assertTrue(context.getLeveringBerichten() == null);
    }

    @Test
    public final void testVoerStapUitGeenLeveringBerichten() {
        doReturn(new ArrayList<SynchronisatieBericht>()).when(berichtFactory)
            .maakBerichten(anyListOf(PersoonHisVolledigView.class), any(Leveringinformatie.class), anyMap(), any(AdministratieveHandelingModel.class));

        // when
        final boolean stapResultaat = maakMaxKennisgevingBerichtStap.voerStapUit(onderwerp, context, resultaat);

        // then
        assertEquals(Stap.STOPPEN, stapResultaat);
        assertTrue(context.getLeveringBerichten() == null);
    }

    @Test
    public final void testVoerNabewerkingStapUit() {
        final SoortAdministratieveHandelingAttribuut soortAdministratieveHandeling =
            new SoortAdministratieveHandelingAttribuut(SoortAdministratieveHandeling.DUMMY);
        final PartijAttribuut partij = new PartijAttribuut(
            TestPartijBuilder.maker().metNaam("gem").metSoort(SoortPartij.GEMEENTE).metCode(34).maak());

        final List<SynchronisatieBericht> leveringBerichten = new ArrayList<>();
        leveringBerichten.add(new MutatieBericht(new AdministratieveHandelingSynchronisatie(
            new AdministratieveHandelingModel(soortAdministratieveHandeling, partij, null, null))));

        context.setLeveringBerichten(leveringBerichten);
        context.setBijgehoudenPersoonViews(new ArrayList<PersoonHisVolledigView>());

        maakMaxKennisgevingBerichtStap.voerNabewerkingStapUit(onderwerp, context, resultaat);
    }

    /**
     * Maakt een persoon view.
     *
     * @param id id van persoon
     * @return persoon his volledig view
     */
    private PersoonHisVolledig maakPersoon(final Integer id) {
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);

        final ActieModel actie = new ActieModel(new SoortActieAttribuut(SoortActie.DUMMY), administratieveHandelingModel,
            null, new DatumEvtDeelsOnbekendAttribuut(DatumAttribuut.gisteren()), null,
            administratieveHandelingModel.getTijdstipRegistratie(), null);

        builder.nieuwAfgeleidAdministratiefRecord(actie).sorteervolgorde(new Byte(id.toString())).eindeRecord();

        final PersoonHisVolledigImpl persoon = builder.build();
        ReflectionTestUtils.setField(persoon, "iD", id);

        return persoon;
    }

    /**
     * Maakt een vul bericht.
     *
     * @return het vul bericht
     */
    private VolledigBericht maakVolledigBericht(final List<PersoonHisVolledigView> bijgehoudenPersonenParam) {
        final VolledigBericht volledigBericht =
            new VolledigBericht(new AdministratieveHandelingSynchronisatie(AdministratieveHandelingTestBouwer.getTestAdministratieveHandeling()));
        volledigBericht.getAdministratieveHandeling().setBijgehoudenPersonen(bijgehoudenPersonenParam);
        return volledigBericht;
    }

    /**
     * Maakt een vul bericht.
     *
     * @return het vul bericht
     */
    private MutatieBericht maakMutatieBericht(final List<PersoonHisVolledigView> bijgehoudenPersonenParam) {
        final MutatieBericht mutatieBericht =
            new MutatieBericht(new AdministratieveHandelingSynchronisatie(AdministratieveHandelingTestBouwer.getTestAdministratieveHandeling()));
        mutatieBericht.getAdministratieveHandeling().setBijgehoudenPersonen(bijgehoudenPersonenParam);
        return mutatieBericht;
    }
}
