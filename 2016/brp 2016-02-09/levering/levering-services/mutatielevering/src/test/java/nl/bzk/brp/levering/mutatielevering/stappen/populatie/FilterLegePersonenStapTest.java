/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.populatie;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.bzk.brp.levering.business.bericht.BerichtService;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerp;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContext;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContextImpl;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieVerwerkingResultaat;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Protocolleringsniveau;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.AdministratieveHandelingSynchronisatie;
import nl.bzk.brp.model.levering.MutatieBericht;
import nl.bzk.brp.model.levering.SynchronisatieBericht;
import nl.bzk.brp.model.levering.VolledigBericht;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.apache.commons.collections.Predicate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class FilterLegePersonenStapTest {

    @InjectMocks
    private final FilterLegePersonenStap filterLegePersonenStap = new FilterLegePersonenStap();

    @Mock
    private BerichtService berichtService;

    @Mock
    private LeveringautorisatieStappenOnderwerp onderwerp;

    private final PersoonHisVolledigImpl johnny                 = TestPersoonJohnnyJordaan.maak();
    private       PersoonHisVolledigView persoonHisVolledigView = new PersoonHisVolledigView(johnny, null);

    private final LeveringsautorisatieVerwerkingContext  context   = new LeveringsautorisatieVerwerkingContextImpl(null, null, null, null, null);
    private final LeveringautorisatieVerwerkingResultaat resultaat = new LeveringautorisatieVerwerkingResultaat();

    private final Predicate nietPredikaat = new Predicate() {

        @Override
        @SuppressWarnings("unchecked")
        public boolean evaluate(final Object object) {
            return false;
        }
    };

    @Before
    public void init() {
        Mockito.when(onderwerp.getLeveringinformatie()).thenReturn(geefLeveringinformatie());
    }

    @Test
    public void testVoerStapUitVolledigBericht() {
        final AdministratieveHandelingSynchronisatie ahKennisgeving = mock(AdministratieveHandelingSynchronisatie.class);
        final SynchronisatieBericht bericht = new VolledigBericht(ahKennisgeving);
        context.setLeveringBerichten(new ArrayList<>(Collections.singletonList(bericht)));


        final boolean stapResultaat = filterLegePersonenStap.voerStapUit(onderwerp, context, resultaat);
        assertTrue(stapResultaat);
        assertThat(context.getLeveringBerichten().size(), is(1));
    }

    @Test
    public void testVoerStapUitMutatieBerichtLeeg() {
        final AdministratieveHandelingSynchronisatie ahKennisgeving = mock(AdministratieveHandelingSynchronisatie.class);
        final SynchronisatieBericht bericht = new MutatieBericht(ahKennisgeving);
        Mockito.when(ahKennisgeving.getBijgehoudenPersonen()).thenReturn(new ArrayList<PersoonHisVolledigView>());

        context.setLeveringBerichten(new ArrayList<>(Collections.singletonList(bericht)));

        final boolean stapResultaat = filterLegePersonenStap.voerStapUit(onderwerp, context, resultaat);
        assertFalse(stapResultaat);
        assertThat(context.getLeveringBerichten().size(), is(0));
    }

    @Test
    public void testVoerStapUitMutatieBerichtGevuld() {
        final AdministratieveHandelingSynchronisatie ahKennisgeving = mock(AdministratieveHandelingSynchronisatie.class);
        final SynchronisatieBericht bericht = new MutatieBericht(ahKennisgeving);
        final List<PersoonHisVolledigView> persoonHisVolledigViews = new ArrayList<>();
//        persoonHisVolledigView = new PersoonHisVolledigView(johnny, new AdministratieveHandelingDeltaPredikaat(1L));
        persoonHisVolledigViews.add(persoonHisVolledigView);
        Mockito.when(ahKennisgeving.getBijgehoudenPersonen()).thenReturn(persoonHisVolledigViews);

        context.setLeveringBerichten(new ArrayList<>(Collections.singletonList(bericht)));

        final boolean stapResultaat = filterLegePersonenStap.voerStapUit(onderwerp, context, resultaat);
        assertTrue(stapResultaat);
        assertThat(context.getLeveringBerichten().size(), is(1));
    }

    @Test
    public void testVoerStapUitMutatieBerichtGevuldLogLeegPersoon() {
        final AdministratieveHandelingSynchronisatie ahKennisgeving = mock(AdministratieveHandelingSynchronisatie.class);
        final SynchronisatieBericht bericht = new MutatieBericht(ahKennisgeving);
        final List<PersoonHisVolledigView> persoonHisVolledigViews = new ArrayList<>();
        persoonHisVolledigView = new PersoonHisVolledigView(johnny, nietPredikaat);
        persoonHisVolledigViews.add(persoonHisVolledigView);
        Mockito.when(ahKennisgeving.getBijgehoudenPersonen()).thenReturn(persoonHisVolledigViews);


        context.setLeveringBerichten(new ArrayList<>(Collections.singletonList(bericht)));

        final boolean stapResultaat = filterLegePersonenStap.voerStapUit(onderwerp, context, resultaat);
        assertTrue(stapResultaat);
        assertThat(context.getLeveringBerichten().size(), is(1));
    }

    /**
     * Geeft een levering autorisatie.
     *
     * @return de levering autorisatie
     */
    private Leveringinformatie geefLeveringinformatie() {
        final Leveringsautorisatie leveringsautorisatie = TestLeveringsautorisatieBuilder.maker().metNaam("testabo").metPopulatiebeperking("WAAR")
            .metProtocolleringsniveau(Protocolleringsniveau
                .GEEN_BEPERKINGEN).metDatumIngang(new DatumAttribuut(20150101)).maak();
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie
            (leveringsautorisatie).maak();

        final Dienst dienst = TestDienstBuilder.maker().metSoortDienst(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING).maak();


        return new Leveringinformatie(toegangLeveringsautorisatie, dienst);
    }


}
