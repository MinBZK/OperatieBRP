/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.jbpm.berichten;

import java.util.List;

/**
 * Berichten access.
 */
public interface BerichtenDao {

    /**
     * Sla een bericht op.
     * 
     * @param kanaal
     *            kanaal
     * @param direction
     *            richting
     * @param messageId
     *            bericht id
     * @param correlatieId
     *            correlatie id
     * @param bericht
     *            bericht inhoud
     * 
     * @return id
     */
    Long saveBericht(
            final String kanaal,
            final Direction direction,
            final String messageId,
            final String correlatieId,
            final String bericht);

    /**
     * Associeer (update) de addresserings gemeenten aan een bericht.
     * 
     * @param berichtId
     *            bericht id
     * @param bronGemeente
     *            bron gemeente
     * @param doelGemeente
     *            doel gemeente
     */
    void updateGemeenten(final Long berichtId, final String bronGemeente, final String doelGemeente);

    /**
     * Associeer (update) een bericht naam aan een bericht.
     * 
     * @param berichtId
     *            bericht id
     * @param naam
     *            bericht naam
     */
    void updateNaam(final Long berichtId, final String naam);

    /**
     * Associeer (update) een proces instantie aan een bericht.
     * 
     * @param berichtId
     *            bericht id
     * @param processInstanceId
     *            proces instantie id
     */
    void updateProcessInstance(final Long berichtId, final Long processInstanceId);

    /**
     * Associeer de herhaling aan een bericht.
     * 
     * @param berichtId
     *            bericht id
     * @param herhaling
     *            herhaling
     */
    void updateHerhaling(final Long berichtId, final Integer herhaling);

    /**
     * Zoek de gerelateerde berichten voor een proces instantie.
     * 
     * @param processInstanceId
     *            proces instantie id
     * @return lijst van berichten
     */
    List<Bericht> findBerichtenByProcessInstanceId(final Long processInstanceId);

    /**
     * Geef een specifiek bericht terug.
     * 
     * @param berichtId
     *            bericht id
     * @return bericht
     */
    Bericht getBericht(final Long berichtId);

    /**
     * Zoek de berichten met een bepaald message id, op een bepaald kanaal, in een bepaalde richting.
     * 
     * @param messageId
     *            message id
     * @param kanaal
     *            kanaal
     * @param richting
     *            richting
     * @return lijst van berichten
     */
    List<Bericht> findBerichtenByMessageId(String messageId, String kanaal, Direction richting);
}
