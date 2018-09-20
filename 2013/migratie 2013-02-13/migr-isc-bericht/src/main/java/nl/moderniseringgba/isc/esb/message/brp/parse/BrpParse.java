/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.esb.message.brp.parse;

import java.math.BigInteger;

import nl.moderniseringgba.migratie.conversie.model.brp.attribuut.BrpDatum;

/**
 * Veel gebruikte parsing voor BRP.
 */
public final class BrpParse {

    private BrpParse() {
        // Niet instantieerbaar
    }

    /**
     * Parse een long.
     * 
     * @param value
     *            string
     * @return long
     */
    public static Long parseLong(final String value) {
        return value == null ? null : Long.valueOf(value);
    }

    /**
     * Parse een brp datum.
     * 
     * @param value
     *            big integer
     * @return brp datum
     */
    public static BrpDatum parseBrpDatum(final BigInteger value) {
        return value == null ? null : new BrpDatum(value.intValue());
    }
}
