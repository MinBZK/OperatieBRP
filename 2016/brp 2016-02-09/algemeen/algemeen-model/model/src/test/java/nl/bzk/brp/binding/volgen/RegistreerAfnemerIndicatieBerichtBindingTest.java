/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding.volgen;

import java.io.IOException;

import nl.bzk.brp.binding.AbstractBindingInIntegratieTest;
import nl.bzk.brp.model.synchronisatie.RegistreerAfnemerindicatieBericht;

import org.jibx.runtime.JiBXException;
import org.junit.Assert;
import org.junit.Test;


public class RegistreerAfnemerIndicatieBerichtBindingTest extends
        AbstractBindingInIntegratieTest<RegistreerAfnemerindicatieBericht>
{

    @Test
    public void testBinding() throws IOException, JiBXException {
        String xml = leesBestand("registreerAfnemerindicatie.xml");
        valideerTegenSchema(xml);

        RegistreerAfnemerindicatieBericht bericht = unmarshalObject(xml);
        Assert.assertNotNull(bericht);
    }

    @Override
    protected Class<RegistreerAfnemerindicatieBericht> getBindingClass() {
        return RegistreerAfnemerindicatieBericht.class;
    }

    @Override
    protected String getSchemaBestand() {
        return "brp0200_lvgAfnemerindicatie_Berichten.xsd";
    }
}
