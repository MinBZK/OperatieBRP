/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web;

import nl.bzk.brp.binding.BerichtResultaat;
import nl.bzk.brp.binding.ResultaatCode;
import nl.bzk.brp.binding.bijhouding.BijhoudingsBericht;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/** Unit test voor de {@link BijhoudingService} class. */
public class BijhoudingServiceTest extends AbstractWebServiceTest<BijhoudingsBericht> {

    @Test
    public void testBijhouden() {
        BerichtResultaat resultaat = ((BijhoudingService) getWebService()).bijhouden(getBericht());
        Assert.assertEquals(ResultaatCode.GOED, resultaat.getResultaat());
        Assert.assertEquals(0, resultaat.getMeldingen().size());
    }


    @Override
    protected AbstractWebService<BijhoudingsBericht> getNieuweWebServiceVoorTest() {
        BijhoudingServiceImpl bijhoudingService = new BijhoudingServiceImpl();
        ReflectionTestUtils.setField(bijhoudingService, "bijhoudingsBerichtVerwerker", getBerichtVerwerker());
        return bijhoudingService;
    }

    @Override
    protected Class<BijhoudingsBericht> getNieuwBerichtClassVoorTest() {
        return BijhoudingsBericht.class;
    }
}
