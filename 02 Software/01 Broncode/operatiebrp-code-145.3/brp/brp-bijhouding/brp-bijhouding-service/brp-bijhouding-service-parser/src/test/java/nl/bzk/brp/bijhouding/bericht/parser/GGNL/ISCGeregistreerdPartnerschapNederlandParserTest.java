/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.parser.GGNL;

import java.util.List;

import nl.bzk.brp.bijhouding.bericht.model.AbstractRegistratieAanvangHuwelijkOfGpActieElement;
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
 * Tests voor Eenvoudig ISC GeregistreerdPartnerschap in Nederland.
 */
public class ISCGeregistreerdPartnerschapNederlandParserTest extends AbstractParserTest {

    public static final String EENVOUDIG_AANGAAN_GP_BERICHT = "ISCeenvoudigAangaanGeregistreerdPartnerschapNederlandBericht.xml";
    public static final String EENVOUDIG_BEEINDIGEN_GP_BERICHT = "ISCeenvoudigBeeindigingGeregistreerdPartnerschapInNederland.xml";

    @Test
    public void testValideerXmlBerichtGp() {
        valideerTegenIscSchema(this.getClass().getResourceAsStream(EENVOUDIG_AANGAAN_GP_BERICHT));
        valideerTegenIscSchema(this.getClass().getResourceAsStream(EENVOUDIG_BEEINDIGEN_GP_BERICHT));
    }

    @Test
    public void testParsenEenvoudigGpBericht() throws ParseException {
        final BijhoudingVerzoekBerichtParser parser = new BijhoudingVerzoekBerichtParser();
        final BijhoudingVerzoekBericht bericht = parser.parse(this.getClass().getResourceAsStream(EENVOUDIG_AANGAAN_GP_BERICHT));
        Assert.assertNotNull(bericht);
        assertAdministratieveHandeling(bericht.getAdministratieveHandeling(), false);
    }

    @Test
    public void testParsenEenvoudigEindeGpBericht() throws ParseException {
        final BijhoudingVerzoekBerichtParser parser = new BijhoudingVerzoekBerichtParser();
        final BijhoudingVerzoekBericht bericht = parser.parse(this.getClass().getResourceAsStream(EENVOUDIG_BEEINDIGEN_GP_BERICHT));
        Assert.assertNotNull(bericht);
        assertAdministratieveHandeling(bericht.getAdministratieveHandeling(), true);
    }

    private void assertAdministratieveHandeling(final AdministratieveHandelingElement administratieveHandeling, final boolean eindeGP) {
        Assert.assertNotNull(administratieveHandeling);
        if (eindeGP) {
            assertEqualStringElement("051801", administratieveHandeling.getPartijCode());
            assertActies(administratieveHandeling.getActies(), true);
        } else {
            assertEqualStringElement("123456", administratieveHandeling.getPartijCode());
            assertEqualStringElement("Test toelichting op de ontlening", administratieveHandeling.getToelichtingOntlening());
            assertActies(administratieveHandeling.getActies(), false);
        }

    }

    private void assertActies(final List<ActieElement> acties, final boolean eindeGP) {
        Assert.assertEquals(1, acties.size());
        if (eindeGP) {
            Assert.assertTrue(acties.get(0) instanceof AbstractRegistratieEindeHuwelijkOfGpActieElement);
            final AbstractRegistratieEindeHuwelijkOfGpActieElement actie = (AbstractRegistratieEindeHuwelijkOfGpActieElement) acties.get(0);
            assertHuwelijkOfGp(actie.getHuwelijkOfGp(), true);
        } else {
            Assert.assertTrue(acties.get(0) instanceof AbstractRegistratieAanvangHuwelijkOfGpActieElement);
            final AbstractRegistratieAanvangHuwelijkOfGpActieElement actie = (AbstractRegistratieAanvangHuwelijkOfGpActieElement) acties.get(0);
            assertHuwelijkOfGp(actie.getHuwelijkOfGp(), false);
        }
    }

    private void assertHuwelijkOfGp(final HuwelijkOfGpElement huwelijk, final boolean eindeGP) {
        Assert.assertNotNull(huwelijk);
        assertRelatie(huwelijk.getRelatieGroep(), eindeGP);
    }

    private void assertRelatie(final RelatieGroepElement relatie, final boolean eindeGP) {
        Assert.assertNotNull(relatie);
        if (eindeGP) {
            assertEqualDatumElement("2016-01-01", relatie.getDatumEinde());
            assertEqualStringElement("0518", relatie.getGemeenteEindeCode());
        } else {
            assertEqualDatumElement("2016-03-22", relatie.getDatumAanvang());
            assertEqualStringElement("1234", relatie.getGemeenteAanvangCode());
        }
        assertEqualStringElement(null, relatie.getWoonplaatsnaamAanvang());
    }
}
