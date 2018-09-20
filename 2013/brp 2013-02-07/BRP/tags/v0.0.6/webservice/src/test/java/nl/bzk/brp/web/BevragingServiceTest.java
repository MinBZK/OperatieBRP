/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.web;

import nl.bzk.brp.binding.BerichtResultaat;
import nl.bzk.brp.binding.ResultaatCode;
import nl.bzk.brp.binding.bevraging.OpvragenPersoonBericht;
import nl.bzk.brp.binding.bevraging.OpvragenPersoonResultaat;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class BevragingServiceTest extends AbstractWebServiceTest<OpvragenPersoonBericht> {

    @Test
    public void testBevraging() {
        BerichtResultaat resultaat = ((BevragingService) getWebService()).opvragenPersoon(getBericht());
        Assert.assertTrue(resultaat instanceof OpvragenPersoonResultaat);
        Assert.assertEquals(ResultaatCode.GOED, resultaat.getResultaat());
        Assert.assertEquals(0, resultaat.getMeldingen().size());
    }


    @Override
    protected AbstractWebService<OpvragenPersoonBericht> getNieuweWebServiceVoorTest() {
        final BevragingServiceImpl ws =  new BevragingServiceImpl();
        ReflectionTestUtils.setField(ws, "bevragingsBerichtVerwerker", getBerichtVerwerker());
        return ws;
    }

    @Override
    protected Class<OpvragenPersoonBericht> getNieuwBerichtClassVoorTest() {
        return OpvragenPersoonBericht.class;
    }
}
