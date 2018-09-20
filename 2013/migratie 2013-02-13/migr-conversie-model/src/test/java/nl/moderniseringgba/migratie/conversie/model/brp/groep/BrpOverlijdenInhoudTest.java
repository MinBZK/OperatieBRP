/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.groep;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;

import java.math.BigDecimal;

import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpLandCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPlaatsCode;

import org.junit.Test;

public class BrpOverlijdenInhoudTest {

    private final BrpOverlijdenInhoud inhoud1 = new BrpOverlijdenInhoud(new BrpDatum(20080102), new BrpGemeenteCode(
            new BigDecimal("123")), new BrpPlaatsCode("456"), "BP", "BR", new BrpLandCode(Integer.valueOf("6030")),
            "omschrijving");
    private final BrpOverlijdenInhoud inhoud2gelijkAan1 = new BrpOverlijdenInhoud(new BrpDatum(20080102),
            new BrpGemeenteCode(new BigDecimal("123")), new BrpPlaatsCode("456"), "BP", "BR", new BrpLandCode(
                    Integer.valueOf("6030")), "omschrijving");
    private final BrpOverlijdenInhoud inhoud3ongelijkAan1 = new BrpOverlijdenInhoud(new BrpDatum(20080102),
            new BrpGemeenteCode(new BigDecimal("333")), new BrpPlaatsCode("456"), "BP", "BR", new BrpLandCode(
                    Integer.valueOf("6030")), "omschrijving");
    private final BrpOverlijdenInhoud inhoud4ongelijkAan1 = new BrpOverlijdenInhoud(new BrpDatum(20080202),
            new BrpGemeenteCode(new BigDecimal("123")), new BrpPlaatsCode("456"), "BP", "BR", new BrpLandCode(
                    Integer.valueOf("6030")), "omschrijving");

    @Test
    public void testHashCode() {
        assertEquals(inhoud1.hashCode(), inhoud2gelijkAan1.hashCode());
    }

    @Test(expected = NullPointerException.class)
    public void testBrpOverlijdenInhoudDatumNull() {
        new BrpOverlijdenInhoud(null, new BrpGemeenteCode(new BigDecimal("123")), new BrpPlaatsCode("456"), "BP",
                "BR", new BrpLandCode(Integer.valueOf("6030")), "omschrijving");
    }

    @Test(expected = NullPointerException.class)
    public void testBrpOverlijdenInhoudLandCodeNull() {
        new BrpOverlijdenInhoud(new BrpDatum(20080102), new BrpGemeenteCode(new BigDecimal("123")),
                new BrpPlaatsCode("456"), "BP", "BR", null, "omschrijving");
    }

    @Test
    public void testGetDatum() {
        assertEquals(new BrpDatum(20080102), inhoud1.getDatum());
    }

    @Test
    public void testGetGemeenteCode() {
        assertEquals(new BrpGemeenteCode(new BigDecimal("123")), inhoud1.getGemeenteCode());
    }

    @Test
    public void testGetPlaatsCode() {
        assertEquals(new BrpPlaatsCode("456"), inhoud1.getPlaatsCode());
    }

    @Test
    public void testGetBuitenlandsePlaats() {
        assertEquals("BP", inhoud1.getBuitenlandsePlaats());
    }

    @Test
    public void testGetBuitenlandseRegio() {
        assertEquals("BR", inhoud1.getBuitenlandseRegio());
    }

    @Test
    public void testGetLandCode() {
        assertEquals(new BrpLandCode(Integer.valueOf("6030")), inhoud1.getLandCode());
    }

    @Test
    public void testGetOmschrijvingLocatie() {
        assertEquals("omschrijving", inhoud1.getOmschrijvingLocatie());
    }

    @Test
    public void testIsLeeg() {
        assertFalse(inhoud1.isLeeg());
        assertFalse(new BrpOverlijdenInhoud(new BrpDatum(20090102), null, null, null, null, new BrpLandCode(
                Integer.valueOf("6030")), null).isLeeg());
    }

    @Test
    public void testEqualsObject() {
        assertEquals(inhoud1, inhoud2gelijkAan1);
        assertNotSame(inhoud1, inhoud3ongelijkAan1);
        assertNotSame(inhoud1, inhoud4ongelijkAan1);
    }

    @Test
    public void testToString() {
        assertEquals(inhoud1.toString(), inhoud2gelijkAan1.toString());
    }

}
