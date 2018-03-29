/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.stuf;

import nl.bzk.brp.service.algemeen.StapMeldingException;

/**
 * Service om de inhoud van een stuf bericht verzoek te controleren.
 */
@FunctionalInterface
interface StufBerichtInhoudControleService {

    /**
     * Controleer de inhoud van een stuf bericht verzoek.
     * @param verzoek stuf bericht verzoek
     */
    void controleerInhoud(final StufBerichtVerzoek verzoek) throws StapMeldingException;
}
