/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.service;

import nl.bzk.brp.bevraging.business.dto.BerichtenIds;


/**
 * Service voor het archiveren van een bericht, inkomend en uitgaand.
 */
public interface ArchiveringService {

    /**
     * Archiveert een bericht.
     *
     * @param data het bericht dat gearchiveerd dient te worden.
     * @return id van het bericht dat is gearchiveerd.
     */
    BerichtenIds archiveer(String data);

    /**
     * Werkt de data van een bericht bij in de database.
     *
     * @param uitgaandBerichtId Id van het uitgaande bericht wat bijgewerkt moet worden.
     * @param nieuweData De data die bij het uitgaande bericht hoort.
     */
    void werkDataBij(final Long uitgaandBerichtId, final String nieuweData);

}
