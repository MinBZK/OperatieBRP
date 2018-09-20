/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.populatie;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerp;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerpImpl;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContext;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContextImpl;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieVerwerkingResultaat;
import nl.bzk.brp.levering.business.toegang.voorkomenfilter.VoorkomenFilterService;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Protocolleringsniveau;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Stelsel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.AdministratieveHandelingSynchronisatie;
import nl.bzk.brp.model.levering.MutatieBericht;
import nl.bzk.brp.model.levering.SynchronisatieBericht;
import nl.bzk.brp.model.levering.VolledigBericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import support.AdministratieveHandelingTestBouwer;

@RunWith(MockitoJUnitRunner.class)
public class VoorkomenFilterStapTest {

    @Mock
    private VoorkomenFilterService voorkomenFilterService;

    @InjectMocks
    private VoorkomenFilterStap voorkomenFilterStap;

    private final AdministratieveHandelingModel administratieveHandelingModel = AdministratieveHandelingTestBouwer.getTestAdministratieveHandeling();

    private LeveringautorisatieStappenOnderwerp onderwerp;
    private final LeveringsautorisatieVerwerkingContext  context                 = new LeveringsautorisatieVerwerkingContextImpl(null, null, null, null,
        null);
    private final LeveringautorisatieVerwerkingResultaat resultaat               = new LeveringautorisatieVerwerkingResultaat();
    private final List<PersoonHisVolledigView>           persoonHisVolledigViews = new ArrayList<>();
    private List<SynchronisatieBericht>            leverberichten;
    private AdministratieveHandelingSynchronisatie admhnd;

    @Mock
    private Dienst dienst;

    @Before
    public final void setup() {
        final Leveringsautorisatie leveringsautorisatie = TestLeveringsautorisatieBuilder.maker().metNaam("testabo").metPopulatiebeperking("WAAR")
            .metProtocolleringsniveau(Protocolleringsniveau
                .GEEN_BEPERKINGEN).metDatumIngang(new DatumAttribuut(20150101)).maak();
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie
            (leveringsautorisatie).maak();

        final Leveringinformatie leveringAutorisatie = new Leveringinformatie(toegangLeveringsautorisatie, dienst);
        onderwerp = new LeveringautorisatieStappenOnderwerpImpl(leveringAutorisatie, administratieveHandelingModel.getID(), Stelsel.BRP);

        leverberichten = new ArrayList<>();
        context.setBijgehoudenPersoonViews(persoonHisVolledigViews);
        persoonHisVolledigViews.add(new PersoonHisVolledigView(TestPersoonJohnnyJordaan.maak(), null));
        context.setLeveringBerichten(leverberichten);
        final Map<Integer, Map<String, List<Attribuut>>> personenAttributenMap = new HashMap<>();
        context.setPersoonAttributenMap(personenAttributenMap);

        final AdministratieveHandelingModel admhndModel = mock(AdministratieveHandelingModel.class);
        when(admhndModel.getSoort()).thenReturn(new SoortAdministratieveHandelingAttribuut(SoortAdministratieveHandeling.VERHUIZING_NAAR_BUITENLAND));
        when(admhndModel.getPartij()).thenReturn(new PartijAttribuut(TestPartijBuilder.maker().maak()));

        admhnd = new AdministratieveHandelingSynchronisatie(admhndModel);
        admhnd.setBijgehoudenPersonen(persoonHisVolledigViews);
    }

    @Test
    public final void testVoerStapUitMetMutatieBericht() throws ExpressieExceptie {
        leverberichten.add(new MutatieBericht(admhnd));
        final boolean stapResultaat = voorkomenFilterStap.voerStapUit(onderwerp, context, resultaat);

        assertTrue(stapResultaat);
        verify(voorkomenFilterService).voerVoorkomenFilterUitVoorMutatieLevering(any(PersoonHisVolledigView.class), eq(dienst), anyMap());
    }

    @Test
    public final void testVoerStapUitMetVolledigBericht() throws ExpressieExceptie {
        leverberichten.add(new VolledigBericht(admhnd));
        final boolean stapResultaat = voorkomenFilterStap.voerStapUit(onderwerp, context, resultaat);

        assertTrue(stapResultaat);
        verify(voorkomenFilterService).voerVoorkomenFilterUit(any(PersoonHisVolledigView.class), eq(dienst), anyMap());
    }

    @Test
    public final void testVoerStapUitMetFout() throws ExpressieExceptie {
        leverberichten.add(new VolledigBericht(admhnd));

        doThrow(ExpressieExceptie.class).when(voorkomenFilterService).voerVoorkomenFilterUit(any(PersoonHisVolledigView.class), eq(dienst), anyMap());

        final boolean stapResultaat = voorkomenFilterStap.voerStapUit(onderwerp, context, resultaat);
        Assert.assertFalse(stapResultaat);
    }

    @Test
    public final void testVoerNabewerkingStapUit() {
        voorkomenFilterStap.voerNabewerkingStapUit(onderwerp, context, resultaat);
    }
}
