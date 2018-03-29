/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.COMMUNICATIE_ID_ATTRIBUUT;
import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.OBJECTTYPE_ATTRIBUUT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * test.
 */
public class BijhoudingElementTest {
    @Test
    public void getInstance() throws Exception {
        final Map<String, String> att = new LinkedHashMap<>();
        att.put(OBJECTTYPE_ATTRIBUUT.toString(), "objecttype");
        att.put(COMMUNICATIE_ID_ATTRIBUUT.toString(), "ci_test");
        final BijhoudingElement instance = BijhoudingElement.getInstance("1");
        final BijhoudingElement bijhoudingElement = new BijhoudingElement(att, new StringElement("1"));

        assertNotNull(instance);
        assertNotNull(bijhoudingElement);
        assertEquals(instance.getPartijCode().getWaarde(), bijhoudingElement.getPartijCode().getWaarde());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getInstanceNull() {
        BijhoudingElement.getInstance(null);
    }

    @Test
    public void testValideer() {
        assertTrue(BijhoudingElement.getInstance("1").valideerInhoud().isEmpty());
    }

}
