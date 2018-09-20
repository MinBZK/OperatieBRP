/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Session;

public class KernRepo<K, T extends Cacheable<K>> {

    private static final Logger log = Logger.getLogger(KernRepo.class);

    public enum Index {CODE, ORDINAL}

    private final EnumSet<Index> indices;
    private final Map<K, T> cacheByCode;
    private final T[] cacheByOrdinal;

    public KernRepo(final Class<T> domainType, final Index... indices) {
        this.indices = EnumSet.of(indices.length==0? Index.CODE: indices[0], indices);
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

    private Map<K, T> creeerCacheByCode(final Session kernSession, final List<T> objects) {

        final Map<K, T> result = new HashMap<K, T>(objects.size());
        for (T object : objects) {
            result.put(object.getKey(), object);
        }

        return result;
    }

    private T[] creeerCacheByOrdinal(final Session kernSession, final List<T> objects) {

        @SuppressWarnings("unchecked")
        final T[] result = (T[]) new Cacheable<?>[objects.size()];
        int i = 0;
        for (T object : objects) {
            result[i++] = object;
        }

        return result;
    }

    public T getByIndex(final int index) {
        return cacheByOrdinal[index];
    }

    public T get(final K key) {
        return cacheByCode.get(key);
    }

    public T get() {
        return cacheByOrdinal[RandomService.random.nextInt(cacheByOrdinal.length)];
    }

}
