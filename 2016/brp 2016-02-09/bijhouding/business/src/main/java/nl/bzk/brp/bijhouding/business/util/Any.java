/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.business.util;

/**
 * Clas met simpele utils die opgaan voor een of meerdere meegegeven parameters.
 */
public final class Any {

    /**
     * private constructor zodat alleen de statische methodes gebruikt kunnen worden.
     */
    private Any() {
    }

    /**
     * bekijkt of een of meerdere objecten null is/zijn.
     *
     * @param objects de objecten die bekeken moeten worden
     * @return true als er ergens een null waarde is
     */
    public static boolean isNull(final Object... objects) {
        boolean isNull = true;
        if (objects != null) {
            for (final Object object : objects) {
                isNull = object == null;
                if (isNull) {
                    break;
                }
            }
        }
        return isNull;
    }

    /**
     * bekijkt of alle objecten die meegegeven worden niet null zijn.
     *
     * @param objects de objecten die bekeken moeten worden
     * @return true als alle objecten niet null zijn
     */
    public static boolean isNotNull(final Object... objects) {
        boolean isNotNull = false;
        if (objects != null) {
            for (final Object object : objects) {
                isNotNull = object != null;
                if (isNotNull) {
                    break;
                }
            }
        }
        return isNotNull;
    }
}
