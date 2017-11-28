/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.protocollering.service.algemeen;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ScopePatroon;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ScopePatroonElement;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.brp.protocollering.service.dal.ProtocolleringRepository;
import org.springframework.stereotype.Service;

/**
 * ScopePatroonService implementaties.
 */
@Service
final class ScopePatroonServiceImpl implements ScopePatroonService {

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private Map<Set<Integer>, CachedScopePatroon> cachePatroonMap = Maps.newConcurrentMap();

    @Inject
    private ProtocolleringRepository protocolleringRepository;

    private ScopePatroonServiceImpl() {
    }

    /**
     * Laad scopepatronen in cache.
     */
    @PostConstruct
    public void naConstructie() {
        final List<ScopePatroon> patroonList = protocolleringRepository.getScopePatronen();
        for (final ScopePatroon scopePatroon : patroonList) {
            final CachedScopePatroon cachedScopePatroon = new CachedScopePatroon(scopePatroon);
            cachePatroonMap.put(cachedScopePatroon.getElementAttribuutSet(), cachedScopePatroon);
        }
    }

    @Override
    public ScopePatroon getScopePatroon(final Set<Integer> attribuutSet) {
        final CachedScopePatroon cachedScopePatroon = cachePatroonMap.get(attribuutSet);
        if (cachedScopePatroon != null) {
            LOGGER.debug("ScopePatroon met id {} gevonden voor attributen {}", cachedScopePatroon.getScopePatroon().getId(), attribuutSet);
            return cachedScopePatroon.getScopePatroon();
        }
        final ScopePatroon nieuwScopePatroon = new ScopePatroon();
        for (final Integer elementId : attribuutSet) {
            final ScopePatroonElement scopePatroonElement = new ScopePatroonElement();
            scopePatroonElement.setElementId(elementId);
            scopePatroonElement.setScopePatroon(nieuwScopePatroon);
            nieuwScopePatroon.getScopePatroonElementSet().add(scopePatroonElement);
        }
        protocolleringRepository.opslaanNieuwScopePatroon(nieuwScopePatroon);
        final CachedScopePatroon nieuwCachedScopePatroon = new CachedScopePatroon(nieuwScopePatroon);
        cachePatroonMap.put(nieuwCachedScopePatroon.getElementAttribuutSet(), nieuwCachedScopePatroon);
        LOGGER.debug("Nieuw ScopePatroon aangemaakt met id {} voor attributen {}", nieuwScopePatroon.getId(), attribuutSet);
        return nieuwScopePatroon;
    }
}
