/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.bevraging.bijhouding;

import nl.bzk.brp.binding.AbstractBindingInIntegratieTest;
import nl.bzk.brp.model.bijhouding.NotificeerBijhoudingsplanBericht;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.BeforeClass;
import org.junit.Test;


public class NotificatieBerichtBindingTest extends AbstractBindingInIntegratieTest<NotificeerBijhoudingsplanBericht>
{

    @Override
    public Class<NotificeerBijhoudingsplanBericht> getBindingClass() {
        return NotificeerBijhoudingsplanBericht.class;
    }

    @BeforeClass
    public static void beforeClass() {
        XMLUnit.setIgnoreWhitespace(true);
    }

    @Test
    public void testOutBindingMetNotificatie() throws Exception {
        final String xml = leesBestand("notificatieBijhoudingsplan_MIN.xml");
        valideerTegenSchema(xml);
    }

    @Override
    protected String getSchemaBestand() {
        return getSchemaUtils().getXsdNotificatieBerichten();
    }

}
