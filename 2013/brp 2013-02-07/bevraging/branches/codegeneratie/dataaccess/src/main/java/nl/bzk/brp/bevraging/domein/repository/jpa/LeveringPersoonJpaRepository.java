/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.domein.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.bevraging.domein.repository.LeveringPersoonRepository;
import nl.bzk.brp.domein.lev.LeveringPersoon;

import org.springframework.stereotype.Repository;


@Repository
public class LeveringPersoonJpaRepository implements LeveringPersoonRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(final LeveringPersoon leveringPersoon) {
        em.persist(leveringPersoon);
    }

    @Override
    public List<LeveringPersoon> findAll() {
        return em.createQuery("select lp from PersistentLeveringPersoon lp", LeveringPersoon.class).getResultList();
    }

}
