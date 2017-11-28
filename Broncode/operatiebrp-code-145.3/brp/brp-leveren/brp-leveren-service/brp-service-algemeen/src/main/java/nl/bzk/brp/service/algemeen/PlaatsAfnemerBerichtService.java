/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.algemeen;

import java.util.List;
import nl.bzk.brp.domain.internbericht.verzendingmodel.AfnemerBericht;

/**
 * Plaatst berichten voor een afnemer ter verzending.
 */
@FunctionalInterface
public interface PlaatsAfnemerBerichtService {

    /**
     * Plaats berichten.
     * @param afnemerBerichten afnemerBerichten
     */
    void plaatsAfnemerberichten(final List<AfnemerBericht> afnemerBerichten);
}
