/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.model;

import java.util.Set;

/**
 * De structuur om administratieve handelingen mee te tonen.
 */
public class GgoAdministratieveHandeling extends GgoVoorkomen {
    private static final long serialVersionUID = 1L;

    private Set<String> betrokkenVoorkomens;

    /**
     * Geef de waarde van betrokken voorkomens.
     *
     * @return betrokken voorkomens
     */
    public final Set<String> getBetrokkenVoorkomens() {
        return betrokkenVoorkomens;
    }

    /**
     * Sets the betrokken voorkomens.
     *
     * @param betrokkenVoorkomens
     *            the betrokken voorkomens
     */
    public final void setBetrokkenVoorkomens(final Set<String> betrokkenVoorkomens) {
        this.betrokkenVoorkomens = betrokkenVoorkomens;
    }
}
