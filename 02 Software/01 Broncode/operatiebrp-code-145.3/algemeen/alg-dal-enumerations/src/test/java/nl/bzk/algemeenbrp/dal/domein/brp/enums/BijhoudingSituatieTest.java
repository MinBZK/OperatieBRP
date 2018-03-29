/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

import static org.junit.Assert.assertEquals;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.BijhoudingSituatie;
import org.junit.Test;

/**
 * Unittest voor {@link BijhoudingSituatie} voor de methodes die niet door de {@link nl.bzk.algemeenbrp.dal.domein.EnumeratieTest} getest worden.
 */
public class BijhoudingSituatieTest {
    @Test
    public void getOmschrijving() throws Exception {
        assertEquals(
            "De persoon valt onder de bijhoudingsverantwoordelijkheid van een partij die valt onder het GBA-regime.",
            BijhoudingSituatie.GBA.getOmschrijving());
    }

}
