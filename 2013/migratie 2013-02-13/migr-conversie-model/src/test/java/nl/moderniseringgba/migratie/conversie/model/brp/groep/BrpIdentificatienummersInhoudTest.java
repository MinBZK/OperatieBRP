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

import org.junit.Before;
import org.junit.Test;

public class BrpIdentificatienummersInhoudTest {

    private BrpIdentificatienummersInhoud inhoud1;
    private BrpIdentificatienummersInhoud inhoud2;
    private BrpIdentificatienummersInhoud inhoud3;
    private BrpIdentificatienummersInhoud inhoud4;
    private BrpIdentificatienummersInhoud inhoud5;
    private BrpIdentificatienummersInhoud inhoud6;

    @Before
    public void setup() {
        inhoud1 = new BrpIdentificatienummersInhoud(123456789L, 987654321L);
        inhoud2 = new BrpIdentificatienummersInhoud(123456789L, 987654321L);
        inhoud3 = new BrpIdentificatienummersInhoud(123456789L, 999999999L);
        inhoud4 = new BrpIdentificatienummersInhoud(111111111L, 987654321L);
        inhoud5 = new BrpIdentificatienummersInhoud(123456789L, null);
        inhoud6 = new BrpIdentificatienummersInhoud(123456789L, null);
    }

    @Test
    public void testHashCode() {
        assertEquals(inhoud1.hashCode(), inhoud2.hashCode());
        assertNotSame(inhoud1.hashCode(), inhoud5.hashCode());
    }

    @Test
    public void testEqualsObject() {
        assertTrue(inhoud1.equals(inhoud2));
        assertFalse(inhoud1.equals(inhoud3));
        assertFalse(inhoud1.equals(inhoud4));
        assertFalse(inhoud1.equals(inhoud5));
        assertFalse(inhoud1.equals(null));
        assertTrue(inhoud1.equals(inhoud1));
        assertFalse(inhoud1.equals(new Object()));
        assertTrue(inhoud5.equals(inhoud6));
        assertFalse(inhoud5.equals(inhoud1));
    }

    @Test
    public void testToString() {
        assertEquals(inhoud1.toString(), inhoud2.toString());
        assertNotSame(inhoud1.toString(), inhoud3.toString());
    }
}
