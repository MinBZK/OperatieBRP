/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bmr.metamodel.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.bmr.metamodel.NamedModelElement;
import nl.bzk.brp.bmr.metamodel.repository.ApplicatieRepository;
import nl.bzk.brp.bmr.metamodel.ui.Applicatie;

import org.springframework.stereotype.Repository;


/**
 * Abstracte superclasse voor alle JPA repositories van de BMR generator.
 */
@Repository("applicatieRepository")
public class ApplicatieJpaRepository implements ApplicatieRepository {

    @PersistenceContext
    private EntityManager em;

    /**
     * {@inheritDoc}
     */
    @Override
    public NamedModelElement findByNaam(final String naam) {
        return em.createQuery("select applicatie from Applicatie applicatie where naam = :naam", Applicatie.class)
                .setParameter("naam", naam).getSingleResult();
    }

    @Override
    public List<Applicatie> findAll() {
        return em.createQuery("select applicatie from Applicatie applicatie", Applicatie.class).getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }
}
