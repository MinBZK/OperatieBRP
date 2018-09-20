/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.groep;

import nl.bzk.brp.model.gedeeld.RedenOpschorting;
import nl.bzk.brp.model.logisch.AbstractIdentificerendeGroep;

/**
 *
 * Logische groep binnen een persoon.
 */

public class PersoonRedenOpschorting extends AbstractIdentificerendeGroep {
    private RedenOpschorting redenOpschortingBijhouding;

    // Het woordenboek zegt dat er nog een datum aanwezig moet zijn. De tabel en xsd hebben dit niet..

    /**
     * @return the redenOpschortingBijhouding
     */
    public RedenOpschorting getRedenOpschortingBijhouding() {
        return redenOpschortingBijhouding;
    }

    /**
     * @param redenOpschortingBijhouding the redenOpschortingBijhouding to set
     */
    public void setRedenOpschortingBijhouding(final RedenOpschorting redenOpschortingBijhouding) {
        this.redenOpschortingBijhouding = redenOpschortingBijhouding;
    }
}
