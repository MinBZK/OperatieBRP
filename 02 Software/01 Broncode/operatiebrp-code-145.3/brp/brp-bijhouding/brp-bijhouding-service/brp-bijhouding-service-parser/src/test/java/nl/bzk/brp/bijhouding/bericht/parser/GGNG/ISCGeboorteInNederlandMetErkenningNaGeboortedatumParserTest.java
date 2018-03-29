/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.parser.GGNG;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import nl.bzk.brp.bijhouding.bericht.model.ActieElement;
import nl.bzk.brp.bijhouding.bericht.model.AdministratieveHandelingElement;
import nl.bzk.brp.bijhouding.bericht.model.BeeindigingNationaliteitActieElement;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.model.RegistratieGeboreneActieElement;
import nl.bzk.brp.bijhouding.bericht.model.RegistratieGeslachtsnaamVoornaamActieElement;
import nl.bzk.brp.bijhouding.bericht.model.RegistratieNationaliteitActieElement;
import nl.bzk.brp.bijhouding.bericht.model.RegistratieOuderActieElement;
import nl.bzk.brp.bijhouding.bericht.model.RegistratieStaatloosActieElement;
import nl.bzk.brp.bijhouding.bericht.parser.AbstractParserTest;
import nl.bzk.brp.bijhouding.bericht.parser.BijhoudingVerzoekBerichtParser;
import nl.bzk.brp.bijhouding.bericht.parser.ParseException;
import org.junit.Test;

/**
 * Tests voor  ISC Geboorte in Nederland.
 */
public class ISCGeboorteInNederlandMetErkenningNaGeboortedatumParserTest extends AbstractParserTest {
    private static final String BERICHT = "iscGeboorteInNederlandMetErkenningNaGeboortedatum.xml";

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
        assertEqualStringElement("053001", administratieveHandeling.getPartijCode());
        assertEqualStringElement("toelichting", administratieveHandeling.getToelichtingOntlening());

        final List<ActieElement> acties = administratieveHandeling.getActies();
        assertEquals(6, acties.size());
        assertTrue(acties.get(0) instanceof RegistratieGeboreneActieElement);
        assertTrue(acties.get(1) instanceof BeeindigingNationaliteitActieElement);
        assertTrue(acties.get(2) instanceof RegistratieOuderActieElement);
        assertTrue(acties.get(3) instanceof RegistratieGeslachtsnaamVoornaamActieElement);
        assertTrue(acties.get(4) instanceof RegistratieNationaliteitActieElement);
        assertTrue(acties.get(5) instanceof RegistratieStaatloosActieElement);
    }
}
