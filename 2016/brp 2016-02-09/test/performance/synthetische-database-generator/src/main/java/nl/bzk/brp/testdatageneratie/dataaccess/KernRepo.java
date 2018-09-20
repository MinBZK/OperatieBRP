/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.dataaccess;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import nl.bzk.brp.testdatageneratie.utils.RandomUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;

/**
 * Kern repo.
 *
 * @param <K> kern bron
 * @param <T> type
 */
public class KernRepo<K, T extends Cacheable<K>> {

    private static final Logger log = Logger.getLogger(KernRepo.class);

    public enum Index {CODE, ORDINAL}

    private final EnumSet<Index> indices;
    private final Map<K, T> cacheByCode;
    private final T[] cacheByOrdinal;

    /**
     * Instantieert Kern repo.
     *
     * @param domainType domain type
     * @param indices indices
     */
    public KernRepo(final Class<T> domainType, final Index... indices) {
        this.indices = EnumSet.of(indices.length ==  0 ? Index.CODE : indices[0], indices);
        Session kernSession = null;
        try {
            kernSession = HibernateSessionFactoryProvider.getInstance().getKernFactory().openSession();

            kernSession.beginTransaction();
            kernSession.setDefaultReadOnly(true);

            @SuppressWarnings("unchecked")
            List<T> objects = kernSession.createCriteria(domainType).list();
            cacheByCode = this.indices.contains(Index.CODE)? creeerCacheByCode(kernSession, objects): null;
            cacheByOrdinal = this.indices.contains(Index.ORDINAL)? creeerCacheByOrdinal(kernSession, objects): null;
            log.info(String.format("%s %s in cache", objects.size(), domainType.getSimpleName()));
        } finally {
            if (kernSession != null) try {
                kernSession.close();
            } catch (RuntimeException e) {
                log.error("", e);
            }
        }
    }

    /**
     * Creeert cache via code.
     *
     * @param kernSession kern session
     * @param objects objects
     * @return map
     */
    private Map<K, T> creeerCacheByCode(final Session kernSession, final List<T> objects) {
        final Map<K, T> result = new HashMap<K, T>(objects.size());
        for (T object : objects) {
            result.put(object.getKey(), object);
        }

        return result;
    }

    /**
     * Creeer cache by ordinal.
     *
     * @param kernSession kern session
     * @param objects objecten
     * @return t array
     */
    private T[] creeerCacheByOrdinal(final Session kernSession, final List<T> objects) {

        @SuppressWarnings("unchecked")
        final T[] result = (T[]) new Cacheable<?>[objects.size()];
        int i = 0;
        for (T object : objects) {
            result[i++] = object;
        }

        return result;
    }

    /**
     * Geeft type via random.
     *
     * @param random random
     * @return type
     */
    public T getByRandom(final Random random) {
        return getByIndex(random.nextInt(cacheByOrdinal.length));
    }

    /**
     * Geeft type via index.
     *
     * @param index index
     * @return type
     */
    public T getByIndex(final int index) {
        return cacheByOrdinal[index];
    }

    /**
     * Geeft type via key.
     *
     * @param key key
     * @return type
     */
    public T get(final K key) {
        return cacheByCode.get(key);
    }

    /**
     * Geeft type.
     *
     * @return random type
     */
    public T get() {
        return cacheByOrdinal[RandomUtil.random.nextInt(cacheByOrdinal.length)];
    }

}
