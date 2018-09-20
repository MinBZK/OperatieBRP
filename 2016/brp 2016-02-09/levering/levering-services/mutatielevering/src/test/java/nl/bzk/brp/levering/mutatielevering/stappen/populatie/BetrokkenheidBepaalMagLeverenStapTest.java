/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.mutatielevering.stappen.populatie;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.levering.business.bepalers.BetrokkenheidMagLeverenBepalerService;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerp;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieStappenOnderwerpImpl;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContext;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringsautorisatieVerwerkingContextImpl;
import nl.bzk.brp.levering.business.stappen.populatie.LeveringautorisatieVerwerkingResultaat;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
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
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.AdministratieveHandelingSynchronisatie;
import nl.bzk.brp.model.levering.MutatieBericht;
import nl.bzk.brp.model.levering.SynchronisatieBericht;
import nl.bzk.brp.model.levering.VolledigBericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonAntwoordPersoon;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BetrokkenheidBepaalMagLeverenStapTest {

    @InjectMocks
    private final BetrokkenheidBepaalMagLeverenStap betrokkenheidBepaalMagLeverenStap = new BetrokkenheidBepaalMagLeverenStap();

    @Mock
    private BetrokkenheidMagLeverenBepalerService betrokkenheidMagLeverenBepalerService;

    private LeveringautorisatieStappenOnderwerp    onderwerp;
    private LeveringsautorisatieVerwerkingContext  context;
    private LeveringautorisatieVerwerkingResultaat resultaat;

    @Before
    public void onInit() {
        final Leveringsautorisatie leveringsautorisatie = TestLeveringsautorisatieBuilder.maker().metNaam("TestLeveringsautorisatie").metPopulatiebeperking
            ("WAAR")
            .metProtocolleringsniveau(Protocolleringsniveau
                .GEEN_BEPERKINGEN).metDatumIngang(DatumAttribuut.gisteren()).maak();
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().metLeveringsautorisatie
            (leveringsautorisatie).maak();

        final Leveringinformatie leveringAutorisatie = new Leveringinformatie(toegangLeveringsautorisatie, null);
        onderwerp = new LeveringautorisatieStappenOnderwerpImpl(leveringAutorisatie, 1L, Stelsel.BRP);

        final AdministratieveHandelingModel administratieveHandelingModel =
            new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                SoortAdministratieveHandeling.VERHUIZING_BINNENGEMEENTELIJK), new PartijAttribuut(TestPartijBuilder.maker().maak()), null,
                DatumTijdAttribuut.nu());

        context = new LeveringsautorisatieVerwerkingContextImpl(null, null, null, null, null);
        final List<SynchronisatieBericht> leveringberichten = new ArrayList<>();
        final AdministratieveHandelingSynchronisatie admHndSynchr =
            new AdministratieveHandelingSynchronisatie(administratieveHandelingModel);
        final List<PersoonHisVolledigView> bijgehoudenPersonen = new ArrayList<>();
        bijgehoudenPersonen.add(new PersoonHisVolledigView(TestPersoonJohnnyJordaan.maak(), null));
        bijgehoudenPersonen.add(new PersoonHisVolledigView(TestPersoonAntwoordPersoon.maakAntwoordPersoon(), null));
        admHndSynchr.setBijgehoudenPersonen(bijgehoudenPersonen);

        final SynchronisatieBericht mutatieBericht = new MutatieBericht(admHndSynchr);
        mutatieBericht.setAdministratieveHandeling(admHndSynchr);
        leveringberichten.add(mutatieBericht);

        final SynchronisatieBericht volledigBericht = new VolledigBericht(admHndSynchr);
        volledigBericht.setAdministratieveHandeling(admHndSynchr);
        leveringberichten.add(volledigBericht);

        context.setLeveringBerichten(leveringberichten);

        resultaat = new LeveringautorisatieVerwerkingResultaat(new ArrayList<Melding>());
    }

    @Test
    public void testVoerStapUitMetTweePersonen() {
        final boolean stapResultaat = betrokkenheidBepaalMagLeverenStap.voerStapUit(onderwerp, context, resultaat);
        Assert.assertTrue(stapResultaat);

        // Personen komen in mutatiebericht en voleldigbericht voor, 2 + 2
        Mockito.verify(betrokkenheidMagLeverenBepalerService, Mockito.times(4))
            .bepaalMagLeveren(Mockito.any(PersoonHisVolledigView.class), Mockito.any(Dienst.class), Mockito.anyBoolean());
    }

    @Test
    public void testVoerStapUitZonderPersoon() {
        final boolean stapResultaat = betrokkenheidBepaalMagLeverenStap.voerStapUit(onderwerp, context, this.resultaat);
        Assert.assertTrue(stapResultaat);
    }
}
