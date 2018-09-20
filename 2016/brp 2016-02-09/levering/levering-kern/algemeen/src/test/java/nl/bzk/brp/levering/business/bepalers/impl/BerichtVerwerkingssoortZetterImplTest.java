/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.bepalers.impl;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import nl.bzk.brp.levering.business.bepalers.BerichtVerwerkingssoortBepaler;
import nl.bzk.brp.levering.business.bepalers.BerichtVerwerkingssoortZetter;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingssoort;
import nl.bzk.brp.model.algemeen.attribuuttype.kern.DatumAttribuut;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.impl.kern.PersoonHisVolledigImpl;
import nl.bzk.brp.model.hisvolledig.predikaat.HistorieVanafPredikaat;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.BetrokkenheidHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.RelatieHisVolledigView;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class BerichtVerwerkingssoortZetterImplTest {

    private static final Long ADMINISTRATIEVE_HANDELING_ID = 1L;

    private BerichtVerwerkingssoortZetter berichtVerwerkingssoortZetter;

    @Mock
    private BerichtVerwerkingssoortBepaler berichtVerwerkingssoortBepaler;

    private final PersoonHisVolledigImpl johnnyJordaanHisVolledig = TestPersoonJohnnyJordaan.maak();

    private final PersoonHisVolledigView johnnyJordaanHisVolledigView =
        new PersoonHisVolledigView(johnnyJordaanHisVolledig, HistorieVanafPredikaat.geldigOpEnNa(new DatumAttribuut(20000101)));

    private final PersoonHisVolledigView persoonHisVolledigView1 = new PersoonHisVolledigView(TestPersoonJohnnyJordaan.maak(), null);

    private final PersoonHisVolledigView persoonHisVolledigView2 = new PersoonHisVolledigView(TestPersoonJohnnyJordaan.maak(), null);

    private final List<PersoonHisVolledigView> bijgehoudenPersonen = new ArrayList<>();

    @Before
    public final void setUp() throws Exception {
        berichtVerwerkingssoortZetter = new BerichtVerwerkingssoortZetterImpl(berichtVerwerkingssoortBepaler);

        Mockito.when(berichtVerwerkingssoortBepaler.bepaalVerwerkingssoortPersoon(Mockito.anyLong(), Mockito.any(PersoonHisVolledigView.class)))
            .thenReturn(Verwerkingssoort.WIJZIGING);
        Mockito.when(berichtVerwerkingssoortBepaler.bepaalVerwerkingssoort(Mockito.any(HistorieEntiteit.class), Mockito.anyLong()))
            .thenReturn(Verwerkingssoort.TOEVOEGING);
        Mockito.when(berichtVerwerkingssoortBepaler.bepaalVerwerkingssoortVoorBetrokkenheden(Mockito.any(BetrokkenheidHisVolledigView.class),
            Mockito.anyLong()))
            .thenReturn(Verwerkingssoort.IDENTIFICATIE);
        Mockito.when(berichtVerwerkingssoortBepaler.bepaalVerwerkingssoortVoorRelaties(Mockito.any(RelatieHisVolledigView.class),
            Mockito.anyLong()))
            .thenReturn(Verwerkingssoort.REFERENTIE);

        bijgehoudenPersonen.add(persoonHisVolledigView1);
        bijgehoudenPersonen.add(persoonHisVolledigView2);
    }

    @Test
    public final void testVoegVerwerkingssoortenToeLijstPersoonViews() {
        // Puur voor coverage, persoon heeft namelijk geen enkele groep en zal overal de == null conditional triggeren
        berichtVerwerkingssoortZetter.voegVerwerkingssoortenToe(bijgehoudenPersonen, ADMINISTRATIEVE_HANDELING_ID);

        assertEquals(Verwerkingssoort.WIJZIGING, persoonHisVolledigView1.getVerwerkingssoort());
        assertEquals(Verwerkingssoort.TOEVOEGING,
                     persoonHisVolledigView1.getPersoonIdentificatienummersHistorie().getActueleRecord().getVerwerkingssoort());
        assertEquals(Verwerkingssoort.IDENTIFICATIE,
                     persoonHisVolledigView1.getBetrokkenheden().iterator().next().getVerwerkingssoort());
        assertEquals(Verwerkingssoort.REFERENTIE,
                     ((RelatieHisVolledigView) persoonHisVolledigView1.getBetrokkenheden().iterator().next().getRelatie())
                         .getVerwerkingssoort());
    }

    @Test
    public final void testVoegVerwerkingssoortenToePersoonView() {
        berichtVerwerkingssoortZetter.voegVerwerkingssoortenToe(johnnyJordaanHisVolledigView, ADMINISTRATIEVE_HANDELING_ID);

        assertEquals(Verwerkingssoort.WIJZIGING, johnnyJordaanHisVolledigView.getVerwerkingssoort());
        assertEquals(Verwerkingssoort.TOEVOEGING,
                     johnnyJordaanHisVolledigView.getPersoonIdentificatienummersHistorie().getActueleRecord().getVerwerkingssoort());
        assertEquals(Verwerkingssoort.IDENTIFICATIE,
                     johnnyJordaanHisVolledigView.getBetrokkenheden().iterator().next().getVerwerkingssoort());
        assertEquals(Verwerkingssoort.REFERENTIE,
                     ((RelatieHisVolledigView) johnnyJordaanHisVolledigView.getBetrokkenheden().iterator().next().getRelatie())
                         .getVerwerkingssoort());
    }

}
