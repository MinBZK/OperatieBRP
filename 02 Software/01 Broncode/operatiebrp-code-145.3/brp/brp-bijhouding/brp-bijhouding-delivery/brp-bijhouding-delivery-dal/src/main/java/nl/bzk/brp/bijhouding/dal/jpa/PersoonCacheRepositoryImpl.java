/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.dal.jpa;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonCache;
import nl.bzk.algemeenbrp.services.blobber.BlobException;
import nl.bzk.algemeenbrp.services.blobber.Blobber;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.bijhouding.dal.PersoonCacheRepository;
import org.springframework.stereotype.Repository;

/**
 * De JPA implementatie van de {@link nl.bzk.brp.bijhouding.dal.PersoonCacheRepository} interface.
 */
@Repository
final class PersoonCacheRepositoryImpl extends AbstractKernRepository<PersoonCache, Long> implements PersoonCacheRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger();
    private static final String WHERE_CLAUSE = "select pc from PersoonCache pc where pc.persoon = :persoon";

    /**
     * Maakt een nieuw PersoonCacheRepositoryImpl object.
     */
    protected PersoonCacheRepositoryImpl() {
        super(PersoonCache.class);
    }

    @Override
    public void slaPersoonCacheOp(final Persoon persoon) {
        final byte[] persoonBlob;
        try {
            persoonBlob = Blobber.toJsonBytes(Blobber.maakBlob(persoon));
            PersoonCache persoonCache = findByPersoon(persoon);
            if (persoonCache == null) {
                persoonCache = new PersoonCache(persoon, (short) 1);
            }
            persoonCache.setPersoonHistorieVolledigGegevens(persoonBlob);
            save(persoonCache);
        } catch (final BlobException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private PersoonCache findByPersoon(final Persoon persoon) {
        final List<PersoonCache> resultaat = getEntityManager().createQuery(WHERE_CLAUSE, PersoonCache.class).setParameter("persoon", persoon).getResultList();
        return resultaat.isEmpty() ? null : resultaat.get(0);
    }
}
