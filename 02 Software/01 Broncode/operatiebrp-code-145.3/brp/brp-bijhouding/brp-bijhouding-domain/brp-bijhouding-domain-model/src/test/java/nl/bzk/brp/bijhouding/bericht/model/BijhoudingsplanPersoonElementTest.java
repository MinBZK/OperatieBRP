/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.model;

import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.COMMUNICATIE_ID_ATTRIBUUT;
import static nl.bzk.brp.bijhouding.bericht.model.BmrAttribuutEnum.OBJECTTYPE_ATTRIBUUT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Testen voor {@link BijhoudingsplanPersoonElement}.
 */
@RunWith(MockitoJUnitRunner.class)
public class BijhoudingsplanPersoonElementTest {
    private Map<String, String> att;

    @Before
    public void setup() {
        att = new LinkedHashMap<>();
        att.put(OBJECTTYPE_ATTRIBUUT.toString(), "objecttype");
        att.put(COMMUNICATIE_ID_ATTRIBUUT.toString(), "ci_test");
    }


    @Test
    public void valideerInhoud() throws Exception {
        final ElementBuilder builder = new ElementBuilder();
        final ElementBuilder.PersoonParameters params = new ElementBuilder.PersoonParameters();
        final PersoonGegevensElement persoon = builder.maakPersoonGegevensElement(att,params);

        final BijhoudingsplanPersoonElement element = new BijhoudingsplanPersoonElement(att, persoon, new StringElement("naam"));
        assertTrue(element.valideerInhoud().isEmpty());
        assertEquals("naam", element.getSituatieNaam().getWaarde());
        assertEquals(persoon, element.getPersoon());
    }

    @Test
    public void getInstanceTest() {
        final ElementBuilder builder = new ElementBuilder();
        final ElementBuilder.PersoonParameters params = new ElementBuilder.PersoonParameters();
        final PersoonGegevensElement persoon = builder.maakPersoonGegevensElement(att,params);

        final BijhoudingsplanPersoonElement element = BijhoudingsplanPersoonElement.getInstance(persoon, "naam");
        assertTrue(element.valideerInhoud().isEmpty());
        assertEquals("naam", element.getSituatieNaam().getWaarde());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetInstanceException() {
        final ElementBuilder builder = new ElementBuilder();
        final ElementBuilder.PersoonParameters params = new ElementBuilder.PersoonParameters();
        final PersoonGegevensElement persoon = builder.maakPersoonGegevensElement(att,params);

        final BijhoudingsplanPersoonElement elementNullName = BijhoudingsplanPersoonElement.getInstance(persoon, null);
        assertNull(elementNullName.getSituatieNaam());
    }

}
