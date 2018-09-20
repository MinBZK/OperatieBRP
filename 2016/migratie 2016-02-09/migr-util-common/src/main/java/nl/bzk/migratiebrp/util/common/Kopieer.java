/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.util.common;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * Utility methodes voor het defensief kopieeren van Mutable waarden.
 */
public final class Kopieer {

    private Kopieer() {
        throw new UnsupportedOperationException();
    }

    /**
     * Utility methode om een kopie van een java.sql.Timestamp te maken. Te gebruiken voor het defensieve kopieren van
     * (mutable) Timestamp objecten in getters, setters en constructors. Kan omgaan met <code>null</code>.
     *
     * @param ts
     *            De te kopieren Timestamp
     * @return Een kopie van de Timestamp
     */
    public static Timestamp timestamp(final Timestamp ts) {
        if (ts == null) {
            return null;
        }

        final Timestamp kopie = new Timestamp(ts.getTime());
        kopie.setNanos(ts.getNanos());
        return kopie;
    }

    /**
     * Utility methode om een kopie van een java.sql.Date te maken. Te gebruiken voor het defensieve kopieren van
     * (mutable) Date objecten in getters, setters en constructors. Kan omgaan met <code>null</code>.
     *
     * @param date
     *            De te kopieren Timestamp
     * @return Een kopie van de Timestamp
     */
    public static Date sqlDate(final Date date) {
        if (date == null) {
            return null;
        }

        return new Date(date.getTime());
    }

    /**
     * Utility methode om een kopie van een java.util.Date te maken. Te gebruiken voor het defensieve kopieren van
     * (mutable) Date objecten in getters, setters en constructors. Kan omgaan met <code>null</code>.
     *
     * @param date
     *            De te kopieren Timestamp
     * @return Een kopie van de Timestamp
     */
    public static java.util.Date utilDate(final java.util.Date date) {
        if (date == null) {
            return null;
        }

        return new java.util.Date(date.getTime());
    }
}
