/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.testdatageneratie.dataaccess;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import nl.bzk.brp.testdatageneratie.dataaccess.KernRepo.Index;
import nl.bzk.brp.testdatageneratie.domain.kern.Aandinhingvermissingreisdoc;
import nl.bzk.brp.testdatageneratie.domain.kern.Aandverblijfsr;
import nl.bzk.brp.testdatageneratie.domain.kern.Aang;
import nl.bzk.brp.testdatageneratie.domain.kern.Auttypevanafgiftereisdoc;
import nl.bzk.brp.testdatageneratie.domain.kern.Dbobject;
import nl.bzk.brp.testdatageneratie.domain.kern.Gem;
import nl.bzk.brp.testdatageneratie.domain.kern.Landgebied;
import nl.bzk.brp.testdatageneratie.domain.kern.Nation;
import nl.bzk.brp.testdatageneratie.domain.kern.Partij;
import nl.bzk.brp.testdatageneratie.domain.kern.Plaats;
import nl.bzk.brp.testdatageneratie.domain.kern.Rdnverknlnation;
import nl.bzk.brp.testdatageneratie.domain.kern.Rdnverliesnlnation;
import nl.bzk.brp.testdatageneratie.domain.kern.Regel;
import nl.bzk.brp.testdatageneratie.domain.kern.Srtdoc;
import org.apache.log4j.Logger;


/**
 * Meta repo.
 */
public final class MetaRepo {

    private static final Logger LOG = Logger.getLogger(MetaRepo.class);
    private static final Map<Class<? extends Cacheable<?>>, KernRepo<?, ?>> META_CACHE = new HashMap<>();

    static {
        LOG.info("----- MetaRepo initialize--------");
        addRepo(Aandinhingvermissingreisdoc.class, KernRepo.Index.ORDINAL);
        addRepo(Aandverblijfsr.class);
        addRepo(Aang.class);
        addRepo(Auttypevanafgiftereisdoc.class);
        addRepo(Dbobject.class, KernRepo.Index.ORDINAL);
        addRepo(Gem.class, KernRepo.Index.ORDINAL, KernRepo.Index.CODE);
        addRepo(Landgebied.class);
        addRepo(Nation.class);
        addRepo(Partij.class, KernRepo.Index.ORDINAL, KernRepo.Index.CODE);
        addRepo(Plaats.class, KernRepo.Index.ORDINAL);
        addRepo(Rdnverknlnation.class);
        addRepo(Rdnverliesnlnation.class);
        addRepo(Regel.class, KernRepo.Index.ORDINAL);
        addRepo(Srtdoc.class, KernRepo.Index.ORDINAL);

        LOG.info("----- MetaRepo end initialize--------");
    }

    /**
     * Instantieert Meta repo.
     */
    private MetaRepo() {

    }

    /**
     * Add repo.
     *
     * @param <K> kern
     * @param <T> type
     * @param domainType domain type
     * @param indices indices
     */
    private static <K, T extends Cacheable<K>> void addRepo(final Class<T> domainType, final Index... indices) {
        LOG.debug("domainType: " + domainType.getSimpleName());
        for (Index index : indices) {
            LOG.debug("Index: " + index);
        }
        META_CACHE.put(domainType, new KernRepo<K, T>(domainType, indices));
    }

    /**
     * Initialiseer wanneer benodigd.
     */
    public static void initIfNeeded() {
        LOG.info(String.format("%s %s in %s", META_CACHE.size(), KernRepo.class.getSimpleName(),
                               MetaRepo.class.getSimpleName()));
    }

    /**
     * Geeft via random.
     *
     * @param <K> kern
     * @param <T> type
     * @param domainType domain type
     * @param random random
     * @return by index
     */
    public static <K, T extends Cacheable<K>> T getByRandom(final Class<T> domainType, final Random random) {
        @SuppressWarnings("unchecked")
        KernRepo<K, T> repo = (KernRepo<K, T>) META_CACHE.get(domainType);
        return repo.getByRandom(random);
    }

    /**
     * Geeft via index.
     *
     * @param <K> kern
     * @param <T> type
     * @param domainType domain type
     * @param index index
     * @return type
     */
    public static <K, T extends Cacheable<K>> T getByIndex(final Class<T> domainType, final int index) {
        @SuppressWarnings("unchecked")
        KernRepo<K, T> repo = (KernRepo<K, T>) META_CACHE.get(domainType);
        return repo.getByIndex(index);
    }

    /**
     * Get t.
     *
     * @param <K> kern
     * @param <T> type
     * @param domainType domain type
     * @param key key
     * @return type
     */
    public static <K, T extends Cacheable<K>> T get(final Class<T> domainType, final K key) {
        @SuppressWarnings("unchecked")
        KernRepo<K, T> repo = (KernRepo<K, T>) META_CACHE.get(domainType);
        return repo.get(key);
    }

    /**
     * Get t.
     *
     * @param <K> kern
     * @param <T> type
     * @param domainType domain type
     * @return type
     */
    public static <K, T extends Cacheable<K>> T get(final Class<T> domainType) {
        @SuppressWarnings("unchecked")
        KernRepo<K, T> repo = (KernRepo<K, T>) META_CACHE.get(domainType);
        return repo.get();
    }

}
