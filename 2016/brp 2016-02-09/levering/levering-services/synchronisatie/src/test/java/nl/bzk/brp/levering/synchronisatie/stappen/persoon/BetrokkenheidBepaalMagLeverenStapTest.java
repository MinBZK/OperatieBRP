/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.synchronisatie.stappen.persoon;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.levering.business.bepalers.BetrokkenheidMagLeverenBepalerService;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.levering.synchronisatie.dto.synchronisatie.SynchronisatieBerichtContext;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.SoortDienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.PartijAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandeling;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortAdministratieveHandelingAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.AdministratieveHandelingSynchronisatie;
import nl.bzk.brp.model.levering.VolledigBericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonAntwoordPersoon;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import nl.bzk.brp.webservice.business.stappen.BerichtenIds;
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

    private SynchronisatieBerichtContext context;

    @Before
    public void onInit() {
        final Partij partij = TestPartijBuilder.maker().metId(1).maak();
        final ToegangLeveringsautorisatie toegangLeveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().maak();
        final Dienst dienst = TestDienstBuilder.maker().metSoortDienst(SoortDienst.MUTATIELEVERING_OP_BASIS_VAN_DOELBINDING).maak();

        final Leveringinformatie leveringAutorisatie = new Leveringinformatie(toegangLeveringsautorisatie, dienst);

        final BerichtenIds berichtenIds = new BerichtenIds(1L, 2L);
        context = new SynchronisatieBerichtContext(berichtenIds, null, null, null);
        context.setLeveringinformatie(leveringAutorisatie);

        final AdministratieveHandelingModel administratieveHandelingModel =
            new AdministratieveHandelingModel(new SoortAdministratieveHandelingAttribuut(
                SoortAdministratieveHandeling.VERHUIZING_BINNENGEMEENTELIJK), new PartijAttribuut(TestPartijBuilder.maker().maak()), null,
                DatumTijdAttribuut.nu());
        final AdministratieveHandelingSynchronisatie admHndSynchr = new AdministratieveHandelingSynchronisatie(administratieveHandelingModel);
        final List<PersoonHisVolledigView> bijgehoudenPersonen = new ArrayList<>();
        bijgehoudenPersonen.add(new PersoonHisVolledigView(TestPersoonJohnnyJordaan.maak(), null));
        bijgehoudenPersonen.add(new PersoonHisVolledigView(TestPersoonAntwoordPersoon.maakAntwoordPersoon(), null));
        admHndSynchr.setBijgehoudenPersonen(bijgehoudenPersonen);

        final VolledigBericht volledigBericht = new VolledigBericht(admHndSynchr);
        volledigBericht.setAdministratieveHandeling(admHndSynchr);

        context.setVolledigBericht(volledigBericht);
    }

    @Test
    public void testVoerStapUit() {
        final boolean stapResultaat = betrokkenheidBepaalMagLeverenStap.voerStapUit(null, context, null);
        Assert.assertTrue(stapResultaat);

        // 2 personen
        Mockito.verify(betrokkenheidMagLeverenBepalerService, Mockito.times(2)).bepaalMagLeveren(Mockito.any(PersoonHisVolledigView.class),
            Mockito.any(Dienst.class), Mockito.anyBoolean());
    }
}
