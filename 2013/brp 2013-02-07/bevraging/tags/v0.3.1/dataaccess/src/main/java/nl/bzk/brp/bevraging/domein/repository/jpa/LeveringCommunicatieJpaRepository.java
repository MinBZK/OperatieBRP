/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.bevraging.domein.repository.LeveringCommunicatieRepository;
import nl.bzk.brp.domein.lev.LeveringCommunicatie;

import org.springframework.stereotype.Repository;


/**
 * JPA implementatie van de {@link LeveringCommunicatieRepository}.
 */
@Repository
public class LeveringCommunicatieJpaRepository implements LeveringCommunicatieRepository {

    @PersistenceContext
    private EntityManager em;

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(final LeveringCommunicatie levCom) {
        em.persist(levCom);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<LeveringCommunicatie> findAll() {
        return em.createQuery("select lc from PersistentLeveringCommunicatie lc", LeveringCommunicatie.class)
                .getResultList();
    }

}
