/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.synchronisatie;

import java.io.IOException;

import nl.bzk.brp.binding.AbstractBindingInIntegratieTest;
import nl.bzk.brp.model.bericht.ber.BerichtZoekcriteriaPersoonGroepBericht;
import nl.bzk.brp.model.synchronisatie.GeefSynchronisatiePersoonBericht;

import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Test;
import nl.bzk.brp.logging.Logger;
import nl.bzk.brp.logging.LoggerFactory;


public class GeefSynchronisatiePersoonBerichtBindingTest extends
        AbstractBindingInIntegratieTest<GeefSynchronisatiePersoonBericht>
{

    @Override
    protected Class<GeefSynchronisatiePersoonBericht> getBindingClass() {
        return GeefSynchronisatiePersoonBericht.class;
    }

    @Override
    protected String getSchemaBestand() {
        return "brp0200_lvgSynchronisatie_Berichten.xsd";
    }

    @Test
    public void testBinding() throws IOException, JiBXException {
        String xml = leesBestand("geefSynchronisatiePersoon.xml");
        valideerTegenSchema(xml);

        Logger logger = LoggerFactory.getLogger();

        logger.info("{}", xml);

        GeefSynchronisatiePersoonBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);
        final BerichtZoekcriteriaPersoonGroepBericht zoekcriteriaPersoon = bericht.getZoekcriteriaPersoon();
        Assert.assertNotNull(zoekcriteriaPersoon);

        Assert.assertEquals(123456789, zoekcriteriaPersoon.getBurgerservicenummer().getWaarde().intValue());
    }
}
