/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.groep;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpGemeenteCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpLandCode;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpPlaatsCode;

import org.junit.Test;

public class BrpGeboorteInhoudTest {

    @Test
    public void testHashCode() {
        final BrpGeboorteInhoud inhoud1 =
                new BrpGeboorteInhoud(new BrpDatum(19800101), new BrpGemeenteCode(new BigDecimal("1234")), null,
                        null, null, new BrpLandCode(Integer.valueOf("6030")), null);
        final BrpGeboorteInhoud inhoud2 =
                new BrpGeboorteInhoud(new BrpDatum(19800101), new BrpGemeenteCode(new BigDecimal("1234")), null,
                        null, null, new BrpLandCode(Integer.valueOf("6030")), null);
        assertEquals(inhoud1.hashCode(), inhoud2.hashCode());
    }

    @Test
    public void testBrpGeboorteInhoud() {
        new BrpGeboorteInhoud(new BrpDatum(19800101), new BrpGemeenteCode(new BigDecimal("1234")), null, null, null,
                new BrpLandCode(Integer.valueOf("6030")), null);
    }

    @Test
    public void testIsLeeg() {
        final BrpGeboorteInhoud inhoud1 =
                new BrpGeboorteInhoud(new BrpDatum(19800101), new BrpGemeenteCode(new BigDecimal("1234")), null,
                        null, null, new BrpLandCode(Integer.valueOf("6030")), null);
        final BrpGeboorteInhoud inhoud2 = new BrpGeboorteInhoud(null, null, null, null, null, null, null);
        assertFalse(inhoud1.isLeeg());
        assertTrue(inhoud2.isLeeg());
    }

    @Test
    public void testEqualsObject() {
        final BrpGeboorteInhoud inhoud1 =
                new BrpGeboorteInhoud(new BrpDatum(19800101), new BrpGemeenteCode(new BigDecimal("1234")), null,
                        null, null, new BrpLandCode(Integer.valueOf("6030")), null);
        final BrpGeboorteInhoud inhoud2 =
                new BrpGeboorteInhoud(new BrpDatum(19800101), new BrpGemeenteCode(new BigDecimal("1234")), null,
                        null, null, new BrpLandCode(Integer.valueOf("6030")), null);
        final BrpGeboorteInhoud inhoud3 =
                new BrpGeboorteInhoud(new BrpDatum(19800101), new BrpGemeenteCode(new BigDecimal("1234")), null,
                        "blgp", null, new BrpLandCode(Integer.valueOf("6030")), null);

        final BrpGeboorteInhoud inhoud4 =
                new BrpGeboorteInhoud(new BrpDatum(19800101), new BrpGemeenteCode(new BigDecimal("1234")),
                        new BrpPlaatsCode("2345"), "blgp", "brc", new BrpLandCode(Integer.valueOf("6030")),
                        "omschrijving");
        final BrpGeboorteInhoud inhoud5 =
                new BrpGeboorteInhoud(new BrpDatum(19800101), new BrpGemeenteCode(new BigDecimal("1234")),
                        new BrpPlaatsCode("2345"), "blgp", "brc", new BrpLandCode(Integer.valueOf("6030")),
                        "omschrijving");

        assertEquals(inhoud1, inhoud2);
        assertNotSame(inhoud1, inhoud3);
        assertEquals(inhoud4, inhoud5);
    }

    @Test
    public void testToString() {
        final BrpGeboorteInhoud inhoud1 =
                new BrpGeboorteInhoud(new BrpDatum(19800101), new BrpGemeenteCode(new BigDecimal("1234")), null,
                        null, null, new BrpLandCode(Integer.valueOf("6030")), null);
        final BrpGeboorteInhoud inhoud2 =
                new BrpGeboorteInhoud(new BrpDatum(19800101), new BrpGemeenteCode(new BigDecimal("1234")), null,
                        null, null, new BrpLandCode(Integer.valueOf("6030")), null);
        assertEquals(inhoud1.toString(), inhoud2.toString());
    }
}
