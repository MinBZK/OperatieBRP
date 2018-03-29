/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.injection;

import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.exception.TestException;

/**
 * Extractor. Haalt waarden uit het test proces en zet deze invariabelen.
 */
public interface Extractor {

    /**
     * Extraheer een waarde.
     * @param context context
     * @param bericht test bericht
     * @param key sleutel (uit configuratie)
     * @return resulterende waarde uit extractie
     * @throws TestException bij fouten
     */
    String extract(Context context, Bericht bericht, String key) throws TestException;
}
