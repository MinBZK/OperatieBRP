/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.repository.jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Persoon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonAfnemerindicatie;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonCache;
import nl.bzk.algemeenbrp.services.blobber.BlobException;
import nl.bzk.algemeenbrp.services.blobber.Blobber;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.AfnemersindicatieRepository;
import org.springframework.stereotype.Repository;

/**
 * DAO (Gegevens toegangs punt) voor alles omtrent BRP afnemersindicaties.
 */
@Repository
public final class AfnemersindicatieRepositoryImpl implements AfnemersindicatieRepository {

    private static final String SELECT_BY_PERSOON_QUERY = "SELECT pa FROM PersoonAfnemerindicatie pa WHERE pa.persoon = :persoon";
    public static final String SELECT_CACHE = "select pc from PersoonCache pc where pc.persoon = :persoon";
    public static final String SELECT_AFNEMERINDICATIES = "select afn from PersoonAfnemerindicatie afn where afn.persoon = :persoon";
    public static final String PERSOON = "persoon";
    @PersistenceContext(name = "syncDalEntityManagerFactory", unitName = "BrpEntities")
    private EntityManager em;

    @Override
    public List<PersoonAfnemerindicatie> findByPersoon(final Persoon persoon) {
        final TypedQuery<PersoonAfnemerindicatie> query = em.createQuery(SELECT_BY_PERSOON_QUERY, PersoonAfnemerindicatie.class);
        query.setParameter("persoon", persoon);

        return query.getResultList();
    }

    @Override
    public PersoonAfnemerindicatie save(final PersoonAfnemerindicatie afnemerindicatie) {
        if (afnemerindicatie.getId() == null) {
            em.persist(afnemerindicatie);
            return afnemerindicatie;
        } else {
            return em.merge(afnemerindicatie);
        }
    }

    @Override
    public void slaAfnemerindicatiesCacheOp(final Persoon persoon) {
        try {
            final byte[] blob = maakBlob(persoon);
            PersoonCache persoonCache = findPersoonCache(persoon);
            if (persoonCache == null) {
                persoonCache = new PersoonCache(persoon, (short) 1);
            }
            persoonCache.setAfnemerindicatieGegevens(blob);
            em.persist(persoonCache);
        } catch (final BlobException e) {
            throw new IllegalStateException(e);
        }
    }

    private PersoonCache findPersoonCache(final Persoon persoon) {
        final List<PersoonCache> resultaat = em.createQuery(SELECT_CACHE, PersoonCache.class).setParameter(PERSOON, persoon).getResultList();
        return resultaat.isEmpty() ? null : resultaat.get(0);
    }

    private byte[] maakBlob(Persoon persoon) throws BlobException {
        List<PersoonAfnemerindicatie>
                afnemerindicaties =
                em.createQuery(SELECT_AFNEMERINDICATIES, PersoonAfnemerindicatie.class).setParameter(PERSOON, persoon).getResultList();
        return Blobber.toJsonBytes(Blobber.maakBlob(afnemerindicaties));
    }

}
