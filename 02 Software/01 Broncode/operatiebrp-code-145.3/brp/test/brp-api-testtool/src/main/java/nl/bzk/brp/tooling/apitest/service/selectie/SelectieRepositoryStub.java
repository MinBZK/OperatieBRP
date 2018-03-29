/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.selectie;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectierun;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectietaak;
import nl.bzk.algemeenbrp.dal.domein.brp.enums.SelectietaakStatus;
import nl.bzk.brp.service.dalapi.SelectieRepository;
import org.springframework.stereotype.Component;

/**
 * SelectieRepositoryStub.
 */
@Component
class SelectieRepositoryStub implements SelectieRepository, SelectieAPIService.TaakBeheer {

    private List<Selectietaak> taken = Lists.newArrayList();
    private Map<Integer, Selectietaak> takenMap = new HashMap<>();


    @Override
    public List<Selectietaak> getTakenGeplandVoorVandaag() {
        return taken;
    }

    @Override
    public Selectietaak haalSelectietaakOp(int selectietaakId) {
        return takenMap.get(selectietaakId);
    }

    @Override
    public void slaSelectieOp(Selectierun selectierun) {
        //entity moet id krijgen
        selectierun.setId(1);
    }

    @Override
    public void werkSelectieBij(Selectierun selectierun) {
        //niet nodig
    }

    @Override
    public void slaSelectietaakOp(final Selectietaak selectietaak) {
        //niet nodig
    }

    @Override
    public List<Selectietaak> getSelectietakenMetStatusTeProtocolleren() {
        return taken.stream().filter(selectietaak -> selectietaak.getStatus() == SelectietaakStatus.TE_PROTOCOLLEREN.getId())
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Selectietaak> getTaken() {
        return taken;
    }

    @Override
    public void setTaken(final Collection<Selectietaak> nieuwTaken) {
        taken.clear();
        takenMap.clear();
        //entity moet id krijgen
        Selectietaak taak = Collections.max(nieuwTaken, Comparator.comparingInt(Selectietaak::getId));
        int id = taak.getId() != null ? taak.getId() + 1 : 1;
        for (Selectietaak selectietaak : nieuwTaken) {
            if (selectietaak.getId() == null) {
                selectietaak.setId(id);
                id++;
            }
            takenMap.put(selectietaak.getId(), selectietaak);
        }

        taken.addAll(nieuwTaken);
    }

    @Override
    public Selectietaak getTaak(Integer id) {
        return takenMap.get(id);
    }
}
