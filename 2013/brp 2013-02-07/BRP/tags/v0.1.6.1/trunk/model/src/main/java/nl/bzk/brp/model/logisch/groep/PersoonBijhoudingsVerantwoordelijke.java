/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.model.logisch.groep;

import nl.bzk.brp.model.gedeeld.Verantwoordelijke;
import nl.bzk.brp.model.logisch.AbstractIdentificerendeGroep;

/** De logische groep BijhoudingsVerantwoordelijke binnen Persoon. */

public class PersoonBijhoudingsVerantwoordelijke extends AbstractIdentificerendeGroep {

    private Verantwoordelijke verantwoordelijke;

    /** @return the verantwoordelijke */
    public Verantwoordelijke getVerantwoordelijke() {
        return verantwoordelijke;
    }

    /** @param verantwoordelijke the verantwoordelijke to set */
    public void setVerantwoordelijke(final Verantwoordelijke verantwoordelijke) {
        this.verantwoordelijke = verantwoordelijke;
    }

}
