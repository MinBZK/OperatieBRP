/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import nl.bzk.brp.model.operationeel.kern.PersistentPersoon;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * JPA Repository voor de {@link nl.bzk.brp.model.operationeel.kern.PersistentPersoon} entity.
 */
public interface PersoonRepository extends JpaRepository<PersistentPersoon, Long> {

    /**
     * Zoek een persoon op basis van een bsn.
     * @param bsn Burgerservicenummer van de te zoeken persoon.
     * @return De gevonden persoon.
     */
    PersistentPersoon findByBurgerservicenummer(String bsn);
}
