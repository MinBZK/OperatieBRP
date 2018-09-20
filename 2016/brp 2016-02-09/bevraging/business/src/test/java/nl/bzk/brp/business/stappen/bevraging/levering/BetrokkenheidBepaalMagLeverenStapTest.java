/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.bevraging.levering;

import nl.bzk.brp.business.dto.bevraging.BevragingResultaat;
import nl.bzk.brp.business.stappen.bevraging.BevragingBerichtContextBasis;
import nl.bzk.brp.levering.business.bepalers.BetrokkenheidMagLeverenBepalerService;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.Dienst;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.TestDienstBuilder;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.TestPartijBuilder;
import nl.bzk.brp.model.basis.CommunicatieIdMap;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.validatie.Melding;
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

@RunWith(MockitoJUnitRunner.class)
public class BetrokkenheidBepaalMagLeverenStapTest {

    @InjectMocks
    private final BetrokkenheidBepaalMagLeverenStap betrokkenheidBepaalMagLeverenStap = new BetrokkenheidBepaalMagLeverenStap();

    @Mock
    private BetrokkenheidMagLeverenBepalerService betrokkenheidMagLeverenBepalerService;

    private BevragingBerichtContextBasis context;
    private BevragingResultaat resultaat;

    @Before
    public void onInit() {
        final BerichtenIds berichtenIds = new BerichtenIds(1L, 2L);
        final Partij afzender = TestPartijBuilder.maker().metId(1).maak();
        final CommunicatieIdMap identificeerbaarObj = new CommunicatieIdMap();
        context = new BevragingBerichtContextBasis(berichtenIds, afzender, "Berichtref", identificeerbaarObj);
        context.setLeveringinformatie(new Leveringinformatie(null, TestDienstBuilder.maker().maak()));

        resultaat = new BevragingResultaat(new ArrayList<Melding>());
    }

    @Test
    public void testVoerStapUitMetTweePersonen() {
        final PersoonHisVolledigView persoon = new PersoonHisVolledigView(TestPersoonJohnnyJordaan.maak(), null);
        final PersoonHisVolledigView persoon2 = new PersoonHisVolledigView(TestPersoonAntwoordPersoon.maakAntwoordPersoon(), null);
        resultaat.voegGevondenPersoonToe(persoon);
        resultaat.voegGevondenPersoonToe(persoon2);

        final boolean stapResultaat = betrokkenheidBepaalMagLeverenStap.voerStapUit(null, context, this.resultaat);
        Assert.assertTrue(stapResultaat);
        // 2 personen
        Mockito.verify(betrokkenheidMagLeverenBepalerService, Mockito.times(2))
            .bepaalMagLeveren(Mockito.any(PersoonHisVolledigView.class), Mockito.any(Dienst.class), Mockito.anyBoolean());
    }

    @Test
    public void testVoerStapUitZonderPersoon() {
        final boolean stapResultaat = betrokkenheidBepaalMagLeverenStap.voerStapUit(null, context, this.resultaat);
        Assert.assertTrue(stapResultaat);
    }
}
