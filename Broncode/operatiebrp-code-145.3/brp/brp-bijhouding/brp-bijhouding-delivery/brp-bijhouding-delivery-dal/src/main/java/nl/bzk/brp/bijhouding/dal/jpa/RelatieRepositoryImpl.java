/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.dal.jpa;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Relatie;
import nl.bzk.brp.bijhouding.dal.RelatieRepository;
import org.springframework.stereotype.Repository;

/**
 * De JPA implementatie van de {@link RelatieRepository} interface.
 */
@Repository
public final class RelatieRepositoryImpl extends AbstractKernRepository<Relatie, Long> implements RelatieRepository {

    /**
     * Maakt een nieuw RelatieRepositoryImpl object.
     */
    protected RelatieRepositoryImpl() {
        super(Relatie.class);
    }
}
