/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.dal.operationeel;

import java.util.List;

import nl.bzk.brp.pocmotor.dal.jpa.PersoonGeslachtsnaamComponentOGMRepositoryCustom;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.attribuuttype.Burgerservicenummer;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.PersoonGeslachtsnaamcomponent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersoonGeslachtsnaamComponentOGMRepository extends JpaRepository<PersoonGeslachtsnaamcomponent, Long>,
                                                                    PersoonGeslachtsnaamComponentOGMRepositoryCustom {

    List<PersoonGeslachtsnaamcomponent> findByPersoonBurgerservicenummer(final Burgerservicenummer bsn);
    
}
