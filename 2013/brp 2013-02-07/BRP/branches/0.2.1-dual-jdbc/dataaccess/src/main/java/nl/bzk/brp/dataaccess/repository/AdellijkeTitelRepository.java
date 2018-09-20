/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository;

import nl.bzk.brp.dataaccess.ReadOnlyRepository;
import nl.bzk.brp.model.attribuuttype.AdellijkeTitelCode;
import nl.bzk.brp.model.objecttype.operationeel.statisch.AdellijkeTitel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository voor {@link AdellijkeTitel}.
 */
public interface AdellijkeTitelRepository extends ReadOnlyRepository<AdellijkeTitel, Short> {

    @Query("SELECT adellijkeTitel FROM AdellijkeTitel adellijkeTitel WHERE adellijkeTitel.adellijkeTitelCode = :code")
    AdellijkeTitel vindAdellijkeTitelOpCode(@Param("code") final AdellijkeTitelCode code);
}
