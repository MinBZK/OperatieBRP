/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.populatie;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.business.stappen.AbstractStap;
import nl.bzk.brp.levering.business.bericht.MarshallService;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerp;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerpImpl;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContext;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContextImpl;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieVerwerkingResultaat;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.mutatielevering.excepties.StapExceptie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPartij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortPersoon;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Stelsel;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.predikaat.AdministratieveHandelingDeltaPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.AdministratieveHandelingSynchronisatie;
import nl.bzk.brp.model.levering.MutatieBericht;
import nl.bzk.brp.model.levering.SynchronisatieBericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.hisvolledig.kern.PersoonHisVolledigImplBuilder;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import support.AdministratieveHandelingTestBouwer;

@RunWith(MockitoJUnitRunner.class)
public class MaakUitgaandBerichtStapTest {

    private static final Integer ID = 123;
    private LeveringsautorisatieVerwerkingContext context;
    private final LeveringautorisatieVerwerkingResultaat afnemerVerwerkingResultaat = new LeveringautorisatieVerwerkingResultaat();
    private LeveringautorisatieStappenOnderwerp onderwerp;

    private final Leveringinformatie leveringAutorisatie = mock(Leveringinformatie.class);

    @Mock
    private MarshallService marshallService;

    @InjectMocks
    private final MaakUitgaandBerichtStap maakUitgaandBerichtStap = new MaakUitgaandBerichtStap();

    @Before
    public final void setup() {
        // Context van de verwerking van de A.H.
        final AdministratieveHandelingModel administratieveHandelingModel =
            AdministratieveHandelingTestBouwer.getTestAdministratieveHandeling();

        // Het Java-model van het uitgaande bericht
        final MutatieBericht kennisgevingBericht =
            new MutatieBericht(new AdministratieveHandelingSynchronisatie(administratieveHandelingModel));
        final PersoonHisVolledigImplBuilder builder = new PersoonHisVolledigImplBuilder(SoortPersoon.INGESCHREVENE);
        final PersoonHisVolledigImpl persoon = builder.build();
        final AdministratieveHandelingDeltaPredikaat deltaPredikaat = new AdministratieveHandelingDeltaPredikaat(
            administratieveHandelingModel.getID());
        final PersoonHisVolledigView persoonView = new PersoonHisVolledigView(persoon, deltaPredikaat);
        ReflectionTestUtils.setField(persoon, "iD", ID);
        final List<PersoonHisVolledigView> bijgehoudenPersonen = new ArrayList<>();
        bijgehoudenPersonen.add(persoonView);
        kennisgevingBericht.getAdministratieveHandeling().setBijgehoudenPersonen(bijgehoudenPersonen);
        context = new LeveringsautorisatieVerwerkingContextImpl(administratieveHandelingModel, null, null, null, null);
        context.setLeveringBerichten(new ArrayList<SynchronisatieBericht>());
        context.getLeveringBerichten().add(kennisgevingBericht);

        onderwerp = new LeveringautorisatieStappenOnderwerpImpl(leveringAutorisatie, administratieveHandelingModel.getID(), Stelsel.BRP);
    }

    @Test
    public final void testVoerStapUit() throws JiBXException {
        when(marshallService.maakBericht(any(MutatieBericht.class))).thenReturn("");

        final boolean resultaat =
            maakUitgaandBerichtStap.voerStapUit(onderwerp, context, afnemerVerwerkingResultaat);

        Assert.assertEquals(AbstractStap.DOORGAAN, resultaat);
    }

    @Test(expected = StapExceptie.class)
    public final void stapKanGeenXmlBerichtMaken() throws JiBXException {
        // given
        when(marshallService.maakBericht(any(MutatieBericht.class))).thenThrow(new JiBXException(""));

        // when
        maakUitgaandBerichtStap.voerStapUit(onderwerp, context, afnemerVerwerkingResultaat);
    }

    @Test
    public final void testVoerNabewerkingStapUit() {
        final SoortAdministratieveHandelingAttribuut soortAdministratieveHandeling =
            new SoortAdministratieveHandelingAttribuut(SoortAdministratieveHandeling.DUMMY);
        final PartijAttribuut partij = new PartijAttribuut(
            TestPartijBuilder.maker().metNaam("gem").metSoort(SoortPartij.GEMEENTE).metCode(34).maak());

        final List<SynchronisatieBericht> leveringBerichten = context.getLeveringBerichten();
        leveringBerichten.add(new MutatieBericht(new AdministratieveHandelingSynchronisatie(
            new AdministratieveHandelingModel(soortAdministratieveHandeling, partij, null, null))));

        maakUitgaandBerichtStap.voerNabewerkingStapUit(onderwerp, context, afnemerVerwerkingResultaat);
    }

}
