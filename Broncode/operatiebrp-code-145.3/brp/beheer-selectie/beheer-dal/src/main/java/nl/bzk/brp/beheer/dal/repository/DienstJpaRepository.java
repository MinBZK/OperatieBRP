/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.dal.repository;

import java.util.Collection;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Dienst;
import nl.bzk.brp.beheer.service.dal.DienstRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Interface met specifieke Spring Data logica voor {@link DienstRepository}.
 */
@Repository
interface DienstJpaRepository extends CrudRepository<Dienst, Integer> {

    /**
     * Geef de selectiediensten met een eerste selectiedatum voor een bepaalde einddatum.
     * @param eindDatum de einddatum
     * @return de selectiediensten
     */
    @Query("select d from Dienst d where d.isActueelEnGeldig = true and d.isActueelEnGeldigVoorSelectie = true and d.soortDienstId = 12 "
            + "and (d.indicatieGeblokkeerd is null or d.indicatieGeblokkeerd = false)"
            + "and d.eersteSelectieDatum < :eindDatum")
    Collection<Dienst> getSelectieDienstenBinnenPeriode(@Param("eindDatum") Integer eindDatum);

}
