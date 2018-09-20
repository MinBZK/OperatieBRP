/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.dataaccess.repository.jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import nl.bzk.brp.dataaccess.repository.ToegangBijhoudingsautorisatieRepository;
import nl.bzk.brp.model.algemeen.stamgegeven.autaut.ToegangBijhoudingsautorisatie;
import org.springframework.stereotype.Repository;


/**
 * Toegang- bijhoudingsautorisatie repository.
 */
@Repository
public final class ToegangBijhoudingsautorisatieRepositoryImpl implements ToegangBijhoudingsautorisatieRepository {

    @PersistenceContext(unitName = "nl.bzk.brp.alleenlezen")
    private EntityManager entityManager;

    @Override
    public List<ToegangBijhoudingsautorisatie> geefToegangBijhoudingsautorisatie(final Integer partijcode) {
        final TypedQuery<ToegangBijhoudingsautorisatie> typedQuery =
            entityManager.createQuery("SELECT tb FROM ToegangBijhoudingsautorisatie tb WHERE tb.geautoriseerde.partij.code.waarde = :partijcode",
                ToegangBijhoudingsautorisatie.class);
        typedQuery.setParameter("partijcode", partijcode);
        return typedQuery.getResultList();
    }
}
