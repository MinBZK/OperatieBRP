/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.monitor.web.servlet.tags.functies;


/**
 * Object Functies.
 *
 */

public final class Functies {

    /**
     * private constructor.
     */
    private Functies() {
    }

    /**
     * Geeft een hashCode terug van een object.
     *
     * @param obj Object
     * @return hashCode
     */
    public static int hashCode(final Object obj) {
        if (obj == null) {
            return 0;
        }

        return obj.hashCode();
    }
}
