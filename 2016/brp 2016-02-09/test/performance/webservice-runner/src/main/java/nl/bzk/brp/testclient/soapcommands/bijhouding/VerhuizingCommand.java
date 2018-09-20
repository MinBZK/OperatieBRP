/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.soapcommands.bijhouding;

import nl.bzk.brp.bijhouding.service.BhgVerblijfAdres;
import nl.bzk.brp.brp0200.MigratieRegistreerVerhuizingBijhouding;
import nl.bzk.brp.brp0200.MigratieRegistreerVerhuizingBijhoudingResultaat;
import nl.bzk.brp.brp0200.ObjecttypeAdministratieveHandeling;
import nl.bzk.brp.testclient.soapcommands.AbstractSoapCommand;


/**
 * Het 'command' dat uitgevoerd dient te worden voor het versturen van een verhuizing SOAP bericht.
 */
public class VerhuizingCommand extends AbstractSoapCommand<BhgVerblijfAdres, MigratieRegistreerVerhuizingBijhouding,
        MigratieRegistreerVerhuizingBijhoudingResultaat>
{

    /**
     * Instantieert een nieuwe migratie verhuizing bijhouding command.
     *
     * @param bijhouding de bijhouding
     */
    public VerhuizingCommand(final MigratieRegistreerVerhuizingBijhouding bijhouding) {
        super(bijhouding);
    }

    /* (non-Javadoc)
     * @see nl.bzk.brp.testclient.soapcommands.AbstractSoapCommand#bepaalEnSetAntwoord(java.lang.Object)
     */
    @Override
    public MigratieRegistreerVerhuizingBijhoudingResultaat bepaalEnSetAntwoord(final BhgVerblijfAdres portType) {
        return portType.registreerVerhuizing(getVraag());
    }

    @Override
    protected ObjecttypeAdministratieveHandeling getAdministratieveHandelingUitAntwoord(
            final MigratieRegistreerVerhuizingBijhoudingResultaat antwoord)
    {
        if (null != antwoord.getVerhuizingBinnengemeentelijk()) {
           return  antwoord.getVerhuizingBinnengemeentelijk().getValue();
        } else if (null != antwoord.getVerhuizingIntergemeentelijk()) {
            return  antwoord.getVerhuizingIntergemeentelijk().getValue();
        } else if (null != antwoord.getVerhuizingNaarBuitenland()) {
            return  antwoord.getVerhuizingNaarBuitenland().getValue();
        } else {
            return null;
        }
    }
}
