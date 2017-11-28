/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.dal.repository;

import java.util.Collection;
import javax.persistence.LockModeType;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Selectietaak;
import nl.bzk.brp.beheer.service.dal.SelectieTaakRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Interface met specifieke Spring Data logica voor {@link SelectieTaakRepository}.
 */
@Repository
interface SelectieTaakJpaRepository extends CrudRepository<Selectietaak, Integer> {

    /**
     * Zoek gepersisteerde geldige {@link Selectietaak selectietaken} binnen een periode.
     * @param beginDatum begindatum van de periode
     * @param eindDatum einddatum van de periode
     * @return de lijst met selectietaken
     */
    Collection<Selectietaak> findByDatumPlanningGreaterThanEqualAndDatumPlanningLessThanAndIsActueelEnGeldigTrue(Integer beginDatum, Integer eindDatum);

    /**
     * Zoek een gepersisteerde {@link Selectietaak selectietaak} voor een update.
     * @param id het ID van de taak
     * @return de selectietaak
     */
    @Query("select t from Selectietaak t where id= :id")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Selectietaak findOneForUpdate(@Param("id") Integer id);
}
