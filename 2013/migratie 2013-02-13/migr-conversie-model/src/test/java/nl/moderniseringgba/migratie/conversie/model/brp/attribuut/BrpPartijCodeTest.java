/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.attribuut;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BrpPartijCodeTest {

    private static final String PARTIJ_NAAM = "partij_naam";
    private static final Integer GEMEENTE_CODE = new Integer(1234);
    private static final Integer GEMEENTE_CODE2 = new Integer(1235);

    private static final BrpPartijCode BRP_PARTIJ_CODE = new BrpPartijCode(PARTIJ_NAAM, GEMEENTE_CODE);
    private static final BrpPartijCode BRP_PARTIJ_CODE_2 = new BrpPartijCode(PARTIJ_NAAM, GEMEENTE_CODE);
    private static final BrpPartijCode BRP_PARTIJ_CODE_3 = new BrpPartijCode(PARTIJ_NAAM + "0", GEMEENTE_CODE);
    private static final BrpPartijCode BRP_PARTIJ_CODE_4 = new BrpPartijCode(PARTIJ_NAAM + "0", null);
    private static final BrpPartijCode BRP_PARTIJ_CODE_5 = new BrpPartijCode(PARTIJ_NAAM, null);
    private static final BrpPartijCode BRP_PARTIJ_CODE_6 = new BrpPartijCode(PARTIJ_NAAM, GEMEENTE_CODE2);
    private static final BrpPartijCode BRP_PARTIJ_CODE_7 = new BrpPartijCode(PARTIJ_NAAM, null);
    private static final BrpPartijCode BRP_PARTIJ_CODE_8 = new BrpPartijCode(null, GEMEENTE_CODE);

    @Test
    public void testBrpPartijCode() {
        new BrpPartijCode(PARTIJ_NAAM, GEMEENTE_CODE);
        new BrpPartijCode(null, GEMEENTE_CODE);
        new BrpPartijCode(PARTIJ_NAAM, null);
    }

    @Test(expected = NullPointerException.class)
    public void testBrpPartijCodeFout() {
        new BrpPartijCode(null, null);
    }

    @Test
    public void testGetNaam() {
        final BrpPartijCode brpPartijCode = new BrpPartijCode(PARTIJ_NAAM, GEMEENTE_CODE);
        assertEquals(PARTIJ_NAAM, brpPartijCode.getNaam());
    }

    @Test
    public void testGetGemeenteCode() {
        final BrpPartijCode brpPartijCode = new BrpPartijCode(PARTIJ_NAAM, GEMEENTE_CODE);
        assertEquals(GEMEENTE_CODE, brpPartijCode.getGemeenteCode());
    }

    @Test
    public void testEqualsObject() {
        assertTrue(BRP_PARTIJ_CODE.equals(BRP_PARTIJ_CODE));
        assertFalse(BRP_PARTIJ_CODE.equals(new Object()));
        assertFalse(BRP_PARTIJ_CODE.equals(null));
        assertTrue(BRP_PARTIJ_CODE.equals(BRP_PARTIJ_CODE_2));
        assertTrue(BRP_PARTIJ_CODE_2.equals(BRP_PARTIJ_CODE));
        assertFalse(BRP_PARTIJ_CODE_5.equals(BRP_PARTIJ_CODE_4));
        assertFalse(BRP_PARTIJ_CODE_4.equals(BRP_PARTIJ_CODE_5));
        assertFalse(BRP_PARTIJ_CODE_4.equals(BRP_PARTIJ_CODE_3));
        assertFalse(BRP_PARTIJ_CODE_3.equals(BRP_PARTIJ_CODE_4));
        assertFalse(BRP_PARTIJ_CODE.equals(BRP_PARTIJ_CODE_6));
        assertFalse(BRP_PARTIJ_CODE_6.equals(BRP_PARTIJ_CODE));
        assertTrue(BRP_PARTIJ_CODE_5.equals(BRP_PARTIJ_CODE_7));
        assertTrue(BRP_PARTIJ_CODE_7.equals(BRP_PARTIJ_CODE_5));
        assertFalse(BRP_PARTIJ_CODE_6.equals(BRP_PARTIJ_CODE_8));
        assertFalse(BRP_PARTIJ_CODE_8.equals(BRP_PARTIJ_CODE_6));

        // normaal verwacht men hier false maar omdat gemeentecode is gevuld wordt alleen hier op gecontroleerd
        assertTrue(BRP_PARTIJ_CODE.equals(BRP_PARTIJ_CODE_3));
        assertTrue(BRP_PARTIJ_CODE_3.equals(BRP_PARTIJ_CODE));
    }

    @Test
    public void testHashCode() {
        assertEquals(BRP_PARTIJ_CODE.hashCode(), BRP_PARTIJ_CODE.hashCode());
        assertEquals(BRP_PARTIJ_CODE.hashCode(), BRP_PARTIJ_CODE_2.hashCode());
        assertFalse(BRP_PARTIJ_CODE_5.hashCode() == BRP_PARTIJ_CODE_4.hashCode());
        assertFalse(BRP_PARTIJ_CODE_4.hashCode() == BRP_PARTIJ_CODE_3.hashCode());
        assertFalse(BRP_PARTIJ_CODE.hashCode() == BRP_PARTIJ_CODE_6.hashCode());
        assertTrue(BRP_PARTIJ_CODE_5.hashCode() == BRP_PARTIJ_CODE_7.hashCode());
        assertFalse(BRP_PARTIJ_CODE_6.hashCode() == BRP_PARTIJ_CODE_8.hashCode());
        assertFalse(BRP_PARTIJ_CODE_8.hashCode() == BRP_PARTIJ_CODE_6.hashCode());

        // normaal verwacht men hier false maar omdat gemeentecode is gevuld wordt alleen hier op gecontroleerd
        assertEquals(BRP_PARTIJ_CODE.hashCode(), BRP_PARTIJ_CODE_3.hashCode());
    }

    @Test
    public void testToString() {
        final BrpPartijCode brpPartijCode = new BrpPartijCode(PARTIJ_NAAM, GEMEENTE_CODE);
        assertTrue(brpPartijCode.toString().contains(PARTIJ_NAAM));
        assertTrue(brpPartijCode.toString().contains(GEMEENTE_CODE.toString()));
    }

}
