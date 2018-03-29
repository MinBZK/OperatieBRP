/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import nl.bzk.brp.bijhouding.bericht.model.ActieElement;
import nl.bzk.brp.bijhouding.bericht.model.AdministratieveHandelingElement;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingBerichtSoort;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.model.GeregistreerdPartnerschapElement;
import nl.bzk.brp.bijhouding.bericht.model.RegistratieAanvangGeregistreerdPartnerschapActieElement;
import nl.bzk.brp.bijhouding.bericht.model.RelatieGroepElement;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests voor BijhoudingVerzoekBerichtParser.
 */
public class EenvoudigAangaanGeregistreerdPartnerschapNederlandParserTest extends AbstractParserTest {

    public static final String EENVOUDIG_BIJHOUDING_BERICHT = "eenvoudigAangaanGeregistreerdPartnerschapNederlandBericht.xml";
    public static final String ANDERE_NAMESPACE_PREFIX_BERICHT = "andereNamespacePrefixBericht.xml";

    @Test
    public void testValideerXmlBericht() {
        valideerTegenSchema(this.getClass().getResourceAsStream(EENVOUDIG_BIJHOUDING_BERICHT));
    }

    @Test
    public void testParsenEenvoudigBericht() throws ParseException {
        final BijhoudingVerzoekBerichtParser parser = new BijhoudingVerzoekBerichtParser();
        final BijhoudingVerzoekBericht bericht = parser.parse(this.getClass().getResourceAsStream(EENVOUDIG_BIJHOUDING_BERICHT));
        assertNotNull(bericht);
        assertEquals(BijhoudingBerichtSoort.REGISTREER_HUWELIJK_GP, bericht.getSoort());
        assertAdministratieveHandeling(bericht.getAdministratieveHandeling());
        assertEquals(bericht, bericht.getAdministratieveHandeling().getVerzoekBericht());
        assertEquals(bericht, bericht.getStuurgegevens().getVerzoekBericht());
        assertEquals(bericht, bericht.getParameters().getVerzoekBericht());
    }

    @Test
    public void testParsenAndereNamespaceBericht() throws ParseException {
        final BijhoudingVerzoekBerichtParser parser = new BijhoudingVerzoekBerichtParser();
        final BijhoudingVerzoekBericht bericht = parser.parse(this.getClass().getResourceAsStream(ANDERE_NAMESPACE_PREFIX_BERICHT));
        assertNotNull(bericht);
    }

    private void assertAdministratieveHandeling(final AdministratieveHandelingElement administratieveHandeling) {
        assertNotNull(administratieveHandeling);
        assertEqualStringElement("123456", administratieveHandeling.getPartijCode());
        assertEqualStringElement("Test toelichting op de ontlening", administratieveHandeling.getToelichtingOntlening());
        assertActies(administratieveHandeling.getActies());
    }

    private void assertActies(final List<ActieElement> acties) {
        assertEquals(1, acties.size());
        Assert.assertTrue(acties.get(0) instanceof RegistratieAanvangGeregistreerdPartnerschapActieElement);
        final RegistratieAanvangGeregistreerdPartnerschapActieElement actie = (RegistratieAanvangGeregistreerdPartnerschapActieElement) acties.get(0);
        assertGeregistreerdPartnerschap(actie.getGeregistreerdPartnerschap());
    }

    private void assertGeregistreerdPartnerschap(final GeregistreerdPartnerschapElement geregistreerdPartnerschap) {
        assertNotNull(geregistreerdPartnerschap);
        assertRelatie(geregistreerdPartnerschap.getRelatieGroep());
    }

    private void assertRelatie(final RelatieGroepElement relatie) {
        assertNotNull(relatie);
        assertEqualDatumElement("2016-03-22", relatie.getDatumAanvang());
        assertEqualStringElement("1234", relatie.getGemeenteAanvangCode());
        assertEqualStringElement(null, relatie.getWoonplaatsnaamAanvang());
    }
}
