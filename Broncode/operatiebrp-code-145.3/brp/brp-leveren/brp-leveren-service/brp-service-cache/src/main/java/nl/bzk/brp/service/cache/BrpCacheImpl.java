/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Component;

/**
 * BrpCache.
 */
@Component
public final class BrpCacheImpl implements BrpCache {

    private Map<String, Object> cacheMap = new HashMap<>();

    @Override
    public void bouwCache(final Set<CacheEntry> cacheEntries) {
        final Map<String, Object> nieuweCacheMap = new HashMap<>(cacheEntries.size(), 1);
        for (CacheEntry cacheEntry : cacheEntries) {
            nieuweCacheMap.put(cacheEntry.getCacheNaam(), cacheEntry.getData());
        }
        cacheMap = nieuweCacheMap;
    }

    @Override
    public Object getCache(final String cacheNaam) {
        return cacheMap.get(cacheNaam);
    }
}
