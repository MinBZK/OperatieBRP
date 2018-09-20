/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.levering.dataaccess.repository.alleenlezen.impl;

import java.util.List;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import nl.bzk.brp.levering.dataaccess.repository.alleenlezen.PartijRepository;
import nl.bzk.brp.model.algemeen.stamgegeven.kern.Partij;
import org.springframework.stereotype.Repository;


/**
 * Implementatie van de partij repository.
 */
@Repository
public class PartijRepositoryImpl implements PartijRepository {


    @PersistenceContext(unitName = "nl.bzk.brp.alleenlezen")
    private EntityManager em;

    @Override
    public final List<Partij> geefAllePartijen() {
        final TypedQuery<Partij> query = em.createQuery("SELECT partij FROM Partij partij", Partij.class);
        query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.BYPASS);
        query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
        return query.getResultList();
    }
}
