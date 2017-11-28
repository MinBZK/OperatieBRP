/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.dal.repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import nl.bzk.brp.beheer.service.dal.ReferentieRepository;
import org.springframework.stereotype.Component;

/**
 * Implementatie van {@link ReferentieRepository}.
 */
@Component
class ReferentieRepositoryImpl implements ReferentieRepository {

    private EntityManager entityManager;

    @Inject
    ReferentieRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public <T> T getReferentie(Class<T> clazz, Object pk) {
        return entityManager.getReference(clazz, pk);
    }
}
