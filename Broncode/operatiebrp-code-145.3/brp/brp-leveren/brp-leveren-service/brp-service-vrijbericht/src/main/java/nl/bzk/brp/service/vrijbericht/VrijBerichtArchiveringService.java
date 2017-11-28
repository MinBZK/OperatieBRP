/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.vrijbericht;

import nl.bzk.brp.domain.berichtmodel.VrijBerichtAntwoordBericht;

/**
 * Service voor het archiveren van vrije berichten.
 */
@FunctionalInterface
interface VrijBerichtArchiveringService {

    /**
     * Archiveert synchrone vrije berichten (het ingaand verzoekbericht en het uitgaand).
     * @param berichtResultaat het berichtresultaat
     * @param antwoordBericht het antwoordbericht
     * @param resultaat resultaat bericht in tekstueel formaat
     */
    void archiveer(final VrijBerichtResultaat berichtResultaat, final VrijBerichtAntwoordBericht antwoordBericht, String resultaat);
}
