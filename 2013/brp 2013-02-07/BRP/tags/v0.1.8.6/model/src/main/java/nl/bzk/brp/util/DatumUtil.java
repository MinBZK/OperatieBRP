/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import nl.bzk.brp.model.attribuuttype.Datum;

/**
 * Utility class voor datum.
 */
public final class DatumUtil {

    /**
     * Private constructor.
     */
    private DatumUtil() {
    }

    /**
     * Retourneert de huidige datum. De datum van vandaag.
     * @return De huidige datum.
     */
    public static Datum vandaag() {
        final SimpleDateFormat datumFormaat = new SimpleDateFormat("yyyyMMdd");
        final String datumVandaagString = datumFormaat.format(new Date());
        return new Datum(Integer.parseInt(datumVandaagString));
    }
}
