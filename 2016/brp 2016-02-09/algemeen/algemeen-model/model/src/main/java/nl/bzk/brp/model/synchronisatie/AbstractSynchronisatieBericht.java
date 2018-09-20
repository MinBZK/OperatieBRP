/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.synchronisatie;

import nl.bzk.brp.model.algemeen.stamgegeven.ber.SoortBerichtAttribuut;
import nl.bzk.brp.model.bericht.ber.BerichtBericht;


/**
 * Abstracte implementaie van een (inkomend) synchronisatie bericht.
 */
public abstract class AbstractSynchronisatieBericht extends BerichtBericht {

    /**
     * Maakt een bericht aan van de meegegeven soort.
     *
     * @param soort de soort.
     */
    protected AbstractSynchronisatieBericht(final SoortBerichtAttribuut soort) {
        super(soort);
    }
}
