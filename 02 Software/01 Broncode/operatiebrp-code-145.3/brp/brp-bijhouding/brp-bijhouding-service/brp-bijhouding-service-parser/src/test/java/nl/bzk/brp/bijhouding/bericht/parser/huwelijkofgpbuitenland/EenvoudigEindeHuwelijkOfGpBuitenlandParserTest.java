/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.parser.huwelijkofgpbuitenland;

import static org.junit.Assert.assertNull;

import java.util.List;
import nl.bzk.brp.bijhouding.bericht.model.AbstractRegistratieEindeHuwelijkOfGpActieElement;
import nl.bzk.brp.bijhouding.bericht.model.ActieElement;
import nl.bzk.brp.bijhouding.bericht.model.AdministratieveHandelingElement;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.model.HuwelijkOfGpElement;
import nl.bzk.brp.bijhouding.bericht.model.RelatieGroepElement;
import nl.bzk.brp.bijhouding.bericht.parser.AbstractParserTest;
import nl.bzk.brp.bijhouding.bericht.parser.BijhoudingVerzoekBerichtParser;
import nl.bzk.brp.bijhouding.bericht.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests voor Eenvoudig Einde GeregistreerdPartnerschap of Huwelijk in buitenland.
 */
public class EenvoudigEindeHuwelijkOfGpBuitenlandParserTest extends AbstractParserTest {

    public static final String EENVOUDIG_EINDE_HUWELIJK_BERICHT = "eenvoudigEindeHuwelijkBuitenlandBericht.xml";
    public static final String EENVOUDIG_EINDE_GP_BERICHT = "eenvoudigEindeGpBuitenlandBericht.xml";

    @Test
    public void testValideerXmlBerichtHuwelijk() {
        valideerTegenSchema(this.getClass().getResourceAsStream(EENVOUDIG_EINDE_HUWELIJK_BERICHT));
    }

    @Test
    public void testValideerXmlBerichtGp() {
        valideerTegenSchema(this.getClass().getResourceAsStream(EENVOUDIG_EINDE_GP_BERICHT));
    }

    @Test
    public void testParsenEenvoudigHuwelijkBericht() throws ParseException {
        final BijhoudingVerzoekBerichtParser parser = new BijhoudingVerzoekBerichtParser();
        final BijhoudingVerzoekBericht bericht = parser.parse(this.getClass().getResourceAsStream(EENVOUDIG_EINDE_HUWELIJK_BERICHT));
        Assert.assertNotNull(bericht);
        assertAdministratieveHandeling(bericht.getAdministratieveHandeling());
    }

    @Test
    public void testParsenEenvoudigGpBericht() throws ParseException {
        final BijhoudingVerzoekBerichtParser parser = new BijhoudingVerzoekBerichtParser();
        final BijhoudingVerzoekBericht bericht = parser.parse(this.getClass().getResourceAsStream(EENVOUDIG_EINDE_GP_BERICHT));
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
        assertNull(relatie.getDatumAanvang());
        assertEqualDatumElement("2016-09-01", relatie.getDatumEinde());
        assertEqualCharacterElement('1', relatie.getRedenEindeCode());
        assertEqualStringElement("6037", relatie.getLandGebiedEindeCode());
        assertEqualStringElement("Barcelona", relatie.getBuitenlandsePlaatsEinde());
        assertEqualStringElement("Catalonie", relatie.getBuitenlandseRegioEinde());
        assertEqualStringElement("Strand", relatie.getOmschrijvingLocatieEinde());
    }
}
