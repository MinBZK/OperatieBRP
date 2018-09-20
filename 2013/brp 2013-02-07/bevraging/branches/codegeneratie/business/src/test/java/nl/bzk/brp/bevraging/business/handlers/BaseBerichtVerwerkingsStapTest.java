/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import nl.bzk.brp.bevraging.business.dto.BerichtContext;
import nl.bzk.brp.bevraging.business.dto.antwoord.BerichtAntwoord;
import nl.bzk.brp.bevraging.business.dto.verzoek.BerichtVerzoek;
import org.junit.Test;
import org.mockito.Mockito;


/**
 * Unit test voor de basis methodes in de {@link AbstractBerichtVerwerkingsStap} class.
 */
public class BaseBerichtVerwerkingsStapTest {

    /**
     * Test dat er standaard niets gebeurd met het bericht in de na verwerkingsstap.
     */
    @SuppressWarnings("unchecked")
    @Test
    public void testNaVerwerkingsStapVoorBericht() {
        BerichtVerzoek<BerichtAntwoord> verzoek = Mockito.mock(BerichtVerzoek.class);
        BerichtContext context = Mockito.mock(BerichtContext.class);

        AbstractBerichtVerwerkingsStap stap = new MockBerichtVerwerkingsStap();
        stap.naVerwerkingsStapVoorBericht(verzoek, context);

        Mockito.verifyZeroInteractions(verzoek);
        Mockito.verifyZeroInteractions(context);
    }

    private class MockBerichtVerwerkingsStap extends AbstractBerichtVerwerkingsStap {

        @Override
        public <T extends BerichtAntwoord> boolean voerVerwerkingsStapUitVoorBericht(final BerichtVerzoek<T> verzoek,
                final BerichtContext context, final T antwoord)
        {
            return false;
        }
    }
}
