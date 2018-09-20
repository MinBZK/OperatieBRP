/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.repository;

import java.util.List;

import nl.bzk.brp.domein.lev.LeveringPersoon;


/**
 * Repository voor de {@link LeveringPersoon} class.
 */
public interface LeveringPersoonRepository {

    /**
     * Bewaar een levering van persoonsgegevens.
     *
     * @param leveringPersoon De levering van persoonsgegevens die bewaard wordt.
     */
    void save(LeveringPersoon leveringPersoon);

    /**
     * Zoek alle leveringen van persoonsgegevens.
     *
     * @return Alle gevonden leveringen van persoonsgegevens.
     */
    List<LeveringPersoon> findAll();
}
