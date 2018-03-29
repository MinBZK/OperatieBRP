/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.cache;

import com.google.common.collect.Sets;
import org.junit.Assert;
import org.junit.Test;

/**
 * BrpCacheImplTest.
 */
public class BrpCacheImplTest {

    @Test
    public void testBouwCache() {
        final BrpCache brpCache = new BrpCacheImpl();
        final String cacheNaam = "cache1";
        final String data = "data";
        final CacheEntry cacheEntry = new CacheEntry(cacheNaam, data);
        brpCache.bouwCache(Sets.newHashSet(cacheEntry));
        final Object cacheData = brpCache.getCache(cacheNaam);
        Assert.assertEquals(data, cacheData);

    }
}
