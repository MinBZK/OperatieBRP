/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto;

import junit.framework.Assert;
import nl.bzk.brp.model.objecttype.operationeel.statisch.Partij;
import org.junit.Test;

/** Unit test voor de {@link BerichtContext} class. */
public class BerichtContextTest {

    @Test
    public void testConstructorEnGetters() {
        BerichtContext context =
            new BerichtContext(new BerichtenIds(Long.valueOf(2L), Long.valueOf(3L)), Integer.valueOf(1), new Partij(),
                "ref");
        Assert.assertNotNull(context.getPartij());
        Assert.assertEquals(2, context.getIngaandBerichtId());
        Assert.assertEquals(3, context.getUitgaandBerichtId());
        Assert.assertEquals(Integer.valueOf(1), context.getAuthenticatieMiddelId());
        Assert.assertNotNull(context.getTijdstipVerwerking());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorMetNullVoorBerichtenIds() {
        new BerichtContext(null, Integer.valueOf(1), new Partij(), "ref");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorMetNullVoorAuthenticatieMiddelId() {
        new BerichtContext(new BerichtenIds(Long.valueOf(2L), Long.valueOf(3L)), null, new Partij(), "ref");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorMetNullVoorPartij() {
        new BerichtContext(new BerichtenIds(Long.valueOf(2L), Long.valueOf(3L)), Integer.valueOf(1), null, "ref");
    }

}
