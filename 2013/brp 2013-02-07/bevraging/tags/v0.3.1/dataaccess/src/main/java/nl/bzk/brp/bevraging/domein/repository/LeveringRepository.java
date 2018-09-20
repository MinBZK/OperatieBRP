/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.repository;

import nl.bzk.brp.domein.lev.Levering;
import nl.bzk.brp.domein.lev.persistent.PersistentLevering;

import org.springframework.stereotype.Repository;


/**
 * Repository voor de {@link Levering} class.
 */
@Repository
public interface LeveringRepository {

    /**
     * Bewaar een levering.
     *
     * @param levering De levering die bewaard wordt.
     * @return De bewaarde levering.
     */
    Levering save(Levering levering);

    /**
     * Bewaar een levering, en forceer een flush naar de database.
     *
     * @param levering De levering die bewaard wordt.
     * @return De bewaarde levering.
     */
    Levering saveAndFlush(PersistentLevering levering);
}
