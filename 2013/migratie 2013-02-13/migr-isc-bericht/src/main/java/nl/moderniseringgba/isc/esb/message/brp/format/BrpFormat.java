/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.brp.format;

import java.math.BigInteger;

import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;

/**
 * Veel gebruikte formatting voor BRP.
 */
public final class BrpFormat {

    private BrpFormat() {
        // Niet instantieerbaar
    }

    /**
     * Format een brp datum.
     * 
     * @param value
     *            datum
     * @return big integer
     */
    public static BigInteger format(final BrpDatum value) {
        return value == null ? null : BigInteger.valueOf(value.getDatum());
    }

    /**
     * Format een long.
     * 
     * @param value
     *            long
     * @return string
     */
    public static String format(final Long value) {
        return value == null ? null : value.toString();
    }

    /**
     * Format een object naar een string.
     * 
     * @param value
     *            waarde
     * @return string
     */
    public static String toString(final Object value) {
        return value == null ? null : value.toString();
    }
}
