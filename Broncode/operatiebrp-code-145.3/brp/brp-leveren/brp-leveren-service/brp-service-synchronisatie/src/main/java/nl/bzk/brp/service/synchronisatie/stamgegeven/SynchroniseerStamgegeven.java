/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.synchronisatie.stamgegeven;

import nl.bzk.brp.service.synchronisatie.SynchronisatieVerzoek;

/**
 * Containerinterface voor de interne calls bij het synchroniseren van het stamgegeven.
 */
public interface SynchroniseerStamgegeven {


    /**
     * Service voor het bepalen van een stamgegeven.
     */
    interface BepaalStamgegevenService {

        /**
         * Bepaalt het te synchroniseren stamgegeven.
         * @param verzoek het verzoek
         * @return het resultaat object
         */
        BepaalStamgegevenResultaat maakResultaat(SynchronisatieVerzoek verzoek);

    }
}
