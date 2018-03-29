/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.verstuurder.soap;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import nl.bzk.brp.funqmachine.configuratie.Omgeving;
import org.junit.Test;

public class SoapParametersTest {
    @Test
    public void correcteParams() throws MalformedURLException {
        String url = "http://localhost/bijhouding/BijhoudingService/bhgHuwelijkGeregistreerdPartnerschap";
        String ns = "http://namespace";

        SoapParameters parameters = Omgeving.getOmgeving().getSoapParameters(url, ns);

        assertEquals(ns, parameters.getNamespace());
        assertEquals("{http://namespace}BijhoudingService", parameters.getServiceQName().toString());
        assertEquals("{http://namespace}bhgHuwelijkGeregistreerdPartnerschap", parameters.getPortQName().toString());
        assertEquals("http://localhost:8080/bijhouding/BijhoudingService/bhgHuwelijkGeregistreerdPartnerschap?wsdl", parameters.getWsdlURL().toString());
    }

}
