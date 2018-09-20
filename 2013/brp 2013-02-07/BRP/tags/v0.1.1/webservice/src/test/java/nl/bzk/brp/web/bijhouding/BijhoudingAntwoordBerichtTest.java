/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.bijhouding;

import nl.bzk.brp.business.dto.bijhouding.BijhoudingResultaat;
import org.junit.Test;

/** Unit test klasse voor de methodes in de {@link BijhoudingAntwoordBericht} klasse. */
public class BijhoudingAntwoordBerichtTest {

    @Test
    public void testConstructor() {
        BijhoudingAntwoordBericht antwoordBericht =
            new BijhoudingAntwoordBericht(new BijhoudingResultaat(null));
    }
}
