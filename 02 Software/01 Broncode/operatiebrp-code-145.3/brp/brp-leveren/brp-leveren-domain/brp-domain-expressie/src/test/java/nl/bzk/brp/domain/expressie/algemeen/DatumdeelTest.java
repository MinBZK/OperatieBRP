/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domain.expressie.algemeen;

import java.util.Date;
import nl.bzk.brp.domain.expressie.Datumdeel;
import org.junit.Assert;
import org.junit.Test;

/**
 * DatumdeelTest.
 */
public class DatumdeelTest {

    @Test
    public void testEquals() {
        final Datumdeel datumdeel1 = Datumdeel.valueOf(10);
        final Datumdeel datumdeel2 = Datumdeel.valueOf(10);
        final Datumdeel datumdeel3 = Datumdeel.valueOf(30);
        Assert.assertEquals(datumdeel1, datumdeel2);

        Assert.assertNotEquals(datumdeel1, datumdeel3);
        Assert.assertNotEquals(datumdeel1, new Date());

    }

    @Test
    public void testHashCode() {
        final Datumdeel datumdeel1 = Datumdeel.valueOf(10);
        final Datumdeel datumdeel2 = Datumdeel.valueOf(10);
        Assert.assertEquals(datumdeel1.hashCode(), datumdeel2.hashCode());
    }


    @Test
    public void testCompareTp() {
        final Datumdeel datumdeel1 = Datumdeel.valueOf(10);
        final Datumdeel datumdeel2 = Datumdeel.valueOf(10);
        Assert.assertEquals(0, datumdeel1.compareTo(datumdeel2));
    }

    @Test
    public void tesOnbekend() {
        final Datumdeel datumdeel1 = Datumdeel.valueOf(10);
        Assert.assertFalse(datumdeel1.isOnbekend());
        final Datumdeel datumdeel2 = Datumdeel.ONBEKEND_DATUMDEEL;
        Assert.assertTrue(datumdeel2.isVraagteken());
    }
}
