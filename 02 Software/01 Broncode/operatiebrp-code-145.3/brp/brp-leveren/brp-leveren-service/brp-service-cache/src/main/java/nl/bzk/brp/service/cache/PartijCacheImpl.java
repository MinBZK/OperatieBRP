/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.cache;

import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PartijRol;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.service.dalapi.PartijRepository;
import org.springframework.stereotype.Component;

/**
 * PartijCache implementatie.  {@link  PartijCache}.
 */
@Component
final class PartijCacheImpl implements PartijCache {

    /**
     * partij cache naam.
     */
    public static final String CACHE_NAAM = "PARTIJ_CACHE";

    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Inject
    private PartijRepository partijRepository;

    @Inject
    private BrpCache brpCache;

    /**
     * PartijCacheImpl.
     */
    private PartijCacheImpl() {
    }

    @Override
    public CacheEntry herlaad() {
        return herlaadImpl();
    }

    @Override
    public Partij geefPartij(final String code) {
        return getCache().partijCodeMap.get(code);
    }

    @Override
    public Partij geefPartijMetOin(final String oin) {
        return getCache().partijOinMap.get(oin);
    }

    @Override
    public Partij geefPartijMetId(final short id) {
        return getCache().partijIdMap.get(id);
    }

    @Override
    public PartijRol geefPartijRol(final int partijRolId) {
        return getCache().partijRolMap.get(partijRolId);
    }

    /**
     * Herlaad de partij tabel.
     */
    private CacheEntry herlaadImpl() {
        LOGGER.debug("Start herladen cache");
        final List<Partij> allePartijen = partijRepository.get();
        final Data data = new Data(allePartijen);
        LOGGER.debug("Einde herladen cache");
        return new CacheEntry(CACHE_NAAM, data);
    }

    private Data getCache() {
        return (Data) brpCache.getCache(CACHE_NAAM);
    }

    /**
     * Data. Data holder for swap in.
     */
    static class Data {
        private final Map<String, Partij> partijCodeMap;
        private final Map<Short, Partij> partijIdMap;
        private final Map<String, Partij> partijOinMap;
        private final Map<Integer, PartijRol> partijRolMap;

        /**
         * Data constructor.
         * @param allePartijen allePartijen
         */
        Data(final List<Partij> allePartijen) {
            final Map<String, Partij> partijCodeMapTemp = Maps.newHashMap();
            final Map<Short, Partij> partijIdMapTemp = Maps.newHashMap();
            final Map<String, Partij> partijOinMapTemp = Maps.newHashMap();
            final Map<Integer, PartijRol> partijRolMapTemp = Maps.newHashMap();

            for (final Partij partij : allePartijen) {
                partijCodeMapTemp.put(partij.getCode(), partij);
                partijIdMapTemp.put(partij.getId(), partij);
                if (partij.getOin() != null) {
                    partijOinMapTemp.put(partij.getOin(), partij);
                }

                final Set<PartijRol> partijRollen = partij.getPartijRolSet();
                for (PartijRol partijRol : partijRollen) {
                    partijRolMapTemp.put(partijRol.getId(), partijRol);
                }
            }
            this.partijCodeMap = Collections.unmodifiableMap(partijCodeMapTemp);
            this.partijIdMap = Collections.unmodifiableMap(partijIdMapTemp);
            this.partijOinMap = Collections.unmodifiableMap(partijOinMapTemp);
            this.partijRolMap = Collections.unmodifiableMap(partijRolMapTemp);

        }

        /**
         * @return partijRolMap
         */
        public Map<Integer, PartijRol> getPartijRolMap() {
            return partijRolMap;
        }

        /**
         * @return partijIdMap
         */
        public Map<Short, Partij> getPartijIdMap() {
            return partijIdMap;
        }
    }
}
