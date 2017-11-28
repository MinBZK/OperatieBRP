/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.levering;

import com.google.common.collect.Lists;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfnemerindicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonCache;
import nl.bzk.algemeenbrp.services.blobber.BlobException;
import nl.bzk.algemeenbrp.services.blobber.Blobber;
import nl.bzk.algemeenbrp.services.blobber.json.AfnemerindicatiesBlob;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.service.dalapi.AfnemerindicatieRepository;
import nl.bzk.brp.service.dalapi.PersoonCacheRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Repository voor de {@link nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonCache} class en standaard implementatie van de {@link PersoonCacheRepository}
 * class.
 */
@Repository
public final class PersoonCacheJpaRepositoryImpl implements PersoonCacheRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @PersistenceContext(unitName = "nl.bzk.brp.master")
    private EntityManager entityManager;

    @Inject
    private DataSource masterDataSource;

    @Inject
    private AfnemerindicatieRepository afnemerindicatieRepository;

    private PersoonCacheJpaRepositoryImpl() {
    }

    @Override
    public PersoonCache haalPersoonCacheOp(final long persoonId) {
        final String query = "select persoonCache from PersoonCache persoonCache "
                + "where persoonCache.persoon.id = :persoonId";
        final List<PersoonCache> persoonCaches =
                entityManager.createQuery(query, PersoonCache.class)
                        .setParameter("persoonId", persoonId).getResultList();
        if (!persoonCaches.isEmpty()) {
            return persoonCaches.get(0);
        }
        return null;
    }


    /**
     * Haalt een lijst van persoon caches op voor een lijst van persoon id's.
     * @param ids De id's van de personen.
     * @return De lijst van persoon caches.
     */
    @Override
    public List<PersoonCache> haalPersoonCachesOp(final Set<Long> ids) {
        if (ids.isEmpty()) {
            return Lists.newArrayList();
        }
        final String query = "SELECT pc FROM PersoonCache pc WHERE pc.persoon.id IN :ids";
        final TypedQuery<PersoonCache> typedQuery = entityManager.createQuery(query, PersoonCache.class);
        typedQuery.setParameter("ids", ids);
        return typedQuery.getResultList();
    }

    @Override
    public void updateAfnemerindicatieBlob(final long persoonId, final Long lockVersiePersoon, final Long afnemerindicatieLockVersie) throws BlobException {
        final List<PersoonAfnemerindicatie> afnemerindicatiesNaToevoegen = afnemerindicatieRepository.haalAfnemerindicatiesOp(persoonId);
        final AfnemerindicatiesBlob afnemerindicatiesBlob = Blobber.maakBlob(afnemerindicatiesNaToevoegen);
        LOGGER.info("Blobify persoon:{}", persoonId);
        final byte[] afnemerindicatiesBlobBytes = Blobber.toJsonBytes(afnemerindicatiesBlob);
        //lockversieafnemerindicatiege = null uit initiele vulling of nog geen afnemerindicatie aanwezig
        final String sql
                = "UPDATE kern.perscache pc "
                + "SET lockversieafnemerindicatiege = CASE WHEN lockversieafnemerindicatiege IS NOT NULL THEN lockversieafnemerindicatiege + 1 ELSE  1 END, "
                + "afnemerindicatiegegevens = :afnemerindicatiegegevens "
                + "WHERE (pc.lockversieafnemerindicatiege = :lockversieAfnemerindicatie OR pc.lockversieafnemerindicatiege is null) "
                + "AND pc.pers = :persoonId "
                + "AND EXISTS "
                + "(SELECT 1 FROM kern.pers p WHERE p.id = pc.pers AND p.lockversie = :persoonLock )";
        final NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(masterDataSource);
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("afnemerindicatiegegevens", afnemerindicatiesBlobBytes);
        parameters.put("lockversieAfnemerindicatie", afnemerindicatieLockVersie);
        parameters.put("persoonId", persoonId);
        parameters.put("persoonLock", lockVersiePersoon);
        final int rowsUpdated = jdbcTemplate.update(sql, parameters);
        if (rowsUpdated != 1) {
            throw new OptimisticLockException("PersoonCache is ondertussen gewijzigd.");
        }
    }
}
