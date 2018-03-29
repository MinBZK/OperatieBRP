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
import nl.bzk.brp.bijhouding.bericht.model.RegistratieWijzigingOnderzoekActieElement;
import nl.bzk.brp.bijhouding.bericht.parser.AbstractParserTest;
import nl.bzk.brp.bijhouding.bericht.parser.BijhoudingVerzoekBerichtParser;
import nl.bzk.brp.bijhouding.bericht.parser.ParseException;
import org.junit.Test;

/**
 * Testen voor het parsen van een bericht tbv het registreren van een aanvang van een onderzoek.
 */
public class RegistreerWijzigingOnderzoekTest extends AbstractParserTest {

    private static final String REGISTRATIE_WIJZIGING_ONDERZOEK_BERICHT ="registratieWijzigingOnderzoek.xml";

    @Test
    public void testValideerXmlBerichtGp() {
        valideerTegenSchema(this.getClass().getResourceAsStream(REGISTRATIE_WIJZIGING_ONDERZOEK_BERICHT));
    }

    @Test
    public void testParsenRegistratieAanvangOnderzoekBericht() throws ParseException {
        final BijhoudingVerzoekBerichtParser parser = new BijhoudingVerzoekBerichtParser();
        final BijhoudingVerzoekBericht bericht = parser.parse(this.getClass().getResourceAsStream(REGISTRATIE_WIJZIGING_ONDERZOEK_BERICHT));
        assertNotNull(bericht);
        assertAdministratieveHandeling(bericht.getAdministratieveHandeling());
    }

    private void assertAdministratieveHandeling(final AdministratieveHandelingElement administratieveHandeling) {
        assertNotNull(administratieveHandeling);
        assertEqualStringElement("053001", administratieveHandeling.getPartijCode());
        assertEqualStringElement("Onderzoek gestaakt", administratieveHandeling.getToelichtingOntlening());
        assertActies(administratieveHandeling.getActies());
    }

    private void assertActies(final List<ActieElement> acties) {
        assertEquals(1, acties.size());
        assertTrue(acties.get(0) instanceof RegistratieWijzigingOnderzoekActieElement);
        final RegistratieWijzigingOnderzoekActieElement actie = (RegistratieWijzigingOnderzoekActieElement) acties.get(0);
        assertNotNull(actie.getPersoon());
        assertEquals(1, actie.getPersoon().getOnderzoeken().size());
        final OnderzoekElement onderzoek = actie.getPersoon().getOnderzoeken().iterator().next();
        assertNotNull(onderzoek.getOnderzoekGroep());
        assertEquals(0, onderzoek.getGegevensInOnderzoek().size());
        assertEquals("Gestaakt", onderzoek.getOnderzoekGroep().getStatusNaam().getWaarde());
    }
}
