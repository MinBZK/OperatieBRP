/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.service.selectie;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SelectietaakStatus;
import org.springframework.stereotype.Service;

/**
 * Implementatie van {@link OverlappendeSelectieTaakFilterService}.
 */
@Service
final class OverlappendeSelectieTaakFilterServiceImpl implements OverlappendeSelectieTaakFilterService {

    @Override
    public Collection<SelectieTaakDTO> filter(Collection<SelectieTaakDTO> berekendeTaakDtos,
                                              Collection<SelectieTaakDTO> gepersisteerdeTaakDtos) {
        List<SelectieTaakDTO> teVerwijderenBerekendeTaken = Lists.newArrayList();
        for (SelectieTaakDTO gepersisteerdeTaakDto : gepersisteerdeTaakDtos) {
            if (SelectieTaakServiceUtil.STATUS_FOUTIEF.contains(SelectietaakStatus.parseId(gepersisteerdeTaakDto.getStatus().intValue()))) {
                // We kunnen geen taken dupliceren vanwege de constraint op leveringsautorisatieId, dienstId en volgnummer.
            }
            for (SelectieTaakDTO berekendeTaakDto : berekendeTaakDtos) {
                boolean overlapt = berekendeTaakDto.getDienstId().equals(gepersisteerdeTaakDto.getDienstId());
                overlapt = overlapt && berekendeTaakDto.getToegangLeveringsautorisatieId().equals(gepersisteerdeTaakDto.getToegangLeveringsautorisatieId());
                if (overlapt && berekendeTaakDto.getVolgnummer().equals(gepersisteerdeTaakDto.getVolgnummer())) {
                    teVerwijderenBerekendeTaken.add(berekendeTaakDto);
                }
            }
        }
        List<SelectieTaakDTO> resultaat = Lists.newArrayList();
        resultaat.addAll(berekendeTaakDtos);
        resultaat.removeAll(teVerwijderenBerekendeTaken);
        resultaat.addAll(gepersisteerdeTaakDtos);
        return resultaat;
    }
}
