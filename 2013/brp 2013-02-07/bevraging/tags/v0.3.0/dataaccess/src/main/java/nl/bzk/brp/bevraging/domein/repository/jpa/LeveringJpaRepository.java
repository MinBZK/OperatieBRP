/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.repository.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.bevraging.domein.repository.LeveringRepository;
import nl.bzk.brp.domein.lev.Levering;
import nl.bzk.brp.domein.lev.persistent.PersistentLevering;

import org.springframework.stereotype.Repository;


/**
 * JPA implementatie van de {@link LeveringRepository}.
 */
@Repository
public class LeveringJpaRepository implements LeveringRepository {

    @PersistenceContext
    private EntityManager em;

    /**
     * {@inheritDoc}
     */
    @Override
    public Levering save(final Levering levering) {
        em.persist(levering);
        return levering;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Levering saveAndFlush(final PersistentLevering levering) {
        em.persist(levering);
        em.flush();
        return levering;
    }

}
