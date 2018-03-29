/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.repository.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Leveringsaantekening;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.LeveringsaantekeningRepository;

import org.springframework.stereotype.Repository;

/**
 * DAO voor alles omtrent BRP leveringsaantekeningen.
 */
@Repository
public final class LeveringsaantekeningRepositoryImpl implements LeveringsaantekeningRepository {

    @PersistenceContext(name = "syncDalEntityManagerFactory", unitName = "BrpEntities")
    private EntityManager em;

    @Override
    public Leveringsaantekening save(final Leveringsaantekening leveringsaantekening) {
        if (leveringsaantekening.getId() == null) {
            em.persist(leveringsaantekening);
            return leveringsaantekening;
        } else {
            return em.merge(leveringsaantekening);
        }
    }

    @Override
    public Leveringsaantekening find(final Long id) {
        return em.find(Leveringsaantekening.class, id);
    }
}
