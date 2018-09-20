/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.conversie.model.lo3.categorie;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3NationaliteitCode;
import nl.moderniseringgba.migratie.conversie.model.lo3.element.Lo3RedenNederlandschapCode;

import org.junit.Test;

public class Lo3NationaliteitInhoudTest {

    @Test
    public void testIsLeeg() {
        assertTrue(new Lo3NationaliteitInhoud(null, null, null, null).isLeeg());
        assertTrue(new Lo3NationaliteitInhoud(null, null, new Lo3RedenNederlandschapCode("001"), null).isLeeg());
        assertFalse(new Lo3NationaliteitInhoud(new Lo3NationaliteitCode("6030"), null, null, null).isLeeg());
        assertFalse(new Lo3NationaliteitInhoud(new Lo3NationaliteitCode("6030"), null,
                new Lo3RedenNederlandschapCode("001"), null).isLeeg());
    }
}
