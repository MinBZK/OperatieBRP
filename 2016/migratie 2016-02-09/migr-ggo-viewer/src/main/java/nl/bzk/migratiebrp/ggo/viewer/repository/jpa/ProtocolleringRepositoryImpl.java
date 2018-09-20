/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.ggo.viewer.repository.jpa;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import nl.bzk.migratiebrp.ggo.viewer.domein.protocollering.entity.Protocollering;
import nl.bzk.migratiebrp.ggo.viewer.repository.ProtocolleringRepository;
import org.springframework.stereotype.Repository;

/**
 * De JPA implementatie van de ProtocolleringRepository.
 */
@Repository
public final class ProtocolleringRepositoryImpl implements ProtocolleringRepository {

    @PersistenceContext(name = "viewerEntityManagerFactory", unitName = "viewerEntityManagerFactory")
    private EntityManager em;

    /**
     * {@inheritDoc}
     */
    @Override
    public Protocollering save(final Protocollering protocollering) {
        if (protocollering == null) {
            throw new IllegalArgumentException("protocollering mag niet null zijn");
        }
        if (protocollering.getId() == null) {
            em.persist(protocollering);
            return protocollering;
        } else {
            return em.merge(protocollering);
        }
    }

    @Override
    public List<Protocollering> findProtocolleringVoorANummer(final Long aNummer) {
        try {
            return em.createQuery(
                         "select p from Protocollering p where p.administratienummer = :administratienummer ",
                         Protocollering.class)
                     .setParameter("administratienummer", aNummer)
                     .getResultList();
        } catch (final NoResultException nre) {
            return null;
        }
    }
}
