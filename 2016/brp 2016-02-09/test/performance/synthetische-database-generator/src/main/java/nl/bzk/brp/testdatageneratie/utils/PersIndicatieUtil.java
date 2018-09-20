/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.utils;

import nl.bzk.brp.model.algemeen.stamgegeven.kern.SoortIndicatie;
import nl.bzk.brp.testdatageneratie.domain.kern.Pers;
import nl.bzk.brp.testdatageneratie.domain.kern.Persindicatie;


/**
 * Pers indicatie util.
 */
public final class PersIndicatieUtil {

    /**
     * Private constructor.
     */
    private PersIndicatieUtil() {

    }

    /**
     * Creeert een pers indicatie.
     *
     * @param pers pers
     * @param soort soort indicatie
     * @return persindicatie
     */
    public static Persindicatie creeerIndicatie(final Pers pers, final SoortIndicatie soort) {
        Persindicatie persindicatie = new Persindicatie();
        if (soort != null) {
            persindicatie.setSrt((short) soort.ordinal());
        }
        persindicatie.setWaarde(true);
        persindicatie.setPers(pers.getId());
        return persindicatie;
    }
}
