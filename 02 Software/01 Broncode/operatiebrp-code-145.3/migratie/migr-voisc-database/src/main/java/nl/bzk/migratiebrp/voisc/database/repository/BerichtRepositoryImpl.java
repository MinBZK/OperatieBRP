/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.migratiebrp.voisc.database.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import nl.bzk.algemeenbrp.util.common.logging.Logger;
import nl.bzk.algemeenbrp.util.common.logging.LoggerFactory;
import nl.bzk.migratiebrp.voisc.database.entities.Bericht;
import nl.bzk.migratiebrp.voisc.database.entities.StatusEnum;
import org.hibernate.NonUniqueResultException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * BerichtVoa repository implementatie.
 */
@Repository
public final class BerichtRepositoryImpl implements BerichtRepository {

    private static final String VOISC_TRANSACTION_MANAGER = "voiscTransactionManager";

    private static final Logger LOGGER = LoggerFactory.getLogger();

    private static final String PARAMETER_ORIGINATOR = "originator";
    private static final String PARAMETER_STATUS = "status";
    private static final String PARAMETER_TS = "ts";

    @PersistenceContext(name = "voiscEntityManagerFactory", unitName = "voiscEntityManagerFactory")
    private EntityManager em;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, value = VOISC_TRANSACTION_MANAGER)
    public Bericht save(final Bericht lo3Bericht) {
        if (lo3Bericht.getId() == null) {
            em.persist(lo3Bericht);
            return lo3Bericht;
        } else {
            return em.merge(lo3Bericht);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, value = VOISC_TRANSACTION_MANAGER)
    public List<Bericht> getBerichtToSendMBS(final String originator) {
        final TypedQuery<Bericht> query = em.createQuery("from Bericht where originator = :originator and status = :status", Bericht.class);
        query.setParameter(PARAMETER_ORIGINATOR, originator);
        query.setParameter(PARAMETER_STATUS, StatusEnum.RECEIVED_FROM_ISC);

        return query.getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, value = VOISC_TRANSACTION_MANAGER)
    public List<Bericht> getBerichtenToSendQueue(final int limit) {
        final TypedQuery<Bericht> query = em.createQuery("from Bericht where status = :status", Bericht.class);
        query.setParameter(PARAMETER_STATUS, StatusEnum.RECEIVED_FROM_MAILBOX);
        if (limit > 0) {
            query.setMaxResults(limit);
        }
        return query.getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, value = VOISC_TRANSACTION_MANAGER)
    public int verwijderVerzondenBerichtenOuderDan(final Date ouderDan, final Set<StatusEnum> statussen) {
        final Query query = em.createQuery("delete from Bericht where status in (:status) and tijdstip_verzonden < :ts");
        query.setParameter(PARAMETER_STATUS, statussen);
        query.setParameter(PARAMETER_TS, ouderDan);

        final int result = query.executeUpdate();
        if (result > 0) {
            LOGGER.info("{} berichten geschoond", result);
        }

        return result;
    }

    @Override
    public Bericht zoekObvDispatchSequenceNumber(final Integer dispatchSequenceNumber, final String originator, final String recipient) {
        final TypedQuery<Bericht> query =
                em.createQuery("from Bericht where dispatchSequenceNumber = :dispatchSequenceNumber "
                        + "and originator = :originator and recipient = :recipient", Bericht.class);
        query.setParameter("dispatchSequenceNumber", dispatchSequenceNumber);
        query.setParameter(PARAMETER_ORIGINATOR, originator);
        query.setParameter("recipient", recipient);

        final List<Bericht> result = query.getResultList();

        if (result.isEmpty()) {
            return null;
        } else if (result.size() == 1) {
            return result.get(0);
        } else {
            throw new NonUniqueResultException(result.size());
        }
    }

    @Override
    public int herstelStatus(final Date ouderDan, final StatusEnum statusVan, final StatusEnum statusNaar) {
        final Query query = em.createQuery("update Bericht set status = :statusNaar " + "where status = :statusVan and tijdstip_in_verwerking < :ts");

        query.setParameter(PARAMETER_TS, ouderDan);
        query.setParameter("statusVan", statusVan);
        query.setParameter("statusNaar", statusNaar);

        return query.executeUpdate();

    }

}
