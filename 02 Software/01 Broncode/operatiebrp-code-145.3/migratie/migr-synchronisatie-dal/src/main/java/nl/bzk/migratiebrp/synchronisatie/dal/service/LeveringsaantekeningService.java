/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.service;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsaantekening;

/**
 * Interface class voor alle operaties die nodig zijn voor leveringsaantekeningen voor de DAL.
 */
public interface LeveringsaantekeningService {
    /**
     * Slaat leveringsaantekeningen op in de BRP database.
     * @param leveringsaantekening de leveringsaantekening
     */
    void persisteerLeveringsaantekening(Leveringsaantekening leveringsaantekening);

    /**
     * Haal leveringsaantekening op aan de hand van id.
     * @param id het id van de leveringsaantekening
     * @return de leveringsaantekening
     */
    Leveringsaantekening bevraagLeveringsaantekening(Long id);
}
