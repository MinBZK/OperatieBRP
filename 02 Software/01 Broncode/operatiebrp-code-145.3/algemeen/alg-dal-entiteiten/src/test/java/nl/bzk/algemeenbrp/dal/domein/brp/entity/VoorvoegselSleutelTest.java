/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.entity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unittest voor {@link VoorvoegselSleutel}.
 */
public class VoorvoegselSleutelTest {
    private VoorvoegselSleutel sleutel = new VoorvoegselSleutel(' ', "van");
    private VoorvoegselSleutel andereSleutel = new VoorvoegselSleutel('\'', "dal");

    @Test
    public void testEquals() {
        assertFalse(sleutel.equals(null));
        assertFalse(sleutel.equals("test"));
        assertFalse(sleutel.equals(andereSleutel));
        assertFalse(andereSleutel.equals(sleutel));
        assertTrue(sleutel.equals(sleutel));
    }

    @Test
    public void testHashCode() {
        final VoorvoegselSleutel tempSleutel = new VoorvoegselSleutel(' ', "van");
        assertNotNull(sleutel.hashCode());
        assertEquals(sleutel.hashCode(), tempSleutel.hashCode());
        assertNotSame(sleutel.hashCode(), andereSleutel.hashCode());
    }
}
