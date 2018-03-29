/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.controllers;

import java.util.Arrays;

/**
 * Helpers voor vergelijkingen.
 */
public interface VergelijkingUtil {

    /**
     * Inhoudelijke vergelijking (null equals null).
     *
     * @param value1
     *            waarde 1
     * @param value2
     *            waarde 2
     * @return true, als de waarden gelijk zijn
     */
    static boolean isEqual(final Object value1, final Object value2) {
        return value1 == null ? value2 == null : value1.equals(value2);
    }

    /**
     * and vergelijking met een aantal boolean waarden.
     *
     * @param waarden
     *            boolean waarden voor de and vergelijking
     * @return resultaat van de vergelijking
     */
    static boolean and(final Boolean... waarden) {
        return Arrays.stream(waarden).reduce(true, (a, b) -> a && b);
    }
}
