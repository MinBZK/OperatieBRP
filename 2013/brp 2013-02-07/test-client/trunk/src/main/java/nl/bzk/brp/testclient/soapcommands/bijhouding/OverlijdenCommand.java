/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.soapcommands.bijhouding;

import nl.bprbzk.brp._0001.OverlijdenRegistratieOverlijdenBijhouding;
import nl.bprbzk.brp._0001.OverlijdenRegistratieOverlijdenBijhoudingResponse;
import nl.bprbzk.brp._0001.service.BijhoudingPortType;
import nl.bzk.brp.testclient.soapcommands.AbstractSoapCommand;

public class OverlijdenCommand extends
        AbstractSoapCommand<BijhoudingPortType, OverlijdenRegistratieOverlijdenBijhouding, OverlijdenRegistratieOverlijdenBijhoudingResponse>
{

    public OverlijdenCommand(final OverlijdenRegistratieOverlijdenBijhouding bijhouding) {
        super(bijhouding);
    }

    /* (non-Javadoc)
     * @see nl.bzk.brp.testclient.soapcommands.AbstractSoapCommand#bepaalEnSetAntwoord(java.lang.Object)
     */
    @Override
    public void bepaalEnSetAntwoord(final BijhoudingPortType portType) {
        setAntwoord(portType.registreerOverlijden(getVraag()));
    }
}
