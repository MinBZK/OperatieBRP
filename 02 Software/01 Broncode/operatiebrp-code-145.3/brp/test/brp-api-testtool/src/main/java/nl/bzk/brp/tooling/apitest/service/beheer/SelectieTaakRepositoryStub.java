/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.tooling.apitest.service.beheer;

import com.google.common.collect.Lists;
import java.time.LocalDate;
import java.util.Collection;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectietaak;
import nl.bzk.brp.beheer.service.dal.SelectieTaakRepository;
import nl.bzk.brp.tooling.apitest.service.basis.Stateful;
import org.springframework.stereotype.Component;

/**
 * Stub voor {@link SelectieTaakRepository}.
 */
@Component
final class SelectieTaakRepositoryStub implements SelectieTaakRepository, Stateful {
    private Collection<Selectietaak> taken = Lists.newArrayList();

    @Override
    public Collection<Selectietaak> getGeldigeSelectieTakenBinnenPeriode(LocalDate beginDatum, LocalDate eindDatum) {
        // Filteren?
        return taken;
    }

    @Override
    public Selectietaak slaOp(Selectietaak selectietaak) {
        return selectietaak;
    }

    @Override
    public Selectietaak vindSelectietaak(Integer id) {
        return null;
    }

    void setSelectietaken(Collection<Selectietaak> taken) {
        this.taken = taken;
    }

    @Override
    public void reset() {
        this.taken = Lists.newArrayList();
    }
}
