/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unittest voor {@link Regel}.
 */
public class RegelTest {

    @Test
    public void testGetSoortMelding() {
        assertEquals(SoortMelding.FOUT, Regel.R1257.getSoortMelding());
    }

    @Test
    public void testGetMelding() {
        assertEquals("De combinatie ondertekenaar en transporteur is onjuist.", Regel.R1257.getMelding());
    }

    @Test
    public void testRegelZonderSoortMelding() {
        assertEquals(null, Regel.R1268.getSoortMelding());
    }
}
