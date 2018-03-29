/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.element;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class Lo3IndicatieOnjuistTest {

    @Test
    public void testGetCodeAsCharacter() throws Exception {
        Lo3IndicatieOnjuist lo3a = new Lo3IndicatieOnjuist("AAP");
        assertTrue('A' == lo3a.getCodeAsCharacter());
    }

    @Test
    public void testCompareTo() throws Exception {

        Lo3IndicatieOnjuist lo3a = new Lo3IndicatieOnjuist("A");
        Lo3IndicatieOnjuist lo3b = new Lo3IndicatieOnjuist("O");
        Lo3IndicatieOnjuist lo3c = new Lo3IndicatieOnjuist("A", null);
        Lo3IndicatieOnjuist lo3d = new Lo3IndicatieOnjuist(null, null);

        try {
            lo3a.compareTo(null);
            fail();
        } catch (NullPointerException n) {
            // OK
        }

        assertTrue(lo3a.compareTo(lo3b) < 0);
        assertEquals(0, lo3a.compareTo(lo3c));
        assertTrue(lo3b.compareTo(lo3c) > 0);
        assertTrue(lo3b.compareTo(lo3d) > 0);
        assertTrue(lo3d.compareTo(lo3b) < 0);
    }

}
