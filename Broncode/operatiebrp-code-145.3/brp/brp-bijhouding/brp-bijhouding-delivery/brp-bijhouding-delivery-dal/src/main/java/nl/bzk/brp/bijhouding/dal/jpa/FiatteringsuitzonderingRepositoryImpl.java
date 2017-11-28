/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.dal.jpa;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.BijhouderFiatteringsuitzondering;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.brp.bijhouding.dal.FiatteringsuitzonderingRepository;
import org.springframework.stereotype.Repository;

/**
 * De JPA implementatie van de {@link FiatteringsuitzonderingRepository} interface.
 */
@Repository
public final class FiatteringsuitzonderingRepositoryImpl extends AbstractKernRepository<BijhouderFiatteringsuitzondering, Integer> implements
        FiatteringsuitzonderingRepository {
    private static final String WHERE_BIJHOUDER_IS = "select b from BijhouderFiatteringsuitzondering b where b.bijhouder.partij = :bijhouder";

    /**
     * Maakt een nieuw FiatteringsuitzonderingRepositoryImpl object.
     */
    public FiatteringsuitzonderingRepositoryImpl() {
        super(BijhouderFiatteringsuitzondering.class);
    }

    @Override
    public List<BijhouderFiatteringsuitzondering> findBijhouderFiatteringsuitzonderingen(final Partij bijhouder) {
        return getEntityManager().createQuery(WHERE_BIJHOUDER_IS, BijhouderFiatteringsuitzondering.class)
                .setParameter("bijhouder", bijhouder)
                .getResultList();
    }
}
