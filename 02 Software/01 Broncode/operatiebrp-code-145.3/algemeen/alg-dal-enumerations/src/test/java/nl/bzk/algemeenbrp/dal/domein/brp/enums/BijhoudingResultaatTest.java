/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unittest voor {@link BijhoudingResultaat} voor de methodes die niet door de {@link nl.bzk.algemeenbrp.dal.domein.EnumeratieTest} getest worden.
 */
public class BijhoudingResultaatTest {

    @Test
    public void testGetOmschrijving() {
        assertEquals("Bijhouding direct verwerkt in BRP", BijhoudingResultaat.VERWERKT.getOmschrijving());
    }

    @Test
    public void parseNaam() {
        assertEquals(BijhoudingResultaat.DEELS_UITGESTELD, BijhoudingResultaat.parseNaam("(Deels) uitgesteld"));
    }
}
