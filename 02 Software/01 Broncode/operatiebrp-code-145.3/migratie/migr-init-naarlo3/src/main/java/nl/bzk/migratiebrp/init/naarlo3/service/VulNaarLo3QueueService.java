/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.init.naarlo3.service;

/**
 * De service voor het vullen van de queue die als werklijst dient voor het converteren van BRP personen naar LO3
 * persoonslijsten.
 */
public interface VulNaarLo3QueueService {

    /**
     * Lees ingeschreven personen uit de BRP database en zet het a-nummer op de queue.
     */
    void leesIngeschrevenenInBrpEnVulQueue();
}
