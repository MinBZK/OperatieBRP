/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testclient.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Datum utlity functies.
 */
public final class DatumUtil {

    /** Datum formatter die van een {@link Date} de juiste (XML) String formatteert. */
    private static final SimpleDateFormat DATUM_XML_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
    /** Datum formatter die van een {@link Date} de juiste (BRP) String formatteert. */
    private static final SimpleDateFormat DATUM_BRP_FORMATTER = new SimpleDateFormat("yyyyMMdd");

    /**
     * Standaard lege constructor, die private is zodat utility klasse niet geinstantieerd kan worden.
     */
    private DatumUtil() {
    }

    /**
     * Retourneert de datum van vandaag in textuele representatie.
     *
     * @return de datum van vandaag.
     */
    public static String vandaagXmlString() {
        return DATUM_XML_FORMATTER.format(new Date());
    }

    /**
     * Retourneert de opgegeven datum in textuele representatie.
     *
     * @param datum de datum die omgezet dient te worden.
     * @return de datum in textuele representatie.
     */
    public static String datumNaarBrpString(final Date datum) {
        return DATUM_BRP_FORMATTER.format(datum);
    }

    /**
     * Retourneert de opgegeven datum in textuele representatie.
     *
     * @param datum de datum die omgezet dient te worden.
     * @return de datum in textuele representatie.
     */
    public static String datumNaarXmlString(final Date datum) {
        return DATUM_XML_FORMATTER.format(datum);
    }
}
