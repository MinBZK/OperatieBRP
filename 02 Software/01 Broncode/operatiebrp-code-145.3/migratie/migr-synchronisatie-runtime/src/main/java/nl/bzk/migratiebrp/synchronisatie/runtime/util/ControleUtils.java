/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.runtime.util;

import nl.bzk.algemeenbrp.dal.domein.brp.enums.Enumeratie;

/**
 * Klasse voor generieke specifieke equals implementaties.
 */
public final class ControleUtils {

    private ControleUtils() {
        // Niet instantieerbaar.
    }

    /**
     * Controleert twee object op een null-safe manier.
     * @param object1 Object 1
     * @param object2 Object 2
     * @return True indien beide objecten gelijk zijn of beide null zijn, false in alle andere gevallen.
     */
    public static boolean equalsNullSafe(final Object object1, final Object object2) {
        if (object1 == null) {
            return object2 == null;
        } else {
            return object1.equals(object2);
        }
    }

    /**
     * Haalt null-safe de code uit een enumeratie.
     * @param enumeratie De enumeratie waaruit we de code halen.
     * @return De code uit de enumeratie.
     */
    public static String geefNullSafeCodeUitEnumeratie(final Enumeratie enumeratie) {
        return enumeratie == null ? null : enumeratie.getCode();
    }

}
