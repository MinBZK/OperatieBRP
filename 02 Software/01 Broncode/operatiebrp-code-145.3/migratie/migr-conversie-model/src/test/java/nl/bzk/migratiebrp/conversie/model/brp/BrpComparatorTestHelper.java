/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.model.brp;

import java.io.Serializable;
import java.util.Comparator;
import nl.bzk.migratiebrp.conversie.model.brp.groep.BrpGroepInhoud;

/**
 * Helper class for sortering binnen testcases.
 *
 * Eigenlijke sortering maakt niet uit, zo lang deze maar consistent is.
 */
public class BrpComparatorTestHelper implements Comparator<BrpGroep<? extends BrpGroepInhoud>>, Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public int compare(final BrpGroep<? extends BrpGroepInhoud> h0, final BrpGroep<? extends BrpGroepInhoud> h1) {
        int result = 0;
        if (h0.getInhoud().hashCode() > h1.getInhoud().hashCode()) {
            result = 1;
        }
        if (h0.getInhoud().hashCode() < h1.getInhoud().hashCode()) {
            result = -1;
        }
        if (h0.getHistorie().hashCode() > h1.getHistorie().hashCode()) {
            result = -1;
        }
        if (h0.getHistorie().hashCode() < h1.getHistorie().hashCode()) {
            result = 1;
        }
        return result;
    }
}
