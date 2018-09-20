/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.dto.bijhouding;

import junit.framework.Assert;
import org.junit.Test;

/**
 * Unit test class voor het testen van de {@link BijhoudingCode} enumeratie.
 */
public class BijhoudingCodeTest {

    @Test
    public void testCodes() {
        Assert.assertEquals("V", BijhoudingCode.DIRECT_VERWERKT.getCode());
        Assert.assertEquals("U", BijhoudingCode.UITGESTELD.getCode());
    }
}
