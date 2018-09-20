/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.poc.dal;

import java.util.List;

import nl.bzk.brp.poc.domein.PocPersoon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository voor de {@link nl.bzk.brp.poc.domein.PocPersoon} class, gebaseerd op Spring's
 * {@link org.springframework.data.jpa.repository.JpaRepository} class.
 */
@Repository
public interface PocPersoonRepository extends JpaRepository<PocPersoon, Long> {

    /**
     * Methode voor het ophalen van een persoon op basis van een burger service nummer (BSN).
     *
     * @param bsn bsn om op te zoeken.
     * @return de persoon geidentificeerd door de bsn.
     */
    List<PocPersoon> findByBurgerservicenummer(Long bsn);

}
