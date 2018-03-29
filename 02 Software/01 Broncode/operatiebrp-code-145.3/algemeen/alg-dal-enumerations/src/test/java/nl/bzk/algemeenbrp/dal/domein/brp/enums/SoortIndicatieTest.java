/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import nl.bzk.algemeenbrp.dal.domein.EnumeratieTest;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortIndicatie;
import org.junit.Test;

/**
 * Uitbreiding van {@link EnumeratieTest} voor {@link SoortIndicatie}.
 */
public class SoortIndicatieTest {

    @Test
    public void testGetOmschrijving(){
        assertEquals("Behandeld als Nederlander?", SoortIndicatie.BEHANDELD_ALS_NEDERLANDER.getOmschrijving());
    }

    @Test
    public void testIsMaterieleHistorieVanToepassing() {
        assertTrue(SoortIndicatie.BEHANDELD_ALS_NEDERLANDER.isMaterieleHistorieVanToepassing());
        assertFalse(SoortIndicatie.BIJZONDERE_VERBLIJFSRECHTELIJKE_POSITIE.isMaterieleHistorieVanToepassing());
    }

}
