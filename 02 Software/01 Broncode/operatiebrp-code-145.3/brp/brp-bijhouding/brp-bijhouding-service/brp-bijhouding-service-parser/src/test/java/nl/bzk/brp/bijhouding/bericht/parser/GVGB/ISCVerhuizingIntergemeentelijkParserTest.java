/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.parser.GVGB;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import nl.bzk.brp.bijhouding.bericht.model.ActieElement;
import nl.bzk.brp.bijhouding.bericht.model.AdministratieveHandelingElement;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.model.RegistratieAdresActieElement;
import nl.bzk.brp.bijhouding.bericht.model.RegistratieVerstrekkingsbeperkingActieElement;
import nl.bzk.brp.bijhouding.bericht.parser.AbstractParserTest;
import nl.bzk.brp.bijhouding.bericht.parser.BijhoudingVerzoekBerichtParser;
import nl.bzk.brp.bijhouding.bericht.parser.ParseException;
import org.junit.Test;

/**
 * Tests voor Eenvoudig ISC Verhuizing intergemeentelijk in Nederland.
 */
public class ISCVerhuizingIntergemeentelijkParserTest extends AbstractParserTest {

    private static final String BERICHT_BESTANDSNAAM = "gba_verhuizingIntergemeentelijk.xml";

    @Test
    public void testValideerXmlBerichtGp() {
        valideerTegenIscSchema(this.getClass().getResourceAsStream(BERICHT_BESTANDSNAAM));
    }

    @Test
    public void testParsenEenvoudigOmzettingBericht() throws ParseException {
        final BijhoudingVerzoekBerichtParser parser = new BijhoudingVerzoekBerichtParser();
        final BijhoudingVerzoekBericht bericht = parser.parse(this.getClass().getResourceAsStream(BERICHT_BESTANDSNAAM));
        assertNotNull(bericht);
        assertAdministratieveHandeling(bericht.getAdministratieveHandeling());
    }

    private void assertAdministratieveHandeling(final AdministratieveHandelingElement administratieveHandeling) {
        assertNotNull(administratieveHandeling);
        assertEqualStringElement("507013", administratieveHandeling.getPartijCode());
        assertActies(administratieveHandeling.getActies());

    }

    private void assertActies(final List<ActieElement> acties) {
        assertEquals(2, acties.size());
        assertTrue(acties.get(0) instanceof RegistratieAdresActieElement);
        assertTrue(acties.get(1) instanceof RegistratieVerstrekkingsbeperkingActieElement);
    }
}
