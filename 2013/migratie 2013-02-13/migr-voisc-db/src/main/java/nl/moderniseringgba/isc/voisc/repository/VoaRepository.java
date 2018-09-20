/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.moderniseringgba.isc.voisc.repository;

import java.util.List;

import nl.moderniseringgba.isc.voisc.entities.Bericht;

/**
 * Voa repository.
 */
public interface VoaRepository {

    /**
     * Sla een bericht op.
     * 
     * @param lo3Bericht
     *            bericht
     * @return bericht
     */
    Bericht save(final Bericht lo3Bericht);

    /**
     * Haal een bericht op.
     * 
     * @param id
     *            id
     * @return bericht
     */
    Bericht getLo3Bericht(final long id);

    /**
     * Haal berichten op om te versturen naar de mailbox.
     * 
     * @param originator
     *            originator
     * @return berichten
     */
    List<Bericht> getBerichtToSendMBS(final String originator);

    /**
     * Haal berichten op om te versturen naar de queue.
     * 
     * @param limit
     *            limiet
     * @return berichten
     */
    List<Bericht> getBerichtToSendQueue(int limit);

    /**
     * Haal bericht op.
     * 
     * @param eref
     *            eref
     * @return bericht
     */
    Bericht getBerichtByEref(final String eref);

    /**
     * Haal bericht op.
     * 
     * @param bref
     *            bref
     * @param originator
     *            originator
     * @return bericht
     */
    Bericht getBerichtByBrefAndOrginator(final String bref, final String originator);

    /**
     * Haal bericht op.
     * 
     * @param messageId
     *            esb message id
     * @return bericht
     */
    Bericht getBerichtByEsbMessageId(String messageId);

    /**
     * Haal bericht op.
     * 
     * @param dispatchSeqNr
     *            dispatch sequence number
     * @return bericht
     */
    Bericht getBerichtByDispatchSeqNr(Integer dispatchSeqNr);
}
