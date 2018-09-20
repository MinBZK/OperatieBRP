/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.soapcommands.bijhouding;

import nl.bprbzk.brp._0001.HuwelijkPartnerschapRegistratieHuwelijkBijhouding;
import nl.bprbzk.brp._0001.HuwelijkPartnerschapRegistratieHuwelijkResponse;
import nl.bprbzk.brp._0001.service.BijhoudingPortType;
import nl.bzk.brp.testclient.soapcommands.AbstractSoapCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * De Class InschrijvingGeboorteCommand.
 */
public class RegistreerHuwelijkEnPartnerschapCommand extends
AbstractSoapCommand<BijhoudingPortType, HuwelijkPartnerschapRegistratieHuwelijkBijhouding, HuwelijkPartnerschapRegistratieHuwelijkResponse>{

    /** De Constante LOG. */
    private static final Logger LOG = LoggerFactory.getLogger(RegistreerHuwelijkEnPartnerschapCommand.class);

    /**
     * Instantieert een nieuwe inschrijving geboorte command.
     *
     * @param vraag de vraag
     */
    public RegistreerHuwelijkEnPartnerschapCommand(final HuwelijkPartnerschapRegistratieHuwelijkBijhouding vraag) {
        super(vraag);
    }

    /* (non-Javadoc)
     * @see nl.bzk.brp.testclient.soapcommands.AbstractSoapCommand#bepaalEnSetAntwoord(java.lang.Object)
     */
    @Override
    public void bepaalEnSetAntwoord(final BijhoudingPortType portType) {
        setAntwoord(portType.registreerHuwelijkEnPartnerschap(getVraag()));
    }

}
