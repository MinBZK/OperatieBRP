/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.parser.GGBN;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import nl.bzk.brp.bijhouding.bericht.model.AbstractRegistratieAanvangHuwelijkOfGpActieElement;
import nl.bzk.brp.bijhouding.bericht.model.ActieElement;
import nl.bzk.brp.bijhouding.bericht.model.AdministratieveHandelingElement;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.model.HuwelijkOfGpElement;
import nl.bzk.brp.bijhouding.bericht.model.RegistratieGeboorteGerelateerdeActieElement;
import nl.bzk.brp.bijhouding.bericht.model.RegistratieGeboreneActieElement;
import nl.bzk.brp.bijhouding.bericht.model.RegistratieNationaliteitActieElement;
import nl.bzk.brp.bijhouding.bericht.model.RegistratieStaatloosActieElement;
import nl.bzk.brp.bijhouding.bericht.model.RelatieGroepElement;
import nl.bzk.brp.bijhouding.bericht.parser.AbstractParserTest;
import nl.bzk.brp.bijhouding.bericht.parser.BijhoudingVerzoekBerichtParser;
import nl.bzk.brp.bijhouding.bericht.parser.ParseException;
import org.junit.Test;

/**
 * Tests voor  ISC Geboorte in Nederland.
 */
public class ISCGeboorteInNederlandParserTest extends AbstractParserTest {
    private static final String BERICHT = "iscGeboorteInNederland.xml";

    @Test
    public void testValideerXmlBerichtGp() {
        valideerTegenIscSchema(this.getClass().getResourceAsStream(BERICHT));
    }

    @Test
    public void testParsenBericht() throws ParseException {
        final BijhoudingVerzoekBerichtParser parser = new BijhoudingVerzoekBerichtParser();
        final BijhoudingVerzoekBericht bericht = parser.parse(this.getClass().getResourceAsStream(BERICHT));
        assertNotNull(bericht);

        final AdministratieveHandelingElement administratieveHandeling = bericht.getAdministratieveHandeling();
        assertNotNull(administratieveHandeling);
        assertEqualStringElement("051801", administratieveHandeling.getPartijCode());
        assertEqualStringElement("Test toelichting op de ontlening", administratieveHandeling.getToelichtingOntlening());

        final List<ActieElement> acties = administratieveHandeling.getActies();
        assertEquals(3, acties.size());
        assertTrue(acties.get(0) instanceof RegistratieGeboreneActieElement);
        assertTrue(acties.get(1) instanceof RegistratieNationaliteitActieElement);
        assertTrue(acties.get(2) instanceof RegistratieStaatloosActieElement);
    }
}
