/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;

import java.time.ZonedDateTime;
import org.junit.Test;

/**
 *
 */
public class StuurgegevensElementTest extends AbstractElementTest {

    @Test
    public void testGetInstanceMetTijdstipVerzending() {
        final ZonedDateTime now = ZonedDateTime.now();
        final StuurgegevensElement
                stuurgegevensElementMetTijdstip =
                StuurgegevensElement.getInstance("ZendendePartij", "ZendendeSysteem", "ontvangendepartij", "crossReferentieNummer", now);
        assertEquals(stuurgegevensElementMetTijdstip.getTijdstipVerzending(), new DatumTijdElement(now));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetInstanceZonderTijdstipVerzending() {
        final StuurgegevensElement stuurgegevensElementZonderTijdstop = StuurgegevensElement.getInstance("ZendendePartij", "ZendendeSysteem",
                "ontvangendepartij", "crossReferentieNummer", null);
        assertEquals(stuurgegevensElementZonderTijdstop.getTijdstipVerzending(), null);
    }
}
