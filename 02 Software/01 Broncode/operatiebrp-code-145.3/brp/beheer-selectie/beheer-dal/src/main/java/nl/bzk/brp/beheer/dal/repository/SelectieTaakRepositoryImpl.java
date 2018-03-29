/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.dal.repository;

import java.time.LocalDate;
import java.util.Collection;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectietaak;
import nl.bzk.algemeenbrp.util.common.DatumUtil;
import nl.bzk.brp.beheer.service.dal.SelectieTaakRepository;
import org.springframework.stereotype.Component;

/**
 * Implementatie van {@link SelectieTaakRepository}.
 */
@Component
class SelectieTaakRepositoryImpl implements SelectieTaakRepository {

    private final SelectieTaakJpaRepository repository;

    @Inject
    SelectieTaakRepositoryImpl(SelectieTaakJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Collection<Selectietaak> getGeldigeSelectieTakenBinnenPeriode(LocalDate beginDatum, LocalDate eindDatum) {
        return repository.findByDatumPlanningGreaterThanEqualAndDatumPlanningLessThanAndIsActueelEnGeldigTrue(DatumUtil.vanDatumNaarInteger(beginDatum),
                DatumUtil.vanDatumNaarInteger(eindDatum));
    }

    @Override
    public Selectietaak slaOp(Selectietaak selectietaak) {
        return repository.save(selectietaak);
    }

    @Override
    public Selectietaak vindSelectietaak(Integer id) {
        return repository.findOneForUpdate(id);
    }
}
