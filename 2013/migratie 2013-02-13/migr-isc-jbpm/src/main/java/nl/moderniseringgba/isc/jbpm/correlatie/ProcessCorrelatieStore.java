/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.jbpm.correlatie;

/**
 * Bericht process correlatie.
 */
public interface ProcessCorrelatieStore {

    /**
     * Bewaar een correlatie (overschrijf de huidige opgeslagen correlatie bij het messageId, vanwege herhaalberichten).
     * 
     * @param messageId
     *            bericht id
     * @param processData
     *            process instance data
     */
    void bewaarProcessCorrelatie(String messageId, ProcessData processData);

    /**
     * Zoek een correlatie.
     * 
     * @param messageId
     *            bericht id
     * @return process instance data (null als niet gevonden)
     */
    ProcessData zoekProcessCorrelatie(String messageId);

}
