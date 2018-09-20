/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.migratie.init.vulnaarlo3queue.service;

import java.text.ParseException;
import java.util.Properties;

/**
 * De service voor het vullen van de queue die als werklijst dient voor het converteren van BRP personen naar LO3
 * persoonslijsten.
 * 
 */
public interface VulNaarLo3QueueService {

    /**
     * Lees ingeschreven personen uit de BRP database en zet het a-nummer op de queue.
     * 
     * @param config
     *            Properties bestand met parameters voor de query.
     * @throws ParseException
     *             als de config properties niet geparsed kunnen worden
     */
    void leesIngeschrevenenInBrpEnVulQueue(Properties config) throws ParseException;
}
