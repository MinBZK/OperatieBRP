/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.serialisatie.notificator.repository.impl;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import nl.bzk.brp.serialisatie.notificator.repository.PersoonIdRepository;
import org.springframework.stereotype.Repository;


/**
 * Implementatie van persoon identifier repository, voor ophalen van persoon identifiers uit database.
 */
@Repository
public class PersoonIdRepositoryImpl implements PersoonIdRepository {

    @PersistenceContext(unitName = "nl.bzk.brp.alleenlezen")
    private EntityManager entityManager;

    @Override
    public final List<Integer> vindAllePersoonIds() {
        final TypedQuery<Integer> tQuery = entityManager.createQuery("SELECT p.id FROM PersoonModel p", Integer.class);
        return tQuery.getResultList();
    }

    @Override
    public final List<Integer> vindPersoonIds(final int vanafId, final int aantalIds) {
        final TypedQuery<Integer> tQuery =
                entityManager.createQuery("SELECT p.id FROM PersoonModel p WHERE p.id >= :vanafId ORDER BY p.id"
                        , Integer.class);
        tQuery.setParameter("vanafId", vanafId);
        tQuery.setMaxResults(aantalIds);

        return tQuery.getResultList();
    }

    @Override
    public final List<Integer> vindBijgehoudenPersoonIds(final Integer uren) {
        final String query = String.format("SELECT afadm.pers FROM kern.his_persafgeleidadministrati afadm "
            + "WHERE afadm.tsreg > (TIMESTAMP 'now' - INTERVAL '%s hours')", uren);

        return entityManager.createNativeQuery(query).getResultList();
    }
}
