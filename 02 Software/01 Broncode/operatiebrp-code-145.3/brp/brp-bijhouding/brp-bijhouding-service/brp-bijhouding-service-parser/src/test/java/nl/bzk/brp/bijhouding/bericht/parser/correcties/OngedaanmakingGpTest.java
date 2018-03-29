/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.parser.correcties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import nl.bzk.brp.bijhouding.bericht.model.ActieElement;
import nl.bzk.brp.bijhouding.bericht.model.AdministratieveHandelingElement;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.model.CharacterElement;
import nl.bzk.brp.bijhouding.bericht.model.VervalGeregistreerdPartnerschapActieElement;
import nl.bzk.brp.bijhouding.bericht.parser.AbstractParserTest;
import nl.bzk.brp.bijhouding.bericht.parser.BijhoudingVerzoekBerichtParser;
import nl.bzk.brp.bijhouding.bericht.parser.ParseException;
import org.junit.Test;

/**
 * Test het parsen van een ongedaanmaking geregistreerd partnerschap bericht.
 */
public class OngedaanmakingGpTest extends AbstractParserTest {

    private static final String ONGEDAANMAKING_GP_XML = "ongedaanmaking_gp.xml";

    @Test
    public void testValideerXmlBerichtHuwelijk() {
        valideerTegenSchema(this.getClass().getResourceAsStream(ONGEDAANMAKING_GP_XML));
    }

    @Test
    public void testParsenCorrectieHuwelijkBericht() throws ParseException {
        final BijhoudingVerzoekBerichtParser parser = new BijhoudingVerzoekBerichtParser();
        final BijhoudingVerzoekBericht bericht = parser.parse(this.getClass().getResourceAsStream(ONGEDAANMAKING_GP_XML));
        assertNotNull(bericht);
        assertAdministratieveHandeling(bericht.getAdministratieveHandeling());
    }

    private void assertAdministratieveHandeling(final AdministratieveHandelingElement administratieveHandeling) {
        assertNotNull(administratieveHandeling);
        assertEqualStringElement("053001", administratieveHandeling.getPartijCode());
        assertActies(administratieveHandeling.getActies());
    }

    private void assertActies(final List<ActieElement> acties) {
        assertEquals(1, acties.size());
        assertTrue(acties.get(0) instanceof VervalGeregistreerdPartnerschapActieElement);
        assertEquals(new CharacterElement('O'), ((VervalGeregistreerdPartnerschapActieElement) acties.get(0)).getNadereAanduidingVerval());
    }
}
