/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.database.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;
import nl.bzk.migratiebrp.voisc.database.entities.Bericht;
import nl.bzk.migratiebrp.voisc.database.entities.StatusEnum;

/**
 * Bericht repository.
 */
public interface BerichtRepository {

    /**
     * Sla een bericht op.
     * @param lo3Bericht bericht
     * @return bericht
     */
    Bericht save(final Bericht lo3Bericht);

    /**
     * Haal berichten op om te versturen naar de mailbox.
     * @param originator originator
     * @return berichten
     */
    List<Bericht> getBerichtToSendMBS(final String originator);

    /**
     * Haal berichten op om te versturen naar de queue.
     * @param limit limiet
     * @return berichten
     */
    List<Bericht> getBerichtenToSendQueue(int limit);

    /**
     * Verwijder die berichten die (een van) de opgegeven statussen hebben en verwerkt zijn voor de opgegeven timestamp.
     * @param ouderDan timestamp
     * @param statussen te verwijderen statussen
     * @return aantal verwijderde berichten
     */
    int verwijderVerzondenBerichtenOuderDan(Date ouderDan, Set<StatusEnum> statussen);

    /**
     * Zoek een bericht obv dispatch sequence number, originator en recipient.
     * @param dispatchSequenceNumber dispatch sequence number
     * @param originator verstuurder van het oorspronkelijke bericht
     * @param recipient ontvanger van het oorspronkelijke bericht
     * @return message id, of null als niet gevonden
     */
    Bericht zoekObvDispatchSequenceNumber(Integer dispatchSequenceNumber, String originator, String recipient);

    /**
     * Herstel status berichten.
     * @param ouderDan timestamp
     * @param statusVan status van
     * @param statusNaar status naar
     * @return aantal herstelde berichten
     */
    int herstelStatus(Date ouderDan, StatusEnum statusVan, StatusEnum statusNaar);
}
