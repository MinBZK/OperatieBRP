/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import nl.bzk.brp.dataaccess.ReadOnlyRepository;
import nl.bzk.brp.model.attribuuttype.AangeverAdreshoudingCode;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AangeverAdreshouding;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository voor {@link AangeverAdreshouding}.
 */
public interface AangeverAdreshoudingRepository extends ReadOnlyRepository<AangeverAdreshouding, Short> {

    @Query("SELECT aangAdresh FROM AangeverAdreshouding aangAdresh WHERE aangAdresh.aangeverAdreshoudingCode = :code")
    AangeverAdreshouding vindAangeverAdreshoudingOpCode(@Param("code") final AangeverAdreshoudingCode code);
}
