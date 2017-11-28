/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.levering;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.brp.service.dalapi.PersoonRepository;
import org.springframework.stereotype.Repository;

/**
 * PersoonJpaRepositoryImpl.
 */
@Repository
public final class PersoonJpaRepositoryImpl implements PersoonRepository {

    @PersistenceContext(unitName = "nl.bzk.brp.master")
    private EntityManager entityManager;

    @Override
    public Persoon haalPersoonOp(final long persoonId) {
        final String query = "select p from Persoon p where p.id = :persoonId";
        return entityManager.createQuery(query, Persoon.class)
                .setParameter("persoonId", persoonId).getSingleResult();
    }
}
