/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.afnemerindicaties.stappen.persoon;

import nl.bzk.brp.levering.afnemerindicaties.service.OnderhoudAfnemerindicatiesBerichtContext;
import nl.bzk.brp.levering.afnemerindicaties.service.OnderhoudAfnemerindicatiesBerichtContextImpl;
import nl.bzk.brp.levering.afnemerindicaties.service.OnderhoudAfnemerindicatiesResultaat;
import nl.bzk.brp.levering.business.bepalers.BetrokkenheidMagLeverenBepalerService;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumTijdAttribuut;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestToegangLeveringautorisatieBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangLeveringsautorisatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.*;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.AdministratieveHandelingSynchronisatie;
import nl.bzk.brp.model.levering.VolledigBericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.synchronisatie.RegistreerAfnemerindicatieBericht;
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

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class BetrokkenheidBepaalMagLeverenStapTest {

    @InjectMocks
    private final BetrokkenheidBepaalMagLeverenStap betrokkenheidBepaalMagLeverenStap = new BetrokkenheidBepaalMagLeverenStap();

    @Mock
    private BetrokkenheidMagLeverenBepalerService betrokkenheidMagLeverenBepalerService;

    private RegistreerAfnemerindicatieBericht        onderwerp;
    private OnderhoudAfnemerindicatiesBerichtContext context;
    private OnderhoudAfnemerindicatiesResultaat      resultaat;

    @Before
    public void onInit() {
        final Partij partij = TestPartijBuilder.maker().metId(1).maak();
        final ToegangLeveringsautorisatie leveringsautorisatie = TestToegangLeveringautorisatieBuilder.maker().maak();

        final Dienst dienst = TestDienstBuilder.dummy();

        final Leveringinformatie leveringAutorisatie = new Leveringinformatie(leveringsautorisatie, dienst);

        final BerichtenIds berichtenIds = new BerichtenIds(1L, 2L);
        context = new OnderhoudAfnemerindicatiesBerichtContextImpl(berichtenIds, null, null, null);
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
        final boolean stapResultaat = betrokkenheidBepaalMagLeverenStap.voerStapUit(onderwerp, context, resultaat);
        Assert.assertTrue(stapResultaat);
        Mockito.verify(betrokkenheidMagLeverenBepalerService).bepaalMagLeveren(Mockito.any(PersoonHisVolledigView.class), Mockito.any(Dienst.class),
            Mockito.anyBoolean());
    }
}
