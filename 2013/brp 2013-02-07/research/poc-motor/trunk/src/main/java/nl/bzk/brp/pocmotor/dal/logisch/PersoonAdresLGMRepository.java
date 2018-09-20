/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.dal.logisch;

import nl.bzk.brp.pocmotor.dal.jpa.PersoonAdresLGMRepositoryCustom;
import nl.bzk.brp.pocmotor.model.logisch.usr.objecttype.PersoonAdres;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersoonAdresLGMRepository extends JpaRepository<PersoonAdres, Long>, PersoonAdresLGMRepositoryCustom {
    
}
