/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.soapcommands.bevraging;

import nl.bzk.brp.bijhouding.service.BhgBevraging;
import nl.bzk.brp.brp0200.BevragingGeefPersonenOpAdresMetBetrokkenhedenResultaat;
import nl.bzk.brp.brp0200.BevragingGeefPersonenOpAdresMetBetrokkenhedenVerzoek;
import nl.bzk.brp.brp0200.ObjecttypeAdministratieveHandeling;
import nl.bzk.brp.testclient.soapcommands.AbstractSoapCommand;


/** De Class VraagDetailsPersoonCommand. */
public class PersonenOpAdresCommand extends
    AbstractSoapCommand<BhgBevraging, BevragingGeefPersonenOpAdresMetBetrokkenhedenVerzoek,
            BevragingGeefPersonenOpAdresMetBetrokkenhedenResultaat>
{

    /**
     * Instantieert een nieuwe vraag details persoon command.
     *
     * @param vraag de vraag
     */
    public PersonenOpAdresCommand(final BevragingGeefPersonenOpAdresMetBetrokkenhedenVerzoek vraag) {
        super(vraag);
    }

    /* (non-Javadoc)
     * @see nl.bzk.brp.testclient.soapcommands.AbstractSoapCommand#bepaalEnSetAntwoord(java.lang.Object)
     */
    @Override
    public BevragingGeefPersonenOpAdresMetBetrokkenhedenResultaat bepaalEnSetAntwoord(final BhgBevraging portType) {
        return portType.geefPersonenOpAdresMetBetrokkenheden(getVraag());
    }

    @Override
    protected ObjecttypeAdministratieveHandeling getAdministratieveHandelingUitAntwoord(
            final BevragingGeefPersonenOpAdresMetBetrokkenhedenResultaat antwoord)
    {
        // bevraging heeft geen Adm Hand
        return null;
    }
}
