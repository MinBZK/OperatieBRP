/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.binding;

import java.util.List;

import nl.bzk.brp.model.logisch.BRPActie;

/**
 * Model class voor het xsd type BRPbericht.
 */
public class BRPBericht {

    private List<BRPActie> brpActies;

    public List<BRPActie> getBrpActies() {
        return brpActies;
    }

    public void setBrpActies(final List<BRPActie> brpActies) {
        this.brpActies = brpActies;
    }
}
