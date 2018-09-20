/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.injection;

import nl.bzk.migratiebrp.test.isc.environment.kanaal.Bericht;
import nl.bzk.migratiebrp.test.isc.exception.TestException;

/**
 * Injector. Stopt de waarde van een variabele in het testbericht.
 */
public interface Injector {

    /**
     * Injecteer een variabele in het bericht.
     * 
     * @param context
     *            context
     * @param bericht
     *            bericht
     * @param key
     *            sleutel/configuratie
     * @param value
     *            waarde
     * @throws TestException
     *             bij fouten
     */
    void inject(Context context, Bericht bericht, String key, String value) throws TestException;
}
