/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.bericht.parser;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import nl.bzk.brp.bijhouding.bericht.model.AdministratieveHandelingPlanElement;
import nl.bzk.brp.bijhouding.bericht.model.NotificatieBijhoudingsplanElement;
import nl.bzk.brp.bijhouding.bericht.model.VerwerkBijhoudingsplanBericht;
import org.junit.Test;

/**
 * Testen voor {@link VerwerkBijhoudingsplanBerichtParser}.
 */
public class VerwerkBijhoudingsplanBerichtParserTest extends AbstractParserTest {

    public static final String BERICHT = "verwerkBijhoudingsplanBericht.xml";

    @Test
    public void testParsenNotificatie() throws ParseException {
        final VerwerkBijhoudingsplanBerichtParser parser = new VerwerkBijhoudingsplanBerichtParser();
        final VerwerkBijhoudingsplanBericht bericht = parser.parse(this.getClass().getResourceAsStream(BERICHT));
        assertNotNull(bericht);
        final AdministratieveHandelingPlanElement administratieveHandelingPlan = bericht.getAdministratieveHandelingPlan();
        assertNotNull(administratieveHandelingPlan);
        assertEqualStringElement("053001", administratieveHandelingPlan.getPartijCode());
        assertEqualStringElement("Actualisering", administratieveHandelingPlan.getCategorieNaam());
        assertEqualStringElement("Voltrekking huwelijk in Nederland", administratieveHandelingPlan.getSoortNaam() );
        final NotificatieBijhoudingsplanElement bijhoudingsplan = administratieveHandelingPlan.getBijhoudingsplan();
        assertNotNull(bijhoudingsplan);
        assertFalse(bijhoudingsplan.getBijhoudingsplanPersonen().isEmpty());
        assertNotNull(bijhoudingsplan.getBijhoudingsvoorstelVerzoekBericht());
        assertNotNull(bijhoudingsplan.getBijhoudingsvoorstelResultaatBericht());
    }
}
