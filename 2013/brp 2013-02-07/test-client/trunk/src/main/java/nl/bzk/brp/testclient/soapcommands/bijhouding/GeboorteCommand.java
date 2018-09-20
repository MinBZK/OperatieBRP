/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.soapcommands.bijhouding;

import nl.bprbzk.brp._0001.AfstammingInschrijvingAangifteGeboorteBijhouding;
import nl.bprbzk.brp._0001.AfstammingInschrijvingAangifteGeboorteBijhoudingResponse;
import nl.bprbzk.brp._0001.service.BijhoudingPortType;
import nl.bzk.brp.testclient.soapcommands.AbstractSoapCommand;


/**
 * De Class InschrijvingGeboorteCommand.
 */
public class GeboorteCommand extends
AbstractSoapCommand<BijhoudingPortType, AfstammingInschrijvingAangifteGeboorteBijhouding, AfstammingInschrijvingAangifteGeboorteBijhoudingResponse>{

    /** De Constante LOG. */
//    private static final Logger LOG = LoggerFactory.getLogger(InschrijvingGeboorteCommand.class);

    /**
     * Instantieert een nieuwe inschrijving geboorte command.
     *
     * @param vraag de vraag
     */
    public GeboorteCommand(final AfstammingInschrijvingAangifteGeboorteBijhouding vraag) {
        super(vraag);
    }

    /* (non-Javadoc)
     * @see nl.bzk.brp.testclient.soapcommands.AbstractSoapCommand#bepaalEnSetAntwoord(java.lang.Object)
     */
    @Override
    public void bepaalEnSetAntwoord(final BijhoudingPortType portType) {
        setAntwoord(portType.inschrijvingGeboorte(getVraag()));
    }

}
