/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie;

import java.util.HashMap;
import java.util.Map;

import nl.bzk.brp.testdatageneratie.KernRepo.Index;
import nl.bzk.brp.testdatageneratie.domain.kern.Aangadresh;
import nl.bzk.brp.testdatageneratie.domain.kern.Autvanafgiftereisdoc;
import nl.bzk.brp.testdatageneratie.domain.kern.Land;
import nl.bzk.brp.testdatageneratie.domain.kern.Nation;
import nl.bzk.brp.testdatageneratie.domain.kern.Partij;
import nl.bzk.brp.testdatageneratie.domain.kern.Plaats;
import org.apache.log4j.Logger;


public class MetaRepo {

    private static final Logger log = Logger.getLogger(KernRepo.class);

    private static final Map<Class<? extends Cacheable<?>>, KernRepo<?, ?>> META_CACHE = new HashMap<Class<? extends Cacheable<?>>, KernRepo<?, ?>>();

    static {
        addRepo(Aangadresh.class);
        addRepo(Land.class);
        addRepo(Partij.class);
        addRepo(Plaats.class, KernRepo.Index.ORDINAL);
        addRepo(Nation.class);
        addRepo(Autvanafgiftereisdoc.class);
    }

    private static <K, T extends Cacheable<K>> void addRepo(final Class<T> domainType, final Index... indices) {
        META_CACHE.put(domainType, new KernRepo<K, T>(domainType, indices));
    }

    public static void initIfNeeded() {
        log.info(String.format("%s %s in %s", META_CACHE.size(), KernRepo.class.getSimpleName(), MetaRepo.class.getSimpleName()));
    }

    public static <K, T extends Cacheable<K>> T get(final Class<T> domainType, final K key) {
        @SuppressWarnings("unchecked")
        KernRepo<K, T> repo = (KernRepo<K, T>) META_CACHE.get(domainType);
        return repo.get(key);
    }

    public static <K, T extends Cacheable<K>> T get(final Class<T> domainType) {
        @SuppressWarnings("unchecked")
        KernRepo<K, T> repo = (KernRepo<K, T>) META_CACHE.get(domainType);
        return repo.get();
    }

}
