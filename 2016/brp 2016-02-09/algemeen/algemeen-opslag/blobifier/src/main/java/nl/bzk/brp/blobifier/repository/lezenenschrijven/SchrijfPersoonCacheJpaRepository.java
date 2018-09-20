/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.blobifier.repository.lezenenschrijven;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.model.hisvolledig.impl.kern.AdministratieveHandelingHisVolledigImpl;
import nl.bzk.brp.model.operationeel.kern.AdministratieveHandelingModel;
import nl.bzk.brp.model.operationeel.kern.PersoonCacheModel;
import org.springframework.stereotype.Repository;

/**
 * Repository waarmee persoon caches geschreven kunnen worden.
 */
@Repository
public final class SchrijfPersoonCacheJpaRepository implements SchrijfPersoonCacheRepository {

    private static final String PERSOON_ID = "persoonId";
    @PersistenceContext(unitName = "nl.bzk.brp.lezenschrijven")
    private EntityManager entityManager;

    @Override
    public PersoonCacheModel haalPersoonCacheOpUitMasterDatabase(final Integer persoonId) {
        final List<PersoonCacheModel> persoonCaches =
                entityManager.createQuery("select persoonCache from PersoonCacheModel persoonCache where "
                                                  + "persoonCache.persoonId = :persoonId",
                                          PersoonCacheModel.class).setParameter(PERSOON_ID, persoonId).getResultList();
        if (!persoonCaches.isEmpty()) {
            return persoonCaches.get(0);
        }
        return null;
    }

    @Override
    public void opslaanNieuwePersoonCache(final PersoonCacheModel persoonCache) {
        if (null == persoonCache) {
            throw new IllegalArgumentException("PersoonCache is verplicht");
        }
        entityManager.persist(persoonCache);
    }

    @Override
    public List<AdministratieveHandelingModel> haalHandelingenOp(final Integer persoonId) {
        final List<AdministratieveHandelingModel> handelingen =
                entityManager.createQuery(
                        "select h.administratieveHandeling from HisPersoonAfgeleidAdministratiefModel h  where "
                                + "h.persoon.id = :persoonId",
                        AdministratieveHandelingModel.class)
                        .setParameter(PERSOON_ID, persoonId).getResultList();

        return handelingen;
    }

    @Override
    public List<AdministratieveHandelingHisVolledigImpl> haalVerantwoordingOp(final Integer persoonId) {
        final List<AdministratieveHandelingHisVolledigImpl> handelingen =
            entityManager.createQuery(
                "select h from AdministratieveHandelingHisVolledigImpl h, HisPersoonAfgeleidAdministratiefModel afg "
                    + "where h.id = afg.administratieveHandeling.id AND afg.persoon.id = :persoonId",
                AdministratieveHandelingHisVolledigImpl.class)
                .setParameter(PERSOON_ID, persoonId).getResultList();

        return handelingen;
    }
}
