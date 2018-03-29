/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.parser;

import java.util.List;

import nl.bzk.brp.bijhouding.bericht.model.AbstractRegistratieEindeHuwelijkOfGpActieElement;
import nl.bzk.brp.bijhouding.bericht.model.ActieElement;
import nl.bzk.brp.bijhouding.bericht.model.AdministratieveHandelingElement;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingBerichtSoort;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.model.HuwelijkOfGpElement;
import nl.bzk.brp.bijhouding.bericht.model.RelatieGroepElement;

import org.junit.Assert;
import org.junit.Test;

/**
 * Tests voor BijhoudingVerzoekBerichtParser.
 */
public class OmzettingGeregistreerdPartnerschapInHuwelijkBerichtParserTest extends AbstractParserTest {

    public static final String OMZETTING_GP_H_BERICHT = "eenvoudigOmzettingGeregistreerdPartnerschapInHuwelijkBericht.xml";


    @Test
    public void testValideerXmlBericht() {
        valideerTegenSchema(this.getClass().getResourceAsStream(OMZETTING_GP_H_BERICHT));
    }

    @Test
    public void testParsenEenvoudigGPBericht() throws ParseException {
        final BijhoudingVerzoekBerichtParser parser = new BijhoudingVerzoekBerichtParser();
        final BijhoudingVerzoekBericht bericht = parser.parse(this.getClass().getResourceAsStream(OMZETTING_GP_H_BERICHT));
        Assert.assertNotNull(bericht);
        Assert.assertEquals(BijhoudingBerichtSoort.REGISTREER_HUWELIJK_GP, bericht.getSoort());
        assertAdministratieveHandeling(bericht.getAdministratieveHandeling());
    }

    private void assertAdministratieveHandeling(final AdministratieveHandelingElement administratieveHandeling) {
        Assert.assertNotNull(administratieveHandeling);
        assertEqualStringElement("123456", administratieveHandeling.getPartijCode());
        assertActies(administratieveHandeling.getActies());

    }

    private void assertActies(final List<ActieElement> acties) {
        Assert.assertEquals(2, acties.size());
        Assert.assertTrue(acties.get(0) instanceof AbstractRegistratieEindeHuwelijkOfGpActieElement);
        final AbstractRegistratieEindeHuwelijkOfGpActieElement actie = (AbstractRegistratieEindeHuwelijkOfGpActieElement) acties.get(0);
        assertHuwelijkOfGp(actie.getHuwelijkOfGp());
    }

    private void assertHuwelijkOfGp(final HuwelijkOfGpElement huwelijk) {
        Assert.assertNotNull(huwelijk);
        assertRelatie(huwelijk.getRelatieGroep());
    }

    private void assertRelatie(final RelatieGroepElement relatie) {
        Assert.assertNotNull(relatie);
        assertEqualDatumElement("2016-03-22", relatie.getDatumEinde());
        assertEqualStringElement("0174", relatie.getGemeenteEindeCode());
        assertEqualStringElement(null, relatie.getWoonplaatsnaamAanvang());
    }
}
