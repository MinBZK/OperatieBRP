/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web.service;

import java.util.ArrayList;

import nl.bzk.brp.business.dto.bevraging.OpvragenPersoonBericht;
import nl.bzk.brp.business.dto.bevraging.OpvragenPersoonResultaat;
import nl.bzk.brp.model.validatie.Melding;
import nl.bzk.brp.web.bevraging.BevragingAntwoordBericht;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/** Unit test klasse voor de {@link BevragingService}. */
public class BevragingServiceTest extends AbstractWebServiceTest<OpvragenPersoonBericht> {

    @Test
    public void testBevraging() {
        initBerichtVerwerker(new ArrayList<Melding>(), OpvragenPersoonResultaat.class, true);

        BevragingAntwoordBericht resultaat = ((BevragingService) getWebService()).opvragenPersoon(getBericht());
        Assert.assertNull(resultaat.getMeldingen());
        Assert.assertEquals(0, resultaat.getAantal());
        Assert.assertTrue(resultaat.getGevondenPersonen().isEmpty());
    }

    @Override
    protected AbstractWebService<OpvragenPersoonBericht> getNieuweWebServiceVoorTest() {
        final BevragingServiceImpl ws = new BevragingServiceImpl();
        ReflectionTestUtils.setField(ws, "bevragingsBerichtVerwerker", getBerichtVerwerker());
        return ws;
    }

    @Override
    protected Class<OpvragenPersoonBericht> getNieuwBerichtClassVoorTest() {
        return OpvragenPersoonBericht.class;
    }
}
