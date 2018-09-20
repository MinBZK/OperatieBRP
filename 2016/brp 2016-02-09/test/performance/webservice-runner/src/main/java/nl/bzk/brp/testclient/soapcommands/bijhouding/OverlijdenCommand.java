/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.soapcommands.bijhouding;

import nl.bzk.brp.bijhouding.service.BhgOverlijden;
import nl.bzk.brp.brp0200.ObjecttypeAdministratieveHandeling;
import nl.bzk.brp.brp0200.OverlijdenRegistreerOverlijdenBijhouding;
import nl.bzk.brp.brp0200.OverlijdenRegistreerOverlijdenBijhoudingResultaat;
import nl.bzk.brp.testclient.soapcommands.AbstractSoapCommand;

/**
 * Het 'command' dat uitgevoerd dient te worden voor het versturen van een overlijden SOAP bericht.
 */
public class OverlijdenCommand extends
        AbstractSoapCommand<BhgOverlijden, OverlijdenRegistreerOverlijdenBijhouding,
                OverlijdenRegistreerOverlijdenBijhoudingResultaat>
{

    /**
     * Standaard constructor die het bijhoudings bericht zet.
     *
     * @param bijhouding het bijhoudingsbericht.
     */
    public OverlijdenCommand(final OverlijdenRegistreerOverlijdenBijhouding bijhouding) {
        super(bijhouding);
    }

    /* (non-Javadoc)
     * @see nl.bzk.brp.testclient.soapcommands.AbstractSoapCommand#bepaalEnSetAntwoord(java.lang.Object)
     */
    @Override
    public OverlijdenRegistreerOverlijdenBijhoudingResultaat bepaalEnSetAntwoord(final BhgOverlijden portType) {
        return portType.registreerOverlijden(getVraag());
    }

    @Override
    protected ObjecttypeAdministratieveHandeling getAdministratieveHandelingUitAntwoord(
            final OverlijdenRegistreerOverlijdenBijhoudingResultaat antwoord)
    {
        if (null != antwoord.getOverlijdenInNederland()) {
           return  antwoord.getOverlijdenInNederland().getValue();
        } else if (null != antwoord.getOverlijdenInBuitenland()) {
               return  antwoord.getOverlijdenInBuitenland().getValue();
        } else {
            return null;
        }
    }
}
