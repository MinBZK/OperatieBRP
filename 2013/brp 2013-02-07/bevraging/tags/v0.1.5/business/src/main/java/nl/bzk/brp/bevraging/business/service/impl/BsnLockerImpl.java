/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.service.impl;

import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nl.bzk.brp.bevraging.business.handlers.BerichtVerwerkingsStap;
import nl.bzk.brp.bevraging.business.service.BsnLocker;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;


/**
 * Implementatie van de BsnLocker die postgresql. {@inheritDoc}
 */

public class BsnLockerImpl implements BsnLocker {

    private static final Logger                   LOG             = LoggerFactory.getLogger(BsnLockerImpl.class);

    @Inject
    private AbstractPlatformTransactionManager    transactionManager;

    @PersistenceContext
    private EntityManager                         em;

    private Long                                  transactionTimeoutInSeconds;

    private static ThreadLocal<TransactionStatus> transactionStatusThreadLocal;

    private static final int                      MSEC_IN_SECONDS = 1000;

    private static final String                   TABLE           = "Kern.PersoonsLock";

    private static final String                   COLUMN          = "BSN";

    /**
     * Converteert een aantal object naar een strin met comma's er tussen.
     *
     * @param <T> type van collection
     * @param collection die geimplodeert moet worden
     * @return String met comma seperation
     */
    private static <T> String implode(final Collection<T> collection) {
        String result = "";
        boolean first = true;

        for (T mylong : collection) {
            // CHECKSTYLE:OFF
            result += (first ? "" : ",") + ("" + mylong);
            // CHECKSTYLE:ON
            first = false;
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getLocks(final Long berichtId, final Collection<Long> readBSNLocks,
            final Collection<Long> writeBSNLocks)
    {
        try {

            final DefaultTransactionDefinition def = new DefaultTransactionDefinition();

            def.setName("Locktransactie t.b.v. berichtId " + berichtId);

            // Method org.postgresql.jdbc4.Jdbc4PreparedStatement.setQueryTimeout(int) is not yet implemented.
            // def.setTimeout(TIMEOUT_IN_SECONDS);

            TransactionStatus insertTransactionStatus = transactionManager.getTransaction(def);
            em.createNativeQuery("SET statement_timeout TO " + (transactionTimeoutInSeconds * MSEC_IN_SECONDS))
                    .executeUpdate();
            em.flush();

            //CHECKSTYLE:OFF
            @SuppressWarnings("unchecked")
            Collection<Long> alleBSNs =
                CollectionUtils.union(
                        readBSNLocks != null ? readBSNLocks : Collections.EMPTY_LIST,
                        writeBSNLocks != null ? writeBSNLocks : Collections.EMPTY_LIST);
            //CHECKSTYLE:ON

            for (Long bsn : alleBSNs) {
                em.createNativeQuery(
                        "INSERT INTO " + TABLE + " (" + COLUMN + ") SELECT " + bsn
                            + " WHERE NOT EXISTS (SELECT true FROM " + TABLE + " WHERE " + COLUMN + "=" + bsn + ")")
                        .executeUpdate();
            }
            transactionManager.commit(insertTransactionStatus);

            transactionStatusThreadLocal = new ThreadLocal<TransactionStatus>() {

                @Override
                protected TransactionStatus initialValue() {
                    return transactionManager.getTransaction(def);
                }
            };

            transactionStatusThreadLocal.get();

            em.createNativeQuery("SET statement_timeout TO " + (transactionTimeoutInSeconds * MSEC_IN_SECONDS))
                    .executeUpdate();
            em.flush();

            if (readBSNLocks != null) {
                em.createNativeQuery(
                        "SELECT * FROM " + TABLE + " WHERE " + COLUMN + " IN (" + implode(readBSNLocks) + ") FOR SHARE")
                        .getResultList();
            }

            if (writeBSNLocks != null) {
                em.createNativeQuery(
                        "SELECT * FROM " + TABLE + " WHERE " + COLUMN + " IN (" + implode(writeBSNLocks)
                            + ") FOR UPDATE").getResultList();
            }
            em.flush();

            return BerichtVerwerkingsStap.DOORGAAN_MET_VERWERKING;
        } catch (Exception e) {
            LOG.error("Kan geen lock krijgen berichtId=" + berichtId, e);
            return BerichtVerwerkingsStap.STOP_VERWERKING;
        }
    }

    public Long getTransactionTimeoutInSeconds() {
        return transactionTimeoutInSeconds;
    }

    public void setTransactionTimeoutInSeconds(final Long transactionTimeoutInSeconds) {
        this.transactionTimeoutInSeconds = transactionTimeoutInSeconds;
    }

    @Override
    public void unLock() {
        transactionManager.commit(transactionStatusThreadLocal.get());
    }

}
