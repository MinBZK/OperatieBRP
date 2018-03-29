/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

/**
 * Testen voor StringElement.
 */
public class StringElementTest {

    @Test
    public void testEquals() {
        final StringElement s1 = new StringElement("s1");
        final StringElement s2 = new StringElement("s1");
        final StringElement s3 = new StringElement("s3");
        assertEquals(s1, s2);
        assertNotEquals(s1, s3);
    }
}
