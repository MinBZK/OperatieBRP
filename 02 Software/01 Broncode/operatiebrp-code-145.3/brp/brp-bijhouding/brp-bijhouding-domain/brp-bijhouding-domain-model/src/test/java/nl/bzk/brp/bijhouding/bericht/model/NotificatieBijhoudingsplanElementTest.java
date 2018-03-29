/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.OBJECTTYPE_ATTRIBUUT;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

/**
 * Unittest voor {@link NotificatieBijhoudingsplanElement}.
 */
public class NotificatieBijhoudingsplanElementTest {
    @Test
    public void valideerInhoud() throws Exception {
        Map<String, String> att = new LinkedHashMap<>();
        att.put(OBJECTTYPE_ATTRIBUUT.toString(), "objecttype");
        List<BijhoudingsplanPersoonElement> bijplanPers = new ArrayList<>();
        NotificatieBijhoudingsplanElement element = new NotificatieBijhoudingsplanElement(att, new StringElement("123"), null, null, null, bijplanPers);
        assertTrue(element.valideerInhoud().isEmpty());
    }


    @Test
    public void getInstance() {
        final NotificatieBijhoudingsplanElement element = NotificatieBijhoudingsplanElement.getInstance("123", null, null, null, new ArrayList<>());
        assertNotNull(element);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getInstanceException() {
        final NotificatieBijhoudingsplanElement element = NotificatieBijhoudingsplanElement.getInstance(null, null, null, null, new ArrayList<>());
    }

}
