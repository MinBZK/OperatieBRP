/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.lo3.categorie;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Datum;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3GemeenteCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3LandCode;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3Long;
import nl.bzk.migratiebrp.conversie.model.lo3.element.Lo3RedenOntbindingHuwelijkOfGpCode;

import org.junit.Test;

public class Lo3HuwelijkOfGpInhoudTest {

    @Test
    public void testIsLeeg() {
        assertTrue(new Lo3HuwelijkOfGpInhoud(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null).isLeeg());
        assertTrue(new Lo3HuwelijkOfGpInhoud(
            Lo3Long.wrap(1234567890L),
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null).isLeeg());
        assertFalse(new Lo3HuwelijkOfGpInhoud(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            new Lo3Datum(20120101),
            null,
            null,
            null,
            null,
            null,
            null,
            null).isLeeg());
        assertFalse(new Lo3HuwelijkOfGpInhoud(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            new Lo3Datum(20120101),
            null,
            null,
            null,
            null).isLeeg());
        assertFalse(new Lo3HuwelijkOfGpInhoud(
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            new Lo3Datum(20120101),
            new Lo3GemeenteCode("1234"),
            new Lo3LandCode("6030"),
            new Lo3Datum(20120101),
            new Lo3GemeenteCode("1234"),
            new Lo3LandCode("6030"),
            new Lo3RedenOntbindingHuwelijkOfGpCode("O"),
            null).isLeeg());
    }

}
