/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import nl.bzk.brp.dataaccess.ReadOnlyRepository;
import nl.bzk.brp.model.attribuuttype.RedenVerliesNaam;
import nl.bzk.brp.model.attribuuttype.basis.RedenVerliesCodeBasis;
import nl.bzk.brp.model.objecttype.operationeel.statisch.RedenVerliesNLNationaliteit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * TODO Add documentation
 */
public interface RedenVerliesNLNationaliteitRepository extends ReadOnlyRepository<RedenVerliesNLNationaliteit, Short> {

    RedenVerliesNLNationaliteit findOneByNaam(RedenVerliesNaam naam);

    @Query( "SELECT rdn FROM RedenVerliesNLNationaliteit rdn WHERE rdn.naam = :naam")
    RedenVerliesNLNationaliteit vindRedenVerliesNLNationaliteitOpNaam(@Param("naam") final RedenVerliesNaam naam);
}
