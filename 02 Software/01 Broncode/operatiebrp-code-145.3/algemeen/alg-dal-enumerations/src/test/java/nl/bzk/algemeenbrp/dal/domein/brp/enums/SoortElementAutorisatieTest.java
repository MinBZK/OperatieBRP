/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.algemeenbrp.dal.domein.brp.enums;

import static org.junit.Assert.assertEquals;

import nl.bzk.algemeenbrp.dal.domein.EnumeratieTest;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortElementAutorisatie;
import org.junit.Test;

/**
 * Uitbreiding van {@link EnumeratieTest} voor {@link SoortElementAutorisatie}.
 */
public class SoortElementAutorisatieTest {

    @Test
    public void testGetOmschrijving() {
        assertEquals("Een afnemer zou bij voorkeur een autorisatie voor dit attribuut moeten krijgen. Er is echter geen wettelijke grondslag.",
            SoortElementAutorisatie.AANBEVOLEN.getOmschrijving());
    }
}
