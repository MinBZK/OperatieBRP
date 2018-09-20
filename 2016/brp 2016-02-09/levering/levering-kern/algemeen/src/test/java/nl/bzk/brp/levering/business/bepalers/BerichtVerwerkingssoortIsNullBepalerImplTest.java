/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.bepalers;

import nl.bzk.brp.levering.business.bepalers.impl.BerichtVerwerkingssoortIsNullBepalerImpl;
import nl.bzk.brp.model.algemeen.attribuuttype.ber.Verwerkingssoort;
import nl.bzk.brp.model.basis.HistorieEntiteit;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.BetrokkenheidHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.PersoonHisVolledigView;
import nl.bzk.brp.model.hisvolledig.predikaatview.kern.RelatieHisVolledigView;
import nl.bzk.brp.util.testpersoonbouwers.TestPersoonJohnnyJordaan;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;


public class BerichtVerwerkingssoortIsNullBepalerImplTest {

    private static final Long ADMINISTRATIEVE_HANDELING_ID = 1L;

    private final BerichtVerwerkingssoortIsNullBepalerImpl bepaler = new BerichtVerwerkingssoortIsNullBepalerImpl();

    @Test
    public final void testBepaalVerwerkingssoort() {
        final HistorieEntiteit historie = Mockito.mock(HistorieEntiteit.class);
        final Verwerkingssoort verwerkingsSoort = bepaler.bepaalVerwerkingssoort(historie, ADMINISTRATIEVE_HANDELING_ID);

        Assert.assertThat(verwerkingsSoort, Matchers.nullValue());
    }

    @Test
    public final void testBepaalVerwerkingssoortPersoon() {
        final PersoonHisVolledigView view = new PersoonHisVolledigView(TestPersoonJohnnyJordaan.maak(), null);
        final Verwerkingssoort verwerkingsSoort = bepaler.bepaalVerwerkingssoortPersoon(ADMINISTRATIEVE_HANDELING_ID, view);

        Assert.assertThat(verwerkingsSoort, Matchers.nullValue());
    }

    @Test
    public final void testBepaalVerwerkingssoortVoorRelaties() {
        final RelatieHisVolledigView view = Mockito.mock(RelatieHisVolledigView.class);

        final Verwerkingssoort verwerkingsSoort = bepaler.bepaalVerwerkingssoortVoorRelaties(view, ADMINISTRATIEVE_HANDELING_ID);
        Assert.assertThat(verwerkingsSoort, Matchers.nullValue());
    }

    @Test
    public final void testBepaalVerwerkingssoortVoorBetrokkenheden() {
        final BetrokkenheidHisVolledigView view = Mockito.mock(BetrokkenheidHisVolledigView.class);
        final Verwerkingssoort verwerkingsSoort = bepaler.bepaalVerwerkingssoortVoorBetrokkenheden(view, ADMINISTRATIEVE_HANDELING_ID);
        Assert.assertThat(verwerkingsSoort, Matchers.nullValue());
    }
}
