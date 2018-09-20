/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.handlers;

import nl.bzk.brp.bevraging.business.berichtcmds.BrpBerichtCommand;
import org.junit.Test;
import org.mockito.Mockito;


/**
 * Unit test voor de basis methodes in de {@link AbstractBerichtVerwerkingsStap} class.
 */
public class BaseBerichtVerwerkingsStapTest {

    /**
     * Test dat er standaard niets gebeurd met het bericht in de na verwerkingsstap.
     */
    @Test
    public void testNaVerwerkingsStapVoorBericht() {
        BrpBerichtCommand bericht = Mockito.mock(BrpBerichtCommand.class);

        AbstractBerichtVerwerkingsStap stap = new MockBerichtVerwerkingsStap();
        stap.naVerwerkingsStapVoorBericht(bericht);

        Mockito.verifyZeroInteractions(bericht);
    }

    private class MockBerichtVerwerkingsStap extends AbstractBerichtVerwerkingsStap {

        @Override
        public boolean voerVerwerkingsStapUitVoorBericht(final BrpBerichtCommand bericht) {
            return false;
        }

    }
}
