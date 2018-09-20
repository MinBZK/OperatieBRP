/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.isc.telling.repository.jpa;

import java.sql.Timestamp;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import nl.bzk.migratiebrp.isc.telling.repository.VirtueelProcesRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementatieklasse voor de Runtime Repository.
 */
@Repository
public final class VirtueelProcesRepositoryImpl implements VirtueelProcesRepository {

    @PersistenceContext(name = "tellingEntityManagerFactory", unitName = "TellingEntities")
    private EntityManager em;

    @Override
    @Transactional(value = "tellingTransactionManager", propagation = Propagation.REQUIRES_NEW)
    public int verwijder(final Timestamp datumTot) {
        final Query query = em.createQuery("delete from ProcesVirtueel where tijdstip < ?");
        query.setParameter(1, datumTot);
        return query.executeUpdate();

    }
}
