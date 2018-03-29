/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.protocollering.domain.algemeen;

import java.time.ZonedDateTime;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * LeveringPersoonTest.
 */
public class LeveringPersoonTest {
    @Test
    public void testEquals(){
        final ZonedDateTime nu =DatumUtil.nuAlsZonedDateTime();
        final LeveringPersoon leveringPersoon1 = new LeveringPersoon(1L, nu);
        final LeveringPersoon leveringPersoon2 = new LeveringPersoon(1L, nu);
        final LeveringPersoon leveringPersoon3 = new LeveringPersoon(2L, nu);

        Assert.assertFalse(leveringPersoon2.equals(leveringPersoon3));
        Assert.assertEquals(leveringPersoon1, leveringPersoon2);
        Assert.assertEquals(leveringPersoon1, leveringPersoon1);
    }
}


