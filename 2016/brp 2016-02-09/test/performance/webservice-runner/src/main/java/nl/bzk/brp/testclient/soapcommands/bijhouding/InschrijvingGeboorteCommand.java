/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.soapcommands.bijhouding;

import nl.bzk.brp.bijhouding.service.BhgAfstamming;
import nl.bzk.brp.brp0200.AfstammingRegistreerGeboorteBijhouding;
import nl.bzk.brp.brp0200.AfstammingRegistreerGeboorteBijhoudingResultaat;
import nl.bzk.brp.brp0200.ObjecttypeAdministratieveHandeling;
import nl.bzk.brp.testclient.soapcommands.AbstractSoapCommand;


/**
 * Het 'command' dat uitgevoerd dient te worden voor het versturen van een inschrijving door geboorte SOAP bericht.
 */
public class InschrijvingGeboorteCommand extends
    AbstractSoapCommand<BhgAfstamming, AfstammingRegistreerGeboorteBijhouding,
            AfstammingRegistreerGeboorteBijhoudingResultaat>
{

    /**
     * Instantieert een nieuwe inschrijving geboorte command.
     *
     * @param vraag de vraag
     */
    public InschrijvingGeboorteCommand(final AfstammingRegistreerGeboorteBijhouding vraag) {
        super(vraag);
    }

    /* (non-Javadoc)
     * @see nl.bzk.brp.testclient.soapcommands.AbstractSoapCommand#bepaalEnSetAntwoord(java.lang.Object)
     */
    @Override
    public AfstammingRegistreerGeboorteBijhoudingResultaat bepaalEnSetAntwoord(final BhgAfstamming portType) {
        return portType.registreerGeboorte(getVraag());
    }

    @Override
    protected ObjecttypeAdministratieveHandeling getAdministratieveHandelingUitAntwoord(
            final AfstammingRegistreerGeboorteBijhoudingResultaat antwoord)
    {
        if (null != antwoord.getGeboorteInNederland()) {
            return  antwoord.getGeboorteInNederland().getValue();
        } else if (null != antwoord.getGeboorteInNederlandMetErkenning()) {
            return  antwoord.getGeboorteInNederlandMetErkenning().getValue();
        } else {
            return null;
        }
    }
}
