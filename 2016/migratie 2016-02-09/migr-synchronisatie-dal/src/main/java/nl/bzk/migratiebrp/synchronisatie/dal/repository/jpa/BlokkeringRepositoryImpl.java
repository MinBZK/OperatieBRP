/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.synchronisatie.dal.repository.jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.migratiebrp.synchronisatie.dal.domein.blokkering.entity.Blokkering;
import nl.bzk.migratiebrp.synchronisatie.dal.repository.BlokkeringRepository;
import org.springframework.stereotype.Repository;

/**
 * Data-access-punt voor alles omtrent blokkeringen.
 * 
 */
@Repository
public final class BlokkeringRepositoryImpl implements BlokkeringRepository {

    private static final String NULLPOINTER_MELDING = "Blokkering mag niet null zijn.";
    private static final String NULLPOINTER_MELDING_A_NUMMER = "A-nummer mag niet null zijn.";
    private static final String SELECT_DEEL = "SELECT b FROM Blokkering b ";

    @PersistenceContext(name = "syncDalEntityManagerFactory", unitName = "BrpEntities")
    private EntityManager em;

    @Override
    public Blokkering blokkeerPersoonslijst(final Blokkering blokkering) {
        if (blokkering == null) {
            throw new NullPointerException(NULLPOINTER_MELDING);
        }

        if (blokkering.getId() == null) {
            em.persist(blokkering);
            return blokkering;
        } else {
            return em.merge(blokkering);
        }
    }

    @Override
    public Blokkering statusBlokkering(final Long aNummer) {

        if (aNummer == null) {
            throw new NullPointerException(NULLPOINTER_MELDING_A_NUMMER);
        }

        final List<Blokkering> resultList =
                em.createQuery(SELECT_DEEL + " WHERE b.aNummer = :aNummer", Blokkering.class)
                  .setMaxResults(1)
                  .setParameter("aNummer", aNummer)
                  .getResultList();
        if (resultList.isEmpty()) {
            return null;
        } else {
            return resultList.get(0);
        }

    }

    @Override
    public void deblokkeerPersoonslijst(final Blokkering teVerwijderenBlokkering) {

        if (teVerwijderenBlokkering == null) {
            throw new NullPointerException(NULLPOINTER_MELDING);
        }

        em.remove(teVerwijderenBlokkering);
    }

}
