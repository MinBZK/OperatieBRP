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

public class BrpLandOfGebiedCodeTest {
    private final Lo3Onderzoek onderzoek = new Lo3Onderzoek(Lo3Integer.wrap(10000), new Lo3Datum(0), null);

    @Test
    public void testVerwijderOnderzoek() throws Exception {
        BrpLandOfGebiedCode code = new BrpLandOfGebiedCode("0045", onderzoek);
        assertNotNull(code.getOnderzoek());
        final BrpLandOfGebiedCode newCode = code.verwijderOnderzoek();
        assertNotNull(code.getOnderzoek());
        assertNotNull(newCode);
        assertNull(newCode.getOnderzoek());
        code = new BrpLandOfGebiedCode(null, onderzoek);
        assertNotNull(code.getOnderzoek());
        assertNull(code.verwijderOnderzoek());
    }

    @Test
    public void testWrapZonderWaardeEnZonderOnderzoek() throws Exception {
        assertNull(BrpLandOfGebiedCode.wrap(null, null));
    }

    @Test
    public void testWrapMetWaardeEnZonderOnderzoek() throws Exception {
        final BrpLandOfGebiedCode result = BrpLandOfGebiedCode.wrap("0045", null);
        assertNull(result.getOnderzoek());
        assertEquals("0045", result.getWaarde());
    }

    @Test
    public void testWrapZonderWaardeEnMetOnderzoek() throws Exception {
        final BrpLandOfGebiedCode result = BrpLandOfGebiedCode.wrap(null, onderzoek);
        assertNotNull(result.getOnderzoek());
        assertNull(result.getWaarde());
    }

}
