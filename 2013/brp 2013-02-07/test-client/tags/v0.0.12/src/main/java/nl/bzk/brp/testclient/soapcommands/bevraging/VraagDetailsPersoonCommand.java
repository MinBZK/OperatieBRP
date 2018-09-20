/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.soapcommands.bevraging;

import nl.bprbzk.brp._0001.BevragingAntwoordPersoonDetails;
import nl.bprbzk.brp._0001.BevragingDetailsPersoon;
import nl.bprbzk.brp._0001.service.BevragingPortType;
import nl.bzk.brp.testclient.soapcommands.AbstractSoapCommand;


/**
 * De Class VraagDetailsPersoonCommand.
 */
public class VraagDetailsPersoonCommand extends
        AbstractSoapCommand<BevragingPortType, BevragingDetailsPersoon, BevragingAntwoordPersoonDetails>
{

    /** De Constante LOG. */
//    private static final Logger LOG = LoggerFactory.getLogger(VraagDetailsPersoonCommand.class);

    /**
     * Instantieert een nieuwe vraag details persoon command.
     *
     * @param vraag de vraag
     */
    public VraagDetailsPersoonCommand(final BevragingDetailsPersoon vraag) {
        super(vraag);
    }

    /* (non-Javadoc)
     * @see nl.bzk.brp.testclient.soapcommands.AbstractSoapCommand#bepaalEnSetAntwoord(java.lang.Object)
     */
    @Override
    public void bepaalEnSetAntwoord(final BevragingPortType portType) {
        setAntwoord(portType.vraagDetailsPersoon(getVraag()));
    }
}
