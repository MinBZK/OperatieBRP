/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.conversie.regels.proces.lo3naarbrp.attributen;

import java.io.Serializable;
import java.util.Comparator;
import nl.bzk.migratiebrp.conversie.model.lo3.Lo3Categorie;

/**
 * Sorteerder op basis van 8510.
 **/
public final class Comparator8510 implements Comparator<Lo3Categorie<?>>, Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * create new Comparator.
     */
    public Comparator8510() {
        /* this comperator holds no variables */
    }

    @Override
    public int compare(final Lo3Categorie<?> o1, final Lo3Categorie<?> o2) {
        int waarde = 0;
        if (o1.getLo3Herkomst().isLo3ActueelVoorkomen()) {
            waarde = 1;
        }
        if (o2.getLo3Herkomst().isLo3ActueelVoorkomen()) {
            waarde = -1;
        }
        if (waarde != 0) {
            return waarde;
        }
        Integer datum1 = o1.getHistorie().getIngangsdatumGeldigheid().getIntegerWaarde();
        Integer datum2 = o2.getHistorie().getIngangsdatumGeldigheid().getIntegerWaarde();
        if (datum1 == null) {
            datum1 = 0;
        }
        if (datum2 == null) {
            datum2 = 0;
        }
        return datum1 - datum2;
    }
}
