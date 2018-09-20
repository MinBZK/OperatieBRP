/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity;

import java.util.Set;

/**
 * Utility class voor entiteiten met historie.
 */
public final class HistorieUtil {

    /*
     * Explicit private constructor.
     */
    private HistorieUtil() {
        throw new AssertionError("HistorieUtil mag niet geinstantieerd worden.");
    }

    /**
     * Geef het actuele historie voorkomen.
     *
     * @param <T>
     *            het type historie
     * @param historieSet
     *            de historie set
     * @return het actuele (niet-vervallen) historische voorkomen van deze set historie, of null als deze niet bestaat
     */
    public static <T extends FormeleHistorie> T getActueelHistorieVoorkomen(final Set<T> historieSet) {
        T result = null;
        for (final T historie : historieSet) {
            if (historie.isActueel()) {
                result = historie;
                break;
            }
        }
        return result;
    }
}
