/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.levering;


import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Partij;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.ToegangBijhoudingsautorisatie;
import nl.bzk.brp.service.dalapi.ToegangBijhoudingautorisatieRepository;
import org.springframework.stereotype.Repository;

/**
 * Implementatie van ToegangBijhoudingautorisatieRepository.
 */
@Repository
public class ToegangBijhoudingautorisatieRepositoryImpl implements ToegangBijhoudingautorisatieRepository {

    private static final String WHERE_GEAUTORISEERDE_IS = "select t from ToegangBijhoudingsautorisatie t where t.geautoriseerde.partij = :geautoriseerde";

    @PersistenceContext(unitName = "nl.bzk.brp.master")
    private EntityManager entityManager;


    @Override
    public final List<ToegangBijhoudingsautorisatie> findByGeautoriseerde(final Partij geautoriseerde) {
        return entityManager.createQuery(WHERE_GEAUTORISEERDE_IS, ToegangBijhoudingsautorisatie.class)
                .setParameter("geautoriseerde", geautoriseerde)
                .getResultList();
    }
}
