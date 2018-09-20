/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut.autorisatie;

import nl.bzk.migratiebrp.conversie.model.brp.attribuut.BrpSoortPartijCode;
import org.junit.Assert;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class BrpSoortPartijCodeTest {

    public static final String DERDE = "derde";
    public static BrpSoortPartijCode GEMEENTE = new BrpSoortPartijCode("gemeente");
    public static BrpSoortPartijCode ANDERS = new BrpSoortPartijCode("iets anders");

    @Test
    public void test() {
        final BrpSoortPartijCode subject = new BrpSoortPartijCode(DERDE);
        final BrpSoortPartijCode equal = new BrpSoortPartijCode(subject.getSoortPartij());

        Assert.assertEquals(subject.getSoortPartij(), equal.getSoortPartij());

    }

    @Test
    public void testEquals() {
        assertTrue(GEMEENTE.equals(GEMEENTE));
        assertFalse(GEMEENTE.equals(null));
        assertFalse(GEMEENTE.equals(ANDERS));
        final BrpSoortPartijCode code = GEMEENTE;
        final BrpSoortPartijCode code2 = new BrpSoortPartijCode("Gemeente");
        assertTrue(GEMEENTE.equals(code));
        assertFalse(GEMEENTE.getSoortPartij().equals(code2));

    }
}
