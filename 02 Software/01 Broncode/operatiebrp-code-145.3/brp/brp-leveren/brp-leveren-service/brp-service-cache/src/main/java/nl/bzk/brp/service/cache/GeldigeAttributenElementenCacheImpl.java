/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.service.cache;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.domain.algemeen.ElementUtil;
import nl.bzk.brp.domain.element.AttribuutElement;
import nl.bzk.brp.domain.element.ElementHelper;
import org.springframework.stereotype.Component;

/**
 * GeldigeElementenCacheImpl.
 */
@Component
final class GeldigeAttributenElementenCacheImpl implements GeldigeAttributenElementenCache {

    /**
     * cache naam.
     */
    public static final String CACHE_NAAM = "ELEMENT_CACHE";

    private static final Logger LOGGER = LoggerFactory.getLogger();


    @Inject
    private BrpCache brpCache;

    private GeldigeAttributenElementenCacheImpl() {

    }

    @Override
    public CacheEntry herlaad() {
        LOGGER.debug("Start herladen cache");
        final Data data = new Data();
        LOGGER.debug("Einde herladen cache");
        LOGGER.debug("Einde publiceer event voor cache verversing");
        return new CacheEntry(CACHE_NAAM, data);
    }


    @Override
    public boolean geldigVoorAttribuutAutorisatie(final AttribuutElement attribuutElement) {
        return getCache().geldigeElementenVoorAttribuutAutorisatieMap.get(attribuutElement);
    }

    @Override
    public boolean geldigVoorGroepAutorisatie(final AttribuutElement attribuutElement) {
        return getCache().geldigeElementenVoorGroepAutorisatieMap.get(attribuutElement);
    }

    private Data getCache() {
        return (Data) this.brpCache.getCache(CACHE_NAAM);
    }

    /**
     * Data. Data holder for swap in
     */
    static final class Data {
        private final Map<AttribuutElement, Boolean> geldigeElementenVoorAttribuutAutorisatieMap;
        private final Map<AttribuutElement, Boolean> geldigeElementenVoorGroepAutorisatieMap;

        private Data() {
            final Integer vandaag = DatumUtil.vandaag();
            final Map<AttribuutElement, Boolean> geldigeElementenMapTemp = new HashMap<>();
            final Map<AttribuutElement, Boolean> geldigeGroepElementenMapTemp = new HashMap<>();
            for (AttribuutElement element : ElementHelper.getAttributen()) {
                final Boolean geldigAttribuutAutorisatie = ElementUtil.isElementGeldigVoorAttribuutAutorisatie(element, vandaag);
                final Boolean geldigGroepAutorisatie = ElementUtil.isElementGeldigVoorGroepAutorisatie(element, vandaag);
                geldigeElementenMapTemp.put(element, geldigAttribuutAutorisatie);
                geldigeGroepElementenMapTemp.put(element, geldigGroepAutorisatie);
            }
            geldigeElementenVoorAttribuutAutorisatieMap = geldigeElementenMapTemp;
            geldigeElementenVoorGroepAutorisatieMap = geldigeGroepElementenMapTemp;
        }
    }
}
