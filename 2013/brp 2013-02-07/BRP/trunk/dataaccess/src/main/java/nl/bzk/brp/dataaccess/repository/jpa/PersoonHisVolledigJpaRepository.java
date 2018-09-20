/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nl.bzk.brp.dataaccess.repository.PersoonHisVolledigRepository;
import nl.bzk.brp.model.hisvolledig.PersoonHisVolledigCache;
import nl.bzk.brp.model.hisvolledig.PersoonHisVolledigSerializer;
import nl.bzk.brp.model.hisvolledig.kern.PersoonHisVolledig;
import nl.bzk.brp.model.operationeel.kern.PersoonModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * JPA implementatie van {@link PersoonHisVolledigRepository}.
 */
@Repository("persoonHisVolledigRepository")
public class PersoonHisVolledigJpaRepository implements PersoonHisVolledigRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersoonHisVolledigJpaRepository.class);

    @PersistenceContext(unitName = "nl.bzk.brp")
    private EntityManager em;

    @Inject
    private PersoonHisVolledigSerializer serializer;

    @Override
    public PersoonHisVolledig haalPersoonOp(final Integer id) {
        PersoonHisVolledigCache cache = em.find(PersoonHisVolledigCache.class, id);

        return haalPersoonHisVolledigUitCacheOfDatabase(id, cache);
    }

    /**
     * Haalt een persoon volledig object uit de cache of uit de database als geen cache bestaat.
     *
     * @param id    De technische sleutel van de persoon.
     * @param cache De cache.
     * @return Het PersoonHisVolledig object.
     */
    private PersoonHisVolledig haalPersoonHisVolledigUitCacheOfDatabase(final Integer id,
                                                                        final PersoonHisVolledigCache cache)
    {
        PersoonHisVolledig persoonHisVolledig;
        if (cache != null) {
            persoonHisVolledig = haalPersoonHisVolledigUitCache(cache);
        } else {
            persoonHisVolledig = haalPersoonHisVolledigUitDatabaseEnSlaCacheOp(id);
        }
        return persoonHisVolledig;
    }

    /**
     * Haalt een persoonHisVolledig uit de database en maakt direct een persoonHisVolledigCache aan en slaat deze op.
     *
     * @param id De technische sleutel van de persoon.
     * @return Het PersoonHisVolledig object.
     */
    private PersoonHisVolledig haalPersoonHisVolledigUitDatabaseEnSlaCacheOp(final Integer id) {
        PersoonHisVolledig persoonHisVolledig = leesGenormalizeerdModel(id);
        // TODO: slaan we de blob meteen op?
        opslaanPersoon(persoonHisVolledig);
        return persoonHisVolledig;
    }

    /**
     * Haalt de persoonHisVolledig uit de cache.
     *
     * @param cache De cache.
     * @return Het PersoonHisVolledig object.
     */
    private PersoonHisVolledig haalPersoonHisVolledigUitCache(final PersoonHisVolledigCache cache) {
        String hash = checksum(cache.getData());

        if (!hash.equals(cache.getChecksum())) {
            LOGGER.warn("PVC [{}] heeft niet de correcte checksum: '{}' ipv '{}'",
                        new Object[]{cache.getId(), hash, cache.getChecksum()});
        }
        return deserializeObject(cache);
    }

    @Override
    public List<PersoonHisVolledig> haalPersonenOp(final List<Integer> ids) {
        final String query = "SELECT pvc FROM PersoonHisVolledigCache pvc WHERE pvc.persoon.id IN :ids";
        final TypedQuery<PersoonHisVolledigCache> typedQuery = em.createQuery(query, PersoonHisVolledigCache.class);
        typedQuery.setParameter("ids", ids);

        final List<PersoonHisVolledigCache> personenVolledigCaches = typedQuery.getResultList();

        final Map<Integer, PersoonHisVolledigCache> personenHisVolledigCachesOpPersoonId =
                getPersoonHisVolledigCacheMap(personenVolledigCaches);

        final List<PersoonHisVolledig> resultaat = new ArrayList<PersoonHisVolledig>();
        for (Integer persoonId : ids) {
            resultaat.add(haalPersoonHisVolledigUitCacheOfDatabase(
                    persoonId, personenHisVolledigCachesOpPersoonId.get(persoonId)));
        }

        return resultaat;
    }

    /**
     * Deze methode maakt een hash map van een lijst van persoonHisVolledigCache objecten. De key van de map is het
     * id van de cache.
     * @param personenVolledigCaches De lijst van caches.
     * @return De hash map met daarin alle elementen uit de lijst.
     */
    private Map<Integer, PersoonHisVolledigCache> getPersoonHisVolledigCacheMap(
            final List<PersoonHisVolledigCache> personenVolledigCaches)
    {
        final Map<Integer, PersoonHisVolledigCache> personenHisVolledigCachesOpPersoonId =
                new HashMap<Integer, PersoonHisVolledigCache>();
        for (PersoonHisVolledigCache persoonHisVolledigCache : personenVolledigCaches) {
            personenHisVolledigCachesOpPersoonId.put(persoonHisVolledigCache.getId(), persoonHisVolledigCache);
        }
        return personenHisVolledigCachesOpPersoonId;
    }

    /**
     * Deserializeert een byte[] naar {@link PersoonHisVolledig} instantie.
     *
     * @param cache object met de byte[]
     * @return instantie van PersoonHisVolledig
     */
    protected PersoonHisVolledig deserializeObject(final PersoonHisVolledigCache cache) {
        try {
            return serializer.deserializeer(cache.getData());
        } catch (IOException e) {
            LOGGER.error("Kan cache data [{}] niet deserializeren: {}", cache.getId(), e);
            return null;
        }
    }

    /**
     * Leest een {@link PersoonHisVolledig} uit de genormalizeerde opslag.
     *
     * @param id de identiteit van de instantie
     * @return instantie van PersoonHisVolledig
     */
    private PersoonHisVolledig leesGenormalizeerdModel(final Integer id) {
        return em.find(PersoonHisVolledig.class, id);
    }

    @Override
    public PersoonHisVolledig haalPersoonOp(final PersoonModel persoonModel) {
        return this.haalPersoonOp(persoonModel.getID());
    }

    @Override
    public void opslaanPersoon(final PersoonHisVolledig persoon) {
        if (null == persoon) {
            throw new IllegalArgumentException("PersoonHisVolledig is verplicht");
        }

        PersoonHisVolledigCache cache = em.find(PersoonHisVolledigCache.class, persoon.getID());

        boolean exists = (cache != null);

        if (!exists) {
            cache = new PersoonHisVolledigCache();

            PersoonModel persoonModel = em.find(PersoonModel.class, persoon.getID());
            cache.setPersoon(persoonModel);
        }

        cache.setData(serializeObject(persoon));
        String hash = checksum(cache.getData());

        LOGGER.debug("PVC '{}' heeft hash '{}'", cache.getId(), hash);
        cache.setChecksum(hash);

        if (!exists) {
            em.persist(cache);
        }
    }

    /**
     * Berekent de checksum over de data.
     *
     * @param data de data
     * @return de checksum
     */
    private String checksum(final byte[] data) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");

            if (data != null && data.length >= 0) {
                md.update(data);
                result = new BigInteger(1, md.digest()).toString(16);
            }
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Digest SHA1 bestaat niet");
        }

        return result;
    }

    /**
     * Serializeert een {@link PersoonHisVolledig} instantie.
     *
     * @param persoon de persoon instantie om te serializeren
     * @return de geserializeerde vorm van persoon
     */
    protected byte[] serializeObject(final PersoonHisVolledig persoon) {
        try {
            return serializer.serializeer(persoon);
        } catch (IOException e) {
            LOGGER.error("Kan persoon [{}] niet serializeren: {}", persoon.getID(), e);
            return null;
        }
    }
}
