/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;
import org.junit.Test;

public class BrpSoortRelatieCodeTest {

    private final Lo3Onderzoek onderzoek = new Lo3Onderzoek(Lo3Integer.wrap(10000), new Lo3Datum(0), null);

    @Test
    public void testVerwijderOnderzoek() throws Exception {
        BrpSoortRelatieCode code = new BrpSoortRelatieCode("M", onderzoek);
        assertNotNull(code.getOnderzoek());
        final BrpSoortRelatieCode newCode = code.verwijderOnderzoek();
        assertNotNull(code.getOnderzoek());
        assertNull(newCode.getOnderzoek());
        code = new BrpSoortRelatieCode(null, onderzoek);
        assertNotNull(code.getOnderzoek());
        assertNull(code.verwijderOnderzoek());
    }

    @Test
    public void testWrapZonderWaardeEnZonderOnderzoek() throws Exception {
        assertNull(BrpSoortRelatieCode.wrap(null, null));
    }

    @Test
    public void testWrapMetWaardeEnZonderOnderzoek() throws Exception {
        final BrpSoortRelatieCode result = BrpSoortRelatieCode.wrap("M", null);
        assertNull(result.getOnderzoek());
        assertEquals("M", result.getWaarde());
    }

    @Test
    public void testWrapZonderWaardeEnMetOnderzoek() throws Exception {
        final BrpSoortRelatieCode result = BrpSoortRelatieCode.wrap(null, onderzoek);
        assertNotNull(result.getOnderzoek());
        assertNull(result.getWaarde());
    }

    @Test(expected = NullPointerException.class)
    public void testCompareToNull() {
        final BrpSoortRelatieCode result = BrpSoortRelatieCode.wrap(null, onderzoek);
        result.compareTo(null);
    }

    @Test
    public void testCompareTo() {
        final BrpSoortRelatieCode result = BrpSoortRelatieCode.wrap("1", onderzoek);
        final BrpSoortRelatieCode result2 = BrpSoortRelatieCode.wrap("1", null);
        assertEquals(0, result.compareTo(result2));
    }
}
