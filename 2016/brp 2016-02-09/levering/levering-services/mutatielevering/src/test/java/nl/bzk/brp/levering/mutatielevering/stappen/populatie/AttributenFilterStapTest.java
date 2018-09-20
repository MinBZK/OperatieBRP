/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.populatie;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerp;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerpImpl;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContext;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContextImpl;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieVerwerkingResultaat;
import nl.bzk.brp.levering.business.toegang.gegevensfilter.AttributenFilterService;
import nl.bzk.brp.levering.excepties.ExpressieExceptie;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienstbundel;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Leveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Protocolleringsniveau;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstbundelBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestLeveringsautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Rol;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Stelsel;
import nl.bzk.brp.model.basis.Attribuut;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import support.AdministratieveHandelingTestBouwer;

@RunWith(MockitoJUnitRunner.class)
public class AttributenFilterStapTest {

    @InjectMocks
    private final AttributenFilterStap attributenFilterStap = new AttributenFilterStap();

    @Mock
    private AttributenFilterService attributenFilterService;

    private final AdministratieveHandelingModel administratieveHandelingModel = AdministratieveHandelingTestBouwer.getTestAdministratieveHandeling();

    private LeveringautorisatieStappenOnderwerp onderwerp;
    private final LeveringsautorisatieVerwerkingContext  context   = new LeveringsautorisatieVerwerkingContextImpl(null, null, null, null, null);
    private final LeveringautorisatieVerwerkingResultaat resultaat = new LeveringautorisatieVerwerkingResultaat();

    @Before
    public final void setup() {

        final Dienst dienst = TestDienstBuilder.maker().maak();
        final Dienstbundel dienstbundel = TestDienstbundelBuilder.maker().metDiensten(dienst).maak();
        final Leveringsautorisatie leveringsautorisatie = TestLeveringsautorisatieBuilder.maker().metNaam("testabo").metPopulatiebeperking("WAAR")
            .metProtocolleringsniveau(Protocolleringsniveau
                .GEEN_BEPERKINGEN).metDatumIngang(new DatumAttribuut(20150101)).metDienstbundels(dienstbundel).maak();
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie
            (leveringsautorisatie).metDummyGeautoriseerde().maak();
        final Leveringinformatie leveringAutorisatie = new Leveringinformatie(toegangLeveringsautorisatie, dienst);
        onderwerp = new LeveringautorisatieStappenOnderwerpImpl(leveringAutorisatie, administratieveHandelingModel.getID(), Stelsel.BRP);
    }

    @Test
    public final void testVoerStapUit() throws ExpressieExceptie {
        final boolean stapResultaat = attributenFilterStap.voerStapUit(onderwerp, context, resultaat);

        assertTrue(stapResultaat);
        verify(attributenFilterService).zetMagGeleverdWordenVlaggen(anyListOf(PersoonHisVolledigView.class), any(Dienst.class), any(Rol.class),
            anyMap());
    }

    @Test
    public final void testVoerStapUitMetFout() throws ExpressieExceptie {
        when(attributenFilterService.zetMagGeleverdWordenVlaggen(anyListOf(PersoonHisVolledigView.class), any(Dienst.class), any(Rol.class),
            anyMap())).thenThrow(new ExpressieExceptie("Test!"));

        final boolean stapResultaat = attributenFilterStap.voerStapUit(onderwerp, context, resultaat);
        Assert.assertFalse(stapResultaat);
    }

    @Test
    public final void testVoerNabewerkingStapUit() {
        final List<Attribuut> testLijst = new ArrayList<>();
        context.setAttributenDieGeleverdMogenWorden(testLijst);

        attributenFilterStap.voerNabewerkingStapUit(onderwerp, context, resultaat);

        verify(attributenFilterService).resetMagGeleverdWordenVlaggen(testLijst);
    }

    @Test
    public final void testVoerNabewerkingStapUitZonderAttributenOpContext() {
        context.setAttributenDieGeleverdMogenWorden(null);

        attributenFilterStap.voerNabewerkingStapUit(onderwerp, context, resultaat);

        verifyZeroInteractions(attributenFilterService);
    }

}
