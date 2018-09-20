/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.bijhouding;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBericht;
import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBerichtAttribuut;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;


/**
 * Model class voor het xsd type BRPbericht.
 */
public class BijhoudingsBericht extends BerichtBericht {

    /**
     * Standaard constructor die de soort van het bericht zet.
     *
     * @param soort de soort van het bericht.
     */
    protected BijhoudingsBericht(final SoortBericht soort) {
        super(new SoortBerichtAttribuut(soort));
    }
}
