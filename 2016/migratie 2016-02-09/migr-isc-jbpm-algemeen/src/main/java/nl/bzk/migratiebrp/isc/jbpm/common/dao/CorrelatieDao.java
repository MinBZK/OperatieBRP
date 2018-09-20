/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.jbpm.common.dao;

import nl.bzk.migratiebrp.isc.runtime.jbpm.model.Correlatie;

/**
 * Bericht process correlatie.
 */
public interface CorrelatieDao {

    /**
     * Bewaar een correlatie (overschrijf de huidige opgeslagen correlatie vanwege herhaalberichten).
     *
     * @param messageId
     *            bericht id
     * @param kanaal
     *            kanaal
     * @param recipient
     *            recipient
     * @param originator
     *            originator
     * @param processInstanceId
     *            process instance id
     * @param tokenId
     *            token id
     * @param nodeId
     *            node id
     */
    void opslaan(String messageId, String kanaal, String recipient, String originator, Long processInstanceId, Long tokenId, Long nodeId);

    /**
     * Zoek een correlatie.
     *
     * @param messageId
     *            bericht id
     * @param kanaal
     *            kanaal
     * @param recipient
     *            recipient
     * @param originator
     *            originator
     * @return process instance data (null als niet gevonden)
     */
    Correlatie zoeken(String messageId, String kanaal, String originator, String recipient);

}
