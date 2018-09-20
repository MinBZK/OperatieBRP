/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.bevraging.domein.repository.BerichtRepository;
import nl.bzk.brp.domein.ber.Bericht;
import nl.bzk.brp.domein.ber.persistent.PersistentBericht;

import org.springframework.stereotype.Repository;


/**
 * JPA implementatie van de {@link BerichtRepository}.
 */
@Repository
public class BerichtJpaRepository implements BerichtRepository {

    @PersistenceContext
    private EntityManager em;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Bericht> findAll() {
        return em.createQuery("select bericht from PersistentBericht bericht", Bericht.class).getResultList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(final Bericht entity) {
        em.persist(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Bericht findOne(final Long id) {
        return em.find(PersistentBericht.class, id);
    }
}
