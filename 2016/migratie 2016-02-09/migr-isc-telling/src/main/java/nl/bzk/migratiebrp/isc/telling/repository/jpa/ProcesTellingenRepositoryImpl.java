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

import nl.bzk.migratiebrp.isc.telling.entiteit.ProcesTelling;
import nl.bzk.migratiebrp.isc.telling.repository.ProcesTellingenRepository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementatieklasse voor de Proces Tellingen Repository.
 */
@Repository
public final class ProcesTellingenRepositoryImpl implements ProcesTellingenRepository {

    @PersistenceContext(name = "tellingEntityManagerFactory", unitName = "TellingEntities")
    private EntityManager em;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(value = "tellingTransactionManager", propagation = Propagation.REQUIRES_NEW)
    public ProcesTelling save(final ProcesTelling procesTelling) {
        if (procesTelling.getProcesnaam() == null) {
            em.persist(procesTelling);
            return procesTelling;
        } else {
            return em.merge(procesTelling);
        }
    }

    @Override
    @Transactional(value = "tellingTransactionManager", propagation = Propagation.REQUIRES_NEW)
    public ProcesTelling haalProcesTellingOp(final String procesnaam, final String kanaal, final String berichtType, final Timestamp datum) {
        final Query selectQuery =
                em.createQuery("SELECT pt FROM ProcesTelling pt WHERE pt.procesnaam = :procesnaam "
                        + "AND pt.datum = :datum AND pt.kanaal = :kanaal AND pt.berichtType = :berichtType");
        selectQuery.setParameter("procesnaam", procesnaam);
        selectQuery.setParameter("kanaal", kanaal);
        selectQuery.setParameter("berichtType", berichtType);
        selectQuery.setParameter("datum", datum, TemporalType.DATE);

        try {
            return (ProcesTelling) selectQuery.getSingleResult();
        } catch (final NoResultException exception) {
            return null;
        }
    }

}
