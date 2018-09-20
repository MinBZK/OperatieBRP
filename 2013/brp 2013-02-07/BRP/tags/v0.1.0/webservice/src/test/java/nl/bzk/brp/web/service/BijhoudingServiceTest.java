/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import java.util.ArrayList;

import nl.bzk.brp.business.dto.bijhouding.BijhoudingResultaat;
import nl.bzk.brp.business.dto.bijhouding.BijhoudingsBericht;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.web.bijhouding.BijhoudingAntwoordBericht;
import nl.bzk.brp.web.bijhouding.VerwerkingsResultaat;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/** Unit test voor de {@link nl.bzk.brp.web.service.BijhoudingService} class. */
public class BijhoudingServiceTest extends AbstractWebServiceTest<BijhoudingsBericht> {

    @Test
    public void testBijhouden() {
        initBerichtVerwerker(new ArrayList<Melding>(), BijhoudingResultaat.class, true);
        BijhoudingAntwoordBericht resultaat = ((BijhoudingService) getWebService()).verhuizing(getBericht());

        Assert.assertEquals(VerwerkingsResultaat.GOED, resultaat.getVerwerkingsResultaat());
        Assert.assertNull(resultaat.getMeldingen());
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
