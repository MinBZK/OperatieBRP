/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.mutatielevering;

/**
 * API interface voor module mutatielevering.
 */
public interface MutatieleveringApiService {

    /**
     * Levert de laatst handeling van de gegeven persoon.
     *
     * @param bsn bsn van de persoon
     */
    void leverLaatsteHandelingVoorPersoon(final String bsn);

    void leverInitieleVullingVoorPersoon(final String bsn);
}
