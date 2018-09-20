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
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.autaut.entity.PersoonAfnemerindicatie;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.brp.kern.entity.Persoon;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.AfnemersindicatieRepository;
import org.springframework.stereotype.Repository;

/**
 * DAO (Gegevens toegangs punt) voor alles omtrent BRP afnemersindicaties.
 */
@Repository
public final class AfnemersindicatieRepositoryImpl implements AfnemersindicatieRepository {

    private static final String SELECT_BY_PERSOON_QUERY = "SELECT pa FROM PersoonAfnemerindicatie pa WHERE pa.persoon = :persoon";
    @PersistenceContext(name = "syncDalEntityManagerFactory", unitName = "BrpEntities")
    private EntityManager em;

    @Override
    public List<PersoonAfnemerindicatie> findByPersoon(final Persoon persoon) {
        final TypedQuery<PersoonAfnemerindicatie> query = em.createQuery(SELECT_BY_PERSOON_QUERY, PersoonAfnemerindicatie.class);
        query.setParameter("persoon", persoon);

        return query.getResultList();
    }

    @Override
    public PersoonAfnemerindicatie save(final PersoonAfnemerindicatie afnemerindicatie) {
        if (afnemerindicatie.getId() == null) {
            em.persist(afnemerindicatie);
            return afnemerindicatie;
        } else {
            return em.merge(afnemerindicatie);
        }
    }
}
