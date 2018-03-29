/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.telling.repository;

import java.sql.Timestamp;

/**
 * Virtueel proces repository.
 */
public interface VirtueelProcesRepository {

    /**
     * Verwijder virtuele processen voor een bepaalde datum.
     * @param datumTot datum tot
     * @return aantal verwijderde virtuele processen
     */
    int verwijder(Timestamp datumTot);
}
