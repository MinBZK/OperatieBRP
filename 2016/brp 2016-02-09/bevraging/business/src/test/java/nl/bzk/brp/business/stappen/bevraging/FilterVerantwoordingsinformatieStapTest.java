/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.stappen.bevraging;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.business.dto.bevraging.BevragingResultaat;
import nl.bzk.brp.levering.business.toegang.gegevensfilter.VerantwoordingsinformatieFilter;
import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
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
public class FilterVerantwoordingsinformatieStapTest {

    @InjectMocks
    private final FilterVerantwoordingsinformatieStap stap = new FilterVerantwoordingsinformatieStap();

    @Mock
    private VerantwoordingsinformatieFilter verantwoordingsinformatieFilter;

    private final BevragingResultaat resultaat = new BevragingResultaat(null);

    @Before
    public final void init() {
        final List<PersoonHisVolledigView> gevondenPersonen = new ArrayList<>();
        final PersoonHisVolledigView persoon1 = new PersoonHisVolledigView(TestPersoonJohnnyJordaan.maak(), null);
        gevondenPersonen.add(persoon1);
        final PersoonHisVolledigView persoon2 = new PersoonHisVolledigView(TestPersoonJohnnyJordaan.maak(), null);
        gevondenPersonen.add(persoon2);
        final PersoonHisVolledigView persoon3 = new PersoonHisVolledigView(TestPersoonJohnnyJordaan.maak(), null);
        gevondenPersonen.add(persoon3);
        resultaat.voegGevondenPersonenToe(gevondenPersonen);
    }

    @Test
    public final void testVoerStapUit() {
        final boolean stapResultaat = stap.voerStapUit(null, Mockito.mock(BevragingBerichtContextBasis.class), resultaat);

        Assert.assertTrue(stapResultaat);
    }

    @Test
    public final void testVoerStapUitGooitExceptie() {
        Mockito.doThrow(Exception.class)
            .when(verantwoordingsinformatieFilter).filter(Mockito.any(PersoonHisVolledigView.class), Mockito.any(Leveringinformatie.class));
        final boolean stapResultaat = stap.voerStapUit(null, null, resultaat);

        Assert.assertFalse(stapResultaat);
    }
}
