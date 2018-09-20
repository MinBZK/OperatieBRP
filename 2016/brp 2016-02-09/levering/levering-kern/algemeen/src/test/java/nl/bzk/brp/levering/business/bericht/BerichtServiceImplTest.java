/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.bericht;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.levering.business.bepalers.BerichtVerwerkingssoortZetter;
import nl.bzk.brp.levering.business.bepalers.LegeBerichtBepaler;
import nl.bzk.brp.levering.dataaccess.repository.alleenlezen.support.AdministratieveHandelingTestBouwer;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.levering.AdministratieveHandelingSynchronisatie;
import nl.bzk.brp.model.levering.MutatieBericht;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BerichtServiceImplTest {

    @Mock
    private BerichtVerwerkingssoortZetter berichtVerwerkingssoortToevoeger;

    @Mock
    private BerichtVerwerkingssoortZetter berichtVerwerkingssoortVerwijderaar;

    @Mock
    private LegeBerichtBepaler legeBerichtBepaler;

    @InjectMocks
    private final BerichtService berichtService = new BerichtServiceImpl();

    @Test
    public final void testVerwijderVerwerkingssoortenUitPersonen() {
        final PersoonHisVolledigView persoonHisVolledigView1 = new PersoonHisVolledigView(TestPersoonJohnnyJordaan.maak(), null);
        final List<PersoonHisVolledigView> bijgehoudenPersonen = new ArrayList<>();
        bijgehoudenPersonen.add(persoonHisVolledigView1);

        berichtService.verwijderVerwerkingssoortenUitPersonen(bijgehoudenPersonen);

        verify(berichtVerwerkingssoortVerwijderaar).voegVerwerkingssoortenToe(persoonHisVolledigView1, null);
    }

    @Test
    public final void testFilterLegePersonen() {
        // given
        final List<PersoonHisVolledigView> betrokkenPersonenViews = new ArrayList<>();

        final PersoonHisVolledigView persoonHisVolledigView1 = new PersoonHisVolledigView(TestPersoonJohnnyJordaan.maak(), null);
        final PersoonHisVolledigView persoonHisVolledigView2 = new PersoonHisVolledigView(TestPersoonJohnnyJordaan.maak(), null);

        betrokkenPersonenViews.add(persoonHisVolledigView1);
        betrokkenPersonenViews.add(persoonHisVolledigView2);

        final AdministratieveHandelingModel administratieveHandelingModel =
                AdministratieveHandelingTestBouwer.getTestAdministratieveHandeling();

        final AdministratieveHandelingSynchronisatie ahKennisgeving = new AdministratieveHandelingSynchronisatie(administratieveHandelingModel);
        ahKennisgeving.setBijgehoudenPersonen(betrokkenPersonenViews);
        final MutatieBericht bericht = new MutatieBericht(ahKennisgeving);

        when(legeBerichtBepaler.magPersoonGeleverdWorden(persoonHisVolledigView1)).thenReturn(Boolean.TRUE);
        when(legeBerichtBepaler.magPersoonGeleverdWorden(persoonHisVolledigView2)).thenReturn(Boolean.FALSE);

        // when
        berichtService.filterLegePersonen(bericht);

        // then
        verify(legeBerichtBepaler, times(2)).magPersoonGeleverdWorden(Matchers.any(PersoonHisVolledigView.class));
        assertThat(bericht.getAdministratieveHandeling().getBijgehoudenPersonen().size(), is(1));
        assertThat(bericht.getAdministratieveHandeling().getBijgehoudenPersonen().get(0), equalTo(persoonHisVolledigView1));
    }
}
