/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp.attribuut;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Integer;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Onderzoek;

import org.junit.Test;

public class BrpPartijCodeTest {

    private static final Integer PARTIJ_CODE = 123401;
    private static final Integer PARTIJ_CODE2 = 123501;

    private static final BrpPartijCode BRP_PARTIJ_CODE = new BrpPartijCode(PARTIJ_CODE);
    private static final BrpPartijCode BRP_PARTIJ_CODE_2 = new BrpPartijCode(PARTIJ_CODE2);
    private final Lo3Onderzoek onderzoek = new Lo3Onderzoek(Lo3Integer.wrap(10000), Lo3Datum.NULL_DATUM, null);

    @Test
    public void testBrpPartijCode() {
        new BrpPartijCode(PARTIJ_CODE);
    }

    @Test
    public void testGetCode() {
        final BrpPartijCode brpPartijCode = new BrpPartijCode(PARTIJ_CODE);
        assertEquals(PARTIJ_CODE, brpPartijCode.getWaarde());
    }

    @Test
    public void testEqualsObject() {
        assertTrue(BRP_PARTIJ_CODE.equals(BRP_PARTIJ_CODE));
        assertFalse(BRP_PARTIJ_CODE.equals(new Object()));
        assertNotNull(BRP_PARTIJ_CODE);
        assertFalse(BRP_PARTIJ_CODE.equals(BRP_PARTIJ_CODE_2));
        assertFalse(BRP_PARTIJ_CODE_2.equals(BRP_PARTIJ_CODE));
    }

    @Test
    public void testHashCode() {
        assertEquals(BRP_PARTIJ_CODE.hashCode(), BRP_PARTIJ_CODE.hashCode());
    }

    @Test
    public void testToString() {
        final BrpPartijCode brpPartijCode = new BrpPartijCode(PARTIJ_CODE);
        assertTrue(brpPartijCode.toString().contains(String.valueOf(PARTIJ_CODE)));
    }

    @Test
    public void testVerwijderOnderzoek() throws Exception {
        BrpPartijCode code = new BrpPartijCode(209, onderzoek);
        assertNotNull(code.getOnderzoek());
        BrpPartijCode newCode = code.verwijderOnderzoek();
        assertNotNull(code.getOnderzoek());
        assertNull(newCode.getOnderzoek());
        code = new BrpPartijCode(null, onderzoek);
        assertNotNull(code.getOnderzoek());
        assertNull(code.verwijderOnderzoek());
    }

    @Test
    public void testWrapZonderWaardeEnZonderOnderzoek() throws Exception {
        assertNull(BrpPartijCode.wrap(null, null));
    }

    @Test
    public void testWrapMetWaardeEnZonderOnderzoek() throws Exception {
        BrpPartijCode result = BrpPartijCode.wrap(207, null);
        assertNull(result.getOnderzoek());
        assertEquals(207, result.getWaarde().intValue());
    }

    @Test
    public void testWrapZonderWaardeEnMetOnderzoek() throws Exception {
        BrpPartijCode result = BrpPartijCode.wrap(null, onderzoek);
        assertNotNull(result.getOnderzoek());
        assertNull(result.getWaarde());
    }

}
