/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.algemeen;

import org.junit.Assert;
import org.junit.Test;


public class LeveringConstantenTest {

    @Test
    public final void testConstanten() {
        Assert.assertEquals("gegevens", LeveringConstanten.JMS_PROPERTY_KEY_BERICHT_GEGEVENS);
        Assert.assertEquals("afnemerXmlBericht", LeveringConstanten.JMS_PROPERTY_KEY_XML_BERICHT);
    }
}
