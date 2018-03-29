/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.dal.jpa;

import nl.bzk.algemeenbrp.dal.domein.brp.entity.Betrokkenheid;
import nl.bzk.brp.bijhouding.dal.BetrokkenheidRepository;
import org.springframework.stereotype.Repository;

/**
 * De JPA implementatie van de {@link BetrokkenheidRepository} interface.
 */
@Repository
public final class BetrokkenheidRepositoryImpl extends AbstractKernRepository<Betrokkenheid, Long> implements BetrokkenheidRepository {

    /**
     * Maakt een nieuw BetrokkenheidRepositoryImpl object.
     */
    protected BetrokkenheidRepositoryImpl() {
        super(Betrokkenheid.class);
    }
}
