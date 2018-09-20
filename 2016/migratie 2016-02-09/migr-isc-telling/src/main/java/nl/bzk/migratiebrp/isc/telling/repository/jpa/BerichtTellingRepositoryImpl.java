/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.telling.repository.jpa;

import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import nl.bzk.migratiebrp.isc.telling.entiteit.BerichtTelling;
import nl.bzk.migratiebrp.isc.telling.repository.BerichtTellingRepository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementatieklasse voor de Bericht Telling Repository.
 */
@Repository
public final class BerichtTellingRepositoryImpl implements BerichtTellingRepository {

    @PersistenceContext(name = "tellingEntityManagerFactory", unitName = "TellingEntities")
    private EntityManager em;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(value = "tellingTransactionManager", propagation = Propagation.REQUIRES_NEW)
    public BerichtTelling save(final BerichtTelling berichtTelling) {
        if (berichtTelling.getBerichtType() == null) {
            em.persist(berichtTelling);
            return berichtTelling;
        } else {
            return em.merge(berichtTelling);
        }
    }

    @Override
    @Transactional(value = "tellingTransactionManager", propagation = Propagation.REQUIRES_NEW)
    public BerichtTelling haalBerichtTellingOp(final String berichtType, final String kanaal, final Timestamp datum) {
        final Query selectQuery =
                em.createQuery("SELECT bt FROM BerichtTelling bt WHERE bt.berichtType = :berichtType " + "AND bt.datum = :datum AND bt.kanaal = :kanaal");
        selectQuery.setParameter("berichtType", berichtType);
        selectQuery.setParameter("kanaal", kanaal);
        selectQuery.setParameter("datum", datum, TemporalType.DATE);

        try {
            return (BerichtTelling) selectQuery.getSingleResult();
        } catch (final NoResultException exception) {
            return null;
        }
    }
}
