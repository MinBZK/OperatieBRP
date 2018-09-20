/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.pocmotor.dal.operationeel;

import nl.bzk.brp.pocmotor.dal.jpa.BetrokkenheidOGMRepositoryCustom;
import nl.bzk.brp.pocmotor.model.gedeeld.usr.enums.SoortBetrokkenheid;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Betrokkenheid;
import nl.bzk.brp.pocmotor.model.operationeel.usr.tabel.Persoon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BetrokkenheidOGMRepository extends JpaRepository<Betrokkenheid, Long>, BetrokkenheidOGMRepositoryCustom {

    Betrokkenheid findByRolAndBetrokkene(final SoortBetrokkenheid rol, final Persoon betrokkene);

}
