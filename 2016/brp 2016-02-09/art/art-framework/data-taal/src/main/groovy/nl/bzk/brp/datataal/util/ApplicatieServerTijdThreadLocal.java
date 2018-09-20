/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.datataal.util;

import java.util.Date;

/**
 * ThreadLocal om de applicatie server tijd over te kunnen nemen bij de uitvoer van de DSL.
 */
public final class ApplicatieServerTijdThreadLocal {

    private static final ThreadLocal<Date> APPLICATIE_SERVER_TIJD_THREAD_LOCAL = new ThreadLocal<>();

    private ApplicatieServerTijdThreadLocal() {
        // private constructor
    }

    /**
     * Zet de waarde.
     *
     * @param applicatieServerTijd de applicatie server tijd
     */
    public static void set(final Date applicatieServerTijd) {
        APPLICATIE_SERVER_TIJD_THREAD_LOCAL.set(applicatieServerTijd);
    }

    /**
     * Verwijdert de waarde.
     */
    public static void unset() {
        APPLICATIE_SERVER_TIJD_THREAD_LOCAL.remove();
    }

    /**
     * Geeft de applicatie server tijd.
     *
     * @return de applicatie server tijd
     */
    public static Date get() {
        return APPLICATIE_SERVER_TIJD_THREAD_LOCAL.get();
    }
}
