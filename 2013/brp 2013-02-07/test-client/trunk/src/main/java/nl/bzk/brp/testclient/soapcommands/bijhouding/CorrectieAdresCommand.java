/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.soapcommands.bijhouding;

import nl.bprbzk.brp._0001.MigratieCorrectieAdresBinnenNLBijhouding;
import nl.bprbzk.brp._0001.MigratieCorrectieAdresBinnenNLBijhoudingResponse;
import nl.bprbzk.brp._0001.service.BijhoudingPortType;
import nl.bzk.brp.testclient.soapcommands.AbstractSoapCommand;


/**
 * De Class MigratieVerhuizingBijhoudingCommand.
 */
public class CorrectieAdresCommand extends
        AbstractSoapCommand<BijhoudingPortType, MigratieCorrectieAdresBinnenNLBijhouding, MigratieCorrectieAdresBinnenNLBijhoudingResponse>
{

    /** De Constante LOG. */
//    private static final Logger LOG = LoggerFactory.getLogger(CorrectieAdresCommand.class);

    /**
     * Instantieert een nieuwe migratie verhuizing bijhouding command.
     *
     * @param bijhouding de bijhouding
     */
    public CorrectieAdresCommand(final MigratieCorrectieAdresBinnenNLBijhouding bijhouding) {
        super(bijhouding);
    }

    /* (non-Javadoc)
     * @see nl.bzk.brp.testclient.soapcommands.AbstractSoapCommand#bepaalEnSetAntwoord(java.lang.Object)
     */
    @Override
    public void bepaalEnSetAntwoord(final BijhoudingPortType portType) {
        setAntwoord(portType.correctieAdresNL(getVraag()));
    }
}
