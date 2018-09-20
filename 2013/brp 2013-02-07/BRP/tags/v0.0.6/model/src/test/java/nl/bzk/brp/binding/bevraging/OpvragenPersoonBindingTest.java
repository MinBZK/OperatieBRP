/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.bevraging;

import java.util.Calendar;

import nl.bzk.brp.binding.AbstractBindingTest;
import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Test;

public class OpvragenPersoonBindingTest extends AbstractBindingTest<OpvragenPersoonBericht> {

    @Override
    protected Class<OpvragenPersoonBericht> getBindingClass() {
        return OpvragenPersoonBericht.class;
    }

    @Test
    public void testOpvragenPersoonBericht() throws JiBXException {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?> "
                + "<bev:OpvragenPersoon xmlns:bev=\"http://www.brp.bzk.nl/bevraging\">\n"
                + "       <bev:afzenderId>1</bev:afzenderId>\n"
                + "       <bev:tijdstipVerzonden>2008-09-29T03:49:45</bev:tijdstipVerzonden>\n"
                + "       <bev:opvragenPersoonCriteria>\n"
                + "          <bev:bsn>111222333</bev:bsn>\n"
                + "       </bev:opvragenPersoonCriteria>\n"
                + "</bev:OpvragenPersoon>";
        OpvragenPersoonBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);
        Assert.assertEquals(1, bericht.getAfzender().getId().intValue());
        Assert.assertEquals(29, bericht.getTijdstipVerzonden().get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(9 - 1, bericht.getTijdstipVerzonden().get(Calendar.MONTH));
        Assert.assertEquals(2008, bericht.getTijdstipVerzonden().get(Calendar.YEAR));
        //UTC tijd
        Assert.assertEquals(3 + 2, bericht.getTijdstipVerzonden().get(Calendar.HOUR));
        Assert.assertEquals(49, bericht.getTijdstipVerzonden().get(Calendar.MINUTE));
        Assert.assertEquals(45, bericht.getTijdstipVerzonden().get(Calendar.SECOND));
        OpvragenPersoonCriteria crit = bericht.getOpvragenPersoonCriteria();
        Assert.assertNotNull(crit);
        Assert.assertEquals("111222333", crit.getIdentificatienummers().getBurgerservicenummer());
        Assert.assertNull(crit.getIdentificatienummers().getAdministratienummer());
    }
}
