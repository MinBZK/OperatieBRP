/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.blobifier.repository.alleenlezen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nl.bzk.brp.model.operationeel.kern.PersoonCacheModel;
import org.springframework.stereotype.Repository;

/**
 * Repository waarmee de toegang tot de persoon caches in de database geregeld wordt.
 */
@Repository
public final class LeesPersoonCacheJpaRepository implements LeesPersoonCacheRepository {

    @PersistenceContext(unitName = "nl.bzk.brp.alleenlezen")
    private EntityManager entityManager;

    /**
     * Haalt een lijst van persoon caches op voor een lijst van persoon id's.
     *
     * @param ids De id's van de personen.
     * @return De lijst van persoon caches.
     */
    private List<PersoonCacheModel> haalPersoonCachesOp(final List<Integer> ids) {
        final String query = "SELECT pc FROM PersoonCacheModel pc WHERE pc.persoonId IN :ids";
        final TypedQuery<PersoonCacheModel> typedQuery = entityManager.createQuery(query, PersoonCacheModel.class);
        typedQuery.setParameter("ids", ids);

        return typedQuery.getResultList();
    }

    /**
     * Deze methode maakt een hash map van een lijst van PersoonCache objecten. De key van de map is het
     * id van de cache.
     *
     * @param personenVolledigCaches De lijst van caches.
     * @return De hash map met daarin alle elementen uit de lijst.
     */
    private Map<Integer, PersoonCacheModel> getPersoonCacheMap(
            final List<PersoonCacheModel> personenVolledigCaches)
    {
        final Map<Integer, PersoonCacheModel> personenHisVolledigCachesOpPersoonId =
                new HashMap<Integer, PersoonCacheModel>();
        for (PersoonCacheModel persoonCache : personenVolledigCaches) {
            personenHisVolledigCachesOpPersoonId.put(persoonCache.getPersoonId(), persoonCache);
        }
        return personenHisVolledigCachesOpPersoonId;
    }

    @Override
    public PersoonCacheModel haalPersoonCacheOp(final Integer persoonId) {
        final List<PersoonCacheModel> persoonCaches =
                entityManager.createQuery("select persoonCache from PersoonCacheModel persoonCache where "
                                       + "persoonCache.persoonId = :persoonId",
                               PersoonCacheModel.class)
                        .setParameter("persoonId", persoonId).getResultList();
        if (!persoonCaches.isEmpty()) {
            return persoonCaches.get(0);
        }
        return null;
    }

    @Override
    public Map<Integer, PersoonCacheModel> haalPersoonCachesOpMetPersoonHisVolledigGegevens(final List<Integer> ids) {
        final List<PersoonCacheModel> persoonCaches = haalPersoonCachesOp(ids);
        //haal de gegevens lazy op
        for (PersoonCacheModel persoonCache : persoonCaches) {
            persoonCache.getStandaard().getPersoonHistorieVolledigGegevens();
        }
        return getPersoonCacheMap(persoonCaches);
    }

    @Override
    public PersoonCacheModel haalPersoonCacheOpMetPersoonHisVolledigGegevens(final Integer persoonId) {
        final PersoonCacheModel persoonCache = haalPersoonCacheOp(persoonId);
        if (persoonCache != null) {
            //haal de gegevens lazy op
            persoonCache.getStandaard().getPersoonHistorieVolledigGegevens();
        }
        return persoonCache;
    }

    @Override
    public Map<Integer, PersoonCacheModel> haalPersoonCachesOpMetAdministratieveHandelingenGegevens(
            final List<Integer> ids)
    {
        final List<PersoonCacheModel> persoonCaches = haalPersoonCachesOp(ids);
        //haal de gegevens lazy op
        for (PersoonCacheModel persoonCache : persoonCaches) {
            persoonCache.getStandaard().getAfnemerindicatieGegevens();
        }
        return getPersoonCacheMap(persoonCaches);
    }

    @Override
    public PersoonCacheModel haalPersoonCacheOpMetAdministratieveHandelingenGegevens(final Integer id) {
        final PersoonCacheModel persoonCache = haalPersoonCacheOp(id);
        if (persoonCache != null) {
            //haal de gegevens lazy op
            persoonCache.getStandaard().getAfnemerindicatieGegevens();
        }
        return persoonCache;
    }
}
