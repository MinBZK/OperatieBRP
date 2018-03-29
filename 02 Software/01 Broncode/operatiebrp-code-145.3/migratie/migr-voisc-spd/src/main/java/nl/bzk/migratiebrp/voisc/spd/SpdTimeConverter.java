/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.spd;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Contains converter methods for convertering to/from sPd-protocol DateTime strings.
 */
public final class SpdTimeConverter {
    /**
     * DateTimeFormatter defining the sPd-protocol DateTime format.
     */
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyMMddHHmm'Z'").withZone(ZoneId.of("UTC"));

    private SpdTimeConverter() {
    }

    /**
     * Convert a TimeString from the sPd-protocol to a Date object.
     * @param datumtijd String with Format YYMMDDHHMMZ (Z stands for Zulu or GMT)
     * @return date representatie van de opgegeven waarde
     */
    public static Instant convertSpdTimeStringToInstant(final String datumtijd) {
        return Instant.from(FORMATTER.parse(datumtijd));
    }

    /**
     * Convert an Instant object to a sPd-protocol time string.
     * @param instant Instant
     * @return String with Format YYMMDDHHMMZ (Z stands for Zulu or GMT)
     */
    public static String convertInstantToSpdTimeString(final Instant instant) {
        return FORMATTER.format(instant);
    }
}
