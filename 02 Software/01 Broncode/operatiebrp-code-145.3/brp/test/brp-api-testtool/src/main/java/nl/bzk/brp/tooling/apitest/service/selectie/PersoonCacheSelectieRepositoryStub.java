/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.selectie;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.PersoonCache;
import nl.bzk.brp.service.dalapi.PersoonCacheRepository;
import nl.bzk.brp.service.selectie.lezer.MinMaxPersoonCacheDTO;
import nl.bzk.brp.service.selectie.lezer.PersoonCacheSelectieRepository;
import nl.bzk.brp.tooling.apitest.service.dataaccess.PersoonDataStubService;
import org.springframework.stereotype.Service;

/**
 * Stub implementatie voor {@link PersoonCacheSelectieRepository}
 */
@Service
final class PersoonCacheSelectieRepositoryStub implements PersoonCacheSelectieRepository, SelectieAPIService.BulkMode {

    @Inject
    private PersoonDataStubService persoonDataStubService;
    @Inject
    private PersoonCacheRepository persoonCacheRepository;

    private Integer bulkMaxId;

    @Override
    public List<PersoonCache> haalPersoonCachesOp(final long minId, final long maxId) {
        if (bulkMaxId != null) {
            final int max = (int) Math.min(maxId, bulkMaxId);
            List<PersoonCache> list = Lists.newArrayListWithCapacity((int) (max - minId));
            final PersoonCache persoonCache = persoonCacheRepository.haalPersoonCacheOp(persoonDataStubService.geefAllePersoonIds().iterator().next());
            for (long i = minId; i < max; i++) {
                list.add(persoonCache);
            }
            return list;
        } else {
            final Set<Long> persoonIds = persoonDataStubService.geefAllePersoonIds();
            final ArrayList<Long> idsList = Lists.newArrayList(persoonIds);
            Collections.sort(idsList);
            return idsList.stream().filter(persId -> persId >= minId && persId < maxId)
                    .map(persoonCacheRepository::haalPersoonCacheOp).collect(Collectors.toList());
        }
    }

    @Override
    public MinMaxPersoonCacheDTO selecteerMinMaxIdVoorSelectie() {
        if (bulkMaxId != null) {
            final MinMaxPersoonCacheDTO dto = new MinMaxPersoonCacheDTO();
            dto.setMinId(0);
            dto.setMaxId(bulkMaxId);
            return dto;
        } else {
            final Set<Long> persoonIds = persoonDataStubService.geefAllePersoonIds();
            final List<Long> sortedPersoonIds = Lists.newArrayList(persoonIds).stream().sorted().collect(Collectors.toList());
            final MinMaxPersoonCacheDTO minMaxPersoonCacheDTO = new MinMaxPersoonCacheDTO();
            minMaxPersoonCacheDTO.setMinId(sortedPersoonIds.get(0).intValue());
            minMaxPersoonCacheDTO.setMaxId(sortedPersoonIds.get(sortedPersoonIds.size() - 1).intValue());
            return minMaxPersoonCacheDTO;
        }
    }

    @Override
    public void reset() {
        bulkMaxId = null;
    }

    @Override
    public void activeerBulkModus(final int maxId) {
        this.bulkMaxId = maxId;
    }
}
