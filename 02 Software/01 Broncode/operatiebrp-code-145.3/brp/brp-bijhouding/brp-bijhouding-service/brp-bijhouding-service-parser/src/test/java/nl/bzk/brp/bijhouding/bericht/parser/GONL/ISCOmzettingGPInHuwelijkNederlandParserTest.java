/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.parser.GONL;

import java.util.List;

import nl.bzk.brp.bijhouding.bericht.model.ActieElement;
import nl.bzk.brp.bijhouding.bericht.model.AdministratieveHandelingElement;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.model.HuwelijkOfGpElement;
import nl.bzk.brp.bijhouding.bericht.model.RegistratieEindeGeregistreerdPartnerschapActieElement;
import nl.bzk.brp.bijhouding.bericht.model.RelatieGroepElement;
import nl.bzk.brp.bijhouding.bericht.parser.AbstractParserTest;
import nl.bzk.brp.bijhouding.bericht.parser.BijhoudingVerzoekBerichtParser;
import nl.bzk.brp.bijhouding.bericht.parser.ParseException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests voor Eenvoudig ISC GeregistreerdPartnerschap in Nederland.
 */
public class ISCOmzettingGPInHuwelijkNederlandParserTest extends AbstractParserTest {

    public static final String EENVOUDIG_OMZETTING_BERICHT = "ISCEenvoudigOmzettingGeregistreerdPartnerschapInHuwelijkInNederlandBericht.xml";

    @Test
    public void testValideerXmlBerichtGp() {
        valideerTegenIscSchema(this.getClass().getResourceAsStream(EENVOUDIG_OMZETTING_BERICHT));
    }

    @Test
    public void testParsenEenvoudigOmzettingBericht() throws ParseException {
        final BijhoudingVerzoekBerichtParser parser = new BijhoudingVerzoekBerichtParser();
        final BijhoudingVerzoekBericht bericht = parser.parse(this.getClass().getResourceAsStream(EENVOUDIG_OMZETTING_BERICHT));
        Assert.assertNotNull(bericht);
        assertAdministratieveHandeling(bericht.getAdministratieveHandeling());
    }

    private void assertAdministratieveHandeling(final AdministratieveHandelingElement administratieveHandeling) {
        Assert.assertNotNull(administratieveHandeling);
        assertEqualStringElement("051801", administratieveHandeling.getPartijCode());
        assertActies(administratieveHandeling.getActies());

    }

    private void assertActies(final List<ActieElement> acties) {
        Assert.assertEquals(1, acties.size());
        Assert.assertTrue(acties.get(0) instanceof RegistratieEindeGeregistreerdPartnerschapActieElement);
        final RegistratieEindeGeregistreerdPartnerschapActieElement actie = (RegistratieEindeGeregistreerdPartnerschapActieElement) acties.get(0);
        assertHuwelijkOfGp(actie.getGeregistreerdPartnerschap());

    }

    private void assertHuwelijkOfGp(final HuwelijkOfGpElement huwelijk) {
        Assert.assertNotNull(huwelijk);
        assertRelatie(huwelijk.getRelatieGroep());
    }

    private void assertRelatie(final RelatieGroepElement relatie) {
        Assert.assertNotNull(relatie);

        assertEqualDatumElement("2016-01-01", relatie.getDatumEinde());
        assertEqualStringElement("0518", relatie.getGemeenteEindeCode());

        assertEqualStringElement(null, relatie.getWoonplaatsnaamAanvang());
    }
}
