/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.parser.VZ;

import java.util.List;
import nl.bzk.brp.bijhouding.bericht.model.ActieElement;
import nl.bzk.brp.bijhouding.bericht.model.AdministratieveHandelingElement;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.model.RegistratieAdresActieElement;
import nl.bzk.brp.bijhouding.bericht.model.RegistratieVerstrekkingsbeperkingActieElement;
import nl.bzk.brp.bijhouding.bericht.parser.AbstractParserTest;
import nl.bzk.brp.bijhouding.bericht.parser.BijhoudingVerzoekBerichtParser;
import nl.bzk.brp.bijhouding.bericht.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests voor verhuizing intergemeentelijk.
 */
public class VerhuizingIntergemeentelijkParserTest extends AbstractParserTest {

    private static final String VERHUIZING_INTERGEMEENTELIJK_XML = "verhuizingIntergemeentelijk.xml";

    @Test
    public void testValideerXmlBerichtGp() {
        valideerTegenSchema(this.getClass().getResourceAsStream(VERHUIZING_INTERGEMEENTELIJK_XML));
    }

    @Test
    public void testParsenEenvoudigOmzettingBericht() throws ParseException {
        final BijhoudingVerzoekBerichtParser parser = new BijhoudingVerzoekBerichtParser();
        final BijhoudingVerzoekBericht bericht = parser.parse(this.getClass().getResourceAsStream(VERHUIZING_INTERGEMEENTELIJK_XML));
        Assert.assertNotNull(bericht);
        assertAdministratieveHandeling(bericht.getAdministratieveHandeling());
    }

    private void assertAdministratieveHandeling(final AdministratieveHandelingElement administratieveHandeling) {
        Assert.assertNotNull(administratieveHandeling);
        assertEqualStringElement("051801", administratieveHandeling.getPartijCode());
        assertActies(administratieveHandeling.getActies());

    }

    private void assertActies(final List<ActieElement> acties) {
        Assert.assertEquals(2, acties.size());
        Assert.assertTrue(acties.get(0) instanceof RegistratieAdresActieElement);
        Assert.assertTrue(acties.get(1) instanceof RegistratieVerstrekkingsbeperkingActieElement);
    }

}
