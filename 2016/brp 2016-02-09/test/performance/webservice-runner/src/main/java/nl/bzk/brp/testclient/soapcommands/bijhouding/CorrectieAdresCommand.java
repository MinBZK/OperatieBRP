/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.soapcommands.bijhouding;

import nl.bzk.brp.bijhouding.service.BhgVerblijfAdres;
import nl.bzk.brp.brp0200.MigratieCorrigeerAdresBijhouding;
import nl.bzk.brp.brp0200.MigratieCorrigeerAdresBijhoudingResultaat;
import nl.bzk.brp.brp0200.ObjecttypeAdministratieveHandeling;
import nl.bzk.brp.testclient.soapcommands.AbstractSoapCommand;


/**
 * Het 'command' dat uitgevoerd dient te worden voor het versturen van een correctie adres SOAP bericht.
 */
public class CorrectieAdresCommand extends
        AbstractSoapCommand<BhgVerblijfAdres, MigratieCorrigeerAdresBijhouding,
                MigratieCorrigeerAdresBijhoudingResultaat>
{

    /**
     * Instantieert een nieuwe correctie adres bijhouding command.
     *
     * @param bijhouding de bijhouding
     */
    public CorrectieAdresCommand(final MigratieCorrigeerAdresBijhouding bijhouding) {
        super(bijhouding);
    }

    /* (non-Javadoc)
     * @see nl.bzk.brp.testclient.soapcommands.AbstractSoapCommand#bepaalEnSetAntwoord(java.lang.Object)
     */
    @Override
    public MigratieCorrigeerAdresBijhoudingResultaat bepaalEnSetAntwoord(final BhgVerblijfAdres portType) {
        return portType.corrigeerAdres(getVraag());
    }

    @Override
    protected ObjecttypeAdministratieveHandeling getAdministratieveHandelingUitAntwoord(
            final MigratieCorrigeerAdresBijhoudingResultaat antwoord)
    {
        ObjecttypeAdministratieveHandeling objecttypeAdministratieveHandeling = null;

        if (null != antwoord.getCorrectieAdres()) {
            objecttypeAdministratieveHandeling = antwoord.getCorrectieAdres().getValue();
        }
        return objecttypeAdministratieveHandeling;
    }
}
