/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.test.isc.environment.kanaal.sync;

import nl.bzk.migratiebrp.test.isc.environment.kanaal.KanaalException;

/**
 * Persoon service.
 */
public interface PersoonService {

    /**
     * Persisteer een persoon.
     * @param inhoud inhoud
     * @throws KanaalException bij fouten
     */
    void persisteerPersoon(final String inhoud) throws KanaalException;
}
