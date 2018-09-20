/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut;

import static org.junit.Assert.assertEquals;

import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;

import org.junit.Assert;
import org.junit.Test;

public class BrpSoortBetrokkenheidCodeTest {

    private final Lo3Onderzoek onderzoek = new Lo3Onderzoek(Lo3Integer.wrap(10000), Lo3Datum.NULL_DATUM, null);

    @Test
    public void testConstructor() {
        BrpSoortBetrokkenheidCode code = new BrpSoortBetrokkenheidCode("waarde");
        assertEquals("", code.getOmschrijving());
        Lo3Onderzoek onderzoek = null;
        code = new BrpSoortBetrokkenheidCode("waarde2", onderzoek);
        assertEquals("", code.getOmschrijving());
    }

    @Test
    public void testBrpSoortBetrokkenheidCode() {
        BrpSoortBetrokkenheidCode code1 = new BrpSoortBetrokkenheidCode("waarde1");
        BrpSoortBetrokkenheidCode code2 = new BrpSoortBetrokkenheidCode("waarde2");
        BrpSoortBetrokkenheidCode code2b = new BrpSoortBetrokkenheidCode("waarde1_langer");
        BrpSoortBetrokkenheidCode code3 = new BrpSoortBetrokkenheidCode("waarde1", onderzoek);
        BrpSoortBetrokkenheidCode code4 = new BrpSoortBetrokkenheidCode("waarde1", "omschrijving");
        BrpSoortBetrokkenheidCode code5 = new BrpSoortBetrokkenheidCode("waarde1", "omschrijving", onderzoek);

        Assert.assertEquals(-1, code1.compareTo(code2));
        Assert.assertEquals(-7, code1.compareTo(code2b));
        Assert.assertEquals(1, code2.compareTo(code1));
        Assert.assertEquals(0, code1.compareTo(code3));
        Assert.assertEquals(0, code1.compareTo(code4));
        Assert.assertEquals(0, code1.compareTo(code5));

    }

    @Test(expected = NullPointerException.class)
    public void testBrpSoortBetrokkenheidCodeNull() {
        BrpSoortBetrokkenheidCode i = new BrpSoortBetrokkenheidCode("waarde1");
        i.compareTo(null);
    }

}
