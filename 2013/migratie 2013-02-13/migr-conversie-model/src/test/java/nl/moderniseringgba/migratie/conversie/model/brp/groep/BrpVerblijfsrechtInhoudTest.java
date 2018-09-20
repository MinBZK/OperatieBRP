/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.brp.groep;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;
import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpVerblijfsrechtCode;

import org.junit.Test;

/**
 * Test het contract van BrpVerblijfsrechtInhoud.
 * 
 */
public class BrpVerblijfsrechtInhoudTest {

    private final BrpVerblijfsrechtInhoud inhoud1 = new BrpVerblijfsrechtInhoud(new BrpVerblijfsrechtCode("12"),
            new BrpDatum(20000101), new BrpDatum(20000802), new BrpDatum(20001001));
    private final BrpVerblijfsrechtInhoud inhoud2GelijkAan1 = new BrpVerblijfsrechtInhoud(new BrpVerblijfsrechtCode(
            "12"), new BrpDatum(20000101), new BrpDatum(20000802), new BrpDatum(20001001));

    @Test
    public void testHashCode() {
        assertEquals(inhoud1.hashCode(), inhoud2GelijkAan1.hashCode());
    }

    @Test
    public void testIsLeeg() {
        assertFalse(inhoud1.isLeeg());
    }

    @Test
    public void testToString() {
        assertEquals(inhoud1.toString(), inhoud2GelijkAan1.toString());
    }
}
