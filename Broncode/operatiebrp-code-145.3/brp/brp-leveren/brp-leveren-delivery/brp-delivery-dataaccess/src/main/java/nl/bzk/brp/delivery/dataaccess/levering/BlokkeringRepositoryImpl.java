/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at www.github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.delivery.dataaccess.levering;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl.bzk.algemeenbrp.dal.domein.brp.entity.Blokkering;
import nl.bzk.brp.service.dalapi.BlokkeringRepository;
import org.springframework.stereotype.Repository;

/**
 * Implementatie van {@link BlokkeringRepository}
 */
@Repository
public final class BlokkeringRepositoryImpl implements BlokkeringRepository {

    @PersistenceContext(unitName = "nl.bzk.brp.master")
    private EntityManager em;

    private BlokkeringRepositoryImpl() {
    }

    @Override
    public List<Blokkering> getAlleBlokkeringen() {
        return em.createQuery("select blokkering from Blokkering blokkering", Blokkering.class).getResultList();
    }

    @Override
    public Blokkering getBlokkeringOpANummer(final String aNummer) {
        List<Blokkering> gevondenBlokkeringen = em.createQuery("select blokkering from Blokkering blokkering "
                        + "where anr = :aNummer",
                Blokkering.class)
                .setParameter("aNummer", aNummer)
                .getResultList();
        if (gevondenBlokkeringen.isEmpty()) {
            return null;
        }
        return gevondenBlokkeringen.get(0);
    }

}
