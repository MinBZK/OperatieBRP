/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.soapcommands.bevraging;

import nl.bzk.brp.bijhouding.service.BhgBevraging;
import nl.bzk.brp.brp0200.BijhoudingBevragingBepaalKandidaatVaderResultaat;
import nl.bzk.brp.brp0200.BijhoudingBevragingBepaalKandidaatVaderVerzoek;
import nl.bzk.brp.brp0200.ObjecttypeAdministratieveHandeling;
import nl.bzk.brp.testclient.soapcommands.AbstractSoapCommand;


/** De Class VraagKandidaatVaderCommand. */
public class KandidaatVaderCommand extends
    AbstractSoapCommand<BhgBevraging, BijhoudingBevragingBepaalKandidaatVaderVerzoek, BijhoudingBevragingBepaalKandidaatVaderResultaat> {

    /**
     * Instantieert een nieuwe vraag kandidaat vader command.
     *
     * @param vraag de vraag
     */
    public KandidaatVaderCommand(final BijhoudingBevragingBepaalKandidaatVaderVerzoek vraag) {
        super(vraag);
    }

    /* (non-Javadoc)
     * @see nl.bzk.brp.testclient.soapcommands.AbstractSoapCommand#bepaalEnSetAntwoord(java.lang.Object)
     */
    @Override
    public BijhoudingBevragingBepaalKandidaatVaderResultaat bepaalEnSetAntwoord(final BhgBevraging portType) {
        return portType.bepaalKandidaatVader(getVraag());
    }

    @Override
    protected ObjecttypeAdministratieveHandeling getAdministratieveHandelingUitAntwoord(
            final BijhoudingBevragingBepaalKandidaatVaderResultaat antwoord)
    {
        // bevraging heeft geen Adm Hand
        return null;
    }

}
