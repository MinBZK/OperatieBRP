/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.autorisatie;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class Lo3AfnemersindicatieInhoudTest {

    @Test
    public void testEmpty() {
        final Lo3AfnemersindicatieInhoud subject = new Lo3AfnemersindicatieInhoud();
        final Lo3AfnemersindicatieInhoud equals = new Lo3AfnemersindicatieInhoud();

        assertTrue(subject.isLeeg());

        assertEquals(subject, equals);
        assertEquals(subject.hashCode(), equals.hashCode());
        assertEquals(subject.toString(), equals.toString());
    }

    @Test
    public void testFilled() {
        final Lo3AfnemersindicatieInhoud subject = new Lo3AfnemersindicatieInhoud("000123");
        final Lo3AfnemersindicatieInhoud equals = new Lo3AfnemersindicatieInhoud("000123");
        final Lo3AfnemersindicatieInhoud different = new Lo3AfnemersindicatieInhoud("000456");

        assertEquals("000123", subject.getAfnemersindicatie());
        assertFalse(subject.isLeeg());
        assertEquals(subject, subject);
        assertEquals(subject, equals);
        assertFalse(subject.equals(new Object()));
        assertFalse(subject.equals(different));
        assertEquals(subject.hashCode(), equals.hashCode());
        assertEquals(subject.toString(), equals.toString());
    }
}
