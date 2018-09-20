/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Unit test class voor de {@link BerichtenIds} class.
 */
public class BerichtenIdsTest {

    @Test
    public void testConstructorEnGetters() {
        BerichtenIds ids = new BerichtenIds(Long.valueOf(2), Long.valueOf(3));
        Assert.assertEquals(Long.valueOf(2), ids.getIngaandBerichtId());
        Assert.assertEquals(Long.valueOf(3), ids.getUitgaandBerichtId());
    }
}
