/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.migratie.domein;

import nl.moderniseringgba.isc.migratie.domein.entities.Gemeente;

import org.junit.Assert;
import org.junit.Test;

public class GemeenteTest {

    @Test
    public void test() {
        final Gemeente gemeente = new Gemeente();
        Assert.assertNull(gemeente.getDatumBrp());
        Assert.assertNull(gemeente.getGemeenteCode());

        gemeente.setDatumBrp(1);
        gemeente.setGemeenteCode(2);

        Assert.assertEquals(Integer.valueOf(1), gemeente.getDatumBrp());
        Assert.assertEquals(Integer.valueOf(2), gemeente.getGemeenteCode());
    }
}
