/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

import static org.junit.Assert.assertEquals;

import nl.bzk.algemeenbrp.dal.domein.EnumeratieTest;
import org.junit.Test;

/**
 * Uitbreiding van {@link EnumeratieTest} voor {@link EffectAfnemerindicaties}.
 */
public class EffectAfnemerindicatiesTest {

    @Test
    public void testGetOmschrijving(){
        assertEquals("Als gevolg van de dienst is een (aantal) afnemerindicatie(s) geplaatst.", EffectAfnemerindicaties.PLAATSING.getOmschrijving());
    }
}
