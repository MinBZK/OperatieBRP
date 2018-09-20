/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.repository;

import java.util.List;

import nl.bzk.brp.domein.lev.Abonnement;
import nl.bzk.brp.domein.lev.persistent.PersistentAbonnement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository voor de {@link Abonnement} class, gebaseerd op Spring's {@link JpaRepository} class.
 */
@Repository
public interface AbonnementRepository extends JpaRepository<PersistentAbonnement, Integer> {

    /**
     * Methode voor het ophalen van een abonnement op basis van de id.
     *
     * @param id id van het abonnement.
     * @return het gevonden abonnement.
     */
    List<Abonnement> findById(Integer id);

}
