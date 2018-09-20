/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.beheer.webapp.repository.kern;

import java.util.List;
import nl.bzk.brp.beheer.webapp.configuratie.annotations.Kern;
import nl.bzk.brp.beheer.webapp.configuratie.jpa.CustomMaxedJpaRepository;
import nl.bzk.brp.beheer.webapp.repository.ReadonlyRepository;
import nl.bzk.brp.model.hisvolledig.impl.kern.AdministratieveHandelingHisVolledigImpl;
import org.springframework.data.jpa.repository.Query;

/**
 *
 */
@Kern
public interface AdministratieveHandelingHisVolledigImplRepository extends ReadonlyRepository<AdministratieveHandelingHisVolledigImpl, Long>,
        CustomMaxedJpaRepository
{

    /**
     * Vind de administratieve handelingen behorende bij een persoon.
     *
     * @param id Technisch Id van een persoon
     * @return lijst van administratieve handelingen met betrekking op gevraagde persoon
     */
    @Query("select h from AdministratieveHandelingHisVolledigImpl h, HisPersoonAfgeleidAdministratiefModel afg "
           + "where h.id = afg.administratieveHandeling.id AND afg.persoon.id = ?1")
    List<AdministratieveHandelingHisVolledigImpl> findByPersoonTechnischId(Integer id);
}
