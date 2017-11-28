/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bijhouding.dal.jpa;

import java.util.List;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangBijhoudingsautorisatie;
import nl.bzk.brp.bijhouding.dal.ToegangBijhoudingsautorisatieRepository;
import org.springframework.stereotype.Repository;

/**
 * De JPA implementatie van de {@link ToegangBijhoudingsautorisatieRepository} interface.
 */
@Repository
public final class ToegangBijhoudingsautorisatieRepositoryImpl extends AbstractKernRepository<ToegangBijhoudingsautorisatie, Integer> implements
        ToegangBijhoudingsautorisatieRepository {

    private static final String WHERE_GEAUTORISEERDE_IS = "select t from ToegangBijhoudingsautorisatie t where t.geautoriseerde.partij = :geautoriseerde";

    /**
     * Maakt een nieuw PersoonRepositoryImpl object.
     */
    public ToegangBijhoudingsautorisatieRepositoryImpl() {
        super(ToegangBijhoudingsautorisatie.class);
    }

    @Override
    public List<ToegangBijhoudingsautorisatie> findByGeautoriseerde(final Partij geautoriseerde) {
        return getEntityManager().createQuery(WHERE_GEAUTORISEERDE_IS, ToegangBijhoudingsautorisatie.class)
                .setParameter("geautoriseerde", geautoriseerde)
                .getResultList();
    }
}
