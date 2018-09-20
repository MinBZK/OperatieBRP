/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.service.zoeken;

import org.junit.Assert;
import org.junit.Test;

public class GevondenPersoonTest {

    @Test
    public void test() {
        final GevondenPersoon subject = new GevondenPersoon(1, 2L, "3");
        Assert.assertEquals(Integer.valueOf(1), subject.getPersoonId());
        Assert.assertEquals(Long.valueOf(2), subject.getAdministratienummer());
        Assert.assertEquals("3", subject.getBijhoudingsgemeente());
    }
}
