/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.domein.repository;

import nl.bzk.brp.domein.lev.LeveringPersoon;
import nl.bzk.brp.domein.lev.persistent.PersistentLeveringPersoon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository voor leveringen van persoonsgegevens.
 */
/**
 * Repository voor de {@link LeveringPersoon} class, gebaseerd op Spring's {@link JpaRepository} class.
 */
@Repository
public interface LeveringPersoonRepository extends JpaRepository<PersistentLeveringPersoon, Long> {
}
