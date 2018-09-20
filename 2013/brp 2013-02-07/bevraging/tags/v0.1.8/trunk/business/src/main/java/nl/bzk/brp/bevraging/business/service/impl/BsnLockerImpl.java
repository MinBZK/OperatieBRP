/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.bevraging.business.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;
import javax.sql.DataSource;

import nl.bzk.brp.bevraging.business.configuratie.BrpConfiguratie;
import nl.bzk.brp.bevraging.business.handlers.BerichtVerwerkingsStap;
import nl.bzk.brp.bevraging.business.service.BsnLocker;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Implementatie van de BsnLocker die postgresql. {@inheritDoc}
 */

public class BsnLockerImpl implements BsnLocker {

    private static final Logger                  LOG                          = LoggerFactory
                                                                                      .getLogger(BsnLockerImpl.class);
    @Inject
    private DataSource                           dataSource;

    private static final ThreadLocal<Connection> TRANSACTIONSTATUSTHREADLOCAL = new ThreadLocal<Connection>();

    private static final int                     MSEC_IN_SECONDS              = 1000;

    private static final String                  TABLE                        = "Kern.PersoonsLock";

    private static final String                  COLUMN                       = "BSN";

    /**
     * Converteert een aantal objecten naar een string met comma's er tussen.
     *
     * @param <T> type van collection
     * @param collection die geimplodeert moet worden
     * @return String met comma seperation
     */
    private static <T> String implode(final Collection<T> collection) {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (T mylong : collection) {
            if (!first) {
                result.append(",");
            }
            result.append(mylong);
            first = false;
        }

        return result.toString();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public boolean getLocks(final Long berichtId, final Collection<Long> readBSNLocks,
            final Collection<Long> writeBSNLocks)
    {
        Connection insertCon = null;

        try {

            if (TRANSACTIONSTATUSTHREADLOCAL.get() != null) {
                throw new IllegalArgumentException(
                        "Deze thread heeft al een lock connectie, niet 2 keer per thread getLocks() aanroepen");
            }

            // CHECKSTYLE:OFF
            @SuppressWarnings("unchecked")
            Collection<Long> alleBSNs =
                CollectionUtils.union(readBSNLocks != null ? readBSNLocks : Collections.EMPTY_LIST,
                        writeBSNLocks != null ? writeBSNLocks : Collections.EMPTY_LIST);
            // CHECKSTYLE:ON

            insertCon = getConnection();

            for (Long bsn : alleBSNs) {
                new QueryRunner().update(insertCon,
                        "INSERT INTO " + TABLE + " (" + COLUMN + ") "
                            + "SELECT " + bsn + " WHERE NOT EXISTS"
                                + " (SELECT true FROM " + TABLE + " WHERE " + COLUMN + "=?)", bsn);
            }

            DbUtils.commitAndClose(insertCon);
            insertCon = null;

            TRANSACTIONSTATUSTHREADLOCAL.set(getConnection());

            if (readBSNLocks != null && readBSNLocks.size() > 0) {
                new QueryRunner().query(TRANSACTIONSTATUSTHREADLOCAL.get(),
                                "SELECT " + COLUMN + " FROM " + TABLE + " WHERE " + COLUMN + " IN ("
                                    + implode(readBSNLocks) + ") FOR SHARE", new ScalarHandler());
            }

            if (writeBSNLocks != null && writeBSNLocks.size() > 0) {
                new QueryRunner().query(TRANSACTIONSTATUSTHREADLOCAL.get(),
                                "SELECT " + COLUMN + " FROM " + TABLE + " WHERE " + COLUMN + " IN ("
                                    + implode(writeBSNLocks) + ") FOR UPDATE", new ScalarHandler());
            }

            return BerichtVerwerkingsStap.DOORGAAN_MET_VERWERKING;

        } catch (Throwable e) {
            LOG.error("Kan geen lock krijgen, berichtId=" + berichtId, e);
            return BerichtVerwerkingsStap.STOP_VERWERKING;
        } finally {
            try {
                DbUtils.rollbackAndClose(insertCon);
            } catch (SQLException e) {
                LOG.error("close() van de lock select transactie", e);
            }
        }
    }

    /**
     * Creert een nieuwe connectie koud van de datasource, en wordt conform
     * transactionTimeoutInSeconds geconditioneerd.
     *
     * @return de geconditioneerde connectie
     * @throws SQLException als het conditioneren verkeerd gaat
     */

    private Connection getConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        String timeOutStatement = "SET statement_timeout TO "
                + (BrpConfiguratie.getDatabaseTimeOutProperty() * MSEC_IN_SECONDS);
        connection.createStatement().execute(timeOutStatement);
        return connection;
    }

    @Override
    public void unLock() {
        try {
            if (TRANSACTIONSTATUSTHREADLOCAL.get() != null) {
                TRANSACTIONSTATUSTHREADLOCAL.get().commit();
            }
        } catch (Throwable e) {
            LOG.error("bussines thread commit", e);
        } finally {
            try {
                if (TRANSACTIONSTATUSTHREADLOCAL.get() != null && !TRANSACTIONSTATUSTHREADLOCAL.get().isClosed()) {
                    TRANSACTIONSTATUSTHREADLOCAL.get().close();
                }
            } catch (Throwable e) {
                LOG.error("bussines thread close", e);
            } finally {
                TRANSACTIONSTATUSTHREADLOCAL.remove();
            }
        }
    }
}
