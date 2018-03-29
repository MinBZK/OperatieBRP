/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.parser;

import static org.junit.Assert.assertNull;

import java.util.List;
import nl.bzk.brp.bijhouding.bericht.model.ActieElement;
import nl.bzk.brp.bijhouding.bericht.model.AdministratieveHandelingElement;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.model.HuwelijkElement;
import nl.bzk.brp.bijhouding.bericht.model.RegistratieEindeHuwelijkActieElement;
import nl.bzk.brp.bijhouding.bericht.model.RelatieGroepElement;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests voor EenvoudigEindeGeregistreerdPartnerschapNederland.
 */
public class EenvoudigEindeHuwelijkNederlandParserTest extends AbstractParserTest {

    public static final String EENVOUDIG_BIJHOUDING_BERICHT = "eenvoudigEindeHuwelijkNederlandBericht.xml";

    @Test
    public void testValideerXmlBericht() {
        valideerTegenSchema(this.getClass().getResourceAsStream(EENVOUDIG_BIJHOUDING_BERICHT));
    }

    @Test
    public void testParsenEenvoudigBericht() throws ParseException {
        final BijhoudingVerzoekBerichtParser parser = new BijhoudingVerzoekBerichtParser();
        final BijhoudingVerzoekBericht bericht = parser.parse(this.getClass().getResourceAsStream(EENVOUDIG_BIJHOUDING_BERICHT));
        Assert.assertNotNull(bericht);
        assertAdministratieveHandeling(bericht.getAdministratieveHandeling());
    }

    private void assertAdministratieveHandeling(final AdministratieveHandelingElement administratieveHandeling) {
        Assert.assertNotNull(administratieveHandeling);
        assertEqualStringElement("123456", administratieveHandeling.getPartijCode());
        assertEqualStringElement("Test toelichting op de ontlening", administratieveHandeling.getToelichtingOntlening());
        assertActies(administratieveHandeling.getActies());
    }

    private void assertActies(final List<ActieElement> acties) {
        Assert.assertEquals(1, acties.size());
        Assert.assertTrue(acties.get(0) instanceof RegistratieEindeHuwelijkActieElement);
        final RegistratieEindeHuwelijkActieElement actie = (RegistratieEindeHuwelijkActieElement) acties.get(0);
        assertHuwelijk(actie.getHuwelijk());
    }

    private void assertHuwelijk(final HuwelijkElement huwelijk) {
        Assert.assertNotNull(huwelijk);
        assertRelatie(huwelijk.getRelatieGroep());
    }

    private void assertRelatie(final RelatieGroepElement relatie) {
        Assert.assertNotNull(relatie);
        assertNull(relatie.getDatumAanvang());
        assertEqualDatumElement("2016-09-01", relatie.getDatumEinde());
        assertEqualCharacterElement('1', relatie.getRedenEindeCode());
        assertEqualStringElement("1111", relatie.getGemeenteEindeCode());
        assertEqualStringElement("plaats", relatie.getWoonplaatsnaamEinde());
    }
}
