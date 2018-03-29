/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.levering;

import java.util.List;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.brp.service.dalapi.PartijRepository;
import org.springframework.stereotype.Repository;


/**
 * Implementatie van de partij repository.
 */
@Repository
public class PartijRepositoryImpl implements PartijRepository {

    @PersistenceContext(unitName = "nl.bzk.brp.master")
    private EntityManager em;

    @Override
    public List<Partij> get() {
        final TypedQuery<Partij> query = em.createQuery("select p from Partij p where p.isActueelEnGeldig = true", Partij.class);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
        return query.getResultList();
    }
}
