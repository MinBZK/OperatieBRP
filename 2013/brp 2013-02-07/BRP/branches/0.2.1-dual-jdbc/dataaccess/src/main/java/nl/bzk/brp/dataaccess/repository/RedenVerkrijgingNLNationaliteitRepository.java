/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import nl.bzk.brp.dataaccess.ReadOnlyRepository;
import nl.bzk.brp.model.attribuuttype.RedenVerkrijgingCode;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenVerkrijgingNLNationaliteit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * TODO Add documentation
 */
public interface RedenVerkrijgingNLNationaliteitRepository extends
        ReadOnlyRepository<RedenVerkrijgingNLNationaliteit, Short>
{

    RedenVerkrijgingNLNationaliteit findOneByCode(RedenVerkrijgingCode code);

    @Query("SELECT rdn FROM RedenVerkrijgingNLNationaliteit rdn WHERE rdn.code = :code ")
    RedenVerkrijgingNLNationaliteit vindRedenVerkregenNlNationaliteitOpCode(@Param("code") final RedenVerkrijgingCode code);
}
