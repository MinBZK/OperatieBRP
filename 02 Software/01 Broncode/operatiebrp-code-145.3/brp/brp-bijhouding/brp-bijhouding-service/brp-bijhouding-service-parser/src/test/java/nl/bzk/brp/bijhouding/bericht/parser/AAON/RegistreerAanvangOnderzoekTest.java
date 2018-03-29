/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.parser.AAON;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import nl.bzk.brp.bijhouding.bericht.model.ActieElement;
import nl.bzk.brp.bijhouding.bericht.model.AdministratieveHandelingElement;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.model.OnderzoekElement;
import nl.bzk.brp.bijhouding.bericht.model.RegistratieAanvangOnderzoekActieElement;
import nl.bzk.brp.bijhouding.bericht.parser.AbstractParserTest;
import nl.bzk.brp.bijhouding.bericht.parser.BijhoudingVerzoekBerichtParser;
import nl.bzk.brp.bijhouding.bericht.parser.ParseException;
import org.junit.Test;

/**
 * Testen voor het parsen van een bericht tbv het registreren van een aanvang van een onderzoek.
 */
public class RegistreerAanvangOnderzoekTest extends AbstractParserTest {

    private static final String REGISTRATIE_AANVANG_ONDERZOEK_BERICHT ="registratieAanvangOnderzoek.xml";

    @Test
    public void testValideerXmlBerichtGp() {
        valideerTegenSchema(this.getClass().getResourceAsStream(REGISTRATIE_AANVANG_ONDERZOEK_BERICHT));
    }

    @Test
    public void testParsenRegistratieAanvangOnderzoekBericht() throws ParseException {
        final BijhoudingVerzoekBerichtParser parser = new BijhoudingVerzoekBerichtParser();
        final BijhoudingVerzoekBericht bericht = parser.parse(this.getClass().getResourceAsStream(REGISTRATIE_AANVANG_ONDERZOEK_BERICHT));
        assertNotNull(bericht);
        assertAdministratieveHandeling(bericht.getAdministratieveHandeling());
    }

    private void assertAdministratieveHandeling(final AdministratieveHandelingElement administratieveHandeling) {
        assertNotNull(administratieveHandeling);
        assertEqualStringElement("053001", administratieveHandeling.getPartijCode());
        assertEqualStringElement("Onderzoek gestart naar echtpaar", administratieveHandeling.getToelichtingOntlening());
        assertActies(administratieveHandeling.getActies());
    }

    private void assertActies(final List<ActieElement> acties) {
        assertEquals(1, acties.size());
        assertTrue(acties.get(0) instanceof RegistratieAanvangOnderzoekActieElement);
        final RegistratieAanvangOnderzoekActieElement actie = (RegistratieAanvangOnderzoekActieElement) acties.get(0);
        assertNotNull(actie.getPersoon());
        assertEquals(1, actie.getPersoon().getOnderzoeken().size());
        final OnderzoekElement onderzoek = actie.getPersoon().getOnderzoeken().iterator().next();
        assertNotNull(onderzoek.getOnderzoekGroep());
        assertEquals(2, onderzoek.getGegevensInOnderzoek().size());
        assertEquals(Integer.valueOf(20140313), onderzoek.getOnderzoekGroep().getDatumAanvang().getWaarde());
    }
}
