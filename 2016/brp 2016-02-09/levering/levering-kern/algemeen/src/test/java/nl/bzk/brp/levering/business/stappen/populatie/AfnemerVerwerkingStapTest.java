/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.business.stappen.populatie;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import nl.bzk.brp.levering.model.Leveringinformatie;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Stelsel;
import org.junit.Test;
import org.mockito.Mockito;


public class AfnemerVerwerkingStapTest {

    private final AfnemerVerwerkingStap stap = new TestAfnemerVerwerkingStap();

    @Test
    public final void testUitvoerenStap() throws Exception {
        // given
        final Long administratieveHandelingId = 3L;
        final Leveringinformatie leveringAutorisatie = Mockito.mock(Leveringinformatie.class);

        final LeveringautorisatieVerwerkingResultaat resultaat = new LeveringautorisatieVerwerkingResultaat();
        final LeveringsautorisatieVerwerkingContext context = new LeveringsautorisatieVerwerkingContextImpl(null, null, null, null, null);

        final LeveringautorisatieStappenOnderwerp onderwerp =
            new LeveringautorisatieStappenOnderwerpImpl(leveringAutorisatie, administratieveHandelingId,
                Stelsel.DUMMY);

        // when
        final boolean stapResultaat = stap.voerStapUit(onderwerp, context, resultaat);

        // then
        assertTrue(stapResultaat);
        assertThat(onderwerp.getLeveringinformatie(), equalTo(leveringAutorisatie));
        assertThat(onderwerp.getAdministratieveHandelingId(), is(administratieveHandelingId));
    }
}
