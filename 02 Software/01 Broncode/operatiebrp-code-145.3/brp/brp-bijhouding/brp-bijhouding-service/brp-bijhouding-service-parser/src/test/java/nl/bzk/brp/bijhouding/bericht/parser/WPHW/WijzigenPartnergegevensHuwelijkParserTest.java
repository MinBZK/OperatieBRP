/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.parser.WPHW;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.SoortActie;
import nl.bzk.brp.bijhouding.bericht.model.AdministratieveHandelingElementSoort;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.parser.AbstractParserTest;
import nl.bzk.brp.bijhouding.bericht.parser.BijhoudingVerzoekBerichtParser;
import nl.bzk.brp.bijhouding.bericht.parser.ParseException;
import org.junit.Test;

/**
 * Unit test voor AH Wijzigen partnergegevens Huwelijk.
 */
public class WijzigenPartnergegevensHuwelijkParserTest extends AbstractParserTest{

    private static final String WIJZIGEN_PARTNER_GEGEVENS_HUWELIJK_XML = "wijzigenPartnerGegevensHuwelijk.xml";

    @Test
    public void testValideerXmlBericht() {
        valideerTegenSchema(this.getClass().getResourceAsStream(WIJZIGEN_PARTNER_GEGEVENS_HUWELIJK_XML));
    }

    @Test
    public void testParsenBericht() throws ParseException {
        final BijhoudingVerzoekBerichtParser parser = new BijhoudingVerzoekBerichtParser();
        final BijhoudingVerzoekBericht bericht = parser.parse(this.getClass().getResourceAsStream(WIJZIGEN_PARTNER_GEGEVENS_HUWELIJK_XML));
        assertNotNull(bericht);
        assertNotNull(bericht.getDatumOntvangst());
        assertEquals(bericht.getAdministratieveHandeling().getSoort(), AdministratieveHandelingElementSoort.WIJZIGING_PARTNERGEGEVENS_HUWELIJK);
        assertEquals(4, bericht.getAdministratieveHandeling().getActies().size());
        assertNotNull(bericht.getAdministratieveHandeling().getActieBySoort(SoortActie.REGISTRATIE_IDENTIFICATIENUMMERS_GERELATEERDE));
        assertNotNull(bericht.getAdministratieveHandeling().getActieBySoort(SoortActie.REGISTRATIE_SAMENGESTELDE_NAAM_GERELATEERDE));
        assertNotNull(bericht.getAdministratieveHandeling().getActieBySoort(SoortActie.REGISTRATIE_GESLACHTSAANDUIDING_GERELATEERDE));
        assertNotNull(bericht.getAdministratieveHandeling().getActieBySoort(SoortActie.REGISTRATIE_GEBOORTE_GERELATEERDE));
    }
}
