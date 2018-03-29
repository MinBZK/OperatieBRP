/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.parser.GBNL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import nl.bzk.brp.bijhouding.bericht.model.ActieElement;
import nl.bzk.brp.bijhouding.bericht.model.AdministratieveHandelingElement;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.parser.AbstractParserTest;
import nl.bzk.brp.bijhouding.bericht.parser.BijhoudingVerzoekBerichtParser;
import nl.bzk.brp.bijhouding.bericht.parser.ParseException;
import org.junit.Test;

/**
 * Unit test voor AH Geboorte In Nederland.
 */
public class GeboorteInNederlandParserTest extends AbstractParserTest {
    private static final String GEBOORTE_IN_NEDERLAND = "geboorteInNederland.xml";

    @Test
    public void testValideerXmlBericht() {
        valideerTegenSchema(this.getClass().getResourceAsStream(GEBOORTE_IN_NEDERLAND));
    }

    @Test
    public void testParsenBericht() throws ParseException {
        final BijhoudingVerzoekBerichtParser parser = new BijhoudingVerzoekBerichtParser();
        final BijhoudingVerzoekBericht bericht = parser.parse(this.getClass().getResourceAsStream(GEBOORTE_IN_NEDERLAND));
        assertNotNull(bericht);
        assertNotNull(bericht.getDatumOntvangst());
        final AdministratieveHandelingElement administratieveHandeling = bericht.getAdministratieveHandeling();
        assertNotNull(administratieveHandeling);
        assertEqualStringElement("051801", administratieveHandeling.getPartijCode());
        final List<ActieElement> acties = administratieveHandeling.getActies();
        assertEquals(5, acties.size());
    }
}
