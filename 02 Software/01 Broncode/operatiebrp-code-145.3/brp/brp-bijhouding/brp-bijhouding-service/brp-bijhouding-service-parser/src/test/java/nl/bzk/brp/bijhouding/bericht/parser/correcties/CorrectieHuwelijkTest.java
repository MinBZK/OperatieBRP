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
import nl.bzk.brp.bijhouding.bericht.model.CorrectieRegistratieRelatieActieElement;
import nl.bzk.brp.bijhouding.bericht.model.CorrectieVervalRelatieActieElement;
import nl.bzk.brp.bijhouding.bericht.parser.AbstractParserTest;
import nl.bzk.brp.bijhouding.bericht.parser.BijhoudingVerzoekBerichtParser;
import nl.bzk.brp.bijhouding.bericht.parser.ParseException;
import org.junit.Test;

/**
 * Test het parsen van een correctie huwelijk bericht.
 */
public class CorrectieHuwelijkTest extends AbstractParserTest {

    private static final String CORRECTIE_HUWELIJK_BERICHT ="correctie_huwelijk.xml";

    @Test
    public void testValideerXmlBerichtHuwelijk() {
        valideerTegenSchema(this.getClass().getResourceAsStream(CORRECTIE_HUWELIJK_BERICHT));
    }

    @Test
    public void testParsenCorrectieHuwelijkBericht() throws ParseException {
        final BijhoudingVerzoekBerichtParser parser = new BijhoudingVerzoekBerichtParser();
        final BijhoudingVerzoekBericht bericht = parser.parse(this.getClass().getResourceAsStream(CORRECTIE_HUWELIJK_BERICHT));
        assertNotNull(bericht);
        assertAdministratieveHandeling(bericht.getAdministratieveHandeling());
    }

    private void assertAdministratieveHandeling(final AdministratieveHandelingElement administratieveHandeling) {
        assertNotNull(administratieveHandeling);
        assertEqualStringElement("053001", administratieveHandeling.getPartijCode());
        assertEqualStringElement("Onjusite registratie datum aanvang huwelijk.", administratieveHandeling.getToelichtingOntlening());
        assertActies(administratieveHandeling.getActies());
    }

    private void assertActies(final List<ActieElement> acties) {
        assertEquals(2, acties.size());
        assertTrue(acties.get(0) instanceof CorrectieVervalRelatieActieElement);
        assertTrue(acties.get(1) instanceof CorrectieRegistratieRelatieActieElement);
    }
}
