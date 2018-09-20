/**
 * This file is copyright 2017 State of the Netherlands (Ministry of Interior Affairs and Kingdom Relations).
 * It is made available under the terms of the GNU Affero General Public License, version 3 as published by the Free Software Foundation.
 * The project of which this file is part, may be found at https://github.com/MinBZK/operatieBRP.
 */

package nl.bzk.brp.business.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;
import javax.sql.DataSource;

import nl.bzk.brp.business.stappen.locking.BsnLockerExceptie;
import nl.bzk.brp.business.service.BrpBusinessConfiguratie;
import nl.bzk.brp.business.service.BsnLocker;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;


/**
 * Creeert shared en exclusive locks op basis van BSN's. Deze implementatie is specifiek voor de Postgresql database.
 */
@Service
public class BsnLockerImpl implements BsnLocker {

    private static final Logger                  LOGGER                       = LoggerFactory
                                                                                      .getLogger(BsnLockerImpl.class);
    private static final ThreadLocal<Connection> TRANSACTIONSTATUSTHREADLOCAL = new ThreadLocal<Connection>();
    private static final int                     MSEC_IN_SECONDS              = 1000;

    @Inject
    @Named("dataSourceSpecial")
    private DataSource                           dataSource;

    @Inject
    private BrpBusinessConfiguratie              brpBusinessConfiguratie;

    private final QueryRunner                    queryRunner                  = new QueryRunner();

    /**
     * {@inheritDoc}
     */
    @Override
    public void getLocks(final Collection<String> readBsnLocks, final Collection<String> writeBsnLocks,
        final Long berichtId)
    {
        if (TRANSACTIONSTATUSTHREADLOCAL.get() != null) {
            throw new IllegalStateException(
                    "Deze thread heeft al een lock connectie, niet 2 keer per thread getLocks() aanroepen!");
        }
        try {
            creeerConnectieVoorTransactieEnPlaatsLock(readBsnLocks, writeBsnLocks, null);
        } catch (SQLException e) {
            // TODO: (Eric Jan Malotaux, 2012-04-19) Als er weer bericht ID's bekend zijn, is het handig om die te tonen
            // in onderstaande melding, om deze fout gemakkelijker te kunnen relateren aan het bericht dat die
            // veroorzaakt.
            String message =
                MessageFormatter.arrayFormat(
                        "Kan geen read-locks krijgen voor BSN's {} en/of write-locks voor BSN's {}, message ID {}.",
                        new Object[] { readBsnLocks, writeBsnLocks, berichtId }).getMessage();
            LOGGER.error(message, e);
            throw new BsnLockerExceptie(message, e);
        }
    }

    /**
     * Creeert een nieuwe (geconditioneerde) connectie, voegt deze toe aan de ThreadLocal voor later gebruik, en
     * plaatst locks op de opgegeven BSNs. Indien er een SQL gerelateerde fout optreedt wordt de connectie gerollbackt
     * en gesloten en de exceptie wordt opnieuw gegooid. <br/>
     * <br/>
     * <b>Let op:</b> Deze methode creeert dus een connectie, maar sluit deze (als er geen fouten optreden) niet! De
     * connectie wordt aan de ThreadLocal toegevoegd, zodat deze connectie verder nog in de thread gebruikt kan
     * worden. Het systeem dient er voor te zorgen dat als deze methode wordt aangeroepen, ook altijd nog de
     * {@link #unLock()} methode wordt aangeroepen.
     *
     * @param readBsnLocks BSNs die gelezen moeten worden.
     * @param writeBsnLocks BSNs die geschreven moeten worden.
     * @param berichtId TODO
     * @throws SQLException indien er een fout is opgetreden bij het conditioneren van de connectie of bij het plaatsen
     *             van de lock(s).
     */
    private void creeerConnectieVoorTransactieEnPlaatsLock(final Collection<String> readBsnLocks,
        final Collection<String> writeBsnLocks, final Long berichtId) throws SQLException
    {
        /*
         * De DataSourceUtils.getConnection zorgt ervoor dat we dezelfde connectie krijgen, en dezelfde transactie, als
         * alle andere data access.
         */
        Connection connectie = DataSourceUtils.getConnection(dataSource);
        try {
            conditioneerConnectie(connectie);
            TRANSACTIONSTATUSTHREADLOCAL.set(connectie);

            if (readBsnLocks != null && readBsnLocks.size() > 0) {
                for (String bsn : readBsnLocks) {
                    queryRunner.query(TRANSACTIONSTATUSTHREADLOCAL.get(), "SELECT pg_advisory_xact_lock_shared(?)",
                            new ScalarHandler(), Long.parseLong(bsn));
                }
            }
            if (writeBsnLocks != null && writeBsnLocks.size() > 0) {
                for (String bsn : writeBsnLocks) {
                    queryRunner.query(TRANSACTIONSTATUSTHREADLOCAL.get(), "SELECT pg_advisory_xact_lock(?)",
                            new ScalarHandler(), Long.parseLong(bsn));
                }
            }
        } catch (SQLException e) {
            String message =
                MessageFormatter.arrayFormat(
                        "Fout opgetreden bij het plaatsen van de read-locks voor BSN's {} en write-locks voor BSN's {};"
                            + " connectie wordt gerollbackt en gesloten, exceptie wordt (gewrapped) verder gegooid.",
                        new Object[] { readBsnLocks, writeBsnLocks, berichtId }).getMessage();
            LOGGER.error(message, e);
            LOGGER.error("SQLState: " + e.getSQLState());
            LOGGER.error("ErrorCode: " + e.getErrorCode());
            DbUtils.rollbackAndCloseQuietly(connectie);
            throw e;
        }
    }

    /**
     * Conditioneert een opgegeven connectie. Standaard zal de <code>autoCommit</code> op de connectie worden
     * uitgezet, zal het isolatie level op {@link Connection#TRANSACTION_SERIALIZABLE} worden gezet en zal de timeout
     * worden gezet.
     *
     * @param connectie de connectie die moet worden geconditioneerd.
     * @throws SQLException indien er een fout is opgetreden tijdens het conditioneren van de connectie.
     */
    private void conditioneerConnectie(final Connection connectie) throws SQLException {
        connectie.setAutoCommit(false);
        connectie.rollback();
        connectie.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        String timeOutStatement =
            "SET statement_timeout TO " + (brpBusinessConfiguratie.getDatabaseTimeOutProperty() * MSEC_IN_SECONDS);
        Statement statement = connectie.createStatement();
        try {
            statement.execute(timeOutStatement);
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unLock() {
        try {
            if (TRANSACTIONSTATUSTHREADLOCAL.get() != null) {
                TRANSACTIONSTATUSTHREADLOCAL.get().commit();
            }
        } catch (SQLException e) {
            LOGGER.error("bussines thread commit", e);
        } finally {
            try {
                if (TRANSACTIONSTATUSTHREADLOCAL.get() != null && !TRANSACTIONSTATUSTHREADLOCAL.get().isClosed()) {
                    TRANSACTIONSTATUSTHREADLOCAL.get().close();
                }
            } catch (SQLException e) {
                LOGGER.error("bussines thread close", e);
            } finally {
                TRANSACTIONSTATUSTHREADLOCAL.remove();
            }
        }
    }
}
