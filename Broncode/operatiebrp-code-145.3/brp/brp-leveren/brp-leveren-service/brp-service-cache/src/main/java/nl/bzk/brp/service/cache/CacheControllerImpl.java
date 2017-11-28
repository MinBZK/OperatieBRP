/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.cache;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;


/**
 * Controller voor het laden van autorisatie- en stamtabel-caches in vereiste volgorde.
 */
@Component
final class CacheControllerImpl implements CacheController {
    private static final Logger LOGGER = LoggerFactory.getLogger();

    @Value("${brp.cache.ververs.tijdinterval.sec:0}")
    private int minTijdsintervalSec;
    @Value("${brp.cache.stamtabelenabled:true}")
    private boolean stamtabelCacheEnabled;

    @Inject
    private PartijCache partijCache;
    @Inject
    private StamTabelCache stamTabelCache;
    @Inject
    private LeveringsAutorisatieCache leveringsAutorisatieCache;
    @Inject
    private GeldigeAttributenElementenCache geldigeAttributenElementenCache;
    @Inject
    private ApplicationContext applicationContext;
    @Inject
    private BrpCache brpCache;

    private Date laatsteCacheVerversTijd;

    private CacheControllerImpl() {

    }

    /**
     * Laadt de cache initieel. NB: methode kan niet final zijn ivm cglib
     */
    @PostConstruct
    @Override
    public void herlaadCaches() {
        if (laatsteCacheVerversTijd == null || ((new Date().getTime() - laatsteCacheVerversTijd.getTime()) > TimeUnit.SECONDS
                .toMillis(minTijdsintervalSec))) {
            LOGGER.debug("start met herladen van de caches");
            final Set<CacheEntry> cacheEntries = new HashSet<>();
            final CacheEntry partijData = partijCache.herlaad();
            cacheEntries.add(partijData);

            if (stamtabelCacheEnabled) {
                final CacheEntry stamtabelData = stamTabelCache.herlaad();
                cacheEntries.add(stamtabelData);
            }
            final CacheEntry levAutData = leveringsAutorisatieCache.herlaad((PartijCacheImpl.Data) partijData.getData());
            cacheEntries.add(levAutData);
            final CacheEntry elementCache = geldigeAttributenElementenCache.herlaad();
            cacheEntries.add(elementCache);
            brpCache.bouwCache(cacheEntries);
            LOGGER.debug("klaar met herladen van de caches");
            applicationContext.publishEvent(new CacheVerversEvent(this));
            laatsteCacheVerversTijd = new Date();
        } else {
            LOGGER.debug("Geen cache ververs uitgevoerd, te kort na laatste verzoek");
        }
    }
}

