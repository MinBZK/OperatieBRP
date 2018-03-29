/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.funqmachine.verstuurder;

import nl.bzk.brp.funqmachine.configuratie.Omgeving;
import nl.bzk.brp.funqmachine.verstuurder.soap.SoapParameters;
import org.junit.Test;

public class SoapSenderTest {

    @Test()
    public void geeftResponseTerug() {
        SoapParameters parameters = Omgeving.getOmgeving().
                getSoapParameters("http://localhost/bijhouding/BijhoudingService/bhgHuwelijkGeregistreerdPartnerschap", "");
        SoapVerstuurder processor = new SoapVerstuurder();
    }
}
