/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.soapcommands.bijhouding;

import nl.bzk.brp.bijhouding.service.BhgAfstamming;
import nl.bzk.brp.brp0200.AfstammingRegistreerAdoptieBijhouding;
import nl.bzk.brp.brp0200.AfstammingRegistreerAdoptieBijhoudingResultaat;
import nl.bzk.brp.brp0200.ObjecttypeAdministratieveHandeling;
import nl.bzk.brp.testclient.soapcommands.AbstractSoapCommand;

/**
 * Het 'command' dat uitgevoerd dient te worden voor het versturen van een adoptie SOAP bericht.
 */
public class AdoptieCommand extends
        AbstractSoapCommand<BhgAfstamming, AfstammingRegistreerAdoptieBijhouding,
                AfstammingRegistreerAdoptieBijhoudingResultaat>
{

    /**
     * Instantieert een nieuwe adoptie soap command.
     *
     * @param vraag de vraag
     */
    public AdoptieCommand(final AfstammingRegistreerAdoptieBijhouding vraag) {
        super(vraag);
    }

    @Override
    public AfstammingRegistreerAdoptieBijhoudingResultaat bepaalEnSetAntwoord(final BhgAfstamming portType) {
        return portType.registreerAdoptie(getVraag());
    }

    @Override
    protected ObjecttypeAdministratieveHandeling getAdministratieveHandelingUitAntwoord(
            final AfstammingRegistreerAdoptieBijhoudingResultaat antwoord)
    {
        if (null != antwoord.getAdoptieIngezetene()) {
            return  antwoord.getAdoptieIngezetene().getValue();
        } else {
            return null;
        }
    }
}
