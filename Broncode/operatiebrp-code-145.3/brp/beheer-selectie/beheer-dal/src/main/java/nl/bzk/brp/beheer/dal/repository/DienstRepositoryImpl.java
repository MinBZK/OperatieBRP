/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.dal.repository;

import java.util.Collection;
import javax.inject.Inject;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.brp.beheer.service.dal.DienstRepository;
import org.springframework.stereotype.Component;

/**
 * Implementatie van {@link DienstRepository}.
 */
@Component
class DienstRepositoryImpl implements DienstRepository {

    private final DienstJpaRepository jpaRepository;

    @Inject
    DienstRepositoryImpl(DienstJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Collection<Dienst> getSelectieDienstenBinnenPeriode(Integer beginDatum, Integer eindDatum) {
        return jpaRepository.getSelectieDienstenBinnenPeriode(eindDatum);
    }

    @Override
    public Dienst findDienstById(Integer id) {
        return jpaRepository.findOne(id);
    }
}
