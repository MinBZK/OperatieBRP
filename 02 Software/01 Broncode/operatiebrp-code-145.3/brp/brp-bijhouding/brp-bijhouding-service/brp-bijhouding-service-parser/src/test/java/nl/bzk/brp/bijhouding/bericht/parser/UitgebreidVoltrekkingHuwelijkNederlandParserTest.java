/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.parser;

import java.util.List;
import nl.bzk.brp.bijhouding.bericht.model.ActieElement;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingBerichtSoort;
import nl.bzk.brp.bijhouding.bericht.model.BijhoudingVerzoekBericht;
import nl.bzk.brp.bijhouding.bericht.model.GeslachtsnaamcomponentElement;
import nl.bzk.brp.bijhouding.bericht.model.NaamgebruikElement;
import nl.bzk.brp.bijhouding.bericht.model.RegistratieAanvangHuwelijkActieElement;
import nl.bzk.brp.bijhouding.bericht.model.RegistratieGeslachtsnaamActieElement;
import nl.bzk.brp.bijhouding.bericht.model.RegistratieNaamgebruikActieElement;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests voor BijhoudingVerzoekBerichtParser.
 */
public class UitgebreidVoltrekkingHuwelijkNederlandParserTest extends AbstractParserTest {

    private static final String UITGEBREID_BIJHOUDING_BERICHT = "uitgebreidVoltrekkingHuwelijkNederlandBericht.xml";

    @Test
    public void testValideerXmlBericht() {
        valideerTegenSchema(this.getClass().getResourceAsStream(UITGEBREID_BIJHOUDING_BERICHT));
    }

    @Test
    public void testParsenUitgebreidBericht() throws ParseException {
        final BijhoudingVerzoekBerichtParser parser = new BijhoudingVerzoekBerichtParser();
        final BijhoudingVerzoekBericht bericht = parser.parse(this.getClass().getResourceAsStream(UITGEBREID_BIJHOUDING_BERICHT));
        Assert.assertNotNull(bericht);
        Assert.assertEquals(BijhoudingBerichtSoort.REGISTREER_HUWELIJK_GP, bericht.getSoort());
        Assert.assertNotNull(bericht.getAdministratieveHandeling());
        assertActies(bericht.getAdministratieveHandeling().getActies());
    }

    private void assertActies(final List<ActieElement> acties) {
        Assert.assertNotNull(acties);
        Assert.assertEquals(3, acties.size());
        RegistratieAanvangHuwelijkActieElement actieRegistratieAanvang = null;
        RegistratieGeslachtsnaamActieElement actieRegistratieGeslachtsnaam = null;
        RegistratieNaamgebruikActieElement actieRegistratieNaamgebruik = null;
        for (final ActieElement actie : acties) {
            if (actie instanceof RegistratieAanvangHuwelijkActieElement) {
                actieRegistratieAanvang = (RegistratieAanvangHuwelijkActieElement) actie;
            } else if (actie instanceof RegistratieGeslachtsnaamActieElement) {
                actieRegistratieGeslachtsnaam = (RegistratieGeslachtsnaamActieElement) actie;
            } else if (actie instanceof RegistratieNaamgebruikActieElement) {
                actieRegistratieNaamgebruik = (RegistratieNaamgebruikActieElement) actie;
            }
        }
        Assert.assertNotNull(actieRegistratieAanvang); // deze is al getest in de eenvoudige bericht test
        assertRegistratieGeslachtsnaam(actieRegistratieGeslachtsnaam);
        assertRegistratieNaamgebruik(actieRegistratieNaamgebruik);
    }

    private void assertRegistratieNaamgebruik(final RegistratieNaamgebruikActieElement actieRegistratieNaamgebruik) {
        Assert.assertNotNull(actieRegistratieNaamgebruik);
        Assert.assertNotNull(actieRegistratieNaamgebruik.getNaamgebruik());
        Assert.assertNotNull(actieRegistratieNaamgebruik.getBronReferenties());
        Assert.assertEquals(1, actieRegistratieNaamgebruik.getBronReferenties().size());
        final NaamgebruikElement naamgebruik = actieRegistratieNaamgebruik.getNaamgebruik();
        assertEqualBooleanElement("N", naamgebruik.getIndicatieAfgeleid());
        assertEqualStringElement("H", naamgebruik.getPredicaatCode());
        assertEqualStringElement("Cornelis Jan", naamgebruik.getVoornamen());
        assertEqualStringElement("voor ", naamgebruik.getVoorvoegsel());
        assertEqualCharacterElement('-', naamgebruik.getScheidingsteken());
        assertEqualStringElement("Jansen", naamgebruik.getGeslachtsnaamstam());
    }

    private void assertRegistratieGeslachtsnaam(final RegistratieGeslachtsnaamActieElement actieRegistratieGeslachtsnaam) {
        Assert.assertNotNull(actieRegistratieGeslachtsnaam);
        Assert.assertNotNull(actieRegistratieGeslachtsnaam.getGeslachtsnaamcomponent());
        Assert.assertNotNull(actieRegistratieGeslachtsnaam.getBronReferenties());
        Assert.assertEquals(1, actieRegistratieGeslachtsnaam.getBronReferenties().size());
        assertEqualDatumElement("2016-03-23", actieRegistratieGeslachtsnaam.getDatumAanvangGeldigheid());
        final GeslachtsnaamcomponentElement geslachtsnaamcomponent = actieRegistratieGeslachtsnaam.getGeslachtsnaamcomponent();
        assertEqualStringElement("B", geslachtsnaamcomponent.getAdellijkeTitelCode());
        assertEqualStringElement("va ", geslachtsnaamcomponent.getVoorvoegsel());
        assertEqualCharacterElement(' ', geslachtsnaamcomponent.getScheidingsteken());
        assertEqualStringElement("Bakkersma", geslachtsnaamcomponent.getStam());
    }
}
