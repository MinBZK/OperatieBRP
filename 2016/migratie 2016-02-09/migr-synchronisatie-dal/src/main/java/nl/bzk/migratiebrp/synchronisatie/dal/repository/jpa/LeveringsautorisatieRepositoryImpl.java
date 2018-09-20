/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.Leveringsautorisatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.PartijRol;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.LeveringsautorisatieRepository;

import org.springframework.stereotype.Repository;

/**
 * De implementatie van de Leveringsautorisatie rRepository.
 */
@Repository
public final class LeveringsautorisatieRepositoryImpl implements LeveringsautorisatieRepository {

    private static final String SELECT_BY_PARTIJ_QUERY = "SELECT autorisatie FROM Leveringsautorisatie autorisatie, "
                    + "IN (autorisatie.toegangLeveringsautorisatieSet) toegang "
                    + "WHERE toegang.geautoriseerde = :partijRol";

    @PersistenceContext(name = "syncDalEntityManagerFactory", unitName = "BrpEntities")
    private EntityManager em;

    @Override
    public List<Leveringsautorisatie> findLeveringsautorisatiesVoorPartij(final PartijRol partijRol) {
        final TypedQuery<Leveringsautorisatie> query = em.createQuery(SELECT_BY_PARTIJ_QUERY, Leveringsautorisatie.class);
        query.setParameter("partijRol", partijRol);

        return query.getResultList();
    }

}
