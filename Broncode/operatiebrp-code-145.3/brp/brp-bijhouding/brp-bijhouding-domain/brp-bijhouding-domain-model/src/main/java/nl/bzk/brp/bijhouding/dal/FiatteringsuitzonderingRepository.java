/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.dal;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BijhouderFiatteringsuitzondering;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.repositories.BasisRepository;

/**
 * CRUD functionaliteit voor de {@link BijhouderFiatteringsuitzondering} entity.
 */
public interface FiatteringsuitzonderingRepository extends BasisRepository<BijhouderFiatteringsuitzondering, Integer> {


    /**
     * Zoek een BijhouderFiatteringsuitzondering a.d.h.v. de gegeven {@link Partij}.
     * @param bijhouder bijhouder Partij
     * @return de BijhouderFiatteringsuitzondering
     */
    List<BijhouderFiatteringsuitzondering> findBijhouderFiatteringsuitzonderingen(Partij bijhouder);
}
